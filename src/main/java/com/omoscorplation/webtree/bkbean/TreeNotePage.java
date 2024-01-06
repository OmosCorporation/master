
package com.omoscorplation.webtree.bkbean;

import com.omoscorplation.webtree.common.Util;
import com.omoscorplation.webtree.entities.Notes;
import com.omoscorplation.webtree.facade.NotesFacade;
import com.omoscorplation.webtree.service.NotesService;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.primefaces.PrimeFaces;
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
    private Map<Integer, TreeNode> nodeMap;
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
        initTree();
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Action">
    public void initTree(){
        // treeインスタンスを作成
        this.root = new DefaultTreeNode("Root", null);
        // ノードのマップを作成（noteRidをキーとする）
        this.nodeMap = new HashMap<>();

        // ノードのマップを初期化
        for (Notes note : notesList) {
            TreeNode node = new DefaultTreeNode(note, root);
            nodeMap.put(note.getNoteRid(), node);
        }

        // ツリーの親子関係を設定
        for (Notes note : notesList) {
            TreeNode currentNode = nodeMap.get(note.getNoteRid());
            Integer parentRid = note.getParentRid();

            if (parentRid != null) {
                // 親ノードを取得し、子ノードとして追加
                TreeNode parentNode = nodeMap.get(parentRid);
                if (parentNode != null) {
                    parentNode.getChildren().add(currentNode);
                }
            }
        }
    }

    public void updateNode(Notes note){
        this.NotesFacade.edit(note);
    }
    
    public void regist(){
//        for(Object obj : this.root.getChildren()){
//            TreeNode node = (TreeNode) obj;
//        }
    }
    
    public void addNode(Notes targetNote){
        
        if(!Util.nb(targetNote)){
            TreeNode targetNode = nodeMap.get(targetNote.getNoteRid());
            Notes note = new Notes();
            Date now = new Date();
            note.setCreateDt(now);
            note.setUpdateDt(now);
            Notes parentNote = (Notes) targetNode.getData();
            note.setParentRid(parentNote.getNoteRid());
            this.NotesFacade.create(note);
            //TreeNodeにnodeを新規作成
            TreeNode node = new DefaultTreeNode(note, targetNode);
            nodeMap.put(note.getNoteRid(), node);
            targetNode.setExpanded(true);
        }
    }

    public void deleteNode(Notes targetNote){
        if(!Util.nb(targetNote)){
            TreeNode targetNode = nodeMap.get(targetNote.getNoteRid());
            //配下のnodeがある場合全て削除
            if(targetNode.getChildCount() > 0){
                for(Object obj : targetNode.getChildren()){
                    TreeNode node = (TreeNode) obj;
                    Notes note = (Notes) node.getData();
                    nodeMap.remove(note.getNoteRid());
                    this.NotesFacade.remove(note, true);
                }
            }
            if(!Util.nb(targetNote.getParentRid())){
                TreeNode parentNode = nodeMap.get(targetNote.getParentRid());
                parentNode.getChildren().remove(targetNode);
            }else{
                nodeMap.remove(targetNote.getNoteRid());
            }
            this.NotesFacade.remove(targetNote, true);
            PrimeFaces.current().executeScript("PF('treeWidget').update();");
        }
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
