package com.penguin.pojo;



import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


import java.time.LocalDateTime;
//lombok  在编译阶段,为实体类自动生成setter  getter toString
// pom文件中引入依赖   在实体类上添加注解

@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

    private Integer id;//主键ID
    private String username;//用户名
    @JsonIgnore
    private String password;//密码
    @NotEmpty
    private String nickname;//昵称

    @Email
    private String email;//邮箱
    private String userPic;//用户头像地址
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间
    private Integer age;
    private String gender;
    private String phone;

//    User(Integer id , String username , String nickname , String phone){
//        this.id = id;
//        this.username = username;
//        this.nickname = nickname;
//        this.phone = phone;
//    }
//

}
