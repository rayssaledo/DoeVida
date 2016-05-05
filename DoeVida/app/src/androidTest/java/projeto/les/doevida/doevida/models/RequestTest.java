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
public class RequestTest extends AndroidTestCase {

    private Request request;
    private String requesterName;
    private DateFormat formatter;
    private Date today, deadline;

    @Before
    public void setUp() throws Exception {
        formatter = new SimpleDateFormat("MM/dd/yy");
        deadline = formatter.parse("25/04/2016");
        today = new Date();
        requesterName = new String("Marcos Antônio Silva Nascimento");
        request = new Request(requesterName, deadline);
    }

    @Test
    public void testConstructor() {
        try {
            new Request(null, deadline);
        } catch (Exception e) {
            assertEquals("Nome do solicitante é obrigatório!", e.getMessage());
        }
        try {
            new Request("", deadline);
        } catch (Exception e) {
            assertEquals("Nome do solicitante é obrigatório!", e.getMessage());
        }
        try {
            new Request("Marcos Antônio Silva Nascimento", null);
        } catch (Exception e) {
            assertEquals("Data da solicitação é obrigatória!", e.getMessage());
        }
    }

    @Test
    public void testGetsRequest() {
        assertNotSame(request.getRequesterName(), new String("Marcos Nascimento"));
        assertNotSame(request.getRequestDate(), today);
        assertNotSame(request.getRequesterName(), new String("Marcos Antônio Nascimento"));
        assertNotSame(request.getRequestDate(), new Date());

        assertEquals(request.getRequesterName(), new String("Marcos Antônio Silva Nascimento"));
        assertEquals(request.getRequestDate(), deadline);
    }

    @Test
    public void testSetsRequest() throws Exception {
        request.setRequesterName("Marcos Antônio Silva Nascimento");
        request.setRequestDate(today);

        assertNotSame(request.getRequesterName(), new String("Jefferson Ricelle Silva Nascimento"));
        assertNotSame(request.getRequestDate(), deadline);

        assertEquals(request.getRequesterName(), new String("Marcos Antônio Silva Nascimento"));
        assertEquals(request.getRequestDate(), today);
    }
}