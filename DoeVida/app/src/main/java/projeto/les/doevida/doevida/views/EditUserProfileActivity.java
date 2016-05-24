package projeto.les.doevida.doevida.views;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.HashMap;

import projeto.les.doevida.doevida.R;
import projeto.les.doevida.doevida.utils.MySharedPreferences;

public class EditUserProfileActivity extends AppCompatActivity {

    private EditText input_name_new;
    private EditText input_date_of_birth_new;
    private EditText input_city_new;
    private EditText input_state_new;
    private Spinner sp_gender_new;
    private Spinner sp_blood_type_new;
    private EditText input_date_of_last_donation_new;
    private EditText input_user_name_new;
    private EditText input_password_current;
    private EditText input_password_new;
    private EditText input_password_confirm_new;

    private MySharedPreferences userLogged;
    private HashMap<String, String> userDetails;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);

        context = this;
        userLogged = new MySharedPreferences(getApplicationContext());
        userDetails = userLogged.getUserDetails();

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
        input_user_name_new = (EditText) findViewById(R.id.input_user_name_new);
        input_user_name_new.setText(userDetails.get(MySharedPreferences.KEY_NAME_USER));
        input_password_current = (EditText) findViewById(R.id.input_password_current);
    }

}
