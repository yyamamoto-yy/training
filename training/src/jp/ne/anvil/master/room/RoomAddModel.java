package jp.ne.anvil.master.room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;

/**
 * 部屋追加のモデル
 * @author y.yamamoto
 * @version 1.0
 */
public class RoomAddModel {


    /**
    * コンストラクタ
    */
    public RoomAddModel() {
        super();
    }

    /**
     * 部屋を追加
     * @param pAddBean 追加するID,PASS
     * @throws Exception
     */
    public RoomAddBean addRoom(RoomAddBean pAddBean ) throws Exception {

        RoomAddBean bean = new RoomAddBean();

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
            String dormitoryName = StringUtils.trim(pAddBean.getDormitoryName());
            String roomId = StringUtils.trim(pAddBean.getRoomId());
            String floorPlan = StringUtils.trim(pAddBean.getFloorPlan());
            String squareMeter = StringUtils.trim(pAddBean.getSquareMeter());



            StringBuilder sb = new StringBuilder()

                    .append("SELECT " )
                    .append("   COUNT(*)  cnt")
                    .append(" FROM " )
                    .append("   m_room " )
                    .append(" WHERE " )
                    .append("   dormitory_id = (" )
                    .append("   SELECT "  )
                    .append("     dormitory_id "  )
                    .append("   FROM m_dormitory "  )
                    .append("   WHERE "  )
                    .append("     dormitory_name = ? ")
                    .append("  ) AND "  )
                    .append("   room_id = ?"  )
                    ;

            ps = conn.prepareStatement(sb.toString());

            int prmCnt = 1;
            // 物件名
            if ( !StringUtils.isEmpty(dormitoryName) ){
                ps.setString(prmCnt, dormitoryName );
                prmCnt++;
            }
            // 部屋番号
            if ( !StringUtils.isEmpty(roomId) ){
                ps.setString(prmCnt, roomId );
                prmCnt++;
            }

            rs = ps.executeQuery();

            int a = 0;
            while(rs.next()) {
                a = rs.getInt("cnt");
            }

            if(a==0) {

                StringBuilder sb2 = new StringBuilder()
                        .append("INSERT INTO ")
                        .append(" m_room (")
                        .append(" dormitory_id")
                        .append(",room_id")
                        .append(",floor_plan")
                        .append(",square_meter")
                        .append(",create_dt")
                        .append(" ) VALUES ")
                        .append("((")
                        .append(" SELECT "  )
                        .append(" dormitory_id "  )
                        .append(" FROM m_dormitory "  )
                        .append(" WHERE "  )
                        .append(" dormitory_name = ? )")
                        .append(", ? , ? , ? ")
                        .append(",sysdate)")
                        ;

                ps2 = conn.prepareStatement(sb2.toString());

                prmCnt = 1;
                // 物件名
                if ( !StringUtils.isEmpty(dormitoryName) ){
                    ps2.setString(prmCnt, dormitoryName );
                    prmCnt++;
                }
                //部屋番号
                if ( !StringUtils.isEmpty(roomId) ){
                    ps2.setString(prmCnt, roomId );
                    prmCnt++;
                }
                // 間取り
                if ( !StringUtils.isEmpty(floorPlan) ){
                    ps2.setString(prmCnt, floorPlan );
                    prmCnt++;
                }
                // 平米
                if ( !StringUtils.isEmpty(squareMeter) ){
                    ps2.setString(prmCnt, squareMeter );
                    prmCnt++;
                }

                i = ps2.executeUpdate();
                bean.setMsg("部屋を追加しました。");

                //コミット
                conn.commit();
            }else {
                bean.setMsg("すでに使用されている部屋番号です。");

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
