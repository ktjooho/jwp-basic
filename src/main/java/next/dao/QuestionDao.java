package next.dao;

import java.util.Date;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

import core.jdbc.JdbcTemplate;
import core.jdbc.RowMapper;
import next.model.Question;

public class QuestionDao {
	
	public void insert(Question question) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = 
        "INSERT INTO QUESTIONS "
        + "(questionId, writer, title, contents, createdDate, countOfAnswer) VALUES"+
"(?, ?, ?, ?,CURRENT_TIMESTAMP(), 0)";
        //jdbcTemplate.update(sql, question.getQ);
        //jdbcTemplate.update(sql, user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
	}
	
	public void upate(Question question) {
		//JdbcTemplate jdbcTemplate = new JdbcTemplate();
		//String sql = "UPDATE QUESTIONS set writer = ?, title = ?, contents = ?, createdDate = CURRENT_TIMESTAMP(), countOfAnswer = ?  WHERE questionId = ?";
	}
	
	public Question findByQuestionId(String questionId) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		String sql = "SELECT * FROM QUESTIONS WHERE questionId=?";
		RowMapper<Question> rm = new RowMapper<Question>() {
			@Override
			public Question mapRow(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				return new Question(rs.getInt("questionId"), rs.getString("writer"), 
						rs.getString("title"), rs.getString("contents"), 
						rs.getTimestamp("createdDate"), rs.getInt("countOfAnswer"));
			}
		};
		return jdbcTemplate.queryForObject(sql, rm, questionId);
	}
	
	public List<Question> findAll() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		String sql = "SELECT * FROM QUESTIONS";
		RowMapper<Question> rm = new RowMapper<Question>() {
			@Override
			public Question mapRow(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				return new Question(rs.getInt("questionId"), rs.getString("writer"), 
						rs.getString("title"), rs.getString("contents"), 
						rs.getTimestamp("createdDate"), rs.getInt("countOfAnswer"));
			}
		};
		return jdbcTemplate.query(sql, rm);
	}
}
