package net.competition.mappers;

import net.competition.model.Application;
import net.competition.model.Expert;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface ExpertMapper {
    @Insert("INSERT INTO expert (id) VALUES (#{id})")
    Integer register(Expert expert);

    @Insert("INSERT INTO rating (rating, applicationid, expertid) VALUES (#{rating}, #{application.id}, " +
            "#{expert.id})")
    void putRating(@Param("expert") Expert expert, @Param("application") Application application, @Param("rating") int rating);

    @Update("UPDATE rating SET rating = #{rating} WHERE expertid = #{expert.id} AND " +
            "applicationid = #{application.id}")
    void changeRating(@Param("expert") Expert expert, @Param("application") Application application, @Param("rating") int rating);

    @Delete("DELETE FROM rating WHERE expertid = #{expert.id} AND applicationid = #{application.id}")
    void deleteRating(@Param("expert") Expert expert, @Param("application") Application application);

    @Select("SELECT user.id, firstName, lastName, login, password FROM user JOIN expert " +
            "ON expert.id = user.id WHERE expert.id = (SELECT userid FROM session WHERE token = #{token})")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "directions", column = "id", javaType = List.class,
                    many = @Many(select = "net.competition.mappers.DirectionMapper.getByExpert", fetchType = FetchType.LAZY))
    })
    Expert getExpertByToken(String token);
}
