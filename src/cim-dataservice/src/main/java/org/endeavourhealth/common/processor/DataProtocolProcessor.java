package org.endeavourhealth.common.processor;

import org.apache.camel.Exchange;
import org.endeavourhealth.common.core.ExchangeHelper;
import org.endeavourhealth.common.core.HeaderKey;
import org.endeavourhealth.common.core.exceptions.NoLegitimateRelationshipException;
import org.endeavourhealth.core.text.TextUtils;
import org.endeavourhealth.common.repository.informationSharing.ISFManager;
import org.endeavourhealth.common.repository.informationSharing.model.InformationSharingProtocol;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.List;

public class DataProtocolProcessor implements org.apache.camel.Processor {
	public static final String SUBSIDIARY_SYSTEM_HAS_NO_LEGITIMATE_RELATIONSHIP_WITH_THIS_ORGANISATION = "Subsidiary system has no legitimate relationship with this organisation";

	@Override
	public void process(Exchange exchange) throws Exception {
		String odsCode = ExchangeHelper.getInHeaderString(exchange, HeaderKey.OdsCode);
		String api_key = ExchangeHelper.getInHeaderString(exchange, HeaderKey.ApiKey);

		//////////////////////////////////////////////////////////////
		// Temporarily disable while working on ISF
		//
		//		if (odsCode != null)
		//			LoadDataProtocols(exchange, api_key, odsCode);
		//////////////////////////////////////////////////////////////

		// temporary check
 		if (TextUtils.isNullOrTrimmedEmpty(odsCode) == false) {
			if (odsCode.equals("A99999") || odsCode.equals("B99999") || odsCode.equals("Y99999") || odsCode.equals("Z99999")) {
				// LR check pass
			} else
				throw new NoLegitimateRelationshipException(SUBSIDIARY_SYSTEM_HAS_NO_LEGITIMATE_RELATIONSHIP_WITH_THIS_ORGANISATION);
			// end temporary check
		}
	}

	private void LoadDataProtocols(Exchange exchange, String api_key, String odsCode) throws RepositoryException, NoLegitimateRelationshipException {
		// Load relevant data protocols from DB
		List<InformationSharingProtocol> informationSharingProtocols = ISFManager.Instance().getRelevantProtocols(odsCode, api_key);

		ValidateLegitimateRelationships(informationSharingProtocols);

		// Set protocols in message header for processing later in pipeline
		exchange.getIn().setHeader(HeaderKey.InformationSharingProtocols, informationSharingProtocols);
	}

	private void ValidateLegitimateRelationships(List<InformationSharingProtocol> informationSharingProtocols) throws NoLegitimateRelationshipException {
		// If a protocol exists then there is a LR
		if (informationSharingProtocols == null || informationSharingProtocols.size() == 0)
			throw new NoLegitimateRelationshipException(SUBSIDIARY_SYSTEM_HAS_NO_LEGITIMATE_RELATIONSHIP_WITH_THIS_ORGANISATION);
	}
}