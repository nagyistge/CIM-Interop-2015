package org.endeavourhealth.cim.transform.openhr.tofhir.clinical;

import org.endeavourhealth.cim.transform.common.FhirUris;
import org.endeavourhealth.cim.transform.common.ReferenceHelper;
import org.endeavourhealth.cim.transform.common.exceptions.TransformException;
import org.endeavourhealth.cim.transform.openhr.tofhir.common.EventEncounterMap;
import org.endeavourhealth.cim.transform.common.OpenHRHelper;
import org.endeavourhealth.cim.transform.common.CodeHelper;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001HealthDomain;
import org.hl7.fhir.instance.model.*;

public class AllergyTransformer implements ClinicalResourceTransformer
{
    public AllergyIntolerance transform(OpenHR001HealthDomain.Event source, OpenHR001HealthDomain healthDomain, EventEncounterMap eventEncounterMap) throws TransformException
    {
        AllergyIntolerance target = new AllergyIntolerance();
        target.setId(source.getId());
        target.setMeta(new Meta().addProfile(FhirUris.PROFILE_URI_ALLERGY_INTOLERANCE));

        target.setStatus(AllergyIntolerance.AllergyIntoleranceStatus.ACTIVE);
        target.setOnsetElement(OpenHRHelper.convertPartialDateTimeToDateTimeType(source.getEffectiveTime()));
        target.setPatient(ReferenceHelper.createReference(ResourceType.Patient, source.getPatient()));
        target.setRecorder(ReferenceHelper.createReference(ResourceType.Practitioner, source.getAuthorisingUserInRole()));
        target.setSubstance(CodeHelper.convertCode(source.getCode(), source.getDisplayTerm()));
        return target;
    }
}
