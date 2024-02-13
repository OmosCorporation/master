
package com.omoscorplation.webtree.service;

import com.omoscorplation.webtree.entities.Category;
import com.omoscorplation.webtree.facade.CategoryFacade;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;

/**
 *
 * @author Takuto Esumi
 */
@Named
@Stateless
@Dependent
public class CategoryService extends BaseService{
    
    @Inject
    private CategoryFacade categoryFacade;
  
    public List<Category> getAll(){
        return this.categoryFacade.findAll();
    }
    
    public List<Category> findAllOrderBy(String EntityKey){
        return this.categoryFacade.findAllOrderBy(EntityKey);
    }
    
}
