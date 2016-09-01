package in.intrface.chexit;

import android.content.Context;

import android.content.Intent;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * Created by Shashank Duhan on 23/08/16.
 */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder mHolder;
    private Camera mCamera;
    private String TAG = "CameraPreview";

    public CameraPreview(Context context, Camera camera) {
        super(context);

        mCamera = camera;

        mHolder = getHolder();
        mHolder.addCallback(this);
        //deprecated settings
        //mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        try{
            mCamera.setPreviewDisplay(surfaceHolder);
            mCamera.startPreview();
        }catch (IOException e){
            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int width, int height) {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.

        if (mHolder.getSurface() == null){
            // preview surface does not exist
            return;
        }

        // stop preview before making changes
        try {
            mCamera.stopPreview();
        } catch (Exception e){
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here

        // start preview with new settings
        try {
            mCamera.setPreviewDisplay(mHolder);

            Camera.Parameters params = mCamera.getParameters();
            Camera.Size size = getBestPreviewSize(width, height, params);
            if(size != null){
                params.setPreviewSize(size.width, size.height);
                mCamera.setParameters(params);
                mCamera.startPreview();
            }



        } catch (Exception e){
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        //-----
        mCamera.stopPreview();
        mCamera.release();
        mCamera = null;
    }

    private Camera.Size getBestPreviewSize(int width, int height, Camera.Parameters params){
        Camera.Size result = null;

        for(Camera.Size size : params.getSupportedPreviewSizes()){
            if(size.width <= width && size.height <= height){
                if(result == null){
                    result = size;
                }else{
                    int resultArea = result.width*result.height;
                    int newArea = size.width*size.height;
                    if(newArea > resultArea){
                        result = size;
                    }
                }
            }
        }

        return result;
    }
}