package cn.hstc.trishop.service;

import cn.hstc.trishop.DAO.ProductDAO;
import cn.hstc.trishop.pojo.Order;
import cn.hstc.trishop.pojo.Product;
import cn.hstc.trishop.pojo.User;
import cn.hstc.trishop.result.Result;
import cn.hstc.trishop.utils.Constants;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductDAO productDAO;
    @Autowired
    UserService userService;
    @Autowired
    OrderService orderService;
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
        System.out.println("新增商品:\n"+product.getName());
    }

    public Product seeProduct(int userId, int productId) {
//        String result = "未知错误";
        if (isExist(productId)) {
            Product product = productDAO.findById(productId);
            // 把当前商品的类型加入到当前用户的喜爱类型列表
            if (null != product) {
                if (userService.userDAO.existsById(userId))
                    userService.addFavorType(userId, product.getType());
                return product;
            } else {
                return null;
            }
        }
        return null;
    }

    public String getProductSwipeImages() throws Exception {
        return "[{\"src\"='"+Constants.urlHead+"/image/nv.jpg'},"
                + "{\"src\"='"+Constants.urlHead+"/image/pangxie.jpg'},"
                + "{\"src\"='"+Constants.urlHead+"/image/phone.png'},"
                + "{\"src\"='"+Constants.urlHead+"/image/quanji.jpg'}]";
    }

    public List<String> getProductDetailSwipe(int id) throws Exception {
        Product product = getById(id);
        if (null != product) {
//            String swipeList = product.getProductDetail().getSwipeList();
            String swipeList = product.getSwipes();

            if (!swipeList.startsWith("["))
                swipeList = "["+swipeList+"]";
//            System.out.println(swipeList);
            return JSONObject.parseArray(swipeList, String.class);
        }
        return null;
    }

    public Result deleteProduct(int id)  {
        Product product = getById(id);
        if (null != product) {
            productDAO.delete(product);
            System.out.println("id: "+product.getId()+" name: "+product.getName()+" deleted");
            return new Result(Constants.code_success, "删除成功");
        } else {
            return new Result(Constants.code_nofind, "找不到该商品");
        }
    }

    public Result buyProduct(int userId, int productId) {
        if (productDAO.existsById(productId) && userService.existsById(userId)) {
            Product product = getById(productId);
            if (null != product) {
                if (product.getQuantity() > 0) {
                    product.setQuantity(product.getQuantity() - 1);
                    productDAO.save(product);
                    return orderService.addOrUpdate(userId, productId, product.getName());
                }
                else
                    return new Result(Constants.code_nofind, "购买失败:库存不足");
            }
            return new Result(Constants.code_nofind, "购买失败:无此商品");
        }
        return new Result(Constants.code_nofind, "购买失败:用户或商品id错误");
    }

    public List<Product> getManyProducts(String res) {
        if (!res.startsWith("["))
            res = "["+res+"]";
        List<Integer> reslist = JSONObject.parseArray(res, Integer.class);
        List<Product> results = new ArrayList<>();
        for (Integer integer : reslist)
            results.add(productDAO.getOne(integer));
        return results;
    }

    public List<Product> getOrderProductsByUserId(int userId) {
        if (userService.existsById(userId)) {
            List<Product> results = new ArrayList<>();
            List<Order> orders = orderService.listByUserId(userId);
            for (Order order : orders) {
                results.add(productDAO.getOne(order.getPid()));
            }

            return results;
        }
        return null;
    }
}
