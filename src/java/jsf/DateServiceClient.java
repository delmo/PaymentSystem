/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jsf;

import ejb.TimestampClientBean;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author Rhayan
 * Managed-bean for displaying time on the home page of the website
 */
@Named
@RequestScoped
public class DateServiceClient {
    
    @EJB
    private TimestampClientBean timer;
    

    /**
     * 
     * @return String timestamp
     */
    public String getTimenow() {        
        return timer.getTimestamp();
    }

    
}
