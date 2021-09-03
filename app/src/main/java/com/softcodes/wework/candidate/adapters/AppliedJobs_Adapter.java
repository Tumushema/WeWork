package com.softcodes.wework.candidate.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.softcodes.wework.R;
import com.softcodes.wework.candidate.models.AppliedJobs;
import java.util.List;

public class AppliedJobs_Adapter extends RecyclerView.Adapter<AppliedJobs_Adapter.JobViewHolder> {

    private Context mCtx;
    private List<AppliedJobs> myapplication_list;

    private AppliedJobs_Adapter.OnItemClickListenerJobs mListener;

    public void setOnClickListenerJobs(AppliedJobs_Adapter.OnItemClickListenerJobs listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public AppliedJobs_Adapter.JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.applied_jobs_list, null);
        return new JobViewHolder(view);
    }


    public AppliedJobs_Adapter(Context mCtx, List<AppliedJobs> myapplication_list) {
        this.mCtx = mCtx;
        this.myapplication_list = myapplication_list;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AppliedJobs_Adapter.JobViewHolder holder, int position) {
        AppliedJobs s = myapplication_list.get(position);

        holder.JName.setText(s.getAplicName());
        holder.Jlocation.setText(s.getAplicLocation());
        holder.Jsalary.setText("Ugx" + " " + s.getAplicSalary());
        holder.Jdeadline.setText(s.getAplicDeadline());
        holder.jobId.setText(s.getAplicID());
        holder.Jcompany.setText(s.getAplicCompanyName());
        holder.Jstatus.setText(s.getAplicStatus());

    }

    public interface OnItemClickListenerJobs {
        void onItemclick(int position);

    }


    @Override
    public int getItemCount() {
        return myapplication_list.size();
    }
    public static class JobViewHolder extends RecyclerView.ViewHolder {
        TextView Jstatus,JName, Jlocation, Jsalary, Jdeadline,Jcompany,jobId;

        public JobViewHolder(@NonNull View itemView) {
            super(itemView);
            Jstatus = itemView.findViewById(R.id.aplicstatus);
            jobId = itemView.findViewById(R.id.aplicref_id);
            JName = itemView.findViewById(R.id.aplicname);
            Jlocation = itemView.findViewById(R.id.apliclocation);
            Jsalary = itemView.findViewById(R.id.aplicsalary);
            Jdeadline = itemView.findViewById(R.id.aplicdeadline);
            Jcompany = itemView.findViewById(R.id.apliccomp);


        }

    }
}
