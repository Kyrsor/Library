package by.itechart.library.service.impl;

import by.itechart.library.dao.DAOFactory;
import by.itechart.library.dao.api.BookDAO;
import by.itechart.library.dao.api.BorrowRecordDAO;
import by.itechart.library.dao.api.UserDAO;
import by.itechart.library.dao.exception.DAOException;
import by.itechart.library.entity.Book;
import by.itechart.library.entity.BorrowRecord;
import by.itechart.library.entity.BorrowRecordStatus;
import by.itechart.library.entity.User;
import by.itechart.library.service.UtilFactory;
import by.itechart.library.service.api.AdminService;
import by.itechart.library.service.dto.BorrowRecordDto;
import by.itechart.library.service.exception.InvalidValuesException;
import by.itechart.library.service.exception.ServiceException;
import by.itechart.library.service.exception.ValidatorException;
import by.itechart.library.service.util.BookValidator;
import by.itechart.library.service.util.BorrowRecordValidator;
import lombok.extern.log4j.Log4j;

import java.util.List;

@Log4j

public class AdminServiceImpl implements AdminService {

    private DAOFactory daoFactory = DAOFactory.getInstance();
    private BookDAO bookDAO = daoFactory.getBookDAO();
    private BorrowRecordDAO borrowRecordDAO = daoFactory.getBorrowRecordDAO();
    private UserDAO userDAO = daoFactory.getUserDAO();
    private UtilFactory utilFactory = UtilFactory.getInstance();
    private BookValidator bookValidator = utilFactory.getBookValidator();
    private BorrowRecordValidator borrowRecordValidator = utilFactory.getBorrowRecordValidator();

    @Override
    public void addBook(Book book) throws ServiceException {
        try {
            bookValidator.validateAdd(book);
            bookDAO.addBook(book);
        } catch (DAOException e) {
            log.error(e);
            throw new ServiceException(e);
        } catch(ValidatorException e){
            throw new InvalidValuesException(e);
        }
    }

    @Override
    public void updateBook(Book book) throws ServiceException {
        try {
            bookValidator.validateUpdate(book);
            bookDAO.updateBook(book);
        } catch (DAOException e) {
            log.error(e);
            throw new ServiceException(e);
        } catch(ValidatorException e){
            throw new InvalidValuesException(e);
        }
    }


    @Override
    public void changeBookDeletedStatus(long bookId) throws ServiceException {
        try {
            bookDAO.changeDeletedStatus(bookId);
        } catch (DAOException e) {
            log.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void changeUserDeletedStatus(long userId) throws ServiceException {
        try {
            userDAO.changeDeletedStatus(userId);
        } catch (DAOException e) {
            log.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<BorrowRecordDto> getAllBorrowRecords() throws ServiceException {
        List<BorrowRecordDto> borrowRecordDtos;
        try {
            borrowRecordDtos = borrowRecordDAO.getAllNew();
        } catch (DAOException e) {
            log.error(e);
            throw new ServiceException(e);
        }
        return borrowRecordDtos;
    }
    @Override
    public void updateBorrowRecord(BorrowRecord borrowRecord) throws ServiceException {
        long bookId = borrowRecord.getBookId();
        BorrowRecordStatus borrowRecordStatus = borrowRecord.getRecordStatus();
        try {
            if (borrowRecordValidator.validateStatus(borrowRecordStatus)) {
                bookDAO.returnBook(bookId);
            }
            borrowRecordValidator.validateUpdateByAdmin(borrowRecord);
            borrowRecordDAO.updateBorrowRecordByAdmin(borrowRecord);
        } catch (DAOException | ValidatorException e) {
            log.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<User> getAllUsers() throws ServiceException {
        List<User> users;
        try {
            users = userDAO.getAll();
        } catch (DAOException e) {
            log.error(e);
            throw new ServiceException(e);
        }
        return users;
    }

    @Override
    public int getNumberOfBorrowRecordRows() throws ServiceException {
        int numberOfRows = 0;
        try {
            numberOfRows = borrowRecordDAO.getNumberOfRowsByAdmin();
        } catch (DAOException e) {
            log.error(e);
            throw new ServiceException(e);
        }
        return numberOfRows;
    }

    @Override
    public int getNumberOfUserRows() throws ServiceException {
        int numberOfRows = 0;
        try {
            numberOfRows = userDAO.getNumberOfRows();
        } catch (DAOException e) {
            log.error(e);
            throw new ServiceException(e);
        }
        return numberOfRows;
    }

    @Override
    public void changeUserRole(long userId) throws ServiceException {
        try {
            userDAO.changeRole(userId);
        } catch (DAOException e) {
            log.error(e);
            throw new ServiceException(e);
        }
    }
}
