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

@Entity
@Configurable
public class CurrentState {

    @Column(unique = true)
    private String currentState;

	public String getCurrentState() {
        return this.currentState;
    }

	public void setCurrentState(String currentState) {
        this.currentState = currentState;
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

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new CurrentState().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countCurrentStates() {
        return entityManager().createQuery("SELECT COUNT(o) FROM CurrentState o", Long.class).getSingleResult();
    }

	public static List<CurrentState> findAllCurrentStates() {
        return entityManager().createQuery("SELECT o FROM CurrentState o", CurrentState.class).getResultList();
    }

	public static CurrentState findCurrentState(Long id) {
        if (id == null) return null;
        return entityManager().find(CurrentState.class, id);
    }

	public static List<CurrentState> findCurrentStateEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM CurrentState o", CurrentState.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            CurrentState attached = CurrentState.findCurrentState(this.id);
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
    public CurrentState merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        CurrentState merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

	public static CurrentState fromJsonToCurrentState(String json) {
        return new JSONDeserializer<CurrentState>().use(null, CurrentState.class).deserialize(json);
    }

	public static String toJsonArray(Collection<CurrentState> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<CurrentState> fromJsonArrayToCurrentStates(String json) {
        return new JSONDeserializer<List<CurrentState>>().use(null, ArrayList.class).use("values", CurrentState.class).deserialize(json);
    }
	
	public static CurrentState find(String name){
		
		TypedQuery<CurrentState> query =  CurrentState.entityManager()
				.createQuery("from CurrentState where currentState = ?", CurrentState.class);
		query.setParameter(1, name);
		
		
		CurrentState currentState = null;
		try {
			currentState = query.getSingleResult();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			currentState = new CurrentState();
			currentState.setCurrentState(name);
			currentState.persist();
		}
		return currentState;
		
	}
}
