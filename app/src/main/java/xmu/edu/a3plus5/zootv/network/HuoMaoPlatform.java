package xmu.edu.a3plus5.zootv.network;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import xmu.edu.a3plus5.zootv.entity.Category;
import xmu.edu.a3plus5.zootv.entity.Room;

public class HuoMaoPlatform extends BasePlatform {

    @Override
    public List<Room> getByCategory(Category category, int page) {
        try {
            Document doc = Jsoup.connect("http://www.huomaotv.cn/channel/zhuji").get();
            System.out.print(doc);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Category> getPopularCategory() {
        return this.getAllCategory().subList(0, 8);
    }

    @Override
    public List<Category> getAllCategory() {
        try {
            Document doc = Jsoup.connect("http://www.huomaotv.cn/game").get();
            System.out.print(doc);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Room> search(String keyword) {
        try {
            Document doc = Jsoup.connect("http://www.huomaotv.cn/search?kw=\u6050\u6016&orderby=default").get();
            System.out.print(doc);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Room> getMostPopular() {
        return null;
    }

    @Override
    public Room getRoomById(String id) {
        return null;
    }

    public static void main(String[] args) {
        //new HuoMaoPlatform().getAllCategory();
        //new HuoMaoPlatform().getByCategory(null, 1);
        new HuoMaoPlatform().search(null);
    }
}
