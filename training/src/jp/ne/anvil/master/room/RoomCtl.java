package jp.ne.anvil.master.room;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import jp.ne.anvil.enums.Action;
import jp.ne.anvil.enums.BeanName;
import jp.ne.anvil.enums.Message;
import jp.ne.anvil.login.LoginBean;
import jp.ne.anvil.master.CsvFileNameBean;

/**
 * 部屋マスター編集のコントローラ
 * @author y.yamamoto
 * @version 1.0
 */
@WebServlet("/MasterRoom")
public class RoomCtl extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = Logger.getLogger(RoomCtl.class);

    /**
    * コンストラクタ
    */
    public RoomCtl() {
        super();
    }

    /**
     * doGet
     * @param request リクエスト
     * @param response レスポンス
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");

        // セッションタイムアウト確認
        LoginBean loginBean = (LoginBean) session.getAttribute(BeanName.LOGIN_USER.getName());
        if (loginBean == null) {
            response.sendRedirect("/WEB-INF/views/timeout.html");
            return;
        }

        String action = request.getParameter("action");
        String msg = "　";
        String forward = null;

        if(Action.ROOM_SEARCH.getAction().equals(action)) {
            LOGGER.info(Message.I0031.getMessage(loginBean.getId()));
            // 検索処理
            try {
                RoomMListSearchBean searchBean = new RoomMListSearchBean();

                searchBean.setDormitoryId(request.getParameter("dormitory_id"));
                searchBean.setDormitoryName(request.getParameter("dormitory_name"));
                searchBean.setRoomId(request.getParameter("room_id"));
                searchBean.setFloorPlan(new String[0]);
                if (request.getParameterValues("plan") != null) {
                    searchBean.setFloorPlan(request.getParameterValues("plan"));
                }
                searchBean.setSquareMeter(request.getParameter("meter"));
                searchBean.setOut(request.getParameter("out"));
                searchBean.setCreateDtFrom(request.getParameter("create_dt_from"));
                searchBean.setCreateDtTo(request.getParameter("create_dt_to"));

                //RoomListModelクラスをnewして使えるようにする
                RoomMListModel roomMListModel = new RoomMListModel();

                //RoomListModelクラスのgetRoomListメソッドを呼び出す
                List<RoomMBean> listBean = roomMListModel.getRoomMList(searchBean);

                msg="検索結果";
                request.setAttribute("msg", msg);
                request.setAttribute(BeanName.ROOM_LIST.getName(), listBean);
                request.setAttribute(BeanName.ROOM_SEARCH.getName(), searchBean);
                forward = "/WEB-INF/views/master/room.jsp";
            }catch(Exception e){
                LOGGER.error(Message.E0000.getMessage(e.getMessage()));
                request.setAttribute("message", e.getMessage());
                forward = "/WEB-INF/views/error.jsp";
            }
            // 設定された遷移先へ移動
            RequestDispatcher dispatcher = request.getRequestDispatcher(forward);
            dispatcher.forward(request, response);

        }else if(Action.ROOM_ADD.getAction().equals(action)) {
         // 追加画面へ
            try {
                 //RoomListModelクラスをnewして使えるようにする
                GetDormitoryModel getDormitoryModel = new GetDormitoryModel();

                //RoomListModelクラスのgetRoomListメソッドを呼び出す
                List<DormitoryListBean> listBean = getDormitoryModel.getDormitoryList();

                RoomAddBean addBean = new RoomAddBean();

                request.setAttribute("msg", msg);
                request.setAttribute(BeanName.ROOM_ADD.getName(), addBean);
                request.setAttribute(BeanName.DORM_LIST.getName(), listBean);
                forward = "/WEB-INF/views/master/roomAdd.jsp";
            }catch(Exception e){
                LOGGER.error(Message.E0000.getMessage(e.getMessage()));
                request.setAttribute("message", e.getMessage());
                forward = "/WEB-INF/views/error.jsp";
            }
            // 設定された遷移先へ移動
            RequestDispatcher dispatcher = request.getRequestDispatcher(forward);
            dispatcher.forward(request, response);

        }else if(Action.ROOM_ADDITION.getAction().equals(action)) {
            // 追加処理
            try {
                RoomAddBean addBean = new RoomAddBean();
                String dormitoryName =request.getParameter("dormitoryName");
                String roomId =request.getParameter("roomId");
                String floorPlan =request.getParameter("plan");
                String squareMeter=request.getParameter("squareMeter");

                addBean.setDormitoryName(dormitoryName);
                addBean.setRoomId(roomId);
                addBean.setFloorPlan(floorPlan);
                addBean.setSquareMeter(squareMeter);

                //物件リスト取得
                GetDormitoryModel getDormitoryModel = new GetDormitoryModel();
                List<DormitoryListBean> listBean = getDormitoryModel.getDormitoryList();

                //入力判定
                if (!StringUtils.isEmpty(roomId) && !StringUtils.isEmpty(squareMeter)
                        && roomId.matches("[0-9]{4}") && (squareMeter.matches("[0-9]{1}")
                        || squareMeter.matches("[0-9]{2}") || squareMeter.matches("[0-9]{3}"))){
                    //RoomAddModelクラスをnewして使えるようにする
                    RoomAddModel roomAddModel = new RoomAddModel();

                    //RoomAddModelクラスのaddUserメソッドを呼び出す
                    addBean = roomAddModel.addRoom(addBean);
                    forward = "/WEB-INF/views/master/room.jsp";

                    LOGGER.info(Message.I0032.getMessage(loginBean.getId()));

                    //使用されている部屋番号の時
                    if(addBean.getMsg().equals("すでに使用されている部屋番号です。")) {
                         request.setAttribute(BeanName.DORM_LIST.getName(), listBean);
                         forward = "/WEB-INF/views/master/roomAdd.jsp";
                         //値の保持
                         addBean.setDormitoryName(dormitoryName);
                         addBean.setRoomId(roomId);
                         addBean.setFloorPlan(floorPlan);
                         addBean.setSquareMeter(squareMeter);
                         LOGGER.info(Message.I0036.getMessage(loginBean.getId()));
                    }

                    msg = addBean.getMsg();

                }else {
                    request.setAttribute(BeanName.DORM_LIST.getName(), listBean);
                     msg = "正しく入力されませんでした。";
                     forward = "/WEB-INF/views/master/roomAdd.jsp";
                }

                //表示初期化
                RoomMListSearchBean searchBean = new RoomMListSearchBean();

                //部屋一覧を取得
                RoomMListModel roomMListModel = new RoomMListModel();
                List<RoomMBean> roomListBean = null;
                try {
                    roomListBean = roomMListModel.getRoomMList(searchBean);
                } catch (Exception e) {
                    LOGGER.error(Message.E0000.getMessage(e.getMessage()));
                    request.setAttribute("message", e.getMessage());
                    forward = "/WEB-INF/views/error.jsp";
                }

                request.setAttribute("msg", msg);
                request.setAttribute(BeanName.ROOM_LIST.getName(), roomListBean);
                request.setAttribute(BeanName.ROOM_ADD.getName(), addBean);
                request.setAttribute(BeanName.ROOM_SEARCH.getName(), searchBean);

            }catch(Exception e){
                LOGGER.error(Message.E0000.getMessage(e.getMessage()));
                request.setAttribute("message", e.getMessage());
                forward = "/WEB-INF/views/error.jsp";
            }
            // 設定された遷移先へ移動
            RequestDispatcher dispatcher = request.getRequestDispatcher(forward);
            dispatcher.forward(request, response);

        }else if(Action.ROOM_DELETE.getAction().equals(action)) {

            // 削除処理
            try {

                RoomDeleteBean deleteBean = new RoomDeleteBean();
                String dormitoryId =request.getParameter("dormitoryId");
                String dormitoryName =request.getParameter("dormitoryName");
                String roomId =request.getParameter("roomId");

                deleteBean.setDormitoryId(dormitoryId);
                deleteBean.setDormitoryName(dormitoryName);
                deleteBean.setRoomId(roomId);

                //RoomDeleteModelクラスをnewして使えるようにする
                RoomDeleteModel roomDeleteModel = new RoomDeleteModel();

                //RoomDeleteModelクラスのdeleteRoomメソッドを呼び出す
                deleteBean = roomDeleteModel.deleteRoom(deleteBean);

                LOGGER.info(Message.I0033.getMessage(loginBean.getId()));

                //表示初期化
                RoomMListSearchBean searchBean = new RoomMListSearchBean();

                msg = deleteBean.getMsg();

                //部屋一覧を取得
                RoomMListModel roomMListModel = new RoomMListModel();
                List<RoomMBean> listBean = roomMListModel.getRoomMList(searchBean);

                request.setAttribute("msg", msg);
                request.setAttribute(BeanName.ROOM_LIST.getName(), listBean);
                request.setAttribute(BeanName.ROOM_SEARCH.getName(), searchBean);
                forward = "/WEB-INF/views/master/room.jsp";

            }catch(Exception e){
                LOGGER.error(Message.E0000.getMessage(e.getMessage()));
                request.setAttribute("message", e.getMessage());
                forward = "/WEB-INF/views/error.jsp";
            }
            // 設定された遷移先へ移動
            RequestDispatcher dispatcher = request.getRequestDispatcher(forward);
            dispatcher.forward(request, response);

        }else if(Action.ROOM_SELECT_DELETE.getAction().equals(action)) {

            // 一括削除処理
            try {
                //物件IDと部屋番号に分けて配列に格納
                if (request.getParameterValues("del_id") != null) {
                    String[] delId = request.getParameterValues("del_id");
                    String delRoom[][];
                    delRoom = new String[delId.length][];
                    for(int i = 0 ; i < delId.length ; i++) {
                        delRoom[i] = delId[i].split(",");
                    }
                    //RoomDeleteModelクラスをnewして使えるようにする
                    RoomSelectDeleteModel roomDeleteModel = new RoomSelectDeleteModel();

                    //RoomDeleteModelクラスのdeleteDormitoryメソッドを呼び出す
                    msg = roomDeleteModel.deleteRoom(delRoom);
                }else {
                    msg = "削除する部屋が選択されませんでした。";
                }

                //表示初期化
                RoomMListSearchBean searchBean = new RoomMListSearchBean();

                //部屋一覧を取得
                RoomMListModel roomMListModel = new RoomMListModel();
                List<RoomMBean> listBean = roomMListModel.getRoomMList(searchBean);

                request.setAttribute("msg", msg);
                request.setAttribute(BeanName.ROOM_LIST.getName(), listBean);
                request.setAttribute(BeanName.ROOM_SEARCH.getName(), searchBean);
                forward = "/WEB-INF/views/master/room.jsp";
                LOGGER.info(Message.I0033.getMessage(loginBean.getId()));

            }catch(Exception e){
                LOGGER.error(Message.E0000.getMessage(e.getMessage()));
                request.setAttribute("message", e.getMessage());
                forward = "/WEB-INF/views/error.jsp";
            }
            // 設定された遷移先へ移動
            RequestDispatcher dispatcher = request.getRequestDispatcher(forward);
            dispatcher.forward(request, response);

        }else if ("room_updateform".equals(action)){
            request.setAttribute("dormitoryId",request.getParameter("dormitoryId"));
            request.setAttribute("dormitoryName",request.getParameter("dormitoryName"));
            request.setAttribute("roomId",request.getParameter("roomId"));
            request.setAttribute("plan",request.getParameter("plan"));
            request.setAttribute("squareMeter",request.getParameter("squareMeter"));

            forward = "/WEB-INF/views/master/roomUpdate.jsp";
            msg = "　";
            request.setAttribute("msg", msg);
            RequestDispatcher dispatcher = request.getRequestDispatcher(forward);
            dispatcher.forward(request, response);

        }else if(Action.ROOM_UPDATE.getAction().equals(action)) {

            // 更新処理
            try {
                RoomUpdateBean updateBean = new RoomUpdateBean();
                String dormitoryId =request.getParameter("dormitoryId");
                String dormitoryName =request.getParameter("dormitoryName");
                String roomId =request.getParameter("roomId");
                String floorPlan =request.getParameter("plan");
                //String squareMeter =request.getParameter("squareMeter");
                String updateMeter =request.getParameter("updateMeter");

                RoomMListSearchBean searchBean = null;
                List<RoomMBean> listBean = null;

              //入力判定
                if (updateMeter.matches("[0-9]{1}") || updateMeter.matches("[0-9]{2}")
                        || updateMeter.matches("[0-9]{3}")){
                    updateBean.setDormitoryId(dormitoryId);
                    updateBean.setDormitoryName(dormitoryName);
                    updateBean.setRoomId(roomId);
                    updateBean.setFloorPlan(floorPlan);
                    updateBean.setUpdateMeter(updateMeter);

                    //DormitoryUpdateModelクラスをnewして使えるようにする
                    RoomUpdateModel roomUpdateModel = new RoomUpdateModel();

                    //UserUpdateModelクラスのupdateUserメソッドを呼び出す
                    updateBean = roomUpdateModel.updateRoom(updateBean);
                    forward = "/WEB-INF/views/master/room.jsp";
                    msg = updateBean.getMsg();

                    //表示初期化
                    searchBean = new RoomMListSearchBean();

                    //部屋一覧を取得
                    RoomMListModel roomMListModel = new RoomMListModel();
                    listBean = roomMListModel.getRoomMList(searchBean);
                    LOGGER.info(Message.I0034.getMessage(loginBean.getId()));

                }else {
                    msg = "正しく入力されませんでした。";
                    request.setAttribute("dormitoryId",request.getParameter("dormitoryId"));
                    request.setAttribute("dormitoryName",request.getParameter("dormitoryName"));
                    request.setAttribute("roomId",request.getParameter("roomId"));
                    request.setAttribute("squareMeter",request.getParameter("squareMeter"));
                    forward = "/WEB-INF/views/master/roomUpdate.jsp";
                }

                request.setAttribute("msg", msg);
                request.setAttribute(BeanName.ROOM_LIST.getName(), listBean);
                request.setAttribute(BeanName.ROOM_SEARCH.getName(), searchBean);

            }catch(Exception e){
                LOGGER.error(Message.E0000.getMessage(e.getMessage()));
                request.setAttribute("message", e.getMessage());
                forward = "/WEB-INF/views/error.jsp";
            }
            // 設定された遷移先へ移動
            RequestDispatcher dispatcher = request.getRequestDispatcher(forward);
            dispatcher.forward(request, response);

        }else if(Action.ROOM_DOWNLOAD.getAction().equals(action)) {
             // ダウンロード処理
            try {
                RoomMListSearchBean searchBean = new RoomMListSearchBean();

                searchBean.setDormitoryId(request.getParameter("dormitory_id"));
                searchBean.setDormitoryName(request.getParameter("dormitory_name"));
                searchBean.setRoomId(request.getParameter("room_id"));
                searchBean.setFloorPlan(new String[0]);
                if (request.getParameterValues("plan") != null) {
                    searchBean.setFloorPlan(request.getParameterValues("plan"));
                }
                searchBean.setSquareMeter(request.getParameter("meter"));
                searchBean.setOut(request.getParameter("out"));
                searchBean.setCreateDtFrom(request.getParameter("create_dt_from"));
                searchBean.setCreateDtTo(request.getParameter("create_dt_to"));

                //RoomListModelクラスをnewして使えるようにする
                RoomMListModel roomMListModel = new RoomMListModel();

                //RoomListModelクラスのgetRoomListメソッドを呼び出す
                List<RoomMBean> listBean = roomMListModel.getRoomMList(searchBean);

                if(listBean.size() != 0 && listBean != null) {
                    //ファイル名の取得
                    CsvFileNameBean csvName = new CsvFileNameBean();
                    csvName.setFileName("m_room");
                    String fileName = csvName.getFileName();

                    //文字コードと出力するCSVファイル名を設定
                    response.setContentType("text/csv;charset=Shift-JIS");
                    response.setHeader("Content-disposition","attachment; filename="+fileName);

                    //ファイル書き込み
                    RoomDLModel roomDLModel =  new RoomDLModel();
                    roomDLModel.dlRoom(listBean,fileName, response);

                    LOGGER.info(Message.I0035.getMessage(loginBean.getId()));

                }else {
                    msg="出力するデータがありません。";

                    request.setAttribute("msg", msg);
                    request.setAttribute(BeanName.ROOM_LIST.getName(), listBean);
                    request.setAttribute(BeanName.ROOM_SEARCH.getName(), searchBean);
                    forward = "/WEB-INF/views/master/room.jsp";
                    // 設定された遷移先へ移動
                    RequestDispatcher dispatcher = request.getRequestDispatcher(forward);
                    dispatcher.forward(request, response);
                }
            }catch(Exception e){
                LOGGER.error(Message.E0000.getMessage(e.getMessage()));
                request.setAttribute("message", e.getMessage());
                forward = "/WEB-INF/views/error.jsp";
                // 設定された遷移先へ移動
                RequestDispatcher dispatcher = request.getRequestDispatcher(forward);
                dispatcher.forward(request, response);
            }

        }else{
            // 初期表示
            RoomMListSearchBean searchBean = new RoomMListSearchBean();

            //部屋一覧を取得
            RoomMListModel roomMListModel = new RoomMListModel();
            List<RoomMBean> listBean = null;
            try {
                listBean = roomMListModel.getRoomMList(searchBean);
            } catch (Exception e) {
                LOGGER.error(Message.E0000.getMessage(e.getMessage()));
                request.setAttribute("message", e.getMessage());
                forward = "/WEB-INF/views/error.jsp";
            }

            msg = "一覧表示";
            request.setAttribute("msg", msg);
            request.setAttribute(BeanName.ROOM_LIST.getName(), listBean);
            request.setAttribute(BeanName.ROOM_SEARCH.getName(), searchBean);
            forward = "/WEB-INF/views/master/room.jsp";
            RequestDispatcher dispatcher = request.getRequestDispatcher(forward);
            dispatcher.forward(request, response);
        }
    }

    /**
     * doPost
     * @param request リクエスト
     * @param response レスポンス
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
