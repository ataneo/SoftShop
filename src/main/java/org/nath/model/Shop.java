package org.nath.model;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

@Configurable
@Entity
public class Shop {

    @NotNull
    private String name;

    @ManyToOne
    private Address address;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Employee> employees = new HashSet<Employee>();

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Customer> customers = new HashSet<Customer>();

    @ManyToOne
    private Employee owner;

    @OneToMany(cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    private Set<Bill> bills = new HashSet<Bill>();

    @OneToOne
    private Stock stock;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Item> items = new HashSet<Item>();
    

    @OneToOne(cascade=CascadeType.ALL)
    private Account account;

	public String getName() {
        return this.name;
    }

	public void setName(String name) {
        this.name = name;
    }

	public Address getAddress() {
        return this.address;
    }

	public void setAddress(Address address) {
        this.address = address;
    }

	public Set<Employee> getEmployees() {
        return this.employees;
    }

	public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

	public Set<Customer> getCustomers() {
        return this.customers;
    }

	public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }

	public Employee getOwner() {
        return this.owner;
    }

	public void setOwner(Employee owner) {
        this.owner = owner;
    }

	public Set<Bill> getBills() {
        return this.bills;
    }

	public void setBills(Set<Bill> bills) {
        this.bills = bills;
    }

	public Stock getStock() {
        return this.stock;
    }

	public void setStock(Stock stock) {
        this.stock = stock;
    }

	public Set<Item> getItems() {
        return this.items;
    }

	public void setItems(Set<Item> items) {
        this.items = items;
    }

	public Account getAccount() {
        return this.account;
    }

	public void setAccount(Account account) {
        this.account = account;
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

	public static Shop fromJsonToShop(String json) {
        return new JSONDeserializer<Shop>().use(null, Shop.class).deserialize(json);
    }

	public static String toJsonArray(Collection<Shop> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<Shop> fromJsonArrayToShops(String json) {
        return new JSONDeserializer<List<Shop>>().use(null, ArrayList.class).use("values", Shop.class).deserialize(json);
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new Shop().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countShops() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Shop o", Long.class).getSingleResult();
    }

	public static List<Shop> findAllShops() {
        return entityManager().createQuery("SELECT o FROM Shop o", Shop.class).getResultList();
    }

	public static Shop findShop(Long id) {
        if (id == null) return null;
        return entityManager().find(Shop.class, id);
    }

	public static List<Shop> findShopEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Shop o", Shop.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Shop attached = Shop.findShop(this.id);
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
    public Shop merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Shop merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
