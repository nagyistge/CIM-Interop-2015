package org.endeavourhealth.cim.InformationSharingFramework.model;

import org.endeavourhealth.cim.InformationSharingFramework.ISFManager;
import org.endeavourhealth.cim.common.models.BaseEntity;
import org.endeavourhealth.cim.common.models.EntityIdentifier;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InformationSharingProtocol extends BaseEntity {
	private UUID id;
	private String name;
	private String description;
	private Boolean enabled;
	private ConsentModel consentModel;
	private UUID derivedFromId;
	private ArrayList<Integer> publisherProfiles = new ArrayList<>();
	private ArrayList<Integer> subscriberProfiles = new ArrayList<>();
	private ArrayList<UUID> publisherSharingAgreements = new ArrayList<>();
	private ArrayList<UUID> subscriberSharingAgreements = new ArrayList<>();

	public UUID getId() { return id; }
	public void setId(UUID id) { this.id = id; }

	public String getName() {
		return name;
	}

	public String getSchemaVersion() {
		return "1.0";
	}

	public List<EntityIdentifier> getIdentifiers() {
		return null;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public ConsentModel getConsentModel() {
		return consentModel;
	}
	public void setConsentModel(ConsentModel consentModel) {
		this.consentModel = consentModel;
	}

	public UUID getDerivedFromId() {
		return derivedFromId;
	}
	public void setDerivedFromId(UUID derivedFromId) {
		this.derivedFromId = derivedFromId;
	}
	public InformationSharingProtocol getDerivedFrom() { return ISFManager.Instance().getInformationSharingProtocol(derivedFromId); }

	public ArrayList<Integer> getPublisherProfiles() {
		return publisherProfiles;
	}

	public ArrayList<Integer> getSubscriberProfiles() {
		return subscriberProfiles;
	}

	public ArrayList<UUID> getPublisherSharingAgreements() {
		return publisherSharingAgreements;
	}

	public ArrayList<UUID> getSubscriberSharingAgreements() {
		return subscriberSharingAgreements;
	}
}
