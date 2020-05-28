package cn.hstc.trishop.DAO;

import cn.hstc.trishop.pojo.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDAO extends JpaRepository<Product, Integer> {
    Product findById(int id);
    /*
       使用名称搜索产品
     */
    Product findByNameLike(String account);
    /*
        使用类型搜索产品
        select * from product where TypeList in(?)
     */
    List<Product> findByTypeListIn(List<String> typeList);

}
