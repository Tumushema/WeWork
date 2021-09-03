package com.softcodes.wework.employeer.jobs.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.softcodes.wework.R;

import com.softcodes.wework.employeer.jobs.models.Applicants;

import java.util.List;

public class Applicants_Adapter extends RecyclerView.Adapter<Applicants_Adapter.JobViewHolder> {

    private Context mCtx;
    private List<Applicants> jobs_list;

    private Applicants_Adapter.OnItemClickListenerJobs mListener;

    public void setOnClickListenerJobs(Applicants_Adapter.OnItemClickListenerJobs listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public Applicants_Adapter.JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.applicant_list, null);
        return new JobViewHolder(view);
    }


    public Applicants_Adapter(Context mCtx, List<Applicants> jobs_list) {
        this.mCtx = mCtx;
        this.jobs_list = jobs_list;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Applicants_Adapter.JobViewHolder holder, int position) {
        Applicants s = jobs_list.get(position);

        Glide.with(mCtx)
                .load(s.getApplicantcv())
                .centerCrop()
                .placeholder(R.drawable.getajob)
                .into(holder.JImage);
        holder.JName.setText(s.getApplicantName());
        if(s.getApplicantJobName().equalsIgnoreCase("Accepted")){

            holder.accept.setVisibility(View.INVISIBLE);
        }
        holder.Jlocation.setText(s.getApplicantJobName());
        holder.jobId.setText(s.getApplicantId());

    }

    public interface OnItemClickListenerJobs {
        void onItemclick(int position);
    }


    @Override
    public int getItemCount() {
        return jobs_list.size();
    }
    public  class JobViewHolder extends RecyclerView.ViewHolder {
        TextView JName, Jlocation,jobId;
        ImageView JImage;
        Button accept;

        public JobViewHolder(@NonNull View itemView) {
            super(itemView);
            jobId = itemView.findViewById(R.id.applicant_id);
            JName = itemView.findViewById(R.id.appli_jobname);
            Jlocation = itemView.findViewById(R.id.applicant_status);
            accept = itemView.findViewById(R.id.accept_applicant);
            JImage = itemView.findViewById(R.id.applicant_cv);


            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemclick(position);
                        }
                    }
                }
            });
        }

    }

}
