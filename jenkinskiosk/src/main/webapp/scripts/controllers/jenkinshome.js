angular.module('EchoWebAppModule').controller('jenkinsHomeController', function($rootScope, $scope, $location, $http) {
	    
		if($rootScope.projectId == null) {
			$location.path('/');
			$location.replace();
		}
	
		$http.get("servlet/jenkinshome/" + $rootScope.projectId)
		 	.then(function(response){
		 		$scope.jobs = response.data;
		});
		
		  $http.get("servlet/nodedashbord/" + $rootScope.projectId)
			.then(function(response){
				$scope.dashboard = response.data;
				
				//The chart
				  // Bar Chart
			    Morris.Bar({
			        element: 'morris-build-chart',
			        data: $scope.dashboard.buildscs,
			        xkey: 'date',
			        ykeys: ['sucessful', 'failed'],
			        labels: ['Success', 'Failed'],
			        barRatio: 0.4,
			        xLabelAngle: 35,
			        hideHover: 'auto',
			        resize: true
			    });
			    $body.removeClass("loading");
				
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


