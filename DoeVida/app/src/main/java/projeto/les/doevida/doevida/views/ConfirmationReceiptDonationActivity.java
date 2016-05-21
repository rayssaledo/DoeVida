package projeto.les.doevida.doevida.views;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import projeto.les.doevida.doevida.R;
import projeto.les.doevida.doevida.models.BodyNotification;

public class ConfirmationReceiptDonationActivity extends AppCompatActivity {

    private TextView tv_user_requester_name;
    private TextView tv_receptor_name;
    private TextView tv_blood_type_receptor;
    private TextView btn_update;
    private BodyNotification bodyNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_receipt_donation);

        tv_user_requester_name = (TextView) findViewById(R.id.tv_user_requester_name);
        tv_receptor_name = (TextView) findViewById(R.id.tv_receptor_name);
        tv_blood_type_receptor = (TextView) findViewById(R.id.tv_blood_type_receptor);
        btn_update = (Button) findViewById(R.id.btn_update);

        Intent it = getIntent();
        bodyNotification = (BodyNotification) it.getSerializableExtra("DONATIONRECEIVED");

        tv_user_requester_name.setText(bodyNotification.getNameOfUser());
        tv_receptor_name.setText(bodyNotification.getPatientName());
        tv_blood_type_receptor.setText(bodyNotification.getBloodTypePatient());

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogDateOfLastDonation();
            }
        });
    }

    private void openDialogDateOfLastDonation() {
        final Dialog dialogDateOfLastDonation = new Dialog(ConfirmationReceiptDonationActivity.this);
        dialogDateOfLastDonation.setContentView(R.layout.dialog_date_of_last_donation);
        dialogDateOfLastDonation.setTitle("Data da última doação");
        dialogDateOfLastDonation.setCancelable(true);
        dialogDateOfLastDonation.show();

        final EditText input_date_of_last_donation = (EditText) dialogDateOfLastDonation.
                findViewById(R.id.input_date_of_last_donation);
        final MaskEditTextChangedListener maskDateOfLastDonation =
                new MaskEditTextChangedListener("##/##/####",
                        input_date_of_last_donation);
        input_date_of_last_donation.addTextChangedListener(maskDateOfLastDonation);

        final Button btn_ok = (Button) dialogDateOfLastDonation.findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDateOfLastDonation.dismiss();
            }
        });
    }
}
