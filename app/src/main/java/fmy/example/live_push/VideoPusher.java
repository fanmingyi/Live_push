package fmy.example.live_push;

import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;

import java.io.IOException;
import java.util.Arrays;

import fmy.example.live_push.bean.VideoParam;
import fmy.example.live_push.jni.PushNative;

/**
 * Created by FMY on 2017/8/6.
 */

public class VideoPusher extends Pusher implements SurfaceHolder.Callback, Camera.PreviewCallback {
    private VideoParam videoParams;
    private SurfaceHolder surfaceHolder;
    private Camera mCamera;
    private byte[] buffers;
    //是否在直播的标识符
    private boolean isPushing = false;

    public VideoPusher(VideoParam videoParams, SurfaceHolder surfaceHolder, PushNative pushNative) {

        this.pushNative = pushNative;
        this.videoParams = videoParams;
        this.surfaceHolder = surfaceHolder;
        surfaceHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        startPreview();
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "surfaceDestroyed() called with: holder = [" + holder + "]");
        stopPreView();

    }

    private static final String TAG = "VideoPusher";

    /**
     * 开始预览
     */
    public void startPreview() {

        mCamera = Camera.open(videoParams.getCamerId());
        try {
            mCamera.setPreviewDisplay(surfaceHolder);
            mCamera.setDisplayOrientation(90);
            mCamera.startPreview();

            //视频缓存队列数据用于保存每一帧 视频数据
            buffers = new byte[videoParams.getWidth() * videoParams.getHeight() * 10];
            mCamera.addCallbackBuffer(buffers);
            //添加预览数据回调
            /**
             * 使用“addCallbackBuffer”方法提供的缓存区，每一个预览帧设置一个回调方法，并且添加到屏幕显示
             * 当预览是活动状态并且缓存区是有效的这个回调将重复调用
             * 其他预览回调缓存将会覆盖
             *
             * 这个方法的目的通过允许预览帧内存重复使用提高预览效率和帧率
             * 你必须使用‘addCallbackBuffer’方法后使用此方法，否则将接受不到回调
             *
             * 如果这个方法传入空，那么缓存队列将会被清除，setPreviewCallback将会被调用，或者setOneShotPreviewCallback
             * 如果你使用预览数据区创建视频或者静止图片。强烈建议使用‘android.media.MediaActionSound’将非常适合捕获的图片或者录制和停止视频给用户展示
             *
             * @param cb 缓存队列回调接口
             * @see #addCallbackBuffer(byte[])
             * @see android.media.MediaActionSound
             */
            mCamera.setPreviewCallbackWithBuffer(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 停止预览
     */
    public void stopPreView() {
        isPushing = false;
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
//        String s =
        Log.d("FMY", Arrays.toString(data));
        System.out.println(Arrays.toString(data));
        //数据缓存队列需要重复添加
        //这里我原来不是很理解 ，后来想想这么做是正确的，如果下一帧数据回调的时候你上一次数据不就被覆盖了吗
        if (mCamera != null) {
            mCamera.addCallbackBuffer(buffers);
        }
    }

    private PushNative pushNative;

    @Override
    public void startPush() {

        isPushing = true;

        pushNative.setVideoOptions(videoParams.getWidth(), videoParams.getHeight(), videoParams.getBitrate(), videoParams.getFps());


    }

    @Override
    public void stopPush() {

    }

    @Override
    public void release() {

        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }
}
