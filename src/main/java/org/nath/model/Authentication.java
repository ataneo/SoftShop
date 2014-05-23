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
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

@Configurable
@Entity
public class Authentication {

    @NotNull
    private String userName;

    @Override
	public boolean equals(Object obj) {
    	
    	if(obj instanceof Authentication && ((Authentication) obj).userName.equals(this.getUserName()) && ((Authentication) obj).password.equals(this.getPassword())){
    		return true;
    	}
    	
		return false;
	}

	@NotNull
    private String password;

	public String getUserName() {
        return this.userName;
    }

	public void setUserName(String userName) {
        this.userName = userName;
    }

	public String getPassword() {
        return this.password;
    }

	public void setPassword(String password) {
        this.password = password;
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new Authentication().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countAuthentications() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Authentication o", Long.class).getSingleResult();
    }

	public static List<Authentication> findAllAuthentications() {
        return entityManager().createQuery("SELECT o FROM Authentication o", Authentication.class).getResultList();
    }

	public static Authentication findAuthentication(Long id) {
        if (id == null) return null;
        return entityManager().find(Authentication.class, id);
    }

	public static List<Authentication> findAuthenticationEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Authentication o", Authentication.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Authentication attached = Authentication.findAuthentication(this.id);
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
    public Authentication merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Authentication merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
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

	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

	public static Authentication fromJsonToAuthentication(String json) {
        return new JSONDeserializer<Authentication>().use(null, Authentication.class).deserialize(json);
    }

	public static String toJsonArray(Collection<Authentication> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<Authentication> fromJsonArrayToAuthentications(String json) {
        return new JSONDeserializer<List<Authentication>>().use(null, ArrayList.class).use("values", Authentication.class).deserialize(json);
    }
}
