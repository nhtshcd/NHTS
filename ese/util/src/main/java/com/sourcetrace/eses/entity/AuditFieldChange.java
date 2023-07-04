package com.sourcetrace.eses.entity;

import org.hibernate.envers.*;

import java.util.*;

public class AuditFieldChange {
    private String fieldName;
    private String oldValue;
    private String newValue;
    private Date revisionDate;
    private Long revisionId;
    private String revisionUsername;
    private RevisionType revisionType;

    // Getters and setters

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public Date getRevisionDate() {
        return revisionDate;
    }

    public void setRevisionDate(Date revisionDate) {
        this.revisionDate = revisionDate;
    }

    public Long getRevisionId() {
        return revisionId;
    }

    public void setRevisionId(Long revisionId) {
        this.revisionId = revisionId;
    }

    public String getRevisionUsername() {
        return revisionUsername;
    }

    public void setRevisionUsername(String revisionUsername) {
        this.revisionUsername = revisionUsername;
    }

    public RevisionType getRevisionType() {
        return revisionType;
    }

    public void setRevisionType(RevisionType revisionType) {
        this.revisionType = revisionType;
    }
}
