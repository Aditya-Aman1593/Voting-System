<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register</title>
    <link rel="stylesheet" href="components/reglogin.css">
</head>
<body>

<button class="Main-menu" onclick="location.href='landingPage.jsp' ">Main Menu</button>
    <div class="container">
    
      <% 
            String errorMessage = request.getParameter("error");
            String name = request.getParameter("name") != null ? request.getParameter("name") : "";
            String dob = request.getParameter("dob") != null ? request.getParameter("dob") : "";
            String phone = request.getParameter("phone") != null ? request.getParameter("phone") : "";
            String aadhaar = request.getParameter("aadhaar") != null ? request.getParameter("aadhaar") : "";
            String nationality = request.getParameter("nationality") != null ? request.getParameter("nationality") : "";
            if (errorMessage != null && !errorMessage.isEmpty()) {
        %>
            <div style="color: red; text-align : center;">*<%= errorMessage %></div>
        <% 
            } 
        %>
    
        <h2>Register</h2>
        
      
        
        <form action="register" method="post">
            <div class="row">
                <div class="column">
                    <label for="name">Name:</label>
                    <input type="text" id="name" name="name" value="<%= name %>" placeholder="Enter your name" required>

                    <label for="dob">Date of Birth:</label>
                    <input type="date" id="dob" name="dob" value="<%= dob %>" placeholder="Enter your dob" required>

                    <label for="phone">Phone Number:</label>
                    <input type="text" id="phone" name="phone" value="<%= phone %>"  placeholder="Enter your phone number" required>
                </div>
                <div class="column">
                    <label for="aadhaar">Aadhaar No:</label>
                    <input type="text" id="aadhaar" name="aadhaar" value="<%= aadhaar %>" placeholder="Enter your aadhaar number" required>

                    <label for="password">Password:</label>
                    <input type="password" id="password" name="password" placeholder="Enter your password"required>

                    <label for="nationality">Nationality:</label>
                    <select id="nationality" name="nationality" required>
                        <option value="">Select your nationality</option>
                        <option value="India" <%= "India".equals(nationality) ? "selected" : "" %>>India</option>
                        <!-- Nationalities will be dynamically populated in JS -->
                    </select>
                </div>
            </div>
            <button type="submit">Register</button>
        </form>
        
        <p class="login-prompt">
            Already have an account? <a href="voterLogin.jsp">Voter Login</a>
              | 
            Are you an admin? <a href="adminLogin.jsp">Admin Login</a>
        </p>
    </div>

    <script>
    document.addEventListener("DOMContentLoaded", function() {
        const nationalitySelect = document.getElementById("nationality");

        
        const apiURL = "https://restcountries.com/v3.1/all"; 

        fetch(apiURL)
            .then(response => response.json())
            .then(data => {
                data.forEach(country => {
                    const option = document.createElement("option");
                    option.value = country.name.common;
                    option.textContent = country.name.common;
                    nationalitySelect.appendChild(option);
                });
            })
            .catch(error => {
                console.error("Error fetching nationalities:", error);
            });
    });
    </script>

</body>
</html>
