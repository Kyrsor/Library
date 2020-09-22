package by.itechart.library.controller.util.impl;

import by.itechart.library.controller.util.api.PathCreator;
import lombok.Getter;

@Getter
public class PathCreatorImpl implements PathCreator {
    private final static String ERROR = "error";
    private final static String SIGN_IN = "sign-in";
    private final static String USER_PAGE = "user-page";
    private final static String MAIN_PAGE = "main-page";
    private final static String EDIT_BOOK = "edit-book";
    private final static String EDIT_PROFILE = "edit-profile";
    @Override
    public String getError() {
        return ERROR;
    }

    @Override
    public String getSignIn() {
        return SIGN_IN;
    }

    @Override
    public String getMainPage() {
        return MAIN_PAGE;
    }

    @Override
    public String getBooksPage() {
        return null;
    }

    @Override
    public String getBookPage(String contextPath, long bookId) {
        return null;
    }

    @Override
    public String getUsersPage() {
        return null;
    }

    @Override
    public String getUserPage() {
        return USER_PAGE;
    }

    @Override
    public String getBorrowRecordsPage() {
        return null;
    }

    @Override
    public String getBorrowRecordPage(String contextPath, long borrowRecordId) {
        return null;
    }

    @Override
    public String getEditBook() {
        return EDIT_BOOK;
    }

    @Override
    public String getEditProfile() {
        return EDIT_PROFILE;
    }
}
