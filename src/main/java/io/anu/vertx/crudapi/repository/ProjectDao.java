package io.anu.vertx.crudapi.repository;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import io.vertx.ext.web.RoutingContext;
import io.anu.vertx.crudapi.entity.Project;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

	/**
	 * This class handles the project data transactions
	 * @author e1066
	 */
	public class ProjectDao {
	    private static ProjectDao instance;
	    protected EntityManager entityManager;

	    public static ProjectDao getInstance(){
	        if (instance == null){
	            instance = new ProjectDao();
	        }

	        return instance;
	    }

	    private ProjectDao() {
	        entityManager = getEntityManager();
	    }

	    private EntityManager getEntityManager() {
	        EntityManagerFactory factory = Persistence.createEntityManagerFactory("crudHibernatePU");
	        if (entityManager == null) {
	            entityManager = factory.createEntityManager();
	        }

	        return entityManager;
	    }

	    /**This method should return projects list 
	     * @return
	     */
	     
	    @SuppressWarnings("unchecked")
	    public List<Project> findAll() {
	        return entityManager.createQuery("FROM " + Project.class.getName()).getResultList();
	    }
	    
	     /**This method used to save projects
	      */
	    public void persistProject(Project newProject)
	   	{
		        try {
		        	 
		            entityManager.getTransaction().begin();
		            entityManager.persist(newProject);
		            entityManager.getTransaction().commit();
		        } catch (Exception ex) {
		            ex.printStackTrace();
		            entityManager.getTransaction().rollback();
		            
		        }
		    }
	    /**This method used to get User Project
	     */
	   public boolean getUserProject(RoutingContext context,String Authorization)
	  	{
		     
	    JedisPool jedisPool = new JedisPool("localhost", 6379);
		  // Get the pool and use the database
		  try (Jedis jedis = jedisPool.getResource()) {
		  //Get token value from redis
		  String result =  jedis.get(Authorization);
		  //Convert String to Json object
		  JsonObject jsonObject = new JsonObject(result);
		  
		 // JsonObject jsonObject1 = new JsonObject();
		 String  jsonObject1=jsonObject.getString("role_name");
		 String  jsonObject2=jsonObject.getString("department_name");

		  System.out.print(jsonObject1);
		  System.out.print(jsonObject2);
		  if(jsonObject1.equals("Admin") || jsonObject2.equals("Dev ops")   ) {
		  return true;
		  }
		  else {
		
		  return false;

	}
		  }
//		  jedisPool.close();
		  }

	   
	   public void removeById(String id) {
	       try {
	       	entityManager.getTransaction().begin();
	       	Query query= entityManager.createQuery("DELETE FROM Project WHERE project_id = '"+id+"'");
	       	query.executeUpdate();
	   		entityManager.getTransaction().commit();
	   		 		 
		  }  catch (Exception ex) {
		            ex.printStackTrace();
		            entityManager.getTransaction().rollback();      
	   }
	       
	   }
	 
	   public void mergeById(String id, String name) {
	       try {
	       	
	       	entityManager.getTransaction().begin();
	           Query project = entityManager.createQuery("UPDATE Project set project_name='"+name+"'"
	           		+ "WHERE project_id='"+id+"'");
	           project.executeUpdate();
	   		entityManager.getTransaction().commit();
	   		 		 
		  }  catch (Exception ex) {
		            ex.printStackTrace();
		            entityManager.getTransaction().rollback();      
	   }
	       }
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
	}


