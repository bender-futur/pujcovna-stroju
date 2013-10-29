/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.pujcovnastroju.service;

import java.sql.Date;
import java.util.List;

import cz.muni.fi.pa165.pujcovnastroju.entity.Revision;

/**
 *
 * @author Matej
 */
public interface RevisionService {
    
     /**
     * Creates a new Revision
     * 
     * @param revID to be created
     * @return created Revision
     * @throws IllegalArgumentException if the Revision is null
     */ 
    public Revision createBizRevision (Revision revID);

    /**
     * Deletes an Revision
     * 
     * @param revID to be deleted
     * @return deleted RevID
     * @throws IllegalArgumentException if the RevID is null
     */
    public Revision deleteBizRevision (Revision revID);   

    /**
     * Updates given revID
     * 
     * @param revID to be updated
     * @return updated RevID
     * @throws IllegalArgumentException if the RevID is null
     */
    public Revision updateBizRevision (Revision revID);
    
    /**
     * Returns all Revisions
     * 
     * @return list of all Revision 
     */
    public List<Revision> findAllrevisionsBizRevision();

    /**
     * Reads Revision with given revID
     * 
     * @param revID of the Revision
     * @return Revision with the given revID
     * @throws IllegalArgumentException if the revID is null
     */
    public Revision readBizRevision (Long revID);
 
    /**
     * Retrieves all revisions that were made in given time interval
     * 
     * @param dateFrom date from which the revisions should be retrieved
     * @param dateTo date to which the revisions should be retrieved
     * @return list of revisions that suit the filter
     */
    public List<Revision> findRevisionsByDateBizRevision(Date dateFrom, Date dateTo);
}
