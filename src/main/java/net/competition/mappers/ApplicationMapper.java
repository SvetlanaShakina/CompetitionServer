package net.competition.mappers;

import net.competition.model.Application;
import net.competition.model.Contestant;
import net.competition.model.Expert;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface ApplicationMapper {

    @Insert("INSERT INTO application (title, description, requestedAmount, contestantid) VALUES (#{application.title}," +
            "#{application.description}, #{application.requestedAmount}, #{contestant.id})")
    @Options(useGeneratedKeys = true, keyProperty = "application.id")
    void addApplication(@Param("application") Application application, @Param("contestant") Contestant contestant);

    @Delete("DELETE FROM application WHERE title = #{application.title} AND contestantid = #{contestant.id}")
    void cancelApplication(@Param("application") Application application, @Param("contestant") Contestant contestant);

    @Select("SELECT id, title, description, requestedAmount FROM application WHERE contestantid = #{id}")
    @Result(property = "directions", column = "id", javaType = List.class,
            many = @Many(select = "net.competition.mappers.DirectionMapper.getByApplication", fetchType = FetchType.LAZY))
    List<Application> getByContestant(Contestant contestant);

    @Select({"<script>",
            "SELECT id, title, description, requestedAmount FROM application WHERE id IN " +
                    "(SELECT applicationid FROM direction WHERE name IN (" +
                    "<foreach item='item' index='index' collection='list' separator=','> " +
                    "#{item}",
            "</foreach>",
            "))",
            "</script>"})
    @Result(property = "directions", column = "id", javaType = List.class,
            many = @Many(select = "net.competition.mappers.DirectionMapper.getByApplication", fetchType = FetchType.EAGER))
    List<Application> getApplicationsByDirection(@Param("list") List<String> directions);

    @Select("SELECT id, title, description, requestedAmount FROM application WHERE id IN (SELECT applicationid FROM rating WHERE " +
            "expertid = #{id})")
    @Result(property = "directions", column = "id", javaType = List.class,
            many = @Many(select = "net.competition.mappers.DirectionMapper.getByApplication", fetchType = FetchType.EAGER))
    List<Application> getApplicationsWithRating(Expert expert);

    @Select("SELECT application.id, title, description, requestedAmount FROM rating, application " +
            "WHERE application.id = applicationid GROUP BY applicationid " +
            "HAVING AVG(rating) >= #{minRating} AND requestedAmount <= #{grantFund} " +
            "ORDER BY AVG(rating) DESC, requestedAmount")
    @Result(property = "directions", column = "id", javaType = List.class,
            many = @Many(select = "net.competition.mappers.DirectionMapper.getByApplication", fetchType = FetchType.EAGER))
    List<Application> summarizing(@Param("grantFund") int grantFund, @Param("minRating") double minRating);

}
