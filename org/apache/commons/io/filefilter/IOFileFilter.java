package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;

public abstract interface IOFileFilter extends FileFilter, FilenameFilter
{
  public abstract boolean accept(File paramFile);

  public abstract boolean accept(File paramFile, String paramString);
}

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.commons.io.filefilter.IOFileFilter
 * JD-Core Version:    0.6.0
 */