package projeto.les.doevida.doevida.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import projeto.les.doevida.doevida.R;
import projeto.les.doevida.doevida.models.Form;

public class InformationOrderActivity extends AppCompatActivity {

    private TextView patient_name;
    private TextView hospital_name;
    private TextView city;
    private TextView state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_order);

        patient_name = (TextView) findViewById(R.id.tv_patient_name);
        hospital_name = (TextView) findViewById(R.id.tv_hospital_name);
        city = (TextView) findViewById(R.id.tv_city_name);
        state = (TextView) findViewById(R.id.tv_state_name);

        Intent it = getIntent();
        Form form = (Form) it.getSerializableExtra("FORM");

        patient_name.setText(form.getPatientName());
        hospital_name.setText(form.getHospitalName());
        city.setText(form.getCity());
        state.setText(form.getState());
    }

}