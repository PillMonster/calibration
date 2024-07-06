package chien.myweb.calibration.service;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

@Component
public class IdentityService{
	
	public static final char [] CHARS = {'2', '3', '4', '5', '6', '7', '8', '9',
			 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M',
			 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
	
	public static Random random = new Random();
	
	public static String getRandomString(){
		StringBuffer buffer = new StringBuffer();
		for(int i=0; i<6; i++){
			buffer.append(CHARS[random.nextInt(CHARS.length)]);//將CHARS[index]裡面的值放入buffer物件內
		}
		return buffer.toString();
	}
	
	public static Color getRandomColor(){
		return new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
	}
	
	public static Color getReverseColor(Color c){//取得對角線的顏色，避免色差太接近
		return new Color(255-c.getRed(), 255-c.getGreen(), 255-c.getBlue());
	}

	public BufferedImage generateImage(HttpServletRequest request) {
		String randomString = getRandomString();//取得亂數字母
		
		//確認是否有給用戶session物件，如果有就繼續使用，如果沒有救生成session物件，給你使用
		//因HTTP第二大特性(無狀態)，所以用此方式保留
		request.getSession(true).setAttribute("ans",randomString);//記錄用戶端狀態
		System.out.println("random number2: " + randomString);
		
		int width = 125;//設定畫布長
		int height = 40;//設定畫布寬
		
		Color color = getRandomColor();//取得隨機顏色
		Color reverse = getReverseColor(color);//將顏色傳入，取得反向的顏色(避免顏色接近)
		
		//帶有緩衝區圖片，可以將一張圖片乘載於畫布中(bi物件)，此例圖片型態為rgb圖片
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = bi.createGraphics();//以Graphics2D形式，透過物件g(畫筆)在畫布上(bi)繪製
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));//設定文字樣式(要傳入Font物件)
		g.setColor(color);//設定畫布的顏色 
		g.fillRect(0, 0, width, height);//設定畫布起始位置 大小
		
		
		//=====產生6個亂數(含英文數字)=====
		//substring(int beginIndex, int endIndex), 
		//Ex:String Str = new String("This is text"); Str.substring(4, 10); -> return: is te
		g.setColor(reverse);//設定驗證碼的顏色
		for(int i=0; i<6; i++){
			g.drawString(randomString.substring(i,i+1), (width/8)*(i+1), random.nextInt(30-10)+15);
		}
		
		//=====繪製隨機干擾點=====
		//g.drawRect(長度起始位置,寬度起始位置,長度,寬度)
		for(int i=0; i<random.nextInt(200); i++){//產生200個小方格
			g.drawRect(random.nextInt(width), random.nextInt(height), random.nextInt(3)+1, random.nextInt(3)+1);
		}
		
		//=====繪製隨機干擾斜線=====
		for(int i=0; i<random.nextInt(100); i++){//產生100個斜線
			int x1 = random.nextInt(width);
			int x2 = random.nextInt(width);
			int x_start = Math.max(x1, x2);
			int x_end = Math.min(x1, x2);
			g.drawLine(x_start, 0, x_end, 100);
		}
		
		return bi;		
	}

}
