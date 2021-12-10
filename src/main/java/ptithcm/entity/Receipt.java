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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "receipt")
public class Receipt {
	@Id
	@Column(name = "code", unique = true)
	@GeneratedValue(generator = "generator_receiptcode")
	@GenericGenerator(name = "generator_receiptcode", strategy = "ptithcm.util.ReceiptCodeGeneratorUtil")
	private String code;

	@OneToOne
	@JoinColumn(name = "order_code")
	private Order order;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

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

	@OneToMany(mappedBy = "receipt", fetch = FetchType.LAZY)
	private Collection<ReceiptDetail> receiptDetails;

	private transient String orderCode;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public Collection<ReceiptDetail> getReceiptDetails() {
		return receiptDetails;
	}

	public void setReceiptDetails(Collection<ReceiptDetail> receiptDetails) {
		this.receiptDetails = receiptDetails;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

}
