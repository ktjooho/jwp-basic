package core.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface PreparedStatementSetter {
	public void setValue(PreparedStatement pstmt) throws SQLException;
}
