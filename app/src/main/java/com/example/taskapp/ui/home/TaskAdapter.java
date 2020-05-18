package com.example.taskapp.ui.home;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapp.R;
import com.example.taskapp.models.Task;
import com.example.taskapp.ui.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private List<Task> list;
    private OnItemClickListener onItemClickListener;

    public TaskAdapter(List<Task> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setIsRecyclable(true);


        if (position % 2 == 0) {
            holder.layout.setBackgroundColor(Color.parseColor("#FFFFFF"));
        } else {
            holder.layout.setBackgroundColor(Color.parseColor("#E5E5E5"));
        }
        holder.onBind(list.get(position));
        Log.e("TAG", "onBindViewHolder:");

    }


    @Override
    public int getItemCount() {

        return list.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;

    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout layout;
        private TextView textTitle, textDesc;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.layout);
            textTitle = itemView.findViewById(R.id.textTitle);
            textDesc = itemView.findViewById(R.id.textDescrip);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(getAdapterPosition());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemClickListener.onItemLongClick(getAdapterPosition());
                    return true;
                };
            });
            Log.e("TAG", "ViewHolder: ");
        }

        public void onBind(Task task) {
            textTitle.setText(task.getTitle());
            textDesc.setText(task.getDesk());
        }
    }

}

