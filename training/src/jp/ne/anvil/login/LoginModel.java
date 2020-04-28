package jp.ne.anvil.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * ログイン処理のモデル
 * @author k.nakamura
 * @version 1.0
 */
public class LoginModel {

    /**
    * コンストラクタ
    */
    public LoginModel() {
        super();
    }

    /**
     * ログイン情報を取得
     * @param id ID
     * @param pass パスワード
     * @throws Exception
     */
    public LoginBean getUserInfo(String pId, String pPassword) throws Exception {

        LoginBean bean = new LoginBean();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            InitialContext context = new InitialContext();
            DataSource ds = (DataSource)context.lookup("java:comp/env/jdbc/oracle");

            conn = ds.getConnection();

            StringBuilder sb = new StringBuilder()
                    .append(" SELECT")
                    .append("     COUNT(*) AS record_cnt")
                    .append(" FROM")
                    .append("     m_login_user")
                    .append(" WHERE  ")
                    .append("     id = ? ")
                    .append(" AND password = ?")
                    ;

            ps = conn.prepareStatement(sb.toString());

            ps.setString(1, pId);
            ps.setString(2, pPassword);

            rs = ps.executeQuery();

            int cnt = 0;
            while(rs.next()) {
                cnt = rs.getInt("record_cnt");
                break;
            }

            if (cnt <= 0) {
                bean.setResult(false);
                bean.setErrorMessage("ログイン失敗");
            }else {
                bean.setResult(true);
                bean.setErrorMessage(null);
                bean.setId(pId);
            }

        }catch(SQLException e) {
            throw e;
        }finally{
            try {
                if (rs != null) {rs.close();}
                if (ps != null) {ps.close();}
                if (conn != null) {conn.close();}
            }catch(SQLException e) {
                throw e;
            }
        }

        return bean;
    }
}
