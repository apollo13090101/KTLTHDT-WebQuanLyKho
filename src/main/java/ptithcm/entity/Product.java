package ptithcm.entity;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "product")
public class Product {
	@Id
	@Column(name = "code", unique = true)
	@GeneratedValue(generator = "generator_prodcode")
	@GenericGenerator(name = "generator_prodcode", strategy = "ptithcm.util.ProdCodeGeneratorUtil")
	private String code;

	@Column(name = "name")
	private String name;

	@Column(name = "image_url")
	private String imageUrl;

	@Column(name = "unit")
	private String unit;

	@ManyToOne
	@JoinColumn(name = "category_code")
	private Category category;

	@Column(name = "active")
	private int active;

	@Column(name = "created")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date created;

	@Column(name = "updated")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updated;

	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
	private Collection<History> histories;

	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
	private Collection<Stock> stocks;

	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
	private Collection<OrderDetail> orderDetails;

	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
	private Collection<ReceiptDetail> receiptDetails;

	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
	private Collection<IssueDetail> issueDetails;

	private transient MultipartFile multipartFile;

	private transient String categoryCode;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public Collection<History> getHistories() {
		return histories;
	}

	public void setHistories(Collection<History> histories) {
		this.histories = histories;
	}

	public Collection<Stock> getStocks() {
		return stocks;
	}

	public void setStocks(Collection<Stock> stocks) {
		this.stocks = stocks;
	}

	public Collection<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(Collection<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public Collection<ReceiptDetail> getReceiptDetails() {
		return receiptDetails;
	}

	public void setReceiptDetails(Collection<ReceiptDetail> receiptDetails) {
		this.receiptDetails = receiptDetails;
	}

	public Collection<IssueDetail> getIssueDetails() {
		return issueDetails;
	}

	public void setIssueDetails(Collection<IssueDetail> issueDetails) {
		this.issueDetails = issueDetails;
	}

	public MultipartFile getMultipartFile() {
		return multipartFile;
	}

	public void setMultipartFile(MultipartFile multipartFile) {
		this.multipartFile = multipartFile;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

}
