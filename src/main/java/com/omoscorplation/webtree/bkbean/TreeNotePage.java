
package com.omoscorplation.webtree.bkbean;

import com.omoscorplation.webtree.common.Util;
import com.omoscorplation.webtree.entities.Notes;
import com.omoscorplation.webtree.facade.NotesFacade;
import com.omoscorplation.webtree.service.NotesService;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.Date;
import java.util.List;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author Takuto Esumi
 */

@Named("TreeNotePage")
@ViewScoped
public class TreeNotePage extends BasePage{
    public TreeNotePage() {
    }
    //<editor-fold defaultstate="collapsed" desc="Fields">
    @Inject
    private NotesService notesService;
    @Inject
    private NotesFacade NotesFacade;
    
    private String pageTabName;
    private String pageTitle;
    
    private TreeNode root;
    private List<Notes> notesList;
//    private TreeNode selectedNode;
//    private String newNodeText;


    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="initialize">
    @Override
    public void init(){
        this.pageTabName = "TreeNote - Web";
        this.pageTitle = "TreeNote";
        notesList = this.notesService.getAll();
        setTree();
    }
    
    public void setTree(){
        // treeインスタンスを作成
        root = new DefaultTreeNode("Root", null);
        // 親がいないnotesをrootに設定
        for(Notes notes : notesList){
            if (Util.nb(notes.getParentRid())) {
                TreeNode node = new DefaultTreeNode(notes, root);
            }
        }
        // 親がいるnotesを親treeに詰める
        for(Object parents : this.root.getChildren()){
            TreeNode parentNode = (TreeNode) parents;
            Notes parentNote =  (Notes) parentNode.getData();
            for(Notes childNote : notesList){
                if(parentNote.getNoteRid().equals(childNote.getParentRid())){
                    TreeNode childNode = new DefaultTreeNode(childNote);
                    parentNode.getChildren().add(childNode);
                }
            }
        }
    }
    
    public void registNode(Notes note){
        this.NotesFacade.edit(note);
    }
    
    public void regist(){
        for(Object obj : this.root.getChildren()){
            TreeNode node = (TreeNode) obj;
        }
    }
    
    public void addNode(Notes targetNote){
        for(Notes notes : notesList){
            if(notes.getNoteRid().equals(targetNote.getNoteRid())){
                TreeNode targetNode = getNode(targetNote);
                Notes note = new Notes();
                Date now = new Date();
                note.setCreateDt(now);
                note.setUpdateDt(now);
                this.NotesFacade.create(note);
                //TreeNodeにnodeを新規作成
                TreeNode node = new DefaultTreeNode(note, targetNode);
            }
        }
    }

    public TreeNode getNode(Notes note){
        TreeNode targetNode = null;
        for(Object obj : this.root.getChildren()){
            TreeNode node = (TreeNode) obj;
            Notes targetNote = (Notes) node.getData();
            if(note.getNoteRid().equals(targetNote.getNoteRid())){
                targetNode = node;
            }
            if(node.getChildCount() > 0){
                List<TreeNode> nodes = node.getChildren();
                for(TreeNode childNode : nodes){
                    Notes childNote = (Notes) childNode.getData();
                    targetNode = getNode(childNote);
                }
            }
        }
        return targetNode;
    }
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
    
    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public List<Notes> getNotesList() {
        return notesList;
    }

    public void setNotesList(List<Notes> notesList) {
        this.notesList = notesList;
    }
    
    //</editor-fold>
}
