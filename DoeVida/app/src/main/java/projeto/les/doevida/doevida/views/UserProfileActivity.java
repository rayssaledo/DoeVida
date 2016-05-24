package projeto.les.doevida.doevida.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.HashMap;

import projeto.les.doevida.doevida.R;
import projeto.les.doevida.doevida.utils.MySharedPreferences;

public class UserProfileActivity extends AppCompatActivity {

    private TextView numberDonates;
    private TextView name;
    private TextView userName;
    private TextView dateOfBirth;
    private TextView city;
    private TextView gender;
    private TextView bloodType;
    private TextView dateOfLastDonation;
    private ImageView donates;
    private Button btn_change_data;

    private MySharedPreferences userLogged;
    private HashMap<String, String> userDetails;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        context = this;
        userLogged = new MySharedPreferences(getApplicationContext());
        userDetails = userLogged.getUserDetails();

        donates = (ImageView) findViewById(R.id.iv_donates);
        Bitmap imageBlood = BitmapFactory.decodeResource(context.getResources(), R.mipmap.blood);
        donates.setImageBitmap(imageBlood);

        numberDonates = (TextView) findViewById(R.id.tv_number_donates);

        name = (TextView) findViewById(R.id.tv_input_name);
        name.setText(userDetails.get(MySharedPreferences.KEY_NAME_USER));
        userName = (TextView) findViewById(R.id.tv_input_user_name);
        userName.setText(userDetails.get(MySharedPreferences.KEY_USERNAME_USER));
        dateOfBirth = (TextView) findViewById(R.id.tv_input_date_of_birth);
        dateOfBirth.setText(userDetails.get(MySharedPreferences.KEY_DATE_BIRTH_USER));
        city = (TextView) findViewById(R.id.tv_city_name);
        city.setText(userDetails.get(MySharedPreferences.KEY_CITY_USER));
        gender = (TextView) findViewById(R.id.tv_input_gender);
        bloodType = (TextView) findViewById(R.id.tv_input_blood_type);
        bloodType.setText(userDetails.get(MySharedPreferences.KEY_BLOOD_TYPE_USER));
        dateOfLastDonation = (TextView) findViewById(R.id.tv_input_date_of_last_donation);
        dateOfLastDonation.setText(userDetails.get(MySharedPreferences.KEY_DATE_DONATION_USER));
        btn_change_data = (Button) findViewById(R.id.btn_change_data);
        btn_change_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setView(UserProfileActivity.this, EditUserProfileActivity.class);
            }
        });

        if(userDetails.get(MySharedPreferences.KEY_GENDER_USER).equals("F")) {
            gender.setText("Feminino");
        } else if (userDetails.get(MySharedPreferences.KEY_GENDER_USER).equals("M")) {
            gender.setText("Masculino");
        }
    }

    public void setView(Context context, Class classe){
        Intent it = new Intent();
        it.setClass(context, classe);
        startActivity(it);
    }
}
