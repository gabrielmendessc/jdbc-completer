package repository;

import repository.utils.StringQueryUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class NamedPreparedStatement {

    private final Connection conn;
    private String dsQuery;

    public NamedPreparedStatement(Connection conn, String dsQuery) {

        this.conn = conn;
        this.dsQuery = dsQuery;

    }

    public PreparedStatement executeQuery() throws SQLException {

        PreparedStatement pstmt = conn.prepareStatement(dsQuery);
        pstmt.execute();

        return pstmt;

    }

    public PreparedStatement executeQuery(Map<String, Object> params) throws SQLException {

        List<ParamIndex> paramIndexList = setParamsInSQL(params);
        PreparedStatement pstmt = conn.prepareStatement(dsQuery);

        setParamsInStatement(pstmt, params, paramIndexList);

        pstmt.execute();

        return pstmt;

    }

    public PreparedStatement executeQueryForResultSet() throws SQLException {

        PreparedStatement pstmt = conn.prepareStatement(dsQuery);
        pstmt.executeQuery();

        return pstmt;

    }

    public PreparedStatement executeQueryForResultSet(Map<String, Object> params) throws SQLException {

        List<ParamIndex> paramIndexList = setParamsInSQL(params);
        PreparedStatement pstmt = conn.prepareStatement(dsQuery);

        setParamsInStatement(pstmt, params, paramIndexList);

        pstmt.executeQuery();

        return pstmt;

    }

    private void setParamsInStatement(PreparedStatement pstmt, Map<String, Object> params, List<ParamIndex> paramIndexList) throws SQLException {

        for (ParamIndex paramIndex : paramIndexList) {

            if (params.containsKey(paramIndex.getDsIndexName())) {

                pstmt.setObject(paramIndex.getNrIndexPos(), params.get(paramIndex.getDsIndexName()));

            } else {

                throw new SQLException("Couldn't find parameter for " + paramIndex.getDsIndexName());

            }

        }

    }

    private List<ParamIndex> setParamsInSQL(Map<String, Object> params) {

        List<ParamIndex> paramIndexList = new ArrayList<>();
        StringBuilder dsNewQuery = new StringBuilder();

        boolean isSingleQuote = false;
        boolean isDoubleQuote = false;

        int index = 1;
        char[] queryChars = dsQuery.toCharArray();
        for(int i = 0; i < queryChars.length; i++) {

            char c = queryChars[i];

            if (isSingleQuote) {

                if (Objects.equals(c, '\'')) isSingleQuote = false;

            } else if (isDoubleQuote) {

                if (Objects.equals(c, '\"')) isDoubleQuote = false;

            } else {

                if (Objects.equals(c, '\'')) {

                    isSingleQuote = true;

                } else if (Objects.equals(c, '\"')) {

                    isDoubleQuote = true;

                } else if (Objects.equals(c, ':') && i + 1 < queryChars.length && !StringQueryUtils.isParamEnd(queryChars[i + 1])) {

                    int j = i + 1;
                    while (j < queryChars.length && !StringQueryUtils.isParamEnd(queryChars[j])) j++;

                    paramIndexList.add(new ParamIndex(index, dsQuery.substring(i + 1, j)));

                    index++;
                    c = '?';
                    i = j - 1;

                }

            }

            dsNewQuery.append(c);

        }

        dsQuery = dsNewQuery.toString();

        return paramIndexList;

    }

    private static class ParamIndex {

        private final Integer nrIndexPos;
        private final String dsIndexName;

        public ParamIndex(Integer nrIndexPos, String dsIndexName) {
            this.nrIndexPos = nrIndexPos;
            this.dsIndexName = dsIndexName;
        }

        public Integer getNrIndexPos() {
            return nrIndexPos;
        }

        public String getDsIndexName() {
            return dsIndexName;
        }

    }

}
