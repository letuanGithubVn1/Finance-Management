//package mainApplication;
//
//import org.springframework.beans.factory.config.BeanPostProcessor;
//import org.springframework.stereotype.Component;
//
//import com.google.api.client.util.Value;
//
//import jakarta.annotation.PostConstruct;
//
//@Component
//public class PackageScannerLogger implements BeanPostProcessor {
//
//	@Value("${DB_URL}")
//    private String dbUrl;
//	
//	 @PostConstruct
//	    public void logScannedPackages() {
//	        System.out.println("📦 Spring Boot đang scan các package...");
//	        System.out.println("✅ Đã scan: budget-management, expense-management, savings-management, core");
//	        System.out.println("DB URL: " + dbUrl);
//	        System.out.println("System.getenv(\"DB_URL\"): " + System.getenv("DB_URL"));
//	        System.out.println("System.getenv(\"DB_PASSWORD\"): " + System.getenv("DB_PASSWORD"));
//	        System.out.println("System.getenv(\"DB_USERNAME\"): " + System.getenv("DB_USERNAME"));
//
//	  }
//	 
//
//}