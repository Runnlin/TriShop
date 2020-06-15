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

    int uid;

    int pid;

    @Column(name = "num")
    int num;

    Date date;

    @Column(name = "product_name")
    String productName;

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

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", uid=" + uid +
                ", pid=" + pid +
                ", num=" + num +
                ", date=" + date +
                ", productName='" + productName + '\'' +
                '}';
    }
}
