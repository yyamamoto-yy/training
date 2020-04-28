package jp.ne.anvil.master.room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * 社員寮一覧取得のモデル
 * @author y.yamamoto
 * @version 1.0
 */
public class GetDormitoryModel {


    /**
    * コンストラクタ
    */
    public GetDormitoryModel() {
        super();
    }

    /**
     * 社員寮一覧を取得
     * @param pSearchBean 検索条件
     * @throws Exception
     */
    public List<DormitoryListBean> getDormitoryList() throws Exception {

        List<DormitoryListBean> listBean = new ArrayList<>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            InitialContext context = new InitialContext();
            DataSource ds = (DataSource)context.lookup("java:comp/env/jdbc/oracle");

            conn = ds.getConnection();
            conn.setAutoCommit(false);


            StringBuilder sb = new StringBuilder()
                    .append(" SELECT")
                    .append("     dormitory_id")
                    .append("   , dormitory_name")
                    .append(" FROM ")
                    .append("     m_dormitory")
                    ;

            ps = conn.prepareStatement(sb.toString());

            rs = ps.executeQuery();

            DormitoryListBean bean = null;
            while(rs.next()) {
                bean = new DormitoryListBean();
                bean.setDormitory_id(rs.getString("dormitory_id"));
                bean.setDormitory_name(rs.getString("dormitory_name"));
                listBean.add(bean);
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

        return listBean;
    }
}
