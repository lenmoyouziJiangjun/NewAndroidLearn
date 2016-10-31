package com.lll.common.ui.tint;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.TintableBackgroundView;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.lll.common.ui.utils.ColorUtils;
import com.tima.common.ui.R;

import java.lang.reflect.Field;

/**
 * Version 1.0
 * Created by lll on 16/10/25.
 * Description 背景着色工具类
 * copyright generalray4239@gmail.com
 */

public class TintHelper {

    @SuppressLint("PrivateResource")
    @ColorInt
    private static int getDefaultRippleColor(@NonNull Context context, boolean useDarkRipple) {
        // Light ripple is actually translucent black, and vice versa
        return ContextCompat.getColor(context, useDarkRipple ?
                R.color.ripple_material_light : R.color.ripple_material_dark);
    }

    @NonNull
    private static ColorStateList getDisabledColorStateList(@ColorInt int normal, @ColorInt int disabled) {

        /**
         * 第一个二维数组,表示对应的状态
         * 第二个一维数组,表示对应状态的颜色
         */
        return new ColorStateList(new int[][]{
                new int[]{-android.R.attr.state_enabled},
                new int[]{android.R.attr.state_enabled}
        }, new int[]{
                disabled,
                normal
        });


    }

    /**
     * 给View的背景着色
     *
     * @param view
     * @param color
     * @param darker
     * @param useDarkTheme
     */
    public static void setTintSelector(@NonNull View view, @ColorInt final int color, final boolean darker, final boolean useDarkTheme) {
        final boolean isColorLight = ColorUtils.isColorLight(color);
        final int disabledColor = ContextCompat.getColor(view.getContext(), useDarkTheme ? R.color.ate_button_disabled_dark : R.color.ate_button_disabled_light);
        final int pressedColor = ColorUtils.shiftColor(color, darker ? 0.9f : 1.1f);
        final int activatedColor = ColorUtils.shiftColor(color, darker ? 1.1f : 0.9f);
        final int textColor = ContextCompat.getColor(view.getContext(), isColorLight ? R.color.ate_primary_text_light : R.color.ate_primary_text_dark);
        //涟漪颜色
        final int rippleColor = getDefaultRippleColor(view.getContext(), isColorLight);

        final ColorStateList sl;
        if (view instanceof Button) {
            sl = getDisabledColorStateList(color, disabledColor);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                    && view.getBackground() instanceof RippleDrawable) {//5.0以后
                RippleDrawable rd = (RippleDrawable) view.getBackground();
                rd.setColor(ColorStateList.valueOf(rippleColor));
            }
            final Button button = (Button) view;
            button.setTextColor(getDisabledColorStateList(textColor, ContextCompat.getColor(view.getContext(), useDarkTheme ? R.color.ate_button_text_disabled_dark : R.color.ate_button_text_disabled_light)));
        } else {
            sl = new ColorStateList(new int[][]{
                    new int[]{-android.R.attr.state_enabled},
                    new int[]{android.R.attr.state_enabled},
                    new int[]{android.R.attr.state_enabled, android.R.attr.state_pressed},
                    new int[]{android.R.attr.state_enabled, android.R.attr.state_activated},
                    new int[]{android.R.attr.state_enabled, android.R.attr.state_checked}
            }, new int[]{
                    disabledColor,
                    color,
                    pressedColor,
                    activatedColor,
                    activatedColor
            });
        }

        Drawable drawable = view.getBackground();
        if (drawable != null) {
            drawable = createTintedDrawable(drawable, sl);
            setViewBackgroud(view, drawable);
        }
        if (view instanceof TextView && !(view instanceof Button)) {
            final TextView tv = (TextView) view;
            tv.setTextColor(getDisabledColorStateList(textColor, ContextCompat.getColor(view.getContext(), isColorLight ? R.color.ate_text_disabled_light : R.color.ate_text_disabled_dark)));
        }
    }

    /**
     * @param view
     * @param color
     * @param background
     * @param isDark
     */
    public static void setViewTint(View view, int color, boolean background, boolean isDark) {
        if (!background) {

        } else {
            if (view instanceof Button) {
                setTintSelector(view, color, isDark, background);
            } else if (view.getBackground() != null) {
                Drawable d = view.getBackground();
                createTintedDrawable(d, color);
                setViewBackgroud(view, d);
            }
        }
    }

    /**
     * 给RadioButton着色
     *
     * @param radioButton
     * @param color
     * @param useDarker
     */
    public static void setTint(@NonNull RadioButton radioButton, @ColorInt int color, boolean useDarker) {
        ColorStateList list = new ColorStateList(new int[][]{//定义三个不同状态
                new int[]{-android.R.attr.state_enabled},//不可用
                new int[]{android.R.attr.state_enabled, -android.R.attr.state_checked},//可用,没选中
                new int[]{android.R.attr.state_enabled, android.R.attr.state_checked}//可用,选中
        }, new int[]{//三个状态对应的颜色
                ColorUtils.stripAlpha(ContextCompat.getColor(radioButton.getContext(), useDarker ? R.color.ate_control_disabled_dark : R.color.ate_control_disabled_light)),
                ContextCompat.getColor(radioButton.getContext(), useDarker ? R.color.ate_control_normal_dark : R.color.ate_control_normal_light),
                color
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0以后的设置
            radioButton.setBackgroundTintList(list);
        } else {
            Drawable d = createTintedDrawable(ContextCompat.getDrawable(radioButton.getContext(), R.drawable.abc_btn_radio_material), list);
            radioButton.setBackgroundDrawable(d);
        }
    }

    /**
     * 给SeekBar着色
     *
     * @param seekBar
     * @param color
     * @param useDarker
     */
    public static void setTint(@NonNull SeekBar seekBar, @ColorInt int color, boolean useDarker) {
        final ColorStateList s1 = getDisabledColorStateList(color,
                ContextCompat.getColor(seekBar.getContext(), useDarker ? R.color.ate_control_disabled_dark : R.color.ate_control_disabled_light));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            seekBar.setThumbTintList(s1);
            seekBar.setProgressTintList(s1);
        } else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) {
            Drawable progressDrawable = createTintedDrawable(seekBar.getProgressDrawable(), s1);
            seekBar.setProgressDrawable(progressDrawable);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                Drawable thumbDrawable = createTintedDrawable(seekBar.getThumb(), s1);
                seekBar.setThumb(thumbDrawable);
            }
        } else {
            PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1) {
                mode = PorterDuff.Mode.MULTIPLY;
            }
            if (seekBar.getIndeterminateDrawable() != null)
                seekBar.getIndeterminateDrawable().setColorFilter(color, mode);
            if (seekBar.getProgressDrawable() != null)
                seekBar.getProgressDrawable().setColorFilter(color, mode);
        }
    }


    /**
     * @param progressBar
     * @param color
     * @param skipIndeterminate 滑动图标
     */
    public static void setTint(@NonNull ProgressBar progressBar, @ColorInt int color, boolean skipIndeterminate) {
        ColorStateList sl = ColorStateList.valueOf(color);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            progressBar.setProgressTintList(sl);
            progressBar.setSecondaryProgressTintList(sl);
            if(!skipIndeterminate){
                progressBar.setIndeterminateTintList(sl);
            }
        }else{
            PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1) {
                mode = PorterDuff.Mode.MULTIPLY;
            }
            if (!skipIndeterminate && progressBar.getIndeterminateDrawable() != null)
                progressBar.getIndeterminateDrawable().setColorFilter(color, mode);
            if (progressBar.getProgressDrawable() != null)
                progressBar.getProgressDrawable().setColorFilter(color, mode);
        }
    }

    public static void setTint(@NonNull EditText editText, @ColorInt int color, boolean useDarker) {
        final ColorStateList editTextColorStateList = new ColorStateList(new int[][]{
                new int[]{-android.R.attr.state_enabled},
                new int[]{android.R.attr.state_enabled,
                        -android.R.attr.state_pressed, -android.R.attr.state_focused},
                new int[]{}
        }, new int[]{
                ContextCompat.getColor(editText.getContext(), useDarker ? R.color.ate_text_disabled_dark : R.color.ate_text_disabled_light),
                ContextCompat.getColor(editText.getContext(), useDarker ? R.color.ate_control_normal_dark : R.color.ate_control_normal_light),
                color
        });
        if (editText instanceof TintableBackgroundView) {
            ViewCompat.setBackgroundTintList(editText, editTextColorStateList);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            editText.setBackgroundTintList(editTextColorStateList);
        }
        setCursorTint(editText, color);
    }

    public static void setTint(@NonNull CheckBox box, @ColorInt int color, boolean useDarker) {
        ColorStateList sl = new ColorStateList(new int[][]{
                new int[]{-android.R.attr.state_enabled},
                new int[]{android.R.attr.state_enabled, -android.R.attr.state_checked},
                new int[]{android.R.attr.state_enabled, android.R.attr.state_checked}
        }, new int[]{
                ContextCompat.getColor(box.getContext(), useDarker ? R.color.ate_control_disabled_dark : R.color.ate_control_disabled_light),
                ContextCompat.getColor(box.getContext(), useDarker ? R.color.ate_control_normal_dark : R.color.ate_control_normal_light),
                color
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            box.setButtonTintList(sl);
        } else {
            Drawable drawable = createTintedDrawable(ContextCompat.getDrawable(box.getContext(), R.drawable.abc_btn_check_material), sl);
            box.setButtonDrawable(drawable);
        }
    }

    /**
     * 给Drawable着色
     *
     * @param drawable
     * @param list
     * @return
     */
    private static Drawable createTintedDrawable(@NonNull Drawable drawable, @NonNull ColorStateList list) {
        if (drawable == null) {
            return null;
        }
        drawable = DrawableCompat.wrap(drawable.mutate());
        DrawableCompat.setTintList(drawable, list);
        return drawable;
    }

    /**
     * @param drawable
     * @param color
     * @return
     */
    private static Drawable createTintedDrawable(@NonNull Drawable drawable, @ColorInt int color) {
        if (drawable == null) return null;
        drawable = DrawableCompat.wrap(drawable.mutate());
        DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_IN);
        DrawableCompat.setTint(drawable, color);
        return drawable;
    }

    /**
     * 设置背景颜色
     *
     * @param view
     * @param drawable
     */
    public static void setViewBackgroud(View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }


    /**
     *
     * @param editText
     * @param color
     */
    public static void setCursorTint(@NonNull EditText editText,@ColorInt int color){
         try {
             Field fCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
             fCursorDrawableRes.setAccessible(true);

             int mCursorDrawableRes = fCursorDrawableRes.getInt(editText);
             Field fEditor = TextView.class.getDeclaredField("mEditor");
             fEditor.setAccessible(true);

             Object editor = fEditor.get(editText);
             Class<?> clazz = editor.getClass();
             Field fCursorDrawable = clazz.getDeclaredField("mCursorDrawable");
             fCursorDrawable.setAccessible(true);
             Drawable[] drawables = new Drawable[2];
             drawables[0] = ContextCompat.getDrawable(editText.getContext(), mCursorDrawableRes);
             drawables[0] = createTintedDrawable(drawables[0], color);
             drawables[1] = ContextCompat.getDrawable(editText.getContext(), mCursorDrawableRes);
             drawables[1] = createTintedDrawable(drawables[1], color);
             fCursorDrawable.set(editor, drawables);

         }catch (Exception e){
             e.printStackTrace();
         }
    }
}
