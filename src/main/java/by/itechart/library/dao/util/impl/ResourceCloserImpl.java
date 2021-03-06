package by.itechart.library.dao.util.impl;


import by.itechart.library.dao.util.api.ResourceCloser;
import lombok.extern.log4j.Log4j;

@Log4j
public class ResourceCloserImpl implements ResourceCloser {

    @Override
    public void close(AutoCloseable resource) {
        try {
            if (resource != null) {
                resource.close();
            }
        } catch (Exception e) {
            log.error(e);
        }
    }
}
