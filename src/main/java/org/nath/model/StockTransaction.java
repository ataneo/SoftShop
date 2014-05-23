package org.nath.model;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;

@Configurable
@Entity
public class StockTransaction {

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date transDate;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Brand brand;

    @Enumerated
    private StockInOut stockInOut;

    private long inOutCount;

    private BigDecimal amount;

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public Date getTransDate() {
        return this.transDate;
    }

	public void setTransDate(Date transDate) {
        this.transDate = transDate;
    }

	public Category getCategory() {
        return this.category;
    }

	public void setCategory(Category category) {
        this.category = category;
    }

	public Brand getBrand() {
        return this.brand;
    }

	public void setBrand(Brand brand) {
        this.brand = brand;
    }

	public StockInOut getStockInOut() {
        return this.stockInOut;
    }

	public void setStockInOut(StockInOut stockInOut) {
        this.stockInOut = stockInOut;
    }

	public long getInOutCount() {
        return this.inOutCount;
    }

	public void setInOutCount(long inOutCount) {
        this.inOutCount = inOutCount;
    }

	public BigDecimal getAmount() {
        return this.amount;
    }

	public void setAmount(BigDecimal amount) {
        this.amount = amount;
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
        EntityManager em = new StockTransaction().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countStockTransactions() {
        return entityManager().createQuery("SELECT COUNT(o) FROM StockTransaction o", Long.class).getSingleResult();
    }

	public static List<StockTransaction> findAllStockTransactions() {
        return entityManager().createQuery("SELECT o FROM StockTransaction o", StockTransaction.class).getResultList();
    }

	public static StockTransaction findStockTransaction(Long id) {
        if (id == null) return null;
        return entityManager().find(StockTransaction.class, id);
    }

	public static List<StockTransaction> findStockTransactionEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM StockTransaction o", StockTransaction.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            StockTransaction attached = StockTransaction.findStockTransaction(this.id);
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
    public StockTransaction merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        StockTransaction merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

	public static StockTransaction fromJsonToStockTransaction(String json) {
        return new JSONDeserializer<StockTransaction>().use(null, StockTransaction.class).deserialize(json);
    }

	public static String toJsonArray(Collection<StockTransaction> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<StockTransaction> fromJsonArrayToStockTransactions(String json) {
        return new JSONDeserializer<List<StockTransaction>>().use(null, ArrayList.class).use("values", StockTransaction.class).deserialize(json);
    }
}
