package projeto.les.doevida.doevida.views;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import projeto.les.doevida.doevida.R;
import projeto.les.doevida.doevida.adapters.DonorsAdapter;
import projeto.les.doevida.doevida.adapters.NotificationsAdapter;
import projeto.les.doevida.doevida.adapters.RequestedMeAdapter;
import projeto.les.doevida.doevida.models.Form;
import projeto.les.doevida.doevida.models.User;
import projeto.les.doevida.doevida.utils.HttpListener;
import projeto.les.doevida.doevida.utils.HttpUtils;
import projeto.les.doevida.doevida.utils.MySharedPreferences;

public class RequestedMeActivity extends AppCompatActivity {

    private ListView lv_requested_me;
    private RequestedMeAdapter adapter;
    private List<Form> myForms;
    private HttpUtils mHttp;
    private String myLogin;
    private HashMap<String, String> userDetails;

    private MySharedPreferences userLogged;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requested_me);

        mHttp = new HttpUtils(this);
        userLogged = new MySharedPreferences(getApplicationContext());
        userDetails = userLogged.getUserDetails();
        myLogin = userDetails.get(MySharedPreferences.KEY_USERNAME_USER);

        lv_requested_me = (ListView) findViewById(R.id.lv_requested_me);
      //  lv_requested_me.setAdapter(adapter);
        lv_requested_me.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        getRequestsMe();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }



    private void getRequestsMe() {
        String urlGetUser = "http://doevida-grupoles.rhcloud.com/getUser?login=" + myLogin;
        mHttp.get(urlGetUser, new HttpListener() {
            @Override
            public void onSucess(JSONObject response) throws JSONException {
                if (response.getInt("ok") == 1) {
                    JSONObject jsonUser = response.getJSONObject("result");
                    JSONArray jsonNotifications = jsonUser.getJSONArray("listMyNotifications");
                  //  JSONObject jsonForm = jsonNotifications.getJSONObject();
                  //listMyNotifications  userLogged.saveMyForms(jsonForm.toString());
                   // myForms = userLogged.getListRequests();
                    userLogged.saveMyForms(jsonNotifications.toString());

                    myForms = userLogged.getListMyForms();

                    adapter = new RequestedMeAdapter(RequestedMeActivity.this,myForms);
                    lv_requested_me.setAdapter(adapter);
                    Log.d("lista de requests", "CERTO!!" +
                            "");
                }
            }

            @Override
            public void onTimeout() {
                if (userLogged.getListRequests() != null) {
                    myForms = userLogged.getListMyForms();
                }
                if (myForms != null && myForms.size() == 0 || myForms == null) {
                    Log.d("Notifications", "Tamanho da lista é zero");
                    lv_requested_me.setVisibility(View.GONE);
                } else {
                    adapter = new RequestedMeAdapter(RequestedMeActivity.this, myForms);
                    lv_requested_me.setAdapter(adapter);
                }
            }
        });
    }



}
