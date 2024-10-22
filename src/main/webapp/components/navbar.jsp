<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title></title>
	<link rel="stylesheet" href="components/style.css">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css"/>
	<meta name="viewport" content="width=device-width,intial-scale=1.0">
</head>
<body>
	
	<nav>
		<input type="checkbox" id="check" name="" value="">
		<label for="check" id="checkbtn"><i class="fa fa-bars"></i></label>
		<label class="logo">Vote India</label>
		<ul>
			
			 <% 
                String voter = (String) session.getAttribute("voter");
                String admin = (String) session.getAttribute("admin");
                
                if (voter != null || admin != null) {
            %>
                <li><a href="logout" class="log">Logout</a></li>
            <% 
                } else {
            %>
                <li><a href="#QuickLink">Quick Links</a></li>
			<li><a href="#FAQ">FAQs</a></li>
			<li><a href="#Rules">Rules</a></li>
                <li><a href="#Contactus" id="ctsbtn">Contact Us</a></li>
            <% 
                } 
            %>
		

		</ul>
		
	</nav>

	<div class="slideshow-container">

		<div class="mySlides fade">
            <div class="numbertext">1 / 3</div>
            <img src="components/Images/image-1080x400 (1).jpg" style="width:100%">
            <div class="text">Vote for Development</div>
        </div>
        
        <div class="mySlides fade">
            <div class="numbertext">2 / 3</div>
            <img src="components/Images/vote-word-letters-scrabble.jpg" style="width:100%">
            <div class="text">Vote for Nation</div>
        </div>
        
        <div class="mySlides fade">
            <div class="numbertext">3 / 3</div>
            <img src="components/Images/photo-1604420022249-87e637722439.avif" style="width:100%">
            <div class="text">Vote for Future</div>
        </div>
		
		</div>
		<br>
		
		<div style="text-align:center">
		  <span class="dot"></span> 
		  <span class="dot"></span> 
		  <span class="dot"></span> 
		</div>
		

		<script>
			let slideIndex = 0;
			showSlides();
			
			function showSlides() {
			  let i;
			  let slides = document.getElementsByClassName("mySlides");
			  let dots = document.getElementsByClassName("dot");
			  for (i = 0; i < slides.length; i++) {
				slides[i].style.display = "none";  
			  }
			  slideIndex++;
			  if (slideIndex > slides.length) {slideIndex = 1}    
			  for (i = 0; i < dots.length; i++) {
				dots[i].className = dots[i].className.replace(" active", "");
			  }
			  slides[slideIndex-1].style.display = "block";  
			  dots[slideIndex-1].className += " active";
			  setTimeout(showSlides, 10000);
			}
			</script>
</body>
</html>