describe('Node Service Unit Test', function(){
	
	var scope;
	var nodeService;
	
	beforeEach(function() {
        module('EchoWebAppModule');        
        inject(function(NodeService) {
        	nodeService = NodeService;
        });
    });


	it('Test Get User Function', function() { 
		var nodes = [
		             {
						"nodeId" : "123",
						"projectName" : "Project One",
						"description" : "Project One Description",
						"serverUrl" : "http://localhost:9999/jenkins",
						"username" : "admin1",
						"password" : "testPwd1"
					 },
		             {
						 "nodeId" : "456",
						"projectName" : "Project Two",
						"description" : "Project Two Description",
						"serverUrl" : "http://localhost:9998/jenkins",
						"username" : "admin2",
						"password" : "testPwd2"
					 },
		             {
						 "nodeId" : "a1b2c3",
						"projectName" : "Project Three",
						"description" : "Project Three Description",
						"serverUrl" : "http://localhost:9997/jenkins",
						"username" : "admin3",
						"password" : "testPwd3"
					 }					 
					 
					];
		
		var gNode = nodeService.getNodeById('123', nodes);		
		expect(gNode.projectName).toBe('Project One');
		gNode = nodeService.getNodeById('456', nodes);
		expect(gNode.serverUrl).toBe('http://localhost:9998/jenkins');
		gNode = nodeService.getNodeById('a1b2c3', nodes);		
		expect(gNode.username).toBe('admin3');
	});
});