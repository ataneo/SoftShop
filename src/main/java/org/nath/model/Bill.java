package org.nath.model;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.OrderBy;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Configurable
public class Bill {

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date billDate;

    private Double amount;
    
  

	private String jobDescription;

    public String getJobDescription() {
		return jobDescription;
	}

	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}

	private Double paidAmount;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date delivaryDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date duePromiseDate;

    @ManyToOne
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL)
    @OrderBy("id")
    @Fetch(FetchMode.JOIN)
    private List<BillProduct> billProduts = new ArrayList<BillProduct>();

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
        EntityManager em = new Bill().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countBills() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Bill o", Long.class).getSingleResult();
    }

	public static List<Bill> findAllBills() {
        return entityManager().createQuery("SELECT o FROM Bill o", Bill.class).getResultList();
    }
	
	public static List<Bill> findAllBillsByCustomer(Long id) {
        return entityManager().createQuery("FROM Bill where CUSTOMER = "+id, Bill.class).getResultList();
    }
	
	

	public static Bill findBill(Long id) {
        if (id == null) return null;
        return entityManager().find(Bill.class, id);
    }

	public static List<Bill> findBillEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Bill o", Bill.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Bill attached = Bill.findBill(this.id);
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
    public Bill merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Bill merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
	
	public String toJsonAll() {
        return new JSONSerializer().include("billProduts").exclude("*.class").serialize(this);
    }

	public static Bill fromJsonToBill(String json) {
        return new JSONDeserializer<Bill>().use(null, Bill.class).deserialize(json);
    }

	public static String toJsonArray(Collection<Bill> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static String toJsonArrayAll(Collection<Bill> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
	
	

	public static Collection<Bill> fromJsonArrayToBills(String json) {
        return new JSONDeserializer<List<Bill>>().use(null, ArrayList.class).use("values", Bill.class).deserialize(json);
    }

	public Date getBillDate() {
        return this.billDate;
    }

	public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

	public Double getAmount() {
        return this.amount;
    }

	public void setAmount(Double amount) {
        this.amount = amount;
    }

	public Double getPaidAmount() {
        return this.paidAmount;
    }

	public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
    }

	public Date getDelivaryDate() {
        return this.delivaryDate;
    }

	public void setDelivaryDate(Date delivaryDate) {
        this.delivaryDate = delivaryDate;
    }

	public Date getDuePromiseDate() {
        return this.duePromiseDate;
    }

	public void setDuePromiseDate(Date duePromiseDate) {
        this.duePromiseDate = duePromiseDate;
    }

	public Customer getCustomer() {
        return this.customer;
    }

	public void setCustomer(Customer customer) {
        this.customer = customer;
    }
	
	public void setCustomer(long customerId) {
        this.customer = Customer.findCustomer(customerId);
    }

	public List<BillProduct> getBillProduts() {
        return this.billProduts;
    }

	public void setBillProduts(List<BillProduct> billProduts) {
        this.billProduts = billProduts;
    }
}
