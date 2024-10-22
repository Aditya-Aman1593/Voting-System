<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.voting.model.Candidate" %>
<% Candidate candidate = (Candidate) request.getAttribute("candidate"); %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Candidate</title>
    <link rel="stylesheet" href="components/editCandidate.css">
</head>
<body>


    
    <div class="container">
        <h2>Edit Candidate Information</h2>
        <% 
        String errorMessage = request.getParameter("errorMessage");
        if (errorMessage != null && !errorMessage.isEmpty()) {
        %>
        <div style="color: red; text-align : center;">*<%= errorMessage %></div>
        <% 
        } 
        %>
        <form action="candidates" method="post" enctype="multipart/form-data">
            <div class="row">
                <div class="column">
                     <input type="hidden" name="action" value="update">
        <!-- Hidden field for candidate ID -->
        <input type="hidden" name="id" value="<%= candidate.getId() %>">

        <label for="fullName">Full Name:</label>
        <input type="text" name="fullName" id="fullName" placeholder="Full Name" value="<%= candidate.getFullName() %>" required>

        <label for="partyName">Party Name:</label>
        <input type="text" name="partyName" id="partyName" placeholder="Party Name" value="<%= candidate.getPartyName() %>" required>

        
       <label for="partyLogo">Party Logo:</label>
 	   <input type="file" name="partyLogo" id="partyLogo" required>

        <button type="submit">Edit Candidate</button>
         
                </div>
            </div>
        </form>
    </div>

 
 

</body>
</html>
