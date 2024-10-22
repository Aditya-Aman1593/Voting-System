<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.voting.model.Candidate" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard</title>
    
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    
    <link rel="stylesheet" href="components/partyDetails.css">
    
</head>
<body>
    
<button class="Main-menu" onclick="location.href='landingPage.jsp' ">Main Menu</button>

<%
@SuppressWarnings("unchecked")
List<Candidate> listCandidates = (List<Candidate>) request.getAttribute("listCandidates");
Integer totalVotes = (Integer) request.getAttribute("totalVotes");

//out.println("ListCandidates: " + listCandidates);
//out.println("TotalVotes: " + totalVotes);


if (listCandidates != null && !listCandidates.isEmpty()) {
%>
    <h1 style="text-align:center;">List of Candidates</h1>
     
   <table>
    <thead>
        <tr>
            <th>Rank</th>
            <th>Name of Candidate</th>
            <th>Name of Party</th>
            <th>Party Logo</th>
            <th>No of Votes</th>
            
           
        </tr>
    </thead>
    <tbody>
        <% int serialNo = 1; %>
        <% for (Candidate candidate : listCandidates) { %>
        <tr>
            <td><%= serialNo++ %></td>
            <td><%= candidate.getFullName() %></td>
            <td><%= candidate.getPartyName() %></td>
            <td><img src="<%= candidate.getPartyLogo() %>" alt="Logo"></td>
            <td><%= candidate.getVotes() %></td>
        
        </tr>
        <% } %>
    </tbody>
</table>

<%
} else {
    out.println("<p>No candidates available.</p>");
}
%>


<p style= "text-align: right; margin-right:50px">Total Votes: <%= totalVotes != null ? totalVotes : "Not available" %><p/>


   
</body>
</html>
