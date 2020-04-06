
function loginUser(){
	console.log("user");

	console.log(document.getElementById("user1").value,document.getElementById("pass1").value);
	
	var pathname = window.location.pathname; 
	var newpath = "file://" + pathname + "/../index.html";
	console.log(newpath);
	window.location.replace(newpath);

}

function loginAdmin(){
	console.log("admin");
	
	console.log(document.getElementById("user2").value,document.getElementById("pass2").value);
	
	var pathname = window.location.pathname; 
	var newpath = "file://" + pathname + "/../index.html";
	console.log(newpath);
	window.location.replace(newpath);

}
