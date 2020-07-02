
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

function query(){
	console.log("query");
	window.open("./../query/query.html",'_self');
	
}

var app = angular.module('myApp', []);



app.controller("MainController",function($scope,$http) {

$http({
		method : "GET",
		url : "/login"
	}).then(function mySuccess(response) {

        console.log("ok")
		}, function myError(response) {
		console.log("err")


});

	$scope.login = function(){

		//inviare nel database la nuova riga con edit
        user=document.getElementById("user1").value
        pass=document.getElementById("pass1").value
		data = "{\"username\":"+user+",\"password\":"+pass+"}";
		//console.log("form submitted:"+angular.toJson($scope.empoyees[$scope.empoyees.length -1] ));
		$http.post('/login',
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
