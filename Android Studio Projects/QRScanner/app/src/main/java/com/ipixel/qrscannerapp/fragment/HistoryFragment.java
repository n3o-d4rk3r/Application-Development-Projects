package com.ipixel.qrscannerapp.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ipixel.qrscannerapp.R;
import com.ipixel.qrscannerapp.adapter.HistoryAdapter;
import com.ipixel.qrscannerapp.data.preference.AppPreference;
import com.ipixel.qrscannerapp.data.preference.PrefKey;
import com.ipixel.qrscannerapp.utility.AppUtils;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by iPixel Inc on 17/09/2020.
 *
 * Developed by Aryan Kabir
 *
 */
public class HistoryFragment extends Fragment {

    private Activity mActivity;
    private Context mContext;

    private TextView noResultView;
    private RecyclerView mRecyclerView;
    private ArrayList<String> arrayList;
    private HistoryAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);

        initView(rootView);
        initFunctionality();
        initListener();

        return rootView;
    }

    private void initVar() {
        mActivity = getActivity();
        mContext = mActivity.getApplicationContext();
    }

    private void initView(View rootView) {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        noResultView = (TextView) rootView.findViewById(R.id.noResultView);
    }

    private void initFunctionality() {
        arrayList = new ArrayList<>();
        adapter = new HistoryAdapter(mContext, arrayList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(adapter);

        arrayList.addAll(AppPreference.getInstance(mContext).getStringArray(PrefKey.RESULT_LIST));
        if(arrayList.isEmpty()) {
            noResultView.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        } else {
            noResultView.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
        Collections.reverse(arrayList);
        adapter.notifyDataSetChanged();
    }

    private void initListener() {
        adapter.setClickListener(new HistoryAdapter.ClickListener() {
            @Override
            public void onItemClicked(int position) {
                AppUtils.copyToClipboard(mContext, arrayList.get(position));
            }
        });
    }





}
