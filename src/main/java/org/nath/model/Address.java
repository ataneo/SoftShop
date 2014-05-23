package org.nath.model;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Configurable
public class Address {

    private String street;

    private String emailId;

    private int pin;

    private BigInteger phoneNumber;

    private BigInteger mobileNumber;

    @ManyToOne
    private PostOffice postOffice;

    @ManyToOne
    private PoliceStation policeStation;

    @ManyToOne
    private District district;

    @ManyToOne
    private CurrentState currentState;

	public String getStreet() {
        return this.street;
    }

	public void setStreet(String street) {
        this.street = street;
    }

	public String getEmailId() {
        return this.emailId;
    }

	public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

	public int getPin() {
        return this.pin;
    }

	public void setPin(int pin) {
        this.pin = pin;
    }

	public BigInteger getPhoneNumber() {
        return this.phoneNumber;
    }

	public void setPhoneNumber(BigInteger phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

	public BigInteger getMobileNumber() {
        return this.mobileNumber;
    }

	public void setMobileNumber(BigInteger mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

	public PostOffice getPostOffice() {
        return this.postOffice;
    }

	public void setPostOffice(PostOffice postOffice) {
        this.postOffice = postOffice;
    }

	public PoliceStation getPoliceStation() {
        return this.policeStation;
    }

	public void setPoliceStation(PoliceStation policeStation) {
        this.policeStation = policeStation;
    }

	public District getDistrict() {
        return this.district;
    }

	public void setDistrict(District district) {
        this.district = district;
    }

	public CurrentState getCurrentState() {
        return this.currentState;
    }

	public void setCurrentState(CurrentState currentState) {
        this.currentState = currentState;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new Address().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countAddresses() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Address o", Long.class).getSingleResult();
    }

	public static List<Address> findAllAddresses() {
        return entityManager().createQuery("SELECT o FROM Address o", Address.class).getResultList();
    }

	public static Address findAddress(Long id) {
        if (id == null) return null;
        return entityManager().find(Address.class, id);
    }

	public static List<Address> findAddressEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Address o", Address.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Address attached = Address.findAddress(this.id);
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
    public Address merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Address merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
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

	public static Address fromJsonToAddress(String json) {
        return new JSONDeserializer<Address>().use(null, Address.class).deserialize(json);
    }

	public static String toJsonArray(Collection<Address> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<Address> fromJsonArrayToAddresses(String json) {
        return new JSONDeserializer<List<Address>>().use(null, ArrayList.class).use("values", Address.class).deserialize(json);
    }
}
