package preserve.controller;

import edu.fudan.common.entity.Seat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import preserve.service.SeatService;

import static org.springframework.http.ResponseEntity.ok;

/**
 * Seat endpoints migrated from ts-seat-service.
 */
@RestController
@RequestMapping("/api/v1/ticket-purchase/seat")
public class SeatController {

    @Autowired
    private SeatService seatService;

    private static final Logger LOGGER = LoggerFactory.getLogger(SeatController.class);

    @GetMapping("/welcome")
    public String home() {
        return "Welcome to [ Seat Service ] !";
    }

    /**
     * Assign seat.
     */
    @CrossOrigin(origins = "*")
    @PostMapping("/seats")
    public HttpEntity<?> create(@RequestBody Seat seatRequest, @RequestHeader HttpHeaders headers) {
        LOGGER.info("[distributeSeat][Create seat][TravelDate: {},TrainNumber: {},SeatType: {}]", seatRequest.getTravelDate(), seatRequest.getTrainNumber(), seatRequest.getSeatType());
        return ok(seatService.distributeSeat(seatRequest, headers));
    }

    /**
     * Get left tickets of interval.
     */
    @CrossOrigin(origins = "*")
    @PostMapping("/seats/left_tickets")
    public HttpEntity<?> getLeftTicketOfInterval(@RequestBody Seat seatRequest, @RequestHeader HttpHeaders headers) {
        LOGGER.info("[getLeftTicketOfInterval][TravelDate: {},TrainNumber: {},SeatType: {}]", seatRequest.getTravelDate(), seatRequest.getTrainNumber(), seatRequest.getSeatType());
        return ok(seatService.getLeftTicketOfInterval(seatRequest, headers));
    }
}
