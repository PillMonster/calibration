package chien.myweb.calibration.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import chien.myweb.calibration.dao.PersonDao;


@Service
public class EmailServiceImpl implements EmailService{
	
	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private PersonService personService;
	 
    @Value("${spring.mail.username}") private String sender; // 取得寄出的信箱
    
    // Method 1
    // To send a simple email
    public String sendSimpleMail(String email){
    	//String getPassword = "";
    	try {
    		String password = personService.findPasswordByEmail(email);	
			System.out.println("您的密碼: " + password);	
            
	        // Try block to check for exceptions
	        try {
	        	// Creating a simple mail message
		        SimpleMailMessage mailMessage = new SimpleMailMessage();
	            // Setting up necessary details
	            mailMessage.setFrom(sender); // 從哪裡寄出
	            mailMessage.setTo(email); // 寄到哪裡
	            mailMessage.setSubject("您的會員密碼"); // 信件標題
	            mailMessage.setText("您的密碼為: " + password); // 信件內容

	        	// Sending the mail
	        	javaMailSender.send(mailMessage);
	        	
	        	return "已將密碼寄到您的信箱。";
        	
	        // Catch block to handle the exceptions
	        } catch (MailSendException e) {
	        	return "郵件伺服器發生問題，無法寄出";
	        } catch (Exception e) {	
                 return "出現其他錯誤，無法寄出";
            }
	        
	    } catch (EmptyResultDataAccessException e) {
			return "您的信箱輸入錯誤或無此信箱，請再輸入一次。";
		}

    }
 
    // Method 2
    // To send an email with attachment
    /*public String sendMailWithAttachment(EmailDetails details){
        // Creating a mime message
        MimeMessage mimeMessage
            = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
 
        try {
 
            // Setting multipart as true for attachments to
            // be send
            mimeMessageHelper
                = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(details.getRecipient());
            mimeMessageHelper.setText(details.getMsgBody());
            mimeMessageHelper.setSubject(
                details.getSubject());
 
            // Adding the attachment
            FileSystemResource file
                = new FileSystemResource(
                    new File(details.getAttachment()));
 
            mimeMessageHelper.addAttachment(
                file.getFilename(), file);
 
            // Sending the mail
            javaMailSender.send(mimeMessage);
            return "Mail sent Successfully";
        }
 
        // Catch block to handle MessagingException
        catch (MessagingException e) {
 
            // Display message when exception occurred
            return "Error while sending mail!!!";
        }
    }*/

	
}
