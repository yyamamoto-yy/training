package jp.ne.anvil.master.room;

/**
 * 部屋一覧検索条件Bean
 * @author y.yamamoto
 * @version 1.0
 */
public class RoomAddBean {
    private String dormitoryId = "";
    private String dormitoryName = "";
    private String roomId = "";
    private String floorPlan = "";
    private String squareMeter = "";
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
    public String getFloorPlan() {
        return floorPlan;
    }
    public void setFloorPlan(String strings) {
        this.floorPlan = strings;
    }
    public String getSquareMeter() {
        return squareMeter;
    }
    public void setSquareMeter(String squareMeter) {
        this.squareMeter = squareMeter;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

}
