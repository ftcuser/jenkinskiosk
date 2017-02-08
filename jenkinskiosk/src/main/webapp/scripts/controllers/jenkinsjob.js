angular.module('EchoWebAppModule').controller('jobController', function($rootScope, $scope, $http) {
	    
		$http.get("servlet/buildhistory/" + $scope.projectId + "/" + jobName)
			.then(function(response){
				$scope.builds = response.data;
			});
		
		
		$scope.getBuildHistory = function(jobName) {
			$rootScope.jobName = jobName;
			
			$location.path('/job');
			$location.replace();
			$http.get("servlet/buildhistory/" + $scope.projectId + "/" + jobName)
			 	.then(function(response){
			 		$scope.builds = response.data;
			});
	  };
});

