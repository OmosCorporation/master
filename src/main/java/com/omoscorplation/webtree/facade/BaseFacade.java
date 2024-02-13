/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.omoscorplation.webtree.facade;

//import com.omoscorplation.webtree.facade.entities.ApplistEntityManager;
import com.omoscorplation.webtree.common.Util;
import com.sun.org.slf4j.internal.Logger;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Takuto Esumi
 */
public abstract class BaseFacade<T> implements Serializable{

    private static final long serialVersionUID = 1L;
    private final Class<T> entityClass;
//    @Inject
//    private transient Logger logger;
    @PersistenceContext
    private EntityManager em;

    public BaseFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    
    protected EntityManager getEntityManager() {
        return em;
    }
    
       /**
     * 追加登録処理
     *
     * @param entity 追加対象のエンティティ
     */
    public void create(T entity) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(entity);
        if (constraintViolations.size() > 0) {
            Iterator<ConstraintViolation<T>> iterator = constraintViolations.iterator();
            while (iterator.hasNext()) {
                ConstraintViolation<T> cv = iterator.next();
//                logger.debug(cv.getRootBeanClass().getSimpleName() + "." + cv.getPropertyPath() + " " + cv.getMessage());
            }
        } else {
            getEntityManager().persist(entity);
            getEntityManager().flush();
        }
    }
    
    /**
     * 編集登録処理
     *
     * @param entity 編集対象のエンティティ
     */
    public void edit(T entity) {
        getEntityManager().detach(entity);
        getEntityManager().merge(entity);
        getEntityManager().flush();
    }
    
    /**
     * 削除処理
     *
     * @param entity
     * @param deleteMode
     */
    public void remove(T entity, boolean isPhysicalDelete) {
        if (isPhysicalDelete) {
            getEntityManager().remove(getEntityManager().merge(entity));
            getEntityManager().flush();
        } else {
//            Date now = new Date(); 
//            ClassStructure.set(entity, "deleteflg", 1);
//            ClassStructure.set(entity, "deletedt", now);
//            edit(entity);
        }
    }
    
    /**
     * find処理
     *
     * @param id entityClassの主キー
     * @return entityClas
     */
    public T find(Object id) {
        return getEntityManager().find(this.entityClass, id);
    }
    
    /**
     * find処理
     *
     * @param id entityClassの主キー
     * @return entityClas
     */
    public T find(Class<T> entityClass, Object id) {
        return getEntityManager().find(entityClass, id);
    }
    
    public void delete(Entity entity){
        em.remove(em.merge(entity));
    }

    
    /**
     * 全検索処理
     *
     * @return entityClas
     */
    public List<T> findAll() {
        jakarta.persistence.criteria.CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        jakarta.persistence.criteria.CriteriaQuery<T> cq = createCriteriaQuery(cb, this.entityClass);
        Root<T> r = cq.from(this.entityClass);
        cq.select(r);
        return getEntityManager().createQuery(cq).getResultList();
    }
    
    /**
     * 全検索処理
     *
     * @param entityKey
     * @return entityClas
     */
    public List<T> findAllOrderBy(String entityKey) {
        jakarta.persistence.criteria.CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        jakarta.persistence.criteria.CriteriaQuery<T> cq = createCriteriaQuery(cb, this.entityClass);
        Root<T> r = cq.from(this.entityClass);
        cq.select(r);
        if(!Util.nb(entityKey)){
            cq.orderBy(cb.asc(r.get(entityKey)));
        }
        return getEntityManager().createQuery(cq).getResultList();
    }
    
    /**
     * CriteriaQueryの生成
     * @param cb
     * @param entityClass
     * @return 
     */
    protected CriteriaQuery<T> createCriteriaQuery(CriteriaBuilder cb, Class<T> entityClass){
        return cb.createQuery(entityClass);
    }
}
