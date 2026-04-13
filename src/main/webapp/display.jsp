<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<title>User Records</title>
</head>
<body>
    <h2>Registered Users</h2>
    <table border="1">
        <tr>
            <th>Name</th>
            <th>Email</th>
        </tr>
        <%
            List<Map<String, String>> userList = (List<Map<String, String>>) request.getAttribute("data");
            if (userList != null) {
                for (Map<String, String> userMap : userList) {
        %>
        <tr>
            <td><%= userMap.get("name") %></td>
            <td><%= userMap.get("email") %></td>
        </tr>
        <%
                }
            } else {
        %>
        <tr><td colspan="2">No records found.</td></tr>
        <% } %>
    </table>
    <br>
    <a href="index.jsp">Add New User</a>
</body>
</html>