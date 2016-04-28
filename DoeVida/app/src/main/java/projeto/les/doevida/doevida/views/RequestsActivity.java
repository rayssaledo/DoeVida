package projeto.les.doevida.doevida.views;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import projeto.les.doevida.doevida.R;
import projeto.les.doevida.doevida.adapters.NotificationsAdapter;
import projeto.les.doevida.doevida.adapters.RequestedMeAdapter;
import projeto.les.doevida.doevida.adapters.RequestsAdapter;
import projeto.les.doevida.doevida.models.Form;
import projeto.les.doevida.doevida.models.User;
import projeto.les.doevida.doevida.utils.HttpListener;
import projeto.les.doevida.doevida.utils.HttpUtils;
import projeto.les.doevida.doevida.utils.MySharedPreferences;

public class RequestsActivity extends AppCompatActivity {

    private ListView listViewRequests;
    private MySharedPreferences userLogged;
    private RequestsAdapter adapter;
    private List<Form> listAllforms;
    private HttpUtils mHttp;
    private List<User> listAllUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);

        mHttp = new HttpUtils(this);
        listAllforms = new ArrayList<>();
        listViewRequests = (ListView) findViewById(R.id.lv_requests);
        userLogged = new MySharedPreferences(getApplicationContext());

        getAllForms();

    }


    private void getAllForms() {
        String url = "http://doevida-grupoles.rhcloud.com/getAllUsers";
        mHttp.get(url, new HttpListener() {
            @Override
            public void onSucess(JSONObject response) throws JSONException {
                if (response.getInt("ok") == 1) {
                    JSONArray jsonArray = response.getJSONArray("result");
                    userLogged.saveListDonors(jsonArray.toString());
                    listAllUsers = userLogged.getListDonors();
                    for (User user : listAllUsers){
                        for (Form form : user.getForms()){
                            listAllforms.add(form);
                        }
                    }
                    adapter = new RequestsAdapter(RequestsActivity.this, listAllforms);
                    listViewRequests.setAdapter(adapter);
                }
            }

            @Override
            public void onTimeout() {

            }
        });
    }



}
