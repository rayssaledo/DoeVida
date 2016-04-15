package projeto.les.doevida.doevida.views;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    private RelativeLayout mDrawerPane;
    private ActionBarDrawerToggle mDrawerToggle;
    private ArrayList<NavItem> mNavItems;
    private TextView nameUserTextView;
    private TextView loginUserTextView;
    private android.support.v7.app.ActionBar actionBar;

    private CharSequence mTitle;

    private String[] mPlanetTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;


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

        Button ask_all = (Button) findViewById(R.id.btn_ask_all);

        ask_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(DonorsActivity.this);
                dialog.setContentView(R.layout.dialog_choose_form);
                dialog.setTitle("Fazer pedido");
                dialog.setCancelable(true);

                final RadioButton rd1 = (RadioButton) dialog.findViewById(R.id.rd1);
                final RadioButton rd2 = (RadioButton) dialog.findViewById(R.id.rd2);

                rd1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rd1.setChecked(true);
                        rd2.setChecked(false);
                    }
                });
                rd2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rd2.setChecked(true);
                        rd1.setChecked(false);
                    }
                });
                dialog.show();
            }
        });

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

                final Dialog dialog = new Dialog(DonorsActivity.this);
                dialog.setContentView(R.layout.dialog_choose_form);
                dialog.setTitle("Fazer pedido");
                dialog.setCancelable(true);

                final RadioButton rd1 = (RadioButton) dialog.findViewById(R.id.rd1);
                final RadioButton rd2 = (RadioButton) dialog.findViewById(R.id.rd2);

                rd1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rd1.setChecked(true);
                        rd2.setChecked(false);
                    }
                });
                rd2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rd2.setChecked(true);
                        rd1.setChecked(false);
                    }
                });
                dialog.show();

            }
        });
        context = this;
        getListDonors();



    }

    public void setmDrawer(ArrayList<NavItem> mNavItems) {
        mNavItems.add(new NavItem("Doadores"));
        mNavItems.add(new NavItem("Informativos"));
        mNavItems.add(new NavItem("Quem precisa"));
        mNavItems.add(new NavItem("Meus pedidos"));
        mNavItems.add(new NavItem("Notificações"));
        mNavItems.add(new NavItem("Pediram-me"));
        mNavItems.add(new NavItem("Sair"));

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mDrawerPane = (RelativeLayout) findViewById(R.id.drawerPane);
        mDrawerList = (ListView) findViewById(R.id.navList);
        DrawerListAdapter adapter2 = new DrawerListAdapter(this, mNavItems);
        mDrawerList.setAdapter(adapter2);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) { // Doadores
                    mDrawerLayout.closeDrawer(mDrawerPane);
                    setView(DonorsActivity.this, DonorsActivity.class);
                } else if (position == 1) { // Informativos
                    mDrawerLayout.closeDrawer(mDrawerPane);
                    setView(DonorsActivity.this, InformationActivity.class);
                } else if (position == 2) { // quem precisa
//                    mDrawerLayout.closeDrawer(mDrawerPane);
//                    setView(DonorsActivity.this, UserCadastreActivity.class);
                } else if (position == 3) { // meus pedidos
//                    mDrawerLayout.closeDrawer(mDrawerPane);
//                    setView(DonorsActivity.this, UserCadastreActivity.class);
                } else if (position == 4) { // notificacoes
//                    mDrawerLayout.closeDrawer(mDrawerPane);
//                    setView(DonorsActivity.this, UserCadastreActivity.class);
                } else if (position == 5) { // Pediram-me
//                    mDrawerLayout.closeDrawer(mDrawerPane);
//                    setView(DonorsActivity.this, UserCadastreActivity.class);
                } else if (position == 6) { // sair
                    mDrawerLayout.closeDrawer(mDrawerPane);
                    userLogged.logoutUser();
//                  setView(DonorsActivity.this, UserCadastreActivity.class);
                }

            }
        });


        actionBar =  getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.options);
        actionBar.setHomeButtonEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mTitle = getTitle().toString();
        mDrawerToggle = new ActionBarDrawerToggle(
                DonorsActivity.this,
                mDrawerLayout,
                R.drawable.options,
                R.string.drawer_open,
                R.string.drawer_close) {

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        int id = item.getItemId();
        if (id == R.id.action_search){
            return true;
        }

        return super.onOptionsItemSelected(item);
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
