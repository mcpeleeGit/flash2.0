package kr.pe.dongha.flash2;
import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.hardware.camera2.CameraAccessException;
import android.content.pm.PackageManager;
import android.view.WindowManager;


public class MainActivity extends AppCompatActivity {
    private CameraManager mCameraManager;
    private String mCameraId;

    private WindowManager.LayoutParams params;
    private float brightness; // 밝기값은 float형으로 저장되어 있습니다.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean isFlashAvailable = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if (isFlashAvailable) {
            flashLight(true);
        }

        // 화면 정보 불러오기
        params = getWindow().getAttributes();

    }

    @Override
    protected void onResume() {
        super.onResume();

        // 기존 밝기 저장
        brightness = params.screenBrightness;
        // 최대 밝기로 설정
        params.screenBrightness = 1f;
        // 밝기 설정 적용
        getWindow().setAttributes(params);
    }

    @Override
    public void onBackPressed() {
        flashLight(false);
        System.exit(0);

        // 기존 밝기로 변경
        params.screenBrightness = brightness;
        getWindow().setAttributes(params);

    }

    @Override
    protected void onUserLeaveHint() {
        onBackPressed();
    }

    private void flashLight(boolean b) {
        //getting the camera manager and camera id
        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            mCameraId = mCameraManager.getCameraIdList()[0];
            mCameraManager.setTorchMode(mCameraId, b);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

}
