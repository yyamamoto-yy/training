package jp.ne.anvil.roomList;

/**
 * 部屋一覧検索条件Bean
 * @author k.nakamura
 * @version 1.0
 */
public class RoomListSearthBean {
    private String dormitoryName;
    private String[] floorPlan;
    private String squareMeter;
    private String out;

    public String getOut() {
        return out;
    }
    public void setOut(String out) {
        this.out = out;
    }
    public String getDormitoryName() {
        return dormitoryName;
    }
    public void setDormitoryName(String dormitoryName) {
        this.dormitoryName = dormitoryName;
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
}
