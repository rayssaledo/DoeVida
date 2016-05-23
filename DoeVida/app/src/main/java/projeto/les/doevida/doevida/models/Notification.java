package projeto.les.doevida.doevida.models;


import java.util.Date;

public class Notification {

    private Date date;
    private Form form;
    private String title;
    private String senderLogin;
    private String receiverLogin;
    private String donor;
    private String patient;
    private String bloodTypePatient;
    private String bloodTypeDonor;

    public Notification(String senderLogin, String receiverLogin, String title, Date date, Form form){
        this.senderLogin = senderLogin;
        this.receiverLogin = receiverLogin;
        this.title = title;
        this.date = date;
        this.form = form;

    }

    public Notification(String senderLogin, String receiverLogin, String title, Date date,
                        String donor, String patient, String bloodTypePatient, String bloodTypeDonor ){

        this.senderLogin = senderLogin;
        this.receiverLogin = receiverLogin;
        this.title = title;
        this.date = date;
        this.donor = donor;
        this.patient = patient;
        this.bloodTypePatient = bloodTypePatient;
        this.bloodTypeDonor = bloodTypeDonor;

    }

    public Notification(String senderLogin, String receiverLogin, String title, Date date,
                        String donor, String patient, String bloodTypePatient){

        this.senderLogin = senderLogin;
        this.receiverLogin = receiverLogin;
        this.title = title;
        this.date = date;
        this.donor = donor;
        this.patient = patient;
        this.bloodTypePatient = bloodTypePatient;

    }


    public String getTitle(){
        return title;
    }

    public Form getForm(){
        return form;
    }

    public Date getDate(){
        return date;
    }

    public String getSenderLogin(){
        return senderLogin;
    }

    public String getReceiverLogin(){
        return receiverLogin;
    }

    public String getDonorName(){
        return donor;
    }

    public String getPatientName(){
        return patient;
    }

    public String getBloodTypePatient(){
        return  bloodTypePatient;
    }
    public String getBloodTypeDonor(){
        return getBloodTypeDonor();
    }
}
