package com.ags.survey.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ags.survey.R;
import com.ags.survey.utils.SurveyCallbacks;
import com.ags.survey.pojo.SurveyData;
import java.util.ArrayList;

/**
 * Created by CheemalaCh on 3/18/2018.
 */

public class SurveyAdapter extends RecyclerView.Adapter<SurveyAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<SurveyData> surveyData;
    private SurveyCallbacks surveyCallbacks;

    public SurveyAdapter(Context context, ArrayList<SurveyData> surveyData, SurveyCallbacks surveyCallbacks) {

        this.context = context;
        this.surveyData = surveyData;
        this.surveyCallbacks = surveyCallbacks;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_surveyqn_item,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.questionTxt.setText(surveyData.get(position).getSurveyQuestion());
        String[] optionsArray = surveyData.get(position).getSurveyOptions().split(";");
        Log.d("option: ",""+optionsArray.toString());
        for (int i = 0; i<optionsArray.length; i++){
            View optionView = LayoutInflater.from(context).inflate(R.layout.option_radio_btn_layout,null,false);
            RadioButton optionBtn = optionView.findViewById(R.id.option_radio_btn);
            optionBtn.setId(i+1);
            optionBtn.setText(optionsArray[i]);
            optionBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    surveyCallbacks.saveAnswer(surveyData.get(position).getSurveyId(),String.valueOf((position+1)),String.valueOf((v.getId())));
                }
            });
            holder.optionLayout.addView(optionView);
            Log.d("option: ",""+optionsArray[i]);
        }
        Log.d("tst: ","qn: "+surveyData.get(position).getSurveyQuestion());
    }

    @Override
    public int getItemCount() {
        return surveyData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView questionTxt;
        public LinearLayout optionLayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            questionTxt = itemView.findViewById(R.id.question_txt);
            optionLayout = itemView.findViewById(R.id.add_option_layout);
        }

    }
}
