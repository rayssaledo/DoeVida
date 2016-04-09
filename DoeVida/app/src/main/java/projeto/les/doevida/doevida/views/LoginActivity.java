package projeto.les.doevida.doevida.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import projeto.les.doevida.doevida.R;
import projeto.les.doevida.doevida.Utils.HttpListener;
import projeto.les.doevida.doevida.Utils.HttpUtils;
import projeto.les.doevida.doevida.Utils.MySharedPreferences;

public class LoginActivity extends AppCompatActivity {

    private Button btn_register;
    private Button btn_enter;
    private HttpUtils mHttp;
    private View mLoading;
    private EditText et_username;
    private EditText et_password;
    private MySharedPreferences mySharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mySharedPreferences = new MySharedPreferences(getApplicationContext());

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
                login(login, password);
            }
        });

    }

    private void login(String login, String password) {
        mLoading.setVisibility(View.VISIBLE);
        String urlCheckLogin = "http://doevida-grupoles.rhcloud.com/checkLogin";
        final JSONObject json = new JSONObject();

        try {
            json.put("login", login);
            json.put("pass", password);
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
                        Log.d("PASSOU AQUI", "ERRO");
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
                        mySharedPreferences.saveUser(name, birthDate, city, state, gender,
                                bloodType, lastDonation, login, pass);
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

}