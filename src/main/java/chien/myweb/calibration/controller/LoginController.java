package chien.myweb.calibration.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import chien.myweb.calibration.enity.PersonInfo;
import chien.myweb.calibration.service.IdentityService;
import chien.myweb.calibration.service.LoginService;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSession;


@RestController
@RequestMapping("/calibration")
public class LoginController implements HttpSessionAttributeListener{
	
	@Autowired
	private LoginService loginService;	
	@Autowired
	private IdentityService identityService;
	//@Autowired
	//private EmailService emailService;
	
	@PostMapping("/login")
	public void login(@RequestBody PersonInfo personInfo,  HttpServletResponse response) throws ServletException {
		
		String account = personInfo.getAccount();
		String password = personInfo.getPassword();

		System.out.println("account: " + account);
		System.out.println("password: " + password);
		/*if("login".equals(action) && account.trim().length()>0 && password.trim().length()>0){
			
			account = account.trim();
			password = password.trim();
			
			if(userMap.get(account) != null && userMap.get(account).equals(password)){
				String pw = userMap.get(account);
				System.out.println("登入成功!!!!密碼: " + pw);
				
				PersonInfo personInfo = new PersonInfo();
				personInfo.setAccount(account.trim().toLowerCase());
				personInfo.setIp(request.getRemoteAddr());
				personInfo.setLoginDate(new Date());
				
				session.setAttribute("personInfo", personInfo);
				
				response.sendRedirect(response.encodeRedirectURL(request.getRequestURI()));
		
			}
			else{
				System.out.println("=====找不到帳戶名稱或密碼錯誤=====");
				
				response.sendRedirect(response.encodeRedirectURL(request.getRequestURI()));
			}
			
			
			return;
		}
		else if("logout".equals(action)){
			
			session.removeAttribute("personInfo");
			
			response.sendRedirect(response.encodeRedirectURL(request.getRequestURI()));
			return;
		}*/
		
	}
	
	@GetMapping("/login/getIdentity")
    public ResponseEntity<ServletOutputStream> showIdentity(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		BufferedImage bi = identityService.generateImage(request);
		ServletOutputStream outStream = response.getOutputStream();
		
		ImageIO.write(bi, "JPEG", outStream);//使用ImageIO寫入圖片 (bi畫布, jpeg圖片格式, 透過outStream物件)
		outStream.flush();//刷新此輸出流並強制將所有緩衝的輸出字節被寫出(將結果呈現)
		outStream.close();

        return new ResponseEntity<ServletOutputStream>(outStream, HttpStatus.OK);
    }
	
	/*@PostMapping("/sendMail")
	@ResponseBody
    public ResponseEntity<String> sendMail(@RequestBody Map<String, String> requestForm, HttpServletRequest request) {
		
    	System.out.println("您輸入的信箱: " + requestForm.get("email"));
    	System.out.println("您輸入的驗證碼: " + requestForm.get("captcha"));
    	
    	String mailStatus = ""; // 初始設定，存放寄信成功或失敗的訊息
    	String email = requestForm.get("email");
    	String captcha = requestForm.get("captcha");
    	String ans = (String)request.getSession(true).getAttribute("ans"); // 從session裡取得驗證碼答案(一開始須將驗證碼存入session)
    	
    	if( email != null && email.trim().length()>0 && // 判斷是否為空的信箱
    		captcha != null && captcha.trim().length()>0) { // 判斷是否為空的驗證碼
    		 
    		if ( userService.validateEmail(email) == false ){  // 判斷信箱格式
				System.out.println("不符合e-mail格式。");
				return ResponseEntity.ok().body("不符合e-mail格式。");
			}
    		
    		if ( userService.validateAccount_number(captcha) == false ) { // 判斷驗證碼格式
    			System.out.println("驗證碼格式須為英文及數字。");
    			return ResponseEntity.ok().body("驗證碼格式須為英文及數字。");
    		}
    		
    		if ( ans.equals(captcha) ) { // 判斷輸入的驗證碼，與系統產生的驗證碼是否一致
    			mailStatus = emailService.sendSimpleMail(email); // 寄送郵件取得密碼
    			System.out.println(mailStatus);
            	return ResponseEntity.ok().body(mailStatus);
    		} else {
    			System.out.println("驗證碼輸入不一致。");
    			return ResponseEntity.ok().body("驗證碼輸入不一致。");  
    		}
        	
    	}
    	else { 
    		System.out.println("您沒有輸入，請再輸入一次。");
    		return ResponseEntity.ok().body("您沒有輸入，請再輸入一次。");
    	}
		
    }*/
	    
}
