package projeto.les.doevida.doevida.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import projeto.les.doevida.doevida.R;
import projeto.les.doevida.doevida.utils.HttpListener;
import projeto.les.doevida.doevida.utils.HttpUtils;
import projeto.les.doevida.doevida.utils.MySharedPreferences;
import projeto.les.doevida.doevida.adapters.DonorsAdapter;
import projeto.les.doevida.doevida.adapters.DrawerListAdapter;
import projeto.les.doevida.doevida.models.NavItem;
import projeto.les.doevida.doevida.models.User;

public class DonorsActivity extends AppCompatActivity {

    private MySharedPreferences userLogged;
    private HashMap<String, String> userDetails;
    private ListView listViewDonors;
    private HttpUtils mHttp;
    private View mLoading;
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

    private EditText mNamePatient;
    private EditText mHospital;
    private EditText mCity;
    private EditText mState;
    private EditText mDateLimitDonation;

    private TextInputLayout layout_name_patient;
    private TextInputLayout layout_hospital;
    private TextInputLayout layout_city;
    private TextInputLayout layout_state;
    private TextInputLayout layout_date_limit_donation;

    private String name_patient;
    private String hospital;
    private String city;
    private String state;
    private String date_limit_donation;

    private List<String> mTypes;
    private String mBlood_Type;
    private String loginUserLogged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donors);

        context = this;

        userLogged = new MySharedPreferences(getApplicationContext());
        userDetails = userLogged.getUserDetails();

        String name = userDetails.get(MySharedPreferences.KEY_NAME_USER);
        loginUserLogged = userDetails.get(MySharedPreferences.KEY_USERNAME_USER);

        Log.d("TESTE", "Nome: " + name);

        nameUserTextView = (TextView) findViewById(R.id.nameUser);
        nameUserTextView.setText(name);

        loginUserTextView = (TextView) findViewById(R.id.login);
        loginUserTextView.setText(loginUserLogged);

        mNavItems = new ArrayList<>();
        setmDrawer(mNavItems);

        Button ask_all = (Button) findViewById(R.id.btn_ask_all);

        ask_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialogChooseForm = new Dialog(DonorsActivity.this);
                dialogChooseForm.setContentView(R.layout.dialog_choose_form);
                dialogChooseForm.setTitle("Fazer pedido");
                dialogChooseForm.setCancelable(true);

                final RadioButton rd1 = (RadioButton) dialogChooseForm.findViewById(R.id.rd1);
                final RadioButton rd2 = (RadioButton) dialogChooseForm.findViewById(R.id.rd2);
                final Button okButton = (Button) dialogChooseForm.findViewById(R.id.btn_ok);
                final Button cancelButton = (Button) dialogChooseForm.findViewById(R.id.btn_cancel);

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

                dialogChooseForm.show();

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogChooseForm.dismiss();
                    }
                });

                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (rd1.isChecked()) {
                            final Dialog dialogForm = new Dialog(DonorsActivity.this);
                            dialogForm.setContentView(R.layout.dialog_form);
                            dialogForm.setTitle("Pedido de doação");
                            dialogForm.setCancelable(true);
                            dialogForm.show();

                            final EditText input_date_limit_donation = (EditText) dialogForm.
                                    findViewById(R.id.input_date_limit_donation);
                            final MaskEditTextChangedListener maskDateLimitDonation =
                                    new MaskEditTextChangedListener("##/##/####",
                                            input_date_limit_donation);
                            input_date_limit_donation.addTextChangedListener(maskDateLimitDonation);

                            final Spinner mBlood_types_spinner = (Spinner) dialogForm.
                                    findViewById(R.id.sp_blood_type);

                            putBloodTypeElementsOnSpinnerArray();
                            ArrayAdapter<String> spinnerArrayAdapterBloodTypes =
                                    new ArrayAdapter<>(context,
                                            android.R.layout.simple_spinner_item, mTypes);
                            spinnerArrayAdapterBloodTypes.setDropDownViewResource(android.
                                    R.layout.simple_spinner_dropdown_item); // The drop down view
                            mBlood_types_spinner.setAdapter(spinnerArrayAdapterBloodTypes);

                            mBlood_types_spinner.setOnItemSelectedListener(new AdapterView.
                                    OnItemSelectedListener() {
                                public void onItemSelected(final AdapterView<?> parent,
                                                           final View view, final int pos,
                                                           final long id) {
                                    Object item = parent.getItemAtPosition(pos);
                                    mBlood_Type = item.toString();
                                }

                                public void onNothingSelected(final AdapterView<?> parent) {
                                }
                            });

                            final Button btn_send = (Button) dialogForm.findViewById(R.id.btn_send);

                            btn_send.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mLoading = dialogForm.findViewById(R.id.loadingForm);
                                    mNamePatient = (EditText) dialogForm.
                                            findViewById(R.id.input_name_patient);
                                    mHospital = (EditText) dialogForm.findViewById(R.id.input_hospital);
                                    mCity = (EditText) dialogForm.findViewById(R.id.input_city);
                                    mState = (EditText) dialogForm.findViewById(R.id.input_state);
                                    mDateLimitDonation = (EditText) dialogForm.
                                            findViewById(R.id.input_date_limit_donation);

                                    layout_name_patient = (TextInputLayout) dialogForm.
                                            findViewById(R.id.input_layout_name_patient);
                                    layout_hospital = (TextInputLayout) dialogForm.
                                            findViewById(R.id.input_layout_hospital);
                                    layout_city = (TextInputLayout) dialogForm.
                                            findViewById(R.id.input_layout_city);
                                    layout_state = (TextInputLayout) dialogForm.
                                            findViewById(R.id.input_layout_state);
                                    layout_date_limit_donation = (TextInputLayout) dialogForm.
                                            findViewById(R.id.input_layout_date_limit_donation);

                                    name_patient = mNamePatient.getText().toString();
                                    hospital = mHospital.getText().toString();
                                    city = mCity.getText().toString();
                                    state = mState.getText().toString();
                                    date_limit_donation = mDateLimitDonation.getText().toString();

                                    if (validateName() && validateHospital() && validateCity() &&
                                            validateState() && validateDateLimitDonation()) {
                                        addForm(name_patient, hospital, city, state, mBlood_Type,
                                                date_limit_donation, dialogForm);
                                        dialogChooseForm.dismiss();
                                    } else if (!validateName()){
                                        return;
                                    } else if (!validateHospital()){
                                        return;
                                    } else if (!validateCity()){
                                        return;
                                    } else if (!validateState()) {
                                        return;
                                    } else if (!validateDateLimitDonation()){
                                        return;
                                    }

                                }
                            });

                            final Button btn_cancel = (Button) dialogForm.findViewById(R.id.btn_cancel);

                            btn_cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialogForm.dismiss();
                                }
                            });
                        }

                        if(rd2.isChecked()){
                            setView(DonorsActivity.this, MyRequestsActivity.class);
                            dialogChooseForm.dismiss();
                        }
                    }
                });
            }
        });

        mHttp = new HttpUtils(this);
        listViewDonors = (ListView) findViewById(R.id.lv_donors);
        listViewDonors.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                User item = (User) adapter.getItem(position);
                String loginDest = item.getUserame();

                String url = "http://doevida-grupoles.rhcloud.com/sendNotification";
                JSONObject json = new JSONObject();

                try {
                    json.put("titleNotification", "Solicitação de sangue");
                    json.put("bodyNotification", "Formulario");
                    json.put("receiverLogin", loginDest);
                    json.put("senderLogin", loginUserLogged);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mHttp.post(url, json.toString(), new HttpListener() {
                    @Override
                    public void onSucess(JSONObject result) throws JSONException{
                        if (result.getInt("ok") == 0) {
                            new AlertDialog.Builder(DonorsActivity.this)
                                    .setTitle("Erro")
                                    .setMessage(result.getString("msg"))
                                    .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            mLoading.setVisibility(View.GONE);
                                        }
                                    })
                                    .create()
                                    .show();
                        } else {
                            new AlertDialog.Builder(DonorsActivity.this)
                                    .setMessage("Fomulário criado com sucesso")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            // dialog.dismiss();
                                        }
                                    })
                                    .create()
                                    .show();
                        }
                    }
                    @Override
                    public void onTimeout() {
                        new AlertDialog.Builder(DonorsActivity.this)
                                .setTitle("Erro")
                                .setMessage("Conexão não disponível")
                                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        mLoading.setVisibility(View.GONE);
                                    }
                                })
                                .create()
                                .show();
                    }
                });




                final Dialog dialogChooseForm = new Dialog(DonorsActivity.this);
                dialogChooseForm.setContentView(R.layout.dialog_choose_form);
                dialogChooseForm.setTitle("Fazer pedido");
                dialogChooseForm.setCancelable(true);

                final RadioButton rd1 = (RadioButton) dialogChooseForm.findViewById(R.id.rd1);
                final RadioButton rd2 = (RadioButton) dialogChooseForm.findViewById(R.id.rd2);
                final Button okButton = (Button) dialogChooseForm.findViewById(R.id.btn_ok);
                final Button cancelButton = (Button) dialogChooseForm.findViewById(R.id.btn_cancel);

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
                dialogChooseForm.show();

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogChooseForm.dismiss();
                    }
                });

                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (rd1.isChecked()) {
                            final Dialog dialogForm = new Dialog(DonorsActivity.this);
                            dialogForm.setContentView(R.layout.dialog_form);
                            dialogForm.setTitle("Pedido de doação");
                            dialogForm.setCancelable(true);
                            dialogForm.show();

                            final EditText input_date_limit_donation = (EditText) dialogForm.
                                    findViewById(R.id.input_date_limit_donation);
                            final MaskEditTextChangedListener maskDateLimitDonation =
                                    new MaskEditTextChangedListener("##/##/####",
                                            input_date_limit_donation);
                            input_date_limit_donation.addTextChangedListener(maskDateLimitDonation);

                            final Spinner mBlood_types_spinner = (Spinner) dialogForm.
                                    findViewById(R.id.sp_blood_type);

                            putBloodTypeElementsOnSpinnerArray();
                            ArrayAdapter<String> spinnerArrayAdapterBloodTypes =
                                    new ArrayAdapter<>(context,
                                            android.R.layout.simple_spinner_item, mTypes);
                            spinnerArrayAdapterBloodTypes.setDropDownViewResource(android.
                                    R.layout.simple_spinner_dropdown_item); // The drop down view
                            mBlood_types_spinner.setAdapter(spinnerArrayAdapterBloodTypes);

                            mBlood_types_spinner.setOnItemSelectedListener(new AdapterView.
                                    OnItemSelectedListener() {
                                public void onItemSelected(final AdapterView<?> parent,
                                                           final View view, final int pos,
                                                           final long id) {
                                    Object item = parent.getItemAtPosition(pos);
                                    mBlood_Type = item.toString();
                                }

                                public void onNothingSelected(final AdapterView<?> parent) {
                                }
                            });

                            final Button btn_send = (Button) dialogForm.findViewById(R.id.btn_send);

                            btn_send.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mLoading = dialogForm.findViewById(R.id.loadingForm);
                                    mNamePatient = (EditText) dialogForm.
                                            findViewById(R.id.input_name_patient);
                                    mHospital = (EditText) dialogForm.
                                            findViewById(R.id.input_hospital);
                                    mCity = (EditText) dialogForm.findViewById(R.id.input_city);
                                    mState = (EditText) dialogForm.findViewById(R.id.input_state);
                                    mDateLimitDonation = (EditText) dialogForm.
                                            findViewById(R.id.input_date_limit_donation);

                                    layout_name_patient = (TextInputLayout) dialogForm.
                                            findViewById(R.id.input_layout_name_patient);
                                    layout_hospital = (TextInputLayout) dialogForm.
                                            findViewById(R.id.input_layout_hospital);
                                    layout_city = (TextInputLayout) dialogForm.
                                            findViewById(R.id.input_layout_city);
                                    layout_state = (TextInputLayout) dialogForm.
                                            findViewById(R.id.input_layout_state);
                                    layout_date_limit_donation = (TextInputLayout) dialogForm.
                                            findViewById(R.id.input_layout_date_limit_donation);

                                    name_patient = mNamePatient.getText().toString();
                                    hospital = mHospital.getText().toString();
                                    city = mCity.getText().toString();
                                    state = mState.getText().toString();
                                    date_limit_donation = mDateLimitDonation.getText().toString();

                                    if (validateName() && validateHospital() && validateCity() &&
                                            validateState() && validateDateLimitDonation()) {
                                        addForm(name_patient, hospital, city, state, mBlood_Type,
                                                date_limit_donation, dialogForm);
                                        dialogChooseForm.dismiss();
                                    } else if (!validateName()) {
                                        return;
                                    } else if (!validateHospital()) {
                                        return;
                                    } else if (!validateCity()) {
                                        return;
                                    } else if (!validateState()) {
                                        return;
                                    } else if (!validateDateLimitDonation()) {
                                        return;
                                    }
                                }

                            });

                            final Button btn_cancel = (Button) dialogForm.findViewById(R.id.btn_cancel);

                            btn_cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialogForm.dismiss();
                                }
                            });
                        }
                        if(rd2.isChecked()){
                            setView(DonorsActivity.this, MyRequestsActivity.class);
                            dialogChooseForm.dismiss();
                        }
                    }
                });
            }
        });
        context = this;
        getListDonors();



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
                    setView(DonorsActivity.this, DonorsActivity.class);
                } else if (position == 1) { // Informativos
                    mDrawerLayout.closeDrawer(mDrawerPane);
                    setView(DonorsActivity.this, InformationActivity.class);
                } else if (position == 2) { // quem precisa
//                    mDrawerLayout.closeDrawer(mDrawerPane);
//                    setView(DonorsActivity.this, UserCadastreActivity.class);
                } else if (position == 3) { // meus pedidos
                    mDrawerLayout.closeDrawer(mDrawerPane);
                    setView(DonorsActivity.this, MyRequestsActivity.class);
                } else if (position == 4) { // notificacoes
                    mDrawerLayout.closeDrawer(mDrawerPane);
                    setView(DonorsActivity.this, NotificationsActivity.class);
                } else if (position == 5) { // Pediram-me
                    mDrawerLayout.closeDrawer(mDrawerPane);
                    setView(DonorsActivity.this, RequestedMeActivity.class);
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
                DonorsActivity.this,
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

    public void putBloodTypeElementsOnSpinnerArray(){
        mTypes = new ArrayList<>();
        mTypes.add(getApplicationContext().getResources().getString(R.string.a_positivo));
        mTypes.add(getApplicationContext().getResources().getString(R.string.a_negativo));
        mTypes.add(getApplicationContext().getResources().getString(R.string.b_positivo));
        mTypes.add(getApplicationContext().getResources().getString(R.string.b_negativo));
        mTypes.add(getApplicationContext().getResources().getString(R.string.ab_positivo));
        mTypes.add(getApplicationContext().getResources().getString(R.string.ab_negativo));
        mTypes.add(getApplicationContext().getResources().getString(R.string.o_positivo));
        mTypes.add(getApplicationContext().getResources().getString(R.string.o_negativo));
    }

    private boolean validateName(){
        if (name_patient.trim().isEmpty() || name_patient == null) {
            layout_name_patient.setError(getString(R.string.err_msg_name));
            requestFocus(mNamePatient);
            return false;
        } else if (name_patient.trim().length() < 10){
            layout_name_patient.setError(getString(R.string.err_short_name));
            requestFocus(mNamePatient);
            return false;
        } else {
            layout_name_patient.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateHospital(){
        if (hospital.trim().isEmpty() || hospital == null) {
            layout_hospital.setError(getString(R.string.err_msg_hospital));
            requestFocus(mHospital);
            return false;
        } else if (hospital.trim().length() < 10){
            layout_hospital.setError(getString(R.string.err_short_hospital_name));
            requestFocus(mHospital);
            return false;
        } else {
            layout_hospital.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateCity(){
        if (city.trim().isEmpty() || city == null) {
            layout_city.setError(getString(R.string.err_msg_city));
            requestFocus(mCity);
            return false;
        } else if (city.trim().length() < 4){
            layout_city.setError(getString(R.string.err_short_city_name));
            requestFocus(mCity);
            return false;
        } else {
            layout_city.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateState(){
        if (state.trim().isEmpty()) {
            layout_state.setError(getString(R.string.err_msg_state));
            requestFocus(mState);
            return false;
        } else if (state.trim().length() < 4) {
            layout_state.setError(getString(R.string.err_short_state_name));
            requestFocus(mState);
            return false;
        } else {
            layout_state.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateDateLimitDonation(){
        if (date_limit_donation.trim().isEmpty()) {
            layout_date_limit_donation.setError(getString(R.string.err_msg_birth));
            requestFocus(mDateLimitDonation);
            return false;
        } else if (date_limit_donation.trim().length() != 10){
            layout_date_limit_donation.setError(getString(R.string.err_invalid_birth));
            requestFocus(mDateLimitDonation);
            return false;
        } else {
            layout_date_limit_donation.setErrorEnabled(false);
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    private void addForm(final String name_patient, final String hospital, final String city,
                         final String state, final String blood_type,
                         final String date_limit_donation, final Dialog dialog) {

        mLoading.setVisibility(View.VISIBLE);
        String url = "http://doevida-grupoles.rhcloud.com/addForm";
        JSONObject json = new JSONObject();
        try {
            json.put("login", loginUserLogged);
            json.put("patientName", name_patient);
            json.put("hospitalName", hospital);
            json.put("city", city);
            json.put("bloodType", blood_type);
            json.put("deadline", date_limit_donation);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mHttp.post(url, json.toString(), new HttpListener() {
            @Override
            public void onSucess(JSONObject result) throws JSONException{
                if (result.getInt("ok") == 0) {
                    new AlertDialog.Builder(DonorsActivity.this)
                            .setTitle("Erro")
                            .setMessage(result.getString("msg"))
                            .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    mLoading.setVisibility(View.GONE);
                                }
                            })
                            .create()
                            .show();
                } else {
                    new AlertDialog.Builder(DonorsActivity.this)
                            .setMessage("Fomulário criado com sucesso")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialog.dismiss();
                                }
                            })
                            .create()
                            .show();
                }
            }
            @Override
            public void onTimeout() {
                new AlertDialog.Builder(DonorsActivity.this)
                        .setTitle("Erro")
                        .setMessage("Conexão não disponível")
                        .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mLoading.setVisibility(View.GONE);
                            }
                        })
                        .create()
                        .show();
            }
        });
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
