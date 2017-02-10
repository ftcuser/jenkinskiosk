angular.module('EchoWebAppModule').controller('jenkinsNodesController', function($rootScope, $scope, $http, $location, $interval) {
	
	  $scope.nodeMode = true;
	  $scope.homeMode = false;
	  $scope.buildListMode = false;
	  $scope.buildMode = false;
	  $scope.actionLabel = "Add";
	  $body = $("body");

	  $body.addClass("loading");   
		 
	  $http.get("servlet/listnodes")
		.then(function(response){
			$scope.nodes = response.data;
			 $body.removeClass("loading");
	  });
	  
	  $scope.gotoJenkinsHome = function(projectId, projectName) {
		  	$rootScope.projectName = projectName;
		    $rootScope.projectId = projectId;
		    $location.path('/jenkins');
		    $location.replace();
	  };

});
