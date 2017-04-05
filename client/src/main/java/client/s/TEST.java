package client.s;

import net.sf.json.JSONObject;

public class TEST {
	static String json_str = "{\"total\":920,\"data\":[{\"ID\":\"634\",\"Name\":\"于东\"},{\"ID\":\"822\",\"Name\":\"于祎\"},{\"ID\":\"782\",\"Name\":\"于燕\"},{\"ID\":\"636\",\"Name\":\"于玲\"},{\"ID\":\"841\",\"Name\":\"于浩\"},{\"ID\":\"383\",\"Name\":\"于娟\"}]}";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JSONObject jsonObject = JSONObject.fromObject(json_str);
		System.out.println(jsonObject.get("data"));
	}

}
