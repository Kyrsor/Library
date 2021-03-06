package by.itechart.library.controller.command.impl;

import by.itechart.library.controller.command.Command;
import by.itechart.library.controller.command.ParameterName;
import by.itechart.library.controller.command.exception.CommandException;
import by.itechart.library.controller.util.ControllerUtilFactory;
import by.itechart.library.controller.util.api.ControllerValueChecker;
import by.itechart.library.controller.util.api.PathCreator;
import by.itechart.library.entity.BorrowRecord;
import by.itechart.library.entity.User;
import by.itechart.library.service.ServiceFactory;
import by.itechart.library.service.api.UserService;
import by.itechart.library.service.exception.ServiceException;
import lombok.extern.log4j.Log4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;

@Log4j
public class EditBorrowRecordByUserCommand implements Command {
    private ControllerUtilFactory utilFactory = ControllerUtilFactory.getInstance();
    private ControllerValueChecker valueChecker = utilFactory.getControllerValueChecker();
    private PathCreator pathCreator = utilFactory.getPathCreator();
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private UserService userService = serviceFactory.getUserService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();

        String path;

        long borrowRecordId = Long.parseLong(request.getParameter(ParameterName.BORROW_RECORD_ID));
        if(request.getParameter(ParameterName.RETURN_DATE)==null){
            throw new CommandException("Return date cant be empty");
        }
        LocalDate returnDate = LocalDate.parse(request.getParameter(ParameterName.RETURN_DATE));

        BorrowRecord borrowRecord = new BorrowRecord();
        borrowRecord.setId(borrowRecordId);
        borrowRecord.setReturnDate(returnDate);

        User user = (User) session.getAttribute(ParameterName.USER);
        int role = user.getRole().getRoleId();
        try {
            if (valueChecker.isUser(role)) {
                userService.updateBorrowRecord(borrowRecord);
                log.info("updating borrow record by user");
                path = pathCreator.getBorrowRecordPageUser(request.getContextPath());
            } else {
                path = pathCreator.getNoAccess();
            }
        } catch (ServiceException e) {
            log.error(e);
            throw new CommandException(e);
        }
        return path;
    }
}
