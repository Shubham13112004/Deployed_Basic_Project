package com.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/controller")
public class LogicCode extends HttpServlet {
    private static final long serialVersionUID = 1L;

 // Replace your old local variables with these:
    private final String url = "jdbc:postgresql://ep-lively-poetry-a1wwois9-pooler.ap-southeast-1.aws.neon.tech/neondb?sslmode=require";
    private final String user = "neondb_owner"; 
    private final String password = "npg_eAJy70ZdLMxh";
    // 1. SELECT OPERATION (Display on JSP)
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Map<String, String>> userList = new ArrayList<>();
        
        try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection(url, user, password);
            PreparedStatement ps = con.prepareStatement("SELECT * FROM emp");
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                Map<String, String> userMap = new HashMap<>();
                userMap.put("name", rs.getString("name"));
                userMap.put("email", rs.getString("email"));
                userList.add(userMap);
            }
            con.close();
            
            // Send the list to display.jsp
            request.setAttribute("data", userList);
            RequestDispatcher rd = request.getRequestDispatcher("display.jsp");
            rd.forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 2. INSERT OPERATION
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");

        try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection(url, user, password);
            
            // CORRECTED: Pass the query inside prepareStatement()
            PreparedStatement ps = con.prepareStatement("INSERT INTO emp(name, email) VALUES(?, ?)");
            ps.setString(1, name);
            ps.setString(2, email);
            
            ps.executeUpdate();
            con.close();
            
            // After insert, redirect to doGet to show updated list
            response.sendRedirect("controller");
            
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().print("Error: " + e.getMessage());
        }
    }
}