package projeto.les.doevida.doevida.views;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import projeto.les.doevida.doevida.R;
import projeto.les.doevida.doevida.adapters.DrawerListAdapter;
import projeto.les.doevida.doevida.adapters.RequestedMeAdapter;
import projeto.les.doevida.doevida.models.Form;
import projeto.les.doevida.doevida.models.NavItem;
import projeto.les.doevida.doevida.utils.HttpUtils;
import projeto.les.doevida.doevida.utils.MySharedPreferences;

public class RequestedMeActivity extends AppCompatActivity {

    private ListView lv_requested_me;
    private RequestedMeAdapter adapter;
    private List<Form> requests_accepeted;
    private HttpUtils mHttp;
    private String myLogin;
    private MySharedPreferences userLogged;
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
    private HashMap<String, String> userDetails;
    private String loginUserLogged;


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
        userDetails = userLogged.getUserDetails();
        String name = userDetails.get(MySharedPreferences.KEY_NAME_USER);
        loginUserLogged = userDetails.get(MySharedPreferences.KEY_USERNAME_USER);

        nameUserTextView = (TextView) findViewById(R.id.nameUser);
        nameUserTextView.setText(name);
        loginUserTextView = (TextView) findViewById(R.id.login);
        loginUserTextView.setText(loginUserLogged);
        mNavItems = new ArrayList<>();
        setmDrawer(mNavItems);
        mNavItems = new ArrayList<>();
        setmDrawer(mNavItems);

        requests_accepeted = userLogged.getListRequestsAccepted();

        adapter = new RequestedMeAdapter(RequestedMeActivity.this, requests_accepeted);
        lv_requested_me.setAdapter(adapter);


    }

    public void setmDrawer(ArrayList<NavItem> mNavItems) {
        mNavItems.add(new NavItem("Doadores", R.mipmap.ic_donors));
        mNavItems.add(new NavItem("Informativos", R.mipmap.ic_informations));
        mNavItems.add(new NavItem("Quem precisa", R.mipmap.ic_needing));
        mNavItems.add(new NavItem("Meus pedidos", R.mipmap.ic_my_forms));
        mNavItems.add(new NavItem("Notificações", R.mipmap.ic_notifications));
        mNavItems.add(new NavItem("Pediram-me", R.mipmap.ic_asked));
        mNavItems.add(new NavItem("Sair", R.mipmap.ic_logout));

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
                    setView(RequestedMeActivity.this, DonorsActivity.class);
                    finish();
                } else if (position == 1) { // Informativos
                    mDrawerLayout.closeDrawer(mDrawerPane);
                    setView(RequestedMeActivity.this, InformationActivity.class);
                    finish();
                } else if (position == 2) { // quem precisa
                    mDrawerLayout.closeDrawer(mDrawerPane);
                    setView(RequestedMeActivity.this, RequestsActivity.class);
                    finish();
                } else if (position == 3) { // meus pedidos
                    mDrawerLayout.closeDrawer(mDrawerPane);
                    setView(RequestedMeActivity.this, MyRequestsActivity.class);
                    finish();
                } else if (position == 4) { // notificacoes
                    mDrawerLayout.closeDrawer(mDrawerPane);
                    setView(RequestedMeActivity.this, NotificationsActivity.class);
                    finish();
                } else if (position == 5) { // Pediram-me
                    mDrawerLayout.closeDrawer(mDrawerPane);
                    setView(RequestedMeActivity.this, RequestedMeActivity.class);
                    finish();
                } else if (position == 6) { // sair
                    mDrawerLayout.closeDrawer(mDrawerPane);
                    userLogged.logoutUser();
                    finish();
                }

            }
        });


        actionBar =  getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu_white_24dp);
        actionBar.setHomeButtonEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mTitle = getTitle().toString();
        mDrawerToggle = new ActionBarDrawerToggle(
                RequestedMeActivity.this,
                mDrawerLayout,
                R.mipmap.ic_menu_white_24dp,
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

    public void setView(Context context, Class classe){
        Intent it = new Intent();
        it.setClass(context, classe);
        startActivity(it);
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




}
