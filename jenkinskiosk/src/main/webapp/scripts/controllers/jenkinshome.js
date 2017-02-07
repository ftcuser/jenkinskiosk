angular.module('EchoWebAppModule').controller('jenkinsHomeController', function($scope, $http) {
	
	
	  $http.get("servlet/listnodes")
		.then(function(response){
			$scope.nodes = response.data;
			console.log(response.data);
		});
});


