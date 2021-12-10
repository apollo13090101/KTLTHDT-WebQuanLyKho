package ptithcm.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ptithcm.dao.OrderDao;
import ptithcm.entity.Order;
import ptithcm.entity.Paging;

@Service
public class OrderService {
	private final static Logger log = Logger.getLogger(OrderService.class);

	@Autowired
	private OrderDao<Order> orderDao;

	public List<Order> getAllOrders(Order order, Paging paging) {
		log.info("Get all orders");
		StringBuilder queryStr = new StringBuilder("");
		Map<String, Object> mapParams = new HashMap<>();
		if (order != null) {
			if (order.getCode() != null && !StringUtils.isEmpty(order.getCode())) {
				queryStr.append(" and entity.code = :code");
				mapParams.put("code", order.getCode());
			}
			if (order.getSupplier() != null && !StringUtils.isEmpty(order.getSupplier())) {
				queryStr.append(" and entity.supplier like :supplier");
				mapParams.put("supplier", "%" + order.getSupplier() + "%");
			}
		}
		return orderDao.getAll(queryStr.toString(), mapParams, paging);
	}

	public List<Order> getOrderByProperty(String property, Object value) {
		log.info("Get order by property");
		log.info("Property = " + property + " and value = " + value.toString());
		return orderDao.getByProperty(property, value);
	}

	public Order getOrderByCode(String code) {
		log.info("Get order by code = " + code);
		return orderDao.getByCode(Order.class, code);
	}

	public void saveOrder(Order order) throws Exception {
		log.info("Save order: " + order.toString());
		order.setActive(1);
		order.setCreated(new Date());
		order.setUpdated(new Date());
		orderDao.save(order);
	}

	public void updateOrder(Order order) throws Exception {
		log.info("Update order: " + order.toString());
		order.setUpdated(new Date());
		orderDao.update(order);
	}

	public void deleteOrder(Order order) throws Exception {
		log.info("Delete order: " + order.toString());
		order.setActive(0);
		order.setUpdated(new Date());
		orderDao.update(order);
	}
}
