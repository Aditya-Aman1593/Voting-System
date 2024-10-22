package com.voting.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.voting.Dao.AdminDao;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@WebServlet("/adminlogin")
public class AdminLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private AdminDao adminDao;

    public AdminLoginServlet() {
        super();
        adminDao = new AdminDao();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        String hashedPassword = hashIt(password);

        boolean isValidAdmin = adminDao.validateAdmin(username, hashedPassword);

        if (isValidAdmin) {
            request.getSession().setAttribute("admin", username);
            response.sendRedirect("candidates?action=list");
        } else {
//        	String error = "Invalid username or password";
            response.sendRedirect("adminLogin.jsp?error=Invalid username or password");
        }
    }
    
    private String hashIt(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}
