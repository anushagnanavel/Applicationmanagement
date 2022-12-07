package io.anu.vertx.crudapi.service;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.anu.vertx.crudapi.entity.User;
import io.anu.vertx.crudapi.repository.SignupDao;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;





public class SignupService {
	private SignupDao signupDao = SignupDao.getInstance();


	/* REGISTER*/

	public void signup(RoutingContext context,User newSignup, Handler<AsyncResult<User>> handler) {
	     Future<User> future = Future.future();
	     future.setHandler(handler);
	  try {
		  // This condition check the manditary fields must filled
			if(newSignup.getName().isEmpty() || newSignup.getPassword().isEmpty()||newSignup.getEmail().isEmpty()) {
	     		 System.out.print("Please filled the manditatory Fields");
	             sendError("Please filled the manditatory Fields", context.response(),400);
	             System.out.println("ghsdgy");
	             }
			else{
			  //This condition check user name already exit
			  User user = signupDao.getByUserEid(newSignup.getName());
			  if(user!=null) {
				  sendError("User exist", context.response(),400);}
			  else {
					 //This condition check Password validation
			    	 String regex = "^(?=.*[0-9])"
		                     + "(?=.*[a-z])(?=.*[A-Z])"
		                     + "(?=.*[@#$%^&+=*!])"
		                     + "(?=\\S+$).{8,20}$";

			    	 Pattern p = Pattern.compile(regex);
			    	 Matcher m = p.matcher(newSignup.getPassword());
			    	 if(m.matches()){
			    		  //This condition check email validation
			    		  String regex1 = "^(.+)@(.+)$";
			    		  Pattern pattern = Pattern.compile(regex1);
			    		  Matcher matcher = pattern.matcher(newSignup.getEmail());
			    		  if(matcher.matches()){
						    System.out.print("success");
						    signupDao.persist(newSignup);	}
					      else {
					    	sendError("Please give a valid Email", context.response(),400);}
			    	 	}
			    	 else{
						  sendError("Password must have length 8 characters,one Uppercase,"
						  		+ "one special character and one digit", context.response(),400);}
			  	}
			  	future.complete();}
		 	  }catch (Throwable ex) {
			            future.fail(ex);}
		}


	/*LOGIN CONDITION */

	public void login(RoutingContext context,User newSignup, Handler<AsyncResult<User>> handler) {
	     Future<User> future = Future.future();
	     future.setHandler(handler);
			 User signup = signupDao.getByEid ( context,newSignup.getEid());
			if(newSignup. getEid().equals(signup. getEid()) && newSignup.getPassword().equals(signup.getPassword()));
			     {
			 System.out.print("your login success");

	/* jeddis connection*/

			  JedisPool jedisPool = new JedisPool ( "localhost", 6379);
			  System.out.print("your login");
      		try (Jedis jedis = jedisPool.getResource())

      		{
      			String token = UUID.randomUUID().toString().toUpperCase();
      			 System.out.print("your login");
      			JsonObject JsonObject = new JsonObject();
         		 System.out.print("String");

      			JsonObject.put("uuid", signup.getId());
         		 System.out.print("wsed");

       			JsonObject.put("name", signup.getName());
       			JsonObject.put("email",signup.getEmail());
       			JsonObject.put("password", signup.getPassword());
				JsonObject.put("role_id",signup.getRole().getRole_Id());
				JsonObject.put("rolename",signup.getRole().getRolename());
				JsonObject.put("designation_id",signup.getDesignation().getDesignation_id());
				JsonObject.put("designation_name",signup.getDesignation().getDesignation_name());
				JsonObject.put("department_id",signup.getDepartment().getDepartment_id());
				JsonObject.put("department_name",signup.getDepartment().getDepartment_name());

      		String	object  = JsonObject.toString();
      		 System.out.print("String");
      		/* KEYS,VALUES */
  			 System.out.print(object);

      			jedis.set(token, object);


      			jedis.expire(token, 60*60*24);

      			String value=jedis.get(token);

      			System.out.print(token);
      				jedisPool.close();
      			sendSuccesss ( "loginsuccess",token,"expiredkey", context.response(),200);
      			future.complete();
      		}catch
      		(Throwable ex) {
			    	 sendError("Login failed", context.response(),400);
			    	 future.fail(ex);
			            System.out.print("follow condition");
      		}}
	}
	public  void gettoken (RoutingContext context, String Authorization,String token,  Handler<AsyncResult<User>> handler) {
		  Future<User> future = Future.future();
	     future.setHandler(handler);

	  try {
		  if(token.equals(Authorization))



		  {

			 JedisPool jedisPool = new JedisPool ( "localhost", 6379);

			 try(Jedis jedis = jedisPool.getResource()){
				 System.out.print(token);
   	  String value=jedis.get(token);
   	 System.out.print(value);
   	 JsonObject jsonObject = new JsonObject(value);

     			 System.out.print("same name");

     			    	// sendError("name already exists", context.response(),400);


   	sendSuccessss (token,jsonObject,context.response(),200);

       future.complete(); }




     jedisPool.close();
    future.complete();
		  }

		  else {
			 sendError("unauthorization", context.response(),401);

		  }
		}catch (Throwable ex) {
   	  sendError("unauthorization", context.response(),401);
   	 System.out.print("follow ");
			            future.fail(ex);

		}
   	    }









	/**
	 * This method used to check signup data while login time
	 * @param context
	 * @param newSignup
	 * @param handler
	 */

//	public void gettoken(RoutingContext context, User newSignup, Handler<AsyncResult<User>> handler) {
//	     Future<User> future = Future.future();
//	     future.setHandler(handler);
//
//	     try {
//	     	 //This conditon is used check the given user name and password is empty or not
//	     	if(newSignup.getEid().isEmpty() || newSignup.getPassword().isEmpty()) {
//	     		 System.out.print("invalid");
//	             sendError("Invalid username and password", context.response(),400);}
//	     	else {
//	     		//This conditon is used check the given user name and password is equals to signup user name and password
//	     		 if(newSignup.getEid().equals(userDao.getByEid(context,newSignup.getEid()).getEid())&&
//	     				newSignup.getPassword().equals(userDao.getByEid(context,newSignup.getEid()).getPassword()))
//	     		 	{
//			     		 JedisPool jedisPool = new JedisPool("localhost", 6379);
//				  		  // Get the pool and use the database
//				  		  try (Jedis jedis = jedisPool.getResource()) {
//			  			  //Generates random UUID
//			  			  UUID uuid=UUID.randomUUID();
//			  			  //Convert UUID to string
//			  			  String uuidAsString = uuid.toString();
//			  			  System.out.println(uuidAsString);
//
//			  			   User user = userDao.getByUserEid(newSignup.getEid());
//			  			   System.out.println("dc");
//
//				  			//Generate the jsonObject
//				  			JsonObject jsonObject = new JsonObject();
//				  			System.out.println("jsonobject");
//							jsonObject.put("id", user.getId());
//							jsonObject.put("name",user.getEid());
//							jsonObject.put("email",user.getName());
//							jsonObject.put("email",user.getLname());
//							jsonObject.put("password",user.getPassword());
//							jsonObject.put("password",user.getCreatedat());
//							jsonObject.put("role",user.getRole());
//							jsonObject.put("role_id",user.getRole().getRole_Id());
//							jsonObject.put("rolename",user.getRole().getRolename());
//							jsonObject.put("designation",user.getDesignation());
//							jsonObject.put("designation_id",user.getDesignation().getDesignation_id());
//							jsonObject.put("designation_name",user.getDesignation().getDesignation_name());
//							jsonObject.put("department",user.getDepartment());
//							jsonObject.put("department_id",user.getDepartment().getDepartment_id());
//							jsonObject.put("department_n"+ "ame",user.getDepartment().getDepartment_name());
//
//

//	/**
//	 * This method used to update a (Forgot) Password
//	 * @param context
//	 * @param user
//	 * @param handler
//	 */
//
//    public void passwordUpdate(RoutingContext context,User user, Handler<AsyncResult<User>> handler) {
//        Future<User> future = Future.future();
//        future.setHandler(handler);
//        try {
//        	 User name = userDao.getByEmail(user.getEmail());
//        	 //This condition check Password validation
//			  if(name!=null) {
//				  String regex = "^(?=.*[0-9])"
//		                     + "(?=.*[a-z])(?=.*[A-Z])"
//		                     + "(?=.*[@#$%^&+=*])"
//		                     + "(?=\\S+$).{8,20}$";
//
//			    	 Pattern p = Pattern.compile(regex);
//			    	 Matcher m = p.matcher(user.getPassword());
//			    	  if(m.matches()){
//			    		  	System.out.print("success");
//							userDao.forgotPassword(context,user.getEmail(), user.getPassword());
//				            future.complete();
//				            sendSuccess("Password Updated", context.response(),200);
//			    	  }else {
//			    		  sendError("Password must have length 8 characters,one Uppercase,one special character and one digit", context.response(),400);}
//			    	  }
//			  else {
//				  sendError("Email not Exist", context.response(),400);}
//        	} catch (Throwable ex) {
//	        	 sendSuccess("fail ", context.response(),400);
//	             future.fail(ex);}
//    	}
//
//    /**
//     * Get User from Redis using token method
//     * @param context
//     * @param Authorization
//     * @param userToken
//     * @param handler
//     */
//    public void getUserName(RoutingContext context,String Authorization,String userToken,Handler<AsyncResult<User>> handler) {
//    Future<User> future = Future.future();
//    future.setHandler(handler);
//        try {
//        	String value=context.request().getHeader("Authorization");
//        	 System.out.print(value+"\n");
//        	 System.out.print(userToken+"\n");
//
//        	 //This condition used to check the token param equals to auth header value
//        	 if( value .equals(userToken)) {
//				          JedisPool jedisPool = new JedisPool("localhost", 6379);
//						  // Get the pool and use the database
//						  try (Jedis jedis = jedisPool.getResource()) {
//						  //Get token value from redis
//						  String result =  jedis.get(userToken);
//						  //Convert String to Json object
//						  JsonObject jsonObject = new JsonObject(result);
//						  System.out.print(jsonObject);
//						  sendMessage(userToken,jsonObject, context.response(),200);
//						  future.complete();
//						  }
//					  jedisPool.close();
//					  future.complete();
//        		 } else {
//        			 sendError("Unauthorized", context.response(),401);}
//          } catch (Throwable ex) {
//       	 sendError("Unauthorized", context.response(),401);
//         future.fail(ex);}
//	 }
//
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

     /**
      * This method used to send a success message,token,expireMessage
      * @param successMessage
      * @param token
      * @param expireMessage
      * @param response
      * @param code
      */
     private void sendToken(String successMessage,String token,String expireMessage, HttpServerResponse response,int code) {
         JsonObject jo = new JsonObject();

         jo.put("successMessage", successMessage);
         jo.put("token", token);
         jo.put("expireMessage", expireMessage);
         response
                 .setStatusCode(200)
                 .setStatusCode(code)
                 .putHeader("content-type", "application/json; charset=utf-8")
                 .putHeader("Authorization", token)
                 .end(Json.encodePrettily(jo));
     	}

     /**
      * This method used to send a token
      * @param userToken
      * @param token
      * @param response
      * @param code
      */
     private void sendMessage( String userToken,Object token,HttpServerResponse response,int code) {
         JsonObject jo = new JsonObject();
         jo.put(userToken, token);
         response
                 .setStatusCode(200)
                 .setStatusCode(code)
                 .putHeader("content-type", "application/json; charset=utf-8")
                 .end(Json.encodePrettily(jo));
     	}



     private void sendSuccesss (String successmessage,String token,String expiremessage,HttpServerResponse response,int code) {
         JsonObject jo = new JsonObject();

         jo.put("successMessage",successmessage);


         jo.put("token",token);

         jo.put("expiremessage",expiremessage);





         response
                 .setStatusCode(200)
                 .setStatusCode(code)
                 .putHeader("content-type", "application/json; charset=utf-8")
                 .end(Json.encodePrettily(jo));
     }
     private void sendSuccessss( String token,JsonObject jsonObject, HttpServerResponse response,int code) {
	        JsonObject jo = new JsonObject();
	        jo.put( token,jsonObject);

	        response
	                .setStatusCode(200)
	                .setStatusCode(code)
	                .putHeader("content-type", "application/json; charset=utf-8")
	                .end(Json.encodePrettily(jo));
	    }
}


