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
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

@Configurable
@Entity
public class District {

    @Column(unique = true)
    private String district;

	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

	public static District fromJsonToDistrict(String json) {
        return new JSONDeserializer<District>().use(null, District.class).deserialize(json);
    }

	public static String toJsonArray(Collection<District> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<District> fromJsonArrayToDistricts(String json) {
        return new JSONDeserializer<List<District>>().use(null, ArrayList.class).use("values", District.class).deserialize(json);
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new District().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countDistricts() {
        return entityManager().createQuery("SELECT COUNT(o) FROM District o", Long.class).getSingleResult();
    }

	public static List<District> findAllDistricts() {
        return entityManager().createQuery("SELECT o FROM District o", District.class).getResultList();
    }

	public static District findDistrict(Long id) {
        if (id == null) return null;
        return entityManager().find(District.class, id);
    }

	public static List<District> findDistrictEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM District o", District.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            District attached = District.findDistrict(this.id);
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
    public District merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        District merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
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

	public String getDistrict() {
        return this.district;
    }

	public void setDistrict(String district) {
        this.district = district;
    }
	
	public static District find(String name){
		
		TypedQuery<District> query =  District.entityManager()
				.createQuery("from District where district = ?", District.class);
		query.setParameter(1, name);
		
		
		District district = null;
		try {
			district = query.getSingleResult();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			district = new District();
			district.setDistrict(name);
			district.persist();
		}
		return district;
		
	}
}
