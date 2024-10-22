package com.voting.controllers;

import com.voting.Dao.CandidateDao;
import com.voting.Dao.VoterDao;
import com.voting.model.Candidate;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/votercandidates")
public class VoterCandidateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CandidateDao candidateDao;
    private VoterDao voterDao;

    @Override
    public void init() {
        candidateDao = new CandidateDao();
        voterDao = new VoterDao();
    }

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	  String action = request.getParameter("action");
          
          if ("list".equals(action)) {
              listCandidates(request, response);
          }  else {
              response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
          }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        
        if ("voted".equals(action)) {
            try {
				vote(request, response);
			} catch (SQLException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }  else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }
    
    private void vote(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String canid = request.getParameter("canid");
        String voterid = request.getParameter("voterid");

        if (canid != null && !canid.isEmpty()) {
            int id = Integer.parseInt(canid);

            boolean votver = voterDao.updateVoterStatus(voterid);

            if (votver) {
                boolean candver = candidateDao.voteCandidate(id);
                
                if (candver) {
                    
                    Candidate candidate = candidateDao.getCandidateById(id);

                    
                    request.setAttribute("candidate", candidate);
                    
                    
                    request.getRequestDispatcher("success.jsp").forward(request, response);
                } else {
                    response.sendRedirect("error.jsp?message=Candidate vote failed");
                }
            } else {
                response.sendRedirect("alreadyVoted.jsp");
            }
        } else {
            response.sendRedirect("votercandidates?action=list");
        }
    }


    private void listCandidates(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Candidate> listCandidates = candidateDao.listAllCandidates();
            int totalVotes = candidateDao.getTotalVotes();

            
            //System.out.println("Starting listCandidates method");
            //System.out.println("Candidates retrieved: " + (listCandidates != null ? listCandidates.size() : "null"));
            //System.out.println("Total votes: " + totalVotes);

            
            request.setAttribute("listCandidates", listCandidates);
            request.setAttribute("totalVotes", totalVotes);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (SQLException e) {
            log("Error retrieving candidates", e);
            request.setAttribute("errorMessage", "An error occurred while retrieving candidates: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
    }


 
  

   



   
