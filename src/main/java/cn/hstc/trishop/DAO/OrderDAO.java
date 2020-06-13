package cn.hstc.trishop.DAO;

import cn.hstc.trishop.pojo.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDAO extends JpaRepository<Order, Integer> {

}
