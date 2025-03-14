package dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class response_transaction_DTO {
	@Data
    @AllArgsConstructor
    public static class TransactionStatusResponse {
        private String status;
        private String message;
        private Long userId;

    }
}
