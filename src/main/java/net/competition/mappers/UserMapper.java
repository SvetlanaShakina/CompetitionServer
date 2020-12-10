package net.competition.mappers;

import net.competition.model.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

public interface UserMapper {
    @Insert("INSERT INTO user (firstName, lastName, login, password) VALUES (#{firstName}, #{lastName}, " +
            "#{login}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer register(User user);

    @Delete("DELETE FROM user WHERE login = #{login}")
    void delete(User user);

    @Delete("DELETE FROM user")
    void deleteAll();
}
