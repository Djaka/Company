package com.djakapermana.company.View;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.djakapermana.company.Contract.IEmployee;
import com.djakapermana.company.Presenter.EmployeePresenter;
import com.djakapermana.company.Presenter.MainPresenter;
import com.djakapermana.company.R;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.model.Image;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Checked;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class AddEmployeeActivity extends AppCompatActivity implements IEmployee.View, Validator.ValidationListener {

    EmployeePresenter presenter;
    @NotEmpty(message = "Must be field")
    @Length(max = 10, min = 6, message = "Input must be between 6 and 10 characters")
    EditText editTextNik;
    @NotEmpty(message = "Must be field")
    EditText editTextName;
    @NotEmpty(message = "Must be field")
    EditText editTextBirthDate;
    EditText editTextBirthPlace;
    @NotEmpty(message = "Must be field")
    EditText editTextLevel;
    @Checked
    RadioGroup radioGroupStatus;
    ImageView imageViewEmployee;
    Button buttonSave;
    String status;
    Validator validator;
    SimpleDateFormat formatdate = new SimpleDateFormat("yyyy-MM-dd");
    Calendar myCalendar = Calendar.getInstance();
    Image image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        presenter = new EmployeePresenter(this, getApplicationContext());

        validator = new Validator(this);
        validator.setValidationListener(this);

        editTextNik = findViewById(R.id.edt_nik);
        editTextName = findViewById(R.id.edt_name);
        editTextBirthDate = findViewById(R.id.edt_birth_date);
        editTextBirthPlace = findViewById(R.id.edt_birth_place);
        radioGroupStatus = findViewById(R.id.rg_status);
        editTextLevel = findViewById(R.id.edt_level);
        buttonSave = findViewById(R.id.btn_save_employee);
        imageViewEmployee = findViewById(R.id.img_employee);

        imageViewEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.create(AddEmployeeActivity.this)
                        .returnMode(ReturnMode.ALL) // set whether pick and / or camera action should return immediate result or not.
                        .folderMode(true) // folder mode (false by default)
                        .toolbarFolderTitle("Folder") // folder selection title
                        .toolbarImageTitle("Tap to select") // image selection title
                        .single() // single mode
                        .showCamera(true) // show camera or not (true by default)
                        .imageDirectory("Camera") // directory name for captured image  ("Camera" folder by default)
                        .theme(R.style.ImagePickerTheme) // must inherit ef_BaseTheme. please refer to sample
                        .enableLog(false) // disabling log
                        .start(); // start image picker activity with request code
            }
        });

        editTextBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener reloadingdate = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar myCalendar = Calendar.getInstance();
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        editTextBirthDate.setText(formatdate.format(myCalendar.getTime()));
                    }
                };
                new DatePickerDialog(AddEmployeeActivity.this, reloadingdate, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();
            }
        });

        radioGroupStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_married:
                        status = "Married";
                        break;
                    case R.id.rb_single:
                        status = "Single";
                        break;
                    default:
                        status = "Single";
                        break;
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            image = ImagePicker.getFirstImageOrNull(data);
            Glide.with(AddEmployeeActivity.this).load(image.getPath()).into(imageViewEmployee);
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void showMessage(String message, int status) {
        if (status == 1) {
            Toasty.success(this, message, Toast.LENGTH_SHORT).show();
            MainPresenter presenter = new MainPresenter();
            presenter.showEmployees();
            finish();
        } else {
            Toasty.error(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MainPresenter presenter = new MainPresenter();
        presenter.showEmployees();
    }

    @Override
    public void onValidationSucceeded() {
        presenter.save(editTextNik.getText().toString(),
                editTextName.getText().toString(),
                editTextBirthDate.getText().toString(),
                editTextBirthPlace.getText().toString(),
                status, editTextLevel.getText().toString(),
                image);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
