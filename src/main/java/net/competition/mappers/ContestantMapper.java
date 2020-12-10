package net.competition.mappers;

import net.competition.model.Contestant;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface ContestantMapper {
    @Insert("INSERT INTO contestant (id, companyName) VALUES (#{id}, #{companyName})")
    Integer register(Contestant contestant);

    @Select("SELECT user.id, firstName, lastName, companyName, login, password FROM user JOIN contestant " +
            "ON contestant.id = user.id WHERE contestant.id = (SELECT userid FROM session WHERE token = #{token})")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "applications", column = "id", javaType = List.class,
                    many = @Many(select = "net.competition.mappers.ApplicationMapper.getByContestant", fetchType = FetchType.LAZY))
    })
    Contestant getContestantByToken(String token);
}
