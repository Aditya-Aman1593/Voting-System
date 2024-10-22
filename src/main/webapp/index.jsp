<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.voting.model.Candidate" %>

<%
if(session.getAttribute("voter") == null) {
    response.sendRedirect("voterLogin.jsp");
    return; // Ensure no further processing after redirect
}
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Voting System</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="components/index.css">
</head>
<body>
<%@include file = "components/navbar.jsp" %> 

<marquee style="background-color:yellow; color:green">
    Welcome to the official Online Voting System! Make sure to complete your registration before the deadline to be eligible to vote in the upcoming elections. Remember, every vote counts! Our secure and user-friendly platform ensures that your vote remains confidential and protected. Verify your identity using the OTP sent to your registered phone number and follow the simple instructions to cast your vote from anywhere, at any time. Stay informed about the candidates, review your vote carefully, and make your voice heard. Voting is your right and responsibility—exercise it wisely!
</marquee>

<h1 style="text-align:center; background-color: red;">Your voter ID is: <%= session.getAttribute("voter") %></h1>

<%
List<Candidate> listCandidates = (List<Candidate>) request.getAttribute("listCandidates");
Integer totalVotes = (Integer) request.getAttribute("totalVotes");

if (listCandidates != null && !listCandidates.isEmpty()) {
%>
    <h1 style="text-align:center;">List of Candidates</h1>
     
    <table>
        <thead>
            <tr>
                <th>S.No</th>
                <th>Name of Candidate</th>
                <th>Name of Party</th>
                <th>Party Logo</th>
                <th>Votes</th>
            </tr>
        </thead>
        <tbody>
            <% int serialNo = 1; %>
            <% for (Candidate candidate : listCandidates) { %>
            <tr>
                <td><%= serialNo++ %></td>
                <td><%= candidate.getFullName() %></td>
                <td><%= candidate.getPartyName() %></td>
                <td><img src="<%= candidate.getPartyLogo() %>" alt="Logo of <%= candidate.getPartyName() %>" width="50" height="50"></td>
                <td>
                    <form action="votercandidates" method="POST" style="display:inline;">
                        <input type="hidden" name="action" value="voted">
                        <input type="hidden" name="canid" value="<%= candidate.getId() %>">
                        <input type="hidden" name="voterid" value="<%= session.getAttribute("voter") %>">
                        <button type="submit" onclick="return confirm('Are you sure you want to vote for <%= candidate.getPartyName() %>?')">Vote</button>       
                    </form>
                </td>
            </tr>
            <% } %>
        </tbody>
    </table>
<%
} else {
    out.println("<p>No candidates available.</p>");
}
%>

<p style="text-align: right; margin-right:50px">Total Votes: <%= totalVotes != null ? totalVotes : "Not available" %></p>

<%@include file = "components/footer.jsp" %>    
</body>
</html>
