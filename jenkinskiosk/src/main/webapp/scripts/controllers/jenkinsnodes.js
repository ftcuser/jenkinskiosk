angular.module('EchoWebAppModule').controller('jenkinsNodesController', function($rootScope, $scope, $http, $location, $interval) {
	
	  $scope.nodeMode = true;
	  $scope.homeMode = false;
	  $scope.buildListMode = false;
	  $scope.buildMode = false;
	  $scope.actionLabel = "Add";
	  
	  $http.get("servlet/listnodes")
		.then(function(response){
			$scope.nodes = response.data;
	  });
	  
	  $scope.gotoJenkinsHome = function(projectId, projectName) {
		  	$rootScope.projectName = projectName;
		    $rootScope.projectId = projectId;
		    $location.path('/jenkins');
		    $location.replace();
	  };

});
