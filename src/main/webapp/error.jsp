<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <h1>Error</h1>
    
    <%
        // Retrieve the error message from the query string
        String errorMessage = request.getParameter("message");

        // Display the error message if available
        if (errorMessage != null && !errorMessage.isEmpty()) {
    %>
        <p style="color: red; font-weight: bold;"><%= errorMessage %></p>
    <%
        } else {
    %>
        <p style="color: red; font-weight: bold;">An unknown error occurred. Please try again.</p>
    <%
        }
    %>


    
</body>
</html>
