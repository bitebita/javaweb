package bean;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 构造BookInfo对象，对应数据库的BookInfo表 提供了多个静态方法完成BookInfo对象与对应数据库表的所有新增、查询、修改、删除等操作
 *
 */
public class BookInfo {

	private String id;
	private String title;
	private String link;
	private String keywords;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

    /*public BookInfo(String id, String title, String link, String keywords) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.keywords = keywords;
    }

	public BookInfo() {
	}*/



    //操作函数	从数据库中获取数据
	//查询所有
	public static ArrayList<BookInfo> getBookList() {
		ArrayList<BookInfo> list = new ArrayList<BookInfo>();
		String sql = "select * from l";
	    DBBean jdbc= new DBBean();
		ResultSet rs = jdbc.executeQuery(sql);
//		System.out.println(rs);
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
//        list.add(new BookInfo("3","Hello","8.131.54.109","xxx"));
		return list;
	}





	public static int selectOne(BookInfo bi) {

		int result = 0;
		String sql = "insert into l values('" + bi.getTitle() + "','" + bi.getLink() + "','" + bi.getKeywords() + "',null)";
		DBBean jdbc = new DBBean();
		result = jdbc.executeUpdate(sql);
		jdbc.close();
		return result;
	}

	//更新数据
	public static int updateBook(BookInfo bi) {
		int result = 0;
		//where id='" + bi.getId()+"'
		String sql = "update l  set title='" + bi.getTitle() + "',link='" + bi.getLink() + "',keywords='"+ bi.getKeywords() +"' "+"where id='" + bi.getId()+"'" ;
		System.out.println("BI_sql:"+sql);
		DBBean jdbc = new DBBean();
		result = jdbc.executeUpdate(sql);
		System.out.println("BI_rs:"+result);
		jdbc.close();
		return result;
	}

	//删除
	public static int deleteBook(String id) {
		int result = 0;
		String sql = "delete from l where id="+ id;
		DBBean jdbc = new DBBean();
		result = jdbc.executeUpdate(sql);
		jdbc.close();
		return result;
	}

	//添加
	public static int addBook(BookInfo bi) {
		int result = 0;
		String sql = "insert into l values('" + bi.getTitle() + "','" + bi.getLink() + "','" + bi.getKeywords() + "',null)";
		DBBean jdbc = new DBBean();
		result = jdbc.executeUpdate(sql);
		jdbc.close();
		return result;
	}

	//词云图
	public static Map<String,Integer> getrc(){
		int tnumi=0;
		String sql= "select * from l";
		Map<String, Integer> map= new HashMap<String, Integer>();
		Map<String, Integer> results= new LinkedHashMap<String, Integer>();
		Statement st=null;
		ResultSet rs=null;
		try {
			st.executeQuery(sql);
			rs=st.executeQuery(sql);
			while(rs.next())
			{
				String keywords=rs.getString("title");
				keywords=keywords.substring(1, keywords.length());//substring(a,b)截取从a到b的字符串
				String[] split = keywords.split(" ");
				for(int i=0;i<split.length;i++)
				{
					if(map.get(split[i])==null)
					{
						map.put(split[i],1);
					}
					else
					{
						map.replace(split[i], map.get(split[i])+1);

					}
				}

				tnumi++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("of的个数为"+map.get("of")+"MAP个数"+map.size());
		map.entrySet()
				.stream()
				.sorted((p1, p2) -> p2.getValue().compareTo(p1.getValue()))
				.collect(Collectors.toList())
				.forEach(ele -> results.put(ele.getKey(), ele.getValue()));
		return results;
	}
}
