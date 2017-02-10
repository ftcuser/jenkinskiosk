angular.module('EchoWebAppModule').controller('dashboardController', function($rootScope, $scope, $http, $location) {
	  
	$body = $("body");

	 $body.addClass("loading");   

	  $http.get("servlet/dashbord")
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

});
