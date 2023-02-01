package repository;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetMapper<T> {

    public T map(ResultSet rs, int row) throws SQLException;

}
