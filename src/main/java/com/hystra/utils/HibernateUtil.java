package com.hystra.utils;

import com.hystra.controllers.login.Codes;
import com.hystra.entity.login.LoginStatus;
import com.hystra.entity.login.User;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HibernateUtil {
    private static SessionFactory sessionFactory = null;
    private static SessionFactory sessionFactoryOracle = null;

    final static Logger LOG = Logger.getLogger(HibernateUtil.class);

    public static int getFiscalYearStart() {
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int year = Calendar.getInstance().get(Calendar.YEAR);
        return (month >= Codes.FIRST_FISCAL_MONTH) ? year : year - 1;
    }
    public static SessionFactory getSessionFactory(){

        if(sessionFactory == null){
            try{
                sessionFactory = new Configuration().configure("hibernate_mysql.cfg.xml").buildSessionFactory();
            }catch(Exception e){
                LOG.error(e);
            }

        }
        return sessionFactory;
    }

    public static boolean saveOrUpdate(Object obj){
        Session session = null;
        Transaction tx =null;
        boolean isSuccessful = false;
        try {
            session = getSessionFactory().openSession();

            tx = session.beginTransaction();

            session.saveOrUpdate(obj);
            tx.commit();
            isSuccessful = true;
        }catch(Exception e){
            LOG.error(e);
        }finally {
            session.clear();session.close();
        }
        return isSuccessful;
    }

    public static boolean save(Object obj){
        Session session = null;
        Transaction tx =null;
        boolean isSuccessful = false;
        try {
            session = getSessionFactory().openSession();
            tx = session.beginTransaction();
            obj = session.save(obj);
            tx.commit();
            isSuccessful = true;
        }catch(Exception e){
            LOG.error(e);
        }finally {
            session.clear();session.close();
        }
        return isSuccessful;
    }
    public static Integer saveAndGetId(Object obj){
        Session session = null;
        Transaction tx =null;
        boolean isSuccessful = false;
        Integer id = null;
        try {
            session = getSessionFactory().openSession();
            tx = session.beginTransaction();
            id = (Integer) session.save(obj);
            tx.commit();
            isSuccessful = true;
        }catch(Exception e){
            LOG.error(e);
        }finally {
            session.clear();session.close();
        }
        return id;
    }

    public static Object getDBObjectsQueryParameter(String queryString, String paramName, List<Long> param){
        Session session = null;
        Object objects = null;

        try {

            session = getSessionFactory()
                    .openSession();
            Query query = session.createQuery(queryString);
            query.setParameterList(paramName,param);
            objects = query.list();
        }catch(Exception e){
            LOG.error(e);
        }finally {
            session.clear();session.close();
        }
        return objects;
    }
    public static ArrayList<Object> getDBObjectsFromSQLQueryClass(String query, Class cls){
        Session session = null;
        ArrayList<Object> objects = new ArrayList<>();
        try {

            session = getSessionFactory()
                    .openSession();
            objects = (ArrayList<Object>) session.createSQLQuery(query).addEntity(cls).list();
        }catch(Exception e){
            LOG.error(e);
        }finally {

            session.clear();
            session.close();
        }
        return objects;
    }


    public static ArrayList<Object> getDBObjectsFromSQLQuery(String query){
        Session session = null;
        ArrayList<Object> objects = new ArrayList<>();
        try {

            session = getSessionFactory()
                    .openSession();
            objects = (ArrayList<Object>) session.createSQLQuery(query).list();
        }catch(Exception e){
            LOG.error(e);
        }finally {

            session.clear();session.close();
        }
        return objects;
    }
    public static Object getDBObjects(String query){
        Session session = null;
        Object objects = null;
        try {

            session = getSessionFactory()
                    .openSession();
            objects = session.createQuery(query).list();
        }catch(Exception e){
            LOG.error(e);
        }finally {

            session.clear();
            session.close();
        }
        return objects;
    }

    public static String generateToken(String uuid) {
        String random = String.valueOf(System.currentTimeMillis())+uuid;
        return random;
    }

    /*
    This method will return count number.
     */
    public static int getRecordCount(String queryString){
        Session session = null;
        String result = "";
        Integer count = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            count = ((Long)session.createQuery(queryString).uniqueResult()).intValue();

        }catch(Exception e){
            LOG.error(e);
        }finally{

            session.clear();session.close();
        }

        return (int) count;
    }

    /*
    This method will return single cell on provided Query.
     */
    public static String getSingleString(String queryString){
        Session session = null;
        String result = "";
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            Query query = session.createSQLQuery(queryString);
            List<Object> cell = (List<Object>) query.list();
            if(cell!=null && cell.size()>0){
                result = cell.get(0).toString();
            }

        }catch(Exception e){
            LOG.error(e);
        }finally{
            session.clear();session.close();
        }

        return result;
    }

    public static long getNextBaseID(int appNumber){
        ArrayList<IDMANAGER> idManagers = (ArrayList<IDMANAGER>) getDBObjects("from IDMANAGER");
        IDMANAGER idManager = idManagers.get(0);
        long lastID = 0;
        if(appNumber== Codes.FYNALS_APP_CODE){
            lastID = idManager.getLastID()+50000;
            idManager.setLastID(lastID);
        }

        idManager.setId(1);
        HibernateUtil.saveOrUpdate(idManager);
        return lastID;
    }

    public static void insertDtismg(String queryString){
        Session session = null;
        String result = "";
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            Query query = session.createSQLQuery(queryString);
            query.executeUpdate();
            int v= 0;

        }catch(Exception e){
            LOG.error(e);
        }finally{
            session.clear();session.close();
        }
    }

    public static boolean saveOrUpdateListMySQL(List<? extends Object> objs){
        Session session = null;
        Transaction tx =null;
        boolean isSuccessful = false;
        try {

            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            for (Object obj : objs){

                session.save(obj);
            }

            tx.commit();
            isSuccessful = true;
        }catch(Exception e){
            LOG.error(e);
        }finally {
            session.clear();session.close();
        }
        return isSuccessful;
    }

    public static Object getSQLQueryResult(String query){
        Session session = null;
        Object objects = null;
        try {

            session = getSessionFactory()
                    .openSession();
            objects = session.createSQLQuery(query).list();
        }catch(Exception e){
            LOG.error(e);
        }finally {
            session.clear();session.close();
        }
        return objects;
    }

    public static boolean deleteObject(String queryString) {
        Session session = null;
        Transaction tx =null;
        boolean isSuccessful = false;
        try {

            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            Query query = session.createQuery(queryString);
            int deletedRows = query.executeUpdate();


            tx.commit();
            if(deletedRows!=0){
                isSuccessful = true;
            }

        }catch(Exception e){
            LOG.error(e);
        }finally {
            session.clear();session.close();
        }
        return isSuccessful;
    }
    public static boolean executeQueryMySQL(String query){
        Session session = null;
        Transaction tx =null;
        boolean isSuccessful = false;
        try {

            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            int s= session.createSQLQuery(query).executeUpdate();

            tx.commit();
            isSuccessful = true;
        }catch(Exception e){
            LOG.error(e);
        }finally {
            session.close();
        }
        return isSuccessful;
    }

    public static boolean isLoginValid(String token){
        boolean isValid = false;
        String query = "from LoginStatus where token = '"+token+"' and status=1";
        List<LoginStatus> loginStatuses = (List<LoginStatus>) getDBObjects(query);
        if(loginStatuses!=null && loginStatuses.size()>0){
            isValid = true;
        }
        return isValid;
    }

    public static LoginStatus getLoginObjFromToken(String token){
        LoginStatus loginStatus = new LoginStatus();
        boolean isValid = false;
        String query = "from LoginStatus where token = '"+token+"' and status=1";
        List<LoginStatus> loginStatuses = (List<LoginStatus>) getDBObjects(query);
        if(loginStatuses!=null && loginStatuses.size()>0){
            loginStatus = loginStatuses.get(0);
        }
        return loginStatus;
    }

    public static User getUser(String token) {
        User user = new User();
        String query = "from LoginStatus where token = '"+token+"' and status=1";
        List<LoginStatus> loginStatuses = (List<LoginStatus>) getDBObjects(query);
        if(loginStatuses!=null && loginStatuses.size()>0){
            String username = loginStatuses.get(0).getUsername();
            query = "from User where username = '"+username+"'";
            List<User> users = (List<User>) getDBObjects(query);
            if(users!=null && users.size()>0){
                user = users.get(0);
            }
        }
        return user;
    }
}
