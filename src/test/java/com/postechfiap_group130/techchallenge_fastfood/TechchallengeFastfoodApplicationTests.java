package com.postechfiap_group130.techchallenge_fastfood;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
        "aws.dynamodb.endpoint=http://localhost:8000",
        "server.port=0"  // Random port for testing
})
class TechchallengeFastfoodApplicationTests {

	@Test
	void contextLoads() {
	}

}
