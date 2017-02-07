describe('This is an echo service test', function() {
	
	var echoService;

	beforeEach(module('EchoWebAppModule'));
	
	beforeEach(function(){
		inject(function(EchoService) {
			echoService = EchoService;
		});
	});
	
	it('Hello world test', function(){
		expect(echoService.hello()).toBe('Hello New World');
	});
	
	it('Echo test', function(){
		expect(echoService.echo('Joe')).toBe('Hello! Joe');
	});
});
