/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sampada.DAO;

import org.hibernate.Query;
import org.hibernate.Session;
import sampada.util.HibernateUtil;

/**
 *
 * @author cdac
 */
public class ConstantsMasterDAO {
    
    public String getApplicationFeebyName(String constantName) {

String lstud = "0";
Session session = null;
try {
session = HibernateUtil.getInstance().getSession();
String hql = "select E.value FROM ConstantsMaster E WHERE E.name =:constantName ";
Query query = session.createQuery(hql);
query.setParameter("constantName", constantName);
lstud = (String)query.list().get(0);

session.close();
} catch (Exception e) {
if(session!=null)
session.close();
e.printStackTrace();
}

return lstud;

}


public String getFilePathbyName(String constantName) {

String lstud = "0";
Session session = null;
try {
session = HibernateUtil.getInstance().getSession();
String hql = "select E.value FROM ConstantsMaster E WHERE E.name =:constantName ";
Query query = session.createQuery(hql);
query.setParameter("constantName", constantName);
lstud = (String)query.list().get(0);

session.close();
} catch (Exception e) {
if(session!=null)
session.close();
e.printStackTrace();
}

return lstud;

}
    
}
