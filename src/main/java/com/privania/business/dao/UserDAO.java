package com.privania.business.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.privania.business.entity.User;

@Repository
public class UserDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public User getUser(Long privadaId, String username){
		String query = "select * from USER where privada_id=? and username=?";
		RowMapper<User> mapper = new RowMapper<User>() {
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User o = new User();
				o.setPrivadaId(rs.getLong(1));
				o.setUsername(rs.getString(2));
				return o;
			}
		};		
		List<User> list = jdbcTemplate.query(query, mapper, privadaId, username);
		if(list!=null && !list.isEmpty()){
			return list.get(0);
		}
		return null;
	}

	public List<String> getUserRoles(Long privadaId, String username){
		String query = "select role from ROLE where privada_id=? and username=?";
		return jdbcTemplate.queryForList(query, String.class, privadaId, username);
	}
	
	public Boolean validateUser(Long privadaId, String username, String password){
		String query = "select count(1) from USER where privada_id=? and username=? and password=?";
		Integer c = jdbcTemplate.queryForObject(query, Integer.class, privadaId, username, password);
		if(c!=null && c>0) return true;
		return false;
	}
}
