package com.yangml.mongodbjava;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class MongoDbTest {
	
	private Mongo mg = null;
	private DB db;
	// 建立与Mongodb数据库的连接
	public void connect(){
		try {
			mg = new Mongo("127.0.0.1",27017);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//得到的所有的数据库名称
	public void getDatabaseNames(){
		List<String> list = mg.getDatabaseNames();
		System.err.println("得到的所有的数据库名称：");
		System.out.println(list.toString());
	}
	//删除一个库
	public void deleteDatabaseByName(String dbName){
		mg.dropDatabase(dbName);
		System.err.println("删除一个库："+dbName);
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
	//列出mydb数据库中user集合中的所有文档  利用游标得到所有记录
	public void listHelloCollectionDocuments(String dbName){
		if(mg!=null){
			db = mg.getDB(dbName);
			DBCollection collection = db.getCollection("user");
			DBCursor cur = collection.find();
			System.err.println(dbName+"数据库的集合的文档记录：");
			while(cur.hasNext()){
				System.out.println(cur.next());
			}
		}else{
			System.out.println("Please connect to MongoDB and then fetch the collection");
		}
		
	}
	//插入文档数据
	public void insrtcontent(String dbName){
		DBCollection collection=null;
		if(mg!=null){
			db = mg.getDB(dbName);
			collection = db.getCollection("user");
			BasicDBObject doc = new BasicDBObject();
			doc.put("name", "MongoDB");  
		    doc.put("type", "database");  
		    doc.put("count", 1);  
		      
		    BasicDBObject info = new BasicDBObject();  
		    info.put("x", 203);  
		    info.put("y", 102);  
		       
		    doc.put("info", info);  
		    collection.insert(doc); // 保存  
		    
		}else{
			System.out.println("Please connect to MongoDB and then fetch the collection");
		}
	     
	}
	//查询集合中的第一份文档
	public void findOneContent(String dbName){
		DBCollection collection=null;
		if(mg!=null){
			db = mg.getDB(dbName);
			collection = db.getCollection("user");
			
			
			DBObject myDoc = collection.findOne();  
			System.err.println("查询集合中的第一份文档:");
			System.out.println(myDoc);
		    
		}else{
			System.out.println("Please connect to MongoDB and then fetch the collection");
		}
		
		
	}
	//插入多个文档
	public void insrtContents(String dbName){
		DBCollection collection=null;
		if(mg!=null){
			db = mg.getDB(dbName);
			collection = db.getCollection("user");
			for(int i=1;i<10;i++){
				collection.insert(new BasicDBObject().append("i", i*2)); // 保存  
			}
		}else{
			System.out.println("Please connect to MongoDB and then fetch the collection");
		}
		
	}
	//统计集合中文档的条数
	public void getCount(String dbName){
		DBCollection collection=null;
		if(mg!=null){
			db = mg.getDB(dbName);
			collection = db.getCollection("user");
			System.err.println("mydb中user集合的文档个数："+collection.count());
		}else{
			System.out.println("Please connect to MongoDB and then fetch the collection");
		}
	}
	//查询指定文档  
	public void query(String dbName){
		DBCollection collection=null;
		if(mg!=null){
			db = mg.getDB(dbName);
			collection = db.getCollection("user");
			
			BasicDBObject query = new BasicDBObject();
//			query.put("x", 8);
			
			query.put("i", new BasicDBObject("$gt",2).append("$lte", 10));
			
			DBCursor cur = collection.find(query);
			System.err.println("mydb中user集合的指定文档：");
			while(cur.hasNext()){
				System.out.println(cur.next());
			}
		}else{
			System.out.println("Please connect to MongoDB and then fetch the collection");
		}
	}
	//创建一个索引 
	public void createIndex(String dbName){
		DBCollection collection=null;
		if(mg!=null){
			db = mg.getDB(dbName);
			collection = db.getCollection("user");
			collection.createIndex(new BasicDBObject("i",1));
			System.err.println("mydb中user集合创建索引 createIndex(new BasicDBObject(i,1)");
		}else{
			System.out.println("Please connect to MongoDB and then fetch the collection");
		}
	}
	//查询集合中的索引列表
	public void getIndexlist(String dbName){
		DBCollection collection=null;
		if(mg!=null){
			db = mg.getDB(dbName);
			collection = db.getCollection("user");
			List<DBObject> list = collection.getIndexInfo();
			System.err.println("mydb中user集合创建的索引列表："+list.toString());
		}else{
			System.out.println("Please connect to MongoDB and then fetch the collection");
		}
	}
	//自动成生主键
	public void getObjectid(){
		ObjectId id= new ObjectId();
		System.err.println("自动生成主键:"+id);
		ObjectId copy = new ObjectId(id+"");
		System.err.println("自动生成主键copy："+copy);
	}
	//正则表达式的应用
	public void regular(String dbName){
		
		DBCollection collection=null;
		if(mg!=null){
			db = mg.getDB(dbName);
			collection = db.getCollection("user");
			Pattern john = Pattern.compile("bling?",Pattern.CASE_INSENSITIVE);
			BasicDBObject query = new BasicDBObject("name",john);
			DBCursor cursor = collection.find(query);
			System.err.println("正则表达式查询：");
			while(cursor.hasNext()){
				System.out.println(cursor.next());
			}
		}else{
			System.out.println("Please connect to MongoDB and then fetch the collection");
		}
	}
	//插入日期类型数据
	public void insertdate(String dbName){
		DBCollection collection=null;
		if(mg!=null){
			db = mg.getDB(dbName);
			collection = db.getCollection("user");
			Date now = new Date();
			BasicDBObject time = new BasicDBObject("ts",now);
			collection.save(time);
		}else{
			System.out.println("Please connect to MongoDB and then fetch the collection");
		}
	}
	//插入数组数据
	public void insertArray(String dbName){
		DBCollection collection=null;
		if(mg!=null){
			db = mg.getDB(dbName);
			collection = db.getCollection("user");
			ArrayList x = new ArrayList();
		       x.add(1);
		       x.add(2);
		       x.add(new BasicDBObject("foo", "bar"));
		       x.add(4);
		    BasicDBObject doc = new BasicDBObject("x", x);
			   
			collection.save(doc);
		}else{
			System.out.println("Please connect to MongoDB and then fetch the collection");
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MongoDbTest mt = new MongoDbTest();
		mt.connect();
		
		//插入文档数据到mydb的user中
		//mt.insrtcontent("mydb");
		//插入多个文档
		//mt.insrtContents("mydb");
		//插入日期类型的数据
		//mt.insertdate("mydb");
		//插入数组类型的数据
		//mt.insertArray("mydb");
		//查询文档中的第一份数据
		mt.findOneContent("mydb");
		//查询指定的文档
		mt.query("mydb");
		//创建索引
		mt.createIndex("mydb");
		//查询集合中的索引列表
		mt.getIndexlist("mydb");
		//得到的所有的数据库名称
		mt.getDatabaseNames();
		//删除一个库
		mt.deleteDatabaseByName("temp");
		mt.getDatabaseNames();
		//查询mydb数据库下的所有集合
		mt.listAllCollections("mydb");
		//查询集合下的所有文档
		mt.listHelloCollectionDocuments("mydb");
		//查询文档的数量
		mt.getCount("mydb");
		//自动生成主键
		mt.getObjectid();
		//正则表达式查询
		mt.regular("mydb");
	}

}
