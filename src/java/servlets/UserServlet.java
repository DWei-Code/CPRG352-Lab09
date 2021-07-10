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
//            System.out.println(users.size());
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
        String firstName = request.getParameter("tableFirstName");
        String lastName = request.getParameter("tableLastName");
//        String password = request.getParameter("standin pass param");
//        String role = request.getParameter("standin role param");
        boolean isActive = false;

        try {
            switch (action) {
                case "Add":
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
                    for (int i = 0; i > nullChecker.size(); i++) {
                        if (nullChecker.get(i) == null || nullChecker.get(i) == "") {
                            String updateMessage = "Please enter all fields to add a new user";
                            request.setAttribute("userMessage", updateMessage);
//                            response.sendRedirect("user");
//                            return;
                            try {
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
                    us.insert(newUser);
                    response.sendRedirect("user");
                    break;
                case "edit":
                    User editUser = new User();
                    editUser = us.get(email);
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

                    request.setAttribute("editEmail", editEmail);
                    request.setAttribute("editFirstName", editFirstName);
                    request.setAttribute("editLastName", editLastName);
                    request.setAttribute("editPassword", editPassword);
                    request.setAttribute("editRole", roleString);

                    getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
                    break;
                case "delete":
                    us.delete(email);
                    break;
                case "Save":
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
                            response.sendRedirect("user");
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
                    us.update(saveUser);
                    response.sendRedirect("user");
                    break;

                case "Cancel":
                    response.sendRedirect("user");
                    break;
            }
        } catch (Exception e) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, e);
            System.err.println("Error Occured carrying out action:" + action);
        }

//        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
    }

}
