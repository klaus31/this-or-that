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
				for (String suffix : suffixes) {
					if (candidate.getName().matches(".+" + suffix + "$")) {
						files.add(candidate);
						break;
					}
				}
			}
		} else {
			System.err.println(directory
					+ " kann nicht gelesen werden, Du Dödel!");
		}
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
