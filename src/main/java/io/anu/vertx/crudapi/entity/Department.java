package io.anu.vertx.crudapi.entity;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.vertx.core.json.JsonObject;

@Entity
@Table(name = "department")
/**
 *Create the role entity inside this class
 */
public class Department implements Serializable {

	@Id
	@Column(name = "department_id")
	private Integer department_id;

	@Column(name = "department_name",unique = true)
    private String department_name;

    public Integer getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(Integer department_id) {
        this.department_id = department_id;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }
    public String toJsonString(){
         return String.valueOf(JsonObject.mapFrom(this));
    }
}






