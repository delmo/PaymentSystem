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
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Rhayan
 * DAO for PaymentTransaction entity
 */
@Stateless
public class TransactionServiceModelBean implements TransactionServiceModel {

    @PersistenceContext
    EntityManager em;

    /**
     * Method to find all transactions
     * @return list of PaymentTransaction
     */
    @Override
    public List<PaymentTransaction> getTransactions() {
        return em.createNamedQuery("findAllTransactions").getResultList();
    }

    /**
     * Method for sending payment to other user of the system.
     * @param payer SystemUser who paying
     * @param payee SystemUser who receives the payment
     * @param paymentType type of payment whether it is debit or credit to payer
     * @param paymentStatus payment status whether it is pending, rejected or completed
     * @param amount BigDecimal amount
     * @param date Date the transaction took place.
     */
    @Override
    public void sendPayment(SystemUser payer, SystemUser payee, PaymentType paymentType, PaymentStatus paymentStatus, BigDecimal amount, Date date) {

        PaymentTransaction newTransaction;
        newTransaction = new PaymentTransaction(payer, payee, paymentType, paymentStatus, amount, date);
        saveTransaction(newTransaction);
    }

    /**
     * Method for requesting payment to someone.
     * @param payer SystemUser who paying
     * @param payee SystemUser who receives the payment
     * @param paymentType type of payment whether it is debit or credit to payer
     * @param paymentStatus payment status whether it is pending, rejected or completed
     * @param amount BigDecimal amount
     * @param date Date the transaction took place.
     */
    @Override
    public void requestPayment(SystemUser payer, SystemUser payee, PaymentType paymentType, PaymentStatus paymentStatus, BigDecimal amount, Date date) {
        PaymentTransaction newTransaction;
        newTransaction = new PaymentTransaction(payer, payee, paymentType, paymentStatus, amount, date);
        saveTransaction(newTransaction);
    }

    /**
     * Method for getting the list of all payment transactions per user
     * @param id Long id of the system user
     * @return List of PaymentTransaction related to user id.
     */
    @Override
    public List<PaymentTransaction> getTransactionsByUser(Long id) {
        List<PaymentTransaction> transactions;
        transactions = em.createNamedQuery("findAllTransactionById").setParameter("payerId", id).setParameter("payeeId", id).getResultList();

        return transactions;
    }

    /**
     * Method for saving transactions. If new, then new transaction will save; otherwise, update
     * @param transaction receives PaymentTransaction
     */
    @Override
    public void saveTransaction(PaymentTransaction transaction) {
        if (transaction.getId() == null) {
            saveNewTransaction(transaction);
        } else {
            updateTransaction(transaction);
        }
    }

    /**
     * Method for saving new transaction
     * @param transaction accepts PaymentTransaction
     */
    @Override
    public void saveNewTransaction(PaymentTransaction transaction) {
        em.persist(transaction);
    }

    /**
     * Method for updating existing transaction.
     * @param transaction accepts PaymentTranasction object.
     */
    @Override
    public void updateTransaction(PaymentTransaction transaction) {
        em.merge(transaction);
    }

    /**
     * Method for removing transaction.
     * @param transaction accepts PaymentTranaction
     */
    @Override
    public void removeTransaction(PaymentTransaction transaction) {
        em.remove(transaction);
    }

    /**
     * Method for finding a transaction based on id
     * @param paymentTransactionId
     * @return PaymentTransaction
     */
    @Override
    public PaymentTransaction getTransaction(Long paymentTransactionId) {
        PaymentTransaction transaction;

        transaction = em.find(PaymentTransaction.class, paymentTransactionId);

        return transaction;
    }

    /**
     * Method for getting list of transaction define by its user's id and status of the payment.
     * @param id of a SystemUser
     * @param status whether pending, completed or rejected
     * @return List of PaymentTransaction
     */
    @Override
    public List<PaymentTransaction> getTransactionByStatus(Long id, PaymentStatus status) {
        List<PaymentTransaction> transactions;

        transactions = em.createNamedQuery("findAllPendingTransaction").setParameter("id", id).setParameter("status", status).getResultList();

        return transactions;
    }

}
