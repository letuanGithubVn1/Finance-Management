package dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class request_transaction_DTO {
	
	@Data
	public static class transactionRequest{
		private Long categoryId;
		private BigDecimal amount;
		private String paymentType;
		private LocalDateTime transactionDate;
		private String note;
	}
	
	
}
