package org.endeavourhealth.cim.transform.emisopen;

import org.endeavourhealth.cim.transform.common.ReferenceHelper;
import org.endeavourhealth.cim.transform.exceptions.SerializationException;
import org.endeavourhealth.cim.transform.exceptions.TransformFeatureNotSupportedException;
import org.endeavourhealth.cim.transform.schemas.emisopen.eomgetpatientappointments.AppointmentStruct;
import org.endeavourhealth.cim.transform.schemas.emisopen.eomgetpatientappointments.PatientAppointmentList;
import org.endeavourhealth.cim.transform.schemas.emisopen.eomorganisationinformation.OrganisationInformation;
import org.endeavourhealth.cim.repository.utils.TextUtils;
import org.endeavourhealth.cim.transform.schemas.emisopen.eomgetpatientappointments.HolderStruct;
import org.hl7.fhir.instance.model.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class AppointmentTransformer {

    public static ArrayList<Resource> transformToAppointmentResources(String patientGuid, PatientAppointmentList patientAppointmentList, OrganisationInformation organisationInformation) throws TransformFeatureNotSupportedException, SerializationException
    {
        ArrayList<Appointment> appointments = new ArrayList<Appointment>();

        for (AppointmentStruct appointment : patientAppointmentList.getAppointment()) {
            appointments.add(transformToAppointment(patientGuid, appointment));
        }

        updateAppointmentParticipantIds(appointments, organisationInformation);

        return new ArrayList<>(appointments);
    }

    public static Appointment transformToAppointment(String patientGuid, AppointmentStruct appointmentStruct) throws SerializationException {
        Appointment appointment = new Appointment();

        appointment.setId(Integer.toString(appointmentStruct.getSlotID()));
        appointment.setStatus(getAppointmentStatus(appointmentStruct.getStatus()));

        if (!TextUtils.isNullOrTrimmedEmpty(appointmentStruct.getReason())) {
            CodeableConcept codeableConcept = new CodeableConcept();
            codeableConcept.setText(appointmentStruct.getReason());

            appointment.setReason(codeableConcept);
        }

        Date startTime = EmisOpenCommon.getDateAndTime(appointmentStruct.getDate(), appointmentStruct.getStartTime());
        appointment.setStart(startTime);

        Date endTime = EmisOpenCommon.addMinutesToTime(startTime, Integer.parseInt(appointmentStruct.getDuration()));
        appointment.setEnd(endTime);

        Reference reference = ReferenceHelper.createReference(ResourceType.Slot, Integer.toString(appointmentStruct.getSlotID()));
        appointment.addSlot(reference);

        Appointment.ParticipantRequired requiredStatus = Appointment.ParticipantRequired.REQUIRED;
        Appointment.ParticipationStatus participationstatus = Appointment.ParticipationStatus.ACCEPTED;

        appointment.addParticipant(createParticipant(ResourceType.Patient, patientGuid, requiredStatus, participationstatus));

        for (HolderStruct holder : appointmentStruct.getHolderList().getHolder())
            appointment.addParticipant(createParticipant(ResourceType.Practitioner, Integer.toString(holder.getDBID()), requiredStatus, participationstatus));

        appointment.addParticipant(createParticipant(ResourceType.Location, Integer.toString(appointmentStruct.getSiteID()), requiredStatus, participationstatus));

        return appointment;
    }

    private static Appointment.AppointmentParticipantComponent createParticipant(ResourceType resourceType, String id, Appointment.ParticipantRequired required, Appointment.ParticipationStatus status) {
        return new Appointment.AppointmentParticipantComponent()
                .setActor(ReferenceHelper.createReference(resourceType, id))
                .setRequired(required)
                .setStatus(status);
    }

    private static Appointment.AppointmentStatus getAppointmentStatus(String status) {
        switch (status) {
            case "Slot Available":
            case "Booked": return Appointment.AppointmentStatus.BOOKED;

            case "Start Call":
            case "Quiet Send In":
            case "Send In":
            case "Arrived": return Appointment.AppointmentStatus.ARRIVED;

            case "Cannot Be Seen":
            case "DNA":
            case "Telephone - Not In":
            case "Walked Out":
            case "Visited - Not In": return Appointment.AppointmentStatus.NOSHOW;

            case "Left":
            case "Telephone - Complete":
            case "Visited": return Appointment.AppointmentStatus.FULFILLED;

            case "Unknown":
            default: return Appointment.AppointmentStatus.NULL;
        }
    }

    public static void updateAppointmentParticipantIds(ArrayList<Appointment> appointments, OrganisationInformation organisationInformation) {
        Map<String, String> userIdGuidMap = EmisOpenCommon.buildUserIdGuidMap(organisationInformation);
        updateAppointmentParticipantIds(appointments, ResourceType.Practitioner, userIdGuidMap);

        Map<String, String> locationIdGuidMap = EmisOpenCommon.buildLocationIdGuidMap(organisationInformation);
        updateAppointmentParticipantIds(appointments, ResourceType.Location, locationIdGuidMap);
    }

    private static void updateAppointmentParticipantIds(ArrayList<Appointment> appointments, ResourceType participantResourceType, Map<String, String> idGuidMap) {
        for (Appointment appointment : appointments) {
            for (Appointment.AppointmentParticipantComponent appointmentParticipantComponent : appointment.getParticipant()) {
                String id = ReferenceHelper.getReferenceId(appointmentParticipantComponent.getActor(), participantResourceType);

                if (!TextUtils.isNullOrTrimmedEmpty(id))
                    if (idGuidMap.containsKey(id))
                        appointmentParticipantComponent.setActor(ReferenceHelper.createReference(participantResourceType, idGuidMap.get(id)));
            }
        }
    }
}