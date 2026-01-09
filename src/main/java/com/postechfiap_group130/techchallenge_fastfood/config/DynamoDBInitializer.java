package com.postechfiap_group130.techchallenge_fastfood.config;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

@Configuration
public class DynamoDBInitializer {

    private static final Logger log = LoggerFactory.getLogger(DynamoDBInitializer.class);
    private static final String PAYMENTS_TABLE_NAME = "Payments";
    private static final Long READ_CAPACITY_UNITS = 5L;
    private static final Long WRITE_CAPACITY_UNITS = 5L;

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @EventListener(ContextRefreshedEvent.class)
    public void init() {
        try {
            // Verifica se a tabela já existe
            if (!tableExists(PAYMENTS_TABLE_NAME)) {
                createTable();
                log.info("Tabela {} criada com sucesso!", PAYMENTS_TABLE_NAME);
            } else {
                log.info("Tabela {} já existe.", PAYMENTS_TABLE_NAME);
            }
        } catch (Exception e) {
            log.error("Erro ao inicializar tabela do DynamoDB: {}", e.getMessage(), e);
        }
    }

    private boolean tableExists(String tableName) {
        try {
            return amazonDynamoDB.listTables().getTableNames().contains(tableName);
        } catch (Exception e) {
            log.warn("Erro ao verificar existência da tabela: {}", e.getMessage());
            return false;
        }
    }

    private void createTable() {
        try {
            CreateTableRequest request = new CreateTableRequest()
                .withTableName(PAYMENTS_TABLE_NAME)
                .withKeySchema(
                    new KeySchemaElement("id", KeyType.HASH)  // Chave de partição
                )
                .withAttributeDefinitions(
                    new AttributeDefinition("id", ScalarAttributeType.S),
                    new AttributeDefinition("orderId", ScalarAttributeType.N)
                )
                .withProvisionedThroughput(
                    new ProvisionedThroughput(READ_CAPACITY_UNITS, WRITE_CAPACITY_UNITS)
                )
                .withGlobalSecondaryIndexes(
                    new GlobalSecondaryIndex()
                        .withIndexName("orderId-index")
                        .withKeySchema(
                            new KeySchemaElement("orderId", KeyType.HASH)
                        )
                        .withProjection(new Projection().withProjectionType(ProjectionType.ALL))
                        .withProvisionedThroughput(new ProvisionedThroughput(READ_CAPACITY_UNITS, WRITE_CAPACITY_UNITS))
                );

            amazonDynamoDB.createTable(request);
            
            // Aguarda a tabela ficar ativa
            amazonDynamoDB.describeTable(PAYMENTS_TABLE_NAME).getTable();
            log.info("Tabela {} criada com sucesso!", PAYMENTS_TABLE_NAME);
            
        } catch (Exception e) {
            log.error("Não foi possível criar a tabela: {}", e.getMessage());
            throw new RuntimeException("Erro ao criar tabela no DynamoDB", e);
        }
    }
}
