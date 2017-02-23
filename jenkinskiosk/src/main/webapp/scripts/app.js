angular.module("EchoWebAppModule", ["ngRoute", "ngAnimate", "ngSanitize", "mgcrea.ngStrap", "datatables"])
	.config(['$routeProvider', function($routeProvider){	
		$routeProvider
			.when("/home", {
				templateUrl: "views/home.html",
				controller: "homeController"
			})
			.when("/nodes", {
				templateUrl: "views/jenkinsnodes.html",
				controller: "jenkinsNodesController"
			})	
			.when("/activenodes", {
				templateUrl: "views/jenkinsactivenodes.html",
				controller: "jenkinsActiveNodesController"
			})	
			.when("/jenkins", {
				templateUrl: "views/jenkinshome.html",
				controller: "jenkinsHomeController"
			})
			.when("/views", {
				templateUrl: "views/jenkinsviews.html",
				controller: "jenkinsViewController"
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
				redirectTo: "/nodes"
			});
	}]);

