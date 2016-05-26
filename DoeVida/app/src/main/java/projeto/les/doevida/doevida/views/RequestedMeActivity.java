package projeto.les.doevida.doevida.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import projeto.les.doevida.doevida.adapters.RequestedMeAdapter;
import projeto.les.doevida.doevida.models.Form;
import projeto.les.doevida.doevida.models.NavItem;
import projeto.les.doevida.doevida.utils.HttpListener;
import projeto.les.doevida.doevida.utils.HttpUtils;
import projeto.les.doevida.doevida.utils.MySharedPreferences;

public class RequestedMeActivity extends AppCompatActivity {

    private ListView lv_requested_me;
    private RequestedMeAdapter adapter;
    private List<Form> requests_accepeted;
    private HttpUtils mHttp;
    private String myLogin;
    private String myName;
    private String myBloodType;
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
    private Dialog dialogConfirmation;
    private Button btn_view_profile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requested_me);

        mHttp = new HttpUtils(this);
        userLogged = new MySharedPreferences(getApplicationContext());
        userDetails = userLogged.getUserDetails();
        btn_view_profile = (Button) findViewById(R.id.btn_view_profile);
        btn_view_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setView(RequestedMeActivity.this, UserProfileActivity.class);
            }
        });

        myLogin = userDetails.get(MySharedPreferences.KEY_USERNAME_USER);
        myName = userDetails.get(MySharedPreferences.KEY_NAME_USER);
        myBloodType = userDetails.get(MySharedPreferences.KEY_BLOOD_TYPE_USER);

        lv_requested_me = (ListView) findViewById(R.id.lv_requested_me);
      //lv_requested_me.setAdapter(adapter);
        lv_requested_me.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Form request_accepeted_item = (Form) adapter.getItem(position);
                openDialogConfirmation(request_accepeted_item);
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

        getListMyFormsReceived();

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

    private void openDialogConfirmation(final Form request_accepeted_item) {
        dialogConfirmation = new Dialog(RequestedMeActivity.this);
        dialogConfirmation.setContentView(R.layout.dialog_confirmation_donation);
        dialogConfirmation.setTitle("Confirme a doação");
        dialogConfirmation.setCancelable(true);
        dialogConfirmation.show();

        final TextView patient_name = (TextView) dialogConfirmation.
                findViewById(R.id.tv_patient_name);
        patient_name.setText(request_accepeted_item.getPatientName());

        final Button btn_ok = (Button) dialogConfirmation.findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "http://doevida-grupoles.rhcloud.com/sendNotification";
                JSONObject json = new JSONObject();
                JSONObject jsonBodyNotification = new JSONObject();

                try {
                    jsonBodyNotification.put("login", loginUserLogged);
                    jsonBodyNotification.put("donorName", myName);
                    jsonBodyNotification.put("bloodTypeDonor", myBloodType);
                    jsonBodyNotification.put("patientName", request_accepeted_item.getPatientName());
                    jsonBodyNotification.put("bloodTypePatient", request_accepeted_item.getTypeOfBlood());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    json.put("titleNotification", "Confirmacao de doacao");
                    json.put("bodyNotification", jsonBodyNotification);
                    json.put("receiverLogin", request_accepeted_item.getLoginDest());
                    Log.d("RECEIVER_LOGIN", request_accepeted_item.getLoginDest()+"");
                    json.put("senderLogin", loginUserLogged);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mHttp.post(url, json.toString(), new HttpListener() {
                    @Override
                    public void onSucess(JSONObject result) throws JSONException {
                        if (result.getInt("ok") == 0) {
                            new AlertDialog.Builder(RequestedMeActivity.this)
                                    .setTitle("Erro")
                                    .setMessage(result.getString("msg"))
                                    .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            //mLoading.setVisibility(View.GONE);
                                        }
                                    })
                                    .create()
                                    .show();
                        } else {
                            /*new AlertDialog.Builder(RequestedMeActivity.this)
                                    .setMessage("Pedido aceito")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            setView(RequestedMeActivity.this, DonorsActivity.class);
                                            finish();
                                        }
                                    })
                                    .create()
                                    .show();
*/
                        }
                    }

                    @Override
                    public void onTimeout() {
                        new AlertDialog.Builder(RequestedMeActivity.this)
                                .setTitle("Erro")
                                .setMessage("Conexão não disponível")
                                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //mLoading.setVisibility(View.GONE);
                                    }
                                })
                                .create()
                                .show();
                    }
                });
                openDialogConfirmationReceipt();
            }
        });
    }

    private void openDialogConfirmationReceipt() {
        dialogConfirmation.dismiss();
        final Dialog dialogConfirmationReceipt = new Dialog(RequestedMeActivity.this);
        dialogConfirmationReceipt.setContentView(R.layout.dialog_confirmation_receipt);
        dialogConfirmationReceipt.setTitle("Aguarde...");
        dialogConfirmationReceipt.setCancelable(true);
        dialogConfirmationReceipt.show();

        final Button btn_ok = (Button) dialogConfirmationReceipt.findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogConfirmationReceipt.dismiss();
            }
        });
    }


    private void getListMyFormsReceived() {
        String urlGetUser = "http://doevida-grupoles.rhcloud.com/getUser?login=" + loginUserLogged ;
        mHttp.get(urlGetUser, new HttpListener() {
            @Override
            public void onSucess(JSONObject response) throws JSONException {
                if (response.getInt("ok") == 1) {
                    JSONObject jsonUser = response.getJSONObject("result");
                    JSONArray jsonArray = jsonUser.getJSONArray("listMyNotifications");
                    userLogged.saveMyFormsReceived(jsonArray.toString());
                    requests_accepeted = userLogged.getListMyFormsReceived();
                    adapter = new RequestedMeAdapter(RequestedMeActivity.this, requests_accepeted);
                    lv_requested_me.setAdapter(adapter);
                    Log.d("lista de requests", "CERTO!!" +
                            "");
                }
            }

            @Override
            public void onTimeout() {
                if (userLogged.getListRequests() != null) {
                    requests_accepeted = userLogged.getListMyFormsReceived();
                }
                if (requests_accepeted != null && requests_accepeted.size() == 0 || requests_accepeted == null) {
                    lv_requested_me.setVisibility(View.GONE);
                } else {
                    adapter = new RequestedMeAdapter(RequestedMeActivity.this, requests_accepeted);
                    lv_requested_me.setAdapter(adapter);
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
