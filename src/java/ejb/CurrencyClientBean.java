/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ejb;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.ejb.Stateless;
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
@Stateless
public class CurrencyClientBean {
    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/PaymentSystem/";
   
    public CurrencyClientBean() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("conversion");   
        
    }

    public String getConversion(String currency1, String currency2, String amount_of_currency1) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}/{1}/{2}", new Object[]{currency1, currency2, amount_of_currency1}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(String.class);
    }

    public BigDecimal convert(String fromCurrency, String toCurrency, BigDecimal amount){
        BigDecimal value;
        String convertedValue;
        convertedValue = getConversion(fromCurrency, toCurrency, amount.toString());
        String[] result = convertedValue.split(" ");
        value = new BigDecimal(result[3]);        
        return value;        
    }
        
    public void close() {
        client.close();
    }

}
