package cn.hstc.trishop.controller;

import cn.hstc.trishop.pojo.Product;
import cn.hstc.trishop.result.Result;
import cn.hstc.trishop.result.UploadFileResponse;
import cn.hstc.trishop.service.FileService;
import cn.hstc.trishop.service.ProductService;
import cn.hstc.trishop.utils.Constants;
import cn.hstc.trishop.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
        return productService.getProductListByType("[" + typeLists1 + "]");
    }

    @ApiOperation(value = "新增商品", notes = "价格：product_fee  和  商品名：product_name  为必填项\n" +
            "<b>不要设置ID</b>")
    @PostMapping("api/product/add")
    @ResponseBody
    public Result addProduct(@RequestBody Product requestProduct) {
        if (requestProduct.getFee() == 0)
            return new Result(Constants.code_void, "价格不可为0");

        if (!requestProduct.getName().isEmpty()) {
            productService.add(requestProduct);
            return new Result(Constants.code_success, "成功");
        } else {
            return new Result(Constants.code_void, "商品名不能为空");
        }
    }

    @ApiOperation(value = "查看商品详情", notes = "只需字段： userId、productId\n" +
            "找到了商品会返回商品详情，同时将该商品类型添加到用户喜爱类型，若没有找到就返回空")
    @GetMapping("api/product/see")
    @ResponseBody
    public String seeProduct(@RequestParam("user_id") int userId,
                             @RequestParam("product_id") int productId) throws Exception {
        return productService.seeProduct(userId, productId);
    }

    @ApiOperation(value = "随机获取商品的图片，可以用作滚动图", notes = "字段：pic_num  用作获取所需图片链接数量\n" +
            "若请求的数量大于合计数量，则返回最大")
    @GetMapping("api/product/getSwipeImages")
    public String getProductSwipeImages() throws Exception {
        return productService.getProductSwipeImages();
    }

    @ApiOperation(value = "上传单个文件")
    @PostMapping("api/product/upload")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file){
        return fileService.upload(file);
    }

    @ApiOperation(value = "上传多个文件", notes = "前后端实现参考：https://zhuanlan.zhihu.com/p/60856486")
    @PostMapping("api/product/uploads")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.stream(files)
                .map(this::uploadFile)
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "下载单个文件")
    @GetMapping("api/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        return fileService.downloadFile(fileName, request);
    }
}
