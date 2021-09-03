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
import com.softcodes.wework.candidate.models.Job_Details;

import java.util.List;

public class Job_Details_Adapter extends RecyclerView.Adapter<Job_Details_Adapter.JobViewHolder> {

private Context mCtx;
private List<Job_Details> jobs_list;

private Job_Details_Adapter.OnItemClickListenerJobs mListener;

public void setOnClickListenerJobs(Job_Details_Adapter.OnItemClickListenerJobs listener) {
        mListener = listener;
        }

@NonNull
@Override
public Job_Details_Adapter.JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
@SuppressLint("InflateParams") View view = inflater.inflate(R.layout.job_deatils, null);
        return new JobViewHolder(view);
        }


public Job_Details_Adapter(Context mCtx, List<Job_Details> jobs_list) {
        this.mCtx = mCtx;
        this.jobs_list = jobs_list;
        }

@SuppressLint("SetTextI18n")
@Override
public void onBindViewHolder(@NonNull Job_Details_Adapter.JobViewHolder holder, int position) {
        Job_Details s = jobs_list.get(position);

        Glide.with(mCtx)
        .load(s.getJbcompanyImage())
        .centerCrop()
        .placeholder(R.drawable.getajob)
        .into(holder.JImage);
        holder.jobId.setText(s.getJbid());
        holder.JName.setText(s.getJbname());
        holder.Jlocation.setText(s.getJblocation());
        holder.Jsalary.setText("Ugx" + " " + s.getJbsalary());
        holder.Jcategory.setText(s.getJbcategory());
        holder.Jdeadline.setText(s.getJbdeadline());
        holder.Jskills.setText(s.getJbskills());
        holder.Jcompany.setText(s.getJbcompanyName());
        holder.Jphone.setText(s.getJbphone());
        holder.Jwebsite.setText(s.getJbwebsite());
        holder.Jqualif.setText(s.getJbqualification());
        holder.Jexper.setText(s.getJbexperience());
        holder.Jwork.setText(s.getJbworktime());
        holder.Jdescrip.setText(s.getJbdescription());


        }

public interface OnItemClickListenerJobs {
    void onItemclick(int position);
    void onbackJobs(int position);

}


    @Override
    public int getItemCount() {
        return jobs_list.size();
    }
public class JobViewHolder extends RecyclerView.ViewHolder {
    TextView JName, Jlocation, Jsalary, Jcategory, Jdeadline,jobId,Jskills,Jcompany,Jphone,Jwebsite,Jqualif,
    Jexper,Jwork,Jdescrip;
    ImageView JImage,back;
    Button apply;
    public JobViewHolder(@NonNull View itemView) {
        super(itemView);
        jobId = itemView.findViewById(R.id.jbref_id);
        JName = itemView.findViewById(R.id.jbname);
        Jlocation = itemView.findViewById(R.id.jblocation);
        Jsalary = itemView.findViewById(R.id.jbsalary);
        Jcategory = itemView.findViewById(R.id.jbcategory);
        JImage = itemView.findViewById(R.id.img_cart);
        Jdeadline = itemView.findViewById(R.id.jbdeadline);
       Jskills =itemView.findViewById(R.id.jbskills);
       Jcompany = itemView.findViewById(R.id.jbcomp);
       Jphone = itemView.findViewById(R.id.jbphone);
       Jwebsite = itemView.findViewById(R.id.jbwebsite);
       Jqualif = itemView.findViewById(R.id.jbqualification);
       Jexper = itemView.findViewById(R.id.jbexperience);
       Jwork = itemView.findViewById(R.id.jbwork_time);
       Jdescrip = itemView.findViewById(R.id.jbdesc);
       back = itemView.findViewById(R.id.img_backd);


        apply = itemView.findViewById(R.id.apply_today);


        apply.setOnClickListener(new View.OnClickListener() {
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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mListener.onbackJobs(position);
                    }
                }
            }
        });
    }

}
}
