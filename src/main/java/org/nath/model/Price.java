package org.nath.model;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;

@Configurable
@Entity
public class Price {

    private Double price;

    @ManyToOne
    private Brand brand;
    
    @ManyToOne
    private Roll roll;
    
    /*@Override
	public boolean equals(Object arg0) {
		if(!(arg0 instanceof Price))
			return false;
		
		Price newStockCount = (Price) arg0;
		
		if(this.getBrand()==newStockCount.getBrand()&&this.getRoll()==newStockCount.getRoll())
			return true;
		
		return false;
	}*/
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		int hashCode = this.getBrand().hashCode();
		hashCode = 19*hashCode + (this.getRoll()==null?22:this.getRoll().hashCode());
		return hashCode;
	}

    public Roll getRoll() {
		return roll;
	}

	public void setRoll(Roll roll) {
		this.roll = roll;
	}

	@Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date entryDate;

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new Price().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countPrices() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Price o", Long.class).getSingleResult();
    }

	public static List<Price> findAllPrices() {
        return entityManager().createQuery("SELECT o FROM Price o", Price.class).getResultList();
    }

	public static Price findPrice(Long id) {
        if (id == null) return null;
        return entityManager().find(Price.class, id);
    }

	public static List<Price> findPriceEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Price o", Price.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Price attached = Price.findPrice(this.id);
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
    public Price merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Price merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public Double getPrice() {
        return this.price;
    }

	public void setPrice(Double price) {
        this.price = price;
    }

	public Brand getBrand() {
        return this.brand;
    }

	public void setBrand(Brand brand) {
        this.brand = brand;
    }

	public Date getEntryDate() {
        return this.entryDate;
    }

	public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

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

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

	public static Price fromJsonToPrice(String json) {
        return new JSONDeserializer<Price>().use(null, Price.class).deserialize(json);
    }

	public static String toJsonArray(Collection<Price> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<Price> fromJsonArrayToPrices(String json) {
        return new JSONDeserializer<List<Price>>().use(null, ArrayList.class).use("values", Price.class).deserialize(json);
    }
}
