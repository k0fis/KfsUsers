package kfs.kfsusers.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author pavedrim
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"login", "groupname"})})
public class KfsUserGroup {

    @Id
    @Column(length = 40)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;    
    
    @OneToOne
    @JoinColumn(name = "groupname")
    private KfsGroup group;
    
    @OneToOne
    @JoinColumn(name = "login")
    private KfsUser user;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public KfsGroup getGroup() {
        return group;
    }

    public void setGroup(KfsGroup group) {
        this.group = group;
    }

    public KfsUser getUser() {
        return user;
    }

    public void setUser(KfsUser user) {
        this.user = user;
    }
}
