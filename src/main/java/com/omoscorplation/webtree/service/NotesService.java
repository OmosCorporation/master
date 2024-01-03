/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.omoscorplation.webtree.service;

import com.omoscorplation.webtree.entities.Notes;
import com.omoscorplation.webtree.facade.NotesFacade;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;

/**
 *
 * @author Takuto Esumi
 */
@Named
@Stateless
@Dependent
public class NotesService extends BaseService{
    
    @Inject
    private NotesFacade notesFacade;
  
    public List<Notes> getAll(){
        return this.notesFacade.findAll();
    }
}
