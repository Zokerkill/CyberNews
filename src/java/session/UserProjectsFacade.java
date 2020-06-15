/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.User;
import entity.UserProjects;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author user
 */
@Stateless
public class UserProjectsFacade extends AbstractFacade<UserProjects> {

    @PersistenceContext(unitName = "jptvr17newsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserProjectsFacade() {
        super(UserProjects.class);
    }
    public List<UserProjects> selectUserProjects(User regUser) {
       try {
            return em.createQuery("SELECT up FROM UserProjects up WHERE up.user = :user AND up.project.isDeleted=false")
                    .setParameter("user", regUser)
                    .getResultList();
        } catch (Exception e) {
            return null;
        } 
    }
    
}
