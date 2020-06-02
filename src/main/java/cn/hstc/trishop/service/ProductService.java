package cn.hstc.trishop.service;

import cn.hstc.trishop.DAO.ProductDAO;
import cn.hstc.trishop.DAO.ProductDetailDAO;
import cn.hstc.trishop.pojo.ProductDetail;
import cn.hstc.trishop.pojo.Product;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductDAO productDAO;
    @Autowired
    ProductDetailDAO productDetailDAO;
    @Autowired
    UserService userService;

    public boolean isExist(int id) {
        Product product = productDAO.findById(id);
        return null != product;
    }

    public List<Product> list() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return productDAO.findAll(sort);
    }

    public List<Product> getProductListBySingleType(String typeList) {
        return productDAO.findByType(typeList);
    }

    public List<Product> getProductListByType(String typeLists) {
        // JSON转换成List进行搜索test
        List<String> list = JSONObject.parseArray(typeLists, String.class);
        if (1 == list.size()) {
            return getProductListBySingleType(list.get(0));
        }
        return productDAO.findByTypeIn(list);
    }

    public List<Product> search(String productName) {
        return productDAO.findByNameLike('%' + productName.toUpperCase() + '%');
    }

    public void add(Product product) {
        productDAO.saveAndFlush(product);
        ProductDetail productDetail = new ProductDetail();
        productDetail.setQuantity(product.getProductDetail().getQuantity());
        productDetail.setSwipeList(product.getProductDetail().getSwipeList());
        productDetailDAO.save(productDetail);
    }

    public String seeProduct(int userId, int productId) throws Exception {
        String result = "未知错误";
        if (isExist(userId)) {
            Product product = productDAO.findById(productId);
            // 把当前商品的类型加入到当前用户的喜爱类型列表
            userService.addFavorType(userId, product.getType());
            // 拼接返回结果，包括了 product和detail
            StringBuilder productDetail = new StringBuilder(product.toString());
            ProductDetail detail = productDetailDAO.findById(productId);
            if (null != detail) {
                if (product.toString().length() > 3) {
                    productDetail.insert(
                            (product.toString().length() - 1),
                            "," + detail.toString());
                } else {
                    productDetail.insert(1, detail.toString());
                }
                result = "[" + productDetail.toString() + "]";
            } else {
                result = "没找到这个商品";
            }
        }
        return result;
    }
}
