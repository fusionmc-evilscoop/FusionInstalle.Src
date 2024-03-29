package net.miginfocom.layout;

import java.beans.Encoder;
import java.beans.Expression;
import java.beans.PersistenceDelegate;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;

public class BoundSize
  implements Serializable
{
  public static final BoundSize NULL_SIZE = new BoundSize(null, null);
  public static final BoundSize ZERO_PIXEL = new BoundSize(UnitValue.ZERO, "0px");
  private final transient UnitValue min;
  private final transient UnitValue pref;
  private final transient UnitValue max;
  private final transient boolean gapPush;
  private static final long serialVersionUID = 1L;

  public BoundSize(UnitValue paramUnitValue, String paramString)
  {
    this(paramUnitValue, paramUnitValue, paramUnitValue, paramString);
  }

  public BoundSize(UnitValue paramUnitValue1, UnitValue paramUnitValue2, UnitValue paramUnitValue3, String paramString)
  {
    this(paramUnitValue1, paramUnitValue2, paramUnitValue3, false, paramString);
  }

  public BoundSize(UnitValue paramUnitValue1, UnitValue paramUnitValue2, UnitValue paramUnitValue3, boolean paramBoolean, String paramString)
  {
    this.min = paramUnitValue1;
    this.pref = paramUnitValue2;
    this.max = paramUnitValue3;
    this.gapPush = paramBoolean;
    LayoutUtil.putCCString(this, paramString);
  }

  public final UnitValue getMin()
  {
    return this.min;
  }

  public final UnitValue getPreferred()
  {
    return this.pref;
  }

  public final UnitValue getMax()
  {
    return this.max;
  }

  public boolean getGapPush()
  {
    return this.gapPush;
  }

  public boolean isUnset()
  {
    return (this == ZERO_PIXEL) || ((this.pref == null) && (this.min == null) && (this.max == null) && (!this.gapPush));
  }

  public int constrain(int paramInt, float paramFloat, ContainerWrapper paramContainerWrapper)
  {
    if (this.max != null)
      paramInt = Math.min(paramInt, this.max.getPixels(paramFloat, paramContainerWrapper, paramContainerWrapper));
    if (this.min != null)
      paramInt = Math.max(paramInt, this.min.getPixels(paramFloat, paramContainerWrapper, paramContainerWrapper));
    return paramInt;
  }

  final UnitValue getSize(int paramInt)
  {
    switch (paramInt)
    {
    case 0:
      return this.min;
    case 1:
      return this.pref;
    case 2:
      return this.max;
    }
    throw new IllegalArgumentException("Unknown size: " + paramInt);
  }

  final int[] getPixelSizes(float paramFloat, ContainerWrapper paramContainerWrapper, ComponentWrapper paramComponentWrapper)
  {
    return new int[] { this.min != null ? this.min.getPixels(paramFloat, paramContainerWrapper, paramComponentWrapper) : 0, this.pref != null ? this.pref.getPixels(paramFloat, paramContainerWrapper, paramComponentWrapper) : 0, this.max != null ? this.max.getPixels(paramFloat, paramContainerWrapper, paramComponentWrapper) : 2097051 };
  }

  String getConstraintString()
  {
    String str = LayoutUtil.getCCString(this);
    if (str != null)
      return str;
    if ((this.min == this.pref) && (this.pref == this.max))
      return this.min != null ? this.min.getConstraintString() + "!" : "null";
    StringBuilder localStringBuilder = new StringBuilder(16);
    if (this.min != null)
      localStringBuilder.append(this.min.getConstraintString()).append(':');
    if (this.pref != null)
    {
      if ((this.min == null) && (this.max != null))
        localStringBuilder.append(":");
      localStringBuilder.append(this.pref.getConstraintString());
    }
    else if (this.min != null)
    {
      localStringBuilder.append('n');
    }
    if (this.max != null)
      localStringBuilder.append(localStringBuilder.length() == 0 ? "::" : ":").append(this.max.getConstraintString());
    if (this.gapPush)
    {
      if (localStringBuilder.length() > 0)
        localStringBuilder.append(':');
      localStringBuilder.append("push");
    }
    return localStringBuilder.toString();
  }

  void checkNotLinked()
  {
    if (((this.min != null) && (this.min.isLinkedDeep())) || ((this.pref != null) && (this.pref.isLinkedDeep())) || ((this.max != null) && (this.max.isLinkedDeep())))
      throw new IllegalArgumentException("Size may not contain links");
  }

  protected Object readResolve()
    throws ObjectStreamException
  {
    return LayoutUtil.getSerializedObject(this);
  }

  private void writeObject(ObjectOutputStream paramObjectOutputStream)
    throws IOException
  {
    if (getClass() == BoundSize.class)
      LayoutUtil.writeAsXML(paramObjectOutputStream, this);
  }

  private void readObject(ObjectInputStream paramObjectInputStream)
    throws IOException, ClassNotFoundException
  {
    LayoutUtil.setSerializedObject(this, LayoutUtil.readAsXML(paramObjectInputStream));
  }

  static
  {
    LayoutUtil.setDelegate(BoundSize.class, new PersistenceDelegate()
    {
      protected Expression instantiate(Object paramObject, Encoder paramEncoder)
      {
        BoundSize localBoundSize = (BoundSize)paramObject;
        return new Expression(paramObject, BoundSize.class, "new", new Object[] { localBoundSize.getMin(), localBoundSize.getPreferred(), localBoundSize.getMax(), Boolean.valueOf(localBoundSize.getGapPush()), localBoundSize.getConstraintString() });
      }
    });
  }
}

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     net.miginfocom.layout.BoundSize
 * JD-Core Version:    0.6.0
 */