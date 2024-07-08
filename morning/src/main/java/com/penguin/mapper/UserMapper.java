package com.penguin.mapper;

import com.penguin.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    //根据用户名查询

    @Select("select * from user where username = #{userName}")
    User findByUserName(String userName);

    @Insert("insert into user (username, password, create_time, update_time)" +
            " values (#{username},#{password},now(),now()) ")
    void add(String username, String password) ;

    @Update("update user set nickname = #{nickname},phone = #{phone},update_time = #{updateTime},age = #{age},gender = #{gender},`e-mail` = #{email} where id = #{id}")
    void update(User user);

    @Update("update user set user_pic =#{avatarUrl},update_time=now() where username = #{username} ")
    void updateavatar(String avatarUrl,String username);

    @Update("update user set  password =#{newPwd},update_time = now() where id = #{id} ")
    void updatePwd(String newPwd,Integer id);

//    @Update("UPDATE user SET username = #{username}, password = #{password} WHERE id = #{id}")
//    void update(User user);
}
