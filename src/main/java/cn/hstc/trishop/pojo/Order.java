package cn.hstc.trishop.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.lettuce.core.dynamic.annotation.Key;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 订单
 */
@Entity
@Table(name = "product_orders")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
@ApiModel("订单实体")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ApiParam(value = "订单——用户id")
    int uid;

    @ApiParam(value = "订单——商品id")
    int pid;

    @ApiParam(value = "订单——商品数量")
    @Column(name = "num")
    int num;

    @ApiParam(value = "订单——购买日期")
    Date date;

    @ApiParam(value = "订单——商品名称")
    @Column(name = "product_name")
    String productName;// 商品名称

    @ApiParam(value = "订单——商品图片")
    @Column(name = "product_photo_url")
    String photoUrl;//产品图片

    @ApiParam(value = "订单——商品费用")
    @Column(name = "product_fee")
    float fee;//产品费用

    public int getId() {
        return this.id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int id) {
        this.uid = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public float getFee() {
        return fee;
    }

    public void setFee(float fee) {
        this.fee = fee;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", uid=" + uid +
                ", pid=" + pid +
                ", num=" + num +
                ", date=" + date +
                ", productName='" + productName + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", fee=" + fee +
                '}';
    }
}
