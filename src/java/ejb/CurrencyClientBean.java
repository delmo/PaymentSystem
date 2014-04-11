/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ejb;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

/**
 * Jersey REST client generated for REST resource:RSCurrency [conversion]<br>
 * USAGE:
 * <pre>
 *        CurrencyClientBean client = new CurrencyClientBean();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Rhayan
 */
@Named
@RequestScoped
public class CurrencyClientBean implements Serializable{
    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/PaymentSystem/";
    private String currency1;
    private String currency2;
    private String amount;
    private String convertedAmount;
    private static final String INITIAL_DEPOSIT = "1000000000";

    
//    public CurrencyClientBean(){        
//    }
    
    public CurrencyClientBean() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("conversion");   
        
    }

    public String getConversion(String currency1, String currency2, String amount_of_currency1) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}/{1}/{2}", new Object[]{currency1, currency2, amount_of_currency1}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(String.class);
    }

    public String initialDeposit(){       
        
        convertedAmount = getConversion(currency1, currency2, INITIAL_DEPOSIT);
        
        return "result";
    }
    public void close() {
        client.close();
    }

    public String getCurrency1() {
        return currency1;
    }

    public void setCurrency1(String currency1) {
        this.currency1 = currency1;
    }

    public String getCurrency2() {
        return currency2;
    }

    public void setCurrency2(String currency2) {
        this.currency2 = currency2;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getConvertedAmount() {        
        return convertedAmount;
    }

    public void setConvertedAmount(String convertedAmount) {
        this.convertedAmount = convertedAmount;
    }
    
    
}
