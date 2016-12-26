package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by YG-MAC on 2016. 12. 26..
 */
public class JdbcTemplate extends AbstractJdbcTemplate {
    private Connection con;

    public JdbcTemplate() {
        this.con = ConnectionManager.getConnection();
    }

    @Override
    public void query(String sql, Object... args) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = createPreparedStatement(con.prepareStatement(sql), args);
            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }

            if (con != null) {
                con.close();
            }
        }
    }

    @Override
    public <T> T queryForObject(String sql, ResultTransFormer<T> resultTransFormer, Object... args) throws SQLException {
        List<T> list = queryForList(sql, resultTransFormer, args);
        if (list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public <T> List<T> queryForList(String sql, ResultTransFormer<T> resultTransFormer, Object... args) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = createPreparedStatement(con.prepareStatement(sql), args);
            List<T> result = new ArrayList<>();
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                result.add(resultTransFormer.execute(rs));
            }
            return result;
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }

            if (con != null) {
                con.close();
            }
        }
    }

}
