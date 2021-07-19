package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.User;
import services.RoleService;

public class UserDB {

    public List<User> getAll() throws Exception {
        List<User> users = new ArrayList<>();
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {
            users = em.createNamedQuery("User.findAll", User.class).getResultList();
            return users;
        } finally {
            em.close();
        }
    }

    public User get(String email) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {
            User user = em.find(User.class, email);
            return user;
        } finally {
            em.close();
        }
    }

    public void insert(User user) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            // started a new transaction
            trans.begin();
            // telling the entity manager to add the user to the database
            em.persist(user);
            // update the user
            em.merge(user);
            trans.commit();
        } catch (Exception ex) {
            // if something goes wrong roll back the data
            trans.rollback();
        } finally {
            em.close();
        }
    }
    
    public void update(User user) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();
            em.merge(user);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }

    public void delete(User user) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        System.out.println(user.getEmail());
        String email = user.getEmail();
        
        try {
            User deleteUser = em.find(User.class, email);
            trans.begin();
            // merge for the system to see it as a entity before deleting it
            em.remove(em.merge(deleteUser));
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }

    }
}
