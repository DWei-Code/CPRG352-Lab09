
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html>
    <head>
        <link rel="stylesheet" href="styles/main.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://fonts.googleapis.com/css2?family=Lora&family=Work+Sans:wght@200&display=swap" rel="stylesheet">
        <title>Users Page</title>
    </head>
    <body>
        <form method="post" action="user">

            <div id="addUserDiv">
                <h1>Add User</h1>
                <input type="text" name="user_email" value="${email_attribute}" placeholder="Email"><br>
                <input type="text" name="user_firstname" value="${firstname_attribute}" placeholder="First Name"><br>
                <input type="text" name="user_lastname" value="${lastname_attribute}" placeholder="Last Name"><br>
                <input type="text" name="user_password" value="${password_attribute}" placeholder="Password"><br>
                <select name="user_roles" id="userRoles">
                    <option value="system_admin">System Admin</option>
                    <option value="regular_user">Regular User</option>
                    <option value="company_admin">Company Admin</option>
                </select><br>
                <input type="submit" value="Save">
            </div>

            <div id="manageUserDiv">
                <h1>Manage Users</h1> // table
                <ul>
                    <c:forEach items="${user}" var="user">
                        <li>${user.email}</li>
                        <li>${user.firstName}</li>
                        <li>${user.lastName}</li>
                        <li>${role.roleName}</li> // roledb data database
                       EDIT
                       DELETE
                        </c:forEach>
                </ul>
            </div>

            <div id="editUserDiv">
                <h1>Edit User</h1>
                <input type="text" name="user_email" value="${email_attribute}"><br>
                <input type="text" name="user_firstname" value="${firstname_attribute}"><br>
                <input type="text" name="user_lastname" value="${lastname_attribute}"><br>
                <input type="text" name="user_password" value="${password_attribute}"><br>
                <select name="edit_user_roles" id="editUserRoles">
                    <option value="systetruem_admin">System Admin</option>
                    <option value="regular_user">Regular User</option>
                    <option value="company_admin">Company Admin</option>
                </select><br>
                <input type="submit" value="Save"><br>
                <input type="submit" value="Cancel">
            </div>
        </form>
    </body>
</html>
