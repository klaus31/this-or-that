package foo;

import java.io.File;

class FilesOfDirectoryAtRandomFactory implements FileFactory{
	private File directory;

	public FilesOfDirectoryAtRandomFactory(File directory) {
		this.directory = directory;
	}

	@Override
	public File[] getTwoFiles() {
		String[] suffixes = { "jpg", "jpeg", "png" };
		RandomFile rf = new RandomFile(directory, suffixes);
		return rf.removeTwoFilesAtRandom();
	}
}
