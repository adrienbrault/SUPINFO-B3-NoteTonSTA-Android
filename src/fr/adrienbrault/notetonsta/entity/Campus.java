package fr.adrienbrault.notetonsta.entity;

import java.util.List;

/**
 * Author: Adrien Brault
 * Date: 09/04/12
 * Time: 17:17
 */

public class Campus {

    protected Integer id;

    protected String name;

    protected List<Intervention> interventions;

    public Campus() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Intervention> getInterventions() {
        return interventions;
    }

    public void setInterventions(List<Intervention> interventions) {
        this.interventions = interventions;
    }

}
