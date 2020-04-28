package jp.ne.anvil.master.room;

/**
 * 部屋削除Bean
 * @author y.yamamoto
 * @version 1.0
 */
public class RoomDeleteBean {
    private String dormitoryId;
    private String dormitoryName;
    private String roomId;
    private String msg;

    public String getDormitoryId() {
        return dormitoryId;
    }
    public void setDormitoryId(String dormitoryId) {
        this.dormitoryId = dormitoryId;
    }

    public String getDormitoryName() {
        return dormitoryName;
    }
    public void setDormitoryName(String dormitoryName) {
        this.dormitoryName = dormitoryName;
    }

    public String getRoomId() {
        return roomId;
    }
    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

}
