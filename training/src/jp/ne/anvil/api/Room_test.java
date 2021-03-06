package jp.ne.anvil.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import jp.ne.anvil.master.room.RoomMBean;

/**
 * 社員寮の件数を取得
 * @author y.yamamoto
 * @version 1.0
 */
public class Room_test {


    /**
    * コンストラクタ
    */
    public Room_test() {
        super();
    }

    /**
     * 社員寮の件数を取得
     * @param
     * @throws Exception
     */
    public List<RoomMBean> getRoom() throws Exception {

        List<RoomMBean> listBean = new ArrayList<>();

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
                    .append("     r.dormitory_id")
                    .append("   , d.dormitory_name")
                    .append("   , r.room_id")
                    .append("   , r.floor_plan")
                    .append("   , r.square_meter")
                    .append("   ,TO_CHAR( r.create_dt, 'YYYY/MM/DD' ) AS create_dt")
                    .append("   , ru.user_name")
                    .append(" FROM ")
                    .append("     m_room r")
                    .append("     LEFT JOIN m_dormitory d ON (r.dormitory_id = d.DORMITORY_ID)")
                    .append("     LEFT JOIN t_room_use tr ON (r.dormitory_id = tr.dormitory_id AND r.room_id = tr.room_id and tr.delete_flg = '0')")
                    .append("     LEFT JOIN m_room_user ru ON (tr.user_id = ru.user_id)")
                    ;

                    sb
                    .append(" ORDER BY")
                    .append("     r.dormitory_id")
                    .append("   , r.room_id")
                    ;

            ps = conn.prepareStatement(sb.toString());

            rs = ps.executeQuery();

            RoomMBean bean = null;
            while(rs.next()) {
                bean = new RoomMBean();
                bean.setDormitory_id(rs.getString("dormitory_id"));
                bean.setDormitory_name(rs.getString("dormitory_name"));
                bean.setRoom_id(rs.getString("room_id"));
                bean.setFloor_plan(rs.getString("floor_plan"));
                bean.setSquare_meter(rs.getString("square_meter"));
                bean.setCreate_dt(rs.getString("create_dt"));
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
