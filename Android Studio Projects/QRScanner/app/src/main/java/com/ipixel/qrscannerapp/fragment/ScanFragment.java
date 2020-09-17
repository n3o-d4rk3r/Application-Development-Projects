package com.ipixel.qrscannerapp.fragment;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.zxing.BarcodeFormat;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.Result;
import com.ipixel.qrscannerapp.R;
import com.ipixel.qrscannerapp.activity.ResultActivity;
import com.ipixel.qrscannerapp.data.preference.AppPreference;
import com.ipixel.qrscannerapp.data.preference.PrefKey;
import com.ipixel.qrscannerapp.utility.ActivityUtils;
import com.ipixel.qrscannerapp.utility.AppUtils;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by iPixel Inc on 17/09/2020.
 *
 * Developed by Aryan Kabir
 *
 */
public class ScanFragment extends Fragment {

    private Activity mActivity;
    private Context mContext;

    private ViewGroup contentFrame;
    private ZXingScannerView zXingScannerView;
    private ArrayList<Integer> mSelectedIndices;

    private FloatingActionButton flash, focus, camera;
    private boolean isFlash, isAutoFocus;
    private int camId, frontCamId, rearCamId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVar();
        zXingScannerView = new ZXingScannerView(mActivity);
        setupFormats();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_scan, container, false);


        initView(rootView);
        initListener();

        return rootView;
    }

    private void initVar() {
        mActivity = getActivity();
        mContext = mActivity.getApplicationContext();

        isFlash = AppPreference.getInstance(mContext).getBoolean(PrefKey.FLASH, false); // flash off by default
        isAutoFocus = AppPreference.getInstance(mContext).getBoolean(PrefKey.FOCUS, true); // auto focus on by default
        camId = AppPreference.getInstance(mContext).getInteger(PrefKey.CAM_ID); // back camera by default
        if(camId == -1) {
            camId = rearCamId;
        }

        loadCams();
    }

    private void initView(View rootView) {
        contentFrame = (ViewGroup) rootView.findViewById(R.id.content_frame);

        flash = (FloatingActionButton) rootView.findViewById(R.id.flash);
        focus = (FloatingActionButton) rootView.findViewById(R.id.focus);
        camera = (FloatingActionButton) rootView.findViewById(R.id.camera);
        initConfigs();

    }


    private void initListener() {

        flash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleFlash();
            }
        });

        focus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleFocus();
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleCamera();
            }
        });

        zXingScannerView.setResultHandler(new ZXingScannerView.ResultHandler() {
            @Override
            public void handleResult(Result result) {

                String resultStr = result.getText();
                ArrayList<String> previousResult = AppPreference.getInstance(mContext).getStringArray(PrefKey.RESULT_LIST);
                previousResult.add(resultStr);
                AppPreference.getInstance(mContext).setStringArray(PrefKey.RESULT_LIST, previousResult);

                zXingScannerView.resumeCameraPreview(this);

                ActivityUtils.getInstance().invokeActivity(mActivity, ResultActivity.class, false);

            }
        });

    }

    private void activateScanner() {
        if(zXingScannerView != null) {

            if(zXingScannerView.getParent()!=null) {
                ((ViewGroup) zXingScannerView.getParent()).removeView(zXingScannerView); // to prevent crush on re adding view
            }
            contentFrame.addView(zXingScannerView);

            if(zXingScannerView.isActivated()) {
                zXingScannerView.stopCamera();
            }

            zXingScannerView.startCamera(camId);
            zXingScannerView.setFlash(isFlash);
            zXingScannerView.setAutoFocus(isAutoFocus);
        }
    }


    public void setupFormats() {
        List<BarcodeFormat> formats = new ArrayList<>();
        if(mSelectedIndices == null || mSelectedIndices.isEmpty()) {
            mSelectedIndices = new ArrayList<>();
            for(int i = 0; i < ZXingScannerView.ALL_FORMATS.size(); i++) {
                mSelectedIndices.add(i);
            }
        }

        for(int index : mSelectedIndices) {
            formats.add(ZXingScannerView.ALL_FORMATS.get(index));
        }
        if(zXingScannerView != null) {
            zXingScannerView.setFormats(formats);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        activateScanner();
    }

    @Override
    public void onPause() {
        super.onPause();
        if(zXingScannerView != null) {
            zXingScannerView.stopCamera();
        }
    }

    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        if(zXingScannerView != null) {
            if (visible) {
                zXingScannerView.setFlash(isFlash);
            } else {
                zXingScannerView.setFlash(false);
            }
        }
    }


    private void toggleFlash() {
        if (isFlash) {
            isFlash = false;
            flash.setImageResource(R.drawable.ic_flash_on);
        } else {
            isFlash = true;
            flash.setImageResource(R.drawable.ic_flash_off);
        }
        AppPreference.getInstance(mContext).setBoolean(PrefKey.FLASH, isFlash);
        zXingScannerView.setFlash(isFlash);
    }

    private void toggleFocus() {
        if (isAutoFocus) {
            isAutoFocus = false;
            focus.setImageResource(R.drawable.ic_focus_on);
            AppUtils.showToast(mContext, getString(R.string.autofocus_off));
        } else {
            isAutoFocus = true;
            focus.setImageResource(R.drawable.ic_focus_off);
            AppUtils.showToast(mContext, getString(R.string.autofocus_on));
        }
        AppPreference.getInstance(mContext).setBoolean(PrefKey.FOCUS, isAutoFocus);
        zXingScannerView.setFocusable(isAutoFocus);
    }

    private void toggleCamera() {

        if (camId == rearCamId) {
            camId = frontCamId;
        } else {
            camId = rearCamId;
        }
        AppPreference.getInstance(mContext).setInteger(PrefKey.CAM_ID, camId);
        zXingScannerView.stopCamera();
        zXingScannerView.startCamera(camId);
    }

    private void initConfigs() {
        if (isFlash) {
            flash.setImageResource(R.drawable.ic_flash_off);
        } else {
            flash.setImageResource(R.drawable.ic_flash_on);
        }
        if (isAutoFocus) {
            focus.setImageResource(R.drawable.ic_focus_off);
        } else {
            focus.setImageResource(R.drawable.ic_focus_on);
        }
    }

    private void loadCams() {
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                frontCamId = i;
            } else if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                rearCamId = i;
            }
        }
        AppPreference.getInstance(mContext).setInteger(PrefKey.CAM_ID, rearCamId);

    }


}
