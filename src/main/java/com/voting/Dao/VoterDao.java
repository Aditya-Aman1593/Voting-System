package com.voting.Dao;

import com.voting.model.Voter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
public class VoterDao {

    private String jdbcURL = "jdbc:mysql://localhost:3306/VotingSystem?useSSL=false&serverTimezone=UTC";
    private String jdbcUsername = "root";
    private String jdbcPassword = "Adity@123";
    private String jdbcDriver = "com.mysql.cj.jdbc.Driver";

    private static final String INSERT_VOTER_SQL = "INSERT INTO Voter(name, dateOfBirth, phoneNumber, aadhaarNumber, password, nationality, voterId) VALUES (?, ?, ?, ?, ?, ?, ?)";
    

    public VoterDao() {}

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }
    
    public boolean validateVoter(String voterId, String password) {
        boolean isValid = false;
        String query = "SELECT * FROM voter WHERE voterId = ? AND password = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, voterId);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                isValid = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isValid;
    }

    public boolean registerVoter(Voter voter) throws SQLException {
        boolean rowInserted = false;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_VOTER_SQL)) {
            

            preparedStatement.setString(1, voter.getName());
            preparedStatement.setDate(2, new Date(voter.getDateOfBirth().getTime()));
            preparedStatement.setString(3, voter.getPhoneNumber());
            preparedStatement.setString(4, voter.getAadhaarNumber());
            preparedStatement.setString(5, voter.getPassword());
            preparedStatement.setString(6, voter.getNationality());
            preparedStatement.setString(7, voter.getVoterId());

            rowInserted = preparedStatement.executeUpdate() > 0;
        }
        return rowInserted;
    }
    
    public boolean isAadhaarRegistered(String aadhaarNumber) throws SQLException {
        String aquery = "SELECT COUNT(*) FROM voter WHERE aadhaarNumber = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(aquery)) {
            preparedStatement.setString(1, aadhaarNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0; 
            }
        } catch (SQLException e) {
            throw new SQLException("Error checking if Aadhaar is registered", e);
        }
        return false;
    }
    
    public boolean isPhoneRegistered(String phoneno) throws SQLException {
        String pquery = "SELECT COUNT(*) FROM voter WHERE phoneNumber = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(pquery)) {
            preparedStatement.setString(1, phoneno);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0; // 
            }
        } catch (SQLException e) {
            throw new SQLException("Error checking if phone is registered", e);
        }
        return false;
    }
    
    public boolean updateVoterStatus(String voterId) throws SQLException {
        String selectQuery = "SELECT status FROM voter WHERE voterId = ?";
        String updateQuery = "UPDATE voter SET status = 1 WHERE voterId = ?";
        
        try (Connection connection = getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(selectQuery);
             PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {

            selectStmt.setString(1, voterId);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next() && rs.getInt("status") == 0) {
                updateStmt.setString(1, voterId);
                return updateStmt.executeUpdate() > 0;
            }
            return false; 
        }
    }

    public boolean getVoterStatus(String voterId) throws SQLException {
        String query = "SELECT status FROM voter WHERE voterId = ?";
        
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, voterId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("status") == 1;
            }
            return false; 
        }
    }

    
    

}
