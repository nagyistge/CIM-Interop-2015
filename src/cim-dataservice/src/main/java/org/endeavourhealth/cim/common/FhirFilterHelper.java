package org.endeavourhealth.cim.common;

import org.hl7.fhir.instance.model.Bundle;

public class FhirFilterHelper {
	public static Bundle getConditions(Bundle bundle) {
		// Apply condition filter (in case supplier can only provide full record)
//		for(Bundle.BundleEntryComponent component : bundle.getEntry()) {
//			if (component.getResource() instanceof Encounter) {
//				Encounter encounter = (Encounter)component.getResource();
//				if (encounter.)
//			}
//		}

		return bundle;
	}
}
