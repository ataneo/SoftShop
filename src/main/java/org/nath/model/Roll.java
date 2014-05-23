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

@Entity
@Configurable
public class Roll {
	
	public static class RollComparator implements Comparator<Roll>{

		@Override
		public int compare(Roll o1, Roll o2) {
			// TODO Auto-generated method stub
			try {
				return (int) (o1.getId()-o2.getId());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return -1;
			}
		}
		
	}

    private String name;

    private int firstDimention;

    private int secondDimention;

    private long stock=0;

	public String getName() {
        return this.name;
    }

	public void setName(String name) {
        this.name = name;
    }

	public int getFirstDimention() {
        return this.firstDimention;
    }

	public void setFirstDimention(int firstDimention) {
        this.firstDimention = firstDimention;
    }

	public int getSecondDimention() {
        return this.secondDimention;
    }

	public void setSecondDimention(int secondDimention) {
        this.secondDimention = secondDimention;
    }

	public long getStock() {
        return this.stock;
    }

	public void setStock(long stock) {
        this.stock = stock;
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new Roll().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countRolls() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Roll o", Long.class).getSingleResult();
    }

	public static List<Roll> findAllRolls() {
        return entityManager().createQuery("SELECT o FROM Roll o", Roll.class).getResultList();
    }

	public static Roll findRoll(Long id) {
        if (id == null) return null;
        return entityManager().find(Roll.class, id);
    }

	public static List<Roll> findRollEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Roll o", Roll.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Roll attached = Roll.findRoll(this.id);
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
    public Roll merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Roll merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

	public static Roll fromJsonToRoll(String json) {
        return new JSONDeserializer<Roll>().use(null, Roll.class).deserialize(json);
    }

	public static String toJsonArray(Collection<Roll> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<Roll> fromJsonArrayToRolls(String json) {
        return new JSONDeserializer<List<Roll>>().use(null, ArrayList.class).use("values", Roll.class).deserialize(json);
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
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
