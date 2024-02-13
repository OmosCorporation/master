
package com.omoscorplation.webtree.bkbean;

import com.omoscorplation.webtree.common.EntityUtil;
import com.omoscorplation.webtree.common.Util;
import com.omoscorplation.webtree.entities.Category;
import com.omoscorplation.webtree.facade.CategoryFacade;
import com.omoscorplation.webtree.service.CategoryService;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Takuto Esumi
 */

@Named("CategoryPage")
@ViewScoped
public class CategoryPage extends BasePage{
    public CategoryPage() {
    }
    //<editor-fold defaultstate="collapsed" desc="Fields">
    @Inject
    private CategoryService categoryService;
    @Inject
    private CategoryFacade categoryFacade;
    
    private String pageTabName;
    private String pageTitle;
    private EntityUtil entityUtil;
    
    // Category用変数

    private Map<Integer, Object> categoryMap;

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="initialize">
    @Override
    public void init(){
        this.pageTabName = "TreeNote - Web";
        this.pageTitle = "TreeNote";
        initCategory();
    }
    
    public void initCategory(){
        
        // categoryンスタンスを作成
        this.categoryMap = new HashMap<>();
        List<Category> categoryList = this.categoryService.findAllOrderBy(entityUtil.getPrimaryKeyFieldName(Category.class));
        for(Category category : categoryList){
            this.categoryMap.put(category.getCategoryRid(), category.getCategoryName());
            if(Util.nb(getRid())){
                setRid(category.getCategoryRid());
            }
        }
        
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Action">
   
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Getter,Setter">   
    public String getPageTabName() {
        return pageTabName;
    }

    public void setPageTabName(String pageTabName) {
        this.pageTabName = pageTabName;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }
    
    public Map<Integer, Object> getCategoryMap() {
        return categoryMap;
    }

    public void setCategoryMap(Map<Integer, Object> categoryMap) {
        this.categoryMap = categoryMap;
    }
    //</editor-fold>
}
