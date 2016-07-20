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
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import xmu.edu.a3plus5.zootv.entity.Category;
import xmu.edu.a3plus5.zootv.entity.Room;

public class ZhanQiPlatform extends BasePlatform {
    private final String baseLink = "http://www.zhanqi.tv";

    public ZhanQiPlatform() {
        super.statusMap = new HashMap<Integer, Integer>();
        super.statusMap.put(0, 0);
        super.statusMap.put(4, 1);
        //this.initialMap();
    }

    @Override
    public List<Room> getByCategory(Category category, int page) {
        String catUrl = category.getCateUrl(ZhanQi);

        if (catUrl == null)
            return null;

        String jsonLink = String.format(catUrl, page);

        return this.getRoomListByZhanQiAPI(jsonLink);
    }

    @Override
    public List<Category> getAllCategory() {
        if (categories != null)
            return categories;

        List<Category> categories = new ArrayList<Category>();

        String jsonLink = "http://www.zhanqi.tv/api/static/game.lists/100-1.json";

        try {
            String jsonStr = Jsoup.connect(jsonLink).ignoreContentType(true).execute().body();

            JSONObject json = new JSONObject(jsonStr);

            JSONObject dataObject = json.getJSONObject("data");

            JSONArray gameArray = dataObject.getJSONArray("games");

            for (int i = 0; i < gameArray.length(); i++) {
                Category c = new Category();

                JSONObject obj = gameArray.getJSONObject(i);

                String url = String.format("http://www.zhanqi.tv/api/static/game.lives/%d", obj.getInt("id")) + "/10-%d.json";

                c.setCateUrl(ZhanQi, url);

                c.setName(obj.getString("name"));

                c.setPicUrl(obj.getString("spic"));

                categories.add(c);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return categories;
    }

    @Override
    public List<Room> search(String keyword) {
        List<Room> roomList = new ArrayList<Room>();

        try {
            keyword = URLEncoder.encode(keyword, "utf-8");

            Document doc = Jsoup.connect("http://www.zhanqi.tv/search?q=" + keyword).get();

            Elements elements = doc.select("a.js-jump-link");

            for (Element element : elements) {
                Room r = new Room(ZhanQi);

                r.setLink(this.baseLink + element.attr("href"));

                r.setPicUrl(element.select("img").attr("src"));

                r.setTitle(element.select("span.name").text());

                r.setPopularity(Long.valueOf(element.select("span[class = dv]").text()));
                r.setWatchingNumByPopularity();

                r.setAnchor(element.select("span[class ^= anchor]").text());

                r.setCate(element.select("span[class ^= game-name]").text());

                r.setRoomId(this.getRoomIdByRoomUrl(r.getLink()));

                roomList.add(r);
            }


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return roomList;
    }

    //一次返回120个
    @Override
    public List<Room> getMostPopular() {
        String jsonLink = "http://www.zhanqi.tv/api/static/live.hots/120-1.json";

        return this.getRoomListByZhanQiAPI(jsonLink);
    }

    @Override
    public Room getRoomById(String id) {
        Room room = null;

        try {
            id = URLEncoder.encode(id, "utf-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }

        String jsonLink = String.format("http://www.zhanqi.tv/api/static/live.roomid/%s.json?sid=", id);

        try {
            String jsonStr = Jsoup.connect(jsonLink).ignoreContentType(true).execute().body();

            JSONObject json = new JSONObject(jsonStr);

            if (json.getInt("code") == 1) return null;

            JSONObject dataObject = json.getJSONObject("data");

            room = new Room(ZhanQi);

            room.setRoomId(dataObject.getString("id"));

            room.setAnchor(dataObject.getString("nickname"));

            room.setLink(this.baseLink + dataObject.getString("url"));

            room.setTitle(dataObject.getString("title"));

            room.setPicUrl(dataObject.getString("spic"));

            room.setPopularity(dataObject.getLong("online"));
            room.setWatchingNumByPopularity();

            room.setCate(dataObject.getString("gameName"));

            room.setStatus(super.statusMap.get(dataObject.getInt("status")));
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return room;

    }

    //根据房间的URL获取该房间ID,房间url用于进入该直播间,房间ID用于搜索该房间,只有战旗TV需要这么做
    private String getRoomIdByRoomUrl(String url) {
        String roomId = null;

        try {
            Document doc = Jsoup.connect(url).get();

            String href = doc.select("a.report-room-bg").attr("href");

            roomId = href.substring(href.lastIndexOf("/") + 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return roomId;
    }

    private List<Room> getRoomListByZhanQiAPI(String jsonLink) {
        ArrayList<Room> roomList = new ArrayList<Room>();

        try {
            String jsonStr = Jsoup.connect(jsonLink).ignoreContentType(true).execute().body();

            JSONObject json = new JSONObject(jsonStr);

            JSONObject dataObject = json.getJSONObject("data");

            JSONArray array = dataObject.getJSONArray("rooms");

            for (int i = 0; i < array.length(); i++) {
                Room r = new Room(ZhanQi);

                JSONObject obj = array.getJSONObject(i);

                r.setAnchor(obj.getString("nickname"));

                r.setRoomId(obj.getString("id"));

                r.setLink(this.baseLink + obj.getString("url"));

                r.setTitle(obj.getString("title"));

                r.setPicUrl(obj.getString("spic"));

                r.setPopularity(obj.getLong("online"));
                r.setWatchingNumByPopularity();

                r.setCate(obj.getString("gameName"));

                roomList.add(r);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return roomList;
    }

    public static void main(String[] args) {
        Room r = new ZhanQiPlatform().getRoomById("3245");

        System.out.println(r);

    }

}
