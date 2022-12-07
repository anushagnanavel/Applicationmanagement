package io.anu.vertx.crudapi.repository;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import io.anu.vertx.crudapi.entity.Deploy;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * This class handles the Deploy data transactions
 * @author e1066
 */
public class DeployDao {
    private static DeployDao instance;
    protected EntityManager entityManager;

    public static DeployDao getInstance(){
        if (instance == null){
            instance = new DeployDao();
        }

        return instance;
    }

    private DeployDao() {
        entityManager = getEntityManager();
    }

    private EntityManager getEntityManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("crudHibernatePU");
        if (entityManager == null) {
            entityManager = factory.createEntityManager();
        }

        return entityManager;
    }

    /**This method should return Deploys list 
     * @return
     */
     
    @SuppressWarnings("unchecked")
    public List<Deploy> findAll() {
        return entityManager.createQuery("FROM " + Deploy.class.getName()).getResultList();
    }
    
     /**This method used to save Deploys
      */
    public void persistDeploy(Deploy newDeploy)
   	{
	        try {
	        	 
	            entityManager.getTransaction().begin();
	            entityManager.persist(newDeploy);
	            entityManager.getTransaction().commit();
	        } catch (Exception ex) {
	            ex.printStackTrace();
	            entityManager.getTransaction().rollback();
	            
	        }
	    }
    

    public void removeByDid(String did) {
	       try {
	       	entityManager.getTransaction().begin();
	       	Query query= entityManager.createQuery("DELETE FROM Deploy WHERE deploy_id = '"+did+"'");
	       	query.executeUpdate();
	   		entityManager.getTransaction().commit();
	   		 		 
		  }  catch (Exception ex) {
		            ex.printStackTrace();
		            entityManager.getTransaction().rollback();      
	   }
	       
	   }
    
    public void mergeById(String did, String mname,String bname,String  cid,
            String otherschanges, String status,String handleby, String rs,String  proomain) {
try {

entityManager.getTransaction().begin();
Query deploy = entityManager.createQuery("UPDATE Deploy  did='"+did+"'"
	+"Mname='"+mname+"'"
	   +"Bname='"+bname+"'"
	   +"Cid='"+cid+"'"
	   +"Otherschanges='"+otherschanges+"'"
	    +"Status='"+status+"'"
	     +"Handleby='"+handleby+"'"
	      +"Rs='"+rs+"'"
	       +"Proomain='"+proomain+"'"
	   
		+ "WHERE did='"+did+"'");
deploy.executeUpdate();
entityManager.getTransaction().commit();
		
}  catch (Exception ex) {
   ex.printStackTrace();
   entityManager.getTransaction().rollback();
}
}
    /**This method used to get User Deploy
     */
   public boolean getUserDeploy(RoutingContext context,String Authorization)
  	{
	     
    JedisPool jedisPool = new JedisPool("localhost", 6379);
	  // Get the pool and use the database
	  try (Jedis jedis = jedisPool.getResource()) {
	  //Get token value from redis
	  String result =  jedis.get(Authorization);
	  //Convert String to Json object
	  JsonObject jsonObject = new JsonObject(result);
	  
	 // JsonObject jsonObject1 = new JsonObject();
	 String  jsonObject1=jsonObject.getString("rolename");
	 String  jsonObject2=jsonObject.getString("department_name");

	  System.out.print(jsonObject1);
	  System.out.print(jsonObject2);
	if(jsonObject1.equals("Admin") || jsonObject2.equals("Devops")   ) {
		  return true;
	}
	else {
	
	  return false;
	}}}
   
   
   
  
   /**
    *  This method used to send a error message
    * @param errorMessage
    * @param response
    * @param code
    */
    private static void sendError(String errorMessage, HttpServerResponse response,int code) {
        JsonObject jo = new JsonObject();
        jo.put("errorMessage", errorMessage);
        response
                .setStatusCode(400)
                .setStatusCode(code)
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encodePrettily(jo));
    }

public void mergeByDid(Integer id, Integer did) {
	// TODO Auto-generated method stub
	
}
}