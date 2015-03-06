package main;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JFrame;

class ThisOrThatFrame extends JFrame {
	private static final long serialVersionUID = -3414798734557496232L;
	private FileImage imageA;
	private FileImage imageB;
	private Dimension screenSize;
	private DoRateActionController[] actionControllers;
	private FileFactory fileFactory;

	ThisOrThatFrame(String title, FileFactory fileFactory,
			DoRateActionController... actionControllers) {
		super(title);
		this.actionControllers = actionControllers;
		this.fileFactory = fileFactory;
		enableEvents(AWTEvent.MOUSE_EVENT_MASK);
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setLocation(0, 20);
	}

	private Component getThisImage() {
		Component result = imageA.getComponent();
		result.setBounds(0, 0, imageA.getWidth(), imageA.getHeight());
		return result;
	}

	private boolean hitsImageA(Point point) {
		return point.x > 0 && point.x < imageA.getWidth() && point.y > 0
				&& point.y < imageA.getHeight();
	}

	private boolean hitsImageB(Point point) {
		return point.x > imageA.getWidth()
				&& point.x < imageA.getWidth() + imageB.getWidth()
				&& point.y > 0 && point.y < imageB.getHeight();
	}

	private Component getThatImage() {
		Component result = imageB.getComponent();
		result.setBounds(imageA.getWidth(), 0, imageB.getWidth(),
				imageB.getHeight());
		return result;
	}

	// override processEvent which is called in Component class every time an
	// event happens
	@Override
	protected void processEvent(AWTEvent event) {
		if (event.getID() == MouseEvent.MOUSE_CLICKED) {
			Point point = ((MouseEvent) event).getPoint();
			if (hitsImageA(point)) {
				callActionControllers(imageA, imageB);
			} else if (hitsImageB(point)) {
				callActionControllers(imageB, imageA);
			}
		}
		super.processEvent(event);
	}

	private void callActionControllers(FileImage choosenImage, FileImage otherImage) {
		for (DoRateActionController ctrl : actionControllers) {
			ctrl.wasChoosen(choosenImage);
			ctrl.wasNotChoosen(otherImage);
		}
	}

	void nextPics() {
		File[] twoFiles = fileFactory.getTwoFiles();
		if (twoFiles != null) {
			imageA = new FileImage(twoFiles[0]);
			imageB = new FileImage(twoFiles[1]);
			imageA.setImageScaledDown(screenSize.width / 2, screenSize.height);
			imageB.setImageScaledDown(screenSize.width / 2, screenSize.height);
			this.getContentPane().removeAll();
			this.getContentPane().add(getThisImage());
			this.getContentPane().add(getThatImage());
			this.pack();
			this.setVisible(true);
			this.setSize(screenSize);
		}
	}
}
