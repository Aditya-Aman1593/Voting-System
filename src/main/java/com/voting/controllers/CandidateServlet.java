package com.voting.controllers;

import com.voting.Dao.CandidateDao;
import com.voting.model.Candidate;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@WebServlet("/candidates")
@MultipartConfig 
public class CandidateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CandidateDao candidateDao;

    @Override
    public void init() {
        candidateDao = new CandidateDao();
    }

   
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("listParties".equals(action)) {
        	System.out.println("called listparty");
        	listCandidates(request, response);
        } else if ("edit".equals(action)) {
            showEditForm(request, response);
        } else if ("list".equals(action) || action == null) {
            listCandidates(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }

  


    private void listCandidates(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Candidate> listCandidates = candidateDao.listAllCandidates();
            int totalVotes = candidateDao.getTotalVotes();

            // Log the data for debugging
            System.out.println("Starting listCandidates method");
            System.out.println("Candidates retrieved: " + (listCandidates != null ? listCandidates.size() : "null"));
            System.out.println("Total votes: " + totalVotes);

            
            request.setAttribute("listCandidates", listCandidates);
            request.setAttribute("totalVotes", totalVotes);

           
            String action = request.getParameter("action");
            if ("listParties".equals(action)) {
            	
            	Collections.sort(listCandidates, new Comparator<Candidate>() {
            	    @Override
            	    public int compare(Candidate c1, Candidate c2) {
            	        return Integer.compare(c2.getVotes(), c1.getVotes()); // Sort in descending order
            	    }
            	});
                request.getRequestDispatcher("PartyDetails.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("adminMainPage.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            log("Error retrieving candidates", e);
            request.setAttribute("errorMessage", "An error occurred while retrieving candidates: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }



    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam != null && !idParam.isEmpty()) {
            int id = Integer.parseInt(idParam);
            try {
                Candidate candidate = candidateDao.getCandidateById(id);
                if (candidate != null) {
                    request.setAttribute("candidate", candidate);
                    request.getRequestDispatcher("editCandidate.jsp").forward(request, response);
                } else {
                    response.sendRedirect("candidates?action=list");
                }
            } catch (SQLException e) {
                log("Error retrieving candidate for edit", e);
                request.setAttribute("errorMessage", "An error occurred while retrieving candidate details: " + e.getMessage());
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        } else {
            response.sendRedirect("candidates?action=list");
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            switch (action) {
                case "add":
                    addCandidate(request, response);
                    break;
                case "update":
                    updateCandidate(request, response);
                    break;
                case "delete":
                    deleteCandidate(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
                    break;
            }
        } catch (SQLException e) {
            log("Error processing action: " + action, e);
            request.setAttribute("errorMessage", "An error occurred: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    private void addCandidate(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, SQLException {
        String fullName = request.getParameter("fullName").trim();
        String dob = request.getParameter("dob").trim();
        String nationality = request.getParameter("nationality").trim();
        String partyName = request.getParameter("partyName");
        
        if (fullName.isEmpty() || dob.isEmpty() ||  nationality.isEmpty()) {
            response.sendRedirect("addCandidate.jsp?errorMessage=All fields must be filled&name=");
            return;
        }
        
        

        
        Date dateOfBirth = Date.valueOf(dob);
        LocalDate birthDate = dateOfBirth.toLocalDate();
        LocalDate currentDate = LocalDate.now();
        int age = Period.between(birthDate, currentDate).getYears();
        if (age < 35) {
            response.sendRedirect("addCandidate.jsp?errorMessage=Candidate must be above 35 years old to register&name=" );
            return;
        }

        if (!"India".equalsIgnoreCase(nationality)) {
            response.sendRedirect("addCandidate.jsp?errorMessage=Only Indian Candidate can be added");
            return;
        }
        
        // Handle file upload
        Part filePart = request.getPart("partyLogo"); 
        String fileName = filePart.getSubmittedFileName(); 
        String uniqueFileName = System.currentTimeMillis() + "_" + fileName; 
        String uploadsDir = getServletContext().getRealPath("/uploads"); 

        
        File uploads = new File(uploadsDir);
        if (!uploads.exists()) {
            uploads.mkdir(); 
        }

        String filePath = uploadsDir + File.separator + uniqueFileName; 

       
        filePart.write(filePath); 

        Candidate newCandidate = new Candidate();
        newCandidate.setFullName(fullName);
        newCandidate.setDob(LocalDate.parse(dob));
        newCandidate.setNationality(nationality);
        newCandidate.setPartyName(partyName);
        newCandidate.setPartyLogo("uploads/" + uniqueFileName); 

        candidateDao.addCandidate(newCandidate);
        response.sendRedirect("candidates?action=list");
    }



    private void updateCandidate(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String idParam = request.getParameter("id");
        if (idParam != null && !idParam.isEmpty()) {
            int id = Integer.parseInt(idParam); 
            String fullName = request.getParameter("fullName").trim();
            String partyName = request.getParameter("partyName").trim();
            
            if (fullName.isEmpty() || partyName.isEmpty()) {
                response.sendRedirect("candidates?action=edit&id=" + idParam + "&errorMessage=All fields must be filled&name=" + fullName);
                return;
            }
            
            Candidate candidate = new Candidate();
            candidate.setId(id);
            candidate.setFullName(fullName);
            candidate.setPartyName(partyName);
            
            // Handle file upload
            Part filePart = request.getPart("partyLogo"); 
            if (filePart != null && filePart.getSize() > 0) { 
                String fileName = filePart.getSubmittedFileName(); 
                String uniqueFileName = System.currentTimeMillis() + "_" + fileName; 
                String uploadsDir = getServletContext().getRealPath("/uploads"); 

                
                File uploads = new File(uploadsDir);
                if (!uploads.exists()) {
                    uploads.mkdir(); 
                }

                String filePath = uploadsDir + File.separator + uniqueFileName; 

             
                try {
                    filePart.write(filePath); 
                    candidate.setPartyLogo("uploads/" + uniqueFileName); 
                } catch (IOException e) {
                  
                    request.setAttribute("errorMessage", "Failed to upload file: " + e.getMessage());
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                    return;
                }
            } else {
               
                Candidate existingCandidate = candidateDao.getCandidateById(id);
                if (existingCandidate != null) {
                    candidate.setPartyLogo(existingCandidate.getPartyLogo()); // Retain existing logo
                }
            }

          
            candidateDao.updateCandidate(candidate);

            
            response.sendRedirect("candidates?action=list");
        } else {
            response.sendRedirect("error.jsp");
        }
    }



    private void deleteCandidate(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String idParam = request.getParameter("id");
        if (idParam != null && !idParam.isEmpty()) {
            int id = Integer.parseInt(idParam);

            // Fetch totalVotes directly from the database
            int totalVotes = candidateDao.getTotalVotes();

            // Perform delete if the candidate has less than half of the total votes
            candidateDao.deleteCandidate(id, totalVotes);

            response.sendRedirect("candidates");
        } else {
            response.sendRedirect("candidates");
        }
    }
}
