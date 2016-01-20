package org.endeavourhealth.common.repository.ehr.model;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class EpisodeOfCare {
	private UUID episodeOfCareId;
    private UUID patientId;
    private UUID serviceId;
    private Map<String, String> metaData;
    private String entryData;

    private Date lastUpdated;

	public UUID getEpisodeOfCareId() {
		return episodeOfCareId;
	}
	public void setEpisodeOfCareId(UUID episodeOfCareId) {
		this.episodeOfCareId = episodeOfCareId;
	}
    public UUID getPatientId() {
        return patientId;
    }
    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }
    public UUID getServiceId() {
        return serviceId;
    }
    public void setServiceId(UUID serviceId) {
        this.serviceId = serviceId;
    }
	public Map<String, String> getMetaData() {
		return metaData;
	}
    public void setMetaData(Map<String, String> metaData) {
        this.metaData = metaData;
    }
    public String getEntryData() {
        return entryData;
    }
    public void setEntryData(String entryData) {
        this.entryData = entryData;
    }


    public Date getLastUpdated() {
        return lastUpdated;
    }
    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }




}
