package com.zhl.huiqu.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;

import com.zhl.huiqu.R;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/10/23.
 */

public class TrafficParser {

    private Context mContext;
    private String[] mSmileyTexts;
    private Pattern mPattern;
    private HashMap<String, Integer> mSmileyToRes;
    public static final int[] DEFAULT_TRAFFIC_RES_IDS = {
            R.drawable.dabache,
            R.drawable.huoche,
            R.drawable.feiji,
            R.drawable.lunc,
    };

    public TrafficParser(Context context) {
        mContext = context;
        mSmileyTexts = mContext.getResources().getStringArray(DEFAULT_SMILEY_TEXTS);
        mSmileyToRes = buildSmileyToRes();
        mPattern = buildPattern();
    }

    public static final int DEFAULT_SMILEY_TEXTS = R.array.default_traffic_texts;

    private HashMap<String, Integer> buildSmileyToRes() {
        if (DEFAULT_TRAFFIC_RES_IDS.length != mSmileyTexts.length) {
//          Log.w("SmileyParser", "Smiley resource ID/text mismatch");
            //表情的数量需要和数组定义的长度一致！
            throw new IllegalStateException("Smiley resource ID/text mismatch");
        }

        HashMap<String, Integer> smileyToRes = new HashMap<String, Integer>(mSmileyTexts.length);
        for (int i = 0; i < mSmileyTexts.length; i++) {
            smileyToRes.put(mSmileyTexts[i], DEFAULT_TRAFFIC_RES_IDS[i]);
        }

        return smileyToRes;
    }

    //构建正则表达式
    private Pattern buildPattern() {
        StringBuilder patternString = new StringBuilder(mSmileyTexts.length * 3);
        patternString.append('(');
        for (String s : mSmileyTexts) {
            patternString.append(Pattern.quote(s));
            patternString.append('|');
        }
        patternString.replace(patternString.length() - 1, patternString.length(), ")");

        return Pattern.compile(patternString.toString());
    }

    //根据文本替换成图片
    public CharSequence replace(CharSequence text) {
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        Matcher matcher = mPattern.matcher(text);
        while (matcher.find()) {
            int resId = mSmileyToRes.get(matcher.group());
            builder.setSpan(new MyIm(mContext, resId),matcher.start(), matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return builder;
    }

    public class MyIm extends ImageSpan
    {
        public MyIm(Context arg0,int arg1) {
            super(arg0, arg1);
        }
        public int getSize(Paint paint, CharSequence text, int start, int end,
                           Paint.FontMetricsInt fm) {
            Drawable d = getDrawable();
            Rect rect = d.getBounds();
            if (fm != null) {
                Paint.FontMetricsInt fmPaint=paint.getFontMetricsInt();
                int fontHeight = fmPaint.bottom - fmPaint.top;
                int drHeight=rect.bottom-rect.top;

                int top= drHeight/2 - fontHeight/4;
                int bottom=drHeight/2 + fontHeight/4;

                fm.ascent=-bottom;
                fm.top=-bottom;
                fm.bottom=top;
                fm.descent=top;
            }
            return rect.right;
        }

        @Override
        public void draw(Canvas canvas, CharSequence text, int start, int end,
                         float x, int top, int y, int bottom, Paint paint) {
            Drawable b = getDrawable();
            canvas.save();
            int transY = 0;
            transY = ((bottom-top) - b.getBounds().bottom)/2+top;
            canvas.translate(x, transY);
            b.draw(canvas);
            canvas.restore();
        }
    }

}
