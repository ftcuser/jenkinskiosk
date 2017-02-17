angular.module('EchoWebAppModule').controller('jenkinsViewController', function($rootScope, $scope, $location, $http) {
	    
		if($rootScope.projectId == null) {
			$location.path('/');
			$location.replace();
		}
	
		$http.get("servlet/jenkinsviews/" + $rootScope.projectId)
		 	.then(function(response){
		 		$scope.views = response.data;
		 		$scope.currentview = $scope.views[0];
		});
		
		$scope.switchView = function(index) {
			$scope.currentview = $scope.views[index];
		};
		
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
