angular.module('EchoWebAppModule').controller('jenkinsNodesController', function($rootScope, $scope, $http, $location, $interval) {
	
	  $scope.editMode = false;
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
	  
	  $scope.addJenkinsNode = function() {
			$scope.node = {};
			$scope.node.id = '';
			$scope.node.projectName = '';
			$scope.node.description = '';
			$scope.node.serverUrl = '';
			$scope.node.username = '';
			$scope.node.password = '';
			$scope.editMode = true;
			$scope.actionLabel = "Add";
	  };
	  
	  $scope.cancelUpdate = function() {
			$scope.editMode = false;
	  };
	  
	  $scope.updateNode = function() {
			 $http.post("servlet/addNode", $scope.node)
			 	.then(function(success){
			 		$scope.nodes = success.data;
			 	});
			 $scope.editMode = false;
	  };

});
