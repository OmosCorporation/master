
package com.omoscorplation.webtree.common;

import java.io.Serializable;
import jakarta.annotation.PostConstruct;

/**
 *
 * @author Takuto Esumi
 */
public abstract class  CommonPage implements Serializable{
    private static final long serialVersionUID = 1L;
    public CommonPage() {
    }
    @PostConstruct
    public void initialize() {
        Exception exception = null;
        try {
            init();
        } catch (Exception ex) {
            exception = ex;
        }
    }
    public abstract void init();
    
}
