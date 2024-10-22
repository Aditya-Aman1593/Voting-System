package com.voting.Dao;

import com.voting.model.Candidate;

import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class CandidateDao {

    private String jdbcURL = "jdbc:mysql://localhost:3306/VotingSystem?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "Adity@123";

    private static final String INSERT_CANDIDATE_SQL = "INSERT INTO Candidate (fullName, dob, nationality, partyName, partyLogo, votes) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_CANDIDATES = "SELECT * FROM Candidate";
    private static final String SELECT_CANDIDATE_BY_ID = "SELECT * FROM Candidate WHERE id = ?";
    private static final String DELETE_CANDIDATE_SQL = "DELETE FROM Candidate WHERE id = ? AND votes < ?";
    private static final String UPDATE_CANDIDATE_SQL = "UPDATE Candidate SET fullName = ?, partyName = ?, partyLogo = ? WHERE id = ?";
    private static final String GET_TOTAL_VOTES_SQL = "SELECT SUM(votes) AS totalVotes FROM Candidate";

    public void addCandidate(Candidate candidate) throws SQLException {
        

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CANDIDATE_SQL)) {
            preparedStatement.setString(1, candidate.getFullName());
            preparedStatement.setDate(2, java.sql.Date.valueOf(candidate.getDob()));
            preparedStatement.setString(3, candidate.getNationality());
            preparedStatement.setString(4, candidate.getPartyName());
            preparedStatement.setString(5, candidate.getPartyLogo());
            preparedStatement.setInt(6, 0); // Initial votes = 0
            preparedStatement.executeUpdate();
        }
    }

    public List<Candidate> listAllCandidates() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CANDIDATES);
             ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                Candidate candidate = new Candidate();
                candidate.setId(rs.getInt("id"));
                candidate.setFullName(rs.getString("fullName"));
                candidate.setDob(rs.getDate("dob").toLocalDate());
                candidate.setNationality(rs.getString("nationality"));
                candidate.setPartyName(rs.getString("partyName"));
                candidate.setPartyLogo(rs.getString("partyLogo"));
                candidate.setVotes(rs.getInt("votes"));
                candidates.add(candidate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return candidates;
    }

    public void updateCandidate(Candidate candidate) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CANDIDATE_SQL)) {
            preparedStatement.setString(1, candidate.getFullName());
            preparedStatement.setString(2, candidate.getPartyName());
            preparedStatement.setString(3, candidate.getPartyLogo());
            preparedStatement.setInt(4, candidate.getId());
            preparedStatement.executeUpdate();
        }
    }


    public void deleteCandidate(int id, int totalVotes) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CANDIDATE_SQL)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, totalVotes / 2); // Less than half of total votes
            preparedStatement.executeUpdate();
        }
    }

    public boolean voteCandidate(int id) throws SQLException {
        String updateQuery = "UPDATE candidate SET votes = votes + 1 WHERE id = ?";
        
        try (Connection connection = getConnection();
             PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {

           
            updateStmt.setInt(1, id);

           
            int rowsUpdated = updateStmt.executeUpdate();

            
            return rowsUpdated > 0;
        }
    }
    
    public int getTotalVotes() throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_TOTAL_VOTES_SQL);
             ResultSet rs = preparedStatement.executeQuery()) {
            if (rs.next()) {
                Integer totalVotes = rs.getInt("totalVotes");
                return (totalVotes != null) ? totalVotes : 0;
            }
        }
        return 0;
    }

    public Candidate getCandidateById(int id) throws SQLException {
        Candidate candidate = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CANDIDATE_BY_ID)) {
            preparedStatement.setInt(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    candidate = new Candidate();
                    candidate.setId(rs.getInt("id"));
                    candidate.setFullName(rs.getString("fullName"));
                    candidate.setDob(rs.getDate("dob").toLocalDate());
                    candidate.setNationality(rs.getString("nationality"));
                    candidate.setPartyName(rs.getString("partyName"));
                    candidate.setPartyLogo(rs.getString("partyLogo"));
                    candidate.setVotes(rs.getInt("votes"));
                }
            }
        }
        return candidate;
    }
    
  

    

    private int calculateAge(LocalDate dob) {
        return Period.between(dob, LocalDate.now()).getYears();
    }

    private Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }
}
