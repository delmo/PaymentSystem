/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf;

import ejb.TransactionBean;
import entities.PaymentStatus;
import entities.PaymentTransaction;
import entities.SystemUser;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author Rhayan This Class is used to organise transaction in web layer.
 */
@Named
@RequestScoped
public class TransactionJSFBean implements Serializable {

    @EJB
    private TransactionBean transactionBean;

    private String my_email;
    private String other_email;
    private BigDecimal amount;
    private List<PaymentTransaction> transactions;
    private PaymentStatus pending = PaymentStatus.PENDING;
    private Long transactionId;

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public PaymentStatus getPending() {
        return pending;
    }

    public List<PaymentTransaction> getTransactions() {
        return transactions;
    }

    public String showTransaction() {
        transactions = transactionBean.showAllTransactions();
        return "transaction";
    }

    //Return list of transactions for a certian user of the system
    public List<PaymentTransaction> userTransactions(SystemUser user) {
        transactions = transactionBean.getUserTransactions(user);
        return transactions;
    }

    //Return list of pending transactions for a certain user of the system
    public List<PaymentTransaction> showPendingTransactions(SystemUser user) {
        transactions = transactionBean.getPendingTransactions(user);
        return transactions;
    }

    //Return number of pending transactions for a certain user.
    public int countPendingTrasanction(SystemUser u) {
        return showPendingTransactions(u).size();
    }

    public void setTransactions(List<PaymentTransaction> transactions) {
        this.transactions = transactions;
    }

    //Method for paying. Call the business logic method submitPayment()
    public String pay(String user_email) {
        this.my_email = user_email;
        int status = transactionBean.submitPayment(this.my_email, this.other_email, amount);
        if (status == 1) {
            return "show";
        } else if (status == -1) {
            return "nofunds";
        } else {
            return "invalidemail";
        }

    }

    //Method for requesting payment. Call the business logic method requestPayment()
    public String request(String other) {
        this.my_email = other;
        int status = transactionBean.requestPayment(this.other_email, this.my_email, amount);

        if (status == 1) {
            return "show";
        } else if (status == -1) {
            return "nofunds";
        } else {
            return "invalidemail";
        }
    }

    public String accept(Long id) {
        if(transactionBean.approvePendingTransaction(id)){
            return "show";
        }else{
            return "nofunds";
        }        
    }

    public String reject(Long id) {
        transactionBean.cancelTransaction(id);
        return "show";
    }

    public String getMy_email() {
        return my_email;
    }

    public void setMy_email(String my_email) {
        this.my_email = my_email;
    }

    public String getOther_email() {
        return other_email;
    }

    public void setOther_email(String other_email) {
        this.other_email = other_email;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

}
