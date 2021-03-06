package org.endeavourhealth.cim.camel.helpers;

public class TokenSearchParameter {

    public TokenSearchParameter() {
    }

    public TokenSearchParameter(String queryString) {

        if (queryString.contains("|")) {
            String[] parts = queryString.split("\\|", -1);

            System = parts[0];
            Code = parts[1];
        }
        else {
            Code = queryString;
        }

    }

    private String Code;
    private String System;

    public String getSystem() {
        return System;
    }

    public void setSystem(String system) {
        System = system;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
