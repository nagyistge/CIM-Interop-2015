package org.endeavourhealth.common.repository.ehr.model;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class Schedule {
	private UUID scheduleId;
    private Map<String, String> metaData;
    private String entryData;

    private Date lastUpdated;

	public UUID getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(UUID scheduleId) {
		this.scheduleId = scheduleId;
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
