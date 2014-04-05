/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models;

import entities.PaymentStatus;
import entities.PaymentTransaction;
import entities.PaymentType;
import entities.SystemUser;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
        List<PaymentTransaction> transactions;
        Query q = em.createQuery("SELECT t FROM PaymentTransaction t ", PaymentTransaction.class);
        transactions = q.getResultList();
        return transactions;
    }

    @Override
    public void sendPayment(SystemUser payer, SystemUser payee, PaymentType paymentType, PaymentStatus paymentStatus, BigDecimal amount, Date date) {
        
        PaymentTransaction newTransaction;
        newTransaction = new PaymentTransaction(payer, payee, paymentType, paymentStatus, amount, date);
        saveTransaction(newTransaction);
    }

    @Override
    public void requestPayment(SystemUser payer, SystemUser payee, PaymentType paymentType, PaymentStatus paymentStatus, BigDecimal amount, Date date) {
        PaymentTransaction newTransaction;
        newTransaction = new PaymentTransaction(payer, payee, paymentType, paymentStatus, amount, date);
        saveTransaction(newTransaction);
    }

    @Override
    public List<PaymentTransaction> getTransactions(SystemUser payer) {
        List<PaymentTransaction> transactions;
        //String status = "pending";
        Query q = em.createQuery("SELECT t FROM PaymentTransaction t WHERE t.payer = :payer AND t.paymentStatus = 'pending'", PaymentTransaction.class);
        
        transactions = q.setParameter("payer", payer).getResultList();
        
        return transactions;
    }


    @Override
    public void saveTransaction(PaymentTransaction transaction) {
        if(transaction.getId() == null){
            saveNewTransaction(transaction);
        }else{
            updateTransaction(transaction);
        }
    }

    @Override
    public void saveNewTransaction(PaymentTransaction transaction) {
         em.persist(transaction);
    }

    @Override
    public void updateTransaction(PaymentTransaction transaction) {
        em.merge(transaction);
    }

    @Override
    public void removeTransaction(PaymentTransaction transaction) {
        em.remove(transaction);
    }

    @Override
    public PaymentTransaction getTransaction(Long paymentTransactionId) {
        PaymentTransaction transaction;
        
        transaction = em.find(PaymentTransaction.class, paymentTransactionId);
        
        return transaction;
    }
   
    
}
