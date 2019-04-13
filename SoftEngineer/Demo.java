package SoftEngineer;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;

/**
 * @author Carl
 */
public class Demo {
	public static void main(String[] args) throws Exception {
//		 String s = get("http://192.168.31.248:8080/ComprehensiveEvaluation/student/getScore/161484");
		 String s = get("http://192.168.31.248:8080/ComprehensiveEvaluation/teacher/getScores");
//		Map<String,String> map = new HashMap();
//		map.put("number","161487");
//		map.put("sports","80");
//		map.put("english","50");
//		map.put("software","40");
//		String s = post(map,"http://192.168.31.248:8080/ComprehensiveEvaluation/teacher/getEvaluation");
		
		System.out.println(s);
		
		
//		Student stu = (Student) JSON.parseObject(s, Student.class);
//		java.util.List<Student> parseArray = JSON.parseArray(s, Student.class);
	}


	// "http://localhost:8080/ComprehensiveEvaluation/teacher/getEvaluation"
	static String post(Map<String,String> map, String u) throws Exception {
		
		StringBuffer params = new StringBuffer();
		Iterator<Entry<String, String>> it = map.entrySet().iterator();
	    while (it.hasNext()) {
	      Map.Entry<String, String> entry = it.next();
	      params.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
	    }
		String substring = params.substring(0,params.length()-1);
		
		
		URL url = new URL(u);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");// 锟结交模式
		conn.setDoOutput(true);// 锟角凤拷锟斤拷锟斤拷锟斤拷锟�
		conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
		conn.setUseCaches(false);
		
		byte[] bypes = substring.getBytes();
		conn.getOutputStream().write(bypes);// 锟斤拷锟斤拷锟斤拷锟�
		InputStream inStream = conn.getInputStream();
		byte[] bytes = new byte[1024];
		int len = 0;
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		while ((len = inStream.read(bytes)) != -1) {
			outStream.write(bytes, 0, len);
		}
		return new String(outStream.toByteArray(),"utf-8");
	}

	// get锟斤拷锟斤拷
	// http://192.168.31.248:8080/ComprehensiveEvaluation/student/getScore/161484
	static String get(String url) throws Exception {
		URL u = new URL(url);

		HttpURLConnection httpUrlConn = (HttpURLConnection) u.openConnection();
		httpUrlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		httpUrlConn.setDoOutput(true);
		httpUrlConn.setDoInput(true);
		httpUrlConn.setUseCaches(false);
		httpUrlConn.setRequestMethod("GET");
		httpUrlConn.connect();

		InputStream in = httpUrlConn.getInputStream(); 
		InputStreamReader streamReader = new InputStreamReader(in);
		BufferedReader reader = new BufferedReader(streamReader);
		return reader.readLine();
//		String s;
//		while ((s = reader.readLine()) != null) {
//		Student stu = (Student) JSON.parseObject(s, Student.class);
//		JSON.parseObject(s, List.class);
//		java.util.List<Student> parseArray = JSON.parseArray(s, Student.class);
//		System.out.println(stu.getClassid());
//		System.out.println(stu.getScore().getComprehensive());
//		}

	}


}
