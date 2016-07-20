package xmu.edu.a3plus5.zootv.network;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import xmu.edu.a3plus5.zootv.entity.Category;
import xmu.edu.a3plus5.zootv.entity.Room;

public abstract class BasePlatform 
{
    public static final String DouYu = "DouYu";
    public static final String Huya = "Huya";
    public static final String Panda = "Panda";
    public static final String ZhanQi = "ZhanQi";
    public static final String QuanMin = "QuanMin";
    public static final String Zoo = "Zoo";
    protected List<Category> categories = null;
    protected Map<Integer, Integer> statusMap;
    
    //按分类获取直播间列表
    public abstract List<Room> getByCategory(Category category, int page);

    //获取最热分类列表
    //public abstract List<Category> getPopularCategory();

    //获取搜索列表
    public abstract List<Room> search(String keyword);

    //获取所有分类
    public abstract List<Category> getAllCategory();

    //获取最热直播间列表，一次返回120个
    public abstract List<Room> getMostPopular();

    //根据id获取直播间
    public abstract Room getRoomById(String id);
    
    //获取最热分类列表
    public List<Category> getPopularCategory()
    {
    	List<Category> cateList = getAllCategory();
    	
    	int end = cateList.size() >= 8 ? 8 :cateList.size();
    	
    	return cateList.subList(0, end);
    }
    
    //根据分类名获得分类
    public Category getCategoryByName(String name) {
        List<Category> cates = getAllCategory();
        for (Category cate : cates) {
            if (cate.getName().equals(name))
                return cate;
        }
        return null;
    }

    //根据页数获得最热直播间，默认一页8个
    public List<Room> getMostPopularByPage(int page) 
    {
        int begin = (page - 1) * 8;
        int end = page * 8;
        List<Room> roomList = this.getMostPopular();
        
        int max = roomList.size();
        
        end = (end > max) ? max : end;

        return roomList.subList(begin, end);
    }

    //根据传入的分类List返回推荐的房间
    public List<Room> getRecommendedRoomByCateList(List<Category> cateList) {
        List<Room> roomList = new ArrayList<Room>();

        //如果传入的分类列表为空，则默认返回当前最热直播
        if (cateList.size() == 0 || cateList == null)
            roomList.addAll(this.getMostPopularByPage(1));
        else {
            int[] portion = this.getPortionByCateListSize(cateList.size());

            for (int j = 0; j < portion.length; j++) {
                List<Room> rooms = this.getByCategory(cateList.get(j), 1);
                if(rooms.size() != 0) {
                    if(rooms.size() >= portion[j]) {
                        roomList.addAll(rooms.subList(0, portion[j]));
                    }else{
                        roomList.addAll(rooms);
                    }
                }
            }
        }

        return roomList;
    }

    //根据列表的大小返回每个分类所占的比例
    protected int[] getPortionByCateListSize(int size) 
    {
        int all = 8;

        if (size > 5) size = 5;

        int[] portion = new int[size];

        int every = all / size;

        for (int i = 0; i < portion.length; i++) {
            portion[i] = every;
            all -= every;
        }

        int i = 0;
        while (all > 0) {
            portion[i]++;
            all--;
            i++;

            if (i == size) i = 0;
        }

        return portion;
    }

    public static void main(String[] args) 
    {
            List<Integer> a = new ArrayList<Integer>();
            a.add(1);
            a.add(2);
            a.add(3);
            a.add(4);
            
            List<Integer> b = a.subList(0, a.size());
            System.out.print(b);
            
            int end = 8;
            int max = 10;
            
            end = (end > max) ? max : end;
            
            System.out.print(end);
    }
}
