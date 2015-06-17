package org.endeavourhealth.cim.processor.event;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.endeavourhealth.cim.adapter.AdapterFactory;
import org.endeavourhealth.cim.adapter.IDataAdapter;
import org.endeavourhealth.cim.common.FhirFilterHelper;
import org.endeavourhealth.cim.transform.Transformer;
import org.endeavourhealth.cim.transform.TransformerFactory;
import org.hl7.fhir.instance.formats.JsonParser;
import org.hl7.fhir.instance.model.Bundle;

import java.util.UUID;

public class GetMedicationPrescriptions implements Processor {
	public void process(Exchange exchange) throws Exception {
		String odsCode =(String) exchange.getIn().getHeader("odsCode");
		String patientId =(String) exchange.getIn().getHeader("id");

		// Get patientApi data (native format) using adapter
		IDataAdapter dataAdapter = AdapterFactory.getDataAdapterForService(odsCode);
		String patientData = dataAdapter.getMedicationPrescriptionsByPatientId(odsCode, UUID.fromString(patientId));

		// Get patientApi data transformer for service (native format -> FHIR)
		Transformer transformer = TransformerFactory.getTransformerForAdapter(dataAdapter);
		Bundle bundle = transformer.toFHIRBundle(patientData);

		// Apply medication prescriptions filter (in case supplier can only provide full record)
		bundle = FhirFilterHelper.getMedicationPrescriptions(bundle);

		String body = new JsonParser().composeString(bundle);

		exchange.getIn().setBody(body);


	}
}