package fmy.example.live_push.bean;

/**
 * Created by FMY on 2017/8/6.
 */

public class VideoParam {

    private int width;
    private int height;
    private int camerId;
    // 码率480kbps
    private int bitrate = 480000;

    public int getBitrate() {
        return bitrate;
    }
    // 帧频默认25帧/s
    private int fps = 25;

    public int getFps() {
        return fps;
    }

    public void setFps(int fps) {
        this.fps = fps;
    }

    public void setBitrate(int bitrate) {
        this.bitrate = bitrate;
    }

    public VideoParam(int width, int height, int camerId) {
        this.width = width;
        this.height = height;
        this.camerId = camerId;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getCamerId() {
        return camerId;
    }

    public void setCamerId(int camerId) {
        this.camerId = camerId;
    }
}
