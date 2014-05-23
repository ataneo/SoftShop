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
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;

@Configurable
@Entity
public class AccountTransaction {

    private String description;

    @NotNull
    private BigDecimal amount;
    
    @ManyToOne
    private Bill bill;
    
    public AccountTransaction(){
    	
    }
    
    public AccountTransaction(String description, BigDecimal amount, Bill bill,
			Date txDate, TranasctionType debitOrCredit) {
		super();
		this.description = description;
		this.amount = amount;
		this.bill = bill;
		this.txDate = txDate;
		this.debitOrCredit = debitOrCredit;
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

	@Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date txDate;

    public Date getTxDate() {
		return txDate;
	}

	public void setTxDate(Date txDate) {
		this.txDate = txDate;
	}

	@Enumerated
    private TranasctionType debitOrCredit;

	@Override
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

	public static AccountTransaction fromJsonToAccountTransaction(String json) {
        return new JSONDeserializer<AccountTransaction>().use(null, AccountTransaction.class).deserialize(json);
    }

	public static String toJsonArray(Collection<AccountTransaction> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<AccountTransaction> fromJsonArrayToAccountTransactions(String json) {
        return new JSONDeserializer<List<AccountTransaction>>().use(null, ArrayList.class).use("values", AccountTransaction.class).deserialize(json);
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new AccountTransaction().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countAccountTransactions() {
        return entityManager().createQuery("SELECT COUNT(o) FROM AccountTransaction o", Long.class).getSingleResult();
    }

	public static List<AccountTransaction> findAllAccountTransactions() {
        return entityManager().createQuery("SELECT o FROM AccountTransaction o", AccountTransaction.class).getResultList();
    }

	public static AccountTransaction findAccountTransaction(Long id) {
        if (id == null) return null;
        return entityManager().find(AccountTransaction.class, id);
    }

	public static List<AccountTransaction> findAccountTransactionEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM AccountTransaction o", AccountTransaction.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            AccountTransaction attached = AccountTransaction.findAccountTransaction(this.id);
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
    public AccountTransaction merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        AccountTransaction merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public String getDescription() {
        return this.description;
    }

	public void setDescription(String description) {
        this.description = description;
    }

	public BigDecimal getAmount() {
        return this.amount;
    }

	public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

	public TranasctionType getDebitOrCredit() {
        return this.debitOrCredit;
    }

	public void setDebitOrCredit(TranasctionType debitOrCredit) {
        this.debitOrCredit = debitOrCredit;
    }

	public static List<AccountTransaction> findAccountTransaction(Long accountId,Long custId) {
		return entityManager().createQuery("SELECT trans from Account as acc inner join acc.transactions as trans where acc.id="+"accountId "+" and trans.bill.customer.id="+custId, AccountTransaction.class).getResultList();
	}
}
