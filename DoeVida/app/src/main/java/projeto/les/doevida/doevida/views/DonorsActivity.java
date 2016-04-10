package projeto.les.doevida.doevida.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import projeto.les.doevida.doevida.R;
import projeto.les.doevida.doevida.Utils.HttpListener;
import projeto.les.doevida.doevida.Utils.HttpUtils;
import projeto.les.doevida.doevida.Utils.MySharedPreferences;
import projeto.les.doevida.doevida.adapters.DonorsAdapter;
import projeto.les.doevida.doevida.models.User;

public class DonorsActivity extends AppCompatActivity {

    private MySharedPreferences userLogged;
    private HashMap<String, String> userDetails;
    private ListView listViewDonors;
    private HttpUtils mHttp;
    private DonorsAdapter adapter;
    private List<User> listDonors;
    private User user;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donors);

        userLogged = new MySharedPreferences(getApplicationContext());
        userDetails = userLogged.getUserDetails();

        String name = userDetails.get(MySharedPreferences.KEY_NAME_USER);

        Log.d("TESTE", "Nome: " + name);

        mHttp = new HttpUtils(this);
        listViewDonors = (ListView) findViewById(R.id.lv_donors);
        listViewDonors.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /* Modificar depois, fiz só para testar
                User donor = (User) adapter.getItem(position);
                Intent intent = new Intent(DonorsActivity.this, UserCadastreActivity.class);
                intent.putExtra("DONOR", donor);
                startActivity(intent);
                */
            }
        });
        context = this;
        getListDonors();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getListDonors();

    }

    private void getListDonors() {
        String urlGetAllUsers = "http://doevida-grupoles.rhcloud.com/getAllUsers";
        mHttp.get(urlGetAllUsers, new HttpListener() {
            @Override
            public void onSucess(JSONObject response) throws JSONException {
                listViewDonors.setVisibility(View.VISIBLE);
                if (response.getInt("ok") == 1) {
                    JSONArray jsonArray = response.getJSONArray("result");
                    userLogged.saveListDonors(jsonArray.toString());
                    listDonors = userLogged.getListDonors();
                    if (listDonors.size() == 0) {
                        listViewDonors.setVisibility(View.GONE);
                    }
                    adapter = new DonorsAdapter(DonorsActivity.this, listDonors);
                    listViewDonors.setAdapter(adapter);
                    Log.d("PASSOU AQUI", "CERTO!!" +
                            "");
                }
            }

            @Override
            public void onTimeout() {
                if (userLogged.getListDonors() != null) {
                    listDonors = userLogged.getListDonors();
                }
                if (listDonors != null && listDonors.size() == 0 || listDonors == null) {
                    Log.d("DONORS", "Tamanho da lista é zero");
                    listViewDonors.setVisibility(View.GONE);
                } else {
                    adapter = new DonorsAdapter(context, listDonors);
                    listViewDonors.setAdapter(adapter);
                }
            }
        });
    }

}
