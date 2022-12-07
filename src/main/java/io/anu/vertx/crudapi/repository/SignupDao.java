package io.anu.vertx.crudapi.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

import io.anu.vertx.crudapi.entity.User;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;


/**
 * This class handles the user data transactions
 * @author e1066
 */
public class SignupDao {
    private static SignupDao instance;
    protected EntityManager entityManager;

    public static SignupDao getInstance()
    	{
        if (instance == null){
            instance = new SignupDao();
        }
        return instance;
    	}

    private SignupDao()
    	{
        entityManager = getEntityManager();
    	}

    private EntityManager getEntityManager()
    	{
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("crudHibernatePU");
        if (entityManager == null) {
            entityManager = factory.createEntityManager();
        }
        return entityManager;
    	}

    @SuppressWarnings("unchecked")
    public List<User> findAll() {
        return entityManager.createQuery("FROM " + User.class.getName()).getResultList();
    }
    /**
     *  This method should return user info for given user name
     * @param name
     * @return
     */

	 public User getByUserEid(String eid) {
		 User user = null;
	    	try {
	    		List<User> users = entityManager.createQuery(
			    		"FROM User WHERE eid = :eid", User.class)
		          .setParameter("eid", eid)
		          .getResultList();
	    		user=users.get(0);
	    		System.out.println("gthcda");
	    	}
	    	catch(Exception ex) {
	    	ex.printStackTrace();
	    	}
	    	return user;
	    	}

	 /**
	  * This method check the given user name same as user table exsiting user name
	  * @param context
	  * @param name
	  * @return
	  */

     public User getByEid(RoutingContext context,String eid)
	  {
	      try{Object result = entityManager.createQuery( "SELECT s FROM User s WHERE s.eid LIKE :eid")
	    	        .setParameter("eid", eid)
	    	        .getSingleResult();
	      System.out.print(result.toString());
	      System.out.println("tdj");


	      if (result != null) {
	          return (User) result;}}
	      catch(NoResultException nre){
	    	  sendError("Login failed", context.response(),400);
	      }
	     return null;
	  }


	  /**
	   * This method handles the post call transaction for sigup data
	   * @param user
	   */
     public void persist(User user)
 	{
	        try {
	            entityManager.getTransaction().begin();
	            System.out.println("hai");
	            entityManager.persist(user);
	            System.out.println("haihai");
	            entityManager.getTransaction().commit();
	        } catch (Exception ex) {
	        	 System.out.println("shb");
	            ex.printStackTrace();
	            entityManager.getTransaction().rollback();
	            System.out.println("cdh");
	        }
	    }


      /**
       * This method should return user info for given user_email
       * @param email
       * @return
       */

      public User getByEmail(String email) {
 		 User user = null;
 	    	try {
 	    		List<User> users = entityManager.createQuery(
 			    		"FROM User WHERE email = :email", User.class)
 		          .setParameter("email", email)
 		          .getResultList();
 	    		user=users.get(0);
 	    	}
 	    	catch(Exception ex) {
 	    	ex.printStackTrace();
 	    	}
 	    	return user;
 	    	}

      /**
       * This method used to update a password
       * @param context
       * @param email
       * @param password
       */
//      public void forgotPassword(RoutingContext context,String email,String password)
//  		{
//    	  try {
//
//	            entityManager.getTransaction().begin();
//	            Query user = entityManager.createQuery("UPDATE User set password='"+password+"'  WHERE email='"+email+"'");
//	            user.executeUpdate();
//        		entityManager.getTransaction().commit();
//        		sendSuccess(" Password Updated", context.response(),200);
//    	  }  catch (Exception ex) {
//		            ex.printStackTrace();
//		            entityManager.getTransaction().rollback();
//		        }
//	    }
//
      /**
       * This method send Error message
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

      /**
       * This method send Success message
       * @param errorMessage
       * @param response
       * @param code
       */
      private void sendSuccess(String successMessage,HttpServerResponse response,int code) {
    	  JsonObject jo = new JsonObject();
          jo.put("successMessage", successMessage);
    	  response
	              .setStatusCode(200)
	              .setStatusCode(code)
	              .putHeader("content-type", "application/json; charset=utf-8")
	              .end(Json.encodePrettily(jo));
	  }}

