angular.module("EchoWebAppModule", ["ngRoute", "ngAnimate", "ngSanitize", "mgcrea.ngStrap"])
	.config(['$routeProvider', function($routeProvider){	
		$routeProvider
			.when("/home", {
				templateUrl: "views/dashboard.html",
				controller: "dashboardController"
			})
			.when("/nodes", {
				templateUrl: "views/jenkinsnodes.html",
				controller: "jenkinsNodesController"
			})	
			.when("/jenkins", {
				templateUrl: "views/jenkinshome.html",
				controller: "jenkinsHomeController"
			})
			.when("/buildlist", {
				templateUrl: "views/buildlist.html",
				controller: "buildlistController"
			})
			.when("/build", {
				templateUrl: "views/buildprocess.html",
				controller: "buildController"
			})
			.when("/buildinfo", {
				templateUrl: "views/buildinfo.html",
				controller: "buildinfoController"
			})
			.otherwise ({
				redirectTo: "/home"
			});
	}]);
