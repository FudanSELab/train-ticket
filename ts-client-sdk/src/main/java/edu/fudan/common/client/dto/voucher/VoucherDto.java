package edu.fudan.common.client.dto.voucher;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VoucherDto {
    private Integer voucherId;
    private String orderId;
    private String travelDate;
    private String travelTime;
    private String contactName;
    private String trainNumber;
    private Integer seatClass;
    private String seatNumber;
    private String startStation;
    private String destStation;
    private Float price;
}
