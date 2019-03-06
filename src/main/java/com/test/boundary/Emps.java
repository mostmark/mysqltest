package com.test.boundary;

import com.test.entity.Emp;
import com.test.entity.EmpManager;
import java.util.ArrayList;
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
    public List<JsonObject> getEmps(@QueryParam("name") String name) {

        List<JsonObject> employees = null;

        if (name != null && name.length() > 0) {
            List<Emp> emps = empManager.findEmpsByName(name);
            if (emps != null) {
                employees = new ArrayList<JsonObject>(emps.size());
                for (Emp emp : emps) {
                    employees.add(emp.toJson());
                }
            }
            return employees;
        } else {
            List<Emp> emps = empManager.getAllEmps();
            if (emps != null) {
                employees = new ArrayList<JsonObject>(emps.size());
                for (Emp emp : emps) {
                    employees.add(emp.toJson());
                }
            }
        }

        return employees;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public JsonObject getEmp(@PathParam("id") int id) {

        Emp emp = empManager.getEmp(id);

        if (emp != null) {
            return emp.toJson();
        } else {
            return null;
        }
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
