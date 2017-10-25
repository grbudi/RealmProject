package realm;

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
	 * Starting Robot
	 */
	public void startRobot() {
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	// Used to find color of portals 
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
	 * Start ROTMG application
	 */
	public void startROTMG() {
		robot.mouseMove(1300, 11);
		Shortcuts.leftClick(robot);
		robot.delay(1000);
		Shortcuts.sendKeys(robot, "terminal");
		robot.delay(1000);
		Shortcuts.sendKeys(robot, "open /Applications/ROTMG/RotMG.app");
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		Shortcuts.quitProgram(robot);
		robot.mouseMove(884, 671);
		robot.delay(20000);
		Shortcuts.leftClick(robot);
		Shortcuts.leftClick(robot);
		robot.delay(5000);
	}
	
	public void setMapCoordinates() {
		rect = new Rectangle(1087, 123, 191, 191);
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
						if(rect.x + x_scale < 97) {
							robot.keyPress(KeyEvent.VK_A);
							robot.delay(1000);
							robot.keyRelease(KeyEvent.VK_A);
							x_scale = 0;
						}
						if(rect.x + x_scale > 97) {
							robot.keyPress(KeyEvent.VK_D);
							robot.delay(1000);
							robot.keyRelease(KeyEvent.VK_D);
							x_scale = 0;
						}
						
					}
				}
			}
		}
		

	}
}
