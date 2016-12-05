package com.lll.common.ui.tint;

import android.content.Context;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatMultiAutoCompleteTextView;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Version 1.0
 * Created by lll on 16/10/31.
 * Description
 * copyright generalray4239@gmail.com
 */

public class ThemeDelegate implements LayoutInflaterFactory {

    /**
     * Mast be call before Activity oncreate()
     * @param inflater
     */
    public void setLayoutFactory(LayoutInflater inflater) {
        if (inflater.getFactory() != null) {
            LayoutInflaterCompat.setFactory(inflater, this);
        }
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        Context ctx = parent.getContext();
        switch (name){//替换掉自己创建的View
            case "EditText":
                return new AppCompatEditText(context, attrs);
            case "Spinner":
                return new AppCompatSpinner(context, attrs);
            case "CheckBox":
                return new AppCompatCheckBox(context, attrs);
            case "RadioButton":
                return new AppCompatRadioButton(context, attrs);
            case "CheckedTextView":
                return new AppCompatCheckedTextView(context, attrs);
            case "AutoCompleteTextView":
                return new AppCompatAutoCompleteTextView(context, attrs);
            case "MultiAutoCompleteTextView":
                return new AppCompatMultiAutoCompleteTextView(context, attrs);
            case "RatingBar":
                return new AppCompatRatingBar(context, attrs);
            case "Button":
                return new AppCompatButton(context, attrs);
            case "TextView":
                return new AppCompatTextView(context, attrs);

        }
        return null;
    }
}
