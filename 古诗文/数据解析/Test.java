package com.desaysv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import lombok.Getter;
import lombok.Setter;

public class Test {

	public static void main(String[] args) {

		ArrayList<String> arrayList = new ArrayList<>();
		try {
			FileReader fr = new FileReader("D:\\writer\\writer0-1000.json");
			BufferedReader bf = new BufferedReader(fr);
			String str;
			// 按行读取字符串
			while ((str = bf.readLine()) != null) {
				arrayList.add(str);
			}
			bf.close();
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(arrayList.size());
		Bmob.initBmob("", "");
		for (String obj : arrayList) {
			JSONObject bson = new JSONObject();
			JSONObject json = JSON.parseObject(obj);
			bson.put("name", json.get("name"));
			bson.put("headImage", json.get("headImageUrl"));
			bson.put("simpleIntro", json.get("simpleIntro"));
			JSONObject detail = json.getJSONObject("detailIntro");
			String detailIntro = (String) detail.get("人物生平");
			if(StringUtils.isBlank(detailIntro)) {
				bson.put("detailIntro", detail.get("生平"));
			}else {
				bson.put("detailIntro", detailIntro);
			}
			String anecdote = (String) detail.get("轶事典故");
			if(StringUtils.isBlank(anecdote)) {
				bson.put("anecdote", detail.get("典故"));
			}else {
				bson.put("anecdote", anecdote);
			}
			bson.put("family", detail.get("家庭成员"));
			bson.put("memorial", detail.get("后世纪念"));
			String achievement = (String) detail.get("主要成就");
			if(StringUtils.isBlank(achievement)) {
				bson.put("achievement", detail.get("成就"));
			}else {
				bson.put("achievement", achievement);
			}
			System.err.println(Bmob.insert("writer", bson.toString()));
		}
		/*JSONObject json = JSON.parseObject(arrayList.get(0));
		System.err.println(json.get("name"));
		System.err.println(json.get("headImageUrl"));
		System.err.println(json.get("simpleIntro"));
		JSONObject detail = json.getJSONObject("detailIntro");
		System.out.println(detail.get("轶事典故"));
		System.out.println(detail.get("家庭成员"));
		System.out.println(detail.get("后世纪念"));
		System.out.println(detail.get("主要成就"));
		System.out.println(detail.get("人物生平"));*/

	}

	@Setter
	@Getter
	public static class Writer implements Serializable {
		private static final long serialVersionUID = 1L;
		private String name;// 姓名
		private String headImage;// 头像
		private String simpleIntro;// 简介
		private String detailIntro;// 生平介绍
		private String anecdote;// 轶事典故
		private String family;// 家庭成员
		private String memorial;// 后世纪念
		private String achievement;// 主要成就

	}

	public void test() {
		Bmob.initBmob("", "");
		JSONObject bson = new JSONObject();
		bson.put("name", "李白");
		bson.put("headImage", "https://guwen-1252396323.cos.ap-chengdu.myqcloud.com/headImage/20180914152354795.jpg");
		bson.put("simpleIntro",
				"李白（701年－762年），字太白，号青莲居士，唐朝浪漫主义诗人，被后人誉为“诗仙”。祖籍陇西成纪(待考)，出生于西域碎叶城，4岁再随父迁至剑南道绵州。李白存世诗文千余篇，有《李太白集》传世。762年病逝，享年61岁。其墓在今安徽当涂，四川江油、湖北安陆有纪念馆。");
		bson.put("detailIntro", "1");
		bson.put("anecdote", "1");
		bson.put("family", "1");
		bson.put("memorial", "1");
		bson.put("achievement", "1");
		System.err.println(Bmob.insert("writer", bson.toString()));
		System.err.println(Bmob.find("writer", 0, 1));
	}
}
