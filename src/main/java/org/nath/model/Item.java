package org.nath.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Entity
@Configurable
public class Item {
	
	/*public static class ItemComparator implements Comparator<Item>{
		@Override
		public int compare(Item o1, Item o2) {
			try {
				return (int) (o1.getId() - o2.getId());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return -1;
			}
		}		
	}
	
	

    @Override
	public boolean equals(Object arg0) {
		if(!(arg0 instanceof Item))
			return false;
		Item item1 = (Item)arg0;
		if(this.getName().equals(item1.getName()))
			return true;
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		int hashCode = 19*this.getName().hashCode();
		return hashCode;
	}*/

	private String name;

    private String unit;

    @OneToMany(cascade = CascadeType.ALL)
    //@Sort(type = SortType.COMPARATOR, comparator = Category.CategoryComparator.class)
    private Set<Category> categories = new HashSet<Category>();

    @OneToMany(cascade = CascadeType.ALL)
    //@Sort(type = SortType.COMPARATOR, comparator = Brand.BrandComparator.class)
    private Set<Brand> brands = new HashSet<Brand>();

    @Enumerated
    private HasRoll hasRoll;

	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

	public static Item fromJsonToItem(String json) {
        return new JSONDeserializer<Item>().use(null, Item.class).deserialize(json);
    }

	public static String toJsonArray(Collection<Item> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<Item> fromJsonArrayToItems(String json) {
        return new JSONDeserializer<List<Item>>().use(null, ArrayList.class).use("values", Item.class).deserialize(json);
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
        EntityManager em = new Item().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countItems() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Item o", Long.class).getSingleResult();
    }

	public static List<Item> findAllItems() {
        return entityManager().createQuery("SELECT o FROM Item o", Item.class).getResultList();
    }

	public static Item findItem(Long id) {
        if (id == null) return null;
        return entityManager().find(Item.class, id);
    }

	public static List<Item> findItemEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Item o", Item.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	@Transactional
    public void persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        System.out.println("persisting "+this.toJson());
        this.entityManager.persist(this);
    }

	@Transactional
    public void remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Item attached = Item.findItem(this.id);
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
    public Item merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Item merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public String getName() {
        return this.name;
    }

	public void setName(String name) {
        this.name = name;
    }

	public String getUnit() {
        return this.unit;
    }

	public void setUnit(String unit) {
        this.unit = unit;
    }

	public Set<Category> getCategories() {
        return this.categories;
    }

	public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

	public Set<Brand> getBrands() {
        return this.brands;
    }

	public void setBrands(Set<Brand> brands) {
        this.brands = brands;
    }

	public HasRoll getHasRoll() {
        return this.hasRoll;
    }

	public void setHasRoll(HasRoll hasRoll) {
        this.hasRoll = hasRoll;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
