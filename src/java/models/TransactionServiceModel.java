/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models;

import entities.PaymentTransaction;
import entities.SystemUser;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Rhayan
 */
public interface TransactionServiceModel {    
    
    public List<PaymentTransaction> getPaymentTransactionList();
    
    public void sendPayment(SystemUser payer, SystemUser payee, String paymentType, 
            String paymentStatus, BigDecimal amount, Date date);
    
    public void requestPayment(SystemUser payer, SystemUser payee, String paymentType, 
            String paymentStatus, BigDecimal amount, Date date);
    
    public List<PaymentTransaction> getPendingTransaction(SystemUser payer);
    
    public void cancelPayment(Long transactionId);
    
    public void approvePayment(Long transactionId);
}
