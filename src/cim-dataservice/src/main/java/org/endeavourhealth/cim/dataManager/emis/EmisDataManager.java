package org.endeavourhealth.cim.dataManager.emis;

import org.endeavourhealth.cim.Registry;
import org.endeavourhealth.cim.common.BundleProperties;
import org.endeavourhealth.cim.dataManager.IDataManager;
import org.endeavourhealth.cim.common.FhirFilterHelper;
import org.endeavourhealth.cim.transform.emisopen.EmisOpenTransformer;
import org.endeavourhealth.cim.transform.openhr.OpenHRTransformer;
import org.hl7.fhir.instance.formats.JsonParser;
import org.hl7.fhir.instance.model.*;

import java.util.*;

@SuppressWarnings("unused")
public class EmisDataManager implements IDataManager {

	protected EmisDataAdapter _emisDataAdapter = new EmisDataAdapter();
	private final OpenHRTransformer _openHrTransformer = new OpenHRTransformer();
	private final EmisOpenTransformer _emisOpenTransformer = new EmisOpenTransformer();

	@Override
	public String getPatientRecord(String odsCode, UUID patientId) throws Exception {

		Bundle bundle = getPatientRecordAsBundle(odsCode, patientId);
		return new JsonParser().composeString(bundle);
	}

	private Bundle getPatientRecordAsBundle(String odsCode, UUID patientId) throws Exception {

		String openHRXml = _emisDataAdapter.getPatientRecordByPatientId(odsCode, patientId);
		String baseUri = Registry.Instance().getBaseUri(odsCode);
		return _openHrTransformer.toFHIRBundle(baseUri, openHRXml);
	}

	@Override
	public String getPatientDemographics(String odsCode, UUID patientId) throws Exception {

		String patientDataInNativeFormat = _emisDataAdapter.getPatientDemographicsByPatientId(odsCode, patientId);
		Patient patient = _openHrTransformer.toFHIRPatient(patientDataInNativeFormat);
		return new JsonParser().composeString(patient);
	}

	@Override
	public String getPatientDemographics(String odsCode, String nhsNumber) throws Exception {

		String patientDataInNativeFormat = _emisDataAdapter.getPatientDemographicsByNHSNumber(odsCode, nhsNumber);
		Patient patient = _openHrTransformer.toFHIRPatient(patientDataInNativeFormat);
		return new JsonParser().composeString(patient);
	}

	@Override
	public String tracePatientByDemographics(String surname, Date dateOfBirth, String gender, String forename, String postcode) throws Exception {

		String patientDataInNativeFormat = _emisDataAdapter.tracePatientByDemographics(surname, dateOfBirth, gender, forename, postcode);
		Patient patient = _openHrTransformer.toFHIRPatient(patientDataInNativeFormat);
		return new JsonParser().composeString(patient);
	}

	@Override
	public String tracePatientByNhsNumber(String nhsNumber) throws Exception {

		String patientDataInNativeFormat = _emisDataAdapter.tracePatientByNhsNumber(nhsNumber);
		Patient patient = _openHrTransformer.toFHIRPatient(patientDataInNativeFormat);
		return new JsonParser().composeString(patient);
	}

	@Override
	public String createCondition(String odsCode, String fhirRequest) throws Exception {

		Condition condition = (Condition)new JsonParser().parse(fhirRequest);
		String request = _openHrTransformer.fromFHIRCondition(condition);
		String response = _emisDataAdapter.createCondition(odsCode, request);
		String fhirResponse = response; // _openHrTransformer.toFHIRCondition(response));

		return fhirResponse;
	}

	@Override
	public ArrayList<UUID> getChangedPatients(String odsCode, Date date) throws Exception {

		return _emisDataAdapter.getChangedPatients(odsCode, date);
	}

	@Override
	public String getConditionsByPatientId(String odsCode, UUID patientId) throws Exception {

		Bundle bundle = getPatientRecordAsBundle(odsCode, patientId);
		bundle = FhirFilterHelper.getConditions(bundle);

		return new JsonParser().composeString(bundle);
	}

	@Override
	public String getAllergyIntolerancesByPatientId(String odsCode, UUID patientId) throws Exception {

		Bundle bundle = getPatientRecordAsBundle(odsCode, patientId);
		bundle = FhirFilterHelper.getAllergyIntolerances(bundle);

		return new JsonParser().composeString(bundle);
	}

	@Override
	public String getImmunizationsByPatientId(String odsCode, UUID patientId) throws Exception {

		Bundle bundle = getPatientRecordAsBundle(odsCode, patientId);
		bundle = FhirFilterHelper.getImmunizations(bundle);

		return new JsonParser().composeString(bundle);
	}

	@Override
	public String getMedicationPrescriptionsByPatientId(String odsCode, UUID patientId) throws Exception {

		Bundle bundle = getPatientRecordAsBundle(odsCode, patientId);
		bundle = FhirFilterHelper.getMedicationPrescriptions(bundle);

		return new JsonParser().composeString(bundle);
	}

	@Override
	public String getSchedules(String odsCode, Date dateFrom, Date dateTo, UUID practitionerId) throws Exception {

		String schedulesXml = _emisDataAdapter.getSchedules(odsCode, dateFrom, dateTo);
		String organisationXml = _emisDataAdapter.getOrganisationInformation(odsCode);
		BundleProperties bundleProperties = getBundleProperties(UUID.randomUUID(), odsCode);

		Bundle bundle = _emisOpenTransformer.toFhirScheduleBundle(bundleProperties, schedulesXml, organisationXml);
		bundle = FhirFilterHelper.filterScheduleByPractitioner(bundle, practitionerId);
		return new JsonParser().composeString(bundle);
	}

	@Override
	public String getSlots(String odsCode, String scheduleId) throws Exception {

		String slotsXml = _emisDataAdapter.getSlots(odsCode, scheduleId);
		BundleProperties bundleProperties = getBundleProperties(UUID.randomUUID(), odsCode);

		Bundle bundle = _emisOpenTransformer.toFhirSlotBundle(bundleProperties, scheduleId, slotsXml);
		return new JsonParser().composeString(bundle);
	}

	@Override
	public String getAppointmentsForPatient(String odsCode, UUID patientId, Date dateFrom, Date dateTo) throws Exception {

		String appointmentDataInNativeFormat = _emisDataAdapter.getAppointmentsForPatient(odsCode, patientId, dateFrom, dateTo);
		String organisationXml = _emisDataAdapter.getOrganisationInformation(odsCode);
		BundleProperties bundleProperties = getBundleProperties(UUID.randomUUID(), odsCode);

		Bundle bundle = _emisOpenTransformer.toFhirAppointmentForPatientBundle(bundleProperties, patientId.toString(), appointmentDataInNativeFormat, organisationXml);
		return new JsonParser().composeString(bundle);
	}

	@Override
	public String bookSlot(String odsCode, String slotId, UUID patientId) throws Exception {

		return _emisDataAdapter.bookSlot(odsCode, slotId, patientId, "");
	}

	public String cancelSlot(String odsCode, String slotId, UUID patientId) throws Exception {

		return _emisDataAdapter.cancelSlot(odsCode, slotId, patientId);
	}

	private BundleProperties getBundleProperties(UUID bundleId, String odsCode) {
		return new BundleProperties()
				.setBundleId(bundleId.toString())
				.setBaseUri(Registry.Instance().getBaseUri(odsCode))
				.setBundleType(Bundle.BundleType.SEARCHSET);
	}
}
