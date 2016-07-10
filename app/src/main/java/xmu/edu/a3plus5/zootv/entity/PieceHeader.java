package xmu.edu.a3plus5.zootv.entity;

/**
 * Created by hd_chen on 2016/7/10.
 */
public class PieceHeader {
    String title;
    String icon;
    String url;

    public PieceHeader() {
    }

    public PieceHeader(String title, String icon, String url) {
        this.title = title;
        this.icon = icon;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
