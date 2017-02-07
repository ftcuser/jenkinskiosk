angular.module("EchoWebAppModule").factory('UserService', function(){
	return {
		getUserByEmail : function(email, users) {
			var user;
			for (i = 0; i < users.length; i++) {
				if(users[i].email == email){
					user = users[i];					 
					break;
				}
			}	
			return user;
		},
		
		getUserDataObj : function(user) {			
		    var dataObj = {
					   firstName : user.firstName,
					   lastName : user.lastName,
					   email : user.email
		    };			    
		    return dataObj;
		}	
	}
});