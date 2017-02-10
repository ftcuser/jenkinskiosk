angular.module('EchoWebAppModule').controller('jenkinsHomeController', function($rootScope, $scope, $location, $http) {
	    
		if($rootScope.projectId == null) {
			$location.path('/');
			$location.replace();
		}
	
		$http.get("servlet/jenkinshome/" + $rootScope.projectId)
		 	.then(function(response){
		 		$scope.jobs = response.data;
		});
		
		
		$scope.getBuildHistory = function(jobName) {
			$rootScope.jobName = jobName;			
			$location.path('/buildlist');
			$location.replace();
	
	  };
	  
	  $scope.startBuild = function(jobName, lastBuild) {
			$rootScope.jobName = jobName;
			$rootScope.lastBuild = lastBuild;
			$location.path('/build');
			$location.replace();
	  };
});


