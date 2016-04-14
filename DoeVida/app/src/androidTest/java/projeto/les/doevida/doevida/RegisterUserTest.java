package projeto.les.doevida.doevida;

import android.test.AndroidTestCase;

import junit.framework.Assert;

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
        String gender = userDetails.get(MySharedPreferences.KEY_GENDER_USER);
        //PEGAR DO userDetails todos os parâmetros do usuário
        //Montar um usuário User user = User(...); com os parâmetros acima, lembrar de converter

        Assert.assertEquals(name, "Luana Pinto"); //Em vez de  name colocar user.getName()
        Assert.assertEquals(state, "Paraiba");//Em vez de state colocar user.getStae
        Assert.assertEquals(city, "Campina Grande");
        Assert.assertEquals(username, "luaninha");
        Assert.assertEquals(password, "123456");
        Assert.assertEquals(blood_type, "A+");
        Assert.assertEquals(gender, "F");
        Assert.assertEquals(date_birth, "30/05/1995");
        Assert.assertEquals(date_donation, "30/05/2015");

    }
}
