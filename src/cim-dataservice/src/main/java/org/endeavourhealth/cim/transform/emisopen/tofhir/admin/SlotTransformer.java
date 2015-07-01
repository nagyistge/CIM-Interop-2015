package org.endeavourhealth.cim.transform.emisopen.tofhir.admin;

import org.endeavourhealth.cim.common.TextUtils;
import org.endeavourhealth.cim.transform.SerializationException;
import org.endeavourhealth.cim.transform.TransformFeatureNotSupportedException;
import org.endeavourhealth.cim.transform.TransformHelper;
import org.endeavourhealth.cim.transform.emisopen.EmisOpenConstants;
import org.endeavourhealth.cim.transform.schemas.emisopen.eomslotsforsession.SlotListStruct;
import org.endeavourhealth.cim.transform.schemas.emisopen.eomslotsforsession.SlotStruct;
import org.hl7.fhir.instance.model.Reference;
import org.hl7.fhir.instance.model.Resource;
import org.hl7.fhir.instance.model.Schedule;
import org.hl7.fhir.instance.model.Slot;

import java.sql.Time;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class SlotTransformer {

    public static ArrayList<Resource> transformToSlotResources(SlotListStruct appointmentSlotList, String scheduleId) throws SerializationException, TransformFeatureNotSupportedException {
        ArrayList<Resource> resources = new ArrayList<Resource>();

        for (SlotStruct appointmentSlot : appointmentSlotList.getSlot())
            resources.add(transformToSlot(appointmentSlot, scheduleId));

        return resources;
    }

    private static Slot transformToSlot(SlotStruct appointmentSlot, String scheduleId) throws SerializationException, TransformFeatureNotSupportedException {
        Slot slot = new Slot();
        slot.setId(Integer.toString(appointmentSlot.getDBID()));

        Time start = getTime(appointmentSlot.getStartTime());
        slot.setStart(start);

        Time end = addMinutesToTime(start, Integer.parseInt(appointmentSlot.getSlotLength()));
        slot.setEnd(end);

        String slotStatus = appointmentSlot.getStatus();

        if (!TextUtils.isNullOrTrimmedEmpty(slotStatus))
            slot.setFreeBusyType(getSlotStatus(slotStatus));

        slot.setSchedule(new Reference().setReference(TransformHelper.createResourceReference(Schedule.class, scheduleId)));

        return slot;
    }

    private static Time addMinutesToTime(Time time, int minutes) {
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(time);
        cal.add(Calendar.MINUTE, minutes);
        return new Time(cal.getTime().getTime());
    }

    private static Time getTime(String timeString) throws SerializationException {
        try {
            return new Time(EmisOpenConstants.EMISOPEN_TIMEFORMAT.parse(timeString).getTime());
        } catch (ParseException e) {
            throw new SerializationException("Could not parse time", e);
        }
    }

    private static Slot.Slotstatus getSlotStatus(String slotStatus) throws TransformFeatureNotSupportedException {
        switch (slotStatus) {
            case "Slot Available":
                return Slot.Slotstatus.FREE;
            case "Arrived":
            case "Send In":
            case "Left":
            case "DNA":
            case "Walked Out":
            case "Visited":
            case "Visited - Not In":
            case "Telephone - Complete":
            case "Telephone - Not In":
            case "Quiet Send In":
            case "Start Call":
            case "Cannot Be Seen":
            case "Booked":
                return Slot.Slotstatus.BUSY;
            case "Blocked":
            case "Embargoed":
                return Slot.Slotstatus.BUSYUNAVAILABLE;
            case "Unknown":
                return Slot.Slotstatus.BUSYTENTATIVE;
            default:
                throw new TransformFeatureNotSupportedException("SlotStatus not supported: " + slotStatus);
        }
    }
}