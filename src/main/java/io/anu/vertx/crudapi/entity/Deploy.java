package io.anu.vertx.crudapi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import io.vertx.core.json.JsonObject;

@Entity
@Table(name = "deploy")

public class Deploy {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name="system-uuid", strategy = "string")
	

	@Column(name = "deploy_id",unique = true, nullable=false)

    private Integer did;

	@Column(name = "module_name",unique = true, nullable=false)
    private String mname;

	@Column(name = "branch_name",unique = true, nullable=false)
    private String bname;

	@Column(name = "commit_id",unique = true, nullable=false)
    private String cid;

    @Column(name = "otherschanges",unique = true, nullable=false)
    private String otherschanges;

    @Column(name = "status",unique = true, nullable=false)
    private String status;
    
	@Column(name = "handle_by",unique = true, nullable=false)
    private String handleby;


	@Column(name = "rs",unique = true, nullable=false)
    private String rs;

    @Column(name = "proo_main",unique = true, nullable=false)
    private String proomain;
//
//    @Column(name = "createdat",unique = true, nullable=false)
//    private String createdat;
    
    public Integer getDid() {
        return did;
    }

    public void setDid(Integer did) {
        this.did = did;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public String getCid() {
        return  cid;
    }

    public void setCid(String  cid) {
        this. cid =  cid;
    }

    public String getotherschanges() {
        return otherschanges;
    }

    public void setotherschangesl(String otherschanges) {
        this.otherschanges = otherschanges;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHandleby() {
        return handleby;
    }
    public void setHandleby(String handleby) {
        this.handleby = handleby;
    }
    
    public String getRs() {
        return rs;
    }
    public void setRs(String rs) {
        this.rs = rs;
    }
    public String getProo_main() {
        return proomain;
    }
    public void setProo_main(String proomain) {
        this.proomain = proomain;
    }
//    public String getCreatedat() {
//        return proomain;
//    }
//    public void setCreatedat(String createdat) {
//        this.createdat =createdat;
//    }
   
    public String toJsonString(){
         return String.valueOf(JsonObject.mapFrom(this));
    }

}



