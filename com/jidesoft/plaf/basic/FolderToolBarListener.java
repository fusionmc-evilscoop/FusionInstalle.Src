package com.jidesoft.plaf.basic;

import java.io.File;

abstract interface FolderToolBarListener
{
  public abstract void deleteFolderButtonClicked();

  public abstract void newFolderButtonClicked();

  public abstract void myDocumentsButtonClicked();

  public abstract void desktopButtonClicked();

  public abstract void recentFolderSelected(File paramFile);

  public abstract void refreshButtonClicked();
}

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.basic.FolderToolBarListener
 * JD-Core Version:    0.6.0
 */