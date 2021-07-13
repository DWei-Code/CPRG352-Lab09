package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import models.Role;

public class RoleDB {

    private static final int ROLE_ID = 1,
            ROLE_NAME = 2;

    public List<Role> getAll() throws Exception {
        List<Role> roles = new ArrayList<>();
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT role_id, role_name from role";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int roleId = rs.getInt(1);
                String roleName = rs.getString(2);
                Role role = new Role(roleId, roleName);
                roles.add(role);
            }
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }

        return roles;
    }

    public Role get(int roleID) throws Exception {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Role role = null;

        String sql = "SELECT role_id, role_name from role WHERE role_ID = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, roleID);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                role = new Role(rs.getInt(ROLE_ID), rs.getString(ROLE_NAME));
            }
            
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
        
        return role;
    }
    
     public Role getByName(String roleName) throws Exception {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Role role = null;

        String sql = "SELECT role_id, role_name from role WHERE role_name = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, roleName.toLowerCase());
            rs = ps.executeQuery();
            
            if (rs.next()) {
                role = new Role(rs.getInt(ROLE_ID), rs.getString(ROLE_NAME));
            }
            
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
        
        return role;
    }
}
