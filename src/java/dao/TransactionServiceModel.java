/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import entities.PaymentStatus;
import entities.PaymentTransaction;
import entities.PaymentType;
import entities.SystemUser;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Rhayan
 */
public interface TransactionServiceModel {    
    
    public List<PaymentTransaction> getTransactions();
    
    public void saveTransaction(PaymentTransaction transaction);
    
    public void saveNewTransaction(PaymentTransaction transaction);
    
    public void updateTransaction(PaymentTransaction transaction);
    
    public void removeTransaction(PaymentTransaction transaction);
    
    public void sendPayment(SystemUser payer, SystemUser payee, PaymentType paymentType, 
            PaymentStatus paymentStatus, BigDecimal amount, Date date);
    
    public void requestPayment(SystemUser payer, SystemUser payee, PaymentType paymentType, 
            PaymentStatus paymentStatus, BigDecimal amount, Date date);
    
    public List<PaymentTransaction> getTransactionsByUser(Long id);
    
    public List<PaymentTransaction> getTransactionByStatus(Long id, PaymentStatus status);    
    
    public PaymentTransaction getTransaction(Long paymentTransactionId);
       
}
