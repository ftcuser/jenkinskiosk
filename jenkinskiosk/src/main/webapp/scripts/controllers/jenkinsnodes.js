angular.module('EchoWebAppModule').controller('jenkinsNodesController', function($rootScope, $scope, $http, $location, $interval, NodeService) {
	
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
		    $location.path('/views');
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
			 $http.post("servlet/updateNode", $scope.node)
			 	.then(function(success){
			 		$scope.nodes = success.data;
			 	});
			 $scope.editMode = false;
	  };
	  
	  $scope.editNode = function(nodeId) {
			console.log(nodeId);
			$scope.actionLabel = "Update";
			$scope.node = NodeService.getNodeById(nodeId, $scope.nodes);
			$scope.editMode = true;
	  };

});
