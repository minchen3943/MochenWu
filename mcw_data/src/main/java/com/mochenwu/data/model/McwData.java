package com.mochenwu.data.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;

/**
 * @author 32035
 */

@Embeddable
@Table(name = "mcw_data")
public class McwData {

    private Integer visitorCount;

    public Integer getVisitorCount() {
        return visitorCount;
    }

    public void setVisitorCount(Integer visitorCount) {
        this.visitorCount = visitorCount;
    }
}