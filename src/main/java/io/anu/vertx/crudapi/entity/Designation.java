package io.anu.vertx.crudapi.entity;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.vertx.core.json.JsonObject;
@Entity
@Table(name = "Designation")
public class Designation implements Serializable {

		@Id

	    @Column(name = "designation_id", nullable=false)
	    private Integer designation_id;

	    @Column(name ="designation_name", nullable=false)
	    private String designation_name;


	    public Integer getDesignation_id() {
	        return designation_id;
	    }
	    public void setDesignation_id(Integer designation_id ) {
	        this.designation_id = designation_id ;
	    }

	    public String getDesignation_name() {
	        return designation_name;
	    }
	    public void setDesignation_name(String designation_name ) {
	        this.designation_name= designation_name ;
	    }
	    public String toJsonString(){
            return String.valueOf(JsonObject.mapFrom(this));
        }
}