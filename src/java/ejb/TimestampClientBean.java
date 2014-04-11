/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ejb;

import javax.ejb.Stateless;


/**
 *
 * @author Rhayan
 */
@Stateless
public class TimestampClientBean {
//    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/TimestampWS/TimestampWS.wsdl")
//    private TimestampWS_Service service;
       
    
    private String dateTimeNow;

    public String getDateTimeNow() {
        return getTimestamp();
    }

    private String getTimestamp() {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        controllers.TimestampWS_Service service = new controllers.TimestampWS_Service();
        controllers.TimestampWS port = service.getTimestampWSPort();
        return port.getTimestamp();
    }

   
    
}
