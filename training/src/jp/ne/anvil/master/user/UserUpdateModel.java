package jp.ne.anvil.master.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;

/**
 * ユーザパスワード変更モデル
 * @author y.yamamoto
 * @version 1.0
 */
public class UserUpdateModel {


    /**
    * コンストラクタ
    */
    public UserUpdateModel() {
        super();
    }

    /**
     * ユーザーパスワードを変更
     * @param pEditBean 変更するID,PASS
     * @throws Exception
     */
    public UserEditBean updateUser(UserEditBean pEditBean ) throws Exception {

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
            String updateId = StringUtils.trim(pEditBean.getUpdateId());
            String updatePass = StringUtils.trim(pEditBean.getUpdatePass());

            StringBuilder sb = new StringBuilder()
                    .append(" UPDATE ")
                    .append(" m_login_user ")
                    .append(" SET ")
                    .append(" password = ? ")
                    .append(" WHERE ")
                    .append(" id = ? ")
                    ;
            ps = conn.prepareStatement(sb.toString());

            int prmCnt = 1;
            // パスワード
            if ( !StringUtils.isEmpty(updatePass) ){
                ps.setString(prmCnt, updatePass );
                prmCnt++;
            }
            // ユーザ名
            if ( !StringUtils.isEmpty(updateId) ){
                ps.setString(prmCnt, updateId );
                prmCnt++;
            }

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
