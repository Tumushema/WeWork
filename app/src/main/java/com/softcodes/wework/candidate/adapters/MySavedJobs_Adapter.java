package com.softcodes.wework.candidate.adapters;

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
import com.softcodes.wework.candidate.models.Jobs;

import java.util.List;

public class MySavedJobs_Adapter extends RecyclerView.Adapter<MySavedJobs_Adapter.JobViewHolder> {

    private Context mCtx;
    private List<Jobs> jobs_list;

    private MySavedJobs_Adapter.OnItemClickListenerJobs mListener;

    public void setOnClickListenerJobs(MySavedJobs_Adapter.OnItemClickListenerJobs listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public MySavedJobs_Adapter.JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.jobs_list, null);
        return new JobViewHolder(view);
    }


    public MySavedJobs_Adapter(Context mCtx, List<Jobs> jobs_list) {
        this.mCtx = mCtx;
        this.jobs_list = jobs_list;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MySavedJobs_Adapter.JobViewHolder holder, int position) {
        Jobs s = jobs_list.get(position);

        Glide.with(mCtx)
                .load(s.getJob_image())
                .centerCrop()
                .placeholder(R.drawable.getajob)
                .into(holder.JImage);
        holder.JName.setText(s.getJob_name());
        holder.Jlocation.setText(s.getJob_location());
        holder.Jsalary.setText("Ugx" + " " + s.getJob_salary());
        holder.Jcategory.setText(s.getJob_category());
        holder.Jdeadline.setText(s.getJob_deadline());
        holder.jobId.setText(s.getJob_id());

    }

    public interface OnItemClickListenerJobs {
        void onItemclick(int position);

    }


    @Override
    public int getItemCount() {
        return jobs_list.size();
    }
    public static class JobViewHolder extends RecyclerView.ViewHolder {
        TextView JName, Jlocation, Jsalary, Jcategory, Jdeadline,Jdetails,jobId,share;
        ImageView JImage,Jshare,Jfavorite;
        Button apply;
        public JobViewHolder(@NonNull View itemView) {
            super(itemView);
            jobId = itemView.findViewById(R.id.ref_id);
            JName = itemView.findViewById(R.id.jobname);
            Jlocation = itemView.findViewById(R.id.joblocation);
            Jsalary = itemView.findViewById(R.id.jobprice);
            Jcategory = itemView.findViewById(R.id.jobcategory);
            JImage = itemView.findViewById(R.id.jobimage);
            Jdeadline = itemView.findViewById(R.id.jobdeadline_date);
            Jshare = itemView.findViewById(R.id.share_tip);
            Jfavorite = itemView.findViewById(R.id.jobsaved);
            Jdetails = itemView.findViewById(R.id.jobdetails);
            apply = itemView.findViewById(R.id.apply_now);
            share = itemView.findViewById(R.id.jobshare);
            Jshare.setVisibility(View.GONE);
            Jfavorite.setVisibility(View.GONE);
            Jdetails.setVisibility(View.GONE);
            apply.setVisibility(View.GONE);
            share.setVisibility(View.GONE);

        }

    }
}
