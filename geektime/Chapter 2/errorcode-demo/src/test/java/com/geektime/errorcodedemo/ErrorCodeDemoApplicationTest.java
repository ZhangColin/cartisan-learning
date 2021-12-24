package com.geektime.errorcodedemo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * @author colin
 */
@SpringBootTest
@Slf4j
public class ErrorCodeDemoApplicationTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testThrowingCustomException() {
        final CustomDuplicatedKeyException customDuplicatedKeyException = assertThrows(CustomDuplicatedKeyException.class, () -> {
            jdbcTemplate.execute("insert into foo(id, bar) values(1, 'a')");
            jdbcTemplate.execute("insert into foo(id, bar) values(1, 'a')");
        });

        log.error(customDuplicatedKeyException.getMessage());
    }
}
