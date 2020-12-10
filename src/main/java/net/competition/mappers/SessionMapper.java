package net.competition.mappers;

import net.competition.model.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface SessionMapper {
    @Insert("INSERT INTO session (token, userid) VALUES (#{token}, #{user.id})")
    void insert(@Param("user") User user, @Param("token") String token);

    @Delete("DELETE FROM session WHERE userid = #{id}")
    void delete(User user);

    @Update("UPDATE session SET token = #{token} WHERE userid = (SELECT id FROM user " +
            "WHERE login = #{login} AND password = #{password})")
    int login(@Param("token") String token, @Param("login") String login, @Param("password") String password);

    @Update("UPDATE session SET token = NULL WHERE userid = (SELECT id FROM user WHERE login = #{login})")
    void logout(User user);
}
