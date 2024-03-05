package com.hackathon.Events.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.hackathon.Events.models.Blog;
import com.hackathon.Events.models.UserDetails;
import com.hackathon.Events.utilities.Constants;

@Service
public class MainService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Blog> searchByUser(String content, String userId) {
		String query = "SELECT * FROM blog WHERE content like ? OR soundex(content) = soundex(?) OR "
				+ "MATCH(content, name) AGAINST(? IN NATURAL language mode) AND STATUS=? AND USER_ID=?;";
		List<Blog> l = jdbcTemplate.query(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				// TODO Auto-generated method stub
				ps.setString(1, "%"+content+"%");
				for(int i=2;i<=3;i++) {
					ps.setString(i, content);
				}
				ps.setString(4, Constants.YES);
				ps.setString(5, userId);
			}
		}, new BlogMapper());
		return l;
	}
	
	public List<Blog> search(String content){
		String query = "SELECT * FROM blog WHERE content like ? OR soundex(content) = soundex(?) OR "
				+ "MATCH(content, name) AGAINST(? IN NATURAL language mode) AND STATUS=?;";
		List<Blog> l = jdbcTemplate.query(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				// TODO Auto-generated method stub
				ps.setString(1, "%"+content+"%");
				for(int i=2;i<=3;i++) {
					ps.setString(i, content);
				}
				ps.setString(4, Constants.YES);
			}
		}, new BlogMapper());
		return l;
	}
}

class BlogMapper implements RowMapper<Blog> {

	@Override
	public Blog mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		Blog b = new Blog();
		b.setId(Long.parseLong(rs.getString("ID")));
		b.setContent(rs.getString("CONTENT"));
		b.setModifiedAt(rs.getTimestamp("MODIFIED_AT"));
		b.setName(rs.getString("NAME"));
		b.setScheduledAt(rs.getTimestamp("SCHEDULED_AT"));
		b.setStatus(rs.getString("STATUS"));
		UserDetails u = new UserDetails();
		u.setUserId(Long.parseLong(rs.getString("USER_ID")));
		b.setUserDetails(u);
		b.setUrl(rs.getString("URL"));
		return b;
	}
}
