/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

/**
 *
 * @author Rhayan
 */
@Entity
public class PaymentTransaction implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String paymentType;

    private String paymentStatus;

    private BigDecimal amount;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date date;
    
    @OneToMany(mappedBy = "paymentTransaction")
    private Collection<PaymentAccount> usersAccount;
    
    private PaymentAccount payer;
    
    private PaymentAccount payee;

    public PaymentTransaction() {
    }

    public PaymentTransaction(PaymentAccount payer, PaymentAccount payee, String paymentType, String paymentStatus, BigDecimal amount, Date date) {
        this.payer = payer;
        this.payee = payee;
        this.paymentType = paymentType;
        this.paymentStatus = paymentStatus;
        this.amount = amount;
        this.date = date;
    }

    public Collection<PaymentAccount> getUsersAccount() {
        return usersAccount;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PaymentAccount getPayer() {
        return payer;
    }

    public void setPayer(PaymentAccount payer) {
        this.payer = payer;
    }

    public PaymentAccount getPayee() {
        return payee;
    }

    public void setPayee(PaymentAccount payee) {
        this.payee = payee;
    }

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PaymentTransaction)) {
            return false;
        }
        PaymentTransaction other = (PaymentTransaction) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.PaymentTransaction[ id=" + id + " ]";
    }

}
