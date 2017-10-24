package demoJavaRobot;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;
import java.awt.image.WritableRaster;

public class Program {
	/** The robot **/
	private Robot robot;
	/** Map **/
	public Rectangle rect;
	
	/**
	 * Used to type a string on keyboard
	 * @param robot
	 * @param word
	 */
	public void sendKeys(Robot robot, String word) {
	    for (char c : word.toCharArray()) {
	        int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
	        if (KeyEvent.CHAR_UNDEFINED == keyCode) {
	            throw new RuntimeException(
	                "Key code not found for character '" + c + "'");
	        }
	        robot.keyPress(keyCode);
	        robot.delay(100);
	        robot.keyRelease(keyCode);
	        robot.delay(100);
	    }
	    robot.keyPress(KeyEvent.VK_ENTER);
	    robot.keyRelease(KeyEvent.VK_ENTER);
	}
	
	/**
	 * Starting Robot
	 */
	public void startRobot() {
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	/** Testing blue color of portal **/
	public void getColor() {
		Rectangle captureSize = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
	    BufferedImage bufferedImage = robot.createScreenCapture(captureSize);
	    int color = bufferedImage.getRGB(1328, 215);
	    int blue = color & 0xff;
	    int green = (color & 0xff00) >> 8;
	    int red = (color & 0xff0000) >> 16;
	    System.out.println(color); //RGB color (-15260171)
	    System.out.println(blue); //Blue (245)
	    System.out.println(green); //Green (37)
	    System.out.println(red); //Red (23)
	}
	
	/**
	 * Start ROTMG app
	 */
	public void startROTMG() {
		robot.mouseMove(1300, 11);
		Shortcuts.leftClick(robot);
		robot.delay(1000);
		this.sendKeys(robot, "terminal");
		robot.delay(1000);
		this.sendKeys(robot, "open /Applications/ROTMG/RotMG.app");
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		Shortcuts.quitTerminal(robot);
		robot.mouseMove(884, 671);
		robot.delay(20000);
		Shortcuts.leftClick(robot);
		Shortcuts.leftClick(robot);
		robot.delay(5000);
	}
	
	public void getIntoRealm() {
		robot.keyPress(KeyEvent.VK_W);
		robot.delay(9000);
		robot.keyRelease(KeyEvent.VK_W);
		while(true) {
			BufferedImage img = robot.createScreenCapture(rect);
			WritableRaster r=img.getRaster();
			DataBuffer db=r.getDataBuffer();
			DataBufferInt dbi=(DataBufferInt)db;
			int[] data = dbi.getData();            		

			for (int x_scale = 0; x_scale < rect.width; x_scale += 10) {
				for(int y_scale = 0; y_scale < rect.height; y_scale += 10) {
					int rgb = data[x_scale + rect.width * y_scale];
					if (rgb == -15260171) {
						robot.mouseMove(rect.x + x_scale, rect.y + y_scale);
					}
				}
			}
		}
		

	}
}
