package projeto.les.doevida.doevida.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import projeto.les.doevida.doevida.R;
import projeto.les.doevida.doevida.models.Form;

public class DonationOrderActivity extends AppCompatActivity {

    private Button btn_refuse;
    private Button btn_accept;
    private TextView patient_name;
    private TextView hospital_name;
    private TextView city;
    private TextView state;
    private TextView bloodType;
    private TextView deadline;
    private  Form form;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_order);

        patient_name = (TextView) findViewById(R.id.tv_patient_name);
        hospital_name = (TextView) findViewById(R.id.tv_hospital_name);
        city = (TextView) findViewById(R.id.tv_city_name);
        state = (TextView) findViewById(R.id.tv_state_name);
        bloodType = (TextView) findViewById(R.id.tv_blood_type_patient);
        deadline = (TextView) findViewById(R.id.tv_deadline);

        btn_refuse = (Button) findViewById(R.id.btn_refuse);
        btn_refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn_accept = (Button) findViewById(R.id.btn_accept);
        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Intent it = getIntent();
        Form form = (Form) it.getSerializableExtra("FORM_ORDER");

//        patient_name.setText(form.getPatientName());
//        hospital_name.setText(form.getHospitalName());
//        city.setText(form.getCity());
//        //state.setText(form.getState().toString());
//        bloodType.setText(form.getTypeOfBlood());
//        deadline.setText(form.getDeadline().toString());

    }
 }

