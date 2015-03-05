package foo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class RandomFile {

	private List<File> files = new ArrayList<>();
	private Random random = new Random();

	RandomFile(File directory, String... suffixes) {
		if (directory.isDirectory() && directory.canRead()) {
			File[] candidates = directory.listFiles();
			for (File candidate : candidates) {
				if (!candidate.isFile() || !candidate.canRead()
						|| !candidate.canWrite()) {
					System.out.println(candidate.getName()
							+ " kann nicht gelesen werden");
					continue;
				}
				if (supportedFile(candidate, suffixes)) {
					files.add(candidate);
				} else {
					System.out.println(candidate.getName()
							+ " not supported file format");
				}
			}
		} else {
			System.err.println(directory
					+ " kann nicht gelesen werden, Du Dödel!");
		}
	}

	private boolean supportedFile(File candidate, String[] suffixes) {
		boolean result = false;
		for (String suffix : suffixes) {
			if (candidate.getName().toLowerCase()
					.matches(".+" + suffix.toLowerCase() + "$")) {
				result = true;
				break;
			}
		}
		return result;
	}

	File[] removeTwoFilesAtRandom() {
		if (files.size() < 2) {
			System.err.println("hab nicht genug dateien");
			return null;
		}
		File file1 = files.remove(random.nextInt(files.size()));
		File file2 = files.remove(random.nextInt(files.size()));
		return new File[] { file1, file2 };
	}

}
