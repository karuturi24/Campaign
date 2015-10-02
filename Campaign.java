package com.inndata.mogames;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Random;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import com.google.gson.Gson;

public class Campaign extends HttpServlet{
	int p;
	int q;
	int r;
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException
	{
		ArrayList<Object> finalArray = new ArrayList<Object>();
		p = Integer.parseInt(req.getParameter("p"));
		q = Integer.parseInt(req.getParameter("q"));
		r = Integer.parseInt(req.getParameter("r"));
		finalArray = getTotalCampaigns();
		Gson jsonString = new Gson();
		String json = jsonString.toJson(finalArray);
		FileWriter file = new FileWriter("/home/hadoop/Documents/campaign.json");
		try {
			file.write(json);
		}catch (IOException e){
			e.printStackTrace();
		} finally {
				file.flush();
				file.close();
		}
		res.setContentType("text/html");
		PrintWriter pw=res.getWriter();
		pw.println("<html><body>");
		pw.println(json);
		pw.println("</body></html>");
		pw.close();
	}
	public ArrayList<Object> getTargetList(){
		ArrayList<Object> arr1 = new ArrayList<Object>();
		final int MIN = 1;
		final int MAX = q-1;
		final java.util.Random generator = new java.util.Random();
		int randomNumberOfAttributes = generator.nextInt(MAX - MIN) + MIN;
		for ( int tgList = 0; tgList < randomNumberOfAttributes ; tgList++){
			LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
			String atrAlpha = getAlpha(tgList);
			map.put("target", "attr_" + atrAlpha);
			HashSet<String> al=new HashSet<String>();
			for (int atrList = 0 ; atrList < p-1 ; atrList++){
				Random rn = new Random();
				int result=rn.nextInt(p -1 -0) + 0;
				String str = Integer.toString(result);
				al.add(atrAlpha+str);
			}
			map.put("attr_list",al);
			arr1.add(map);
		}
		return arr1;
	}
	public ArrayList<Object> getTotalCampaigns(){
		ArrayList<Object> finalArray = new ArrayList<Object>();
		float minX = 0.0f;
		float maxX = 10.0f;
		Random rand = new Random();
		for ( int camp = 0 ; camp < r ; camp++){
			LinkedHashMap<String, Object> Lh = new LinkedHashMap<String, Object>();
			ArrayList<Object> arr1 = new ArrayList<Object>();
			arr1 = getTargetList();
			float finalX = rand.nextFloat() * (maxX - minX) + minX;
			Lh.put("campaign", "campaign"+camp);
			Lh.put("price", finalX);
			Lh.put("targetlist", arr1);
			finalArray.add(Lh);
		}
		return finalArray;
	}
	public static String getAlpha(int num) {
		return "ABCDEFGHIJKLMNOPQRSTUVWXYZ".substring(num, num+1);
	}
}
