package servlets;

import bean.BookInfo;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 用来接收客户端的后缀为do的请求
 *
 */
@WebServlet("*.do")
public class BookController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		String actionUrl = request.getServletPath(); // 获取客户请求的Servlet地址

		if (actionUrl.equals("/index.do")) { // 查询所有图书
			ArrayList<BookInfo> list = BookInfo.getBookList(); // 调用BookInfo的getBookList方法查询所有图书，赋值给list
			request.setAttribute("list", list); // 在request增加属性list，其结果为list对象
			request.getRequestDispatcher("/index.jsp").forward(request, response);// 重定向至index.jsp进行显示

		} else if (actionUrl.equals("/addview.do")) { // 新增图书显示页面
            System.out.println("BC_addviewdo");
			request.getRequestDispatcher("add.html").forward(request, response); 
		} else if (actionUrl.equals("/add.do")) { // 新增图书
			BookInfo bi = new BookInfo();
			bi.setTitle(request.getParameter("title"));
			bi.setLink(request.getParameter("link"));
			bi.setKeywords(request.getParameter("keywords"));
			int r = BookInfo.addBook(bi); // 调用BookInfor的addBook方法完成
			if (r == 1){
                System.out.println("BC_adddo");
				request.getRequestDispatcher("success.html").forward(request, response); // 成功的话重定向至success.html
			}
			else{
				request.getRequestDispatcher("failure.html").forward(request, response); // 失败的话重定向至failure.html
			}

		} else if (actionUrl.equals("/update.do")) { // 用户输入要修改的图书的信息之后需要保存到数据库
			BookInfo bi = new BookInfo();
			bi.setId(request.getParameter("id"));
			bi.setTitle(request.getParameter("title"));
			bi.setLink(request.getParameter("link"));
			bi.setKeywords(request.getParameter("keywords"));
			int r = BookInfo.updateBook(bi);// 调用BookInfo的updateBook方法实现
			if (r == 1){
                System.out.println("BC_updatedo");
				request.getRequestDispatcher("/success.html").forward(request, response);// 成功的话重定向至success.html

			}
			else{
				request.getRequestDispatcher("/failure.html").forward(request, response);// 失败的话重定向至failure.html

			}

		} else if (actionUrl.equals("/delete.do")) { // 用户需要删除指定id的图书
			String id = request.getParameter("id");
			int r = BookInfo.deleteBook(id); // 调用BookInfo的deleteBook方法实现
			if (r == 1){
				request.getRequestDispatcher("/success.html").forward(request, response);// 成功的话重定向至success.html
			}
			else{
				request.getRequestDispatcher("/failure.html").forward(request, response);// 失败的话重定向至failure.html
			}
		}
	}

}
