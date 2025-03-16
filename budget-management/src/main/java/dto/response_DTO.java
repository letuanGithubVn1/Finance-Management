package dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.qlct.budgetManagement.entity.Budget;
import com.qlct.budgetManagement.entity.ExpenseAllocation;

import dto.request_DTO.ExpenseAllocationRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class response_DTO {
	@Data
	@AllArgsConstructor
	@NoArgsConstructor // Giúp spring-boot tự động map dữ liệu
	public static class BudgetResponse {
	    private Long b_id;
	    private Long userId;
	    private BigDecimal limitAmount;
	    private LocalDate startDate;
	    private LocalDate endDate;
	    private LocalDateTime created_at;
	    private List<ExpenseAllocationResponse> expenseAllocations;

	    public BudgetResponse(Budget budget, List<ExpenseAllocationRequest> allocations) {
	        this.b_id = budget.getId();
	        this.userId = budget.getUser().getId();
	        this.limitAmount = budget.getLimit_amount();
	        this.startDate = budget.getStart_date();
	        this.endDate = budget.getEnd_date();
	        this.created_at = budget.getCreated_at();
	        this.expenseAllocations = allocations.stream()
	                .map(exp -> new ExpenseAllocationResponse(exp.getCategoryId(), exp.getAllocatedAmount()))
	                .collect(Collectors.toList());
	    }	   
	  
	}

	@Data
	@AllArgsConstructor
	public static class ExpenseAllocationResponse {
	    private Long categoryId;
	    private BigDecimal allocatedAmount;

	    public ExpenseAllocationResponse(ExpenseAllocation allocation) {
	        this.categoryId = allocation.getCategory().getId();
	        this.allocatedAmount = allocation.getAllocatedAmount();
	    }

	}	
	
	@Data
    @AllArgsConstructor
    public static class BudgetStatusResponse {
        private String status;
        private String message;
        private Long budgetId;
        
//        public BudgetStatusResponse(String status, String message, Long budgetId) {
//        	this.status = status;
//        	this.message = message;
//        	this.budgetId = budgetId;
//        }
    }



}
