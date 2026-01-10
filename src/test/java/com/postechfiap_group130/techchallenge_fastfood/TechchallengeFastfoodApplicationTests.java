package com.postechfiap_group130.techchallenge_fastfood;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import java.util.Collections;

import static org.mockito.BDDMockito.given;

@SpringBootTest
@TestPropertySource(properties = {
        "aws.dynamodb.endpoint=http://localhost:8000",
        "server.port=0"
})
class TechchallengeFastfoodApplicationTests {

    @MockBean
    private AmazonDynamoDB amazonDynamoDB;

	@Test
	void contextLoads() {
        // Mock behavior to avoid connection errors in DynamoDBInitializer
        given(amazonDynamoDB.listTables()).willReturn(new ListTablesResult().withTableNames(Collections.emptyList()));
	}

}
