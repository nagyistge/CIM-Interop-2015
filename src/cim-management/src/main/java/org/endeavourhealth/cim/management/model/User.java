package org.endeavourhealth.cim.management.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {
    private String name;
    private String email;
    private String organisation;
    private String purpose;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
}
