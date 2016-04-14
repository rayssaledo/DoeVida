package projeto.les.doevida.doevida;

import android.test.AndroidTestCase;

import junit.framework.Assert;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import projeto.les.doevida.doevida.Utils.MySharedPreferences;
import projeto.les.doevida.doevida.models.User;

/**
 * Created by Queiroz on 13/04/2016.
 */
public class RegisterUserTest extends AndroidTestCase {

    private MySharedPreferences mySharedPreferences;
    private HashMap<String, String> userDetails;

    public void setUp() {

        mySharedPreferences = new MySharedPreferences(getContext());
        userDetails = mySharedPreferences.getUserDetails();
    }

    public void testLogin() throws Exception {
        Assert.assertTrue(mySharedPreferences.isUserLoggedIn());
        Assert.assertEquals(mySharedPreferences.isUserLoggedIn(), true);
        Assert.assertEquals(!mySharedPreferences.isUserLoggedIn(), false);
        String name = userDetails.get(MySharedPreferences.KEY_NAME_USER);
        String state = userDetails.get(MySharedPreferences.KEY_STATE_USER);
        String city = userDetails.get(MySharedPreferences.KEY_CITY_USER);
        String username = userDetails.get(MySharedPreferences.KEY_USERNAME_USER);
        String password = userDetails.get(MySharedPreferences.KEY_PASSWORD_USER);
        String blood_type = userDetails.get(MySharedPreferences.KEY_BLOOD_TYPE_USER);
        String date_birth = userDetails.get(MySharedPreferences.KEY_DATE_BIRTH_USER);
        String date_donation = userDetails.get(MySharedPreferences.KEY_DATE_DONATION_USER);
        Character gender = userDetails.get(MySharedPreferences.KEY_GENDER_USER).charAt(0);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        Date dateLastDonation = null;
        if (!date_donation.equals("")) {
            formatter.parse(date_donation);
        }

        User user = new User(name, username, password, state, city, formatter.parse(date_birth),
                dateLastDonation, gender, blood_type);

        Assert.assertEquals(name, user.getName());
        Assert.assertEquals(state, user.getState());
        Assert.assertEquals(city, user.getCity());
        Assert.assertEquals(username, user.getUserame());
        Assert.assertEquals(password, user.getPassword());
        Assert.assertEquals(blood_type, user.getTypeOfBlood());
        Assert.assertEquals(gender, user.getGender());
        Assert.assertEquals(formatter.parse(date_birth), user.getBirthdate());


    }
}
