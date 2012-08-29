package net.miginfocom.layout;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;

public final class DimConstraint
  implements Externalizable
{
  final ResizeConstraint resize = new ResizeConstraint();
  private String sizeGroup = null;
  private BoundSize size = BoundSize.NULL_SIZE;
  private BoundSize gapBefore = null;
  private BoundSize gapAfter = null;
  private UnitValue align = null;
  private String endGroup = null;
  private boolean fill = false;
  private boolean noGrid = false;

  public int getGrowPriority()
  {
    return this.resize.growPrio;
  }

  public void setGrowPriority(int paramInt)
  {
    this.resize.growPrio = paramInt;
  }

  public Float getGrow()
  {
    return this.resize.grow;
  }

  public void setGrow(Float paramFloat)
  {
    this.resize.grow = paramFloat;
  }

  public int getShrinkPriority()
  {
    return this.resize.shrinkPrio;
  }

  public void setShrinkPriority(int paramInt)
  {
    this.resize.shrinkPrio = paramInt;
  }

  public Float getShrink()
  {
    return this.resize.shrink;
  }

  public void setShrink(Float paramFloat)
  {
    this.resize.shrink = paramFloat;
  }

  public UnitValue getAlignOrDefault(boolean paramBoolean)
  {
    if (this.align != null)
      return this.align;
    if (paramBoolean)
      return UnitValue.LEADING;
    return (this.fill) || (!PlatformDefaults.getDefaultRowAlignmentBaseline()) ? UnitValue.CENTER : UnitValue.BASELINE_IDENTITY;
  }

  public UnitValue getAlign()
  {
    return this.align;
  }

  public void setAlign(UnitValue paramUnitValue)
  {
    this.align = paramUnitValue;
  }

  public BoundSize getGapAfter()
  {
    return this.gapAfter;
  }

  public void setGapAfter(BoundSize paramBoundSize)
  {
    this.gapAfter = paramBoundSize;
  }

  boolean hasGapAfter()
  {
    return (this.gapAfter != null) && (!this.gapAfter.isUnset());
  }

  boolean isGapAfterPush()
  {
    return (this.gapAfter != null) && (this.gapAfter.getGapPush());
  }

  public BoundSize getGapBefore()
  {
    return this.gapBefore;
  }

  public void setGapBefore(BoundSize paramBoundSize)
  {
    this.gapBefore = paramBoundSize;
  }

  boolean hasGapBefore()
  {
    return (this.gapBefore != null) && (!this.gapBefore.isUnset());
  }

  boolean isGapBeforePush()
  {
    return (this.gapBefore != null) && (this.gapBefore.getGapPush());
  }

  public BoundSize getSize()
  {
    return this.size;
  }

  public void setSize(BoundSize paramBoundSize)
  {
    paramBoundSize.checkNotLinked();
    this.size = paramBoundSize;
  }

  public String getSizeGroup()
  {
    return this.sizeGroup;
  }

  public void setSizeGroup(String paramString)
  {
    this.sizeGroup = paramString;
  }

  public String getEndGroup()
  {
    return this.endGroup;
  }

  public void setEndGroup(String paramString)
  {
    this.endGroup = paramString;
  }

  public boolean isFill()
  {
    return this.fill;
  }

  public void setFill(boolean paramBoolean)
  {
    this.fill = paramBoolean;
  }

  public boolean isNoGrid()
  {
    return this.noGrid;
  }

  public void setNoGrid(boolean paramBoolean)
  {
    this.noGrid = paramBoolean;
  }

  int[] getRowGaps(ContainerWrapper paramContainerWrapper, BoundSize paramBoundSize, int paramInt, boolean paramBoolean)
  {
    BoundSize localBoundSize = paramBoolean ? this.gapBefore : this.gapAfter;
    if ((localBoundSize == null) || (localBoundSize.isUnset()))
      localBoundSize = paramBoundSize;
    if ((localBoundSize == null) || (localBoundSize.isUnset()))
      return null;
    int[] arrayOfInt = new int[3];
    for (int i = 0; i <= 2; i++)
    {
      UnitValue localUnitValue = localBoundSize.getSize(i);
      arrayOfInt[i] = (localUnitValue != null ? localUnitValue.getPixels(paramInt, paramContainerWrapper, null) : -2147471302);
    }
    return arrayOfInt;
  }

  int[] getComponentGaps(ContainerWrapper paramContainerWrapper, ComponentWrapper paramComponentWrapper1, BoundSize paramBoundSize, ComponentWrapper paramComponentWrapper2, String paramString, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    BoundSize localBoundSize = paramInt2 < 2 ? this.gapBefore : this.gapAfter;
    int i = (localBoundSize != null) && (localBoundSize.getGapPush()) ? 1 : 0;
    if (((localBoundSize == null) || (localBoundSize.isUnset())) && ((paramBoundSize == null) || (paramBoundSize.isUnset())) && (paramComponentWrapper1 != null))
      localBoundSize = PlatformDefaults.getDefaultComponentGap(paramComponentWrapper1, paramComponentWrapper2, paramInt2 + 1, paramString, paramBoolean);
    if (localBoundSize == null)
      return i != 0 ? new int[] { 0, 0, -2147471302 } : null;
    int[] arrayOfInt = new int[3];
    for (int j = 0; j <= 2; j++)
    {
      UnitValue localUnitValue = localBoundSize.getSize(j);
      arrayOfInt[j] = (localUnitValue != null ? localUnitValue.getPixels(paramInt1, paramContainerWrapper, null) : -2147471302);
    }
    return arrayOfInt;
  }

  private Object readResolve()
    throws ObjectStreamException
  {
    return LayoutUtil.getSerializedObject(this);
  }

  public void readExternal(ObjectInput paramObjectInput)
    throws IOException, ClassNotFoundException
  {
    LayoutUtil.setSerializedObject(this, LayoutUtil.readAsXML(paramObjectInput));
  }

  public void writeExternal(ObjectOutput paramObjectOutput)
    throws IOException
  {
    if (getClass() == DimConstraint.class)
      LayoutUtil.writeAsXML(paramObjectOutput, this);
  }
}

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     net.miginfocom.layout.DimConstraint
 * JD-Core Version:    0.6.0
 */