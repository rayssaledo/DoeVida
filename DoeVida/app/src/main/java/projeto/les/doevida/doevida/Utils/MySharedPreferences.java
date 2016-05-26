package projeto.les.doevida.doevida.utils;

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

import projeto.les.doevida.doevida.models.Form;
import projeto.les.doevida.doevida.models.Notification;
import projeto.les.doevida.doevida.models.Request;
import projeto.les.doevida.doevida.models.User;
import projeto.les.doevida.doevida.views.DonorsActivity;
import projeto.les.doevida.doevida.views.LoginActivity;

public class MySharedPreferences {

    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;
    private Context mContext;
    int PRIVATE_MODE = 0;
    private List<User> listDonors;
    private List<Request> my_forms;
    private List<Form> myForms;
    private List<Notification> my_notifications;
    private List<Form> requestAccepted;
    private List<Form> myFormsReceived;
    private JSONArray jsonArrayRequestedme;

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
    public static final String KEY_LIST_FORMS = "list_forms";
    public static final String KEY_LIST_NOTIFICATIONS = "list_notifications";
    public static final String KEY_LIST_MY_FORMS = "list_my_forms";
    public static final String KEY_REQUESTS_ACCEPTED = "list_requests_accepted";
    public static final String KEY_LIST_MY_FORMS_RECEIVED = "list_requests_received";
    public static final String KEY_NUMBER_DONATIONS = "number_donations";

    public static final String KEY_REG_ID = "reg_id";


    public MySharedPreferences(Context context){
        this.mContext = context;
        mPref = context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        mEditor = mPref.edit();
        jsonArrayRequestedme = new JSONArray();
    }

    public void saveRegId(String reg_id){
        mEditor.putString(KEY_REG_ID, reg_id);
        mEditor.commit();
    }

    public void saveRequestsAccepted(JSONObject jsonForm){
        Log.d("JSON_ARRAY", jsonArrayRequestedme + "");
        jsonArrayRequestedme.put(jsonForm);
        Log.d("JSON_ARRAY", jsonArrayRequestedme.toString());

        mEditor.putString(KEY_REQUESTS_ACCEPTED, jsonArrayRequestedme.toString());
        mEditor.commit();
    }

    public void loadsListRequestMe(JSONArray jsonArray) throws JSONException{
        requestAccepted = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonForm = jsonArray.getJSONObject(i);
            String loginDest = jsonForm.getString("login");
            String hospital = jsonForm.getString("hospitalName");
            String patientName = jsonForm.getString("patientName");
            String city = jsonForm.getString("city");
            String state = jsonForm.getString("state");
            String bloodType = jsonForm.getString("bloodType");
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date deadline = null;
            try {
                deadline = format.parse(jsonForm.getString("deadline"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                Form form = new Form(loginDest, patientName, hospital, city, state, bloodType, deadline);
                requestAccepted.add(form);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    public List<Form> getListRequestsAccepted(){
        String jsonFormString = mPref.getString(KEY_REQUESTS_ACCEPTED, "");
        try {
            JSONArray jsonArray = new JSONArray(jsonFormString);
            loadsListRequestMe(jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requestAccepted;
    }
    public void saveUser(String name, String date_birth, String city, String state, String gender,
                         String blood_type, String date_donation,String username, String password,
                         String numberDonations){
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
        mEditor.putString(KEY_NUMBER_DONATIONS, numberDonations);
        mEditor.commit();
    }


    public User getUser(){
        String name = mPref.getString(KEY_NAME_USER, null);
        String date_birth_user = mPref.getString(KEY_DATE_BIRTH_USER, null);
        String city = mPref.getString(KEY_CITY_USER, null);
        String state =  mPref.getString(KEY_STATE_USER, null);
        String gender_user = mPref.getString(KEY_GENDER_USER, null);
        String blood_type_user = mPref.getString(KEY_BLOOD_TYPE_USER, null);
        String date_donation_user = mPref.getString(KEY_DATE_DONATION_USER, null);
        String password =  mPref.getString(KEY_PASSWORD_USER, null);
        String username = mPref.getString(KEY_USERNAME_USER, null);
        String numberDonations = mPref.getString(KEY_NUMBER_DONATIONS, null);

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

        Integer numDonations = Integer.parseInt(numberDonations);

        User user = null;
        try {
            user = new User(name, username, password, state, city, date_birth, date_donation, gender, blood_type, numDonations);
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
        userDetails.put(KEY_NUMBER_DONATIONS, mPref.getString(KEY_NUMBER_DONATIONS,null));

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

    public void saveListRequests(String listForms) {
        mEditor.putString(KEY_LIST_FORMS, listForms);
        mEditor.commit();
    }

    public void saveListNotifications(String listNotifications) {
        mEditor.putString(KEY_LIST_NOTIFICATIONS, listNotifications);
        mEditor.commit();
    }

    public void saveMyForms(String listMyForms) {
        mEditor.putString(KEY_LIST_MY_FORMS, listMyForms);
        mEditor.commit();
    }
    public void saveMyFormsReceived(String listMyFormsReceived) {
        mEditor.putString(KEY_LIST_MY_FORMS_RECEIVED, listMyFormsReceived);
        mEditor.commit();
    }

    public List<User> getListDonors() {
        String jsonArrayString = mPref.getString(KEY_LIST_DONORS, "");
        try {
            JSONArray jsonArray = new JSONArray(jsonArrayString);
            loadsList(jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listDonors;
    }

    public List<Notification> getListNotifications(){
        String jsonArrayString = mPref.getString(KEY_LIST_NOTIFICATIONS, "");
        Log.d("JSON_ARRAY_STRING", jsonArrayString);
        try {
            JSONArray jsonArray = new JSONArray(jsonArrayString);
            Log.d("JSON_ARRAY", jsonArray +"");
            loadMyNotifications(jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        } return my_notifications;
    }

    public List<Form> getListMyForms(){
        String jsonArrayString = mPref.getString(KEY_LIST_MY_FORMS, "");
        try {
            JSONArray jsonArray = new JSONArray(jsonArrayString);
            loadMyFormsSend(jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return myForms;
    }

    public List<Form> getListMyFormsReceived(){
        String jsonArrayString = mPref.getString( KEY_LIST_MY_FORMS_RECEIVED, "");
        try {
            JSONArray jsonArray = new JSONArray(jsonArrayString);
            loadMyFormsReceived(jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return myFormsReceived;
    }


    public List<Request> getListRequests(){
        String jsonArrayString = mPref.getString(KEY_LIST_FORMS, "");
        try {
            JSONArray jsonArray = new JSONArray(jsonArrayString);
            loadMyForms(jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        } return my_forms;
    }

    private void loadMyNotifications(JSONArray jsonArray) throws JSONException{
        my_notifications =  new ArrayList<>();
        Log.d("TAMANHO", jsonArray.length() + "" );
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonRequest = jsonArray.getJSONObject(i);

            Log.d("json_body", jsonRequest+"");
            String senderlogin = jsonRequest.getString("senderLogin");
            //  String receiverlogin = jsonRequest.getString("receiverLogin");
            String title = jsonRequest.getString("titleNotification");
            JSONObject jsonBody = jsonRequest.getJSONObject("bodyNotification");

//            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
//            Date deadline = null;
//            try {
//                deadline = format.parse(jsonBody.getString("deadline"));
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }

            if (title.equals("Solicitacao de sangue")){
                String patient = jsonBody.getString("patientName");
                String hospital = jsonBody.getString("hospitalName");
                String city = jsonBody.getString("city");
                String state = jsonBody.getString("state");
                String bloodType = jsonBody.getString("bloodType");
                try {
//                    Form form = new Form(senderLogin, patient, hospital, city, state, bloodType, deadline);
//                    Notification notification = new Notification(senderlogin, "receiver", title, deadline, form);
                    Form form = new Form(senderlogin, patient, hospital, city, state, bloodType);
                    Notification notification = new Notification(senderlogin, "receiver", title, form);
                    my_notifications.add(notification);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (title.equals("Doacao recebida")){
                String nameOfUser = jsonBody.getString("nameOfUser");
                String receptorName = jsonBody.getString("receptorName");
                String bloodTypeReceptor = jsonBody.getString("bloodTypeReceptor");

//              Notification notification = new Notification(senderlogin, "receiver", title, deadline, "", patient, bloodType);
                Notification notification = new Notification(senderlogin, "receiver", title, nameOfUser, receptorName, bloodTypeReceptor);
                my_notifications.add(notification);
            }
            else {
                String loginDest = jsonBody.getString("login");
                String donorName = jsonBody.getString("donorName");
                String bloodTypeDonor = jsonBody.getString("bloodTypeDonor");
                String patientName = jsonBody.getString("patientName");
                String bloodTypePatient = jsonBody.getString("bloodTypePatient");

                Notification notification = new Notification(senderlogin, loginDest, title, donorName,
                        patientName, bloodTypePatient, bloodTypeDonor);
                //                Notification notification = new Notification(senderlogin, "receiver", title, deadline, donor,
                my_notifications.add(notification);
            }
        }
    }

    private void loadMyFormsReceived(JSONArray jsonArray) throws JSONException{
        myFormsReceived =  new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonRequest = jsonArray.getJSONObject(i);
            //String loginDest = jsonRequest.getString("senderLogin");
            String title = jsonRequest.getString("titleNotification");
            JSONObject jsonBody = jsonRequest.getJSONObject("bodyNotification");


//            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
//            Date deadline = null;
//            try {
//                deadline = format.parse(jsonBody.getString("deadline"));
//            } catch (ParseException e) {
//                e.printStackTrace();
//
//  }

            if (title.equals("Solicitacao de sangue")){
                String loginDest = jsonBody.getString("login");
                String patient = jsonBody.getString("patientName");
                String hospital = jsonBody.getString("hospitalName");
                String city = jsonBody.getString("city");
                String state = jsonBody.getString("state");
                String bloodType = jsonBody.getString("bloodType");
                try {
//                    Form form = new Form(patient, hospital, city, state, bloodType, deadline);
//                    Notification notification = new Notification(senderlogin, "receiver", title, deadline, form);
                    Form form = new Form(loginDest, patient, hospital, city, state, bloodType);
                    myFormsReceived.add(form);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private void loadMyFormsSend(JSONArray jsonArray) throws JSONException{
        myForms =  new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonRequest = jsonArray.getJSONObject(i);
            String patient = jsonRequest.getString("patientName");
            String hospital = jsonRequest.getString("hospitalName");
            String city = jsonRequest.getString("city");
            String state = jsonRequest.getString("state");
            String bloodType = jsonRequest.getString("bloodType");

            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date deadline = null;
            try {
                deadline = format.parse(jsonRequest.getString("deadline"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                Form form = new Form(patient, hospital, city, state, bloodType, deadline);
                myForms.add(form);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    private void loadMyForms(JSONArray jsonArray) throws JSONException{
        my_forms =  new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonRequest = jsonArray.getJSONObject(i);
            String patientName = jsonRequest.getString("patientName");

            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date deadline = null;
            try {
                deadline = format.parse(jsonRequest.getString("deadline"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                Request request = new Request(patientName, deadline);
                my_forms.add(request);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
            Integer numberDonations = Integer.parseInt(jsonDonor.getString("numberDonations"));

            char genderDonor = gender.charAt(0);

            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date birthdateDonor = new Date();
            Date lastDonationDonor = null;
            try {
                birthdateDonor = format.parse(birthdate);
                lastDonationDonor = format.parse(lastDonation);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            User donor = null;
            try {
                donor = new User(name, login, pass, state, city, birthdateDonor, lastDonationDonor, genderDonor, typeOfBlood, numberDonations);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                JSONArray jsonForms = jsonDonor.getJSONArray("forms");
                String loginDest = "";
                String patient = "";
                String hospital = "";
                String city_patient = "";
                String state_patient = "";
                String blood_type_patient = "";
                String deadline_date = "";
//                Date deadline = new Date();

                Form form;
                for (int j = 0; j < jsonForms.length(); j++) {
                    JSONObject formjson = jsonForms.getJSONObject(j);
                    // String senderLogin = formjson.getString("senderLogin");
                    loginDest = formjson.getString("receiverLogin");
                    patient = formjson.getString("patientName");
                    hospital = formjson.getString("hospitalName");
                    city_patient = formjson.getString("city");
                    state_patient = formjson.getString("state");
                    blood_type_patient = formjson.getString("bloodType");
    //                deadline_date = formjson.getString("deadline");
    //
    //                try {
    //                    deadline = format.parse(deadline_date);
    //                } catch (ParseException e) {
    //                    e.printStackTrace();
    //                }
                    try {
    //                    form = new Form(patient, hospital, city_patient, state_patient, blood_type_patient, deadline);
                        form = new Form(patient, hospital, city_patient, state_patient, blood_type_patient);
                        donor.addForm(form);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
             }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            listDonors.add(donor);

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
