package net.miginfocom.layout;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;
import java.util.ArrayList;

public final class CC
  implements Externalizable
{
  private static final BoundSize DEF_GAP = BoundSize.NULL_SIZE;
  static final String[] DOCK_SIDES = { "north", "west", "south", "east" };
  private int dock = -1;
  private UnitValue[] pos = null;
  private UnitValue[] padding = null;
  private Boolean flowX = null;
  private int skip = 0;
  private int split = 1;
  private int spanX = 1;
  private int spanY = 1;
  private int cellX = -1;
  private int cellY = 0;
  private String tag = null;
  private String id = null;
  private int hideMode = -1;
  private DimConstraint hor = new DimConstraint();
  private DimConstraint ver = new DimConstraint();
  private BoundSize newline = null;
  private BoundSize wrap = null;
  private boolean boundsInGrid = true;
  private boolean external = false;
  private Float pushX = null;
  private Float pushY = null;
  private static final String[] EMPTY_ARR = new String[0];
  private transient String[] linkTargets = null;

  String[] getLinkTargets()
  {
    if (this.linkTargets == null)
    {
      ArrayList localArrayList = new ArrayList(2);
      if (this.pos != null)
        for (int i = 0; i < this.pos.length; i++)
          addLinkTargetIDs(localArrayList, this.pos[i]);
      this.linkTargets = (localArrayList.size() == 0 ? EMPTY_ARR : (String[])localArrayList.toArray(new String[localArrayList.size()]));
    }
    return this.linkTargets;
  }

  private void addLinkTargetIDs(ArrayList<String> paramArrayList, UnitValue paramUnitValue)
  {
    if (paramUnitValue != null)
    {
      String str = paramUnitValue.getLinkTargetId();
      if (str != null)
        paramArrayList.add(str);
      else
        for (int i = paramUnitValue.getSubUnitCount() - 1; i >= 0; i--)
        {
          UnitValue localUnitValue = paramUnitValue.getSubUnitValue(i);
          if (!localUnitValue.isLinkedDeep())
            continue;
          addLinkTargetIDs(paramArrayList, localUnitValue);
        }
    }
  }

  public final CC endGroupX(String paramString)
  {
    this.hor.setEndGroup(paramString);
    return this;
  }

  public final CC sizeGroupX(String paramString)
  {
    this.hor.setSizeGroup(paramString);
    return this;
  }

  public final CC minWidth(String paramString)
  {
    this.hor.setSize(LayoutUtil.derive(this.hor.getSize(), ConstraintParser.parseUnitValue(paramString, true), null, null));
    return this;
  }

  public final CC width(String paramString)
  {
    this.hor.setSize(ConstraintParser.parseBoundSize(paramString, false, true));
    return this;
  }

  public final CC maxWidth(String paramString)
  {
    this.hor.setSize(LayoutUtil.derive(this.hor.getSize(), null, null, ConstraintParser.parseUnitValue(paramString, true)));
    return this;
  }

  public final CC gapX(String paramString1, String paramString2)
  {
    if (paramString1 != null)
      this.hor.setGapBefore(ConstraintParser.parseBoundSize(paramString1, true, true));
    if (paramString2 != null)
      this.hor.setGapAfter(ConstraintParser.parseBoundSize(paramString2, true, true));
    return this;
  }

  public final CC alignX(String paramString)
  {
    this.hor.setAlign(ConstraintParser.parseUnitValueOrAlign(paramString, true, null));
    return this;
  }

  public final CC growPrioX(int paramInt)
  {
    this.hor.setGrowPriority(paramInt);
    return this;
  }

  public final CC growPrio(int[] paramArrayOfInt)
  {
    switch (paramArrayOfInt.length)
    {
    default:
      throw new IllegalArgumentException("Illegal argument count: " + paramArrayOfInt.length);
    case 2:
      growPrioY(paramArrayOfInt[1]);
    case 1:
    }
    growPrioX(paramArrayOfInt[0]);
    return this;
  }

  public final CC growX()
  {
    this.hor.setGrow(ResizeConstraint.WEIGHT_100);
    return this;
  }

  public final CC growX(float paramFloat)
  {
    this.hor.setGrow(new Float(paramFloat));
    return this;
  }

  public final CC grow(float[] paramArrayOfFloat)
  {
    switch (paramArrayOfFloat.length)
    {
    default:
      throw new IllegalArgumentException("Illegal argument count: " + paramArrayOfFloat.length);
    case 2:
      growY(Float.valueOf(paramArrayOfFloat[1]));
    case 1:
    }
    growX(paramArrayOfFloat[0]);
    return this;
  }

  public final CC shrinkPrioX(int paramInt)
  {
    this.hor.setShrinkPriority(paramInt);
    return this;
  }

  public final CC shrinkPrio(int[] paramArrayOfInt)
  {
    switch (paramArrayOfInt.length)
    {
    default:
      throw new IllegalArgumentException("Illegal argument count: " + paramArrayOfInt.length);
    case 2:
      shrinkPrioY(paramArrayOfInt[1]);
    case 1:
    }
    shrinkPrioX(paramArrayOfInt[0]);
    return this;
  }

  public final CC shrinkX(float paramFloat)
  {
    this.hor.setShrink(new Float(paramFloat));
    return this;
  }

  public final CC shrink(float[] paramArrayOfFloat)
  {
    switch (paramArrayOfFloat.length)
    {
    default:
      throw new IllegalArgumentException("Illegal argument count: " + paramArrayOfFloat.length);
    case 2:
      shrinkY(paramArrayOfFloat[1]);
    case 1:
    }
    shrinkX(paramArrayOfFloat[0]);
    return this;
  }

  public final CC endGroupY(String paramString)
  {
    this.ver.setEndGroup(paramString);
    return this;
  }

  public final CC endGroup(String[] paramArrayOfString)
  {
    switch (paramArrayOfString.length)
    {
    default:
      throw new IllegalArgumentException("Illegal argument count: " + paramArrayOfString.length);
    case 2:
      endGroupY(paramArrayOfString[1]);
    case 1:
    }
    endGroupX(paramArrayOfString[0]);
    return this;
  }

  public final CC sizeGroupY(String paramString)
  {
    this.ver.setSizeGroup(paramString);
    return this;
  }

  public final CC sizeGroup(String[] paramArrayOfString)
  {
    switch (paramArrayOfString.length)
    {
    default:
      throw new IllegalArgumentException("Illegal argument count: " + paramArrayOfString.length);
    case 2:
      sizeGroupY(paramArrayOfString[1]);
    case 1:
    }
    sizeGroupX(paramArrayOfString[0]);
    return this;
  }

  public final CC minHeight(String paramString)
  {
    this.ver.setSize(LayoutUtil.derive(this.ver.getSize(), ConstraintParser.parseUnitValue(paramString, false), null, null));
    return this;
  }

  public final CC height(String paramString)
  {
    this.ver.setSize(ConstraintParser.parseBoundSize(paramString, false, false));
    return this;
  }

  public final CC maxHeight(String paramString)
  {
    this.ver.setSize(LayoutUtil.derive(this.ver.getSize(), null, null, ConstraintParser.parseUnitValue(paramString, false)));
    return this;
  }

  public final CC gapY(String paramString1, String paramString2)
  {
    if (paramString1 != null)
      this.ver.setGapBefore(ConstraintParser.parseBoundSize(paramString1, true, false));
    if (paramString2 != null)
      this.ver.setGapAfter(ConstraintParser.parseBoundSize(paramString2, true, false));
    return this;
  }

  public final CC alignY(String paramString)
  {
    this.ver.setAlign(ConstraintParser.parseUnitValueOrAlign(paramString, false, null));
    return this;
  }

  public final CC growPrioY(int paramInt)
  {
    this.ver.setGrowPriority(paramInt);
    return this;
  }

  public final CC growY()
  {
    this.ver.setGrow(ResizeConstraint.WEIGHT_100);
    return this;
  }

  public final CC growY(Float paramFloat)
  {
    this.ver.setGrow(paramFloat);
    return this;
  }

  public final CC shrinkPrioY(int paramInt)
  {
    this.ver.setShrinkPriority(paramInt);
    return this;
  }

  public final CC shrinkY(float paramFloat)
  {
    this.ver.setShrink(new Float(paramFloat));
    return this;
  }

  public final CC hideMode(int paramInt)
  {
    setHideMode(paramInt);
    return this;
  }

  public final CC id(String paramString)
  {
    setId(paramString);
    return this;
  }

  public final CC tag(String paramString)
  {
    setTag(paramString);
    return this;
  }

  public final CC cell(int[] paramArrayOfInt)
  {
    switch (paramArrayOfInt.length)
    {
    default:
      throw new IllegalArgumentException("Illegal argument count: " + paramArrayOfInt.length);
    case 4:
      setSpanY(paramArrayOfInt[3]);
    case 3:
      setSpanX(paramArrayOfInt[2]);
    case 2:
      setCellY(paramArrayOfInt[1]);
    case 1:
    }
    setCellX(paramArrayOfInt[0]);
    return this;
  }

  public final CC span(int[] paramArrayOfInt)
  {
    if ((paramArrayOfInt == null) || (paramArrayOfInt.length == 0))
    {
      setSpanX(2097051);
      setSpanY(1);
    }
    else if (paramArrayOfInt.length == 1)
    {
      setSpanX(paramArrayOfInt[0]);
      setSpanY(1);
    }
    else
    {
      setSpanX(paramArrayOfInt[0]);
      setSpanY(paramArrayOfInt[1]);
    }
    return this;
  }

  public final CC gap(String[] paramArrayOfString)
  {
    switch (paramArrayOfString.length)
    {
    default:
      throw new IllegalArgumentException("Illegal argument count: " + paramArrayOfString.length);
    case 4:
      gapBottom(paramArrayOfString[3]);
    case 3:
      gapTop(paramArrayOfString[2]);
    case 2:
      gapRight(paramArrayOfString[1]);
    case 1:
    }
    gapLeft(paramArrayOfString[0]);
    return this;
  }

  public final CC gapBefore(String paramString)
  {
    this.hor.setGapBefore(ConstraintParser.parseBoundSize(paramString, true, true));
    return this;
  }

  public final CC gapAfter(String paramString)
  {
    this.hor.setGapAfter(ConstraintParser.parseBoundSize(paramString, true, true));
    return this;
  }

  public final CC gapTop(String paramString)
  {
    this.ver.setGapBefore(ConstraintParser.parseBoundSize(paramString, true, false));
    return this;
  }

  public final CC gapLeft(String paramString)
  {
    this.hor.setGapBefore(ConstraintParser.parseBoundSize(paramString, true, true));
    return this;
  }

  public final CC gapBottom(String paramString)
  {
    this.ver.setGapAfter(ConstraintParser.parseBoundSize(paramString, true, false));
    return this;
  }

  public final CC gapRight(String paramString)
  {
    this.hor.setGapAfter(ConstraintParser.parseBoundSize(paramString, true, true));
    return this;
  }

  public final CC spanY()
  {
    return spanY(2097051);
  }

  public final CC spanY(int paramInt)
  {
    setSpanY(paramInt);
    return this;
  }

  public final CC spanX()
  {
    return spanX(2097051);
  }

  public final CC spanX(int paramInt)
  {
    setSpanX(paramInt);
    return this;
  }

  public final CC push()
  {
    return pushX().pushY();
  }

  public final CC push(Float paramFloat1, Float paramFloat2)
  {
    return pushX(paramFloat1).pushY(paramFloat2);
  }

  public final CC pushY()
  {
    return pushY(ResizeConstraint.WEIGHT_100);
  }

  public final CC pushY(Float paramFloat)
  {
    setPushY(paramFloat);
    return this;
  }

  public final CC pushX()
  {
    return pushX(ResizeConstraint.WEIGHT_100);
  }

  public final CC pushX(Float paramFloat)
  {
    setPushX(paramFloat);
    return this;
  }

  public final CC split(int paramInt)
  {
    setSplit(paramInt);
    return this;
  }

  public final CC split()
  {
    setSplit(2097051);
    return this;
  }

  public final CC skip(int paramInt)
  {
    setSkip(paramInt);
    return this;
  }

  public final CC skip()
  {
    setSkip(1);
    return this;
  }

  public final CC external()
  {
    setExternal(true);
    return this;
  }

  public final CC flowX()
  {
    setFlowX(Boolean.TRUE);
    return this;
  }

  public final CC flowY()
  {
    setFlowX(Boolean.FALSE);
    return this;
  }

  public final CC grow()
  {
    growX();
    growY();
    return this;
  }

  public final CC newline()
  {
    setNewline(true);
    return this;
  }

  public final CC newline(String paramString)
  {
    BoundSize localBoundSize = ConstraintParser.parseBoundSize(paramString, true, (this.flowX != null) && (!this.flowX.booleanValue()));
    if (localBoundSize != null)
      setNewlineGapSize(localBoundSize);
    else
      setNewline(true);
    return this;
  }

  public final CC wrap()
  {
    setWrap(true);
    return this;
  }

  public final CC wrap(String paramString)
  {
    BoundSize localBoundSize = ConstraintParser.parseBoundSize(paramString, true, (this.flowX != null) && (!this.flowX.booleanValue()));
    if (localBoundSize != null)
      setWrapGapSize(localBoundSize);
    else
      setWrap(true);
    return this;
  }

  public final CC dockNorth()
  {
    setDockSide(0);
    return this;
  }

  public final CC dockWest()
  {
    setDockSide(1);
    return this;
  }

  public final CC dockSouth()
  {
    setDockSide(2);
    return this;
  }

  public final CC dockEast()
  {
    setDockSide(3);
    return this;
  }

  public final CC x(String paramString)
  {
    return corrPos(paramString, 0);
  }

  public final CC y(String paramString)
  {
    return corrPos(paramString, 1);
  }

  public final CC x2(String paramString)
  {
    return corrPos(paramString, 2);
  }

  public final CC y2(String paramString)
  {
    return corrPos(paramString, 3);
  }

  private final CC corrPos(String paramString, int paramInt)
  {
    UnitValue[] arrayOfUnitValue = getPos();
    if (arrayOfUnitValue == null)
      arrayOfUnitValue = new UnitValue[4];
    arrayOfUnitValue[paramInt] = ConstraintParser.parseUnitValue(paramString, paramInt % 2 == 0 ? 1 : false);
    setPos(arrayOfUnitValue);
    setBoundsInGrid(true);
    return this;
  }

  public final CC pos(String paramString1, String paramString2)
  {
    UnitValue[] arrayOfUnitValue = getPos();
    if (arrayOfUnitValue == null)
      arrayOfUnitValue = new UnitValue[4];
    arrayOfUnitValue[0] = ConstraintParser.parseUnitValue(paramString1, true);
    arrayOfUnitValue[1] = ConstraintParser.parseUnitValue(paramString2, false);
    setPos(arrayOfUnitValue);
    setBoundsInGrid(false);
    return this;
  }

  public final CC pos(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    setPos(new UnitValue[] { ConstraintParser.parseUnitValue(paramString1, true), ConstraintParser.parseUnitValue(paramString2, false), ConstraintParser.parseUnitValue(paramString3, true), ConstraintParser.parseUnitValue(paramString4, false) });
    setBoundsInGrid(false);
    return this;
  }

  public final CC pad(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    setPadding(new UnitValue[] { new UnitValue(paramInt1), new UnitValue(paramInt2), new UnitValue(paramInt3), new UnitValue(paramInt4) });
    return this;
  }

  public final CC pad(String paramString)
  {
    setPadding(paramString != null ? ConstraintParser.parseInsets(paramString, false) : null);
    return this;
  }

  public DimConstraint getHorizontal()
  {
    return this.hor;
  }

  public void setHorizontal(DimConstraint paramDimConstraint)
  {
    this.hor = (paramDimConstraint != null ? paramDimConstraint : new DimConstraint());
  }

  public DimConstraint getVertical()
  {
    return this.ver;
  }

  public void setVertical(DimConstraint paramDimConstraint)
  {
    this.ver = (paramDimConstraint != null ? paramDimConstraint : new DimConstraint());
  }

  public DimConstraint getDimConstraint(boolean paramBoolean)
  {
    return paramBoolean ? this.hor : this.ver;
  }

  public UnitValue[] getPos()
  {
    return this.pos != null ? new UnitValue[] { this.pos[0], this.pos[1], this.pos[2], this.pos[3] } : null;
  }

  public void setPos(UnitValue[] paramArrayOfUnitValue)
  {
    this.pos = (paramArrayOfUnitValue != null ? new UnitValue[] { paramArrayOfUnitValue[0], paramArrayOfUnitValue[1], paramArrayOfUnitValue[2], paramArrayOfUnitValue[3] } : null);
    this.linkTargets = null;
  }

  public boolean isBoundsInGrid()
  {
    return this.boundsInGrid;
  }

  void setBoundsInGrid(boolean paramBoolean)
  {
    this.boundsInGrid = paramBoolean;
  }

  public int getCellX()
  {
    return this.cellX;
  }

  public void setCellX(int paramInt)
  {
    this.cellX = paramInt;
  }

  public int getCellY()
  {
    return this.cellX < 0 ? -1 : this.cellY;
  }

  public void setCellY(int paramInt)
  {
    if (paramInt < 0)
      this.cellX = -1;
    this.cellY = (paramInt < 0 ? 0 : paramInt);
  }

  public int getDockSide()
  {
    return this.dock;
  }

  public void setDockSide(int paramInt)
  {
    if ((paramInt < -1) || (paramInt > 3))
      throw new IllegalArgumentException("Illegal dock side: " + paramInt);
    this.dock = paramInt;
  }

  public boolean isExternal()
  {
    return this.external;
  }

  public void setExternal(boolean paramBoolean)
  {
    this.external = paramBoolean;
  }

  public Boolean getFlowX()
  {
    return this.flowX;
  }

  public void setFlowX(Boolean paramBoolean)
  {
    this.flowX = paramBoolean;
  }

  public int getHideMode()
  {
    return this.hideMode;
  }

  public void setHideMode(int paramInt)
  {
    if ((paramInt < -1) || (paramInt > 3))
      throw new IllegalArgumentException("Wrong hideMode: " + paramInt);
    this.hideMode = paramInt;
  }

  public String getId()
  {
    return this.id;
  }

  public void setId(String paramString)
  {
    this.id = paramString;
  }

  public UnitValue[] getPadding()
  {
    return this.padding != null ? new UnitValue[] { this.padding[0], this.padding[1], this.padding[2], this.padding[3] } : null;
  }

  public void setPadding(UnitValue[] paramArrayOfUnitValue)
  {
    this.padding = (paramArrayOfUnitValue != null ? new UnitValue[] { paramArrayOfUnitValue[0], paramArrayOfUnitValue[1], paramArrayOfUnitValue[2], paramArrayOfUnitValue[3] } : null);
  }

  public int getSkip()
  {
    return this.skip;
  }

  public void setSkip(int paramInt)
  {
    this.skip = paramInt;
  }

  public int getSpanX()
  {
    return this.spanX;
  }

  public void setSpanX(int paramInt)
  {
    this.spanX = paramInt;
  }

  public int getSpanY()
  {
    return this.spanY;
  }

  public void setSpanY(int paramInt)
  {
    this.spanY = paramInt;
  }

  public Float getPushX()
  {
    return this.pushX;
  }

  public void setPushX(Float paramFloat)
  {
    this.pushX = paramFloat;
  }

  public Float getPushY()
  {
    return this.pushY;
  }

  public void setPushY(Float paramFloat)
  {
    this.pushY = paramFloat;
  }

  public int getSplit()
  {
    return this.split;
  }

  public void setSplit(int paramInt)
  {
    this.split = paramInt;
  }

  public String getTag()
  {
    return this.tag;
  }

  public void setTag(String paramString)
  {
    this.tag = paramString;
  }

  public boolean isWrap()
  {
    return this.wrap != null;
  }

  public void setWrap(boolean paramBoolean)
  {
    this.wrap = (paramBoolean ? this.wrap : this.wrap == null ? DEF_GAP : null);
  }

  public BoundSize getWrapGapSize()
  {
    return this.wrap == DEF_GAP ? null : this.wrap;
  }

  public void setWrapGapSize(BoundSize paramBoundSize)
  {
    this.wrap = (paramBoundSize == null ? null : this.wrap != null ? DEF_GAP : paramBoundSize);
  }

  public boolean isNewline()
  {
    return this.newline != null;
  }

  public void setNewline(boolean paramBoolean)
  {
    this.newline = (paramBoolean ? this.newline : this.newline == null ? DEF_GAP : null);
  }

  public BoundSize getNewlineGapSize()
  {
    return this.newline == DEF_GAP ? null : this.newline;
  }

  public void setNewlineGapSize(BoundSize paramBoundSize)
  {
    this.newline = (paramBoundSize == null ? null : this.newline != null ? DEF_GAP : paramBoundSize);
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
    if (getClass() == CC.class)
      LayoutUtil.writeAsXML(paramObjectOutput, this);
  }
}

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     net.miginfocom.layout.CC
 * JD-Core Version:    0.6.0
 */