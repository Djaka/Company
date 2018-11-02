package com.djakapermana.company.Contract;

import com.esafirm.imagepicker.model.Image;

public interface IEmployee {
    interface Presenter{
        void save(String nik, String name, String birthDate, String birthPlace, String status, String level, Image photo);
        void update();
        void delete();
    }

    interface View{
        void showMessage(String message, int status);
    }
}
