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

    Date date;

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

    @Override
    public String toString() {
        return "Order{" +
                "uid=" + uid +
                ", pid=" + pid +
                ", date=" + date +
                '}';
    }
}
