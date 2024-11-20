package net.fullstack7.springboot;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;

import static org.assertj.core.api.Assertions.assertThat;

@Log4j2
@SpringBootTest
public class DatabaseConnectionTest {

    // @Autowired
    // private DataSource dataSource;

    // @Test
    // public void testDatabaseConnection() throws Exception {
    //     try (Connection connection = dataSource.getConnection()) {
    //         assertThat(connection).isNotNull();
    //         assertThat(connection.isValid(1)).isTrue();
    //         log.info("Connection: {}", connection);
    //     }
    // }

} 