package com.geektime.datasourcedemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootApplication
@Slf4j
public class DataSourceDemoApplication implements CommandLineRunner {
	@Autowired
	private DataSource dataSource;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {
		SpringApplication.run(DataSourceDemoApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		showConnection();
		showData();
	}

	private void showConnection() throws SQLException {
		log.info(dataSource.toString());
		final Connection connection = dataSource.getConnection();
		log.info(connection.toString());
		connection.close();
	}

	private void showData() {
		jdbcTemplate.queryForList("select * from foo")
				.forEach(row -> log.info(row.toString()));
	}
}
