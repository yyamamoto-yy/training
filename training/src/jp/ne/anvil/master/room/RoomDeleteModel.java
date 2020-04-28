package jp.ne.anvil.master.room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;

/**
 * 部屋削除のモデル
 * @author y.yamamoto
 * @version 1.0
 */
public class RoomDeleteModel {


    /**
    * コンストラクタ
    */
    public RoomDeleteModel() {
        super();
    }

    /**
     * 部屋を削除
     * @param pEditBean 削除するID
     * @throws Exception
     */
    public RoomDeleteBean deleteRoom(RoomDeleteBean pDeleteBean ) throws Exception {

        RoomDeleteBean bean = new RoomDeleteBean();

        Connection conn = null;
        PreparedStatement ps = null;
        int i = 0;

        try {
            InitialContext context = new InitialContext();
            DataSource ds = (DataSource)context.lookup("java:comp/env/jdbc/oracle");

            conn = ds.getConnection();
            conn.setAutoCommit(false);

            // Trimする
            String dormitoryId = StringUtils.trim(pDeleteBean.getDormitoryId());
            String dormitoryName = StringUtils.trim(pDeleteBean.getDormitoryName());
            String roomId = StringUtils.trim(pDeleteBean.getRoomId());

            StringBuilder sb = new StringBuilder()
                    .append("DELETE FROM ")
                    .append(" m_room ")
                    .append("WHERE ")
                    .append(" dormitory_id = ? ")
                    .append(" AND ")
                    .append(" room_id = ? ")
                    ;
            ps = conn.prepareStatement(sb.toString());

            int prmCnt = 1;
            // 物件ID
            if ( !StringUtils.isEmpty(dormitoryId) ){
                ps.setString(prmCnt, dormitoryId );
                prmCnt++;
            }
            // 部屋番号
            if ( !StringUtils.isEmpty(roomId) ){
                ps.setString(prmCnt, roomId );
                prmCnt++;
            }

            i = ps.executeUpdate();

            //コミット
            conn.commit();

            bean.setMsg("「" + dormitoryName + "」の部屋番号：" + roomId + "を削除しました。");

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
