
package com.omoscorplation.webtree.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 *
 * @author user
 */
@Entity
@Table(name = "notes")
public class Notes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "note_rid")
    private Integer noteRid;
    @Column(name = "note_title")
    private String noteTitle;
    @Column(name = "note_text")
    private String noteText;
    @Column(name = "category_rid")
    private Integer categoryRid;
    @Column(name = "create_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDt;
    @Column(name = "update_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDt;
//    @OneToMany(mappedBy = "parentRid")
//    private Collection<Notes> notesCollection;
//    @JoinColumn(name = "parent_rid", referencedColumnName = "note_rid")
    @Column(name = "parent_rid")
    private Integer parentRid;

    public Notes() {
    }

    public Notes(Integer noteRid) {
        this.noteRid = noteRid;
    }

    public Integer getNoteRid() {
        return noteRid;
    }

    public void setNoteRid(Integer noteRid) {
        this.noteRid = noteRid;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public Integer getCategoryRid() {
        return categoryRid;
    }

    public void setCategoryRid(Integer categoryRid) {
        this.categoryRid = categoryRid;
    }

    public Date getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }

    public Date getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(Date updateDt) {
        this.updateDt = updateDt;
    }

//    public Collection<Notes> getNotesCollection() {
//        return notesCollection;
//    }
//
//    public void setNotesCollection(Collection<Notes> notesCollection) {
//        this.notesCollection = notesCollection;
//    }

    public Integer getParentRid() {
        return parentRid;
    }

    public void setParentRid(Integer parentRid) {
        this.parentRid = parentRid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (noteRid != null ? noteRid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Notes)) {
            return false;
        }
        Notes other = (Notes) object;
        if ((this.noteRid == null && other.noteRid != null) || (this.noteRid != null && !this.noteRid.equals(other.noteRid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.omos.applist.entities.Notes[ noteRid=" + noteRid + " ]";
    }
    
}
