package org.endeavourhealth.cim.processor.clinical;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.endeavourhealth.cim.common.ExchangeHelper;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.endeavourhealth.cim.dataManager.IDataManager;

import java.util.UUID;

public class GetMedicationPrescriptionsProcessor implements Processor {

	public void process(Exchange exchange) throws Exception {

		String odsCode = ExchangeHelper.getInHeaderString(exchange, HeaderKey.OdsCode);
		UUID patientId = ExchangeHelper.getInHeaderUUID(exchange, HeaderKey.Id, true);

		IDataManager dataManager = DataManagerFactory.getDataManagerForService(odsCode);
		String responseBody = dataManager.getMedicationPrescriptionsByPatientId(odsCode, patientId);

		ExchangeHelper.setInBodyString(exchange, responseBody);
	}
}
