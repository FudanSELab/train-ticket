package preserve.entity;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RebookInfo {
    private String oldOrderId;
    private String tripId;
    private String seatType;
    private String date;
}
