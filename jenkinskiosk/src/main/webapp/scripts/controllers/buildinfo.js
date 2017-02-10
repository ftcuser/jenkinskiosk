angular.module('EchoWebAppModule').controller('buildinfoController', function($rootScope, $scope, $http, $location) {
		    
	if($rootScope.projectId == null) {
		$location.path('/');
		$location.replace();
	}
	
	$http.get("servlet/getbuilddetail/" + $rootScope.projectId + "/" + $rootScope.jobName + "/" + $rootScope.buildNumber)
 	.then(function(response){
 		$scope.build = response.data;
 		$scope.jobName = $rootScope.jobName;
 	});
});
