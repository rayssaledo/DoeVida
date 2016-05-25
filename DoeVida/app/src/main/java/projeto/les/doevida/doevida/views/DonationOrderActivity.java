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

import java.util.Date;
import java.util.HashMap;

import projeto.les.doevida.doevida.R;
import projeto.les.doevida.doevida.models.Form;
import projeto.les.doevida.doevida.utils.HttpListener;
import projeto.les.doevida.doevida.utils.HttpUtils;
import projeto.les.doevida.doevida.utils.MySharedPreferences;

public class DonationOrderActivity extends AppCompatActivity {

    private MySharedPreferences userLogged;
    private HashMap<String, String> userDetails;
    private String nameUserLogged;
    private String loginUserLogged;
    private String bloodTypeDonor;
    private String patientName;
    private String cityPatient;
    private String statePatient;
    private String hospitalPatient;
    private String bloodTypePatient;
    private String loginDest;
    private Date date;

    private Button btn_refuse;
    private Button btn_accept;
    private TextView patient_name;
    private TextView hospital_name;
    private TextView city;
    private TextView state;
    private TextView bloodType;
    private TextView deadline;
    private Form form;

    private HttpUtils mHttp;
    private View mLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_order);

        userLogged = new MySharedPreferences(getApplicationContext());
        userDetails = userLogged.getUserDetails();

        nameUserLogged = userDetails.get(MySharedPreferences.KEY_NAME_USER);
        loginUserLogged = userDetails.get(MySharedPreferences.KEY_USERNAME_USER);
        bloodTypeDonor = userDetails.get(MySharedPreferences.KEY_BLOOD_TYPE_USER);

        patient_name = (TextView) findViewById(R.id.tv_patient_name);
        hospital_name = (TextView) findViewById(R.id.tv_hospital_name);
        city = (TextView) findViewById(R.id.tv_city_name);
        state = (TextView) findViewById(R.id.tv_state_name);
        bloodType = (TextView) findViewById(R.id.tv_blood_type_patient);
        deadline = (TextView) findViewById(R.id.tv_deadline);


        Intent it = getIntent();
        form = (Form) it.getSerializableExtra("FORMORDER");
        if (form == null){
            form = (Form) it.getSerializableExtra("NOTIFICATIONT1");
        }

        patient_name.setText(form.getPatientName());
        hospital_name.setText(form.getHospitalName());
        city.setText(form.getCity());
        state.setText(form.getState());
        bloodType.setText(form.getTypeOfBlood());

       // date = form.getDeadline();
       //// SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
       // String mDeadline = format1.format(date);

        //deadline.setText(mDeadline);

        patientName = form.getPatientName();
        hospitalPatient = form.getHospitalName();
        cityPatient = form.getCity();
        statePatient = form.getState();
        bloodTypePatient = form.getTypeOfBlood();
        loginDest = form.getLoginDest();

        mHttp = new HttpUtils(this);
        mLoading = findViewById(R.id.loadingDonationOrder);

        btn_refuse = (Button) findViewById(R.id.btn_refuse);
        btn_refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setView(DonationOrderActivity.this, DonorsActivity.class);
                finish();
            }

//                String url = "http://doevida-grupoles.rhcloud.com/refuseRequestDonation";
//                JSONObject json = new JSONObject();
//                JSONObject jsonBody = new JSONObject();
//                try {
//                    jsonBody.put("login", loginDest);
//                    jsonBody.put("patientName", patientName);
//                    jsonBody.put("hospitalName", hospitalPatient);
//                    jsonBody.put("city", cityPatient);
//                    jsonBody.put("state", statePatient);
//                    jsonBody.put("bloodType", bloodTypePatient);
//                    SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
//                    String mDeadline = format1.format(date);
//                    Date dateFormat = format1.parse(mDeadline);
//                    jsonBody.put("deadline", dateFormat);
//
//                    System.out.println(mDeadline + "deadlineLLL");
//                    System.out.println(jsonBody + "JSONBODY");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//
//                try {
//                    json.put("receiverLogin", loginUserLogged);
//                    json.put("senderLogin", loginDest);
//                    json.put("titleNotification", "Solicitacao de sangue");
//                    json.put("bodyNotification", jsonBody);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                mHttp.post(url, json.toString(), new HttpListener() {
//
//                    @Override
//                    public void onSucess(JSONObject response) throws JSONException {
//                        if (response.getInt("ok") == 0) {
//                            new AlertDialog.Builder(DonationOrderActivity.this)
//                                    .setTitle("Erro")
//                                    .setMessage(response.getString("msg"))
//                                    .setNeutralButton("OK", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialogInterface, int i) {
//                                            mLoading.setVisibility(View.GONE);
//                                        }
//                                    })
//                                    .create()
//                                    .show();
//                        } else {
//                            new AlertDialog.Builder(DonationOrderActivity.this)
//                                    .setMessage(response.getString("msg"))
//                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialogInterface, int i) {
//                                            setView(DonationOrderActivity.this, DonorsActivity.class);
//                                            finish();
//                                        }
//                                    })
//                                    .create()
//                                    .show();
//
//                        }
//                    }
//
//                    @Override
//                    public void onTimeout() {
//                        new AlertDialog.Builder(DonationOrderActivity.this)
//                                .setTitle("Erro")
//                                .setMessage("Conexão não disponível")
//                                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i) {
//                                        mLoading.setVisibility(View.GONE);
//                                    }
//                                })
//                                .create()
//                                .show();
//                    }
//                });
//            }
        });

        btn_accept = (Button) findViewById(R.id.btn_accept);
        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveRequestsAccepted(form);
                String url = "http://doevida-grupoles.rhcloud.com/sendNotification";
                JSONObject json = new JSONObject();
                JSONObject jsonBodyNotification = new JSONObject();

                try {
                    jsonBodyNotification.put("login", loginUserLogged);
                    jsonBodyNotification.put("donorName", nameUserLogged);
                    jsonBodyNotification.put("bloodTypeDonor", bloodTypeDonor);
                    jsonBodyNotification.put("patientName", patientName);
                    jsonBodyNotification.put("bloodTypePatient", bloodTypePatient);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    json.put("titleNotification", "Pedido aceito");
                    json.put("bodyNotification", jsonBodyNotification);
                    json.put("receiverLogin", loginDest);
                    json.put("senderLogin", loginUserLogged);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mHttp.post(url, json.toString(), new HttpListener() {
                    @Override
                    public void onSucess(JSONObject result) throws JSONException {
                        if (result.getInt("ok") == 0) {
                            new AlertDialog.Builder(DonationOrderActivity.this)
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
                            new AlertDialog.Builder(DonationOrderActivity.this)
                                    .setMessage("Pedido aceito")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            setView(DonationOrderActivity.this, DonorsActivity.class);
                                            finish();
                                        }
                                    })
                                    .create()
                                    .show();

                        }
                    }

                    @Override
                    public void onTimeout() {
                        new AlertDialog.Builder(DonationOrderActivity.this)
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
        });
    }

    private void saveRequestsAccepted(Form form) {
        JSONObject jsonFormulario = new JSONObject();
        try {
            jsonFormulario.put("login", form.getLoginDest());
            jsonFormulario.put("patientName", form.getPatientName());
            jsonFormulario.put("hospitalName", form.getHospitalName());
            jsonFormulario.put("city", form.getCity());
            jsonFormulario.put("state", form.getState());
            jsonFormulario.put("bloodType", form.getTypeOfBlood());
            jsonFormulario.put("deadline", form.getDeadline());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        userLogged.saveRequestsAccepted(jsonFormulario);
    }

    public void setView(Context context, Class classe) {
        Intent it = new Intent();
        it.setClass(context, classe);
        startActivity(it);
    }
}