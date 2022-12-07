package io.anu.vertx.crudapi.service;
import java.util.List;
import io.anu.vertx.crudapi.entity.Project;
import io.anu.vertx.crudapi.repository.ProjectDao;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class ProjectService {
    private ProjectDao projectDao = ProjectDao.getInstance();

    /**
     * This method used to get project list
     * @param context
     * @param Authorization
     * @param handler
     */
    public void list(RoutingContext context,String Authorization, Handler<AsyncResult<List<Project>>> handler){
        Future<List<Project>> future = Future.future();
        future.setHandler(handler);
        if(Authorization.isEmpty()) {		
    		sendError("Unauthorized", context.response(),401);
    	}
        else {
	        try {
	        	
	        	if ( projectDao.getUserProject(context,Authorization)) {
	       
	            List<Project> result1 = projectDao.findAll();
	            future.complete(result1);
			  }
			  else {
				  System.out.print("fail");
				  sendError("Unauthorized - Admin/Dev ops only view a Project", context.response(),401);
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
     * @param newproject
     * @param handler
     */
	public void save(RoutingContext context,String Authorization, Project newproject,Handler<AsyncResult<Project>> handler) {
		Future<Project> future = Future.future();
        future.setHandler(handler);
        if(Authorization.isEmpty()) {
    		
    		
    		sendError("Unauthorized", context.response(),401);
    	}else {
        
        try {
        	if ( projectDao.getUserProject(context,Authorization)) {

						
				        	projectDao.persistProject(newproject);
				        	future.complete();
				            
						  }
						  else {
							  System.out.print("fail");
							  sendError("Admin/Dev ops only create a Project", context.response(),401);
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
    public void update(RoutingContext context,String Authorization,String id,Project project, Handler<AsyncResult<Project>> handler) {
        Future<Project> future = Future.future();
        future.setHandler(handler);
        if(Authorization.isEmpty()) {
    		sendError("Unauthorized", context.response(),401);
    	}else {
        
        try {
        	if ( projectDao.getUserProject(context,Authorization)) {		
        	projectDao.mergeById(id,project.getProjectname());
        	future.complete();
			  }
			  else {
				  System.out.print("fail");
				  sendError("Admin/Dev ops only update a Project", context.response(),401);
			  }	
          future.complete();	   	 
		} 
		catch (Throwable ex) {
		future.fail(ex);
		}}
		}

    /**
     * This method used to delete a project
     * @param id
     * @param handler
     */
    public void remove(RoutingContext context,String Authorization,String id, Handler<AsyncResult<Project>> handler) {
        Future<Project> future = Future.future();
        future.setHandler(handler);
        if(Authorization.isEmpty()) {
    		sendError("Unauthorized", context.response(),401);
    	}else {
        
        try {
        	if ( projectDao.getUserProject(context,Authorization)) {			
        	projectDao.removeById(id);
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

