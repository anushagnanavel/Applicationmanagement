package io.anu.vertx.crudapi.service;
import java.util.List;

import io.anu.vertx.crudapi.entity.Deploy;
import io.anu.vertx.crudapi.entity.Project;
import io.anu.vertx.crudapi.repository.DeployDao;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class DeployService {
	
	private DeployDao deployDao = DeployDao.getInstance();

    /**
     * This method used to get Deploy list
     * @param context
     * @param Authorization
     * @param handler
     */
    public void list(RoutingContext context,String Authorization, Handler<AsyncResult<List<Deploy>>> handler){
        Future<List<Deploy>> future = Future.future();
        future.setHandler(handler);
        if(Authorization.isEmpty()) {		
    		sendError("Unauthorized", context.response(),401);
    	}
        else {
	        try {
	        	
	        	if ( deployDao.getUserDeploy(context,Authorization)) {
	       
	            List<Deploy> result1 = deployDao.findAll();
	            future.complete(result1);
			  }
			  else {
				  System.out.print("fail");
				  sendError("Unauthorized - Admin/Dev ops only view a Deploy", context.response(),400);
			  }	
				            
	        	future.complete();	   	 
	        	} 
	        catch (Throwable ex) {
	            future.fail(ex);
	        }
        }
    }


    /**
     * 
     * @param context
     * @param Authorization
     * @param newDeploy
     * @param handler
     */
	public void saves(RoutingContext context,String Authorization, Deploy newDeploy,Handler<AsyncResult<Deploy>> handler) {
		Future<Deploy> future = Future.future();
        future.setHandler(handler);
        if(Authorization.isEmpty()) {
    		
    		
    		sendError("Unauthorized", context.response(),401);
    	}else {
        
        try {
        	if ( deployDao.getUserDeploy(context,Authorization)) {

						
				        	deployDao.persistDeploy(newDeploy);
				        	future.complete();
				            
						  }
						  else {
							  System.out.print("fail");
							  sendError("Admin/Dev ops only create a Deploy", context.response(),400);
						  }	
			            future.complete();	   	 
        	} 
        catch (Throwable ex) {
            future.fail(ex);
        }}
    }
	
	
	

	 /**
	 * This method used to update project
	 * @param id
	 * @param project
	 * @param handler
	 */
//    public void updated(RoutingContext context,String Authorization,String did,Deploy deploy, Handler<AsyncResult<Deploy>> handler) {
//        Future<Deploy> future = Future.future();
//        future.setHandler(handler);
//        if(Authorization.isEmpty()) {
//    		sendError("Unauthorized", context.response(),401);
//    	}else {
//        
//        try {
//        	if ( deployDao.getUserDeploy(context,Authorization)) {		
//        		deployDao.mergeByDid(did, did);
//        	future.complete();
//			  }
//			  else {
//				  System.out.print("fail");
//				  sendError("Admin/Dev ops only update a Deploy", context.response(),401);
//			  }	
//          future.complete();	   	 
//		} 
//		catch (Throwable ex) {
//		future.fail(ex);
//		}}
//		}
    public void updated(RoutingContext context,String Authorization,String did,Deploy deploy, Handler<AsyncResult<Deploy>> handler) {
        Future<Deploy> future = Future.future();
        future.setHandler(handler);
        if(Authorization.isEmpty()) {
    		sendError("Unauthorized", context.response(),401);
    	}else {
        try {
        	if ( deployDao.getUserDeploy(context,Authorization)) {		
        	deployDao.mergeById(did,deploy.getMname(),deploy.getBname()
        			,deploy.getCid(),deploy.getotherschanges(),deploy.getStatus(),deploy.getHandleby(),deploy.getProo_main(),deploy.getRs());
        	future.complete();
			  }
			  else {
				  System.out.print("fail");
				  sendError("Admin/Dev ops only update a Application", context.response(),401);
			  }	
          future.complete();	   	
		}
		catch (Throwable ex) {
		future.fail(ex);
		}}
		}

    
    
    
    public void remove(RoutingContext context,String Authorization,String did, Handler<AsyncResult<Project>> handler) {
        Future<Project> future = Future.future();
        future.setHandler(handler);
        if(Authorization.isEmpty()) {
    		sendError("Unauthorized", context.response(),401);
    	}else {
        
        try {
        	if ( deployDao.getUserDeploy(context,Authorization)) {			
        	deployDao.removeByDid(did);
        	future.complete();
			  }
			  else {
				  System.out.print("fail");
				  sendError("Admin/Dev ops only delete a Project", context.response(),401);
			  }	
            future.complete();	   	 
			} 
			catch (Throwable ex) {
			future.fail(ex);
			}}
			
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
     
     /**
      * This method used to send a success message
      * @param successMessage
      * @param response
      * @param code
      */
     private void sendSuccess(String successMessage, HttpServerResponse response,int code) {
         JsonObject jo = new JsonObject();
         jo.put("successMessage", successMessage);
         response
                 .setStatusCode(200)
                 .setStatusCode(code)
                 .putHeader("content-type", "application/json; charset=utf-8")
                 .end(Json.encodePrettily(jo));
     	}


}  							  
       							  
       							  
       							  
       							 
       						  
       						  
       	          
        
                   
                   


	