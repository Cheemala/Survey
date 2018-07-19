package com.ags.survey.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ags.survey.R;
import com.ags.survey.pojo.AdminSurveyData;
import com.ags.survey.utils.SurveyResultCallbacks;

import java.util.ArrayList;

/**
 * Created by CheemalaCh on 3/25/2018.
 */

public class AdminSurveyAdapter extends RecyclerView.Adapter<AdminSurveyAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<AdminSurveyData> surveyData;
    private SurveyResultCallbacks srvyRsltCallbacks;

    public AdminSurveyAdapter(Context context, ArrayList<AdminSurveyData> surveyData, SurveyResultCallbacks srvyRsltCallbacks) {

        this.context = context;
        this.surveyData = surveyData;
        this.srvyRsltCallbacks = srvyRsltCallbacks;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View surveyItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_survey_item,parent,false);
        return new MyViewHolder(surveyItem);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.getAdminSurveyId().setText(surveyData.get(position).getSurveyId());
        holder.getAdminSurveyTitle().setText(surveyData.get(position).getSurveyTitle());
        holder.setSurveyRsltStsBtn(surveyData.get(position).getSurveyStatus());

        holder.getSurveyRsltStsBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (surveyData.get(position).getSurveyStatus().contentEquals("Pending")){
                    srvyRsltCallbacks.changeSurveySts(surveyData.get(position).getSurveyId());
                }else {
                    Toast.makeText(context,"Survey already released!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return surveyData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView adminSurveyTitle,adminSurveyId;
        public Button surveyRsltStsBtn;

        public MyViewHolder(View itemView) {
            super(itemView);
            adminSurveyTitle = itemView.findViewById(R.id.admin_survey_title);
            adminSurveyId = itemView.findViewById(R.id.admin_survey_id);
            surveyRsltStsBtn = itemView.findViewById(R.id.survey_rslt_sts_btn);
        }

        public TextView getAdminSurveyId() {
            return adminSurveyId;
        }

        public void setAdminSurveyId(TextView adminSurveyId) {
            this.adminSurveyId = adminSurveyId;
        }

        public TextView getAdminSurveyTitle() {
            return adminSurveyTitle;
        }

        public void setAdminSurveyTitle(TextView adminSurveyTitle) {
            this.adminSurveyTitle = adminSurveyTitle;
        }

        public String getButtonText(){
            return surveyRsltStsBtn.getText().toString().trim();
        }

        public void setSurveyRsltStsBtn(String btnTextVal) {
            this.surveyRsltStsBtn.setText(btnTextVal);
        }

        public Button getSurveyRsltStsBtn() {
            return surveyRsltStsBtn;
        }
    }
}
