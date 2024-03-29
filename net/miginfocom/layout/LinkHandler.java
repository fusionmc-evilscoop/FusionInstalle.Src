package net.miginfocom.layout;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

public final class LinkHandler
{
  public static final int X = 0;
  public static final int Y = 1;
  public static final int WIDTH = 2;
  public static final int HEIGHT = 3;
  public static final int X2 = 4;
  public static final int Y2 = 5;
  private static final ArrayList<WeakReference<Object>> LAYOUTS = new ArrayList(4);
  private static final ArrayList<HashMap<String, int[]>> VALUES = new ArrayList(4);
  private static final ArrayList<HashMap<String, int[]>> VALUES_TEMP = new ArrayList(4);

  public static synchronized Integer getValue(Object paramObject, String paramString, int paramInt)
  {
    Integer localInteger = null;
    int i = 1;
    for (int j = LAYOUTS.size() - 1; j >= 0; j--)
    {
      Object localObject = ((WeakReference)LAYOUTS.get(j)).get();
      if ((localInteger == null) && (localObject == paramObject))
      {
        int[] arrayOfInt = (int[])((HashMap)VALUES_TEMP.get(j)).get(paramString);
        if ((i != 0) && (arrayOfInt != null) && (arrayOfInt[paramInt] != -2147471302))
        {
          localInteger = new Integer(arrayOfInt[paramInt]);
        }
        else
        {
          arrayOfInt = (int[])((HashMap)VALUES.get(j)).get(paramString);
          localInteger = (arrayOfInt != null) && (arrayOfInt[paramInt] != -2147471302) ? new Integer(arrayOfInt[paramInt]) : null;
        }
        i = 0;
      }
      if (localObject != null)
        continue;
      LAYOUTS.remove(j);
      VALUES.remove(j);
      VALUES_TEMP.remove(j);
    }
    return localInteger;
  }

  public static synchronized boolean setBounds(Object paramObject, String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return setBounds(paramObject, paramString, paramInt1, paramInt2, paramInt3, paramInt4, false, false);
  }

  static synchronized boolean setBounds(Object paramObject, String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean1, boolean paramBoolean2)
  {
    for (int i = LAYOUTS.size() - 1; i >= 0; i--)
    {
      localObject = ((WeakReference)LAYOUTS.get(i)).get();
      if (localObject != paramObject)
        continue;
      HashMap localHashMap = (HashMap)(paramBoolean1 ? VALUES_TEMP : VALUES).get(i);
      int[] arrayOfInt2 = (int[])localHashMap.get(paramString);
      if ((arrayOfInt2 == null) || (arrayOfInt2[0] != paramInt1) || (arrayOfInt2[1] != paramInt2) || (arrayOfInt2[2] != paramInt3) || (arrayOfInt2[3] != paramInt4))
      {
        if ((arrayOfInt2 == null) || (!paramBoolean2))
        {
          localHashMap.put(paramString, new int[] { paramInt1, paramInt2, paramInt3, paramInt4, paramInt1 + paramInt3, paramInt2 + paramInt4 });
          return true;
        }
        int j = 0;
        int k;
        if (paramInt1 != -2147471302)
        {
          if ((arrayOfInt2[0] == -2147471302) || (paramInt1 < arrayOfInt2[0]))
          {
            arrayOfInt2[0] = paramInt1;
            arrayOfInt2[2] = (arrayOfInt2[4] - paramInt1);
            j = 1;
          }
          if (paramInt3 != -2147471302)
          {
            k = paramInt1 + paramInt3;
            if ((arrayOfInt2[4] == -2147471302) || (k > arrayOfInt2[4]))
            {
              arrayOfInt2[4] = k;
              arrayOfInt2[2] = (k - arrayOfInt2[0]);
              j = 1;
            }
          }
        }
        if (paramInt2 != -2147471302)
        {
          if ((arrayOfInt2[1] == -2147471302) || (paramInt2 < arrayOfInt2[1]))
          {
            arrayOfInt2[1] = paramInt2;
            arrayOfInt2[3] = (arrayOfInt2[5] - paramInt2);
            j = 1;
          }
          if (paramInt4 != -2147471302)
          {
            k = paramInt2 + paramInt4;
            if ((arrayOfInt2[5] == -2147471302) || (k > arrayOfInt2[5]))
            {
              arrayOfInt2[5] = k;
              arrayOfInt2[3] = (k - arrayOfInt2[1]);
              j = 1;
            }
          }
        }
        return j;
      }
      return false;
    }
    LAYOUTS.add(new WeakReference(paramObject));
    int[] arrayOfInt1 = { paramInt1, paramInt2, paramInt3, paramInt4, paramInt1 + paramInt3, paramInt2 + paramInt4 };
    Object localObject = new HashMap(4);
    if (paramBoolean1)
      ((HashMap)localObject).put(paramString, arrayOfInt1);
    VALUES_TEMP.add(localObject);
    localObject = new HashMap(4);
    if (!paramBoolean1)
      ((HashMap)localObject).put(paramString, arrayOfInt1);
    VALUES.add(localObject);
    return true;
  }

  public static synchronized boolean clearBounds(Object paramObject, String paramString)
  {
    for (int i = LAYOUTS.size() - 1; i >= 0; i--)
    {
      Object localObject = ((WeakReference)LAYOUTS.get(i)).get();
      if (localObject == paramObject)
        return ((HashMap)VALUES.get(i)).remove(paramString) != null;
    }
    return false;
  }

  static synchronized void clearTemporaryBounds(Object paramObject)
  {
    for (int i = LAYOUTS.size() - 1; i >= 0; i--)
    {
      Object localObject = ((WeakReference)LAYOUTS.get(i)).get();
      if (localObject != paramObject)
        continue;
      ((HashMap)VALUES_TEMP.get(i)).clear();
      return;
    }
  }
}

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     net.miginfocom.layout.LinkHandler
 * JD-Core Version:    0.6.0
 */