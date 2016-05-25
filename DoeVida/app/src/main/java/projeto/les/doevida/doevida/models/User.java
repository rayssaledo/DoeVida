package projeto.les.doevida.doevida.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Marcos Nascimento on 4/2/2016.
 */
public class User implements Serializable{

    private String name, username, password, state, city;
    private Date birthdate, lastDonation;
    private Character gender;
    private String typeOfBlood;
    private Boolean canDonate;
    private List<Form> forms;
    private List<Request> myRequests;
    private Integer numberDonations;

    public User(String name, String username, String password, String state, String city, Date birthdate,
                Date lastDonation, Character gender, String typeOfBlood, Integer numberDonations) throws Exception {
        if(name == null || name.equals("")){
            throw new Exception("Name is invalid.");
        }
        if(username == null || username.equals("")){
            throw new Exception("Username is invalid.");
        }
        if(password == null || password.equals("")){
            throw new Exception("Password is invalid.");
        }
        if(state == null || state.equals("")){
            throw new Exception("State is invalid.");
        }
        if(city == null || city.equals("")){
            throw new Exception("City is invalid.");
        }
        if(birthdate == null){
            throw new Exception("Birthdate is null.");
        }
        if(gender == null){
            throw new Exception("Gender is null.");
        }
        if(typeOfBlood == null || typeOfBlood.equals("")){
            throw new Exception("Type of blood is invalid.");
        }
        if(birthdate != null && lastDonation != null && birthdate.equals(lastDonation)){
            throw new Exception("Last donation date is invalid.");
        }
        this.name = name;
        this.username = username;
        this.password = password;
        this.state = state;
        this.city = city;
        this.birthdate = birthdate;
        this.lastDonation = lastDonation;
        this.gender = gender;
        this.typeOfBlood = typeOfBlood;
        this.forms = new ArrayList<Form>();
        this.myRequests =  new ArrayList<Request>();
        this.canDonate = false;
        this.numberDonations = numberDonations;

    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public Date getLastDonation() {
        return lastDonation;
    }

    public String getTypeOfBlood() {
        return typeOfBlood;
    }

    public Character getGender() {
        return gender;
    }

    public Boolean getCanDonate() {
        //TODO
        return canDonate;
    }

    public int getAge(){
        //TODO
        return 0;
    }

    public List<Form> getForms(){
        return forms;
    }

    public void setGender(Character gender) throws Exception {
        if(gender == null){
            throw new Exception("Gender is invalid.");
        }
        this.gender = gender;
    }

    public void setTypeOfBlood(String typeOfBlood) throws Exception {
        if(typeOfBlood == null || typeOfBlood.equals("")){
            throw new Exception("Type of blood is invalid.");
        }
        this.typeOfBlood = typeOfBlood;
    }

    public void setName(String name) throws Exception {
        if(name == null || name.equals("")){
            throw new Exception("Name is invalid.");
        }
        this.name = name;
    }

    public void setUsername(String username) throws Exception {
        if(username == null || username.equals("")){
            throw new Exception("Username is invalid.");
        }
        this.username = username;
    }

    public void setPassword(String password) throws Exception {
        if(password == null || password.equals("")){
            throw new Exception("Password is invalid.");
        }
        this.password = password;
    }

    public void setState(String state) throws Exception {
        if(state == null || state.equals("")){
            throw new Exception("State is invalid.");
        }

        this.state = state;
    }

    public void setCity(String city) throws Exception {
        if(city == null || city.equals("")){
            throw new Exception("City is invalid.");
        }
        this.city = city;
    }

    public void setBirthdate(Date birthdate) throws Exception {
        if(birthdate == null){
            throw new Exception("Birthdate is null.");
        }
        this.birthdate = birthdate;
    }

    public void setLastDonation(Date lastDonation) throws Exception {
        if(lastDonation == null){
            throw new Exception("Last donation is null.");
        }
        this.lastDonation = lastDonation;
    }

    public void addForm(Form form) throws Exception {
        if(form == null){
            throw new Exception("Form is null.");
        }
        forms.add(form);
    }

    public void setMyRequests (List<Request> requests) {
        this.myRequests = requests;
    }

    public List<Request> getMyRequests () {
        return this.myRequests;
    }

    public void addRequest (String requesterName, Date date) throws Exception {
        this.myRequests.add(new Request(requesterName, date));
    }

    public Integer getNumberDonations() {
        return numberDonations;
    }

    public void setNumberDonations(Integer numberDonations) {
        this.numberDonations = numberDonations;
    }
}