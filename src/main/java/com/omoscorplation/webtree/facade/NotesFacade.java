
package com.omoscorplation.webtree.facade;

import com.omoscorplation.webtree.entities.Notes;
import jakarta.ejb.Stateless;
import jakarta.inject.Named;
import java.util.List;

/**
 *
 * @author Takuto Esumi
 * @param <Notes>
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
    
    public List<Notes> findNotesByCategory(Integer categoryRid){
        
        // JPQLクエリの作成
        String jpql = "SELECT n FROM Notes n WHERE n.categoryRid = :categoryId";
        // クエリの作成とパラメーターの設定
        List<Notes> notesList = super.getEntityManager().createQuery(jpql, Notes.class)
                .setParameter("categoryId", categoryRid)
                .getResultList();
        return notesList;
    }
    
    
}
