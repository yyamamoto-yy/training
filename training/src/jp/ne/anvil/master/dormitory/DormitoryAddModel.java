package jp.ne.anvil.master.dormitory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;

/**
 * 社員寮追加のモデル
 * @author y.yamamoto
 * @version 1.0
 */
public class DormitoryAddModel {


    /**
    * コンストラクタ
    */
    public DormitoryAddModel() {
        super();
    }

    /**
     * 社員寮を追加
     * @param pAddBean 追加するID,PASS
     * @throws Exception
     */
    public DormitoryAddBean addDormitory(DormitoryAddBean pAddBean ) throws Exception {

        DormitoryAddBean bean = new DormitoryAddBean();

        Connection conn = null;
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        int i = 0;
        ResultSet rs = null;

        try {
            InitialContext context = new InitialContext();
            DataSource ds = (DataSource)context.lookup("java:comp/env/jdbc/oracle");

            conn = ds.getConnection();
            conn.setAutoCommit(false);

            // Trimする
            String addId = StringUtils.trim(pAddBean.getAddId());
            String addName = StringUtils.trim(pAddBean.getAddName());

            StringBuilder sb = new StringBuilder()

                    .append("SELECT " )
                    .append(" COUNT(*)  cnt")
                    .append(" FROM " )
                    .append(" m_dormitory " )
                    .append(" WHERE " )
                    .append(" dormitory_id =  ? " )
                    ;

            ps = conn.prepareStatement(sb.toString());

            ps.setString(1, addId );

            rs = ps.executeQuery();


            int a = 0;
            while(rs.next()) {
                a = rs.getInt("cnt");
            }

            if(a==0) {

            StringBuilder sb2 = new StringBuilder()
                    .append("INSERT INTO ")
                    .append(" m_dormitory ")
                    .append(" (dormitory_id,dormitory_name,create_dt) ")
                    .append(" VALUES ")
                    .append("( ? ")
                    .append(", ? ")
                    .append(",sysdate)")
                    ;

            ps2 = conn.prepareStatement(sb2.toString());

            int prmCnt = 1;
            // ユーザ名
            if ( !StringUtils.isEmpty(addId) ){
                ps2.setString(prmCnt, addId );
                prmCnt++;
            }
            // パスワード
            if ( !StringUtils.isEmpty(addName) ){
                ps2.setString(prmCnt, addName );
                prmCnt++;
            }

            i = ps2.executeUpdate();
            bean.setMsg("社員寮を追加しました。");

            //コミット
            conn.commit();
            }else {
                bean.setMsg("すでに使用されているIDです。");

            }

        }catch(SQLException e) {
            conn.rollback();       //ロールバックする
            throw e;
        }finally{
            try {

                if (ps != null) {ps.close();}
                if (ps2 != null) {ps2.close();}
                if (conn != null) {conn.close();}
            }catch(SQLException e) {
                throw e;
            }
        }

        return bean;
    }
}
