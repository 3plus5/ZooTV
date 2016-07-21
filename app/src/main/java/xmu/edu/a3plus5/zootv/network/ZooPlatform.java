package xmu.edu.a3plus5.zootv.network;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import xmu.edu.a3plus5.zootv.entity.Category;
import xmu.edu.a3plus5.zootv.entity.Room;

public class ZooPlatform extends BasePlatform {
    private List<BasePlatform> platforms;
    private int[] portion = {3, 3, 2};

    public ZooPlatform() {
        platforms = new ArrayList<>();
        platforms.add(PlatformFactory.createPlatform(DouYu));
        platforms.add(PlatformFactory.createPlatform(Panda));
        platforms.add(PlatformFactory.createPlatform(ZhanQi));
    }

    @Override
    public List<Room> getRecommendedRoomByCateList(List<Category> cateList) {
        if (cateList.size() == 0 || cateList == null)
            return this.getMostPopularByPage(1);

        else {
            String[] platName = {DouYu, Panda, ZhanQi};

            Random r = new Random();

            List<Room> roomList = new ArrayList<Room>();

            int[] portion = this.getPortionByCateListSize(cateList.size());

            for (int j = 0; j < portion.length; j++) {
                List<Integer> choice = new ArrayList<Integer>() {{
                    add(0);
                    add(1);
                    add(2);
                }};

                while (true) {
                    int index = choice.get(r.nextInt(choice.size()));

                    if (cateList.get(j).getCateUrl(platName[index]) != null) {
                        List<Room> tmp = platforms.get(index).getByCategory(cateList.get(j), 1);

                        int end = portion[j] > tmp.size() ? tmp.size() : portion[j];

                        roomList.addAll(tmp.subList(0, end));

                        break;
                    } else {
                        choice.remove(index);
                    }
                }
            }

            return roomList;
        }
    }

    @Override
    public List<Room> getMostPopularByPage(int page) {

        int i = 0;
        List<Room> roomList = new ArrayList<Room>();
        List<Room> tmpList = null;

        for (BasePlatform platform : platforms) {
            int begin = (page - 1) * portion[i];
            int end = page * portion[i];
            tmpList = platform.getMostPopular();
            int max = tmpList.size();
            end = (end > max) ? max : end;
            roomList.addAll(tmpList.subList(begin, end));

            i++;
        }

        Collections.sort(roomList);
        return roomList;
    }

    @Override
    public List<Room> getByCategory(Category category, int page) {
        List<Room> rooms = new ArrayList<>();
        for (BasePlatform platform : platforms) {
            List<Room> temp = platform.getByCategory(category, page);
            if (temp != null)
                rooms.addAll(temp);
        }
        Collections.sort(rooms);

        return rooms;
    }

    @Override
    public List<Room> search(String keyword) {
        List<Room> rooms = new ArrayList<>();

        for (BasePlatform platform : platforms) {
            List<Room> temp = platform.search(keyword);

            if (temp != null)
                rooms.addAll(temp);
        }

        Collections.sort(rooms);

        return rooms;
    }

    @Override
    public List<Category> getAllCategory() {
        if (categories != null)
            return categories;

        categories = platforms.get(0).getAllCategory();

        for (int i = 1; i < platforms.size(); i++) {
            List<Category> cates = platforms.get(i).getAllCategory();

            categories.addAll(cates);
            for (int j = 0; j < categories.size(); j++) {
                for (int k = j + 1; k < categories.size(); k++) {
                    if (categories.get(j).getName().equals(categories.get(k).getName())) {
                        categories.get(j).getCateMap().putAll(categories.get(k).getCateMap());
                        categories.remove(k);
                        break;
                    }
                }
            }
        }

        List<Category> cates = new ArrayList<>();
        for (Category category : categories) {
            if (category.getCateMap().size() > 1)
                cates.add(category);
        }
        categories = cates;
        return categories;
    }

    @Override
    public List<Room> getMostPopular() {
        List<Room> rooms = new ArrayList<>();
        for (BasePlatform platform : platforms) {
            rooms.addAll(platform.getMostPopular());
        }
        Collections.sort(rooms);
        return rooms;
    }

    @Override
    public Room getRoomById(String id) {
        return null;
    }

    public static void main(String[] args) {
        List<Room> ret = new ZooPlatform().search("10015");

        for (Room r : ret)
            System.out.println(r);
    }
}
