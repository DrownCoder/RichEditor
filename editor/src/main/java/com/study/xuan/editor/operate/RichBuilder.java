package com.study.xuan.editor.operate;

import com.study.xuan.editor.common.Const;
import com.study.xuan.editor.operate.font.FontParam;
import com.study.xuan.editor.operate.param.IParamManger;
import com.study.xuan.editor.operate.param.ParamManager;
import com.study.xuan.editor.operate.span.factory.AbstractSpanFactory;
import com.study.xuan.editor.operate.span.factory.IAbstractSpanFactory;
import com.study.xuan.editor.widget.panel.IPanel;
import com.study.xuan.editor.widget.panel.PanelBuilder;

/**
 * Author : xuan.
 * Date : 18-3-24.
 * Description : RichEditor的构造者
 */
public class RichBuilder {
    public static final int SELECT_STATUS = 1000;
    public static final int CLICK_STATUS = 1001;
    public static final int NORMAL_STATUS = 1002;
    private volatile static RichBuilder builder;
    private int status;
    private IPanel panelBuilder;
    private IParamManger manger;
    private IAbstractSpanFactory factory;

    public static synchronized RichBuilder getInstance() {
        if (builder == null) {
            synchronized (RichBuilder.class) {
                if (builder == null) {
                    builder = new RichBuilder();
                }
            }
        }
        return builder;
    }

    private RichBuilder() {
        panelBuilder = new PanelBuilder();
        manger = new ParamManager();
        factory = new AbstractSpanFactory();
    }

    public IPanel getPanelBuilder() {
        return panelBuilder;
    }

    public IParamManger getManger() {
        return manger;
    }

    public IAbstractSpanFactory getFactory() {
        return factory;
    }

    public void destroy() {
        panelBuilder = null;
        manger = null;
        factory = null;
        builder = null;
    }

    public void resetParam(FontParam param, int paragraphType) {
        panelBuilder.reverse(param, paragraphType);
        manger.reset().setCurrentParam(param);
    }

    public void resetParam(FontParam param) {
        panelBuilder.reverse(param, Const.PARAGRAPH_NONE);
        manger.reset().setCurrentParam(param);
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void reset() {
        panelBuilder.reset();
        manger.reset();
        status = NORMAL_STATUS;
    }
}
