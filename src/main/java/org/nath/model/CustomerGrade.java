package org.nath.model;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Configurable
public class CustomerGrade {

    @NotNull
    private String grade;
    
    

    @Override
	public boolean equals(Object obj) {
    	if(!(obj instanceof CustomerGrade))
			return false;
    	
    	CustomerGrade newCustomerGrade = (CustomerGrade) obj;
    	if(grade.equals(newCustomerGrade.getGrade()))
    		return true;
    	else return false;		
	}

	private String description;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

	@Version
    @Column(name = "version")
    private Integer version;

	public Long getId() {
        return this.id;
    }

	public void setId(Long id) {
        this.id = id;
    }

	public Integer getVersion() {
        return this.version;
    }

	public void setVersion(Integer version) {
        this.version = version;
    }

	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

	public static CustomerGrade fromJsonToCustomerGrade(String json) {
        return new JSONDeserializer<CustomerGrade>().use(null, CustomerGrade.class).deserialize(json);
    }

	public static String toJsonArray(Collection<CustomerGrade> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<CustomerGrade> fromJsonArrayToCustomerGrades(String json) {
        return new JSONDeserializer<List<CustomerGrade>>().use(null, ArrayList.class).use("values", CustomerGrade.class).deserialize(json);
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new CustomerGrade().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countCustomerGrades() {
        return entityManager().createQuery("SELECT COUNT(o) FROM CustomerGrade o", Long.class).getSingleResult();
    }

	public static List<CustomerGrade> findAllCustomerGrades() {
        return entityManager().createQuery("SELECT o FROM CustomerGrade o", CustomerGrade.class).getResultList();
    }

	public static CustomerGrade findCustomerGrade(Long id) {
        if (id == null) return null;
        return entityManager().find(CustomerGrade.class, id);
    }

	public static List<CustomerGrade> findCustomerGradeEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM CustomerGrade o", CustomerGrade.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	@Transactional
    public void persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }

	@Transactional
    public void remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            CustomerGrade attached = CustomerGrade.findCustomerGrade(this.id);
            this.entityManager.remove(attached);
        }
    }

	@Transactional
    public void flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }

	@Transactional
    public void clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }

	@Transactional
    public CustomerGrade merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        CustomerGrade merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public String getGrade() {
        return this.grade;
    }

	public void setGrade(String grade) {
        this.grade = grade;
    }

	public String getDescription() {
        return this.description;
    }

	public void setDescription(String description) {
        this.description = description;
    }
	
	public static CustomerGrade find(String name){
		
		TypedQuery<CustomerGrade> query =  CustomerGrade.entityManager()
				.createQuery("from CustomerGrade where grade = ?", CustomerGrade.class);
		query.setParameter(1, name);
		
		
		CustomerGrade customerGrade = null;
		try {
			customerGrade = query.getSingleResult();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			customerGrade = new CustomerGrade();
			customerGrade.setGrade(name);
			customerGrade.persist();
		}
		return customerGrade;
		
	}
}
