package net.miginfocom.swt;

import net.miginfocom.layout.ComponentWrapper;
import net.miginfocom.layout.ContainerWrapper;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public final class SwtContainerWrapper extends SwtComponentWrapper
  implements ContainerWrapper
{
  public SwtContainerWrapper(Composite paramComposite)
  {
    super(paramComposite);
  }

  public ComponentWrapper[] getComponents()
  {
    Composite localComposite = (Composite)getComponent();
    Control[] arrayOfControl = localComposite.getChildren();
    ComponentWrapper[] arrayOfComponentWrapper = new ComponentWrapper[arrayOfControl.length];
    for (int i = 0; i < arrayOfComponentWrapper.length; i++)
      arrayOfComponentWrapper[i] = new SwtComponentWrapper(arrayOfControl[i]);
    return arrayOfComponentWrapper;
  }

  public int getComponentCount()
  {
    return ((Composite)getComponent()).getChildren().length;
  }

  public Object getLayout()
  {
    return ((Composite)getComponent()).getLayout();
  }

  public final boolean isLeftToRight()
  {
    return (((Composite)getComponent()).getStyle() & 0x2000000) > 0;
  }

  public final void paintDebugCell(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
  }

  public int getComponetType(boolean paramBoolean)
  {
    return 1;
  }

  public int getLayoutHashCode()
  {
    int i = super.getLayoutHashCode();
    if (isLeftToRight())
      i |= 67108864;
    return i;
  }
}

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     net.miginfocom.swt.SwtContainerWrapper
 * JD-Core Version:    0.6.0
 */