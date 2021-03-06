/**
 * 
 */
package org.openmrs.module.household.dao.hibernate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.jfree.util.Log;
import org.openmrs.Concept;
import org.openmrs.ConceptName;
import org.openmrs.Form;
import org.openmrs.Person;
import org.openmrs.User;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.household.dao.HouseholdDAO;
import org.openmrs.module.household.exception.HouseholdModuleException;
import org.openmrs.module.household.model.Household;
import org.openmrs.module.household.model.HouseholdAttribValue;
import org.openmrs.module.household.model.HouseholdAttribute;
import org.openmrs.module.household.model.HouseholdDefinition;
import org.openmrs.module.household.model.HouseholdDefinitionParent;
import org.openmrs.module.household.model.HouseholdEncounter;
import org.openmrs.module.household.model.HouseholdEncounterType;
import org.openmrs.module.household.model.HouseholdLocation;
import org.openmrs.module.household.model.HouseholdLocationEntry;
import org.openmrs.module.household.model.HouseholdLocationLevel;
import org.openmrs.module.household.model.HouseholdMembership;
import org.openmrs.module.household.model.HouseholdObs;


/**
 * @author Ampath Developers
 *
 */
public class HibernateHouseholdDAO implements HouseholdDAO {
	
	private SessionFactory sessionFactory;
	
	
    /**
     * @return the sessionFactory
     */
    public SessionFactory getSessionFactory() {
    	return sessionFactory;
    }

	
    /**
     * @param sessionFactory the sessionFactory to set
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
    	this.sessionFactory = sessionFactory;
    }


	/* (non-Javadoc)
	 * @see org.openmrs.module.household.dao.HouseholdDAO#saveHouseholdDefinition(
	 * org.openmrs.module.household.model.HouseholdDefinition)
	 */
    
	public HouseholdDefinition saveHouseholdDefinition(
			HouseholdDefinition householdDefinition) {
		sessionFactory.getCurrentSession().saveOrUpdate(householdDefinition);
		return householdDefinition;
	}


	/* (non-Javadoc)
	 * @see org.openmrs.module.household.dao.HouseholdDAO#getHouseholdDefinition(java.lang.Integer)
	 */
	
	public HouseholdDefinition getHouseholdDefinition(Integer id) {
		return (HouseholdDefinition) sessionFactory.getCurrentSession().get(
				HouseholdDefinition.class, id);
	}
	
	/* (non-Javadoc)
	 * @see org.openmrs.module.household.dao.HouseholdDAO#getHouseholdDefinition(HouseholdDefinition)
	 */
	
	public HouseholdDefinition getHouseholdDefinition(HouseholdDefinition hd) {
		return (HouseholdDefinition) sessionFactory.getCurrentSession().get(
				HouseholdDefinition.class, hd.getId());
	}
	
	/* (non-Javadoc)
	 * @see org.openmrs.module.household.dao.HouseholdDAO#getHouseholdDefinition(java.lang.Integer)
	 */
	
	public HouseholdDefinition getHouseholdDefinition(String def) {
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HouseholdDefinition.class).add(
		    Expression.eq("householdDefinitionsCode", def));
		
		@SuppressWarnings("unchecked")
        List<HouseholdDefinition> defs = criteria.list();
		if (null == defs || defs.isEmpty()) {
			return null;
		}
		return defs.get(0);
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.dao.HouseholdDAO#getHouseholdDefinition(java.lang.Integer)
	 */
	
	public HouseholdDefinition getHouseholdDefinitionByUuid (String strUuid){
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HouseholdDefinition.class).add(
		    Expression.eq("uuid", strUuid));
		
		@SuppressWarnings("unchecked")
		List<HouseholdDefinition> hdef = criteria.list();
		if (null == hdef || hdef.isEmpty()) {
			return null;
		}
		return hdef.get(0);
	}
	public HouseholdDefinition getHouseholdDefinitionByCode(String code){
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HouseholdDefinition.class).add(
		    Expression.eq("household_definition_code", code));
		
		@SuppressWarnings("unchecked")
		List<HouseholdDefinition> hdef = criteria.list();
		if (null == hdef || hdef.isEmpty()) {
			return null;
		}
		return hdef.get(0);
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.dao.HouseholdDAO#getAllHouseholdDefinitions()
	 */
	
	@SuppressWarnings("unchecked")
	public List<HouseholdDefinition> getAllHouseholdDefinitions() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				HouseholdDefinition.class);
		return criteria.list();
	}
	
	public boolean purgeHouseholdDefinition(HouseholdDefinition hd){
		try {
			sessionFactory.getCurrentSession().delete(hd);
			return true;
        }
        catch (Exception e) {
        	return false;
        }
	}


	/* (non-Javadoc)
	 * @see org.openmrs.module.household.dao.HouseholdDAO#saveHouseholdGroup(
	 * org.openmrs.module.household.model.HouseholdGroups)
	 */
	
	public Household saveHouseholdGroup(Household householdGroups) {
		sessionFactory.getCurrentSession().saveOrUpdate(householdGroups);
		return householdGroups;
	}


	/* (non-Javadoc)
	 * @see org.openmrs.module.household.dao.HouseholdDAO#getHouseholdGroup(java.lang.Integer)
	 */
	
	public Household getHouseholdGroup(Integer id) {
		return (Household) sessionFactory.getCurrentSession().get(
				Household.class, id);
	}
	
	/* (non-Javadoc)
	 * @see org.openmrs.module.household.dao.HouseholdDAO#getHouseholdGroupByUuid(java.lang.Integer)
	 */
	public Household getHouseholdGroupByIdentifier(String householdIdentifier){
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Household.class).add(
			    Expression.eq("householdIdentifier", householdIdentifier));
			
		@SuppressWarnings("unchecked")
		List<Household> hhold = criteria.list();
		if (null == hhold || hhold.isEmpty()) {
			return null;
		}
		return hhold.get(0);
	}


	/* (non-Javadoc)
	 * @see org.openmrs.module.household.dao.HouseholdDAO#getAllHouseholdGroups()
	 */
	
	@SuppressWarnings("unchecked")
	public List<Household> getAllHouseholdGroups() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				Household.class);
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Household> getAllHouseholdsByDefinitionUuid(String definitionUuid){
		HouseholdDefinition hd = new HouseholdDefinition();
		hd.setUuid(definitionUuid);
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Household.class).add(
			    Expression.eq("householdDef", hd));
		return criteria.list();
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.dao.HouseholdDAO#saveHouseholdMembership(
	 * org.openmrs.module.household.model.HouseholdMembership)
	 */
	
	public HouseholdMembership saveHouseholdMembership(
			HouseholdMembership householdMemberships) {
		sessionFactory.getCurrentSession().saveOrUpdate(householdMemberships);
		return householdMemberships;
	}


	/* (non-Javadoc)
	 * @see org.openmrs.module.household.dao.HouseholdDAO#getHouseholdMembership(java.lang.Integer)
	 */
	
	public HouseholdMembership getHouseholdMembership(Integer id) {
		return (HouseholdMembership) sessionFactory.getCurrentSession().get(
				HouseholdMembership.class, id);
	}
	
        /* (non-Javadoc)
	 * Returns all the householdmemberships whether voided, retired, or active
         * Helpful for History
	 */
	public List<HouseholdMembership> getHouseholdMembershipByHousehold(Household household) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HouseholdMembership.class).add(
			    Expression.eq("householdMembershipGroups", household))
                        .addOrder(Order.desc("id"));
		return criteria.list();
	}


	/* (non-Javadoc)
	 * @see org.openmrs.module.household.dao.HouseholdDAO#getAllHouseholdMemberships()
	 */
	
	public List<HouseholdMembership> getAllHouseholdMemberships() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HouseholdMembership.class)
		.add(Expression.eq("voided", false))
                .add(Expression.eq("endDate", null));
		
		return criteria.list();
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.dao.HouseholdDAO#getAllHouseholdMembershipsByID(int id)
	 */
	public List<HouseholdMembership> getAllHouseholdMembershipsByGroup(Household grp) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HouseholdMembership.class)
                    .add(Expression.eq("householdMembershipGroups",grp))
                    .add(Expression.eq("voided", false));
                List<HouseholdMembership> toret = criteria.list();
                List<HouseholdMembership> tor = new ArrayList<HouseholdMembership>();
            for (HouseholdMembership householdMembership : toret) {
                if((householdMembership.getRetireReason() == null) && (householdMembership.getQuasi() == false)){
                    tor.add(householdMembership);
                }
            }
            return tor;
	}
	
	/* (non-Javadoc)
	 * @see org.openmrs.module.household.dao.HouseholdDAO#getAllHouseholdMembershipsByPerson(Person p)
	 */
	@SuppressWarnings("unchecked")
	
	public List<HouseholdMembership> getAllHouseholdMembershipsByPerson(Person p) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				HouseholdMembership.class).add(Restrictions.eq("householdMembershipMember",p));
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	
	public List<HouseholdMembership> getHouseholdMembershipByGrpByPsn(Person p,Household grp) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HouseholdMembership.class)
				.add(Restrictions.eq("householdMembershipMember",p))
				.add(Restrictions.eq("householdMembershipGroups", grp));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<HouseholdMembership> getAllVoidedHouseholdMembershipsByGroup(Household grp){
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HouseholdMembership.class)
		
		.add(Expression.eq("householdMembershipGroups",grp))
		.add(Expression.eq("voided", true));

		return criteria.list();
	}
	
	// finding the index of a given household==================================================
	@SuppressWarnings("unchecked")
	public List<HouseholdMembership> getIndexPerson(Integer id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HouseholdMembership.class)
		.add(Expression.eq("id",id))
		.add(Expression.eq("householdMembershipHeadship", true));
		
		return criteria.list();
	}
	//get the list of persons
	@SuppressWarnings("unchecked")
	public List<HouseholdMembership> getAllNonVoidedHouseholdMembershipsByGroupNotIndex(Household grp){
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HouseholdMembership.class)
		.add(Expression.eq("householdMembershipGroups",grp))
		.add(Expression.eq("voided", false))
		.add(Expression.eq("householdMembershipHeadship", false));
		return criteria.list();
	}
	@SuppressWarnings("unchecked")
	public List<HouseholdMembership> getHouseholdIndexByGroup(Household grp){
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HouseholdMembership.class)
		.add(Expression.eq("householdMembershipGroups",grp))
		.add(Expression.eq("voided", false))
		.add(Expression.eq("householdMembershipHeadship", true));
		return criteria.list();
	}

//========================================================================
	
	public HouseholdAttribute saveHouseholdAttribute(
			HouseholdAttribute householdAttribute) {
		sessionFactory.getCurrentSession().saveOrUpdate(householdAttribute);
		return householdAttribute;
	}


	
	public HouseholdAttribute getHouseholdAttribute(Integer id) {
		return (HouseholdAttribute) sessionFactory.getCurrentSession().get(
				HouseholdAttribute.class, id);
	}


	@SuppressWarnings("unchecked")
	
	public List<HouseholdAttribute> getAllHouseholdAttribute() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				HouseholdAttribute.class);
		return criteria.list();
	}


	
	public HouseholdAttribValue saveHouseholdAttribValue(
			HouseholdAttribValue householdAttribValue) {
		sessionFactory.getCurrentSession().saveOrUpdate(householdAttribValue);
		return householdAttribValue;
	}


	
	public HouseholdAttribValue getHouseholdAttribValue(Integer id) {
		return (HouseholdAttribValue) sessionFactory.getCurrentSession().get(
				HouseholdAttribValue.class, id);
	}


	@SuppressWarnings("unchecked")
	
	public List<HouseholdAttribValue> getAllHouseholdAttribValue() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				HouseholdAttribValue.class);
		return criteria.list();
	}


	
/////////////////////////**********************************************************//////////////////////////
	
	
	
	public HouseholdLocation saveHouseholdLocation(
			HouseholdLocation householdLocation) {
		sessionFactory.getCurrentSession().saveOrUpdate(householdLocation);
		return householdLocation;
	}


	
	public HouseholdLocation getHouseholdLocation(Integer id) {
		return (HouseholdLocation) sessionFactory.getCurrentSession().get(
				HouseholdLocation.class, id);
	}


	@SuppressWarnings("unchecked")
	
	public List<HouseholdLocation> getAllHouseholdLocation() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				HouseholdLocation.class);
		return criteria.list();
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.dao.HouseholdDAO#getHouseholdLocation(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public HouseholdLocation getHouseholdLocation(String name) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HouseholdLocation.class).add(
			    Expression.eq("name", name));
			
			List<HouseholdLocation> locations = criteria.list();
			if (null == locations || locations.isEmpty()) {
				return null;
			}
			return locations.get(0);
	}


	/* (non-Javadoc)
	 * @see org.openmrs.module.household.dao.HouseholdDAO#getAllHouseholdLocations(boolean)
	 */
	@SuppressWarnings("unchecked")
	public List<HouseholdLocation> getAllHouseholdLocations(
			boolean includeRetired) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HouseholdLocation.class);
		if (!includeRetired) {
			criteria.add(Expression.eq("retired", false));
		}
		criteria.addOrder(Order.asc("name"));
		return criteria.list();
	}


	/* (non-Javadoc)
	 * @see org.openmrs.module.household.dao.HouseholdDAO#getHouseholdLocations(java.lang.String, boolean, java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	public List<HouseholdLocation> getHouseholdLocations(String nameFragment,
			boolean includeRetired, Integer start, Integer length)
			throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HouseholdLocation.class);
		if (!includeRetired)
			criteria.add(Expression.eq("retired", false));
		
		if (StringUtils.isNotBlank(nameFragment))
			criteria.add(Expression.ilike("name", nameFragment, MatchMode.START));
		
		criteria.addOrder(Order.asc("name"));
		if (start != null)
			criteria.setFirstResult(start);
		if (length != null && length > 0)
			criteria.setMaxResults(length);
		
		return criteria.list();
	}


	/* (non-Javadoc)
	 * @see org.openmrs.module.household.dao.HouseholdDAO#deleteHouseholdLocation(org.openmrs.module.household.model.HouseholdLocation)
	 */
	public void deleteHouseholdLocation(HouseholdLocation location) {
		sessionFactory.getCurrentSession().delete(location);
	}


	/* (non-Javadoc)
	 * @see org.openmrs.module.household.dao.HouseholdDAO#getHouseholdLocationByUuid(java.lang.String)
	 */
	public HouseholdLocation getHouseholdLocationByUuid(String uuid) {
		return (HouseholdLocation) sessionFactory.getCurrentSession().createQuery("from Household_Location l where l.uuid = :uuid").setString(
			    "uuid", uuid).uniqueResult();
	}


	/* (non-Javadoc)
	 * @see org.openmrs.module.household.dao.HouseholdDAO#getCountOfHouseholdLocations(java.lang.String, java.lang.Boolean)
	 */
	public Integer getCountOfHouseholdLocations(String nameFragment,
			Boolean includeRetired) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HouseholdLocation.class);
		if (!includeRetired)
			criteria.add(Expression.eq("retired", false));
		
		if (StringUtils.isNotBlank(nameFragment))
			criteria.add(Expression.ilike("name", nameFragment, MatchMode.START));
		
		criteria.setProjection(Projections.rowCount());
		
		return (Integer) criteria.uniqueResult();
	}
	
	/**
	 * Get all DISTINCT locations
	 * 
	 * @param includeRetired boolean - include retired locations as well?
	 * @return <code>List<HouseholdLocation></code> object of all <code>HouseholdLocation</code>s, possibly including
	 *         retired locations
	 */
	@SuppressWarnings("unchecked")
	public List<HouseholdLocation> getAllHouseholdLocationsByLocation(boolean includeRetired){
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HouseholdLocation.class)
			.setProjection(Projections.distinct(Projections.property("cityLocation")));
		if (!includeRetired) {
			criteria.add(Expression.eq("retired", false));
		}
		criteria.addOrder(Order.asc("cityLocation"));
		return criteria.list();
	}
	
	/**
	 * Get all DISTINCT sub locations
	 * 
	 * @param includeRetired boolean - include retired locations as well?
	 * @return <code>List<HouseholdLocation></code> object of all <code>HouseholdLocation</code>s, possibly including
	 *         retired locations
	 */
	@SuppressWarnings("unchecked")
	public List<HouseholdLocation> getAllHouseholdLocationsBySubLocation(String location, boolean includeRetired){
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HouseholdLocation.class);
		if (!includeRetired)
			criteria.add(Expression.eq("retired", false));
		
		if (StringUtils.isNotBlank(location))
			criteria.add(Expression.ilike("cityLocation", location, MatchMode.START));
		
		criteria.setProjection(Projections.distinct(Projections.property("citySubLocation")));
		
		criteria.addOrder(Order.asc("citySubLocation"));
		return criteria.list();
	}
	
	/**
	 * Get all DISTINCT villages
	 * 
	 * @param includeRetired boolean - include retired locations as well?
	 * @return <code>List<HouseholdLocation></code> object of all <code>HouseholdLocation</code>s, possibly including
	 *         retired locations
	 */
	@SuppressWarnings("unchecked")
	public List<HouseholdLocation> getAllHouseholdLocationsByVillage(String subLocation, String location, boolean includeRetired){
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HouseholdLocation.class);
		if (!includeRetired)
			criteria.add(Expression.eq("retired", false));
		
		if (StringUtils.isNotBlank(location))
			criteria.add(Expression.ilike("cityLocation", location, MatchMode.START));
		
		if (StringUtils.isNotBlank(location))
			criteria.add(Expression.ilike("citySubLocation", subLocation, MatchMode.START));
		
		criteria.setProjection(Projections.distinct(Projections.property("cityVillage")));
		
		criteria.addOrder(Order.asc("cityVillage"));
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public HouseholdLocation getAllHouseholdLocationsByLocSubVil(String village, String subLocation, String location, boolean includeRetired){
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HouseholdLocation.class);
		if (!includeRetired)
			criteria.add(Expression.eq("retired", false));
		
		if (StringUtils.isNotBlank(location))
			criteria.add(Expression.ilike("cityLocation", location, MatchMode.START));
		
		if (StringUtils.isNotBlank(location))
			criteria.add(Expression.ilike("citySubLocation", subLocation, MatchMode.START));
		
		if (StringUtils.isNotBlank(location))
			criteria.add(Expression.ilike("cityVillage", village, MatchMode.START));
		
		List<HouseholdLocation> hl = criteria.list();
		HouseholdLocation strHl = null;
		for( int i = 0; i<hl.size();i++){
			strHl = hl.get(i);
		}
		return strHl;
	}

	@SuppressWarnings("unchecked")
	public int getHouseholdLocationEntryCount() {
		int x = 0;
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(HouseholdLocationEntry.class);
		List<Integer> rows = c.setProjection((Projections.rowCount())).list();
		if (rows.size() > 0) {
			x = rows.get(0).intValue();
		}
		return x;
	}
	
	@SuppressWarnings("unchecked")
	public int getHouseholdLocationEntryCountByLevel(HouseholdLocationLevel level) {
		int x = 0;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HouseholdLocationEntry.class);
		criteria.createCriteria("level").add(Restrictions.eq("levelId", level.getId()));
		List<Integer> rows = criteria.setProjection((Projections.rowCount())).list();
		if (rows.size() > 0) {
			x = rows.get(0).intValue();
		}
		return x;	
	}
	
	public HouseholdLocationEntry getHouseholdLocationEntry(int householdLocationEntryId) {
		Session session = sessionFactory.getCurrentSession();
		HouseholdLocationEntry ah = (HouseholdLocationEntry) session.load(HouseholdLocationEntry.class, householdLocationEntryId);
		return ah;
	}
	
	@SuppressWarnings("unchecked")
	public HouseholdLocationEntry getHouseholdLocationEntryByUserGenId(String userGeneratedId) {
		HouseholdLocationEntry ah = null;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HouseholdLocationEntry.class);
		
		List<HouseholdLocationEntry> list = criteria.add(Restrictions.eq("userGeneratedId", userGeneratedId)).list();
		if (list != null && list.size() > 0) {
			ah = list.get(0);
		}
		return ah;
	}
	
	@SuppressWarnings("unchecked")
    public List<HouseholdLocationEntry> getHouseholdLocationEntriesByLevel(HouseholdLocationLevel householdLocationLevel) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HouseholdLocationEntry.class);
		criteria.createCriteria("level").add(Restrictions.eq("levelId", householdLocationLevel.getId()));
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
    public List<HouseholdLocationEntry> getHouseholdLocationEntriesByLevelAndName(HouseholdLocationLevel householdLocationLevel, String name) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HouseholdLocationEntry.class);
		criteria.createCriteria("level").add(Restrictions.eq("levelId", householdLocationLevel.getId()));
		criteria.add(Restrictions.eq("name", name).ignoreCase());
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<HouseholdLocationEntry> getChildHouseholdLocationEntries(HouseholdLocationEntry entry) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HouseholdLocationEntry.class);
		List<HouseholdLocationEntry> list = criteria.createCriteria("parent").add(
		    Restrictions.eq("householdLocationEntryId", entry.getId())).list();
		return list;
	}
	
	public HouseholdLocationEntry getChildHouseholdLocationEntryByName(HouseholdLocationEntry entry, String childName) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HouseholdLocationEntry.class);
		criteria.createCriteria("parent").add(Restrictions.eq("householdLocationEntryId", entry.getId()));
		criteria.add(Restrictions.eq("name", childName).ignoreCase());  // do a case-insensitive match
		// this will throw an exception if we don't get a unique result--entries should always be unique on parent and name
		return (HouseholdLocationEntry) criteria.uniqueResult();    
	}
	
	public void saveHouseholdLocationEntry(HouseholdLocationEntry ah) {
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(ah);
		}
		catch (Throwable t) {
			throw new DAOException(t);
		}
	}
	
	public void deleteAllHouseholdLocationEntries() {
		Session session = sessionFactory.getCurrentSession();
		session.createSQLQuery("SET foreign_key_checks = 0").executeUpdate();
		session.createSQLQuery("DELETE FROM household_location_entry").executeUpdate();
		session.createSQLQuery("SET foreign_key_checks = 1").executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	public List<HouseholdLocationLevel> getHouseholdLocationLevels() {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HouseholdLocationLevel.class);
		return criteria.list();
	}
	
	public HouseholdLocationLevel getTopHouseholdLocationLevel() {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HouseholdLocationLevel.class);
		criteria.add(Restrictions.isNull("parent"));
		
		HouseholdLocationLevel topLevel = null;
		
		try {
			topLevel = (HouseholdLocationLevel) criteria.uniqueResult();
		}
		catch (Exception e) {
			throw new HouseholdModuleException("Unable to fetch top level household location type", e);
		}
		
		return topLevel;
	}
	
	public HouseholdLocationLevel getHouseholdLocationLevel(int levelId) {
		Session session = sessionFactory.getCurrentSession();
		HouseholdLocationLevel type = (HouseholdLocationLevel) session.load(HouseholdLocationLevel.class, levelId);
		return type;
	}
	
    public HouseholdLocationLevel getHouseholdLocationLevelByParent(HouseholdLocationLevel parent) {
    	Session session = sessionFactory.getCurrentSession();
    	Criteria criteria = session.createCriteria(HouseholdLocationLevel.class);
    	criteria.add(Restrictions.eq("parent", parent));
    	
    	HouseholdLocationLevel child = null;
		
		try {
			child = (HouseholdLocationLevel) criteria.uniqueResult();
		}
		catch (Exception e) {
			throw new HouseholdModuleException("Unable to fetch child household location type", e);
		}
		
		return child;
    }
	
	public void saveHouseholdLocationLevel(HouseholdLocationLevel level) {
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(level);
		}
		catch (Throwable t) {
			throw new DAOException(t);
		}
	}
	

    public void deleteHouseholdLocationLevel(HouseholdLocationLevel level) {
    	try {
			sessionFactory.getCurrentSession().delete(level);
		}
		catch (Throwable t) {
			throw new DAOException(t);
		}
    }
	
	
/////////////////////////**********************************************************//////////////////////////
	
	
	
	

	public HouseholdEncounter saveHouseholdEncounter(
			HouseholdEncounter householdEncounter) {
		sessionFactory.getCurrentSession().saveOrUpdate(householdEncounter);
		return householdEncounter;
	}


	
	public HouseholdEncounter getHouseholdEncounter(Integer id) {
		return (HouseholdEncounter) sessionFactory.getCurrentSession().get(
				HouseholdEncounter.class, id);
	}


	@SuppressWarnings("unchecked")
	
	public List<HouseholdEncounter> getEncountersByHouseholdUuid(String householdUuid) throws DAOException {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(HouseholdEncounter.class).createAlias("householdGroupId", "h").add(
			    Expression.eq("h.uuid", householdUuid)).add(Expression.eq("voided", false)).addOrder(
			    Order.desc("householdEncounterId"));
			
			return crit.list();
	}


	
	public HouseholdEncounter getHouseholdEncounterByUUID(String uuid) {
		return (HouseholdEncounter) sessionFactory.getCurrentSession().createQuery("from HouseholdEncounter e where e.uuid = " +
				":uuid order by e.householdEncounterDatetime").setString("uuid", uuid).uniqueResult();
	}


	@SuppressWarnings("unchecked")
	
	public List<HouseholdEncounter> getAllHouseholdEncountersByHouseholdId(
			Integer id) {
		Criteria criteria = (Criteria) sessionFactory.getCurrentSession().get(
				HouseholdEncounter.class, id);
		return criteria.list();
	}


	@SuppressWarnings("unchecked")
	
	public List<HouseholdEncounter> getAllHouseholdEncountersByHouseholdIdentifiers(
			String identifier) {
		//TODO Correct this
		Criteria criteria = (Criteria) sessionFactory.getCurrentSession().get(
				HouseholdEncounter.class, identifier);
		return criteria.list();
	}


	@SuppressWarnings("unchecked")
	
	public List<HouseholdEncounter> getHouseholdEncounters(Household household,
			HouseholdLocation loc, Date fromDate, Date toDate,
			Collection<Form> enteredViaForms,
			Collection<HouseholdEncounterType> householdEncounterTypes,
			Collection<User> providers, boolean includeVoided) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(HouseholdEncounter.class);
		if (household != null && household.getId() != null) {
			crit.add(Expression.eq("householdGroupId", household));
		}
		if (loc != null && loc.getHouseholdLocationId() != null) {
			crit.add(Expression.eq("householdLocation", loc));
		}
		if (fromDate != null) {
			crit.add(Expression.ge("householdEncounterDatetime", fromDate));
		}
		if (toDate != null) {
			crit.add(Expression.le("householdEncounterDatetime", toDate));
		}
		if (enteredViaForms != null && enteredViaForms.size() > 0) {
			crit.add(Expression.in("form", enteredViaForms));
		}
		if (householdEncounterTypes != null && householdEncounterTypes.size() > 0) {
			crit.add(Expression.in("householdEncounterTypes", householdEncounterTypes));
		}
		if (providers != null && providers.size() > 0) {
			crit.add(Expression.in("provider", providers));
		}
		if (!includeVoided) {
			crit.add(Expression.eq("voided", false));
		}
		crit.addOrder(Order.asc("householdEncounterDatetime"));
		return crit.list();
	}


	
	public HouseholdEncounterType saveHouseholdEncounterType(
			HouseholdEncounterType encounterType) {
		sessionFactory.getCurrentSession().saveOrUpdate(encounterType);
		return encounterType;
	}


	
	public HouseholdEncounterType getHouseholdEncounterType(
			Integer encounterTypeId) throws APIException {
		return (HouseholdEncounterType) sessionFactory.getCurrentSession().get(
				HouseholdEncounterType.class, encounterTypeId);
	}


	
	public HouseholdEncounterType getHouseholdEncounterTypeByUuid(String uuid)
			throws APIException {
		return (HouseholdEncounterType) sessionFactory.getCurrentSession().createQuery("from Household_Encounter_Type et where et.uuid = :uuid")
        .setString("uuid", uuid).uniqueResult();
	}
	
	public HouseholdEncounterType getHouseholdEncounterTypeByName(String name) throws APIException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HouseholdEncounterType.class).add(
		    Expression.eq("name", name));
		
		@SuppressWarnings("unchecked")
        List<HouseholdEncounterType> et = criteria.list();
		if (null == et || et.isEmpty()) {
			return null;
		}
		return et.get(0);
	}



	@SuppressWarnings("unchecked")
	
	public List<HouseholdEncounterType> getAllHouseholdEncounterTypes()
			throws APIException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				HouseholdEncounterType.class);
		return criteria.list();
	}


	@SuppressWarnings("unchecked")
	
	public List<HouseholdEncounterType> getAllHouseholdEncounterTypes(
			boolean includeRetired) throws APIException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HouseholdEncounterType.class);
		
		criteria.addOrder(Order.asc("name"));
		
		if (includeRetired == false)
			criteria.add(Expression.eq("retired", false));
		
		return criteria.list();
	}


	@SuppressWarnings("unchecked")
	
	public List<HouseholdEncounterType> findHouseholdEncounterTypes(String name)
			throws APIException {
		return sessionFactory.getCurrentSession().createCriteria(HouseholdEncounterType.class)
		// 'ilike' case insensitive search
		        .add(Expression.ilike("name", name, MatchMode.START)).addOrder(Order.asc("name")).addOrder(
			            Order.asc("retired")).list();
	}

	
	public HouseholdObs getHouseholdObs(Integer obsId) throws APIException {
		return (HouseholdObs) sessionFactory.getCurrentSession().get(HouseholdObs.class, obsId);
	}


	
	public HouseholdObs getHouseholdObsByUuid(String uuid) throws APIException {
		return (HouseholdObs) sessionFactory.getCurrentSession().createQuery("from Household_Obs o where o.uuid = :uuid").setString("uuid",
			    uuid).uniqueResult();
	}


	
	public HouseholdObs saveHouseholdObs(HouseholdObs obs)
			throws APIException {
		/*if (obs.hasGroupMembers() && obs.getId() != null) {
			// hibernate has a problem updating child collections
			// if the parent object was already saved so we do it
			// explicitly here
			for (HouseholdObs member : obs.getHouseholdGroupMembers())
				if (member.getId() == null)
					saveHouseholdObs(member);
		}*/
		
		sessionFactory.getCurrentSession().saveOrUpdate(obs);
		
		return obs;
	}
	
	/**
	 * @see org.openmrs.api.HouseholdObsService#purgeHouseholdObs(HouseholdObs)
	 */
	public void deleteHouseholdObs(HouseholdObs obs) throws APIException{
		sessionFactory.getCurrentSession().delete(obs);
	}

	
	@SuppressWarnings("unchecked")
	public List<HouseholdObs> getObservations(List<Household> household,
			List<HouseholdEncounter> encounters, List<Concept> questions,
			List<Concept> answers, List<HouseholdLocation> locations,
			List<String> sortList, Integer mostRecentN, Integer obsGroupId,
			Date fromDate, Date toDate, boolean includeVoidedObs)
			throws APIException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HouseholdObs.class, "obs");
		
		if (CollectionUtils.isNotEmpty(household))
			criteria.add(Restrictions.in("householdGroups", household));
		
		if (CollectionUtils.isNotEmpty(encounters))
			criteria.add(Restrictions.in("householdEncounter", encounters));
		
		if (CollectionUtils.isNotEmpty(questions))
			criteria.add(Restrictions.in("concept", questions));
		
		if (CollectionUtils.isNotEmpty(answers))
			criteria.add(Restrictions.in("valueCoded", answers));
		
		if (CollectionUtils.isNotEmpty(locations))
			criteria.add(Restrictions.in("householdLocation", locations));
		
		// TODO add an option for each sort item to be asc/desc
		if (CollectionUtils.isNotEmpty(sortList)) {
			for (String sort : sortList) {
				if (sort != null && !"".equals(sort))
					criteria.addOrder(Order.desc(sort));
			}
		}
		
		if (mostRecentN != null && mostRecentN > 0)
			criteria.setMaxResults(mostRecentN);
		
		if (obsGroupId != null) {
			criteria.createAlias("householdObsGroup", "og");
			criteria.add(Restrictions.eq("og.obsId", obsGroupId));
		}
		
		if (fromDate != null)
			criteria.add(Restrictions.ge("householdObsDatetime", fromDate));
		
		if (toDate != null)
			criteria.add(Restrictions.le("householdObsDatetime", toDate));
		
		List<ConceptName> valueCodedNameAnswers = null;
			criteria.add(Restrictions.in("valueCodedName", valueCodedNameAnswers));
		
		if (includeVoidedObs == false)
			criteria.add(Restrictions.eq("voided", false));
			
			return criteria.list();
	}


	
	public void deleteHouseholdEncounter(HouseholdEncounter encounter)
			throws DAOException {
		sessionFactory.getCurrentSession().delete(encounter);
		
	}
	
	/**
	 * @see org.openmrs.api.db.EncounterDAO#deleteEncounterType(org.openmrs.EncounterType)
	 */
	public void deleteHouseholdEncounterType(HouseholdEncounterType encounterType) throws DAOException {
		sessionFactory.getCurrentSession().delete(encounterType);
	}
        
        public boolean saveHouseholdDefinitionParent(HouseholdDefinitionParent householdDefinitionParent){
            try {
			sessionFactory.getCurrentSession().saveOrUpdate(householdDefinitionParent);
			return true;
            }
            catch (Exception e) {
                    return false;
            }
        }
	public HouseholdDefinitionParent getHouseholdDefinitionParent(int id){
            return (HouseholdDefinitionParent) sessionFactory.getCurrentSession().get(HouseholdDefinitionParent.class, id);
        }
	public HouseholdDefinitionParent getHouseholdDefinitionParent(String uuid){
            Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HouseholdDefinitionParent.class).add(
		    Expression.eq("uuid", uuid));
            @SuppressWarnings("unchecked")
            List<HouseholdDefinitionParent> hp = criteria.list();
            if (null == hp || hp.isEmpty()) {
                    return null;
            }
            return hp.get(0);
        }
	public HouseholdDefinitionParent getHouseholdDefinitionParent(HouseholdDefinitionParent householdDefinitionParent){
            if (householdDefinitionParent.getId() != null){
	    	return (HouseholdDefinitionParent) sessionFactory.getCurrentSession().get(HouseholdDefinitionParent.class, householdDefinitionParent.getId());
	    }else if(householdDefinitionParent.getUuid() != null){
	    	Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HouseholdDefinitionParent.class).add(
			    Expression.eq("uuid", householdDefinitionParent.getUuid()));
			@SuppressWarnings("unchecked")
			List<HouseholdDefinitionParent> hp = criteria.list();
			if (null == hp || hp.isEmpty()) {
				return null;
			}
			return hp.get(0);
	    }else
	    	return null;
        }
	public boolean purgeHouseholdDefinitionParent(HouseholdDefinitionParent householdDefinitionParent, String voidReason){
            householdDefinitionParent.setVoided(Boolean.TRUE);
            householdDefinitionParent.setVoidedBy(Context.getUserContext().getAuthenticatedUser());
            householdDefinitionParent.setVoidReason(voidReason);
            try {
                    sessionFactory.getCurrentSession().saveOrUpdate(householdDefinitionParent);
                    return true;
            }
            catch (Exception e) {
                    return false;
            }
        }
	public List<HouseholdDefinitionParent> getHouseholdDefinitionParent(boolean voidedIncluded){
            Criteria criteria;
            if(!voidedIncluded){
                criteria = sessionFactory.getCurrentSession().createCriteria(HouseholdDefinitionParent.class).add(
                    Expression.eq("voided", voidedIncluded));
            }else
                criteria = sessionFactory.getCurrentSession().createCriteria(HouseholdDefinitionParent.class);
            @SuppressWarnings("unchecked")
            List<HouseholdDefinitionParent> hdp = criteria.list();
            return hdp;
        }
        public List<HouseholdDefinition> getHouseholdDefinitionParentChildren(HouseholdDefinitionParent hdp) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HouseholdDefinition.class).add(
			    Expression.eq("parent", hdp));
		return criteria.list();
	}
        
        //HouseholdMembership
        public List<HouseholdMembership> getHouseholdQuasiMembersByHousehold(Household household){
            Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HouseholdMembership.class).add(
                        Expression.eq("householdMembershipGroups", household))
                    .add(Expression.eq("quasi", true))
                    .add(Expression.eq("voided", false));
            return criteria.list();
        }
        public List<HouseholdMembership> getHouseholdRetiredMembersByHousehold(Household household){
            Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HouseholdMembership.class).add(
                        Expression.eq("householdMembershipGroups", household));
            
            List<HouseholdMembership> toret = criteria.list();
            List<HouseholdMembership> tor = new ArrayList<HouseholdMembership>();
            
            for (HouseholdMembership householdMembership : toret) {
                if(householdMembership.getRetireReason() != null){
                    tor.add(householdMembership);
                }
            }
            return tor;
        }
        public List<HouseholdMembership> getHouseholdMembersVoidedByHousehold(Household household){
            Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HouseholdMembership.class)
                    .add(Expression.eq("householdMembershipGroups", household))
                    .add(Expression.eq("voided", true));
            return criteria.list();
        }
        
        public List<Household> getHouseholdsByDefinition(HouseholdDefinition householdDefinition){
            Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Household.class)
                    .add(Expression.eq("householdDef", householdDefinition));
            return criteria.list();
        }
}