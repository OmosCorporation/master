
package com.omoscorplation.webtree.bkbean;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

/**
 *
 * @author Takuto Esumi
 */
@Named("LoginPage")
@ViewScoped
public class LoginPage extends BasePage{
    
    private String loginId;
    private String password;

    public LoginPage() {
    }
    
    @Override
    public void init(){
    }
    
    public String next(){
        if(loginId.equals("omos") && password.equals("pass")){
            return "note_template.xhtml?faces-redirect=true";
        }
        return "result.xhtml?faces-redirect=true";
    
    }
    
    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
