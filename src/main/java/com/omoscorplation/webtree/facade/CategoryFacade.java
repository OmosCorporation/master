
package com.omoscorplation.webtree.facade;

import com.omoscorplation.webtree.entities.Category;
import jakarta.ejb.Stateless;
import jakarta.inject.Named;

/**
 *
 * @author Takuto Esumi
 */
@Named
@Stateless
public class CategoryFacade extends BaseFacade<Category>{
//<editor-fold defaultstate="collapsed" desc="Fields">
    
    private static final long serialVersionUID = 1L;   
    
    public CategoryFacade(){
        super(Category.class);
    }
//</editor-fold>
    
}
