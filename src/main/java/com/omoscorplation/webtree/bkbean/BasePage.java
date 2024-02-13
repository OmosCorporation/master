
package com.omoscorplation.webtree.bkbean;

import com.omoscorplation.webtree.common.CommonPage;
import java.util.Set;
import java.util.logging.Logger;

/**
 *
 * @author Takuto Esumi
 */
public class BasePage extends CommonPage{
    
    //<editor-fold defaultstate="collapsed" desc="Fields">
    private transient Logger logger;
    private Integer rid;
    private Set<Integer> ridSet;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="initialize">
    @Override
    public void init(){}
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getter,Setter">   
    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }
    //</editor-fold>

    //各種javaDoc
    
    //<editor-fold defaultstate="collapsed" desc="initialize">
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Fields">
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Getter,Setter">   
    
    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public Set<Integer> getRidSet() {
        return ridSet;
    }

    public void setRidSet(Set<Integer> ridSet) {
        this.ridSet = ridSet;
    }
    
    //</editor-fold>
    
}
