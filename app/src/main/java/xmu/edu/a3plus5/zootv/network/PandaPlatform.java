package xmu.edu.a3plus5.zootv.network;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import xmu.edu.a3plus5.zootv.entity.Category;
import xmu.edu.a3plus5.zootv.entity.Room;


public class PandaPlatform extends BasePlatform{

	@Override
	public List<Room> getByCateGory(Category category, int page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Room> search(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Category> getAllCategory() 
	{
		String link = "http://www.panda.tv/cate";
		
		try 
		{
			Document document = Jsoup.connect(link).get();
			System.out.println(document);
			
			Elements elements = document.select("li");
			
			System.out.println(elements.size());
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Room> getMostPopular() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Category> getPopularCategory() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static void main(String[] args)
	{
		//List<Category> categories = new DouYuPlatform().getAllCategory();
		//System.out.println(categories);
		//System.out.println(new DouYuPlatform().getPopularCategory());
		new PandaPlatform().getAllCategory();
		//new DouYuPlatform().getMostPopular();
	}

}
