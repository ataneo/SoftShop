package org.nath.model;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Configurable
public class Employee extends Person {
	
	

    @Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
    	
    	if(obj instanceof Employee && ((Employee) obj).getId()==this.getId())
			return true;
    	
    	return false;
	}

	@ManyToOne
    private Authentication authentication;

	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

	public static Employee fromJsonToEmployee(String json) {
        return new JSONDeserializer<Employee>().use(null, Employee.class).deserialize(json);
    }

	public static String toJsonArray(Collection<Employee> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<Employee> fromJsonArrayToEmployees(String json) {
        return new JSONDeserializer<List<Employee>>().use(null, ArrayList.class).use("values", Employee.class).deserialize(json);
    }

	public Authentication getAuthentication() {
        return this.authentication;
    }

	public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public static long countEmployees() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Employee o", Long.class).getSingleResult();
    }

	public static List<Employee> findAllEmployees() {
        return entityManager().createQuery("SELECT o FROM Employee o", Employee.class).getResultList();
    }

	public static Employee findEmployee(Long id) {
        if (id == null) return null;
        return entityManager().find(Employee.class, id);
    }

	public static List<Employee> findEmployeeEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Employee o", Employee.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	@Transactional
    public Employee merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Employee merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
}
