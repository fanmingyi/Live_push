package fmy.example.live_push.jni;

/**
 * 调用C代码进行编码与推流
 * @author Jason
 * QQ: 1476949583
 * @date 2016年11月13日
 * @version 1.0
 */
public class PushNative {
	
	public native void startPush(String url);
	
	public native void stopPush();
	
	public native void release();
	
	/**
	 * 设置视频参数
	 * @param width
	 * @param height
	 * @param bitrate
	 * @param fps
	 */
	public native void setVideoOptions(int width, int height, int bitrate, int fps);
	
	/**
	 * 设置音频参数
	 * @param sampleRateInHz
	 * @param channel
	 */
	public native void setAudioOptions(int sampleRateInHz, int channel);
	

	/**
	 * 发送视频数据
	 * @param data
	 */
	public native void fireVideo(byte[] data);
	
	/**
	 * 发送音频数据
	 * @param data
	 * @param len
	 */
	public native void fireAudio(byte[] data, int len);
	
	static{
		System.loadLibrary("native-lib");
	}
	
}
