package cn.hstc.trishop.service;

import cn.hstc.trishop.DAO.OrderDAO;
import cn.hstc.trishop.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderDAO orderDAO;

    public List<Order> list() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return orderDAO.findAll(sort);
    }

    public Order get(int id) {
        Order order = orderDAO.findById(id).orElse(null);
        return order;
    }
}
