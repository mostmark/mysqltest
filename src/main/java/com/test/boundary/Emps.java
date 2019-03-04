package com.test.boundary;

import com.test.entity.Emp;
import com.test.entity.EmpManager;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("employees")
public class Emps {

    @EJB
    EmpManager empManager;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Emp> getEmps(@QueryParam("name") String name) {
        if (name != null && name.length() > 0) {
            return empManager.findEmpsByName(name);
        } else {
            return empManager.getAllEmps();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Emp getEmp(@PathParam("id") int id) {
        return empManager.getEmp(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject addEmp(JsonObject person) {

        Emp emp = new Emp(person);
        empManager.addEmp(emp);

        return Json.createObjectBuilder().add("timestamp", new Date().toString()).add("result", "ok").build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject updateEmp(JsonObject person) {

        Emp emp = new Emp(person);
        empManager.updateEmp(emp);

        return Json.createObjectBuilder().add("timestamp", new Date().toString()).add("result", "ok").build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public JsonObject deleteEmp(@PathParam("id") int id) {

        empManager.deleteEmp(id);

        return Json.createObjectBuilder().add("timestamp", new Date().toString()).add("result", "ok").build();
    }

}
