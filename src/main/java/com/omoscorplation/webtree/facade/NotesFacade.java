
package com.omoscorplation.webtree.facade;

import com.omoscorplation.webtree.entities.Notes;
import jakarta.ejb.Stateless;
import jakarta.inject.Named;

/**
 *
 * @author Takuto Esumi
 * @param <UserBasic>
 */
@Named
@Stateless
public class NotesFacade extends BaseFacade<Notes>{
//<editor-fold defaultstate="collapsed" desc="Fields">
    
    private static final long serialVersionUID = 1L;   
    
    public NotesFacade(){
        super(Notes.class);
    }
//</editor-fold>
    
    
    
}
