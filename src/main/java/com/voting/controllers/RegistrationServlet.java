package com.voting.controllers;

import com.voting.Dao.VoterDao;
import com.voting.model.Voter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private VoterDao voterDao;

    public void init() {
        voterDao = new VoterDao();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        
        String name = request.getParameter("name").trim();
        String dob = request.getParameter("dob").trim();
        String phone = request.getParameter("phone").trim();
        String aadhaar = request.getParameter("aadhaar").trim();
        String password = request.getParameter("password").trim();
        String nationality = request.getParameter("nationality").trim();

      
        if (name.isEmpty() || dob.isEmpty() || phone.isEmpty() || aadhaar.isEmpty() || password.isEmpty() || nationality.isEmpty()) {
            response.sendRedirect("voterRegister.jsp?error=All fields must be filled&name=" + name + "&dob=" + dob + "&phone=" + phone + "&aadhaar=" + aadhaar + "&nationality=" + nationality);
            return;
        }

        
        Date dateOfBirth = Date.valueOf(dob);
        LocalDate birthDate = dateOfBirth.toLocalDate();
        LocalDate currentDate = LocalDate.now();
        int age = Period.between(birthDate, currentDate).getYears();
        if (age < 18) {
            response.sendRedirect("voterRegister.jsp?error=You must be above 18 years old to register&name=" + name + "&dob=" + dob + "&phone=" + phone + "&aadhaar=" + aadhaar + "&nationality=" + nationality);
            return;
        }

        // Validate phone number length (must be 10 digits)
        if (!phone.matches("\\d{10}")) {
            response.sendRedirect("voterRegister.jsp?error=Invalid phone number&name=" + name + "&dob=" + dob + "&phone=" + phone + "&aadhaar=" + aadhaar + "&nationality=" + nationality);
            return;
        }
        
        
        if (!aadhaar.matches("\\d{12}")) {
            response.sendRedirect("voterRegister.jsp?error=Invalid aadhaar number&name=" + name + "&dob=" + dob + "&phone=" + phone + "&aadhaar=" + aadhaar + "&nationality=" + nationality);
            return;
        }
        
    
        try {
            if (voterDao.isAadhaarRegistered(aadhaar)) {
                response.sendRedirect("voterRegister.jsp?error=Aadhaar number already registered&name=" + name + "&dob=" + dob + "&phone=" + phone + "&aadhaar=" + aadhaar + "&nationality=" + nationality);
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("voterRegister.jsp?error=Database error&name=" + name + "&dob=" + dob + "&phone=" + phone + "&aadhaar=" + aadhaar + "&nationality=" + nationality);
            return;
        }
       

      
        try {
            if (voterDao.isPhoneRegistered(phone)) {
                response.sendRedirect("voterRegister.jsp?error=Phone number already registered&name=" + name + "&dob=" + dob + "&phone=" + phone + "&aadhaar=" + aadhaar + "&nationality=" + nationality);
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("voterRegister.jsp?error=Database error&name=" + name + "&dob=" + dob + "&phone=" + phone + "&aadhaar=" + aadhaar + "&nationality=" + nationality);
            return;
        }

     
        if (!"India".equalsIgnoreCase(nationality)) {
            response.sendRedirect("voterRegister.jsp?error=Only Indian nationals can register&name=" + name + "&dob=" + dob + "&phone=" + phone + "&aadhaar=" + aadhaar + "&nationality=" + nationality);
            return;
        }

        

        String hashedPassword = hashIt(password);

        
        Voter voter = new Voter();
        voter.setName(name);
        voter.setDateOfBirth(dateOfBirth);
        voter.setPhoneNumber(phone);
        voter.setAadhaarNumber(aadhaar);
        voter.setPassword(hashedPassword);
        voter.setNationality(nationality);
        String voterId = "VOTER" + System.currentTimeMillis();
        voter.setVoterId(voterId);

      
        try {
            boolean isRegistered = voterDao.registerVoter(voter);
            if (isRegistered) {
                request.getSession().setAttribute("RegvoterId", voterId);
                response.sendRedirect("registeredSuccessfull.jsp");
            } else {
                response.sendRedirect("voterRegister.jsp?error=Registration failed&name=" + name + "&dob=" + dob + "&phone=" + phone + "&aadhaar=" + aadhaar + "&nationality=" + nationality);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("voterRegister.jsp?error=Database error&name=" + name + "&dob=" + dob + "&phone=" + phone + "&aadhaar=" + aadhaar + "&nationality=" + nationality);
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
