
package com.omoscorplation.webtree.bkbean;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

/**
 *
 * @author Takuto Esumi
 */
@Named("IndexPage")
@ViewScoped
public class IndexPage extends BasePage{
    public IndexPage() {
    }
    
    @Override
    public void init(){
    }
    
    public String next(){
        return "master_tree.xhtml?faces-redirect=true";
    
    }
}
