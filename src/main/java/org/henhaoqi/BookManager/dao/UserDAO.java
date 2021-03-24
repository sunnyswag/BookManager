package org.henhaoqi.BookManager.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.henhaoqi.BookManager.entity.User;

@Mapper
public interface UserDAO {

    String table_name = " user ";
    String insert_filed = " name, email, password ";
    String select_field = " id, " + insert_filed;

    @Insert({"insert into", table_name, "(", insert_filed, ") values (#{name}, #{email}, #{password})"})
    int addUser(User user);

    @Select({"select", select_field, "from", table_name, "where id=#{id}"})
    User selectById(int id);

    @Select({"select", select_field, "from", table_name, "where name = #{name}"})
    User selectByName(String name);

    @Select({"select", select_field, "from", table_name, "where email=#{email}"})
    User selectByEmail(String email);

    @Select({"update", table_name, "set password=#{password} where id=#{id}"})
    User updatePassword(User user);
}
