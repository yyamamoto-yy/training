package jp.ne.anvil.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * 社員寮の件数を取得
 * @author y.yamamoto
 * @version 1.0
 */
public class DormitoryCount {


    /**
    * コンストラクタ
    */
    public DormitoryCount() {
        super();
    }

    /**
     * 社員寮の件数を取得
     * @param
     * @throws Exception
     */
    public DormitoryInfoBean getDormitoryInfo() throws Exception {

        DormitoryInfoBean bean = new DormitoryInfoBean();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            InitialContext context = new InitialContext();
            DataSource ds = (DataSource)context.lookup("java:comp/env/jdbc/oracle");

            conn = ds.getConnection();
            conn.setAutoCommit(false);

            StringBuilder sb = new StringBuilder()

                    .append("SELECT " )
                    .append(" COUNT(*)  cnt")
                    .append(" FROM " )
                    .append(" m_dormitory " )
                    ;

            ps = conn.prepareStatement(sb.toString());

            rs = ps.executeQuery();

            rs.next();
            int a = rs.getInt("cnt");

            bean.setCount(a);
            bean.setResult(0);
            //コミット
            conn.commit();

        }catch(SQLException e) {
            conn.rollback();       //ロールバックする
            bean.setResult(1);
            bean.setError(e.toString());
        }finally{
            try {
                if (ps != null) {ps.close();}
                if (conn != null) {conn.close();}
            }catch(SQLException e) {
                bean.setResult(1);
                bean.setError(e.toString());
            }
        }

        return bean;
    }
}
