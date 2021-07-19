package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import models.Role;

public class RoleDB {

    private static final int ROLE_ID = 1,
            ROLE_NAME = 2;

    public List<Role> getAll() throws Exception {
        List<Role> roles = new ArrayList<>();
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {
            roles = em.createNamedQuery("Role.findAll", Role.class).getResultList();
            return roles;
        } finally {
            em.close();
        }
    }

    public Role get(int roleID) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {
            Role role = em.find(Role.class, roleID);
            return role;
        } finally {
            em.close();
        }
    }
    
     public Role getByName(String roleName) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {
            Role role = em.find(Role.class, roleName);
            return role;
        } finally {
            em.close();
        }
    }
}
