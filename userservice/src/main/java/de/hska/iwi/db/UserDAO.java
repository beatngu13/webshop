package de.hska.iwi.db;

import de.hska.iwi.domain.User;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class UserDAO extends GenericHibernateDAO<User, Integer> {

    public User getUserByUsername(String name) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            User user = null;
            session.beginTransaction();
            Criteria crit = session.createCriteria(User.class);
            crit.add(Restrictions.eq("username", name));
            List<User> resultList = crit.list();
            if (resultList.size() > 0) {
                user = (User) crit.list().get(0);
            }
            session.getTransaction().commit();
            return user;
        } catch (HibernateException e) {
            System.out.println("Hibernate Exception" + e.getMessage());
            session.getTransaction().rollback();
            throw new RuntimeException(e);
        }
    }

}
