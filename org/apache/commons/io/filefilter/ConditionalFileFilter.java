package org.apache.commons.io.filefilter;

import java.util.List;

public abstract interface ConditionalFileFilter
{
  public abstract void addFileFilter(IOFileFilter paramIOFileFilter);

  public abstract List getFileFilters();

  public abstract boolean removeFileFilter(IOFileFilter paramIOFileFilter);

  public abstract void setFileFilters(List paramList);
}

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.commons.io.filefilter.ConditionalFileFilter
 * JD-Core Version:    0.6.0
 */