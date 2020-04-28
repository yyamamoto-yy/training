package jp.ne.anvil.master.room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * 社員寮一括削除のモデル
 * @author y.yamamoto
 * @version 1.0
 */
public class RoomSelectDeleteModel {


    /**
    * コンストラクタ
    */
    public RoomSelectDeleteModel() {
        super();
    }

    /**
     * 社員寮を一括削除
     * @param pEditBean 削除するID
     * @throws Exception
     */
    public String deleteRoom(String[][] pDeleteRoom ) throws Exception {


        Connection conn = null;
        PreparedStatement ps = null;
        String msg = null;
        int i = 0;

        try {
            InitialContext context = new InitialContext();
            DataSource ds = (DataSource)context.lookup("java:comp/env/jdbc/oracle");

            conn = ds.getConnection();
            conn.setAutoCommit(false);

            String[][] delRoom = pDeleteRoom;

            StringBuilder sb = new StringBuilder()
                    .append(" DELETE FROM ")
                    .append("   m_room ")
                    .append(" WHERE 1=1 ")
                    ;

            //Id
            if ( 0 < delRoom.length && delRoom != null){
                sb.append(" AND (");
                for(int j = 0; j < delRoom.length; j++) {
                    // 初回以外はORを追加
                    if ( 1 <= j ) {
                        sb.append(" OR (");
                    }
                    sb.append(" dormitory_id = ? ");
                    sb.append(" AND ");
                    sb.append(" room_id = ? )");

                }

            }
            ps = conn.prepareStatement(sb.toString());

            int prmCnt = 1;
            if ( 0 < delRoom.length ){
                for(int j = 0; j < delRoom.length; j++) {
                    ps.setString(prmCnt, delRoom[j][0]);
                    prmCnt++;
                    ps.setString(prmCnt, delRoom[j][1]);
                    prmCnt++;
                }
            }

            i = ps.executeUpdate();

            //コミット
            conn.commit();

            msg="選択された部屋を削除しました。";

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

        return msg;
    }
}
