package com.jidesoft.swing;

public abstract interface Alignable
{
  public static final String PROPERTY_ORIENTATION = "orientation";

  public abstract boolean supportVerticalOrientation();

  public abstract boolean supportHorizontalOrientation();

  public abstract void setOrientation(int paramInt);

  public abstract int getOrientation();
}

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.Alignable
 * JD-Core Version:    0.6.0
 */