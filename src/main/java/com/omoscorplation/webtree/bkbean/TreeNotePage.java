
package com.omoscorplation.webtree.bkbean;

import com.omoscorplation.webtree.common.EntityUtil;
import com.omoscorplation.webtree.common.Util;
import com.omoscorplation.webtree.entities.Category;
import com.omoscorplation.webtree.entities.Notes;
import com.omoscorplation.webtree.facade.CategoryFacade;
import com.omoscorplation.webtree.facade.NotesFacade;
import com.omoscorplation.webtree.service.CategoryService;
import com.omoscorplation.webtree.service.NotesService;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author Takuto Esumi
 */

@Named("TreeNotePage")
@ViewScoped
public class TreeNotePage extends BasePage{
    
    private static final long serialVersionUID = 108478021854906487L;
    
    public TreeNotePage() {
    }
    //<editor-fold defaultstate="collapsed" desc="Fields">
    @Inject
    private CategoryService categoryService;
    @Inject
    private CategoryFacade categoryFacade;
    @Inject
    private NotesService notesService;
    @Inject
    private NotesFacade NotesFacade;
    
    private String pageTabName;
    private String pageTitle;
    private EntityUtil entityUtil;
    private Notes editTargetNote;
    private TreeNode selectedNode;

    private Map<Integer, Object> categoryMap;




    // tree用変数
    private TreeNode root;
    private Map<Integer, TreeNode> nodeMap;
    private List<Notes> notesList;
    
    private boolean expendMode = false;
    private boolean delMode = false;

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="initialize">
    @Override
    public void init(){
        this.pageTabName = "TreeNote - Web";
        this.pageTitle = "TreeNote";
        System.out.println("call initTree() by init()");
        initTree();
    }
    
    public void initCategory(){
        
        // categoryンスタンスを作成
        this.categoryMap = new HashMap<>();
        List<Category> categoryList = this.categoryService.findAllOrderBy(entityUtil.getPrimaryKeyFieldName(Category.class));
        for(Category category : categoryList){
            this.categoryMap.put(category.getCategoryRid(), category.getCategoryName());
        }
        
    }
    
    public void initTree(){
        // treeインスタンスを作成
        if(!Util.nb(getRid())){
            notesList = this.notesService.findNotesByCategory(getRid());
        }else{
            notesList = this.notesService.getAll();
        }
        this.root = new DefaultTreeNode("Root", null);
        // ノードのマップを作成（noteRidをキーとする）
        this.nodeMap = new HashMap<>();

        // ノードのマップを初期化
        for (Notes note : notesList) {
            TreeNode node = new DefaultTreeNode(note, root);
            node.setExpanded(note.getExpand());
            nodeMap.put(note.getNoteRid(), node);
            if(Util.nb(this.editTargetNote)){
                this.editTargetNote = note;
            }
        }

        // ツリーの親子関係を設定
        for (Notes note : notesList) {
            Integer parentRid = note.getParentRid();
            if (parentRid != null) {
                // 親ノードを取得し、子ノードとして追加
                TreeNode parentNode = nodeMap.get(parentRid);
                if (parentNode != null) {
                    TreeNode currentNode = nodeMap.get(note.getNoteRid());
                    currentNode.setExpanded(note.getExpand());
                    parentNode.getChildren().add(currentNode);
                }
            }
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Action">
    public void changeCategory(Integer categoryRid){
        setRid(categoryRid);
        System.out.println("call initTree() by changeCategory()");
        initTree();
    }
        
    public void changeDelMode(){
        if(this.delMode){
            this.delMode = false;
        }else{
            this.delMode = true;
        }
    }
   
    public String getNameDelMode(){
        if(this.delMode){
            return "AddMode:ON";
        }else{
            return "DelMode:ON";
        }
    }
    
    public void changeExpandlMode(){
        if(this.expendMode){
            for(Object obj : this.root.getChildren()){
                TreeNode node = (TreeNode) obj;
                node.setExpanded(false);
                Notes note = (Notes) node.getData();
                note.setExpand(false);
            }
            this.expendMode = false;
        }else{
            for(Object obj : this.root.getChildren()){
                TreeNode node = (TreeNode) obj;
                node.setExpanded(true);
                Notes note = (Notes) node.getData();
                note.setExpand(true);
            }
            this.expendMode = true;
        }
    }
   
    public String getExpandModeName(){
        if(this.expendMode){
            return "Collapsed:ON";
        }else{
            return "AllExpand:ON";
        }
    }
    
    public void addRote(){
            Notes note = new Notes();
            Date now = new Date();
            note.setCreateDt(now);
            note.setUpdateDt(now);
            note.setCategoryRid(getRid());
            note.setExpand(true);
            note.setStyle("color: #008BBB");
            this.NotesFacade.create(note);
            //TreeNodeにnodeを新規作成
            TreeNode node = new DefaultTreeNode(note, this.root);
            node.setExpanded(true);
            nodeMap.put(note.getNoteRid(), node);
    }
    
    public void updateNode(Notes note){
        this.NotesFacade.edit(note);
    }
    
    public void updateNodeStyle(Notes note){
        String style =note.getStyle();
        TreeNode currentNode = nodeMap.get(note.getNoteRid());
        for(Object nodeObj : currentNode.getChildren()){
            TreeNode node = (TreeNode) nodeObj;
            Notes childNote = (Notes) node.getData();
            childNote.setStyle(style);
            updateNode(childNote);
        }
        updateNode(note);
    }

    public void setEditTarget(Notes note){
        this.editTargetNote = note;
    }
    
    public void updateEditTarget(){
        this.NotesFacade.edit(this.editTargetNote);
    }

    public void regist(){
//        for(Object obj : this.root.getChildren()){
//            TreeNode node = (TreeNode) obj;
//        }
    }
    
        public void addNode(Notes targetNote){
        
        if(!Util.nb(targetNote)){
            TreeNode targetNode = nodeMap.get(targetNote.getNoteRid());
            // notesの新規作成
            Notes note = new Notes();
            Date now = new Date();
            note.setCreateDt(now);
            note.setUpdateDt(now);
            Notes parentNote = (Notes) targetNode.getData();
            note.setParentRid(parentNote.getNoteRid());
            note.setCategoryRid(parentNote.getCategoryRid());
            note.setExpand(true);
            note.setStyle(parentNote.getStyle());
            this.NotesFacade.create(note);
            //TreeNodeにnodeを新規作成
            TreeNode node = new DefaultTreeNode(note, targetNode);
            nodeMap.put(note.getNoteRid(), node);
            targetNode.setExpanded(true);
        }
    }
    
    public void addNode(){
        
        if(!Util.nb(this.selectedNode )){
            Notes targetNote = (Notes)this.selectedNode.getData();
            TreeNode targetNode = nodeMap.get(targetNote.getNoteRid());
            // notesの新規作成
            Notes note = new Notes();
            Date now = new Date();
            note.setCreateDt(now);
            note.setUpdateDt(now);
            Notes parentNote = (Notes) targetNode.getData();
            note.setParentRid(parentNote.getNoteRid());
            note.setCategoryRid(parentNote.getCategoryRid());
            note.setExpand(true);
            note.setStyle(parentNote.getStyle());
            this.NotesFacade.create(note);
            //TreeNodeにnodeを新規作成
            TreeNode node = new DefaultTreeNode(note, targetNode);
            nodeMap.put(note.getNoteRid(), node);
        }
    }

    public void deleteNode(){
        if(!Util.nb(this.selectedNode)){
            Notes targetNote = (Notes)this.selectedNode.getData();
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
                // 不整合データでtargetNote.getParentRid()があってもnodeMapからgetできない場合がある
                if(!Util.nb(parentNode)){
                    parentNode.getChildren().remove(targetNode);
                }else{
                    nodeMap.remove(targetNote.getNoteRid());
                }
            }else{
                nodeMap.remove(targetNote.getNoteRid());
            }
            this.NotesFacade.remove(targetNote, true);
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
                // 不整合データでtargetNote.getParentRid()があってもnodeMapからgetできない場合がある
                if(!Util.nb(parentNode)){
                    parentNode.getChildren().remove(targetNode);
                }else{
                    nodeMap.remove(targetNote.getNoteRid());
                }
            }else{
                nodeMap.remove(targetNote.getNoteRid());
            }
            this.NotesFacade.remove(targetNote, true);
//            PrimeFaces.current().executeScript("PF('treeWidget').update();");
        }
    }
    
    public void deleteNodeMap(Integer targetRid){
        nodeMap.remove(targetRid);
    }
    
    public void expandTreeNode(Notes targetNote){
        TreeNode targetNode = nodeMap.get(targetNote.getNoteRid());
        targetNode.setExpanded(true);
        targetNote.setExpand(true);
        updateNode(targetNote);
    }
    
    public void collapseTreeNode(Notes targetNote){
        TreeNode targetNode = nodeMap.get(targetNote.getNoteRid());
        targetNode.setExpanded(false);
        targetNote.setExpand(false);
        updateNode(targetNote);
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
    
    public boolean isDelMode() {
        return delMode;
    }

    public void setDelMode(boolean delMode) {
        this.delMode = delMode;
    }
    
    public boolean isExpendMode() {
        return expendMode;
    }

    public void setExpendMode(boolean expendMode) {
        this.expendMode = expendMode;
    }
    
    public Map<Integer, Object> getCategoryMap() {
        return categoryMap;
    }

    public void setCategoryMap(Map<Integer, Object> categoryMap) {
        this.categoryMap = categoryMap;
    }
    
    public Notes getEditTargetNote() {
        return editTargetNote;
    }

    public void setEditTargetNote(Notes editTargetNote) {
        this.editTargetNote = editTargetNote;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }
    //</editor-fold>
}
