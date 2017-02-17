package com.citizant.jenkinsmanager.dao.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.citizant.jenkinsmanager.dao.BaseDao;

public class BaseDaoImpl implements BaseDao {
    private static Log log = LogFactory.getLog("com.citizant.kudos.dao.impl.BaseDaoImpl");

	protected AmazonDynamoDBClient dynamoDB;
	protected DynamoDBMapper mapper;
	
	public BaseDaoImpl(){
        dynamoDB = new AmazonDynamoDBClient();
        mapper = new DynamoDBMapper(dynamoDB);
        try{
        	initDatabase() ; 
        } catch (Exception e) {
        	log.error("BaseDaoImple: exception when calling initDatabase()", e);
        }
	}
	
	//Initialize DynamoDB, create tables if they are not exist
	public void initDatabase() throws Exception {
		
		try {
			
			//Create User table
            String tableName = "Jenkins_Nodes";

            // Create a table with a primary hash key named 'name', which holds a string
            CreateTableRequest createTableRequest = new CreateTableRequest().withTableName(tableName)
                .withKeySchema(new KeySchemaElement().withAttributeName("NodeID").withKeyType(KeyType.HASH))
                .withAttributeDefinitions(new AttributeDefinition().withAttributeName("NodeID").withAttributeType(ScalarAttributeType.S))
                .withProvisionedThroughput(new ProvisionedThroughput().withReadCapacityUnits(1L).withWriteCapacityUnits(1L));

            // Create table if it does not exist yet
            TableUtils.createTableIfNotExists(dynamoDB, createTableRequest);
            // wait for the table to move into ACTIVE state
            TableUtils.waitUntilActive(dynamoDB, tableName);

            // Describe our new table
            DescribeTableRequest describeTableRequest = new DescribeTableRequest().withTableName(tableName);
            TableDescription tableDescription = dynamoDB.describeTable(describeTableRequest).getTable();
            log.debug("Table Description: " + tableDescription);
            
           
        } catch (AmazonServiceException ase) {
            log.error("Caught an AmazonServiceException, which means your request made it "
                    + "to AWS, but was rejected with an error response for some reason.");
            log.error("Error Message:    " + ase.getMessage());
            log.error("HTTP Status Code: " + ase.getStatusCode());
            log.error("AWS Error Code:   " + ase.getErrorCode());
            log.error("Error Type:       " + ase.getErrorType());
            log.error("Request ID:       " + ase.getRequestId());
            log.error(ase);
        } catch (AmazonClientException ace) {
            log.error("Caught an AmazonClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with AWS, "
                    + "such as not being able to access the network.");
            log.error("Error Message: " + ace.getMessage());
            log.error(ace);
        }
		
		try {
			
			//Create User table
            String tableName = "Jenkins_value_store";

            // Create a table with a primary hash key named 'name', which holds a string
            CreateTableRequest createTableRequest = new CreateTableRequest().withTableName(tableName)
                .withKeySchema(new KeySchemaElement().withAttributeName("property").withKeyType(KeyType.HASH))
                .withAttributeDefinitions(new AttributeDefinition().withAttributeName("property").withAttributeType(ScalarAttributeType.S))
                .withProvisionedThroughput(new ProvisionedThroughput().withReadCapacityUnits(1L).withWriteCapacityUnits(1L));

            // Create table if it does not exist yet
            TableUtils.createTableIfNotExists(dynamoDB, createTableRequest);
            // wait for the table to move into ACTIVE state
            TableUtils.waitUntilActive(dynamoDB, tableName);

            // Describe our new table
            DescribeTableRequest describeTableRequest = new DescribeTableRequest().withTableName(tableName);
            TableDescription tableDescription = dynamoDB.describeTable(describeTableRequest).getTable();
            log.debug("Table Description: " + tableDescription);
            
           
        } catch (AmazonServiceException ase) {
            log.error("Caught an AmazonServiceException, which means your request made it "
                    + "to AWS, but was rejected with an error response for some reason.");
            log.error("Error Message:    " + ase.getMessage());
            log.error("HTTP Status Code: " + ase.getStatusCode());
            log.error("AWS Error Code:   " + ase.getErrorCode());
            log.error("Error Type:       " + ase.getErrorType());
            log.error("Request ID:       " + ase.getRequestId());
            log.error(ase);
        } catch (AmazonClientException ace) {
            log.error("Caught an AmazonClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with AWS, "
                    + "such as not being able to access the network.");
            log.error("Error Message: " + ace.getMessage());
            log.error(ace);
        }		
		
		
	}

	@Override
	public void save(Object o) {
		mapper.save(o);
	}

	@Override
	public void delete(Object o) {
		mapper.delete(o);
	}
}
