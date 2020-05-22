package cn.hstc.trishop.DAO;

import cn.hstc.trishop.pojo.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDAO extends JpaRepository<Product, Integer> {
    /*
       使用名称搜索产品
     */
        Product findByName(String account);
    /*
        使用类型搜索产品
     */
        Product findByTypeList(String typeList);

}
