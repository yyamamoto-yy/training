package jp.ne.anvil.master.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;

/**
 * ユーザー削除のモデル
 * @author y.yamamoto
 * @version 1.0
 */
public class UserDeleteModel {


    /**
    * コンストラクタ
    */
    public UserDeleteModel() {
        super();
    }

    /**
     * ユーザーを追加
     * @param pEditBean 削除するID
     * @throws Exception
     */
    public UserEditBean deleteUser(UserEditBean pEditBean ) throws Exception {

        UserEditBean bean = new UserEditBean();

        Connection conn = null;
        PreparedStatement ps = null;
        int i = 0;

        try {
            InitialContext context = new InitialContext();
            DataSource ds = (DataSource)context.lookup("java:comp/env/jdbc/oracle");

            conn = ds.getConnection();
            conn.setAutoCommit(false);

            // Trimする
            String deleteId = StringUtils.trim(pEditBean.getDeleteId());

            StringBuilder sb = new StringBuilder()
                    .append("DELETE FROM ")
                    .append("m_login_user")
                    .append(" WHERE ")
                    .append(" id = ? ")
                    ;
            ps = conn.prepareStatement(sb.toString());

            ps.setString(1, deleteId );

            i = ps.executeUpdate();

            //コミット
            conn.commit();

        }catch(SQLException e) {
            conn.rollback();       //ロールバックする
            throw e;
        }finally{
            try {

                if (ps != null) {ps.close();}
                if (conn != null) {conn.close();}
            }catch(SQLException e) {
                throw e;
            }
        }

        return bean;
    }
}
