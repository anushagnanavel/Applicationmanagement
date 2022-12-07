package io.anu.vertx.crudapi.entity;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import io.vertx.core.json.JsonObject;

@Entity
@Table(name = "project")
/**
 *Create the project entity inside this class
 */
public class Project implements Serializable {

		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "project_id")
		private int projectId;	  
		
		@Column(name = "project_name",unique = true)
	    private String projectName;
	    
	    public String getProjectname() {
	        return projectName;
	    }

	    public void setProjectname(String projectName) {
	        this.projectName = projectName;
	    }
	    
	    public Integer getProjectId() {
	        return projectId;
	    }

	    public void setProjectId(Integer projectId) {
	        this.projectId = projectId;
	    }
	    public String toJsonString(){
	         return String.valueOf(JsonObject.mapFrom(this));
	    }
	}


