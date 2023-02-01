package repository;

import db.DatabaseConnection;
import db.exception.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepositoryTemplate {

    public void executeQuery(String dsQuery) {

        PreparedStatement pstmt = null;

        try {

            Connection conn = DatabaseConnection.getConnection();
            NamedPreparedStatement namedPstmt = new NamedPreparedStatement(conn, dsQuery);

            pstmt = namedPstmt.executeQuery();

        } catch (SQLException e) {

            throw new DatabaseException(e.getMessage());

        } finally {

            DatabaseConnection.close(pstmt);

        }

    }

    public void executeQuery(String dsQuery, Map<String, Object> params) {

        PreparedStatement pstmt = null;

        try {

            Connection conn = DatabaseConnection.getConnection();
            NamedPreparedStatement namedPstmt = new NamedPreparedStatement(conn, dsQuery);

            pstmt = namedPstmt.executeQuery(params);

        } catch (SQLException e) {

            throw new DatabaseException(e.getMessage());

        } finally {

            DatabaseConnection.close(pstmt);

        }

    }

    public <T> T executeQueryForObject(String dsQuery, Map<String, Object> params, ResultSetMapper<T> resultSetMapper) {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            Connection conn = DatabaseConnection.getConnection();
            NamedPreparedStatement namedPstmt = new NamedPreparedStatement(conn, dsQuery);

            pstmt = namedPstmt.executeQueryForResultSet(params);
            rs = pstmt.getResultSet();

            return rs.next() ? resultSetMapper.map(rs, 1) : null;

        } catch (SQLException e) {

            throw new DatabaseException(e.getMessage());

        } finally {

            DatabaseConnection.close(pstmt, rs);

        }

    }

    public <T> T executeQueryForObject(String dsQuery, ResultSetMapper<T> resultSetMapper) {

        return executeQueryForObject(dsQuery, new HashMap<>(), resultSetMapper);

    }

   public <T> List<T> executeQueryForList(String dsQuery, Map<String, Object> params, ResultSetMapper<T> resultSetMapper) {

       PreparedStatement pstmt = null;
       ResultSet rs = null;

       try {

           Connection conn = DatabaseConnection.getConnection();
           NamedPreparedStatement namedPstmt = new NamedPreparedStatement(conn, dsQuery);

           pstmt = namedPstmt.executeQueryForResultSet(params);
           rs = pstmt.getResultSet();

           List<T> listT = new ArrayList<>();
           while (rs.next()) {

               listT.add(resultSetMapper.map(rs, rs.getRow()));

           }

           return listT;

       } catch (SQLException e) {

           throw new DatabaseException(e.getMessage());

       } finally {

           DatabaseConnection.close(pstmt, rs);

       }

   }

   public <T> List<T> executeQueryForList(String dsQuery, ResultSetMapper<T> resultSetMapper) {

        return executeQueryForList(dsQuery, new HashMap<>(), resultSetMapper);

   }

}