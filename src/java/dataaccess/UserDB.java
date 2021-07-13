package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import models.User;
import services.RoleService;

public class UserDB {

    public List<User> getAll() throws Exception {
        List<User> users = new ArrayList<>();
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT email, active, first_name, last_name, password, role from user";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String email = rs.getString(1);
                boolean active = rs.getBoolean(2);
                String firstName = rs.getString(3);
                String lastName = rs.getString(4);
                String password = rs.getString(5);
//                int role = rs.getInt(6);

                RoleService roleService = new RoleService();
                User user = new User(email, active, firstName, lastName, password, roleService.get(rs.getInt(6)));
                users.add(user);
            }
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }

        return users;
    }

    public User get(String email) throws Exception {
        User user = null;
        ConnectionPool conPool = ConnectionPool.getInstance();
        Connection con = conPool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT active,first_name,last_name, password, role from user  WHERE email=?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                boolean active = rs.getBoolean(1);
                String firstName = rs.getString(2);
                String lastName = rs.getString(3);
                String password = rs.getString(4);
//                int role = rs.getInt(5);

                RoleService roleService = new RoleService();
                user = new User(email, active, firstName, lastName, password, roleService.get(rs.getInt(5)));
            }
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            conPool.freeConnection(con);
        }

        return user;
    }

    public void insert(User user) throws Exception {
        insert(user.getEmail(), user.isActive(), user.getFirstName(), user.getLastName(),
                user.getPassword(), user.getRole().getRoleId());
    }

    public void insert(String email, boolean isActive, String firstName, String lastName, String password, int roleID) throws Exception {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        String sql = "INSERT INTO user (email, active, first_name, last_name, password, role) VALUES (?, ?, ?, ?, ?, ?)";

        System.out.println("ROLE: " + roleID);
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setBoolean(2, isActive);
            ps.setString(3, firstName);
            ps.setString(4, lastName);
            ps.setString(5, password);
            ps.setInt(6, roleID);
            ps.executeUpdate();
        } finally {
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
    }

    public void update(User user) throws Exception {
        update(user.getEmail(), user.isActive(), user.getFirstName(),
                user.getLastName(), user.getPassword(), user.getRole().getRoleId());
    }

    public void update(String email, boolean isActive, String firstName, String lastName, String password, int roleID) throws Exception {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        String sql = "UPDATE user SET active=?, first_name=?, last_name=?, password=?, role=? WHERE email=?";

        try {
            ps = con.prepareStatement(sql);
            ps.setBoolean(1, isActive);
            ps.setString(2, firstName);
            ps.setString(3, lastName);
            ps.setString(4, password);
            ps.setInt(5, roleID);
            ps.setString(6, email);
            ps.executeUpdate();
        } finally {
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
    }

    public void delete(User user) throws Exception {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        String sql = "DELETE FROM user WHERE email=?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, user.getEmail());
            ps.executeUpdate();
        } finally {
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
    }
}
