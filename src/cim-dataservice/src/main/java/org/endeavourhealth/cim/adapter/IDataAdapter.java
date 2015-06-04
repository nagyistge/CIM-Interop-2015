package org.endeavourhealth.cim.adapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public interface IDataAdapter {
    String getPatientRecordByPatientId(UUID patientId);
    String getPatientDemographicsByNHSNumber(String nhsNumber);
    String createCondition(String request);
    ArrayList<UUID> getChangedPatients(Date date);
}