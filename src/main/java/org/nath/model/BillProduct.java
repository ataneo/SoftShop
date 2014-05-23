package org.nath.model;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
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
import javax.persistence.Transient;
import javax.persistence.Version;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Configurable
public class BillProduct {
	public static long countBillProducts() {
        return entityManager().createQuery("SELECT COUNT(o) FROM BillProduct o", Long.class).getSingleResult();
    }

    public static final EntityManager entityManager() {
        EntityManager em = new BillProduct().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

    public static List<BillProduct> findAllBillProducts() {
        return entityManager().createQuery("SELECT o FROM BillProduct o", BillProduct.class).getResultList();
    }
    public static BillProduct findBillProduct(Long id) {
        if (id == null) return null;
        return entityManager().find(BillProduct.class, id);
    }

	public static List<BillProduct> findBillProductEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM BillProduct o", BillProduct.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static Collection<BillProduct> fromJsonArrayToBillProducts(String json) {
        return new JSONDeserializer<List<BillProduct>>().use(null, ArrayList.class).use("values", BillProduct.class).deserialize(json);
    }

	public static BillProduct fromJsonToBillProduct(String json) {
        return new JSONDeserializer<BillProduct>().use(null, BillProduct.class).deserialize(json);
    }

	public static String toJsonArray(Collection<BillProduct> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	@ManyToOne
	private Item item;

	@ManyToOne
    private Category category;

	@ManyToOne
    private Brand brand;

	private String length;

	private String width;

	private String area;
	
	private String roll;
	
	public String getRoll() {
		return roll;
	}

	public void setRoll(String roll) {
		this.roll = roll;
	}

	public void setLength(String length) {
		try {
			this.length = new Integer(Integer.parseInt(length)).toString();
		} catch (NumberFormatException e) {
			this.length = "0";
		}		
	}

	public void setWidth(String width) {
		try {
			this.width = new Integer(Integer.parseInt(width)).toString();
		} catch (NumberFormatException e) {
			this.width = "0";
		}	
	}

	public void setArea(String area) {
		try {
			this.area = new Integer(Integer.parseInt(area)).toString();
		} catch (NumberFormatException e) {
			this.area = "0";
		}	
	}

	public void setCount(String count) {
		this.count = count;
	}

	private String count;

	private String bothSide;

	private Double amount;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

	@Version
    @Column(name = "version")
    private Integer version;
    
    @PersistenceContext
    transient EntityManager entityManager;
    public BillProduct(){
    	
    }
   
    @Transactional
    public void clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    @Transactional
    public void flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    public double getAmount() {
		return amount;
	}    

	public String getBothSide() {
		return bothSide;
	}

	public Brand getBrand() {
        return this.brand;
    }

	public Category getCategory() {
        return this.category;
    }

	public Long getId() {
        return this.id;
    }

	public Item getItem() {
		return item;
	}

	public Integer getVersion() {
        return this.version;
    }

	@Transactional
    public BillProduct merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        BillProduct merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
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
            BillProduct attached = BillProduct.findBillProduct(this.id);
            this.entityManager.remove(attached);
        }
    }

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public void setBothSide(String bothSide) {
		this.bothSide = bothSide;
	}

	public void setBrand(Brand brand) {
        this.brand = brand;
    }
	
	public void setBrand(long brand) {
        this.brand = Brand.findBrand(brand);
    }
	
	public void setBrand(String brand) {
		 this.brand = null;
    }

	public void setCategory(Category category) {
        this.category = category;
    }
	
	public void setCategory(String category) {
        this.category = null;
    }
	
	public void setCategory(long category) {
        this.category = Category.findCategory(category);
    }

	public void setId(Long id) {
        this.id = id;
    }

	public void setItem(Item item) {
		this.item = item;
	}
	
	public void setItem(long item) {
		this.item =Item.findItem(item);
	}
	
	public void setItem(String item) {
		this.item = null;
	}

	public void setVersion(Integer version) {
        this.version = version;
    }

	

	public String getLength() {
		return length;
	}

	public String getWidth() {
		return width;
	}

	public String getArea() {
		return area;
	}

	public String getCount() {
		return count;
	}

	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
