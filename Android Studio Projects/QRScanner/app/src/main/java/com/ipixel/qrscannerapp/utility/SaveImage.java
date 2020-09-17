package com.ipixel.qrscannerapp.utility;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;

import com.ipixel.qrscannerapp.data.constant.Constants;

import java.io.File;
import java.io.FileOutputStream;


/**
 * Created by iPixel Inc on 17/09/2020.
 *
 * Developed by Aryan Kabir
 *
 */
public class SaveImage extends AsyncTask<Void, Void, String> {

    private String name;
    private Bitmap bitmap;

    private SaveListener saveListener;

    public SaveImage(String name, Bitmap bitmap) {
        this.name = name;
        this.bitmap = bitmap;
    }

    public void setSaveListener(SaveListener saveListener) {
        this.saveListener = saveListener;
    }

    @Override
    protected String doInBackground(Void... voids) {
        return saveImageFile(bitmap, name);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(saveListener != null) {
            saveListener.onSaved(s);
        }
    }

    public interface SaveListener {
        public void onSaved(String savedTo);
    }

    private String saveImageFile(Bitmap bitmap, String fileName) {
        FileOutputStream out = null;
        String filePath = getFilename(fileName);
        try {
            out = new FileOutputStream(filePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePath;
    }

    private String getFilename(String fileName) {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), Constants.SAVE_TO);
        if (!file.exists()) {
            file.mkdirs();
        }
        if(fileName.contains("/")) {
            fileName = fileName.replace("/", "\\");
        }
        return (file.getAbsolutePath() + "/" + fileName + ".png");
    }

}

