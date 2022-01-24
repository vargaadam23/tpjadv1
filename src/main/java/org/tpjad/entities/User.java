package org.tpjad.entities;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha1Hash;
import org.apache.shiro.util.ByteSource;
import org.apache.tapestry5.beaneditor.NonVisual;
import org.apache.tapestry5.beaneditor.Validate;


@Entity
@Table(name = "users")
public class User
{
    public static enum Role {
        user(1), manager(5);
        private int weight;

        Role(int weight) {
            this.weight = weight;
        }

        public int weight() {
            return weight;
        }
    }

    private Integer id;

    private String username;

    private String firstName;

    private String lastName;

    private String emailAddress;

    private String encodedPassword;

    private String phone;

    private Date created = new Date();

    private boolean accountLocked;

    private boolean credentialsExpired;

    private Set<Role> roles = new HashSet<Role>();

    private byte[] passwordSalt;



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NonVisual
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        try {
            return (obj instanceof User && ((User) obj).getUsername().equals(username));
        } catch (NullPointerException e) {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return username == null ? 0 : username.hashCode();
    }

    @Column(unique = true)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Validate("required,regexp=^[0-9a-zA-Z._%+-]+@[0-9a-zA-Z]+[\\.]{1}[0-9a-zA-Z]+[\\.]?[0-9a-zA-Z]+$")
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @NonVisual
    public String getEncodedPassword() {
        return encodedPassword;
    }

    public void setEncodedPassword(String encodedPassword) {
        this.encodedPassword = encodedPassword;
    }

    @NonVisual
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public boolean isAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    public boolean isCredentialsExpired() {
        return credentialsExpired;
    }

    public void setCredentialsExpired(boolean credentialsExpired) {
        this.credentialsExpired = credentialsExpired;
    }

    public void setRoles(Set<Role> roles) {
        this.roles.addAll(roles);
    }

    @ElementCollection
    public Set<Role> getRoles() {
        return roles;
    }

    @Transient
    public String getPassword() {
        return getEncodedPassword();
    }

    public void setPassword(String password) {
        if (password != null && !password.equals(encodedPassword) && !"".equals(password)) {
            ByteSource saltSource = new SecureRandomNumberGenerator().nextBytes();
            this.passwordSalt = saltSource.getBytes();
            this.encodedPassword = new Sha1Hash(password, saltSource).toString();
        }
    }

    @Override
    public String toString() {
        return "User " + username;
    }

    public void setPasswordSalt(byte[] passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    @NonVisual
    @Column(length = 128)
    public byte[] getPasswordSalt() {
        return passwordSalt;
    }


    @Transient
    public Object getLocalAccountPrimaryPrincipal() {
        return getId();
    }

}