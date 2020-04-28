package jp.ne.anvil.master.user;

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
 * ユーザー一覧のモデル
 * @author y.yamamoto
 * @version 1.0
 */
public class UserModel {


    /**
    * コンストラクタ
    */
    public UserModel() {
        super();
    }

    /**
     * ユーザー一覧を取得
     * @param pSearchBean 検索条件
     * @throws Exception
     */
    public List<UserBean> getUser(UserSearchBean pSearchBean) throws Exception {

        List<UserBean> listBean = new ArrayList<>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            InitialContext context = new InitialContext();
            DataSource ds = (DataSource)context.lookup("java:comp/env/jdbc/oracle");

            conn = ds.getConnection();
            conn.setAutoCommit(false);

            // Trimする
            String userId = StringUtils.trim(pSearchBean.getUserId());
            String createDtFrom = StringUtils.trim(pSearchBean.getCreateDtFrom());
            String createDtTo = StringUtils.trim(pSearchBean.getCreateDtTo());

            StringBuilder sb = new StringBuilder()
                    .append("SELECT")
                    .append(" id")
                    .append(",TO_CHAR( create_dt, 'YYYY/MM/DD' ) AS create_dt")
                    .append(" FROM ")
                    .append(" m_login_user")
                    .append(" WHERE 1=1 ")
                    ;

            // ID
            if ( !StringUtils.isEmpty(userId) ){
                sb.append(" AND id LIKE  ? ");
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
            .append("     create_dt")
            .append("   , id")
            ;

            ps = conn.prepareStatement(sb.toString());

            int prmCnt = 1;
            // 物件名
            if ( !StringUtils.isEmpty(userId) ){
                ps.setString(prmCnt, "%"+ userId +"%");
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

            UserBean bean = null;
            while(rs.next()) {
                bean = new UserBean();
                bean.setUser_id(rs.getString("id"));
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
