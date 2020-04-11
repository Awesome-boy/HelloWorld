package com.example.helloworld.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.fragment.app.DialogFragment;

import com.example.helloworld.R;
import com.example.helloworld.widget.CustomerKeyboard;
import com.example.helloworld.widget.PasswordEditText;


/**
 * 支付密码对话框
 */
public class PayPWDialogFragment extends DialogFragment implements CustomerKeyboard.CustomerKeyboardClickListener, PasswordEditText.PasswordFullListener {

    private PasswordEditText mPasswordEt;


    private OnCompleteListener mOnCompleteListener;
    private OnCancelListener mOnCancelListener;

    private static PayPWDialogFragment payPWDialogFragment;


    //写一个静态方法产生实例
    public static PayPWDialogFragment newInstance() {
        if (payPWDialogFragment == null) {
            payPWDialogFragment = new PayPWDialogFragment();
        }
        return payPWDialogFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pay_password_dialog_layout, container, false);
        mPasswordEt = (PasswordEditText) view.findViewById(R.id.password_et);
        CustomerKeyboard mCustomerKeyboard = view.findViewById(R.id.custom_key_board);
        mCustomerKeyboard.setOnCustomerKeyboardClickListener(this);
        mPasswordEt.setEnabled(false);
        mPasswordEt.setOnPasswordFullListener(this);

        return view;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) { //此处可以设置Dialog的style等等
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.CustomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.pay_password_dialog_layout);
        dialog.setCanceledOnTouchOutside(false);

        /**
         * 设置宽度全屏，要设置在show的后面
         */
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        dialog.getWindow().getDecorView().setPadding(0, 0, 0, 0);

        dialog.getWindow().setAttributes(layoutParams);
        return dialog;
    }


    @Override
    public void click(String number) {
        mPasswordEt.addPassword(number);
    }

    @Override
    public void delete() {
        mPasswordEt.deleteLastPassword();
    }

    @Override
    public void passwordFull(String password) {
        if (mOnCompleteListener != null)
            mOnCompleteListener.onComplete(password);
        dismiss();
        mPasswordEt.clear();
    }

    public interface OnCompleteListener {
        void onComplete(String content);
    }

    public interface OnCancelListener {
        void onCancel();
    }

    public PayPWDialogFragment setOnCompleteListener(OnCompleteListener listener) {
        this.mOnCompleteListener = listener;
        return this;
    }

    public PayPWDialogFragment setOnCancelListener(OnCancelListener listener) {
        this.mOnCancelListener = listener;
        return this;
    }
}