package projeto.les.doevida.doevida.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import projeto.les.doevida.doevida.R;
import projeto.les.doevida.doevida.models.Form;
import projeto.les.doevida.doevida.models.Request;
import projeto.les.doevida.doevida.utils.HttpUtils;
import projeto.les.doevida.doevida.utils.MySharedPreferences;

public class InformationOrderActivity extends AppCompatActivity {

    private TextView donor_name;
    private TextView patient_name;
    private TextView hospital_name;
    private TextView city;
    private TextView state;

    private MySharedPreferences userLogged;
    private List<Form> userDetails;
    private Request request;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_order);

//        userLogged = new MySharedPreferences(getApplicationContext());
//        userDetails = userLogged.getListMyForms();

        //String name = userDetails.get(form.getPatientName());

        donor_name = (TextView) findViewById(R.id.tv_donor_name);
        patient_name = (TextView) findViewById(R.id.tv_patient_name);
        hospital_name = (TextView) findViewById(R.id.tv_hospital_name);
        city = (TextView) findViewById(R.id.tv_city_name);
        state = (TextView) findViewById(R.id.tv_state_name);
        //deadline = (TextView) findViewById(R.id.tv_deadline);

//        Intent it = getIntent();
//        request = (Request) it.getSerializableExtra("FORMORDER");
//
//        donor_name.setText(request.getRequesterName());
//        patient_name.setText(request.getRequestDate().toString());

        Intent it = getIntent();
        Form form = (Form) it.getSerializableExtra("FORMORDER");

        donor_name.setText(form.getLoginDest());
        patient_name.setText(form.getPatientName());
        hospital_name.setText(form.getHospitalName());
        city.setText(form.getCity());
        state.setText(form.getState());
    }

}