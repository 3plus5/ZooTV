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

public class HuYaPlatform extends BasePlatform{
	private  List<Room> parseFromDoc(Document doc)
	{
		List<Room> rooms = new ArrayList<Room>();
		Elements elements = doc.select("li[class = video-list-item]");
		for(Element element : elements)
		{
			String link = element.select("a[class = clickstat]").attr("href");
			String title = element.select("a[class = clickstat]").text();
			String picPath = element.select("img[class = pic]").attr("src");
			String watchingNum = element.select("i[class = js-num").text();
			String anchor = element.select("i[class = nick]").text();
			long num = getPopularity(watchingNum);
			
			Room room = new Room();
			rooms.add(room);
			System.out.println(room);
		}
		return rooms;
	}
	
	@Override
	public List<Room> getByCateGory(Category category, int page) {
		Document doc = null;
		try {
			doc = Jsoup.connect(category.getHuyaUrl()).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<Room> rooms  = parseFromDoc(doc); 
		return rooms;
	}

	@Override
	public List<Room> search(String keyword) {
		Document doc = null;
		try {
			keyword = URLEncoder.encode(keyword, "utf-8");
			
			doc = Jsoup.connect("http://www.huya.com/search.php?hsk=" + keyword).get();
			System.out.println(doc);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<Room> rooms  = parseFromDoc(doc); 
		return rooms;
	}

	@Override
	public List<Category> getAllCategory() {
		Document doc = null;
		try {
			doc = Jsoup.connect("http://www.huya.com/g" ).get();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Elements elements = doc.select("li[class ^= game-list-item]");
		List<Category> categories = new ArrayList<>();
		for(Element e : elements)
		{
			Category c = new Category();
			c.setName(e.select("img").attr("alt"));
			c.setPicUrl(e.select("img").attr("src"));
			c.setHuyaUrl(e.select("a").attr("href"));
			categories.add(c);
		}
		System.out.println(categories);
		return categories;
	}

	@Override
	public List<Room> getMostPopular() {
		return null;
	}

	public static void main(String[] args)
	{
		HuYaPlatform huYaPlatform = new HuYaPlatform();
		Category category  = new Category();
		category.setHuyaUrl("http://www.huya.com/g/hearthstone");
		huYaPlatform.search("overwatch");
	}

	@Override
	public List<Category> getPopularCategory() {
		// TODO Auto-generated method stub
		return null;
	}
}
