/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sampada.DAO;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import sampada.pojo.EntityType;
import sampada.util.HibernateUtil;

/**
 *
 * @author cdac
 */
public class EntityTypeDAO {
    public List<EntityType> entityTypeList() {

        Session session = null;
        try {
            session = HibernateUtil.getInstance().getSession();
            Query query = session.createQuery("from EntityType s order by entityType");
            List<EntityType> queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {

                return null;
            } else {
                System.out.println("list size" + queryList.size());
                return queryList;
            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            session.close();

        }

    }

    
}
