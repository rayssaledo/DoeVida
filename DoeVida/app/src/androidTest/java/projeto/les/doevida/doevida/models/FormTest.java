package projeto.les.doevida.doevida.models;

import android.test.AndroidTestCase;

import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Marcos N. on 5/4/2016.
 */
public class FormTest extends AndroidTestCase {

    private User user, user2;
    private Form form;
    private DateFormat formatter;
    private Date today, deadline, birthdate;

    @Before
    public void setUp() throws Exception {
        formatter = new SimpleDateFormat("MM/dd/yy");
        birthdate = formatter.parse("29/09/1991");
        deadline = formatter.parse("25/04/2016");
        today = new Date();

        user = new User("Marcos Nascimento", "marcosn", "mypassiseasy", "Paraíba", "Campina Grande", birthdate,
                today, 'M', "+O");
        user2 = new User("Ricardo Toledo Sampaio", "ricardots", "mypassiseasy", "Paraíba", "Campina Grande", birthdate,
                today, 'M', "+O");
        form = new Form(user2.getUsername(),"jefferson Ricelle Silva Nascimento", "Hemocentro de Campina Grande",
                "Campina Grande", "Paraíba", "+O", deadline);
    }

    @Test
    public void testConstructor() {
        try{
            new Form(null,"jefferson Ricelle Silva Nascimento", "Hemocentro de Campina Grande",
                    "Campina Grande", "Paraíba", "+O", deadline);
        }catch(Exception e){
            assertEquals("Login receiver is invalid.", e.getMessage());
        }
        try{
            new Form("","jefferson Ricelle Silva Nascimento", "Hemocentro de Campina Grande",
                    "Campina Grande", "Paraíba", "+O", deadline);
        }catch(Exception e){
            assertEquals("Login receiver is invalid.", e.getMessage());
        }
        try{
            new Form(user2.getUsername(), null, "Hemocentro de Campina Grande",
                    "Campina Grande", "Paraíba", "+O", deadline);
        }catch(Exception e){
            assertEquals("Patient name is invalid.", e.getMessage());
        }
        try{
            new Form(user2.getUsername(), "", "Hemocentro de Campina Grande",
                    "Campina Grande", "Paraíba", "+O", deadline);
        }catch(Exception e){
            assertEquals("Patient name is invalid.", e.getMessage());
        }
        try{
            new Form(user2.getUsername(),"jefferson Ricelle Silva Nascimento", null,
                    "Campina Grande", "Paraíba", "+O", deadline);
        }catch (Exception e){
            assertEquals("Hospital name is invalid.", e.getMessage());
        }
        try{
            new Form(user2.getUsername(),"jefferson Ricelle Silva Nascimento", "",
                    "Campina Grande", "Paraíba", "+O", deadline);
        }catch (Exception e){
            assertEquals("Hospital name is invalid.", e.getMessage());
        }
        try{
            new Form(user2.getUsername(), "jefferson Ricelle Silva Nascimento",
                    "Hemocentro de Campina Grande", null, "Paraíba", "+O", deadline);
        }catch(Exception e){
            assertEquals("City is invalid.", e.getMessage());
        }
        try{
            new Form(user2.getUsername(), "jefferson Ricelle Silva Nascimento",
                    "Hemocentro de Campina Grande", "", "Paraíba", "+O", deadline);
        }catch(Exception e){
            assertEquals("City is invalid.", e.getMessage());
        }
        try{
            new Form(user2.getUsername(), "jefferson Ricelle Silva Nascimento",
                    "Hemocentro de Campina Grande", "Campina Grande", null, "+O", deadline);
        }catch(Exception e){
            assertEquals("State is invalid.", e.getMessage());
        }
        try{
            new Form(user2.getUsername(), "jefferson Ricelle Silva Nascimento",
                    "Hemocentro de Campina Grande", "Campina Grande", "", "+O", deadline);
        }catch(Exception e){
            assertEquals("State is invalid.", e.getMessage());
        }
        try{
            new Form(user2.getUsername(), "jefferson Ricelle Silva Nascimento", "Hemocentro de Campina Grande",
                    "Campina Grande", "Paraíba", null, deadline);
        }catch(Exception e){
            assertEquals("Type of blood is invalid.", e.getMessage());
        }
        try{
            new Form(user2.getUsername(), "jefferson Ricelle Silva Nascimento", "Hemocentro de Campina Grande",
                    "Campina Grande", "Paraíba", "", deadline);
        }catch(Exception e){
            assertEquals("Type of blood is invalid.", e.getMessage());
        }
        try{
            new Form(user2.getUsername(), "jefferson Ricelle Silva Nascimento", "Hemocentro de Campina Grande",
                    "Campina Grande", "Paraíba", "+O", null);
        }catch(Exception e){
            assertEquals("Deadline is null.", e.getMessage());
        }
    }

    @Test
    public void testGetsForm() {
        assertNotSame(form.getLoginDest(), user.getUsername());
        assertNotSame(form.getPatientName(), new String("Marcos Nascimento"));
        assertNotSame(form.getHospitalName(), new String("Hospital"));
        assertNotSame(form.getCity(), new String("Cidade"));
        assertNotSame(form.getState(), new String("Estado"));
        assertNotSame(form.getTypeOfBlood(), new String("+o"));
        assertNotSame(form.getDeadline(), today);

        assertEquals(form.getLoginDest(), user2.getUsername());
        assertEquals(form.getPatientName(), new String("jefferson Ricelle Silva Nascimento"));
        assertEquals(form.getHospitalName(), new String("Hemocentro de Campina Grande"));
        assertEquals(form.getCity(), new String("Campina Grande"));
        assertEquals(form.getState(), new String("Paraíba"));
        assertEquals(form.getTypeOfBlood(), new String("+O"));
        assertEquals(form.getDeadline(), deadline);
    }

    @Test
    public void testSetsForm() throws Exception {
        form.setLoginDest("leticiabb");
        form.setPatientName("Marcos Antônio Silva Nascimento");
        form.setHospitalName("Hospital de Trauma de Campina Grande");
        form.setCity("Campina Grande");
        form.setState("Paraíba");
        form.setTypeOfBlood("-O");
        form.setDeadline(today);

        assertNotSame(form.getLoginDest(), user2.getUsername());
        assertNotSame(form.getPatientName(), new String("jefferson Ricelle Silva Nascimento"));
        assertNotSame(form.getHospitalName(), new String("Hemocentro de Campina Grande"));
        assertNotSame(form.getCity(), new String("João Pessoa"));
        assertNotSame(form.getState(), new String("Pernambuco"));
        assertNotSame(form.getHospitalName(), new String("+O"));
        assertNotSame(form.getDeadline(), deadline);

        assertEquals(form.getLoginDest(), new String("leticiabb"));
        assertEquals(form.getPatientName(), new String("Marcos Antônio Silva Nascimento"));
        assertEquals(form.getHospitalName(), new String("Hospital de Trauma de Campina Grande"));
        assertEquals(form.getCity(), new String("Campina Grande"));
        assertEquals(form.getState(), new String("Paraíba"));
        assertEquals(form.getTypeOfBlood(), new String("-O"));
        assertEquals(form.getDeadline(), today);
    }
}
