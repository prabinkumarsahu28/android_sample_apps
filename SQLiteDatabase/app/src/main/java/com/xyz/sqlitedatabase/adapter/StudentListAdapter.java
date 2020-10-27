package com.xyz.sqlitedatabase.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xyz.sqlitedatabase.R;
import com.xyz.sqlitedatabase.RecyclerItemClickListener;
import com.xyz.sqlitedatabase.Student;
import com.xyz.sqlitedatabase.StudentListViewHolder;

import java.util.List;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListViewHolder> {
    private List<Student> studentList;
    private RecyclerItemClickListener listener;

    public StudentListAdapter(List<Student> studentList, RecyclerItemClickListener listener) {
        this.studentList = studentList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public StudentListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new StudentListViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentListViewHolder holder, int position) {
        Student student = studentList.get(position);
        holder.setData(student);
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }
}
