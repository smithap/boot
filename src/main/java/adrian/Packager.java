package adrian;

import java.io.File;
import java.io.IOException;

import org.springframework.boot.loader.tools.Libraries;
import org.springframework.boot.loader.tools.LibraryCallback;
import org.springframework.boot.loader.tools.LibraryScope;
import org.springframework.boot.loader.tools.Repackager;

public class Packager {
	public static void main(String[] args) throws Exception {
		String srcJar = args[0];
		String mainClass = args[1];
		final String libDir = args[2];
		
		Repackager repackager = new Repackager(new File(srcJar));
		repackager.setMainClass(mainClass);
		repackager.repackage(new Libraries() {
			@Override
			public void doWithLibraries(LibraryCallback libraryCallback)
					throws IOException {
				File lib = new File(libDir);
				String[] jars = lib.list();
				for (String jar : jars) {
					System.err.println("adding " + jar);
					libraryCallback.library(new File(lib + "/" + jar),
							LibraryScope.RUNTIME);
				}
			}
		});
	}
}
