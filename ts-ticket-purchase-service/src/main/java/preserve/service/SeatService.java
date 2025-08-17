package preserve.service;

import edu.fudan.common.entity.Seat;
import edu.fudan.common.util.Response;
import org.springframework.http.HttpHeaders;

/**
 * Seat service interface migrated from ts-seat-service.
 */
public interface SeatService {

    Response distributeSeat(Seat seatRequest, HttpHeaders headers);

    Response getLeftTicketOfInterval(Seat seatRequest, HttpHeaders headers);
}
