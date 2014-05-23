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
public class PoliceStation {

    @Column(unique = true)
    private String policeStation;

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

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new PoliceStation().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countPoliceStations() {
        return entityManager().createQuery("SELECT COUNT(o) FROM PoliceStation o", Long.class).getSingleResult();
    }

	public static List<PoliceStation> findAllPoliceStations() {
        return entityManager().createQuery("SELECT o FROM PoliceStation o", PoliceStation.class).getResultList();
    }

	public static PoliceStation findPoliceStation(Long id) {
        if (id == null) return null;
        return entityManager().find(PoliceStation.class, id);
    }

	public static List<PoliceStation> findPoliceStationEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM PoliceStation o", PoliceStation.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            PoliceStation attached = PoliceStation.findPoliceStation(this.id);
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
    public PoliceStation merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        PoliceStation merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

	public static PoliceStation fromJsonToPoliceStation(String json) {
        return new JSONDeserializer<PoliceStation>().use(null, PoliceStation.class).deserialize(json);
    }

	public static String toJsonArray(Collection<PoliceStation> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<PoliceStation> fromJsonArrayToPoliceStations(String json) {
        return new JSONDeserializer<List<PoliceStation>>().use(null, ArrayList.class).use("values", PoliceStation.class).deserialize(json);
    }

	public String getPoliceStation() {
        return this.policeStation;
    }

	public void setPoliceStation(String policeStation) {
        this.policeStation = policeStation;
    }
	public static PoliceStation find(String name){
		
		TypedQuery<PoliceStation> query =  PoliceStation.entityManager()
				.createQuery("from PoliceStation where policeStation = ?", PoliceStation.class);
		query.setParameter(1, name);
		
		
		PoliceStation policeStation = null;
		try {
			policeStation = query.getSingleResult();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			policeStation = new PoliceStation();
			policeStation.setPoliceStation(name);
			policeStation.persist();
		}
		return policeStation;
		
	}
}
