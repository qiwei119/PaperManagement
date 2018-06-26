package com.hxbank.scyxs.PaperManagement.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.hxbank.scyxs.PaperManagement.utils.Neo4jUtils;


public class PaperLabelService {

	private static Logger logger = Logger.getLogger(PaperLabelService.class);
	
	public String getLabel(String rootBusinenssNode, String paperName)
	{
		
		Connection neocon = Neo4jUtils.getConnection();
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
	
	//	JSONArray jsonArray = null;
	//	JSONObject jsonObject = null;
		
	    String	sql ="MATCH (n:PaperNode { name:"+ paperName + " }),(m:BusinessNode{ name:\""+ rootBusinenssNode +"\" }), p = shortestPath((n)-[*]->(m)) RETURN nodes(p)";
	  
	    ArrayList nodeList = new ArrayList<String>();
	    ArrayList nodeList_tmp = new ArrayList<String>();

	    try {
			pst = neocon.prepareStatement(sql);
			logger.info("查询语句:" + sql);
			rs = pst.executeQuery();
			rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
	//		jsonArray = new JSONArray();
			
			while (rs.next()) {
	//			jsonObject = new JSONObject();
				for (int i = 0; i < columnCount; i++) {
		
		//			Object object2 = rs.getObject(i + 1);
				 
					nodeList_tmp = (ArrayList) rs.getObject(i + 1);
					
		//			Object object3 = object2.toString().replace("}", ", showId=0}");
		//			logger.info("JSon对象rs.getObject2：" + object2);
					
		//			logger.info("JSon对象rs.getObject2：" + object2);
		//			logger.info("JSon对象rs.getObject：" + object3);
		//			jsonObject.put(rsmd.getColumnName(i + 1), "null".equals(object2) ? "未知" : object2);

		//			logger.info("JSon对象Key：" + rsmd.getColumnName(i + 1));
		//			logger.info("JSon对象Value：" + object2.toString().replace("}", ", showId=0}"));
				
				}
				for (int i = 1; i < nodeList_tmp.size(); i++)
				{
					String node = nodeList_tmp.get(i).toString();
					
					String[] str = node.split("=");
					String[] str2 = str[1].split("}");
					
					nodeList.add(str2[0]);
					logger.info("node list:"+ str2[0]);
				}
			
	   //		jsonArray.add(jsonObject);
				
			}

			System.out.println("##########################");

		} catch (SQLException e) {
			e.printStackTrace();
			logger.info("查询 出错");
		} finally {
			Neo4jUtils.closeAll(neocon, pst, rs);
		}
	    
		return nodeList.toString();
	}
	
	public void createPaperNodeAndRel(String rootBusinenssNode, String paperName)
	{
		
        
	}
	
	public String  getSameBusinessPaperName(String paperName)
	{
		
		
		
		return "ok";
	}
}
