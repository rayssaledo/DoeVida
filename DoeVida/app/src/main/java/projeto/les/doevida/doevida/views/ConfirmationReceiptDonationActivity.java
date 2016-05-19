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

public class ConfirmationReceiptDonationActivity extends AppCompatActivity {

    private TextView tv_user_requester_name;
    private TextView tv_blood_type_requester;
    private TextView tv_receptor_name;
    private TextView tv_blood_type_receptor;
    private TextView btn_atualizar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_receipt_donation);

        tv_user_requester_name = (TextView) findViewById(R.id.tv_user_requester_name);
        tv_blood_type_requester = (TextView) findViewById(R.id.tv_blood_type_requester);
        tv_receptor_name = (TextView) findViewById(R.id.tv_receptor_name);
        tv_blood_type_receptor = (TextView) findViewById(R.id.tv_blood_type_receptor);
        btn_atualizar = (Button) findViewById(R.id.btn_atualizar);

        tv_user_requester_name.setText("login pedinte");
        tv_blood_type_requester.setText("tipo sanguíeo pedinte");
        tv_receptor_name.setText("nome paciente");
        tv_blood_type_receptor.setText("tipo sanguíneo paciente");
    }
}
