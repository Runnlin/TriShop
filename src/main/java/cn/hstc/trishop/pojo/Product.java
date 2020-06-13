package cn.hstc.trishop.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.*;
import io.swagger.annotations.ApiParam;
import org.springframework.web.util.HtmlUtils;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "product")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
@ApiModel("产品实体")
public class Product {
    @Id
    // 这是确保每个级联的表的主键ID都从一开始增加
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(hidden = true)
    @Column(name = "id")
    int id;//唯一产品id

    @ApiParam(value = "产品名称")
    @Column(name = "product_name")
    String name;//产品名称

    @ApiParam(value = "产品图片")
    @Column(name = "product_photo_url")
    String photoUrl;//产品图片

    @ApiParam(value = "产品介绍")
    @Column(name = "product_introduction")
    String introduction; //产品介绍

    @ApiParam(value = "产品类型: 1:数码  2:生活  ")
    @Column(name = "product_type")
    String type;//产品类型

    @ApiParam(value = "产品费用")
    @Column(name = "product_fee")
    float fee;//产品费用

    @ApiParam(value = "产品销量")
    @Column(name = "product_sales")
    int sales;//产品销量

    @ApiParam(value = "产品上架日期")
    @Column(name = "product_date")
    @ApiModelProperty(hidden = true)
    Date date;//产品上架日期

    @ApiParam(value = "产品存货数量")
    @Column(name = "product_quantity")
    int quantity; // 剩余存货

    @ApiParam(value = "产品轮播图")
    @Column(name = "product_swipe")
    String swipes;

    // ProductDetail 关联
    // 通过关联表保存一对一关系
//    @OneToOne(cascade = CascadeType.ALL)
//    @PrimaryKeyJoinColumn(name = "detail_id")
//    ProductDetail productDetail;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getFee() {
        return fee;
    }

    public void setFee(float fee) {
        this.fee = fee;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSwipes() {
        return swipes;
    }

    public void setSwipes(String swipes) {
        this.swipes = swipes;
    }

    //    public ProductDetail getProductDetail() {
//        return productDetail;
//    }

//    public void setProductDetail(ProductDetail productDetail) {
//        this.productDetail = productDetail;
//    }


    @Override
    public String toString() {
        return HtmlUtils.htmlUnescape("[\"{" +
                "\"id\"=" + id +
                ", \"name\"=\"" + name + "\"" +
                ", \"photoUrl\"=\"" + photoUrl + "\"" +
                ", \"introduction\"=\"" + introduction + "\"" +
                ", \"type\"=\"" + type + "\"" +
                ", \"fee\"=" + fee +
                ", \"sales\"=" + sales +
                ", \"date\"=" + date + "\"" +
                ", \"quantity\"=" + quantity +
                ", \"swipes\"=\"" + swipes + "\"" +
                "}\"]");
//                        .replace("&lt;","<")
//                .replace("&#39;","'")
//                .replace("&gt;",">")
//                .replace("&quot;", "\"")
//                .replace("&nbsp;"," ");

    }
}
