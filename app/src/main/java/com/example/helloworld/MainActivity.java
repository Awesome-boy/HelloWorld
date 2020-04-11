package com.example.helloworld;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.helloworld.db.ContactsDao;
import com.example.helloworld.fragment.PayPWDialogFragment;
import com.example.helloworld.retrofit.MyObserver;
import com.example.helloworld.retrofit.RetrofitUtils;
import com.example.helloworld.retrofit.RxHelper;
import com.example.helloworld.vedio.VedioPlayActivity;
import com.example.helloworld.widget.LiveDataBus;
import com.example.helloworld.widget.TimeDownView;
import com.jeremyliao.liveeventbus.LiveEventBus;

import java.util.Observable;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tv_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayPWDialogFragment.newInstance()
                        .setOnCompleteListener(new PayPWDialogFragment.OnCompleteListener() {
                            @Override
                            public void onComplete(String content) {
                                Toast.makeText(MainActivity.this,"full",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show(getSupportFragmentManager(), "payPWDialogFragment");
            }
        });
        findViewById(R.id.btn_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showUpdateDialog();
//                startActivity(new Intent(MainActivity.this, VedioPlayActivity.class));

            }
        });
        LiveEventBus
                .get("key_name", String.class)
                .observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        Log.d("zt1---","接受信息"+s);
                    }
                });
        RetrofitUtils.getApiUrl().getData("s")
                .compose(RxHelper.observableIO2Main(this))
                .subscribe(new MyObserver<String>(this) {
                    @Override
                    public void onSuccess(String result) {

                    }

                    @Override
                    public void onFailure(Throwable e, String errorMsg) {

                    }
                });

    }



    private void showUpdateDialog() {
//
//        final String[]items={"同时转发到QQ","同时转发到微信","同时转发到微博"};
//        AlertDialog.Builder builder=new AlertDialog.Builder(this);
//        builder.setSingleChoiceItems(items, 1, new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(getApplicationContext(), items[which], Toast.LENGTH_SHORT).show();
//                dialog.dismiss();
//            }
//        });
//        AlertDialog dialog=	builder.create();
//        dialog.show();
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("升级测试");
//        builder.setMessage("发现了新版本，请先升级");
        final String[] items=new String[]{"版本1","版本2","版本3","版本4"};
        builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), items[which], Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
//        builder.setPositiveButton("立刻升级", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                switch (items[0]){
//                    case "版本1":
//
//                        break;
//                    case "版本2":
//
//                        break;
//                    case "版本4":
//
//                        break;
//                    case "版本3":
//
//                        break;
//                }
//
//            }
//        });
//        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
//        builder.create();
//        builder.show();


    }


}