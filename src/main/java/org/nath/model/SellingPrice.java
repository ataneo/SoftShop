package org.nath.model;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

@Configurable
@Entity
public class SellingPrice extends Price {

    private long countLimit;
    
    /*@Override
	public boolean equals(Object arg0) {
		if(!(arg0 instanceof SellingPrice))
			return false;
		
		SellingPrice newSellingPrice = (SellingPrice) arg0;
		if(newSellingPrice.getCustomerGrade().equals(this.getCustomerGrade())&&newSellingPrice.getPrice().equals(this.getPrice()))
			return true;
		else
			return false;
	}*/

    @ManyToOne
    private CustomerGrade customerGrade;

	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

	public static SellingPrice fromJsonToSellingPrice(String json) {
        return new JSONDeserializer<SellingPrice>().use(null, SellingPrice.class).deserialize(json);
    }

	public static String toJsonArray(Collection<SellingPrice> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<SellingPrice> fromJsonArrayToSellingPrices(String json) {
        return new JSONDeserializer<List<SellingPrice>>().use(null, ArrayList.class).use("values", SellingPrice.class).deserialize(json);
    }

	public static long countSellingPrices() {
        return entityManager().createQuery("SELECT COUNT(o) FROM SellingPrice o", Long.class).getSingleResult();
    }

	public static List<SellingPrice> findAllSellingPrices() {
        return entityManager().createQuery("SELECT o FROM SellingPrice o", SellingPrice.class).getResultList();
    }

	public static SellingPrice findSellingPrice(Long id) {
        if (id == null) return null;
        return entityManager().find(SellingPrice.class, id);
    }

	public static List<SellingPrice> findSellingPriceEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM SellingPrice o", SellingPrice.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	@Transactional
    public SellingPrice merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        SellingPrice merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public long getCountLimit() {
        return this.countLimit;
    }

	public void setCountLimit(long countLimit) {
        this.countLimit = countLimit;
    }

	public CustomerGrade getCustomerGrade() {
        return this.customerGrade;
    }

	public void setCustomerGrade(CustomerGrade customerGrade) {
        this.customerGrade = customerGrade;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
