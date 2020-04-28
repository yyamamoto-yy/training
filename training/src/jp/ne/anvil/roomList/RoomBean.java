package jp.ne.anvil.roomList;

/**
 * 部屋一覧Bean
 * @author k.nakamura
 * @version 1.0
 */
public class RoomBean {
    private String dormitory_name;
    private String room_id;
    private String floor_plan;
    private String square_meter;
    private String user_name;

    public String getDormitory_name() {
        return dormitory_name;
    }
    public void setDormitory_name(String dormitory_name) {
        this.dormitory_name = dormitory_name;
    }
    public String getRoom_id() {
        return room_id;
    }
    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }
    public String getFloor_plan() {
        return floor_plan;
    }
    public void setFloor_plan(String floor_plan) {
        this.floor_plan = floor_plan;
    }
    public String getSquare_meter() {
        return square_meter;
    }
    public void setSquare_meter(String square_meter) {
        this.square_meter = square_meter;
    }
    public String getUser_name() {
        return user_name;
    }
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
