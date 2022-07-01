package servlets;

import bean.BookInfo;
import bean.DBBean;
import org.json.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 接受客户端后缀为action的请求，进行处理并返回响应
 *
 */
@WebServlet("*.action")
public class AjaxController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AjaxController() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");

		String actionUrl = request.getServletPath(); // 获取客户端的访问URL地址信息
//		System.out.println(actionUrl+"  !!!");

		if (actionUrl.equals("/list.action")) { // 查询
			ArrayList<BookInfo> list = BookInfo.getBookList(); // 调用BookInfo的getBookList方法完成
			//System.out.println("AC_ls:"+list.size());
            // 使用JSONArray对象将结果构建为json对象并输出到客户端
			JSONArray jsonArray = new JSONArray();

			for (int i = 0; i < list.size(); i++) {
				BookInfo book = list.get(i);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", book.getId());
				map.put("title", book.getTitle());
				map.put("link", book.getLink());
				map.put("keywords", book.getKeywords());
				JSONObject BookObj = new JSONObject(map);
				jsonArray.put(BookObj);
			}
			// 向客户端返回json结果,返回到data
			response.getWriter().print(jsonArray.toString());

		}else if (actionUrl.equals("/cloud.action")){

			request.setCharacterEncoding("utf-8");
			int shuzi=Integer.parseInt(request.getParameter("shuzi"));
			Map<String, Integer>sortMap=BookInfo.getrc();
			JSONArray json =new JSONArray();
			int k=0;
			for (Map.Entry<String, Integer> entry : sortMap.entrySet())
			{
				JSONObject ob=new JSONObject(sortMap);
				try {
					ob.put("name", entry.getKey());
					ob.put("value", entry.getValue());
				} catch (JSONException e) {
					e.printStackTrace();
				}
				if(k==shuzi){
					break;
				}

			}
			System.out.println(json.toString());
			response.getWriter().write(json.toString());
		}
		else if (actionUrl.equals("/select.action")) { // 指定标题查询

			BookInfo bi = new BookInfo();
			bi.setTitle(request.getParameter("title"));
			bi.setLink(request.getParameter("link"));
			bi.setKeywords(request.getParameter("keywords"));
			System.out.println("AC_addaction");
			//String str = request.getParameter("keywords");
			int r = BookInfo.selectOne(bi); // 调用BookInfo的addBook方法完成
			// 向客户端返回结果
			response.getWriter().print(r);

		} /*else if (actionUrl.equals("/select.action")) { // 指定标题查询
			String title = request.getParameter("title");
			BookInfo bi = BookInfo.selectOne(title); // 调用BookInfo的selectOne方法完成

			System.out.println("AC_bibi:"+bi);
			System.out.println("AC_bi:"+bi.getTitle());

			// 将该对象构建为json数据
			Map<String, Object> map = new HashMap<String, Object>();//无序的
			map.put("id", bi.getId());
			map.put("title", bi.getTitle());
			map.put("link", bi.getLink());
			map.put("keywords", bi.getKeywords());

			System.out.println("AC_map:"+map);

			JSONObject BookObj = new JSONObject(map);//通过json传值

			System.out.println("AC_JSON:"+BookObj);
			// 向客户端返回结果
			response.getWriter().print(BookObj.toString());

		} */
		/*else if (actionUrl.equals("/select.action")) { // 指定标题查询

			String title = request.getParameter("title");
			String keywords = request.getParameter("keywords");

			String sql ="select * from l where 1=1 ";
			if(!title.equals("")){
				sql=sql+"and title like" + "\"%" +title + "%\"";
			}else if(!keywords.equals("")){
				sql=sql+"and keywords like" + "\"%" +keywords + "%\"";
			}

			ArrayList<BookInfo> list = BookInfo.getBookList(); // 调用BookInfo的getBookList方法完成
			DBBean jdbc = new DBBean();
			ResultSet rs = jdbc.executeQuery(sql);
			try {
				while (rs.next()) {
					BookInfo bi = new BookInfo();
					bi.setId(rs.getString("id"));
					bi.setTitle(rs.getString("title"));
					bi.setLink(rs.getString("link"));
					bi.setKeywords(rs.getString("keywords"));
					list.add(bi);
//				System.out.println("getBookList():"+bi.getId());
				}
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			jdbc.close();
			//System.out.println("AC_ls:"+list.size());
			// 使用JSONArray对象将结果构建为json对象并输出到客户端
			JSONArray jsonArray = new JSONArray();
			for (int i = 0; i < list.size(); i++) {
				BookInfo book = list.get(i);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", book.getId());
				map.put("title", book.getTitle());
				map.put("link", book.getLink());
				map.put("keywords", book.getKeywords());
//				System.out.println("AC_id"+book.getId());
				JSONObject BookObj = new JSONObject(map);
				jsonArray.put(BookObj);
			}
			// 向客户端返回json结果,返回到data
			response.getWriter().print(jsonArray.toString());


		}*/else if (actionUrl.equals("/add.action")) { // 增加

			BookInfo bi = new BookInfo();
			bi.setTitle(request.getParameter("title"));
			bi.setLink(request.getParameter("link"));
			bi.setKeywords(request.getParameter("keywords"));
            System.out.println("AC_addaction");
			//String str = request.getParameter("keywords");
			int r = BookInfo.addBook(bi); // 调用BookInfo的addBook方法完成
			// 向客户端返回结果
			response.getWriter().print(r);

		} else if (actionUrl.equals("/update.action")) { // 更新
			BookInfo bi = new BookInfo();
			bi.setId(request.getParameter("id"));
			bi.setTitle(request.getParameter("title"));
			bi.setLink(request.getParameter("link"));
			bi.setKeywords(request.getParameter("keywords"));

			System.out.println("AC_updateaction");
			System.out.println("AC_bi:"+bi.getId());
			System.out.println("AC_bi:"+bi.getTitle());

			int rs = BookInfo.updateBook(bi);// 调用BookInfo的updateBook方法完成

			System.out.println("AC_rs:"+rs);

			response.getWriter().print(rs); // 向客户端返回结果

		} else if (actionUrl.equals("/delete.action")) { // 删除
			String id = request.getParameter("id");
			int rs = BookInfo.deleteBook(id); // 调用BookInfo的deleteBook方法完成
			System.out.println("AC_deleteaction");
			response.getWriter().print(rs); // 向客户端返回结果
		}
	}

}
