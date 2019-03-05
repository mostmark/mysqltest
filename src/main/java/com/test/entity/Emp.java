package com.test.entity;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "emp")
public class Emp {

    @Id
    private int empno;
    private String ename;
    private String job;
    private int mgr;
    private Date hiredate;
    private int sal;
    private int comm;
    private int deptno;

    public Emp() {

    }

    public Emp(int empno,
            String ename,
            String job,
            int mgr,
            Date hiredate,
            int sal,
            int comm,
            int deptno
    ) {
        this.empno = empno;
        this.ename = ename;
        this.job = job;
        this.mgr = mgr;
        this.hiredate = hiredate;
        this.sal = sal;
        this.comm = comm;
        this.deptno = deptno;
    }

    public Emp(JsonObject input) {
        String hireDateStr;
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

        this.empno = input.getInt("empno");
        this.ename = input.getString("ename");
        this.job = input.getString("job");
        this.mgr = input.getInt("mgr");
        hireDateStr = input.getString("hiredate");
        this.sal = input.getInt("sal");
        this.comm = input.getInt("comm");
        this.deptno = input.getInt("deptno");

        if (hireDateStr != null) {
            try {
                this.hiredate = dateFormatter.parse(hireDateStr);
            } catch (ParseException e) {
                System.out.println("Could not parse date string. Value = " + hireDateStr);
            }
        }

    }

    public JsonObject toJson() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        return Json.createObjectBuilder().add("empno", empno)
                .add("ename", ename)
                .add("job", job)
                .add("mgr", mgr)
                .add("hiredate", dateFormatter.format(hiredate))
                .add("sal", sal)
                .add("comm", comm)
                .add("deptno", deptno)
                .build();
    }

    public int getEmpno() {
        return empno;
    }

    public void setEmpno(int empno) {
        this.empno = empno;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public int getMgr() {
        return mgr;
    }

    public void setMgr(int mgr) {
        this.mgr = mgr;
    }

    public Date getHiredate() {
        return hiredate;
    }

    public void setHiredate(Date hiredate) {
        this.hiredate = hiredate;
    }

    public int getSal() {
        return sal;
    }

    public void setSal(int sal) {
        this.sal = sal;
    }

    public int getComm() {
        return comm;
    }

    public void setComm(int comm) {
        this.comm = comm;
    }

    public int getDeptno() {
        return deptno;
    }

    public void setDeptno(int deptno) {
        this.deptno = deptno;
    }

}
