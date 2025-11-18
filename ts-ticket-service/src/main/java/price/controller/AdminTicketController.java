package price.controller;

import edu.fudan.common.client.dto.ticket.PriceDto;
import edu.fudan.common.util.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import price.mapper.PriceMapper;
import price.service.PriceService;

import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequestMapping("/api/v1/ticket/admin")
public class AdminTicketController {
    @Autowired
    private PriceService priceService;
    @Autowired
    private PriceMapper priceMapper;

    @PostMapping("/prices")
    public ResponseEntity<Response<PriceDto>> addPrice(@RequestBody PriceDto priceDto, @RequestHeader HttpHeaders headers){
        log.info("[addPrice][Admin add price][id: {}]", priceDto.getId());
        Response<PriceDto> response = priceMapper.toDtoResponse(priceService.createPrice(priceMapper.toEntity(priceDto), headers));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/prices")
    public ResponseEntity<Response<PriceDto>> modifyPrice(@RequestBody PriceDto priceDto, @RequestHeader HttpHeaders headers){
        log.info("[modifyPrice][Admin modify price][id: {}]", priceDto.getId());
        Response<PriceDto> response = priceMapper.toDtoResponse(priceService.updatePrice(priceMapper.toEntity(priceDto), headers));
        return ok(response);
    }

    @DeleteMapping("/prices/{priceId}")
    public ResponseEntity<Response<PriceDto>> deletePrice(@PathVariable String priceId, @RequestHeader HttpHeaders headers){
        log.info("[deletePrice][Admin delete price][id: {}]", priceId);
        return ok(priceMapper.toDtoResponse(priceService.deletePrice(priceId, headers)));
    }
}
