package py.edu.ucsa.rest.api;

import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
public class WebConfig {

//	 @Bean
//	    public FilterRegistrationBean corsFilter() {
//	        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//	        registrationBean.setName("corsFilter");
//	        registrationBean.addUrlPatterns("/*");
//	        CORSFilter corsFilter = new CORSFilter();
//	        registrationBean.setFilter(corsFilter);
//	        registrationBean.setEnabled(Boolean.TRUE);
//	        registrationBean.setAsyncSupported(Boolean.TRUE);
//	        registrationBean.setOrder(1);
//	        return registrationBean;
//	    }
}
