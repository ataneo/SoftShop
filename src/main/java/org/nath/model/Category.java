package org.nath.model;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Configurable
public class Category {

    private String name;

	@OneToMany(cascade = CascadeType.ALL)
    private Set<Price> purchingPrice = new HashSet<Price>();

    @OneToMany(cascade = CascadeType.ALL)
    private Set<SellingPrice> SellingPrice = new HashSet<SellingPrice>();

    @OneToMany(cascade = CascadeType.ALL)
    private Set<StockCount> stockCount = new HashSet<StockCount>();
    
    @OneToMany(cascade = CascadeType.ALL)
    private Set<PriceRange> priceRange = new HashSet<PriceRange>();

    private long capacity;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Roll> rolls = new HashSet<Roll>();

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

	public String getName() {
        return this.name;
    }

	public void setName(String name) {
        this.name = name;
    }

	public Set<Price> getPurchingPrice() {
        return this.purchingPrice;
    }

	public void setPurchingPrice(Set<Price> purchingPrice) {
        this.purchingPrice = purchingPrice;
    }

	public Set<SellingPrice> getSellingPrice() {
        return this.SellingPrice;
    }

	public void setSellingPrice(Set<SellingPrice> SellingPrice) {
        this.SellingPrice = SellingPrice;
    }

	public Set<StockCount> getStockCount() {
        return this.stockCount;
    }

	public void setStockCount(Set<StockCount> stockCount) {
        this.stockCount = stockCount;
    }

	public long getCapacity() {
        return this.capacity;
    }

	public void setCapacity(long capacity) {
        this.capacity = capacity;
    }

	public Set<Roll> getRolls() {
        return this.rolls;
    }

	public void setRolls(Set<Roll> rolls) {
        this.rolls = rolls;
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new Category().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countCategorys() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Category o", Long.class).getSingleResult();
    }

	public static List<Category> findAllCategorys() {
        return entityManager().createQuery("SELECT o FROM Category o", Category.class).getResultList();
    }

	public static Category findCategory(Long id) {
        if (id == null) return null;
        return entityManager().find(Category.class, id);
    }

	public static List<Category> findCategoryEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Category o", Category.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	@Transactional
    public void persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        System.out.println(this.toJson());
        this.entityManager.persist(this);
    }

	@Transactional
    public void remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Category attached = Category.findCategory(this.id);
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
    public Category merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Category merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

	public static Category fromJsonToCategory(String json) {
        return new JSONDeserializer<Category>().use(null, Category.class).deserialize(json);
    }

	public static String toJsonArray(Collection<Category> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<Category> fromJsonArrayToCategorys(String json) {
        return new JSONDeserializer<List<Category>>().use(null, ArrayList.class).use("values", Category.class).deserialize(json);
    }

	public Set<PriceRange> getPriceRange() {
		return priceRange;
	}

	public void setPriceRange(Set<PriceRange> priceRange) {
		this.priceRange = priceRange;
	}
}
