angular.module('EchoWebAppModule').controller('buildController', function($rootScope, $scope, $http, $location, $interval) {
	    
	var stop;
		    
	$http.get("servlet/startbuild/" + $rootScope.projectId + "/" + $rootScope.jobName+ "/" + $rootScope.lastBuild)
		 	.then(function(response){
		 		$scope.builds = response.data;
	});
		    
	stop = $interval(function() {
		$http.get("servlet/getbuild/" + $rootScope.projectId + "/" + $rootScope.jobName + "/" + $rootScope.lastBuild)
			 	.then(function(response){
			 		$scope.runningBuild = response.data;
			 		if($scope.runningBuild.running == false) {
			 			  $scope.stopFight();
			 		}
			 	});
	          }, 2000);
	  
	  
	 $scope.stopFight = function() {
          if (angular.isDefined(stop)) {
            $interval.cancel(stop);
            stop = undefined;
          }
      };
});
