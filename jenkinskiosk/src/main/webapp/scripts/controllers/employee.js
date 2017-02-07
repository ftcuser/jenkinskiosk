angular.module('EchoWebAppModule').controller('employeeController', function($scope, $http, UserService, EchoService) {
	
	  $scope.editMode = false;
	  $scope.actionLabel = "Add";
	  
	  $http.get("servlet/listusers")
		.then(function(response){
			$scope.users = response.data;
		});
	  	$scope.message = EchoService.hello();
	  	//bind JavaScript function
	  	$scope.editUser = function(email) {
			console.log(email);
			$scope.actionLabel = "Update";
			$scope.user = UserService.getUserByEmail(email, $scope.users);
			$scope.editMode = true;
		};
		
		$scope.addUser = function() {
			$scope.user = {};
			$scope.user.firstName = '';
			$scope.user.lastName = '';
			$scope.user.email = '';
			$scope.editMode = true;
			$scope.actionLabel = "Add";
		};
		
		$scope.cancelUpdate = function() {
			$scope.editMode = false;
		};
		
		$scope.updateUser = function() {
			 $scope.editMode = false;
			 $http.post("servlet/adduser", UserService.getUserDataObj($scope.user))
			 	.then(function(success){
			 		$scope.users = success.data;
			 	});
		};
		
		$scope.deleteUser = function(email) {
			var user = UserService.getUserByEmail(email, $scope.users);			
			$http.post("servlet/deleteuser", UserService.getUserDataObj(user))
				.then(function(success){
					$scope.users = success.data;
				});
			$scope.editMode = false;
		};
});
