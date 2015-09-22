package kfs.kfsusers.domain;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author pavedrim
 */
@Entity
public class KfsGroup implements Serializable {

    @Id
    @Column(length = 32)
    private String groupName;
    
    private Boolean groupFixed = Boolean.FALSE;
    
    private String groupLabel;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.groupName);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final KfsGroup other = (KfsGroup) obj;
        if (!Objects.equals(this.groupName, other.groupName)) {
            return false;
        }
        return true;
    }
    
    
    
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupLabel() {
        return groupLabel;
    }

    public void setGroupLabel(String groupLabel) {
        this.groupLabel = groupLabel;
    }

    public Boolean getGroupFixed() {
        return groupFixed;
    }

    public void setGroupFixed(Boolean groupFixed) {
        this.groupFixed = groupFixed;
    }
    
    
}
