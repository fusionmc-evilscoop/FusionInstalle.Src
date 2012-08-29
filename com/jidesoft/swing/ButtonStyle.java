package com.jidesoft.swing;

public abstract interface ButtonStyle
{
  public static final String BUTTON_STYLE_PROPERTY = "buttonStyle";
  public static final int TOOLBAR_STYLE = 0;
  public static final int TOOLBOX_STYLE = 1;
  public static final int FLAT_STYLE = 2;
  public static final int HYPERLINK_STYLE = 3;
  public static final String CLIENT_PROPERTY_SEGMENT_POSITION = "JButton.segmentPosition";
  public static final String SEGMENT_POSITION_FIRST = "first";
  public static final String SEGMENT_POSITION_MIDDLE = "middle";
  public static final String SEGMENT_POSITION_LAST = "last";
  public static final String SEGMENT_POSITION_ONLY = "only";

  public abstract int getButtonStyle();

  public abstract void setButtonStyle(int paramInt);
}

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.ButtonStyle
 * JD-Core Version:    0.6.0
 */