package cn.hstc.trishop.service;

import cn.hstc.trishop.DAO.ProductDAO;
import cn.hstc.trishop.pojo.Product;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springfox.documentation.spring.web.json.Json;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductDAO productDAO;

    private boolean isExist(int id) {
        Product product = productDAO.findById(id);
        return null!=product;
    }

    public List<Product> getByProductTypeList(String typeList) {
        //解析类型列表，进行搜索
        return new ArrayList<>(
                productDAO.findByTypeListIn(JSONArray.parseArray(
                        typeList, String.class)
        ));
    }

    public Product get(String productName) {
        return productDAO.findByNameLike(productName);
    }
}
