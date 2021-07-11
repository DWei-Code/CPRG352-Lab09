package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Role;
import models.User;
import services.UserService;

public class UserServlet extends HttpServlet {

    private static final String ERROR = "ERROR";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserService us = new UserService();

        try {
            HttpSession session = request.getSession();
            // using get all method from user services
            List<User> users = us.getAll();
            request.setAttribute("user", users);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, e);
            System.err.println("Error Occured retrieving user data");
        }

        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        UserService us = new UserService();

        // get an action from the JSP
        String action = request.getParameter("action");

        // get all the attributes of a user thats about to be added
        String email = request.getParameter("tableEmail");
        if (action.indexOf("edit") == 0) {
            String[] strings = action.split(" ");
            action = strings[0];
            email = strings[1];
        }

        if (action.indexOf("delete") == 0) {
            String[] strings = action.split(" ");
            action = strings[0];
            email = strings[1];
        }
        String firstName = request.getParameter("tableFirstName");
        String lastName = request.getParameter("tableLastName");
//        String password = request.getParameter("standin pass param");
//        String role = request.getParameter("standin role param");

        List<User> users;
        try {
            users = us.getAll();
        } catch (Exception e) {
            users = new ArrayList<>();
            users.add(new User(ERROR, false, ERROR, ERROR, ERROR, 1));

            e.printStackTrace();
        }

        try {
            switch (action) {
                case "Add":
                    add(request, response, us, users);
                    break;
                case "edit":
                    edit(request, response, us, users, email);
                    break;
                case "delete":
                    us.delete(email);
                    response.sendRedirect("user");
                    break;
                case "Save":
                    save(request, response, us);
                    break;

                case "Cancel":
                    response.sendRedirect("user");
                    break;
            }
        } catch (Exception e) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, e);
            System.err.println("Error Occured carrying out action:" + action);
        }
    }

    private void add(HttpServletRequest request, HttpServletResponse response,
            UserService us, List<User> users) {
        boolean isActive = false;

        ArrayList<String> nullChecker = new ArrayList<>();
        // acquiring params for a user thats being added
        String newEmail = request.getParameter("user_email");
        nullChecker.add(newEmail);
        String newFirstName = request.getParameter("user_firstname");
        nullChecker.add(newFirstName);
        String newLastName = request.getParameter("user_lastname");
        nullChecker.add(newLastName);
        String newPassword = request.getParameter("user_password");
        nullChecker.add(newPassword);
        String newRole = request.getParameter("add_user_roles");
        nullChecker.add(newRole);

        // all fields need to be entered before adding user
        for (int i = 0; i < nullChecker.size(); i++) {
            if (nullChecker.get(i) == null || nullChecker.get(i).equals("")) {
                System.out.println("EMPYT!!!!!!");
                String updateMessage = "Please enter all fields to add a new user";
                request.setAttribute("userMessage", updateMessage);
                
                try {
                    request.setAttribute("user", users);
                    getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
                    return;
                    
                } catch (Exception e) {
                    e.printStackTrace();
                    Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, e);
                    System.err.println("Error Occured retrieving user data");
                }
            }
        }

        try {
            if (!(us.get(newEmail) == null)) {
                request.setAttribute("userMessage", "Email already exists. Please enter a unique email.");
                request.setAttribute("user", users);

                getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
                return;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        // switch the role ID to int for user creation
        int roleId = 0;
        switch (newRole.toLowerCase()) {
            case "system admin":
                roleId = 1;
                break;
            case "regular user":
                roleId = 2;
                break;
            case "company admin":
                roleId = 3;
                break;
        }
        isActive = true;

        User newUser = new User(newEmail, isActive, newFirstName, newLastName, newPassword, roleId);
        try {
            us.insert(newUser);
            response.sendRedirect("user");
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void edit(HttpServletRequest request, HttpServletResponse response,
            UserService us, List<User> users, String email) {
        User editUser = new User();
        try {
            editUser = us.get(email);
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        String editEmail = editUser.getEmail();

        String editFirstName = editUser.getFirstName();
        String editLastName = editUser.getLastName();
        String editPassword = editUser.getPassword();
        int editRole = editUser.getRole();
        String roleString = "";
        switch (editRole) {
            case 1:
                roleString = "system admin";
            case 2:
                roleString = "regular user";
            case 3:
                roleString = "company admin";
        }

        try {
            // using get all method from user services
            request.setAttribute("user", users);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, e);
            System.err.println("Error Occured retrieving user data");
        }

        request.setAttribute("editEmail", editEmail);
        request.setAttribute("editFirstName", editFirstName);
        request.setAttribute("editLastName", editLastName);
        request.setAttribute("editPassword", editPassword);
        request.setAttribute("editRole", roleString);

        try {
            getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void save(HttpServletRequest request, HttpServletResponse response,
            UserService us) {
        boolean isActive = false;

        ArrayList<String> nullCheckerSave = new ArrayList<>();
        // acquiring params for a user thats being added
        String saveEmail = request.getParameter("saveuser_email");
        nullCheckerSave.add(saveEmail);

        String saveFirstName = request.getParameter("saveuser_firstname");
        nullCheckerSave.add(saveFirstName);

        String saveLastName = request.getParameter("saveuser_lastname");
        nullCheckerSave.add(saveLastName);

        String savePassword = request.getParameter("saveuser_password");
        nullCheckerSave.add(savePassword);

        String saveRole = request.getParameter("edit_user_roles");
        nullCheckerSave.add(saveRole);

        // all fields need to be entered before updating user info
        for (int i = 0; i > nullCheckerSave.size(); i++) {
            if (nullCheckerSave.get(i) == null || nullCheckerSave.get(i) == "") {
                String updateMessage = "Please enter all fields to update the user";
                request.setAttribute("userMessage", updateMessage);
                try {
                    response.sendRedirect("user");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }
        }
        // switch the role ID to int
        int roleIdSave = 0;
        switch (saveRole.toLowerCase()) {
            case "system admin":
                roleIdSave = 1;
                break;
            case "regular user":
                roleIdSave = 2;
                break;
            case "company admin":
                roleIdSave = 3;
                break;
        }
        isActive = true;

        User saveUser = new User(saveEmail, isActive, saveFirstName, saveLastName, savePassword, roleIdSave);
        try {
            us.update(saveUser);
            response.sendRedirect("user");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
