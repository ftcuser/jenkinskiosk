angular.module('EchoWebAppModule').controller('buildlistController', function($rootScope, $scope, $location, $http, DTOptionsBuilder) {
	
	if($rootScope.projectId == null) {
		$location.path('/');
		$location.replace();
	}
	
		$http.get("servlet/buildhistory/" + $rootScope.projectId + "/" + $rootScope.jobName)
			.then(function(response){
				$scope.builds = response.data;
				$scope.dtOptions = DTOptionsBuilder.newOptions()
			    .withOption('order', [0, 'desc']);
			
			});
		
		
		$scope.getBuildDetail = function(buildNumber) {
		
			$rootScope.buildNumber = buildNumber;
			
			$location.path('/buildinfo');
			$location.replace();
	  };
	  
	  
});

