package dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class request_transaction_DTO {
	
	private static request_transaction_DTO instance;

    private request_transaction_DTO() {}

    // Phương thức để lấy đối tượng duy nhất 
    public static request_transaction_DTO getInstance() {
        if (instance == null) {
            instance = new request_transaction_DTO(); 
        }
        return instance;
    }
	
	@Data
	public static class transactionRequest{
		private Long categoryId;
		private BigDecimal amount;
		private String paymentType;
		private LocalDateTime transactionDate;
		private String note;
	}
	
	
}
