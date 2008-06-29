package hudson.zipscript.resource;

import hudson.zipscript.parser.exception.ExecutionException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class FileResource extends AbstractResource {

	File file;

	public FileResource (File file) {
		this.file = file;
	}

	public InputStream getInputStream() {
		try {
			return new FileInputStream(file);
		}
		catch (FileNotFoundException e) {
			throw new ExecutionException("The file '" + file.getPath() + "' could not be located", null, e);
		}
	}

	public boolean hasBeenModifiedSince(long currentMilis) {
		return (file.lastModified() > currentMilis);
	}
}
