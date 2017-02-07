angular.module('EchoWebAppModule').controller('jenkinsNodesController', function($scope, $http, $interval) {
	
	  $scope.nodeMode = true;
	  $scope.homeMode = false;
	  $scope.buildListMode = false;
	  $scope.buildMode = false;
	  $scope.actionLabel = "Add";
	  
	  $http.get("servlet/listnodes")
		.then(function(response){
			$scope.nodes = response.data;
	  });
	  
	  $scope.getJenkinsHome = function(projectId) {
		  
		    $scope.nodeMode = false;
		    $scope.homeMode = true;
		    $scope.buildListMode = false;
		    $scope.buildMode = false;
		    $scope.projectId = projectId;
			$http.get("servlet/jenkinshome/" + projectId)
			 	.then(function(response){
			 		$scope.jobs = response.data;
			});
	  };
	  
	  $scope.getBuildHistory = function(jobName) {
		    $scope.nodeMode = false;
		    $scope.homeMode = false;
		    $scope.buildListMode = true;
		    $scope.buildMode = false;
			$http.get("servlet/buildhistory/" + $scope.projectId + "/" + jobName)
			 	.then(function(response){
			 		$scope.builds = response.data;
			});
	  };
	  
	  var stop;
	  
	  $scope.startBuild = function(jobName, lastBuild) {
		    $scope.nodeMode = false;
		    $scope.homeMode = false;
		    $scope.buildListMode = false;
		    $scope.buildMode = true;
		    
			$http.get("servlet/startbuild/" + $scope.projectId + "/" + jobName+ "/" + lastBuild)
		 	.then(function(response){
		 		$scope.builds = response.data;
		 	});
		    
		    stop = $interval(function() {
		    	$http.get("servlet/getbuild/" + $scope.projectId + "/" + jobName + "/" + lastBuild)
			 	.then(function(response){
			 		$scope.runningBuild = response.data;
			 		if($scope.runningBuild.running == false) {
			 			  $scope.stopFight();
			 		}
			 	});
	          }, 2000);
	  };
	  
	  $scope.stopFight = function() {
          if (angular.isDefined(stop)) {
            $interval.cancel(stop);
            stop = undefined;
          }
      };
});
