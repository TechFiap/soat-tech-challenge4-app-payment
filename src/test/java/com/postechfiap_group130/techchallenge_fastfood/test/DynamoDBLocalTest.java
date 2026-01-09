package com.postechfiap_group130.techchallenge_fastfood.test;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

//@SpringBootTest
//@ActiveProfiles("test")
//public class DynamoDBLocalTest {
//
//    private static final Logger log = LoggerFactory.getLogger(DynamoDBLocalTest.class);
//    private static final String TEST_TABLE = "TestTable";
//
//    @Autowired
//    private AmazonDynamoDB amazonDynamoDB;
//
//    @Test
//    public void testDynamoDBLocalConnection() {
//        try {
//            // 1. Listar tabelas existentes
//            List<String> tables = amazonDynamoDB.listTables().getTableNames();
//            log.info("Tabelas existentes: {}", tables);
//
//            // 2. Criar uma tabela de teste
//            if (!tables.contains(TEST_TABLE)) {
//                createTestTable();
//                log.info("Tabela de teste criada com sucesso!");
//            }
//
//            // 3. Listar tabelas novamente para confirmar
//            tables = amazonDynamoDB.listTables().getTableNames();
//            log.info("Tabelas após criação: {}", tables);
//
//            // 4. Descrever a tabela
//            DescribeTableResult tableInfo = amazonDynamoDB.describeTable(TEST_TABLE);
//            log.info("Informações da tabela: {}", tableInfo.getTable());
//
//        } catch (Exception e) {
//            log.error("Erro ao testar conexão com DynamoDB Local: {}", e.getMessage(), e);
//            throw e;
//        }
//    }
//
//    private void createTestTable() {
//        CreateTableRequest request = new CreateTableRequest()
//                .withTableName(TEST_TABLE)
//                .withKeySchema(new KeySchemaElement("id", KeyType.HASH))
//                .withAttributeDefinitions(
//                        new AttributeDefinition("id", ScalarAttributeType.S)
//                )
//                .withProvisionedThroughput(
//                        new ProvisionedThroughput(5L, 5L)
//                );
//
//        amazonDynamoDB.createTable(request);
//    }
//}