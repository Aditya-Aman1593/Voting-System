<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="true" %>
<%
    // Fetch voter ID from the session
    String voterId = (String) session.getAttribute("RegvoterId");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registration Success</title>
    <link rel="stylesheet" href="components/registeredsuccessfull.css">
</head>
<body>

    <div class="candidate-info">
        <!-- Space for the picture -->
        <img src="components/Images/successtick.png" alt="Success Image" style="width: 150px; height: 150px;"/>

        <h1>Registration Successful!</h1>
        <p>Thank you for registering. Your Voter ID is:</p>
        
        <h2>Voter ID: <%= voterId %></h2>
        
		<p>Note down your voter id for future use</p>
        
        <a href="voterLogin.jsp" class="login-btn">Login</a>
    </div>

</body>
</html>
