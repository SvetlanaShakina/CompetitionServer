package net.competition.daoimpl;

import net.competition.mappers.*;
import net.competition.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

public class DaoImplBase {

    protected SqlSession getSession() {
        return MyBatisUtils.getSqlSessionFactory().openSession();
    }

    protected ContestantMapper getContestantMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(ContestantMapper.class);
    }

    protected ApplicationMapper getApplicationMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(ApplicationMapper.class);
    }

    protected ExpertMapper getExpertMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(ExpertMapper.class);
    }

    protected DirectionMapper getDirectionMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(DirectionMapper.class);
    }

    protected SessionMapper getSessionMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(SessionMapper.class);
    }

    protected UserMapper getUserMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(UserMapper.class);
    }
}
