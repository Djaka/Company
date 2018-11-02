package com.djakapermana.company.Presenter;

import com.djakapermana.company.Contract.IMain;
import com.djakapermana.company.Model.Employee;
import com.djakapermana.company.View.MainActivity;

import java.util.List;

public class MainPresenter implements IMain.Presenter {

    public MainPresenter() {
    }

    @Override
    public void showEmployees() {
        List<Employee> employees = Employee.listAll(Employee.class);
        MainActivity.pInstance.showEmployees(employees);
    }
}
