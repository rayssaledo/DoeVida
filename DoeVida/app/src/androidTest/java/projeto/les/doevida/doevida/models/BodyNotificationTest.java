package projeto.les.doevida.doevida.models;

import android.test.AndroidTestCase;

import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Marcos N. on 5/5/2016.
 */
public class BodyNotificationTest extends AndroidTestCase {

    private BodyNotification bodyNotification;

    private User user;
    private User user2;

    private Form form;
    private Form form2;

    private Date today;
    private Date birthdate;
    private Date deadline;

    private DateFormat formatter;

    @Before
    public void setUp() throws Exception {
        formatter = new SimpleDateFormat("MM/dd/yy");
        deadline = formatter.parse("25/04/2016");
        birthdate = formatter.parse("29/09/1991");
        today = new Date();

        user = new User("Marcos Nascimento", "marcosn", "mypassiseasy", "Paraíba", "Campina Grande",
                birthdate, today, 'M', "+A");
        user2 = new User("Ricardo Toledo Sampaio", "ricardots", "mypassiseasy", "Paraíba", "Campina Grande",
                birthdate, today, 'M', "+O");
        form = new Form(user2.getUsername(), "jefferson Ricelle Silva Nascimento", "Hemocentro de Campina Grande",
                "Campina Grande", "Paraíba", "+O", deadline);
        form2 = new Form(user.getUsername(), "Juliana Amorin Santos", "Hemocentro de Campina Grande",
                "João Pessoa", "Paraíba", "-A", deadline);

        bodyNotification = new BodyNotification(user.getUsername(),user2.getName(),user2.getTypeOfBlood(),
                form.getPatientName(),form.getTypeOfBlood());
    }

    @Test
    public void testConstructor() {
        try{
            new BodyNotification(null, user2.getName(),user2.getTypeOfBlood(),
                    form.getPatientName(),form.getTypeOfBlood());
        }catch (Exception e){
            assertEquals("Login receiver is invalid.", e.getMessage());
        }
        try{
            new BodyNotification("", user2.getName(),user2.getTypeOfBlood(),
                    form.getPatientName(),form.getTypeOfBlood());
        }catch (Exception e){
            assertEquals("Login receiver is invalid.", e.getMessage());
        }
        try{
            new BodyNotification(user.getUsername(), null, user2.getTypeOfBlood(),
                    form.getPatientName(),form.getTypeOfBlood());
        }catch (Exception e){
            assertEquals("Donor name is invalid.", e.getMessage());
        }
        try{
            new BodyNotification(user.getUsername(), "", user2.getTypeOfBlood(),
                    form.getPatientName(),form.getTypeOfBlood());
        }catch (Exception e){
            assertEquals("Donor name is invalid.", e.getMessage());
        }
        try{
            new BodyNotification(user.getUsername(), user2.getName(), null,
                    form.getPatientName(),form.getTypeOfBlood());
        }catch (Exception e){
            assertEquals("Blood type donor is invalid.", e.getMessage());
        }
        try{
            new BodyNotification(user.getUsername(), user2.getName(), "",
                    form.getPatientName(),form.getTypeOfBlood());
        }catch (Exception e){
            assertEquals("Blood type donor is invalid.", e.getMessage());
        }
        try{
            new BodyNotification(user.getUsername(),user2.getName(),user2.getTypeOfBlood(),
                    null, form.getTypeOfBlood());
        }catch (Exception e){
            assertEquals("Patient name is invalid.", e.getMessage());
        }
        try{
            new BodyNotification(user.getUsername(),user2.getName(),user2.getTypeOfBlood(),
                    "", form.getTypeOfBlood());
        }catch (Exception e){
            assertEquals("Patient name is invalid.", e.getMessage());
        }
        try{
            new BodyNotification(user.getUsername(),user2.getName(),user2.getTypeOfBlood(),
                    form.getPatientName(), null);
        }catch (Exception e){
            assertEquals("Blood type patient is invalid.", e.getMessage());
        }
        try{
            new BodyNotification(user.getUsername(),user2.getName(),user2.getTypeOfBlood(),
                    form.getPatientName(), "");
        }catch (Exception e){
            assertEquals("Blood type patient is invalid.", e.getMessage());
        }
    }

    @Test
    public void testGetsBodyNotification() {
        assertNotSame(bodyNotification.getLoginDest(), user2.getUsername());
        assertNotSame(bodyNotification.getDonorName(), user.getName());
        assertNotSame(bodyNotification.getBloodTypeDonor(), user.getTypeOfBlood());
        assertNotSame(bodyNotification.getPatientName(), new String("Juliana Amorin"));
        assertNotSame(bodyNotification.getBloodTypePatient(), user.getTypeOfBlood());

        assertEquals(bodyNotification.getLoginDest(), user.getUsername());
        assertEquals(bodyNotification.getDonorName(), user2.getName());
        assertEquals(bodyNotification.getBloodTypeDonor(), user2.getTypeOfBlood());
        assertEquals(bodyNotification.getPatientName(), form.getPatientName());
        assertEquals(bodyNotification.getBloodTypePatient(), form.getTypeOfBlood());
    }

    @Test
    public void testSetsBodyNotification()throws Exception {
        bodyNotification.setLoginDest(user2.getUsername());
        bodyNotification.setDonorName(user.getName());
        bodyNotification.setBloodTypeDonor(user.getTypeOfBlood());
        bodyNotification.setPatientName(form2.getPatientName());
        bodyNotification.setBloodTypePatient(form2.getTypeOfBlood());

        assertNotSame(bodyNotification.getLoginDest(), user.getUsername());
        assertNotSame(bodyNotification.getDonorName(), user2.getName());
        assertNotSame(bodyNotification.getBloodTypeDonor(), user2.getTypeOfBlood());
        assertNotSame(bodyNotification.getPatientName(), form.getPatientName());
        assertNotSame(bodyNotification.getBloodTypePatient(), form.getTypeOfBlood());

        assertEquals(bodyNotification.getLoginDest(), user2.getUsername());
        assertEquals(bodyNotification.getDonorName(), user.getName());
        assertEquals(bodyNotification.getBloodTypeDonor(), user.getTypeOfBlood());
        assertEquals(bodyNotification.getPatientName(), form2.getPatientName());
        assertEquals(bodyNotification.getBloodTypePatient(), form2.getTypeOfBlood());
    }
}
