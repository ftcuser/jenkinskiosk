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
			.when("/jenkins", {
				templateUrl: "views/jenkinshome.html",
				controller: "jenkinsHomeController"
			})
			.when("/form", {
				templateUrl: "views/form.html",
				controller: "formController"
			})
			.otherwise ({
				redirectTo: "/nodes"
			});
	}]);

