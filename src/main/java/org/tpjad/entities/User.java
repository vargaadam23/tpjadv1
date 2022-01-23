package org.tpjad.entities;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.Email;

@Entity
@Table(name = "users")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    @Column(nullable = false, unique = true)
    @NotNull
    @Size(min = 3, max = 15)
    private String username;

    @Column(nullable = false)
    @NotNull
    @Size(min = 3, max = 50)
    private String fullname;

    @Column(nullable = false)
    @NotNull
    private String email;

    @Column(nullable = false)
    @Size(min = 3, max = 256)
    @NotNull
    private String password;

    @Column(nullable = false)
    @Size(min = 9, max = 14)
    @NotNull
    private String phone;

    public User()
    {
    }

    public User(final String fullname, final String username, final String email, final String phone)
    {
        this.fullname = fullname;
        this.username = username;
        this.email = email;
        this.phone = phone;
    }

    /**
     * Construct a new user using an already-encrypted password
     * @param fullname the full name (e.g. "John Smith")
     * @param username the username
     * @param email the email address
     * @param password the password (already encrypted)
     */
    public User(final String fullname, final String username, final String email,final String phone,
                final String password)
    {
        this(fullname, username, email, phone);
        this.password = password;
    }

    /**
     * Construct a user using an already-known database ID
     * @param id the database ID (primary key)
     * @param fullname the full name (e.g. "John Smith")
     * @param username the username
     * @param email the email address
     * @param password the password (already encrypted)
     */
    public User(Long id, String username, String fullname, String email, String phone, String password)
    {
        super();
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("id ");
        builder.append(id);
        builder.append(",");
        builder.append("username ");
        builder.append(username);
        return builder.toString();
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setFullname(String fullname)
    {
        this.fullname = fullname;
    }

    public String getFullname()
    {
        return fullname;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}