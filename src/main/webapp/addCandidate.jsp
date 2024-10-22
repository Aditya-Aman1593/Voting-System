<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Candidate</title>
    <link rel="stylesheet" href="components/addCandidate.css">
</head>
<body>

<button class="Main-menu" onclick="location.href='candidates?action=list' ">Previous</button>
    <div class="container">
        <h2>Add Candidate</h2>
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
                    <input type="hidden" name="action" value="add">
                    <label for="fullName">Full Name</label>
                    <input type="text" name="fullName" id="fullName" placeholder="Enter full name of candidate" required>
                    
                    <label for="dob">Date of Birth</label>
                    <input type="date" name="dob" id="dob" placeholder="Date of Birth" required>

                    

					 <label for="nationality">Nationality</label>
                    <select id="nationality" name="nationality" required>
                        <option value="">Select your nationality</option>
                        <option value="India" >India</option>
                       
                    </select>
						
                    <label for="partyName">Party Name</label>
                    <input type="text" name="partyName" id="partyName" placeholder="Enter the party name" required>

                    <label for="partyLogo">Party Logo</label>
                    <input type="file" name="partyLogo" id="partyLogo" required >   
                </div>
            </div>
            <button type="submit">Add Candidate</button>
        </form>
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
