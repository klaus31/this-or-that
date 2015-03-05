package foo;

import java.io.File;

class RatePlusOneController implements DoActionController {
	@Override
	public void doActionOn(FileImage fileImage) {
		File file = fileImage.getFile();
		File newFile = getRenamedFile(file);
		if(file.renameTo(newFile)) {
			System.out.println(fileImage.getFile().getName() + " -> "
					+ newFile.getName());			
		} else {
			System.err.println("Konnte " + file.getName() + " nicht umbenennen.");
		}
	}

	private File getRenamedFile(File file) {
		if(isAlreadyRated(file)) {
			int currentRate = getCurrentRate(file);
			String newName = file.getName().replaceFirst("--rate-" + currentRate, "--rate-" + ++currentRate);
			return new File(file.getParent() + File.separatorChar + newName);
		} else {
			int fullstop = file.getName().lastIndexOf('.');
			String newName = file.getName().substring(0, fullstop);
			newName += "--rate-1";
			newName += file.getName().substring(fullstop);
			return new File(file.getParent() + File.separatorChar + newName);
		}
	}

	private int getCurrentRate(File file) {
		String number = file.getName().replaceFirst(".+--rate-", "");
		number = number.replaceAll("\\..+", "");
		try {
			return Integer.parseInt(number);
		} catch(NumberFormatException e) {
			return 0;
		}
	}

	private boolean isAlreadyRated(File file) {
		return file.getName().matches(".+--rate-[0-9]+\\..+");
	}

}
