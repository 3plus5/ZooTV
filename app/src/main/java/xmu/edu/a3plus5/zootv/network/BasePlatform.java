package xmu.edu.a3plus5.zootv.network;

import java.util.List;


import xmu.edu.a3plus5.zootv.entity.Category;
import xmu.edu.a3plus5.zootv.entity.Room;

public abstract class BasePlatform {
	public static final String DouYu = "DouYu";
	public static final String Huya = "Huya";
	public static final String Panda = "Panda";
	public static final String ZhanQi = "ZhanQi";
	public static final String QuanMin = "QuanMin";
	public static final String Zoo = "Zoo";
	protected List<Category> categories = null;

	//按分类获取直播间列表
	public abstract List<Room> getByCategory(Category category, int page);
	//获取最热直播间列表
	public abstract List<Category> getPopularCategory();
	//获取搜索列表
	public abstract List<Room> search(String keyword);
	//获取所有分类
	public abstract List<Category> getAllCategory();
	//获取最热分类
	public abstract List<Room> getMostPopular();
	//根据id获取直播间
	public abstract Room getRoomById(String id);

	public Category getCategoryByName(String name)
	{
		List<Category> cates = getAllCategory();
		for(Category cate : cates)
		{
			if(cate.getName().equals(name))
				return cate;
		}
		return null;
	}
}
