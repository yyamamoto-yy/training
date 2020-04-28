package jp.ne.anvil.master.room;

/**
 * 部屋一覧検索条件Bean
 * @author y.yamamoto
 * @version 1.0
 */
public class RoomMListSearchBean {
    private String dormitoryId = "";
    private String dormitoryName = "";
    private String roomId = "";
    private String[] floorPlan = new String[0];
    private String squareMeter = "";
    private String out = "";
    private String createDtFrom = "";
    private String createDtTo = "";

    public String getOut() {
        return out;
    }
    public void setOut(String out) {
        this.out = out;
    }
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
    public String[] getFloorPlan() {
        return floorPlan;
    }
    public void setFloorPlan(String[] strings) {
        this.floorPlan = strings;
    }
    public String getSquareMeter() {
        return squareMeter;
    }
    public void setSquareMeter(String squareMeter) {
        this.squareMeter = squareMeter;
    }
    public String getCreateDtFrom() {
        return createDtFrom;
    }
    public void setCreateDtFrom(String createDtFrom) {
        this.createDtFrom = createDtFrom;
    }
    public String getCreateDtTo() {
        return createDtTo;
    }
    public void setCreateDtTo(String createDtTo) {
        this.createDtTo = createDtTo;
    }
}
