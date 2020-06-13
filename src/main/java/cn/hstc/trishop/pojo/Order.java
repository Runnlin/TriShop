package cn.hstc.trishop.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.lettuce.core.dynamic.annotation.Key;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

import javax.persistence.*;
import java.util.List;

/**
 * 订单
 */
@Entity
@Table(name = "cart")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
@ApiModel("订单实体")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

    String pids;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPids() {
        return pids;
    }

    public void setPids(String pids) {
        this.pids = pids;
    }
}
