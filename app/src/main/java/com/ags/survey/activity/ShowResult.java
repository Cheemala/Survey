package com.ags.survey.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.ags.survey.R;
import com.ags.survey.adapters.ResultAdapter;
import com.ags.survey.pojo.ResultOptionRawDta;
import com.ags.survey.pojo.ResultRawData;
import com.ags.survey.utils.EqualSpacingItemDecoration;

import java.util.ArrayList;

public class ShowResult extends AppCompatActivity {

    private ArrayList<ResultRawData> resultData;
    private ArrayList<ResultOptionRawDta> optionRawDta;
    private RecyclerView resultDataRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_result);
        resultData = getIntent().getParcelableArrayListExtra("RESULT_DATA");
    }

    @Override
    protected void onResume() {
        super.onResume();
        resultDataRecyclerView = (RecyclerView) findViewById(R.id.result_data_recycler_view);
        if (resultData.size() > 0){
            Toast.makeText(ShowResult.this,"Result size: "+resultData.size(),Toast.LENGTH_SHORT).show();
            setUpResultData();
        }else {
            Toast.makeText(ShowResult.this,"No Data Found!",Toast.LENGTH_SHORT).show();
        }
    }

    private void setUpResultData() {
        ResultAdapter rsltAdpter = new ResultAdapter(ShowResult.this,resultData);
        resultDataRecyclerView.setHasFixedSize(true);
        resultDataRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        resultDataRecyclerView.setItemAnimator(new DefaultItemAnimator());
        resultDataRecyclerView.addItemDecoration(new EqualSpacingItemDecoration(20, EqualSpacingItemDecoration.VERTICAL));
        resultDataRecyclerView.setAdapter(rsltAdpter);
    }

}
