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
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Configurable
public class StockCount {

    private Long stockCount;

    @ManyToOne
    private Brand brand;
    
    @ManyToOne
    private Roll roll;

	@Override
	public boolean equals(Object arg0) {
		if(!(arg0 instanceof StockCount))
			return false;
		
		StockCount newStockCount = (StockCount) arg0;
		
		if(this.getBrand()==newStockCount.getBrand()&&this.getRoll()==newStockCount.getRoll())
			return true;
		
		return false;
	}
	
	

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		int hashCode = this.getBrand().hashCode();
		hashCode = 19*hashCode + (this.getRoll()==null?33:this.getRoll().hashCode());
		return hashCode;
	}



	public Roll getRoll() {
		return roll;
	}

	public void setRoll(Roll roll) {
		this.roll = roll;
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

	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

	public static StockCount fromJsonToStockCount(String json) {
        return new JSONDeserializer<StockCount>().use(null, StockCount.class).deserialize(json);
    }

	public static String toJsonArray(Collection<StockCount> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<StockCount> fromJsonArrayToStockCounts(String json) {
        return new JSONDeserializer<List<StockCount>>().use(null, ArrayList.class).use("values", StockCount.class).deserialize(json);
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new StockCount().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countStockCounts() {
        return entityManager().createQuery("SELECT COUNT(o) FROM StockCount o", Long.class).getSingleResult();
    }

	public static List<StockCount> findAllStockCounts() {
        return entityManager().createQuery("SELECT o FROM StockCount o", StockCount.class).getResultList();
    }

	public static StockCount findStockCount(Long id) {
        if (id == null) return null;
        return entityManager().find(StockCount.class, id);
    }

	public static List<StockCount> findStockCountEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM StockCount o", StockCount.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            StockCount attached = StockCount.findStockCount(this.id);
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
    public StockCount merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        StockCount merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public Long getStockCount() {
        return this.stockCount;
    }

	public void setStockCount(Long stockCount) {
        this.stockCount = stockCount;
    }

	public Brand getBrand() {
        return this.brand;
    }

	public void setBrand(Brand brand) {
        this.brand = brand;
    }
}
