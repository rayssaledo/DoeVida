package projeto.les.doevida.doevida.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import projeto.les.doevida.doevida.R;
import projeto.les.doevida.doevida.Utils.HttpListener;
import projeto.les.doevida.doevida.Utils.HttpUtils;
import projeto.les.doevida.doevida.Utils.MySharedPreferences;
import projeto.les.doevida.doevida.models.User;

public class UserCadastreActivity extends AppCompatActivity {

    private EditText mName;
    private EditText mDate_of_birth;
    private EditText mCity;
    private EditText mState;
    private EditText mUsername;
    private EditText mPassword;
    private EditText mPasswordConfirm;
    private EditText mDate_of_last_donation;
    private Spinner mGenders_spinner;
    private Spinner mBlood_types_spinner;
    private Button bt_cadastre;
    private TextInputLayout layout_name;
    private TextInputLayout layout_date_birth;
    private TextInputLayout layout_city;
    private TextInputLayout layout_state;
    private TextInputLayout layout_date_donation;
    private TextInputLayout layout_username;
    private TextInputLayout layout_password;
    private TextInputLayout layout_password_confirm;

    private List<String> genders;
    private List<String> mTypes;
    String mGender_user;
    String mBlood_Type_user;
    private String name_user;
    private String date_birth_user;
    private String city_user;
    private String state_user;
    private String date_donation_user;
    private String username_user;
    private String password_user;
    private String password_confirm_user;

    private MySharedPreferences mySharedPreferences;
    private HttpUtils mHttp;
    private User donor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cadastre);

        /* Modificar depois, fiz só para testar
        Intent it = getIntent();
        donor = (User) it.getSerializableExtra("DONOR");*/

        mySharedPreferences = new MySharedPreferences(getApplicationContext());
        mHttp = new HttpUtils(this);

        mName = (EditText) findViewById(R.id.input_name);
        mDate_of_birth = (EditText) findViewById(R.id.input_date_of_birth);
        mCity = (EditText) findViewById(R.id.input_city);
        mState = (EditText) findViewById(R.id.input_state);
        mUsername = (EditText) findViewById(R.id.input_user_name);
        mPassword = (EditText) findViewById(R.id.input_password);
        mPasswordConfirm = (EditText) findViewById(R.id.input_password_confirm);
        mDate_of_last_donation = (EditText) findViewById(R.id.input_date_of_last_donation);
        mGenders_spinner = (Spinner) findViewById(R.id.sp_gender);
        mBlood_types_spinner = (Spinner) findViewById(R.id.sp_blood_type);
        bt_cadastre = (Button) findViewById(R.id.btn_cadastre);
        layout_name = (TextInputLayout) findViewById(R.id.input_layout_name);
        layout_date_birth = (TextInputLayout) findViewById(R.id.input_layout_date_of_birth);
        layout_city = (TextInputLayout) findViewById(R.id.input_layout_city);
        layout_state = (TextInputLayout) findViewById(R.id.input_layout_state);
        layout_name = (TextInputLayout) findViewById(R.id.input_layout_name);
        layout_username = (TextInputLayout) findViewById(R.id.input_layout_user_name);
        layout_password = (TextInputLayout) findViewById(R.id.input_layout_password);
        layout_password_confirm = (TextInputLayout) findViewById(R.id.input_layout_password_confirm);


        MaskEditTextChangedListener maskDateOfBirth = new MaskEditTextChangedListener("##/##/####", mDate_of_birth);
        MaskEditTextChangedListener maskDateOfLastDonation = new MaskEditTextChangedListener("##/##/####", mDate_of_last_donation);

        mDate_of_birth.addTextChangedListener(maskDateOfBirth);
        mDate_of_last_donation.addTextChangedListener(maskDateOfLastDonation);

        putGenderElementsOnSpinnerArray();
        ArrayAdapter<String> spinnerArrayAdapterGenders = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, genders);
        spinnerArrayAdapterGenders.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        mGenders_spinner.setAdapter(spinnerArrayAdapterGenders);

        mGenders_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        mBlood_types_spinner.setAdapter(spinnerArrayAdapterBloodTypes);

        mBlood_types_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(final AdapterView<?> parent, final View view, final int pos, final long id) {
                Object item = parent.getItemAtPosition(pos);
                mBlood_Type_user = item.toString();
            }

            public void onNothingSelected(final AdapterView<?> parent) {
            }
        });

        bt_cadastre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name_user = mName.getText().toString();
                date_birth_user = mDate_of_birth.getText().toString();
                city_user = mCity.getText().toString();
                state_user = mState.getText().toString();
                date_donation_user = mDate_of_last_donation.getText().toString();
                username_user = mUsername.getText().toString();
                password_user = mPassword.getText().toString();
                password_confirm_user = mPasswordConfirm.getText().toString();

                if (validateName() && validateDateOfBirth() && validateCity() && validateState() &&
                        validateUsername() && validatePassword() && validatePasswordConfirm() && confirmationPassword()){

                    cadastreUser(name_user,date_birth_user, city_user, state_user, date_donation_user,
                            username_user, password_user, mGender_user, mBlood_Type_user);
                } else if (!validateName()){
                    return;
                } else if (!validateDateOfBirth()){
                    return;
                } else if (!validateCity()){
                    return;
                } else if (!validateState()){
                    return;
                } else if (!validateUsername()){
                    return;
                } else if (!validatePassword()){
                    return;
                } else if (!validatePasswordConfirm()){
                    return;
                } else if (!confirmationPassword()){
                    new AlertDialog.Builder(UserCadastreActivity.this)
                            .setMessage("As senhas não coincidem!")
                            .setNeutralButton("OK", new DialogInterface.OnClickListener(){
                                public void onClick(DialogInterface arg0, int arg1) {
                                    mPassword.setText("");
                                    mPasswordConfirm.setText("");
                                    requestFocus(mPassword);
                                }
                            })
                            .create()
                            .show();
                }
            }

        });
    }

    public void cadastreUser(final String name_user, final String date_birth_user, final String city_user,
                             final String state_user, final String date_donation_user,
                             final String username_user, final String password_user, final String
                                     gender_user, final String blood_type_user){

        String url = "http://doevida-grupoles.rhcloud.com/addUser";
        JSONObject json = new JSONObject();
        try {
            json.put("login", username_user);
            json.put("pass", password_user);
            json.put("name", name_user);
            json.put("state", state_user);
            json.put("city", city_user);
            json.put("birthDate", date_birth_user);
            json.put("gender", gender_user);
            json.put("bloodType", blood_type_user);
            json.put("lastDonation", date_donation_user);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mHttp.post(url, json.toString(), new HttpListener() {
            @Override
            public void onSucess(JSONObject result) throws JSONException{
                if (result.getInt("ok") == 0) {
                    new AlertDialog.Builder(UserCadastreActivity.this)
                            .setTitle("Erro")
                            .setMessage(result.getString("msg"))
                            .setNeutralButton("OK", null)
                            .create()
                            .show();
                } else {
                    new AlertDialog.Builder(UserCadastreActivity.this)
                            .setMessage("Cadastro realizado com sucesso")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();
                                    finish();
                                    mySharedPreferences.saveUser(name_user, date_birth_user, city_user, state_user,
                                            date_donation_user, username_user, password_user, mGender_user, mBlood_Type_user);
                                    setView(UserCadastreActivity.this, DonorsActivity.class);
                                }
                            })
                            .create()
                            .show();
                }
            }
            @Override
            public void onTimeout() {
                new AlertDialog.Builder(UserCadastreActivity.this)
                        .setTitle("Erro")
                        .setMessage("Conexão não disponível")
                        .setNeutralButton("OK", null)
                        .create()
                        .show();
            }
        });
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

    private boolean validateName(){
        if (name_user.trim().isEmpty() || name_user == null) {
            layout_name.setError(getString(R.string.err_msg_name));
            requestFocus(mName);
            return false;
        } else if (name_user.trim().length() < 10){
            layout_name.setError(getString(R.string.err_short_name));
            requestFocus(mName);
            return false;
        } else {
            layout_name.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateDateOfBirth(){
        if (date_birth_user.trim().isEmpty()) {
            layout_date_birth.setError(getString(R.string.err_msg_birth));
            requestFocus(mDate_of_birth);
            return false;
        } else if (date_birth_user.trim().length() != 10){
            layout_date_birth.setError(getString(R.string.err_invalid_birth));
            requestFocus(mDate_of_birth);
            return false;
        } else {
            layout_date_birth.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateCity(){
        if (city_user.trim().isEmpty() || city_user == null) {
            layout_city.setError(getString(R.string.err_msg_city));
            requestFocus(mCity);
            return false;
        } else if (city_user.trim().length() < 4){
            layout_city.setError(getString(R.string.err_short_city_name));
            requestFocus(mCity);
            return false;
        } else {
            layout_city.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateState(){
        if (state_user.trim().isEmpty()) {
            layout_state.setError(getString(R.string.err_msg_state));
            requestFocus(mState);
            return false;
        } else if (state_user.trim().length() < 4) {
            layout_state.setError(getString(R.string.err_msg_state));
            requestFocus(mState);
            return false;
        } else {
            layout_state.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateUsername(){
        if (username_user.trim().isEmpty()) {
            layout_username.setError(getString(R.string.err_msg_username));
            requestFocus(mUsername);
            return false;
        } else if (username_user.trim().length() < 5) {
            layout_username.setError(getString(R.string.err_short_username));
            requestFocus(mUsername);
            return false;
        } else{
            layout_username.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validatePassword(){
        if (password_user.trim().isEmpty()) {
            layout_password.setError(getString(R.string.err_msg_password));
            requestFocus(mPassword);
            return false;
        } else if (password_user.trim().length() < 6){
            layout_password.setError(getString(R.string.err_short_password));
            requestFocus(mPassword);
            return false;
        } else {
            layout_password.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validatePasswordConfirm(){
        if (password_confirm_user.trim().isEmpty()) {
            layout_password_confirm.setError(getString(R.string.err_msg_password_confirm));
            requestFocus(mPasswordConfirm);
            return false;
        } else if (password_confirm_user.trim().length() < 6){
            layout_password_confirm.setError(getString(R.string.err_short_password));
            requestFocus(mPasswordConfirm);
            return false;
        } else {
            layout_password_confirm.setErrorEnabled(false);
        }
        return true;
    }

    private boolean confirmationPassword(){
        if (!password_user.trim().equals(password_confirm_user)){
            return false;
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public void setView(Context context, Class classe) {
        Intent it = new Intent();
        it.setClass(context, classe);
        startActivity(it);
    }
}
