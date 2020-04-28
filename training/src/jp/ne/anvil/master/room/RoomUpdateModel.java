package jp.ne.anvil.master.room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;

/**
 * 部屋情報変更モデル
 * @author y.yamamoto
 * @version 1.0
 */
public class RoomUpdateModel {


    /**
    * コンストラクタ
    */
    public RoomUpdateModel() {
        super();
    }

    /**
     * 部屋情報を編集
     * @param pEditBean 編集するID,寮名
     * @throws Exception
     */
    public RoomUpdateBean updateRoom(RoomUpdateBean pEditBean ) throws Exception {

        RoomUpdateBean bean = new RoomUpdateBean();

        Connection conn = null;
        PreparedStatement ps = null;
        int i = 0;

        try {
            InitialContext context = new InitialContext();
            DataSource ds = (DataSource)context.lookup("java:comp/env/jdbc/oracle");

            conn = ds.getConnection();
            conn.setAutoCommit(false);

            // Trimする
            String dormitoryId = StringUtils.trim(pEditBean.getDormitoryId());
            String dormitoryName = StringUtils.trim(pEditBean.getDormitoryName());
            String roomId = StringUtils.trim(pEditBean.getRoomId());
            String floorPlan = StringUtils.trim(pEditBean.getFloorPlan());
            String updateMeter = StringUtils.trim(pEditBean.getUpdateMeter());

            StringBuilder sb = new StringBuilder()
                    .append(" UPDATE ")
                    .append("   m_room ")
                    .append(" SET ")
                    .append(" floor_plan = ? ")
                    .append(" ,square_meter = ? ")
                    .append(" ,create_dt = sysdate" )
                    .append(" WHERE ")
                    .append(" dormitory_id = ? ")
                    .append(" AND ")
                    .append(" room_id = ? ")
                    ;
            ps = conn.prepareStatement(sb.toString());

            int prmCnt = 1;
            // 間取り
            if ( !StringUtils.isEmpty(floorPlan) ){
                ps.setString(prmCnt, floorPlan );
                prmCnt++;
            }
            // 平米
            if ( !StringUtils.isEmpty(updateMeter) ){
                ps.setString(prmCnt, updateMeter );
                prmCnt++;
            }
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

            bean.setMsg("社員寮「" + dormitoryName + "」部屋番号「" + roomId + "」の部屋情報を変更しました。");


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
