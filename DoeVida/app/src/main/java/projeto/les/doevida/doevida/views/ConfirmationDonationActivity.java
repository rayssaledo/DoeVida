package projeto.les.doevida.doevida.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import projeto.les.doevida.doevida.R;
import projeto.les.doevida.doevida.models.BodyNotification;

public class ConfirmationDonationActivity extends AppCompatActivity {

    private TextView tv_donor_name;
    private TextView tv_blood_type_donor;
    private TextView tv_receptor_name;

    private BodyNotification bodyNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_donation);

        tv_donor_name = (TextView) findViewById(R.id.tv_donor_name);
        tv_blood_type_donor = (TextView) findViewById(R.id.tv_blood_type_donor);
        tv_receptor_name = (TextView) findViewById(R.id.tv_receptor_name);

        Intent it = getIntent();
        bodyNotification = (BodyNotification) it.getSerializableExtra("CONFIRMATIONDONATION");

        tv_donor_name.setText(bodyNotification.getDonorName());
        tv_blood_type_donor.setText(bodyNotification.getBloodTypeDonor());
        tv_receptor_name.setText(bodyNotification.getPatientName());

    }

}
