package net.competition.daoimpl;

import net.competition.dao.ContestantDao;
import net.competition.exception.ServerErrorCode;
import net.competition.exception.ServerException;
import net.competition.model.Application;
import net.competition.model.Contestant;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

public class ContestantDaoImpl extends DaoImplBase implements ContestantDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContestantDaoImpl.class);

    @Override
    public String registerContestant(Contestant contestant) throws ServerException {
        LOGGER.debug("DAO register contestant {}", contestant);
        String token = UUID.randomUUID().toString();
        try (SqlSession sqlSession = getSession()) {
            try {
                int resultOfOperation = getUserMapper(sqlSession).register(contestant);
                if (resultOfOperation == 0) {
                    sqlSession.rollback();
                    throw new ServerException(ServerErrorCode.USER_WRONG);
                }
                getContestantMapper(sqlSession).register(contestant);
                getSessionMapper(sqlSession).insert(contestant, token);
            } catch (RuntimeException ex) {
                LOGGER.debug("Can't register contestant {}", contestant, ex);
                sqlSession.rollback();
                throw new ServerException(ServerErrorCode.USER_WRONG);
            }
            sqlSession.commit();
        }
        return token;
    }

    @Override
    public void delete(Contestant contestant) {
        LOGGER.debug("DAO delete contestant {}", contestant);
        try (SqlSession sqlSession = getSession()) {
            try {
                getUserMapper(sqlSession).delete(contestant);
                getSessionMapper(sqlSession).delete(contestant);
            } catch (RuntimeException ex) {
                LOGGER.debug("Can't delete contestant {}", contestant, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }

    @Override
    public String login(String login, String password) throws ServerException {
        LOGGER.debug("DAO login contestant {}", login);
        String token = UUID.randomUUID().toString();
        try (SqlSession sqlSession = getSession()) {
            try {
                int resultOfOperation = getSessionMapper(sqlSession).login(token, login, password);
                if (resultOfOperation == 0) {
                    sqlSession.rollback();
                    throw new ServerException(ServerErrorCode.LOGIN_WRONG);
                }
            } catch (RuntimeException ex) {
                LOGGER.debug("Can't login contestant {}", login, ex);
                sqlSession.rollback();
                throw new ServerException(ServerErrorCode.LOGIN_WRONG);
            }
            sqlSession.commit();
        }
        return token;
    }

    @Override
    public void logout(Contestant contestant) {
        LOGGER.debug("DAO logout contestant {}", contestant);
        try (SqlSession sqlSession = getSession()) {
            try {
                getSessionMapper(sqlSession).logout(contestant);
            } catch (RuntimeException ex) {
                LOGGER.debug("Can't logout contestant {}", contestant, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }

    @Override
    public void addApplication(Contestant contestant, List<Application> applications) {
        LOGGER.debug("DAO add applications {} by contestant {}", applications, contestant);
        try (SqlSession sqlSession = getSession()) {
            try {
                for (Application application : applications) {
                    getApplicationMapper(sqlSession).addApplication(application, contestant);
                    for (String direction : application.getDirections()) {
                        getDirectionMapper(sqlSession).addDirection(direction, application);
                    }
                }
            } catch (RuntimeException ex) {
                LOGGER.debug("Can't add applications", ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }

    @Override
    public void cancelApplication(Contestant contestant, List<Application> applications) {
        LOGGER.debug("DAO cancel applications {} by contestant {}", applications, contestant);
        try (SqlSession sqlSession = getSession()) {
            try {
                for (Application application : applications) {
                    getApplicationMapper(sqlSession).cancelApplication(application, contestant);
                }
            } catch (RuntimeException ex) {
                LOGGER.debug("Can't cancel applications", ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }

    @Override
    public Contestant getContestantByToken(String token) throws ServerException {
        LOGGER.debug("DAO get contestant by token {}", token);
        try (SqlSession sqlSession = getSession()) {
            Contestant contestant = getContestantMapper(sqlSession).getContestantByToken(token);
            if (contestant == null) throw new ServerException(ServerErrorCode.TOKEN_WRONG);
            return contestant;
        } catch (RuntimeException ex) {
            LOGGER.debug("Can't get contestant by token {}", token, ex);
            throw new ServerException(ServerErrorCode.TOKEN_WRONG);
        }
    }
}
