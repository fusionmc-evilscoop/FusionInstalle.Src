package net.miginfocom.layout;

import java.util.HashMap;

public class IDEUtil
{
  public static final UnitValue ZERO = UnitValue.ZERO;
  public static final UnitValue TOP = UnitValue.TOP;
  public static final UnitValue LEADING = UnitValue.LEADING;
  public static final UnitValue LEFT = UnitValue.LEFT;
  public static final UnitValue CENTER = UnitValue.CENTER;
  public static final UnitValue TRAILING = UnitValue.TRAILING;
  public static final UnitValue RIGHT = UnitValue.RIGHT;
  public static final UnitValue BOTTOM = UnitValue.BOTTOM;
  public static final UnitValue LABEL = UnitValue.LABEL;
  public static final UnitValue INF = UnitValue.INF;
  public static final UnitValue BASELINE_IDENTITY = UnitValue.BASELINE_IDENTITY;
  private static final String[] X_Y_STRINGS = { "x", "y", "x2", "y2" };

  public String getIDEUtilVersion()
  {
    return "1.0";
  }

  public static HashMap<Object, int[]> getGridPositions(Object paramObject)
  {
    return Grid.getGridPositions(paramObject);
  }

  public static int[][] getRowSizes(Object paramObject)
  {
    return Grid.getSizesAndIndexes(paramObject, true);
  }

  public static int[][] getColumnSizes(Object paramObject)
  {
    return Grid.getSizesAndIndexes(paramObject, false);
  }

  public static final String getConstraintString(AC paramAC, boolean paramBoolean1, boolean paramBoolean2)
  {
    StringBuffer localStringBuffer = new StringBuffer(32);
    DimConstraint[] arrayOfDimConstraint = paramAC.getConstaints();
    BoundSize localBoundSize1 = paramBoolean2 ? PlatformDefaults.getGridGapX() : PlatformDefaults.getGridGapY();
    for (int i = 0; i < arrayOfDimConstraint.length; i++)
    {
      DimConstraint localDimConstraint = arrayOfDimConstraint[i];
      addRowDimConstraintString(localDimConstraint, localStringBuffer, paramBoolean1);
      if (i >= arrayOfDimConstraint.length - 1)
        continue;
      BoundSize localBoundSize2 = localDimConstraint.getGapAfter();
      if ((localBoundSize2 == localBoundSize1) || (localBoundSize2 == null))
        localBoundSize2 = arrayOfDimConstraint[(i + 1)].getGapBefore();
      if (localBoundSize2 != null)
      {
        String str = getBS(localBoundSize2);
        if (paramBoolean1)
          localStringBuffer.append(".gap(\"").append(str).append("\")");
        else
          localStringBuffer.append(str);
      }
      else
      {
        if (!paramBoolean1)
          continue;
        localStringBuffer.append(".gap()");
      }
    }
    return localStringBuffer.toString();
  }

  private static final void addRowDimConstraintString(DimConstraint paramDimConstraint, StringBuffer paramStringBuffer, boolean paramBoolean)
  {
    int i = paramDimConstraint.getGrowPriority();
    int j = paramStringBuffer.length();
    BoundSize localBoundSize = paramDimConstraint.getSize();
    if (!localBoundSize.isUnset())
      if (paramBoolean)
        paramStringBuffer.append(".size(\"").append(getBS(localBoundSize)).append("\")");
      else
        paramStringBuffer.append(',').append(getBS(localBoundSize));
    if (i != 100)
      if (paramBoolean)
        paramStringBuffer.append(".growPrio(").append(i).append("\")");
      else
        paramStringBuffer.append(",growprio ").append(i);
    Float localFloat1 = paramDimConstraint.getGrow();
    if (localFloat1 != null)
    {
      String str1 = localFloat1.floatValue() != 100.0F ? floatToString(localFloat1.floatValue(), paramBoolean) : "";
      if (paramBoolean)
      {
        if (str1.length() == 0)
          paramStringBuffer.append(".grow()");
        else
          paramStringBuffer.append(".grow(\"").append(str1).append("\")");
      }
      else
        paramStringBuffer.append(",grow").append(str1.length() > 0 ? " " + str1 : "");
    }
    int k = paramDimConstraint.getShrinkPriority();
    if (k != 100)
      if (paramBoolean)
        paramStringBuffer.append(".shrinkPrio(").append(k).append("\")");
      else
        paramStringBuffer.append(",shrinkprio ").append(k);
    Float localFloat2 = paramDimConstraint.getShrink();
    if ((localFloat2 != null) && (localFloat2.intValue() != 100))
    {
      str2 = floatToString(localFloat2.floatValue(), paramBoolean);
      if (paramBoolean)
        paramStringBuffer.append(".shrink(\"").append(str2).append("\")");
      else
        paramStringBuffer.append(",shrink ").append(str2);
    }
    String str2 = paramDimConstraint.getEndGroup();
    if (str2 != null)
      if (paramBoolean)
        paramStringBuffer.append(".endGroup(\"").append(str2).append("\")");
      else
        paramStringBuffer.append(",endgroup ").append(str2);
    String str3 = paramDimConstraint.getSizeGroup();
    if (str3 != null)
      if (paramBoolean)
        paramStringBuffer.append(".sizeGroup(\"").append(str3).append("\")");
      else
        paramStringBuffer.append(",sizegroup ").append(str3);
    UnitValue localUnitValue = paramDimConstraint.getAlign();
    if (localUnitValue != null)
      if (paramBoolean)
      {
        paramStringBuffer.append(".align(\"").append(getUV(localUnitValue)).append("\")");
      }
      else
      {
        String str4 = getUV(localUnitValue);
        String str5 = (str4.equals("top")) || (str4.equals("bottom")) || (str4.equals("left")) || (str4.equals("label")) || (str4.equals("leading")) || (str4.equals("center")) || (str4.equals("trailing")) || (str4.equals("right")) || (str4.equals("baseline")) ? "" : "align ";
        paramStringBuffer.append(',').append(str5).append(str4);
      }
    if (paramDimConstraint.isNoGrid())
      if (paramBoolean)
        paramStringBuffer.append(".noGrid()");
      else
        paramStringBuffer.append(",nogrid");
    if (paramDimConstraint.isFill())
      if (paramBoolean)
        paramStringBuffer.append(".fill()");
      else
        paramStringBuffer.append(",fill");
    if (!paramBoolean)
      if (paramStringBuffer.length() > j)
      {
        paramStringBuffer.setCharAt(j, '[');
        paramStringBuffer.append(']');
      }
      else
      {
        paramStringBuffer.append("[]");
      }
  }

  private static final void addComponentDimConstraintString(DimConstraint paramDimConstraint, StringBuffer paramStringBuffer, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    int i = paramDimConstraint.getGrowPriority();
    if (i != 100)
      if (paramBoolean1)
        paramStringBuffer.append(paramBoolean2 ? ".growPrioX(" : ".growPrioY(").append(i).append(')');
      else
        paramStringBuffer.append(paramBoolean2 ? ",growpriox " : ",growprioy ").append(i);
    if (!paramBoolean3)
    {
      Float localFloat = paramDimConstraint.getGrow();
      if (localFloat != null)
      {
        localObject = localFloat.floatValue() != 100.0F ? floatToString(localFloat.floatValue(), paramBoolean1) : "";
        if (paramBoolean1)
          paramStringBuffer.append(paramBoolean2 ? ".growX(" : ".growY(").append((String)localObject).append(')');
        else
          paramStringBuffer.append(paramBoolean2 ? ",growx" : ",growy").append(((String)localObject).length() > 0 ? " " + (String)localObject : "");
      }
    }
    int j = paramDimConstraint.getShrinkPriority();
    if (j != 100)
      if (paramBoolean1)
        paramStringBuffer.append(paramBoolean2 ? ".shrinkPrioX(" : ".shrinkPrioY(").append(j).append(')');
      else
        paramStringBuffer.append(paramBoolean2 ? ",shrinkpriox " : ",shrinkprioy ").append(j);
    Object localObject = paramDimConstraint.getShrink();
    if ((localObject != null) && (((Float)localObject).intValue() != 100))
    {
      str1 = floatToString(((Float)localObject).floatValue(), paramBoolean1);
      if (paramBoolean1)
        paramStringBuffer.append(paramBoolean2 ? ".shrinkX(" : ".shrinkY(").append(str1).append(')');
      else
        paramStringBuffer.append(paramBoolean2 ? ",shrinkx " : ",shrinky ").append(str1);
    }
    String str1 = paramDimConstraint.getEndGroup();
    if (str1 != null)
      if (paramBoolean1)
        paramStringBuffer.append(paramBoolean2 ? ".endGroupX(\"" : ".endGroupY(\"").append(str1).append("\")");
      else
        paramStringBuffer.append(paramBoolean2 ? ",endgroupx " : ",endgroupy ").append(str1);
    String str2 = paramDimConstraint.getSizeGroup();
    if (str2 != null)
      if (paramBoolean1)
        paramStringBuffer.append(paramBoolean2 ? ".sizeGroupX(\"" : ".sizeGroupY(\"").append(str2).append("\")");
      else
        paramStringBuffer.append(paramBoolean2 ? ",sizegroupx " : ",sizegroupy ").append(str2);
    appendBoundSize(paramDimConstraint.getSize(), paramStringBuffer, paramBoolean2, paramBoolean1);
    UnitValue localUnitValue = paramDimConstraint.getAlign();
    if (localUnitValue != null)
      if (paramBoolean1)
        paramStringBuffer.append(paramBoolean2 ? ".alignX(\"" : ".alignY(\"").append(getUV(localUnitValue)).append("\")");
      else
        paramStringBuffer.append(paramBoolean2 ? ",alignx " : ",aligny ").append(getUV(localUnitValue));
    BoundSize localBoundSize1 = paramDimConstraint.getGapBefore();
    BoundSize localBoundSize2 = paramDimConstraint.getGapAfter();
    if ((localBoundSize1 != null) || (localBoundSize2 != null))
      if (paramBoolean1)
        paramStringBuffer.append(paramBoolean2 ? ".gapX(\"" : ".gapY(\"").append(getBS(localBoundSize1)).append("\", \"").append(getBS(localBoundSize2)).append("\")");
      else
        paramStringBuffer.append(paramBoolean2 ? ",gapx " : ",gapy ").append(getBS(localBoundSize1)).append(' ').append(getBS(localBoundSize2));
  }

  private static void appendBoundSize(BoundSize paramBoundSize, StringBuffer paramStringBuffer, boolean paramBoolean1, boolean paramBoolean2)
  {
    if (!paramBoundSize.isUnset())
      if (paramBoundSize.getPreferred() == null)
      {
        if (paramBoundSize.getMin() == null)
        {
          if (paramBoolean2)
            paramStringBuffer.append(paramBoolean1 ? ".maxWidth(\"" : ".maxHeight(\"").append(getUV(paramBoundSize.getMax())).append("\")");
          else
            paramStringBuffer.append(paramBoolean1 ? ",wmax " : ",hmax ").append(getUV(paramBoundSize.getMax()));
        }
        else if (paramBoundSize.getMax() == null)
        {
          if (paramBoolean2)
            paramStringBuffer.append(paramBoolean1 ? ".minWidth(\"" : ".minHeight(\"").append(getUV(paramBoundSize.getMin())).append("\")");
          else
            paramStringBuffer.append(paramBoolean1 ? ",wmin " : ",hmin ").append(getUV(paramBoundSize.getMin()));
        }
        else if (paramBoolean2)
          paramStringBuffer.append(paramBoolean1 ? ".width(\"" : ".height(\"").append(getUV(paramBoundSize.getMin())).append("::").append(getUV(paramBoundSize.getMax())).append("\")");
        else
          paramStringBuffer.append(paramBoolean1 ? ",width " : ",height ").append(getUV(paramBoundSize.getMin())).append("::").append(getUV(paramBoundSize.getMax()));
      }
      else if (paramBoolean2)
        paramStringBuffer.append(paramBoolean1 ? ".width(\"" : ".height(\"").append(getBS(paramBoundSize)).append("\")");
      else
        paramStringBuffer.append(paramBoolean1 ? ",width " : ",height ").append(getBS(paramBoundSize));
  }

  public static final String getConstraintString(CC paramCC, boolean paramBoolean)
  {
    StringBuffer localStringBuffer = new StringBuffer(16);
    if (paramCC.isNewline())
      localStringBuffer.append(paramBoolean ? ".newline()" : ",newline");
    if (paramCC.isExternal())
      localStringBuffer.append(paramBoolean ? ".external()" : ",external");
    Boolean localBoolean = paramCC.getFlowX();
    if (localBoolean != null)
      if (paramBoolean)
        localStringBuffer.append(localBoolean.booleanValue() ? ".flowX()" : ".flowY()");
      else
        localStringBuffer.append(localBoolean.booleanValue() ? ",flowx" : ",flowy");
    UnitValue[] arrayOfUnitValue1 = paramCC.getPadding();
    if (arrayOfUnitValue1 != null)
    {
      localStringBuffer.append(paramBoolean ? ".pad(\"" : ",pad ");
      for (int i = 0; i < arrayOfUnitValue1.length; i++)
        localStringBuffer.append(getUV(arrayOfUnitValue1[i])).append(i < arrayOfUnitValue1.length - 1 ? " " : "");
      if (paramBoolean)
        localStringBuffer.append("\")");
    }
    UnitValue[] arrayOfUnitValue2 = paramCC.getPos();
    if (arrayOfUnitValue2 != null)
    {
      int j;
      if (paramCC.isBoundsInGrid())
      {
        for (j = 0; j < 4; j++)
        {
          if (arrayOfUnitValue2[j] == null)
            continue;
          if (paramBoolean)
            localStringBuffer.append('.').append(X_Y_STRINGS[j]).append("(\"").append(getUV(arrayOfUnitValue2[j])).append("\")");
          else
            localStringBuffer.append(',').append(X_Y_STRINGS[j]).append(getUV(arrayOfUnitValue2[j]));
        }
      }
      else
      {
        localStringBuffer.append(paramBoolean ? ".pos(\"" : ",pos ");
        j = (arrayOfUnitValue2[2] != null) || (arrayOfUnitValue2[3] != null) ? 4 : 2;
        for (int k = 0; k < j; k++)
          localStringBuffer.append(getUV(arrayOfUnitValue2[k])).append(k < j - 1 ? " " : "");
        if (paramBoolean)
          localStringBuffer.append("\")");
      }
    }
    String str1 = paramCC.getId();
    if (str1 != null)
      if (paramBoolean)
        localStringBuffer.append(".id(\"").append(str1).append("\")");
      else
        localStringBuffer.append(",id ").append(str1);
    String str2 = paramCC.getTag();
    if (str2 != null)
      if (paramBoolean)
        localStringBuffer.append(".tag(\"").append(str2).append("\")");
      else
        localStringBuffer.append(",tag ").append(str2);
    int m = paramCC.getHideMode();
    if (m >= 0)
      if (paramBoolean)
        localStringBuffer.append(".hideMode(").append(m).append(')');
      else
        localStringBuffer.append(",hideMode ").append(m);
    int n = paramCC.getSkip();
    if (n > 0)
      if (paramBoolean)
        localStringBuffer.append(".skip(").append(n).append(')');
      else
        localStringBuffer.append(",skip ").append(n);
    int i1 = paramCC.getSplit();
    if (i1 > 1)
    {
      String str3 = i1 == 2097051 ? "" : String.valueOf(i1);
      if (paramBoolean)
        localStringBuffer.append(".split(").append(str3).append(')');
      else
        localStringBuffer.append(",split ").append(str3);
    }
    int i2 = paramCC.getCellX();
    int i3 = paramCC.getCellY();
    int i4 = paramCC.getSpanX();
    int i5 = paramCC.getSpanY();
    if ((i2 >= 0) && (i3 >= 0))
    {
      if (paramBoolean)
      {
        localStringBuffer.append(".cell(").append(i2).append(", ").append(i3);
        if ((i4 > 1) || (i5 > 1))
          localStringBuffer.append(", ").append(i4).append(", ").append(i5);
        localStringBuffer.append(')');
      }
      else
      {
        localStringBuffer.append(",cell ").append(i2).append(' ').append(i3);
        if ((i4 > 1) || (i5 > 1))
          localStringBuffer.append(' ').append(i4).append(' ').append(i5);
      }
    }
    else if ((i4 > 1) || (i5 > 1))
    {
      if ((i4 > 1) && (i5 > 1))
        localStringBuffer.append(paramBoolean ? ".span(" : ",span ").append(i4).append(paramBoolean ? ", " : " ").append(i5);
      else if (i4 > 1)
        localStringBuffer.append(paramBoolean ? ".spanX(" : ",spanx ").append(i4 == 2097051 ? "" : String.valueOf(i4));
      else if (i5 > 1)
        localStringBuffer.append(paramBoolean ? ".spanY(" : ",spany ").append(i5 == 2097051 ? "" : String.valueOf(i5));
      if (paramBoolean)
        localStringBuffer.append(')');
    }
    Float localFloat1 = paramCC.getPushX();
    Float localFloat2 = paramCC.getPushY();
    if ((localFloat1 != null) || (localFloat2 != null))
    {
      if ((localFloat1 != null) && (localFloat2 != null))
      {
        localStringBuffer.append(paramBoolean ? ".push(" : ",push ");
        if ((localFloat1.floatValue() != 100.0D) || (localFloat2.floatValue() != 100.0D))
          localStringBuffer.append(localFloat1).append(paramBoolean ? ", " : " ").append(localFloat2);
      }
      else if (localFloat1 != null)
      {
        localStringBuffer.append(paramBoolean ? ".pushX(" : ",pushx ").append(localFloat1.floatValue() == 100.0F ? "" : String.valueOf(localFloat1));
      }
      else if (localFloat2 != null)
      {
        localStringBuffer.append(paramBoolean ? ".pushY(" : ",pushy ").append(localFloat2.floatValue() == 100.0F ? "" : String.valueOf(localFloat2));
      }
      if (paramBoolean)
        localStringBuffer.append(')');
    }
    int i6 = paramCC.getDockSide();
    if (i6 >= 0)
    {
      String str4 = CC.DOCK_SIDES[i6];
      if (paramBoolean)
        localStringBuffer.append(".dock").append(Character.toUpperCase(str4.charAt(0))).append(str4.substring(1)).append("()");
      else
        localStringBuffer.append(",").append(str4);
    }
    boolean bool = (paramCC.getHorizontal().getGrow() != null) && (paramCC.getHorizontal().getGrow().intValue() == 100) && (paramCC.getVertical().getGrow() != null) && (paramCC.getVertical().getGrow().intValue() == 100);
    addComponentDimConstraintString(paramCC.getHorizontal(), localStringBuffer, paramBoolean, true, bool);
    addComponentDimConstraintString(paramCC.getVertical(), localStringBuffer, paramBoolean, false, bool);
    if (bool)
      localStringBuffer.append(paramBoolean ? ".grow()" : ",grow");
    if (paramCC.isWrap())
      localStringBuffer.append(paramBoolean ? ".wrap()" : ",wrap");
    String str5 = localStringBuffer.toString();
    return (str5.length() == 0) || (str5.charAt(0) != ',') ? str5 : str5.substring(1);
  }

  public static final String getConstraintString(LC paramLC, boolean paramBoolean)
  {
    StringBuffer localStringBuffer = new StringBuffer(16);
    if (!paramLC.isFlowX())
      localStringBuffer.append(paramBoolean ? ".flowY()" : ",flowy");
    boolean bool1 = paramLC.isFillX();
    boolean bool2 = paramLC.isFillY();
    if ((bool1) || (bool2))
      if (bool1 == bool2)
        localStringBuffer.append(paramBoolean ? ".fill()" : ",fill");
      else
        localStringBuffer.append(bool1 ? ",fillx" : paramBoolean ? ".fillY()" : bool1 ? ".fillX()" : ",filly");
    Boolean localBoolean = paramLC.getLeftToRight();
    if (localBoolean != null)
      if (paramBoolean)
        localStringBuffer.append(".leftToRight(").append(localBoolean).append(')');
      else
        localStringBuffer.append(localBoolean.booleanValue() ? ",ltr" : ",rtl");
    String str1;
    if ((!paramLC.getPackWidth().isUnset()) || (!paramLC.getPackHeight().isUnset()))
      if (paramBoolean)
      {
        localObject = getBS(paramLC.getPackWidth());
        str1 = getBS(paramLC.getPackHeight());
        localStringBuffer.append(".pack(");
        if ((((String)localObject).equals("pref")) && (str1.equals("pref")))
          localStringBuffer.append(')');
        else
          localStringBuffer.append('"').append((String)localObject).append("\", \"").append(str1).append("\")");
      }
      else
      {
        localStringBuffer.append(",pack");
        localObject = getBS(paramLC.getPackWidth()) + " " + getBS(paramLC.getPackHeight());
        if (!((String)localObject).equals("pref pref"))
          localStringBuffer.append(' ').append((String)localObject);
      }
    if ((paramLC.getPackWidthAlign() != 0.5F) || (paramLC.getPackHeightAlign() != 1.0F))
      if (paramBoolean)
        localStringBuffer.append(".packAlign(").append(floatToString(paramLC.getPackWidthAlign(), paramBoolean)).append(", ").append(floatToString(paramLC.getPackHeightAlign(), paramBoolean)).append(')');
      else
        localStringBuffer.append(",packalign ").append(floatToString(paramLC.getPackWidthAlign(), paramBoolean)).append(' ').append(floatToString(paramLC.getPackHeightAlign(), paramBoolean));
    if (!paramLC.isTopToBottom())
      localStringBuffer.append(paramBoolean ? ".bottomToTop()" : ",btt");
    Object localObject = paramLC.getInsets();
    if (localObject != null)
    {
      str1 = LayoutUtil.getCCString(localObject);
      if (str1 != null)
      {
        if (paramBoolean)
          localStringBuffer.append(".insets(\"").append(str1).append("\")");
        else
          localStringBuffer.append(",insets ").append(str1);
      }
      else
      {
        localStringBuffer.append(paramBoolean ? ".insets(\"" : ",insets ");
        for (int j = 0; j < localObject.length; j++)
          localStringBuffer.append(getUV(localObject[j])).append(j < localObject.length - 1 ? " " : "");
        if (paramBoolean)
          localStringBuffer.append("\")");
      }
    }
    if (paramLC.isNoGrid())
      localStringBuffer.append(paramBoolean ? ".noGrid()" : ",nogrid");
    if (!paramLC.isVisualPadding())
      localStringBuffer.append(paramBoolean ? ".noVisualPadding()" : ",novisualpadding");
    int i = paramLC.getHideMode();
    if (i > 0)
      if (paramBoolean)
        localStringBuffer.append(".hideMode(").append(i).append(')');
      else
        localStringBuffer.append(",hideMode ").append(i);
    appendBoundSize(paramLC.getWidth(), localStringBuffer, true, paramBoolean);
    appendBoundSize(paramLC.getHeight(), localStringBuffer, false, paramBoolean);
    UnitValue localUnitValue1 = paramLC.getAlignX();
    UnitValue localUnitValue2 = paramLC.getAlignY();
    if ((localUnitValue1 != null) || (localUnitValue2 != null))
    {
      if ((localUnitValue1 != null) && (localUnitValue2 != null))
        localStringBuffer.append(paramBoolean ? ".align(\"" : ",align ").append(getUV(localUnitValue1)).append(' ').append(getUV(localUnitValue2));
      else if (localUnitValue1 != null)
        localStringBuffer.append(paramBoolean ? ".alignX(\"" : ",alignx ").append(getUV(localUnitValue1));
      else if (localUnitValue2 != null)
        localStringBuffer.append(paramBoolean ? ".alignY(\"" : ",aligny ").append(getUV(localUnitValue2));
      if (paramBoolean)
        localStringBuffer.append("\")");
    }
    BoundSize localBoundSize1 = paramLC.getGridGapX();
    BoundSize localBoundSize2 = paramLC.getGridGapY();
    if ((localBoundSize1 != null) || (localBoundSize2 != null))
    {
      if ((localBoundSize1 != null) && (localBoundSize2 != null))
        localStringBuffer.append(paramBoolean ? ".gridGap(\"" : ",gap ").append(getBS(localBoundSize1)).append(' ').append(getBS(localBoundSize2));
      else if (localBoundSize1 != null)
        localStringBuffer.append(paramBoolean ? ".gridGapX(\"" : ",gapx ").append(getBS(localBoundSize1));
      else if (localBoundSize2 != null)
        localStringBuffer.append(paramBoolean ? ".gridGapY(\"" : ",gapy ").append(getBS(localBoundSize2));
      if (paramBoolean)
        localStringBuffer.append("\")");
    }
    int k = paramLC.getWrapAfter();
    if (k != 2097051)
    {
      String str2 = k > 0 ? String.valueOf(k) : "";
      if (paramBoolean)
        localStringBuffer.append(".wrap(").append(str2).append(')');
      else
        localStringBuffer.append(",wrap ").append(str2);
    }
    int m = paramLC.getDebugMillis();
    if (m > 0)
      if (paramBoolean)
        localStringBuffer.append(".debug(").append(m).append(')');
      else
        localStringBuffer.append(",debug ").append(m);
    String str3 = localStringBuffer.toString();
    return (str3.length() == 0) || (str3.charAt(0) != ',') ? str3 : (String)str3.substring(1);
  }

  private static String getUV(UnitValue paramUnitValue)
  {
    return paramUnitValue != null ? paramUnitValue.getConstraintString() : "null";
  }

  private static String getBS(BoundSize paramBoundSize)
  {
    return paramBoundSize != null ? paramBoundSize.getConstraintString() : "null";
  }

  private static final String floatToString(float paramFloat, boolean paramBoolean)
  {
    String str = String.valueOf(paramFloat);
    return str + (paramBoolean ? "f" : "");
  }
}

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     net.miginfocom.layout.IDEUtil
 * JD-Core Version:    0.6.0
 */