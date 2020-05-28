package cn.hstc.trishop.controller;

import cn.hstc.trishop.pojo.Product;
import cn.hstc.trishop.pojo.User;
import cn.hstc.trishop.result.Result;
import cn.hstc.trishop.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.elasticsearch.client.license.LicensesStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * 商品相关
 */
@RestController
@Api(tags = "商品相关接口")
public class ProductController {
    @Autowired
    ProductService productService;

    @ApiOperation(value = "获取所有商品列表")
    @GetMapping("api/products")
    public List<Product> list() throws Exception {
        return productService.list();
    }

    @ApiOperation(value = "通过商品名搜索")
    @GetMapping("api/product/search")
    public List<Product> searchProduct(@RequestParam("keywords") String keywords) throws Exception {
        if ("".equals(keywords)) {
            return productService.list();
        } else {
            return productService.search(keywords);
        }
    }

    @ApiOperation(value = "通过类别获取获取商品列表", notes = "通过typeList进行查找，typeList格式：“0,1,2”" +
            "\n 如果typeList为单独一个0，则返回所有商品数据" +
            "\n 参数名： types")
    @GetMapping("api/product/types")
    @ResponseBody
    public List<Product> listByType(@RequestParam("types") String typeLists) throws Exception {
        // 对html标签进行转义，防止xss攻击
        String typeLists1 = HtmlUtils.htmlEscape(typeLists);
        return productService.getProductListByType("["+typeLists1+"]");
    }
}
