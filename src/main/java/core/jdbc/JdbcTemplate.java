package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdbcTemplate{
	
	private static final Logger log = LoggerFactory.getLogger(JDBCLibrary.class);
	
	public void update(String sql, Object ...params)  {
		update(sql, createPreparedStatementSetter(params));
	}

	public void update(String sql, PreparedStatementSetter pss) throws DataAccessException {
		try (Connection con = ConnectionManager.getConnection();
		PreparedStatement pstmt = con.prepareStatement(sql)) {
			pss.setValue(pstmt);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}

	public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object ...params) {
		return query(sql, rowMapper, createPreparedStatementSetter(params));
	}
	
	public <T> List<T> query(String sql, RowMapper<T> rowMapper, PreparedStatementSetter pss) throws DataAccessException {
		ResultSet rs = null;
		try (Connection con = ConnectionManager.getConnection();
		PreparedStatement pstmt = con.prepareStatement(sql)) {
			
			pss.setValue(pstmt);
			rs = pstmt.executeQuery();
			
			List<T> list = new ArrayList<T>();
			while(rs.next()) {
				T object = rowMapper.mapRow(rs);
				list.add(object);
			}
			return list;
		} catch (SQLException e) {
			// TODO: handle exception
			throw new DataAccessException(e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				throw new DataAccessException(e);
			}
		}
	}
	
	public <T> T queryForObject(String sql, RowMapper<T> rowMapper,PreparedStatementSetter pss) {
		List<T> result = query(sql, rowMapper, pss);
		if(result.isEmpty()) {
			return null;
		}
		return result.get(0);
	}
	
	public <T> T queryForObject(String sql, RowMapper<T> rowMapper, Object ...params) {
		return queryForObject(sql, rowMapper, createPreparedStatementSetter(params));
	}
	
	private PreparedStatementSetter createPreparedStatementSetter(Object...params) { 
		return new PreparedStatementSetter() { //Anonymous class.
			@Override
			public void setValue(PreparedStatement pstmt) throws SQLException {
				// TODO Auto-generated method stub
				for(int i=0; i<params.length; ++i) {
					pstmt.setObject(i+1, params[i]);
				}
			}
		};
	}

	@Deprecated
	private PreparedStatement generatePstmt(Connection con, String sql, Object ...params) throws SQLException {
		PreparedStatement pstmt=null;
		pstmt = con.prepareStatement(sql);
		for(int paramIndex=1; paramIndex<=params.length; ++paramIndex) {
			pstmt.setObject(paramIndex, params[paramIndex-1]);
		}
		return pstmt;
	}
}
