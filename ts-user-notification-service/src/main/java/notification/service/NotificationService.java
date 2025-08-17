package notification.service;

import notification.entity.NotifyInfo;
import org.springframework.http.HttpHeaders;

/**
 * @author Wenvi
 * @date 2017/6/15
 */
public interface NotificationService {

    /**
     * preserve success with notify info
     *
     * @param info notify info
     * @param headers headers
     * @return boolean
     */
    boolean preserveSuccess(NotifyInfo info, HttpHeaders headers);

    /**S
     * order create success with notify info
     *
     * @param info notify info
     * @param headers headers
     * @return boolean
     */
    boolean orderCreateSuccess(NotifyInfo info, HttpHeaders headers);

    /**
     * order changed success with notify info
     *
     * @param info notify info
     * @param headers headers
     * @return boolean
     */
    boolean orderChangedSuccess(NotifyInfo info, HttpHeaders headers);

    /**
     * order cancel success with notify info
     *
     * @param info notify info
     * @param headers headers
     * @return boolean
     */
    boolean orderCancelSuccess(NotifyInfo info, HttpHeaders headers);
}
