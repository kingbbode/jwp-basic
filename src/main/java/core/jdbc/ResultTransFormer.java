package core.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by YG-MAC on 2016. 12. 26..
 */
public interface ResultTransFormer<T> {
    T execute(ResultSet resultSet) throws SQLException;
}
