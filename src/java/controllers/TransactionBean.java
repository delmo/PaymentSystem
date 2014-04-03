/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;

import entities.SystemUser;
import java.math.BigDecimal;
import java.util.Date;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import models.TransactionServiceModel;
import models.UserServiceModel;

/**
 *
 * @author Rhayan
 */
@Named
@RequestScoped
public class TransactionBean {
    
    @EJB
    UserServiceModel userService;
    
    @EJB
    TransactionServiceModel transactionService;
    
    
    private String email;
    private String currency;
    private Long transactionId;
    private SystemUser payer;
    private SystemUser payee;
    private String paymentType;
    private String paymentStatus;
    private BigDecimal amount;
    private Date date;
    
    
    public String submitPayment(SystemUser payer){
        
        payee = userService.findUser(email);
        
        System.out.println("Payee email: " + payee.getEmail());
        System.out.println("Payer email: " + payer.getEmail());
        
        if (payee != null){
            if (payer.getBalance().compareTo(amount) == 1) {
                transactionService.sendPayment(payer, payee, "debit", "completed", amount, date);
                
                BigDecimal payee_new_balance = payee.getBalance().add(amount);
                BigDecimal payer_new_balance = payer.getBalance().subtract(amount);
                
                payer.setBalance(payer_new_balance);
                payee.setBalance(payee_new_balance);
                
                System.out.println("Payee new balance: " + payee.getBalance());
                System.out.println("Payer new balance: " + payer.getBalance());
                
                userService.updateUser(payer);
                userService.updateUser(payee);
            }
        }
        return "show";
    }
    
    
    public String requestPayment(){

        return "show";
    }
    
    public UserServiceModel getUserService() {
        return userService;
    }

    public void setUserService(UserServiceModel userService) {
        this.userService = userService;
    }

    public TransactionServiceModel getTransactionService() {
        return transactionService;
    }

    public void setTransactionService(TransactionServiceModel transactionService) {
        this.transactionService = transactionService;
    }    
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
    
    
    
    
    
}
