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

@Configurable
@Entity
public class Customer extends Person {

    @ManyToOne
    private CustomerGrade grade;
	private String reference;
	private double balance;

	public static long countCustomers() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Customer o", Long.class).getSingleResult();
    }

	public static List<Customer> findAllCustomers() {
        return entityManager().createQuery("SELECT o FROM Customer o", Customer.class).getResultList();
    }

	public static Customer findCustomer(Long id) {
        if (id == null) return null;
        return entityManager().find(Customer.class, id);
    }

	public String getReference() {
		return reference;
	}

	public static List<Customer> findCustomerEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Customer o", Customer.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	@Transactional
    public Customer merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Customer merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

	public static Customer fromJsonToCustomer(String json) {
        return new JSONDeserializer<Customer>().use(null, Customer.class).deserialize(json);
    }

	public static String toJsonArray(Collection<Customer> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<Customer> fromJsonArrayToCustomers(String json) {
        return new JSONDeserializer<List<Customer>>().use(null, ArrayList.class).use("values", Customer.class).deserialize(json);
    }

	public CustomerGrade getGrade() {
        return this.grade;
    }

	public void setGrade(CustomerGrade grade) {
        this.grade = grade;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public void setReference(String customer_Reco) {
		this.reference = customer_Reco;
		
	}

	public void setBalance(double nowPayingAmt) {
		this.balance = 	nowPayingAmt;
	}

	public double getBalance() {
		return balance;
	}
	
}
