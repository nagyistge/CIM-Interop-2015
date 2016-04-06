package org.endeavourhealth.cim.transform.openhr.tofhir.clinical;

import org.endeavourhealth.cim.transform.common.exceptions.TransformException;
import org.endeavourhealth.cim.transform.openhr.tofhir.common.EventEncounterMap;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001HealthDomain;
import org.hl7.fhir.instance.model.ReferralRequest;

public class ReferralTransformer implements ClinicalResourceTransformer
{
    public ReferralRequest transform(OpenHR001HealthDomain healthDomain, EventEncounterMap eventEncounterMap, OpenHR001HealthDomain.Event source) throws TransformException
    {
        ReferralRequest target = new ReferralRequest();
        target.setId(source.getId());

        return target;
    }
}