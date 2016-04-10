package projeto.les.doevida.doevida.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.os.Parcelable;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import projeto.les.doevida.doevida.R;
import projeto.les.doevida.doevida.Utils.HttpListener;
import projeto.les.doevida.doevida.Utils.HttpUtils;
import projeto.les.doevida.doevida.Utils.MySharedPreferences;
import projeto.les.doevida.doevida.adapters.DonorsAdapter;
import projeto.les.doevida.doevida.adapters.DrawerListAdapter;
import projeto.les.doevida.doevida.models.NavItem;
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

    private ListView mDrawerList;
    private RelativeLayout mDrawerPane;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private ArrayList<NavItem> mNavItems;
    private TextView nameUserTextView;
    private TextView loginUserTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donors);

        userLogged = new MySharedPreferences(getApplicationContext());
        userDetails = userLogged.getUserDetails();

        String name = userDetails.get(MySharedPreferences.KEY_NAME_USER);
        String username = userDetails.get(MySharedPreferences.KEY_USERNAME_USER);

        Log.d("TESTE", "Nome: " + name);

        nameUserTextView = (TextView) findViewById(R.id.nameUser);
        nameUserTextView.setText(name);

        loginUserTextView = (TextView) findViewById(R.id.login);
        loginUserTextView.setText(username);



        mNavItems = new ArrayList<>();
        setmDrawer(mNavItems);

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

    public void setmDrawer(ArrayList<NavItem> mNavItems){
        mNavItems.add(new NavItem("Informativos"));
        mNavItems.add(new NavItem("Doadores"));
        mNavItems.add(new NavItem("Quem precisa"));
        mNavItems.add(new NavItem("Meus pedidos"));
        mNavItems.add(new NavItem("Notificações"));
        mNavItems.add(new NavItem("Minhas declarações"));
        mNavItems.add(new NavItem("Sair"));

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mDrawerPane = (RelativeLayout) findViewById(R.id.drawerPane);
        mDrawerList = (ListView) findViewById(R.id.navList);
        DrawerListAdapter adapter2 = new DrawerListAdapter(this, mNavItems);
        mDrawerList.setAdapter(adapter2);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) { // informativos
//                    mDrawerLayout.closeDrawer(mDrawerPane);
//                    setView(DonorsActivity.this, LoginActivity.class);
                } else if (position == 1){ // doadores
                    mDrawerLayout.closeDrawer(mDrawerPane);
                    setView(DonorsActivity.this, DonorsActivity.class);
                } else if (position == 2){ // quem precisa
//                    mDrawerLayout.closeDrawer(mDrawerPane);
//                    setView(DonorsActivity.this, UserCadastreActivity.class);
                } else if (position == 3){ // meus pedidos
//                    mDrawerLayout.closeDrawer(mDrawerPane);
//                    setView(DonorsActivity.this, UserCadastreActivity.class);
                } else if (position == 4){ // notificacoes
//                    mDrawerLayout.closeDrawer(mDrawerPane);
//                    setView(DonorsActivity.this, UserCadastreActivity.class);
                } else if (position == 5){ // minhas declaracoes
//                    mDrawerLayout.closeDrawer(mDrawerPane);
//                    setView(DonorsActivity.this, UserCadastreActivity.class);
                } else if (position == 6){ // sair
//                    mDrawerLayout.closeDrawer(mDrawerPane);
//                    setView(DonorsActivity.this, UserCadastreActivity.class);
                }

            }
        });
    }
    public void setView(Context context, Class classe){
        Intent it = new Intent();
        it.setClass(context, classe);
        startActivity(it);
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
