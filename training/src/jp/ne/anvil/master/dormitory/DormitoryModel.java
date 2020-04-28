package jp.ne.anvil.master.dormitory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;

/**
 * 社員寮一覧のモデル
 * @author y.yamamoto
 * @version 1.0
 */
public class DormitoryModel {


    /**
    * コンストラクタ
    */
    public DormitoryModel() {
        super();
    }

    /**
     * 社員寮一覧を取得
     * @param pSearchBean 検索条件
     * @throws Exception
     */
    public List<DormitoryBean> getDormitory(DormitorySearchBean pSearchBean) throws Exception {

        List<DormitoryBean> listBean = new ArrayList<>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            InitialContext context = new InitialContext();
            DataSource ds = (DataSource)context.lookup("java:comp/env/jdbc/oracle");

            conn = ds.getConnection();
            conn.setAutoCommit(false);

            // Trimする
            String dormitoryId = StringUtils.trim(pSearchBean.getDormitoryId());
            String dormitoryName = StringUtils.trim(pSearchBean.getDormitoryName());
            String createDtFrom = StringUtils.trim(pSearchBean.getCreateDtFrom());

            String createDtTo = StringUtils.trim(pSearchBean.getCreateDtTo());

            StringBuilder sb = new StringBuilder()
                    .append("SELECT")
                    .append(" dormitory_id")
                    .append(",dormitory_name")
                    .append(",TO_CHAR( create_dt, 'YYYY/MM/DD' ) AS create_dt")
                    .append(" FROM ")
                    .append(" m_dormitory")
                    .append(" WHERE 1=1 ")
                    ;

            // ID
            if ( !StringUtils.isEmpty(dormitoryId) ){
                sb.append(" AND dormitory_id LIKE ? ");
            }

            // 物件名
            if ( !StringUtils.isEmpty(dormitoryName) ){
                sb.append(" AND dormitory_name LIKE ? ");
            }

            // 日付
            if ( !StringUtils.isEmpty(createDtFrom) ){
                sb.append(" AND  TO_CHAR( create_dt, 'YYYY-MM-DD' )  >= ? ");
            }
            if (  !StringUtils.isEmpty(createDtTo)){
                sb.append(" AND TO_CHAR( create_dt, 'YYYY-MM-DD' )  <= ? ");
            }

            sb
            .append(" ORDER BY")
            .append("  dormitory_id")
            ;

            ps = conn.prepareStatement(sb.toString());

            int prmCnt = 1;
            // 物件ID
            if ( !StringUtils.isEmpty(dormitoryId) ){
                ps.setString(prmCnt, "%"+ dormitoryId +"%");
                prmCnt++;
            }
            // 物件名
            if ( !StringUtils.isEmpty(dormitoryName) ){
                ps.setString(prmCnt, "%"+ dormitoryName +"%");
                prmCnt++;
            }
            // 日付
            if ( !StringUtils.isEmpty(createDtFrom) ){
                ps.setString(prmCnt, createDtFrom );
                prmCnt++;
            }
            if ( !StringUtils.isEmpty(createDtTo) ){
                ps.setString(prmCnt, createDtTo );
                prmCnt++;
            }

            rs = ps.executeQuery();

            DormitoryBean bean = null;
            while(rs.next()) {
                bean = new DormitoryBean();
                bean.setDormitory_id(rs.getString("dormitory_id"));
                bean.setDormitory_name(rs.getString("dormitory_name"));
                bean.setCreate_dt(rs.getString("create_dt"));
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
