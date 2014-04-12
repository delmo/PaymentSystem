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
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Rhayan
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

    public String showTransaction(){
        transactions = transactionBean.showAllTransactions();
        return "transaction";
    }
    
    public List<PaymentTransaction> userTransactions(SystemUser user){
        transactions = transactionBean.getUserTransactions(user);
        return transactions;
    }
    
    public List<PaymentTransaction> showPendingTransactions(SystemUser user){
        transactions = transactionBean.getPendingTransactions(user);
        return transactions;
    }
    
    public int countPendingTrasanction(SystemUser u){
        
        return showPendingTransactions(u).size();
    }
    
    public void setTransactions(List<PaymentTransaction> transactions) {
        this.transactions = transactions;
    }
    
    public String pay(String user_email){
        this.my_email = user_email;
        if(transactionBean.submitPayment(this.my_email, this.other_email, amount)){
            return "show";
        }        
        return "error";
    }
    
    public String request(String other){
        this.my_email = other;        
        if(transactionBean.requestPayment(this.other_email, this.my_email, amount)){
            return "show";
        }
        return "error";
    }
    
    public String accept(Long id){
        transactionBean.approvePendingTransaction(id);
        return "show";
    }
    
    public String reject(Long id){
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
