
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
    private String updateCategoryName;
    private Category targetCategory;
    private String categoryText;

    private Map<Integer, Object> categoryNameMap;
    private Map<Integer, Category> categoryMap;
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
        this.categoryNameMap = new HashMap<>();
        List<Category> categoryList = this.categoryService.findAllOrderBy(entityUtil.getPrimaryKeyFieldName(Category.class));
        for(Category category : categoryList){
            this.categoryMap.put(category.getCategoryRid(), category);
            this.categoryNameMap.put(category.getCategoryRid(), category.getCategoryName());
            if(Util.nb(getRid())){
                setRid(category.getCategoryRid());
                this.targetCategory = category;
                this.categoryText = this.targetCategory.getCategoryName();
            }
        }
    }
    
    public void updateTarget(Integer rid){
        for (Map.Entry<Integer, Category> entry : this.categoryMap.entrySet()) {
            if(entry.getKey().equals(rid)){
                Category category = (Category)entry.getValue();
                this.targetCategory = category;
                this.categoryText = this.targetCategory.getCategoryName();
                break;
            }
        }
   }

    public void categoryTitleUpdate(String value){
        this.targetCategory.setCategoryName(value);
        this.categoryFacade.edit(this.targetCategory);
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
    
        
    public Category getTargetCategory() {
        return targetCategory;
    }

    public void setTargetCategory(Category targetCategory) {
        this.targetCategory = targetCategory;
    }
    
    public String getCategoryText() {
        return categoryText;
    }

    public void setCategoryText(String categoryText) {
        this.categoryText = categoryText;
    }
    
    public String getUpdateCategoryName() {
        return updateCategoryName;
    }

    public void setUpdateCategoryName(String updateCategoryName) {
        this.updateCategoryName = updateCategoryName;
    }

    public Map<Integer, Object> getCategoryNameMap() {
        return categoryNameMap;
    }

    public void setCategoryNameMap(Map<Integer, Object> categoryNameMap) {
        this.categoryNameMap = categoryNameMap;
    }
    
    public Map<Integer, Category> getCategoryMap() {
        return categoryMap;
    }

    public void setCategoryMap(Map<Integer, Category> categoryMap) {
        this.categoryMap = categoryMap;
    }

    //</editor-fold>
}
