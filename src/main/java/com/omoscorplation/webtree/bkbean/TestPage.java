
package com.omoscorplation.webtree.bkbean;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;



/**
 *
 * @author Takuto Esumi
 */

@Named("TestPage")
@ViewScoped
public class TestPage extends BasePage{
    public TestPage() {
    }
    //<editor-fold defaultstate="collapsed" desc="Fields">
    
    private String pageTabName;
    private String pageTitle;

    private Map<String,Map<String,Boolean>> parentMap =new LinkedHashMap<>();
    private String[] codes = {"childMap2","childMap3"};

//    2024/01/22
    private Map<String,Boolean> masterMap = new HashMap<>();
    private Map<String,Boolean> selectedMap = new HashMap<>();
    private List<String> selectedList = new ArrayList<>();
    private boolean validateError = false;
    private String firstPage;
    private List<String> firstPageItems = new ArrayList<>();
    
    public String getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(String firstPage) {
        this.firstPage = firstPage;
    }

    public List<String> getFirstPageItems() {
        return firstPageItems;
    }

    public void setFirstPageItems(List<String> firstPageItems) {
        this.firstPageItems = firstPageItems;
    }
    
    public void setItems() {
        this.firstPageItems.add("valueA");
        this.firstPageItems.add("valueB");
        this.firstPageItems.add("valueC");
    }
    
   
    public String[] getCodes() {
        return codes;
    }

    public void setCodes(String[] codes) {
        this.codes = codes;
    }
    
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="initialize">
    @Override
    public void init(){
        this.pageTabName = "TreeNote - Web";
        this.pageTitle = "TreeNote";
        
        this.masterMap.put("master1", false);
        this.masterMap.put("master2", true);
        this.masterMap.put("master3", false);
        this.masterMap.put("master4", true);
        this.masterMap.put("master5", false);
        
        for (Map.Entry<String, Boolean> entry : this.masterMap.entrySet()) {
            if (entry.getValue()) {
                this.selectedList.add(entry.getKey());
            }
        }
        setItems();
    }
    
    public void validationCheck() {
        
        if(this.selectedList.isEmpty()){
           this.validateError = true;
        }else{
            this.validateError = true;
        }
        
    }
    
    public boolean isNotRegist () {
        
        if (this.selectedList.isEmpty()) {
            return true;
        }
        return false;
    }
            
            
    public void changeOption () {
        FacesContext context = FacesContext.getCurrentInstance();
        
        if (this.selectedList.isEmpty()) {
            FacesMessage message = new FacesMessage("どれか一つを選んで下さい");
            context.addMessage("testTag", message); // "form"は適切なフォームIDに置き換えてください
            context.validationFailed();
        }
        
        System.out.println("this.masterMap:" + this.masterMap);
        System.out.println("this.selectedList:" + this.selectedList);
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Action">
    public void regist () {
        System.out.println("this.masterMap:" + this.masterMap);
        System.out.println("this.selectedList:" + this.selectedList);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Getter,Setter">   
    
        
    public Map<String, Map<String, Boolean>> getParentMap() {
        return parentMap;
    }

    public void setParentMap(Map<String, Map<String, Boolean>> parentMap) {
        this.parentMap = parentMap;
    }

    
    public Map<String, Boolean> getMasterMap() {
        return masterMap;
    }

    public void setMasterMap(Map<String, Boolean> masterMap) {
        this.masterMap = masterMap;
    }

    public Map<String, Boolean> getSelectedMap() {
        return selectedMap;
    }

    public void setSelectedMap(Map<String, Boolean> selectedMap) {
        this.selectedMap = selectedMap;
    }
    
    public List<String> getSelectedList() {
        return selectedList;
    }

    public void setSelectedList(List<String> selectedList) {
        this.selectedList = selectedList;
    }
    
    
    
    
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
    
    public boolean isValidateError() {
        return validateError;
    }

    public void setValidateError(boolean validateError) {
        this.validateError = validateError;
    }

    //</editor-fold>
}
