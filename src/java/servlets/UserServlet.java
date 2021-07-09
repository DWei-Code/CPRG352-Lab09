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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserService us = new UserService();

        try {
            HttpSession session = request.getSession();
            // using get all method from user services
            List<User> users = us.getAll();
            request.setAttribute("users", users);
        } catch (Exception e) {
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
        String email = request.getParameter("standin email param");
        String firstName = request.getParameter("standin firstName param");
        String lastName = request.getParameter("standin lastName param");
//        String password = request.getParameter("standin pass param");
        String role = request.getParameter("standin role param");
        boolean isActive = false;

        try {
            switch (action) {
                case "add":
                    ArrayList<String> nullChecker = new ArrayList<>();
                    // acquiring params for a user thats being added
                    String newEmail = request.getParameter("to be added email param");
                    nullChecker.add(newEmail);
                    String newFirstName = request.getParameter("to be added first name param");
                    nullChecker.add(newFirstName);
                    String newLastName = request.getParameter("to be added lastName param");
                    nullChecker.add(newLastName);
                    String newPassword = request.getParameter("to be added pass param");
                    nullChecker.add(newPassword);
                    String newRole = request.getParameter("to be added role param");
                    nullChecker.add(newRole);

                    // all fields need to be entered before adding user
                    for (int i = 0; i > nullChecker.size(); i++) {
                        if (nullChecker.get(i) == null || nullChecker.get(i) == "") {
                            String updateMessage = "Please enter all fields to add a new user";
                            request.setAttribute("userMessage", updateMessage);
                            response.sendRedirect("user");
                            return;
                        }
                    }
                    // switch the role ID to int for user creation
                    Role getId = new Role(newRole);
                    int roleId = getId.getRoleId();
                    isActive = true;

                    User newUser = new User(newEmail, isActive, newFirstName, newLastName, newPassword, roleId);
                    us.addUser(newUser);
                    response.sendRedirect("user");
                    break;
                case "edit":
                    User editUser = new User();
                    editUser = us.get(email);
                    String editEmail = editUser.getEmail();
                    String editFirstName = editUser.getFirstName();
                    String editLastName = editUser.getLastName();
                    String editPassword = editUser.getPassword();
                    String editRole = editUser.getRole();

                    request.setAttribute("editEmail", editEmail);
                    request.setAttribute("editFirstName", editFirstName);
                    request.setAttribute("editLastName", editLastName);
                    request.setAttribute("editPassword", editPassword);
                    request.setAttribute("editRole", editRole);

                    getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
                    break;
                case "delete":
                    us.removeUser(email);
                    break;
                case "saveEdit":
                    ArrayList<String> nullCheckerSave = new ArrayList<>();
                    // acquiring params for a user thats being added
                    String saveEmail = request.getParameter("to be added email param");
                    nullCheckerSave.add(saveEmail);
                    String saveFirstName = request.getParameter("to be added first name param");
                    nullCheckerSave.add(saveFirstName);
                    String saveLastName = request.getParameter("to be added lastName param");
                    nullCheckerSave.add(saveLastName);
                    String savePassword = request.getParameter("to be added pass param");
                    nullCheckerSave.add(savePassword);
                    String saveRole = request.getParameter("to be added role param");
                    nullCheckerSave.add(saveRole);

                    // all fields need to be entered before updating user info
                    for (int i = 0; i > nullCheckerSave.size(); i++) {
                        if (nullCheckerSave.get(i) == null || nullCheckerSave.get(i) == "") {
                            String updateMessage = "Please enter all fields to update the user";
                            request.setAttribute("userMessage", updateMessage);
                            response.sendRedirect("user");
                            return;
                        }
                    }
                    // switch the role ID to int
                    Role getSaveId = new Role(saveRole);
                    int roleSaveId = getSaveId.getRoleId();
                    isActive = true;

                    User saveUser = new User(newEmail, isActive, newFirstName, newLastName, newPassword, roleId);
                    us.updateUser(saveUser);
                    response.sendRedirect("user");
                    break;
                // will there be a cancel button?
            }
        } catch (Exception e) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, e);
            System.err.println("Error Occured carrying out action:" + action);
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
    }

}