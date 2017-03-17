package next.dao;

import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

import core.jdbc.JdbcTemplate;
import core.jdbc.RowMapper;
import next.model.Answer;
import next.model.Question;

public class AnswerDao {
	
	/*
	 * CREATE TABLE ANSWERS (
	answerId 			bigint				auto_increment,
	writer				varchar(30)			NOT NULL,
	contents			varchar(5000)		NOT NULL,
	createdDate			timestamp			NOT NULL,
	questionId			bigint				NOT NULL,				
	PRIMARY KEY         (answerId)
);
		
	 * 
	 */
	//public Answer(int answerId, String writer, String contents, Date createdDate, int questionId) {
	public List<Answer> findByQuestionId(String questionId) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		String sql = "SELECT * FROM ANSWERS WHERE questionId=?";
		return jdbcTemplate.query(sql, (ResultSet rs)->{
			return new Answer(rs.getInt("answerId"), rs.getString("writer"), 
					rs.getString("contents"), rs.getDate("createdDate"),
					rs.getInt("questionId"));
		}, questionId);
	}
	
}
