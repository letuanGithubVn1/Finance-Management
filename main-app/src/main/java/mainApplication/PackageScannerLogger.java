package mainApplication;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class PackageScannerLogger implements BeanPostProcessor {

	 @PostConstruct
	    public void logScannedPackages() {
	        System.out.println("📦 Spring Boot đang scan các package...");
	        System.out.println("✅ Đã scan: budget-management, expense-management, savings-management, core");
	  }
}