package adrian;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bt.wifi.sesm.util.security.SesmEncrypt;

@Controller
@EnableAutoConfiguration
@ComponentScan("com.bt.wifi.sesm.util.security")
public class SampleController {

	@RequestMapping("/")
	@ResponseBody
	String home() {
		return "Hello World!";
	}

	@Autowired
	private SesmEncrypt sesmEncrypt;

	@RequestMapping("/f2eureg/register-check.php")
	String registerCheck(HttpServletRequest httpServletRequest) {
		String email = httpServletRequest.getParameter("email");
		System.err.println(email);
		String template = httpServletRequest.getParameter("t");
		System.err.println(template);
		if ("3".equals(template) || "2".equals(template))
			return "redirect:https://localhost:8443/freeToEndUserRegLogon?status=0&email=" + email + "&sessionValidation=" + getSessionValidation(httpServletRequest);

		return "redirect:https://localhost:8443/f2euRegistration?s=0&e=" + base64(email);
	}

	private String getSessionValidation(HttpServletRequest httpServletRequest) {
		return sesmEncrypt.encrypt(httpServletRequest.getRemoteAddr());
	}

	private String base64(String s) {
		return Base64.encodeBase64String(s.getBytes());
	}

	public static void main(String[] args) {
		SpringApplication.run(SampleController.class, args);
	}
}