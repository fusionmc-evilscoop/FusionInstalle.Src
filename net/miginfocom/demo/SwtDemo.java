package net.miginfocom.demo;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.Random;
import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.layout.LayoutUtil;
import net.miginfocom.layout.PlatformDefaults;
import net.miginfocom.swt.MigLayout;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class SwtDemo
{
  public static final int SELECTED_INDEX = 0;
  private static final String[][] panels = { { "Welcome", "\n\n         \"MigLayout makes complex layouts easy and normal layouts one-liners.\"" }, { "Quick Start", "This is an example of how to build a common dialog type. Note that there are no special components, nested panels or absolute references to cell positions. If you look at the source code you will see that the layout code is very simple to understand." }, { "Plain", "A simple example on how simple it is to create normal forms. No builders needed since the whole layout manager works like a builder." }, { "Alignments", "Shows how the alignment of components are specified. At the top/left is the alignment for the column/row. The components have no alignments specified.\n\nNote that baseline alignment will be interpreted as 'center' before JDK 6." }, { "Cell Alignments", "Shows how components are aligned when both column/row alignments and component constraints are specified. At the top/left are the alignment for the column/row and the text on the buttons is the component constraint that will override the column/row alignment if it is an alignment.\n\nNote that baseline alignment will be interpreted as 'center' before JDK 6." }, { "Basic Sizes", "A simple example that shows how to use the column or row min/preferred/max size to set the sizes of the contained components and also an example that shows how to do this directly in the component constraints." }, { "Growing", "A simple example that shows how to use the growx and growy constraint to set the sizes and how they should grow to fit the available size. Both the column/row and the component grow/shrink constraints can be set, but the components will always be confined to the space given by its column/row." }, { "Grow Shrink", "Demonstrates the very flexible grow and shrink constraints that can be set on a component.\nComponents can be divided into grow/shrink groups and also have grow/shrink weight within each of those groups.\n\nBy default components shrink to their inherent (or specified) minimum size, but they don't grow." }, { "Span", "This example shows the powerful spanning and splitting that can be specified in the component constraints. With spanning any number of cells can be merged with the additional option to split that space for more than one component. This makes layouts very flexible and reduces the number of times you will need nested panels to very few." }, { "Flow Direction", "Shows the different flow directions. Flow direction for the layout specifies if the next cell will be in the x or y dimension. Note that it can be a different flow direction in the slit cell (the middle cell is slit in two). Wrap is set to 3 for all panels." }, { "Grouping", "Sizes for both components and columns/rows can be grouped so they get the same size. For instance buttons in a button bar can be given a size-group so that they will all get the same minimum and preferred size (the largest within the group). Size-groups can be set for the width, height or both." }, { "Units", "Demonstrates the basic units that are understood by MigLayout. These units can be extended by the user by adding one or more UnitConverter(s)." }, { "Component Sizes", "Minimum, preferred and maximum component sizes can be overridden in the component constraints using any unit type. The format to do this is short and simple to understand. You simply specify the min, preferred and max sizes with a colon between.\n\nAbove are some examples of this. An exclamation mark means that the value will be used for all sizes." }, { "Bound Sizes", "Shows how to create columns that are stable between tabs using minimum sizes." }, { "Cell Position", "Even though MigLayout has automatic grid flow you can still specify the cell position explicitly. You can even combine absolute (x, y) and flow (skip, wrap and newline) constraints to build your layout." }, { "Orientation", "MigLayout supports not only right-to-left orientation, but also bottom-to-top. You can even set the flow direction so that the flow is vertical instead of horizontal. It will automatically pick up if right-to-left is to be used depending on the ComponentWrapper, but it can also be manually set for every layout." }, { "Absolute Position", "Demonstrates the option to place any number of components using absolute coordinates. This can be just the position (if min/preferred size) using \"x y p p\" format orthe bounds using the \"x1 y1 x2 y2\" format. Any unit can be used and percent is relative to the parent.\nAbsolute components will not disturb the flow or occupy cells in the grid. Absolute positioned components will be taken into account when calculating the container's preferred size." }, { "Component Links", "Components can be linked to any side of any other component. It can be a forward, backward or cyclic link references, as long as it is stable and won't continue to change value over many iterations.Links are referencing the ID of another component. The ID can be overridden by the component's constrains or is provided by the ComponentWrapper. For instance it will use the component's 'name' on Swing.\nSince the links can be combined with any expression (such as 'butt1.x+10' or 'max(button.x, 200)' the links are very customizable." }, { "Docking", "Docking components can be added around the grid. The docked component will get the whole width/height on the docked side by default, however this can be overridden. When all docked components are laid out, whatever space is left will be available for the normal grid laid out components. Docked components does not in any way affect the flow in the grid.\n\nSince the docking runs in the same code path as the normal layout code the same properties can be specified for the docking components. You can for instance set the sizes and alignment or link other components to their docked component's bounds." }, { "Button Bars", "Button order is very customizable and are by default different on the supported platforms. E.g. Gaps, button order and minimum button size are properties that are 'per platform'. MigLayout picks up the current platform automatically and adjusts the button order and minimum button size accordingly, all without using a button builder or any other special code construct." }, { "Debug", "Demonstrates the non-intrusive way to get visual debugging aid. There is no need to use a special DebugPanel or anything that will need code changes. The user can simply turn on debug on the layout manager by using the ìdebugî constraint and it will continuously repaint the panel with debug information on top. This means you don't have to change your code to debug!" }, { "Layout Showdown", "This is an implementation of the Layout Showdown posted on java.net by John O'Conner. The first tab is a pure implemenetation of the showdown that follows all the rules. The second tab is a slightly fixed version that follows some improved layout guidelines.The source code is for bothe the first and for the fixed version. Note the simplification of the code for the fixed version. Writing better layouts with MiG Layout is reasier that writing bad.\n\nReference: http://weblogs.java.net/blog/joconner/archive/2006/10/more_informatio.html" }, { "API Constraints1", "This dialog shows the constraint API added to v2.0. It works the same way as the string constraints but with chained method calls. See the source code for details." }, { "API Constraints2", "This dialog shows the constraint API added to v2.0. It works the same way as the string constraints but with chained method calls. See the source code for details." } };
  private static int DOUBLE_BUFFER = 0;
  private static int benchRuns = 0;
  private static long startupMillis = 0L;
  private static long timeToShowMillis = 0L;
  private static long benchRunTime = 0L;
  private static String benchOutFileName = null;
  private static boolean append = false;
  private static long lastRunTimeStart = 0L;
  private static StringBuffer runTimeSB = null;
  private static Display display = null;
  final List pickerList;
  final Composite layoutDisplayPanel;
  final StyledText descrTextArea;
  private static Control[] comps = null;
  private static Control[] tabs = null;

  public static void main(String[] paramArrayOfString)
  {
    startupMillis = System.currentTimeMillis();
    if (paramArrayOfString.length > 0)
      for (int i = 0; i < paramArrayOfString.length; i++)
      {
        String str = paramArrayOfString[i].trim();
        if (str.startsWith("-bench"))
        {
          benchRuns = 10;
          try
          {
            if (str.length() > 6)
              benchRuns = Integer.parseInt(str.substring(6));
          }
          catch (Exception localException)
          {
          }
        }
        else if (str.startsWith("-bout"))
        {
          benchOutFileName = str.substring(5);
        }
        else if (str.startsWith("-append"))
        {
          append = true;
        }
        else if (str.startsWith("-verbose"))
        {
          runTimeSB = new StringBuffer(256);
        }
        else
        {
          System.out.println("Usage: [-bench[#_of_runs]] [-bout[benchmark_results_filename]] [-append]\n -bench Run demo as benchmark. Run count can be appended. 10 is default.\n -bout  Benchmark results output filename.\n -append Appends the result to the \"-bout\" file.\n -verbose Print the times of every run.\n\nExamples:\n java -jar swtdemoapp.jar -bench -boutC:/bench.txt -append\n java -jar swtdemoapp.jar -bench20\nNOTE! swt-win32-3232.dll must be in the current directory!");
          System.exit(0);
        }
      }
    if (benchRuns == 0)
      LayoutUtil.setDesignTime(null, true);
    new SwtDemo();
  }

  public SwtDemo()
  {
    display = new Display();
    Shell localShell = new Shell();
    localShell.setLayout(new MigLayout("wrap", "[]u[grow,fill]", "[grow,fill][pref!]"));
    localShell.setText("MigLayout SWT Demo v2.5 - Mig Layout v" + LayoutUtil.getVersion());
    TabFolder localTabFolder1 = new TabFolder(localShell, DOUBLE_BUFFER);
    localTabFolder1.setLayoutData("spany,grow");
    this.pickerList = new List(localTabFolder1, 0x4 | DOUBLE_BUFFER);
    this.pickerList.setBackground(localTabFolder1.getBackground());
    deriveFont(this.pickerList, 1, -1);
    TabItem localTabItem = new TabItem(localTabFolder1, DOUBLE_BUFFER);
    localTabItem.setControl(this.pickerList);
    localTabItem.setText("Example Browser");
    for (int i = 0; i < panels.length; i++)
      this.pickerList.add(panels[i][0]);
    this.layoutDisplayPanel = new Composite(localShell, DOUBLE_BUFFER);
    this.layoutDisplayPanel.setLayout(new MigLayout("fill, insets 0"));
    TabFolder localTabFolder2 = new TabFolder(localShell, DOUBLE_BUFFER);
    localTabFolder2.setLayoutData("growx,hmin 120,w 500:500");
    this.descrTextArea = createTextArea(localTabFolder2, "", "", 66);
    this.descrTextArea.setBackground(localTabFolder2.getBackground());
    localTabItem = new TabItem(localTabFolder2, DOUBLE_BUFFER);
    localTabItem.setControl(this.descrTextArea);
    localTabItem.setText("Description");
    this.pickerList.addSelectionListener(new SelectionAdapter()
    {
      public void widgetSelected(SelectionEvent paramSelectionEvent)
      {
        SwtDemo.this.dispatchSelection();
      }
    });
    localShell.setSize(900, 650);
    localShell.open();
    localShell.layout();
    if (benchRuns > 0)
    {
      doBenchmark();
    }
    else
    {
      this.pickerList.select(0);
      dispatchSelection();
      display.addFilter(1, new Listener()
      {
        public void handleEvent(Event paramEvent)
        {
          if (paramEvent.character == 'b')
          {
            SwtDemo.access$102(System.currentTimeMillis());
            SwtDemo.access$202(System.currentTimeMillis() - SwtDemo.startupMillis);
            SwtDemo.access$302(1);
            SwtDemo.this.doBenchmark();
          }
        }
      });
    }
    System.out.println(Display.getCurrent().getDPI());
    while (!localShell.isDisposed())
    {
      if (display.readAndDispatch())
        continue;
      display.sleep();
    }
    display.dispose();
  }

  private void doBenchmark()
  {
    int i = this.pickerList.getItemCount();
    3 local3 = new Thread(i)
    {
      public void run()
      {
        for (int i = 0; i < SwtDemo.benchRuns; i++)
        {
          SwtDemo.access$502(System.currentTimeMillis());
          int j = i;
          for (int k = 0; k < this.val$pCnt; k++)
          {
            int m = k;
            try
            {
              SwtDemo.display.syncExec(new Runnable(m)
              {
                public void run()
                {
                  SwtDemo.this.pickerList.setSelection(this.val$ii);
                  SwtDemo.this.dispatchSelection();
                }
              });
            }
            catch (Exception localException1)
            {
              localException1.printStackTrace();
            }
            SwtDemo.display.syncExec(new Runnable()
            {
              public void run()
              {
                SwtDemo.access$702(SwtDemo.this.layoutDisplayPanel.getChildren());
              }
            });
            for (int n = 0; n < SwtDemo.comps.length; n++)
            {
              if (!(SwtDemo.comps[n] instanceof TabFolder))
                continue;
              TabFolder localTabFolder = (TabFolder)SwtDemo.comps[n];
              SwtDemo.display.syncExec(new Runnable(localTabFolder)
              {
                public void run()
                {
                  SwtDemo.access$802(this.val$tp.getTabList());
                }
              });
              for (int i1 = 0; i1 < SwtDemo.tabs.length; i1++)
              {
                int i2 = i1;
                try
                {
                  SwtDemo.display.syncExec(new Runnable(localTabFolder, i2)
                  {
                    public void run()
                    {
                      this.val$tp.setSelection(this.val$kk);
                      if (SwtDemo.timeToShowMillis == 0L)
                        SwtDemo.access$202(System.currentTimeMillis() - SwtDemo.startupMillis);
                    }
                  });
                }
                catch (Exception localException2)
                {
                  localException2.printStackTrace();
                }
              }
            }
          }
          if (SwtDemo.runTimeSB == null)
            continue;
          SwtDemo.runTimeSB.append("Run ").append(j).append(": ");
          SwtDemo.runTimeSB.append(System.currentTimeMillis() - SwtDemo.lastRunTimeStart).append(" millis.\n");
        }
        SwtDemo.access$1002(System.currentTimeMillis() - SwtDemo.startupMillis - SwtDemo.timeToShowMillis);
        String str = "Java Version:       " + System.getProperty("java.version") + "\n" + "Time to Show:       " + SwtDemo.timeToShowMillis + " millis.\n" + (SwtDemo.runTimeSB != null ? SwtDemo.runTimeSB.toString() : "") + "Benchmark Run Time: " + SwtDemo.benchRunTime + " millis.\n" + "Average Run Time:   " + SwtDemo.benchRunTime / SwtDemo.benchRuns + " millis (" + SwtDemo.benchRuns + " runs).\n\n";
        SwtDemo.display.syncExec(new Runnable(str)
        {
          public void run()
          {
            Object localObject1;
            if (SwtDemo.benchOutFileName == null)
            {
              localObject1 = new MessageBox(SwtDemo.display.getActiveShell(), 34);
              ((MessageBox)localObject1).setText("Results");
              ((MessageBox)localObject1).setMessage(this.val$message);
              ((MessageBox)localObject1).open();
            }
            else
            {
              localObject1 = null;
              try
              {
                localObject1 = new FileWriter(SwtDemo.benchOutFileName, SwtDemo.append);
                ((FileWriter)localObject1).write(this.val$message);
              }
              catch (IOException localIOException3)
              {
                localIOException2.printStackTrace();
              }
              finally
              {
                if (localObject1 != null)
                  try
                  {
                    ((FileWriter)localObject1).close();
                  }
                  catch (IOException localIOException4)
                  {
                  }
              }
            }
          }
        });
        System.out.println(str);
        if (SwtDemo.benchOutFileName != null)
          System.exit(0);
      }
    };
    local3.start();
  }

  private void dispatchSelection()
  {
    int i = this.pickerList.getSelectionIndex();
    if (i == -1)
      return;
    String str = "create" + panels[i][0].replace(' ', '_');
    Control[] arrayOfControl = this.layoutDisplayPanel.getChildren();
    for (int j = 0; j < arrayOfControl.length; j++)
      arrayOfControl[j].dispose();
    try
    {
      Control localControl = (Control)SwtDemo.class.getMethod(str, new Class[] { Composite.class }).invoke(this, new Object[] { this.layoutDisplayPanel });
      localControl.setLayoutData("grow, wmin 500");
      this.descrTextArea.setText(panels[i][1]);
      this.layoutDisplayPanel.layout();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public Control createTest(Composite paramComposite)
  {
    Composite localComposite = new Composite(paramComposite, 0);
    localComposite.setLayout(new MigLayout("debug", "[right][grow]", ""));
    Button localButton = new Button(localComposite, 8);
    localButton.setText("New");
    localButton.setLayoutData("span 2, align left, split, sgx button");
    localButton = new Button(localComposite, 8);
    localButton.setText("Edit");
    localButton.setLayoutData("sgx button");
    localButton = new Button(localComposite, 8);
    localButton.setText("Cancel");
    localButton.setLayoutData("sgx button");
    localButton = new Button(localComposite, 8);
    localButton.setText("Save");
    localButton.setLayoutData("sgx button, wrap");
    new Label(localComposite, 0).setText("Name");
    Text localText = new Text(localComposite, 2048);
    localText.setLayoutData("sgy control, pushx, growx, wrap");
    new Label(localComposite, 0).setText("Sex");
    Combo localCombo = new Combo(localComposite, 4);
    localCombo.setLayoutData("sgy control, w 50!, wrap");
    localCombo.setItems(new String[] { "M", "F", "-" });
    return localComposite;
  }

  public Control createWelcome(Composite paramComposite)
  {
    TabFolder localTabFolder = new TabFolder(paramComposite, DOUBLE_BUFFER);
    TabItem localTabItem = createTabPanel(localTabFolder, "Welcome", new MigLayout());
    MigLayout localMigLayout = new MigLayout("ins 20, fill");
    Composite localComposite = createPanel(localTabFolder, localMigLayout);
    localTabItem.setControl(localComposite);
    String str = "MigLayout's main purpose is to make layouts for SWT and Swing, and possibly other frameworks, much more powerful and a lot easier to create, especially for manual coding.\n\nThe motto is: \"MigLayout makes complex layouts easy and normal layouts one-liners.\"\n\nThe layout engine is very flexible and advanced, something that is needed to make it simple to use yet handle almost all layout use-cases.\n\nMigLayout can handle all layouts that the commonly used Swing Layout Managers can handle and this with a lot of extra features. It also incorporates most, if not all, of the open source alternatives FormLayout's and TableLayout's functionality.\n\n\nThanks to Karsten Lentzsch from JGoodies.com for allowing the reuse of the main demo application layout and for his inspiring talks that led to this layout Manager.\n\n\nMikael Grev\nMiG InfoCom AB\nmiglayout@miginfocom.com";
    StyledText localStyledText = createTextArea(localComposite, str, "w 500:500, ay top, grow, push", 0);
    localStyledText.setBackground(localComposite.getBackground());
    localStyledText.setBackgroundMode(0);
    return localTabFolder;
  }

  public Composite createAPI_Constraints1(Composite paramComposite)
  {
    TabFolder localTabFolder = new TabFolder(paramComposite, DOUBLE_BUFFER);
    LC localLC = new LC().fill().wrap();
    AC localAC1 = new AC().align("right", new int[] { 0 }).fill(new int[] { 1, 3 }).grow(100.0F, new int[] { 1, 3 }).align("right", new int[] { 2 }).gap("15", new int[] { 1 });
    AC localAC2 = new AC().align("top", new int[] { 7 }).gap("15!", new int[] { 6 }).grow(100.0F, new int[] { 8 });
    TabItem localTabItem = createTabPanel(localTabFolder, "Layout Showdown (improved)", new MigLayout(localLC, localAC1, localAC2));
    createList(localTabItem, "Mouse, Mickey", new CC().dockWest().minWidth("150").gapX(null, "10"));
    createLabel(localTabItem, "Last Name", "");
    createTextField(localTabItem, "", "");
    createLabel(localTabItem, "First Name", "");
    createTextField(localTabItem, "", new CC().wrap());
    createLabel(localTabItem, "Phone", "");
    createTextField(localTabItem, "", "");
    createLabel(localTabItem, "Email", "");
    createTextField(localTabItem, "", "");
    createLabel(localTabItem, "Address 1", "");
    createTextField(localTabItem, "", new CC().spanX().growX());
    createLabel(localTabItem, "Address 2", "");
    createTextField(localTabItem, "", new CC().spanX().growX());
    createLabel(localTabItem, "City", "");
    createTextField(localTabItem, "", new CC().wrap());
    createLabel(localTabItem, "State", "");
    createTextField(localTabItem, "", "");
    createLabel(localTabItem, "Postal Code", "");
    createTextField(localTabItem, "", new CC().spanX(2).growX(0.0F));
    createLabel(localTabItem, "Country", "");
    createTextField(localTabItem, "", new CC().wrap());
    createButton(localTabItem, "New", new CC().spanX(5).split(5).tag("other"));
    createButton(localTabItem, "Delete", new CC().tag("other"));
    createButton(localTabItem, "Edit", new CC().tag("other"));
    createButton(localTabItem, "Save", new CC().tag("other"));
    createButton(localTabItem, "Cancel", new CC().tag("cancel"));
    return localTabFolder;
  }

  public Composite createAPI_Constraints2(Composite paramComposite)
  {
    TabFolder localTabFolder = new TabFolder(paramComposite, DOUBLE_BUFFER);
    LC localLC = new LC().fill().wrap();
    AC localAC1 = new AC().align("right", new int[] { 0 }).fill(new int[] { 1, 3 }).grow(100.0F, new int[] { 1, 3 }).align("right", new int[] { 2 }).gap("15", new int[] { 1 });
    AC localAC2 = new AC().index(6).gap("15!").align("top").grow(100.0F, new int[] { 8 });
    TabItem localTabItem = createTabPanel(localTabFolder, "Layout Showdown (improved)", new MigLayout(localLC, localAC1, localAC2));
    createLabel(localTabItem, "Last Name", "");
    createTextField(localTabItem, "", "");
    createLabel(localTabItem, "First Name", "");
    createTextField(localTabItem, "", new CC().wrap());
    createLabel(localTabItem, "Phone", "");
    createTextField(localTabItem, "", "");
    createLabel(localTabItem, "Email", "");
    createTextField(localTabItem, "", "");
    createLabel(localTabItem, "Address 1", "");
    createTextField(localTabItem, "", new CC().spanX().growX());
    createLabel(localTabItem, "Address 2", "");
    createTextField(localTabItem, "", new CC().spanX().growX());
    createLabel(localTabItem, "City", "");
    createTextField(localTabItem, "", new CC().wrap());
    createLabel(localTabItem, "State", "");
    createTextField(localTabItem, "", "");
    createLabel(localTabItem, "Postal Code", "");
    createTextField(localTabItem, "", new CC().spanX(2).growX(0.0F));
    createLabel(localTabItem, "Country", "");
    createTextField(localTabItem, "", new CC().wrap());
    createButton(localTabItem, "New", new CC().spanX(5).split(5).tag("other"));
    createButton(localTabItem, "Delete", new CC().tag("other"));
    createButton(localTabItem, "Edit", new CC().tag("other"));
    createButton(localTabItem, "Save", new CC().tag("other"));
    createButton(localTabItem, "Cancel", new CC().tag("cancel"));
    return localTabFolder;
  }

  public Composite createLayout_Showdown(Composite paramComposite)
  {
    TabFolder localTabFolder = new TabFolder(paramComposite, DOUBLE_BUFFER);
    TabItem localTabItem1 = createTabPanel(localTabFolder, "Layout Showdown (pure)", new MigLayout("", "[]15[][grow,fill]15[grow]"));
    createList(localTabItem1, "Mouse, Mickey", "spany, growy, wmin 150");
    createLabel(localTabItem1, "Last Name", "");
    createTextField(localTabItem1, "", "");
    createLabel(localTabItem1, "First Name", "split");
    createTextField(localTabItem1, "", "growx, wrap");
    createLabel(localTabItem1, "Phone", "");
    createTextField(localTabItem1, "", "");
    createLabel(localTabItem1, "Email", "split");
    createTextField(localTabItem1, "", "growx, wrap");
    createLabel(localTabItem1, "Address 1", "");
    createTextField(localTabItem1, "", "span, growx");
    createLabel(localTabItem1, "Address 2", "");
    createTextField(localTabItem1, "", "span, growx");
    createLabel(localTabItem1, "City", "");
    createTextField(localTabItem1, "", "wrap");
    createLabel(localTabItem1, "State", "");
    createTextField(localTabItem1, "", "");
    createLabel(localTabItem1, "Postal Code", "split");
    createTextField(localTabItem1, "", "growx, wrap");
    createLabel(localTabItem1, "Country", "");
    createTextField(localTabItem1, "", "wrap 15");
    createButton(localTabItem1, "New", "span, split, align right");
    createButton(localTabItem1, "Delete", "");
    createButton(localTabItem1, "Edit", "");
    createButton(localTabItem1, "Save", "");
    createButton(localTabItem1, "Cancel", "wrap push");
    TabItem localTabItem2 = createTabPanel(localTabFolder, "Layout Showdown (improved)", new MigLayout("", "[]15[][grow,fill]15[][grow,fill]"));
    createList(localTabItem2, "Mouse, Mickey", "spany, growy, wmin 150");
    createLabel(localTabItem2, "Last Name", "");
    createTextField(localTabItem2, "", "");
    createLabel(localTabItem2, "First Name", "");
    createTextField(localTabItem2, "", "wrap");
    createLabel(localTabItem2, "Phone", "");
    createTextField(localTabItem2, "", "");
    createLabel(localTabItem2, "Email", "");
    createTextField(localTabItem2, "", "wrap");
    createLabel(localTabItem2, "Address 1", "");
    createTextField(localTabItem2, "", "span");
    createLabel(localTabItem2, "Address 2", "");
    createTextField(localTabItem2, "", "span");
    createLabel(localTabItem2, "City", "");
    createTextField(localTabItem2, "", "wrap");
    createLabel(localTabItem2, "State", "");
    createTextField(localTabItem2, "", "");
    createLabel(localTabItem2, "Postal Code", "");
    createTextField(localTabItem2, "", "width 50, grow 0, wrap");
    createLabel(localTabItem2, "Country", "");
    createTextField(localTabItem2, "", "wrap 15");
    createButton(localTabItem2, "New", "tag other, span, split");
    createButton(localTabItem2, "Delete", "tag other");
    createButton(localTabItem2, "Edit", "tag other");
    createButton(localTabItem2, "Save", "tag other");
    createButton(localTabItem2, "Cancel", "tag cancel, wrap push");
    return localTabFolder;
  }

  public Composite createDocking(Composite paramComposite)
  {
    TabFolder localTabFolder = new TabFolder(paramComposite, DOUBLE_BUFFER);
    localTabFolder.setLayoutData("grow");
    TabItem localTabItem1 = createTabPanel(localTabFolder, "Docking 1", new MigLayout("fill"));
    createPanel(localTabItem1, "1. North", "north");
    createPanel(localTabItem1, "2. West", "west");
    createPanel(localTabItem1, "3. East", "east");
    createPanel(localTabItem1, "4. South", "south");
    Table localTable = new Table(getComposite(localTabItem1), DOUBLE_BUFFER);
    for (int i = 0; i < 5; i++)
    {
      localObject1 = new TableColumn(localTable, 16897);
      ((TableColumn)localObject1).setText("Column " + (i + 1));
      ((TableColumn)localObject1).setWidth(100);
    }
    for (i = 0; i < 15; i++)
    {
      localObject1 = new TableItem(localTable, 0);
      localObject2 = new String[6];
      for (int j = 0; j < localObject2.length; j++)
        localObject2[j] = ("Cell " + (i + 1) + ", " + (j + 1));
      ((TableItem)localObject1).setText(localObject2);
    }
    localTable.setHeaderVisible(true);
    localTable.setLinesVisible(true);
    localTable.setLayoutData("grow");
    TabItem localTabItem2 = createTabPanel(localTabFolder, "Docking 2 (fill)", new MigLayout("fill", "[c]", ""));
    createPanel(localTabItem2, "1. North", "north");
    createPanel(localTabItem2, "2. North", "north");
    createPanel(localTabItem2, "3. West", "west");
    createPanel(localTabItem2, "4. West", "west");
    createPanel(localTabItem2, "5. South", "south");
    createPanel(localTabItem2, "6. East", "east");
    createButton(localTabItem2, "7. Normal", "");
    createButton(localTabItem2, "8. Normal", "");
    createButton(localTabItem2, "9. Normal", "");
    Object localObject1 = createTabPanel(localTabFolder, "Docking 3", new MigLayout());
    createPanel(localObject1, "1. North", "north");
    createPanel(localObject1, "2. South", "south");
    createPanel(localObject1, "3. West", "west");
    createPanel(localObject1, "4. East", "east");
    createButton(localObject1, "5. Normal", "");
    Object localObject2 = createTabPanel(localTabFolder, "Docking 4", new MigLayout());
    createPanel(localObject2, "1. North", "north");
    createPanel(localObject2, "2. North", "north");
    createPanel(localObject2, "3. West", "west");
    createPanel(localObject2, "4. West", "west");
    createPanel(localObject2, "5. South", "south");
    createPanel(localObject2, "6. East", "east");
    createButton(localObject2, "7. Normal", "");
    createButton(localObject2, "8. Normal", "");
    createButton(localObject2, "9. Normal", "");
    TabItem localTabItem3 = createTabPanel(localTabFolder, "Docking 5 (fillx)", new MigLayout("fillx", "[c]", ""));
    createPanel(localTabItem3, "1. North", "north");
    createPanel(localTabItem3, "2. North", "north");
    createPanel(localTabItem3, "3. West", "west");
    createPanel(localTabItem3, "4. West", "west");
    createPanel(localTabItem3, "5. South", "south");
    createPanel(localTabItem3, "6. East", "east");
    createButton(localTabItem3, "7. Normal", "");
    createButton(localTabItem3, "8. Normal", "");
    createButton(localTabItem3, "9. Normal", "");
    TabItem localTabItem4 = createTabPanel(localTabFolder, "Random Docking", new MigLayout("fill"));
    String[] arrayOfString = { "north", "east", "south", "west" };
    Random localRandom = new Random();
    for (int k = 0; k < 20; k++)
    {
      int m = localRandom.nextInt(4);
      createPanel(localTabItem4, k + 1 + " " + arrayOfString[m], arrayOfString[m]);
    }
    createPanel(localTabItem4, "I'm in the Center!", "grow");
    return (Composite)(Composite)localTabFolder;
  }

  public Control createAbsolute_Position(Composite paramComposite)
  {
    TabFolder localTabFolder = new TabFolder(paramComposite, DOUBLE_BUFFER);
    TabItem localTabItem1 = createTabPanel(localTabFolder, "X Y Positions", new FillLayout());
    Composite localComposite1 = createPanel(localTabItem1, new MigLayout());
    createButton(localComposite1, "pos 0.5al 0al", null);
    createButton(localComposite1, "pos 1al 0al", null);
    createButton(localComposite1, "pos 0.5al 0.5al", null);
    createButton(localComposite1, "pos 5in 45lp", null);
    createButton(localComposite1, "pos 0.5al 0.5al", null);
    createButton(localComposite1, "pos 0.5al 1al", null);
    createButton(localComposite1, "pos 1al .25al", null);
    createButton(localComposite1, "pos visual.x2-pref visual.y2-pref", null);
    createButton(localComposite1, "pos 1al -1in", null);
    createButton(localComposite1, "pos 100 100", null);
    createButton(localComposite1, "pos (10+(20*3lp)) 200", null);
    createButton(localComposite1, "Drag Window! (pos 500-container.xpos 500-container.ypos)", "pos 500-container.xpos 500-container.ypos");
    TabItem localTabItem2 = createTabPanel(localTabFolder, "X1 Y1 X2 Y2 Bounds", new FillLayout());
    Composite localComposite2 = createPanel(localTabItem2, new MigLayout());
    Label localLabel = createLabel(localComposite2, "pos (visual.x+visual.w*0.1) visual.y2-40 (visual.x2-visual.w*0.1) visual.y2", null, 16779264);
    localLabel.setBackground(new Color(display, 200, 200, 255));
    deriveFont(localLabel, 1, 10);
    createButton(localComposite2, "pos 0 0 container.x2 n", null);
    createButton(localComposite2, "pos visual.x 40 visual.x2 70", null);
    createButton(localComposite2, "pos visual.x 100 visual.x2 p", null);
    createButton(localComposite2, "pos 0.1al 0.4al n visual.y2-10", null);
    createButton(localComposite2, "pos 0.9al 0.4al n visual.y2-10", null);
    createButton(localComposite2, "pos 0.5al 0.5al, pad 3 0 -3 0", null);
    createButton(localComposite2, "pos n n 50% 50%", null);
    createButton(localComposite2, "pos 50% 50% n n", null);
    createButton(localComposite2, "pos 50% n n 50%", null);
    createButton(localComposite2, "pos n 50% 50% n", null);
    paramComposite.getShell().addControlListener(new ControlAdapter(localComposite1, paramComposite)
    {
      public void controlMoved(ControlEvent paramControlEvent)
      {
        if (!this.val$posPanel.isDisposed())
          this.val$posPanel.layout();
        else
          this.val$parent.getShell().removeControlListener(this);
      }
    });
    return localTabFolder;
  }

  public Control createComponent_Links(Composite paramComposite)
  {
    TabFolder localTabFolder = new TabFolder(paramComposite, DOUBLE_BUFFER);
    TabItem localTabItem1 = createTabPanel(localTabFolder, "Component Links", new MigLayout());
    createButton(localTabItem1, "Mini", "pos null ta.y ta.x2 null, pad 3 0 -3 0");
    createTextArea(localTabItem1, "Components, Please Link to Me!\nMy ID is: 'ta'", "id ta, pos 0.5al 0.5al, w 300");
    createButton(localTabItem1, "id b1,pos ta.x2 ta.y2", null);
    createButton(localTabItem1, "pos b1.x2+rel b1.y visual.x2 null", null);
    createCheck(localTabItem1, "pos (ta.x+indent) (ta.y2+rel)", null);
    createButton(localTabItem1, "pos ta.x2+rel ta.y visual.x2 null", null);
    createButton(localTabItem1, "pos null ta.y+(ta.h-pref)/2 ta.x-rel null", null);
    createButton(localTabItem1, "pos ta.x ta.y2+100 ta.x2 null", null);
    TabItem localTabItem2 = createTabPanel(localTabFolder, "External Components", new MigLayout());
    Button localButton = createButton(localTabItem2, "Bounds Externally Set!", "id ext, external");
    localButton.setBounds(250, 130, 200, 40);
    createButton(localTabItem2, "pos ext.x2 ext.y2", "pos ext.x2 ext.y2");
    createButton(localTabItem2, "pos null null ext.x ext.y", "pos null null ext.x ext.y");
    TabItem localTabItem3 = createTabPanel(localTabFolder, "End Grouping", new FillLayout());
    Composite localComposite1 = createPanel(localTabItem3, new MigLayout());
    createButton(localComposite1, "id b1, endgroupx g1, pos 200 200", null);
    createButton(localComposite1, "id b2, endgroupx g1, pos (b1.x+2ind) (b1.y2+rel)", null);
    createButton(localComposite1, "id b3, endgroupx g1, pos (b1.x+4ind) (b2.y2+rel)", null);
    createButton(localComposite1, "id b4, endgroupx g1, pos (b1.x+6ind) (b3.y2+rel)", null);
    TabItem localTabItem4 = createTabPanel(localTabFolder, "Group Bounds", new FillLayout());
    Composite localComposite2 = createPanel(localTabItem4, new MigLayout());
    createButton(localComposite2, "id grp1.b1, pos n 0.5al 50% n", null);
    createButton(localComposite2, "id grp1.b2, pos 50% 0.5al n n", null);
    createButton(localComposite2, "id grp1.b3, pos 0.5al n n b1.y", null);
    createButton(localComposite2, "id grp1.b4, pos 0.5al b1.y2 n n", null);
    createButton(localComposite2, "pos n grp1.y2 grp1.x n", null);
    createButton(localComposite2, "pos n n grp1.x grp1.y", null);
    createButton(localComposite2, "pos grp1.x2 n n grp1.y", null);
    createButton(localComposite2, "pos grp1.x2 grp1.y2", null);
    Composite localComposite3 = createPanel(localComposite2, (Layout)null);
    localComposite3.setLayoutData("pos grp1.x grp1.y grp1.x2 grp1.y2");
    localComposite3.setBackground(new Color(display, 200, 200, 255));
    return localTabFolder;
  }

  public Control createFlow_Direction(Composite paramComposite)
  {
    TabFolder localTabFolder = new TabFolder(paramComposite, DOUBLE_BUFFER);
    createFlowPanel(localTabFolder, "Layout: flowx, Cell: flowx", "", "flowx");
    createFlowPanel(localTabFolder, "Layout: flowx, Cell: flowy", "", "flowy");
    createFlowPanel(localTabFolder, "Layout: flowy, Cell: flowx", "flowy", "flowx");
    createFlowPanel(localTabFolder, "Layout: flowy, Cell: flowy", "flowy", "flowy");
    return localTabFolder;
  }

  private TabItem createFlowPanel(TabFolder paramTabFolder, String paramString1, String paramString2, String paramString3)
  {
    MigLayout localMigLayout = new MigLayout("center, wrap 3," + paramString2, "[110,fill]", "[110,fill]");
    TabItem localTabItem = createTabPanel(paramTabFolder, paramString1, localMigLayout);
    for (int i = 0; i < 9; i++)
    {
      localObject = createPanel(localTabItem, "" + (i + 1), paramString3);
      Font localFont = deriveFont((Control)localObject, -1, 20);
      localObject.getChildren()[0].setFont(localFont);
    }
    Composite localComposite = createPanel(localTabItem, "5:2", paramString3 + ",cell 1 1");
    Object localObject = deriveFont(localComposite, -1, 20);
    localComposite.getChildren()[0].setFont((Font)localObject);
    return (TabItem)localTabItem;
  }

  public Control createDebug(Composite paramComposite)
  {
    return createPlainImpl(paramComposite, true);
  }

  public Control createButton_Bars(Composite paramComposite)
  {
    MigLayout localMigLayout = new MigLayout("ins 0 0 15lp 0", "[grow]", "[grow]u[baseline,nogrid]");
    Composite localComposite = new Composite(paramComposite, DOUBLE_BUFFER);
    localComposite.setLayout(localMigLayout);
    TabFolder localTabFolder = new TabFolder(localComposite, DOUBLE_BUFFER);
    localTabFolder.setLayoutData("grow, wrap");
    createButtonBarsPanel(localTabFolder, "Buttons", "help", false);
    createButtonBarsPanel(localTabFolder, "Buttons with Help2", "help2", false);
    createButtonBarsPanel(localTabFolder, "Buttons (Same width)", "help", true);
    createLabel(localComposite, "Button Order:", "");
    Label localLabel = createLabel(localComposite, "", "growx");
    deriveFont(localLabel, 1, -1);
    Button localButton1 = createToggleButton(localComposite, "Windows", "wmin button");
    Button localButton2 = createToggleButton(localComposite, "Mac OS X", "wmin button");
    localButton1.addSelectionListener(new SelectionAdapter(localButton1, localLabel, localButton2, localComposite)
    {
      public void widgetSelected(SelectionEvent paramSelectionEvent)
      {
        if (this.val$winButt.getSelection())
        {
          PlatformDefaults.setPlatform(0);
          this.val$formatLabel.setText("'" + PlatformDefaults.getButtonOrder() + "'");
          this.val$macButt.setSelection(false);
          this.val$mainPanel.layout();
        }
      }
    });
    localButton2.addSelectionListener(new SelectionAdapter(localButton2, localLabel, localButton1, localComposite)
    {
      public void widgetSelected(SelectionEvent paramSelectionEvent)
      {
        if (this.val$macButt.getSelection())
        {
          PlatformDefaults.setPlatform(1);
          this.val$formatLabel.setText("'" + PlatformDefaults.getButtonOrder() + "'");
          this.val$winButt.setSelection(false);
          this.val$mainPanel.layout();
        }
      }
    });
    Button localButton3 = createButton(localComposite, "Help", "gap unrel,wmin button");
    localButton3.addSelectionListener(new SelectionAdapter(paramComposite)
    {
      public void widgetSelected(SelectionEvent paramSelectionEvent)
      {
        MessageBox localMessageBox = new MessageBox(this.val$parent.getShell());
        localMessageBox.setMessage("See JavaDoc for PlatformConverter.getButtonBarOrder(..) for details on the format string.");
        localMessageBox.open();
      }
    });
    (PlatformDefaults.getPlatform() == 0 ? localButton1 : localButton2).setSelection(true);
    return localComposite;
  }

  private TabItem createButtonBarsPanel(TabFolder paramTabFolder, String paramString1, String paramString2, boolean paramBoolean)
  {
    MigLayout localMigLayout = new MigLayout("nogrid, fillx, aligny 100%, gapy unrel");
    TabItem localTabItem = createTabPanel(paramTabFolder, paramString1, localMigLayout);
    String[][] arrayOfString; = { { "No", "Yes" }, { "Help", "Close" }, { "OK", "Help" }, { "OK", "Cancel", "Help" }, { "OK", "Cancel", "Apply", "Help" }, { "No", "Yes", "Cancel" }, { "Help", "< Move Back", "Move Forward >", "Cancel" }, { "Print...", "Cancel", "Help" } };
    for (int i = 0; i < arrayOfString;.length; i++)
      for (int j = 0; j < arrayOfString;[i].length; j++)
      {
        String str1 = arrayOfString;[i][j];
        String str2 = str1;
        if (str1.equals("Help"))
          str2 = paramString2;
        else if (str1.equals("< Move Back"))
          str2 = "back";
        else if (str1.equals("Close"))
          str2 = "cancel";
        else if (str1.equals("Move Forward >"))
          str2 = "next";
        else if (str1.equals("Print..."))
          str2 = "other";
        String str3 = j == arrayOfString;[i].length - 1 ? ",wrap" : "";
        String str4 = paramBoolean ? "sgx " + i + "," : "";
        createButton(localTabItem, str1, str4 + "tag " + str2 + str3);
      }
    return localTabItem;
  }

  public Control createOrientation(Composite paramComposite)
  {
    TabFolder localTabFolder = new TabFolder(paramComposite, DOUBLE_BUFFER);
    MigLayout localMigLayout1 = new MigLayout("flowy", "[grow,fill]", "[]0[]15lp[]0[]");
    TabItem localTabItem = createTabPanel(localTabFolder, "Orientation", localMigLayout1);
    MigLayout localMigLayout2 = new MigLayout("", "[][grow,fill]", "");
    Composite localComposite1 = createPanel(localTabItem, localMigLayout2);
    addSeparator(localComposite1, "Default Orientation");
    createLabel(localComposite1, "Level", "");
    createTextField(localComposite1, "", "span,growx");
    createLabel(localComposite1, "Radar", "");
    createTextField(localComposite1, "", "");
    createTextField(localComposite1, "", "");
    MigLayout localMigLayout3 = new MigLayout("rtl,ttb", "[][grow,fill]", "");
    Composite localComposite2 = createPanel(localTabItem, localMigLayout3);
    addSeparator(localComposite2, "Right to Left");
    createLabel(localComposite2, "Level", "");
    createTextField(localComposite2, "", "span,growx");
    createLabel(localComposite2, "Radar", "");
    createTextField(localComposite2, "", "");
    createTextField(localComposite2, "", "");
    MigLayout localMigLayout4 = new MigLayout("rtl,btt", "[][grow,fill]", "");
    Composite localComposite3 = createPanel(localTabItem, localMigLayout4);
    addSeparator(localComposite3, "Right to Left, Bottom to Top");
    createLabel(localComposite3, "Level", "");
    createTextField(localComposite3, "", "span,growx");
    createLabel(localComposite3, "Radar", "");
    createTextField(localComposite3, "", "");
    createTextField(localComposite3, "", "");
    MigLayout localMigLayout5 = new MigLayout("ltr,btt", "[][grow,fill]", "");
    Composite localComposite4 = createPanel(localTabItem, localMigLayout5);
    addSeparator(localComposite4, "Left to Right, Bottom to Top");
    createLabel(localComposite4, "Level", "");
    createTextField(localComposite4, "", "span,growx");
    createLabel(localComposite4, "Radar", "");
    createTextField(localComposite4, "", "");
    createTextField(localComposite4, "", "");
    return localTabFolder;
  }

  public Control createCell_Position(Composite paramComposite)
  {
    TabFolder localTabFolder = new TabFolder(paramComposite, DOUBLE_BUFFER);
    MigLayout localMigLayout1 = new MigLayout("", "[100:pref,fill]", "[100:pref,fill]");
    TabItem localTabItem1 = createTabPanel(localTabFolder, "Absolute", localMigLayout1);
    createPanel(localTabItem1, "cell 0 0", null);
    createPanel(localTabItem1, "cell 2 0", null);
    createPanel(localTabItem1, "cell 3 0", null);
    createPanel(localTabItem1, "cell 1 1", null);
    createPanel(localTabItem1, "cell 0 2", null);
    createPanel(localTabItem1, "cell 2 2", null);
    createPanel(localTabItem1, "cell 2 2", null);
    MigLayout localMigLayout2 = new MigLayout("wrap", "[100:pref,fill][100:pref,fill][100:pref,fill][100:pref,fill]", "[100:pref,fill]");
    TabItem localTabItem2 = createTabPanel(localTabFolder, "Relative + Wrap", localMigLayout2);
    createPanel(localTabItem2, "", null);
    createPanel(localTabItem2, "skip", null);
    createPanel(localTabItem2, "", null);
    createPanel(localTabItem2, "skip,wrap", null);
    createPanel(localTabItem2, "", null);
    createPanel(localTabItem2, "skip,split", null);
    createPanel(localTabItem2, "", null);
    MigLayout localMigLayout3 = new MigLayout("", "[100:pref,fill]", "[100:pref,fill]");
    TabItem localTabItem3 = createTabPanel(localTabFolder, "Relative", localMigLayout3);
    createPanel(localTabItem3, "", null);
    createPanel(localTabItem3, "skip", null);
    createPanel(localTabItem3, "wrap", null);
    createPanel(localTabItem3, "skip,wrap", null);
    createPanel(localTabItem3, "", null);
    createPanel(localTabItem3, "skip,split", null);
    createPanel(localTabItem3, "", null);
    MigLayout localMigLayout4 = new MigLayout("", "[100:pref,fill]", "[100:pref,fill]");
    TabItem localTabItem4 = createTabPanel(localTabFolder, "Mixed", localMigLayout4);
    createPanel(localTabItem4, "", null);
    createPanel(localTabItem4, "cell 2 0", null);
    createPanel(localTabItem4, "", null);
    createPanel(localTabItem4, "cell 1 1,wrap", null);
    createPanel(localTabItem4, "", null);
    createPanel(localTabItem4, "cell 2 2,split", null);
    createPanel(localTabItem4, "", null);
    return localTabFolder;
  }

  public Control createPlain(Composite paramComposite)
  {
    return createPlainImpl(paramComposite, false);
  }

  private Control createPlainImpl(Composite paramComposite, boolean paramBoolean)
  {
    TabFolder localTabFolder = new TabFolder(paramComposite, DOUBLE_BUFFER);
    MigLayout localMigLayout = new MigLayout((paramBoolean) && (benchRuns == 0) ? "debug" : "", "[r][100lp, fill][60lp][95lp, fill]", "");
    TabItem localTabItem = createTabPanel(localTabFolder, "Plain", localMigLayout);
    addSeparator(localTabItem, "Manufacturer");
    createLabel(localTabItem, "Company", "");
    createTextField(localTabItem, "", "span,growx");
    createLabel(localTabItem, "Contact", "");
    createTextField(localTabItem, "", "span,growx");
    createLabel(localTabItem, "Order No", "");
    createTextField(localTabItem, "", "wmin 15*6,wrap");
    addSeparator(localTabItem, "Inspector");
    createLabel(localTabItem, "Name", "");
    createTextField(localTabItem, "", "span,growx");
    createLabel(localTabItem, "Reference No", "");
    createTextField(localTabItem, "", "wrap");
    createLabel(localTabItem, "Status", "");
    createCombo(localTabItem, new String[] { "In Progress", "Finnished", "Released" }, "wrap");
    addSeparator(localTabItem, "Ship");
    createLabel(localTabItem, "Shipyard", "");
    createTextField(localTabItem, "", "span,growx");
    createLabel(localTabItem, "Register No", "");
    createTextField(localTabItem, "", "");
    createLabel(localTabItem, "Hull No", "right");
    createTextField(localTabItem, "", "wmin 15*6,wrap");
    createLabel(localTabItem, "Project StructureType", "");
    createCombo(localTabItem, new String[] { "New Building", "Convention", "Repair" }, "wrap");
    if (paramBoolean)
      createLabel(localTabItem, "Blue is component bounds. Cell bounds (red) can not be shown in SWT", "newline,ax left,span,gaptop 40");
    return localTabFolder;
  }

  public Control createBound_Sizes(Composite paramComposite)
  {
    TabFolder localTabFolder = new TabFolder(paramComposite, DOUBLE_BUFFER);
    for (int i = 0; i < 2; i++)
    {
      String str = i == 0 ? "[right][300]" : "[right, 150lp:pref][300]";
      MigLayout localMigLayout1 = new MigLayout("wrap", str, "");
      TabItem localTabItem1 = createTabPanel(localTabFolder, i == 0 ? "Jumping 1" : "Stable 1", localMigLayout1);
      createLabel(localTabItem1, "File Number:", "");
      createTextField(localTabItem1, "", "growx");
      createLabel(localTabItem1, "RFQ Number:", "");
      createTextField(localTabItem1, "", "growx");
      createLabel(localTabItem1, "Entry Date:", "");
      createTextField(localTabItem1, "        ", "wmin 6*6");
      createLabel(localTabItem1, "Sales Person:", "");
      createTextField(localTabItem1, "", "growx");
      MigLayout localMigLayout2 = new MigLayout("wrap", str, "");
      TabItem localTabItem2 = createTabPanel(localTabFolder, i == 0 ? "Jumping 2" : "Stable 2", localMigLayout2);
      createLabel(localTabItem2, "Shipper:", "");
      createTextField(localTabItem2, "        ", "split 2");
      createTextField(localTabItem2, "", "growx");
      createLabel(localTabItem2, "Consignee:", "");
      createTextField(localTabItem2, "        ", "split 2");
      createTextField(localTabItem2, "", "growx");
      createLabel(localTabItem2, "Departure:", "");
      createTextField(localTabItem2, "        ", "split 2");
      createTextField(localTabItem2, "", "growx");
      createLabel(localTabItem2, "Destination:", "");
      createTextField(localTabItem2, "        ", "split 2");
      createTextField(localTabItem2, "", "growx");
    }
    return localTabFolder;
  }

  public Control createComponent_Sizes(Composite paramComposite)
  {
    TabFolder localTabFolder = new TabFolder(paramComposite, DOUBLE_BUFFER);
    MigLayout localMigLayout = new MigLayout("wrap", "[right][0:pref,grow]", "");
    TabItem localTabItem = createTabPanel(localTabFolder, "Component Sizes", new FillLayout());
    SashForm localSashForm = new SashForm(getComposite(localTabItem), 65792);
    localSashForm.setBackground(new Color(display, 255, 255, 255));
    localSashForm.setBackgroundMode(2);
    Composite localComposite = createPanel(localSashForm, localMigLayout, 2048);
    createTextArea(localSashForm, "Use slider to see how the components grow and shrink depending on the constraints set on them.", "");
    createLabel(localComposite, "", "");
    createTextField(localComposite, "8       ", "");
    createLabel(localComposite, "width min!", null);
    createTextField(localComposite, "3  ", "width min!");
    createLabel(localComposite, "width pref!", "");
    createTextField(localComposite, "3  ", "width pref!");
    createLabel(localComposite, "width min:pref", null);
    createTextField(localComposite, "8       ", "width min:pref");
    createLabel(localComposite, "width min:100:150", null);
    createTextField(localComposite, "8       ", "width min:100:150");
    createLabel(localComposite, "width min:100:150, growx", null);
    createTextField(localComposite, "8       ", "width min:100:150, growx");
    createLabel(localComposite, "width min:100, growx", null);
    createTextField(localComposite, "8       ", "width min:100, growx");
    createLabel(localComposite, "width 40!", null);
    createTextField(localComposite, "8       ", "width 40!");
    createLabel(localComposite, "width 40:40:40", null);
    createTextField(localComposite, "8       ", "width 40:40:40");
    return localTabFolder;
  }

  public Control createCell_Alignments(Composite paramComposite)
  {
    TabFolder localTabFolder = new TabFolder(paramComposite, DOUBLE_BUFFER);
    MigLayout localMigLayout1 = new MigLayout("wrap", "[grow,left][grow,center][grow,right][grow,fill,center]", "[]unrel[][]");
    TabItem localTabItem1 = createTabPanel(localTabFolder, "Horizontal", localMigLayout1);
    String[] arrayOfString = { "", "growx", "growx 0", "left", "center", "right", "leading", "trailing" };
    createLabel(localTabItem1, "[left]", "c");
    createLabel(localTabItem1, "[center]", "c");
    createLabel(localTabItem1, "[right]", "c");
    createLabel(localTabItem1, "[fill,center]", "c, growx 0");
    for (int i = 0; i < arrayOfString.length; i++)
      for (int j = 0; j < 4; j++)
      {
        localObject = arrayOfString[i].length() > 0 ? arrayOfString[i] : "default";
        createButton(localTabItem1, (String)localObject, arrayOfString[i]);
      }
    MigLayout localMigLayout2 = new MigLayout("wrap,flowy", "[right][]", "[grow,top][grow,center][grow,bottom][grow,fill,bottom][grow,fill,baseline]");
    TabItem localTabItem2 = createTabPanel(localTabFolder, "Vertical", localMigLayout2);
    Object localObject = { "", "growy", "growy 0", "top", "center", "bottom" };
    createLabel(localTabItem2, "[top]", "center");
    createLabel(localTabItem2, "[center]", "center");
    createLabel(localTabItem2, "[bottom]", "center");
    createLabel(localTabItem2, "[fill, bottom]", "center, growy 0");
    createLabel(localTabItem2, "[fill, baseline]", "center");
    for (int k = 0; k < localObject.length; k++)
      for (int m = 0; m < 5; m++)
      {
        String str = localObject[k].length() > 0 ? localObject[k] : "default";
        createButton(localTabItem2, str, localObject[k]);
      }
    return (Control)localTabFolder;
  }

  public Control createUnits(Composite paramComposite)
  {
    TabFolder localTabFolder = new TabFolder(paramComposite, DOUBLE_BUFFER);
    MigLayout localMigLayout1 = new MigLayout("wrap", "[right][]", "");
    TabItem localTabItem1 = createTabPanel(localTabFolder, "Horizontal", localMigLayout1);
    String[] arrayOfString1 = { "72pt", "25.4mm", "2.54cm", "1in", "72px", "96px", "120px", "25%", "30sp" };
    for (int i = 0; i < arrayOfString1.length; i++)
    {
      createLabel(localTabItem1, arrayOfString1[i], "");
      createTextField(localTabItem1, "", "width " + arrayOfString1[i] + "");
    }
    MigLayout localMigLayout2 = new MigLayout("", "[right][][]", "");
    TabItem localTabItem2 = createTabPanel(localTabFolder, "Horizontal LP", localMigLayout2);
    createLabel(localTabItem2, "9 cols", "");
    createTextField(localTabItem2, "", "wmin 9*6");
    String[] arrayOfString2 = { "75lp", "75px", "88px", "100px" };
    createLabel(localTabItem2, "", "wrap");
    for (int j = 0; j < arrayOfString2.length; j++)
    {
      createLabel(localTabItem2, arrayOfString2[j], "");
      createTextField(localTabItem2, "", "width " + arrayOfString2[j] + ", wrap");
    }
    MigLayout localMigLayout3 = new MigLayout("wrap,flowy", "[c]", "[top][top]");
    TabItem localTabItem3 = createTabPanel(localTabFolder, "Vertical", localMigLayout3);
    String[] arrayOfString3 = { "72pt", "25.4mm", "2.54cm", "1in", "72px", "96px", "120px", "25%", "30sp" };
    for (int k = 0; k < arrayOfString1.length; k++)
    {
      createLabel(localTabItem3, arrayOfString3[k], "");
      createTextArea(localTabItem3, "", "width 50!, height " + arrayOfString3[k] + "");
    }
    MigLayout localMigLayout4 = new MigLayout("wrap,flowy", "[c]", "[top][top]40px[top][top]");
    TabItem localTabItem4 = createTabPanel(localTabFolder, "Vertical LP", localMigLayout4);
    createLabel(localTabItem4, "4 rows", "");
    createTextArea(localTabItem4, "\n\n\n\n", "width 50!");
    createLabel(localTabItem4, "field", "");
    createTextField(localTabItem4, "", "wmin 5*9");
    String[] arrayOfString4 = { "63lp", "57px", "63px", "68px", "25%" };
    String[] arrayOfString5 = { "21lp", "21px", "23px", "24px", "10%" };
    for (int m = 0; m < arrayOfString4.length; m++)
    {
      createLabel(localTabItem4, arrayOfString4[m], "");
      createTextArea(localTabItem4, "", "width 50!, height " + arrayOfString4[m] + "");
      createLabel(localTabItem4, arrayOfString5[m], "");
      createTextField(localTabItem4, "", "height " + arrayOfString5[m] + "!,wmin 5*6");
    }
    createLabel(localTabItem4, "button", "skip 2");
    createButton(localTabItem4, "...", "");
    return localTabFolder;
  }

  public Control createGrouping(Composite paramComposite)
  {
    TabFolder localTabFolder = new TabFolder(paramComposite, DOUBLE_BUFFER);
    MigLayout localMigLayout1 = new MigLayout("", "[]push[][][]", "");
    TabItem localTabItem1 = createTabPanel(localTabFolder, "Ungrouped", localMigLayout1);
    createButton(localTabItem1, "Help", "");
    createButton(localTabItem1, "< Back", "gap push");
    createButton(localTabItem1, "Forward >", "");
    createButton(localTabItem1, "Apply", "gap unrel");
    createButton(localTabItem1, "Cancel", "gap unrel");
    MigLayout localMigLayout2 = new MigLayout("nogrid, fillx");
    TabItem localTabItem2 = createTabPanel(localTabFolder, "Grouped (Components)", localMigLayout2);
    createButton(localTabItem2, "Help", "sg");
    createButton(localTabItem2, "< Back", "sg, gap push");
    createButton(localTabItem2, "Forward >", "sg");
    createButton(localTabItem2, "Apply", "sg, gap unrel");
    createButton(localTabItem2, "Cancel", "sg, gap unrel");
    MigLayout localMigLayout3 = new MigLayout("", "[sg,fill]push[sg,fill][sg,fill]unrel[sg,fill]unrel[sg,fill]", "");
    TabItem localTabItem3 = createTabPanel(localTabFolder, "Grouped (Columns)", localMigLayout3);
    createButton(localTabItem3, "Help", "");
    createButton(localTabItem3, "< Back", "");
    createButton(localTabItem3, "Forward >", "");
    createButton(localTabItem3, "Apply", "");
    createButton(localTabItem3, "Cancel", "");
    MigLayout localMigLayout4 = new MigLayout();
    TabItem localTabItem4 = createTabPanel(localTabFolder, "Ungrouped Rows", localMigLayout4);
    createLabel(localTabItem4, "File Number:", "");
    createTextField(localTabItem4, "30                            ", "wrap");
    createLabel(localTabItem4, "BL/MBL number:", "");
    createTextField(localTabItem4, "7      ", "split 2");
    createTextField(localTabItem4, "7      ", "wrap");
    createLabel(localTabItem4, "Entry Date:", "");
    createTextField(localTabItem4, "7      ", "wrap");
    createLabel(localTabItem4, "RFQ Number:", "");
    createTextField(localTabItem4, "30                            ", "wrap");
    createLabel(localTabItem4, "Goods:", "");
    createCheck(localTabItem4, "Dangerous", "wrap");
    createLabel(localTabItem4, "Shipper:", "");
    createTextField(localTabItem4, "30                            ", "wrap");
    createLabel(localTabItem4, "Customer:", "");
    createTextField(localTabItem4, "", "split 2,growx");
    createButton(localTabItem4, "...", "width 60px:pref,wrap");
    createLabel(localTabItem4, "Port of Loading:", "");
    createTextField(localTabItem4, "30                            ", "wrap");
    createLabel(localTabItem4, "Destination:", "");
    createTextField(localTabItem4, "30                            ", "wrap");
    MigLayout localMigLayout5 = new MigLayout("", "[]", "[sg]");
    TabItem localTabItem5 = createTabPanel(localTabFolder, "Grouped Rows", localMigLayout5);
    createLabel(localTabItem5, "File Number:", "");
    createTextField(localTabItem5, "30                            ", "wrap");
    createLabel(localTabItem5, "BL/MBL number:", "");
    createTextField(localTabItem5, "7      ", "split 2");
    createTextField(localTabItem5, "7      ", "wrap");
    createLabel(localTabItem5, "Entry Date:", "");
    createTextField(localTabItem5, "7      ", "wrap");
    createLabel(localTabItem5, "RFQ Number:", "");
    createTextField(localTabItem5, "30                            ", "wrap");
    createLabel(localTabItem5, "Goods:", "");
    createCheck(localTabItem5, "Dangerous", "wrap");
    createLabel(localTabItem5, "Shipper:", "");
    createTextField(localTabItem5, "30                            ", "wrap");
    createLabel(localTabItem5, "Customer:", "");
    createTextField(localTabItem5, "", "split 2,growx");
    createButton(localTabItem5, "...", "width 50px:pref,wrap");
    createLabel(localTabItem5, "Port of Loading:", "");
    createTextField(localTabItem5, "30                            ", "wrap");
    createLabel(localTabItem5, "Destination:", "");
    createTextField(localTabItem5, "30                            ", "wrap");
    return localTabFolder;
  }

  public Control createSpan(Composite paramComposite)
  {
    TabFolder localTabFolder = new TabFolder(paramComposite, DOUBLE_BUFFER);
    MigLayout localMigLayout1 = new MigLayout("", "[fill][25%,fill][105lp!,fill][150px!,fill]", "[]15[][]");
    TabItem localTabItem1 = createTabPanel(localTabFolder, "Column Span/Split", localMigLayout1);
    createTextField(localTabItem1, "Col1 [ ]", "");
    createTextField(localTabItem1, "Col2 [25%]", "");
    createTextField(localTabItem1, "Col3 [105lp!]", "");
    createTextField(localTabItem1, "Col4 [150px!]", "wrap");
    createLabel(localTabItem1, "Full Name:", "");
    createTextField(localTabItem1, "span, growx                              ", "span,growx");
    createLabel(localTabItem1, "Phone:", "");
    createTextField(localTabItem1, "   ", "span 3, split 5");
    createTextField(localTabItem1, "     ", null);
    createTextField(localTabItem1, "     ", null);
    createTextField(localTabItem1, "       ", null);
    createLabel(localTabItem1, "(span 3, split 4)", "wrap");
    createLabel(localTabItem1, "Zip/City:", "");
    createTextField(localTabItem1, "     ", "");
    createTextField(localTabItem1, "span 2, growx", null);
    MigLayout localMigLayout2 = new MigLayout("wrap", "[225lp]para[225lp]", "[]3[]unrel[]3[]unrel[]3[]");
    TabItem localTabItem2 = createTabPanel(localTabFolder, "Row Span", localMigLayout2);
    createLabel(localTabItem2, "Name", "");
    createLabel(localTabItem2, "Notes", "");
    createTextField(localTabItem2, "growx", null);
    createTextArea(localTabItem2, "spany,grow          ", "spany,grow,hmin 13*5");
    createLabel(localTabItem2, "Phone", "");
    createTextField(localTabItem2, "growx", null);
    createLabel(localTabItem2, "Fax", "");
    createTextField(localTabItem2, "growx", null);
    return localTabFolder;
  }

  public Control createGrowing(Composite paramComposite)
  {
    TabFolder localTabFolder = new TabFolder(paramComposite, DOUBLE_BUFFER);
    MigLayout localMigLayout1 = new MigLayout("", "[pref!][grow,fill]", "[]15[]");
    TabItem localTabItem1 = createTabPanel(localTabFolder, "All", localMigLayout1);
    createLabel(localTabItem1, "Fixed", "");
    createLabel(localTabItem1, "Gets all extra space", "wrap");
    createTextField(localTabItem1, "     ", "");
    createTextField(localTabItem1, "     ", "");
    MigLayout localMigLayout2 = new MigLayout("", "[pref!][grow,fill]", "[]15[]");
    TabItem localTabItem2 = createTabPanel(localTabFolder, "Half", localMigLayout2);
    createLabel(localTabItem2, "Fixed", "");
    createLabel(localTabItem2, "Gets half of extra space", "");
    createLabel(localTabItem2, "Gets half of extra space", "wrap");
    createTextField(localTabItem2, "     ", "");
    createTextField(localTabItem2, "     ", "");
    createTextField(localTabItem2, "     ", "");
    MigLayout localMigLayout3 = new MigLayout("", "[pref!][0:0,grow 25,fill][0:0,grow 75,fill]", "[]15[]");
    TabItem localTabItem3 = createTabPanel(localTabFolder, "Percent 1", localMigLayout3);
    createLabel(localTabItem3, "Fixed", "");
    createLabel(localTabItem3, "Gets 25% of extra space", "");
    createLabel(localTabItem3, "Gets 75% of extra space", "wrap");
    createTextField(localTabItem3, "     ", "");
    createTextField(localTabItem3, "     ", "");
    createTextField(localTabItem3, "     ", "");
    MigLayout localMigLayout4 = new MigLayout("", "[0:0,grow 33,fill][0:0,grow 67,fill]", "[]15[]");
    TabItem localTabItem4 = createTabPanel(localTabFolder, "Percent 2", localMigLayout4);
    createLabel(localTabItem4, "Gets 33% of extra space", "");
    createLabel(localTabItem4, "Gets 67% of extra space", "wrap");
    createTextField(localTabItem4, "     ", "");
    createTextField(localTabItem4, "     ", "");
    MigLayout localMigLayout5 = new MigLayout("flowy", "[]15[]", "[][c,pref!][c,grow 25,fill][c,grow 75,fill]");
    TabItem localTabItem5 = createTabPanel(localTabFolder, "Vertical 1", localMigLayout5);
    createLabel(localTabItem5, "Fixed", "skip");
    createLabel(localTabItem5, "Gets 25% of extra space", "");
    createLabel(localTabItem5, "Gets 75% of extra space", "wrap");
    createLabel(localTabItem5, "new Text(SWT.MULTI | SWT.WRAP | SWT.BORDER)", "");
    createTextArea(localTabItem5, "", "hmin 4*13");
    createTextArea(localTabItem5, "", "hmin 4*13");
    createTextArea(localTabItem5, "", "hmin 4*13");
    MigLayout localMigLayout6 = new MigLayout("flowy", "[]15[]", "[][c,grow 33,fill][c,grow 67,fill]");
    TabItem localTabItem6 = createTabPanel(localTabFolder, "Vertical 2", localMigLayout6);
    createLabel(localTabItem6, "Gets 33% of extra space", "skip");
    createLabel(localTabItem6, "Gets 67% of extra space", "wrap");
    createLabel(localTabItem6, "new Text(SWT.MULTI | SWT.WRAP | SWT.BORDER)", "");
    createTextArea(localTabItem6, "", "hmin 4*13");
    createTextArea(localTabItem6, "", "hmin 4*13");
    return localTabFolder;
  }

  public Control createBasic_Sizes(Composite paramComposite)
  {
    TabFolder localTabFolder = new TabFolder(paramComposite, DOUBLE_BUFFER);
    MigLayout localMigLayout1 = new MigLayout("", "[]15[75px]25[min]25[]", "[]15");
    TabItem localTabItem1 = createTabPanel(localTabFolder, "Horizontal - Column size set", localMigLayout1);
    createLabel(localTabItem1, "75px", "skip");
    createLabel(localTabItem1, "Min", "");
    createLabel(localTabItem1, "Pref", "wrap");
    createLabel(localTabItem1, "new Text(15)", "");
    createTextField(localTabItem1, "               ", "wmin 10");
    createTextField(localTabItem1, "               ", "wmin 10");
    createTextField(localTabItem1, "               ", "wmin 10");
    MigLayout localMigLayout2 = new MigLayout("flowy,wrap", "[]15[]", "[]15[c,45:45]15[c,min]15[c,pref]");
    TabItem localTabItem2 = createTabPanel(localTabFolder, "\"Vertical - Row sized\"", localMigLayout2);
    createLabel(localTabItem2, "45px", "skip");
    createLabel(localTabItem2, "Min", "");
    createLabel(localTabItem2, "Pref", "");
    createLabel(localTabItem2, "new Text(SWT.MULTI)", "");
    createTextArea(localTabItem2, "", "");
    createTextArea(localTabItem2, "", "");
    createTextArea(localTabItem2, "", "");
    MigLayout localMigLayout3 = new MigLayout("flowy,wrap", "[]15[]", "[]15[baseline]15[baseline]15[baseline]");
    TabItem localTabItem3 = createTabPanel(localTabFolder, "\"Vertical - Component sized + Baseline\"", localMigLayout3);
    createLabel(localTabItem3, "45px", "skip");
    createLabel(localTabItem3, "Min", "");
    createLabel(localTabItem3, "Pref", "");
    createLabel(localTabItem3, "new Text(SWT.MULTI)", "");
    createTextArea(localTabItem3, "", "height 45");
    createTextArea(localTabItem3, "", "height min");
    createTextArea(localTabItem3, "", "height pref");
    return localTabFolder;
  }

  public Control createAlignments(Composite paramComposite)
  {
    TabFolder localTabFolder = new TabFolder(paramComposite, DOUBLE_BUFFER);
    MigLayout localMigLayout1 = new MigLayout("wrap", "[label]15[left]15[center]15[right]15[fill]15[]", "[]15[]");
    String[] arrayOfString1 = { "[label]", "[left]", "[center]", "[right]", "[fill]", "[] (Default)" };
    TabItem localTabItem1 = createTabPanel(localTabFolder, "Horizontal", localMigLayout1);
    String[] arrayOfString2 = { "First Name", "Phone Number", "Facsmile", "Email", "Address", "Other" };
    for (int i = 0; i < arrayOfString1.length; i++)
      createLabel(localTabItem1, arrayOfString1[i], "");
    for (i = 0; i < arrayOfString1.length; i++)
      for (int j = 0; j < arrayOfString2.length; j++)
        if (j == 0)
          createLabel(localTabItem1, arrayOfString2[i] + ":", "");
        else
          createButton(localTabItem1, arrayOfString2[i], "");
    MigLayout localMigLayout2 = new MigLayout("wrap,flowy", "[]unrel[]rel[]", "[top]15[center]15[bottom]15[fill]15[fill,baseline]15[baseline]15[]");
    String[] arrayOfString3 = { "[top]", "[center]", "[bottom]", "[fill]", "[fill,baseline]", "[baseline]", "[] (Default)" };
    TabItem localTabItem2 = createTabPanel(localTabFolder, "Vertical", localMigLayout2);
    for (int k = 0; k < arrayOfString3.length; k++)
      createLabel(localTabItem2, arrayOfString3[k], "");
    for (k = 0; k < arrayOfString3.length; k++)
      createButton(localTabItem2, "A Button", "");
    for (k = 0; k < arrayOfString3.length; k++)
      createTextField(localTabItem2, "JTextFied", "");
    for (k = 0; k < arrayOfString3.length; k++)
      createTextArea(localTabItem2, "Text    ", "");
    for (k = 0; k < arrayOfString3.length; k++)
      createTextArea(localTabItem2, "Text\nwith two lines", "");
    for (k = 0; k < arrayOfString3.length; k++)
      createTextArea(localTabItem2, "Scrolling Text\nwith two lines", "");
    return localTabFolder;
  }

  public Control createQuick_Start(Composite paramComposite)
  {
    TabFolder localTabFolder = new TabFolder(paramComposite, DOUBLE_BUFFER);
    MigLayout localMigLayout = new MigLayout("wrap", "[right][fill,sizegroup]unrel[right][fill,sizegroup]", "");
    TabItem localTabItem = createTabPanel(localTabFolder, "Quick Start", localMigLayout);
    addSeparator(localTabItem, "General");
    createLabel(localTabItem, "Company", "gap indent");
    createTextField(localTabItem, "", "span,growx");
    createLabel(localTabItem, "Contact", "gap indent");
    createTextField(localTabItem, "", "span,growx");
    addSeparator(localTabItem, "Propeller");
    createLabel(localTabItem, "PTI/kW", "gap indent");
    createTextField(localTabItem, "", "wmin 130");
    createLabel(localTabItem, "Power/kW", "gap indent");
    createTextField(localTabItem, "", "wmin 130");
    createLabel(localTabItem, "R/mm", "gap indent");
    createTextField(localTabItem, "", "wmin 130");
    createLabel(localTabItem, "D/mm", "gap indent");
    createTextField(localTabItem, "", "wmin 130");
    return localTabFolder;
  }

  public Control createGrow_Shrink(Composite paramComposite)
  {
    TabFolder localTabFolder = new TabFolder(paramComposite, DOUBLE_BUFFER);
    MigLayout localMigLayout = new MigLayout("nogrid");
    TabItem localTabItem1 = createTabPanel(localTabFolder, "Shrink", new FillLayout());
    SashForm localSashForm1 = new SashForm(getComposite(localTabItem1), 65792);
    localSashForm1.setBackground(new Color(display, 255, 255, 255));
    localSashForm1.setBackgroundMode(2);
    Composite localComposite1 = createPanel(localSashForm1, localMigLayout, 2048);
    localComposite1.setLayoutData("wmin 100");
    createTextField(localComposite1, "shp 110", "shp 110,w 10:130");
    createTextField(localComposite1, "Default (100)", "w 10:130");
    createTextField(localComposite1, "shp 90", "shp 90,w 10:130");
    createTextField(localComposite1, "shrink 25", "newline,shrink 25,w 10:130");
    createTextField(localComposite1, "shrink 75", "shrink 75,w 10:130");
    createTextField(localComposite1, "Default", "newline, w 10:130");
    createTextField(localComposite1, "Default", "w 10:130");
    createTextField(localComposite1, "shrink 0", "newline,shrink 0,w 10:130");
    createTextField(localComposite1, "shp 110", "newline,shp 110,w 10:130");
    createTextField(localComposite1, "shp 100,shrink 25", "shp 100,shrink 25,w 10:130");
    createTextField(localComposite1, "shp 100,shrink 75", "shp 100,shrink 75,w 10:130");
    createTextArea(localSashForm1, "Use the slider to see how the components shrink depending on the constraints set on them.\n\n'shp' means Shrink Priority. Lower values will be shrunk before higer ones and the default value is 100.\n\n'shrink' means Shrink Weight. Lower values relative to other's means they will shrink less when space is scarse. Shrink Weight is only relative to components with the same Shrink Priority. Default Shrink Weight is 100.\n\nThe component's minimum size will always be honored.\n\nFor SWT, which doesn't have a component notion of minimum, preferred or maximum size, those sizes are set explicitly to minimum 10 and preferred 130 pixels.", "");
    TabItem localTabItem2 = createTabPanel(localTabFolder, "Grow", new FillLayout());
    SashForm localSashForm2 = new SashForm(getComposite(localTabItem2), 65792);
    localSashForm2.setBackground(new Color(display, 255, 255, 255));
    localSashForm2.setBackgroundMode(2);
    Composite localComposite2 = createPanel(localSashForm2, new MigLayout("nogrid", "[grow]"), 2048);
    localComposite2.setLayoutData("wmin 100");
    createButton(localComposite2, "gp 110, grow", "gp 110, grow, wmax 170");
    createButton(localComposite2, "Default (100), grow", "grow, wmax 170");
    createButton(localComposite2, "gp 90, grow", "gp 90, grow, wmax 170");
    createButton(localComposite2, "Default Button", "newline");
    createButton(localComposite2, "growx", "newline,growx,wrap");
    createButton(localComposite2, "gp 110, grow", "gp 110, grow, wmax 170");
    createButton(localComposite2, "gp 100, grow 25", "gp 100, grow 25, wmax 170");
    createButton(localComposite2, "gp 100, grow 75", "gp 100, grow 75, wmax 170");
    createTextArea(localSashForm2, "'gp' means Grow Priority. Lower values will be grown before higher ones and the default value is 100.\n\n'grow' means Grow Weight. Higher values relative to other's means they will grow more when space is up for takes. Grow Weight is only relative to components with the same Grow Priority. Default Grow Weight is 0 which means components will normally not grow. \n\nNote that the buttons in the first and last row have max width set to 170 to emphasize Grow Priority.\n\nThe component's maximum size will always be honored.", "");
    return localTabFolder;
  }

  private Combo createCombo(Object paramObject1, String[] paramArrayOfString, Object paramObject2)
  {
    Combo localCombo = new Combo(getComposite(paramObject1), 4);
    for (int i = 0; i < paramArrayOfString.length; i++)
      localCombo.add(paramArrayOfString[i]);
    localCombo.setLayoutData(paramObject2);
    return localCombo;
  }

  private Label createLabel(Object paramObject1, String paramString, Object paramObject2)
  {
    return createLabel(paramObject1, paramString, paramObject2, 16384);
  }

  private Label createLabel(Object paramObject1, String paramString, Object paramObject2, int paramInt)
  {
    Label localLabel = new Label(getComposite(paramObject1), paramInt | DOUBLE_BUFFER);
    localLabel.setText(paramString);
    localLabel.setLayoutData(paramObject2 != null ? paramObject2 : paramString);
    return localLabel;
  }

  private Text createTextField(Object paramObject1, String paramString, Object paramObject2)
  {
    Text localText = new Text(getComposite(paramObject1), 0x804 | DOUBLE_BUFFER);
    localText.setText(paramString);
    localText.setLayoutData(paramObject2 != null ? paramObject2 : paramString);
    return localText;
  }

  private Button createButton(Object paramObject1, String paramString, Object paramObject2)
  {
    return createButton(getComposite(paramObject1), paramString, paramObject2, false);
  }

  private Button createButton(Object paramObject1, String paramString, Object paramObject2, boolean paramBoolean)
  {
    Button localButton = new Button(getComposite(paramObject1), 0x40008 | DOUBLE_BUFFER);
    localButton.setText(paramString.length() == 0 ? "\"\"" : paramString);
    localButton.setLayoutData(paramObject2 != null ? paramObject2 : paramString);
    return localButton;
  }

  private Composite createPanel(Object paramObject1, String paramString, Object paramObject2)
  {
    Color localColor = new Color(display.getActiveShell().getDisplay(), 255, 255, 255);
    Composite localComposite = new Composite(getComposite(paramObject1), DOUBLE_BUFFER | 0x800);
    localComposite.setLayout(new MigLayout("fill"));
    localComposite.setBackground(localColor);
    localComposite.setLayoutData(paramObject2 != null ? paramObject2 : paramString);
    paramString = paramString.length() == 0 ? "\"\"" : paramString;
    Label localLabel = createLabel(localComposite, paramString, "grow", 17039360);
    localLabel.setBackground(localColor);
    return localComposite;
  }

  private TabItem createTabPanel(TabFolder paramTabFolder, String paramString, Layout paramLayout)
  {
    Composite localComposite = new Composite(paramTabFolder, DOUBLE_BUFFER);
    TabItem localTabItem = new TabItem(paramTabFolder, DOUBLE_BUFFER);
    localTabItem.setControl(localComposite);
    localTabItem.setText(paramString);
    if (paramLayout != null)
      localComposite.setLayout(paramLayout);
    return localTabItem;
  }

  private Composite createPanel(Object paramObject, Layout paramLayout)
  {
    return createPanel(paramObject, paramLayout, 0);
  }

  private Composite createPanel(Object paramObject, Layout paramLayout, int paramInt)
  {
    Composite localComposite = new Composite(getComposite(paramObject), DOUBLE_BUFFER | paramInt);
    localComposite.setLayout(paramLayout);
    return localComposite;
  }

  private Button createToggleButton(Object paramObject1, String paramString, Object paramObject2)
  {
    Button localButton = new Button(getComposite(paramObject1), 0x2 | DOUBLE_BUFFER);
    localButton.setText(paramString.length() == 0 ? "\"\"" : paramString);
    localButton.setLayoutData(paramObject2 != null ? paramObject2 : paramString);
    return localButton;
  }

  private Button createCheck(Object paramObject1, String paramString, Object paramObject2)
  {
    Button localButton = new Button(getComposite(paramObject1), 0x20 | DOUBLE_BUFFER);
    localButton.setText(paramString);
    localButton.setLayoutData(paramObject2 != null ? paramObject2 : paramString);
    return localButton;
  }

  private List createList(Object paramObject1, String paramString, Object paramObject2)
  {
    List localList = new List(getComposite(paramObject1), DOUBLE_BUFFER | 0x800);
    localList.add(paramString);
    localList.setLayoutData(paramObject2);
    return localList;
  }

  private StyledText createTextArea(Object paramObject, String paramString1, String paramString2)
  {
    return createTextArea(paramObject, paramString1, paramString2, 0x842 | DOUBLE_BUFFER);
  }

  private StyledText createTextArea(Object paramObject, String paramString1, String paramString2, int paramInt)
  {
    StyledText localStyledText = new StyledText(getComposite(paramObject), 0x42 | paramInt | DOUBLE_BUFFER);
    localStyledText.setText(paramString1);
    localStyledText.setLayoutData(paramString2 != null ? paramString2 : paramString1);
    return localStyledText;
  }

  public Composite getComposite(Object paramObject)
  {
    if ((paramObject instanceof Control))
      return (Composite)paramObject;
    return (Composite)((TabItem)paramObject).getControl();
  }

  private Font deriveFont(Control paramControl, int paramInt1, int paramInt2)
  {
    Font localFont1 = paramControl.getFont();
    FontData localFontData = localFont1.getFontData()[0];
    if (paramInt1 != -1)
      localFontData.setStyle(paramInt1);
    if (paramInt2 != -1)
      localFontData.setHeight(paramInt2);
    Font localFont2 = new Font(display, localFontData);
    paramControl.setFont(localFont2);
    return localFont2;
  }

  private void addSeparator(Object paramObject, String paramString)
  {
    Label localLabel1 = createLabel(paramObject, paramString, "gaptop para, span, split 2");
    localLabel1.setForeground(new Color(display, 0, 70, 213));
    Label localLabel2 = new Label(getComposite(paramObject), 0x102 | DOUBLE_BUFFER);
    localLabel2.setLayoutData("gapleft rel, gaptop para, growx");
  }
}

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     net.miginfocom.demo.SwtDemo
 * JD-Core Version:    0.6.0
 */