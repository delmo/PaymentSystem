/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.xml.ws.WebServiceRef;


/**
 *
 * @author Rhayan
 */
@Stateless
@DeclareRoles({"users", "admins"})
public class TimestampClientBean {
//    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/TimestampWS/TimestampWS.wsdl")
//    private TimestampWS_Service service;
    

    private Date dateTimeNow;

    @PermitAll
    public Date getDateTimeNow() {
        SimpleDateFormat formater = new SimpleDateFormat("MM/dd/yyyy HH:mm a");
        try {
            dateTimeNow = formater.parse(getTimestamp());
        } catch (ParseException ex) {
            Logger.getLogger(TimestampClientBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dateTimeNow;        
    }

    //@PermitAll
    
//    private String getTimestamp() {
//        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
//        // If the calling of port operations may lead to race condition some synchronization is required.
//        controllers.TimestampWS_Service service = new controllers.TimestampWS_Service();
//        controllers.TimestampWS port = service.getTimestampWSPort();
//        return port.getTimestamp();
//    }

    @PermitAll
    public String getTimestamp() {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        TimestampWS_Service service = new TimestampWS_Service();
        ejb.TimestampWS port = service.getTimestampWSPort();
        return port.getTimestamp();
    }

    
}
