package io.anu.vertx.crudapi.repository;
//package io.manju.vertx.crudapi.repository;
//import java.util.List;
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;
//import javax.persistence.Query;
//
//import io.manju.vertx.crudapi.entity.Designation;
//import io.vertx.core.http.HttpServerResponse;
//import io.vertx.core.json.Json;
//import io.vertx.core.json.JsonObject;
//import io.vertx.ext.web.RoutingContext;
//
//public class DepartmentDao {
//	private static DepartmentDao instance;
//protected EntityManager entityManager;
//
//public static DepartmentDao getInstance()
//	{
//    if (instance == null){
//        instance = new DepartmentDao();
//    }
//    return instance;
//	}
//
//private DepartmentDao()
//	{
//    entityManager = getEntityManager();
//	}
//
//private EntityManager getEntityManager()
//	{
//    EntityManagerFactory factory = Persistence.createEntityManagerFactory("crudHibernatePU");
//    if (entityManager == null) {
//        entityManager = factory.createEntityManager();
//    }
//    return entityManager;
//	}
//
//public void persist(Designation newTask){
//
//    try {
//
//        entityManager.getTransaction().begin();
//        System.out.println("hello");
//        entityManager.persist(newTask);
//        System.out.println("anusha");
//        entityManager.getTransaction().commit();
//        System.out.println("welcome");
//    } catch (Exception ex) {
//
//        ex.printStackTrace();
//        entityManager.getTransaction().rollback();
//    }}
//
//
//
//
//public void removeBytask_id(String task_id) {
//    try {
//        entityManager.getTransaction().begin();
//       Query tasks = entityManager.createQuery("delete  from Task  where task_id='"+task_id+"'") ;
//       tasks.executeUpdate();
//      //  entityManager.remove(task);
//        entityManager.getTransaction().commit();
//    } catch (Exception ex) {
//        ex.printStackTrace();
//        entityManager.getTransaction().rollback();
//    }
//}
//public List<Designation> getBYName(String assignto){
//
//	return entityManager.createQuery(
//    		"FROM Task WHERE assignto = :assignto", Designation.class)
//      .setParameter("assignto",assignto )
//      .getResultList();
//
//
//}
//public void name(RoutingContext context,String name)
//	{
//  try {
//
//        entityManager.getTransaction().begin();
//        Query task = entityManager.createQuery("UPDATE User set status='"+name+"'  WHERE name='"+name+"'");
//        task.executeUpdate();
//		entityManager.getTransaction().commit();
//		sendSuccess(" completed", context.response(),200);
//
//
//  }  catch (Exception ex) {
//            ex.printStackTrace();
//            entityManager.getTransaction().rollback();
//        }
//}
//
//
////public void removeBytask_id(String task_id) {
////    try {
////    	System.out.println(task_id);
////        Task task = getByTask_id(task_id);
////        remove(task);
////    	System.out.println(task_id);
////
////    } catch (Exception ex) {
////        ex.printStackTrace();
////    }
////}
////
////
////
////
////
////
////private Task getByTask_id(String task_id) {
////	 Object result = entityManager.find(Task.class, task_id);
////	 System.out.println("hai");
////
////	 System.out.println(task_id);
////
////    if (result != null) {
////        return (Task) result;
////    } else {
////        return null;
////    }
////}
//public void  name(RoutingContext context,String name,String assignto)
//{
//try {
//
//    entityManager.getTransaction().begin();
//    Query user = entityManager.createQuery("UPDATE User set status='"+name+"'  WHERE name='"+name+"'");
//    user.executeUpdate();
//	entityManager.getTransaction().commit();
//	sendSuccess(" completed", context.response(),200);
//
//
//}  catch (Exception ex) {
//        ex.printStackTrace();
//        entityManager.getTransaction().rollback();
//    }
//}
//
//@SuppressWarnings("unchecked")
//public List<Designation> findAlll() {
//return entityManager.createQuery("FROM " + Designation.class.getName()).getResultList();
//}
//
//public Designation getName(String task_id) {
//	return null;
//}
//
//
//
// private void sendSuccess(String successMessage,HttpServerResponse response,int code) {
//	  JsonObject jo = new JsonObject();
//      jo.put("successMessage", successMessage);
//	  response
//              .setStatusCode(200)
//              .setStatusCode(code)
//              .putHeader("content-type", "application/json; charset=utf-8")
//              .end(Json.encodePrettily(jo));
//  }
//}
//
