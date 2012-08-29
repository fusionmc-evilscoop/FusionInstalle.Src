package net.miginfocom.layout;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.ArrayList<Lnet.miginfocom.layout.Grid.LinkedDimGroup;>;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.WeakHashMap;

public final class Grid
{
  public static final boolean TEST_GAPS = true;
  private static final Float[] GROW_100 = { ResizeConstraint.WEIGHT_100 };
  private static final DimConstraint DOCK_DIM_CONSTRAINT = new DimConstraint();
  private static final int MAX_GRID = 30000;
  private static final int MAX_DOCK_GRID = 32767;
  private static final ResizeConstraint GAP_RC_CONST;
  private static final ResizeConstraint GAP_RC_CONST_PUSH;
  private final LC lc;
  private final ContainerWrapper container;
  private final LinkedHashMap<Integer, Cell> grid = new LinkedHashMap();
  private HashMap<Integer, BoundSize> wrapGapMap = null;
  private final TreeSet<Integer> rowIndexes = new TreeSet();
  private final TreeSet<Integer> colIndexes = new TreeSet();
  private final AC rowConstr;
  private final AC colConstr;
  private FlowSizeSpec colFlowSpecs = null;
  private FlowSizeSpec rowFlowSpecs = null;
  private ArrayList<LinkedDimGroup>[] colGroupLists;
  private ArrayList<LinkedDimGroup>[] rowGroupLists;
  private int[] width = null;
  private int[] height = null;
  private ArrayList<int[]> debugRects = null;
  private HashMap<String, Boolean> linkTargetIDs = null;
  private final int dockOffY;
  private final int dockOffX;
  private final Float[] pushXs;
  private final Float[] pushYs;
  private final ArrayList<LayoutCallback> callbackList;
  private static WeakHashMap[] PARENT_ROWCOL_SIZES_MAP;
  private static WeakHashMap<Object, LinkedHashMap<Integer, Cell>> PARENT_GRIDPOS_MAP;

  public Grid(ContainerWrapper paramContainerWrapper, LC paramLC, AC paramAC1, AC paramAC2, Map<ComponentWrapper, CC> paramMap, ArrayList<LayoutCallback> paramArrayList)
  {
    this.lc = paramLC;
    this.rowConstr = paramAC1;
    this.colConstr = paramAC2;
    this.container = paramContainerWrapper;
    this.callbackList = paramArrayList;
    int i = paramLC.getWrapAfter() != 0 ? paramLC.getWrapAfter() : (paramLC.isFlowX() ? paramAC2 : paramAC1).getConstaints().length;
    ComponentWrapper[] arrayOfComponentWrapper = paramContainerWrapper.getComponents();
    boolean bool1 = false;
    int j = 0;
    int k = 0;
    int m = 0;
    int[] arrayOfInt1 = new int[2];
    ArrayList localArrayList1 = new ArrayList(2);
    DimConstraint[] arrayOfDimConstraint = (paramLC.isFlowX() ? paramAC1 : paramAC2).getConstaints();
    int n = 0;
    int i1 = 0;
    int[] arrayOfInt2 = null;
    LinkHandler.clearTemporaryBounds(paramContainerWrapper.getLayout());
    int i2 = 0;
    Object localObject2;
    Object localObject3;
    Object localObject4;
    Object localObject5;
    while (i2 < arrayOfComponentWrapper.length)
    {
      localObject1 = arrayOfComponentWrapper[i2];
      localObject2 = getCC((ComponentWrapper)localObject1, paramMap);
      addLinkIDs((CC)localObject2);
      int i5 = ((CC)localObject2).getHideMode() != -1 ? ((CC)localObject2).getHideMode() : ((ComponentWrapper)localObject1).isVisible() ? -1 : paramLC.getHideMode();
      if (i5 == 3)
      {
        setLinkedBounds((ComponentWrapper)localObject1, (CC)localObject2, ((ComponentWrapper)localObject1).getX(), ((ComponentWrapper)localObject1).getY(), ((ComponentWrapper)localObject1).getWidth(), ((ComponentWrapper)localObject1).getHeight(), ((CC)localObject2).isExternal());
        i2++;
        continue;
      }
      if (((CC)localObject2).getHorizontal().getSizeGroup() != null)
        n++;
      if (((CC)localObject2).getVertical().getSizeGroup() != null)
        i1++;
      localObject3 = getPos((ComponentWrapper)localObject1, (CC)localObject2);
      BoundSize[] arrayOfBoundSize = getCallbackSize((ComponentWrapper)localObject1);
      if ((localObject3 != null) || (((CC)localObject2).isExternal()))
      {
        localObject4 = new CompWrap((ComponentWrapper)localObject1, (CC)localObject2, i5, localObject3, arrayOfBoundSize, null);
        localObject5 = (Cell)this.grid.get(null);
        if (localObject5 == null)
          this.grid.put(null, new Cell((CompWrap)localObject4, null));
        else
          ((Cell)localObject5).compWraps.add(localObject4);
        if ((!((CC)localObject2).isBoundsInGrid()) || (((CC)localObject2).isExternal()))
        {
          setLinkedBounds((ComponentWrapper)localObject1, (CC)localObject2, ((ComponentWrapper)localObject1).getX(), ((ComponentWrapper)localObject1).getY(), ((ComponentWrapper)localObject1).getWidth(), ((ComponentWrapper)localObject1).getHeight(), ((CC)localObject2).isExternal());
          i2++;
          continue;
        }
      }
      if (((CC)localObject2).getDockSide() != -1)
      {
        if (arrayOfInt2 == null)
          arrayOfInt2 = new int[] { -32767, -32767, 32767, 32767 };
        addDockingCell(arrayOfInt2, ((CC)localObject2).getDockSide(), new CompWrap((ComponentWrapper)localObject1, (CC)localObject2, i5, localObject3, arrayOfBoundSize, null));
        i2++;
        continue;
      }
      localObject4 = ((CC)localObject2).getFlowX();
      localObject5 = null;
      if (((CC)localObject2).isNewline())
        wrap(arrayOfInt1, ((CC)localObject2).getNewlineGapSize());
      else if (m != 0)
        wrap(arrayOfInt1, null);
      m = 0;
      if (!paramLC.isNoGrid());
      int i10 = ((DimConstraint)LayoutUtil.getIndexSafe(arrayOfDimConstraint, paramLC.isFlowX() ? arrayOfInt1[1] : arrayOfInt1[0])).isNoGrid() ? 1 : 0;
      int i11 = ((CC)localObject2).getCellX();
      int i12 = ((CC)localObject2).getCellY();
      if (((i11 < 0) || (i12 < 0)) && (i10 == 0) && (((CC)localObject2).getSkip() == 0));
      while (!isCellFree(arrayOfInt1[1], arrayOfInt1[0], localArrayList1))
      {
        if (Math.abs(increase(arrayOfInt1, 1)) < i)
          continue;
        wrap(arrayOfInt1, null);
        continue;
        if ((i11 >= 0) && (i12 >= 0))
          if (i12 >= 0)
          {
            arrayOfInt1[0] = i11;
            arrayOfInt1[1] = i12;
          }
          else if (paramLC.isFlowX())
          {
            arrayOfInt1[0] = i11;
          }
          else
          {
            arrayOfInt1[1] = i11;
          }
        localObject5 = getCell(arrayOfInt1[1], arrayOfInt1[0]);
      }
      int i13 = 0;
      int i14 = ((CC)localObject2).getSkip();
      while (i13 < i14)
      {
        do
        {
          if (Math.abs(increase(arrayOfInt1, 1)) < i)
            continue;
          wrap(arrayOfInt1, null);
        }
        while (!isCellFree(arrayOfInt1[1], arrayOfInt1[0], localArrayList1));
        i13++;
      }
      if (localObject5 == null)
      {
        i13 = Math.min((i10 != 0) && (paramLC.isFlowX()) ? 2097051 : ((CC)localObject2).getSpanX(), 30000 - arrayOfInt1[0]);
        i14 = Math.min((i10 != 0) && (!paramLC.isFlowX()) ? 2097051 : ((CC)localObject2).getSpanY(), 30000 - arrayOfInt1[1]);
        localObject5 = new Cell(i13, i14, localObject4 != null ? ((Boolean)localObject4).booleanValue() : paramLC.isFlowX(), null);
        setCell(arrayOfInt1[1], arrayOfInt1[0], (Cell)localObject5);
        if ((i13 > 1) || (i14 > 1))
          localArrayList1.add(new int[] { arrayOfInt1[0], arrayOfInt1[1], i13, i14 });
      }
      i13 = 0;
      i14 = i10 != 0 ? 2097051 : ((CC)localObject2).getSplit() - 1;
      int i15 = 0;
      int i16 = (paramLC.isFlowX() ? ((CC)localObject2).getSpanX() : ((CC)localObject2).getSpanY()) == 2097051 ? 1 : 0;
      while ((i14 >= 0) && (i2 < arrayOfComponentWrapper.length))
      {
        ComponentWrapper localComponentWrapper = arrayOfComponentWrapper[i2];
        CC localCC3 = getCC(localComponentWrapper, paramMap);
        addLinkIDs(localCC3);
        boolean bool3 = localComponentWrapper.isVisible();
        i5 = localCC3.getHideMode() != -1 ? localCC3.getHideMode() : bool3 ? -1 : paramLC.getHideMode();
        if ((localCC3.isExternal()) || (i5 == 3))
        {
          i2++;
          i14++;
        }
        else
        {
          j |= (((bool3) || (i5 > 1)) && (localCC3.getPushX() != null) ? 1 : 0);
          k |= (((bool3) || (i5 > 1)) && (localCC3.getPushY() != null) ? 1 : 0);
          if (localCC3 != localObject2)
          {
            if ((localCC3.isNewline()) || (!localCC3.isBoundsInGrid()) || (localCC3.getDockSide() != -1))
              break;
            if ((i14 > 0) && (localCC3.getSkip() > 0))
            {
              i15 = 1;
              break;
            }
            localObject3 = getPos(localComponentWrapper, localCC3);
            arrayOfBoundSize = getCallbackSize(localComponentWrapper);
          }
          CompWrap localCompWrap = new CompWrap(localComponentWrapper, localCC3, i5, localObject3, arrayOfBoundSize, null);
          ((Cell)localObject5).compWraps.add(localCompWrap);
          Cell.access$476((Cell)localObject5, localCC3.getTag() != null ? 1 : 0);
          bool1 |= ((Cell)localObject5).hasTagged;
          if (localCC3 != localObject2)
          {
            if (localCC3.getHorizontal().getSizeGroup() != null)
              n++;
            if (localCC3.getVertical().getSizeGroup() != null)
              i1++;
          }
          i2++;
          if ((localCC3.isWrap()) || ((i16 != 0) && (i14 == 0)))
          {
            if (localCC3.isWrap())
              wrap(arrayOfInt1, localCC3.getWrapGapSize());
            else
              m = 1;
            i13 = 1;
            break;
          }
        }
        i14--;
      }
      if ((i13 == 0) && (i10 == 0))
      {
        int i17 = paramLC.isFlowX() ? ((Cell)localObject5).spanx : ((Cell)localObject5).spany;
        if (Math.abs(paramLC.isFlowX() ? arrayOfInt1[0] : arrayOfInt1[1]) + i17 >= i)
          m = 1;
        else
          increase(arrayOfInt1, i15 != 0 ? i17 - 1 : i17);
      }
    }
    HashMap localHashMap;
    int i9;
    String str1;
    if ((n > 0) || (i1 > 0))
    {
      localHashMap = n > 0 ? new HashMap(n) : null;
      localObject1 = i1 > 0 ? new HashMap(i1) : null;
      localObject2 = new ArrayList(Math.max(n, i1));
      Iterator localIterator1 = this.grid.values().iterator();
      while (localIterator1.hasNext())
      {
        localObject3 = (Cell)localIterator1.next();
        for (i9 = 0; i9 < ((Cell)localObject3).compWraps.size(); i9++)
        {
          localObject4 = (CompWrap)((Cell)localObject3).compWraps.get(i9);
          localObject5 = ((CompWrap)localObject4).cc.getHorizontal().getSizeGroup();
          str1 = ((CompWrap)localObject4).cc.getVertical().getSizeGroup();
          if ((localObject5 == null) && (str1 == null))
            continue;
          if ((localObject5 != null) && (localHashMap != null))
            addToSizeGroup(localHashMap, (String)localObject5, ((CompWrap)localObject4).horSizes);
          if ((str1 != null) && (localObject1 != null))
            addToSizeGroup((HashMap)localObject1, str1, ((CompWrap)localObject4).verSizes);
          ((ArrayList)localObject2).add(localObject4);
        }
      }
      for (int i6 = 0; i6 < ((ArrayList)localObject2).size(); i6++)
      {
        localObject3 = (CompWrap)((ArrayList)localObject2).get(i6);
        if (localHashMap != null)
          ((CompWrap)localObject3).setSizes((int[])localHashMap.get(((CompWrap)localObject3).cc.getHorizontal().getSizeGroup()), true);
        if (localObject1 == null)
          continue;
        ((CompWrap)localObject3).setSizes((int[])((HashMap)localObject1).get(((CompWrap)localObject3).cc.getVertical().getSizeGroup()), false);
      }
    }
    if ((n > 0) || (i1 > 0))
    {
      localHashMap = n > 0 ? new HashMap(n) : null;
      localObject1 = i1 > 0 ? new HashMap(i1) : null;
      localObject2 = new ArrayList(Math.max(n, i1));
      Iterator localIterator2 = this.grid.values().iterator();
      while (localIterator2.hasNext())
      {
        localObject3 = (Cell)localIterator2.next();
        for (i9 = 0; i9 < ((Cell)localObject3).compWraps.size(); i9++)
        {
          localObject4 = (CompWrap)((Cell)localObject3).compWraps.get(i9);
          localObject5 = ((CompWrap)localObject4).cc.getHorizontal().getSizeGroup();
          str1 = ((CompWrap)localObject4).cc.getVertical().getSizeGroup();
          if ((localObject5 == null) && (str1 == null))
            continue;
          if ((localObject5 != null) && (localHashMap != null))
            addToSizeGroup(localHashMap, (String)localObject5, ((CompWrap)localObject4).horSizes);
          if ((str1 != null) && (localObject1 != null))
            addToSizeGroup((HashMap)localObject1, str1, ((CompWrap)localObject4).verSizes);
          ((ArrayList)localObject2).add(localObject4);
        }
      }
      for (int i7 = 0; i7 < ((ArrayList)localObject2).size(); i7++)
      {
        localObject3 = (CompWrap)((ArrayList)localObject2).get(i7);
        if (localHashMap != null)
          ((CompWrap)localObject3).setSizes((int[])localHashMap.get(((CompWrap)localObject3).cc.getHorizontal().getSizeGroup()), true);
        if (localObject1 == null)
          continue;
        ((CompWrap)localObject3).setSizes((int[])((HashMap)localObject1).get(((CompWrap)localObject3).cc.getVertical().getSizeGroup()), false);
      }
    }
    if (bool1)
      sortCellsByPlatform(this.grid.values(), paramContainerWrapper);
    boolean bool2 = LayoutUtil.isLeftToRight(paramLC, paramContainerWrapper);
    Object localObject1 = this.grid.values().iterator();
    while (((Iterator)localObject1).hasNext())
    {
      localObject2 = (Cell)((Iterator)localObject1).next();
      ArrayList localArrayList2 = ((Cell)localObject2).compWraps;
      int i8 = 0;
      i9 = localArrayList2.size() - 1;
      while (i8 <= i9)
      {
        localObject4 = (CompWrap)localArrayList2.get(i8);
        localObject5 = i8 > 0 ? ((CompWrap)localArrayList2.get(i8 - 1)).comp : null;
        str1 = i8 < i9 ? ((CompWrap)localArrayList2.get(i8 + 1)).comp : null;
        String str2 = getCC(((CompWrap)localObject4).comp, paramMap).getTag();
        CC localCC1 = localObject5 != null ? getCC((ComponentWrapper)localObject5, paramMap) : null;
        CC localCC2 = str1 != null ? getCC(str1, paramMap) : null;
        ((CompWrap)localObject4).calcGaps((ComponentWrapper)localObject5, localCC1, str1, localCC2, str2, ((Cell)localObject2).flowx, bool2);
        i8++;
      }
    }
    this.dockOffX = getDockInsets(this.colIndexes);
    this.dockOffY = getDockInsets(this.rowIndexes);
    int i3 = 0;
    int i4 = paramAC1.getCount();
    while (i3 < i4)
    {
      this.rowIndexes.add(new Integer(i3));
      i3++;
    }
    i3 = 0;
    i4 = paramAC2.getCount();
    while (i3 < i4)
    {
      this.colIndexes.add(new Integer(i3));
      i3++;
    }
    this.colGroupLists = divideIntoLinkedGroups(false);
    this.rowGroupLists = divideIntoLinkedGroups(true);
    this.pushXs = ((j != 0) || (paramLC.isFillX()) ? getDefaultPushWeights(false) : null);
    this.pushYs = ((k != 0) || (paramLC.isFillY()) ? getDefaultPushWeights(true) : null);
    if (LayoutUtil.isDesignTime(paramContainerWrapper))
      saveGrid(paramContainerWrapper, this.grid);
  }

  private static CC getCC(ComponentWrapper paramComponentWrapper, Map<ComponentWrapper, CC> paramMap)
  {
    CC localCC = (CC)paramMap.get(paramComponentWrapper);
    return localCC != null ? localCC : new CC();
  }

  private void addLinkIDs(CC paramCC)
  {
    String[] arrayOfString = paramCC.getLinkTargets();
    for (int i = 0; i < arrayOfString.length; i++)
    {
      if (this.linkTargetIDs == null)
        this.linkTargetIDs = new HashMap();
      this.linkTargetIDs.put(arrayOfString[i], null);
    }
  }

  public void invalidateContainerSize()
  {
    this.colFlowSpecs = null;
  }

  public boolean layout(int[] paramArrayOfInt, UnitValue paramUnitValue1, UnitValue paramUnitValue2, boolean paramBoolean1, boolean paramBoolean2)
  {
    if (paramBoolean1)
      this.debugRects = new ArrayList();
    checkSizeCalcs();
    resetLinkValues(true, true);
    layoutInOneDim(paramArrayOfInt[2], paramUnitValue1, false, this.pushXs);
    layoutInOneDim(paramArrayOfInt[3], paramUnitValue2, true, this.pushYs);
    HashMap localHashMap1 = null;
    HashMap localHashMap2 = null;
    int i = this.container.getComponentCount();
    boolean bool1 = false;
    Object localObject;
    if (i > 0)
      for (int j = 0; j < (this.linkTargetIDs != null ? 2 : 1); j++)
      {
        int k = 0;
        boolean bool2;
        do
        {
          bool2 = false;
          Iterator localIterator2 = this.grid.values().iterator();
          while (localIterator2.hasNext())
          {
            ArrayList localArrayList2 = ((Cell)localIterator2.next()).compWraps;
            int i1 = 0;
            int i2 = localArrayList2.size();
            while (i1 < i2)
            {
              localObject = (CompWrap)localArrayList2.get(i1);
              if (j == 0)
              {
                bool2 |= doAbsoluteCorrections((CompWrap)localObject, paramArrayOfInt);
                if (!bool2)
                {
                  if (((CompWrap)localObject).cc.getHorizontal().getEndGroup() != null)
                    localHashMap1 = addToEndGroup(localHashMap1, ((CompWrap)localObject).cc.getHorizontal().getEndGroup(), ((CompWrap)localObject).x + ((CompWrap)localObject).w);
                  if (((CompWrap)localObject).cc.getVertical().getEndGroup() != null)
                    localHashMap2 = addToEndGroup(localHashMap2, ((CompWrap)localObject).cc.getVertical().getEndGroup(), ((CompWrap)localObject).y + ((CompWrap)localObject).h);
                }
                if ((this.linkTargetIDs != null) && ((this.linkTargetIDs.containsKey("visual")) || (this.linkTargetIDs.containsKey("container"))))
                  bool1 = true;
              }
              if ((this.linkTargetIDs == null) || (j == 1))
              {
                if (((CompWrap)localObject).cc.getHorizontal().getEndGroup() != null)
                  CompWrap.access$1502((CompWrap)localObject, ((Integer)localHashMap1.get(((CompWrap)localObject).cc.getHorizontal().getEndGroup())).intValue() - ((CompWrap)localObject).x);
                if (((CompWrap)localObject).cc.getVertical().getEndGroup() != null)
                  CompWrap.access$1702((CompWrap)localObject, ((Integer)localHashMap2.get(((CompWrap)localObject).cc.getVertical().getEndGroup())).intValue() - ((CompWrap)localObject).y);
                CompWrap.access$1412((CompWrap)localObject, paramArrayOfInt[0]);
                CompWrap.access$1612((CompWrap)localObject, paramArrayOfInt[1]);
                bool1 |= ((CompWrap)localObject).transferBounds((paramBoolean2) && (!bool1));
                if (this.callbackList != null)
                  for (int i3 = 0; i3 < this.callbackList.size(); i3++)
                    ((LayoutCallback)this.callbackList.get(i3)).correctBounds(((CompWrap)localObject).comp);
              }
              i1++;
            }
          }
          clearGroupLinkBounds();
          k++;
          if (k <= (i << 3) + 10)
            continue;
          System.err.println("Unstable cyclic dependency in absolute linked values!");
          break;
        }
        while (bool2);
      }
    if (paramBoolean1)
    {
      Collection localCollection = this.grid.values();
      Iterator localIterator1 = localCollection.iterator();
      while (localIterator1.hasNext())
      {
        ArrayList localArrayList1 = ((Cell)localIterator1.next()).compWraps;
        int m = 0;
        int n = localArrayList1.size();
        while (m < n)
        {
          CompWrap localCompWrap = (CompWrap)localArrayList1.get(m);
          LinkedDimGroup localLinkedDimGroup = getGroupContaining(this.colGroupLists, localCompWrap);
          localObject = getGroupContaining(this.rowGroupLists, localCompWrap);
          if ((localLinkedDimGroup != null) && (localObject != null))
            this.debugRects.add(new int[] { LinkedDimGroup.access$1900(localLinkedDimGroup) + paramArrayOfInt[0] - (localLinkedDimGroup.fromEnd ? localLinkedDimGroup.lSize : 0), LinkedDimGroup.access$1900((LinkedDimGroup)localObject) + paramArrayOfInt[1] - (((LinkedDimGroup)localObject).fromEnd ? ((LinkedDimGroup)localObject).lSize : 0), LinkedDimGroup.access$2100(localLinkedDimGroup), LinkedDimGroup.access$2100((LinkedDimGroup)localObject) });
          m++;
        }
      }
    }
    return bool1;
  }

  public void paintDebug()
  {
    if (this.debugRects != null)
    {
      this.container.paintDebugOutline();
      ArrayList localArrayList1 = new ArrayList();
      int i = 0;
      int j = this.debugRects.size();
      while (i < j)
      {
        int[] arrayOfInt = (int[])this.debugRects.get(i);
        if (!localArrayList1.contains(arrayOfInt))
        {
          this.container.paintDebugCell(arrayOfInt[0], arrayOfInt[1], arrayOfInt[2], arrayOfInt[3]);
          localArrayList1.add(arrayOfInt);
        }
        i++;
      }
      Iterator localIterator = this.grid.values().iterator();
      while (localIterator.hasNext())
      {
        ArrayList localArrayList2 = ((Cell)localIterator.next()).compWraps;
        int k = 0;
        int m = localArrayList2.size();
        while (k < m)
        {
          ((CompWrap)localArrayList2.get(k)).comp.paintDebugOutline();
          k++;
        }
      }
    }
  }

  public final int[] getWidth()
  {
    checkSizeCalcs();
    return this.width;
  }

  public final int[] getHeight()
  {
    checkSizeCalcs();
    return this.height;
  }

  private void checkSizeCalcs()
  {
    if (this.colFlowSpecs == null)
    {
      this.colFlowSpecs = calcRowsOrColsSizes(true);
      this.rowFlowSpecs = calcRowsOrColsSizes(false);
      this.width = getMinPrefMaxSumSize(true);
      this.height = getMinPrefMaxSumSize(false);
      if (this.linkTargetIDs == null)
      {
        resetLinkValues(false, true);
      }
      else
      {
        layout(new int[4], null, null, false, false);
        resetLinkValues(false, false);
      }
      adjustSizeForAbsolute(true);
      adjustSizeForAbsolute(false);
    }
  }

  private final UnitValue[] getPos(ComponentWrapper paramComponentWrapper, CC paramCC)
  {
    UnitValue[] arrayOfUnitValue1 = null;
    if (this.callbackList != null)
      for (int i = 0; (i < this.callbackList.size()) && (arrayOfUnitValue1 == null); i++)
        arrayOfUnitValue1 = ((LayoutCallback)this.callbackList.get(i)).getPosition(paramComponentWrapper);
    UnitValue[] arrayOfUnitValue2 = paramCC.getPos();
    if ((arrayOfUnitValue1 == null) || (arrayOfUnitValue2 == null))
      return arrayOfUnitValue1 != null ? arrayOfUnitValue1 : arrayOfUnitValue2;
    for (int j = 0; j < 4; j++)
    {
      UnitValue localUnitValue = arrayOfUnitValue1[j];
      if (localUnitValue == null)
        continue;
      arrayOfUnitValue2[j] = localUnitValue;
    }
    return arrayOfUnitValue2;
  }

  private final BoundSize[] getCallbackSize(ComponentWrapper paramComponentWrapper)
  {
    if (this.callbackList != null)
      for (int i = 0; i < this.callbackList.size(); i++)
      {
        BoundSize[] arrayOfBoundSize = ((LayoutCallback)this.callbackList.get(i)).getSize(paramComponentWrapper);
        if (arrayOfBoundSize != null)
          return arrayOfBoundSize;
      }
    return null;
  }

  private static final int getDockInsets(TreeSet<Integer> paramTreeSet)
  {
    int i = 0;
    Iterator localIterator = paramTreeSet.iterator();
    while ((localIterator.hasNext()) && (((Integer)localIterator.next()).intValue() < -30000))
      i++;
    return i;
  }

  private boolean setLinkedBounds(ComponentWrapper paramComponentWrapper, CC paramCC, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean)
  {
    String str1 = paramCC.getId() != null ? paramCC.getId() : paramComponentWrapper.getLinkId();
    if (str1 == null)
      return false;
    String str2 = null;
    int i = str1.indexOf('.');
    if (i != -1)
    {
      str2 = str1.substring(0, i);
      str1 = str1.substring(i + 1);
    }
    Object localObject = this.container.getLayout();
    boolean bool = false;
    if ((paramBoolean) || ((this.linkTargetIDs != null) && (this.linkTargetIDs.containsKey(str1))))
      bool = LinkHandler.setBounds(localObject, str1, paramInt1, paramInt2, paramInt3, paramInt4, !paramBoolean, false);
    if ((str2 != null) && ((paramBoolean) || ((this.linkTargetIDs != null) && (this.linkTargetIDs.containsKey(str2)))))
    {
      if (this.linkTargetIDs == null)
        this.linkTargetIDs = new HashMap(4);
      this.linkTargetIDs.put(str2, Boolean.TRUE);
      bool |= LinkHandler.setBounds(localObject, str2, paramInt1, paramInt2, paramInt3, paramInt4, !paramBoolean, true);
    }
    return bool;
  }

  private final int increase(int[] paramArrayOfInt, int paramInt)
  {
    return paramArrayOfInt[1] += paramInt;
  }

  private final void wrap(int[] paramArrayOfInt, BoundSize paramBoundSize)
  {
    boolean bool = this.lc.isFlowX();
    paramArrayOfInt[0] = (bool ? 0 : paramArrayOfInt[0] + 1);
    paramArrayOfInt[1] = (bool ? paramArrayOfInt[1] + 1 : 0);
    if (paramBoundSize != null)
    {
      if (this.wrapGapMap == null)
        this.wrapGapMap = new HashMap(8);
      this.wrapGapMap.put(new Integer(paramArrayOfInt[0]), paramBoundSize);
    }
    if (bool)
      this.rowIndexes.add(new Integer(paramArrayOfInt[1]));
    else
      this.colIndexes.add(new Integer(paramArrayOfInt[0]));
  }

  private static void sortCellsByPlatform(Collection<Cell> paramCollection, ContainerWrapper paramContainerWrapper)
  {
    String str1 = PlatformDefaults.getButtonOrder();
    String str2 = str1.toLowerCase();
    int i = PlatformDefaults.convertToPixels(1.0F, "u", true, 0.0F, paramContainerWrapper, null);
    if (i == -87654312)
      throw new IllegalArgumentException("'unrelated' not recognized by PlatformDefaults!");
    int[] arrayOfInt1 = { i, i, -2147471302 };
    int[] arrayOfInt2 = { 0, 0, -2147471302 };
    Iterator localIterator = paramCollection.iterator();
    while (localIterator.hasNext())
    {
      Cell localCell = (Cell)localIterator.next();
      if (!localCell.hasTagged)
        continue;
      Object localObject = null;
      int j = 0;
      int k = 0;
      ArrayList localArrayList = new ArrayList(localCell.compWraps.size());
      int m = 0;
      int n = str2.length();
      while (m < n)
      {
        char c = str2.charAt(m);
        if ((c == '+') || (c == '_'))
        {
          j = 1;
          if (c == '+')
            k = 1;
        }
        else
        {
          String str3 = PlatformDefaults.getTagForChar(c);
          if (str3 != null)
          {
            int i1 = 0;
            int i2 = localCell.compWraps.size();
            while (i1 < i2)
            {
              CompWrap localCompWrap2 = (CompWrap)localCell.compWraps.get(i1);
              if (str3.equals(localCompWrap2.cc.getTag()))
              {
                if (Character.isUpperCase(str1.charAt(m)))
                {
                  int i3 = PlatformDefaults.getMinimumButtonWidth().getPixels(0.0F, paramContainerWrapper, localCompWrap2.comp);
                  if (i3 > localCompWrap2.horSizes[0])
                    localCompWrap2.horSizes[0] = i3;
                  correctMinMax(localCompWrap2.horSizes);
                }
                localArrayList.add(localCompWrap2);
                if (j != 0)
                {
                  (localObject != null ? localObject : localCompWrap2).mergeGapSizes(arrayOfInt1, localCell.flowx, localObject == null);
                  if (k != 0)
                  {
                    CompWrap.access$2302(localCompWrap2, 1);
                    j = 0;
                    k = 0;
                  }
                }
                if (c == 'u')
                  j = 1;
                localObject = localCompWrap2;
              }
              i1++;
            }
          }
        }
        m++;
      }
      if (localArrayList.size() > 0)
      {
        CompWrap localCompWrap1 = (CompWrap)localArrayList.get(localArrayList.size() - 1);
        if (j != 0)
        {
          localCompWrap1.mergeGapSizes(arrayOfInt1, localCell.flowx, false);
          if (k != 0)
            CompWrap.access$2376(localCompWrap1, 2);
        }
        if (localCompWrap1.cc.getHorizontal().getGapAfter() == null)
          localCompWrap1.setGaps(arrayOfInt2, 3);
        localCompWrap1 = (CompWrap)localArrayList.get(0);
        if (localCompWrap1.cc.getHorizontal().getGapBefore() == null)
          localCompWrap1.setGaps(arrayOfInt2, 1);
      }
      if (localCell.compWraps.size() == localArrayList.size())
        localCell.compWraps.clear();
      else
        localCell.compWraps.removeAll(localArrayList);
      localCell.compWraps.addAll(localArrayList);
    }
  }

  private Float[] getDefaultPushWeights(boolean paramBoolean)
  {
    ArrayList[] arrayOfArrayList = paramBoolean ? this.rowGroupLists : this.colGroupLists;
    Float[] arrayOfFloat = GROW_100;
    int i = 0;
    for (int j = 1; i < arrayOfArrayList.length; j += 2)
    {
      ArrayList localArrayList = arrayOfArrayList[i];
      Object localObject1 = null;
      for (int k = 0; k < localArrayList.size(); k++)
      {
        LinkedDimGroup localLinkedDimGroup = (LinkedDimGroup)localArrayList.get(k);
        for (int m = 0; m < localLinkedDimGroup._compWraps.size(); m++)
        {
          CompWrap localCompWrap = (CompWrap)localLinkedDimGroup._compWraps.get(m);
          int n = localCompWrap.cc.getHideMode() != -1 ? localCompWrap.cc.getHideMode() : localCompWrap.comp.isVisible() ? -1 : this.lc.getHideMode();
          Object localObject2 = n < 2 ? localCompWrap.cc.getPushX() : paramBoolean ? localCompWrap.cc.getPushY() : null;
          if ((localObject1 != null) && ((localObject2 == null) || (localObject2.floatValue() <= localObject1.floatValue())))
            continue;
          localObject1 = localObject2;
        }
      }
      if (localObject1 != null)
      {
        if (arrayOfFloat == GROW_100)
          arrayOfFloat = new Float[(arrayOfArrayList.length << 1) + 1];
        arrayOfFloat[j] = localObject1;
      }
      i++;
    }
    return arrayOfFloat;
  }

  private void clearGroupLinkBounds()
  {
    if (this.linkTargetIDs == null)
      return;
    Iterator localIterator = this.linkTargetIDs.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      if (localEntry.getValue() == Boolean.TRUE)
        LinkHandler.clearBounds(this.container.getLayout(), (String)localEntry.getKey());
    }
  }

  private void resetLinkValues(boolean paramBoolean1, boolean paramBoolean2)
  {
    Object localObject = this.container.getLayout();
    if (paramBoolean2)
      LinkHandler.clearTemporaryBounds(localObject);
    boolean bool = !hasDocks();
    int i = paramBoolean1 ? this.lc.getWidth().constrain(this.container.getWidth(), getParentSize(this.container, true), this.container) : 0;
    int j = paramBoolean1 ? this.lc.getHeight().constrain(this.container.getHeight(), getParentSize(this.container, false), this.container) : 0;
    int k = LayoutUtil.getInsets(this.lc, 0, bool).getPixels(0.0F, this.container, null);
    int m = LayoutUtil.getInsets(this.lc, 1, bool).getPixels(0.0F, this.container, null);
    int n = i - k - LayoutUtil.getInsets(this.lc, 2, bool).getPixels(0.0F, this.container, null);
    int i1 = j - m - LayoutUtil.getInsets(this.lc, 3, bool).getPixels(0.0F, this.container, null);
    LinkHandler.setBounds(localObject, "visual", k, m, n, i1, true, false);
    LinkHandler.setBounds(localObject, "container", 0, 0, i, j, true, false);
  }

  private static LinkedDimGroup getGroupContaining(ArrayList<LinkedDimGroup>[] paramArrayOfArrayList, CompWrap paramCompWrap)
  {
    for (int i = 0; i < paramArrayOfArrayList.length; i++)
    {
      ArrayList<LinkedDimGroup> localArrayList = paramArrayOfArrayList[i];
      int j = 0;
      int k = localArrayList.size();
      while (j < k)
      {
        ArrayList localArrayList1 = ((LinkedDimGroup)localArrayList.get(j))._compWraps;
        int m = 0;
        int n = localArrayList1.size();
        while (m < n)
        {
          if (localArrayList1.get(m) == paramCompWrap)
            return (LinkedDimGroup)localArrayList.get(j);
          m++;
        }
        j++;
      }
    }
    return null;
  }

  private boolean doAbsoluteCorrections(CompWrap paramCompWrap, int[] paramArrayOfInt)
  {
    boolean bool = false;
    int[] arrayOfInt = getAbsoluteDimBounds(paramCompWrap, paramArrayOfInt[2], true);
    if (arrayOfInt != null)
      paramCompWrap.setDimBounds(arrayOfInt[0], arrayOfInt[1], true);
    arrayOfInt = getAbsoluteDimBounds(paramCompWrap, paramArrayOfInt[3], false);
    if (arrayOfInt != null)
      paramCompWrap.setDimBounds(arrayOfInt[0], arrayOfInt[1], false);
    if (this.linkTargetIDs != null)
      bool = setLinkedBounds(paramCompWrap.comp, paramCompWrap.cc, paramCompWrap.x, paramCompWrap.y, paramCompWrap.w, paramCompWrap.h, false);
    return bool;
  }

  private void adjustSizeForAbsolute(boolean paramBoolean)
  {
    int[] arrayOfInt1 = paramBoolean ? this.width : this.height;
    Cell localCell = (Cell)this.grid.get(null);
    if ((localCell == null) || (localCell.compWraps.size() == 0))
      return;
    ArrayList localArrayList = localCell.compWraps;
    int i = 0;
    int j = 0;
    int k = localCell.compWraps.size();
    while (j < k + 3)
    {
      boolean bool = false;
      for (int m = 0; m < k; m++)
      {
        CompWrap localCompWrap = (CompWrap)localArrayList.get(m);
        int[] arrayOfInt2 = getAbsoluteDimBounds(localCompWrap, 0, paramBoolean);
        int n = arrayOfInt2[0] + arrayOfInt2[1];
        if (i < n)
          i = n;
        if (this.linkTargetIDs == null)
          continue;
        bool |= setLinkedBounds(localCompWrap.comp, localCompWrap.cc, arrayOfInt2[0], arrayOfInt2[0], arrayOfInt2[1], arrayOfInt2[1], false);
      }
      if (!bool)
        break;
      i = 0;
      clearGroupLinkBounds();
      j++;
    }
    i += LayoutUtil.getInsets(this.lc, paramBoolean ? 3 : 2, !hasDocks()).getPixels(0.0F, this.container, null);
    if (arrayOfInt1[0] < i)
      arrayOfInt1[0] = i;
    if (arrayOfInt1[1] < i)
      arrayOfInt1[1] = i;
  }

  private int[] getAbsoluteDimBounds(CompWrap paramCompWrap, int paramInt, boolean paramBoolean)
  {
    if (paramCompWrap.cc.isExternal())
    {
      if (paramBoolean)
        return new int[] { CompWrap.access$1100(paramCompWrap).getX(), CompWrap.access$1100(paramCompWrap).getWidth() };
      return new int[] { CompWrap.access$1100(paramCompWrap).getY(), CompWrap.access$1100(paramCompWrap).getHeight() };
    }
    Object localObject1 = this.lc.isVisualPadding() ? paramCompWrap.comp.getVisualPadding() : null;
    UnitValue[] arrayOfUnitValue = paramCompWrap.cc.getPadding();
    if ((paramCompWrap.pos == null) && (localObject1 == null) && (arrayOfUnitValue == null))
      return null;
    int i = paramBoolean ? paramCompWrap.x : paramCompWrap.y;
    int j = paramBoolean ? paramCompWrap.w : paramCompWrap.h;
    UnitValue localUnitValue;
    if (paramCompWrap.pos != null)
    {
      localUnitValue = paramCompWrap.pos != null ? paramCompWrap.pos[1] : null;
      Object localObject2 = paramCompWrap.pos != null ? paramCompWrap.pos[3] : null;
      int n = paramCompWrap.getSize(0, paramBoolean);
      int i1 = paramCompWrap.getSize(2, paramBoolean);
      j = Math.min(Math.max(paramCompWrap.getSize(1, paramBoolean), n), i1);
      if (localUnitValue != null)
      {
        i = localUnitValue.getPixels(paramInt, this.container, paramCompWrap.comp);
        if (localObject2 != null)
          j = Math.min(Math.max((paramBoolean ? paramCompWrap.x + paramCompWrap.w : paramCompWrap.y + paramCompWrap.h) - i, n), i1);
      }
      if (localObject2 != null)
        if (localUnitValue != null)
          j = Math.min(Math.max(localObject2.getPixels(paramInt, this.container, paramCompWrap.comp) - i, n), i1);
        else
          i = localObject2.getPixels(paramInt, this.container, paramCompWrap.comp) - j;
    }
    if (arrayOfUnitValue != null)
    {
      localUnitValue = arrayOfUnitValue[0];
      int m = localUnitValue != null ? localUnitValue.getPixels(paramInt, this.container, paramCompWrap.comp) : 0;
      i += m;
      localUnitValue = arrayOfUnitValue[2];
      j += -m + (localUnitValue != null ? localUnitValue.getPixels(paramInt, this.container, paramCompWrap.comp) : 0);
    }
    if (localObject1 != null)
    {
      int k = localObject1[0];
      i += k;
      j += -k + localObject1[2];
    }
    return new int[] { i, j };
  }

  private void layoutInOneDim(int paramInt, UnitValue paramUnitValue, boolean paramBoolean, Float[] paramArrayOfFloat)
  {
    int i = paramBoolean ? !this.lc.isTopToBottom() : !LayoutUtil.isLeftToRight(this.lc, this.container) ? 1 : 0;
    DimConstraint[] arrayOfDimConstraint = (paramBoolean ? this.rowConstr : this.colConstr).getConstaints();
    FlowSizeSpec localFlowSizeSpec = paramBoolean ? this.rowFlowSpecs : this.colFlowSpecs;
    ArrayList[] arrayOfArrayList = paramBoolean ? this.rowGroupLists : this.colGroupLists;
    int[] arrayOfInt1 = LayoutUtil.calculateSerial(localFlowSizeSpec.sizes, localFlowSizeSpec.resConstsInclGaps, paramArrayOfFloat, 1, paramInt);
    if (LayoutUtil.isDesignTime(this.container))
    {
      TreeSet localTreeSet = paramBoolean ? this.rowIndexes : this.colIndexes;
      int[] arrayOfInt2 = new int[localTreeSet.size()];
      int m = 0;
      Iterator localIterator = localTreeSet.iterator();
      while (localIterator.hasNext())
      {
        Integer localInteger = (Integer)localIterator.next();
        arrayOfInt2[(m++)] = localInteger.intValue();
      }
      putSizesAndIndexes(this.container.getComponent(), arrayOfInt1, arrayOfInt2, paramBoolean);
    }
    int j = paramUnitValue != null ? Math.round(paramUnitValue.getPixels(paramInt - LayoutUtil.sum(arrayOfInt1), this.container, null)) : 0;
    if (i != 0)
      j = paramInt - j;
    for (int k = 0; k < arrayOfArrayList.length; k++)
    {
      ArrayList localArrayList = arrayOfArrayList[k];
      int n = k - (paramBoolean ? this.dockOffY : this.dockOffX);
      int i1 = k << 1;
      int i2 = i1 + 1;
      j += (i != 0 ? -arrayOfInt1[i1] : arrayOfInt1[i1]);
      DimConstraint localDimConstraint = n >= 0 ? arrayOfDimConstraint[n] : DOCK_DIM_CONSTRAINT;
      int i3 = arrayOfInt1[i2];
      for (int i4 = 0; i4 < localArrayList.size(); i4++)
      {
        LinkedDimGroup localLinkedDimGroup = (LinkedDimGroup)localArrayList.get(i4);
        int i5 = i3;
        if (localLinkedDimGroup.span > 1)
          i5 = LayoutUtil.sum(arrayOfInt1, i2, Math.min((localLinkedDimGroup.span << 1) - 1, arrayOfInt1.length - i2 - 1));
        localLinkedDimGroup.layout(localDimConstraint, j, i5, localLinkedDimGroup.span);
      }
      j += (i != 0 ? -i3 : i3);
    }
  }

  private static void addToSizeGroup(HashMap<String, int[]> paramHashMap, String paramString, int[] paramArrayOfInt)
  {
    int[] arrayOfInt = (int[])paramHashMap.get(paramString);
    if (arrayOfInt == null)
    {
      paramHashMap.put(paramString, new int[] { paramArrayOfInt[0], paramArrayOfInt[1], paramArrayOfInt[2] });
    }
    else
    {
      arrayOfInt[0] = Math.max(paramArrayOfInt[0], arrayOfInt[0]);
      arrayOfInt[1] = Math.max(paramArrayOfInt[1], arrayOfInt[1]);
      arrayOfInt[2] = Math.min(paramArrayOfInt[2], arrayOfInt[2]);
    }
  }

  private static HashMap<String, Integer> addToEndGroup(HashMap<String, Integer> paramHashMap, String paramString, int paramInt)
  {
    if (paramString != null)
    {
      if (paramHashMap == null)
        paramHashMap = new HashMap(2);
      Integer localInteger = (Integer)paramHashMap.get(paramString);
      if ((localInteger == null) || (paramInt > localInteger.intValue()))
        paramHashMap.put(paramString, new Integer(paramInt));
    }
    return paramHashMap;
  }

  private FlowSizeSpec calcRowsOrColsSizes(boolean paramBoolean)
  {
    ArrayList[] arrayOfArrayList = paramBoolean ? this.colGroupLists : this.rowGroupLists;
    Float[] arrayOfFloat = paramBoolean ? this.pushXs : this.pushYs;
    int i = paramBoolean ? this.container.getWidth() : this.container.getHeight();
    BoundSize localBoundSize1 = paramBoolean ? this.lc.getWidth() : this.lc.getHeight();
    if (!localBoundSize1.isUnset())
      i = localBoundSize1.constrain(i, getParentSize(this.container, paramBoolean), this.container);
    DimConstraint[] arrayOfDimConstraint1 = (paramBoolean ? this.colConstr : this.rowConstr).getConstaints();
    TreeSet localTreeSet = paramBoolean ? this.colIndexes : this.rowIndexes;
    int[][] arrayOfInt = new int[localTreeSet.size()][];
    HashMap localHashMap = new HashMap(2);
    DimConstraint[] arrayOfDimConstraint2 = new DimConstraint[localTreeSet.size()];
    Iterator localIterator = localTreeSet.iterator();
    for (int j = 0; j < arrayOfInt.length; j++)
    {
      int k = ((Integer)localIterator.next()).intValue();
      localObject1 = new int[3];
      if ((k >= -30000) && (k <= 30000))
        arrayOfDimConstraint2[j] = arrayOfDimConstraint1[k];
      else
        arrayOfDimConstraint2[j] = DOCK_DIM_CONSTRAINT;
      localObject2 = arrayOfArrayList[j];
      int[] arrayOfInt1 = { getTotalGroupsSizeParallel((ArrayList)localObject2, 0, false), getTotalGroupsSizeParallel((ArrayList)localObject2, 1, false), 2097051 };
      correctMinMax(arrayOfInt1);
      BoundSize localBoundSize2 = arrayOfDimConstraint2[j].getSize();
      for (int m = 0; m <= 2; m++)
      {
        int n = arrayOfInt1[m];
        UnitValue localUnitValue = localBoundSize2.getSize(m);
        if (localUnitValue != null)
        {
          int i1 = localUnitValue.getUnit();
          if (i1 == 14)
            n = arrayOfInt1[1];
          else if (i1 == 13)
            n = arrayOfInt1[0];
          else if (i1 == 15)
            n = arrayOfInt1[2];
          else
            n = localUnitValue.getPixels(i, this.container, null);
        }
        else if ((k >= -30000) && (k <= 30000) && (n == 0))
        {
          n = LayoutUtil.isDesignTime(this.container) ? LayoutUtil.getDesignTimeEmptySize() : 0;
        }
        localObject1[m] = n;
      }
      correctMinMax(localObject1);
      addToSizeGroup(localHashMap, arrayOfDimConstraint2[j].getSizeGroup(), localObject1);
      arrayOfInt[j] = localObject1;
    }
    if (localHashMap.size() > 0)
      for (j = 0; j < arrayOfInt.length; j++)
      {
        if (arrayOfDimConstraint2[j].getSizeGroup() == null)
          continue;
        arrayOfInt[j] = ((int[])localHashMap.get(arrayOfDimConstraint2[j].getSizeGroup()));
      }
    ResizeConstraint[] arrayOfResizeConstraint = getRowResizeConstraints(arrayOfDimConstraint2);
    boolean[] arrayOfBoolean = new boolean[arrayOfDimConstraint2.length + 1];
    Object localObject1 = getRowGaps(arrayOfDimConstraint2, i, paramBoolean, arrayOfBoolean);
    Object localObject2 = mergeSizesGapsAndResConstrs(arrayOfResizeConstraint, arrayOfBoolean, arrayOfInt, localObject1);
    adjustMinPrefForSpanningComps(arrayOfDimConstraint2, arrayOfFloat, (FlowSizeSpec)localObject2, arrayOfArrayList);
    return (FlowSizeSpec)(FlowSizeSpec)localObject2;
  }

  private static int getParentSize(ComponentWrapper paramComponentWrapper, boolean paramBoolean)
  {
    ContainerWrapper localContainerWrapper = paramComponentWrapper.getParent();
    return localContainerWrapper != null ? paramComponentWrapper.getHeight() : paramBoolean ? paramComponentWrapper.getWidth() : 0;
  }

  private int[] getMinPrefMaxSumSize(boolean paramBoolean)
  {
    int[][] arrayOfInt = paramBoolean ? this.colFlowSpecs.sizes : this.rowFlowSpecs.sizes;
    int[] arrayOfInt1 = new int[3];
    BoundSize localBoundSize = paramBoolean ? this.lc.getWidth() : this.lc.getHeight();
    for (int i = 0; i < arrayOfInt.length; i++)
    {
      if (arrayOfInt[i] == null)
        continue;
      int[] arrayOfInt2 = arrayOfInt[i];
      for (int j = 0; j <= 2; j++)
        if (localBoundSize.getSize(j) != null)
        {
          if (i != 0)
            continue;
          arrayOfInt1[j] = localBoundSize.getSize(j).getPixels(getParentSize(this.container, paramBoolean), this.container, null);
        }
        else
        {
          int k = arrayOfInt2[j];
          if (k != -2147471302)
          {
            if (j == 1)
            {
              int m = arrayOfInt2[2];
              if ((m != -2147471302) && (m < k))
                k = m;
              m = arrayOfInt2[0];
              if (m > k)
                k = m;
            }
            arrayOfInt1[j] += k;
          }
          if ((arrayOfInt2[2] != -2147471302) && (arrayOfInt1[2] <= 2097051))
            continue;
          arrayOfInt1[2] = 2097051;
        }
    }
    correctMinMax(arrayOfInt1);
    return arrayOfInt1;
  }

  private static ResizeConstraint[] getRowResizeConstraints(DimConstraint[] paramArrayOfDimConstraint)
  {
    ResizeConstraint[] arrayOfResizeConstraint = new ResizeConstraint[paramArrayOfDimConstraint.length];
    for (int i = 0; i < arrayOfResizeConstraint.length; i++)
      arrayOfResizeConstraint[i] = paramArrayOfDimConstraint[i].resize;
    return arrayOfResizeConstraint;
  }

  private static ResizeConstraint[] getComponentResizeConstraints(ArrayList<CompWrap> paramArrayList, boolean paramBoolean)
  {
    ResizeConstraint[] arrayOfResizeConstraint = new ResizeConstraint[paramArrayList.size()];
    for (int i = 0; i < arrayOfResizeConstraint.length; i++)
    {
      CC localCC = ((CompWrap)paramArrayList.get(i)).cc;
      arrayOfResizeConstraint[i] = localCC.getDimConstraint(paramBoolean).resize;
      int j = localCC.getDockSide();
      if (paramBoolean ? (j == 0) && (j == 2) : (j != 1) && (j != 3))
        continue;
      ResizeConstraint localResizeConstraint = arrayOfResizeConstraint[i];
      arrayOfResizeConstraint[i] = new ResizeConstraint(localResizeConstraint.shrinkPrio, localResizeConstraint.shrink, localResizeConstraint.growPrio, ResizeConstraint.WEIGHT_100);
    }
    return arrayOfResizeConstraint;
  }

  private static boolean[] getComponentGapPush(ArrayList<CompWrap> paramArrayList, boolean paramBoolean)
  {
    boolean[] arrayOfBoolean = new boolean[paramArrayList.size() + 1];
    for (int i = 0; i < arrayOfBoolean.length; i++)
    {
      boolean bool = i > 0 ? ((CompWrap)paramArrayList.get(i - 1)).isPushGap(paramBoolean, false) : false;
      if ((!bool) && (i < arrayOfBoolean.length - 1))
        bool = ((CompWrap)paramArrayList.get(i)).isPushGap(paramBoolean, true);
      arrayOfBoolean[i] = bool;
    }
    return arrayOfBoolean;
  }

  private int[][] getRowGaps(DimConstraint[] paramArrayOfDimConstraint, int paramInt, boolean paramBoolean, boolean[] paramArrayOfBoolean)
  {
    BoundSize localBoundSize1 = paramBoolean ? this.lc.getGridGapX() : this.lc.getGridGapY();
    if (localBoundSize1 == null)
      localBoundSize1 = paramBoolean ? PlatformDefaults.getGridGapX() : PlatformDefaults.getGridGapY();
    int[] arrayOfInt1 = localBoundSize1.getPixelSizes(paramInt, this.container, null);
    boolean bool = !hasDocks();
    UnitValue localUnitValue1 = LayoutUtil.getInsets(this.lc, paramBoolean ? 1 : 0, bool);
    UnitValue localUnitValue2 = LayoutUtil.getInsets(this.lc, paramBoolean ? 3 : 2, bool);
    int[][] arrayOfInt = new int[paramArrayOfDimConstraint.length + 1][];
    int i = 0;
    int j = 0;
    while (i < arrayOfInt.length)
    {
      Object localObject1 = i > 0 ? paramArrayOfDimConstraint[(i - 1)] : null;
      Object localObject2 = i < paramArrayOfDimConstraint.length ? paramArrayOfDimConstraint[i] : null;
      int k = (localObject1 == DOCK_DIM_CONSTRAINT) || (localObject1 == null) ? 1 : 0;
      int m = (localObject2 == DOCK_DIM_CONSTRAINT) || (localObject2 == null) ? 1 : 0;
      if ((k == 0) || (m == 0))
      {
        BoundSize localBoundSize2 = (this.wrapGapMap == null) || (paramBoolean == this.lc.isFlowX()) ? null : (BoundSize)this.wrapGapMap.get(new Integer(j++));
        if (localBoundSize2 == null)
        {
          int[] arrayOfInt2 = localObject1 != null ? localObject1.getRowGaps(this.container, null, paramInt, false) : null;
          int[] arrayOfInt3 = localObject2 != null ? localObject2.getRowGaps(this.container, null, paramInt, true) : null;
          int n;
          if ((k != 0) && (arrayOfInt3 == null) && (localUnitValue1 != null))
          {
            n = localUnitValue1.getPixels(paramInt, this.container, null);
            arrayOfInt[i] = { n, n, n };
          }
          else if ((m != 0) && (arrayOfInt2 == null) && (localUnitValue1 != null))
          {
            n = localUnitValue2.getPixels(paramInt, this.container, null);
            arrayOfInt[i] = { n, n, n };
          }
          else
          {
            arrayOfInt[i] = { arrayOfInt1[0], arrayOfInt1[1], arrayOfInt3 != arrayOfInt2 ? mergeSizes(arrayOfInt3, arrayOfInt2) : arrayOfInt1[2] };
          }
          if (((localObject1 != null) && (localObject1.isGapAfterPush())) || ((localObject2 != null) && (localObject2.isGapBeforePush())))
            paramArrayOfBoolean[i] = true;
        }
        else
        {
          if (localBoundSize2.isUnset())
            arrayOfInt[i] = { arrayOfInt1[0], arrayOfInt1[1], arrayOfInt1[2] };
          else
            arrayOfInt[i] = localBoundSize2.getPixelSizes(paramInt, this.container, null);
          paramArrayOfBoolean[i] = localBoundSize2.getGapPush();
        }
      }
      i++;
    }
    return arrayOfInt;
  }

  private static int[][] getGaps(ArrayList<CompWrap> paramArrayList, boolean paramBoolean)
  {
    int i = paramArrayList.size();
    int[][] arrayOfInt = new int[i + 1][];
    arrayOfInt[0] = CompWrap.access$3400((CompWrap)paramArrayList.get(0), paramBoolean, true);
    for (int j = 0; j < i; j++)
    {
      int[] arrayOfInt1 = ((CompWrap)paramArrayList.get(j)).getGaps(paramBoolean, false);
      int[] arrayOfInt2 = j < i - 1 ? ((CompWrap)paramArrayList.get(j + 1)).getGaps(paramBoolean, true) : null;
      arrayOfInt[(j + 1)] = mergeSizes(arrayOfInt1, arrayOfInt2);
    }
    return arrayOfInt;
  }

  private boolean hasDocks()
  {
    return (this.dockOffX > 0) || (this.dockOffY > 0) || (((Integer)this.rowIndexes.last()).intValue() > 30000) || (((Integer)this.colIndexes.last()).intValue() > 30000);
  }

  private void adjustMinPrefForSpanningComps(DimConstraint[] paramArrayOfDimConstraint, Float[] paramArrayOfFloat, FlowSizeSpec paramFlowSizeSpec, ArrayList<LinkedDimGroup>[] paramArrayOfArrayList)
  {
    for (int i = paramArrayOfArrayList.length - 1; i >= 0; i--)
    {
      ArrayList<LinkedDimGroup> localArrayList = paramArrayOfArrayList[i];
      for (int j = 0; j < localArrayList.size(); j++)
      {
        LinkedDimGroup localLinkedDimGroup = (LinkedDimGroup)localArrayList.get(j);
        if (localLinkedDimGroup.span == 1)
          continue;
        int[] arrayOfInt = localLinkedDimGroup.getMinPrefMax();
        for (int k = 0; k <= 1; k++)
        {
          int m = arrayOfInt[k];
          if (m == -2147471302)
            continue;
          int n = 0;
          int i1 = (i << 1) + 1;
          int i2 = Math.min(localLinkedDimGroup.span << 1, paramFlowSizeSpec.sizes.length - i1) - 1;
          for (int i3 = i1; i3 < i1 + i2; i3++)
          {
            i4 = paramFlowSizeSpec.sizes[i3][k];
            if (i4 == -2147471302)
              continue;
            n += i4;
          }
          if (n >= m)
            continue;
          i3 = 0;
          int i4 = 0;
          while ((i3 < 4) && (i4 < m))
          {
            i4 = paramFlowSizeSpec.expandSizes(paramArrayOfDimConstraint, paramArrayOfFloat, m, i1, i2, k, i3);
            i3++;
          }
        }
      }
    }
  }

  private ArrayList<LinkedDimGroup>[] divideIntoLinkedGroups(boolean paramBoolean)
  {
    boolean bool1 = paramBoolean ? !this.lc.isTopToBottom() : !LayoutUtil.isLeftToRight(this.lc, this.container);
    TreeSet localTreeSet1 = paramBoolean ? this.rowIndexes : this.colIndexes;
    TreeSet localTreeSet2 = paramBoolean ? this.colIndexes : this.rowIndexes;
    DimConstraint[] arrayOfDimConstraint = (paramBoolean ? this.rowConstr : this.colConstr).getConstaints();
    ArrayList[] arrayOfArrayList = new ArrayList[localTreeSet1.size()];
    int i = 0;
    Iterator localIterator1 = localTreeSet1.iterator();
    while (localIterator1.hasNext())
    {
      int j = ((Integer)localIterator1.next()).intValue();
      DimConstraint localDimConstraint;
      if ((j >= -30000) && (j <= 30000))
        localDimConstraint = arrayOfDimConstraint[j];
      else
        localDimConstraint = DOCK_DIM_CONSTRAINT;
      ArrayList localArrayList = new ArrayList(2);
      arrayOfArrayList[(i++)] = localArrayList;
      Iterator localIterator2 = localTreeSet2.iterator();
      while (localIterator2.hasNext())
      {
        int k = ((Integer)localIterator2.next()).intValue();
        Cell localCell = paramBoolean ? getCell(j, k) : getCell(k, j);
        if ((localCell == null) || (localCell.compWraps.size() == 0))
          continue;
        int m = paramBoolean ? localCell.spany : localCell.spanx;
        if (m > 1)
          m = convertSpanToSparseGrid(j, m, localTreeSet1);
        int n = localCell.flowx == paramBoolean ? 1 : 0;
        int i1;
        Object localObject;
        if (((n == 0) && (localCell.compWraps.size() > 1)) || (m > 1))
        {
          i1 = n != 0 ? 1 : 0;
          localObject = new LinkedDimGroup("p," + k, m, i1, !paramBoolean, bool1, null);
          ((LinkedDimGroup)localObject).setCompWraps(localCell.compWraps);
          localArrayList.add(localObject);
        }
        else
        {
          for (i1 = 0; i1 < localCell.compWraps.size(); i1++)
          {
            localObject = (CompWrap)localCell.compWraps.get(i1);
            if ((paramBoolean) && (this.lc.isTopToBottom()));
            boolean bool2 = localDimConstraint.getAlignOrDefault(!paramBoolean) == UnitValue.BASELINE_IDENTITY;
            int i2 = (paramBoolean) && (((CompWrap)localObject).isBaselineAlign(bool2)) ? 1 : 0;
            String str = i2 != 0 ? "baseline" : null;
            int i3 = 0;
            int i4 = 0;
            int i5 = localArrayList.size() - 1;
            while (i4 <= i5)
            {
              LinkedDimGroup localLinkedDimGroup2 = (LinkedDimGroup)localArrayList.get(i4);
              if ((localLinkedDimGroup2.linkCtx == str) || ((str != null) && (str.equals(localLinkedDimGroup2.linkCtx))))
              {
                localLinkedDimGroup2.addCompWrap((CompWrap)localObject);
                i3 = 1;
                break;
              }
              i4++;
            }
            if (i3 != 0)
              continue;
            i4 = i2 != 0 ? 2 : 1;
            LinkedDimGroup localLinkedDimGroup1 = new LinkedDimGroup(str, 1, i4, !paramBoolean, bool1, null);
            localLinkedDimGroup1.addCompWrap((CompWrap)localObject);
            localArrayList.add(localLinkedDimGroup1);
          }
        }
      }
    }
    return (ArrayList<LinkedDimGroup>)arrayOfArrayList;
  }

  private static int convertSpanToSparseGrid(int paramInt1, int paramInt2, TreeSet<Integer> paramTreeSet)
  {
    int i = paramInt1 + paramInt2;
    int j = 1;
    Iterator localIterator = paramTreeSet.iterator();
    while (localIterator.hasNext())
    {
      int k = ((Integer)localIterator.next()).intValue();
      if (k <= paramInt1)
        continue;
      if (k >= i)
        break;
      j++;
    }
    return j;
  }

  private final boolean isCellFree(int paramInt1, int paramInt2, ArrayList<int[]> paramArrayList)
  {
    if (getCell(paramInt1, paramInt2) != null)
      return false;
    for (int i = 0; i < paramArrayList.size(); i++)
    {
      int[] arrayOfInt = (int[])paramArrayList.get(i);
      if ((arrayOfInt[0] <= paramInt2) && (arrayOfInt[1] <= paramInt1) && (arrayOfInt[0] + arrayOfInt[2] > paramInt2) && (arrayOfInt[1] + arrayOfInt[3] > paramInt1))
        return false;
    }
    return true;
  }

  private Cell getCell(int paramInt1, int paramInt2)
  {
    return (Cell)this.grid.get(new Integer((paramInt1 << 16) + paramInt2));
  }

  private void setCell(int paramInt1, int paramInt2, Cell paramCell)
  {
    if ((paramInt2 < 0) || (paramInt2 > 30000) || (paramInt1 < 0) || (paramInt1 > 30000))
      throw new IllegalArgumentException("Cell position out of bounds. row: " + paramInt1 + ", col: " + paramInt2);
    this.rowIndexes.add(new Integer(paramInt1));
    this.colIndexes.add(new Integer(paramInt2));
    this.grid.put(new Integer((paramInt1 << 16) + paramInt2), paramCell);
  }

  private void addDockingCell(int[] paramArrayOfInt, int paramInt, CompWrap paramCompWrap)
  {
    int k = 1;
    int m = 1;
    int i;
    int j;
    switch (paramInt)
    {
    case 0:
    case 2:
      int tmp42_41 = 0;
      int[] tmp42_40 = paramArrayOfInt;
      int tmp44_43 = tmp42_40[tmp42_41];
      tmp42_40[tmp42_41] = (tmp44_43 + 1);
      int tmp53_52 = 2;
      int[] tmp53_51 = paramArrayOfInt;
      int tmp55_54 = tmp53_51[tmp53_52];
      tmp53_51[tmp53_52] = (paramInt == 0 ? tmp44_43 : tmp55_54 - 1);
      i = tmp55_54;
      j = paramArrayOfInt[1];
      k = paramArrayOfInt[3] - paramArrayOfInt[1] + 1;
      this.colIndexes.add(new Integer(paramArrayOfInt[3]));
      break;
    case 1:
    case 3:
      int tmp105_104 = 1;
      int[] tmp105_103 = paramArrayOfInt;
      int tmp107_106 = tmp105_103[tmp105_104];
      tmp105_103[tmp105_104] = (tmp107_106 + 1);
      int tmp116_115 = 3;
      int[] tmp116_114 = paramArrayOfInt;
      int tmp118_117 = tmp116_114[tmp116_115];
      tmp116_114[tmp116_115] = (paramInt == 1 ? tmp107_106 : tmp118_117 - 1);
      j = tmp118_117;
      i = paramArrayOfInt[0];
      m = paramArrayOfInt[2] - paramArrayOfInt[0] + 1;
      this.rowIndexes.add(new Integer(paramArrayOfInt[2]));
      break;
    default:
      throw new IllegalArgumentException("Internal error 123.");
    }
    this.rowIndexes.add(new Integer(i));
    this.colIndexes.add(new Integer(j));
    this.grid.put(new Integer((i << 16) + j), new Cell(paramCompWrap, k, m, k > 1, null));
  }

  private static void layoutBaseline(ContainerWrapper paramContainerWrapper, ArrayList<CompWrap> paramArrayList, DimConstraint paramDimConstraint, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int[] arrayOfInt = getBaselineAboveBelow(paramArrayList, paramInt3, true);
    int i = arrayOfInt[0] + arrayOfInt[1];
    CC localCC = ((CompWrap)paramArrayList.get(0)).cc;
    UnitValue localUnitValue = localCC.getVertical().getAlign();
    if ((paramInt4 == 1) && (localUnitValue == null))
      localUnitValue = paramDimConstraint.getAlignOrDefault(false);
    if (localUnitValue == UnitValue.BASELINE_IDENTITY)
      localUnitValue = UnitValue.CENTER;
    int j = paramInt1 + arrayOfInt[0] + (localUnitValue != null ? Math.max(0, localUnitValue.getPixels(paramInt2 - i, paramContainerWrapper, null)) : 0);
    int k = 0;
    int m = paramArrayList.size();
    while (k < m)
    {
      CompWrap localCompWrap = (CompWrap)paramArrayList.get(k);
      CompWrap.access$1612(localCompWrap, j);
      if (localCompWrap.y + localCompWrap.h > paramInt1 + paramInt2)
        CompWrap.access$1702(localCompWrap, paramInt1 + paramInt2 - localCompWrap.y);
      k++;
    }
  }

  private static void layoutSerial(ContainerWrapper paramContainerWrapper, ArrayList<CompWrap> paramArrayList, DimConstraint paramDimConstraint, int paramInt1, int paramInt2, boolean paramBoolean1, int paramInt3, boolean paramBoolean2)
  {
    FlowSizeSpec localFlowSizeSpec = mergeSizesGapsAndResConstrs(getComponentResizeConstraints(paramArrayList, paramBoolean1), getComponentGapPush(paramArrayList, paramBoolean1), getComponentSizes(paramArrayList, paramBoolean1), getGaps(paramArrayList, paramBoolean1));
    Float[] arrayOfFloat = paramDimConstraint.isFill() ? GROW_100 : null;
    int[] arrayOfInt = LayoutUtil.calculateSerial(localFlowSizeSpec.sizes, localFlowSizeSpec.resConstsInclGaps, arrayOfFloat, 1, paramInt2);
    setCompWrapBounds(paramContainerWrapper, arrayOfInt, paramArrayList, paramDimConstraint.getAlignOrDefault(paramBoolean1), paramInt1, paramInt2, paramBoolean1, paramBoolean2);
  }

  private static void setCompWrapBounds(ContainerWrapper paramContainerWrapper, int[] paramArrayOfInt, ArrayList<CompWrap> paramArrayList, UnitValue paramUnitValue, int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
  {
    int i = LayoutUtil.sum(paramArrayOfInt);
    CC localCC = ((CompWrap)paramArrayList.get(0)).cc;
    UnitValue localUnitValue = correctAlign(localCC, paramUnitValue, paramBoolean1, paramBoolean2);
    int j = paramInt1;
    int k = paramInt2 - i;
    if ((k > 0) && (localUnitValue != null))
    {
      m = Math.min(k, Math.max(0, localUnitValue.getPixels(k, paramContainerWrapper, null)));
      j += (paramBoolean2 ? -m : m);
    }
    int m = 0;
    int n = 0;
    int i1 = paramArrayList.size();
    while (m < i1)
    {
      CompWrap localCompWrap = (CompWrap)paramArrayList.get(m);
      if (paramBoolean2)
      {
        j -= paramArrayOfInt[(n++)];
        localCompWrap.setDimBounds(j - paramArrayOfInt[n], paramArrayOfInt[n], paramBoolean1);
        j -= paramArrayOfInt[(n++)];
      }
      else
      {
        j += paramArrayOfInt[(n++)];
        localCompWrap.setDimBounds(j, paramArrayOfInt[n], paramBoolean1);
        j += paramArrayOfInt[(n++)];
      }
      m++;
    }
  }

  private static void layoutParallel(ContainerWrapper paramContainerWrapper, ArrayList<CompWrap> paramArrayList, DimConstraint paramDimConstraint, int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
  {
    int[][] arrayOfInt1 = new int[paramArrayList.size()][];
    for (int i = 0; i < arrayOfInt1.length; i++)
    {
      CompWrap localCompWrap = (CompWrap)paramArrayList.get(i);
      DimConstraint localDimConstraint = localCompWrap.cc.getDimConstraint(paramBoolean1);
      ResizeConstraint[] arrayOfResizeConstraint = { localCompWrap.isPushGap(paramBoolean1, true) ? GAP_RC_CONST_PUSH : GAP_RC_CONST, localDimConstraint.resize, localCompWrap.isPushGap(paramBoolean1, false) ? GAP_RC_CONST_PUSH : GAP_RC_CONST };
      int[][] arrayOfInt2 = { CompWrap.access$3400(localCompWrap, paramBoolean1, true), paramBoolean1 ? localCompWrap.horSizes : CompWrap.access$900(localCompWrap), CompWrap.access$3400(localCompWrap, paramBoolean1, false) };
      Float[] arrayOfFloat = paramDimConstraint.isFill() ? GROW_100 : null;
      arrayOfInt1[i] = LayoutUtil.calculateSerial(arrayOfInt2, arrayOfResizeConstraint, arrayOfFloat, 1, paramInt2);
    }
    UnitValue localUnitValue = paramDimConstraint.getAlignOrDefault(paramBoolean1);
    setCompWrapBounds(paramContainerWrapper, arrayOfInt1, paramArrayList, localUnitValue, paramInt1, paramInt2, paramBoolean1, paramBoolean2);
  }

  private static void setCompWrapBounds(ContainerWrapper paramContainerWrapper, int[][] paramArrayOfInt, ArrayList<CompWrap> paramArrayList, UnitValue paramUnitValue, int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
  {
    for (int i = 0; i < paramArrayOfInt.length; i++)
    {
      CompWrap localCompWrap = (CompWrap)paramArrayList.get(i);
      UnitValue localUnitValue = correctAlign(localCompWrap.cc, paramUnitValue, paramBoolean1, paramBoolean2);
      int[] arrayOfInt = paramArrayOfInt[i];
      int j = arrayOfInt[0];
      int k = arrayOfInt[1];
      int m = arrayOfInt[2];
      int n = paramBoolean2 ? paramInt1 - j : paramInt1 + j;
      int i1 = paramInt2 - k - j - m;
      if ((i1 > 0) && (localUnitValue != null))
      {
        int i2 = Math.min(i1, Math.max(0, localUnitValue.getPixels(i1, paramContainerWrapper, null)));
        n += (paramBoolean2 ? -i2 : i2);
      }
      localCompWrap.setDimBounds(paramBoolean2 ? n - k : n, k, paramBoolean1);
    }
  }

  private static UnitValue correctAlign(CC paramCC, UnitValue paramUnitValue, boolean paramBoolean1, boolean paramBoolean2)
  {
    UnitValue localUnitValue = (paramBoolean1 ? paramCC.getHorizontal() : paramCC.getVertical()).getAlign();
    if (localUnitValue == null)
      localUnitValue = paramUnitValue;
    if (localUnitValue == UnitValue.BASELINE_IDENTITY)
      localUnitValue = UnitValue.CENTER;
    if (paramBoolean2)
      if (localUnitValue == UnitValue.LEFT)
        localUnitValue = UnitValue.RIGHT;
      else if (localUnitValue == UnitValue.RIGHT)
        localUnitValue = UnitValue.LEFT;
    return localUnitValue;
  }

  private static int[] getBaselineAboveBelow(ArrayList<CompWrap> paramArrayList, int paramInt, boolean paramBoolean)
  {
    int i = -32768;
    int j = -32768;
    int k = 0;
    int m = paramArrayList.size();
    while (k < m)
    {
      CompWrap localCompWrap = (CompWrap)paramArrayList.get(k);
      int n = localCompWrap.getSize(paramInt, false);
      if (n >= 2097051)
        return new int[] { 1048525, 1048525 };
      int i1 = localCompWrap.getBaseline(paramInt);
      int i2 = i1 + localCompWrap.getGapBefore(paramInt, false);
      i = Math.max(i2, i);
      j = Math.max(n - i1 + localCompWrap.getGapAfter(paramInt, false), j);
      if (paramBoolean)
        localCompWrap.setDimBounds(-i1, n, false);
      k++;
    }
    return new int[] { i, j };
  }

  private static int getTotalSizeParallel(ArrayList<CompWrap> paramArrayList, int paramInt, boolean paramBoolean)
  {
    int i = paramInt == 2 ? 2097051 : 0;
    int j = 0;
    int k = paramArrayList.size();
    while (j < k)
    {
      CompWrap localCompWrap = (CompWrap)paramArrayList.get(j);
      int m = localCompWrap.getSizeInclGaps(paramInt, paramBoolean);
      if (m >= 2097051)
        return 2097051;
      if (paramInt == 2 ? m < i : m > i)
        i = m;
      j++;
    }
    return constrainSize(i);
  }

  private static final int getTotalSizeSerial(ArrayList<CompWrap> paramArrayList, int paramInt, boolean paramBoolean)
  {
    int i = 0;
    int j = 0;
    int k = paramArrayList.size();
    int m = 0;
    while (j < k)
    {
      CompWrap localCompWrap = (CompWrap)paramArrayList.get(j);
      int n = localCompWrap.getGapBefore(paramInt, paramBoolean);
      if (n > m)
        i += n - m;
      i += localCompWrap.getSize(paramInt, paramBoolean);
      i += (m = localCompWrap.getGapAfter(paramInt, paramBoolean));
      if (i >= 2097051)
        return 2097051;
      j++;
    }
    return constrainSize(i);
  }

  private static final int getTotalGroupsSizeParallel(ArrayList<LinkedDimGroup> paramArrayList, int paramInt, boolean paramBoolean)
  {
    int i = paramInt == 2 ? 2097051 : 0;
    int j = 0;
    int k = paramArrayList.size();
    while (j < k)
    {
      LinkedDimGroup localLinkedDimGroup = (LinkedDimGroup)paramArrayList.get(j);
      if ((paramBoolean) || (localLinkedDimGroup.span == 1))
      {
        int m = localLinkedDimGroup.getMinPrefMax()[paramInt];
        if (m >= 2097051)
          return 2097051;
        if (paramInt == 2 ? m < i : m > i)
          i = m;
      }
      j++;
    }
    return constrainSize(i);
  }

  private static int[][] getComponentSizes(ArrayList<CompWrap> paramArrayList, boolean paramBoolean)
  {
    int[][] arrayOfInt = new int[paramArrayList.size()][];
    for (int i = 0; i < arrayOfInt.length; i++)
    {
      CompWrap localCompWrap = (CompWrap)paramArrayList.get(i);
      arrayOfInt[i] = (paramBoolean ? localCompWrap.horSizes : CompWrap.access$900(localCompWrap));
    }
    return arrayOfInt;
  }

  private static FlowSizeSpec mergeSizesGapsAndResConstrs(ResizeConstraint[] paramArrayOfResizeConstraint, boolean[] paramArrayOfBoolean, int[][] paramArrayOfInt1, int[][] paramArrayOfInt2)
  {
    int[][] arrayOfInt = new int[(paramArrayOfInt1.length << 1) + 1][];
    ResizeConstraint[] arrayOfResizeConstraint = new ResizeConstraint[arrayOfInt.length];
    arrayOfInt[0] = paramArrayOfInt2[0];
    int i = 0;
    for (int j = 1; i < paramArrayOfInt1.length; j += 2)
    {
      arrayOfResizeConstraint[j] = paramArrayOfResizeConstraint[i];
      arrayOfInt[j] = paramArrayOfInt1[i];
      arrayOfInt[(j + 1)] = paramArrayOfInt2[(i + 1)];
      if (arrayOfInt[(j - 1)] != null)
        arrayOfResizeConstraint[(j - 1)] = (paramArrayOfBoolean[(paramArrayOfBoolean.length - 1)] != 0 ? GAP_RC_CONST_PUSH : GAP_RC_CONST);
      if ((i == paramArrayOfInt1.length - 1) && (arrayOfInt[(j + 1)] != null))
        arrayOfResizeConstraint[(j + 1)] = (paramArrayOfBoolean[(paramArrayOfBoolean.length - 1)] != 0 ? GAP_RC_CONST_PUSH : GAP_RC_CONST);
      i++;
    }
    for (i = 0; i < arrayOfInt.length; i++)
    {
      if (arrayOfInt[i] != null)
        continue;
      arrayOfInt[i] = new int[3];
    }
    return new FlowSizeSpec(arrayOfInt, arrayOfResizeConstraint, null);
  }

  private static final int[] mergeSizes(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    if (paramArrayOfInt1 == null)
      return paramArrayOfInt2;
    if (paramArrayOfInt2 == null)
      return paramArrayOfInt1;
    int[] arrayOfInt = new int[paramArrayOfInt1.length];
    for (int i = 0; i < arrayOfInt.length; i++)
      arrayOfInt[i] = mergeSizes(paramArrayOfInt1[i], paramArrayOfInt2[i], true);
    return arrayOfInt;
  }

  private static final int mergeSizes(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    if ((paramInt1 == -2147471302) || (paramInt1 == paramInt2))
      return paramInt2;
    if (paramInt2 == -2147471302)
      return paramInt1;
    return paramBoolean != paramInt1 > paramInt2 ? paramInt2 : paramInt1;
  }

  private static final int constrainSize(int paramInt)
  {
    return paramInt > 0 ? 2097051 : paramInt < 2097051 ? paramInt : 0;
  }

  private static final void correctMinMax(int[] paramArrayOfInt)
  {
    if (paramArrayOfInt[0] > paramArrayOfInt[2])
      paramArrayOfInt[0] = paramArrayOfInt[2];
    if (paramArrayOfInt[1] < paramArrayOfInt[0])
      paramArrayOfInt[1] = paramArrayOfInt[0];
    if (paramArrayOfInt[1] > paramArrayOfInt[2])
      paramArrayOfInt[1] = paramArrayOfInt[2];
  }

  private static Float[] extractSubArray(DimConstraint[] paramArrayOfDimConstraint, Float[] paramArrayOfFloat, int paramInt1, int paramInt2)
  {
    if ((paramArrayOfFloat == null) || (paramArrayOfFloat.length < paramInt1 + paramInt2))
    {
      arrayOfFloat = new Float[paramInt2];
      for (i = paramInt1 + paramInt2 - 1; i >= 0; i -= 2)
      {
        int j = i >> 1;
        if (paramArrayOfDimConstraint[j] == DOCK_DIM_CONSTRAINT)
          continue;
        arrayOfFloat[(i - paramInt1)] = ResizeConstraint.WEIGHT_100;
        return arrayOfFloat;
      }
      return arrayOfFloat;
    }
    Float[] arrayOfFloat = new Float[paramInt2];
    for (int i = 0; i < paramInt2; i++)
      arrayOfFloat[i] = paramArrayOfFloat[(paramInt1 + i)];
    return arrayOfFloat;
  }

  private static synchronized void putSizesAndIndexes(Object paramObject, int[] paramArrayOfInt1, int[] paramArrayOfInt2, boolean paramBoolean)
  {
    if (PARENT_ROWCOL_SIZES_MAP == null)
      PARENT_ROWCOL_SIZES_MAP = new WeakHashMap[] { new WeakHashMap(4), new WeakHashMap(4) };
    PARENT_ROWCOL_SIZES_MAP[1].put(paramObject, new int[][] { paramArrayOfInt2, paramArrayOfInt1 });
  }

  static synchronized int[][] getSizesAndIndexes(Object paramObject, boolean paramBoolean)
  {
    if (PARENT_ROWCOL_SIZES_MAP == null)
      return (int[][])null;
    return (int[][])(int[][])PARENT_ROWCOL_SIZES_MAP[1].get(paramObject);
  }

  private static synchronized void saveGrid(ComponentWrapper paramComponentWrapper, LinkedHashMap<Integer, Cell> paramLinkedHashMap)
  {
    if (PARENT_GRIDPOS_MAP == null)
      PARENT_GRIDPOS_MAP = new WeakHashMap();
    PARENT_GRIDPOS_MAP.put(paramComponentWrapper.getComponent(), paramLinkedHashMap);
  }

  static synchronized HashMap<Object, int[]> getGridPositions(Object paramObject)
  {
    if (PARENT_GRIDPOS_MAP == null)
      return null;
    LinkedHashMap localLinkedHashMap = (LinkedHashMap)PARENT_GRIDPOS_MAP.get(paramObject);
    if (localLinkedHashMap == null)
      return null;
    HashMap localHashMap = new HashMap();
    Iterator localIterator1 = localLinkedHashMap.entrySet().iterator();
    while (localIterator1.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator1.next();
      Cell localCell = (Cell)localEntry.getValue();
      Integer localInteger = (Integer)localEntry.getKey();
      if (localInteger != null)
      {
        int i = localInteger.intValue();
        int j = i & 0xFFFF;
        int k = i >> 16;
        Iterator localIterator2 = localCell.compWraps.iterator();
        while (localIterator2.hasNext())
        {
          CompWrap localCompWrap = (CompWrap)localIterator2.next();
          localHashMap.put(localCompWrap.comp.getComponent(), new int[] { j, k, Cell.access$500(localCell), Cell.access$600(localCell) });
        }
      }
    }
    return localHashMap;
  }

  static
  {
    DOCK_DIM_CONSTRAINT.setGrowPriority(0);
    GAP_RC_CONST = new ResizeConstraint(200, ResizeConstraint.WEIGHT_100, 50, null);
    GAP_RC_CONST_PUSH = new ResizeConstraint(200, ResizeConstraint.WEIGHT_100, 50, ResizeConstraint.WEIGHT_100);
    PARENT_ROWCOL_SIZES_MAP = null;
    PARENT_GRIDPOS_MAP = null;
  }

  private static final class FlowSizeSpec
  {
    private final int[][] sizes;
    private final ResizeConstraint[] resConstsInclGaps;

    private FlowSizeSpec(int[][] paramArrayOfInt, ResizeConstraint[] paramArrayOfResizeConstraint)
    {
      this.sizes = paramArrayOfInt;
      this.resConstsInclGaps = paramArrayOfResizeConstraint;
    }

    private int expandSizes(DimConstraint[] paramArrayOfDimConstraint, Float[] paramArrayOfFloat, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
    {
      ResizeConstraint[] arrayOfResizeConstraint = new ResizeConstraint[paramInt3];
      int[][] arrayOfInt = new int[paramInt3][];
      for (int i = 0; i < paramInt3; i++)
      {
        arrayOfInt1 = this.sizes[(i + paramInt2)];
        arrayOfInt[i] = { arrayOfInt1[paramInt4], arrayOfInt1[1], arrayOfInt1[2] };
        if ((paramInt5 <= 1) && (i % 2 == 0))
        {
          j = i + paramInt2 - 1 >> 1;
          DimConstraint localDimConstraint = (DimConstraint)LayoutUtil.getIndexSafe(paramArrayOfDimConstraint, j);
          BoundSize localBoundSize = localDimConstraint.getSize();
          if (((paramInt4 == 0) && (localBoundSize.getMin() != null) && (localBoundSize.getMin().getUnit() != 13)) || ((paramInt4 == 1) && (localBoundSize.getPreferred() != null) && (localBoundSize.getPreferred().getUnit() != 14)))
            continue;
        }
        arrayOfResizeConstraint[i] = ((ResizeConstraint)LayoutUtil.getIndexSafe(this.resConstsInclGaps, i + paramInt2));
      }
      Float[] arrayOfFloat = (paramInt5 == 1) || (paramInt5 == 3) ? Grid.access$5600(paramArrayOfDimConstraint, paramArrayOfFloat, paramInt2, paramInt3) : null;
      int[] arrayOfInt1 = LayoutUtil.calculateSerial(arrayOfInt, arrayOfResizeConstraint, arrayOfFloat, 1, paramInt1);
      int j = 0;
      for (int k = 0; k < paramInt3; k++)
      {
        int m = arrayOfInt1[k];
        this.sizes[(k + paramInt2)][paramInt4] = m;
        j += m;
      }
      return j;
    }
  }

  private static final class CompWrap
  {
    private final ComponentWrapper comp;
    private final CC cc;
    private final UnitValue[] pos;
    private int[][] gaps;
    private final int[] horSizes = new int[3];
    private final int[] verSizes = new int[3];
    private int x = -2147471302;
    private int y = -2147471302;
    private int w = -2147471302;
    private int h = -2147471302;
    private int forcedPushGaps = 0;

    private CompWrap(ComponentWrapper paramComponentWrapper, CC paramCC, int paramInt, UnitValue[] paramArrayOfUnitValue, BoundSize[] paramArrayOfBoundSize)
    {
      this.comp = paramComponentWrapper;
      this.cc = paramCC;
      this.pos = paramArrayOfUnitValue;
      if (paramInt <= 0)
      {
        BoundSize localBoundSize1 = (paramArrayOfBoundSize != null) && (paramArrayOfBoundSize[0] != null) ? paramArrayOfBoundSize[0] : paramCC.getHorizontal().getSize();
        BoundSize localBoundSize2 = (paramArrayOfBoundSize != null) && (paramArrayOfBoundSize[1] != null) ? paramArrayOfBoundSize[1] : paramCC.getVertical().getSize();
        int j = -1;
        int k = -1;
        if ((this.comp.getWidth() > 0) && (this.comp.getHeight() > 0))
        {
          k = this.comp.getHeight();
          j = this.comp.getWidth();
        }
        for (int m = 0; m <= 2; m++)
        {
          this.horSizes[m] = getSize(localBoundSize1, m, true, k);
          this.verSizes[m] = getSize(localBoundSize2, m, false, j > 0 ? j : this.horSizes[m]);
        }
        Grid.access$4900(this.horSizes);
        Grid.access$4900(this.verSizes);
      }
      if (paramInt > 1)
      {
        this.gaps = new int[4][];
        for (int i = 0; i < this.gaps.length; i++)
          this.gaps[i] = new int[3];
      }
    }

    private final int getSize(BoundSize paramBoundSize, int paramInt1, boolean paramBoolean, int paramInt2)
    {
      if ((paramBoundSize == null) || (paramBoundSize.getSize(paramInt1) == null))
      {
        switch (paramInt1)
        {
        case 0:
          return paramBoolean ? this.comp.getMinimumWidth(paramInt2) : this.comp.getMinimumHeight(paramInt2);
        case 1:
          return paramBoolean ? this.comp.getPreferredWidth(paramInt2) : this.comp.getPreferredHeight(paramInt2);
        }
        return paramBoolean ? this.comp.getMaximumWidth(paramInt2) : this.comp.getMaximumHeight(paramInt2);
      }
      ContainerWrapper localContainerWrapper = this.comp.getParent();
      return paramBoundSize.getSize(paramInt1).getPixels(localContainerWrapper.getHeight(), localContainerWrapper, this.comp);
    }

    private final void calcGaps(ComponentWrapper paramComponentWrapper1, CC paramCC1, ComponentWrapper paramComponentWrapper2, CC paramCC2, String paramString, boolean paramBoolean1, boolean paramBoolean2)
    {
      ContainerWrapper localContainerWrapper = this.comp.getParent();
      int i = localContainerWrapper.getWidth();
      int j = localContainerWrapper.getHeight();
      BoundSize localBoundSize1 = paramComponentWrapper1 != null ? (paramBoolean1 ? paramCC1.getHorizontal() : paramCC1.getVertical()).getGapAfter() : null;
      BoundSize localBoundSize2 = paramComponentWrapper2 != null ? (paramBoolean1 ? paramCC2.getHorizontal() : paramCC2.getVertical()).getGapBefore() : null;
      mergeGapSizes(this.cc.getVertical().getComponentGaps(localContainerWrapper, this.comp, localBoundSize1, paramBoolean1 ? null : paramComponentWrapper1, paramString, j, 0, paramBoolean2), false, true);
      mergeGapSizes(this.cc.getHorizontal().getComponentGaps(localContainerWrapper, this.comp, localBoundSize1, paramBoolean1 ? paramComponentWrapper1 : null, paramString, i, 1, paramBoolean2), true, true);
      mergeGapSizes(this.cc.getVertical().getComponentGaps(localContainerWrapper, this.comp, localBoundSize2, paramBoolean1 ? null : paramComponentWrapper2, paramString, j, 2, paramBoolean2), false, false);
      mergeGapSizes(this.cc.getHorizontal().getComponentGaps(localContainerWrapper, this.comp, localBoundSize2, paramBoolean1 ? paramComponentWrapper2 : null, paramString, i, 3, paramBoolean2), true, false);
    }

    private final void setDimBounds(int paramInt1, int paramInt2, boolean paramBoolean)
    {
      if (paramBoolean)
      {
        this.x = paramInt1;
        this.w = paramInt2;
      }
      else
      {
        this.y = paramInt1;
        this.h = paramInt2;
      }
    }

    private final boolean isPushGap(boolean paramBoolean1, boolean paramBoolean2)
    {
      if (paramBoolean1)
        if (((paramBoolean2 ? 1 : 2) & this.forcedPushGaps) != 0)
          return true;
      DimConstraint localDimConstraint = this.cc.getDimConstraint(paramBoolean1);
      BoundSize localBoundSize = paramBoolean2 ? localDimConstraint.getGapBefore() : localDimConstraint.getGapAfter();
      return (localBoundSize != null) && (localBoundSize.getGapPush());
    }

    private final boolean transferBounds(boolean paramBoolean)
    {
      this.comp.setBounds(this.x, this.y, this.w, this.h);
      if ((paramBoolean) && (this.w != this.horSizes[1]))
      {
        BoundSize localBoundSize = this.cc.getVertical().getSize();
        if ((localBoundSize.getPreferred() == null) && (this.comp.getPreferredHeight(-1) != this.verSizes[1]))
          return true;
      }
      return false;
    }

    private final void setSizes(int[] paramArrayOfInt, boolean paramBoolean)
    {
      if (paramArrayOfInt == null)
        return;
      int[] arrayOfInt = paramBoolean ? this.horSizes : this.verSizes;
      arrayOfInt[0] = paramArrayOfInt[0];
      arrayOfInt[1] = paramArrayOfInt[1];
      arrayOfInt[2] = paramArrayOfInt[2];
    }

    private void setGaps(int[] paramArrayOfInt, int paramInt)
    {
      if (this.gaps == null)
        this.gaps = new int[][] { null, null, null, null };
      this.gaps[paramInt] = paramArrayOfInt;
    }

    private final void mergeGapSizes(int[] paramArrayOfInt, boolean paramBoolean1, boolean paramBoolean2)
    {
      if (this.gaps == null)
        this.gaps = new int[][] { null, null, null, null };
      if (paramArrayOfInt == null)
        return;
      int i = getGapIx(paramBoolean1, paramBoolean2);
      int[] arrayOfInt = this.gaps[i];
      if (arrayOfInt == null)
      {
        arrayOfInt = new int[] { 0, 0, 2097051 };
        this.gaps[i] = arrayOfInt;
      }
      arrayOfInt[0] = Math.max(paramArrayOfInt[0], arrayOfInt[0]);
      arrayOfInt[1] = Math.max(paramArrayOfInt[1], arrayOfInt[1]);
      arrayOfInt[2] = Math.min(paramArrayOfInt[2], arrayOfInt[2]);
    }

    private final int getGapIx(boolean paramBoolean1, boolean paramBoolean2)
    {
      return paramBoolean2 ? 0 : paramBoolean1 ? 3 : paramBoolean2 ? 1 : 2;
    }

    private final int getSizeInclGaps(int paramInt, boolean paramBoolean)
    {
      return filter(paramInt, getGapBefore(paramInt, paramBoolean) + getSize(paramInt, paramBoolean) + getGapAfter(paramInt, paramBoolean));
    }

    private final int getSize(int paramInt, boolean paramBoolean)
    {
      return filter(paramInt, paramBoolean ? this.horSizes[paramInt] : this.verSizes[paramInt]);
    }

    private final int getGapBefore(int paramInt, boolean paramBoolean)
    {
      int[] arrayOfInt = getGaps(paramBoolean, true);
      return arrayOfInt != null ? filter(paramInt, arrayOfInt[paramInt]) : 0;
    }

    private final int getGapAfter(int paramInt, boolean paramBoolean)
    {
      int[] arrayOfInt = getGaps(paramBoolean, false);
      return arrayOfInt != null ? filter(paramInt, arrayOfInt[paramInt]) : 0;
    }

    private final int[] getGaps(boolean paramBoolean1, boolean paramBoolean2)
    {
      return this.gaps[getGapIx(paramBoolean1, paramBoolean2)];
    }

    private final int filter(int paramInt1, int paramInt2)
    {
      if (paramInt2 == -2147471302)
        return paramInt1 != 2 ? 0 : 2097051;
      return Grid.access$5000(paramInt2);
    }

    private final boolean isBaselineAlign(boolean paramBoolean)
    {
      Float localFloat = this.cc.getVertical().getGrow();
      if ((localFloat != null) && (localFloat.intValue() != 0))
        return false;
      UnitValue localUnitValue = this.cc.getVertical().getAlign();
      return (localUnitValue != null ? localUnitValue == UnitValue.BASELINE_IDENTITY : paramBoolean) && (this.comp.hasBaseline());
    }

    private final int getBaseline(int paramInt)
    {
      return this.comp.getBaseline(getSize(paramInt, true), getSize(paramInt, false));
    }
  }

  private static class LinkedDimGroup
  {
    private static final int TYPE_SERIAL = 0;
    private static final int TYPE_PARALLEL = 1;
    private static final int TYPE_BASELINE = 2;
    private final String linkCtx;
    private final int span;
    private final int linkType;
    private final boolean isHor;
    private final boolean fromEnd;
    private ArrayList<Grid.CompWrap> _compWraps = new ArrayList(4);
    private int[] sizes = null;
    private int lStart = 0;
    private int lSize = 0;

    private LinkedDimGroup(String paramString, int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
    {
      this.linkCtx = paramString;
      this.span = paramInt1;
      this.linkType = paramInt2;
      this.isHor = paramBoolean1;
      this.fromEnd = paramBoolean2;
    }

    private void addCompWrap(Grid.CompWrap paramCompWrap)
    {
      this._compWraps.add(paramCompWrap);
      this.sizes = null;
    }

    private void setCompWraps(ArrayList<Grid.CompWrap> paramArrayList)
    {
      if (this._compWraps != paramArrayList)
      {
        this._compWraps = paramArrayList;
        this.sizes = null;
      }
    }

    private void layout(DimConstraint paramDimConstraint, int paramInt1, int paramInt2, int paramInt3)
    {
      this.lStart = paramInt1;
      this.lSize = paramInt2;
      if (this._compWraps.size() == 0)
        return;
      ContainerWrapper localContainerWrapper = ((Grid.CompWrap)this._compWraps.get(0)).comp.getParent();
      if (this.linkType == 1)
        Grid.access$4300(localContainerWrapper, this._compWraps, paramDimConstraint, paramInt1, paramInt2, this.isHor, this.fromEnd);
      else if (this.linkType == 2)
        Grid.access$4400(localContainerWrapper, this._compWraps, paramDimConstraint, paramInt1, paramInt2, 1, paramInt3);
      else
        Grid.access$4500(localContainerWrapper, this._compWraps, paramDimConstraint, paramInt1, paramInt2, this.isHor, paramInt3, this.fromEnd);
    }

    private int[] getMinPrefMax()
    {
      if ((this.sizes == null) && (this._compWraps.size() > 0))
      {
        this.sizes = new int[3];
        for (int i = 0; i <= 1; i++)
          if (this.linkType == 1)
          {
            this.sizes[i] = Grid.access$4600(this._compWraps, i, this.isHor);
          }
          else if (this.linkType == 2)
          {
            int[] arrayOfInt = Grid.access$4700(this._compWraps, i, false);
            this.sizes[i] = (arrayOfInt[0] + arrayOfInt[1]);
          }
          else
          {
            this.sizes[i] = Grid.access$4800(this._compWraps, i, this.isHor);
          }
        this.sizes[2] = 2097051;
      }
      return this.sizes;
    }
  }

  private static class Cell
  {
    private final int spanx;
    private final int spany;
    private final boolean flowx;
    private final ArrayList<Grid.CompWrap> compWraps = new ArrayList(1);
    private boolean hasTagged = false;

    private Cell(Grid.CompWrap paramCompWrap)
    {
      this(paramCompWrap, 1, 1, true);
    }

    private Cell(int paramInt1, int paramInt2, boolean paramBoolean)
    {
      this(null, paramInt1, paramInt2, paramBoolean);
    }

    private Cell(Grid.CompWrap paramCompWrap, int paramInt1, int paramInt2, boolean paramBoolean)
    {
      if (paramCompWrap != null)
        this.compWraps.add(paramCompWrap);
      this.spanx = paramInt1;
      this.spany = paramInt2;
      this.flowx = paramBoolean;
    }
  }
}

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     net.miginfocom.layout.Grid
 * JD-Core Version:    0.6.0
 */