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
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Rhayan
 */
public class TransactionServiceModelBean implements TransactionServiceModel{

    @PersistenceContext
    EntityManager em;
    
    
    @Override
    public List<PaymentTransaction> getPaymentTransactionList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void sendPayment(SystemUser payer, SystemUser payee, BigDecimal amount) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void receivePayment(SystemUser user, SystemUser payee, BigDecimal amount) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
