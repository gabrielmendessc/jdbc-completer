package db;

import db.exception.DatabaseException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public abstract class DatabaseConnection {

    private static Connection conn = null;

    public static Connection getConnection() {

        if (Objects.isNull(conn)) {

            try {

                conn = DriverManager.getConnection(Database.dsDbPath, Database.dsUser, Database.dsPassword);

            } catch (SQLException e) {

                throw new DatabaseException(e.getMessage());

            }

        }

        return conn;

    }

    public static void close() {

            try {

                Connection conn = getConnection();
                conn.close();

            } catch (SQLException e) {

                throw new DatabaseException(e.getMessage());

            }

    }

    public static void close(PreparedStatement pstmt) {

        if (Objects.nonNull(pstmt)) {

            try {

                pstmt.close();

            } catch (SQLException e) {

                throw new DatabaseException(e.getMessage());

            }

        }

    }

    public static void close(ResultSet rs) {

        if (Objects.nonNull(rs)) {

            try {

                rs.close();

            } catch (SQLException e) {

                throw new DatabaseException(e.getMessage());

            }

        }

    }

    public static void close(PreparedStatement pstmt, ResultSet rs) {

        close(pstmt);
        close(rs);

    }

}
