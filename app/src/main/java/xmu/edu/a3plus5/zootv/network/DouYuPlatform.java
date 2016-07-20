package xmu.edu.a3plus5.zootv.network;

import android.util.Log;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import xmu.edu.a3plus5.zootv.entity.Category;
import xmu.edu.a3plus5.zootv.entity.Room;

public class DouYuPlatform extends BasePlatform {

    public DouYuPlatform() {
        super.statusMap = new HashMap<Integer, Integer>();
        super.statusMap.put(2, 0);
        super.statusMap.put(1, 1);
    }

    private List<Room> parseFromDoc(Document doc) {
        if (doc == null)
            return null;

        List<Room> rooms = new ArrayList<Room>();
        Elements elements = doc.select("li[data-rid]");
        for (Element element : elements) {
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
        if (categories != null)
            return categories;

        Document doc = null;

        try {
            doc = Jsoup.connect("http://www.douyu.com/directory").get();
            Elements elements = doc.select("li[class ^= unit]");
            List<Category> categories = new ArrayList<>();
            for (Element e : elements) {
                Category c = new Category();
                c.setName(e.select("p").text());
                c.setPicUrl(e.select("img").attr("data-original"));
                c.setCateUrl(DouYu, e.select("a").attr("href"));
                //c.setDouyuUrl(e.select("a").attr("href"));
                categories.add(c);
            }
            return categories;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Room> getMostPopular() {
        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.douyu.com/directory/all?page=1&isAjax=1").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("getpopu","douyu");
        return parseFromDoc(doc);
    }

    @Override
    public Room getRoomById(String id) {
        Room room = null;

        String jsonLink = "http://open.douyucdn.cn/api/RoomApi/room/" + id;

        try {
            String jsonStr = Jsoup.connect(jsonLink).ignoreContentType(true).execute().body();

            JSONObject json = new JSONObject(jsonStr);

            JSONObject dataObject = json.getJSONObject("data");

            room = new Room(DouYu);

            room.setRoomId(dataObject.getString("room_id"));

            room.setCate(dataObject.getString("cate_name"));

            room.setTitle(dataObject.getString("room_name"));

            room.setAnchor(dataObject.getString("owner_name"));

            room.setLink("http://www.douyu.com/" + room.getRoomId());

            room.setPicUrl(dataObject.getString("room_thumb"));

            room.setPopularity(dataObject.getLong("online"));
            room.setWatchingNumByPopularity();

            room.setStatus(super.statusMap.get(dataObject.getInt("room_status")));
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return room;
    }


    public static void main(String[] args) {
        List<Category> categories = new DouYuPlatform().getPopularCategory();

        for (Category cate : categories)
            System.out.println(cate);

    }
}
