package projeto.les.doevida.doevida.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import projeto.les.doevida.doevida.R;

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

    private List<String> genders;
    private List<String> mTypes;
    String mGender_user;
    String mBlood_Type_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cadastre);

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
                String itemBloodType = item.toString();

                mBlood_Type_user = itemBloodType;
            }

            public void onNothingSelected(final AdapterView<?> parent) {
            }
        });

        bt_cadastre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name_user = mName.getText().toString();
                final String date_birth_user = mDate_of_birth.getText().toString();
                final String city_user = mCity.getText().toString();
                final String state_user = mState.getText().toString();
                final String date_donation_user = mDate_of_last_donation.getText().toString();
                final String username_user = mUsername.getText().toString();
                final String password_user = mPassword.getText().toString();
                final String password_confirm_user = mPasswordConfirm.getText().toString();

                //cadastreUser(name_user,date_birth_user, city_user, state_user, date_donation_user,
                       // username_user, password_user);

                Log.d("CADASTRE_TEST", "Name: " + name_user);
                Log.d("CADASTRE_TEST", "Date birth: " + date_birth_user);
                Log.d("CADASTRE_TEST", "City: " + city_user);
                Log.d("CADASTRE_TEST", "State: " + state_user);
                Log.d("CADASTRE_TEST", "Last donation: " + date_donation_user);
                Log.d("CADASTRE_TEST", "Username: " + username_user);
                Log.d("CADASTRE_TEST", "Name: " + name_user);
                Log.d("CADASTRE_TEST", "Password: " + password_user);
                Log.d("CADASTRE_TEST", "Confirm password: " + password_confirm_user);
                Log.d("CADASTRE_TEST", "Gender: " + mGender_user);
                Log.d("CADASTRE_TEST", "Blood type: " + mBlood_Type_user);

            }


        });


    }

    public void cadastreUser(final String name_user, final String date_birth_user, final String city_user,
                             final String state_user, final String date_donation_user,
                             final String username_user, final String password_user){

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

}
