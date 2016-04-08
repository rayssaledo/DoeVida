package projeto.les.doevida.doevida.views;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import android.util.Log;

import java.util.HashMap;

import projeto.les.doevida.doevida.R;
import projeto.les.doevida.doevida.Utils.MySharedPreferences;

public class DonorsActivity extends AppCompatActivity {

    private MySharedPreferences userLogged;
    private HashMap<String, String> userDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donors);

        userLogged = new MySharedPreferences(getApplicationContext());
        userDetails = userLogged.getUserDetails();

        String name = userDetails.get(MySharedPreferences.KEY_NAME_USER);

        Log.d("TESTE", "Nome: " + name);

    }
}
