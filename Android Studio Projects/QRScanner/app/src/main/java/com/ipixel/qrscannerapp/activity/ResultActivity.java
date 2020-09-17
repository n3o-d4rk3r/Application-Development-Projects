package com.ipixel.qrscannerapp.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ipixel.qrscannerapp.R;
import com.ipixel.qrscannerapp.data.preference.AppPreference;
import com.ipixel.qrscannerapp.data.preference.PrefKey;
import com.ipixel.qrscannerapp.utility.AppUtils;

import java.util.ArrayList;

/**
 * Created by iPixel Inc on 17/09/2020.
 *
 * Developed by Aryan Kabir
 *
 */
public class ResultActivity extends AppCompatActivity {

    private Activity mActivity;
    private Context mContext;

    private TextView result;
    private FloatingActionButton copyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVars();
        initViews();
        initFunctionality();
        initListeners();
    }

    private void initVars() {
        mActivity = ResultActivity.this;
        mContext = mActivity.getApplicationContext();
    }

    private void initViews() {
        setContentView(R.layout.activity_result);

        result = (TextView) findViewById(R.id.result);
        copyButton = (FloatingActionButton) findViewById(R.id.copy);
    }

    private void initFunctionality() {

        getSupportActionBar().setTitle(getString(R.string.result));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ArrayList<String> arrayList = AppPreference.getInstance(mContext).getStringArray(PrefKey.RESULT_LIST);
        String lastResult = arrayList.get(arrayList.size()-1);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            result.setText(Html.fromHtml(lastResult, Html.FROM_HTML_MODE_LEGACY));
        } else {
            result.setText(Html.fromHtml(lastResult));
        }
        result.setMovementMethod(LinkMovementMethod.getInstance());

    }

    private void initListeners() {
        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUtils.copyToClipboard(mContext, result.getText().toString());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

