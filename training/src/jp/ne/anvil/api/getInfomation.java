package jp.ne.anvil.api;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class getInfomation
 */
@WebServlet("/api")
public class getInfomation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Gson gson = new Gson();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getInfomation() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

	    try {
	        //社員寮の件数を取得
	        DormitoryInfoBean bean = new DormitoryInfoBean();
	        DormitoryCount dormitoryCount = new DormitoryCount();
            bean = dormitoryCount.getDormitoryInfo();

            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().println(gson.toJson(bean));

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
