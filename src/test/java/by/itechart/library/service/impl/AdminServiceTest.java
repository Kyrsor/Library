package by.itechart.library.service.impl;

import by.itechart.library.dao.api.BookDAO;
import by.itechart.library.dao.api.BorrowRecordDAO;
import by.itechart.library.dao.api.UserDAO;
import by.itechart.library.dao.exception.DAOException;
import by.itechart.library.entity.Book;
import by.itechart.library.entity.BorrowRecord;
import by.itechart.library.entity.BorrowRecordStatus;
import by.itechart.library.entity.User;
import by.itechart.library.service.exception.ServiceException;
import by.itechart.library.service.exception.ValidatorException;
import by.itechart.library.service.util.BookValidator;
import by.itechart.library.service.util.BorrowRecordValidator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class AdminServiceTest {

    @InjectMocks
    AdminServiceImpl adminService;

    @Mock
    BookDAO bookDAO;

    @Mock
    UserDAO userDAO;

    @Mock
    BorrowRecordDAO borrowRecordDAO;

    @Mock
    BookValidator bookValidator;

    @Mock
    BorrowRecordValidator borrowRecordValidator;

    @Mock
    Book book;

    BorrowRecord borrowRecord = new BorrowRecord();

    @Before
    public void init() {
        initMocks(this);
        borrowRecord.setRecordStatus(BorrowRecordStatus.RETURNED);
        borrowRecord.setUserId(1);
    }

    @Test(expected = ServiceException.class)
    public void addBook_ValidatorException() throws ValidatorException, ServiceException, DAOException {
        Mockito.doThrow(ValidatorException.class)
               .when(bookValidator)
               .validateAdd(Mockito.any());

        adminService.addBook(book);

    }
    @Test(expected = ServiceException.class)
    public void addBook_DAOException() throws ValidatorException, ServiceException, DAOException {
        Mockito.doReturn(true)
               .when(bookValidator)
               .validateAdd(Mockito.any());
        Mockito.doThrow(DAOException.class)
               .when(bookDAO)
               .addBook(book);
        adminService.addBook(book);

    }

    @Test
    public void addBook_validParams() throws ValidatorException, ServiceException {
        when(bookValidator.validateAdd(Mockito.any())).thenReturn(true);
        adminService.addBook(Mockito.any());

    }


    @Test(expected = ServiceException.class)
    public void updateBook_ValidatorException() throws ValidatorException, ServiceException {
        Mockito.doThrow(ValidatorException.class)
               .when(bookValidator)
               .validateUpdate(Mockito.any());
        adminService.updateBook(Mockito.any());
    }

    @Test(expected = ServiceException.class)
    public void updateBook_DAOException() throws ValidatorException, ServiceException, DAOException {
        Mockito.doReturn(true)
               .when(bookValidator)
               .validateUpdate(Mockito.any());
        Mockito.doThrow(DAOException.class).when(bookDAO).updateBook(book);
        adminService.updateBook(book);
    }


    @Test
    public void updateBook_validParams() throws ValidatorException, ServiceException {
        when(bookValidator.validateUpdate(Mockito.any())).thenReturn(true);
        adminService.updateBook(Mockito.any());
    }

    @Test(expected = ServiceException.class)
    public void changeBookDeletedStatus_DAOException() throws DAOException, ServiceException {
        Mockito.doThrow(DAOException.class)
               .when(bookDAO)
               .changeDeletedStatus(1);
        adminService.changeBookDeletedStatus(1);
    }

    @Test
    public void changeBookDeletedStatus_validParams() throws ServiceException, DAOException {
        Mockito.doNothing()
               .when(bookDAO)
               .changeDeletedStatus(Mockito.anyInt());
        adminService.changeBookDeletedStatus(Mockito.anyInt());
    }

    @Test(expected = ServiceException.class)
    public void changeUserDeletedStatus_DAOException() throws DAOException, ServiceException {
        Mockito.doThrow(DAOException.class)
               .when(userDAO)
               .changeDeletedStatus(1);
        adminService.changeUserDeletedStatus(1);
    }

    @Test
    public void changeUserDeletedStatus_validParams() throws ServiceException, DAOException {
        Mockito.doNothing()
               .when(userDAO)
               .changeDeletedStatus(Mockito.anyInt());
        adminService.changeUserDeletedStatus(Mockito.anyInt());
    }


    @Test
    public void getAllBorrowRecordsNew_validParams() throws DAOException, ServiceException {
        when(borrowRecordDAO.getAll()).thenReturn(Collections.emptyList());
        adminService.getAllBorrowRecords();
    }


    @Test
    public void getAllBorrowRecords_validParams() throws DAOException, ServiceException {
        when(borrowRecordDAO.getAll()).thenReturn(Collections.emptyList());
        adminService.getAllBorrowRecords();
    }


    @Test(expected = ServiceException.class)
    public void updateBorrowRecord_ValidatorException() throws ValidatorException, ServiceException {
        long bookId = borrowRecord.getBookId();
        BorrowRecordStatus borrowRecordStatus = borrowRecord.getRecordStatus();
        int borrowRecordStatusId = borrowRecordStatus.getBorrowRecordStatusId();
        Mockito.doThrow(ValidatorException.class)
               .when(borrowRecordValidator)
               .validateStatus(borrowRecordStatus);
        adminService.updateBorrowRecord(borrowRecord);
    }


    @Test
    public void updateBorrowRecord_validParams() throws DAOException, ValidatorException, ServiceException {

        long bookId = borrowRecord.getBookId();
        BorrowRecordStatus borrowRecordStatus = borrowRecord.getRecordStatus();
        int borrowRecordStatusId = borrowRecordStatus.getBorrowRecordStatusId();
        Mockito.doReturn(true)
               .when(borrowRecordValidator)
               .validateStatus(borrowRecordStatus);
        Mockito.doNothing()
               .when(bookDAO)
               .returnBook(bookId);
        adminService.updateBorrowRecord(borrowRecord);
    }


    @Test
    public void updateBorrowRecord_DAOException() throws ValidatorException, ServiceException, DAOException {
        long bookId = borrowRecord.getBookId();
        BorrowRecordStatus borrowRecordStatus = borrowRecord.getRecordStatus();
        int borrowRecordStatusId = borrowRecordStatus.getBorrowRecordStatusId();
        Mockito.doThrow(DAOException.class)
               .when(bookDAO)
               .returnBook(Mockito.anyInt());
        adminService.updateBorrowRecord(borrowRecord);
    }

    @Test(expected = ServiceException.class)
    public void getAllUsers_DAOException() throws DAOException, ServiceException {
        Mockito.doThrow(DAOException.class)
               .when(userDAO)
               .getAll();
        adminService.getAllUsers();

    }

    @Test
    public void getAllUsers_validParams() throws DAOException, ServiceException {
        List<User> users = new ArrayList<>();
        Mockito.doReturn(users)
               .when(userDAO)
               .getAll();
        List<User> result = adminService.getAllUsers();

        assertEquals(result, users);
    }


    @Test(expected = ServiceException.class)
    public void getNumberOfBorrowRecordRows_DAOException() throws DAOException, ServiceException {
        Mockito.doThrow(DAOException.class)
               .when(borrowRecordDAO)
               .getNumberOfRowsByAdmin();
        adminService.getNumberOfBorrowRecordRows();

    }

    @Test
    public void getNumberOfBorrowRecordRows_validParams() throws DAOException, ServiceException {
        int numberOfRows = 0;
        Mockito.doReturn(numberOfRows)
               .when(borrowRecordDAO)
               .getNumberOfRowsByAdmin();
        int number = adminService.getNumberOfBorrowRecordRows();
        assertEquals(numberOfRows, number);

    }

    @Test(expected = ServiceException.class)
    public void getNumberOfUserRows_DAOException() throws DAOException, ServiceException {
        Mockito.doThrow(DAOException.class)
               .when(userDAO)
               .getNumberOfRows();
        adminService.getNumberOfUserRows();

    }

    @Test
    public void getNumberOfUserRows_validParams() throws DAOException, ServiceException {
        int numberOfRows = 0;
        Mockito.doReturn(numberOfRows)
               .when(userDAO)
               .getNumberOfRows();

        int number = adminService.getNumberOfUserRows();
        assertEquals(numberOfRows, number);

    }
}