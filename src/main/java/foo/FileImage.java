package foo;

import static java.awt.Image.SCALE_SMOOTH;

import java.awt.Component;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

class FileImage {
	private ImageIcon imageIcon;
	private File file;

	Component getComponent() {
		JLabel label = new JLabel(imageIcon);
		return label;
	}

	int getWidth() {
		return imageIcon.getIconWidth();
	}

	int getHeight() {
		return imageIcon.getIconHeight();
	}

	File getFile() {
		return file;
	}

	FileImage(File file) {
		this.file = file;
		try {
			BufferedImage image = ImageIO.read(file);
			imageIcon = new ImageIcon(image);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void setImageScaledDown(int maxWidth, int maxHeight) {
		int originalWidth = getWidth();
		int originalHeight = getHeight();
		int scaledWidth = originalWidth;
		int scaledHeight = originalHeight;
		if (scaledWidth > maxWidth) {
			scaledWidth = maxWidth;
			scaledHeight = originalHeight * scaledWidth / originalWidth;
		}
		if (scaledHeight > maxHeight) {
			scaledHeight = maxHeight;
			scaledWidth = originalWidth * scaledHeight / originalHeight;
		}
		Image scaledImage = imageIcon.getImage().getScaledInstance(scaledWidth,
				scaledHeight, SCALE_SMOOTH);
		imageIcon = new ImageIcon(scaledImage);
	}

	FileImage(String filePath) {
		this(new File(filePath));
	}
}
