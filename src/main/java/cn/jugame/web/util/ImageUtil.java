package cn.jugame.web.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtil {

	/**
	 * 二值化图片
	 * 
	 * @param fromUrl
	 * @param toUrl
	 * @throws IOException
	 */
	public void binaryImage(String fromUrl, String toUrl) throws IOException {
		File file = new File(fromUrl);
		BufferedImage image = ImageIO.read(file);

		int width = image.getWidth();
		int height = image.getHeight();

		BufferedImage grayImage = new BufferedImage(width, height,
				BufferedImage.TYPE_BYTE_BINARY);// 重点，技巧在这个参数BufferedImage.TYPE_BYTE_BINARY
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int rgb = image.getRGB(i, j);
				grayImage.setRGB(i, j, rgb);
			}
		}

		File newFile = new File(toUrl);
		ImageIO.write(grayImage, "jpg", newFile);
	}

	/**
	 * 灰度图片
	 * 
	 * @param fromUrl
	 * @param toUrl
	 * @throws IOException
	 */
	public static void grayImage(String fromUrl, String toUrl)
			throws IOException {
		File file = new File(fromUrl);
		BufferedImage image = ImageIO.read(file);

		int width = image.getWidth();
		int height = image.getHeight();

		BufferedImage grayImage = new BufferedImage(width, height,
				BufferedImage.TYPE_BYTE_GRAY);// 重点，技巧在这个参数BufferedImage.TYPE_BYTE_GRAY
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int rgb = image.getRGB(i, j);
				grayImage.setRGB(i, j, rgb);
			}
		}

		File newFile = new File(toUrl);
		ImageIO.write(grayImage, "jpg", newFile);
	}

	public static void main(String[] args) throws IOException {
		ImageUtil demo = new ImageUtil();
		demo.grayImage("H:/06数据库/正式/New Folder/tt.jpg",
				"H:/06数据库/正式/New Folder/tt2.jpg");
	}

}