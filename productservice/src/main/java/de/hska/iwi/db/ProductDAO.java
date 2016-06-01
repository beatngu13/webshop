package de.hska.iwi.db;

import de.hska.iwi.domain.Product;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class ProductDAO extends GenericHibernateDAO<Product, Integer> {

    public List<Product> getProductListByCriteria(String searchDescription,
                                                  Double searchMinPrice, Double searchMaxPrice) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = null;
        List<Product> productList = null;

        try {
            transaction = session.beginTransaction();
            Criteria crit = session.createCriteria(Product.class);

            // Define Search HQL:
            if (searchDescription != null && searchDescription.length() > 0) {
                // searchValue is set:
                searchDescription = "%" + searchDescription + "%";
                crit.add(Restrictions.ilike("details", searchDescription));
            }

            if ((searchMinPrice != null) && (searchMaxPrice != null)) {
                crit.add(Restrictions.between("price", searchMinPrice, searchMaxPrice));
            } else if (searchMinPrice != null) {
                crit.add(Restrictions.ge("price", searchMinPrice));
            } else if (searchMaxPrice != null) {
                crit.add(Restrictions.le("price", searchMaxPrice));
            }

            productList = crit.list();

            transaction.commit();

        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }

        return productList;
    }

    public Product getProductByName(String name) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        String queryString = "from Product where name = :name";
        Transaction transaction = null;
        Product product = null;

        try {
            transaction = session.beginTransaction();
            Query query = session.createQuery(queryString);

            query.setString("name", name);

            product = (Product) query.uniqueResult();

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }

        return product;
    }


}
