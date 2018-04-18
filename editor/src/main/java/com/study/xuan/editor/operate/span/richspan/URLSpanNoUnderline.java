package com.study.xuan.editor.operate.span.richspan;

import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.URLSpan;

public class URLSpanNoUnderline extends URLSpan {
    public URLSpanNoUnderline(String url) {  
        super(url);  
    }  

    @Override  
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);  
        ds.setUnderlineText(false);  
        ds.setColor(Color.parseColor("#3194d0"));
    }  
}  

