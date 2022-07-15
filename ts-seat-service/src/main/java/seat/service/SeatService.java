package seat.service;

import edu.fudan.common.util.Response;
import org.springframework.http.HttpHeaders;
import edu.fudan.common.entity.Seat;

/**
 * @author fdse
 */
public interface SeatService {

    Response distributeSeat(Seat seatRequest, HttpHeaders headers);
    Response getLeftTicketOfInterval(Seat seatRequest, HttpHeaders headers);
}
