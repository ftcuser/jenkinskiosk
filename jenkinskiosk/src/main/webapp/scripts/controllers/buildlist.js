angular.module('EchoWebAppModule').controller('buildlistController', function($rootScope, $scope, $http) {
	    
		$http.get("servlet/buildhistory/" + $rootScope.projectId + "/" + $rootScope.jobName)
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

