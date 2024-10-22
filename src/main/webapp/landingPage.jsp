<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Landing Page</title>
    <link rel="stylesheet" href="components/landingPage.css">
    
</head>
<body>
     <%@include file="components/navbar.jsp" %> 
<marquee style="background-color:yellow; color:red"> Welcome to the official Online Voting System! Make sure to complete your registration before the deadline to be eligible to vote in the upcoming elections. Remember, every vote counts! Our secure and user-friendly platform ensures that your vote remains confidential and protected. Verify your identity using the OTP sent to your registered phone number and follow the simple instructions to cast your vote from anywhere, at any time. Stay informed about the candidates, review your vote carefully, and make your voice heard. Voting is your right and responsibility—exercise it wisely!
</marquee>
<h2 id ="QuickLink">Quick Links</h2>
  <div class="button-container">
        <div class="button-box">
            <img src="components/Images/registervoter.png" alt="registervoter image" class="button-image">
            <h3>Voter Registration</h3>
            
            <button onclick="location.href='voterRegister.jsp' " class="styled-button">Register Voter</button>
        </div>
        <div class="button-box">
            <img src="components/Images/loginimage.webp" alt="Image 2" class="button-image">
            <h3>Voter Login</h3>
            <button onclick="location.href='voterLogin.jsp' " class="styled-button">Login</button>
        </div>
        <div class="button-box">
            <img src="components/Images/adminloginiamge.jpg" alt="Image 3" class="button-image">
            <h3>Admin Login</h3>
            <button onclick="location.href='adminLogin.jsp' " class="styled-button">Login</button>
        </div>
        <div class="button-box">
            <img src="components/Images/leaderboardimage.jpg" alt="Image 4" class="button-image">
             <h3>LeaderBoard</h3>
            <form action="candidates" method="get">
    <input type="hidden" name="action" value="listParties">
    <button type="submit" class="styled-button">View Party Details</button>
</form>
        </div>
    </div>
    
 

    <section class="faq-section" id ="FAQ">
        <h2>Frequently Asked Questions</h2>
        <div class="faq-item">
            <div class="faq-question">1. Is the online voting system secure?</div>
            <div class="faq-answer">Yes, our online voting system uses advanced encryption and authentication methods to ensure that your vote is secure and cannot be tampered with. Additionally, we regularly monitor and update our security protocols.</div>
        </div>
        <div class="faq-item">
            <div class="faq-question">2. What happens if my internet connection drops while I’m voting?</div>
            <div class="faq-answer">If your internet connection drops, you will be able to resume voting once the connection is restored, provided you haven’t submitted your vote yet. Your vote will not be counted until you click the Submit button.</div>
        </div>
        <div class="faq-item">
            <div class="faq-question">3. What devices can I use to vote online?</div>
            <div class="faq-answer">You can vote using any device with an internet connection, including smartphones, tablets, laptops, or desktop computers. However, we recommend using a secure and updated device for a smooth voting experience.

</div>
</div>
 <div class="faq-item">
            <div class="faq-question">4. How do I register to vote online?</div>
            <div class="faq-answer">To register for online voting, go to the Register page on our website. You will need to provide details like your full name, date of birth, phone number, Aadhaar number, and nationality. Once submitted, you will receive a confirmation email or SMS with further instructions.

</div>
</div>
 <div class="faq-item">
            <div class="faq-question">5. What should I do if I encounter technical issues while voting?</div>
            <div class="faq-answer">If you experience any technical difficulties, you can contact our support team by clicking on the Help or Contact Us link on the website. Our team will assist you as soon as possible.

</div>
        </div>
    </section>

<h2 id ="Rules">Rules And Regulations</h2>

    <div class="flex-container">
     <div class="flex-item">
    <ul>
        <li>Ensure your device is secure and updated with the latest antivirus software.</li>
        <li>Use a reliable internet connection to avoid interruptions during the voting process.</li>
        <li>Verify your identity through the required secure authentication method (OTP, biometrics, etc.).</li>
        <li>Follow the instructions provided by the online voting platform.</li>
        <li>Double-check your vote before submitting it.</li>
        <li>Ensure your personal information is entered accurately.</li>
        <li>Keep your login credentials confidential.</li>
        <li>Report any technical issues immediately to the election authority.</li>
        <li>Log out from the voting system once you've cast your vote.</li>
        <li>Make sure you complete the voting process before the deadline.</li>
    </ul>
</div>




       <div class="flex-item" id="dont">
       <h3></h3>
    <ul>
        <li>Don’t share your login details or OTP with anyone.</li>
        <li>Don’t use public or unsecured Wi-Fi networks to cast your vote.</li>
        <li>Don’t vote on a device that may be infected with malware or viruses.</li>
        <li>Don’t attempt to vote multiple times or let someone else vote on your behalf.</li>
        <li>Don’t share screenshots or photos of your vote online or with others.</li>
        <li>Don’t leave your device unattended while voting.</li>
        <li>Don’t ignore any verification or authentication steps.</li>
        <li>Don’t wait until the last minute to vote in case of technical issues.</li>
        <li>Don’t trust unofficial links or emails claiming to be from the voting platform.</li>
        <li>Don’t engage in any form of fraudulent activities or manipulation of the voting system.</li>
    </ul>
</div>

    </div>


<h2 id ="Contactus">Contact Us</h2>
    <section class="contact-form-section">
        
        <form class="contact-form"  action="https://formspree.io/f/xanwryzp"
  method="POST">
            <label for="voterId">Voter Id</label>
            <input type="text" id="voterId" name="voterId" placeholder="Enter your voter id" required>
       

			<label for="subject">Subject</label>
            <input type="text" id="subject" name="subject" placeholder="Enter your query in brief" required>
            
            <label for="message">Message</label>
            <textarea id="message" name="message" placeholder="Describe your query"  required></textarea>

            <button type="submit">Submit</button>
        </form>
    </section>
   

    <!-- Footer inclusion -->
    <%@include file="components/footer.jsp" %>
    
    <script>  
    
    document.querySelectorAll('.faq-question').forEach(question => {
        question.addEventListener('click', () => {
            const answer = question.nextElementSibling;
            answer.style.display = answer.style.display === 'block' ? 'none' : 'block';
        });
    });

    
    </script>
</body>
</html>
