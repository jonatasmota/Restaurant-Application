"use strict";

/* Navbar Toggle */

const navbar = document.querySelector("[data-navbar]");
const navbarLinks = document.querySelector("[data-nav-link]");
const menuToggleBtn = document.querySelector("[data-nav-toggle-btn]");

menuToggleBtn.addEventListener("click", function () {
  navbar.classList.toggle("active");
  this.classList.toggle("active");
});

for (let i = 0; i < navbarLinks.length; i++) {
  navbarLinks[i].addEventListener("click", function () {
    navbar.classList.toggle("active");
    menuToggleBtn.classList.toggle("active");
  });
}

/* Header Stick and back to Top */

const header = document.querySelector("[data-header]");
const backTopBtn = document.querySelector("[data-back-top-btn]");

window.addEventListener("scroll", function () {
  if (this.window.scrollY >= 100) {
    header.classList.add("active");
    backTopBtn.classList.add("active");
  } else {
    header.classList.remove("active");
    backTopBtn.classList.remove("active");
  }
});

/* Login Modal */
$(document).ready(function () {
  $("#loginModal").modal("show");
  $(function () {
    $('[data-toggle="tooltip"]').tooltip();
  });
});


/* Gallery Filter */
let filterContainer = document.querySelector(".filter-list"),
 galleryItems = document.querySelectorAll(".gallery-item");

 filterContainer.addEventListener("click", (event) =>{
   if(event.target.classList.contains("filter-btn")){
   	 // deactivate existing active 'filter-btn'
   	 filterContainer.querySelector(".active").classList.remove("active");
   	 // activate new 'filter-btn'
   	 event.target.classList.add("active");
   	 const filterValue = event.target.getAttribute("data-filter");
   	 galleryItems.forEach((item) =>{
       if(item.classList.contains(filterValue) || filterValue === 'all'){
       	item.classList.remove("hide");
       	 item.classList.add("show");
       }
       else{
       	item.classList.remove("show");
       	item.classList.add("hide");
       }
   	 });
   }
 });
 
 
 /* Gallery Image Zoom */ 
 document.querySelectorAll(".gallery-item").forEach(function (view) {
      view.addEventListener('click', function () {
        view.classList.toggle('zoom');
      });
    });