package by.itechart.library.dao.util.impl;

import by.itechart.library.dao.util.api.StatementInitializer;
import by.itechart.library.entity.Book;
import by.itechart.library.entity.BorrowRecord;
import by.itechart.library.entity.User;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class StatementInitializerImpl implements StatementInitializer {
    @Override
    public void addCredentials(PreparedStatement statement, String username, String password) throws SQLException {
        statement.setString(1, username);
        statement.setString(2, password);
    }

    @Override
    public void addUser(PreparedStatement statement, User user) throws SQLException {
        statement.setString(1, user.getFirstName());
        statement.setString(2, user.getEmail());
        statement.setDate(3, Date.valueOf(user.getDateOfRegistration()));
        statement.setString(4, user.getPhoneNumber());
        statement.setString(5, user.getLastName());
        statement.setBoolean(6, user.isGender());
        statement.setString(7, user.getUsername());
        statement.setString(8, user.getPassword());
        statement.setInt(9, user.getRole()
                                .getRoleId());
    }

    @Override
    public void addId(PreparedStatement statement, long id) throws SQLException {
        statement.setLong(1, id);
    }

    @Override
    public void updateUser(PreparedStatement statement, User user) throws SQLException {
        statement.setNString(1, user.getFirstName());
        statement.setNString(2, user.getPhoneNumber());
        statement.setNString(3, user.getLastName());
        statement.setBoolean(4, user.isGender());
        statement.setNString(5, user.getPassword());
        statement.setLong(6, user.getId());
    }

    @Override
    public void changeDeletedStatus(PreparedStatement statement, long id) throws SQLException {
        statement.setLong(1, id);
    }

    @Override
    public void addBook(PreparedStatement statement, Book book) throws SQLException {
        statement.setBinaryStream(1,book.getCoverStream());
        statement.setNString(2, book.getTitle());
        statement.setNString(3, book.getAuthors());
        statement.setNString(4, book.getPublisher());
        statement.setDate(5, Date.valueOf(book.getPublishDate()));
        statement.setNString(6, book.getGenres());
        statement.setInt(7, book.getPageCount());
        statement.setNString(8, book.getISBN());
        statement.setNString(9, book.getDescription());
        statement.setInt(10, book.getTotalAmount());
        statement.setInt(11, book.getAvailableAmount());
    }

    @Override
    public void updateBook(PreparedStatement statement, Book book) throws SQLException {
        statement.setBinaryStream(1,book.getCoverStream());
        statement.setNString(2, book.getTitle());
        statement.setNString(3, book.getAuthors());
        statement.setNString(4, book.getPublisher());
        statement.setDate(5, Date.valueOf(book.getPublishDate()));
        statement.setNString(6, book.getGenres());
        statement.setInt(7, book.getPageCount());
        statement.setNString(8, book.getISBN());
        statement.setNString(9, book.getDescription());
        statement.setInt(10, book.getTotalAmount());
        statement.setInt(11, book.getAvailableAmount());
        statement.setLong(12, book.getId());
    }

    @Override
    public void addBorrowRecord(PreparedStatement statement, BorrowRecord borrowRecord) throws SQLException {
        statement.setLong(1, borrowRecord.getUserId());
        statement.setDate(2, Date.valueOf(borrowRecord.getBorrowDate()));
        statement.setDate(3, Date.valueOf(borrowRecord.getDueDate()));
        statement.setLong(4, borrowRecord.getBookId());
    }

    @Override
    public void updateBorrowRecordByAdmin(PreparedStatement statement, BorrowRecord borrowRecord) throws SQLException {
        statement.setInt(1, borrowRecord.getRecordStatus()
                                        .getBorrowRecordStatusId());
        statement.setNString(2, borrowRecord.getComment());
        statement.setLong(3, borrowRecord.getId());
    }

    @Override
    public void updateBorrowRecordByUser(PreparedStatement statement, BorrowRecord borrowRecord) throws SQLException {
        statement.setDate(1, Date.valueOf(borrowRecord.getReturnDate()));
        statement.setLong(2, borrowRecord.getId());
    }

    @Override
    public void addSearchParameters(PreparedStatement statement, String searchParameter) throws SQLException {
        statement.setString(1, searchParameter);
        statement.setString(2, searchParameter);
        statement.setString(3, searchParameter);
        statement.setString(4, searchParameter);
    }

    @Override
    public void addISBNToUpdate(PreparedStatement statement, String ISBN, long id) throws SQLException {
        statement.setNString(1, ISBN);
        statement.setLong(2, id);
    }

    @Override
    public void addISBNToAdd(PreparedStatement statement, String ISBN) throws SQLException {
        statement.setNString(1, ISBN);

    }

    @Override
    public void addEmailToAdd(PreparedStatement statement, String email) throws SQLException {
        statement.setNString(1, email);
    }

    @Override
    public void addUsernameToAdd(PreparedStatement statement, String username) throws SQLException {
        statement.setNString(1, username);
    }

    @Override
    public void updateBookAvailableAmount(PreparedStatement statement, long bookId, int availableAmount) throws SQLException {
        statement.setInt(1, availableAmount);
        statement.setLong(2, bookId);
    }

    @Override
    public void addPaginationParameters(PreparedStatement statement, long userId) throws SQLException {
        statement.setLong(1, userId);
    }

    @Override
    public void addRemindDate(PreparedStatement statement, LocalDate dueDate) throws SQLException {
        statement.setDate(1, Date.valueOf(dueDate));
    }

    @Override
    public void changeUserRole(PreparedStatement statement, long userId) throws SQLException {
        statement.setLong(1, userId);
    }
}
