package com.privania.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

import com.privania.utils.Constants;

@Configuration
public class DataConfig {
	
	@Bean(name="dataSource")
    public DataSource dataSource() {
		JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
		return dataSourceLookup.getDataSource(Constants.DATA_SOURCE_NAME);
    }
	
	@Bean
	public JdbcTemplate getJdbcTemplate() {
		JdbcTemplate jdbcTemplate =new JdbcTemplate(dataSource());
		return jdbcTemplate;
	}
}
