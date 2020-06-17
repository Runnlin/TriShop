package cn.hstc.trishop.controller;

import cn.hstc.trishop.pojo.Product;
import cn.hstc.trishop.result.Result;
import cn.hstc.trishop.result.UploadFileResponse;
import cn.hstc.trishop.service.FileService;
import cn.hstc.trishop.service.ProductService;
import cn.hstc.trishop.utils.Constants;
import cn.hstc.trishop.utils.StringUtils;
import com.alibaba.fastjson.parser.Feature;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.mapstruct.MapperConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品相关
 */
@RestController
@Api(tags = "商品相关接口")
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    FileService fileService;

    @ApiOperation(value = "获取所有商品列表")
    @GetMapping("api/products")
    @ResponseBody
    public List<Product> list() throws Exception {
        return productService.list();
    }

    @ApiOperation(value = "通过商品名搜索")
    @GetMapping("api/product/search")
    @ResponseBody
    public List<Product> searchProduct(@RequestParam("keywords") String keywords) throws Exception {
        if ("".equals(keywords)) {
            return productService.list();
        } else {
            return productService.search(keywords);
        }
    }

    @ApiOperation(value = "通过类别获取获取商品列表", notes = "通过typeList进行查找，typeList格式：“1,2”" +
            "\n 参数名： types")
    @GetMapping("api/product/types")
    @ResponseBody
    public List<Product> listByType(@RequestParam(name = "types") String typeLists,
                                    @RequestParam(name = "user_id", required = false, defaultValue = "0") Integer userId) throws Exception {
        // 对html标签进行转义，防止xss攻击
        String typeLists1 = HtmlUtils.htmlEscape(typeLists);
        // 获取全部
        if (typeLists1.equals("0")) {
                return productService.list();
        }
        // 获取私人推荐
        if (typeLists1.equals("9")) {
            if (0 != userId)
                return productService.getProductListByPerson(userId);
            else
                return productService.list();
        }
        return productService.getProductListByType("[" + typeLists1 + "]");
    }

    @ApiOperation(value = "新增/编辑 商品", notes = "必填项： product_fee(不为0)  product_name  \n" +
            "若要编辑商品信息，发送更新后的相关字段时同时发送商品id\n" +
            "<b>新增商品不要设置ID，编辑商品需要上传ID</b>")
    @PostMapping("api/product/add")
    @ResponseBody
    public Result addOrUpdate(@RequestBody Product requestProduct) {
        if (requestProduct.getFee() == 0)
            return new Result(Constants.code_void, "价格不可为0");

        if (!requestProduct.getName().isEmpty()) {
            return new Result(Constants.code_success, "成功");
        } else {
            return new Result(Constants.code_void, "商品名不能为空");
        }
    }

    @ApiOperation(value = "查看商品详情", notes = "只需字段： userId、productId\n" +
            "找到了商品会返回商品详情，同时将该商品类型添加到用户喜爱类型，若没有找到就返回空")
    @GetMapping("api/product/see")
    @ResponseBody
    public Product seeProduct(@RequestParam("user_id") int userId,
                             @RequestParam("product_id") int productId) throws Exception {
        return productService.seeProduct(userId, productId);
    }

    @ApiOperation(value = "首页轮播图片", notes = "内容暂时写死")
    @GetMapping("api/product/getSwipeImages")
    @ResponseBody
    public String getProductSwipeImages() throws Exception {
        return productService.getProductSwipeImages();
    }

    @ApiOperation(value = "获取某商品的轮播图")
    @GetMapping("api/product/getlunbotu")
    @ResponseBody
    public List<String> getProductDetailSwipe(@RequestParam int id) throws Exception {
        return productService.getProductDetailSwipe(id);
    }

    @ApiOperation(value = "删除某商品")
    @GetMapping("api/product/delete")
    @ResponseBody
    public Result deleteProduct(@RequestParam int id) throws Exception {
        return productService.deleteProduct(id);
    }

    @ApiOperation(value = "购买商品", notes = "会把商品库存减一，同时添加入订单表\n" +
            "product_num: 购买数量")
    @GetMapping("api/product/buy")
    @ResponseBody
    public Result buyProduct(@RequestParam("user_id") int userId,
                             @RequestParam("product_id") int productId,
                             @RequestParam(name = "product_num", defaultValue = "1", required = false) Integer num) {
        return productService.buyProduct(userId, productId, num);
    }

    @ApiOperation(value = "购买多个商品", notes = "分别使用商品id和数量进行购买\n" +
            "pids： 购买商品id列表\n" +
            "nums：购买数量列表\n" +
            "pid和num必须按顺序写入，确保他们的对应关系")
    @GetMapping("api/product/buymany")
    @ResponseBody
    public Result buyProducts(@RequestParam("user_id") int userId,
                              @RequestParam("pids") List<Integer> pids,
                             @RequestParam("nums") List<Integer> nums) {
        return productService.buyProducts(userId, pids, nums);
    }

    @ApiOperation(value = "通过多个商品id获取多个商品信息", notes = "request格式：\"1,2,3\"")
    @GetMapping("api/product/getlist")
    @ResponseBody
    public List<Product> getManyProducts(@RequestParam String res) {
        // 对html标签进行转义，防止xss攻击
        res = HtmlUtils.htmlEscape(res);
        return productService.getManyProducts(res);
    }

    @ApiOperation(value = "获取用户已购买订单")
    @GetMapping("api/product/getOrders")
    @ResponseBody
    public List<Product> getOrders(@RequestParam int userId) {
        return productService.getOrderProductsByUserId(userId);
    }

//    @ApiOperation(value = "上传单个文件")
//    @PostMapping("api/product/upload")
//    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file){
//        return fileService.upload(file);
//    }
//
//    @ApiOperation(value = "上传多个文件", notes = "前后端实现参考：https://zhuanlan.zhihu.com/p/60856486")
//    @PostMapping("api/product/uploads")
//    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
//        return Arrays.stream(files)
//                .map(this::uploadFile)
//                .collect(Collectors.toList());
//    }
//
//    @ApiOperation(value = "下载单个文件")
//    @GetMapping("api/downloadFile/{fileName:.+}")
//    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
//        return fileService.downloadFile(fileName, request);
//    }
}
