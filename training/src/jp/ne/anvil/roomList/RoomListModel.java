package jp.ne.anvil.roomList;

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
 * 部屋一覧のモデル
 * @author k.nakamura
 * @version 1.0
 */
public class RoomListModel {


    /**
    * コンストラクタ
    */
    public RoomListModel() {
        super();
    }

    /**
     * 部屋一覧を取得
     * @param pSearchBean 検索条件
     * @throws Exception
     */
    public List<RoomBean> getRoomList(RoomListSearthBean pSearchBean) throws Exception {

        List<RoomBean> listBean = new ArrayList<>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            InitialContext context = new InitialContext();
            DataSource ds = (DataSource)context.lookup("java:comp/env/jdbc/oracle");

            conn = ds.getConnection();

            // Trimする
            String dormitoryName = StringUtils.trim(pSearchBean.getDormitoryName());
            String[] floorPlans = pSearchBean.getFloorPlan();
            String squareMeter = StringUtils.trim(pSearchBean.getSquareMeter());
            String out = pSearchBean.getOut();

            StringBuilder sb = new StringBuilder()
                    .append(" SELECT")
                    .append("     d.dormitory_name")
                    .append("   , r.room_id")
                    .append("   , r.floor_plan")
                    .append("   , r.square_meter")
                    .append("   , ru.user_name")
                    .append(" FROM ")
                    .append("     m_room r")
                    .append("     LEFT JOIN m_dormitory d ON (r.dormitory_id = d.DORMITORY_ID)")
                    .append("     LEFT JOIN t_room_use tr ON (r.dormitory_id = tr.dormitory_id AND r.room_id = tr.room_id and tr.delete_flg = '0')")
                    .append("     LEFT JOIN m_room_user ru ON (tr.user_id = ru.user_id)")
                    .append(" WHERE 1=1 ")
                    ;

                    // 社員寮名
                    if ( !StringUtils.isEmpty(dormitoryName) ){
                        sb.append(" AND d.dormitory_name LIKE ? ");
                    }

                    // 間取り
                    if ( 0 < floorPlans.length ){
                        sb.append(" AND (");
                        for(int i = 0; i < floorPlans.length; i++) {
                            // 初回以外はORを追加
                            if ( 1 <= i ) {
                                sb.append(" OR ");
                            }
                            sb.append(" r.floor_plan = ? ");
                        }
                        sb.append(" )");
                    }
                    // 広さ
                    if ( !StringUtils.isEmpty(squareMeter) ){
                        sb.append(" AND r.square_meter >= ?");
                    }

                    // 入居済みを除外
                    if ( !StringUtils.isEmpty(out)) {
                        sb.append(" AND ru.user_name IS NULL");
                    }

                    sb
                    .append(" ORDER BY")
                    .append("     r.dormitory_id")
                    .append("   , r.room_id")
                    ;

            ps = conn.prepareStatement(sb.toString());

            int prmCnt = 1;
            // 社員寮名
            if ( !StringUtils.isEmpty(dormitoryName) ){
                ps.setString(prmCnt, "%" + dormitoryName + "%");
                prmCnt++;
            }
            // 間取り
            if ( 0 < floorPlans.length ){
                for(int i = 0; i < floorPlans.length; i++) {
                    ps.setString(prmCnt, floorPlans[i]);
                    prmCnt++;
                }
            }
            // 広さ
            if ( !StringUtils.isEmpty(squareMeter) ){
                ps.setString(prmCnt, squareMeter);
                prmCnt++;
            }

            rs = ps.executeQuery();

            RoomBean bean = null;
            while(rs.next()) {
                bean = new RoomBean();
                bean.setDormitory_name(rs.getString("dormitory_name"));
                bean.setRoom_id(rs.getString("room_id"));
                bean.setFloor_plan(rs.getString("floor_plan"));
                bean.setSquare_meter(rs.getString("square_meter"));
                bean.setUser_name(rs.getString("user_name"));
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
