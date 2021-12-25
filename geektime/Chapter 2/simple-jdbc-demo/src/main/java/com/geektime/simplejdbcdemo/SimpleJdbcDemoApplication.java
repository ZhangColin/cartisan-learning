package com.geektime.simplejdbcdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * @author colin
 */
@SpringBootApplication
@Slf4j
public class SimpleJdbcDemoApplication implements CommandLineRunner {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    public static void main(String[] args) {
        SpringApplication.run(SimpleJdbcDemoApplication.class);
    }
    @Override
    public void run(String... args) {
        insertData();
        batchInsert();
        listData();
    }


    public SimpleJdbcInsert simpleJdbcInsert() {
        return new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("foo")
                .usingGeneratedKeyColumns("id");
    }

    public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    public void insertData() {
        asList("b", "c").forEach(bar->jdbcTemplate.update("insert into foo(bar) values(?)", bar));

        final HashMap<String, String> row = new HashMap<>();
        row.put("BAR", "d");
        final Number id = simpleJdbcInsert().executeAndReturnKey(row);

        log.info("ID of d: {}", id.longValue());
    }

    public void listData() {
        log.info("Count: {}", jdbcTemplate.queryForObject("select count(*) from foo", Long.class));

        final List<String> list = jdbcTemplate.queryForList("select bar from foo", String.class);
        list.forEach(bar->log.info("BAR: {}", bar));

        final List<Foo> fooList = jdbcTemplate.query("select * from foo", new RowMapper<Foo>() {
            @Override
            public Foo mapRow(ResultSet rs, int rowNum) throws SQLException {
                return Foo.builder().id(rs.getLong(1)).bar(rs.getString(2)).build();
            }
        });

        fooList.forEach(foo->log.info("Foo: {}", foo));
    }

    public void batchInsert() {
        jdbcTemplate.batchUpdate("insert into foo(bar) values(?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, "b-"+i);
                    }

                    @Override
                    public int getBatchSize() {
                        return 2;
                    }
                });

        List<Foo> list = new ArrayList<>();
        list.add(Foo.builder().id(100L).bar("b-100").build());
        list.add(Foo.builder().id(101L).bar("b-101").build());

        namedParameterJdbcTemplate().batchUpdate("insert into foo(id, bar) values(:id, :bar)", SqlParameterSourceUtils.createBatch(list));
    }
}
