/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jsf;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Rhayan
 */
@FacesValidator("jsf.BalanceValidator")
public class BalanceValidator implements Validator{

    @Override
    public void validate(FacesContext context, 
            UIComponent component, Object value) 
            throws ValidatorException {
        
    }
    
}
