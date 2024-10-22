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
        <h2>Admin Login</h2>
       <% 
    String errorMessage = request.getParameter("error");
    if (errorMessage != null && !errorMessage.isEmpty()) {
%>
    <div style="color: red; text-align : center;">*<%= errorMessage %></div>
<% 
    } 
%>
        <form action="adminlogin" method="post">
            <div class="row">
                <div class="column">
                    <label for="voter-id">Username</label>
                    <input type="text" id="username" name="username" placeholder="Enter your username"  required>

                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" placeholder="Enter your password"  required>
                </div>
            </div>
            <p class="agreement-text">
                By logging in, you agree to our <a href="#">Terms and Conditions</a>.
            </p>
            <button type="submit">Login</button>
        </form>
         <p class="register-prompt">
           
            Are you a voter? <a href="voterLogin.jsp">Voter Login</a>
        </p>
      
    </div>
    
    
</body>
</html>
