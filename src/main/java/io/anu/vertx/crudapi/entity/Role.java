package io.anu.vertx.crudapi.entity;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.vertx.core.json.JsonObject;

@Entity
@Table(name = "role")
/**
 *Create the role entity inside this class
 */
public class Role implements Serializable {

	@Id
	@Column(name = "role_id",unique = true)
	private int role_id;

	@Column(name = "role_name",unique = true)
    private String rolename;

	public int getRole_Id() {
		return role_id;
	}

	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String toJsonString(){
         return String.valueOf(JsonObject.mapFrom(this));
    }
}






