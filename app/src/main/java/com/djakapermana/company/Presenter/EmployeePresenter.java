package com.djakapermana.company.Presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;

import com.djakapermana.company.Contract.IEmployee;
import com.djakapermana.company.Model.Employee;
import com.esafirm.imagepicker.model.Image;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import static com.djakapermana.company.Utils.ImageHelper.compressbitmap;

public class EmployeePresenter implements IEmployee.Presenter {

    IEmployee.View view;
    Context context;

    public EmployeePresenter(IEmployee.View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void save(final String nik, final String name, final String birthDate, final String birthPlace, final String status, final String level, final Image photo) {

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                try {

                    String strPhoto;

                    Employee employee = new Employee();
                    employee.setNik(nik);
                    employee.setName(name);
                    employee.setBirthDate(birthDate);
                    employee.setBirthPlace(birthPlace);
                    employee.setStatus(status);
                    employee.setLevel(level);

                    if (photo != null) {
                        File imagedir = new File(Environment.getExternalStorageDirectory(), "/Company/" + name + "/");
                        if (!imagedir.exists()) {
                            imagedir = new File(Environment.getExternalStorageDirectory(), "/Company/");
                            imagedir.mkdir();
                            imagedir = new File(Environment.getExternalStorageDirectory(), "/Company/" + name + "/");
                            imagedir.mkdir();
                        }

                        Bitmap bitmap = BitmapFactory.decodeFile(photo.getPath());
                        Bitmap empPhoto = compressbitmap(bitmap, false);

                        String uuidImage = UUID.randomUUID().toString();
                        File fileImg = new File(imagedir, uuidImage + ".jpeg");
                        OutputStream outImage = null;
                        try {
                            outImage = new FileOutputStream(fileImg, true);
                            empPhoto.compress(Bitmap.CompressFormat.JPEG, 75, outImage);
                            outImage.flush();
                            outImage.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        strPhoto = uuidImage + ".jpeg";
                    } else {
                        strPhoto = null;
                    }

                    employee.setPhoto(strPhoto);
                    employee.save();

                    view.showMessage("Success", 1);

                } catch (Exception e) {
                    view.showMessage("Error: " + e.getMessage(), 0);
                }
            }
        });
    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }
}
