package org.endeavourhealth.cim.adapter;

import javax.xml.soap.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

public class MockDataAdapter implements IDataAdapter {
    private final String _soapUri = "http://localhost:9001/GPApiService/Soap";
    private final String _actionUri = "http://tempuri.org/IGPApiService";

    public String getTransformerTypeName() {
        return "org.endeavourhealth.cim.transform.openhr.OpenHRTransformer";
    }

    public String getPatientRecordByPatientId(String odsCode, UUID patientId) {
        SOAPConnection soapConnection = null;
        try {
            try {
                SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
                soapConnection = soapConnectionFactory.createConnection();

                // Create basic message
                SOAPMessage requestMessage = createSOAPRequest("GetPatient");

                // SOAP Body
                SOAPElement soapMethodElement = requestMessage.getSOAPBody().addChildElement("GetPatient", "", "http://tempuri.org/");
                SOAPElement soapMethodParamElement1 = soapMethodElement.addChildElement("patientGuid");
                soapMethodParamElement1.addTextNode(patientId.toString());
                requestMessage.saveChanges();

                // Send SOAP Message to SOAP Server
                SOAPMessage soapResponse = soapConnection.call(requestMessage, _soapUri + "/GetPatient");
                return soapResponse.getSOAPBody().getElementsByTagName("GetPatientResult").item(0).getTextContent();
            } finally {
                if (soapConnection != null)
                    soapConnection.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getPatientDemographicsByNHSNumber(String odsCode, String nhsNumber) {
        SOAPConnection soapConnection = null;
        try {
            try {
                SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
                soapConnection = soapConnectionFactory.createConnection();

                // Create basic message
                SOAPMessage requestMessage = createSOAPRequest("GetPatientDemographics");

                // SOAP Body
                SOAPElement soapMethodElement = requestMessage.getSOAPBody().addChildElement("GetPatientDemographics", "", "http://tempuri.org/");
                SOAPElement soapMethodParamElement1 = soapMethodElement.addChildElement("nhsNumber");
                soapMethodParamElement1.addTextNode(nhsNumber);
                requestMessage.saveChanges();

                // Send SOAP Message to SOAP Server
                SOAPMessage soapResponse = soapConnection.call(requestMessage, _soapUri + "/GetPatientDemographics");
                return soapResponse.getSOAPBody().getElementsByTagName("GetPatientDemographicsResult").item(0).getTextContent();
            } finally {
                if (soapConnection != null)
                    soapConnection.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String tracePatient(String surname, Date dateOfBirth, String gender) {
        SOAPConnection soapConnection = null;
        try {
            try {
                SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
                soapConnection = soapConnectionFactory.createConnection();

                // Create basic message
                SOAPMessage requestMessage = createSOAPRequest("TracePatient");

                // SOAP Body
                SOAPElement soapMethodElement = requestMessage.getSOAPBody().addChildElement("TracePatient", "", "http://tempuri.org/");
                SOAPElement criteriaElement = soapMethodElement.addChildElement("criteria");
                SOAPElement surnameElement = criteriaElement.addChildElement("Surname");
                surnameElement.addTextNode(surname);
                SOAPElement sexElement = criteriaElement.addChildElement("Sex");
                sexElement.addTextNode(gender);
                SOAPElement dobElement = criteriaElement.addChildElement("DateOfBirth");
                dobElement.addTextNode(new SimpleDateFormat("yyyy-MM-dd").format(dateOfBirth));
                requestMessage.saveChanges();

                // Send SOAP Message to SOAP Server
                SOAPMessage soapResponse = soapConnection.call(requestMessage, _soapUri + "/TracePatient");
                return soapResponse.getSOAPBody().getElementsByTagName("TracePatientResult").item(0).getTextContent();
            } finally {
                if (soapConnection != null)
                    soapConnection.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public String tracePatient(String nhsNumber) {
        SOAPConnection soapConnection = null;
        try {
            try {
                SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
                soapConnection = soapConnectionFactory.createConnection();

                // Create basic message
                SOAPMessage requestMessage = createSOAPRequest("TracePatient");

                // SOAP Body
                SOAPElement soapMethodElement = requestMessage.getSOAPBody().addChildElement("TracePatient", "", "http://tempuri.org/");
                SOAPElement criteriaElement = soapMethodElement.addChildElement("criteria");
                SOAPElement surnameElement = criteriaElement.addChildElement("NhsNumber");
                surnameElement.addTextNode(nhsNumber);
                requestMessage.saveChanges();

                // Send SOAP Message to SOAP Server
                SOAPMessage soapResponse = soapConnection.call(requestMessage, _soapUri + "/TracePatient");
                return soapResponse.getSOAPBody().getElementsByTagName("TracePatientResult").item(0).getTextContent();
            } finally {
                if (soapConnection != null)
                    soapConnection.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String createCondition(String odsCode, String requestData) {
        SOAPConnection soapConnection = null;
        try {
            try {
                SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
                soapConnection = soapConnectionFactory.createConnection();

                // Create basic message
                SOAPMessage requestMessage = createSOAPRequest("UpdatePatient");

                // SOAP Body
                SOAPElement soapMethodElement = requestMessage.getSOAPBody().addChildElement("UpdatePatient", "", "http://tempuri.org/");
                SOAPElement soapMethodParamElement1 = soapMethodElement.addChildElement("openHRXml");
                soapMethodParamElement1.addTextNode(requestData);
                requestMessage.saveChanges();

                // Send SOAP Message to SOAP Server
                SOAPMessage soapResponse = soapConnection.call(requestMessage, _soapUri + "/UpdatePatient");

                soapResponse.writeTo(System.out);

                return soapResponse.getSOAPBody().getElementsByTagName("UpdatePatientResult").item(0).getTextContent();

            } finally {
                if (soapConnection != null)
                    soapConnection.close();
            }
        }
        catch (Exception e) {
            return null;
        }
    }

    public ArrayList<UUID> getChangedPatients(String odsCode, Date date) {
        SOAPConnection soapConnection = null;
        try {
            try {
                SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
                soapConnection = soapConnectionFactory.createConnection();

                // Create basic message
                SOAPMessage requestMessage = createSOAPRequest("GetChangedPatients");

                // SOAP Body
                SOAPElement soapMethodElement = requestMessage.getSOAPBody().addChildElement("GetChangedPatients", "", "http://tempuri.org/");
                SOAPElement soapMethodParamElement1 = soapMethodElement.addChildElement("sinceDateTime");
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                soapMethodParamElement1.addTextNode(dateFormat.format(date));
                requestMessage.saveChanges();

                // Send SOAP Message to SOAP Server
                SOAPMessage soapResponse = soapConnection.call(requestMessage, _soapUri + "/GetChangedPatients");
                String responseText = soapResponse.getSOAPBody().getElementsByTagName("GetChangedPatientsResult").item(0).getTextContent();

                ArrayList<UUID> uuids = new ArrayList<>();
                if (responseText.isEmpty() == false)
                    for (String uuidString : responseText.split("(?<=\\G.{36})"))
                        uuids.add(UUID.fromString(uuidString));

                return uuids;

            } finally {
                if (soapConnection != null)
                    soapConnection.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private SOAPMessage createSOAPRequest(String methodCall) throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();

        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", _actionUri + "/" + methodCall);

        return soapMessage;
    }
}
