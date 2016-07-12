package xmu.edu.a3plus5.zootv.network;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import xmu.edu.a3plus5.zootv.entity.Category;
import xmu.edu.a3plus5.zootv.entity.Room;

public class DouYuPlatform extends BasePlatform{
	
	private  List<Room> parseFromDoc(Document doc)
	{
		if(doc == null)
			return null;
		
		List<Room> rooms = new ArrayList<Room>();
		Elements elements = doc.select("li[data-rid]");
		for(Element element : elements)
		{
			String roomId = element.attr("data-rid");
			String link = "http://www.douyu.com/" + roomId;
			String title = element.select("h3[class = ellipsis]").text();
			String picUrl = element.select("img").attr("data-original");
			String watchingNum = element.select("span[class = dy-num fr]").text();
			String anchor = element.select("span[class = dy-name ellipsis fl]").text();
			String cate = element.select("span[class = tag ellipsis]").text();
			long popularity = Room.getPopularity(watchingNum);
			String platform = BasePlatform.DouYu;
			Room room = new Room(link, title, anchor, picUrl, popularity, watchingNum, platform, roomId, cate);
			rooms.add(room);
		}
		return rooms;
	}

	@Override
	public List<Room> getByCategory(Category category, int page) {
		Document doc = null;
		String getRequest = String.format("http://www.douyu.com%s?page=%d&isAjax=1", category.getCateUrl(DouYu), page);
		try {
			doc = Jsoup.connect(getRequest).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return parseFromDoc(doc);
	}

	@Override
	public List<Room> search(String keyword) {
		Document doc = null;
		try {
			keyword = URLEncoder.encode(keyword, "utf-8");
			doc = Jsoup.connect("http://www.douyu.com/search/" + keyword).get();
			
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return parseFromDoc(doc);
	}
	
	@Override
	public List<Category> getAllCategory() {
		Document doc = null;
		try {
			doc = Jsoup.connect("http://www.douyu.com/directory" ).get();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Elements elements = doc.select("li[class ^= unit]");
		List<Category> categories = new ArrayList<>();
		for(Element e : elements)
		{
			Category c = new Category();
			c.setName(e.select("p").text());
			c.setPicUrl(e.select("img").attr("data-original"));
			c.setCateUrl(DouYu, e.select("a").attr("href"));
			//c.setDouyuUrl(e.select("a").attr("href"));
			categories.add(c);
		}
		return categories;
	}

	
	@Override
	public List<Room> getMostPopular() {
		Document doc = null;
		try {
			doc = Jsoup.connect("http://www.douyu.com/directory/all?page=1&isAjax=1" ).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return parseFromDoc(doc);
	}
	
	@Override
	public List<Category> getPopularCategory() {
		List<Category> categories = getAllCategory();
		categories = categories.subList(0, 8);
		return categories;
	}



	@Override
	public Room getRoomById(String id) {
		List<Room> rooms = search(id);
		for(Room room : rooms)
			if(room.getRoomId().equals(id))
				return room;
		return null;
	}

	
	public static void main(String[] args)
	{
		List<Category> categories = new DouYuPlatform().getAllCategory();
		System.out.println(new DouYuPlatform().getByCategory(categories.get(0), 1));
	}
}
