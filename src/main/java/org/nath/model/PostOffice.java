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
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.Version;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

@Configurable
@Entity

public class PostOffice {

    @Column(unique = true)
    private String postOffice;

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new PostOffice().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countPostOffices() {
        return entityManager().createQuery("SELECT COUNT(o) FROM PostOffice o", Long.class).getSingleResult();
    }

	public static List<PostOffice> findAllPostOffices() {
        return entityManager().createQuery("SELECT o FROM PostOffice o", PostOffice.class).getResultList();
    }

	public static PostOffice findPostOffice(Long id) {
        if (id == null) return null;
        return entityManager().find(PostOffice.class, id);
    }

	public static List<PostOffice> findPostOfficeEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM PostOffice o", PostOffice.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            PostOffice attached = PostOffice.findPostOffice(this.id);
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
    public PostOffice merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        PostOffice merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

	public static PostOffice fromJsonToPostOffice(String json) {
        return new JSONDeserializer<PostOffice>().use(null, PostOffice.class).deserialize(json);
    }

	public static String toJsonArray(Collection<PostOffice> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<PostOffice> fromJsonArrayToPostOffices(String json) {
        return new JSONDeserializer<List<PostOffice>>().use(null, ArrayList.class).use("values", PostOffice.class).deserialize(json);
    }

	public String getPostOffice() {
        return this.postOffice;
    }

	public void setPostOffice(String postOffice) {
        this.postOffice = postOffice;
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
	
	
	public static PostOffice find(String name){
		
		TypedQuery<PostOffice> query =  PostOffice.entityManager()
				.createQuery("from PostOffice where postOffice = ?", PostOffice.class);
		query.setParameter(1, name);
		
		
		PostOffice postOffice = null;
		try {
			postOffice = query.getSingleResult();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			postOffice = new PostOffice();
			postOffice.setPostOffice(name);
			postOffice.persist();
		}
		return postOffice;
		
	}
	
}
