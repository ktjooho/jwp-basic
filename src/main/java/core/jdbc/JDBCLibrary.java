package core.jdbc;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import next.support.context.ContextLoaderListener;

/***
 * 1. 사용자가 SQL쿼리작성, 파라미터, 데이터추출 3가지만 집중할 수 있게, 나머지 역할을 해주는 라이브러리이다.
 *    
 * @author nokdu
 *
 */
public class JDBCLibrary extends RuntimeException {
	private static final Logger log = LoggerFactory.getLogger(JDBCLibrary.class);
	private Connection con=null;
	private PreparedStatement pstmt=null;
	
	private void createParamterizedSql(String sql, String ...params ) throws SQLException {
		pstmt = con.prepareStatement(sql);
		
		
		
		log.debug("Params Length :" + params.length);
		for(int paramIndex=1; paramIndex<=params.length; ++paramIndex) {
			if(params[paramIndex-1].isEmpty())
				continue;
			pstmt.setString(paramIndex, params[paramIndex-1]);
		}
	}
	public void updateQuery(String sql, String ...params ) throws SQLException {
		openConnection();

		createParamterizedSql(sql,params);
		
		pstmt.executeUpdate();

		closeConnection();
	}
	public ResultSet executeQuery(String sql, String ...params) throws SQLException {
		openConnection();
		
		createParamterizedSql(sql, params);

		ResultSet rs = pstmt.executeQuery();
		
		//closeConnection();
		
		return rs;
	}
	/*
	public Object executeQuery(String sql, String ...params) throws SQLException {
		
		openConnection();
		
		pstmt = con.prepareStatement(sql);
		
		for(int paramIndex=1; paramIndex<=params.length; ++paramIndex) {
			pstmt.setString(paramIndex, params[paramIndex]);
		}
		
		rs = pstmt.executeQuery();
		
		ResultSetMetaData rsmd = rs.getMetaData();
		
		int columnCount = rsmd.getColumnCount();
		
		if(params.length < columnCount) {
			//Fully 
			
		} else {
			//Sub
			
		}
		
		closeConnection();
		return null;
	}
	*/
	private void closeConnection() throws SQLException {
		if(pstmt != null) {
			pstmt.close();
			pstmt = null;
		}
		if(con != null) {
			con.close();
			con = null;
		}
		/*
		if(rs != null) {
			rs.close();
			rs = null;
		}
		*/
	}
	private void openConnection() {
		con = ConnectionManager.getConnection();
		pstmt = null;
	}
}