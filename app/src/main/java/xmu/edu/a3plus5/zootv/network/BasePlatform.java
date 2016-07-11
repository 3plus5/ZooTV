package xmu.edu.a3plus5.zootv.network;

import java.util.List;

import xmu.edu.a3plus5.zootv.entity.Category;
import xmu.edu.a3plus5.zootv.entity.Room;

public abstract class BasePlatform {
	public static final String DouYu = "DouYu";
	public static final String Huya = "Huya";
	public static final String Panda = "Panda";

	public abstract List<Room> getByCateGory(Category category, int page);
	public abstract List<Category> getPopularCategory();
	public abstract List<Room> search(String keyword);
	public abstract List<Category> getAllCategory();
	public abstract List<Room> getMostPopular();

	protected long getPopularity(String watchingNum)
	{
		String num;
		if(watchingNum.contains("万"))
		{
			return (long) (Float.parseFloat(watchingNum.substring(0, watchingNum.length() - 1)) * 10000);
		}
		else
			return Long.parseLong(watchingNum);
	}

	protected String getWatchingNum(long popularity)
	{
		return null;
	}
}