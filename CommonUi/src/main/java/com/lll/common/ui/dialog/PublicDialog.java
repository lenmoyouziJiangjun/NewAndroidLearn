package com.lll.common.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.ArrayRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.tima.common.ui.R;

/**
 * Version 1.0
 * Created by lll on 16/9/27.
 * Description 定义公共dialog
 * copyright generalray4239@gmail.com
 */

public class PublicDialog extends Dialog {


    public PublicDialog(Context context) {
        //采用默认样式
        super(context, R.style.Common_Dialog);
    }

    //支持自定义样式
    public PublicDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected PublicDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    /**
     * 基本属性设置,宽高
     */
    private void initDialog() {
        Window dialogWindow = getWindow();
        //设置屏幕中的位置
        dialogWindow.setGravity(Gravity.LEFT | Gravity.TOP);
        //设置基本属性
        final WindowManager.LayoutParams attrs = dialogWindow.getAttributes();
        attrs.width = (int) (getContext().getResources().getDisplayMetrics().widthPixels * 0.6);
        attrs.height = 100;
        attrs.alpha = 50;//透明度
        dialogWindow.setAttributes(attrs);
    }

    /**
     * 集成list,普通message 的dialog
     */
    private static class DialogParams implements AdapterView.OnItemClickListener, View
            .OnClickListener {
        private CharSequence mTitle;
        private CharSequence mMessage;
        private View mView;
        private Integer mViewResId;
        private CharSequence mPositiveButtonText;
        private OnClickListener mPositiveButtonListener;
        //        private CharSequence mNeutralButtonText;
//        private OnClickListener mNeutralButtonListener;
        private CharSequence mNegativeButtonText;
        private OnClickListener mNegativeButtonListener;
        private boolean mCloseButtonEnable;

        //列表
        private CharSequence[] mItems;
        //单选
        private OnClickListener mOnClickListener;
        private int mCheckedItem;
        private boolean mIsSingleChoice;

        //多选
        private boolean[] mCheckedItems;
        private boolean mIsMultiChoice;
        private OnMultiChoiceClickListener mOnCheckboxClickListener;

        private LayoutInflater mInflater;
        private Context mContext;
        private PublicDialog mDialog;

        private boolean mPreventDismiss;

        public DialogParams(Context context) {
            mContext = context;
            mInflater = LayoutInflater.from(context);
        }

        public void apply(PublicDialog dialog) {
            mDialog = dialog;
            dialog.setContentView(R.layout.public_dialog);

            View dialogContent = dialog.findViewById(R.id.dialogContent);
            boolean hasTitle = !TextUtils.isEmpty(mTitle);
            View titleContainer = dialog.findViewById(R.id.titles);
            titleContainer.setVisibility(mCloseButtonEnable || hasTitle ? View.VISIBLE : View.GONE);

            TextView titleView = (TextView) dialog.findViewById(R.id.title);
            if (!hasTitle) {
                titleView.setVisibility(View.GONE);
            } else {
                titleView.setVisibility(View.VISIBLE);
                titleView.setText(mTitle);
            }

            ImageButton closeButton = (ImageButton) dialog.findViewById(R.id.close);
            closeButton.setOnClickListener(this);
            closeButton.setVisibility(mCloseButtonEnable ? View.VISIBLE : View.GONE);

            ViewGroup content = (ViewGroup) dialog.findViewById(R.id.content);
            ListView list = null;
            if (null != mView || null != mViewResId) {
                applyView(content);
            } else if (null != mItems) {
                list = applyItems(content);
            } else if (null != mMessage) {
                applyMessage(content);
            }

            LinearLayout buttons = (LinearLayout) dialog.findViewById(R.id.buttons);
            Button positive = (Button) buttons.findViewById(R.id.positive);
            positive.setText(mPositiveButtonText);
            positive.setOnClickListener(this);
            positive.setVisibility(TextUtils.isEmpty(mPositiveButtonText) ? View.GONE : View
                    .VISIBLE);

            Button negative = (Button) buttons.findViewById(R.id.negative);
            negative.setText(mNegativeButtonText);
            negative.setOnClickListener(this);
            negative.setVisibility(TextUtils.isEmpty(mNegativeButtonText) ? View.GONE : View
                    .VISIBLE);

            int buttonCount = (View.VISIBLE == negative.getVisibility() ? 1 : 0) + (View.VISIBLE
                    == positive.getVisibility() ? 1 : 0);
            buttons.setVisibility(0 != buttonCount ? View.VISIBLE : View.GONE);
            if (0 == buttonCount) {
                //针对UI设计特殊处理
                int paddingBottom = 0;
//                if (null != list) {暂时注释掉
//                    list.setFirstAndLastSelectorEnabled(true);
//                } else {
                    paddingBottom = dialogContent.getPaddingBottom();
//                }
                dialogContent.setPadding(0, dialogContent.getPaddingTop(), 0, paddingBottom);
            } else if (2 == buttonCount) {
                buttons.setGravity(Gravity.NO_GRAVITY);
            }
        }

        private void applyView(ViewGroup content) {
            View view = mView;
            if (null == view) {
                view = mInflater.inflate(mViewResId, content, false);
            }
            content.addView(view);
        }

        private ListView applyItems(ViewGroup content) {
            ListView list = (ListView) mInflater.inflate(R.layout
                    .dialog_content_items, content, false);
            if (mIsMultiChoice) {//多选
                list.setAdapter(new ArrayAdapter<CharSequence>(mContext, R.layout
                        .dialog_multiple_choice_item, mItems));
                list.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
                if (null != mCheckedItems) {
                    for (int position = 0; position < mCheckedItems.length; position++) {
                        list.setItemChecked(position, mCheckedItems[position]);
                    }
                }
            } else if (mIsSingleChoice) {//单选
                list.setAdapter(new ArrayAdapter<CharSequence>(mContext, R.layout
                        .dialog_single_choice_item, mItems));
                list.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
                list.setItemChecked(mCheckedItem, true);
            } else {//不选
                list.setAdapter(new ArrayAdapter<CharSequence>(mContext, R.layout
                        .dialog_list_item, mItems));
            }
            list.setOnItemClickListener(this);
            content.addView(list);
            return list;
        }

        private void applyMessage(ViewGroup content) {
            TextView message = (TextView) mInflater.inflate(R.layout
                    .dialog_content_message, content, false);
            message.setText(mMessage);
            content.addView(message);
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (mIsMultiChoice) {
                if (null != mOnCheckboxClickListener) {
                    mOnCheckboxClickListener.onClick(mDialog, position, ((ListView) parent)
                            .isItemChecked(position));
                }
            } else {
                if (null != mOnClickListener) {
                    mOnClickListener.onClick(mDialog, position);
                }
            }
        }

        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.positive) {
                if (null != mPositiveButtonListener) {
                    mPositiveButtonListener.onClick(mDialog, DialogInterface.BUTTON_POSITIVE);
                }
            } else if (id == R.id.negative) {
                if (null != mNegativeButtonListener) {
                    mNegativeButtonListener.onClick(mDialog, DialogInterface.BUTTON_NEGATIVE);
                }
            }
            if (null != mDialog && !mPreventDismiss) {
                mDialog.dismiss();
            }
        }
    }

    /**
     * 定义一个构建者
     */
    public static class Builder {
        private DialogParams mParams;

        public Builder(Context context) {
            mParams = new DialogParams(context);
        }

        public Builder setTitle(CharSequence title) {
            mParams.mTitle = title;
            return this;
        }

        public Builder setTitle(@StringRes int titleRes) {
            mParams.mTitle = mParams.mContext.getText(titleRes);
            return this;
        }

        public Builder setMessage(CharSequence message) {
            mParams.mMessage = message;
            return this;
        }

        public Builder setMessage(@StringRes int messageRes) {
            mParams.mMessage = mParams.mContext.getText(messageRes);
            return this;
        }

        public Builder setView(View view) {
            mParams.mView = view;
            mParams.mViewResId = 0;
            return this;
        }

        public Builder setView(@LayoutRes int viewRes) {
            mParams.mView = null;
            mParams.mViewResId = viewRes;
            return this;
        }

        public Builder setPositiveButton(@StringRes int positiveButtonTextRes, OnClickListener
                listener) {
            return setPositiveButton(mParams.mContext.getText(positiveButtonTextRes), listener);
        }

        public Builder setPositiveButton(CharSequence positiveButtonText, OnClickListener
                listener) {
            mParams.mPositiveButtonText = positiveButtonText;
            mParams.mPositiveButtonListener = listener;
            return this;
        }

        public Builder setNegativeButton(@StringRes int negativeButtonTextRes, OnClickListener
                listener) {
            return setNegativeButton(mParams.mContext.getText(negativeButtonTextRes), listener);
        }

        public Builder setNegativeButton(CharSequence negativeButtonText, OnClickListener
                listener) {
            mParams.mNegativeButtonText = negativeButtonText;
            mParams.mNegativeButtonListener = listener;
            return this;
        }

        /**
         * 启用右上角关闭按钮
         *
         * @param enable
         * @return
         */
        public Builder setCloseButtonEnable(boolean enable) {
            mParams.mCloseButtonEnable = enable;
            return this;
        }

        /**
         * 展示列表
         *
         * @param items
         * @param listener
         * @return
         */
        public Builder setItems(CharSequence[] items, final OnClickListener listener) {
            mParams.mItems = items;
            mParams.mOnClickListener = listener;
            return this;
        }

        /**
         * 展示列表
         *
         * @param itemsId
         * @param listener
         * @return
         */
        public Builder setItems(@ArrayRes int itemsId, final OnClickListener listener) {
            return setItems(mParams.mContext.getResources().getTextArray(itemsId), listener);
        }

        /**
         * 展示单选列表
         *
         * @param items
         * @param checkedItem
         * @param listener
         * @return
         */
        public Builder setSingleChoiceItems(CharSequence[] items, int checkedItem,
                                            final OnClickListener listener) {
            mParams.mItems = items;
            mParams.mCheckedItem = checkedItem;
            mParams.mOnClickListener = listener;
            mParams.mIsSingleChoice = true;
            return this;
        }

        /**
         * 展示单选列表
         *
         * @param itemsId
         * @param checkedItem
         * @param listener
         * @return
         */
        public Builder setSingleChoiceItems(@ArrayRes int itemsId, int checkedItem,
                                            final OnClickListener listener) {
            return setSingleChoiceItems(mParams.mContext.getResources().getTextArray(itemsId),
                    checkedItem, listener);
        }

        /**
         * 展示多选框列表
         *
         * @param items
         * @param checkedItems
         * @param listener
         * @return
         */
        public Builder setMultiChoiceItems(CharSequence[] items, boolean[] checkedItems,
                                           final OnMultiChoiceClickListener listener) {
            mParams.mItems = items;
            mParams.mCheckedItems = checkedItems;
            mParams.mOnCheckboxClickListener = listener;
            mParams.mIsMultiChoice = true;
            return this;
        }

        /**
         * 展示多选框列表
         *
         * @param itemsId
         * @param checkedItems
         * @param listener
         * @return
         */
        public Builder setMultiChoiceItems(@ArrayRes int itemsId, boolean[] checkedItems,
                                           final OnMultiChoiceClickListener listener) {
            return setMultiChoiceItems(mParams.mContext.getResources().getTextArray(itemsId),
                    checkedItems,
                    listener);
        }

        /**
         * 用于阻止点击按钮默认关闭对话框
         *
         * @param preventDismiss
         * @return
         */
        public Builder setPreventDismiss(boolean preventDismiss) {
            mParams.mPreventDismiss = preventDismiss;
            return this;
        }

        /**
         * 创建对话框
         *
         * @return
         */
        public PublicDialog create() {
            PublicDialog dialog = new PublicDialog(mParams.mContext, R.style
                    .Common_Dialog);
            mParams.apply(dialog);
            return dialog;
        }
    }
}
