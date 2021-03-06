/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

/**
 *
 * @author Rhayan
 * Entity class for recording transactions
 */
@Entity
@NamedQueries({
    @NamedQuery(
            name="findAllTransactionById",
            query="SELECT t FROM PaymentTransaction t WHERE t.payer.Id = :payerId OR t.payee.Id = :payeeId"
    ),
    @NamedQuery(
            name="findAllPending",
            query="SELECT t FROM PaymentTransaction t WHERE t.paymentStatus = :status"
    ),
    @NamedQuery(
            name="findAllPendingTransaction",
            query="SELECT t FROM PaymentTransaction t WHERE t.payer.Id = :id AND t.paymentStatus = :status"
    ),
    @NamedQuery(
            name="findAllTransactions",
            query="SELECT t FROM PaymentTransaction t"
    )
})
public class PaymentTransaction implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TRANSACTION_ID")
    private Long id;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;   

    @Column(name="AMOUNT", scale = 2, precision = 13)
    private BigDecimal amount;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date date;
    
    @OneToMany(mappedBy = "paymentTransaction")
    private Collection<SystemUser> usersAccount;
    
    private SystemUser payer;
    
    private SystemUser payee;

    public PaymentTransaction() {
    }

    public PaymentTransaction(SystemUser payer, SystemUser payee, PaymentType paymentType, PaymentStatus paymentStatus, BigDecimal amount, Date date) {
        this.payer = payer;
        this.payee = payee;
        this.paymentType = paymentType; //debit or credit to payer 
        this.paymentStatus = paymentStatus; //pending or completed
        this.amount = amount;
        this.date = date;        
    }
    
    public Collection<SystemUser> getUsersAccount() {
        return usersAccount;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
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

    public SystemUser getPayer() {
        return payer;
    }

    public void setPayer(SystemUser payer) {
        this.payer = payer;
    }

    public SystemUser getPayee() {
        return payee;
    }

    public void setPayee(SystemUser payee) {
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
