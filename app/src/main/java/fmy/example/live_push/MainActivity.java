package fmy.example.live_push;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    private LivePusher livePusher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.surface);

        livePusher = new LivePusher(surfaceView.getHolder());

    }


    /**
     * 开始直播
     *
     * @param view
     */
    public void mStartLive(View view) {
        Button btn = (Button) view;
        String btnContent = btn.getText().toString();


        if ("开始直播".equals(btnContent)) {
            btn.setText("停止直播");
            livePusher.startPush(null);
        } else {
            btn.setText("开始直播");
            livePusher.stopPush();
        }

    }


    /**
     * 切换摄像头
     *
     * @param view
     */
    public void mSwitchCamera(View view) {
        livePusher.switchCamera();
    }
}
