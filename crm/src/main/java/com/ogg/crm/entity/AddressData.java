package com.ogg.crm.entity;

import java.io.Serializable;

public class AddressData implements Serializable {

	private static final long serialVersionUID = 8912365559481657349L;

    private String regionName;

	private String regionId;

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }
}
