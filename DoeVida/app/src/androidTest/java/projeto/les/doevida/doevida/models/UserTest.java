package projeto.les.doevida.doevida.models;

import android.test.AndroidTestCase;

import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Marcos N. on 4/18/2016.
 */
public class UserTest extends AndroidTestCase {

    private User user;
    private Form form;
    private DateFormat formatter;
    private Date birthdate, today, deadline;
    private Request request;

    @Before
    public void setUp() throws Exception {
        formatter = new SimpleDateFormat("MM/dd/yy");
        birthdate = formatter.parse("29/09/1991");
        deadline = formatter.parse("25/04/2016");
        today = new Date();

        user = new User("Marcos Nascimento", "marcosn", "mypassiseasy", "Paraíba", "Campina Grande", birthdate,
                today, 'M', "+O");
        form = new Form("jefferson Ricelle Silva Nascimento", "Hemocentro de Campina Grande", "Campina Grande", "+O", deadline);
        request = new Request(form.getPatientName(), deadline);
    }

    @Test
    public void testConstructor() {
        try {
            new User(null, "marcosn", "mypassiseasy", "Paraíba", "Campina Grande", birthdate,
                    today, 'M', "+O");
        }catch (Exception e){
            assertEquals("Name is invalid.", e.getMessage());
        }
        try {
            new User("", "marcosn", "mypassiseasy", "Paraíba", "Campina Grande", birthdate,
                    today, 'M', "+O");
        }catch (Exception e){
            assertEquals("Name is invalid.", e.getMessage());
        }
        try {
            new User("Marcos Nascimento", null, "mypassiseasy", "Paraíba", "Campina Grande", birthdate,
                    today, 'M', "+O");
        }catch (Exception e){
            assertEquals("Username is invalid.", e.getMessage());
        }
        try {
            new User("Marcos Nascimento", "", "mypassiseasy", "Paraíba", "Campina Grande", birthdate,
                    today, 'M', "+O");
        }catch (Exception e){
            assertEquals("Username is invalid.", e.getMessage());
        }
        try {
            new User("Marcos Nascimento", "marcosn", null, "Paraíba", "Campina Grande", birthdate,
                    today, 'M', "+O");
        }catch (Exception e){
            assertEquals("Password is invalid.", e.getMessage());
        }
        try {
            new User("Marcos Nascimento", "marcosn", "", "Paraíba", "Campina Grande", birthdate,
                    today, 'M', "+O");
        }catch (Exception e){
            assertEquals("Password is invalid.", e.getMessage());
        }
        try {
            new User("Marcos Nascimento", "marcosn", "mypassiseasy", null, "Campina Grande", birthdate,
                    today, 'M', "+O");
        }catch (Exception e){
            assertEquals("State is invalid.", e.getMessage());
        }
        try {
            new User("Marcos Nascimento", "marcosn", "mypassiseasy", "", "Campina Grande", birthdate,
                    today, 'M', "+O");
        }catch (Exception e){
            assertEquals("State is invalid.", e.getMessage());
        }
        try {
            new User("Marcos Nascimento", "marcosn", "mypassiseasy","Paraíba", null, birthdate,
                    today, 'M', "+O");
        }catch (Exception e){
            assertEquals("City is invalid.", e.getMessage());
        }
        try {
            new User("Marcos Nascimento", "marcosn", "mypassiseasy", "Paraíba", "", birthdate,
                    today, 'M', "+O");
        }catch (Exception e){
            assertEquals("City is invalid.", e.getMessage());
        }
        try {
            new User("Marcos Nascimento", "marcosn", "mypassiseasy","Paraíba", "Campina Grande", null,
                    today, 'M', "+O");
        }catch (Exception e){
            assertEquals("Birthdate is null.", e.getMessage());
        }
        try {
            new User("Marcos Nascimento", "marcosn", "mypassiseasy", "Paraíba", "Campina Grande", birthdate,
                    birthdate, 'M', "+O");
        }catch (Exception e){
            assertEquals("Last donation date is invalid.", e.getMessage());
        }
        try {
            new User("Marcos Nascimento", "marcosn", "mypassiseasy", "Paraíba", "Campina Grande", birthdate,
                    today, null, "+O");
        }catch (Exception e){
            assertEquals("Gender is null.", e.getMessage());
        }
        try {
            new User("Marcos Nascimento", "marcosn", "mypassiseasy", "Paraíba", "Campina Grande", birthdate,
                    today, null, "+O");
        }catch (Exception e){
            assertEquals("Gender is null.", e.getMessage());
        }
        try {
            new User("Marcos Nascimento", "marcosn", "mypassiseasy", "Paraíba", "Campina Grande", birthdate,
                    today, 'M', null);
        }catch (Exception e){
            assertEquals("Type of blood is invalid.", e.getMessage());
        }
        try {
            new User("Marcos Nascimento", "marcosn", "mypassiseasy", "Paraíba", "Campina Grande", birthdate,
                    today, 'M', "");
        }catch (Exception e){
            assertEquals("Type of blood is invalid.", e.getMessage());
        }
    }

    @Test
    public void testGetsUser() {
        assertEquals(user.getName(), new String("Marcos Nascimento"));
        assertEquals(user.getUserame(), new String("marcosn"));
        assertEquals(user.getPassword(), new String("mypassiseasy"));
        assertEquals(user.getCity(), new String("Campina Grande"));
        assertEquals(user.getState(), new String("Paraíba"));
        assertEquals(user.getBirthdate(), birthdate);
        assertEquals(user.getLastDonation(), today);
        assertEquals(user.getGender(), new Character('M'));
        assertEquals(user.getTypeOfBlood(), new String("+O"));
        assertEquals(user.getAge(), 0);
        assertFalse(user.getCanDonate());
        assertEquals(user.getForms(), new ArrayList<Form>());
        assertEquals(user.getMyRequests(), new ArrayList<Request>());
    }

    @Test
    public void testSetsUser()  throws Exception {
        user.setName("Marcos Antônio Silva Nascimento");
        user.setUsername("marcosnas");
        user.setPassword("m4rc0s");
        user.setCity("João Pessoa");
        user.setState("PB");
        user.setBirthdate(birthdate);
        user.setLastDonation(today);
        user.setGender('M');
        user.setTypeOfBlood("-O");
        user.addForm(form);
        user.addRequest(form.getPatientName(), deadline);

        assertEquals(user.getName(), new String("Marcos Antônio Silva Nascimento"));
        assertEquals(user.getUserame(), new String("marcosnas"));
        assertEquals(user.getPassword(), new String("m4rc0s"));
        assertEquals(user.getCity(), new String("João Pessoa"));
        assertEquals(user.getState(), new String("PB"));
        assertEquals(user.getBirthdate(), birthdate);
        assertEquals(user.getLastDonation(), today);
        assertEquals(user.getGender(), new Character('M'));
        assertEquals(user.getTypeOfBlood(), new String("-O"));
        assertTrue(user.getForms().contains(form));

        assertTrue(user.getMyRequests().contains(request));
        assertTrue(user.getMyRequests().size() == 1);
        assertTrue(user.getMyRequests().contains(new Request(form.getPatientName(), deadline)));
    }
}