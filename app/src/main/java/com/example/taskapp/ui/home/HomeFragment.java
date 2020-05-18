package com.example.taskapp.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapp.App;
import com.example.taskapp.FormActivity;
import com.example.taskapp.R;
import com.example.taskapp.models.Task;
import com.example.taskapp.ui.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment {

    private TaskAdapter adapter;
    private List<Task> list = new ArrayList<>();
    LinearLayoutManager layoutManager;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        arrayList.addAll(App.getInstance().getDatabase().taskDao().getAll());
        adapter = new TaskAdapter(list);
        recyclerView.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
//        для изменения порядка
//        чтобы самые последние записи отображались в верхней части списка
        layoutManager.setStackFromEnd(true);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Intent intent = new Intent(getContext(), FormActivity.class);
                intent.putExtra("task", list.get(pos));
                startActivity(intent);

            }

            @Override
            public void onItemLongClick(int pos) {
                showAlert(list.get(pos));


            }

        });
        loadData();


    }

    private void showAlert(final Task task) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setMessage("Delete ?").setNegativeButton("Cancel", null).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                App.getInstance().getDatabase().taskDao().delete(task);

            }
        });
        builder.show();
    }


    private void loadData() {
        App.getInstance().getDatabase().taskDao().getAllLive().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                list.clear();
                list.addAll(tasks);
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void sortL() {
        list.clear();
        list.addAll(App.getInstance().getDatabase().taskDao().sort());
        adapter.notifyDataSetChanged();
        layoutManager.setReverseLayout(false);
        layoutManager.setStackFromEnd(false);


    }

    public void initList() {
        list.clear();
        list.addAll(App.getInstance().getDatabase().taskDao().sort());
        adapter.notifyDataSetChanged();
        layoutManager.setReverseLayout(false);
        layoutManager.setStackFromEnd(false);
    }
}
