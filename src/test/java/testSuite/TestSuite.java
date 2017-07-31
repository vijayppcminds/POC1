package testSuite;
import static org.junit.Assert.*;

import org.junit.Test;

import com.assignment.dto.RequestBean;
import com.assignment.util.JsonParserUtil;

public class TestSuite {

	@Test
	   public void testPass1() {
		RequestBean bean = new RequestBean();
		bean.setFilter("censoring");
		bean.setLevel("Censored");
	      assertEquals("error in application", JsonParserUtil.parseJSON(bean, "Censored") , JsonParserUtil.parseJSON(bean, "Censored"));	    
	   }
	@Test
	public void testPass2() {
		RequestBean bean = new RequestBean();
		bean.setFilter("censoring");
		bean.setLevel("Uncensored");
	      assertEquals("error in application", JsonParserUtil.parseJSON(bean, "Uncensored"), JsonParserUtil.parseJSON(bean, "Uncensored"));
	     }
	
	@Test
	public void testPass3() {
		RequestBean bean = new RequestBean();
		bean.setFilter("censoring");
		bean.setLevel("-");	      
	      assertEquals("error in application",  JsonParserUtil.parseJSON(bean, "-"),JsonParserUtil.parseJSON(bean, "-"));
	   }
	
	  @Test
	   public void testParamFail() {
		  RequestBean bean = new RequestBean();
			bean.setFilter("censoring");
			bean.setLevel("");	  
			 assertNotEquals("error in request params",  JsonParserUtil.parseJSON(bean, ""),JsonParserUtil.parseJSON(bean, ""));
	   }
	  
	  @Test
	   public void testParamFail2() {
		  RequestBean bean = new RequestBean();
			bean.setFilter("censoring");
			bean.setLevel("abc");	  
			 assertNotEquals("error in request params",  JsonParserUtil.parseJSON(bean, "abc"),JsonParserUtil.parseJSON(bean, "abc"));
	   }
	  
	  @Test
	   public void testParamFail3() {
		  RequestBean bean = new RequestBean();
			bean.setFilter("");
			bean.setLevel("");	  
			 assertNotEquals("error in request params",  JsonParserUtil.parseJSON(bean, ""),JsonParserUtil.parseJSON(bean, ""));
	   }
	  
	  @Test
	   public void testParamFail4() {
		  RequestBean bean = new RequestBean();
			bean.setFilter("");
			bean.setLevel("-");	  
			 assertNotEquals("error in request params",  JsonParserUtil.parseJSON(bean, "-"),JsonParserUtil.parseJSON(bean, "-"));
	   }
	
}
