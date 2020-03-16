var app = angular.module('myApp', []);
	


app.controller("MainController",function($scope,$http) {



			
	
	$http({
		method : "GET",
		url : "http://localhost:4567/get_link_table"
	}).then(function mySuccess(response) {

		$scope.empoyees = angular.copy( response.data);
		$scope.enabledEdit =[];
	

		}, function myError(response) {
			$scope.myWelcome = response.statusText;


});




    $scope.addEmployee = function(){
		var emp ={ link:"",professore:"",materia:"",
		anno:"",disableEdit:false};
		$scope.empoyees.push(emp);
		$scope.enabledEdit[$scope.empoyees.length-1]=true;
	}
	$scope.editEmployee = function(index){
		console.log("edit index"+index);
		$scope.enabledEdit[index] = true;
	}
	$scope.deleteEmployee = function(link) {
		//cancellare nel database la riga con edit
		
		console.log(angular.toJson(link));
		
		$http.post('http://localhost:4567/delete',
			angular.toJson(link) ,null)
			.then(function successCallback(response){
				if (response.data.response =='ok'){
					alert('andata')
				}
				return
			}, 
				function errorCallback(){
					alert('errore di comunicazione')
					return
				});
	}
	
	

	
	$scope.submitEmployee = function(){
		//inviare nel database la nuova riga con edit 
		
		//console.log("form submitted:"+angular.toJson($scope.empoyees[$scope.empoyees.length -1] ));
		$http.post('http://localhost:4567/login',
			angular.toJson($scope.empoyees[$scope.empoyees.length -1] ),null).then
				(function successCallback(response){
					if (response.data.response =='db error'){
						alert('errore db')
					}
					return
				}, 
				function errorCallback(){
					alert('errore di comunicazione')
					return
				});
	
	}
	
});
