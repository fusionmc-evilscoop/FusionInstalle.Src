package com.jidesoft.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract interface PersistenceUtilsCallback
{
  public static abstract interface Load
  {
    public abstract void load(Document paramDocument, Element paramElement, Object paramObject);
  }

  public static abstract interface Save
  {
    public abstract void save(Document paramDocument, Element paramElement, Object paramObject);
  }
}

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.utils.PersistenceUtilsCallback
 * JD-Core Version:    0.6.0
 */