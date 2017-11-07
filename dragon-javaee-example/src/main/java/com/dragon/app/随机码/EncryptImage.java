package com.dragon.app.随机码;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class EncryptImage {

	public static void main(String[] args) throws Exception {
		encrypt();
		decrypt();
	}

	public static void encrypt() throws Exception {
		try {
			File file = new File("E:\\u26.jpg");
			BufferedImage mBufferedImage = ImageIO.read(file);
			int width = mBufferedImage.getWidth();
			int height = mBufferedImage.getHeight();
			int n = Math.max(width, height);
			BufferedImage newBufferedImage = new BufferedImage(n, n, BufferedImage.TYPE_INT_RGB);
//			for (int k = 0; k < 3; ++k) {
//				if (k > 0) {
//					oldBufferedImage = newBufferedImage;
//					newBufferedImage = new BufferedImage(n, n, BufferedImage.TYPE_INT_RGB);
//				}
				for (int i = mBufferedImage.getMinX(); i < width; ++i) {
					for (int j = mBufferedImage.getMinY(); j < height; ++j) {
//						int x = i + j;
//						x -= x / n * n;
//						int y = i + 2 * j;
//						y -= y / n * n;
						
						int x = (i + j) % n;
						int y = (i + 2 * j) % n;
						newBufferedImage.setRGB(x, y, mBufferedImage.getRGB(i, j));
					}
				}
//			}

			ImageIO.write(newBufferedImage, "JPEG", new File("E:\\encrypt.jpg"));
		} catch (Exception e) {
		}
	}

	public static void decrypt() throws Exception {
		try {
			File file = new File("E:\\encrypt.jpg");
			BufferedImage mBufferedImage = ImageIO.read(file);
			int width = mBufferedImage.getWidth();
			int height = mBufferedImage.getHeight();
			int n = Math.max(width, height);
			BufferedImage newBufferedImage = new BufferedImage(n, n, BufferedImage.TYPE_INT_RGB);

//			for (int k = 0; k < 3; ++k) {
//				if (k > 0) {
//					oldBufferedImage = newBufferedImage;
//					newBufferedImage = new BufferedImage(n, n, BufferedImage.TYPE_INT_RGB);
//				}
				for (int i = mBufferedImage.getMinX(); i < width; ++i) {
					for (int j = mBufferedImage.getMinY(); j < height; ++j) {
						int x = 2 * i - j;
						x -= x / n * n;
						if (x < 0) {
							x += n;
						}
						int y = -i + j;
						y -= y / n * n;
						if (y < 0) {
							y += n;
						}
						
						newBufferedImage.setRGB(x, y, mBufferedImage.getRGB(i, j));
					}
				}
//			}

			ImageIO.write(newBufferedImage, "JPEG", new File("E:\\decrypt.jpg"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}