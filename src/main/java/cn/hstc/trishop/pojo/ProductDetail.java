//package cn.hstc.trishop.pojo;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import io.swagger.annotations.ApiModelProperty;
//
//import javax.persistence.*;
//import java.util.List;
//
//@Entity
//@Table(name = "product_detail")
//@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
//public class ProductDetail {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @ApiModelProperty(hidden = true)
//    @Column(name = "id")
//    int id;
//
//    @Column(name = "product_quantity")
//    int quantity;
//
//
//    @Column(name = "product_swipe")
//    String swipeList;
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public int getQuantity() {
//        return quantity;
//    }
//
//    public void setQuantity(int quantity) {
//        this.quantity = quantity;
//    }
//
//    public String getSwipeList() {
//        return swipeList;
//    }
//
//    public void setSwipeList(String swipeList) {
//        this.swipeList = swipeList;
//    }
//
//    @Override
//    public String toString() {
//        return "quantity=" + quantity +
//                ", swipeList={" + swipeList + "}";
//
//    }
//}
