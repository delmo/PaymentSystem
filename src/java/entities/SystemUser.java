/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 *
 * @author Rhayan
 * Entity class for users of the system
 */
@Entity
@NamedQueries({
    @NamedQuery(
            name="findAllSystemUsersWithEmail",
            query="SELECT u FROM SystemUser u WHERE u.email = :email"
    ),
    @NamedQuery(
            name="findAllSystemUsers",
            query="SELECT u FROM SystemUser u"
    ),
    @NamedQuery(
            name="findAllEmails",
            query="SELECT u.email FROM SystemUser u"
    )
})
public class SystemUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_ID")
    private Long Id;

    @NotNull
    private String firstname;

    @NotNull
    private String lastname;

    @NotNull
    @Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\."
            + "[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@"
            + "(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
            message = "{invalid.email}")
    private String email;

    @NotNull
    private String password;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date registrationDate;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date lastVisit;   

    @ManyToOne
    @JoinColumn(name = "GROUP_ID")
    private UserGroup userGroup;
    
    @ManyToOne
    @JoinColumn(name = "TRANSACTION_ID")
    private PaymentTransaction paymentTransaction;
    
    private String currency;
    
    @Column(name="BALANCE", scale = 2, precision = 13)
    private BigDecimal balance;

    public SystemUser() {
    }

    public SystemUser(String firstname, String lastname, String email,
            String password, BigDecimal balance, String currency, Date registrationDate, Date lastVisit) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.balance = balance;
        this.currency = currency;
        this.registrationDate = registrationDate;
        this.lastVisit = lastVisit;
        
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(Date lastVisit) {
        this.lastVisit = lastVisit;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }

    public PaymentTransaction getPaymentTransaction() {
        return paymentTransaction;
    }

    public void setPaymentTransaction(PaymentTransaction paymentTransaction) {
        this.paymentTransaction = paymentTransaction;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.Id);
        hash = 71 * hash + Objects.hashCode(this.email);
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
        final SystemUser other = (SystemUser) obj;
        if (!Objects.equals(this.Id, other.Id)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SystemUser{" + "Id=" + Id + ", firstname=" + firstname + ", lastname=" + lastname + ", email=" + email + ", password=" + password + ", registrationDate=" + registrationDate + ", lastVisit=" + lastVisit + ", userGroup=" + userGroup + ", currency=" + currency + ", balance=" + balance + '}';
    }

    
    
   

}
