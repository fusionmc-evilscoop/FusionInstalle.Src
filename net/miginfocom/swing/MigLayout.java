package net.miginfocom.swing;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.LayoutManager2;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import net.miginfocom.layout.AC;
import net.miginfocom.layout.BoundSize;
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

public final class MigLayout
  implements LayoutManager2, Externalizable
{
  private final Map<Component, Object> scrConstrMap = new IdentityHashMap(8);
  private Object layoutConstraints = "";
  private Object colConstraints = "";
  private Object rowConstraints = "";
  private transient ContainerWrapper cacheParentW = null;
  private final transient Map<ComponentWrapper, CC> ccMap = new HashMap(8);
  private transient Timer debugTimer = null;
  private transient LC lc = null;
  private transient AC colSpecs = null;
  private transient AC rowSpecs = null;
  private transient Grid grid = null;
  private transient int lastModCount = PlatformDefaults.getModCount();
  private transient int lastHash = -1;
  private transient Dimension lastInvalidSize = null;
  private transient boolean lastWasInvalid = false;
  private transient ArrayList<LayoutCallback> callbackList = null;
  private transient boolean dirty = true;
  private long lastSize = 0L;

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
    this.dirty = true;
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
    this.dirty = true;
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
    this.dirty = true;
  }

  public Map<Component, Object> getConstraintMap()
  {
    return new IdentityHashMap(this.scrConstrMap);
  }

  public void setConstraintMap(Map<Component, Object> paramMap)
  {
    this.scrConstrMap.clear();
    this.ccMap.clear();
    Iterator localIterator = paramMap.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      setComponentConstraintsImpl((Component)localEntry.getKey(), localEntry.getValue(), true);
    }
  }

  public Object getComponentConstraints(Component paramComponent)
  {
    synchronized (paramComponent.getParent().getTreeLock())
    {
      return this.scrConstrMap.get(paramComponent);
    }
  }

  public void setComponentConstraints(Component paramComponent, Object paramObject)
  {
    setComponentConstraintsImpl(paramComponent, paramObject, false);
  }

  private void setComponentConstraintsImpl(Component paramComponent, Object paramObject, boolean paramBoolean)
  {
    Container localContainer = paramComponent.getParent();
    synchronized (localContainer != null ? localContainer.getTreeLock() : new Object())
    {
      if ((!paramBoolean) && (!this.scrConstrMap.containsKey(paramComponent)))
        throw new IllegalArgumentException("Component must already be added to parent!");
      SwingComponentWrapper localSwingComponentWrapper = new SwingComponentWrapper(paramComponent);
      if ((paramObject == null) || ((paramObject instanceof String)))
      {
        String str = ConstraintParser.prepare((String)paramObject);
        this.scrConstrMap.put(paramComponent, paramObject);
        this.ccMap.put(localSwingComponentWrapper, ConstraintParser.parseComponentConstraint(str));
      }
      else if ((paramObject instanceof CC))
      {
        this.scrConstrMap.put(paramComponent, paramObject);
        this.ccMap.put(localSwingComponentWrapper, (CC)paramObject);
      }
      else
      {
        throw new IllegalArgumentException("Constraint must be String or ComponentConstraint: " + paramObject.getClass().toString());
      }
      this.dirty = true;
    }
  }

  public boolean isManagingComponent(Component paramComponent)
  {
    return this.scrConstrMap.containsKey(paramComponent);
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
    if ((paramBoolean) && ((this.debugTimer == null) || (this.debugTimer.getDelay() != getDebugMillis())))
    {
      if (this.debugTimer != null)
        this.debugTimer.stop();
      ContainerWrapper localContainerWrapper = paramComponentWrapper.getParent();
      Component localComponent = localContainerWrapper != null ? (Component)localContainerWrapper.getComponent() : null;
      this.debugTimer = new Timer(getDebugMillis(), new MyDebugRepaintListener(this, null));
      if (localComponent != null)
        SwingUtilities.invokeLater(new Runnable(localComponent)
        {
          public void run()
          {
            Container localContainer = this.val$parent.getParent();
            if (localContainer != null)
              if ((localContainer instanceof JComponent))
              {
                ((JComponent)localContainer).revalidate();
              }
              else
              {
                this.val$parent.invalidate();
                localContainer.validate();
              }
          }
        });
      this.debugTimer.setInitialDelay(100);
      this.debugTimer.start();
    }
    else if ((!paramBoolean) && (this.debugTimer != null))
    {
      this.debugTimer.stop();
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

  private void checkCache(Container paramContainer)
  {
    if (paramContainer == null)
      return;
    if (this.dirty)
      this.grid = null;
    int i = PlatformDefaults.getModCount();
    if (this.lastModCount != i)
    {
      this.grid = null;
      this.lastModCount = i;
    }
    if (!paramContainer.isValid())
    {
      if (!this.lastWasInvalid)
      {
        this.lastWasInvalid = true;
        int j = 0;
        int k = 0;
        Object localObject1 = this.ccMap.keySet().iterator();
        while (((Iterator)localObject1).hasNext())
        {
          ComponentWrapper localComponentWrapper = (ComponentWrapper)((Iterator)localObject1).next();
          Object localObject2 = localComponentWrapper.getComponent();
          if (((localObject2 instanceof JTextArea)) || ((localObject2 instanceof JEditorPane)))
            k = 1;
          j += localComponentWrapper.getLayoutHashCode();
        }
        if (k != 0)
          resetLastInvalidOnParent(paramContainer);
        if (j != this.lastHash)
        {
          this.grid = null;
          this.lastHash = j;
        }
        localObject1 = paramContainer.getSize();
        if ((this.lastInvalidSize == null) || (!this.lastInvalidSize.equals(localObject1)))
        {
          if (this.grid != null)
            this.grid.invalidateContainerSize();
          this.lastInvalidSize = ((Dimension)localObject1);
        }
      }
    }
    else
      this.lastWasInvalid = false;
    ContainerWrapper localContainerWrapper = checkParent(paramContainer);
    setDebug(localContainerWrapper, getDebugMillis() > 0);
    if (this.grid == null)
      this.grid = new Grid(localContainerWrapper, this.lc, this.rowSpecs, this.colSpecs, this.ccMap, this.callbackList);
    this.dirty = false;
  }

  private void resetLastInvalidOnParent(Container paramContainer)
  {
    while (paramContainer != null)
    {
      LayoutManager localLayoutManager = paramContainer.getLayout();
      if ((localLayoutManager instanceof MigLayout))
        ((MigLayout)localLayoutManager).lastWasInvalid = false;
      paramContainer = paramContainer.getParent();
    }
  }

  private ContainerWrapper checkParent(Container paramContainer)
  {
    if (paramContainer == null)
      return null;
    if ((this.cacheParentW == null) || (this.cacheParentW.getComponent() != paramContainer))
      this.cacheParentW = new SwingContainerWrapper(paramContainer);
    return this.cacheParentW;
  }

  public void layoutContainer(Container paramContainer)
  {
    synchronized (paramContainer.getTreeLock())
    {
      checkCache(paramContainer);
      Insets localInsets = paramContainer.getInsets();
      int[] arrayOfInt = { localInsets.left, localInsets.top, paramContainer.getWidth() - localInsets.left - localInsets.right, paramContainer.getHeight() - localInsets.top - localInsets.bottom };
      if (this.grid.layout(arrayOfInt, this.lc.getAlignX(), this.lc.getAlignY(), getDebug(), true))
      {
        this.grid = null;
        checkCache(paramContainer);
        this.grid.layout(arrayOfInt, this.lc.getAlignX(), this.lc.getAlignY(), getDebug(), false);
      }
      long l = this.grid.getHeight()[1] + (this.grid.getWidth()[1] << 32);
      if (this.lastSize != l)
      {
        this.lastSize = l;
        ContainerWrapper localContainerWrapper = checkParent(paramContainer);
        Window localWindow = (Window)SwingUtilities.getAncestorOfClass(Window.class, (Component)localContainerWrapper.getComponent());
        if (localWindow != null)
          if (localWindow.isVisible())
            SwingUtilities.invokeLater(new Runnable(localContainerWrapper)
            {
              public void run()
              {
                MigLayout.this.adjustWindowSize(this.val$containerWrapper);
              }
            });
          else
            adjustWindowSize(localContainerWrapper);
      }
      this.lastInvalidSize = null;
    }
  }

  private void adjustWindowSize(ContainerWrapper paramContainerWrapper)
  {
    BoundSize localBoundSize1 = this.lc.getPackWidth();
    BoundSize localBoundSize2 = this.lc.getPackHeight();
    if ((localBoundSize1 == null) && (localBoundSize2 == null))
      return;
    Window localWindow = (Window)SwingUtilities.getAncestorOfClass(Window.class, (Component)paramContainerWrapper.getComponent());
    if (localWindow == null)
      return;
    Dimension localDimension = localWindow.getPreferredSize();
    int i = constrain(checkParent(localWindow), localWindow.getWidth(), localDimension.width, localBoundSize1);
    int j = constrain(checkParent(localWindow), localWindow.getHeight(), localDimension.height, localBoundSize2);
    int k = Math.round(localWindow.getX() - (i - localWindow.getWidth()) * (1.0F - this.lc.getPackWidthAlign()));
    int m = Math.round(localWindow.getY() - (j - localWindow.getHeight()) * (1.0F - this.lc.getPackHeightAlign()));
    localWindow.setBounds(k, m, i, j);
  }

  private int constrain(ContainerWrapper paramContainerWrapper, int paramInt1, int paramInt2, BoundSize paramBoundSize)
  {
    if (paramBoundSize == null)
      return paramInt1;
    int i = paramInt1;
    UnitValue localUnitValue = paramBoundSize.getPreferred();
    if (localUnitValue != null)
      i = localUnitValue.getPixels(paramInt2, paramContainerWrapper, paramContainerWrapper);
    i = paramBoundSize.constrain(i, paramInt2, paramContainerWrapper);
    return paramBoundSize.getGapPush() ? Math.max(paramInt1, i) : i;
  }

  public Dimension minimumLayoutSize(Container paramContainer)
  {
    synchronized (paramContainer.getTreeLock())
    {
      return getSizeImpl(paramContainer, 0);
    }
  }

  public Dimension preferredLayoutSize(Container paramContainer)
  {
    synchronized (paramContainer.getTreeLock())
    {
      return getSizeImpl(paramContainer, 1);
    }
  }

  public Dimension maximumLayoutSize(Container paramContainer)
  {
    return new Dimension(32767, 32767);
  }

  private Dimension getSizeImpl(Container paramContainer, int paramInt)
  {
    checkCache(paramContainer);
    Insets localInsets = paramContainer.getInsets();
    int i = LayoutUtil.getSizeSafe(this.grid != null ? this.grid.getWidth() : null, paramInt) + localInsets.left + localInsets.right;
    int j = LayoutUtil.getSizeSafe(this.grid != null ? this.grid.getHeight() : null, paramInt) + localInsets.top + localInsets.bottom;
    return new Dimension(i, j);
  }

  public float getLayoutAlignmentX(Container paramContainer)
  {
    return (this.lc != null) && (this.lc.getAlignX() != null) ? this.lc.getAlignX().getPixels(1.0F, checkParent(paramContainer), null) : 0.0F;
  }

  public float getLayoutAlignmentY(Container paramContainer)
  {
    return (this.lc != null) && (this.lc.getAlignY() != null) ? this.lc.getAlignY().getPixels(1.0F, checkParent(paramContainer), null) : 0.0F;
  }

  public void addLayoutComponent(String paramString, Component paramComponent)
  {
    addLayoutComponent(paramComponent, paramString);
  }

  public void addLayoutComponent(Component paramComponent, Object paramObject)
  {
    synchronized (paramComponent.getParent().getTreeLock())
    {
      setComponentConstraintsImpl(paramComponent, paramObject, true);
    }
  }

  public void removeLayoutComponent(Component paramComponent)
  {
    synchronized (paramComponent.getParent().getTreeLock())
    {
      this.scrConstrMap.remove(paramComponent);
      this.ccMap.remove(new SwingComponentWrapper(paramComponent));
    }
  }

  public void invalidateLayout(Container paramContainer)
  {
    this.dirty = true;
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

  private static class MyDebugRepaintListener
    implements ActionListener
  {
    private final WeakReference<MigLayout> layoutRef;

    private MyDebugRepaintListener(MigLayout paramMigLayout)
    {
      this.layoutRef = new WeakReference(paramMigLayout);
    }

    public void actionPerformed(ActionEvent paramActionEvent)
    {
      MigLayout localMigLayout = (MigLayout)this.layoutRef.get();
      if ((localMigLayout != null) && (localMigLayout.grid != null))
        localMigLayout.grid.paintDebug();
    }
  }
}

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     net.miginfocom.swing.MigLayout
 * JD-Core Version:    0.6.0
 */