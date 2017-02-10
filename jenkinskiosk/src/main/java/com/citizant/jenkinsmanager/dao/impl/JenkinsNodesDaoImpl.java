package com.citizant.jenkinsmanager.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.citizant.jenkinsmanager.dao.JenkinsNodesDao;
import com.citizant.jenkinsmanager.domain.JenkinsNode;

public class JenkinsNodesDaoImpl extends BaseDaoImpl implements JenkinsNodesDao {

	@Override
	public List<JenkinsNode> getNodes() {
		List<JenkinsNode> nodesList = new ArrayList<JenkinsNode>();
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
		@SuppressWarnings("rawtypes")
		PaginatedScanList result = mapper.scan(JenkinsNode.class, scanExpression);
		if(result!=null){
			@SuppressWarnings("rawtypes")
			Iterator<JenkinsNode> its = result.iterator();
			while(its.hasNext()){
				nodesList.add(its.next());
			}
		}
		return nodesList;
	}
}
