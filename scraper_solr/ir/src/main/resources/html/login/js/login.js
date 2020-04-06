
/*function loginUser(){
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
*/

var app = angular.module('myApp', []);



app.controller("MainController",function($scope,$http) {

	$scope.login = function(){
	    console.log("dddddddddddddddddddddd")
		//inviare nel database la nuova riga con edit
        user=document.getElementById("user1").value
        pass=document.getElementById("pass1").value
		data = "{\"username\":"+user+",\"password\":"+pass+"}";
		//console.log("form submitted:"+angular.toJson($scope.empoyees[$scope.empoyees.length -1] ));
		$http.post('http://localhost:4567/login',
			data,null).then
				(function successCallback(response){
					if (response.data.autentication =='fail'){
						alert('password non corretta')
					}
					if (response.data.autentication =='ok'){

                        location.href='/link_table_html/'
					}
					return
				},
				function errorCallback(){
					alert('errore di comunicazione')
					return
				});

	}



});
