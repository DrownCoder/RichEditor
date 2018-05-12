package com.study.xuan.editor.operate.parse;

import android.graphics.Typeface;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.AlignmentSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.SparseArray;

import com.study.xuan.editor.callback.onFormatCallback;
import com.study.xuan.editor.common.Const;
import com.study.xuan.editor.model.RichModel;
import com.study.xuan.editor.model.SpanModel;
import com.study.xuan.editor.operate.RichBuilder;
import com.study.xuan.editor.operate.helper.RichModelHelper;
import com.study.xuan.editor.operate.param.IParamManger;
import com.study.xuan.editor.operate.span.factory.IAbstractSpanFactory;
import com.study.xuan.editor.operate.span.richspan.ReferSpan;
import com.study.xuan.editor.operate.span.richspan.URLSpanNoUnderline;
import com.study.xuan.editor.widget.panel.IPanel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author : xuan.
 * Date : 18-4-9.
 * Description : span转换成MarkDown语法的String
 */
public class MarkDownParser extends Parser {
    private Formater formater;
    private Transformer transformer;
    private SparseArray<String> pool;
    private IParamManger paramManager;
    private IAbstractSpanFactory factory;
    private IPanel panel;
    private int priority = 0;

    private onFormatCallback callback;

    public MarkDownParser() {
        formater = new MarkDownFormater();
        transformer = new MarkDownTransformer();
        pool = new SparseArray<>();
    }

    @Override
    public String toString(List<RichModel> data) {
        if (data == null) {
            return null;
        }
        int count = data.size();
        StringBuilder str = new StringBuilder();
        int line = 0;
        for (RichModel model : data) {
            if (callback != null) {
                callback.onFormat(line++, count);
            }
            RichModelHelper.formatSpan(model.getSpanList());
            //规整区间结束后，将字符串按照区间分割并重新拼接,按照优先级将字符串分割成一小段
            // ，（因为可能存在顺序不是递增的，所以需要用优先级），最后再按照优先级将拼接成整体。

            //拼接字符级别markdown
            if (model.getSpanList() == null || model.getSpanList().size() == 0) {
                str.append(model.source).append("\n");
            } else {
                SpanModel item;
                int lastEnd = 0;
                for (int i = 0; i < model.getSpanList().size(); i++) {
                    item = model.getSpanList().get(i);
                    if (lastEnd != item.start) {
                        pool.put(priority++, model.source.substring(lastEnd, item.start));
                        //str.append(model.source.substring(lastEnd, item.start));
                    } else {
                        //两个相邻的span之间markdown需要用空格隔开
                        pool.put(priority++, " ");
                        //str.append(" ");
                    }
                    pool.put(item.start, spanToMarkDown(item.mSpans, model.source, item.start, item.end));
                    priority = item.start + 1;
                    //str.append(spanToMarkDown(item.mSpans, model.source, item.start, item.end));
                    lastEnd = item.end;
                }
                pool.put(priority++, model.source.substring(lastEnd, model.source.length()));
                //str.append(model.source.substring(lastEnd, model.source.length())).append("\n");
                for (int t = 0; t < pool.size(); t++) {
                    str.append(pool.valueAt(t));
                }
                pool.clear();
            }
            //拼接段落级别markdown语法
            if (model.isParagraphStyle) {
                str.replace(0, str.length(), paragraphToMarkDown(model.paragraphSpan.mSpans, str));
            }
            str.append("\n");
        }
        return str.toString();
    }

    private String paragraphToMarkDown(List<Object> mSpans, StringBuilder source) {
        if (mSpans == null || mSpans.size() == 0) {
            return source.toString();
        }
        String str = source.toString();
        for (Object obj : mSpans) {
            if (obj instanceof ReferSpan) {
                str = formater.formatRefer(str);
            } else if (obj instanceof AlignmentSpan) {
                //markdown是不支持居中的！！！，后期考虑注释掉
                // str = formater.formatCenter(str);
            } else if (obj instanceof AbsoluteSizeSpan) {
                AbsoluteSizeSpan sizeSpan = (AbsoluteSizeSpan) obj;
                switch (sizeSpan.getSize()) {
                    case Const.T1_SIZE:
                        str = formater.formatH1(str);
                        break;
                    case Const.T2_SIZE:
                        str = formater.formatH2(str);
                        break;
                    case Const.T3_SIZE:
                        str = formater.formatH3(str);
                        break;
                    case Const.T4_SIZE:
                        str = formater.formatH4(str);
                        break;
                }
            }
        }
        return str;
    }

    @Override
    public List<RichModel> fromString(String result) {
        if (TextUtils.isEmpty(result)) {
            return null;
        }
        RichBuilder.getInstance().reset(false);
        factory = RichBuilder.getInstance().getFactory();
        paramManager = RichBuilder.getInstance().getManger();
        panel = RichBuilder.getInstance().getPanelBuilder();

        List<String> lineArr = splitLine(result);
        List<RichModel> data = new ArrayList<>();
        for (String line : lineArr) {
            //空行
            if (TextUtils.isEmpty(line)) {
                continue;
            }
            reset();
            RichModel model = MarkDownToSpan(line);
            data.add(model);
        }
        return data;
    }

    /**
     *
     */
    private RichModel MarkDownToSpan(String line) {
        RichModel model = new RichModel();
        if (Pattern.matches(transformer.imageRegex(), line)) {
            model.isImg().setSource(transformer.imageUrl(line));
        } else {
            model.isText();
        }
        Pattern pattern = Pattern.compile(transformer.paragraphRegex());
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            //是段落样式
            int end = matcher.end();
            initParagraph(line, end);
            line = line.substring(end);
            SpanModel span = new SpanModel(panel.getParagraphType());
            span.code = paramManager.getParamCode(panel.getFontParam(), panel.getParagraphType());
            span.mSpans = factory.createSpan(span.code);
            model.setParagraphSpan(span);
        } else {
            model.setNoParagraphSpan();
        }
        pattern = Pattern.compile(transformer.fontRegex());
        matcher = pattern.matcher(line);
        int index;
        int lastIndex = 0;
        while (matcher.find()) {
            String itemStr = matcher.group();
            SpanModel span = new SpanModel();
            index = matcher.start();
            putStr(line.substring(lastIndex, index));
            span.start = obtainStr().length();
            itemStr = initFont(itemStr);
            span.param = paramManager.cloneParam(panel.getFontParam());
            span.code = paramManager.getParamCode(panel.getFontParam(), Const.PARAGRAPH_NONE);
            span.mSpans = factory.createSpan(span.code);
            span.end = span.start + itemStr.length();
            lastIndex = matcher.end();
            model.addSpanModel(span);
        }
        putStr(line.substring(lastIndex, line.length()));
        model.setSource(obtainStr());
        return model;
    }

    private String initFont(String itemStr) {
        if (Pattern.matches(transformer.boldRegex(), itemStr)) {
            panel.setBold(true);
            return initFont(itemStr.substring(2, itemStr.length() - 2));
        } else if (Pattern.matches(transformer.italicsRegex(), itemStr)) {
            panel.setItalics(true);
            return initFont(itemStr.substring(1, itemStr.length() - 1));
        } else if (Pattern.matches(transformer.centerRegex(), itemStr)) {
            panel.setCenterLine(true);
            return initFont(itemStr.substring(2, itemStr.length() - 2));
        }
        putStr(itemStr);
        return itemStr;
    }

    private void initParagraph(String line, int start) {
        String startStr = line.substring(0, start);
        switch (startStr) {
            case "#":
                panel.setH1(true);
                putStr(line.substring(1));
                break;
            case "##":
                panel.setH2(true);
                putStr(line.substring(2));
                break;
            case "###":
                panel.setH3(true);
                putStr(line.substring(3));
                break;
            case "####":
                panel.setH4(true);
                putStr(line.substring(4));
                break;
            case ">":
                panel.setRefer(true);
                putStr(line.substring(1));
                break;
        }
    }

    private List<String> splitLine(String result) {
        BufferedReader rdr = new BufferedReader(new StringReader(result));
        List<String> lines = new ArrayList<String>();
        try {
            for (String line = rdr.readLine(); line != null; line = rdr.readLine()) {
                lines.add(line);
            }
            rdr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }


    private String spanToMarkDown(List<Object> mSpans, String source, int start, int end) {
        if (mSpans == null || mSpans.size() == 0) {
            return source.substring(start, end);
        }
        String str = source.substring(start, end);
        for (Object obj : mSpans) {
            if (obj instanceof StyleSpan) {
                StyleSpan objSpan = (StyleSpan) obj;
                switch (objSpan.getStyle()) {
                    case Typeface.BOLD:
                        str = formater.formatBold(str);
                        break;
                    case Typeface.ITALIC:
                        str = formater.formatItalics(str);
                        break;
                }
            } else if (obj instanceof UnderlineSpan) {
                //markdown是不支持下划线的！！！，后期考虑注释掉
                str = formater.formatUnderLine(str);
            } else if (obj instanceof StrikethroughSpan) {
                str = formater.formatCenterLine(str);
            } else if (obj instanceof URLSpanNoUnderline) {
                URLSpanNoUnderline url = (URLSpanNoUnderline) obj;
                str = formater.formatLink(str, url.getURL());
            }
        }
        return str;
    }

    private void reset() {
        if (pool != null) {
            pool.clear();
        } else {
            pool = new SparseArray<>();
        }
        priority = 0;
    }

    private void putStr(String str) {
        pool.put(priority++, str);
    }

    public String obtainStr() {
        StringBuilder str = new StringBuilder();
        for (int t = 0; t < pool.size(); t++) {
            str.append(pool.valueAt(t));
        }
        return str.toString();
    }

    public void setCallback(onFormatCallback callback) {
        this.callback = callback;
    }
}
