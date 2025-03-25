package dto;

import java.math.BigDecimal;
import com.qlct.budgetManagement.entity.ExpenseAllocation;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class response_DTO {
	
	private static response_DTO instance;

    private response_DTO() {}

    // Phương thức để lấy đối tượng duy nhất 
    public static response_DTO getInstance() {
        if (instance == null) {
            instance = new response_DTO(); 
        }
        return instance;
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
//    @AllArgsConstructor
    public static class BudgetStatusResponse {
        private String status;
        private String message;
        private Long budgetId;
        
        public BudgetStatusResponse(String status, String message, Long budgetId) {
        	this.status = status;
            this.message = message;
            this.budgetId = budgetId;
        }
        
 
        
    }



}
