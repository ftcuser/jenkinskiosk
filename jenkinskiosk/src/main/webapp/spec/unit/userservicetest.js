describe('User Service Unit Test', function(){
	
	var scope;
	var userService;
	
	beforeEach(function() {
        module('EchoWebAppModule');        
        inject(function(UserService) {
        	userService = UserService;
        });
    });


	it('Test Get User Function', function() { 
		var users = [
		             {
						"email" : "joe1@g.com",
						"firstName" : "joe1",
						"lastName" : "doe1",
					 },
		             {
						"email" : "joe2@g.com",
						"firstName" : "joe2",
						"lastName" : "doe2",
					 },
		             {
						"email" : "joe3@g.com",
						"firstName" : "joe3",
						"lastName" : "doe3",
					 }					 
					 
					];
		
		var guser = userService.getUserByEmail('joe1@g.com', users);		
		expect(guser.firstName).toBe('joe1');

	});
});