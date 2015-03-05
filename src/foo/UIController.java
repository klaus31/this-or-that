package foo;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class UIController implements DoActionController {
	private ThisOrThatFrame thisOrThatFrame;

	public UIController() {
		File directory = getSelectedDirectory();
		if (directory == null) {
			System.exit(0);
		} else {
			DoActionController ratePlusOneController = new RatePlusOneController();
			FileFactory fileFactory = new FilesOfDirectoryAtRandomFactory(
					directory);

			thisOrThatFrame = new ThisOrThatFrame("this or that", fileFactory,
					ratePlusOneController, this);
			thisOrThatFrame.nextPics();
		}
	}

	private JFileChooser fileChooser() {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Wo Bilder?");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		return chooser;
	}

	private JFrame fileChooserFrame(JFileChooser chooser) {
		JFrame frame = new JFrame("");
		frame.getContentPane().add(chooser);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		return frame;
	}

	@Override
	public void doActionOn(FileImage fileImage) {
		thisOrThatFrame.nextPics();
	}

	private File getSelectedDirectory() {
		File directory = null;
		JFileChooser chooser = fileChooser();
		JFrame frame = fileChooserFrame(chooser);
		if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
			directory = chooser.getSelectedFile();
			frame.setVisible(false);
		} else {
			System.out.println("Nix Bilder!");
		}
		return directory;
	}
}
