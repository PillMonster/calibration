package chien.myweb.calibration.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import chien.myweb.calibration.enity.PersonInfo;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSessionBindingEvent;

@Component
public class LoginService implements HttpSessionAttributeListener{
	
	Log log = LogFactory.getLog(this.getClass());
	
	HttpSession[] sessionArray = new HttpSession[2];

	Map<String, HttpSession> map = new HashMap<String, HttpSession>();

	@Override
	public void attributeAdded(HttpSessionBindingEvent event) {
		
		System.out.println("attributeAdded已被呼叫");
		
		String name = event.getName(); // 取得該屬性的物件名稱(personInfo)
		//System.out.println(event.getName());
		//System.out.println(event.getValue());
		if(name.equals("personInfo")) {
			
			PersonInfo personInfo = (PersonInfo)event.getValue(); // 取得personInfo物件(PersonInfo@422c0a04)
			
			System.out.println("登入帳號為:" + personInfo.getAccount());
			System.out.println("登入帳號的session ID為: " + event.getSession());
			
			
			// 下列if區塊是尋找map集合中的key值(account)，所對應的value(session物件)，判斷是否有有內容
			if(map.get(personInfo.getAccount()) != null) { 
				
				System.out.println("相同使用者帳號!! map集合中帳號對應的session ID為: " + map.get(personInfo.getAccount()));
				
				// 從map取得該account所對應的session物件
				// EX: session為: org.apache.catalina.session.StandardSessionFacade@7c8e51c7
				HttpSession session = map.get(personInfo.getAccount()); 	
				
				// 從session物件中，取得key(personInfo)對應的的value(personInfo物件)，並轉換成PersonInfo的類別
				PersonInfo oldPersonInfo = (PersonInfo)session.getAttribute("personInfo");
				
				System.out.println("帳號: " + oldPersonInfo.getAccount() + " 在" + oldPersonInfo.getIp() + "已經登入，該登入將被迫下線。");
				
				// 從session物件中，取得該key(personInfo)所對應的value(session物件)，並移除該session物件，亦即上一個時間點登入的相同帳號
				// 透過listener，移除的時候會呼叫attributeRemoved方法
				session.removeAttribute("personInfo");
				session.setAttribute("msg", "您的帳號已經在其他機器上登入，您被迫下線。");// 設定session，可作為前端接收訊息之用途
			}
			// 若map集合中找不到對應的使用者帳號，則執行以下敘述
			map.put(personInfo.getAccount(), event.getSession()); // 將登入的使用者帳號，存入map集合中[帳號,session id]
			System.out.println("帳號:" + personInfo.getAccount() + "在" + personInfo.getIp() + "登入。");
			
			map.forEach((key, value) -> {
				System.out.println("新增一組map，帳號:" + key + ", session ID:" + value);
			});
			
		}
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent event) {
		
		System.out.println("attributeRemoved已被呼叫");
		
		String name = event.getName(); // 取得該屬性的物件名稱(personInfo)
		
		if(name.equals("personInfo")) {
			PersonInfo personInfo = (PersonInfo)event.getValue(); // 取得personInfo物件(PersonInfo@422c0a04)
			map.remove(personInfo.getAccount()); // 從map集合中移除該key(account)所對應的value(session物件)
			System.out.println("帳號:" + personInfo.getAccount() + "註銷。");
		}
		
		if(map.isEmpty() == true) {
			System.out.println("map為空的");
		}
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent event) {
		
		//System.out.println("attributeReplaced已被呼叫");
		
		String name = event.getName(); // 取得該屬性的物件名稱(personInfo)
		
		if(name.equals("perosnInfo")) {
			
			PersonInfo oldPersonInfo = (PersonInfo)event.getValue(); // 取得personInfo物件(PersonInfo@422c0a04)
			map.remove(oldPersonInfo.getAccount());
			
			PersonInfo personInfo = (PersonInfo)event.getSession().getAttribute("personInfo");
			
			if(map.get(personInfo.getAccount()) != null) {
				
				HttpSession session = map.get(personInfo.getAccount());
				session.removeAttribute("personInfo");
				session.setAttribute("msg", "您的帳號已經在其他機器上登錄，您被迫下線~~");
			}
			map.put(personInfo.getAccount(), event.getSession());
			System.out.println("attributeReplaced: Called.");
		}
	}
}
