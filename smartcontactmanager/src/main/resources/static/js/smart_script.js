const toggleSidebar=()=>{
	
	if($('.sidebar').is(":visible"))
	{
	
	$(".sidebar").css("display","none");
	$(".content").css("margin-left","0%");	
	}else{
		$(".sidebar").css("display","block");
	$(".content").css("margin-left","25%");	
	}
};

// Retrieve the necessary elements from the DOM
const firstNameInput = document.getElementById('firstName');
const lastNameInput = document.getElementById('lastName');
const emailInput = document.getElementById('email');

// Event listeners for the first and last name inputs
firstNameInput.addEventListener('input', updateEmail);
lastNameInput.addEventListener('input', updateEmail);

// Function to update the email input based on first and last name values
function updateEmail() {
  const firstName = firstNameInput.value.trim().toLowerCase();
  const lastName = lastNameInput.value.trim().toLowerCase();

  const generatedEmail = firstName+"."+lastName+"@miniorange.in"; // Replace "example.com" with your desired domain

  emailInput.value = generatedEmail;
}