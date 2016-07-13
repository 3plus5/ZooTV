package xmu.edu.a3plus5.zootv.network;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import xmu.edu.a3plus5.zootv.entity.Category;
import xmu.edu.a3plus5.zootv.entity.Room;


public class ZooPlatform extends BasePlatform{
	private List<BasePlatform> platforms;
	
	public ZooPlatform() {
		platforms = new ArrayList<>();
		platforms.add(PlatformFactory.createPlatform(DouYu));
		platforms.add(PlatformFactory.createPlatform(Panda));
	}
	

	@Override
	public List<Room> getByCategory(Category category, int page) {
		List<Room> rooms = new ArrayList<>();
		for(BasePlatform platform : platforms)
		{
			List<Room> temp = platform.getByCategory(category, page);
			if(temp != null)
				rooms.addAll(temp);
		}
		Collections.sort(rooms);
		return rooms;
	}

	@Override
	public List<Category> getPopularCategory() {
		List<Category> categories = getAllCategory();
		if(categories == null){
			return null;
		}
		categories = categories.subList(0, 8);
		return categories;
	}

	@Override
	public List<Room> search(String keyword) {
		List<Room> rooms = new ArrayList<>();
		for(BasePlatform platform : platforms)
			rooms.addAll(platform.search(keyword));
		Collections.sort(rooms);
		return rooms;
	}

	@Override
	public List<Category> getAllCategory() {
		List<Category> categories = platforms.get(0).getAllCategory();
		for(int i = 1; i < platforms.size(); i++)
		{
			List<Category> cates = platforms.get(i).getAllCategory();
			for(Category t : categories)
				for(Category c: cates)
				{
					if(t.getName().equals(c.getName()))
					{
						t.getCateMap().putAll(c.getCateMap());
					}
				}
		}
		
		List<Category> cates = new ArrayList<>();
		for(Category category : categories)
		{
			if(category.getCateMap().size() > 1)
				cates.add(category);
		}
		return cates;
	}

	@Override
	public List<Room> getMostPopular() {
		List<Room> rooms = new ArrayList<>();
		for(BasePlatform platform : platforms)
			rooms.addAll(platform.getMostPopular());
		Collections.sort(rooms);
		return rooms;
	}

	@Override
	public Room getRoomById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args)
	{
		ZooPlatform zootv = new ZooPlatform();
		List<Category> categories = zootv.getAllCategory();
		System.out.println(categories);
//		System.out.println(zootv.getByCategory(categories.get(1), 1));
		System.out.println(zootv.getMostPopular());
	}
}
