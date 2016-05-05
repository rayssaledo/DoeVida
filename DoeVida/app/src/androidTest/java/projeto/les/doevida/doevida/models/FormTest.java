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

    private Form form;
    private DateFormat formatter;
    private Date today, deadline;

    @Before
    public void setUp() throws Exception {
        formatter = new SimpleDateFormat("MM/dd/yy");
        deadline = formatter.parse("25/04/2016");
        today = new Date();

        form = new Form("jefferson Ricelle Silva Nascimento", "Hemocentro de Campina Grande", "Campina Grande", "+O", deadline);
    }

    @Test
    public void testConstructor() {
        try{
            new Form(null, "Hemocentro de Campina Grande", "Campina Grande", "+O", deadline);
        }catch(Exception e){
            assertEquals("Patient name is invalid.", e.getMessage());
        }
        try{
            new Form("", "Hemocentro de Campina Grande", "Campina Grande", "+O", deadline);
        }catch(Exception e){
            assertEquals("Patient name is invalid.", e.getMessage());
        }
        try{
            new Form("jefferson Ricelle Silva Nascimento", null, "Campina Grande", "+O", deadline);
        }catch(Exception e){
            assertEquals("Hospital name is invalid.", e.getMessage());
        }
        try{
            new Form("jefferson Ricelle Silva Nascimento", "", "Campina Grande", "+O", deadline);
        }catch(Exception e){
            assertEquals("Hospital name is invalid.", e.getMessage());
        }
        try{
            new Form("jefferson Ricelle Silva Nascimento", "Hemocentro de Campina Grande", null, "+O", deadline);
        }catch(Exception e){
            assertEquals("City is invalid.", e.getMessage());
        }
        try{
            new Form("jefferson Ricelle Silva Nascimento", "Hemocentro de Campina Grande", "", "+O", deadline);
        }catch(Exception e){
            assertEquals("City is invalid.", e.getMessage());
        }
        try{
            new Form("jefferson Ricelle Silva Nascimento", "Hemocentro de Campina Grande", "Campina Grande", null, deadline);
        }catch(Exception e){
            assertEquals("Type of blood is invalid.", e.getMessage());
        }
        try{
            new Form("jefferson Ricelle Silva Nascimento", "Hemocentro de Campina Grande", "Campina Grande", "", deadline);
        }catch(Exception e){
            assertEquals("Type of blood is invalid.", e.getMessage());
        }
        try{
            new Form("jefferson Ricelle Silva Nascimento", "Hemocentro de Campina Grande", "Campina Grande", "+O", null);
        }catch(Exception e){
            assertEquals("Deadline is null.", e.getMessage());
        }
    }

    @Test
    public void testGetsForm() {
        assertNotSame(form.getPatientName(), new String("Marcos Nascimento"));
        assertNotSame(form.getHospitalName(), new String("Hospital"));
        assertNotSame(form.getCity(), new String("Cidade"));
        assertNotSame(form.getTypeOfBlood(), new String("+o"));
        assertNotSame(form.getDeadline(), today);

        assertEquals(form.getPatientName(), new String("jefferson Ricelle Silva Nascimento"));
        assertEquals(form.getHospitalName(), new String("Hemocentro de Campina Grande"));
        assertEquals(form.getCity(), new String("Campina Grande"));
        assertEquals(form.getTypeOfBlood(), new String("+O"));
        assertEquals(form.getDeadline(), deadline);
    }

    @Test
    public void testSetsForm() throws Exception {
        form.setPatientName("Marcos Antônio Silva Nascimento");
        form.setHospitalName("Hospital de Trauma de Campina Grande");
        form.setCity("Campina Grande");
        form.setTypeOfBlood("-O");
        form.setDeadline(today);

        assertNotSame(form.getPatientName(), new String("jefferson Ricelle Silva Nascimento"));
        assertNotSame(form.getHospitalName(), new String("Hemocentro de Campina Grande"));
        assertNotSame(form.getCity(), new String("João Pessoa"));
        assertNotSame(form.getHospitalName(), new String("+O"));
        assertNotSame(form.getDeadline(), deadline);

        assertEquals(form.getPatientName(), new String("Marcos Antônio Silva Nascimento"));
        assertEquals(form.getHospitalName(), new String("Hospital de Trauma de Campina Grande"));
        assertEquals(form.getCity(), new String("Campina Grande"));
        assertEquals(form.getTypeOfBlood(), new String("-O"));
        assertEquals(form.getDeadline(), today);
    }
}
