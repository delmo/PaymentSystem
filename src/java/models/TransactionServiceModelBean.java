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
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Rhayan
 */
@Stateless
public class TransactionServiceModelBean implements TransactionServiceModel{

    @PersistenceContext
    EntityManager em;

    @Override
    public List<PaymentTransaction> getPaymentTransactionList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void sendPayment(SystemUser payer, SystemUser payee, String paymentType, String paymentStatus, BigDecimal amount, Date date) {
        
        PaymentTransaction newTransaction;
        newTransaction = new PaymentTransaction(payer, payee, paymentType, paymentStatus, amount, date);
        em.persist(newTransaction);
    }

    @Override
    public void requestPayment(SystemUser payer, SystemUser payee, String paymentType, String paymentStatus, BigDecimal amount, Date date) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<PaymentTransaction> getPendingTransaction(SystemUser payer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cancelPayment(Long transactionId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void approvePayment(Long transactionId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
}
