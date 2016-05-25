package projeto.les.doevida.doevida.views;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import projeto.les.doevida.doevida.R;
import projeto.les.doevida.doevida.models.BodyNotification;
import projeto.les.doevida.doevida.utils.HttpListener;
import projeto.les.doevida.doevida.utils.HttpUtils;
import projeto.les.doevida.doevida.utils.MySharedPreferences;

public class ConfirmationReceiptDonationActivity extends AppCompatActivity {

    private TextView tv_user_requester_name;
    private TextView tv_receptor_name;
    private TextView tv_blood_type_receptor;
    private TextView btn_update;
    private BodyNotification bodyNotification;

    private MySharedPreferences userLogged;
    private HashMap<String, String> userDetails;

    private HttpUtils mHttp;
    private View mLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_receipt_donation);

        userLogged = new MySharedPreferences(getApplicationContext());
        userDetails = userLogged.getUserDetails();

        mHttp = new HttpUtils(this);

        tv_user_requester_name = (TextView) findViewById(R.id.tv_user_requester_name);
        tv_receptor_name = (TextView) findViewById(R.id.tv_receptor_name);
        tv_blood_type_receptor = (TextView) findViewById(R.id.tv_blood_type_receptor);
        btn_update = (Button) findViewById(R.id.btn_update);

        Intent it = getIntent();
        bodyNotification = (BodyNotification) it.getSerializableExtra("DONATIONRECEIVED");
        if (bodyNotification == null){
            bodyNotification = (BodyNotification) it.getSerializableExtra("NOTIFICATIONT3");
        }

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

        mLoading = dialogDateOfLastDonation.findViewById(R.id.loadingDateOfLastDonation);
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
                String dateOfLastDonation = input_date_of_last_donation.getText().toString();
                String login = userDetails.get(MySharedPreferences.KEY_USERNAME_USER);
                updateDateOfLastDonation(login, dateOfLastDonation, dialogDateOfLastDonation);
                incrementNumberDonations(login);
            }
        });
    }

    private void updateDateOfLastDonation(String login, String lastDonation, final Dialog dialog){
        mLoading.setVisibility(View.VISIBLE);
        String url = "http://doevida-grupoles.rhcloud.com/updateLastDonation";
        JSONObject json = new JSONObject();

        try {
            json.put("login", login);
            json.put("lastDonation", lastDonation);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        mHttp.post(url, json.toString(), new HttpListener() {
            @Override
            public void onSucess(JSONObject result) throws JSONException {
                if (result.getInt("ok") == 0) {
                    new AlertDialog.Builder(ConfirmationReceiptDonationActivity.this)
                            .setTitle("Erro")
                            .setMessage(result.getString("msg"))
                            .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    mLoading.setVisibility(View.GONE);
                                }
                            })
                            .create()
                            .show();
                } else {
                    new AlertDialog.Builder(ConfirmationReceiptDonationActivity.this)
                            .setMessage("Data de última doação atualizada com sucesso!")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialog.dismiss();
                                }
                            })
                            .create()
                            .show();
                }
            }

            @Override
            public void onTimeout() {
                new AlertDialog.Builder(ConfirmationReceiptDonationActivity.this)
                        .setTitle("Erro")
                        .setMessage("Conexão não disponível")
                        .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mLoading.setVisibility(View.GONE);
                            }
                        })
                        .create()
                        .show();
            }
        });
    }


    private void incrementNumberDonations(String login){
        String url = "http://doevida-grupoles.rhcloud.com/incNumDonations";
        JSONObject json = new JSONObject();

        try {
            json.put("login", login);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        mHttp.post(url, json.toString(), new HttpListener() {
            @Override
            public void onSucess(JSONObject result) throws JSONException{
                if (result.getInt("ok") == 0) {
                    new AlertDialog.Builder(ConfirmationReceiptDonationActivity.this)
                            .setTitle("Erro")
                            .setMessage(result.getString("msg"))
                            .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    mLoading.setVisibility(View.GONE);
                                }
                            })
                            .create()
                            .show();
                } else {
                    new AlertDialog.Builder(ConfirmationReceiptDonationActivity.this)
                            .setMessage("Número de doação incrementado!")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .create()
                            .show();
                }
            }
            @Override
            public void onTimeout() {
                new AlertDialog.Builder(ConfirmationReceiptDonationActivity.this)
                        .setTitle("Erro")
                        .setMessage("Conexão não disponível")
                        .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mLoading.setVisibility(View.GONE);
                            }
                        })
                        .create()
                        .show();
            }
        });
    }
}
