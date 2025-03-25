//package mainApplication;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.beans.factory.config.BeanPostProcessor;
//import org.springframework.stereotype.Component;
//
//
//
//import jakarta.annotation.PostConstruct;
//
//@Component
//public class PackageScannerLogger implements BeanPostProcessor {
//
//	@Value("${spring.datasource.url}")
//    private String dbUrl;
//	
//	@Value("${spring.datasource.username}")
//	private String dbUsername;
//	
//	@Value("${spring.datasource.password}")
//	private String dbPassword;
//	
//	 @PostConstruct
//	    public void logScannedPackages() {
//	        System.out.println("📦 Spring Boot đang scan các package...");
//	        System.out.println("✅ Đã scan: budget-management, expense-management, savings-management, core");
//	       
//	        System.out.println("DB URL: " + dbUrl + "'");
//	        System.out.println("DB USERNAME: " + dbUsername + "'");
//	        System.out.println("DB PASSWORD: " + dbPassword + "'");
//	        
//	        System.out.println("System.getenv DB_URL: '" + System.getenv("DB_URL") + "'");
//	        System.out.println("System.getenv DB_USERNAME: '" + System.getenv("DB_USERNAME") + "'");
//	        System.out.println("System.getenv DB_PASSWORD: '" + System.getenv("DB_PASSWORD") + "'");
//
//
//	  }
//	 
//
//} //