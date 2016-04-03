package projeto.les.doevida.doevida.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import projeto.les.doevida.doevida.models.User;

public class MySharedPreferences {

    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;
    private Context mContext;
    int PRIVATE_MODE = 0;

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


    public MySharedPreferences(Context context){
        this.mContext = context;
        mPref = context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        mEditor = mPref.edit();
    }


    public void saveUser(String name, String date_birth, String city, String state, String gender,
                         String blood_type, String date_donation,String username, String password){

       // mEditor.putBoolean(IS_USER_LOGIN, true);
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
        char blood_type = blood_type_user.charAt(0);

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

}
