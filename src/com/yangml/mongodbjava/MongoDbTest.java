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
	// ������Mongodb���ݿ������
	public void connect(){
		try {
			mg = new Mongo("127.0.0.1",27017);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//�õ������е����ݿ�����
	public void getDatabaseNames(){
		List<String> list = mg.getDatabaseNames();
		System.err.println("�õ������е����ݿ����ƣ�");
		System.out.println(list.toString());
	}
	//ɾ��һ����
	public void deleteDatabaseByName(String dbName){
		mg.dropDatabase(dbName);
		System.err.println("ɾ��һ���⣺"+dbName);
	}
	//�г�dbName���ݿ��е����м���
	public void listAllCollections(String dbName){
		if(mg!=null){
			db = mg.getDB(dbName);
			Set<String> collections = db.getCollectionNames();
			System.err.println(dbName+"���ݿ�����м��ϣ�");
			for(String c:collections){
				System.out.println(c);
			}
		}
	}
	//�г�mydb���ݿ���user�����е������ĵ�  �����α�õ����м�¼
	public void listHelloCollectionDocuments(String dbName){
		if(mg!=null){
			db = mg.getDB(dbName);
			DBCollection collection = db.getCollection("user");
			DBCursor cur = collection.find();
			System.err.println(dbName+"���ݿ�ļ��ϵ��ĵ���¼��");
			while(cur.hasNext()){
				System.out.println(cur.next());
			}
		}else{
			System.out.println("Please connect to MongoDB and then fetch the collection");
		}
		
	}
	//�����ĵ�����
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
		    collection.insert(doc); // ����  
		    
		}else{
			System.out.println("Please connect to MongoDB and then fetch the collection");
		}
	     
	}
	//��ѯ�����еĵ�һ���ĵ�
	public void findOneContent(String dbName){
		DBCollection collection=null;
		if(mg!=null){
			db = mg.getDB(dbName);
			collection = db.getCollection("user");
			
			
			DBObject myDoc = collection.findOne();  
			System.err.println("��ѯ�����еĵ�һ���ĵ�:");
			System.out.println(myDoc);
		    
		}else{
			System.out.println("Please connect to MongoDB and then fetch the collection");
		}
		
		
	}
	//�������ĵ�
	public void insrtContents(String dbName){
		DBCollection collection=null;
		if(mg!=null){
			db = mg.getDB(dbName);
			collection = db.getCollection("user");
			for(int i=1;i<10;i++){
				collection.insert(new BasicDBObject().append("i", i*2)); // ����  
			}
		}else{
			System.out.println("Please connect to MongoDB and then fetch the collection");
		}
		
	}
	//ͳ�Ƽ������ĵ�������
	public void getCount(String dbName){
		DBCollection collection=null;
		if(mg!=null){
			db = mg.getDB(dbName);
			collection = db.getCollection("user");
			System.err.println("mydb��user���ϵ��ĵ�������"+collection.count());
		}else{
			System.out.println("Please connect to MongoDB and then fetch the collection");
		}
	}
	//��ѯָ���ĵ�  
	public void query(String dbName){
		DBCollection collection=null;
		if(mg!=null){
			db = mg.getDB(dbName);
			collection = db.getCollection("user");
			
			BasicDBObject query = new BasicDBObject();
//			query.put("x", 8);
			
			query.put("i", new BasicDBObject("$gt",2).append("$lte", 10));
			
			DBCursor cur = collection.find(query);
			System.err.println("mydb��user���ϵ�ָ���ĵ���");
			while(cur.hasNext()){
				System.out.println(cur.next());
			}
		}else{
			System.out.println("Please connect to MongoDB and then fetch the collection");
		}
	}
	//����һ������ 
	public void createIndex(String dbName){
		DBCollection collection=null;
		if(mg!=null){
			db = mg.getDB(dbName);
			collection = db.getCollection("user");
			collection.createIndex(new BasicDBObject("i",1));
			System.err.println("mydb��user���ϴ������� createIndex(new BasicDBObject(i,1)");
		}else{
			System.out.println("Please connect to MongoDB and then fetch the collection");
		}
	}
	//��ѯ�����е������б�
	public void getIndexlist(String dbName){
		DBCollection collection=null;
		if(mg!=null){
			db = mg.getDB(dbName);
			collection = db.getCollection("user");
			List<DBObject> list = collection.getIndexInfo();
			System.err.println("mydb��user���ϴ����������б�"+list.toString());
		}else{
			System.out.println("Please connect to MongoDB and then fetch the collection");
		}
	}
	//�Զ���������
	public void getObjectid(){
		ObjectId id= new ObjectId();
		System.err.println("�Զ���������:"+id);
		ObjectId copy = new ObjectId(id+"");
		System.err.println("�Զ���������copy��"+copy);
	}
	//������ʽ��Ӧ��
	public void regular(String dbName){
		
		DBCollection collection=null;
		if(mg!=null){
			db = mg.getDB(dbName);
			collection = db.getCollection("user");
			Pattern john = Pattern.compile("bling?",Pattern.CASE_INSENSITIVE);
			BasicDBObject query = new BasicDBObject("name",john);
			DBCursor cursor = collection.find(query);
			System.err.println("������ʽ��ѯ��");
			while(cursor.hasNext()){
				System.out.println(cursor.next());
			}
		}else{
			System.out.println("Please connect to MongoDB and then fetch the collection");
		}
	}
	//����������������
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
	//������������
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
		
		//�����ĵ����ݵ�mydb��user��
		//mt.insrtcontent("mydb");
		//�������ĵ�
		//mt.insrtContents("mydb");
		//�����������͵�����
		//mt.insertdate("mydb");
		//�����������͵�����
		//mt.insertArray("mydb");
		//��ѯ�ĵ��еĵ�һ������
		mt.findOneContent("mydb");
		//��ѯָ�����ĵ�
		mt.query("mydb");
		//��������
		mt.createIndex("mydb");
		//��ѯ�����е������б�
		mt.getIndexlist("mydb");
		//�õ������е����ݿ�����
		mt.getDatabaseNames();
		//ɾ��һ����
		mt.deleteDatabaseByName("temp");
		mt.getDatabaseNames();
		//��ѯmydb���ݿ��µ����м���
		mt.listAllCollections("mydb");
		//��ѯ�����µ������ĵ�
		mt.listHelloCollectionDocuments("mydb");
		//��ѯ�ĵ�������
		mt.getCount("mydb");
		//�Զ���������
		mt.getObjectid();
		//������ʽ��ѯ
		mt.regular("mydb");
	}

}
