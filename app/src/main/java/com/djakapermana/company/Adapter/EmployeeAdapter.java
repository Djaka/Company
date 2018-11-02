package com.djakapermana.company.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.djakapermana.company.Model.Employee;
import com.djakapermana.company.R;

import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {

    Context context;
    List<Employee> employees;

    public EmployeeAdapter(Context context, List<Employee> employees) {
        this.context = context;
        this.employees = employees;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_employee,parent,false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        Employee employee = employees.get(position);

        holder.textViewNik.setText(employee.getNik());
        holder.textViewName.setText(employee.getName());
        holder.textViewLevel.setText(employee.getLevel());
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    public class EmployeeViewHolder extends RecyclerView.ViewHolder{

        TextView textViewNik, textViewName, textViewLevel;

        public EmployeeViewHolder(View itemView) {
            super(itemView);

            textViewNik = itemView.findViewById(R.id.txt_nik);
            textViewName = itemView.findViewById(R.id.txt_name);
            textViewLevel = itemView.findViewById(R.id.txt_level);
        }
    }

}
