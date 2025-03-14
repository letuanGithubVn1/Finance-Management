package dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class request_DTO {

	@Data
	public static class BudgetRequest {
	    private Long userId;
	    private Long budgetId;
	    private BigDecimal limitAmount;
	    private LocalDate startDate;
	    private LocalDate endDate;
	    private List<ExpenseAllocationRequest> expenseAllocations;
	}

	@Data
	public static class UpdateBudgetRequest {
	    private Long userId;
	    private BigDecimal limitAmount;
	    private LocalDate startDate;
	    private LocalDate endDate;
	    private LocalDateTime created_at;
	    private List<ExpenseAllocationRequest> expenseAllocations;
	}
	
	@Data
	public static class ExpenseAllocationRequest {
	    private Long categoryId;
	    private BigDecimal allocatedAmount;
	}

}
