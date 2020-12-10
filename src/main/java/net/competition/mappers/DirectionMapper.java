package net.competition.mappers;

import net.competition.model.Application;
import net.competition.model.Expert;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface DirectionMapper {

    @Select("SELECT name FROM direction WHERE applicationid = #{id}")
    List<String> getByApplication(Application application);

    @Delete("DELETE FROM direction")
    void deleteAll();

    @Insert("INSERT INTO direction SET name = #{direction}, applicationid = #{application.id}")
    void addDirection(@Param("direction") String direction, @Param("application") Application application);

    @Insert("INSERT INTO direction SET name = #{direction}, expertid = #{expert.id}")
    void addExpertDirection(@Param("direction") String direction, @Param("expert") Expert expert);

    @Select("SELECT name FROM direction WHERE expertid = #{expert.id}")
    List<String> getByExpert(Expert expert);
}
