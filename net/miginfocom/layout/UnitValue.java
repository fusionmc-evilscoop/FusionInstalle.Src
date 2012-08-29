package net.miginfocom.layout;

import java.beans.Encoder;
import java.beans.Expression;
import java.beans.PersistenceDelegate;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public final class UnitValue
  implements Serializable
{
  private static final HashMap<String, Integer> UNIT_MAP = new HashMap(32);
  private static final ArrayList<UnitConverter> CONVERTERS = new ArrayList();
  public static final int STATIC = 100;
  public static final int ADD = 101;
  public static final int SUB = 102;
  public static final int MUL = 103;
  public static final int DIV = 104;
  public static final int MIN = 105;
  public static final int MAX = 106;
  public static final int MID = 107;
  public static final int PIXEL = 0;
  public static final int LPX = 1;
  public static final int LPY = 2;
  public static final int MM = 3;
  public static final int CM = 4;
  public static final int INCH = 5;
  public static final int PERCENT = 6;
  public static final int PT = 7;
  public static final int SPX = 8;
  public static final int SPY = 9;
  public static final int ALIGN = 12;
  public static final int MIN_SIZE = 13;
  public static final int PREF_SIZE = 14;
  public static final int MAX_SIZE = 15;
  public static final int BUTTON = 16;
  public static final int LINK_X = 18;
  public static final int LINK_Y = 19;
  public static final int LINK_W = 20;
  public static final int LINK_H = 21;
  public static final int LINK_X2 = 22;
  public static final int LINK_Y2 = 23;
  public static final int LINK_XPOS = 24;
  public static final int LINK_YPOS = 25;
  public static final int LOOKUP = 26;
  public static final int LABEL_ALIGN = 27;
  private static final int IDENTITY = -1;
  static final UnitValue ZERO;
  static final UnitValue TOP;
  static final UnitValue LEADING;
  static final UnitValue LEFT;
  static final UnitValue CENTER;
  static final UnitValue TRAILING;
  static final UnitValue RIGHT;
  static final UnitValue BOTTOM;
  static final UnitValue LABEL;
  static final UnitValue INF;
  static final UnitValue BASELINE_IDENTITY;
  private final transient float value;
  private final transient int unit;
  private final transient int oper;
  private final transient String unitStr;
  private transient String linkId = null;
  private final transient boolean isHor;
  private final transient UnitValue[] subUnits;
  private static final float[] SCALE;
  private static final long serialVersionUID = 1L;

  public UnitValue(float paramFloat)
  {
    this(paramFloat, null, 0, true, 100, null, null, paramFloat + "px");
  }

  public UnitValue(float paramFloat, int paramInt, String paramString)
  {
    this(paramFloat, null, paramInt, true, 100, null, null, paramString);
  }

  UnitValue(float paramFloat, String paramString1, boolean paramBoolean, int paramInt, String paramString2)
  {
    this(paramFloat, paramString1, -1, paramBoolean, paramInt, null, null, paramString2);
  }

  UnitValue(boolean paramBoolean, int paramInt, UnitValue paramUnitValue1, UnitValue paramUnitValue2, String paramString)
  {
    this(0.0F, "", -1, paramBoolean, paramInt, paramUnitValue1, paramUnitValue2, paramString);
    if ((paramUnitValue1 == null) || (paramUnitValue2 == null))
      throw new IllegalArgumentException("Sub units is null!");
  }

  private UnitValue(float paramFloat, String paramString1, int paramInt1, boolean paramBoolean, int paramInt2, UnitValue paramUnitValue1, UnitValue paramUnitValue2, String paramString2)
  {
    if ((paramInt2 < 100) || (paramInt2 > 107))
      throw new IllegalArgumentException("Unknown Operation: " + paramInt2);
    if ((paramInt2 >= 101) && (paramInt2 <= 107) && ((paramUnitValue1 == null) || (paramUnitValue2 == null)))
      throw new IllegalArgumentException(paramInt2 + " Operation may not have null sub-UnitValues.");
    this.value = paramFloat;
    this.oper = paramInt2;
    this.isHor = paramBoolean;
    this.unitStr = paramString1;
    this.unit = (paramString1 != null ? parseUnitString() : paramInt1);
    this.subUnits = ((paramUnitValue1 != null) && (paramUnitValue2 != null) ? new UnitValue[] { paramUnitValue1, paramUnitValue2 } : null);
    LayoutUtil.putCCString(this, paramString2);
  }

  public final int getPixels(float paramFloat, ContainerWrapper paramContainerWrapper, ComponentWrapper paramComponentWrapper)
  {
    return Math.round(getPixelsExact(paramFloat, paramContainerWrapper, paramComponentWrapper));
  }

  public final float getPixelsExact(float paramFloat, ContainerWrapper paramContainerWrapper, ComponentWrapper paramComponentWrapper)
  {
    if (paramContainerWrapper == null)
      return 1.0F;
    float f1;
    if (this.oper == 100)
    {
      switch (this.unit)
      {
      case 0:
        return this.value;
      case 1:
      case 2:
        return paramContainerWrapper.getPixelUnitFactor(this.unit == 1) * this.value;
      case 3:
      case 4:
      case 5:
      case 7:
        f1 = SCALE[(this.unit - 3)];
        Float localFloat = this.isHor ? PlatformDefaults.getHorizontalScaleFactor() : PlatformDefaults.getVerticalScaleFactor();
        if (localFloat != null)
          f1 *= localFloat.floatValue();
        return (this.isHor ? paramContainerWrapper.getHorizontalScreenDPI() : paramContainerWrapper.getVerticalScreenDPI()) * this.value / f1;
      case 6:
        return this.value * paramFloat * 0.01F;
      case 8:
      case 9:
        return (this.unit == 8 ? paramContainerWrapper.getScreenWidth() : paramContainerWrapper.getScreenHeight()) * this.value * 0.01F;
      case 12:
        Integer localInteger1 = LinkHandler.getValue(paramContainerWrapper.getLayout(), "visual", this.isHor ? 0 : 1);
        Integer localInteger2 = LinkHandler.getValue(paramContainerWrapper.getLayout(), "visual", this.isHor ? 2 : 3);
        if ((localInteger1 == null) || (localInteger2 == null))
          return 0.0F;
        return this.value * (Math.max(0, localInteger2.intValue()) - paramFloat) + localInteger1.intValue();
      case 13:
        if (paramComponentWrapper == null)
          return 0.0F;
        return paramComponentWrapper.getMinimumHeight(paramComponentWrapper.getWidth());
      case 14:
        if (paramComponentWrapper == null)
          return 0.0F;
        return paramComponentWrapper.getPreferredHeight(paramComponentWrapper.getWidth());
      case 15:
        if (paramComponentWrapper == null)
          return 0.0F;
        return paramComponentWrapper.getMaximumHeight(paramComponentWrapper.getWidth());
      case 16:
        return PlatformDefaults.getMinimumButtonWidth().getPixels(paramFloat, paramContainerWrapper, paramComponentWrapper);
      case 18:
      case 19:
      case 20:
      case 21:
      case 22:
      case 23:
      case 24:
      case 25:
        Integer localInteger3 = LinkHandler.getValue(paramContainerWrapper.getLayout(), getLinkTargetId(), this.unit - (this.unit >= 24 ? 24 : 18));
        if (localInteger3 == null)
          return 0.0F;
        if (this.unit == 24)
          return paramContainerWrapper.getScreenLocationX() + localInteger3.intValue();
        if (this.unit == 25)
          return paramContainerWrapper.getScreenLocationY() + localInteger3.intValue();
        return localInteger3.intValue();
      case 26:
        float f3 = lookup(paramFloat, paramContainerWrapper, paramComponentWrapper);
        if (f3 == -87654312.0F)
          break;
        return f3;
      case 27:
        return PlatformDefaults.getLabelAlignPercentage() * paramFloat;
      case -1:
      case 10:
      case 11:
      case 17:
      }
      throw new IllegalArgumentException("Unknown/illegal unit: " + this.unit + ", unitStr: " + this.unitStr);
    }
    if ((this.subUnits != null) && (this.subUnits.length == 2))
    {
      f1 = this.subUnits[0].getPixelsExact(paramFloat, paramContainerWrapper, paramComponentWrapper);
      float f2 = this.subUnits[1].getPixelsExact(paramFloat, paramContainerWrapper, paramComponentWrapper);
      switch (this.oper)
      {
      case 101:
        return f1 + f2;
      case 102:
        return f1 - f2;
      case 103:
        return f1 * f2;
      case 104:
        return f1 / f2;
      case 105:
        return f1 < f2 ? f1 : f2;
      case 106:
        return f1 > f2 ? f1 : f2;
      case 107:
        return (f1 + f2) * 0.5F;
      }
    }
    throw new IllegalArgumentException("Internal: Unknown Oper: " + this.oper);
  }

  private float lookup(float paramFloat, ContainerWrapper paramContainerWrapper, ComponentWrapper paramComponentWrapper)
  {
    float f = -87654312.0F;
    for (int i = CONVERTERS.size() - 1; i >= 0; i--)
    {
      f = ((UnitConverter)CONVERTERS.get(i)).convertToPixels(this.value, this.unitStr, this.isHor, paramFloat, paramContainerWrapper, paramComponentWrapper);
      if (f != -87654312.0F)
        return f;
    }
    return PlatformDefaults.convertToPixels(this.value, this.unitStr, this.isHor, paramFloat, paramContainerWrapper, paramComponentWrapper);
  }

  private int parseUnitString()
  {
    int i = this.unitStr.length();
    if (i == 0)
      return this.isHor ? PlatformDefaults.getDefaultHorizontalUnit() : PlatformDefaults.getDefaultVerticalUnit();
    Integer localInteger = (Integer)UNIT_MAP.get(this.unitStr);
    if (localInteger != null)
      return localInteger.intValue();
    if (this.unitStr.equals("lp"))
      return this.isHor ? 1 : 2;
    if (this.unitStr.equals("sp"))
      return this.isHor ? 8 : 9;
    if (lookup(0.0F, null, null) != -87654312.0F)
      return 26;
    int j = this.unitStr.indexOf('.');
    if (j != -1)
    {
      this.linkId = this.unitStr.substring(0, j);
      String str = this.unitStr.substring(j + 1);
      if (str.equals("x"))
        return 18;
      if (str.equals("y"))
        return 19;
      if ((str.equals("w")) || (str.equals("width")))
        return 20;
      if ((str.equals("h")) || (str.equals("height")))
        return 21;
      if (str.equals("x2"))
        return 22;
      if (str.equals("y2"))
        return 23;
      if (str.equals("xpos"))
        return 24;
      if (str.equals("ypos"))
        return 25;
    }
    throw new IllegalArgumentException("Unknown keyword: " + this.unitStr);
  }

  final boolean isLinked()
  {
    return this.linkId != null;
  }

  final boolean isLinkedDeep()
  {
    if (this.subUnits == null)
      return this.linkId != null;
    for (int i = 0; i < this.subUnits.length; i++)
      if (this.subUnits[i].isLinkedDeep())
        return true;
    return false;
  }

  final String getLinkTargetId()
  {
    return this.linkId;
  }

  final UnitValue getSubUnitValue(int paramInt)
  {
    return this.subUnits[paramInt];
  }

  final int getSubUnitCount()
  {
    return this.subUnits != null ? this.subUnits.length : 0;
  }

  public final UnitValue[] getSubUnits()
  {
    return this.subUnits;
  }

  public final int getUnit()
  {
    return this.unit;
  }

  public final String getUnitString()
  {
    return this.unitStr;
  }

  public final int getOperation()
  {
    return this.oper;
  }

  public final float getValue()
  {
    return this.value;
  }

  public final boolean isHorizontal()
  {
    return this.isHor;
  }

  public final String toString()
  {
    return getClass().getName() + ". Value=" + this.value + ", unit=" + this.unit + ", unitString: " + this.unitStr + ", oper=" + this.oper + ", isHor: " + this.isHor;
  }

  public final String getConstraintString()
  {
    return LayoutUtil.getCCString(this);
  }

  public final int hashCode()
  {
    return (int)(this.value * 12345.0F) + (this.oper >>> 5) + this.unit >>> 17;
  }

  public static final synchronized void addGlobalUnitConverter(UnitConverter paramUnitConverter)
  {
    if (paramUnitConverter == null)
      throw new NullPointerException();
    CONVERTERS.add(paramUnitConverter);
  }

  public static final synchronized boolean removeGlobalUnitConverter(String paramString)
  {
    return CONVERTERS.remove(paramString);
  }

  public static final synchronized UnitConverter[] getGlobalUnitConverters()
  {
    return (UnitConverter[])CONVERTERS.toArray(new UnitConverter[CONVERTERS.size()]);
  }

  /** @deprecated */
  public static final int getDefaultUnit()
  {
    return PlatformDefaults.getDefaultHorizontalUnit();
  }

  /** @deprecated */
  public static final void setDefaultUnit(int paramInt)
  {
    PlatformDefaults.setDefaultHorizontalUnit(paramInt);
    PlatformDefaults.setDefaultVerticalUnit(paramInt);
  }

  private Object readResolve()
    throws ObjectStreamException
  {
    return LayoutUtil.getSerializedObject(this);
  }

  private void writeObject(ObjectOutputStream paramObjectOutputStream)
    throws IOException
  {
    if (getClass() == UnitValue.class)
      LayoutUtil.writeAsXML(paramObjectOutputStream, this);
  }

  private void readObject(ObjectInputStream paramObjectInputStream)
    throws IOException, ClassNotFoundException
  {
    LayoutUtil.setSerializedObject(this, LayoutUtil.readAsXML(paramObjectInputStream));
  }

  static
  {
    UNIT_MAP.put("px", new Integer(0));
    UNIT_MAP.put("lpx", new Integer(1));
    UNIT_MAP.put("lpy", new Integer(2));
    UNIT_MAP.put("%", new Integer(6));
    UNIT_MAP.put("cm", new Integer(4));
    UNIT_MAP.put("in", new Integer(5));
    UNIT_MAP.put("spx", new Integer(8));
    UNIT_MAP.put("spy", new Integer(9));
    UNIT_MAP.put("al", new Integer(12));
    UNIT_MAP.put("mm", new Integer(3));
    UNIT_MAP.put("pt", new Integer(7));
    UNIT_MAP.put("min", new Integer(13));
    UNIT_MAP.put("minimum", new Integer(13));
    UNIT_MAP.put("p", new Integer(14));
    UNIT_MAP.put("pref", new Integer(14));
    UNIT_MAP.put("max", new Integer(15));
    UNIT_MAP.put("maximum", new Integer(15));
    UNIT_MAP.put("button", new Integer(16));
    UNIT_MAP.put("label", new Integer(27));
    ZERO = new UnitValue(0.0F, null, 0, true, 100, null, null, "0px");
    TOP = new UnitValue(0.0F, null, 6, false, 100, null, null, "top");
    LEADING = new UnitValue(0.0F, null, 6, true, 100, null, null, "leading");
    LEFT = new UnitValue(0.0F, null, 6, true, 100, null, null, "left");
    CENTER = new UnitValue(50.0F, null, 6, true, 100, null, null, "center");
    TRAILING = new UnitValue(100.0F, null, 6, true, 100, null, null, "trailing");
    RIGHT = new UnitValue(100.0F, null, 6, true, 100, null, null, "right");
    BOTTOM = new UnitValue(100.0F, null, 6, false, 100, null, null, "bottom");
    LABEL = new UnitValue(0.0F, null, 27, false, 100, null, null, "label");
    INF = new UnitValue(2097051.0F, null, 0, true, 100, null, null, "inf");
    BASELINE_IDENTITY = new UnitValue(0.0F, null, -1, false, 100, null, null, "baseline");
    SCALE = new float[] { 25.4F, 2.54F, 1.0F, 0.0F, 72.0F };
    LayoutUtil.setDelegate(UnitValue.class, new PersistenceDelegate()
    {
      protected Expression instantiate(Object paramObject, Encoder paramEncoder)
      {
        UnitValue localUnitValue = (UnitValue)paramObject;
        String str = localUnitValue.getConstraintString();
        if (str == null)
          throw new IllegalStateException("Design time must be on to use XML persistence. See LayoutUtil.");
        return new Expression(paramObject, ConstraintParser.class, "parseUnitValueOrAlign", new Object[] { localUnitValue.getConstraintString(), localUnitValue.isHorizontal() ? Boolean.TRUE : Boolean.FALSE, null });
      }
    });
  }
}

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     net.miginfocom.layout.UnitValue
 * JD-Core Version:    0.6.0
 */