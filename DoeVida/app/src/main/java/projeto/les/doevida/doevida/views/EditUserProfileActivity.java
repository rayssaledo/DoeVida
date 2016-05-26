package projeto.les.doevida.doevida.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import projeto.les.doevida.doevida.R;
import projeto.les.doevida.doevida.models.User;
import projeto.les.doevida.doevida.utils.HttpListener;
import projeto.les.doevida.doevida.utils.HttpUtils;
import projeto.les.doevida.doevida.utils.MySharedPreferences;

public class EditUserProfileActivity extends AppCompatActivity {

    private EditText input_name_new;
    private EditText input_date_of_birth_new;
    private EditText input_city_new;
    private EditText input_state_new;
    private Spinner sp_gender_new;
    private Spinner sp_blood_type_new;
    private EditText input_date_of_last_donation_new;
    private EditText input_password_current;
    private EditText input_password_new;
    private EditText input_password_confirm_new;
    private Button btn_save;
    private List<String> genders;
    private List<String> mTypes;
    String mGender_user;
    String mBlood_Type_user;
    private AlertDialog.Builder alert;

    private MySharedPreferences userLogged;
    private HashMap<String, String> userDetails;
    Context context;
    private HttpUtils mHttp;
    private String password_new;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);

        context = this;
        userLogged = new MySharedPreferences(getApplicationContext());
        userDetails = userLogged.getUserDetails();
        mHttp = new HttpUtils(this);

        input_name_new = (EditText) findViewById(R.id.input_name_new);
        input_name_new.setText(userDetails.get(MySharedPreferences.KEY_NAME_USER));
        input_date_of_birth_new = (EditText) findViewById(R.id.input_date_of_birth_new);
        input_date_of_birth_new.setText(userDetails.get(MySharedPreferences.KEY_DATE_BIRTH_USER));
        input_city_new = (EditText) findViewById(R.id.input_city_new);
        input_city_new.setText(userDetails.get(MySharedPreferences.KEY_CITY_USER));
        input_state_new = (EditText) findViewById(R.id.input_state_new);
        input_state_new.setText(userDetails.get(MySharedPreferences.KEY_STATE_USER));
        sp_gender_new = (Spinner) findViewById(R.id.sp_gender_new);
        sp_blood_type_new = (Spinner) findViewById(R.id.sp_blood_type_new);
        input_date_of_last_donation_new = (EditText) findViewById(R.id.input_date_of_last_donation_new);
        input_date_of_last_donation_new.setText(userDetails.get(MySharedPreferences.KEY_DATE_DONATION_USER));
        input_password_current = (EditText) findViewById(R.id.input_password_current);
        input_password_new = (EditText) findViewById(R.id.input_password_new);
        input_password_confirm_new = (EditText) findViewById(R.id.input_password_confirm_new);
        btn_save = (Button) findViewById(R.id.btn_save);

        MaskEditTextChangedListener maskDateOfBirth = new MaskEditTextChangedListener("##/##/####", input_date_of_birth_new);
        MaskEditTextChangedListener maskDateOfLastDonation = new MaskEditTextChangedListener("##/##/####", input_date_of_last_donation_new);

        input_date_of_birth_new.addTextChangedListener(maskDateOfBirth);
        input_date_of_last_donation_new.addTextChangedListener(maskDateOfLastDonation);

        putGenderElementsOnSpinnerArray();
        ArrayAdapter<String> spinnerArrayAdapterGenders = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, genders);
        spinnerArrayAdapterGenders.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        sp_gender_new.setAdapter(spinnerArrayAdapterGenders);

        sp_gender_new.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(final AdapterView<?> parent, final View view, final int pos, final long id) {
                Object item = parent.getItemAtPosition(pos);
                String itemGender = item.toString();

                if (itemGender.equals(getApplicationContext().getResources().getString(R.string.female))) {
                    mGender_user = "F";
                } else if (itemGender.equals(getApplicationContext().getResources().getString(R.string.male))) {
                    mGender_user = "M";
                }
            }

            public void onNothingSelected(final AdapterView<?> parent) {
            }
        });

        putBloodTypeElementsOnSpinnerArray();
        ArrayAdapter<String> spinnerArrayAdapterBloodTypes = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mTypes);
        spinnerArrayAdapterBloodTypes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        sp_blood_type_new.setAdapter(spinnerArrayAdapterBloodTypes);

        sp_blood_type_new.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(final AdapterView<?> parent, final View view, final int pos, final long id) {
                Object item = parent.getItemAtPosition(pos);
                mBlood_Type_user = item.toString();
            }

            public void onNothingSelected(final AdapterView<?> parent) {
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = input_name_new.getText().toString();
                String date_of_birth = input_date_of_birth_new.getText().toString();
                String city = input_city_new.getText().toString();
                String state = input_state_new.getText().toString();
                String gender = mGender_user;
                String bloodType = mBlood_Type_user;
                String date_of_last_donation = input_date_of_last_donation_new.getText().toString();
                String user_name = userDetails.get(MySharedPreferences.KEY_USERNAME_USER);
                String password_current = input_password_current.getText().toString();
                password_new = userDetails.get(MySharedPreferences.KEY_PASSWORD_USER);
                String password_new_tv = input_password_new.getText().toString();
                String password_confirm_new = input_password_confirm_new.getText().toString();

                if (!validateName(name)) {
                    showDialog("Nome inválido!");
                    input_name_new.setText("");
                } else if (!validateBirthdate(date_of_birth)) {
                    showDialog("Data de nascimento inválida!");
                    input_date_of_birth_new.setText("");
                } else if (!validateCity(city)) {
                    showDialog("Nome de cidade inválida!");
                    input_city_new.setText("");
                } else if (!validateState(state)) {
                    showDialog("Nome de estado inválido!");
                    input_state_new.setText("");
                } else if (!validateLastDonation(date_of_birth, date_of_last_donation)) {
                    showDialog("Data da última doação inválida!");
                    input_date_of_last_donation_new.setText("");
                } else if (!validatePasswords(password_current, password_new_tv, password_confirm_new)) {
                    input_password_new.setText("");
                    input_password_confirm_new.setText("");
                } else {
                    String url = "http://doevida-grupoles.rhcloud.com/updateUser";
                    JSONObject json = new JSONObject();

                    try {
                        if (password_confirm_new != null && !password_confirm_new.equals("")) {
                            password_new = password_confirm_new;
                        }
                        json.put("login", user_name);
                        json.put("pass", password_new);
                        json.put("name", name);
                        json.put("state", state);
                        json.put("city", city);
                        json.put("birthDate", date_of_birth);
                        json.put("gender",gender);
                        json.put("bloodType", bloodType);
                        json.put("lastDonation", date_of_last_donation);
                        json.put("canDonate", true);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mHttp.post(url, json.toString(), new HttpListener() {
                        @Override
                        public void onSucess(JSONObject result) throws JSONException {
                            if (result.getInt("ok") == 0) {
                                new AlertDialog.Builder(EditUserProfileActivity.this)
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
                                new AlertDialog.Builder(EditUserProfileActivity.this)
                                        .setTitle("Sucesso")
                                        .setMessage(result.getString("msg"))
                                        .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                //mLoading.setVisibility(View.GONE);
                                            }
                                        })
                                        .create()
                                        .show();
                                setView(EditUserProfileActivity.this, UserProfileActivity.class);
                            }
                        }

                        @Override
                        public void onTimeout() {
                            new AlertDialog.Builder(EditUserProfileActivity.this)
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

            }

        });
    }

    public void setView(Context context, Class classe){
        Intent it = new Intent();
        it.setClass(context, classe);
        startActivity(it);
    }

    private boolean validateName(String name) {
        if(name == null || name.equals("")){
            return false;
        } return true;
    }

    private boolean validateBirthdate (String birthdate) {
        if (birthdate == null) {
            return false;
        } return true;
    }

    private boolean validateCity(String city) {
        if(city == null || city.equals("")){
            return false;
        } return true;
    }

    private boolean validateState(String state) {
        if(state == null || state.equals("")){
            return false;
        } return true;
    }

    private boolean validateLastDonation (String birthdate, String lastDonation) {
        if(birthdate != null && lastDonation != null && birthdate.equals(lastDonation)){
            return false;
        } return true;
    }

    private  boolean validatePasswords(String passwordCurrent, String password, String passwordConfirm){
        if (passwordCurrent != null && !passwordCurrent.equals("")) {
            if (!passwordCurrent.equals(userDetails.get(MySharedPreferences.KEY_PASSWORD_USER))) {
                showDialog("Senha atual não coincide!");
                return false;
            }
            if(password == null || password.equals("")){
                showDialog("Nova senha não informada!");
                return false;
            }
            if(passwordConfirm == null || passwordConfirm.equals("")){
                showDialog("Confirme a senha!");
                return false;
            }
            if (!password.equals(passwordConfirm)){
                showDialog("Senhas não coincidem!");
                return false;
            }
        } else if(password != null && !password.equals("")) {
            showDialog("Digite a senha atual!");
            return false;
        } else  if(passwordConfirm != null && !passwordConfirm.equals("")) {
            showDialog("Digite a senha atual!");
            return false;
        }
        return true;
    }

    public void putGenderElementsOnSpinnerArray() {
        genders = new ArrayList<>();
        genders.add(getApplicationContext().getResources().getString(R.string.female));
        genders.add(getApplicationContext().getResources().getString(R.string.male));
    }

    public void putBloodTypeElementsOnSpinnerArray(){
        mTypes = new ArrayList<>();
        mTypes.add(getApplicationContext().getResources().getString(R.string.a_positivo));
        mTypes.add(getApplicationContext().getResources().getString(R.string.a_negativo));
        mTypes.add(getApplicationContext().getResources().getString(R.string.b_positivo));
        mTypes.add(getApplicationContext().getResources().getString(R.string.b_negativo));
        mTypes.add(getApplicationContext().getResources().getString(R.string.ab_positivo));
        mTypes.add(getApplicationContext().getResources().getString(R.string.ab_negativo));
        mTypes.add(getApplicationContext().getResources().getString(R.string.o_positivo));
        mTypes.add(getApplicationContext().getResources().getString(R.string.o_negativo));
    }

    public void showDialog(String message) {
        alert = new AlertDialog.Builder(context);
        alert.setPositiveButton("OK", null);
        alert.setMessage(message);
        alert.create().show();
    }
}
