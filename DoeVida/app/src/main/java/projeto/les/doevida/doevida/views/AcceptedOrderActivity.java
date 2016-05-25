package projeto.les.doevida.doevida.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import projeto.les.doevida.doevida.R;
import projeto.les.doevida.doevida.models.BodyNotification;

public class AcceptedOrderActivity extends AppCompatActivity {

    private Button btn_ok;
    private TextView donor_name;
    private TextView blood_type_donor;
    private TextView receptor_name;
    private TextView blood_type_receptor;
    private BodyNotification bodyNotification;

    private String donorName;
    private String bloodTypeDonor;
    private String receptorName;
    private String bloodTypeReceptor;
    private String loginDest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accepted_order);

        donor_name= (TextView) findViewById(R.id.tv_donor_name);
        blood_type_donor = (TextView) findViewById(R.id.tv_blood_type_donor);
        receptor_name = (TextView) findViewById(R.id.tv_receptor_name);
        blood_type_receptor = (TextView) findViewById(R.id.tv_blood_type_receptor);

        Intent it = getIntent();
        bodyNotification = (BodyNotification) it.getSerializableExtra("BODYNOTIFICATION");
        if (bodyNotification == null){
            bodyNotification = (BodyNotification) it.getSerializableExtra("NOTIFICATIONT4");
        }
        donor_name.setText(bodyNotification.getDonorName());
        blood_type_donor.setText(bodyNotification.getBloodTypeDonor());
        receptor_name.setText(bodyNotification.getPatientName());
        blood_type_receptor.setText(bodyNotification.getBloodTypePatient());

        loginDest = bodyNotification.getLoginDest();
        donorName = donor_name.getText().toString();
        bloodTypeDonor = blood_type_donor.getText().toString();
        receptorName = receptor_name.getText().toString();
        bloodTypeReceptor = blood_type_receptor.getText().toString();

        btn_ok = (Button) findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setView(AcceptedOrderActivity.this, DonorsActivity.class);
                finish();
            }
        });
    }

    public void setView(Context context, Class classe){
        Intent it = new Intent();
        it.setClass(context, classe);
        startActivity(it);
    }
}
