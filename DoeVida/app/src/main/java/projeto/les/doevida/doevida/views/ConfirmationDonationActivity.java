package projeto.les.doevida.doevida.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import projeto.les.doevida.doevida.R;
import projeto.les.doevida.doevida.models.BodyNotification;
import projeto.les.doevida.doevida.utils.HttpListener;
import projeto.les.doevida.doevida.utils.HttpUtils;
import projeto.les.doevida.doevida.utils.MySharedPreferences;

public class ConfirmationDonationActivity extends AppCompatActivity {

    private TextView tv_donor_name;
    private TextView tv_blood_type_donor;
    private TextView tv_receptor_name;
    private Button btn_recebi;
    private HttpUtils mHttp;
    private MySharedPreferences userLogged;
    private HashMap<String, String> userDetails;
    private BodyNotification bodyNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_donation);

        mHttp = new HttpUtils(this);
        userLogged = new MySharedPreferences(getApplicationContext());
        userDetails = userLogged.getUserDetails();
        final String loginUserLogged = userDetails.get(MySharedPreferences.KEY_USERNAME_USER);
        final String nameUserLogged = userDetails.get(MySharedPreferences.KEY_NAME_USER);
        tv_donor_name = (TextView) findViewById(R.id.tv_donor_name);
        tv_blood_type_donor = (TextView) findViewById(R.id.tv_blood_type_donor);
        tv_receptor_name = (TextView) findViewById(R.id.tv_receptor_name);
        btn_recebi = (Button) findViewById(R.id.btn_recebi);

        Intent it = getIntent();
        bodyNotification = (BodyNotification) it.getSerializableExtra("CONFIRMATIONDONATION");

        if (bodyNotification == null){
            bodyNotification = (BodyNotification) it.getSerializableExtra("NOTIFICATIONT2");
        }
        tv_donor_name.setText(bodyNotification.getDonorName());
        tv_blood_type_donor.setText(bodyNotification.getBloodTypeDonor());
        tv_receptor_name.setText(bodyNotification.getPatientName());

        btn_recebi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://doevida-grupoles.rhcloud.com/sendNotification";
                JSONObject json = new JSONObject();
                JSONObject jsonBodyNotification = new JSONObject();

                try {
                    jsonBodyNotification.put("nameOfUser", nameUserLogged);
                    jsonBodyNotification.put("receptorName", bodyNotification.getPatientName());
                    jsonBodyNotification.put("bloodTypeReceptor", bodyNotification.getBloodTypePatient());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    json.put("titleNotification", "Doacao recebida");
                    json.put("bodyNotification", jsonBodyNotification);
                    json.put("receiverLogin", bodyNotification.getLoginDest());
                    json.put("senderLogin", loginUserLogged);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mHttp.post(url, json.toString(), new HttpListener() {
                    @Override
                    public void onSucess(JSONObject result) throws JSONException {
                        if (result.getInt("ok") == 0) {
                            new AlertDialog.Builder(ConfirmationDonationActivity.this)
                                    .setTitle("Erro")
                                    .setMessage(result.getString("msg"))
                                    .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            //mLoading.setVisibility(View.GONE);
                                        }
                                    })
                                    .create()
                                    .show();
                        } else {
                            setView(ConfirmationDonationActivity.this, DonorsActivity.class);
                        }
                    }

                    @Override
                    public void onTimeout() {
                        new AlertDialog.Builder(ConfirmationDonationActivity.this)
                                .setTitle("Erro")
                                .setMessage("Conexão não disponível")
                                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //mLoading.setVisibility(View.GONE);
                                    }
                                })
                                .create()
                                .show();
                    }
                });
            }
        });

    }

    public void setView(Context context, Class classe){
        Intent it = new Intent();
        it.setClass(context, classe);
        startActivity(it);
    }

}
