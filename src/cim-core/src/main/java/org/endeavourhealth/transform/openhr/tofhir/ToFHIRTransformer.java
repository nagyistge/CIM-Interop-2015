package org.endeavourhealth.transform.openhr.tofhir;

import org.endeavourhealth.transform.common.BundleProperties;
import org.endeavourhealth.transform.exceptions.TransformException;
import org.endeavourhealth.transform.openhr.tofhir.admin.*;
import org.endeavourhealth.transform.openhr.tofhir.clinical.HealthDomainTransformer;
import org.endeavourhealth.transform.schemas.openhr.OpenHR001Location;
import org.endeavourhealth.transform.schemas.openhr.OpenHR001OpenHealthRecord;
import org.endeavourhealth.transform.schemas.openhr.OpenHR001Organisation;
import org.endeavourhealth.transform.schemas.openhr.OpenHR001PatientTask;
import org.endeavourhealth.transform.common.BundleHelper;
import org.endeavourhealth.transform.common.TransformHelper;
import org.hl7.fhir.instance.model.*;

import java.util.Date;

public class ToFHIRTransformer {

    public Bundle transformToBundle(BundleProperties bundleProperties, OpenHR001OpenHealthRecord openHR) throws TransformException
    {

        FHIRContainer container = transform(openHR);
        Date creationDate = TransformHelper.toDate(openHR.getCreationTime());
        return BundleHelper.createBundle(Bundle.BundleType.SEARCHSET, openHR.getId(), creationDate, container.getResources());
    }

    public Patient transformToPatient(OpenHR001OpenHealthRecord openHR) throws TransformException {

        return PatientTransformer.transform(openHR.getAdminDomain());
    }

    public Person transformToPerson(OpenHR001OpenHealthRecord openHR) throws TransformException {

        return PersonTransformer.transform(openHR.getAdminDomain());
    }

	public Organization transformToOrganisation(OpenHR001Organisation openHR) throws TransformException {
		return OrganisationTransformer.transform(openHR);
	}

	public Location transformToLocation(OpenHR001Location openHR) throws TransformException {
		return LocationTransformer.transform(openHR);
	}

	public Order transformToTask(OpenHR001PatientTask openHR) throws TransformException {
		return TaskTransformer.transform(openHR);
	}

    private FHIRContainer transform(OpenHR001OpenHealthRecord openHR) throws TransformException {
        FHIRContainer container = new FHIRContainer();

        AdminDomainTransformer.transform(container, openHR);
        HealthDomainTransformer.transform(container, openHR);

        return container;
    }
}