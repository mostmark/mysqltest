package com.test.entity;


import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class EmpManager {

    @PersistenceContext(name = "employees")
    EntityManager em;

    public List<Emp> getAllEmps(){
        return em.createQuery("SELECT e FROM Emp e", Emp.class).getResultList();
    }

    public List<Emp> findEmpsByName(String name){
        return em.createQuery(
                "SELECT e FROM Emp e WHERE e.ename LIKE :eName", Emp.class)
                .setParameter("eName", name + "%")
                .getResultList();
    }

    public Emp getEmp(int id) {
        return em.find(Emp.class, id);
    }

    public void addEmp(Emp emp){
        em.persist(emp);
    }

    public void deleteEmp(int id){
        Emp e = em.find(Emp.class, id);
        if(e != null){
            em.remove(e);
        }
    }

    public void updateEmp(Emp emp){
        em.merge(emp);
    }

}