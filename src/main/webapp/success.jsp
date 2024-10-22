<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.voting.model.Candidate" %>
<%
    // Retrieve candidate object from the request attribute
    Candidate candidate = (Candidate) request.getAttribute("candidate");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Vote Success</title>
    <link rel="stylesheet" href="components/success.css">
</head>
<body>
    

    <div class="candidate-info">
    <h1>Vote Successful!</h1>
    <p>Thank you for voting! Here is the information about the candidate you voted for:</p>
        <h2>Candidate Details</h2>
        <p><strong>Name:</strong> <%= candidate.getFullName() %></p>
        <p><strong>Party Name:</strong> <%= candidate.getPartyName() %></p>
        <img src="<%= candidate.getPartyLogo() %>" alt="Party Logo" style="width: 150px; height: 150px;"/>

        <p><strong>Total Votes:</strong> <%= candidate.getVotes() %></p>

      
        <a href="logout" class="logout-btn">Logout</a>
    </div>
</body>
</html>
