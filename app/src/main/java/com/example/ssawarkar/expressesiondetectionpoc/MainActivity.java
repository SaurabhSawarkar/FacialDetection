package com.example.ssawarkar.expressesiondetectionpoc;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.widget.TextView;

import com.affectiva.android.affdex.sdk.Frame;
import com.affectiva.android.affdex.sdk.detector.CameraDetector;
import com.affectiva.android.affdex.sdk.detector.Detector;
import com.affectiva.android.affdex.sdk.detector.Face;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private Detector mDetector;
    private SurfaceView mSurfaceView;
    private TextView mExpressionTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        mContext = this;
        mSurfaceView = (SurfaceView) findViewById(R.id.camera_surface_view);
        mDetector = new CameraDetector(mContext, CameraDetector.CameraType.CAMERA_FRONT, mSurfaceView, 10, Detector.FaceDetectorMode.LARGE_FACES);
        mDetector.setDetectAllExpressions(true);
        mDetector.setImageListener(mImageListener);
        mExpressionTv = (TextView) findViewById(R.id.tv_expression);
        mDetector.start();
    }

    private Detector.ImageListener mImageListener = new Detector.ImageListener() {
        @Override
        public void onImageResults(List<Face> list, Frame frame, float v) {
            if (list == null || list.size() == 0) {
                mExpressionTv.setText("No face detected");
            } else {
                Face face = list.get(0);
                float smile = face.expressions.getSmile();
                float browRaised = face.expressions.getBrowRaise();
                float eyeCloser = face.expressions.getEyeClosure();
                float jawDrop = face.expressions.getJawDrop();

                StringBuffer builder = new StringBuffer();
                builder.append("Face expressions: " + "/n"
                        + String.format("Smile: %2f", smile) + "\n"
                        + String.format("BrowRaised: %2f", smile) + "\n"
                        + String.format("EyeCloser: %2f", eyeCloser) + "\n"
                        + String.format("JawDropped: %2f", jawDrop)
                );

                mExpressionTv.setText(builder.toString());
            }
        }
    };
}
