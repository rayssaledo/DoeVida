package projeto.les.doevida.doevida.models;

import java.io.Serializable;

/**
 * Created by Lucas on 05/05/2016.
 */
public class BodyNotification implements Serializable {

    private String loginDest;
    private String donorName;
    private String bloodTypeDonor;
    private String patientName;
    private String bloodTypePatient;

    public BodyNotification(String loginDest, String donorName, String bloodTypeDonor, String patientName, String bloodTypePatient) throws Exception {
        if(loginDest == null || loginDest.equals("")){
            throw new Exception("Login receiver is invalid.");
        }
        if(donorName == null || donorName.equals("")){
            throw new Exception("Donor name is invalid.");
        }
        if(bloodTypeDonor == null || bloodTypeDonor.equals("")){
            throw new Exception("Blood type donor is invalid.");
        }
        if(patientName == null || patientName.equals("")){
            throw new Exception("Patient name is invalid.");
        }
        if(bloodTypePatient == null || bloodTypePatient.equals("")){
            throw new Exception("Blood type patient is invalid.");
        }
        this.loginDest = loginDest;
        this.donorName = donorName;
        this.bloodTypeDonor = bloodTypeDonor;
        this.patientName = patientName;
        this.bloodTypePatient = bloodTypePatient;
    }

    public String getLoginDest() {
        return loginDest;
    }

    public void setLoginDest(String loginDest) throws Exception {
        if(loginDest == null || loginDest.equals("")){
            throw new Exception("Login receiver is invalid.");
        }
        this.loginDest = loginDest;
    }

    public String getDonorName() {
        return donorName;
    }

    public void setDonorName(String donorName) throws Exception {
        if(donorName == null || donorName.equals("")){
            throw new Exception("Donor name is invalid.");
        }
        this.donorName = donorName;
    }

    public String getBloodTypeDonor() {
        return bloodTypeDonor;
    }

    public void setBloodTypeDonor(String bloodTypeDonor) throws Exception {
        if(bloodTypeDonor == null || bloodTypeDonor.equals("")){
            throw new Exception("Blood type donor is invalid.");
        }
        this.bloodTypeDonor = bloodTypeDonor;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) throws Exception {
        if(patientName == null || patientName.equals("")){
            throw new Exception("Patient name is invalid.");
        }
        this.patientName = patientName;
    }

    public String getBloodTypePatient() {
        return bloodTypePatient;
    }

    public void setBloodTypePatient(String bloodTypePatient) throws Exception {
        if(bloodTypePatient == null || bloodTypePatient.equals("")){
            throw new Exception("Blood type petient is invalid.");
        }
        this.bloodTypePatient = bloodTypePatient;
    }

}
