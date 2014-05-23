package org.nath.model;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
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
public class Brand {

    public static class BrandComparator implements Comparator<Brand>{
    	@Override
		public int compare(Brand o1, Brand o2) {
    		try{
			return (int) (o1.getId() - o2.getId());
    		}catch(Exception e){
    			return -1;
    		}
		}
	}

	private String brandName;

	public String getBrandName() {
        return this.brandName;
    }

	public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new Brand().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countBrands() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Brand o", Long.class).getSingleResult();
    }

	public static List<Brand> findAllBrands() {
        return entityManager().createQuery("SELECT o FROM Brand o", Brand.class).getResultList();
    }

	public static Brand findBrand(Long id) {
        if (id == null) return null;
        return entityManager().find(Brand.class, id);
    }

	public static List<Brand> findBrandEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Brand o", Brand.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Brand attached = Brand.findBrand(this.id);
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
    public Brand merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Brand merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

	public static Brand fromJsonToBrand(String json) {
        return new JSONDeserializer<Brand>().use(null, Brand.class).deserialize(json);
    }

	public static String toJsonArray(Collection<Brand> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<Brand> fromJsonArrayToBrands(String json) {
        return new JSONDeserializer<List<Brand>>().use(null, ArrayList.class).use("values", Brand.class).deserialize(json);
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
