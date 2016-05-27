package de.hska.iwi.db;

import de.hska.iwi.domain.Role;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class RoleDAO extends GenericHibernateDAO<Role, Integer> {
	
	public Role getRoleByLevel(int roleLevel) {
		
		Role role = null;
	    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try
		{
			session.beginTransaction();
            Criteria crit = session.createCriteria(Role.class);
            crit.add(Restrictions.eq("level",roleLevel));
            List<Role> resultList = crit.list();
            
            if (resultList.size() > 0) {
            	role = resultList.get(0);
            }
            session.getTransaction().commit();
            return role;
		}
		catch (HibernateException e)
		{
			System.out.println("Hibernate Exception" + e.getMessage());
			session.getTransaction().rollback();
			throw new RuntimeException(e);
		}

		

	}

}
