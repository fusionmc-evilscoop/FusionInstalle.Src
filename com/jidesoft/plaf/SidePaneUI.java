package com.jidesoft.plaf;

import com.jidesoft.swing.SidePaneGroup;
import com.jidesoft.swing.SidePaneItem;
import java.awt.Point;
import javax.swing.plaf.PanelUI;

public abstract class SidePaneUI extends PanelUI
{
  public abstract int getSelectedItemIndex(Point paramPoint);

  public abstract SidePaneGroup getGroupForIndex(int paramInt);

  public abstract SidePaneItem getItemForIndex(int paramInt);
}

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.SidePaneUI
 * JD-Core Version:    0.6.0
 */