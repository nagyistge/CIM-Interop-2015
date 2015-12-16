package org.endeavourhealth.cim.processor.administrative;

import org.endeavourhealth.cim.dataManager.IDataManager;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.endeavourhealth.common.core.ExchangeHelper;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.endeavourhealth.common.core.HeaderKey;

public class GetOrganisationProcessor implements Processor {

	@SuppressWarnings("unchecked")
	public void process(Exchange exchange) throws Exception {

		String odsCode = ExchangeHelper.getInHeaderString(exchange, HeaderKey.DestinationOdsCode, true);

		IDataManager dataManager = DataManagerFactory.getDataManagerForService(odsCode);
		String responseBody = dataManager.getOrganisation(odsCode);

		ExchangeHelper.setInBodyString(exchange, responseBody);
	}
}