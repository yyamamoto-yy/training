package jp.ne.anvil.master.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;

/**
 * ユーザー一覧のモデル
 * @author y.yamamoto
 * @version 1.0
 */
public class UserAddModel {


    /**
    * コンストラクタ
    */
    public UserAddModel() {
        super();
    }

    /**
     * ユーザーを追加
     * @param pAddBean 追加するID,PASS
     * @throws Exception
     */
    public UserAddBean addUser(UserAddBean pAddBean ) throws Exception {

        UserAddBean bean = new UserAddBean();

        Connection conn = null;
        PreparedStatement ps = null;
        int i = 0;

        try {
            InitialContext context = new InitialContext();
            DataSource ds = (DataSource)context.lookup("java:comp/env/jdbc/oracle");

            conn = ds.getConnection();
            conn.setAutoCommit(false);

            // Trimする
            String addId = StringUtils.trim(pAddBean.getAddId());
            String addPass = StringUtils.trim(pAddBean.getAddPass());

            StringBuilder sb = new StringBuilder()
                    .append("INSERT INTO ")
                    .append("m_login_user")
                    .append(" (id,password,create_dt) ")
                    .append(" VALUES ")
                    .append("( ? ")
                    .append(", ? ")
                    .append(",sysdate)")
                    ;

            ps = conn.prepareStatement(sb.toString());

            int prmCnt = 1;
            // ユーザ名
            if ( !StringUtils.isEmpty(addId) ){
                ps.setString(prmCnt,addId );
                prmCnt++;
            }
            // パスワード
            if ( !StringUtils.isEmpty(addPass) ){
                ps.setString(prmCnt, addPass );
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
