package com.djakapermana.company.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.djakapermana.company.Adapter.EmployeeAdapter;
import com.djakapermana.company.Model.Employee;
import com.djakapermana.company.Presenter.MainPresenter;
import com.djakapermana.company.R;

import java.util.List;

public class MainActivity extends AppCompatActivity{

    MainPresenter presenter;
    FloatingActionButton fabAdd;
    public static MainActivity pInstance;
    EmployeeAdapter employeeAdapter;
    RecyclerView recyclerViewEmployees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pInstance = this;

        recyclerViewEmployees = findViewById(R.id.rec_employees);
        recyclerViewEmployees.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        presenter = new MainPresenter();
        presenter.showEmployees();


        fabAdd = findViewById(R.id.fab_add_employee);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEmployeeActivity.class);
                startActivity(intent);
            }
        });
    }

    public void showEmployees(List<Employee> employees) {
        employeeAdapter = new EmployeeAdapter(this, employees);
        recyclerViewEmployees.setAdapter(employeeAdapter);
    }
}
