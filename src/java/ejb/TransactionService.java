/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ejb;

import entity.PaymentTransaction;
import entity.SystemUser;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Rhayan
 */
public interface TransactionService {
    
    public List<PaymentTransaction> getPaymentTransactionList();
    
    public void sendPayment(SystemUser payer, SystemUser payee, BigDecimal amount);
    
    public void receivePayment(SystemUser user, SystemUser payee, BigDecimal amount);
}
