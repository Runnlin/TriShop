package cn.hstc.trishop.DAO;

import cn.hstc.trishop.pojo.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDAO extends JpaRepository<Order, Integer> {
    List<Order> findByUidLike(int userId);
}
