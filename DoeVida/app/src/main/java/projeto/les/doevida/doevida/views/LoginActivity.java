package projeto.les.doevida.doevida.views;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import projeto.les.doevida.doevida.R;
import projeto.les.doevida.doevida.utils.HttpListener;
import projeto.les.doevida.doevida.utils.HttpUtils;
import projeto.les.doevida.doevida.utils.MySharedPreferences;

public class LoginActivity extends AppCompatActivity {

    private Button btn_register;
    private Button btn_enter;
    private HttpUtils mHttp;
    private View mLoading;
    private EditText et_username;
    private EditText et_password;
    private MySharedPreferences mySharedPreferences;
    private GoogleCloudMessaging gcm;
    private String reg_id;
    private String PROJECT_NUMBER = "904914935842";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mySharedPreferences = new MySharedPreferences(getApplicationContext());
        getRegId();

        /*Esse código mostra na tela de login se o usuário está logado através de um valor booleano.
        Se o usuário estiver logado aparece: "Logado: true"
        Caso contrário ele mostra: "Deslogado :("
         */
        /*if (mySharedPreferences.isUserLoggedIn()){
            Toast.makeText(this, "Logado: " + mySharedPreferences.isUserLoggedIn() , Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Deslogado :( " , Toast.LENGTH_LONG).show();
        }*/

        mHttp = new HttpUtils(this);
        mLoading = findViewById(R.id.loading);

        btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setView(LoginActivity.this, UserCadastreActivity.class);
            }
        });

        btn_enter = (Button) findViewById(R.id.btn_enter);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = et_username.getText().toString();
                String password = et_password.getText().toString();

                //TODO : validar login, reg_id e password antes de mandar para o servidor
                Log.d("GCM_LOGIN", reg_id);
                login(login, password, reg_id);
//                if(!validateUsername(login)){
//                    new AlertDialog.Builder(LoginActivity.this)
//                            .setTitle("Campo obrigatório")
//                            .setMessage("Insira seu login")
//                            .setNeutralButton("OK", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//
//                                }
//                            })
//                            .create()
//                            .show();
//                } else if(!validatePassword(password)){
//                    new AlertDialog.Builder(LoginActivity.this)
//                            .setTitle("Campo obrigatório")
//                            .setMessage("Insira sua senha")
//                            .setNeutralButton("OK", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//
//                                }
//                            })
//                            .create()
//                            .show();
//                } else {
//                    login(login, password, reg_id);
//                }

            }
        });

    }

    private boolean validateUsername(String login) {
        if(login == null || login.equals("")){
            return false;
        } else {
            return true;
        }
    }

    private boolean validatePassword(String password) {
        if(password==null || password.equals("")){
            return false;
        } else{
            return true;
        }
    }

    private void login(String login, String password, String regId) {
        mLoading.setVisibility(View.VISIBLE);
        String urlCheckLogin = "http://doevida-grupoles.rhcloud.com/checkLogin";
        final JSONObject json = new JSONObject();

        try {
            json.put("login", login);
            json.put("pass", password);
            json.put("regId", regId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mHttp.post(urlCheckLogin, json.toString(), new HttpListener() {
            @Override
            public void onSucess(JSONObject result) {
                try {
                    if (result.getInt("ok") == 0) {
                        new AlertDialog.Builder(LoginActivity.this)
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
                        setView(LoginActivity.this, DonorsActivity.class);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onTimeout() {
                new AlertDialog.Builder(LoginActivity.this)
                        .setTitle("Erro")
                        .setMessage("Conexão não disponível.")
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

        String urlGetUser = "http://doevida-grupoles.rhcloud.com/getUser?login=" + login;

        mHttp.get(urlGetUser, new HttpListener() {
            @Override
            public void onSucess(JSONObject result) {
                try {
                    if (result.getInt("ok") == 0) {
                        new AlertDialog.Builder(LoginActivity.this)
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
                        JSONObject jsonUser = result.getJSONObject("result");
                        String login = jsonUser.getString("login");
                        String pass = jsonUser.getString("pass");
                        String name = jsonUser.getString("name");
                        String state = jsonUser.getString("state");
                        String city = jsonUser.getString("city");
                        String birthDate = jsonUser.getString("birthDate");
                        String gender = jsonUser.getString("gender");
                        String bloodType = jsonUser.getString("bloodType");
                        String lastDonation = jsonUser.getString("lastDonation");
                        String regId = jsonUser.getString("regId");
                        String numberDonations = jsonUser.getString("numberDonations");
                        mySharedPreferences.saveUser(name, birthDate, city, state, gender,
                                bloodType, lastDonation, login, pass, numberDonations);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onTimeout() {
                new AlertDialog.Builder(LoginActivity.this)
                        .setTitle("Erro")
                        .setMessage("Conexão não disponível.")
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

    public void setView(Context context, Class classe) {
        Intent it = new Intent();
        it.setClass(context, classe);
        startActivity(it);
    }

    public void getRegId(){
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
                    }
                    reg_id = gcm.register(PROJECT_NUMBER);
                    mySharedPreferences.saveRegId(reg_id);
                    msg = "Device registered, registration ID=" + reg_id;
                    Log.d("GCM",  msg);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                }
                return msg;
            }
            @Override
            protected void onPostExecute(String msg) {
                //etRegId.setText(msg + "\n");
            }
        }.execute(null, null, null);
    }

}