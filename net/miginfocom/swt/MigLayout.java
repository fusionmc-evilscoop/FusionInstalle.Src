package net.miginfocom.swt;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.ComponentWrapper;
import net.miginfocom.layout.ConstraintParser;
import net.miginfocom.layout.ContainerWrapper;
import net.miginfocom.layout.Grid;
import net.miginfocom.layout.LC;
import net.miginfocom.layout.LayoutCallback;
import net.miginfocom.layout.LayoutUtil;
import net.miginfocom.layout.PlatformDefaults;
import net.miginfocom.layout.UnitValue;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Layout;

public final class MigLayout extends Layout
  implements Externalizable
{
  private final Map<Control, Object> scrConstrMap = new IdentityHashMap(8);
  private Object layoutConstraints = "";
  private Object colConstraints = "";
  private Object rowConstraints = "";
  private transient ContainerWrapper cacheParentW = null;
  private final transient Map<ComponentWrapper, CC> ccMap = new HashMap(8);
  private transient LC lc = null;
  private transient AC colSpecs = null;
  private transient AC rowSpecs = null;
  private transient Grid grid = null;
  private transient Timer debugTimer = null;
  private transient long curDelay = -1L;
  private transient int lastModCount = PlatformDefaults.getModCount();
  private transient int lastHash = -1;
  private transient ArrayList<LayoutCallback> callbackList = null;

  public MigLayout()
  {
    this("", "", "");
  }

  public MigLayout(String paramString)
  {
    this(paramString, "", "");
  }

  public MigLayout(String paramString1, String paramString2)
  {
    this(paramString1, paramString2, "");
  }

  public MigLayout(String paramString1, String paramString2, String paramString3)
  {
    setLayoutConstraints(paramString1);
    setColumnConstraints(paramString2);
    setRowConstraints(paramString3);
  }

  public MigLayout(LC paramLC)
  {
    this(paramLC, null, null);
  }

  public MigLayout(LC paramLC, AC paramAC)
  {
    this(paramLC, paramAC, null);
  }

  public MigLayout(LC paramLC, AC paramAC1, AC paramAC2)
  {
    setLayoutConstraints(paramLC);
    setColumnConstraints(paramAC1);
    setRowConstraints(paramAC2);
  }

  public Object getLayoutConstraints()
  {
    return this.layoutConstraints;
  }

  public void setLayoutConstraints(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof String)))
    {
      paramObject = ConstraintParser.prepare((String)paramObject);
      this.lc = ConstraintParser.parseLayoutConstraint((String)paramObject);
    }
    else if ((paramObject instanceof LC))
    {
      this.lc = ((LC)paramObject);
    }
    else
    {
      throw new IllegalArgumentException("Illegal constraint type: " + paramObject.getClass().toString());
    }
    this.layoutConstraints = paramObject;
    this.grid = null;
  }

  public Object getColumnConstraints()
  {
    return this.colConstraints;
  }

  public void setColumnConstraints(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof String)))
    {
      paramObject = ConstraintParser.prepare((String)paramObject);
      this.colSpecs = ConstraintParser.parseColumnConstraints((String)paramObject);
    }
    else if ((paramObject instanceof AC))
    {
      this.colSpecs = ((AC)paramObject);
    }
    else
    {
      throw new IllegalArgumentException("Illegal constraint type: " + paramObject.getClass().toString());
    }
    this.colConstraints = paramObject;
    this.grid = null;
  }

  public Object getRowConstraints()
  {
    return this.rowConstraints;
  }

  public void setRowConstraints(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof String)))
    {
      paramObject = ConstraintParser.prepare((String)paramObject);
      this.rowSpecs = ConstraintParser.parseRowConstraints((String)paramObject);
    }
    else if ((paramObject instanceof AC))
    {
      this.rowSpecs = ((AC)paramObject);
    }
    else
    {
      throw new IllegalArgumentException("Illegal constraint type: " + paramObject.getClass().toString());
    }
    this.rowConstraints = paramObject;
    this.grid = null;
  }

  public Map<Control, Object> getConstraintMap()
  {
    return new IdentityHashMap(this.scrConstrMap);
  }

  public void setConstraintMap(Map<Control, Object> paramMap)
  {
    this.scrConstrMap.clear();
    this.ccMap.clear();
    Iterator localIterator = paramMap.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      setComponentConstraintsImpl((Control)localEntry.getKey(), localEntry.getValue(), true);
    }
  }

  private void setComponentConstraintsImpl(Control paramControl, Object paramObject, boolean paramBoolean)
  {
    if ((!paramBoolean) && (!this.scrConstrMap.containsKey(paramControl)))
      throw new IllegalArgumentException("Component must already be added to parent!");
    SwtComponentWrapper localSwtComponentWrapper = new SwtComponentWrapper(paramControl);
    if ((paramObject == null) || ((paramObject instanceof String)))
    {
      String str = ConstraintParser.prepare((String)paramObject);
      this.scrConstrMap.put(paramControl, paramObject);
      this.ccMap.put(localSwtComponentWrapper, ConstraintParser.parseComponentConstraint(str));
    }
    else if ((paramObject instanceof CC))
    {
      this.scrConstrMap.put(paramControl, paramObject);
      this.ccMap.put(localSwtComponentWrapper, (CC)paramObject);
    }
    else
    {
      throw new IllegalArgumentException("Constraint must be String or ComponentConstraint: " + paramObject.getClass().toString());
    }
    this.grid = null;
  }

  public boolean isManagingComponent(Control paramControl)
  {
    return this.scrConstrMap.containsKey(paramControl);
  }

  public void addLayoutCallback(LayoutCallback paramLayoutCallback)
  {
    if (paramLayoutCallback == null)
      throw new NullPointerException();
    if (this.callbackList == null)
      this.callbackList = new ArrayList(1);
    this.callbackList.add(paramLayoutCallback);
  }

  public void removeLayoutCallback(LayoutCallback paramLayoutCallback)
  {
    if (this.callbackList != null)
      this.callbackList.remove(paramLayoutCallback);
  }

  private synchronized void setDebug(ComponentWrapper paramComponentWrapper, boolean paramBoolean)
  {
    if ((paramBoolean) && ((this.debugTimer == null) || (this.curDelay != getDebugMillis())))
    {
      if (this.debugTimer != null)
        this.debugTimer.cancel();
      this.debugTimer = new Timer(true);
      this.curDelay = getDebugMillis();
      this.debugTimer.schedule(new MyDebugRepaintTask(this, null), this.curDelay, this.curDelay);
      ContainerWrapper localContainerWrapper = paramComponentWrapper.getParent();
      Object localObject = localContainerWrapper != null ? (Composite)localContainerWrapper.getComponent() : null;
      if (localObject != null)
        localObject.layout();
    }
    else if ((!paramBoolean) && (this.debugTimer != null))
    {
      this.debugTimer.cancel();
      this.debugTimer = null;
    }
  }

  private boolean getDebug()
  {
    return this.debugTimer != null;
  }

  private int getDebugMillis()
  {
    int i = LayoutUtil.getGlobalDebugMillis();
    return i > 0 ? i : this.lc.getDebugMillis();
  }

  private void checkCache(Composite paramComposite)
  {
    if (paramComposite == null)
      return;
    checkConstrMap(paramComposite);
    ContainerWrapper localContainerWrapper = checkParent(paramComposite);
    int i = PlatformDefaults.getModCount();
    if (this.lastModCount != i)
    {
      this.grid = null;
      this.lastModCount = i;
    }
    int j = paramComposite.getSize().hashCode();
    Iterator localIterator = this.ccMap.keySet().iterator();
    while (localIterator.hasNext())
      j += ((ComponentWrapper)localIterator.next()).getLayoutHashCode();
    if (j != this.lastHash)
    {
      this.grid = null;
      this.lastHash = j;
    }
    setDebug(localContainerWrapper, getDebugMillis() > 0);
    if (this.grid == null)
      this.grid = new Grid(localContainerWrapper, this.lc, this.rowSpecs, this.colSpecs, this.ccMap, this.callbackList);
  }

  private boolean checkConstrMap(Composite paramComposite)
  {
    Control[] arrayOfControl = paramComposite.getChildren();
    int i = arrayOfControl.length != this.scrConstrMap.size() ? 1 : 0;
    int j;
    Control localControl;
    if (i == 0)
      for (j = 0; j < arrayOfControl.length; j++)
      {
        localControl = arrayOfControl[j];
        if (this.scrConstrMap.get(localControl) == localControl.getLayoutData())
          continue;
        i = 1;
        break;
      }
    if (i != 0)
    {
      this.scrConstrMap.clear();
      for (j = 0; j < arrayOfControl.length; j++)
      {
        localControl = arrayOfControl[j];
        setComponentConstraintsImpl(localControl, localControl.getLayoutData(), true);
      }
    }
    return i;
  }

  private ContainerWrapper checkParent(Composite paramComposite)
  {
    if (paramComposite == null)
      return null;
    if ((this.cacheParentW == null) || (this.cacheParentW.getComponent() != paramComposite))
      this.cacheParentW = new SwtContainerWrapper(paramComposite);
    return this.cacheParentW;
  }

  public float getLayoutAlignmentX(Composite paramComposite)
  {
    return (this.lc != null) && (this.lc.getAlignX() != null) ? this.lc.getAlignX().getPixels(1.0F, checkParent(paramComposite), null) : 0.0F;
  }

  public float getLayoutAlignmentY(Composite paramComposite)
  {
    return (this.lc != null) && (this.lc.getAlignY() != null) ? this.lc.getAlignY().getPixels(1.0F, checkParent(paramComposite), null) : 0.0F;
  }

  protected Point computeSize(Composite paramComposite, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    checkCache(paramComposite);
    int i = LayoutUtil.getSizeSafe(this.grid != null ? this.grid.getWidth() : null, 1);
    int j = LayoutUtil.getSizeSafe(this.grid != null ? this.grid.getHeight() : null, 1);
    return new Point(i, j);
  }

  protected void layout(Composite paramComposite, boolean paramBoolean)
  {
    checkCache(paramComposite);
    Rectangle localRectangle = paramComposite.getClientArea();
    int[] arrayOfInt = { localRectangle.x, localRectangle.y, localRectangle.width, localRectangle.height };
    boolean bool = this.grid.layout(arrayOfInt, this.lc.getAlignX(), this.lc.getAlignY(), getDebug(), true);
    if (bool)
    {
      this.grid = null;
      checkCache(paramComposite);
      this.grid.layout(arrayOfInt, this.lc.getAlignX(), this.lc.getAlignY(), getDebug(), false);
    }
  }

  protected boolean flushCache(Control paramControl)
  {
    this.grid = null;
    return true;
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
    if (getClass() == MigLayout.class)
      LayoutUtil.writeAsXML(paramObjectOutput, this);
  }

  static
  {
    if (PlatformDefaults.getPlatform() == 1)
      PlatformDefaults.setDefaultDPI(Integer.valueOf(72));
  }

  private static class MyDebugRepaintTask extends TimerTask
  {
    private final WeakReference<MigLayout> layoutRef;

    private MyDebugRepaintTask(MigLayout paramMigLayout)
    {
      this.layoutRef = new WeakReference(paramMigLayout);
    }

    public void run()
    {
      MigLayout localMigLayout = (MigLayout)this.layoutRef.get();
      if ((localMigLayout != null) && (localMigLayout.grid != null))
        Display.getDefault().asyncExec(new Runnable(localMigLayout)
        {
          public void run()
          {
            if (this.val$layout.grid != null)
              this.val$layout.grid.paintDebug();
          }
        });
    }
  }
}

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     net.miginfocom.swt.MigLayout
 * JD-Core Version:    0.6.0
 */