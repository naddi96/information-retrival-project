var app = angular.module('myApp', []);


app.controller("MainController",function($scope,$http) {


			
	
	$http({
		method : "GET",
		url : "http://localhost:4567/get_link_table"
	}).then(function mySuccess(response) {

		$scope.empoyees = angular.copy( response.data);
		$scope.enabledEdit =[];
	
		console.log(response);
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
	$scope.deleteEmployee = function(index) {
		//cancellare nel database la riga con edit
		$scope.empoyees.splice(index,1);
	}
	
	$scope.submitEmployee = function(){
		//inviare nel database la nuova riga con edit 
		console.log("form submitted:"+angular.toJson($scope.empoyees ));
	}
	
});
