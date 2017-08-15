package fmy.example.live_push;


import android.hardware.Camera;
import android.view.SurfaceHolder;

import fmy.example.live_push.bean.AudioParam;
import fmy.example.live_push.bean.VideoParam;
import fmy.example.live_push.jni.PushNative;


/**
 * Created by FMY on 2017/8/6.
 */

public class LivePusher implements SurfaceHolder.Callback {

    private SurfaceHolder surfaceHolder;
    private VideoPusher videoPusher;
    private VideoParam videoParam;
    private AudioPusher audioPusher;
    private PushNative pushNative;

    public LivePusher(SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
        surfaceHolder.addCallback(this);
        prepare();

    }


    private void prepare() {
        pushNative = new PushNative();

        videoParam = new VideoParam(720, 1280, android.hardware.Camera.CameraInfo.CAMERA_FACING_BACK);

        videoPusher = new VideoPusher(videoParam, surfaceHolder, pushNative);

        AudioParam audioParam = new AudioParam();
        audioPusher = new AudioPusher(audioParam);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stopPush();
        release();

    }

    public void stopPush() {
        videoPusher.stopPreView();
        audioPusher.stopPush();
    }

    /**
     * 释放资源
     */
    private void release() {
        videoPusher.release();
        audioPusher.release();
//        pushNative.release();
    }

    /**
     * 开始推流
     */
    public void startPush(String url) {
        videoPusher.startPush();
        audioPusher.startPush();

//        pushNative.startPush(null);

    }

    /**
     * 切换摄像头
     */
    public void switchCamera() {

        if (videoParam == null) {
            return;
        }
        if (videoParam.getCamerId() == Camera.CameraInfo.CAMERA_FACING_BACK) {
            videoParam.setCamerId(Camera.CameraInfo.CAMERA_FACING_FRONT);
        } else {
            videoParam.setCamerId(Camera.CameraInfo.CAMERA_FACING_BACK);
        }
        videoPusher.release();

        videoPusher.startPreview();
    }
}
