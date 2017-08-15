package fmy.example.live_push;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

import java.util.Arrays;

import fmy.example.live_push.bean.AudioParam;

/**
 * Created by FMY on 2017/8/8.
 */
public class AudioPusher extends Pusher {

    private AudioParam audioParam;

    private AudioRecord audioRecord;

    private boolean isPushing = false;

    private int minBufferSize;

    public AudioPusher(AudioParam audioParam) {
        this.audioParam = audioParam;

        int channelConfig = audioParam.getChannel() == 1 ?
                AudioFormat.CHANNEL_IN_MONO : AudioFormat.CHANNEL_IN_STEREO;

        // 最小缓存区
        // 返回以字节为单位成功创建AudioRecord对象所需
        minBufferSize = AudioRecord.getMinBufferSize(audioParam.getSampleRateInHz(), channelConfig, AudioFormat.ENCODING_PCM_16BIT);

        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, audioParam.getSampleRateInHz(), channelConfig, AudioFormat.ENCODING_PCM_16BIT, minBufferSize);


    }

    class AudioRecordTask implements Runnable {

        @Override
        public void run() {

            //开始录音
            audioRecord.startRecording();

            while (isPushing) {
                //通过AudioRecord不断读取音频数据
                byte[] buffer = new byte[minBufferSize];
                int len = audioRecord.read(buffer, 0, buffer.length);
//                System.out.println(buffer);
                Log.d("音频",Arrays.toString(buffer));
                if (len > 0) {
                    //传给Native代码，进行音频编码
                }
            }
        }
    }

    @Override
    public void startPush() {
        isPushing = true;
        new Thread(new AudioRecordTask()).start();
    }

    @Override
    public void stopPush() {
        isPushing = false;
        audioRecord.stop();
    }

    @Override
    public void release() {
        if (audioRecord != null) {
            audioRecord.release();
            audioRecord = null;
        }
    }
}
