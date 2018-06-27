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
		Connection neocon = Neo4jUtils.getConnection();
		
		PreparedStatement pst = null;
		PreparedStatement pst1 = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		
		String sql_node ="Create (n;PaperNode) where n.name =" + paperName;
		String sql_rel = "MATCH (m:PaperNode{name:line.PaperNode}) , (n:BusinessNode{name:line.BusinessNode}) CREATE (m)-[:RELATION { type:}]->(n)";
		
		 try {
				pst = neocon.prepareStatement(sql_node);
				rs = pst.executeQuery();
				
				pst1 = neocon.prepareStatement(sql_rel);
				rs = pst.executeQuery();
				
		 }catch (SQLException e) {
				e.printStackTrace();
				logger.info("查询 出错");
			} finally {
				Neo4jUtils.closeAll(neocon, pst, rs);
			}
        
	}
	
	public String  getSameBusinessPaperName(String paperName)
	{
	    Connection neocon = Neo4jUtils.getConnection();
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		
	    String sql_label ="match (n:PaperNode)-[r:RELATION]->(m:BusinessNode) where n.name="+ paperName +" and r.type=\"隶属\" return m.name"; 
	    
	    String paper_label = null;
	    
	    try {
			pst = neocon.prepareStatement(sql_label);
			logger.info("查询语句:" + sql_label);
			rs = pst.executeQuery();
			rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			
			while (rs.next()) {
				for (int i = 0; i < columnCount; i++) {
		
					paper_label = rs.getObject(i + 1).toString();
				
				}
			}

			System.out.println("##########################");

		} catch (SQLException e) {
			e.printStackTrace();
			logger.info("查询 出错");
		} 
	    
	    ArrayList paperList = new ArrayList<String>();
	    
	    if (paper_label ==null)
	    {
	    	   return null;
	    
	    }else
	    {
	    String sql_list= "match (n:PaperNode)-[r:RELATION]->(m:BusinessNode) where m.name=\""+ paper_label +"\" and r.type=\"隶属\" return n.name limit 5";
	    	try {
				pst = neocon.prepareStatement(sql_list);
				logger.info("查询语句:" + sql_list);
				rs = pst.executeQuery();
				rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();
				
				while (rs.next()) {
					for (int i = 0; i < columnCount; i++) {
			
						paperList.add(rs.getObject(i + 1).toString());
					
					}
				}

				System.out.println("##########################");

			} catch (SQLException e) {
				e.printStackTrace();
				logger.info("查询 出错");
			} finally {
				Neo4jUtils.closeAll(neocon, pst, rs);
			}
	      	return paperList.toString();
	    }
	}
}
