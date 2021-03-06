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
        User user;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dbConnectionPool.getConnection();
            statement = connection.prepareStatement(request);
            statementInitializer.addCredentials(statement, username, password);
            resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new DAOException("message.no_user");
            }
            user = resultCreator.getNextUser(resultSet);
        } catch (SQLException | ConnectionPoolException e) {
            log.error(e);
            throw new DAOException("Something went wrong during getting user by credentials");
        } finally {
            resourceCloser.close(resultSet);
            resourceCloser.close(statement);
            resourceCloser.close(connection);
        }
        return user;
    }

    @Override
    public void addUser(User user) throws DAOException {
        String request = SQLRequest.CREATE_USER;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dbConnectionPool.getConnection();
            statement = connection.prepareStatement(request);
            statementInitializer.addUser(statement, user);
            statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            log.error(e);
            throw new DAOException("Something went wrong during adding user");
        } finally {
            resourceCloser.close(statement);
            resourceCloser.close(connection);
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
            statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            log.error(e);
            throw new DAOException("Something went wrong during updating user");
        } finally {
            resourceCloser.close(statement);
            resourceCloser.close(connection);
        }
    }

    @Override
    public User getUser(long userId) throws DAOException {
        String request = SQLRequest.GET_USER_BY_ID;
        User user;
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
            throw new DAOException("Something went wrong during getting user by id");
        } finally {
            resourceCloser.close(resultSet);
            resourceCloser.close(statement);
            resourceCloser.close(connection);
        }
        return user;
    }

    @Override
    public void changeDeletedStatus(long userId) throws DAOException {
        String request = SQLRequest.CHANGE_USER_DELETED_STATUS;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dbConnectionPool.getConnection();
            statement = connection.prepareStatement(request);
            statementInitializer.changeDeletedStatus(statement, userId);
            statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            log.error(e);
            throw new DAOException("Something went wrong during deleting user");
        } finally {
            resourceCloser.close(statement);
            resourceCloser.close(connection);
        }
    }

    @Override
    public void changeRole(long userId) throws DAOException {
        String request = SQLRequest.CHANGE_USER_ROLE;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dbConnectionPool.getConnection();
            statement = connection.prepareStatement(request);
            statementInitializer.changeUserRole(statement, userId);
            statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            log.error(e);
            throw new DAOException("Something went wrong during changing user role");
        } finally {
            resourceCloser.close(statement);
            resourceCloser.close(connection);
        }
    }

    @Override
    public int getNumberOfRows() throws DAOException {
        String request = SQLRequest.COUNT_ROWS_OF_USERS;
        Connection connection = null;
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
            throw new DAOException("Something went wrong during counting number of rows in users");
        } finally {
            resourceCloser.close(resultSet);
            resourceCloser.close(statement);
            resourceCloser.close(connection);
        }
        return numberOfRows;
    }

    @Override
    public List<User> getAll() throws DAOException {
        String request = SQLRequest.GET_ALL_USERS;
        List<User> users = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dbConnectionPool.getConnection();
            statement = connection.prepareStatement(request);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = resultCreator.getNextUser(resultSet);
                users.add(user);
            }
        } catch (SQLException | ConnectionPoolException e) {
            log.error(e);
            throw new DAOException("Something went wrong during getting all users");
        } finally {
            resourceCloser.close(resultSet);
            resourceCloser.close(statement);
            resourceCloser.close(connection);
        }
        return users;
    }

    @Override
    public boolean checkEmail(String email) throws DAOException {
        String request = SQLRequest.GET_ALL_USERS_BY_EMAIL_TO_ADD;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dbConnectionPool.getConnection();
            statement = connection.prepareStatement(request);
            statementInitializer.addEmailToAdd(statement, email);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                throw new DAOException("Your email is not unique");
            }
        } catch (SQLException | ConnectionPoolException e) {
            log.error(e);
            throw new DAOException("Something went wrong during checking email");
        } finally {
            resourceCloser.close(resultSet);
            resourceCloser.close(statement);
            resourceCloser.close(connection);
        }
        return true;
    }

    @Override
    public boolean checkUsername(String username) throws DAOException {
        String request = SQLRequest.GET_ALL_USERS_BY_USERNAME_TO_ADD;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dbConnectionPool.getConnection();
            statement = connection.prepareStatement(request);
            statementInitializer.addUsernameToAdd(statement, username);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                throw new DAOException("Your username is not unique");
            }
        } catch (SQLException | ConnectionPoolException e) {
            log.error(e);
            throw new DAOException("Something went wrong during checking username");
        } finally {
            resourceCloser.close(resultSet);
            resourceCloser.close(statement);
            resourceCloser.close(connection);
        }
        return true;
    }
}
