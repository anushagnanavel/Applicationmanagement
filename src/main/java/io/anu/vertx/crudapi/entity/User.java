package io.anu.vertx.crudapi.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import io.vertx.core.json.JsonObject;

@Entity
@Table(name = "user_table")
/**
 *Create the signup entity inside this class
 */
public class User implements Serializable{

	@Id

	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String id;

	@Column(name = "employee_id",unique = true, nullable=false)

    private String eid;

	@Column(name = "name",unique = true, nullable=false)
    private String name;

	@Column(name = "last_name",unique = true, nullable=false)
    private String lname;

	@Column(name = "email",unique = true, nullable=false)
    private String email;

    @Column(name = "password",unique = true, nullable=false)
    private String password;

   
//    @Column(name = "role_id", nullable=false)
//    private String role_id;

//    @Column(name = "department_id", nullable=false)
//   private Integer department_id;

//    @Column(name = "designation_id", nullable=false)
//    private String designation_id;


   @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name="department_id")
    private Department department;

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }




    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
     @JoinColumn(name="role_id")
     private Role role;

     public Role getRole() {
         return role;
     }

     public void setRole(Role role) {
         this.role = role;
     }


     @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
     @JoinColumn(name="designation_id")
     private Designation designation;

     public Designation getDesignation() {
         return designation;
     }

     public void setDesignation(Designation designation) {
         this.designation = designation;
     }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

   

//    public String getRole_id() {
//        return role_id;
//    }
//        public void setRole_id(String role_id) {
//            this.role_id = role_id;
//    }
//     public Integer getDepartment_id() {
//          return department_id;
//        }
//      public void setDepartment_id(Integer department_id) {
//          this.department_id = department_id;
//        }
//      public String getDesignation_id() {
//          return designation_id;
//        }
//      public void setDesignation_id(String designation_id) {
//          this.designation_id = designation_id;
//        }
    public String toJsonString(){
         return String.valueOf(JsonObject.mapFrom(this));
    }

}
