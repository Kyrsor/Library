package by.itechart.library.controller.command.impl;

import by.itechart.library.controller.command.Command;
import by.itechart.library.controller.command.ParameterName;
import by.itechart.library.controller.command.exception.CommandException;
import by.itechart.library.controller.util.ControllerUtilFactory;
import by.itechart.library.controller.util.api.ControllerValueChecker;
import by.itechart.library.controller.util.api.PathCreator;
import by.itechart.library.entity.Book;
import by.itechart.library.entity.BorrowRecord;
import by.itechart.library.service.ServiceFactory;
import by.itechart.library.service.api.CommonService;
import by.itechart.library.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ForwardEditBorrowRecordCommand implements Command {
    private ControllerUtilFactory utilFactory = ControllerUtilFactory.getInstance();
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private CommonService commonService = serviceFactory.getCommonService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        ControllerValueChecker valueChecker = utilFactory.getControllerValueChecker();
        PathCreator pathCreator = utilFactory.getPathCreator();
        HttpSession session= request.getSession();
        String path = pathCreator.getError();
        int borrowRecordId = Integer.parseInt(request.getParameter(ParameterName.BORROW_RECORD_ID));

        BorrowRecord borrowRecord;

        int role = (int) session.getAttribute(ParameterName.ROLE);
        try {
            if (valueChecker.isAnyUser(role)) {
                borrowRecord = commonService.getBorrowRecord(borrowRecordId);
                request.setAttribute(ParameterName.BORROW_RECORD, borrowRecord);
                path = pathCreator.getEditBook();
            }else {
                path = pathCreator.getError();
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return path;


    }
}
