<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.voting.model.Candidate" %>
<%
if (session.getAttribute("admin") == null) {
    response.sendRedirect("adminLogin.jsp");
    return;  
}
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard</title>
    
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    
    <link rel="stylesheet" href="components/adminMainPage.css">
    
</head>
<body>
    
     <%@include file="components/navbar.jsp" %> 

    <!-- <a href="addCandidate.jsp">Add Candidate</a> -->
   
  
  


<%
@SuppressWarnings("unchecked")
List<Candidate> listCandidates = (List<Candidate>) request.getAttribute("listCandidates");
Integer totalVotes = (Integer) request.getAttribute("totalVotes");

//out.println("ListCandidates: " + listCandidates);
//out.println("TotalVotes: " + totalVotes);


if (listCandidates != null && !listCandidates.isEmpty()) {
%>
    <h1 style="text-align:center;">List of Candidates</h1>
     <button class="addcandbtn"onclick="location.href='addCandidate.jsp' ">Add Candidate</button>
   <table>
    <thead>
        <tr>
            <th>S.No</th>
            <th>Name of Candidate</th>
            <th>Name of Party</th>
            <th>Party Logo</th>
            <th>No of Votes</th>
            <th>Edit</th>
            <th>Delete</th>
           
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
            <td>
                <a href="candidates?action=edit&id=<%= candidate.getId() %>">
                    <i class="fas fa-edit"></i>
                </a>
            </td>
            <td>
                <form action="candidates" method="POST" style="display:inline;">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="id" value="<%= candidate.getId() %>">
                    <button type="submit" onclick="return confirm('Are you sure you want to delete this candidate?');" style="background:none;border:none;">
                        <i class="fa-solid fa-trash-can"></i>
                    </button>
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


<p style= "text-align: right; margin-right:50px">Total Votes: <%= totalVotes != null ? totalVotes : "Not available" %><p/>


    <!-- Footer inclusion -->
    <%@include file="components/footer.jsp" %>
</body>
</html>
