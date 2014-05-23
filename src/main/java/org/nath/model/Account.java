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
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

@Configurable
@Entity
public class Account {

    @OneToMany(cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    private Set<AccountTransaction> transactions = new HashSet<AccountTransaction>();

    @NotNull
    private BigDecimal balance;

    private String description;

	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

	public static Account fromJsonToAccount(String json) {
        return new JSONDeserializer<Account>().use(null, Account.class).deserialize(json);
    }

	public static String toJsonArray(Collection<Account> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<Account> fromJsonArrayToAccounts(String json) {
        return new JSONDeserializer<List<Account>>().use(null, ArrayList.class).use("values", Account.class).deserialize(json);
    }

	public Set<AccountTransaction> getTransactions() {
        return this.transactions;
    }

	public void setTransactions(Set<AccountTransaction> transactions) {
        this.transactions = transactions;
    }

	public BigDecimal getBalance() {
        return this.balance;
    }

	public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

	public String getDescription() {
        return this.description;
    }

	public void setDescription(String description) {
        this.description = description;
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
        EntityManager em = new Account().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countAccounts() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Account o", Long.class).getSingleResult();
    }

	public static List<Account> findAllAccounts() {
        return entityManager().createQuery("SELECT o FROM Account o", Account.class).getResultList();
    }

	public static Account findAccount(Long id) {
        if (id == null) return null;
        return entityManager().find(Account.class, id);
    }

	public static List<Account> findAccountEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Account o", Account.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Account attached = Account.findAccount(this.id);
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
    public Account merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Account merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
}
