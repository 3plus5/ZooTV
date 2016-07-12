package xmu.edu.a3plus5.zootv.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Category implements Serializable
{
	private String name;			//分类名
	private String picUrl;				//图片地址
	private Map<String, String> cateMap; //分类地址

	public Category()
	{
		cateMap = new HashMap<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public Map<String, String> getCateMap() {
		return cateMap;
	}

	public void setCateMap(Map<String, String> cateMap) {
		this.cateMap = cateMap;
	}

	public String getPicUrl() {
		return picUrl;
	}


	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public void setCateUrl(String platform, String url)
	{
		cateMap.put(platform, url);
	}

	public String getCateUrl(String platform)
	{
		return cateMap.get(platform);
	}

	public Category(String name, String picUrl, Map<String, String> cateMap) {
		this.name = name;
		this.picUrl = picUrl;
		this.cateMap = cateMap;
	}

	public String toString()
	{
		String str = name + " " + picUrl + "\n";
		for(Map.Entry<String, String> entry : cateMap.entrySet())
			str += entry.getKey() + " : " + entry.getValue() + "\n";
		return str;
	}



//	public static void main(String[] args)
//	{
//		Category c = new Category();
//		System.out.println(c.getCateUrl("123"));
//	}
}
