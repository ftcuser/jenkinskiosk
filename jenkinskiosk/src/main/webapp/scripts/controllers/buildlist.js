angular.module('EchoWebAppModule').controller('buildlistController', function($rootScope, $scope, $location, $http) {
	    
		$http.get("servlet/buildhistory/" + $rootScope.projectId + "/" + $rootScope.jobName)
			.then(function(response){
				$scope.builds = response.data;
			});
		
		
		$scope.getBuildDetail = function(buildNumber) {
		
			$rootScope.buildNumber = buildNumber;
			
			$location.path('/buildinfo');
			$location.replace();
	  };
	  
	  
});

