package ptithcm.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ptithcm.dao.OrderDetailDao;
import ptithcm.entity.OrderDetail;
import ptithcm.entity.Paging;

@Service
public class OrderDetailService {
	private final static Logger log = Logger.getLogger(OrderDetailService.class);

	@Autowired
	private OrderDetailDao<OrderDetail> orderDetailDao;

	public List<OrderDetail> getAllOrderDetails(OrderDetail orderDetail, Paging paging) {
		log.info("Get all order details");
		StringBuilder queryStr = new StringBuilder("");
		Map<String, Object> mapParams = new HashMap<>();
		if (orderDetail != null) {
			if (orderDetail.getId() != null && orderDetail.getId() != 0) {
				queryStr.append(" and entity.id = :id");
				mapParams.put("id", orderDetail.getId());
			}

			if (orderDetail.getOrder() != null && !StringUtils.isEmpty(orderDetail.getOrder().getCode())) {
				queryStr.append(" and entity.order.code = :orderCode");
				mapParams.put("orderCode", orderDetail.getOrder().getCode());
			}

			if (orderDetail.getProduct() != null && !StringUtils.isEmpty(orderDetail.getProduct().getCode())) {
				queryStr.append(" and entity.product.code = :productCode");
				mapParams.put("productCode", orderDetail.getProduct().getCode());
			}
		}
		return orderDetailDao.getAll(queryStr.toString(), mapParams, paging);
	}

	public int getOrderDetailQuantity(String prod_code) {
		return orderDetailDao.getOrderDetailQuantity(prod_code);
	}

	public BigDecimal getOrderDetailPrice(String prod_code) {
		return orderDetailDao.getOrderDetailPrice(prod_code);
	}

	public List<OrderDetail> getOrderDetailByProperty(String property, Object value) {
		log.info("Get orderDetail by property");
		log.info("Property = " + property + " and value = " + value.toString());
		return orderDetailDao.getByProperty(property, value);
	}

	public OrderDetail getOrderDetailById(int id) {
		log.info("Get order detail by id = " + id);
		return orderDetailDao.getById(OrderDetail.class, id);
	}

	public void saveOrderDetail(OrderDetail orderDetail) throws Exception {
		log.info("Save orderDetail: " + orderDetail.toString());
		boolean checkOrderDetail = orderDetailDao.checkOrderDetail(orderDetail.getOrderCode(),
				orderDetail.getProductCode());
		if (!checkOrderDetail) {
			orderDetail.setActive(1);
			orderDetail.setCreated(new Date());
			orderDetail.setUpdated(new Date());
			orderDetailDao.save(orderDetail);
		}
	}

	public void updateOrderDetail(OrderDetail orderDetail) throws Exception {
		log.info("Update orderDetail: " + orderDetail.toString());
		boolean checkOrderDetail = orderDetailDao.checkOrderDetail(orderDetail.getOrderCode(),
				orderDetail.getProductCode());
		if (!checkOrderDetail) {
			orderDetail.setUpdated(new Date());
			orderDetailDao.update(orderDetail);
		}
	}

	public void deleteOrderDetail(OrderDetail orderDetail) throws Exception {
		log.info("Delete orderDetail: " + orderDetail.toString());
		orderDetail.setActive(0);
		orderDetail.setUpdated(new Date());
		orderDetailDao.update(orderDetail);
	}
}
