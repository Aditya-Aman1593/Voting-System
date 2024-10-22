package com.voting.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import com.voting.Dao.VoterDao;

@WebServlet("/voterlogin")
public class VoterLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
	private VoterDao voterDao;
	
    public VoterLoginServlet() {
        super();
        // TODO Auto-generated constructor stub
        voterDao = new VoterDao();
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	 @Override
	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        String voterId = request.getParameter("voterId");
	        String password = request.getParameter("password");
	        
	        String hashedPassword = hashIt(password);

	        boolean isValidVoter = voterDao.validateVoter(voterId, hashedPassword);
	        System.out.println(isValidVoter);
	        
	        
	        boolean votver = false;
	        try {
				 votver = voterDao.getVoterStatus(voterId);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        if(votver==true) {
	        	response.sendRedirect("alreadyVoted.jsp");
	        	return;
	        }

	        System.out.println(votver);
	        if (isValidVoter) {
	            request.getSession().setAttribute("voter", voterId);
	            response.sendRedirect("votercandidates?action=list");
	        } else {
	            response.sendRedirect("voterLogin.jsp?error=Invalid Voter ID or password");
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
