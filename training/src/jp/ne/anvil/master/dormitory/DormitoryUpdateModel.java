package jp.ne.anvil.master.dormitory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;

/**
 * 社員寮編集モデル
 * @author y.yamamoto
 * @version 1.0
 */
public class DormitoryUpdateModel {


    /**
    * コンストラクタ
    */
    public DormitoryUpdateModel() {
        super();
    }

    /**
     * 社員寮を編集
     * @param pEditBean 編集するID,寮名
     * @throws Exception
     */
    public DormitoryEditBean updateDormitory(DormitoryEditBean pEditBean ) throws Exception {

        DormitoryEditBean bean = new DormitoryEditBean();

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
            String updateName = StringUtils.trim(pEditBean.getUpdateName());
            String beforeName = StringUtils.trim(pEditBean.getBeforeName());
            StringBuilder sb = new StringBuilder()
                    .append(" UPDATE ")
                    .append(" m_dormitory ")
                    .append(" SET ")
                    .append(" dormitory_name = ? ")
                    .append(" WHERE ")
                    .append(" dormitory_id = ? ")
                    ;
            ps = conn.prepareStatement(sb.toString());

            int prmCnt = 1;
            // 物件名
            if ( !StringUtils.isEmpty(updateName) ){
                ps.setString(prmCnt, updateName );
                prmCnt++;
            }
            // 物件ID
            if ( !StringUtils.isEmpty(updateId) ){
                ps.setString(prmCnt, updateId );
                prmCnt++;
            }


            i = ps.executeUpdate();

            //コミット
            conn.commit();

            bean.setMsg("社員寮「"+beforeName+"」（ID："+updateId+"）を「"+updateName+"」に変更しました。");

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
