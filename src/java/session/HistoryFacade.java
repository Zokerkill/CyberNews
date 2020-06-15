/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.History;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Melnikov
 */
@Stateless
public class HistoryFacade extends AbstractFacade<History> {

    @PersistenceContext(unitName = "jptvr17newsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HistoryFacade() {
        super(History.class);
    }

    public List<History> findGivenBooks() {
        try {
            List<History> listHistories = 
                 em.createQuery("SELECT h FROM History h WHERE h.dateEnd = NULL")
                .getResultList();
            return listHistories;
        } catch (Exception e) {
            return null;
        }
        
    }
    public List<History> findByRange(Date fromDate, Date toDate) {
        try {
            return em.createQuery("SELECT h FROM History h WHERE h.dateBegin > :fromDate AND h.dateBegin < :toDate")
                    .setParameter("fromDate", fromDate)
                    .setParameter("toDate", toDate)
                    .getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
}
