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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import projeto.les.doevida.doevida.R;
import projeto.les.doevida.doevida.adapters.DrawerListAdapter;
import projeto.les.doevida.doevida.adapters.NotificationsAdapter;
import projeto.les.doevida.doevida.models.BodyNotification;
import projeto.les.doevida.doevida.models.Form;
import projeto.les.doevida.doevida.models.NavItem;
import projeto.les.doevida.doevida.models.Notification;
import projeto.les.doevida.doevida.utils.HttpListener;
import projeto.les.doevida.doevida.utils.HttpUtils;
import projeto.les.doevida.doevida.utils.MySharedPreferences;

public class NotificationsActivity extends AppCompatActivity {

    private ArrayList<NavItem> mNavItems;
    private Context context;
    private MySharedPreferences userLogged;
    private TextView nameUserTextView;
    private TextView loginUserTextView;
    private HashMap<String, String> userDetails;
    private ListView listViewMyNotifications;
    private HttpUtils mHttp;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private RelativeLayout mDrawerPane;
    private ActionBarDrawerToggle mDrawerToggle;
    private String username;
    private List<Notification> list_notifications;

    private NotificationsAdapter adapter;

    private android.support.v7.app.ActionBar actionBar;
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        mHttp = new HttpUtils(this);
        userLogged = new MySharedPreferences(getApplicationContext());
        userDetails = userLogged.getUserDetails();

        String name = userDetails.get(MySharedPreferences.KEY_NAME_USER);
        username = userDetails.get(MySharedPreferences.KEY_USERNAME_USER);

        nameUserTextView = (TextView) findViewById(R.id.nameUser);
        nameUserTextView.setText(name);

        loginUserTextView = (TextView) findViewById(R.id.login);
        loginUserTextView.setText(username);
        listViewMyNotifications = (ListView) findViewById(R.id.lv_notifications);
        listViewMyNotifications.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Notification item = (Notification) adapter.getItem(position);
                if (item.getTitle().equals("Solicitacao de sangue")){
                    Form form = item.getForm();
                    Intent it = new Intent(NotificationsActivity.this,DonationOrderActivity.class );
                    it.putExtra("NOTIFICATIONT1", form);
                    startActivity(it);
                } else if (item.getTitle().equals("Confirmacao de doacao")){
                    Intent it = new Intent(NotificationsActivity.this, ConfirmationDonationActivity.class);
                    try {
                        BodyNotification bodyNotification = new BodyNotification(item.getReceiverLogin(),
                                item.getDonorName(), item.getBloodTypeDonor(), item.getPatientName(),
                                item.getBloodTypePatient());
                        it.putExtra("NOTIFICATIONT2", bodyNotification);
                        startActivity(it);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (item.getTitle().equals("Doacao recebida")){
                    Intent it = new Intent(NotificationsActivity.this,
                            ConfirmationReceiptDonationActivity.class);
                    try {
                        BodyNotification bodyNotification = new BodyNotification(item.getNameOfUser(),
                                item.getPatientName(), item.getBloodTypePatient());
                        it.putExtra("NOTIFICATIONT3", bodyNotification);
                        startActivity(it);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (item.getTitle().equals("Pedido aceito")){
                    Intent it = new Intent(NotificationsActivity.this, AcceptedOrderActivity.class);
                    try {
                        BodyNotification bodyNotification = new BodyNotification(item.getReceiverLogin(),
                                item.getDonorName(), item.getBloodTypeDonor(), item.getPatientName(),
                                item.getBloodTypePatient());
                        it.putExtra("NOTIFICATIONT4", bodyNotification);
                        startActivity(it);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        mNavItems = new ArrayList<>();
        setmDrawer(mNavItems);

        context = this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getListMyNotifications();
    }

    private void getListMyNotifications() {
        String urlGetUser = "http://doevida-grupoles.rhcloud.com/getUser?login=" + username ;
        mHttp.get(urlGetUser, new HttpListener() {
            @Override
            public void onSucess(JSONObject response) throws JSONException {
                if (response.getInt("ok") == 1) {
                    JSONObject jsonUser = response.getJSONObject("result");
                    JSONArray jsonArray = jsonUser.getJSONArray("listMyNotifications");
                    userLogged.saveListNotifications(jsonArray.toString());
                    list_notifications = new ArrayList<>();
                    list_notifications = userLogged.getListNotifications();
                    adapter = new NotificationsAdapter(NotificationsActivity.this, list_notifications);
                    listViewMyNotifications.setAdapter(adapter);
                }
            }

            @Override
            public void onTimeout() {
//                if (userLogged.getListRequests() != null) {
//                    listMyNotifications = userLogged.getListRequests();
//                }
//                if (listMyNotifications != null && listMyNotifications.size() == 0 || listMyNotifications == null) {
//                    Log.d("Notifications", "Tamanho da lista é zero");
//                    listViewMyNotifications.setVisibility(View.GONE);
//                } else {
//                    adapter = new NotificationsAdapter(context, listMyNotifications);
//                    listViewMyNotifications.setAdapter(adapter);
//                }
            }
        });
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
                    finish();
                } else if (position == 1) { // Informativos
                    mDrawerLayout.closeDrawer(mDrawerPane);
                    setView(NotificationsActivity.this, InformationActivity.class);
                    finish();
                } else if (position == 2) { // quem precisa
                    mDrawerLayout.closeDrawer(mDrawerPane);
                    setView(NotificationsActivity.this, RequestsActivity.class);
                    finish();
                } else if (position == 3) { // meus pedidos
                    mDrawerLayout.closeDrawer(mDrawerPane);
                    setView(NotificationsActivity.this, MyRequestsActivity.class);
                    finish();
                } else if (position == 4) { // notificacoes
                    mDrawerLayout.closeDrawer(mDrawerPane);
                    setView(NotificationsActivity.this, NotificationsActivity.class);
                    finish();
                } else if (position == 5) { // Pediram-me
                    mDrawerLayout.closeDrawer(mDrawerPane);
                    setView(NotificationsActivity.this, RequestedMeActivity.class);
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
