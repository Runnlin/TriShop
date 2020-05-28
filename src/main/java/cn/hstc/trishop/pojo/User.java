package cn.hstc.trishop.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.List;

/**
 * 用户信息
 */
@Entity
@Table(name = "user")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
@ApiModel("用户实体")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
            @ApiModelProperty("唯一用户id")
    int id;//唯一用户id
    @Column(name = "account")
    String account;//帐号
    @Column(name = "password")
    String password;//密码
    @Column(name = "gender")
    int gender = 3;//性别：1男  2女  3其他
    @Column(name = "phone_num")
    String phoneNum = "none";//手机号
    @Column(name = "email")
    String email = "none";//邮箱
    @Column(name = "head_url")
    String headUrl = "http://";//头像的url
    @Column(name = "is_admin")
    int isAdmin = 0; // 用户类型，1为管理员账户  0为普通会员
    @Column(name = "favor_type_list")
    String favorTypeList = "0";//用户的喜好类型列表，详情看Type类

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getFavorTypeList() {
        return favorTypeList;
    }

    public void setFavorTypeList(String favorTypeList) {
        this.favorTypeList = favorTypeList;
    }

    public int isAdmin() {
        return isAdmin;
    }

    public void setAdmin(int admin) {
        isAdmin = admin;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", gender=" + gender +
                ", phoneNum='" + phoneNum + '\'' +
                ", email='" + email + '\'' +
                ", headUrl='" + headUrl + '\'' +
                ", isAdmin=" + isAdmin +
                ", favorTypeList='" + favorTypeList + '\'' +
                '}';
    }
}
