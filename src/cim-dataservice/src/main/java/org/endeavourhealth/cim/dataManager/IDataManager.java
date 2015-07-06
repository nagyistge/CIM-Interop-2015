package org.endeavourhealth.cim.dataManager;

import org.endeavourhealth.cim.transform.TransformException;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public interface IDataManager {
    String getPatientRecordByPatientId(String odsCode, UUID patientId) throws Exception;
    String getPatientDemographicsByNHSNumber(String odsCode, String nhsNumber) throws Exception;
    String tracePatientByDemographics(String surname, Date dateOfBirth, String gender, String forename, String postcode) throws Exception;
    String tracePatientByNhsNumber(String nhsNumber) throws Exception;
    String createCondition(String odsCode, String request) throws Exception;
    ArrayList<UUID> getChangedPatients(String odsCode, Date date);

    String getConditionsByPatientId(String odsCode, UUID patientId) throws Exception;
    String getAllergyIntolerancesByPatientId(String odsCode, UUID patientId) throws Exception;

    String getImmunizationsByPatientId(String odsCode, UUID patientId) throws Exception;

    String getMedicationPrescriptionsByPatientId(String odsCode, UUID patientId) throws Exception;

    String getAppointmentsForPatient(String odsCode, UUID patientId, Date dateFrom, Date dateTo) throws Exception;

    void requestOrder(String odsCode, String orderRequest) throws Exception;

    String getSchedules(String odsCode, Date dateFrom, Date dateTo) throws Exception;
    String getSchedules(String odsCode, String actor) throws Exception;
    String getSlots(String odsCode, String scheduleId) throws Exception;
}