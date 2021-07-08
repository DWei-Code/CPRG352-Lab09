
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Users Page</title>
    </head>
    <body>
        <form method="post" action="user">
            <h1>Add User</h1>
            <input type="text" name="user_email" value="${email_attribute}">
            <input type="text" name="user_firstname" value="${firstname_attribute}">
            <input type="text" name="user_lastname" value="${lastname_attribute}">
            <input type="text" name="user_password" value="${password_attribute}">
            <select name="user_roles" id="userRoles">
                <option value="system_admin">System Admin</option>
                <option value="regular_user">Regular User</option>
                <option value="company_admin">Company Admin</option>
            </select>
            <a href="user?edit=true">Save</a>
            <h1>Manage Users</h1>
            <span>First Name</span>
            <span>Last Name</span>
            <span>Edit</span>
            <span>Delete</span>
            <h1>Edit User</h1>
            <span>${user.email}</span>
            <span>${user.firstName}</span>
            <span>${user.lastName}</span>
            <select name="user_roles" id="userRoles">
                <option value="systetruem_admin">System Admin</option>
                <option value="regular_user">Regular User</option>
                <option value="company_admin">Company Admin</option>
            </select>
            <a href="user?edit=true">Save</a>
            <a href="user?edit=">Cancel</a>
        </form>
    </body>
</html>
