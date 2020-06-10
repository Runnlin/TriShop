package cn.hstc.trishop.service;

import cn.hstc.trishop.DAO.ProductDAO;
//import cn.hstc.trishop.DAO.ProductDetailDAO;
//import cn.hstc.trishop.pojo.ProductDetail;
import cn.hstc.trishop.pojo.Product;
import cn.hstc.trishop.result.Result;
import cn.hstc.trishop.result.UploadFileResponse;
import cn.hstc.trishop.utils.Constants;
import cn.hstc.trishop.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
public class ProductService {
    @Autowired
    ProductDAO productDAO;
//    @Autowired
//    ProductDetailDAO productDetailDAO;
//    ProductDetail productDetail;
    @Autowired
    UserService userService;
    @Autowired
    FileService fileService;

    public boolean isExist(int id) {
        Product product = productDAO.findById(id);
        return null != product;
    }

    public Product getById(int id) {
        return productDAO.findById(id);
    }

    public List<Product> list() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return productDAO.findAll(sort);
    }

    public int getSize() {
        return (int)productDAO.count();
    }

    public List<Product> getProductListBySingleType(String typeList) {
        return productDAO.findByType(typeList);
    }

    public List<Product> getProductListByType(String typeLists) {
        // JSON转换成List进行搜索test
        List<String> lists = JSONObject.parseArray(typeLists, String.class);
        if (1 == lists.size()) {
            return getProductListBySingleType(lists.get(0));
        }
        return productDAO.findByTypeIn(lists);
    }

    public List<Product> search(String productName) {
        return productDAO.findByNameLike('%' + productName.toUpperCase() + '%');
    }

    public void addOrUpdate(Product product) {
        productDAO.save(product);
//        productDetail = productDetailDAO.getOne(product.getId());
//        productDetail.setQuantity(product.getProductDetail().getQuantity());
//        productDetail.setSwipeList(product.getProductDetail().getSwipeList());
//        productDetailDAO.save(productDetail);
        System.out.println("新增商品:\n"+product.getName());
    }

    public String seeProduct(int userId, int productId) throws Exception {
        String result = "未知错误";
        if (isExist(userId)) {
            Product product = productDAO.findById(productId);
            // 把当前商品的类型加入到当前用户的喜爱类型列表
            if (null != product) {
                userService.addFavorType(userId, product.getType());
                return product.toString();
            } else {
                return "无此商品";
            }
            // 拼接返回结果，包括了 product和detail
//            StringBuilder productDetail = new StringBuilder(product.toString());
//            ProductDetail detail = productDetailDAO.findById(productId);
//            if (null != detail) {
//                if (product.toString().length() > 3) {
//                    productDetail.insert(
//                            (product.toString().length() - 1),
//                            "," + detail.toString());
//                } else {
//                    productDetail.insert(1, detail.toString());
//                }
//                result = "[" + productDetail.toString() + "]";
//            } else {
//                result = "没找到这个商品";
//            }
        }
        return result;
    }

    public String getProductSwipeImages() throws Exception {
        return "[{\"src\"='http://localhost:8443/image/nv.jpg'},"
                + "{\"src\"='http://localhost:8443/image/pangxie.jpg'},"
                + "{\"src\"='http://localhost:8443/image/phone.png'},"
                + "{\"src\"='http://localhost:8443/image/quanji.jpg'}]";
    }

    public List<String> getProductDetailSwipe(int id) throws Exception {
        Product product = getById(id);
        if (null != product) {
//            String swipeList = product.getProductDetail().getSwipeList();
            String swipeList = product.getSwipes();

            if (!swipeList.startsWith("["))
                swipeList = "["+swipeList+"]";
            return JSONObject.parseArray(swipeList, String.class);
        }
        return null;
    }

    public Result deleteProduct(int id) throws Exception {
        Product product = getById(id);
        if (null != product) {
            productDAO.delete(product);
            System.out.println("id: "+product.getId()+" name: "+product.getName()+" deleted");
            return new Result(Constants.code_success, "删除成功");
        } else {
            return new Result(Constants.code_nofind, "找不到该商品");
        }
    }
}
