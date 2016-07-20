package xmu.edu.a3plus5.zootv.network;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import xmu.edu.a3plus5.zootv.entity.Category;
import xmu.edu.a3plus5.zootv.entity.Room;

public class PandaPlatform extends BasePlatform 
{
    private HashMap<String, String> cateMap;
    private String baseLink = "http://m.panda.tv/room.html?roomid=";

    public PandaPlatform() 
    {
    	super.statusMap = new HashMap<Integer, Integer>();
    	super.statusMap.put(2, 1);
    	super.statusMap.put(3, 0);
    	
        this.initialCategoryNameMap();
    }

    @Override
    public List<Room> getByCategory(Category category, int page) {
        String catUrl = category.getCateUrl(Panda);
        if (catUrl == null)
            return null;

        String jsonLink = catUrl + String.format("&pageno=%d", page);
        return this.getRoomListByPandaAPI(jsonLink);
    }

    @Override
    public List<Room> search(String keyword) {
        try {
            keyword = URLEncoder.encode(keyword, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String link = String.format("http://www.panda.tv/ajax_search?name=%s&order_cond=fans&pageno=%d&pagenum=%d",
                keyword, 1, 1);

        return this.getRoomListBySearchAjaxUrl(link);
    }

    @Override
    public List<Category> getAllCategory() 
    {
        if (categories != null)
            return categories;
        
        categories = new ArrayList<Category>();

        String jsonLink = "http://static.api.m.panda.tv/android_hd/cate.json";

        try 
        {
            String jsonStr = Jsoup.connect(jsonLink).ignoreContentType(true).execute().body();

            JSONObject json = new JSONObject(jsonStr);

            JSONArray array = json.getJSONArray("data");

            for (int i = 0; i < array.length(); i++) 
            {
                Category cate = new Category();

                JSONObject obj = array.getJSONObject(i);

                cate.setName(obj.getString("cname"));
                cate.setCateUrl(Panda, "http://static.api.m.panda.tv/android_hd/catelist_.json?cate=" + obj.getString("ename"));
                //cate.setPandaUrl("http://static.api.m.panda.tv/android_hd/catelist_.json?cate=" + obj.getString("ename"));

                cate.setPicUrl(obj.getString("img"));

                categories.add(cate);
            }
        } catch (IOException | JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return categories;
    }

    //一次返回12个
    @Override
    public List<Room> getMostPopular() 
    {
        List<Room> roomList = new ArrayList<Room>();

        for (int i = 1; i <= 12; i++) 
        {
            String jsonLink = "http://static.api.m.panda.tv/android_hd/alllist_.json?pageno=" + i;
            List<Room> rooms = this.getRoomListByPandaAPI(jsonLink);
            if(rooms != null)
                roomList.addAll(rooms);
        }

        return roomList;
    }

    @Override
    public Room getRoomById(String id)
    {
        Room room = null;

        try 
        {
            id = URLEncoder.encode(id, "utf-8");
        }
        catch (UnsupportedEncodingException e) 
        {
            e.printStackTrace();
        }

        String jsonLink = "http://api.m.panda.tv/ajax_search?roomid=" + id;
        
		try 
		{
			 String jsonStr = Jsoup.connect(jsonLink).ignoreContentType(true).execute().body();
			
			JSONObject json = new JSONObject(jsonStr);
			
			JSONArray array = json.getJSONObject("data").getJSONArray("items");
			
			if(array.length() > 0)
			{
				JSONObject obj = array.getJSONObject(0);
				
				room = new Room(Panda);
				
				room.setAnchor(obj.getString("nickname"));
				room.setRoomId(obj.getString("roomid"));
				room.setLink(this.baseLink + room.getRoomId());
				room.setTitle(obj.getString("name"));
				room.setCate(obj.getString("classification"));
				
				room.setPopularity(obj.getLong("person_num"));
				room.setWatchingNumByPopularity();
				
				JSONObject picObj = obj.getJSONObject("pictures");
				room.setPicUrl(picObj.getString("img"));
				
                room.setStatus(super.statusMap.get(obj.getInt("status")));
			}
			
		} 
		catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
       

        return room;
    }

    private List<Room> getRoomListBySearchAjaxUrl(String link) {
        ArrayList<Room> ret = new ArrayList<Room>();

        try {
            Document doc = Jsoup.connect(link).get();

            System.out.println(doc);
            
            String jsonStr = doc.body().text();

            JSONObject json = new JSONObject(jsonStr);

            JSONArray array = json.getJSONObject("data").getJSONArray("items");

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);

                Room room = new Room(Panda);

                room.setRoomId(obj.getString("roomid"));
                room.setAnchor(obj.getString("nickname"));
                room.setTitle(obj.getString("name"));
                room.setCate(obj.getString("classification"));
                
                JSONObject pic = obj.getJSONObject("pictures");
                room.setPicUrl(pic.getString("img"));
                
                room.setPopularity(obj.getLong("person_num"));
                room.setWatchingNumByPopularity();

                room.setLink("http://m.panda.tv/room.html?roomid=" + room.getRoomId());
                
//                room.setStatus(super.statusMap.get(obj.get("status")));

                ret.add(room);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return ret;
    }

    private List<Room> getRoomListByPandaAPI(String jsonLink) {
        ArrayList<Room> ret = new ArrayList<Room>();

        try {
            String jsonStr = Jsoup.connect(jsonLink).ignoreContentType(true).execute().body();

            JSONObject json = new JSONObject(jsonStr);

            JSONArray array = json.getJSONObject("data").getJSONArray("items");

            for (int i = 0; i < array.length(); i++) {
                Room r = new Room(Panda);

                JSONObject obj = array.getJSONObject(i);

                r.setRoomId(obj.getString("id"));
                r.setLink("http://m.panda.tv/room.html?roomid=" + r.getRoomId());

                r.setTitle(obj.getString("name"));

                r.setPopularity(obj.getLong("person_num"));

                r.setWatchingNumByPopularity();

                String ename;

                JSONObject classificationObj = null;

                if ((classificationObj = obj.optJSONObject("classification")) != null) {
                    ename = classificationObj.getString("ename");
                } else
                    ename = obj.getString("classification");

                //r.setCate(cateMap.get(obj.getString("classification")));
                r.setCate(cateMap.get(ename));

                JSONObject picObj = obj.getJSONObject("pictures");
                r.setPicUrl(picObj.getString("img"));

                JSONObject userObj = obj.getJSONObject("userinfo");
                r.setAnchor(userObj.getString("nickName"));

                ret.add(r);
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }

        return ret;
    }

    private void initialCategoryNameMap() {
        cateMap = new HashMap<String, String>();

        String jsonLink = "http://static.api.m.panda.tv/android_hd/cate.json";

        try {
            String jsonStr = Jsoup.connect(jsonLink).ignoreContentType(true).execute().body();

            JSONObject json = new JSONObject(jsonStr);

            JSONArray array = json.getJSONArray("data");

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);

                cateMap.put(obj.getString("ename"), obj.getString("cname"));
            }
        } catch (IOException | JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static void main(String[] args) 
    {
        List<Category> categories = new PandaPlatform().getPopularCategory();
        
        for(Category cate:categories)
        	System.out.println(cate);
    }

}
