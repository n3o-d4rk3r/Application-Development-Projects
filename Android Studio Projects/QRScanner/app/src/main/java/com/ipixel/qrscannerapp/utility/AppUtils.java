package com.ipixel.qrscannerapp.utility;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Vibrator;
import android.widget.Toast;

import com.ipixel.qrscannerapp.R;

import java.io.File;


/**
 * Created by iPixel Inc on 17/09/2020.
 *
 * Developed by Aryan Kabir
 *
 */

public class AppUtils {

    private static long backPressed = 0;

    public static void tapToExit(Activity activity) {
        if (backPressed + 2500 > System.currentTimeMillis()){
            activity.finish();
        }
        else{
            showToast(activity.getApplicationContext(), activity.getResources().getString(R.string.tapAgain));
        }
        backPressed = System.currentTimeMillis();
    }

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }




    public static void shareApp(Activity activity) {
        try {
            final String appPackageName = activity.getPackageName();
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, activity.getResources().getString(R.string.share) + " https://play.google.com/store/apps/details?id=" + appPackageName);
            sendIntent.setType("text/plain");
            activity.startActivity(sendIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void rateThisApp(Activity activity) {
        try {
            activity.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + activity.getPackageName())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void vibrateDevice(Context context) {
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        v.vibrate(500);
    }

    public static void share(Activity activity, String fileUrl) {//, String text) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        //shareIntent.putExtra(Intent.EXTRA_TEXT, text);

        Uri uri = Uri.fromFile(new File(fileUrl));
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        activity.startActivity(Intent.createChooser(shareIntent, "Share via"));
    }

    public static void copyToClipboard(Context context, String text) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("label", text);
        clipboard.setPrimaryClip(clip);
        showToast(context, context.getResources().getString(R.string.copied));
    }


}

