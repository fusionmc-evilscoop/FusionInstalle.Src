package net.miginfocom.layout;

public abstract class LayoutCallback
{
  public UnitValue[] getPosition(ComponentWrapper paramComponentWrapper)
  {
    return null;
  }

  public BoundSize[] getSize(ComponentWrapper paramComponentWrapper)
  {
    return null;
  }

  public void correctBounds(ComponentWrapper paramComponentWrapper)
  {
  }
}

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     net.miginfocom.layout.LayoutCallback
 * JD-Core Version:    0.6.0
 */