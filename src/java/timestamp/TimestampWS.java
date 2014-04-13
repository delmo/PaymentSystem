/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timestamp;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.ejb.Stateless;

/**
 *
 * @author Rhayan
 */
@WebService(serviceName = "TimestampWS")
@Stateless()
public class TimestampWS {

     /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "getTimestamp")
    public String getTimestamp() {
        Date dateToday = new Date();;
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm a");
        return dateFormat.format(dateToday);
    }
}
