package fr.adrienbrault.notetonsta.entity;

import java.util.List;

/**
 * Author: Adrien Brault
 * Date: 09/04/12
 * Time: 17:21
 */

public class Speaker {

    protected Integer id;

    protected String email;

    protected String firstName;

    protected String lastName;

    protected List<Intervention> interventions;

    public Speaker() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Intervention> getInterventions() {
        return interventions;
    }

    public void setInterventions(List<Intervention> interventions) {
        this.interventions = interventions;
    }

}
