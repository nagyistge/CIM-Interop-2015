package org.endeavourhealth.cim.transform.openhr.tofhir.clinical;

import org.endeavourhealth.cim.transform.common.exceptions.TransformException;
import org.endeavourhealth.cim.transform.openhr.tofhir.common.EventEncounterMap;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001HealthDomain;
import org.hl7.fhir.instance.model.DiagnosticOrder;

public class DiagnosticOrderTransformer implements ClinicalResourceTransformer
{
    public DiagnosticOrder transform(OpenHR001HealthDomain healthDomain, EventEncounterMap eventEncounterMap, OpenHR001HealthDomain.Event source) throws TransformException {
        DiagnosticOrder target = new DiagnosticOrder();
        target.setId(source.getId());

        return target;
    }
}