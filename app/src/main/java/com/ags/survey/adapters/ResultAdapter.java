package com.ags.survey.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ags.survey.R;
import com.ags.survey.pojo.ResultOptionRawDta;
import com.ags.survey.pojo.ResultRawData;

import java.util.ArrayList;

/**
 * Created by CheemalaCh on 5/6/2018.
 */

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.MyViewHolder>{

    private Context context;
    private ArrayList<ResultRawData> resultData;
    private LayoutInflater li;

    public ResultAdapter(Context context,ArrayList<ResultRawData> resultData){
        this.context = context;
        this.resultData = resultData;
        li = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View resultItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.survey_rslt_layut,parent,false);
        return new ResultAdapter.MyViewHolder(resultItem);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.getRsltQnTxt().setText(resultData.get(position).getQuestionTxt());
        ArrayList<ResultOptionRawDta> optionDta = resultData.get(position).getOptionDta();
        if (optionDta.size() > 0){
            for (ResultOptionRawDta optionRawDta: optionDta){
                View optionView = li.inflate(R.layout.srvy_rslt_option_lyot,null);
                TextView optnTxt = optionView.findViewById(R.id.rslt_optn_txt);
                TextView optnPrcntTxt = optionView.findViewById(R.id.rslt_prcnt_txt);
                optnTxt.setText(optionRawDta.getOptionTxt());
                optnPrcntTxt.setText(optionRawDta.getOptionPercentage());
                holder.getOptionLayout().addView(optionView);
            }
        }else {
            Toast.makeText(context,"No options!",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return resultData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView rsltQnTxt;

        public TextView getRsltQnTxt() {
            return rsltQnTxt;
        }

        public void setRsltQnTxt(TextView rsltQnTxt) {
            this.rsltQnTxt = rsltQnTxt;
        }

        public LinearLayout getOptionLayout() {
            return optionLayout;
        }

        public void setOptionLayout(LinearLayout optionLayout) {
            this.optionLayout = optionLayout;
        }

        private LinearLayout optionLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            rsltQnTxt = itemView.findViewById(R.id.rslt_qn_txt);
            optionLayout = itemView.findViewById(R.id.rslt_optn_layt);
        }
    }
}
