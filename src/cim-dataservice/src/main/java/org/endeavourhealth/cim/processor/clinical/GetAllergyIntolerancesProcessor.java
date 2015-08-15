package org.endeavourhealth.cim.processor.clinical;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.endeavourhealth.cim.dataManager.IDataManager;

import java.util.UUID;

public class GetAllergyIntolerancesProcessor implements Processor {
	public void process(Exchange exchange) throws Exception {
		String odsCode =(String) exchange.getIn().getHeader(HeaderKey.OdsCode);
		String patientId =(String) exchange.getIn().getHeader(HeaderKey.Id);

		IDataManager dataManager = DataManagerFactory.getDataManagerForService(odsCode);

		String body = dataManager.getAllergyIntolerancesByPatientId(odsCode, UUID.fromString(patientId));

		exchange.getIn().setBody(body);
	}
}