package projeto.les.doevida.doevida.models;

import java.util.Date;

public class Form {

    private String patientName, hospitalName, city;
    private String typeOfBlood;
    private Date deadline;

    public Form(String patientName, String hospitalName, String city, String typeOfBlood, Date deadline) throws Exception {
        if(patientName == null || patientName.equals("")){
            throw new Exception("Patient name is invalid.");
        }
        if(hospitalName == null || hospitalName.equals("")){
            throw new Exception("Hospital name is invalid.");
        }
        if(city == null || city.equals("")){
            throw new Exception("City is invalid.");
        }
        if(typeOfBlood == null){
            throw new Exception("Type of blood is null.");
        }
        if(deadline == null){
            throw new Exception("Deadline is null.");
        }
        this.patientName = patientName;
        this.hospitalName = hospitalName;
        this.city = city;
        this.typeOfBlood = typeOfBlood;
        this.deadline = deadline;
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

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) throws Exception {
        if(hospitalName == null || hospitalName.equals("")){
            throw new Exception("Hospital name is invalid.");
        }
        this.hospitalName = hospitalName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) throws Exception {
        if(city == null || city.equals("")){
            throw new Exception("City is invalid.");
        }
        this.city = city;
    }

    public String getTypeOfBlood() {
        return typeOfBlood;
    }

    public void setTypeOfBlood(String typeOfBlood) throws Exception {
        if(typeOfBlood == null){
            throw new Exception("Type of blood is null.");
        }
        this.typeOfBlood = typeOfBlood;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) throws Exception {
        if(deadline == null){
            throw new Exception("Deadline is null.");
        }
        this.deadline = deadline;
    }
}
