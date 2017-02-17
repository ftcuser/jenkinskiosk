package com.citizant.jenkinsmanager.domain;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

/*
 * This is a object to store some Json values
 * Used for cache statistic results
 * 
 */
@DynamoDBTable(tableName = "Jenkins_value_store")
public class ValueStore {
	private String property;
	private String json;
	
	@DynamoDBHashKey(attributeName="property")
	public String getProperty() {
		return property;
	}
	
	public void setProperty(String property) {
		this.property = property;
	}
	@DynamoDBAttribute(attributeName = "json")
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
	
	
}
