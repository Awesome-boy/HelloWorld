package com.example.helloworld.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.helloworld.R;

public class CustomerKeyboard extends LinearLayout implements View.OnClickListener {

        public CustomerKeyboard(Context context) {
            this(context, null);
        }

        public CustomerKeyboard(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
        }

        public CustomerKeyboard(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            // 直接加载布局
            inflate(context, R.layout.ui_customer_keyboard, this);
            setItemClickListener(this);
        }

        /**
         * 设置子View的ClickListener
         */
        private void setItemClickListener(View view) {
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                int childCount = viewGroup.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    //不断的递归给里面所有的View设置OnClickListener
                    View childView = viewGroup.getChildAt(i);
                    setItemClickListener(childView);
                }
            } else {
                view.setOnClickListener(this);
            }
        }

        @Override
        public void onClick(View v) {
            if (v instanceof TextView) {
                if ("删除".equals(((TextView) v).getText().toString().trim())) {
                    // 点击的是删除
                    if (mListener != null) {
                        mListener.delete();
                    }
                } else {
                    // 点击的是数字
                    String number = ((TextView) v).getText().toString().trim();
                    if (mListener != null) {
                        mListener.click(number);
                    }
                }
            }
        }

        // 设置点击回掉监听
        private CustomerKeyboardClickListener mListener;

        public void setOnCustomerKeyboardClickListener(CustomerKeyboardClickListener listener) {
            this.mListener = listener;
        }

        /**
         * 点击键盘的回调监听
         */
        public interface CustomerKeyboardClickListener {
            public void click(String number);

            public void delete();
        }

}
