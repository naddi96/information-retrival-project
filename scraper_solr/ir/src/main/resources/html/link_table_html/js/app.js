var app = angular.module('myApp', []);


app.controller("MainController",function($scope,$http) {


  $scope.data = [{ firstName:"Jayaram",lastName:"P",email:"jayaram@gmail.com",
	                   project:"javasavvy",designation:"Software Engineer",empId:"10001"},
	              {firstName:"Arjun",lastName:"D",email:"Arjun@gmail.com",
	                   project:"Sample Project",designation:"Test",empId:"10002"                 
	              } ];
			
	
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
		  $scope.empoyees.splice(index,1);
	}
	
	$scope.submitEmployee = function(){
	
		console.log("form submitted:"+angular.toJson($scope.empoyees ));
	}
	
});
