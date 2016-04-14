package projeto.les.doevida.doevida.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import projeto.les.doevida.doevida.models.User;
import projeto.les.doevida.doevida.views.DonorsActivity;
import projeto.les.doevida.doevida.views.LoginActivity;

public class MySharedPreferences {

    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;
    private Context mContext;
    int PRIVATE_MODE = 0;
    private List<User> listDonors;

    private static final String PREFER_NAME = "Pref";
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";
    public static final String KEY_NAME_USER = "name_user";
    public static final String KEY_DATE_BIRTH_USER = "date_birth_user";
    public static final String KEY_CITY_USER = "city_user";
    public static final String KEY_STATE_USER = "state_user";
    public static final String KEY_GENDER_USER = "gender_user";
    public static final String KEY_BLOOD_TYPE_USER = "blood_type_user";
    public static final String KEY_DATE_DONATION_USER = "date_donation_user";
    public static final String KEY_USERNAME_USER = "username_user";
    public static final String KEY_PASSWORD_USER = "password_user";
    public static final String KEY_LIST_DONORS = "list_donors";


    public MySharedPreferences(Context context){
        this.mContext = context;
        mPref = context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        mEditor = mPref.edit();
    }

    public void saveUser(String name, String date_birth, String city, String state, String gender,
                         String blood_type, String date_donation,String username, String password){
        mEditor.putBoolean(IS_USER_LOGIN, true);
        mEditor.putString(KEY_NAME_USER, name);
        mEditor.putString(KEY_DATE_BIRTH_USER, date_birth);
        mEditor.putString(KEY_CITY_USER, city);
        mEditor.putString(KEY_STATE_USER, state);
        mEditor.putString(KEY_GENDER_USER, gender);
        mEditor.putString(KEY_BLOOD_TYPE_USER, blood_type);
        mEditor.putString(KEY_DATE_DONATION_USER, date_donation);
        mEditor.putString(KEY_USERNAME_USER, username);
        mEditor.putString(KEY_PASSWORD_USER, password);
        mEditor.commit();
    }


    public User getUser(){
        String name = mPref.getString(KEY_NAME_USER, null);
        String date_birth_user = mPref.getString(KEY_DATE_BIRTH_USER, null);
        String city = mPref.getString(KEY_CITY_USER, null);
        String state =  mPref.getString(KEY_STATE_USER, null);
        String gender_user = mPref.getString(KEY_GENDER_USER, null);
        String blood_type_user = mPref.getString(KEY_BLOOD_TYPE_USER, null);
        String date_donation_user = mPref.getString(KEY_BLOOD_TYPE_USER, null);
        String password =  mPref.getString(KEY_PASSWORD_USER, null);
        String username = mPref.getString(KEY_USERNAME_USER, null);

        char gender = gender_user.charAt(0);
        String blood_type = blood_type_user;

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date_birth = null;
        Date date_donation = null;
        try {
            date_birth = formatter.parse(date_birth_user);
            date_donation = formatter.parse(date_donation_user);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        User user = null;
        try {
            user = new User(name, username, password, state, city, date_birth, date_donation, gender, blood_type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> userDetails = new HashMap<String, String>();
        userDetails.put(KEY_NAME_USER, mPref.getString(KEY_NAME_USER, null));
        userDetails.put(KEY_USERNAME_USER, mPref.getString(KEY_USERNAME_USER, null));
        userDetails.put(KEY_PASSWORD_USER, mPref.getString(KEY_PASSWORD_USER, null));
        userDetails.put(KEY_DATE_BIRTH_USER, mPref.getString(KEY_DATE_BIRTH_USER, null));
        userDetails.put(KEY_DATE_DONATION_USER, mPref.getString(KEY_DATE_DONATION_USER, null));
        userDetails.put(KEY_CITY_USER, mPref.getString(KEY_CITY_USER, null));
        userDetails.put(KEY_STATE_USER, mPref.getString(KEY_STATE_USER, null));
        userDetails.put(KEY_GENDER_USER, mPref.getString(KEY_GENDER_USER, null));
        userDetails.put(KEY_BLOOD_TYPE_USER, mPref.getString(KEY_BLOOD_TYPE_USER, null));

        return userDetails;
    }

    public boolean checkLogin(){
        if(this.isUserLoggedIn()){
            Intent i = new Intent(mContext, DonorsActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(i);

            return true;
        }
        return false;
    }

    public boolean isUserLoggedIn(){
        return mPref.getBoolean(IS_USER_LOGIN, false);
    }

    public void saveListDonors (String listDonors) {
        mEditor.putString(KEY_LIST_DONORS, listDonors);
        mEditor.commit();
    }

    public List<User> getListDonors() {
        String jsonArrayString = mPref.getString(KEY_LIST_DONORS, "");
        try {
            JSONArray jsonArray = new JSONArray(jsonArrayString);
            loadsList(jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        } return listDonors;
    }

    private void loadsList(JSONArray jsonArray) throws JSONException {
        listDonors = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonDonor = jsonArray.getJSONObject(i);
            String name = jsonDonor.getString("name");
            String login = jsonDonor.getString("login");
            String pass = jsonDonor.getString("pass");
            String state = jsonDonor.getString("state");
            String city = jsonDonor.getString("city");
            String birthdate = jsonDonor.getString("birthDate");
            String lastDonation = jsonDonor.getString("lastDonation");
            String gender = jsonDonor.getString("gender");
            String typeOfBlood = jsonDonor.getString("bloodType");

            char genderDonor = gender.charAt(0);

            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date birthdateDonor = new Date();
            Date lastDonationDonor = new Date();
            try {
                birthdateDonor = format.parse(birthdate);
                lastDonationDonor = format.parse(lastDonation);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            User donor = null;
            try {
                donor = new User(name, login, pass, state, city, birthdateDonor, lastDonationDonor, genderDonor, typeOfBlood);
                listDonors.add(donor);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void logoutUser(){
        mEditor.clear();
        mEditor.commit();
        Intent i = new Intent(mContext, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(i);
    }
}
