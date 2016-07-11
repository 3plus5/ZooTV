package xmu.edu.a3plus5.zootv.entity;


public class Room {
	private String link;	
	private String title;	
	private String anchor;
	private String picUrl;
	private long popularity;
	private String watchingNum;
	private String platform;
	private String roomId;
	private String cate;
	
	public Room(){}
	
	public Room(String link, String title, String anchor, String picUrl, long popularity, String watchingNum,
			String platform, String roomId, String cate) {
		super();
		this.link = link;
		this.title = title;
		this.anchor = anchor;
		this.picUrl = picUrl;
		this.popularity = popularity;
		this.watchingNum = watchingNum;
		this.platform = platform;
		this.roomId = roomId;
		this.cate = cate;
	}



	public String getLink() {
		return link;
	}


	public void setLink(String link) {
		this.link = link;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getAnchor() {
		return anchor;
	}


	public void setAnchor(String anchor) {
		this.anchor = anchor;
	}


	public String getPicUrl() {
		return picUrl;
	}


	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}


	public long getPopularity() {
		return popularity;
	}


	public void setPopularity(long popularity) {
		this.popularity = popularity;
	}


	public String getWatchingNum() {
		return watchingNum;
	}


	public void setWatchingNum(String watchingNum) {
		this.watchingNum = watchingNum;
	}


	public String getPlatform() {
		return platform;
	}


	public void setPlatform(String platform) {
		this.platform = platform;
	}


	public String getRoomId() {
		return roomId;
	}


	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}


	public String getCate() {
		return cate;
	}


	public void setCate(String cate) {
		this.cate = cate;
	}


	public String toString()
	{
		return String.format(" platform:%s\n title:%s\n roomId:%s\n link:%s\n cate:%s\n anchor:%s\n watchingNum:%s\n popularity:%d\n picUrl:%s\n", platform, title, roomId, link, cate, anchor, watchingNum, popularity, picUrl);
	}
}
