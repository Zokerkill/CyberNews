/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Project;
import entity.Cover;
import entity.CoverProject;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Melnikov
 */
@Stateless
public class CoverProjectFacade extends AbstractFacade<CoverProject> {

    @PersistenceContext(unitName = "jptvr17newsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CoverProjectFacade() {
        super(CoverProject.class);
    }
    
    public Cover findCover(Project project) {
        try {
            CoverProject coverProject = (CoverProject) em.createQuery("SELECT cp FROM CoverProject cp WHERE cp.project = :project")
                    .setParameter("project", project)
                    .getSingleResult();
            return coverProject.getCover();
        } catch (Exception e) {
            return null;
        }
    }

    public CoverProject findCoverProjectByProjectId(Long projectId) {
        try {
            return (CoverProject) em.createQuery("SELECT cp FROM CoverProject cp WHERE cp.project.id = :projectId")
                    .setParameter("projectId", projectId)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
}
