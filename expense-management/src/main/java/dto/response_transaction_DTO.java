package dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class response_transaction_DTO {
	
	private static response_transaction_DTO instance;

    private response_transaction_DTO() {}

    // Phương thức để lấy đối tượng duy nhất 
    public static response_transaction_DTO getInstance() {
        if (instance == null) {
            instance = new response_transaction_DTO(); 
        }
        return instance;
    }
    
	@Data
    @AllArgsConstructor
    public static class TransactionStatusResponse {
        private String status;
        private String message;
        private Long userId;

    }
}
