package com.example.helloworld.vedio;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.helloworld.R;
import com.example.helloworld.widget.LiveDataBus;
import com.jeremyliao.liveeventbus.LiveEventBus;

import java.io.File;

public class VedioPlayActivity extends AppCompatActivity implements View.OnClickListener {
    private VideoView videoView;

    private Button play;
    private Button pause;
    private Button replay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedio_play);
        play = findViewById(R.id.play);

        pause = findViewById(R.id.pause);

        replay = findViewById(R.id.replay);

        videoView = findViewById(R.id.video_view);
        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        replay.setOnClickListener(this);

        initVideoPath();

    }


    private void initVideoPath() {

        File file = new File(Environment.getExternalStorageDirectory(), "movie.3gp");

        videoView.setVideoPath(file.getPath()); // 指定视频文件的路径

    }


    @Override

    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.play:

                if (!videoView.isPlaying()) {

                    videoView.start(); // 开始播放

                    LiveEventBus
                            .get("key_name")
                            .postAcrossProcess("value");
                }

                break;

            case R.id.pause:

                if (videoView.isPlaying()) {

                    videoView.pause(); // 暂时播放

                }

                break;

            case R.id.replay:

                if (videoView.isPlaying()) {

                    videoView.resume(); // 重新播放

                }

                break;

        }

    }


    @Override

    protected void onDestroy() {

        super.onDestroy();

        if (videoView != null) {

            videoView.suspend();

        }


    }


}
