package projeto.les.doevida.doevida.models;

import java.util.Date;

/**
 * Created by Andreza on 16/04/2016.
 */
public class Request {

    private String requesterName;
    private Date requestDate;

    public Request (String requesterName, Date requestDate) throws Exception {
        setRequesterName(requesterName);
        setRequestDate(requestDate);
    }

    public void setRequesterName (String requesterName) throws Exception {
        if (requesterName == null || requesterName.equals("")) {
            throw new Exception("Nome do solicitante e obrigatório!");
        }
        this.requesterName = requesterName;
    }

    public String getRequesterName () {
        return this.requesterName;
    }

    public void setRequestDate (Date requestDate) throws Exception {
        if (requestDate == null) {
            throw new Exception("Data da solicitação é obrigatória!");
        }
        this.requestDate = requestDate;
    }

    public Date getRequestDate () {
        return this.requestDate;
    }

}
