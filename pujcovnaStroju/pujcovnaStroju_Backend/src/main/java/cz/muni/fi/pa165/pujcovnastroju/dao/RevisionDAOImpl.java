package cz.muni.fi.pa165.pujcovnastroju.dao;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import cz.muni.fi.pa165.pujcovnastroju.entity.Machine;
import cz.muni.fi.pa165.pujcovnastroju.entity.Revision;
import cz.muni.fi.pa165.pujcovnastroju.entity.SystemUser;

/**
 * 
 * @author Matej Fucek
 */
@Repository
public class RevisionDAOImpl implements RevisionDAO {

	@PersistenceContext
	private EntityManager em;

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}
	
	public RevisionDAOImpl() {
		
	}

	public RevisionDAOImpl(EntityManager em) throws IllegalArgumentException {
		if (em == null) {
			throw new IllegalArgumentException("em is null");
		} else {
			this.em = em;
		}
	}

	public Revision create(Revision revision) throws IllegalArgumentException {
		if (revision == null) {
			throw new IllegalArgumentException("revision is null");
		}
		Machine machine = revision.getMachine();
		machine = em.merge(machine);
		List<Revision> machineRevisions = machine.getRevisions();
		if (machineRevisions == null) {
			machineRevisions = new ArrayList<>();
		}
		machineRevisions.add(revision);
		revision.setMachine(machine);
		
		SystemUser user = revision.getSystemUser();
		user = em.merge(user);
		List<Revision> userRevisions = user.getRevisions();
		if (userRevisions == null) {
			userRevisions = new ArrayList<>();
		}
		userRevisions.add(revision);
		revision.setSystemUser(user);
		em.persist(revision);

		return em.find(Revision.class, revision.getRevID());

	}

	public Revision delete(Revision revision) throws IllegalArgumentException {
		if (revision == null) {
			throw new IllegalArgumentException("revision is null");
		}
		Revision revision1 = em.merge(revision);
		em.remove(revision1);
		return em.find(Revision.class, revision.getRevID());
	}

	public Revision update(Revision revision) throws IllegalArgumentException {
		if (revision == null) {
			throw new IllegalArgumentException("revision is null");
		}
		if (revision.getRevID() == null) {
			throw new IllegalArgumentException("revision.id is null");
		}
		Revision revision1 = em.merge(revision);
		em.persist(revision1);
		return em.find(Revision.class, revision1.getRevID());
	}

	public Revision read(Long RevID) {
		if (RevID == null) {
			throw new IllegalArgumentException("unset argument 'RevID'");
		}
		return em.find(Revision.class, RevID);
	}

	public List<Revision> findAllrevisions() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Revision> cq = cb.createQuery(Revision.class);
		Root<Revision> revisionRoot = cq.from(Revision.class);
		cq.select(revisionRoot);

		return em.createQuery(cq).getResultList();
	}

	public List<Revision> findRevisionsByDate(Date dateFrom, Date dateTo) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Revision> cq = cb.createQuery(Revision.class);
		Root<Revision> revisionRoot = cq.from(Revision.class);
		cq.select(revisionRoot);
		if (dateFrom != null) {
			Expression<Date> dateFromExp = revisionRoot.get("revDate");
			cq.where(cb.greaterThanOrEqualTo(dateFromExp, dateFrom));
		}
		if (dateTo != null) {
			Expression<Date> dateToExp = revisionRoot.get("revDate");
			cq.where(cb.lessThanOrEqualTo(dateToExp, dateTo));
		}
		return em.createQuery(cq).getResultList();
	}
        
        public List<Revision> findRevisionsByParams(String comment, Date revDate,
                            Machine machine, SystemUser systemUser){
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Revision> cq = cb.createQuery(Revision.class);
            Root<Revision> revisionRoot = cq.from(Revision.class);
            cq.select(revisionRoot);
            if (comment != null) {
                Expression<String> commentExp = revisionRoot.get("comment");
                cq.where(cb.equal(commentExp, comment));
            }
            if (revDate != null) {
                Expression<Date> revDateExp = revisionRoot.get("revDate");
                cq.where(cb.greaterThanOrEqualTo(revDateExp, revDate));
            }
            if (machine != null) {
                Expression<Machine> machineExp = revisionRoot.get("machine");
                cq.where(cb.equal(machineExp, machine));
            }
            if (systemUser != null) {
                Expression<SystemUser> systemUserExp = revisionRoot.get("systemUser");
                cq.where(cb.equal(systemUserExp, systemUser));
            }
            return em.createQuery(cq).getResultList();
        }
}
