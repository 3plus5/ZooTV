package xmu.edu.a3plus5.zootv.entity;


import java.text.DecimalFormat;

public class Room implements Comparable<Room>{

	private int rid;                //编号
	private String link;	  		//链接
	private String title;	    	//标题
	private String anchor;			//主播
	private String picUrl;			//图片地址
	private long popularity;		//热度
	private String watchingNum;		//观看人数
	private String platform;		//平台
	private String roomId;			//直播间id
	private String cate;			//分类

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

	public int getRid() {
		return rid;
	}

	public void setRid(int rid) {
		this.rid = rid;
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

	public Room(int rid, String platform, String roomId) {
		this.rid = rid;
		this.platform = platform;
		this.roomId = roomId;
	}

	public void setWatchingNumByPopularity()
	{
		DecimalFormat df =new DecimalFormat("#.00");

		if(this.popularity < 10000)
			this.setWatchingNum(this.popularity + "");
		else
			this.setWatchingNum(df.format(this.popularity/(double)10000) + "万");
	}

	public static long getPopularity(String watchingNum)
	{
		if(watchingNum.contains("万"))
		{
			return (long) (Float.parseFloat(watchingNum.substring(0, watchingNum.length() - 1)) * 10000);
		}
		else
			return Long.parseLong(watchingNum);
	}

	@Override
	public int compareTo(Room o) {
		if(this.popularity > o.popularity)
			return -1;
		else if(this.popularity < o.popularity)
			return 1;
		else
			return 0;
	}


}
