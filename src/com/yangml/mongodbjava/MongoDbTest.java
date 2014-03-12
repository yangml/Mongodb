package com.yangml.mongodbjava;

import java.util.Set;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;

public class MongoDbTest {
	
	private Mongo mg = null;
	private DB db;
	// 建立与Mongodb数据库的连接
	public void connect(){
		try {
			mg = new Mongo("192.168.170.128",27017);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//列出dbName数据库中的所有集合
	public void listAllCollections(String dbName){
		if(mg!=null){
			db = mg.getDB(dbName);
			Set<String> collections = db.getCollectionNames();
			System.err.println(dbName+"数据库的所有集合：");
			for(String c:collections){
				System.out.println(c);
			}
		}
	}
	//列出temp数据库中hello集合中的所有文档  
	public void listHelloCollectionDocuments(){
		if(mg!=null){
			db = mg.getDB("temp");
			DBCollection collection = db.getCollection("hello");
			DBCursor cur = collection.find();
			while(cur.hasNext()){
				System.out.println(cur.next());
			}
		}else{
			System.out.println("Please connect to MongoDB and then fetch the collection");
		}
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MongoDbTest mt = new MongoDbTest();
		mt.connect();
		mt.listAllCollections("temp");
		mt.listHelloCollectionDocuments();
	}

}
