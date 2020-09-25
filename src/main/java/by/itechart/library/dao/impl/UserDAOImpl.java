package by.itechart.library.dao.impl;

import by.itechart.library.dao.SQLRequest;
import by.itechart.library.dao.api.UserDAO;
import by.itechart.library.dao.exception.ConnectionPoolException;
import by.itechart.library.dao.exception.DAOException;
import by.itechart.library.dao.pool.DBConnectionPool;
import by.itechart.library.dao.util.DAOUtilFactory;
import by.itechart.library.dao.util.api.ResourceCloser;
import by.itechart.library.dao.util.api.ResultCreator;
import by.itechart.library.dao.util.api.StatementInitializer;
import by.itechart.library.entity.User;
import lombok.extern.log4j.Log4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Log4j
public class UserDAOImpl implements UserDAO {

    private DBConnectionPool dbConnectionPool = DBConnectionPool.getInstance();
    private DAOUtilFactory utilFactory = DAOUtilFactory.getInstance();
    private ResultCreator resultCreator = utilFactory.getResultCreator();
    private ResourceCloser resourceCloser = utilFactory.getResourceCloser();
    private StatementInitializer statementInitializer = utilFactory.getStatementInitializer();


    @Override
    public User getUser(String username, String password) throws DAOException {
        String request = SQLRequest.GET_USER_BY_CREDENTIALS;
        User user = null;
        Connection connection;
        PreparedStatement statement;
        ResultSet resultSet;
        try {
            connection = dbConnectionPool.getConnection();
            statement = connection.prepareStatement(request);
            statementInitializer.addCredentials(statement, username, password);
            resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new DAOException("No user with such credentials!");
            }
            user = resultCreator.getNextUser(resultSet);
        } catch (SQLException | ConnectionPoolException e) {
            log.error(e);
            throw new DAOException(e);
        }
        return user;
    }

    @Override
    public void addUser(User user) throws DAOException {
        String request = SQLRequest.CREATE_USER;
        Connection connection;
        PreparedStatement statement;
        try {
            connection = dbConnectionPool.getConnection();
            statement = connection.prepareStatement(request);
            statementInitializer.addUser(statement, user);
            statement.execute();
        } catch (SQLException | ConnectionPoolException e) {
            log.error(e);
            throw new DAOException("Something went wrong. Please try again.");
        }
    }

    @Override
    public void updateUser(User user) throws DAOException {
        String request = SQLRequest.UPDATE_USER;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dbConnectionPool.getConnection();
            statement = connection.prepareStatement(request);
            statementInitializer.updateUser(statement, user);
            statement.executeQuery();
        } catch (SQLException | ConnectionPoolException e) {
            log.error(e);
            throw new DAOException(e);
        } finally {
            resourceCloser.close(statement);
        }
    }

    @Override
    public User getUser(long userId) throws DAOException {
        String request = SQLRequest.GET_USER_BY_ID;
        User user = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dbConnectionPool.getConnection();
            statement = connection.prepareStatement(request);
            statementInitializer.addId(statement, userId);
            resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new DAOException("No user with such id!");
            }
            user = resultCreator.getNextUser(resultSet);
        } catch (SQLException | ConnectionPoolException e) {
            log.error(e);
            throw new DAOException(e);
        } finally {
            resourceCloser.close(resultSet);
            resourceCloser.close(statement);
        }
        return user;
    }

    @Override
    public int changeDeletedStatus(long userId) throws DAOException {
        String request = SQLRequest.CHANGE_USER_DELETED_STATUS;
        Connection connection = null;
        PreparedStatement statement = null;
        int result = 0;
        try {
            connection = dbConnectionPool.getConnection();
            statement = connection.prepareStatement(request);
            User user = getUser(userId);
            statementInitializer.changeDeletedStatus(statement, !user.isDeletedStatus(), userId);
            result = statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            log.error(e);
            throw new DAOException(e);
        } finally {
            resourceCloser.close(statement);
        }
        return result;
    }

    @Override
    public int getNumberOfRows() throws DAOException {
        String request = SQLRequest.COUNT_ROWS_OF_USERS;
        Connection connection;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int numberOfRows = 0;
        try {
            connection = dbConnectionPool.getConnection();
            statement = connection.prepareStatement(request);
            resultSet = statement.executeQuery();
            resultSet.last();
            numberOfRows = resultSet.getInt(1);
            while (resultSet.next()) {
                numberOfRows++;
            }
        } catch (SQLException | ConnectionPoolException e) {
            log.error(e);
            throw new DAOException(e);
        } finally {
            resourceCloser.close(resultSet);
            resourceCloser.close(statement);
        }
        return numberOfRows;
    }

    @Override
    public List<User> getAll(int currentPage, int recordsPerPage) throws DAOException {
        String request = SQLRequest.GET_ALL_USERS;
        int start = currentPage * recordsPerPage - recordsPerPage;
        List<User> users = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dbConnectionPool.getConnection();
            statement = connection.prepareStatement(request);
            statementInitializer.addPaginationParameters(statement, start, recordsPerPage);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = resultCreator.getNextUser(resultSet);
                users.add(user);
            }
        } catch (SQLException | ConnectionPoolException e) {
            log.error(e);
            throw new DAOException(e);
        } finally {
            resourceCloser.close(resultSet);
            resourceCloser.close(statement);
        }
        return users;
    }
}
