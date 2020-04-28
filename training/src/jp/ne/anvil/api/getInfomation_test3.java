package jp.ne.anvil.api;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import jp.ne.anvil.master.room.RoomMBean;

/**
 * Servlet implementation class getInfomation
 */
@WebServlet("/api4")
public class getInfomation_test3 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Gson gson = new Gson();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getInfomation_test3() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

	    try {
	        //部屋一覧を取得
	        List<RoomMBean> listBean = new ArrayList<>();
	        Room_test room = new Room_test();
	        listBean = room.getRoom();

            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().println(gson.toJson(listBean));

        } catch (Exception e) {
            e.printStackTrace();
        }

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
