package net.competition.daoimpl;

import net.competition.dao.ExpertDao;
import net.competition.exception.ServerErrorCode;
import net.competition.exception.ServerException;
import net.competition.model.Application;
import net.competition.model.Expert;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ExpertDaoImpl extends DaoImplBase implements ExpertDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExpertDaoImpl.class);

    @Override
    public String registerExpert(Expert expert) throws ServerException {
        LOGGER.debug("DAO register expert {}", expert);
        String token = UUID.randomUUID().toString();
        try (SqlSession sqlSession = getSession()) {
            try {
                int resultOfOperation = getUserMapper(sqlSession).register(expert);
                if (resultOfOperation == 0) {
                    sqlSession.rollback();
                    throw new ServerException(ServerErrorCode.USER_WRONG);
                }
                getExpertMapper(sqlSession).register(expert);
                getSessionMapper(sqlSession).insert(expert, token);
                for (String direction : expert.getDirections()) {
                    getDirectionMapper(sqlSession).addExpertDirection(direction, expert);
                }
            } catch (RuntimeException ex) {
                LOGGER.debug("Can't register expert {}", expert, ex);
                sqlSession.rollback();
                throw new ServerException(ServerErrorCode.USER_WRONG);
            }
            sqlSession.commit();
        }
        return token;
    }

    @Override
    public void delete(Expert expert) {
        LOGGER.debug("DAO delete expert {}", expert);
        try (SqlSession sqlSession = getSession()) {
            try {
                getUserMapper(sqlSession).delete(expert);
                getSessionMapper(sqlSession).delete(expert);
            } catch (RuntimeException ex) {
                LOGGER.debug("Can't delete expert {}", expert, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }

    @Override
    public String login(String login, String password) throws ServerException {
        LOGGER.debug("DAO login expert {}", login);
        String token = UUID.randomUUID().toString();
        try (SqlSession sqlSession = getSession()) {
            try {
                int resultOfOperation = getSessionMapper(sqlSession).login(token, login, password);
                if (resultOfOperation == 0) {
                    sqlSession.rollback();
                    throw new ServerException(ServerErrorCode.LOGIN_WRONG);
                }
            } catch (RuntimeException ex) {
                LOGGER.debug("Can't login expert {}", login, ex);
                sqlSession.rollback();
                throw new ServerException(ServerErrorCode.LOGIN_WRONG);
            }
            sqlSession.commit();
        }
        return token;
    }

    @Override
    public void logout(Expert expert) {
        LOGGER.debug("DAO logout expert {}", expert);
        try (SqlSession sqlSession = getSession()) {
            try {
                getSessionMapper(sqlSession).logout(expert);
            } catch (RuntimeException ex) {
                LOGGER.debug("Can't logout expert {}", expert, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }

    @Override
    public void putRating(Expert expert, Application application, int rating) {
        LOGGER.debug("DAO put rating {} for application {} by expert {}", rating, application, expert);
        try (SqlSession sqlSession = getSession()) {
            try {
                getExpertMapper(sqlSession).putRating(expert, application, rating);
            } catch (RuntimeException ex) {
                LOGGER.debug("Can't put rating {}", rating, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }

    @Override
    public void changeRating(Expert expert, Application application, int rating) {
        LOGGER.debug("DAO change rating {} for application {} by expert {}", rating, application, expert);
        try (SqlSession sqlSession = getSession()) {
            try {
                getExpertMapper(sqlSession).changeRating(expert, application, rating);
            } catch (RuntimeException ex) {
                LOGGER.debug("Can't change rating {}", rating, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }

    @Override
    public void deleteRating(Expert expert, Application application) {
        LOGGER.debug("DAO delete rating for application {} by expert {}", application, expert);
        try (SqlSession sqlSession = getSession()) {
            try {
                getExpertMapper(sqlSession).deleteRating(expert, application);
            } catch (RuntimeException ex) {
                LOGGER.debug("Can't delete rating for application {} by expert {}", application, expert, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }

    @Override
    public List<Application> getApplicationsWithRating(Expert expert) {
        LOGGER.debug("DAO get applications with rating by expert {}", expert);
        try (SqlSession sqlSession = getSession()) {
            return getApplicationMapper(sqlSession).getApplicationsWithRating(expert);
        } catch (RuntimeException ex) {
            LOGGER.debug("Can't get applications with rating by expert {}", expert, ex);
            throw ex;
        }
    }

    @Override
    public List<Application> getApplicationsByDirection(List<String> directions) {
        LOGGER.debug("DAO get applications by directions {}", directions);
        try (SqlSession sqlSession = getSession()) {
            return getApplicationMapper(sqlSession).getApplicationsByDirection(directions);
        } catch (RuntimeException ex) {
            LOGGER.debug("Can't get applications by directions {}", directions, ex);
            throw ex;
        }
    }

    @Override
    public List<Application> summarizing(int grantFund, double minRating) {
        LOGGER.debug("DAO summarize the results of competition");
        try (SqlSession sqlSession = getSession()) {
            List<Application> sortedApplications = getApplicationMapper(sqlSession).summarizing(grantFund, minRating);
            Iterator<Application> applicationIterator = sortedApplications.iterator();
            while (applicationIterator.hasNext()) {
                Application nextApplication = applicationIterator.next();
                if (grantFund >= nextApplication.getRequestedAmount())
                    grantFund -= nextApplication.getRequestedAmount();
                else applicationIterator.remove();
            }
            return sortedApplications;
        } catch (RuntimeException ex) {
            LOGGER.debug("Can't summarize the results of competition");
            throw ex;
        }
    }

    @Override
    public Expert getExpertByToken(String token) throws ServerException {
        LOGGER.debug("DAO get expert by token {}", token);
        try (SqlSession sqlSession = getSession()) {
            Expert expert = getExpertMapper(sqlSession).getExpertByToken(token);
            if (expert == null) throw new ServerException(ServerErrorCode.TOKEN_WRONG);
            return expert;
        } catch (RuntimeException ex) {
            LOGGER.debug("Can't get expert by token {}", token, ex);
            throw new ServerException(ServerErrorCode.TOKEN_WRONG);
        }
    }

    @Override
    public void clearDataBase() {
        try (SqlSession sqlSession = getSession()) {
            try {
                getUserMapper(sqlSession).deleteAll();
            } catch (RuntimeException ex) {
                LOGGER.debug("Can't delete all", ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }

}
