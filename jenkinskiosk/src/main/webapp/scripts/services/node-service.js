angular.module('EchoWebAppModule').factory('NodeService', function(){
	return {
		getNodeById : function(nodeId, nodes) {
			var node;
			for (i = 0; i < nodes.length; i++) {
				if(nodes[i].id == nodeId){
					node = nodes[i];					 
					break;
				}
			}	
			return node;
		}
	}
});