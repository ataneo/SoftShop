package org.nath.web;

import org.nath.model.Account;
import org.nath.model.AccountTransaction;
import org.nath.model.Address;
import org.nath.model.Authentication;
import org.nath.model.Bill;
import org.nath.model.BillProduct;
import org.nath.model.Brand;
import org.nath.model.Category;
import org.nath.model.Customer;
import org.nath.model.CustomerGrade;
import org.nath.model.Employee;
import org.nath.model.Item;
import org.nath.model.Person;
import org.nath.model.Price;
import org.nath.model.Roll;
import org.nath.model.SellingPrice;
import org.nath.model.Shop;
import org.nath.model.Stock;
import org.nath.model.StockCount;
import org.nath.model.StockTransaction;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;

@Configurable
/**
 * A central place to register application converters and formatters. 
 */
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {

	@Override
	protected void installFormatters(FormatterRegistry registry) {
		super.installFormatters(registry);
		// Register application converters and formatters
	}

	public Converter<Account, String> getAccountToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<org.nath.model.Account, java.lang.String>() {
            public String convert(Account account) {
                return new StringBuilder().append(account.getBalance()).append(' ').append(account.getDescription()).toString();
            }
        };
    }

	public Converter<Long, Account> getIdToAccountConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, org.nath.model.Account>() {
            public org.nath.model.Account convert(java.lang.Long id) {
                return Account.findAccount(id);
            }
        };
    }

	public Converter<String, Account> getStringToAccountConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, org.nath.model.Account>() {
            public org.nath.model.Account convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Account.class);
            }
        };
    }

	public Converter<AccountTransaction, String> getAccountTransactionToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<org.nath.model.AccountTransaction, java.lang.String>() {
            public String convert(AccountTransaction accountTransaction) {
                return new StringBuilder().append(accountTransaction.getDescription()).append(' ').append(accountTransaction.getAmount()).toString();
            }
        };
    }

	public Converter<Long, AccountTransaction> getIdToAccountTransactionConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, org.nath.model.AccountTransaction>() {
            public org.nath.model.AccountTransaction convert(java.lang.Long id) {
                return AccountTransaction.findAccountTransaction(id);
            }
        };
    }

	public Converter<String, AccountTransaction> getStringToAccountTransactionConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, org.nath.model.AccountTransaction>() {
            public org.nath.model.AccountTransaction convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), AccountTransaction.class);
            }
        };
    }

	public Converter<Address, String> getAddressToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<org.nath.model.Address, java.lang.String>() {
            public String convert(Address address) {
                return new StringBuilder().append(address.getStreet()).append(' ').append(address.getPostOffice()).append(' ').append(address.getPoliceStation()).append(' ').append(address.getDistrict()).toString();
            }
        };
    }

	public Converter<Long, Address> getIdToAddressConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, org.nath.model.Address>() {
            public org.nath.model.Address convert(java.lang.Long id) {
                return Address.findAddress(id);
            }
        };
    }

	public Converter<String, Address> getStringToAddressConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, org.nath.model.Address>() {
            public org.nath.model.Address convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Address.class);
            }
        };
    }

	public Converter<Authentication, String> getAuthenticationToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<org.nath.model.Authentication, java.lang.String>() {
            public String convert(Authentication authentication) {
                return new StringBuilder().append(authentication.getUserName()).append(' ').append(authentication.getPassword()).toString();
            }
        };
    }

	public Converter<Long, Authentication> getIdToAuthenticationConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, org.nath.model.Authentication>() {
            public org.nath.model.Authentication convert(java.lang.Long id) {
                return Authentication.findAuthentication(id);
            }
        };
    }

	public Converter<String, Authentication> getStringToAuthenticationConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, org.nath.model.Authentication>() {
            public org.nath.model.Authentication convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Authentication.class);
            }
        };
    }

	public Converter<Bill, String> getBillToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<org.nath.model.Bill, java.lang.String>() {
            public String convert(Bill bill) {
                return new StringBuilder().append(bill.getBillDate()).append(' ').append(bill.getAmount()).append(' ').append(bill.getPaidAmount()).append(' ').append(bill.getDelivaryDate()).toString();
            }
        };
    }

	public Converter<Long, Bill> getIdToBillConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, org.nath.model.Bill>() {
            public org.nath.model.Bill convert(java.lang.Long id) {
                return Bill.findBill(id);
            }
        };
    }

	public Converter<String, Bill> getStringToBillConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, org.nath.model.Bill>() {
            public org.nath.model.Bill convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Bill.class);
            }
        };
    }

	public Converter<BillProduct, String> getBillProductToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<org.nath.model.BillProduct, java.lang.String>() {
            public String convert(BillProduct billProduct) {
                return new StringBuilder().append(billProduct.getCount()).toString();
            }
        };
    }

	public Converter<Long, BillProduct> getIdToBillProductConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, org.nath.model.BillProduct>() {
            public org.nath.model.BillProduct convert(java.lang.Long id) {
                return BillProduct.findBillProduct(id);
            }
        };
    }

	public Converter<String, BillProduct> getStringToBillProductConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, org.nath.model.BillProduct>() {
            public org.nath.model.BillProduct convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), BillProduct.class);
            }
        };
    }

	public Converter<Brand, String> getBrandToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<org.nath.model.Brand, java.lang.String>() {
            public String convert(Brand brand) {
                return new StringBuilder().append(brand.getBrandName()).toString();
            }
        };
    }

	public Converter<Long, Brand> getIdToBrandConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, org.nath.model.Brand>() {
            public org.nath.model.Brand convert(java.lang.Long id) {
                return Brand.findBrand(id);
            }
        };
    }

	public Converter<String, Brand> getStringToBrandConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, org.nath.model.Brand>() {
            public org.nath.model.Brand convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Brand.class);
            }
        };
    }

	public Converter<Category, String> getCategoryToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<org.nath.model.Category, java.lang.String>() {
            public String convert(Category category) {
                return new StringBuilder().append(category.getName()).append(' ').append(category.getCapacity()).toString();
            }
        };
    }

	public Converter<Long, Category> getIdToCategoryConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, org.nath.model.Category>() {
            public org.nath.model.Category convert(java.lang.Long id) {
                return Category.findCategory(id);
            }
        };
    }

	public Converter<String, Category> getStringToCategoryConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, org.nath.model.Category>() {
            public org.nath.model.Category convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Category.class);
            }
        };
    }

	public Converter<Customer, String> getCustomerToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<org.nath.model.Customer, java.lang.String>() {
            public String convert(Customer customer) {
                return new StringBuilder().append(customer.getName()).toString();
            }
        };
    }

	public Converter<Long, Customer> getIdToCustomerConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, org.nath.model.Customer>() {
            public org.nath.model.Customer convert(java.lang.Long id) {
                return Customer.findCustomer(id);
            }
        };
    }

	public Converter<String, Customer> getStringToCustomerConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, org.nath.model.Customer>() {
            public org.nath.model.Customer convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Customer.class);
            }
        };
    }

	public Converter<CustomerGrade, String> getCustomerGradeToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<org.nath.model.CustomerGrade, java.lang.String>() {
            public String convert(CustomerGrade customerGrade) {
                return new StringBuilder().append(customerGrade.getGrade()).append(' ').append(customerGrade.getDescription()).toString();
            }
        };
    }

	public Converter<Long, CustomerGrade> getIdToCustomerGradeConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, org.nath.model.CustomerGrade>() {
            public org.nath.model.CustomerGrade convert(java.lang.Long id) {
                return CustomerGrade.findCustomerGrade(id);
            }
        };
    }

	public Converter<String, CustomerGrade> getStringToCustomerGradeConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, org.nath.model.CustomerGrade>() {
            public org.nath.model.CustomerGrade convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), CustomerGrade.class);
            }
        };
    }

	public Converter<Employee, String> getEmployeeToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<org.nath.model.Employee, java.lang.String>() {
            public String convert(Employee employee) {
                return new StringBuilder().append(employee.getName()).toString();
            }
        };
    }

	public Converter<Long, Employee> getIdToEmployeeConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, org.nath.model.Employee>() {
            public org.nath.model.Employee convert(java.lang.Long id) {
                return Employee.findEmployee(id);
            }
        };
    }

	public Converter<String, Employee> getStringToEmployeeConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, org.nath.model.Employee>() {
            public org.nath.model.Employee convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Employee.class);
            }
        };
    }

	public Converter<Item, String> getItemToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<org.nath.model.Item, java.lang.String>() {
            public String convert(Item item) {
                return new StringBuilder().append(item.getName()).append(' ').append(item.getUnit()).toString();
            }
        };
    }

	public Converter<Long, Item> getIdToItemConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, org.nath.model.Item>() {
            public org.nath.model.Item convert(java.lang.Long id) {
                return Item.findItem(id);
            }
        };
    }

	public Converter<String, Item> getStringToItemConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, org.nath.model.Item>() {
            public org.nath.model.Item convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Item.class);
            }
        };
    }

	public Converter<Person, String> getPersonToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<org.nath.model.Person, java.lang.String>() {
            public String convert(Person person) {
                return new StringBuilder().append(person.getName()).toString();
            }
        };
    }

	public Converter<Long, Person> getIdToPersonConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, org.nath.model.Person>() {
            public org.nath.model.Person convert(java.lang.Long id) {
                return Person.findPerson(id);
            }
        };
    }

	public Converter<String, Person> getStringToPersonConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, org.nath.model.Person>() {
            public org.nath.model.Person convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Person.class);
            }
        };
    }

	public Converter<Price, String> getPriceToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<org.nath.model.Price, java.lang.String>() {
            public String convert(Price price) {
                return new StringBuilder().append(price.getPrice()).append(' ').append(price.getEntryDate()).toString();
            }
        };
    }

	public Converter<Long, Price> getIdToPriceConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, org.nath.model.Price>() {
            public org.nath.model.Price convert(java.lang.Long id) {
                return Price.findPrice(id);
            }
        };
    }

	public Converter<String, Price> getStringToPriceConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, org.nath.model.Price>() {
            public org.nath.model.Price convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Price.class);
            }
        };
    }

	public Converter<Roll, String> getRollToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<org.nath.model.Roll, java.lang.String>() {
            public String convert(Roll roll) {
                return new StringBuilder().append(roll.getName()).append(' ').append(roll.getFirstDimention()).append(' ').append(roll.getSecondDimention()).append(' ').append(roll.getStock()).toString();
            }
        };
    }

	public Converter<Long, Roll> getIdToRollConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, org.nath.model.Roll>() {
            public org.nath.model.Roll convert(java.lang.Long id) {
                return Roll.findRoll(id);
            }
        };
    }

	public Converter<String, Roll> getStringToRollConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, org.nath.model.Roll>() {
            public org.nath.model.Roll convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Roll.class);
            }
        };
    }

	public Converter<SellingPrice, String> getSellingPriceToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<org.nath.model.SellingPrice, java.lang.String>() {
            public String convert(SellingPrice sellingPrice) {
                return new StringBuilder().append(sellingPrice.getPrice()).append(' ').append(sellingPrice.getEntryDate()).append(' ').append(sellingPrice.getCountLimit()).toString();
            }
        };
    }

	public Converter<Long, SellingPrice> getIdToSellingPriceConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, org.nath.model.SellingPrice>() {
            public org.nath.model.SellingPrice convert(java.lang.Long id) {
                return SellingPrice.findSellingPrice(id);
            }
        };
    }

	public Converter<String, SellingPrice> getStringToSellingPriceConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, org.nath.model.SellingPrice>() {
            public org.nath.model.SellingPrice convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), SellingPrice.class);
            }
        };
    }

	public Converter<Shop, String> getShopToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<org.nath.model.Shop, java.lang.String>() {
            public String convert(Shop shop) {
                return new StringBuilder().append(shop.getName()).toString();
            }
        };
    }

	public Converter<Long, Shop> getIdToShopConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, org.nath.model.Shop>() {
            public org.nath.model.Shop convert(java.lang.Long id) {
                return Shop.findShop(id);
            }
        };
    }

	public Converter<String, Shop> getStringToShopConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, org.nath.model.Shop>() {
            public org.nath.model.Shop convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Shop.class);
            }
        };
    }

	public Converter<Stock, String> getStockToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<org.nath.model.Stock, java.lang.String>() {
            public String convert(Stock stock) {
                return new StringBuilder().append(stock.getAmount()).toString();
            }
        };
    }

	public Converter<Long, Stock> getIdToStockConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, org.nath.model.Stock>() {
            public org.nath.model.Stock convert(java.lang.Long id) {
                return Stock.findStock(id);
            }
        };
    }

	public Converter<String, Stock> getStringToStockConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, org.nath.model.Stock>() {
            public org.nath.model.Stock convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Stock.class);
            }
        };
    }

	public Converter<StockCount, String> getStockCountToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<org.nath.model.StockCount, java.lang.String>() {
            public String convert(StockCount stockCount) {
                return new StringBuilder().append(stockCount.getStockCount()).toString();
            }
        };
    }

	public Converter<Long, StockCount> getIdToStockCountConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, org.nath.model.StockCount>() {
            public org.nath.model.StockCount convert(java.lang.Long id) {
                return StockCount.findStockCount(id);
            }
        };
    }

	public Converter<String, StockCount> getStringToStockCountConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, org.nath.model.StockCount>() {
            public org.nath.model.StockCount convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), StockCount.class);
            }
        };
    }

	public Converter<StockTransaction, String> getStockTransactionToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<org.nath.model.StockTransaction, java.lang.String>() {
            public String convert(StockTransaction stockTransaction) {
                return new StringBuilder().append(stockTransaction.getTransDate()).append(' ').append(stockTransaction.getInOutCount()).append(' ').append(stockTransaction.getAmount()).toString();
            }
        };
    }

	public Converter<Long, StockTransaction> getIdToStockTransactionConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, org.nath.model.StockTransaction>() {
            public org.nath.model.StockTransaction convert(java.lang.Long id) {
                return StockTransaction.findStockTransaction(id);
            }
        };
    }

	public Converter<String, StockTransaction> getStringToStockTransactionConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, org.nath.model.StockTransaction>() {
            public org.nath.model.StockTransaction convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), StockTransaction.class);
            }
        };
    }

	public void installLabelConverters(FormatterRegistry registry) {
        registry.addConverter(getAccountToStringConverter());
        registry.addConverter(getIdToAccountConverter());
        registry.addConverter(getStringToAccountConverter());
        registry.addConverter(getAccountTransactionToStringConverter());
        registry.addConverter(getIdToAccountTransactionConverter());
        registry.addConverter(getStringToAccountTransactionConverter());
        registry.addConverter(getAddressToStringConverter());
        registry.addConverter(getIdToAddressConverter());
        registry.addConverter(getStringToAddressConverter());
        registry.addConverter(getAuthenticationToStringConverter());
        registry.addConverter(getIdToAuthenticationConverter());
        registry.addConverter(getStringToAuthenticationConverter());
        registry.addConverter(getBillToStringConverter());
        registry.addConverter(getIdToBillConverter());
        registry.addConverter(getStringToBillConverter());
        registry.addConverter(getBillProductToStringConverter());
        registry.addConverter(getIdToBillProductConverter());
        registry.addConverter(getStringToBillProductConverter());
        registry.addConverter(getBrandToStringConverter());
        registry.addConverter(getIdToBrandConverter());
        registry.addConverter(getStringToBrandConverter());
        registry.addConverter(getCategoryToStringConverter());
        registry.addConverter(getIdToCategoryConverter());
        registry.addConverter(getStringToCategoryConverter());
        registry.addConverter(getCustomerToStringConverter());
        registry.addConverter(getIdToCustomerConverter());
        registry.addConverter(getStringToCustomerConverter());
        registry.addConverter(getCustomerGradeToStringConverter());
        registry.addConverter(getIdToCustomerGradeConverter());
        registry.addConverter(getStringToCustomerGradeConverter());
        registry.addConverter(getEmployeeToStringConverter());
        registry.addConverter(getIdToEmployeeConverter());
        registry.addConverter(getStringToEmployeeConverter());
        registry.addConverter(getItemToStringConverter());
        registry.addConverter(getIdToItemConverter());
        registry.addConverter(getStringToItemConverter());
        registry.addConverter(getPersonToStringConverter());
        registry.addConverter(getIdToPersonConverter());
        registry.addConverter(getStringToPersonConverter());
        registry.addConverter(getPriceToStringConverter());
        registry.addConverter(getIdToPriceConverter());
        registry.addConverter(getStringToPriceConverter());
        registry.addConverter(getRollToStringConverter());
        registry.addConverter(getIdToRollConverter());
        registry.addConverter(getStringToRollConverter());
        registry.addConverter(getSellingPriceToStringConverter());
        registry.addConverter(getIdToSellingPriceConverter());
        registry.addConverter(getStringToSellingPriceConverter());
        registry.addConverter(getShopToStringConverter());
        registry.addConverter(getIdToShopConverter());
        registry.addConverter(getStringToShopConverter());
        registry.addConverter(getStockToStringConverter());
        registry.addConverter(getIdToStockConverter());
        registry.addConverter(getStringToStockConverter());
        registry.addConverter(getStockCountToStringConverter());
        registry.addConverter(getIdToStockCountConverter());
        registry.addConverter(getStringToStockCountConverter());
        registry.addConverter(getStockTransactionToStringConverter());
        registry.addConverter(getIdToStockTransactionConverter());
        registry.addConverter(getStringToStockTransactionConverter());
    }

	public void afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }
}
