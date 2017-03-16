package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.jdbc.ConnectionManager;
import core.jdbc.JDBCLibrary;
import core.jdbc.JdbcTemplate;
import next.model.User;

/*
 *  sql = String createQueryForInsert() ==> INSERT INTO USER VALUES(?,?,?,?)
 *  pstmt = con.prepareStatement(sql) 
 *  setValuesForInsert(User, pstmt) ==> setValuesForQuery(User,pstmt)
 *  ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
 *  JdbcTemplate has dependency for User, it requires to remove dependency between User and 
 *  
 *  Common : making query and put the parameters.
 *  Diff : Select 
 */
public class UserDao {
	
	private JDBCLibrary jdbcLibrary;
	
	private void init() {
		jdbcLibrary = new JDBCLibrary();
	}
	
	public UserDao() {
		// TODO Auto-generated constructor stub
		init();
	}
    public void insert(User user) throws SQLException {
    	String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
    	JdbcTemplate template = new JdbcTemplate();
    	template.update(sql,  user.getUserId(), user.getPassword(),
    			user.getName(), user.getEmail());
    	 /*
    	jdbcLibrary.updateQuery(sql, user.getUserId(), user.getPassword(),
    			user.getName(), user.getEmail());
    			*/
    }

    public void update(User user) throws SQLException {
        // TODO 구현 필요함.
    	String sql = "UPDATE USERS SET name=?, password=?, email=? WHERE userId=?";
    	JdbcTemplate template = new JdbcTemplate();
    	template.update(sql, user.getName(), user.getPassword(),
    			user.getEmail(), user.getUserId());
    	/*
    	jdbcLibrary.updateQuery(sql, user.getName(), user.getPassword(),
    			user.getEmail(), user.getUserId());
    			*/
    }

    public List<User> findAll() throws SQLException {
    	String sql = "SELECT * FROM USERS";
    	JdbcTemplate template = new JdbcTemplate();
    	
    	//ResultSet에 대한 처리를 DAO한테 넘겼다. 
    	
    	ArrayList<User> userList = (ArrayList<User>)template.query(sql, (ResultSet rs)->{
    		return new User(rs.getString("userId"), rs.getString("password"), 
    				rs.getString("name"), rs.getString("email"));
    	});    			

    	return userList;
    }

    public User findByUserId(String userId) throws SQLException {
        String sql = "SELECT userId, password, name, email FROM USERS WHERE userId=?";
        JdbcTemplate template = new JdbcTemplate();
        
        User user = template.queryForObject(sql, (ResultSet rs)->{
        	return new User(rs.getString("userId"), rs.getString("password"), 
    				rs.getString("name"), rs.getString("email"));
        }, userId);
        
        /*
        ResultSet rs = jdbcLibrary.executeQuery(sql, userId);
        
        User user = null;
        if (rs.next()) {
            user = new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                    rs.getString("email"));
        }
        */
        return user;
    }
}	
