package kfs.kfsusers.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import kfs.kfsvaalib.fields.KfsPasswd;
import kfs.kfsvaalib.kfsForm.KfsField;
import kfs.kfsvaalib.kfsForm.KfsMField;
import kfs.kfsvaalib.kfsTable.KfsTablePos;
import kfs.kfsvaalib.kfsTable.Pos;

/**
 *
 * @author pavedrim
 */
@Entity
public class KfsUser implements Serializable {

    public final static int userNameLength = 50;

    @Id
    @Column(length = userNameLength)
    @KfsTablePos({
        @Pos(name = "AdmUsers", genName = "login0", value = 10)})
    @KfsMField({
        @KfsField(pos = 10, name = "AdmUserDlg.new"),
        @KfsField(pos = 10, name = "AdmUserDlg.edit", readOnly = true),
        @KfsField(pos = 10, name = "UserDlg", readOnly = true),
    })
    private String login;

    @NotNull
    @Column(length = userNameLength)
    @KfsTablePos({
        @Pos(name = "AdmUsers", value = 20)})
    @KfsMField({
        @KfsField(pos = 20, name = "AdmUserDlg.new"),
        @KfsField(pos = 20, name = "AdmUserDlg.edit"),
        @KfsField(pos = 20, name = "UserDlg"),
    })
    private String userName;

    @Column(length = 250)
    @KfsTablePos({
        @Pos(name = "AdmUsers", value = 50)})
    @KfsMField({
        @KfsField(pos = 30, name = "AdmUserDlg.new"),
        @KfsField(pos = 30, name = "AdmUserDlg.edit"),
        @KfsField(pos = 30, name = "UserDlg"),
    })
    private String line;

    @Column(length = 50, nullable = false)
    private String password;
    
    @Transient
    @KfsMField({
        @KfsField(pos = 40, name = "AdmUserDlg.new", fieldClass = KfsPasswd.class),
        @KfsField(pos = 40, name = "AdmUserDlg.edit", fieldClass = KfsPasswd.class),
        @KfsField(pos = 40, name = "UserDlg", fieldClass = KfsPasswd.class),
    })    
    private String passwordOld;

    @Pattern(regexp = "(^$|^[\\w\\-]+(\\.[\\w\\-]+)*@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,6}$)")
    @Size(max = 512)
    @Column(length = 512)
    @KfsTablePos({
        @Pos(name = "AdmUsers", value = 30)})
    @KfsMField({
        @KfsField(pos = 50, name = "AdmUserDlg.new"),
        @KfsField(pos = 50, name = "AdmUserDlg.edit"),
        @KfsField(pos = 50, name = "UserDlg"),
    })
    private String mail;

    @Column(length = 20)
    @KfsTablePos({
        @Pos(name = "AdmUsers", value = 40)})
    @KfsMField({
        @KfsField(pos = 60, name = "AdmUserDlg.new"),
        @KfsField(pos = 60, name = "AdmUserDlg.edit"),
        @KfsField(pos = 60, name = "UserDlg"),
    })
    private String phone;

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + (this.login != null ? this.login.hashCode() : 0);
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
        final KfsUser other = (KfsUser) obj;
        if ((this.login == null) ? (other.login != null) : !this.login.equals(other.login)) {
            return false;
        }
        return true;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return getUserName();
    }

    public String getPasswordOld() {
        return passwordOld;
    }

    public void setPasswordOld(String passwordOld) {
        this.passwordOld = passwordOld;
    }

}
