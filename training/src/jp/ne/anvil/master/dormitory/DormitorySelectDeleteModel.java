package jp.ne.anvil.master.dormitory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * 社員寮一括削除のモデル
 * @author y.yamamoto
 * @version 1.0
 */
public class DormitorySelectDeleteModel {


    /**
    * コンストラクタ
    */
    public DormitorySelectDeleteModel() {
        super();
    }

    /**
     * 社員寮を一括削除
     * @param pEditBean 削除するID
     * @throws Exception
     */
    public DormitorySelectDeleteBean deleteDormitory(DormitorySelectDeleteBean pDeleteBean ) throws Exception {

        DormitorySelectDeleteBean bean = new DormitorySelectDeleteBean();

        Connection conn = null;
        PreparedStatement ps = null;
        int i = 0;

        try {
            InitialContext context = new InitialContext();
            DataSource ds = (DataSource)context.lookup("java:comp/env/jdbc/oracle");

            conn = ds.getConnection();
            conn.setAutoCommit(false);

            String[] deleteId = pDeleteBean.getDeleteId();

            StringBuilder sb = new StringBuilder()
                    .append(" DELETE FROM ")
                    .append("   m_dormitory ")
                    .append(" WHERE 1=1 ")
                    ;

            //Id
            if ( 0 < deleteId.length && deleteId != null){
                sb.append(" AND (");
                for(int j = 0; j < deleteId.length; j++) {
                    // 初回以外はORを追加
                    if ( 1 <= j ) {
                        sb.append(" OR ");
                    }
                    sb.append(" dormitory_id = ? ");
                }
                sb.append(" )");
            }
            ps = conn.prepareStatement(sb.toString());

            int prmCnt = 1;
            if ( 0 < deleteId.length ){
                for(int j = 0; j < deleteId.length; j++) {
                    ps.setString(prmCnt, deleteId[j]);
                    prmCnt++;
                }
            }

            i = ps.executeUpdate();

            //コミット
            conn.commit();

            bean.setMsg("選択された物件を削除しました。");

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
