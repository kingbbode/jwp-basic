package core.jdbc;

import org.springframework.jdbc.core.PreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by YG-MAC on 2016. 12. 26..
 */
public abstract class AbstractJdbcTemplate {
    protected PreparedStatement createPreparedStatement(PreparedStatement pstmt, Object... args) throws SQLException {
        setValues(pstmt, args);
        return pstmt;
    }

    private void setValues(PreparedStatement pstmt, Object... args) throws SQLException {
        for (int i = 1; i <= args.length; i++) {
            pstmt.setObject(i, args[i - 1]);
        }
    }

    protected PreparedStatementSetter createPreparedStatementSetter(Object... args){
        return preparedStatement -> {
            for (int i = 1; i <= args.length; i++) {
                preparedStatement.setObject(i, args[i - 1]);
            }
        };
    }

    public abstract void query(String sql, Object... args) throws SQLException;

    public abstract <T> T queryForObject(String sql, ResultTransFormer<T> resultTransFormer, Object... args) throws SQLException;

    public abstract <T> List<T> queryForList(String sql, ResultTransFormer<T> resultTransFormer, Object... args) throws SQLException;

}
