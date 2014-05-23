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
import javax.persistence.Version;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

@Configurable
@Entity
public class PriceRange {

    private int toRange;

    private int fromRange;

    private String price;

    private String doubleSidedprice;

	@PersistenceContext
    transient EntityManager entityManager;
	
	public PriceRange(){		
	}

	public PriceRange(int toRange, int fromRange, String price,
			String doubleSidedprice) {
		super();
		this.toRange = toRange;
		this.fromRange = fromRange;
		this.price = price;
		this.doubleSidedprice = doubleSidedprice;
	}
	

	public static final EntityManager entityManager() {
        EntityManager em = new PriceRange().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countPriceRanges() {
        return entityManager().createQuery("SELECT COUNT(o) FROM PriceRange o", Long.class).getSingleResult();
    }

	public static List<PriceRange> findAllPriceRanges() {
        return entityManager().createQuery("SELECT o FROM PriceRange o", PriceRange.class).getResultList();
    }

	public static PriceRange findPriceRange(Long id) {
        if (id == null) return null;
        return entityManager().find(PriceRange.class, id);
    }

	public static List<PriceRange> findPriceRangeEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM PriceRange o", PriceRange.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            PriceRange attached = PriceRange.findPriceRange(this.id);
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
    public PriceRange merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        PriceRange merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

	public static PriceRange fromJsonToPriceRange(String json) {
        return new JSONDeserializer<PriceRange>().use(null, PriceRange.class).deserialize(json);
    }

	public static String toJsonArray(Collection<PriceRange> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<PriceRange> fromJsonArrayToPriceRanges(String json) {
        return new JSONDeserializer<List<PriceRange>>().use(null, ArrayList.class).use("values", PriceRange.class).deserialize(json);
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public int getToRange() {
        return this.toRange;
    }

	public void setToRange(int toRange) {
        this.toRange = toRange;
    }

	public int getFromRange() {
        return this.fromRange;
    }

	public void setFromRange(int fromRange) {
        this.fromRange = fromRange;
    }

	public String getPrice() {
        return this.price;
    }

	public void setPrice(String price) {
        this.price = price;
    }

	public String getDoubleSidedprice() {
        return this.doubleSidedprice;
    }

	public void setDoubleSidedprice(String doubleSidedprice) {
        this.doubleSidedprice = doubleSidedprice;
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
}
