<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link rel="stylesheet" href="components/login.css">
</head>
<body>

<button class="Main-menu" onclick="location.href='landingPage.jsp' ">Main Menu</button>
    <div class="container">
    
        <% 
    String errorMessage = request.getParameter("error");
    if (errorMessage != null && !errorMessage.isEmpty()) {
%>
    <div style="color: red; text-align : center;">*<%= errorMessage %></div>
<% 
    } 
%>
        <h2>Voter Login</h2>
        <form action="voterlogin" method="post" >
            <div class="row">
                <div class="column">
                    <label for="voterId">Voter ID:</label>
                    <input type="text" id="voterId" name="voterId" placeholder="Enter your voter id"  required>

                    <label for="password">Password:</label>
                    <input type="password" id="password" name="password" placeholder="Enter your password" required>
                </div>
            </div>
            <p class="agreement-text">
                By logging in, you agree to our <a href="#">Terms and Conditions</a>.
            </p>
            <button type="submit">Login</button>
        </form>
       <p class="register-prompt">
            Don't have an account? <a href="voterRegister.jsp">Voter Register here</a>
            | 
            Are you an admin? <a href="adminLogin.jsp">Admin Login</a>
        </p>
    </div>
</body>
</html>
