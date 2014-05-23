package org.nath.model;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.math.BigDecimal;
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
import javax.persistence.PersistenceContext;
import javax.persistence.Version;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

@Configurable
@Entity
public class Stock {

    private BigDecimal amount;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<StockTransaction> transactions = new HashSet<StockTransaction>();

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

	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

	public static Stock fromJsonToStock(String json) {
        return new JSONDeserializer<Stock>().use(null, Stock.class).deserialize(json);
    }

	public static String toJsonArray(Collection<Stock> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<Stock> fromJsonArrayToStocks(String json) {
        return new JSONDeserializer<List<Stock>>().use(null, ArrayList.class).use("values", Stock.class).deserialize(json);
    }

	public BigDecimal getAmount() {
        return this.amount;
    }

	public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

	public Set<StockTransaction> getTransactions() {
        return this.transactions;
    }

	public void setTransactions(Set<StockTransaction> transactions) {
        this.transactions = transactions;
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new Stock().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countStocks() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Stock o", Long.class).getSingleResult();
    }

	public static List<Stock> findAllStocks() {
        return entityManager().createQuery("SELECT o FROM Stock o", Stock.class).getResultList();
    }

	public static Stock findStock(Long id) {
        if (id == null) return null;
        return entityManager().find(Stock.class, id);
    }

	public static List<Stock> findStockEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Stock o", Stock.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Stock attached = Stock.findStock(this.id);
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
    public Stock merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Stock merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
}
