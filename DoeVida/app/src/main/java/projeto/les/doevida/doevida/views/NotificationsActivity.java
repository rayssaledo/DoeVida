package projeto.les.doevida.doevida.views;

import android.content.Context;
import android.content.Intent;
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

import projeto.les.doevida.doevida.R;
import projeto.les.doevida.doevida.utils.MySharedPreferences;
import projeto.les.doevida.doevida.adapters.DrawerListAdapter;
import projeto.les.doevida.doevida.models.NavItem;

public class NotificationsActivity extends AppCompatActivity {

    private ArrayList<NavItem> mNavItems;
    private Context context;
    private MySharedPreferences userLogged;
    private TextView nameUserTextView;
    private TextView loginUserTextView;
    private HashMap<String, String> userDetails;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private RelativeLayout mDrawerPane;
    private ActionBarDrawerToggle mDrawerToggle;

    private android.support.v7.app.ActionBar actionBar;
    private CharSequence mTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        userLogged = new MySharedPreferences(getApplicationContext());
        userDetails = userLogged.getUserDetails();

        String name = userDetails.get(MySharedPreferences.KEY_NAME_USER);
        String username = userDetails.get(MySharedPreferences.KEY_USERNAME_USER);

        nameUserTextView = (TextView) findViewById(R.id.nameUser);
        nameUserTextView.setText(name);

        loginUserTextView = (TextView) findViewById(R.id.login);
        loginUserTextView.setText(username);

        // Navigation bar
        mNavItems = new ArrayList<>();
        setmDrawer(mNavItems);

        context = this;
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
                    setView(NotificationsActivity.this, DonorsActivity.class);
                } else if (position == 1) { // Informativos
                    mDrawerLayout.closeDrawer(mDrawerPane);
                    setView(NotificationsActivity.this, InformationActivity.class);
                } else if (position == 2) { // quem precisa
//                    mDrawerLayout.closeDrawer(mDrawerPane);
//                    setView(DonorsActivity.this, UserCadastreActivity.class);
                } else if (position == 3) { // meus pedidos
                    mDrawerLayout.closeDrawer(mDrawerPane);
                    setView(NotificationsActivity.this, MyRequestsActivity.class);
                } else if (position == 4) { // notificacoes
                    mDrawerLayout.closeDrawer(mDrawerPane);
                    setView(NotificationsActivity.this, NotificationsActivity.class);
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
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu_white_24dp);
        actionBar.setHomeButtonEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mTitle = getTitle().toString();
        mDrawerToggle = new ActionBarDrawerToggle(
                NotificationsActivity.this,
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) return true;
        return super.onOptionsItemSelected(item);
    }
}
