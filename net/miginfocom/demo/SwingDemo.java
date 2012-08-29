package net.miginfocom.demo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.Random;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JViewport;
import javax.swing.ListModel;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.ConstraintParser;
import net.miginfocom.layout.IDEUtil;
import net.miginfocom.layout.LC;
import net.miginfocom.layout.LayoutUtil;
import net.miginfocom.layout.PlatformDefaults;
import net.miginfocom.swing.MigLayout;

public class SwingDemo extends JFrame
{
  public static final int INITIAL_INDEX = 0;
  private static final boolean DEBUG = false;
  private static final boolean OPAQUE = false;
  private static final String[][] panels = { { "Welcome", "\n\n         \"MigLayout makes complex layouts easy and normal layouts one-liners.\"" }, { "Quick Start", "This is an example of how to build a common dialog type. Note that there are no special components, nested panels or absolute references to cell positions. If you look at the source code you will see that the layout code is very simple to understand." }, { "Plain", "A simple example on how simple it is to create normal forms. No builders needed since the whole layout manager works like a builder." }, { "Alignments", "Shows how the alignment of components are specified. At the top/left is the alignment for the column/row. The components have no alignments specified.\n\nNote that baseline alignment will be interpreted as 'center' before JDK 6." }, { "Cell Alignments", "Shows how components are aligned when both column/row alignments and component constraints are specified. At the top/left are the alignment for the column/row and the text on the buttons is the component constraint that will override the column/row alignment if it is an alignment.\n\nNote that baseline alignment will be interpreted as 'center' before JDK 6." }, { "Basic Sizes", "A simple example that shows how to use the column or row min/preferred/max size to set the sizes of the contained components and also an example that shows how to do this directly in the component constraints." }, { "Growing", "A simple example that shows how to use the growx and growy constraint to set the sizes and how they should grow to fit the available size. Both the column/row and the component grow/shrink constraints can be set, but the components will always be confined to the space given by its column/row." }, { "Grow Shrink", "Demonstrates the very flexible grow and shrink constraints that can be set on a component.\nComponents can be divided into grow/shrink groups and also have grow/shrink weight within each of those groups.\n\nBy default components shrink to their inherent (or specified) minimum size, but they don't grow." }, { "Span", "This example shows the powerful spanning and splitting that can be specified in the component constraints. With spanning any number of cells can be merged with the additional option to split that space for more than one component. This makes layouts very flexible and reduces the number of times you will need nested panels to very few." }, { "Flow Direction", "Shows the different flow directions. Flow direction for the layout specifies if the next cell will be in the x or y dimension. Note that it can be a different flow direction in the slit cell (the middle cell is slit in two). Wrap is set to 3 for all panels." }, { "Grouping", "Sizes for both components and columns/rows can be grouped so they get the same size. For instance buttons in a button bar can be given a size-group so that they will all get the same minimum and preferred size (the largest within the group). Size-groups can be set for the width, height or both." }, { "Units", "Demonstrates the basic units that are understood by MigLayout. These units can be extended by the user by adding one or more UnitConverter(s)." }, { "Component Sizes", "Minimum, preferred and maximum component sizes can be overridden in the component constraints using any unit type. The format to do this is short and simple to understand. You simply specify the min, preferred and max sizes with a colon between.\n\nAbove are some examples of this. An exclamation mark means that the value will be used for all sizes." }, { "Bound Sizes", "Shows how to create columns that are stable between tabs using minimum sizes." }, { "Cell Position", "Even though MigLayout has automatic grid flow you can still specify the cell position explicitly. You can even combine absolute (x, y) and flow (skip, wrap and newline) constraints to build your layout." }, { "Orientation", "MigLayout supports not only right-to-left orientation, but also bottom-to-top. You can even set the flow direction so that the flow is vertical instead of horizontal. It will automatically pick up if right-to-left is to be used depending on the ComponentWrapper, but it can also be manually set for every layout." }, { "Absolute Position", "Demonstrates the option to place any number of components using absolute coordinates. This can be just the position (if min/preferred size) using \"x y p p\" format orthe bounds using the \"x1 y1 x2 y2\" format. Any unit can be used and percent is relative to the parent.\nAbsolute components will not disturb the flow or occupy cells in the grid. Absolute positioned components will be taken into account when calculating the container's preferred size." }, { "Component Links", "Components can be linked to any side of any other component. It can be a forward, backward or cyclic link references, as long as it is stable and won't continue to change value over many iterations.Links are referencing the ID of another component. The ID can be overridden by the component's constrains or is provided by the ComponentWrapper. For instance it will use the component's 'name' on Swing.\nSince the links can be combined with any expression (such as 'butt1.x+10' or 'max(button.x, 200)' the links are very customizable." }, { "Docking", "Docking components can be added around the grid. The docked component will get the whole width/height on the docked side by default, however this can be overridden. When all docked components are laid out, whatever space is left will be available for the normal grid laid out components. Docked components does not in any way affect the flow in the grid.\n\nSince the docking runs in the same code path as the normal layout code the same properties can be specified for the docking components. You can for instance set the sizes and alignment or link other components to their docked component's bounds." }, { "Button Bars", "Button order is very customizable and are by default different on the supported platforms. E.g. Gaps, button order and minimum button size are properties that are 'per platform'. MigLayout picks up the current platform automatically and adjusts the button order and minimum button size accordingly, all without using a button builder or any other special code construct." }, { "Visual Bounds", "Human perceptible bounds may not be the same as the mathematical bounds for the component. This is for instance the case if there is a drop shadow painted by the component's border. MigLayout can compensate for this in a simple way. Note the top middle tab-component, it is not aligned visually correct on Windows XP. For the second tab the bounds are corrected automatically on Windows XP." }, { "Debug", "Demonstrates the non-intrusive way to get visual debugging aid. There is no need to use a special DebugPanel or anything that will need code changes. The user can simply turn on debug on the layout manager by using the \"debug\" constraint and it will continuously repaint the panel with debug information on top. This means you don't have to change your code to debug!" }, { "Layout Showdown", "This is an implementation of the Layout Showdown posted on java.net by John O'Conner. The first tab is a pure implemenetation of the showdown that follows all the rules. The second tab is a slightly fixed version that follows some improved layout guidelines.The source code is for both the first and for the fixed version. Note the simplification of the code for the fixed version. Writing better layouts with MiG Layout is reasier that writing bad.\n\nReference: http://weblogs.java.net/blog/joconner/archive/2006/10/more_informatio.html" }, { "API Constraints1", "This dialog shows the constraint API added to v2.0. It works the same way as the string constraints but with chained method calls. See the source code for details." }, { "API Constraints2", "This dialog shows the constraint API added to v2.0. It works the same way as the string constraints but with chained method calls. See the source code for details." } };
  private int lastIndex = -10;
  private JPanel contentPanel = new JPanel(new MigLayout("wrap", "[]unrel[grow]", "[grow][pref]"));
  private JTabbedPane layoutPickerTabPane = new JTabbedPane();
  private JList pickerList = new JList(new DefaultListModel());
  private JTabbedPane southTabPane = new JTabbedPane();
  private JScrollPane descrTextAreaScroll = createTextAreaScroll("", 5, 80, true);
  private JTextArea descrTextArea = (JTextArea)this.descrTextAreaScroll.getViewport().getView();
  private JScrollPane sourceTextAreaScroll = null;
  private JTextArea sourceTextArea = null;
  private JPanel layoutDisplayPanel = new JPanel(new BorderLayout(0, 0));
  private static boolean buttonOpaque = true;
  private static boolean contentAreaFilled = true;
  private JFrame sourceFrame = null;
  private JTextArea sourceFrameTextArea = null;
  private static int benchRuns = 0;
  private static long startupMillis = 0L;
  private static long timeToShowMillis = 0L;
  private static long benchRunTime = 0L;
  private static String benchOutFileName = null;
  private static boolean append = false;
  private static long lastRunTimeStart = 0L;
  private static StringBuffer runTimeSB = null;
  private final ToolTipListener toolTipListener = new ToolTipListener(null);
  private final ConstraintListener constraintListener = new ConstraintListener(null);
  private static final Font BUTT_FONT = new Font("monospaced", 0, 12);
  static final Color LABEL_COLOR = new Color(0, 70, 213);

  public static void main(String[] paramArrayOfString)
  {
    try
    {
      System.setProperty("apple.laf.useScreenMenuBar", "true");
      System.setProperty("com.apple.mrj.application.apple.menu.about.name", "MiGLayout Swing Demo");
    }
    catch (Throwable localThrowable)
    {
    }
    startupMillis = System.currentTimeMillis();
    String str1 = UIManager.getSystemLookAndFeelClassName();
    if (paramArrayOfString.length > 0)
      for (int i = 0; i < paramArrayOfString.length; i++)
      {
        String str3 = paramArrayOfString[i].trim();
        if (str3.startsWith("-laf"))
        {
          str1 = str3.substring(4);
        }
        else if (str3.startsWith("-bench"))
        {
          benchRuns = 10;
          try
          {
            benchRuns = Integer.parseInt(str3.substring(6));
          }
          catch (Exception localException)
          {
          }
        }
        else if (str3.startsWith("-bout"))
        {
          benchOutFileName = str3.substring(5);
        }
        else if (str3.startsWith("-append"))
        {
          append = true;
        }
        else if (str3.startsWith("-verbose"))
        {
          runTimeSB = new StringBuffer(256);
        }
        else if (str3.equals("-steel"))
        {
          str1 = "javax.swing.plaf.metal.MetalLookAndFeel";
          System.setProperty("swing.metalTheme", "steel");
        }
        else if (str3.equals("-ocean"))
        {
          str1 = "javax.swing.plaf.metal.MetalLookAndFeel";
        }
        else
        {
          System.out.println("Usage: [-laf[look_&_feel_class_name]] [-bench[#_of_runs]] [-bout[benchmark_results_filename]] [-append] [-steel] [-ocean]\n -laf    Set the Application Look&Feel. (Look and feel must be in Classpath)\n -bench  Run demo as benchmark. Run count can be appended. 10 is default.\n -bout   Benchmark results output filename.\n -append Appends the result to the -bout file.\n -verbose Print the times of every run.\n -steel  Sets the old Steel theme for Sun's Metal look&feel.\n -ocean  Sets the Ocean theme for Sun's Metal look&feel.\n\nExamples:\n java -jar swingdemoapp.jar -bench -boutC:/bench.txt -append\n java -jar swingdemoapp.jar -ocean -bench20\n java -cp c:\\looks-2.0.4.jar;.\\swingdemoapp.jar net.miginfocom.demo.SwingDemo -lafcom.jgoodies.looks.plastic.PlasticLookAndFeel -bench20 -boutC:/bench.txt");
          System.exit(0);
        }
      }
    if (benchRuns == 0)
      LayoutUtil.setDesignTime(null, true);
    if (str1.endsWith("WindowsLookAndFeel"))
      buttonOpaque = false;
    if (str1.endsWith("AquaLookAndFeel"))
      contentAreaFilled = false;
    String str2 = str1;
    SwingUtilities.invokeLater(new Runnable(str2)
    {
      public void run()
      {
        try
        {
          UIManager.setLookAndFeel(this.val$laff);
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
        new SwingDemo();
      }
    });
  }

  public SwingDemo()
  {
    super("MigLayout Swing Demo v2.5 - Mig Layout v" + LayoutUtil.getVersion());
    if (benchRuns == 0)
    {
      this.sourceTextAreaScroll = createTextAreaScroll("", 5, 80, true);
      this.sourceTextArea = ((JTextArea)this.sourceTextAreaScroll.getViewport().getView());
    }
    this.contentPanel.add(this.layoutPickerTabPane, "spany,grow");
    this.contentPanel.add(this.layoutDisplayPanel, "grow");
    this.contentPanel.add(this.southTabPane, "growx");
    setContentPane(this.contentPanel);
    this.pickerList.setOpaque(false);
    ((DefaultListCellRenderer)this.pickerList.getCellRenderer()).setOpaque(false);
    this.pickerList.setSelectionForeground(new Color(0, 0, 220));
    this.pickerList.setBackground(null);
    this.pickerList.setBorder(new EmptyBorder(2, 5, 0, 4));
    this.pickerList.setFont(this.pickerList.getFont().deriveFont(1));
    this.layoutPickerTabPane.addTab("Example Browser", this.pickerList);
    this.descrTextAreaScroll.setBorder(null);
    this.descrTextAreaScroll.setOpaque(false);
    this.descrTextAreaScroll.getViewport().setOpaque(false);
    this.descrTextArea.setOpaque(false);
    this.descrTextArea.setEditable(false);
    this.descrTextArea.setBorder(new EmptyBorder(0, 4, 0, 4));
    this.southTabPane.addTab("Description", this.descrTextAreaScroll);
    if (this.sourceTextArea != null)
    {
      this.sourceTextAreaScroll.setBorder(null);
      this.sourceTextAreaScroll.setOpaque(false);
      this.sourceTextAreaScroll.getViewport().setOpaque(false);
      this.sourceTextAreaScroll.setHorizontalScrollBarPolicy(30);
      this.sourceTextArea.setOpaque(false);
      this.sourceTextArea.setLineWrap(false);
      this.sourceTextArea.setWrapStyleWord(false);
      this.sourceTextArea.setEditable(false);
      this.sourceTextArea.setBorder(new EmptyBorder(0, 4, 0, 4));
      this.sourceTextArea.setFont(new Font("monospaced", 0, 11));
      this.southTabPane.addTab("Source Code", this.sourceTextAreaScroll);
      this.southTabPane.addMouseListener(new MouseAdapter()
      {
        public void mouseClicked(MouseEvent paramMouseEvent)
        {
          if (paramMouseEvent.getClickCount() == 2)
            SwingDemo.this.showSourceInFrame();
        }
      });
    }
    for (int i = 0; i < panels.length; i++)
      ((DefaultListModel)this.pickerList.getModel()).addElement(panels[i][0]);
    try
    {
      if (UIManager.getLookAndFeel().getID().equals("Aqua"))
        setSize(1000, 750);
      else
        setSize(900, 650);
      setLocationRelativeTo(null);
      setDefaultCloseOperation(3);
      setVisible(true);
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
      System.exit(1);
    }
    this.pickerList.addListSelectionListener(new ListSelectionListener()
    {
      public void valueChanged(ListSelectionEvent paramListSelectionEvent)
      {
        int i = SwingDemo.this.pickerList.getSelectedIndex();
        if ((i == -1) || (SwingDemo.this.lastIndex == i))
          return;
        SwingDemo.access$202(SwingDemo.this, i);
        String str = "create" + SwingDemo.panels[i][0].replace(' ', '_');
        SwingDemo.this.layoutDisplayPanel.removeAll();
        try
        {
          SwingDemo.this.pickerList.requestFocusInWindow();
          JComponent localJComponent = (JComponent)SwingDemo.class.getMethod(str, new Class[0]).invoke(SwingDemo.this, new Object[0]);
          SwingDemo.this.layoutDisplayPanel.add(localJComponent);
          SwingDemo.this.descrTextArea.setText(SwingDemo.panels[i][1]);
          SwingDemo.this.descrTextArea.setCaretPosition(0);
          SwingDemo.this.contentPanel.revalidate();
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
        SwingDemo.this.southTabPane.setSelectedIndex(0);
      }
    });
    this.pickerList.requestFocusInWindow();
    Toolkit.getDefaultToolkit().setDynamicLayout(true);
    if (benchRuns > 0)
    {
      doBenchmark();
    }
    else
    {
      this.pickerList.setSelectedIndex(0);
      KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher()
      {
        public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
        {
          if ((paramKeyEvent.getID() == 401) && (paramKeyEvent.getKeyCode() == 66) && ((paramKeyEvent.getModifiersEx() & 0x80) > 0))
          {
            SwingDemo.access$802(System.currentTimeMillis());
            SwingDemo.access$902(System.currentTimeMillis() - SwingDemo.startupMillis);
            SwingDemo.access$1002(1);
            SwingDemo.this.doBenchmark();
            return true;
          }
          return false;
        }
      });
    }
  }

  private void doBenchmark()
  {
    5 local5 = new Thread()
    {
      public void run()
      {
        for (int i = 0; i < SwingDemo.benchRuns; i++)
        {
          SwingDemo.access$1202(System.currentTimeMillis());
          int j = 0;
          int k = SwingDemo.this.pickerList.getModel().getSize();
          while (j < k)
          {
            if ((SwingDemo.benchRuns <= 0) || (!SwingDemo.panels[j][0].equals("Visual Bounds")))
            {
              int m = j;
              try
              {
                SwingUtilities.invokeAndWait(new Runnable(m)
                {
                  public void run()
                  {
                    SwingDemo.this.pickerList.setSelectedIndex(this.val$ii);
                    Toolkit.getDefaultToolkit().sync();
                  }
                });
              }
              catch (Exception localException1)
              {
                localException1.printStackTrace();
              }
              Component[] arrayOfComponent = SwingDemo.this.layoutDisplayPanel.getComponents();
              for (int n = 0; n < arrayOfComponent.length; n++)
              {
                if (!(arrayOfComponent[n] instanceof JTabbedPane))
                  continue;
                JTabbedPane localJTabbedPane = (JTabbedPane)arrayOfComponent[n];
                int i1 = 0;
                int i2 = localJTabbedPane.getTabCount();
                while (i1 < i2)
                {
                  int i3 = i1;
                  try
                  {
                    SwingUtilities.invokeAndWait(new Runnable(localJTabbedPane, i3)
                    {
                      public void run()
                      {
                        this.val$tp.setSelectedIndex(this.val$kk);
                        Toolkit.getDefaultToolkit().sync();
                        if (SwingDemo.timeToShowMillis == 0L)
                          SwingDemo.access$902(System.currentTimeMillis() - SwingDemo.startupMillis);
                      }
                    });
                  }
                  catch (Exception localException2)
                  {
                    localException2.printStackTrace();
                  }
                  i1++;
                }
              }
            }
            j++;
          }
          if (SwingDemo.runTimeSB == null)
            continue;
          SwingDemo.runTimeSB.append("Run ").append(i).append(": ");
          SwingDemo.runTimeSB.append(System.currentTimeMillis() - SwingDemo.lastRunTimeStart).append(" millis.\n");
        }
        SwingDemo.access$1402(System.currentTimeMillis() - SwingDemo.startupMillis - SwingDemo.timeToShowMillis);
        String str = "Java Version:       " + System.getProperty("java.version") + "\n" + "Look & Feel:        " + UIManager.getLookAndFeel().getDescription() + "\n" + "Time to Show:       " + SwingDemo.timeToShowMillis + " millis.\n" + (SwingDemo.runTimeSB != null ? SwingDemo.runTimeSB.toString() : "") + "Benchmark Run Time: " + SwingDemo.benchRunTime + " millis.\n" + "Average Run Time:   " + SwingDemo.benchRunTime / SwingDemo.benchRuns + " millis (" + SwingDemo.benchRuns + " runs).\n\n";
        if (SwingDemo.benchOutFileName == null)
        {
          JOptionPane.showMessageDialog(SwingDemo.this, str, "Results", 1);
        }
        else
        {
          FileWriter localFileWriter = null;
          try
          {
            localFileWriter = new FileWriter(SwingDemo.benchOutFileName, SwingDemo.append);
            localFileWriter.write(str);
          }
          catch (IOException localIOException3)
          {
            localIOException2.printStackTrace();
          }
          finally
          {
            if (localFileWriter != null)
              try
              {
                localFileWriter.close();
              }
              catch (IOException localIOException4)
              {
              }
          }
        }
        System.out.println(str);
      }
    };
    local5.start();
  }

  private void setSource(String paramString)
  {
    if ((benchRuns > 0) || (this.sourceTextArea == null))
      return;
    if (paramString.length() > 0)
    {
      paramString = paramString.replaceAll("\t\t", "");
      paramString = "DOUBLE CLICK TAB TO SHOW SOURCE IN SEPARATE WINDOW!\n===================================================\n\n" + paramString;
    }
    this.sourceTextArea.setText(paramString);
    this.sourceTextArea.setCaretPosition(0);
    if ((this.sourceFrame != null) && (this.sourceFrame.isVisible()))
    {
      this.sourceFrameTextArea.setText(paramString.length() > 105 ? paramString.substring(105) : "No Source Code Available!");
      this.sourceFrameTextArea.setCaretPosition(0);
    }
  }

  private void showSourceInFrame()
  {
    if (this.sourceTextArea == null)
      return;
    JScrollPane localJScrollPane = createTextAreaScroll("", 5, 80, true);
    this.sourceFrameTextArea = ((JTextArea)localJScrollPane.getViewport().getView());
    localJScrollPane.setBorder(null);
    localJScrollPane.setHorizontalScrollBarPolicy(30);
    this.sourceFrameTextArea.setLineWrap(false);
    this.sourceFrameTextArea.setWrapStyleWord(false);
    this.sourceFrameTextArea.setEditable(false);
    this.sourceFrameTextArea.setBorder(new EmptyBorder(10, 10, 10, 10));
    this.sourceFrameTextArea.setFont(new Font("monospaced", 0, 12));
    String str = this.sourceTextArea.getText();
    this.sourceFrameTextArea.setText(str.length() > 105 ? str.substring(105) : "No Source Code Available!");
    this.sourceFrameTextArea.setCaretPosition(0);
    this.sourceFrame = new JFrame("Source Code");
    this.sourceFrame.getContentPane().add(localJScrollPane, "Center");
    this.sourceFrame.setSize(700, 800);
    this.sourceFrame.setLocationRelativeTo(this);
    this.sourceFrame.setVisible(true);
  }

  public JComponent createTest()
  {
    JPanel localJPanel = new JPanel();
    MigLayout localMigLayout = new MigLayout();
    localJPanel.setLayout(localMigLayout);
    return localJPanel;
  }

  public JComponent createAPI_Constraints1()
  {
    JTabbedPane localJTabbedPane = new JTabbedPane();
    LC localLC = new LC().fill().wrap();
    AC localAC1 = new AC().align("right", new int[] { 1 }).fill(new int[] { 2, 4 }).grow(100.0F, new int[] { 2, 4 }).align("right", new int[] { 3 }).gap("15", new int[] { 2 });
    AC localAC2 = new AC().align("top", new int[] { 7 }).gap("15!", new int[] { 6 }).grow(100.0F, new int[] { 8 });
    JPanel localJPanel = createTabPanel(new MigLayout(localLC, localAC1, localAC2));
    JScrollPane localJScrollPane = new JScrollPane(new JList(new String[] { "Mouse, Mickey" }));
    localJPanel.add(localJScrollPane, new CC().spanY().growY().minWidth("150").gapX(null, "10"));
    localJPanel.add(new JLabel("Last Name"));
    localJPanel.add(new JTextField());
    localJPanel.add(new JLabel("First Name"));
    localJPanel.add(new JTextField(), new CC().wrap().alignX("right"));
    localJPanel.add(new JLabel("Phone"));
    localJPanel.add(new JTextField());
    localJPanel.add(new JLabel("Email"));
    localJPanel.add(new JTextField());
    localJPanel.add(new JLabel("Address 1"));
    localJPanel.add(new JTextField(), new CC().spanX().growX());
    localJPanel.add(new JLabel("Address 2"));
    localJPanel.add(new JTextField(), new CC().spanX().growX());
    localJPanel.add(new JLabel("City"));
    localJPanel.add(new JTextField(), new CC().wrap());
    localJPanel.add(new JLabel("State"));
    localJPanel.add(new JTextField());
    localJPanel.add(new JLabel("Postal Code"));
    localJPanel.add(new JTextField(10), new CC().spanX(2).growX(0.0F));
    localJPanel.add(new JLabel("Country"));
    localJPanel.add(new JTextField(), new CC().wrap());
    localJPanel.add(new JButton("New"), new CC().spanX(5).split(5).tag("other"));
    localJPanel.add(new JButton("Delete"), new CC().tag("other"));
    localJPanel.add(new JButton("Edit"), new CC().tag("other"));
    localJPanel.add(new JButton("Save"), new CC().tag("other"));
    localJPanel.add(new JButton("Cancel"), new CC().tag("cancel"));
    localJTabbedPane.addTab("Layout Showdown (improved)", localJPanel);
    setSource("JTabbedPane tabbedPane = new JTabbedPane();\n\nLC layC = new LC().fill().wrap();\nAC colC = new AC().align(\"right\", 1).fill(2, 4).grow(100, 2, 4).align(\"right\", 3).gap(\"15\", 2);\nAC rowC = new AC().align(\"top\", 7).gap(\"15!\", 6).grow(100, 8);\n\nJPanel panel = createTabPanel(new MigLayout(layC, colC, rowC));    // Makes the background gradient\n\n// References to text fields not stored to reduce code clutter.\n\nJScrollPane list2 = new JScrollPane(new JList(new String[] {\"Mouse, Mickey\"}));\npanel.add(list2,                     new CC().spanY().growY().minWidth(\"150\").gapX(null, \"10\"));\n\npanel.add(new JLabel(\"Last Name\"));\npanel.add(new JTextField());\npanel.add(new JLabel(\"First Name\"));\npanel.add(new JTextField(),          new CC().wrap().alignX(\"right\"));\npanel.add(new JLabel(\"Phone\"));\npanel.add(new JTextField());\npanel.add(new JLabel(\"Email\"));\npanel.add(new JTextField());\npanel.add(new JLabel(\"Address 1\"));\npanel.add(new JTextField(),          new CC().spanX().growX());\npanel.add(new JLabel(\"Address 2\"));\npanel.add(new JTextField(),          new CC().spanX().growX());\npanel.add(new JLabel(\"City\"));\npanel.add(new JTextField(),          new CC().wrap());\npanel.add(new JLabel(\"State\"));\npanel.add(new JTextField());\npanel.add(new JLabel(\"Postal Code\"));\npanel.add(new JTextField(10),        new CC().spanX(2).growX(0));\npanel.add(new JLabel(\"Country\"));\npanel.add(new JTextField(),          new CC().wrap());\n\npanel.add(new JButton(\"New\"),        new CC().spanX(5).split(5).tag(\"other\"));\npanel.add(new JButton(\"Delete\"),     new CC().tag(\"other\"));\npanel.add(new JButton(\"Edit\"),       new CC().tag(\"other\"));\npanel.add(new JButton(\"Save\"),       new CC().tag(\"other\"));\npanel.add(new JButton(\"Cancel\"),     new CC().tag(\"cancel\"));\n\ntabbedPane.addTab(\"Layout Showdown (improved)\", panel);");
    return localJTabbedPane;
  }

  public JComponent createAPI_Constraints2()
  {
    JTabbedPane localJTabbedPane = new JTabbedPane();
    LC localLC = new LC().fill().wrap();
    AC localAC1 = new AC().align("right", new int[] { 0 }).fill(new int[] { 1, 3 }).grow(100.0F, new int[] { 1, 3 }).align("right", new int[] { 2 }).gap("15", new int[] { 1 });
    AC localAC2 = new AC().index(6).gap("15!").align("top").grow(100.0F, new int[] { 8 });
    JPanel localJPanel = createTabPanel(new MigLayout(localLC, localAC1, localAC2));
    localJPanel.add(new JLabel("Last Name"));
    localJPanel.add(new JTextField());
    localJPanel.add(new JLabel("First Name"));
    localJPanel.add(new JTextField(), new CC().wrap());
    localJPanel.add(new JLabel("Phone"));
    localJPanel.add(new JTextField());
    localJPanel.add(new JLabel("Email"));
    localJPanel.add(new JTextField());
    localJPanel.add(new JLabel("Address 1"));
    localJPanel.add(new JTextField(), new CC().spanX().growX());
    localJPanel.add(new JLabel("Address 2"));
    localJPanel.add(new JTextField(), new CC().spanX().growX());
    localJPanel.add(new JLabel("City"));
    localJPanel.add(new JTextField(), new CC().wrap());
    localJPanel.add(new JLabel("State"));
    localJPanel.add(new JTextField());
    localJPanel.add(new JLabel("Postal Code"));
    localJPanel.add(new JTextField(10), new CC().spanX(2).growX(0.0F));
    localJPanel.add(new JLabel("Country"));
    localJPanel.add(new JTextField(), new CC().wrap());
    localJPanel.add(createButton("New"), new CC().spanX(5).split(5).tag("other"));
    localJPanel.add(createButton("Delete"), new CC().tag("other"));
    localJPanel.add(createButton("Edit"), new CC().tag("other"));
    localJPanel.add(createButton("Save"), new CC().tag("other"));
    localJPanel.add(createButton("Cancel"), new CC().tag("cancel"));
    localJTabbedPane.addTab("Layout Showdown (improved)", localJPanel);
    setSource("JTabbedPane tabbedPane = new JTabbedPane();\n\nLC layC = new LC().fill().wrap();\nAC colC = new AC().align(\"right\", 0).fill(1, 3).grow(100, 1, 3).align(\"right\", 2).gap(\"15\", 1);\nAC rowC = new AC().index(6).gap(\"15!\").align(\"top\").grow(100, 8);\n\nJPanel panel = createTabPanel(new MigLayout(layC, colC, rowC));    // Makes the background gradient\n\n// References to text fields not stored to reduce code clutter.\n\npanel.add(new JLabel(\"Last Name\"));\npanel.add(new JTextField());\npanel.add(new JLabel(\"First Name\"));\npanel.add(new JTextField(),          new CC().wrap());\npanel.add(new JLabel(\"Phone\"));\npanel.add(new JTextField());\npanel.add(new JLabel(\"Email\"));\npanel.add(new JTextField());\npanel.add(new JLabel(\"Address 1\"));\npanel.add(new JTextField(),          new CC().spanX().growX());\npanel.add(new JLabel(\"Address 2\"));\npanel.add(new JTextField(),          new CC().spanX().growX());\npanel.add(new JLabel(\"City\"));\npanel.add(new JTextField(),          new CC().wrap());\npanel.add(new JLabel(\"State\"));\npanel.add(new JTextField());\npanel.add(new JLabel(\"Postal Code\"));\npanel.add(new JTextField(10),        new CC().spanX(2).growX(0));\npanel.add(new JLabel(\"Country\"));\npanel.add(new JTextField(),          new CC().wrap());\n\npanel.add(createButton(\"New\"),        new CC().spanX(5).split(5).tag(\"other\"));\npanel.add(createButton(\"Delete\"),     new CC().tag(\"other\"));\npanel.add(createButton(\"Edit\"),       new CC().tag(\"other\"));\npanel.add(createButton(\"Save\"),       new CC().tag(\"other\"));\npanel.add(createButton(\"Cancel\"),     new CC().tag(\"cancel\"));\n\ntabbedPane.addTab(\"Layout Showdown (improved)\", panel);");
    return localJTabbedPane;
  }

  public JComponent createLayout_Showdown()
  {
    JTabbedPane localJTabbedPane = new JTabbedPane();
    JPanel localJPanel1 = createTabPanel(new MigLayout("", "[]15[][grow,fill]15[grow]"));
    JScrollPane localJScrollPane1 = new JScrollPane(new JList(new String[] { "Mouse, Mickey" }));
    localJPanel1.add(localJScrollPane1, "spany, growy, wmin 150");
    localJPanel1.add(new JLabel("Last Name"));
    localJPanel1.add(new JTextField());
    localJPanel1.add(new JLabel("First Name"), "split");
    localJPanel1.add(new JTextField(), "growx, wrap");
    localJPanel1.add(new JLabel("Phone"));
    localJPanel1.add(new JTextField());
    localJPanel1.add(new JLabel("Email"), "split");
    localJPanel1.add(new JTextField(), "growx, wrap");
    localJPanel1.add(new JLabel("Address 1"));
    localJPanel1.add(new JTextField(), "span, growx");
    localJPanel1.add(new JLabel("Address 2"));
    localJPanel1.add(new JTextField(), "span, growx");
    localJPanel1.add(new JLabel("City"));
    localJPanel1.add(new JTextField(), "wrap");
    localJPanel1.add(new JLabel("State"));
    localJPanel1.add(new JTextField());
    localJPanel1.add(new JLabel("Postal Code"), "split");
    localJPanel1.add(new JTextField(), "growx, wrap");
    localJPanel1.add(new JLabel("Country"));
    localJPanel1.add(new JTextField(), "wrap 15");
    localJPanel1.add(createButton("New"), "span, split, align left");
    localJPanel1.add(createButton("Delete"), "");
    localJPanel1.add(createButton("Edit"), "");
    localJPanel1.add(createButton("Save"), "");
    localJPanel1.add(createButton("Cancel"), "wrap push");
    localJTabbedPane.addTab("Layout Showdown (pure)", localJPanel1);
    JPanel localJPanel2 = createTabPanel(new MigLayout("", "[]15[][grow,fill]15[][grow,fill]"));
    JScrollPane localJScrollPane2 = new JScrollPane(new JList(new String[] { "Mouse, Mickey" }));
    localJPanel2.add(localJScrollPane2, "spany, growy, wmin 150");
    localJPanel2.add(new JLabel("Last Name"));
    localJPanel2.add(new JTextField());
    localJPanel2.add(new JLabel("First Name"));
    localJPanel2.add(new JTextField(), "wrap");
    localJPanel2.add(new JLabel("Phone"));
    localJPanel2.add(new JTextField());
    localJPanel2.add(new JLabel("Email"));
    localJPanel2.add(new JTextField(), "wrap");
    localJPanel2.add(new JLabel("Address 1"));
    localJPanel2.add(new JTextField(), "span");
    localJPanel2.add(new JLabel("Address 2"));
    localJPanel2.add(new JTextField(), "span");
    localJPanel2.add(new JLabel("City"));
    localJPanel2.add(new JTextField(), "wrap");
    localJPanel2.add(new JLabel("State"));
    localJPanel2.add(new JTextField());
    localJPanel2.add(new JLabel("Postal Code"));
    localJPanel2.add(new JTextField(10), "growx 0, wrap");
    localJPanel2.add(new JLabel("Country"));
    localJPanel2.add(new JTextField(), "wrap 15");
    localJPanel2.add(createButton("New"), "tag other, span, split");
    localJPanel2.add(createButton("Delete"), "tag other");
    localJPanel2.add(createButton("Edit"), "tag other");
    localJPanel2.add(createButton("Save"), "tag other");
    localJPanel2.add(createButton("Cancel"), "tag cancel, wrap push");
    localJTabbedPane.addTab("Layout Showdown (improved)", localJPanel2);
    setSource("JTabbedPane tabbedPane = new JTabbedPane();\n\nJPanel p = createTabPanel(new MigLayout(\"\", \"[]15[][grow,fill]15[grow]\"));\n\nJScrollPane list1 = new JScrollPane(new JList(new String[] {\"Mouse, Mickey\"}));\n\np.add(list1,                     \"spany, growy, wmin 150\");\np.add(new JLabel(\"Last Name\"));\np.add(new JTextField());\np.add(new JLabel(\"First Name\"),  \"split\");  // split divides the cell\np.add(new JTextField(),          \"growx, wrap\");\np.add(new JLabel(\"Phone\"));\np.add(new JTextField());\np.add(new JLabel(\"Email\"),       \"split\");\np.add(new JTextField(),          \"growx, wrap\");\np.add(new JLabel(\"Address 1\"));\np.add(new JTextField(),          \"span, growx\"); // span merges cells\np.add(new JLabel(\"Address 2\"));\np.add(new JTextField(),          \"span, growx\");\np.add(new JLabel(\"City\"));\np.add(new JTextField(),          \"wrap\"); // wrap continues on next line\np.add(new JLabel(\"State\"));\np.add(new JTextField());\np.add(new JLabel(\"Postal Code\"), \"split\");\np.add(new JTextField(),          \"growx, wrap\");\np.add(new JLabel(\"Country\"));\np.add(new JTextField(),          \"wrap 15\");\n\np.add(createButton(\"New\"),        \"span, split, align left\");\np.add(createButton(\"Delete\"),     \"\");\np.add(createButton(\"Edit\"),       \"\");\np.add(createButton(\"Save\"),       \"\");\np.add(createButton(\"Cancel\"),     \"wrap push\");\n\ntabbedPane.addTab(\"Layout Showdown (pure)\", p);\n\t\t// Fixed version *******************************************\nJPanel p2 = createTabPanel(new MigLayout(\"\", \"[]15[][grow,fill]15[][grow,fill]\"));    // Makes the background gradient\n\n// References to text fields not stored to reduce code clutter.\n\nJScrollPane list2 = new JScrollPane(new JList(new String[] {\"Mouse, Mickey\"}));\np2.add(list2,                     \"spany, growy, wmin 150\");\np2.add(new JLabel(\"Last Name\"));\np2.add(new JTextField());\np2.add(new JLabel(\"First Name\"));\np2.add(new JTextField(),          \"wrap\");\np2.add(new JLabel(\"Phone\"));\np2.add(new JTextField());\np2.add(new JLabel(\"Email\"));\np2.add(new JTextField(),          \"wrap\");\np2.add(new JLabel(\"Address 1\"));\np2.add(new JTextField(),          \"span\");\np2.add(new JLabel(\"Address 2\"));\np2.add(new JTextField(),          \"span\");\np2.add(new JLabel(\"City\"));\np2.add(new JTextField(),          \"wrap\");\np2.add(new JLabel(\"State\"));\np2.add(new JTextField());\np2.add(new JLabel(\"Postal Code\"));\np2.add(new JTextField(10),        \"growx 0, wrap\");\np2.add(new JLabel(\"Country\"));\np2.add(new JTextField(),          \"wrap 15\");\n\np2.add(createButton(\"New\"),        \"tag other, span, split\");\np2.add(createButton(\"Delete\"),     \"tag other\");\np2.add(createButton(\"Edit\"),       \"tag other\");\np2.add(createButton(\"Save\"),       \"tag other\");\np2.add(createButton(\"Cancel\"),     \"tag cancel, wrap push\");\n\ntabbedPane.addTab(\"Layout Showdown (improved)\", p2);");
    return localJTabbedPane;
  }

  public JComponent createWelcome()
  {
    JTabbedPane localJTabbedPane = new JTabbedPane();
    MigLayout localMigLayout = new MigLayout("ins 20, fill", "", "[grow]unrel[]");
    JPanel localJPanel = createTabPanel(localMigLayout);
    String str = "MigLayout's main purpose is to make layouts for SWT and Swing, and possibly other frameworks, much more powerful and a lot easier to create, especially for manual coding.\n\nThe motto is: \"MigLayout makes complex layouts easy and normal layouts one-liners.\"\n\nThe layout engine is very flexible and advanced, something that is needed to make it simple to use yet handle almost all layout use-cases.\n\nMigLayout can handle all layouts that the commonly used Swing Layout Managers can handle and this with a lot of extra features. It also incorporates most, if not all, of the open source alternatives FormLayout's and TableLayout's functionality.\n\n\nThanks to Karsten Lentzsch of JGoodies.com for allowing the reuse of the main demo application layout and for his inspiring talks that led to this layout Manager.\n\n\nMikael Grev\nMiG InfoCom AB\nmiglayout@miginfocom.com";
    JTextArea localJTextArea = new JTextArea(str);
    localJTextArea.setEditable(false);
    localJTextArea.setSize(400, 400);
    if (PlatformDefaults.getCurrentPlatform() == 0)
      localJTextArea.setFont(new Font("tahoma", 1, 11));
    localJTextArea.setOpaque(false);
    localJTextArea.setWrapStyleWord(true);
    localJTextArea.setLineWrap(true);
    JLabel localJLabel = new JLabel("You can Right Click any Component or Container to change the constraints for it!");
    localJLabel.setForeground(new Color(200, 0, 0));
    localJPanel.add(localJTextArea, "wmin 500, ay top, grow, push, wrap");
    localJPanel.add(localJLabel, "growx");
    localJLabel.setFont(localJLabel.getFont().deriveFont(1));
    localJTabbedPane.addTab("Welcome", localJPanel);
    setSource("");
    return localJTabbedPane;
  }

  public JComponent createVisual_Bounds()
  {
    JTabbedPane localJTabbedPane1 = new JTabbedPane();
    JPanel localJPanel1 = createTabPanel(new MigLayout("fill, ins 3, novisualpadding"));
    localJPanel1.setBorder(new LineBorder(Color.BLACK));
    JTabbedPane localJTabbedPane2 = new JTabbedPane();
    JPanel localJPanel2 = new JPanel();
    localJPanel2.setBackground(Color.WHITE);
    localJTabbedPane2.addTab("Demo Tab", localJPanel2);
    localJPanel1.add(createTextArea("A JTextArea", 1, 100), "grow, aligny bottom, wmin 100");
    localJPanel1.add(localJTabbedPane2, "grow, aligny bottom");
    localJPanel1.add(createTextField("A JTextField", 100), "grow, aligny bottom, wmin 100");
    localJPanel1.add(createTextArea("A JTextArea", 1, 100), "newline,grow, aligny bottom, wmin 100");
    localJPanel1.add(createTextArea("A JTextArea", 1, 100), "grow, aligny bottom, wmin 100");
    localJPanel1.add(createTextArea("A JTextArea", 1, 100), "grow, aligny bottom, wmin 100");
    JPanel localJPanel3 = createTabPanel(new MigLayout("center,center,fill,ins 3"));
    localJPanel3.setBorder(new LineBorder(Color.BLACK));
    JTabbedPane localJTabbedPane3 = new JTabbedPane();
    JPanel localJPanel4 = new JPanel();
    localJPanel4.setBackground(Color.WHITE);
    localJTabbedPane3.addTab("Demo Tab", localJPanel4);
    localJPanel3.add(createTextArea("A JTextArea", 1, 100), "grow, aligny bottom, wmin 100");
    localJPanel3.add(localJTabbedPane3, "grow, aligny bottom");
    localJPanel3.add(createTextField("A JTextField", 100), "grow, aligny bottom, wmin 100");
    localJPanel3.add(createTextArea("A JTextArea", 1, 100), "newline,grow, aligny bottom, wmin 100");
    localJPanel3.add(createTextArea("A JTextArea", 1, 100), "grow, aligny bottom, wmin 100");
    localJPanel3.add(createTextArea("A JTextArea", 1, 100), "grow, aligny bottom, wmin 100");
    localJTabbedPane1.addTab("Visual Bounds (Not Corrected)", localJPanel1);
    localJTabbedPane1.addTab("Visual Bounds (Corrected on XP)", localJPanel3);
    setSource("JTabbedPane tabbedPane = new JTabbedPane();\n\n// \"NON\"-corrected bounds\nJPanel p1 = createTabPanel(new MigLayout(\"fill, ins 3, novisualpadding\"));\np1.setBorder(new LineBorder(Color.BLACK));\n\nJTabbedPane demoPane2 = new JTabbedPane();\nJPanel demoPanel2 = new JPanel();\ndemoPanel2.setBackground(Color.WHITE);\ndemoPane2.addTab(\"Demo Tab\", demoPanel2);\n\np1.add(createTextArea(\"A JTextArea\", 1, 100), \"grow, aligny bottom, wmin 100\");\np1.add(demoPane2, \"grow, aligny bottom\");\np1.add(createTextField(\"A JTextField\", 100), \"grow, aligny bottom, wmin 100\");\np1.add(createTextArea(\"A JTextArea\", 1, 100), \"newline,grow, aligny bottom, wmin 100\");\np1.add(createTextArea(\"A JTextArea\", 1, 100), \"grow, aligny bottom, wmin 100\");\np1.add(createTextArea(\"A JTextArea\", 1, 100), \"grow, aligny bottom, wmin 100\");\n\nJPanel p2 = createTabPanel(new MigLayout(\"center,center,fill,ins 3\"));\np2.setBorder(new LineBorder(Color.BLACK));\n\nJTabbedPane demoPane = new JTabbedPane();\nJPanel demoPanel = new JPanel();\ndemoPanel.setBackground(Color.WHITE);\ndemoPane.addTab(\"Demo Tab\", demoPanel);\n\np2.add(createTextArea(\"A JTextArea\", 1, 100), \"grow, aligny bottom, wmin 100\");\np2.add(demoPane, \"grow, aligny bottom\");\np2.add(createTextField(\"A JTextField\", 100), \"grow, aligny bottom, wmin 100\");\np2.add(createTextArea(\"A JTextArea\", 1, 100), \"newline,grow, aligny bottom, wmin 100\");\np2.add(createTextArea(\"A JTextArea\", 1, 100), \"grow, aligny bottom, wmin 100\");\np2.add(createTextArea(\"A JTextArea\", 1, 100), \"grow, aligny bottom, wmin 100\");\n\ntabbedPane.addTab(\"Visual Bounds (Not Corrected)\", p1);\ntabbedPane.addTab(\"Visual Bounds (Corrected on XP)\", p2);");
    return localJTabbedPane1;
  }

  public JComponent createDocking()
  {
    JTabbedPane localJTabbedPane = new JTabbedPane();
    JPanel localJPanel1 = createTabPanel(new MigLayout("fill"));
    localJPanel1.add(createPanel("1. North"), "north");
    localJPanel1.add(createPanel("2. West"), "west");
    localJPanel1.add(createPanel("3. East"), "east");
    localJPanel1.add(createPanel("4. South"), "south");
    String[][] arrayOfString = new String[20][6];
    for (int i = 0; i < arrayOfString.length; i++)
    {
      arrayOfString[i] = new String[6];
      for (int j = 0; j < arrayOfString[i].length; j++)
        arrayOfString[i][j] = ("Cell " + (i + 1) + ", " + (j + 1));
    }
    JTable localJTable = new JTable(arrayOfString, new String[] { "Column 1", "Column 2", "Column 3", "Column 4", "Column 5", "Column 6" });
    localJPanel1.add(new JScrollPane(localJTable), "grow");
    JPanel localJPanel2 = createTabPanel(new MigLayout("fill", "[c]", ""));
    localJPanel2.add(createPanel("1. North"), "north");
    localJPanel2.add(createPanel("2. North"), "north");
    localJPanel2.add(createPanel("3. West"), "west");
    localJPanel2.add(createPanel("4. West"), "west");
    localJPanel2.add(createPanel("5. South"), "south");
    localJPanel2.add(createPanel("6. East"), "east");
    localJPanel2.add(createButton("7. Normal"));
    localJPanel2.add(createButton("8. Normal"));
    localJPanel2.add(createButton("9. Normal"));
    JPanel localJPanel3 = createTabPanel(new MigLayout());
    localJPanel3.add(createPanel("1. North"), "north");
    localJPanel3.add(createPanel("2. South"), "south");
    localJPanel3.add(createPanel("3. West"), "west");
    localJPanel3.add(createPanel("4. East"), "east");
    localJPanel3.add(createButton("5. Normal"));
    JPanel localJPanel4 = createTabPanel(new MigLayout());
    localJPanel4.add(createPanel("1. North"), "north");
    localJPanel4.add(createPanel("2. North"), "north");
    localJPanel4.add(createPanel("3. West"), "west");
    localJPanel4.add(createPanel("4. West"), "west");
    localJPanel4.add(createPanel("5. South"), "south");
    localJPanel4.add(createPanel("6. East"), "east");
    localJPanel4.add(createButton("7. Normal"));
    localJPanel4.add(createButton("8. Normal"));
    localJPanel4.add(createButton("9. Normal"));
    JPanel localJPanel5 = createTabPanel(new MigLayout("fillx", "[c]", ""));
    localJPanel5.add(createPanel("1. North"), "north");
    localJPanel5.add(createPanel("2. North"), "north");
    localJPanel5.add(createPanel("3. West"), "west");
    localJPanel5.add(createPanel("4. West"), "west");
    localJPanel5.add(createPanel("5. South"), "south");
    localJPanel5.add(createPanel("6. East"), "east");
    localJPanel5.add(createButton("7. Normal"));
    localJPanel5.add(createButton("8. Normal"));
    localJPanel5.add(createButton("9. Normal"));
    JPanel localJPanel6 = createTabPanel(new MigLayout("fill", "", ""));
    Random localRandom = new Random();
    String[] arrayOfString1 = { "north", "east", "south", "west" };
    for (int k = 0; k < 20; k++)
    {
      int m = localRandom.nextInt(4);
      localJPanel6.add(createPanel(k + 1 + " " + arrayOfString1[m]), arrayOfString1[m]);
    }
    localJPanel6.add(createPanel("I'm in the Center!"), "dock center");
    localJTabbedPane.addTab("Docking 1 (fill)", localJPanel1);
    localJTabbedPane.addTab("Docking 2 (fill)", localJPanel2);
    localJTabbedPane.addTab("Docking 3", localJPanel3);
    localJTabbedPane.addTab("Docking 4", localJPanel4);
    localJTabbedPane.addTab("Docking 5 (fillx)", localJPanel5);
    localJTabbedPane.addTab("Random Docking", new JScrollPane(localJPanel6));
    setSource("JTabbedPane tabbedPane = new JTabbedPane();\n\nJPanel p1 = createTabPanel(new MigLayout(\"fill\"));\n\np1.add(createPanel(\"1. North\"), \"north\");\np1.add(createPanel(\"2. West\"), \"west\");\np1.add(createPanel(\"3. East\"), \"east\");\np1.add(createPanel(\"4. South\"), \"south\");\n\nString[][] data = new String[20][6];\nfor (int r = 0; r < data.length; r++) {\n\tdata[r] = new String[6];\n\tfor (int c = 0; c < data[r].length; c++)\n\t\tdata[r][c] = \"Cell \" + (r + 1) + \", \" + (c + 1);\n}\nJTable table = new JTable(data, new String[] {\"Column 1\", \"Column 2\", \"Column 3\", \"Column 4\", \"Column 5\", \"Column 6\"});\np1.add(new JScrollPane(table), \"grow\");\n\nJPanel p2 = createTabPanel(new MigLayout(\"fill\", \"[c]\", \"\"));\n\np2.add(createPanel(\"1. North\"), \"north\");\np2.add(createPanel(\"2. North\"), \"north\");\np2.add(createPanel(\"3. West\"), \"west\");\np2.add(createPanel(\"4. West\"), \"west\");\np2.add(createPanel(\"5. South\"), \"south\");\np2.add(createPanel(\"6. East\"), \"east\");\np2.add(createButton(\"7. Normal\"));\np2.add(createButton(\"8. Normal\"));\np2.add(createButton(\"9. Normal\"));\n\nJPanel p3 = createTabPanel(new MigLayout());\n\np3.add(createPanel(\"1. North\"), \"north\");\np3.add(createPanel(\"2. South\"), \"south\");\np3.add(createPanel(\"3. West\"), \"west\");\np3.add(createPanel(\"4. East\"), \"east\");\np3.add(createButton(\"5. Normal\"));\n\nJPanel p4 = createTabPanel(new MigLayout());\n\np4.add(createPanel(\"1. North\"), \"north\");\np4.add(createPanel(\"2. North\"), \"north\");\np4.add(createPanel(\"3. West\"), \"west\");\np4.add(createPanel(\"4. West\"), \"west\");\np4.add(createPanel(\"5. South\"), \"south\");\np4.add(createPanel(\"6. East\"), \"east\");\np4.add(createButton(\"7. Normal\"));\np4.add(createButton(\"8. Normal\"));\np4.add(createButton(\"9. Normal\"));\n\nJPanel p5 = createTabPanel(new MigLayout(\"fillx\", \"[c]\", \"\"));\n\np5.add(createPanel(\"1. North\"), \"north\");\np5.add(createPanel(\"2. North\"), \"north\");\np5.add(createPanel(\"3. West\"), \"west\");\np5.add(createPanel(\"4. West\"), \"west\");\np5.add(createPanel(\"5. South\"), \"south\");\np5.add(createPanel(\"6. East\"), \"east\");\np5.add(createButton(\"7. Normal\"));\np5.add(createButton(\"8. Normal\"));\np5.add(createButton(\"9. Normal\"));\n\nJPanel p6 = createTabPanel(new MigLayout(\"fill\", \"\", \"\"));\n\nRandom rand = new Random();\nString[] sides = {\"north\", \"east\", \"south\", \"west\"};\nfor (int i = 0; i < 20; i++) {\n\tint side = rand.nextInt(4);\n\tp6.add(createPanel((i + 1) + \" \" + sides[side]), sides[side]);\n}\np6.add(createButton(\"I'm in the middle!\"), \"grow\");\n\ntabbedPane.addTab(\"Docking 1 (fill)\", p1);\ntabbedPane.addTab(\"Docking 2 (fill)\", p2);\ntabbedPane.addTab(\"Docking 3\", p3);\ntabbedPane.addTab(\"Docking 4\", p4);\ntabbedPane.addTab(\"Docking 5 (fillx)\", p5);\ntabbedPane.addTab(\"Docking Spiral\", new JScrollPane(p6));");
    return localJTabbedPane;
  }

  public JComponent createAbsolute_Position()
  {
    JTabbedPane localJTabbedPane = new JTabbedPane();
    JPanel localJPanel1 = createTabPanel(new MigLayout());
    localJPanel1.add(createButton(), "pos 0.5al 0al");
    localJPanel1.add(createButton(), "pos 1al 0al");
    localJPanel1.add(createButton(), "pos 0.5al 0.5al");
    localJPanel1.add(createButton(), "pos 5in 45lp");
    localJPanel1.add(createButton(), "pos 0.5al 0.5al");
    localJPanel1.add(createButton(), "pos 0.5al 1.0al");
    localJPanel1.add(createButton(), "pos 1al .25al");
    localJPanel1.add(createButton(), "pos visual.x2-pref visual.y2-pref");
    localJPanel1.add(createButton(), "pos 1al -1in");
    localJPanel1.add(createButton(), "pos 100 100");
    localJPanel1.add(createButton(), "pos (10+(20*3lp)) 200");
    localJPanel1.add(createButton("Drag Window! (pos 500-container.xpos 500-container.ypos)"), "pos 500-container.xpos 500-container.ypos");
    JPanel localJPanel2 = createTabPanel(new MigLayout());
    String str = "pos (visual.x+visual.w*0.1) visual.y2-40 (visual.x2-visual.w*0.1) visual.y2";
    JLabel localJLabel = createLabel(str, 0);
    localJLabel.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
    localJLabel.setBackground(new Color(200, 200, 255, benchRuns == 0 ? 70 : 255));
    localJLabel.setOpaque(true);
    localJLabel.setFont(localJLabel.getFont().deriveFont(1));
    localJPanel2.add(localJLabel, str);
    localJPanel2.add(createButton(), "pos 0 0 container.x2 n");
    localJPanel2.add(createButton(), "pos visual.x 40 visual.x2 70");
    localJPanel2.add(createButton(), "pos visual.x 100 visual.x2 p");
    localJPanel2.add(createButton(), "pos 0.1al 0.4al n (visual.y2 - 10)");
    localJPanel2.add(createButton(), "pos 0.9al 0.4al n visual.y2-10");
    localJPanel2.add(createButton(), "pos 0.5al 0.5al, pad 3 0 -3 0");
    localJPanel2.add(createButton(), "pos n n 50% 50%");
    localJPanel2.add(createButton(), "pos 50% 50% n n");
    localJPanel2.add(createButton(), "pos 50% n n 50%");
    localJPanel2.add(createButton(), "pos n 50% 50% n");
    localJTabbedPane.addTab("X Y Positions", localJPanel1);
    localJTabbedPane.addTab("X1 Y1 X2 Y2 Bounds", localJPanel2);
    if (benchRuns == 0)
    {
      JPanel localJPanel3 = createTabPanel(new MigLayout("align c c, ins 0"));
      JButton localJButton = createButton("Press me!!");
      localJPanel3.add(localJButton);
      localJButton.addActionListener(new ActionListener(localJButton, localJPanel3)
      {
        public void actionPerformed(ActionEvent paramActionEvent)
        {
          this.val$butt.setEnabled(false);
          1 local1 = new JPanel(new MigLayout("align c c,fill"))
          {
            public void paint(Graphics paramGraphics)
            {
              paramGraphics.setColor(getBackground());
              paramGraphics.fillRect(0, 0, getWidth(), getHeight());
              super.paint(paramGraphics);
            }
          };
          local1.setOpaque(false);
          SwingDemo.this.configureActiveComponet(local1);
          JLabel localJLabel = SwingDemo.this.createLabel("You don't need a GlassPane to be cool!");
          localJLabel.setFont(localJLabel.getFont().deriveFont(30.0F));
          localJLabel.setForeground(new Color(255, 255, 255, 0));
          local1.add(localJLabel, "align 50% 30%");
          this.val$glassPanel.add(local1, "pos visual.x visual.y visual.x2 visual.y2", 0);
          long l1 = System.nanoTime();
          long l2 = l1 + 500000000L;
          this.val$glassPanel.revalidate();
          Timer localTimer = new Timer(25, null);
          localTimer.addActionListener(new ActionListener(l1, l2, local1, localJLabel, localTimer)
          {
            public void actionPerformed(ActionEvent paramActionEvent)
            {
              long l = System.nanoTime();
              int i = (int)((l - this.val$startTime) / (this.val$endTime - this.val$startTime) * 300.0D);
              if (i < 150)
                this.val$bg.setBackground(new Color(100, 100, 100, i));
              if ((i > 150) && (i < 405))
              {
                this.val$label.setForeground(new Color(255, 255, 255, i - 150));
                this.val$bg.repaint();
              }
              if (i > 405)
                this.val$timer.stop();
            }
          });
          localTimer.start();
        }
      });
      localJTabbedPane.addTab("GlassPane Substitute", localJPanel3);
      addComponentListener(new ComponentAdapter(localJPanel1)
      {
        public void componentMoved(ComponentEvent paramComponentEvent)
        {
          if (this.val$posPanel.isDisplayable())
            this.val$posPanel.revalidate();
          else
            SwingDemo.this.removeComponentListener(this);
        }
      });
    }
    setSource("JTabbedPane tabbedPane = new JTabbedPane();\n\n// Pos tab\nfinal JPanel posPanel = createTabPanel(new MigLayout());\n\nposPanel.add(createButton(), \"pos 0.5al 0al\");\nposPanel.add(createButton(), \"pos 1al 0al\");\nposPanel.add(createButton(), \"pos 0.5al 0.5al\");\nposPanel.add(createButton(), \"pos 5in 45lp\");\nposPanel.add(createButton(), \"pos 0.5al 0.5al\");\nposPanel.add(createButton(), \"pos 0.5al 1.0al\");\nposPanel.add(createButton(), \"pos 1al .25al\");\nposPanel.add(createButton(), \"pos visual.x2-pref visual.y2-pref\");\nposPanel.add(createButton(), \"pos 1al -1in\");\nposPanel.add(createButton(), \"pos 100 100\");\nposPanel.add(createButton(), \"pos (10+(20*3lp)) 200\");\nposPanel.add(createButton(\"Drag Window! (pos 500-container.xpos 500-container.ypos)\"),\n                            \"pos 500-container.xpos 500-container.ypos\");\n\n// Bounds tab\nJPanel boundsPanel = createTabPanel(new MigLayout());\n\nString constr = \"pos (visual.x+visual.w*0.1) visual.y2-40 (visual.x2-visual.w*0.1) visual.y2\";\nJLabel southLabel = createLabel(constr, SwingConstants.CENTER);\nsouthLabel.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));\nsouthLabel.setBackground(new Color(200, 200, 255, 70));\nsouthLabel.setOpaque(true);\nsouthLabel.setFont(southLabel.getFont().deriveFont(Font.BOLD));\nboundsPanel.add(southLabel, constr);\n\nboundsPanel.add(createButton(), \"pos 0 0 container.x2 n\");\nboundsPanel.add(createButton(), \"pos visual.x 40 visual.x2 70\");\nboundsPanel.add(createButton(), \"pos visual.x 100 visual.x2 p\");\nboundsPanel.add(createButton(), \"pos 0.1al 0.4al n visual.y2-10\");\nboundsPanel.add(createButton(), \"pos 0.9al 0.4al n visual.y2-10\");\nboundsPanel.add(createButton(), \"pos 0.5al 0.5al, pad 3 0 -3 0\");\nboundsPanel.add(createButton(), \"pos n n 50% 50%\");\nboundsPanel.add(createButton(), \"pos 50% 50% n n\");\nboundsPanel.add(createButton(), \"pos 50% n n 50%\");\nboundsPanel.add(createButton(), \"pos n 50% 50% n\");\n\n// Glass pane tab\nfinal JPanel glassPanel = createTabPanel(new MigLayout(\"align c c, ins 0\"));\nfinal JButton butt = createButton(\"Press me!!\");\nglassPanel.add(butt);\n\nbutt.addActionListener(new ActionListener()\t\t{\n\tpublic void actionPerformed(ActionEvent e)\n\t{\n\t\tbutt.setEnabled(false);\n\t\tfinal JPanel bg = new JPanel(new MigLayout(\"align c c,fill\")) {\n\t\t\tpublic void paint(Graphics g)\n\t\t\t{\n\t\t\t\tg.setColor(getBackground());\n\t\t\t\tg.fillRect(0, 0, getWidth(), getHeight());\n\t\t\t\tsuper.paint(g);\n\t\t\t}\n\t\t};\n\t\tbg.setOpaque(OPAQUE);\n\t\tconfigureActiveComponet(bg);\n\n\t\tfinal JLabel label = createLabel(\"You don't need a GlassPane to be cool!\");\n\t\tlabel.setFont(label.getFont().deriveFont(30f));\n\t\tlabel.setForeground(new Color(255, 255, 255, 0));\n\t\tbg.add(label, \"align 50% 30%\");\n\n\t\tglassPanel.add(bg, \"pos visual.x visual.y visual.x2 visual.y2\", 0);\n\t\tfinal long startTime = System.nanoTime();\n\t\tfinal long endTime = startTime + 500000000L;\n\n\t\tglassPanel.revalidate();\n\n\t\tfinal javax.swing.Timer timer = new Timer(25, null);\n\n\t\ttimer.addActionListener(new ActionListener() {\n\t\t\tpublic void actionPerformed(ActionEvent e)\n\t\t\t{\n\t\t\t\tlong now = System.nanoTime();\n\t\t\t\tint alpha = (int) (((now - startTime) / (double) (endTime - startTime)) * 300);\n\t\t\t\tif (alpha < 150)\n\t\t\t\t\tbg.setBackground(new Color(100, 100, 100, alpha));\n\n\t\t\t\tif (alpha > 150 && alpha < 405) {\n\t\t\t\t\tlabel.setForeground(new Color(255, 255, 255, (alpha - 150)));\n\t\t\t\t\tbg.repaint();\n\t\t\t\t}\n\t\t\t\tif (alpha > 405)\n\t\t\t\t\ttimer.stop();\n\t\t\t}\n\t\t});\n\t\ttimer.start();\n\t}\n});\n\ntabbedPane.addTab(\"X Y Positions\", posPanel);\ntabbedPane.addTab(\"X1 Y1 X2 Y2 Bounds\", boundsPanel);\ntabbedPane.addTab(\"GlassPane Substitute\", glassPanel);\n\naddComponentListener(new ComponentAdapter() {\n\tpublic void componentMoved(ComponentEvent e) {\n\t\tif (posPanel.isDisplayable()) {\n\t\t\tposPanel.revalidate();\n\t\t} else {\n\t\t\tremoveComponentListener(this);\n\t\t}\n\t}\n});");
    return localJTabbedPane;
  }

  public JComponent createComponent_Links()
  {
    JTabbedPane localJTabbedPane = new JTabbedPane();
    JPanel localJPanel1 = createTabPanel(new MigLayout());
    JButton localJButton1 = createButton("Mini");
    localJButton1.setMargin(new Insets(0, 1, 0, 1));
    localJPanel1.add(localJButton1, "pos null ta.y ta.x2 null");
    localJPanel1.add(createTextArea("Components, Please Link to Me!\nMy ID is: 'ta'", 3, 30), "id ta, pos 0.5al 0.5al");
    localJPanel1.add(createButton(), "id b1,pos ta.x2 ta.y2");
    localJPanel1.add(createButton(), "pos b1.x2+rel b1.y visual.x2 null");
    localJPanel1.add(createButton(), "pos ta.x2+rel ta.y visual.x2 null");
    localJPanel1.add(createButton(), "pos null ta.y+(ta.h-pref)/2 ta.x-rel null");
    localJPanel1.add(createButton(), "pos ta.x ta.y2+100 ta.x2 null");
    localJPanel1.add(createCheck("pos (ta.x+indent) (ta.y2+rel)"), "pos (ta.x+indent) (ta.y2+rel)");
    JPanel localJPanel2 = createTabPanel(new MigLayout());
    JButton localJButton2 = createButton("Bounds Externally Set!");
    localJButton2.setBounds(250, 130, 200, 40);
    localJPanel2.add(localJButton2, "id ext, external");
    localJPanel2.add(createButton(), "pos ext.x2 ext.y2");
    localJPanel2.add(createButton(), "pos null null ext.x ext.y");
    JPanel localJPanel3 = createTabPanel(new MigLayout());
    localJPanel3.add(createButton(), "id b1, endgroupx g1, pos 200 200");
    localJPanel3.add(createButton(), "id b2, endgroupx g1, pos (b1.x+2ind) (b1.y2+rel)");
    localJPanel3.add(createButton(), "id b3, endgroupx g1, pos (b1.x+4ind) (b2.y2+rel)");
    localJPanel3.add(createButton(), "id b4, endgroupx g1, pos (b1.x+6ind) (b3.y2+rel)");
    JPanel localJPanel4 = createTabPanel(new MigLayout());
    localJPanel4.add(createButton(), "id grp1.b1, pos n 0.5al 50% n");
    localJPanel4.add(createButton(), "id grp1.b2, pos 50% 0.5al n n");
    localJPanel4.add(createButton(), "id grp1.b3, pos 0.5al n n b1.y");
    localJPanel4.add(createButton(), "id grp1.b4, pos 0.5al b1.y2 n n");
    localJPanel4.add(createButton(), "pos n grp1.y2 grp1.x n");
    localJPanel4.add(createButton(), "pos n n grp1.x grp1.y");
    localJPanel4.add(createButton(), "pos grp1.x2 n n grp1.y");
    localJPanel4.add(createButton(), "pos grp1.x2 grp1.y2");
    JPanel localJPanel5 = new JPanel(null);
    localJPanel5.setBackground(new Color(200, 200, 255));
    localJPanel4.add(localJPanel5, "pos grp1.x grp1.y grp1.x2 grp1.y2");
    localJTabbedPane.addTab("Component Links", localJPanel1);
    localJTabbedPane.addTab("External Components", localJPanel2);
    localJTabbedPane.addTab("End Grouping", localJPanel3);
    localJTabbedPane.addTab("Group Bounds", localJPanel4);
    setSource("JTabbedPane tabbedPane = new JTabbedPane();\n\nJPanel linksPanel = createTabPanel(new MigLayout());\n\n// Links tab\nJButton mini = createButton(\"Mini\");\nmini.setMargin(new Insets(0, 1, 0, 1));\nlinksPanel.add(mini, \"pos null ta.y ta.x2 null\");\nlinksPanel.add(createTextArea(\"Components, Please Link to Me!\\nMy ID is: 'ta'\", 3, 30), \"id ta, pos 0.5al 0.5al\");\nlinksPanel.add(createButton(), \"id b1,pos ta.x2 ta.y2\");\nlinksPanel.add(createButton(), \"pos b1.x2+rel b1.y visual.x2 null\");\nlinksPanel.add(createButton(), \"pos ta.x2+rel ta.y visual.x2 null\");\nlinksPanel.add(createButton(), \"pos null ta.y+(ta.h-pref)/2 ta.x-rel null\");\nlinksPanel.add(createButton(), \"pos ta.x ta.y2+100 ta.x2 null\");\nlinksPanel.add(createCheck(\"pos (ta.x+indent) (ta.y2+rel)\"),\n                           \"pos (ta.x+indent) (ta.y2+rel)\");\n\n// External tab\nJPanel externalPanel = createTabPanel(new MigLayout());\n\nJButton extButt = createButton(\"Bounds Externally Set!\");\nextButt.setBounds(250, 130, 200, 40);\nexternalPanel.add(extButt, \"id ext, external\");\nexternalPanel.add(createButton(), \"pos ext.x2 ext.y2\");\nexternalPanel.add(createButton(), \"pos null null ext.x ext.y\");\n\n// Start/End Group tab\nJPanel egPanel = createTabPanel(new MigLayout());\n\negPanel.add(createButton(), \"id b1, endgroupx g1, pos 200 200\");\negPanel.add(createButton(), \"id b2, endgroupx g1, pos (b1.x+2ind) (b1.y2+rel)\");\negPanel.add(createButton(), \"id b3, endgroupx g1, pos (b1.x+4ind) (b2.y2+rel)\");\negPanel.add(createButton(), \"id b4, endgroupx g1, pos (b1.x+6ind) (b3.y2+rel)\");\n\n// Group Bounds tab\nJPanel gpPanel = createTabPanel(new MigLayout());\n\ngpPanel.add(createButton(), \"id grp1.b1, pos n 0.5al 50% n\");\ngpPanel.add(createButton(), \"id grp1.b2, pos 50% 0.5al n n\");\ngpPanel.add(createButton(), \"id grp1.b3, pos 0.5al n n b1.y\");\ngpPanel.add(createButton(), \"id grp1.b4, pos 0.5al b1.y2 n n\");\n\ngpPanel.add(createButton(), \"pos n grp1.y2 grp1.x n\");\ngpPanel.add(createButton(), \"pos n n grp1.x grp1.y\");\ngpPanel.add(createButton(), \"pos grp1.x2 n n grp1.y\");\ngpPanel.add(createButton(), \"pos grp1.x2 grp1.y2\");\n\nJPanel boundsPanel = new JPanel(null);\nboundsPanel.setBackground(new Color(200, 200, 255));\ngpPanel.add(boundsPanel, \"pos grp1.x grp1.y grp1.x2 grp1.y2\");\n\n\ntabbedPane.addTab(\"Component Links\", linksPanel);\ntabbedPane.addTab(\"External Components\", externalPanel);\ntabbedPane.addTab(\"End Grouping\", egPanel);\ntabbedPane.addTab(\"Group Bounds\", gpPanel);");
    return localJTabbedPane;
  }

  public JComponent createFlow_Direction()
  {
    JTabbedPane localJTabbedPane = new JTabbedPane();
    localJTabbedPane.addTab("Layout: flowx, Cell: flowx", createFlowPanel("", "flowx"));
    localJTabbedPane.addTab("Layout: flowx, Cell: flowy", createFlowPanel("", "flowy"));
    localJTabbedPane.addTab("Layout: flowy, Cell: flowx", createFlowPanel("flowy", "flowx"));
    localJTabbedPane.addTab("Layout: flowy, Cell: flowy", createFlowPanel("flowy", "flowy"));
    setSource("JTabbedPane tabbedPane = new JTabbedPane();\n\ntabbedPane.addTab(\"Layout: flowx, Cell: flowx\", createFlowPanel(\"\", \"flowx\"));\ntabbedPane.addTab(\"Layout: flowx, Cell: flowy\", createFlowPanel(\"\", \"flowy\"));\ntabbedPane.addTab(\"Layout: flowy, Cell: flowx\", createFlowPanel(\"flowy\", \"flowx\"));\ntabbedPane.addTab(\"Layout: flowy, Cell: flowy\", createFlowPanel(\"flowy\", \"flowy\"));\n\tpublic JPanel createFlowPanel(String gridFlow, String cellFlow)\n\t{\nMigLayout lm = new MigLayout(\"center, wrap 3,\" + gridFlow,\n                             \"[110,fill]\",\n                             \"[110,fill]\");\nJPanel panel = createTabPanel(lm);\n\nfor (int i = 0; i < 9; i++) {\n\tJButton b = createButton(\"\" + (i + 1));\n\tb.setFont(b.getFont().deriveFont(20f));\n\tpanel.add(b, cellFlow);\n}\n\nJButton b = createButton(\"5:2\");\nb.setFont(b.getFont().deriveFont(20f));\npanel.add(b, cellFlow + \",cell 1 1\");\n\nreturn panel;\n\t}");
    return localJTabbedPane;
  }

  public JPanel createFlowPanel(String paramString1, String paramString2)
  {
    MigLayout localMigLayout = new MigLayout("center, wrap 3," + paramString1, "[110,fill]", "[110,fill]");
    JPanel localJPanel = createTabPanel(localMigLayout);
    Font localFont = localJPanel.getFont().deriveFont(20.0F);
    for (int i = 0; i < 9; i++)
    {
      JComponent localJComponent2 = createPanel("" + (i + 1));
      localJComponent2.setFont(localFont);
      localJPanel.add(localJComponent2, paramString2);
    }
    JComponent localJComponent1 = createPanel("5:2");
    localJComponent1.setFont(localFont);
    localJPanel.add(localJComponent1, paramString2 + ",cell 1 1");
    return localJPanel;
  }

  public JComponent createDebug()
  {
    return createPlainImpl(true);
  }

  public JComponent createButton_Bars()
  {
    MigLayout localMigLayout = new MigLayout("ins 0 0 15lp 0", "[grow]", "[grow][baseline,nogrid]");
    JPanel localJPanel = new JPanel(localMigLayout);
    JLabel localJLabel = createLabel("");
    localJLabel.setFont(localJLabel.getFont().deriveFont(1));
    JTabbedPane localJTabbedPane = new JTabbedPane();
    JToggleButton localJToggleButton1 = createToggleButton("Windows");
    JToggleButton localJToggleButton2 = createToggleButton("Mac OS X");
    JButton localJButton = createButton("Help");
    if (benchRuns == 0)
    {
      localJToggleButton1.addActionListener(new ActionListener(localJLabel)
      {
        public void actionPerformed(ActionEvent paramActionEvent)
        {
          PlatformDefaults.setPlatform(0);
          this.val$formatLabel.setText("'" + PlatformDefaults.getButtonOrder() + "'");
          ((JPanel)((JFrame)Frame.getFrames()[0]).getContentPane()).revalidate();
        }
      });
      localJToggleButton2.addActionListener(new ActionListener(localJLabel)
      {
        public void actionPerformed(ActionEvent paramActionEvent)
        {
          PlatformDefaults.setPlatform(1);
          this.val$formatLabel.setText(PlatformDefaults.getButtonOrder());
          ((JPanel)((JFrame)Frame.getFrames()[0]).getContentPane()).revalidate();
        }
      });
      localJButton.addActionListener(new ActionListener(localJPanel)
      {
        public void actionPerformed(ActionEvent paramActionEvent)
        {
          JOptionPane.showMessageDialog(this.val$mainPanel, "See JavaDoc for PlatformConverter.getButtonBarOrder(..) for details on the format string.");
        }
      });
    }
    ButtonGroup localButtonGroup = new ButtonGroup();
    localButtonGroup.add(localJToggleButton1);
    localButtonGroup.add(localJToggleButton2);
    if (benchRuns == 0)
      if (PlatformDefaults.getCurrentPlatform() == 1)
        localJToggleButton2.doClick();
      else
        localJToggleButton1.doClick();
    localJTabbedPane.addTab("Buttons", createButtonBarsPanel("help", false));
    localJTabbedPane.addTab("Buttons with Help2", createButtonBarsPanel("help2", false));
    localJTabbedPane.addTab("Buttons (Same width)", createButtonBarsPanel("help", true));
    localJPanel.add(localJTabbedPane, "grow,wrap");
    localJPanel.add(createLabel("Button Order:"));
    localJPanel.add(localJLabel, "growx");
    localJPanel.add(localJToggleButton1);
    localJPanel.add(localJToggleButton2);
    localJPanel.add(localJButton, "gapbefore unrel");
    setSource("MigLayout lm = new MigLayout(\"ins 0 0 15lp 0\",\n                                  \"[grow]\",\n                                  \"[grow][baseline,nogrid,gap unrelated]\");\n\nfinal JPanel mainPanel = new JPanel(lm);\nfinal JLabel formatLabel = createLabel(\"\");\nformatLabel.setFont(formatLabel.getFont().deriveFont(Font.BOLD));\nJTabbedPane tabbedPane = new JTabbedPane();\n\nJToggleButton winButt = createToggleButton(\"Windows\");\n\nwinButt.addActionListener(new ActionListener() {\n\tpublic void actionPerformed(ActionEvent e) {\n\t\tPlatformDefaults.setPlatform(PlatformDefaults.WINDOWS_XP);\n\t\tformatLabel.setText(\"'\" + PlatformDefaults.getButtonOrder() + \"'\");\n\t\tSwingUtilities.updateComponentTreeUI(mainPanel);\n\t}\n});\n\nJToggleButton macButt = createToggleButton(\"Mac OS X\");\nmacButt.addActionListener(new ActionListener() {\n\tpublic void actionPerformed(ActionEvent e) {\n\t\tPlatformDefaults.setPlatform(PlatformDefaults.MAC_OSX);\n\t\tformatLabel.setText(PlatformDefaults.getButtonOrder());\n\t\tSwingUtilities.updateComponentTreeUI(mainPanel);\n\t}\n});\n\nJButton helpButt = createButton(\"Help\");\nhelpButt.addActionListener(new ActionListener() {\n\tpublic void actionPerformed(ActionEvent e) {\n\t\tJOptionPane.showMessageDialog(mainPanel, \"See JavaDoc for PlatformConverter.getButtonBarOrder(..) for details on the format string.\");\n\t}\n});\n\nButtonGroup bg = new ButtonGroup();\nbg.add(winButt);\nbg.add(macButt);\nwinButt.doClick();\n\ntabbedPane.addTab(\"Buttons\", createButtonBarsPanel(\"help\", false));\ntabbedPane.addTab(\"Buttons with Help2\", createButtonBarsPanel(\"help2\", false));\ntabbedPane.addTab(\"Buttons (Same width)\", createButtonBarsPanel(\"help\", true));\n\nmainPanel.add(tabbedPane, \"grow,wrap\");\n\nmainPanel.add(createLabel(\"Button Order:\"));\nmainPanel.add(formatLabel, \"growx\");\nmainPanel.add(winButt);\nmainPanel.add(macButt);\nmainPanel.add(helpButt, \"gapbefore unrel\");");
    return localJPanel;
  }

  private JComponent createButtonBarsPanel(String paramString, boolean paramBoolean)
  {
    MigLayout localMigLayout = new MigLayout("nogrid, fillx, aligny 100%, gapy unrel");
    JPanel localJPanel = createTabPanel(localMigLayout);
    String[][] arrayOfString; = { { "OK" }, { "No", "Yes" }, { "Help", "Close" }, { "OK", "Help" }, { "OK", "Cancel", "Help" }, { "OK", "Cancel", "Apply", "Help" }, { "No", "Yes", "Cancel" }, { "Help", "< Back", "Forward >", "Cancel" }, { "Print...", "Cancel", "Help" } };
    for (int i = 0; i < arrayOfString;.length; i++)
      for (int j = 0; j < arrayOfString;[i].length; j++)
      {
        String str1 = arrayOfString;[i][j];
        String str2 = str1;
        if (str1.equals("Help"))
          str2 = paramString;
        else if (str1.equals("< Back"))
          str2 = "back";
        else if (str1.equals("Close"))
          str2 = "cancel";
        else if (str1.equals("Forward >"))
          str2 = "next";
        else if (str1.equals("Print..."))
          str2 = "other";
        String str3 = j == arrayOfString;[i].length - 1 ? ",wrap" : "";
        String str4 = paramBoolean ? "sgx " + i + "," : "";
        localJPanel.add(createButton(str1), str4 + "tag " + str2 + str3);
      }
    return localJPanel;
  }

  public JComponent createOrientation()
  {
    JTabbedPane localJTabbedPane = new JTabbedPane();
    MigLayout localMigLayout1 = new MigLayout("flowy", "[grow,fill]", "[]20[]20[]20[]");
    JPanel localJPanel1 = createTabPanel(localMigLayout1);
    MigLayout localMigLayout2 = new MigLayout("", "[trailing][grow,fill]", "");
    JPanel localJPanel2 = createTabPanel(localMigLayout2);
    addSeparator(localJPanel2, "Default Orientation");
    localJPanel2.add(createLabel("Level of Trust"));
    localJPanel2.add(createTextField(""), "span,growx");
    localJPanel2.add(createLabel("Radar Presentation"));
    localJPanel2.add(createTextField(""));
    localJPanel2.add(createTextField(""));
    MigLayout localMigLayout3 = new MigLayout("rtl,ttb", "[trailing][grow,fill]", "");
    JPanel localJPanel3 = createTabPanel(localMigLayout3);
    addSeparator(localJPanel3, "Right to Left");
    localJPanel3.add(createLabel("Level of Trust"));
    localJPanel3.add(createTextField(""), "span,growx");
    localJPanel3.add(createLabel("Radar Presentation"));
    localJPanel3.add(createTextField(""));
    localJPanel3.add(createTextField(""));
    MigLayout localMigLayout4 = new MigLayout("rtl,btt", "[trailing][grow,fill]", "");
    JPanel localJPanel4 = createTabPanel(localMigLayout4);
    addSeparator(localJPanel4, "Right to Left, Bottom to Top");
    localJPanel4.add(createLabel("Level of Trust"));
    localJPanel4.add(createTextField(""), "span,growx");
    localJPanel4.add(createLabel("Radar Presentation"));
    localJPanel4.add(createTextField(""));
    localJPanel4.add(createTextField(""));
    MigLayout localMigLayout5 = new MigLayout("ltr,btt", "[trailing][grow,fill]", "");
    JPanel localJPanel5 = createTabPanel(localMigLayout5);
    addSeparator(localJPanel5, "Left to Right, Bottom to Top");
    localJPanel5.add(createLabel("Level of Trust"));
    localJPanel5.add(createTextField(""), "span,growx");
    localJPanel5.add(createLabel("Radar Presentation"));
    localJPanel5.add(createTextField(""));
    localJPanel5.add(createTextField(""));
    localJPanel1.add(localJPanel2);
    localJPanel1.add(localJPanel3);
    localJPanel1.add(localJPanel4);
    localJPanel1.add(localJPanel5);
    localJTabbedPane.addTab("Orientation", localJPanel1);
    setSource("JTabbedPane tabbedPane = new JTabbedPane();\n\nMigLayout lm = new MigLayout(\"flowy\", \"[grow,fill]\", \"[]0[]15lp[]0[]\");\nJPanel mainPanel = createTabPanel(lm);\n\n// Default orientation\nMigLayout defLM = new MigLayout(\"\", \"[][grow,fill]\", \"\");\n\nJPanel defPanel = createTabPanel(defLM);\naddSeparator(defPanel, \"Default Orientation\");\ndefPanel.add(createLabel(\"Level\"));\ndefPanel.add(createTextField(\"\"), \"span,growx\");\ndefPanel.add(createLabel(\"Radar\"));\ndefPanel.add(createTextField(\"\"));\ndefPanel.add(createTextField(\"\"));\n\n// Right-to-left, Top-to-bottom\nMigLayout rtlLM = new MigLayout(\"rtl,ttb\",\n                                \"[][grow,fill]\",\n                                \"\");\nJPanel rtlPanel = createTabPanel(rtlLM);\naddSeparator(rtlPanel, \"Right to Left\");\nrtlPanel.add(createLabel(\"Level\"));\nrtlPanel.add(createTextField(\"\"), \"span,growx\");\nrtlPanel.add(createLabel(\"Radar\"));\nrtlPanel.add(createTextField(\"\"));\nrtlPanel.add(createTextField(\"\"));\n\n// Right-to-left, Bottom-to-top\nMigLayout rtlbLM = new MigLayout(\"rtl,btt\",\n                                      \"[][grow,fill]\",\n                                      \"\");\nJPanel rtlbPanel = createTabPanel(rtlbLM);\naddSeparator(rtlbPanel, \"Right to Left, Bottom to Top\");\nrtlbPanel.add(createLabel(\"Level\"));\nrtlbPanel.add(createTextField(\"\"), \"span,growx\");\nrtlbPanel.add(createLabel(\"Radar\"));\nrtlbPanel.add(createTextField(\"\"));\nrtlbPanel.add(createTextField(\"\"));\n\n// Left-to-right, Bottom-to-top\nMigLayout ltrbLM = new MigLayout(\"ltr,btt\",\n                                      \"[][grow,fill]\",\n                                      \"\");\nJPanel ltrbPanel = createTabPanel(ltrbLM);\naddSeparator(ltrbPanel, \"Left to Right, Bottom to Top\");\nltrbPanel.add(createLabel(\"Level\"));\nltrbPanel.add(createTextField(\"\"), \"span,growx\");\nltrbPanel.add(createLabel(\"Radar\"));\nltrbPanel.add(createTextField(\"\"));\nltrbPanel.add(createTextField(\"\"));\n\nmainPanel.add(defPanel);\nmainPanel.add(rtlPanel);\nmainPanel.add(rtlbPanel);\nmainPanel.add(ltrbPanel);\n\ntabbedPane.addTab(\"Orientation\", mainPanel);");
    return localJTabbedPane;
  }

  public JComponent createCell_Position()
  {
    JTabbedPane localJTabbedPane = new JTabbedPane();
    MigLayout localMigLayout1 = new MigLayout("", "[100:pref,fill]", "[100:pref,fill]");
    JPanel localJPanel1 = createTabPanel(localMigLayout1);
    localJPanel1.add(createButton(), "cell 0 0");
    localJPanel1.add(createButton(), "cell 2 0");
    localJPanel1.add(createButton(), "cell 3 0");
    localJPanel1.add(createButton(), "cell 1 1");
    localJPanel1.add(createButton(), "cell 0 2");
    localJPanel1.add(createButton(), "cell 2 2");
    localJPanel1.add(createButton(), "cell 2 2");
    MigLayout localMigLayout2 = new MigLayout("wrap", "[100:pref,fill][100:pref,fill][100:pref,fill][100:pref,fill]", "[100:pref,fill]");
    JPanel localJPanel2 = createTabPanel(localMigLayout2);
    localJPanel2.add(createButton());
    localJPanel2.add(createButton(), "skip");
    localJPanel2.add(createButton());
    localJPanel2.add(createButton(), "skip,wrap");
    localJPanel2.add(createButton());
    localJPanel2.add(createButton(), "skip,split");
    localJPanel2.add(createButton());
    MigLayout localMigLayout3 = new MigLayout("", "[100:pref,fill]", "[100:pref,fill]");
    JPanel localJPanel3 = createTabPanel(localMigLayout3);
    localJPanel3.add(createButton());
    localJPanel3.add(createButton(), "skip");
    localJPanel3.add(createButton(), "wrap");
    localJPanel3.add(createButton(), "skip,wrap");
    localJPanel3.add(createButton());
    localJPanel3.add(createButton(), "skip,split");
    localJPanel3.add(createButton());
    MigLayout localMigLayout4 = new MigLayout("", "[100:pref,fill]", "[100:pref,fill]");
    JPanel localJPanel4 = createTabPanel(localMigLayout4);
    localJPanel4.add(createButton());
    localJPanel4.add(createButton(), "cell 2 0");
    localJPanel4.add(createButton());
    localJPanel4.add(createButton(), "cell 1 1,wrap");
    localJPanel4.add(createButton());
    localJPanel4.add(createButton(), "cell 2 2,split");
    localJPanel4.add(createButton());
    localJTabbedPane.addTab("Absolute", localJPanel1);
    localJTabbedPane.addTab("Relative + Wrap", localJPanel2);
    localJTabbedPane.addTab("Relative", localJPanel3);
    localJTabbedPane.addTab("Mixed", localJPanel4);
    setSource("\t\tJTabbedPane tabbedPane = new JTabbedPane();\n\n\t\t// Absolute grid position\n\t\tMigLayout absLM = new MigLayout(\"\",\n\t\t                                \"[100:pref,fill]\",\n\t\t                                \"[100:pref,fill]\");\n\t\tJPanel absPanel = createTabPanel(absLM);\n\t\tabsPanel.add(createPanel(), \"cell 0 0\");\n\t\tabsPanel.add(createPanel(), \"cell 2 0\");\n\t\tabsPanel.add(createPanel(), \"cell 3 0\");\n\t\tabsPanel.add(createPanel(), \"cell 1 1\");\n\t\tabsPanel.add(createPanel(), \"cell 0 2\");\n\t\tabsPanel.add(createPanel(), \"cell 2 2\");\n\t\tabsPanel.add(createPanel(), \"cell 2 2\");\n\n\n\t\t// Relative grid position with wrap\n\t\tMigLayout relAwLM = new MigLayout(\"wrap\",\n\t\t                                       \"[100:pref,fill][100:pref,fill][100:pref,fill][100:pref,fill]\",\n\t\t                                       \"[100:pref,fill]\");\n\t\tJPanel relAwPanel = createTabPanel(relAwLM);\n\t\trelAwPanel.add(createPanel());\n\t\trelAwPanel.add(createPanel(), \"skip\");\n\t\trelAwPanel.add(createPanel());\n\t\trelAwPanel.add(createPanel(), \"skip,wrap\");\n\t\trelAwPanel.add(createPanel());\n\t\trelAwPanel.add(createPanel(), \"skip,split\");\n\t\trelAwPanel.add(createPanel());\n\n\n\t\t// Relative grid position with manual wrap\n\t\tMigLayout relWLM = new MigLayout(\"\",\n\t\t                                      \"[100:pref,fill]\",\n\t\t                                      \"[100:pref,fill]\");\n\t\tJPanel relWPanel = createTabPanel(relWLM);\n\t\trelWPanel.add(createPanel());\n\t\trelWPanel.add(createPanel(), \"skip\");\n\t\trelWPanel.add(createPanel(), \"wrap\");\n\t\trelWPanel.add(createPanel(), \"skip,wrap\");\n\t\trelWPanel.add(createPanel());\n\t\trelWPanel.add(createPanel(), \"skip,split\");\n\n\t\trelWPanel.add(createPanel());\n\n\n\t\t// Mixed relative and absolute grid position\n\t\tMigLayout mixLM = new MigLayout(\"\",\n\t\t                                \"[100:pref,fill]\",\n\t\t                                \"[100:pref,fill]\");\n\t\tJPanel mixPanel = createTabPanel(mixLM);\n\t\tmixPanel.add(createPanel());\n\t\tmixPanel.add(createPanel(), \"cell 2 0\");\n\t\tmixPanel.add(createPanel());\n\t\tmixPanel.add(createPanel(), \"cell 1 1,wrap\");\n\t\tmixPanel.add(createPanel());\n\t\tmixPanel.add(createPanel(), \"cell 2 2,split\");\n\t\tmixPanel.add(createPanel());\n\n\t\ttabbedPane.addTab(\"Absolute\", absPanel);\n\t\ttabbedPane.addTab(\"Relative + Wrap\", relAwPanel);\n\t\ttabbedPane.addTab(\"Relative\", relWPanel);\n\t\ttabbedPane.addTab(\"Mixed\", mixPanel);");
    return localJTabbedPane;
  }

  public JComponent createPlain()
  {
    return createPlainImpl(false);
  }

  private JComponent createPlainImpl(boolean paramBoolean)
  {
    JTabbedPane localJTabbedPane = new JTabbedPane();
    MigLayout localMigLayout = new MigLayout((paramBoolean) && (benchRuns == 0) ? "debug, inset 20" : "ins 20", "[para]0[][100lp, fill][60lp][95lp, fill]", "");
    JPanel localJPanel = createTabPanel(localMigLayout);
    addSeparator(localJPanel, "Manufacturer");
    localJPanel.add(createLabel("Company"), "skip");
    localJPanel.add(createTextField(""), "span, growx");
    localJPanel.add(createLabel("Contact"), "skip");
    localJPanel.add(createTextField(""), "span, growx");
    localJPanel.add(createLabel("Order No"), "skip");
    localJPanel.add(createTextField(15), "wrap para");
    addSeparator(localJPanel, "Inspector");
    localJPanel.add(createLabel("Name"), "skip");
    localJPanel.add(createTextField(""), "span, growx");
    localJPanel.add(createLabel("Reference No"), "skip");
    localJPanel.add(createTextField(""), "wrap");
    localJPanel.add(createLabel("Status"), "skip");
    localJPanel.add(createCombo(new String[] { "In Progress", "Finnished", "Released" }), "wrap para");
    addSeparator(localJPanel, "Ship");
    localJPanel.add(createLabel("Shipyard"), "skip");
    localJPanel.add(createTextField(""), "span, growx");
    localJPanel.add(createLabel("Register No"), "skip");
    localJPanel.add(createTextField(""));
    localJPanel.add(createLabel("Hull No"), "right");
    localJPanel.add(createTextField(15), "wrap");
    localJPanel.add(createLabel("Project StructureType"), "skip");
    localJPanel.add(createCombo(new String[] { "New Building", "Convention", "Repair" }));
    if (paramBoolean)
      localJPanel.add(createLabel("Red is cell bounds. Blue is component bounds."), "newline,ax left,span,gaptop 40,");
    localJTabbedPane.addTab("Plain", localJPanel);
    setSource("JTabbedPane tabbedPane = new JTabbedPane();\n\nMigLayout lm = new MigLayout((debug && benchRuns == 0 ? \"debug, inset 20\" : \"ins 20\"), \"[para]0[][100lp, fill][60lp][95lp, fill]\", \"\");\nJPanel panel = createTabPanel(lm);\n\naddSeparator(panel, \"Manufacturer\");\n\npanel.add(createLabel(\"Company\"),   \"skip\");\npanel.add(createTextField(\"\"),      \"span, growx\");\npanel.add(createLabel(\"Contact\"),   \"skip\");\npanel.add(createTextField(\"\"),      \"span, growx\");\npanel.add(createLabel(\"Order No\"),  \"skip\");\npanel.add(createTextField(15),      \"wrap para\");\n\naddSeparator(panel, \"Inspector\");\n\npanel.add(createLabel(\"Name\"),         \"skip\");\npanel.add(createTextField(\"\"),         \"span, growx\");\npanel.add(createLabel(\"Reference No\"), \"skip\");\npanel.add(createTextField(\"\"),         \"wrap\");\npanel.add(createLabel(\"Status\"),       \"skip\");\npanel.add(createCombo(new String[] {\"In Progress\", \"Finnished\", \"Released\"}), \"wrap para\");\n\naddSeparator(panel, \"Ship\");\n\npanel.add(createLabel(\"Shipyard\"),     \"skip\");\npanel.add(createTextField(\"\"),         \"span, growx\");\npanel.add(createLabel(\"Register No\"),  \"skip\");\npanel.add(createTextField(\"\"));\npanel.add(createLabel(\"Hull No\"),      \"right\");\npanel.add(createTextField(15), \"wrap\");\npanel.add(createLabel(\"Project StructureType\"), \"skip\");\npanel.add(createCombo(new String[] {\"New Building\", \"Convention\", \"Repair\"}));\n\nif (debug)\n\tpanel.add(createLabel(\"Red is cell bounds. Blue is component bounds.\"), \"newline,ax left,span,gaptop 40,\");\n\ntabbedPane.addTab(\"Plain\", panel);");
    return localJTabbedPane;
  }

  public JComponent createBound_Sizes()
  {
    JTabbedPane localJTabbedPane = new JTabbedPane();
    for (int i = 0; i < 2; i++)
    {
      String str = i == 0 ? "[right][300]" : "[right, 100lp:pref][300]";
      MigLayout localMigLayout1 = new MigLayout("wrap", str, "");
      JPanel localJPanel1 = createTabPanel(localMigLayout1);
      localJPanel1.add(createLabel("File Number:"));
      localJPanel1.add(createTextField(""), "growx");
      localJPanel1.add(createLabel("RFQ Number:"));
      localJPanel1.add(createTextField(""), "growx");
      localJPanel1.add(createLabel("Entry Date:"));
      localJPanel1.add(createTextField(6));
      localJPanel1.add(createLabel("Sales Person:"));
      localJPanel1.add(createTextField(""), "growx");
      MigLayout localMigLayout2 = new MigLayout("wrap", str, "");
      JPanel localJPanel2 = createTabPanel(localMigLayout2);
      localJPanel2.add(createLabel("Shipper:"));
      localJPanel2.add(createTextField(6), "split 2");
      localJPanel2.add(createTextField(""), "growx");
      localJPanel2.add(createLabel("Consignee:"));
      localJPanel2.add(createTextField(6), "split 2");
      localJPanel2.add(createTextField(""), "growx");
      localJPanel2.add(createLabel("Departure:"));
      localJPanel2.add(createTextField(6), "split 2");
      localJPanel2.add(createTextField(""), "growx");
      localJPanel2.add(createLabel("Destination:"));
      localJPanel2.add(createTextField(6), "split 2");
      localJPanel2.add(createTextField(""), "growx");
      localJTabbedPane.addTab(i == 0 ? "Jumping 1" : "Stable 1", localJPanel1);
      localJTabbedPane.addTab(i == 0 ? "Jumping 2" : "Stable 2", localJPanel2);
    }
    setSource("JTabbedPane tabbedPane = new JTabbedPane();\n\nfor (int i = 0; i < 2; i++) {   // Jumping for 0 and Stable for 1\n\tString colConstr = i == 0 ? \"[right][300]\" : \"[right, 100lp:pref][300]\";\n\n\tMigLayout LM1 = new MigLayout(\"wrap\", colConstr, \"\");\n\tJPanel panel1 = createTabPanel(LM1);\n\tpanel1.add(createLabel(\"File Number:\"));\n\tpanel1.add(createTextField(\"\"), \"growx\");\n\tpanel1.add(createLabel(\"RFQ Number:\"));\n\tpanel1.add(createTextField(\"\"), \"growx\");\n\tpanel1.add(createLabel(\"Entry Date:\"));\n\tpanel1.add(createTextField(6));\n\tpanel1.add(createLabel(\"Sales Person:\"));\n\tpanel1.add(createTextField(\"\"), \"growx\");\n\n\tMigLayout LM2 = new MigLayout(\"wrap\", colConstr, \"\");\n\tJPanel panel2 = createTabPanel(LM2);\n\tpanel2.add(createLabel(\"Shipper:\"));\n\tpanel2.add(createTextField(6), \"split 2\");\n\tpanel2.add(createTextField(\"\"), \"growx\");\n\tpanel2.add(createLabel(\"Consignee:\"));\n\tpanel2.add(createTextField(6), \"split 2\");\n\tpanel2.add(createTextField(\"\"), \"growx\");\n\tpanel2.add(createLabel(\"Departure:\"));\n\tpanel2.add(createTextField(6), \"split 2\");\n\tpanel2.add(createTextField(\"\"), \"growx\");\n\tpanel2.add(createLabel(\"Destination:\"));\n\tpanel2.add(createTextField(6), \"split 2\");\n\tpanel2.add(createTextField(\"\"), \"growx\");\n\n\ttabbedPane.addTab(i == 0 ? \"Jumping 1\" : \"Stable 2\", panel1);\n\ttabbedPane.addTab(i == 0 ? \"Jumping 2\" : \"Stable 2\", panel2);\n}");
    return localJTabbedPane;
  }

  public JComponent createComponent_Sizes()
  {
    JTabbedPane localJTabbedPane = new JTabbedPane();
    MigLayout localMigLayout = new MigLayout("wrap", "[right][0:pref,grow]", "");
    JPanel localJPanel = createTabPanel(localMigLayout);
    JScrollPane localJScrollPane = createTextAreaScroll("Use slider to see how the components grow and shrink depending on the constraints set on them.", 0, 0, false);
    localJScrollPane.setOpaque(false);
    localJScrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));
    ((JTextArea)localJScrollPane.getViewport().getView()).setOpaque(false);
    localJScrollPane.getViewport().setOpaque(false);
    JSplitPane localJSplitPane = new JSplitPane(1, true, localJPanel, localJScrollPane);
    localJSplitPane.setOpaque(false);
    localJSplitPane.setBorder(null);
    localJPanel.add(createLabel("\"\""));
    localJPanel.add(createTextField(""));
    localJPanel.add(createLabel("\"min!\""));
    localJPanel.add(createTextField("3", 3), "width min!");
    localJPanel.add(createLabel("\"pref!\""));
    localJPanel.add(createTextField("3", 3), "width pref!");
    localJPanel.add(createLabel("\"min:pref\""));
    localJPanel.add(createTextField("8", 8), "width min:pref");
    localJPanel.add(createLabel("\"min:100:150\""));
    localJPanel.add(createTextField("8", 8), "width min:100:150");
    localJPanel.add(createLabel("\"min:100:150, growx\""));
    localJPanel.add(createTextField("8", 8), "width min:100:150, growx");
    localJPanel.add(createLabel("\"min:100, growx\""));
    localJPanel.add(createTextField("8", 8), "width min:100, growx");
    localJPanel.add(createLabel("\"40!\""));
    localJPanel.add(createTextField("8", 8), "width 40!");
    localJPanel.add(createLabel("\"40:40:40\""));
    localJPanel.add(createTextField("8", 8), "width 40:40:40");
    localJTabbedPane.addTab("Component Sizes", localJSplitPane);
    setSource("JTabbedPane tabbedPane = new JTabbedPane();\n\n\t\tMigLayout LM = new MigLayout(\"wrap\", \"[right][0:pref,grow]\", \"\");\n\n\t\tJPanel panel = createTabPanel(LM);\n\t\tJScrollPane descrText = createTextAreaScroll(\"Use slider to see how the components grow and shrink depending on the constraints set on them.\", 0, 0, false);\n\n\t\tdescrText.setOpaque(OPAQUE);\n\t\tdescrText.setBorder(new EmptyBorder(10, 10, 10, 10));\n\t\t((JTextArea) descrText.getViewport().getView()).setOpaque(OPAQUE);\n\t\tdescrText.getViewport().setOpaque(OPAQUE);\n\n\t\tJSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, panel, descrText);\n\t\tsplitPane.setOpaque(OPAQUE);\n\t\tsplitPane.setBorder(null);\n\n\t\tpanel.add(createLabel(\"\\\"\\\"\"));\n\t\tpanel.add(createTextField(\"\"));\n\t\tpanel.add(createLabel(\"\\\"min!\\\"\"));\n\t\tpanel.add(createTextField(\"3\", 3), \"width min!\");\n\t\tpanel.add(createLabel(\"\\\"pref!\\\"\"));\n\t\tpanel.add(createTextField(\"3\", 3), \"width pref!\");\n\t\tpanel.add(createLabel(\"\\\"min:pref\\\"\"));\n\t\tpanel.add(createTextField(\"8\", 8), \"width min:pref\");\n\t\tpanel.add(createLabel(\"\\\"min:100:150\\\"\"));\n\t\tpanel.add(createTextField(\"8\", 8), \"width min:100:150\");\n\t\tpanel.add(createLabel(\"\\\"min:100:150, growx\\\"\"));\n\t\tpanel.add(createTextField(\"8\", 8), \"width min:100:150, growx\");\n\t\tpanel.add(createLabel(\"\\\"min:100, growx\\\"\"));\n\t\tpanel.add(createTextField(\"8\", 8), \"width min:100, growx\");\n\t\tpanel.add(createLabel(\"\\\"40!\\\"\"));\n\t\tpanel.add(createTextField(\"8\", 8), \"width 40!\");\n\t\tpanel.add(createLabel(\"\\\"40:40:40\\\"\"));\n\t\tpanel.add(createTextField(\"8\", 8), \"width 40:40:40\");\n\n\t\ttabbedPane.addTab(\"Component Sizes\", splitPane);");
    return localJTabbedPane;
  }

  public JComponent createCell_Alignments()
  {
    JTabbedPane localJTabbedPane = new JTabbedPane();
    MigLayout localMigLayout1 = new MigLayout("wrap", "[grow,left][grow,center][grow,right][grow,fill,center]", "[]unrel[][]");
    JPanel localJPanel1 = createTabPanel(localMigLayout1);
    String[] arrayOfString = { "", "growx", "growx 0", "left", "center", "right", "leading", "trailing" };
    localJPanel1.add(createLabel("[left]"), "c");
    localJPanel1.add(createLabel("[center]"), "c");
    localJPanel1.add(createLabel("[right]"), "c");
    localJPanel1.add(createLabel("[fill,center]"), "c, growx 0");
    for (int i = 0; i < arrayOfString.length; i++)
      for (int j = 0; j < 4; j++)
      {
        localObject = arrayOfString[i].length() > 0 ? arrayOfString[i] : "default";
        localJPanel1.add(createButton((String)localObject), arrayOfString[i]);
      }
    MigLayout localMigLayout2 = new MigLayout("wrap,flowy", "[right][]", "[grow,top][grow,center][grow,bottom][grow,fill,bottom][grow,fill,baseline]");
    JPanel localJPanel2 = createTabPanel(localMigLayout2);
    Object localObject = { "", "growy", "growy 0", "top", "aligny center", "bottom" };
    localJPanel2.add(createLabel("[top]"), "aligny center");
    localJPanel2.add(createLabel("[center]"), "aligny center");
    localJPanel2.add(createLabel("[bottom]"), "aligny center");
    localJPanel2.add(createLabel("[fill, bottom]"), "aligny center, growy 0");
    localJPanel2.add(createLabel("[fill, baseline]"), "aligny center");
    for (int k = 0; k < localObject.length; k++)
      for (int m = 0; m < 5; m++)
      {
        String str = localObject[k].length() > 0 ? localObject[k] : "default";
        JButton localJButton = createButton(str);
        if ((m == 4) && (k <= 1))
          localJButton.setFont(new Font("sansserif", 0, 16 + k * 5));
        localJPanel2.add(localJButton, localObject[k]);
      }
    localJTabbedPane.addTab("Horizontal", localJPanel1);
    localJTabbedPane.addTab("Vertical", localJPanel2);
    setSource("JTabbedPane tabbedPane = new JTabbedPane();\n\n// Horizontal\nMigLayout hLM = new MigLayout(\"wrap\",\n                              \"[grow,left][grow,center][grow,right][grow,fill,center]\",\n                              \"[]unrel[][]\");\nJPanel hPanel = createTabPanel(hLM);\nString[] sizes = new String[] {\"\", \"growx\", \"growx 0\", \"left\", \"center\", \"right\", \"leading\", \"trailing\"};\nhPanel.add(createLabel(\"[left]\"), \"c\");\nhPanel.add(createLabel(\"[center]\"), \"c\");\nhPanel.add(createLabel(\"[right]\"), \"c\");\nhPanel.add(createLabel(\"[fill,center]\"), \"c, growx 0\");\n\nfor (int r = 0; r < sizes.length; r++) {\n\tfor (int c = 0; c < 4; c++) {\n\t\tString text = sizes[r].length() > 0 ? sizes[r] : \"default\";\n\t\thPanel.add(createButton(text), sizes[r]);\n\t}\n}\n\n// Vertical\nMigLayout vLM = new MigLayout(\"wrap,flowy\",\n                                   \"[right][]\",\n                                   \"[grow,top][grow,center][grow,bottom][grow,fill,bottom][grow,fill,baseline]\");\nJPanel vPanel = createTabPanel(vLM);\nString[] vSizes = new String[] {\"\", \"growy\", \"growy 0\", \"top\", \"center\", \"bottom\"};\nvPanel.add(createLabel(\"[top]\"), \"center\");\nvPanel.add(createLabel(\"[center]\"), \"center\");\nvPanel.add(createLabel(\"[bottom]\"), \"center\");\nvPanel.add(createLabel(\"[fill, bottom]\"), \"center, growy 0\");\nvPanel.add(createLabel(\"[fill, baseline]\"), \"center\");\n\nfor (int c = 0; c < vSizes.length; c++) {\n\tfor (int r = 0; r < 5; r++) {\n\t\tString text = vSizes[c].length() > 0 ? vSizes[c] : \"default\";\n\t\tJButton b = createButton(text);\n\t\tif (r == 4 && c <= 1)\n\t\t\tb.setFont(new Font(\"sansserif\", Font.PLAIN, 16 + c * 5));\n\t\tvPanel.add(b, vSizes[c]);\n\t}\n}\n\ntabbedPane.addTab(\"Horizontal\", hPanel);\ntabbedPane.addTab(\"Vertical\", vPanel);");
    return (JComponent)localJTabbedPane;
  }

  public JComponent createUnits()
  {
    JTabbedPane localJTabbedPane = new JTabbedPane();
    MigLayout localMigLayout1 = new MigLayout("wrap,nocache", "[right][]", "");
    JPanel localJPanel1 = createTabPanel(localMigLayout1);
    String[] arrayOfString1 = { "72pt", "25.4mm", "2.54cm", "1in", "72px", "96px", "120px", "25%", "20sp" };
    for (int i = 0; i < arrayOfString1.length; i++)
    {
      localJPanel1.add(createLabel(arrayOfString1[i]));
      localJPanel1.add(createTextField(""), "width " + arrayOfString1[i] + "!");
    }
    MigLayout localMigLayout2 = new MigLayout("nocache", "[right][][]", "");
    JPanel localJPanel2 = createTabPanel(localMigLayout2);
    localJPanel2.add(createLabel("9 cols"));
    localJPanel2.add(createTextField(9));
    String[] arrayOfString2 = { "75lp", "75px", "88px", "100px" };
    localJPanel2.add(createLabel("Width of createTextField(9)"), "wrap");
    for (int j = 0; j < arrayOfString2.length; j++)
    {
      localJPanel2.add(createLabel(arrayOfString2[j]));
      localJPanel2.add(createTextField(""), "width " + arrayOfString2[j] + "!, wrap");
    }
    MigLayout localMigLayout3 = new MigLayout("wrap,flowy,nocache", "[c]", "[top][top]");
    JPanel localJPanel3 = createTabPanel(localMigLayout3);
    String[] arrayOfString3 = { "72pt", "25.4mm", "2.54cm", "1in", "72px", "96px", "120px", "25%", "20sp" };
    for (int k = 0; k < arrayOfString1.length; k++)
    {
      localJPanel3.add(createLabel(arrayOfString3[k]));
      localJPanel3.add(createTextArea("", 0, 5), "width 50!, height " + arrayOfString3[k] + "!");
    }
    MigLayout localMigLayout4 = new MigLayout("wrap,flowy,nocache", "[c]", "[top][top]40px[top][top]");
    JPanel localJPanel4 = createTabPanel(localMigLayout4);
    localJPanel4.add(createLabel("4 rows"));
    localJPanel4.add(createTextArea("", 4, 5), "width 50!");
    localJPanel4.add(createLabel("field"));
    localJPanel4.add(createTextField(5));
    String[] arrayOfString4 = { "63lp", "57px", "63px", "68px", "25%" };
    String[] arrayOfString5 = { "21lp", "21px", "23px", "24px", "10%" };
    for (int m = 0; m < arrayOfString4.length; m++)
    {
      localJPanel4.add(createLabel(arrayOfString4[m]));
      localJPanel4.add(createTextArea("", 1, 5), "width 50!, height " + arrayOfString4[m] + "!");
      localJPanel4.add(createLabel(arrayOfString5[m]));
      localJPanel4.add(createTextField(5), "height " + arrayOfString5[m] + "!");
    }
    localJPanel4.add(createLabel("button"), "skip 2");
    localJPanel4.add(createButton("..."));
    localJTabbedPane.addTab("Horizontal", localJPanel1);
    localJTabbedPane.addTab("Horizontal LP", localJPanel2);
    localJTabbedPane.addTab("Vertical", localJPanel3);
    localJTabbedPane.addTab("Vertical LP", localJPanel4);
    setSource("JTabbedPane tabbedPane = new JTabbedPane();\n\n// Horizontal\nMigLayout hLM = new MigLayout(\"wrap,nocache\",\n\t\t\t\t\t\t\t  \"[right][]\",\n\t\t\t\t\t\t\t  \"\");\nJPanel hPanel = createTabPanel(hLM);\nString[] sizes = new String[] {\"72pt\", \"25.4mm\", \"2.54cm\", \"1in\", \"72px\", \"96px\", \"120px\", \"25%\", \"20sp\"};\nfor (int i = 0; i < sizes.length; i++) {\n\thPanel.add(createLabel(sizes[i]));\n\thPanel.add(createTextField(\"\"), \"width \" + sizes[i] + \"!\");\n}\n\n// Horizontal lp\nMigLayout hlpLM = new MigLayout(\"nocache\", \"[right][][]\", \"\");\nJPanel hlpPanel = createTabPanel(hlpLM);\nhlpPanel.add(createLabel(\"9 cols\"));\nhlpPanel.add(createTextField(9));\nString[] lpSizes = new String[] {\"75lp\", \"75px\", \"88px\", \"100px\"};\nhlpPanel.add(createLabel(\"Width of createTextField(9)\"), \"wrap\");\nfor (int i = 0; i < lpSizes.length; i++) {\n\thlpPanel.add(createLabel(lpSizes[i]));\n\thlpPanel.add(createTextField(\"\"), \"width \" + lpSizes[i] + \"!, wrap\");\n}\n\n// Vertical\nMigLayout vLM = new MigLayout(\"wrap,flowy,nocache\",\n\t\t\t\t\t\t\t  \"[c]\",\n\t\t\t\t\t\t\t  \"[top][top]\");\nJPanel vPanel = createTabPanel(vLM);\nString[] vSizes = new String[] {\"72pt\", \"25.4mm\", \"2.54cm\", \"1in\", \"72px\", \"96px\", \"120px\", \"25%\", \"20sp\"};\nfor (int i = 0; i < sizes.length; i++) {\n\tvPanel.add(createLabel(vSizes[i]));\n\tvPanel.add(createTextArea(\"\", 0, 5), \"width 50!, height \" + vSizes[i] + \"!\");\n}\n\n// Vertical lp\nMigLayout vlpLM = new MigLayout(\"wrap,flowy,nocache\",\n\t\t\t\t\t\t\t\t\"[c]\",\n\t\t\t\t\t\t\t\t\"[top][top]40px[top][top]\");\nJPanel vlpPanel = createTabPanel(vlpLM);\nvlpPanel.add(createLabel(\"4 rows\"));\nvlpPanel.add(createTextArea(\"\", 4, 5), \"width 50!\");\nvlpPanel.add(createLabel(\"field\"));\nvlpPanel.add(createTextField(5));\n\nString[] vlpSizes1 = new String[] {\"63lp\", \"57px\", \"63px\", \"68px\", \"25%\"};\nString[] vlpSizes2 = new String[] {\"21lp\", \"21px\", \"23px\", \"24px\", \"10%\"};\nfor (int i = 0; i < vlpSizes1.length; i++) {\n\tvlpPanel.add(createLabel(vlpSizes1[i]));\n\tvlpPanel.add(createTextArea(\"\", 1, 5), \"width 50!, height \" + vlpSizes1[i] + \"!\");\n\tvlpPanel.add(createLabel(vlpSizes2[i]));\n\tvlpPanel.add(createTextField(5), \"height \" + vlpSizes2[i] + \"!\");\n}\n\nvlpPanel.add(createLabel(\"button\"), \"skip 2\");\nvlpPanel.add(createButton(\"...\"));\n\ntabbedPane.addTab(\"Horizontal\", hPanel);\ntabbedPane.addTab(\"Horizontal LP\", hlpPanel);\ntabbedPane.addTab(\"Vertical\", vPanel);\ntabbedPane.addTab(\"Vertical LP\", vlpPanel);");
    return localJTabbedPane;
  }

  public JComponent createGrouping()
  {
    JTabbedPane localJTabbedPane = new JTabbedPane();
    MigLayout localMigLayout1 = new MigLayout("", "[]push[][][]", "");
    JPanel localJPanel1 = createTabPanel(localMigLayout1);
    localJPanel1.add(createButton("Help"));
    localJPanel1.add(createButton("< Back"), "");
    localJPanel1.add(createButton("Forward >"), "gap push");
    localJPanel1.add(createButton("Apply"), "gap unrel");
    localJPanel1.add(createButton("Cancel"), "gap unrel");
    MigLayout localMigLayout2 = new MigLayout("nogrid, fillx");
    JPanel localJPanel2 = createTabPanel(localMigLayout2);
    localJPanel2.add(createButton("Help"), "sg");
    localJPanel2.add(createButton("< Back"), "sg,gap push");
    localJPanel2.add(createButton("Forward >"), "sg");
    localJPanel2.add(createButton("Apply"), "sg,gap unrel");
    localJPanel2.add(createButton("Cancel"), "sg,gap unrel");
    MigLayout localMigLayout3 = new MigLayout("", "[sg,fill]push[sg,fill][sg,fill]unrel[sg,fill]unrel[sg,fill]", "");
    JPanel localJPanel3 = createTabPanel(localMigLayout3);
    localJPanel3.add(createButton("Help"));
    localJPanel3.add(createButton("< Back"));
    localJPanel3.add(createButton("Forward >"));
    localJPanel3.add(createButton("Apply"));
    localJPanel3.add(createButton("Cancel"));
    MigLayout localMigLayout4 = new MigLayout();
    JPanel localJPanel4 = createTabPanel(localMigLayout4);
    localJPanel4.add(createLabel("File Number:"));
    localJPanel4.add(createTextField(30), "wrap");
    localJPanel4.add(createLabel("BL/MBL number:"));
    localJPanel4.add(createTextField(7), "split 2");
    localJPanel4.add(createTextField(7), "wrap");
    localJPanel4.add(createLabel("Entry Date:"));
    localJPanel4.add(createTextField(7), "wrap");
    localJPanel4.add(createLabel("RFQ Number:"));
    localJPanel4.add(createTextField(30), "wrap");
    localJPanel4.add(createLabel("Goods:"));
    localJPanel4.add(createCheck("Dangerous"), "wrap");
    localJPanel4.add(createLabel("Shipper:"));
    localJPanel4.add(createTextField(30), "wrap");
    localJPanel4.add(createLabel("Customer:"));
    localJPanel4.add(createTextField(""), "split 2,growx");
    localJPanel4.add(createButton("..."), "width 60px:pref,wrap");
    localJPanel4.add(createLabel("Port of Loading:"));
    localJPanel4.add(createTextField(30), "wrap");
    localJPanel4.add(createLabel("Destination:"));
    localJPanel4.add(createTextField(30), "wrap");
    MigLayout localMigLayout5 = new MigLayout("", "[]", "[sg]");
    JPanel localJPanel5 = createTabPanel(localMigLayout5);
    localJPanel5.add(createLabel("File Number:"));
    localJPanel5.add(createTextField(30), "wrap");
    localJPanel5.add(createLabel("BL/MBL number:"));
    localJPanel5.add(createTextField(7), "split 2");
    localJPanel5.add(createTextField(7), "wrap");
    localJPanel5.add(createLabel("Entry Date:"));
    localJPanel5.add(createTextField(7), "wrap");
    localJPanel5.add(createLabel("RFQ Number:"));
    localJPanel5.add(createTextField(30), "wrap");
    localJPanel5.add(createLabel("Goods:"));
    localJPanel5.add(createCheck("Dangerous"), "wrap");
    localJPanel5.add(createLabel("Shipper:"));
    localJPanel5.add(createTextField(30), "wrap");
    localJPanel5.add(createLabel("Customer:"));
    localJPanel5.add(createTextField(""), "split 2,growx");
    localJPanel5.add(createButton("..."), "width 50px:pref,wrap");
    localJPanel5.add(createLabel("Port of Loading:"));
    localJPanel5.add(createTextField(30), "wrap");
    localJPanel5.add(createLabel("Destination:"));
    localJPanel5.add(createTextField(30), "wrap");
    localJTabbedPane.addTab("Ungrouped", localJPanel1);
    localJTabbedPane.addTab("Grouped (Components)", localJPanel2);
    localJTabbedPane.addTab("Grouped (Columns)", localJPanel3);
    localJTabbedPane.addTab("Ungrouped Rows", localJPanel4);
    localJTabbedPane.addTab("Grouped Rows", localJPanel5);
    setSource("JTabbedPane tabbedPane = new JTabbedPane();\n\n// Ungrouped\nMigLayout ugM = new MigLayout(\"\", \"[]push[][][]\", \"\");\nJPanel ugPanel = createTabPanel(ugM);\nugPanel.add(createButton(\"Help\"));\nugPanel.add(createButton(\"< Back\"), \"\");\nugPanel.add(createButton(\"Forward >\"), \"gap push\");\nugPanel.add(createButton(\"Apply\"), \"gap unrel\");\nugPanel.add(createButton(\"Cancel\"), \"gap unrel\");\n\n// Grouped Components\nMigLayout gM = new MigLayout(\"nogrid, fillx\");\nJPanel gPanel = createTabPanel(gM);\ngPanel.add(createButton(\"Help\"), \"sg\");\ngPanel.add(createButton(\"< Back\"), \"sg,gap push\");\ngPanel.add(createButton(\"Forward >\"), \"sg\");\ngPanel.add(createButton(\"Apply\"), \"sg,gap unrel\");\ngPanel.add(createButton(\"Cancel\"), \"sg,gap unrel\");\n\n// Grouped Columns\nMigLayout gcM = new MigLayout(\"\", \"[sg,fill]push[sg,fill][sg,fill]unrel[sg,fill]unrel[sg,fill]\", \"\");\nJPanel gcPanel = createTabPanel(gcM);\ngcPanel.add(createButton(\"Help\"));\ngcPanel.add(createButton(\"< Back\"));\ngcPanel.add(createButton(\"Forward >\"));\ngcPanel.add(createButton(\"Apply\"));\ngcPanel.add(createButton(\"Cancel\"));\n\n// Ungrouped Rows\nMigLayout ugrM = new MigLayout();     // no \"sg\" is the only difference to next panel\nJPanel ugrPanel = createTabPanel(ugrM);\nugrPanel.add(createLabel(\"File Number:\"));\nugrPanel.add(createTextField(30), \"wrap\");\nugrPanel.add(createLabel(\"BL/MBL number:\"));\nugrPanel.add(createTextField(7), \"split 2\");\nugrPanel.add(createTextField(7), \"wrap\");\nugrPanel.add(createLabel(\"Entry Date:\"));\nugrPanel.add(createTextField(7), \"wrap\");\nugrPanel.add(createLabel(\"RFQ Number:\"));\nugrPanel.add(createTextField(30), \"wrap\");\nugrPanel.add(createLabel(\"Goods:\"));\nugrPanel.add(createCheck(\"Dangerous\"), \"wrap\");\nugrPanel.add(createLabel(\"Shipper:\"));\nugrPanel.add(createTextField(30), \"wrap\");\nugrPanel.add(createLabel(\"Customer:\"));\nugrPanel.add(createTextField(\"\"), \"split 2,growx\");\nugrPanel.add(createButton(\"...\"), \"width 60px:pref,wrap\");\nugrPanel.add(createLabel(\"Port of Loading:\"));\nugrPanel.add(createTextField(30), \"wrap\");\nugrPanel.add(createLabel(\"Destination:\"));\nugrPanel.add(createTextField(30), \"wrap\");\n\n// Grouped Rows\nMigLayout grM = new MigLayout(\"\", \"[]\", \"[sg]\");    // \"sg\" is the only difference to previous panel\nJPanel grPanel = createTabPanel(grM);\ngrPanel.add(createLabel(\"File Number:\"));\ngrPanel.add(createTextField(30),\"wrap\");\ngrPanel.add(createLabel(\"BL/MBL number:\"));\ngrPanel.add(createTextField(7),\"split 2\");\ngrPanel.add(createTextField(7), \"wrap\");\ngrPanel.add(createLabel(\"Entry Date:\"));\ngrPanel.add(createTextField(7), \"wrap\");\ngrPanel.add(createLabel(\"RFQ Number:\"));\ngrPanel.add(createTextField(30), \"wrap\");\ngrPanel.add(createLabel(\"Goods:\"));\ngrPanel.add(createCheck(\"Dangerous\"), \"wrap\");\ngrPanel.add(createLabel(\"Shipper:\"));\ngrPanel.add(createTextField(30), \"wrap\");\ngrPanel.add(createLabel(\"Customer:\"));\ngrPanel.add(createTextField(\"\"), \"split 2,growx\");\ngrPanel.add(createButton(\"...\"), \"width 50px:pref,wrap\");\ngrPanel.add(createLabel(\"Port of Loading:\"));\ngrPanel.add(createTextField(30), \"wrap\");\ngrPanel.add(createLabel(\"Destination:\"));\ngrPanel.add(createTextField(30), \"wrap\");\n\ntabbedPane.addTab(\"Ungrouped\", ugPanel);\ntabbedPane.addTab(\"Grouped (Components)\", gPanel);\ntabbedPane.addTab(\"Grouped (Columns)\", gcPanel);\ntabbedPane.addTab(\"Ungrouped Rows\", ugrPanel);\ntabbedPane.addTab(\"Grouped Rows\", grPanel);");
    return localJTabbedPane;
  }

  public JComponent createSpan()
  {
    JTabbedPane localJTabbedPane = new JTabbedPane();
    MigLayout localMigLayout1 = new MigLayout("nocache", "[fill][25%!,fill][105lp!,fill][100px!,fill]", "[]15[][]");
    JPanel localJPanel1 = createTabPanel(localMigLayout1);
    localJPanel1.add(createTextField("Col1 [ ]"));
    localJPanel1.add(createTextField("Col2 [25%!]"));
    localJPanel1.add(createTextField("Col3 [105lp!]"));
    localJPanel1.add(createTextField("Col4 [100px!]"), "wrap");
    localJPanel1.add(createLabel("Full Name:"));
    localJPanel1.add(createTextField("span, growx", 40), "span,growx");
    localJPanel1.add(createLabel("Phone:"));
    localJPanel1.add(createTextField(5), "span 3, split 5");
    localJPanel1.add(createTextField(7));
    localJPanel1.add(createTextField(7));
    localJPanel1.add(createTextField(9));
    localJPanel1.add(createLabel("(span 3, split 4)"), "wrap");
    localJPanel1.add(createLabel("Zip/City:"));
    localJPanel1.add(createTextField(5));
    localJPanel1.add(createTextField("span 2, growx", 5), "span 2,growx");
    MigLayout localMigLayout2 = new MigLayout("wrap", "[225lp]para[225lp]", "[]3[]unrel[]3[]unrel[]3[]");
    JPanel localJPanel2 = createTabPanel(localMigLayout2);
    localJPanel2.add(createLabel("Name"));
    localJPanel2.add(createLabel("Notes"));
    localJPanel2.add(createTextField("growx"), "growx");
    localJPanel2.add(createTextArea("spany,grow", 5, 20), "spany,grow");
    localJPanel2.add(createLabel("Phone"));
    localJPanel2.add(createTextField("growx"), "growx");
    localJPanel2.add(createLabel("Fax"));
    localJPanel2.add(createTextField("growx"), "growx");
    localJTabbedPane.addTab("Column Span/Split", localJPanel1);
    localJTabbedPane.addTab("Row Span", localJPanel2);
    setSource("\t\tJTabbedPane tabbedPane = new JTabbedPane();\n\n\t\t// Horizontal span\n\t\tMigLayout colLM = new MigLayout(\"\",\n\t\t                                     \"[fill][25%!,fill][105lp!,fill][100px!,fill]\",\n\t\t                                     \"[]15[][]\");\n\t\tJPanel colPanel = createTabPanel(colLM);\n\t\tcolPanel.add(createTextField(\"Col1 [ ]\"));\n\t\tcolPanel.add(createTextField(\"Col2 [25%!]\"));\n\t\tcolPanel.add(createTextField(\"Col3 [105lp!]\"));\n\t\tcolPanel.add(createTextField(\"Col4 [100px!]\"), \"wrap\");\n\n\t\tcolPanel.add(createLabel(\"Full Name:\"));\n\t\tcolPanel.add(createTextField(\"span, growx\", 40), \"span,growx\");\n\n\t\tcolPanel.add(createLabel(\"Phone:\"));\n\t\tcolPanel.add(createTextField(5), \"span 3, split 5\");\n\t\tcolPanel.add(createTextField(7));\n\t\tcolPanel.add(createTextField(7));\n\t\tcolPanel.add(createTextField(9));\n\t\tcolPanel.add(createLabel(\"(span 3, split 4)\"), \"wrap\");\n\n\t\tcolPanel.add(createLabel(\"Zip/City:\"));\n\t\tcolPanel.add(createTextField(5));\n\t\tcolPanel.add(createTextField(\"span 2, growx\", 5), \"span 2,growx\");\n\n\t\t// Vertical span\n\t\tMigLayout rowLM = new MigLayout(\"wrap\",\n\t\t                                     \"[225lp]para[225lp]\",\n\t\t                                     \"[]3[]unrel[]3[]unrel[]3[]\");\n\t\tJPanel rowPanel = createTabPanel(rowLM);\n\t\trowPanel.add(createLabel(\"Name\"));\n\t\trowPanel.add(createLabel(\"Notes\"));\n\t\trowPanel.add(createTextField(\"growx\"), \"growx\");\n\t\trowPanel.add(createTextArea(\"spany,grow\", 5, 20), \"spany,grow\");\n\t\trowPanel.add(createLabel(\"Phone\"));\n\t\trowPanel.add(createTextField(\"growx\"), \"growx\");\n\t\trowPanel.add(createLabel(\"Fax\"));\n\t\trowPanel.add(createTextField(\"growx\"), \"growx\");\n\n\t\ttabbedPane.addTab(\"Column Span/Split\", colPanel);\n\t\ttabbedPane.addTab(\"Row Span\", rowPanel);");
    return localJTabbedPane;
  }

  public JComponent createGrowing()
  {
    JTabbedPane localJTabbedPane = new JTabbedPane();
    MigLayout localMigLayout1 = new MigLayout("", "[pref!][grow,fill]", "[]15[]");
    JPanel localJPanel1 = createTabPanel(localMigLayout1);
    localJPanel1.add(createLabel("Fixed"));
    localJPanel1.add(createLabel("Gets all extra space"), "wrap");
    localJPanel1.add(createTextField(5));
    localJPanel1.add(createTextField(5));
    MigLayout localMigLayout2 = new MigLayout("", "[pref!][grow,fill]", "[]15[]");
    JPanel localJPanel2 = createTabPanel(localMigLayout2);
    localJPanel2.add(createLabel("Fixed"));
    localJPanel2.add(createLabel("Gets half of extra space"));
    localJPanel2.add(createLabel("Gets half of extra space"), "wrap");
    localJPanel2.add(createTextField(5));
    localJPanel2.add(createTextField(5));
    localJPanel2.add(createTextField(5));
    MigLayout localMigLayout3 = new MigLayout("", "[pref!][0:0,grow 25,fill][0:0,grow 75,fill]", "[]15[]");
    JPanel localJPanel3 = createTabPanel(localMigLayout3);
    localJPanel3.add(createLabel("Fixed"));
    localJPanel3.add(createLabel("Gets 25% of extra space"), "");
    localJPanel3.add(createLabel("Gets 75% of extra space"), "wrap");
    localJPanel3.add(createTextField(5));
    localJPanel3.add(createTextField(5));
    localJPanel3.add(createTextField(5));
    MigLayout localMigLayout4 = new MigLayout("", "[0:0,grow 33,fill][0:0,grow 67,fill]", "[]15[]");
    JPanel localJPanel4 = createTabPanel(localMigLayout4);
    localJPanel4.add(createLabel("Gets 33% of extra space"), "");
    localJPanel4.add(createLabel("Gets 67% of extra space"), "wrap");
    localJPanel4.add(createTextField(5));
    localJPanel4.add(createTextField(5));
    MigLayout localMigLayout5 = new MigLayout("flowy", "[]15[]", "[][c,pref!][c,grow 25,fill][c,grow 75,fill]");
    JPanel localJPanel5 = createTabPanel(localMigLayout5);
    localJPanel5.add(createLabel("Fixed"), "skip");
    localJPanel5.add(createLabel("Gets 25% of extra space"));
    localJPanel5.add(createLabel("Gets 75% of extra space"), "wrap");
    localJPanel5.add(createLabel("new JTextArea(4, 30)"));
    localJPanel5.add(createTextAreaScroll("", 4, 30, false));
    localJPanel5.add(createTextAreaScroll("", 4, 30, false));
    localJPanel5.add(createTextAreaScroll("", 4, 30, false));
    MigLayout localMigLayout6 = new MigLayout("flowy", "[]15[]", "[][c,grow 33,fill][c,grow 67,fill]");
    JPanel localJPanel6 = createTabPanel(localMigLayout6);
    localJPanel6.add(createLabel("Gets 33% of extra space"), "skip");
    localJPanel6.add(createLabel("Gets 67% of extra space"), "wrap");
    localJPanel6.add(createLabel("new JTextArea(4, 30)"));
    localJPanel6.add(createTextAreaScroll("", 4, 30, false));
    localJPanel6.add(createTextAreaScroll("", 4, 30, false));
    localJTabbedPane.addTab("All", localJPanel1);
    localJTabbedPane.addTab("Half", localJPanel2);
    localJTabbedPane.addTab("Percent 1", localJPanel3);
    localJTabbedPane.addTab("Percent 2", localJPanel4);
    localJTabbedPane.addTab("Vertical 1", localJPanel5);
    localJTabbedPane.addTab("Vertical 2", localJPanel6);
    setSource("JTabbedPane tabbedPane = new JTabbedPane();\n\n// All tab\nMigLayout allLM = new MigLayout(\"\",\n                                \"[pref!][grow,fill]\",\n                                \"[]15[]\");\nJPanel allTab = createTabPanel(allLM);\nallTab.add(createLabel(\"Fixed\"));\nallTab.add(createLabel(\"Gets all extra space\"), \"wrap\");\nallTab.add(createTextField(5));\nallTab.add(createTextField(5));\n\n// Half tab\nMigLayout halfLM = new MigLayout(\"\",\n                                 \"[pref!][grow,fill]\",\n                                 \"[]15[]\");\nJPanel halfTab = createTabPanel(halfLM);\nhalfTab.add(createLabel(\"Fixed\"));\nhalfTab.add(createLabel(\"Gets half of extra space\"));\nhalfTab.add(createLabel(\"Gets half of extra space\"), \"wrap\");\nhalfTab.add(createTextField(5));\nhalfTab.add(createTextField(5));\nhalfTab.add(createTextField(5));\n\n// Percent 1 tab\nMigLayout p1LM = new MigLayout(\"\",\n                               \"[pref!][0:0,grow 25,fill][0:0,grow 75,fill]\",\n                               \"[]15[]\");\nJPanel p1Tab = createTabPanel(p1LM);\np1Tab.add(createLabel(\"Fixed\"));\np1Tab.add(createLabel(\"Gets 25% of extra space\"), \"\");\np1Tab.add(createLabel(\"Gets 75% of extra space\"), \"wrap\");\np1Tab.add(createTextField(5));\np1Tab.add(createTextField(5));\np1Tab.add(createTextField(5));\n\n// Percent 2 tab\nMigLayout p2LM = new MigLayout(\"\",\n                               \"[0:0,grow 33,fill][0:0,grow 67,fill]\",\n                               \"[]15[]\");\nJPanel p2Tab = createTabPanel(p2LM);\np2Tab.add(createLabel(\"Gets 33% of extra space\"), \"\");\np2Tab.add(createLabel(\"Gets 67% of extra space\"), \"wrap\");\np2Tab.add(createTextField(5));\np2Tab.add(createTextField(5));\n\n// Vertical 1 tab\nMigLayout v1LM = new MigLayout(\"flowy\",\n                               \"[]15[]\",\n                               \"[][c,pref!][c,grow 25,fill][c,grow 75,fill]\");\nJPanel v1Tab = createTabPanel(v1LM);\nv1Tab.add(createLabel(\"Fixed\"), \"skip\");\nv1Tab.add(createLabel(\"Gets 25% of extra space\"));\nv1Tab.add(createLabel(\"Gets 75% of extra space\"), \"wrap\");\nv1Tab.add(createLabel(\"new JTextArea(4, 30)\"));\nv1Tab.add(createTextAreaScroll(\"\", 4, 30, false));\nv1Tab.add(createTextAreaScroll(\"\", 4, 30, false));\nv1Tab.add(createTextAreaScroll(\"\", 4, 30, false));\n\n// Vertical 2 tab\nMigLayout v2LM = new MigLayout(\"flowy\",\n                               \"[]15[]\",\n                               \"[][c,grow 33,fill][c,grow 67,fill]\");\nJPanel v2Tab = createTabPanel(v2LM);\nv2Tab.add(createLabel(\"Gets 33% of extra space\"), \"skip\");\nv2Tab.add(createLabel(\"Gets 67% of extra space\"), \"wrap\");\nv2Tab.add(createLabel(\"new JTextArea(4, 30)\"));\nv2Tab.add(createTextAreaScroll(\"\", 4, 30, false));\nv2Tab.add(createTextAreaScroll(\"\", 4, 30, false));\n\ntabbedPane.addTab(\"All\", allTab);\ntabbedPane.addTab(\"Half\", halfTab);\ntabbedPane.addTab(\"Percent 1\", p1Tab);\ntabbedPane.addTab(\"Percent 2\", p2Tab);\ntabbedPane.addTab(\"Vertical 1\", v1Tab);\ntabbedPane.addTab(\"Vertical 2\", v2Tab);");
    return localJTabbedPane;
  }

  public JComponent createBasic_Sizes()
  {
    JTabbedPane localJTabbedPane = new JTabbedPane();
    MigLayout localMigLayout1 = new MigLayout("", "[]15[75px]25[min]25[]", "[]15");
    JPanel localJPanel1 = createTabPanel(localMigLayout1);
    localJPanel1.add(createLabel("75px"), "skip");
    localJPanel1.add(createLabel("Min"));
    localJPanel1.add(createLabel("Pref"), "wrap");
    localJPanel1.add(createLabel("new TextField(15)"));
    localJPanel1.add(createTextField(15));
    localJPanel1.add(createTextField(15));
    localJPanel1.add(createTextField(15));
    MigLayout localMigLayout2 = new MigLayout("flowy,wrap", "[]15[]", "[]15[c,45px]15[c,min]15[c,pref]");
    JPanel localJPanel2 = createTabPanel(localMigLayout2);
    localJPanel2.add(createLabel("45px"), "skip");
    localJPanel2.add(createLabel("Min"));
    localJPanel2.add(createLabel("Pref"));
    localJPanel2.add(createLabel("new JTextArea(10, 40)"));
    localJPanel2.add(createTextArea("", 10, 40));
    localJPanel2.add(createTextArea("", 10, 40));
    localJPanel2.add(createTextArea("", 10, 40));
    MigLayout localMigLayout3 = new MigLayout("flowy,wrap", "[]15[]", "[]15[baseline]15[baseline]15[baseline]");
    JPanel localJPanel3 = createTabPanel(localMigLayout3);
    localJPanel3.add(createLabel("45px"), "skip");
    localJPanel3.add(createLabel("Min"));
    localJPanel3.add(createLabel("Pref"));
    localJPanel3.add(createLabel("new JTextArea(10, 40)"));
    localJPanel3.add(createTextArea("", 10, 40), "height 45");
    localJPanel3.add(createTextArea("", 10, 40), "height min");
    localJPanel3.add(createTextArea("", 10, 40), "height pref");
    localJTabbedPane.addTab("Horizontal - Column size set", localJPanel1);
    localJTabbedPane.addTab("Vertical - Row sized", localJPanel2);
    localJTabbedPane.addTab("Vertical - Component sized + Baseline", localJPanel3);
    setSource("JTabbedPane tabbedPane = new JTabbedPane();\n\n// Horizontal tab\nMigLayout horLM = new MigLayout(\"\",\n                                     \"[]15[75px]25[min]25[]\",\n                                     \"[]15\");\nJPanel horTab = createTabPanel(horLM);\nhorTab.add(createLabel(\"75px\"), \"skip\");\nhorTab.add(createLabel(\"Min\"));\nhorTab.add(createLabel(\"Pref\"), \"wrap\");\n\nhorTab.add(createLabel(\"new TextField(15)\"));\nhorTab.add(createTextField(15));\nhorTab.add(createTextField(15));\nhorTab.add(createTextField(15));\n\n// Vertical tab 1\nMigLayout verLM = new MigLayout(\"flowy,wrap\",\n                                     \"[]15[]\",\n                                     \"[]15[c,45px]15[c,min]15[c,pref]\");\nJPanel verTab = createTabPanel(verLM);\nverTab.add(createLabel(\"45px\"), \"skip\");\nverTab.add(createLabel(\"Min\"));\nverTab.add(createLabel(\"Pref\"));\n\nverTab.add(createLabel(\"new JTextArea(10, 40)\"));\nverTab.add(createTextArea(\"\", 10, 40));\nverTab.add(createTextArea(\"\", 10, 40));\nverTab.add(createTextArea(\"\", 10, 40));\n\n// Componentsized/Baseline 2\nMigLayout verLM2 = new MigLayout(\"flowy,wrap\",\n                                 \"[]15[]\",\n                                 \"[]15[baseline]15[baseline]15[baseline]\");\nJPanel verTab2 = createTabPanel(verLM2);\nverTab2.add(createLabel(\"45px\"), \"skip\");\nverTab2.add(createLabel(\"Min\"));\nverTab2.add(createLabel(\"Pref\"));\n\nverTab2.add(createLabel(\"new JTextArea(10, 40)\"));\nverTab2.add(createTextArea(\"\", 10, 40), \"height 45\");\nverTab2.add(createTextArea(\"\", 10, 40), \"height min\");\nverTab2.add(createTextArea(\"\", 10, 40), \"height pref\");\n\ntabbedPane.addTab(\"Horizontal - Column size set\", horTab);\ntabbedPane.addTab(\"Vertical - Row sized\", verTab);\ntabbedPane.addTab(\"Vertical - Component sized + Baseline\", verTab2);");
    return localJTabbedPane;
  }

  public JComponent createAlignments()
  {
    JTabbedPane localJTabbedPane = new JTabbedPane();
    MigLayout localMigLayout1 = new MigLayout("wrap", "[label]15[left]15[center]15[right]15[fill]15[]", "[]15[]");
    String[] arrayOfString1 = { "[label]", "[left]", "[center]", "[right]", "[fill]", "[] (Default)" };
    JPanel localJPanel1 = createTabPanel(localMigLayout1);
    String[] arrayOfString2 = { "First Name", "Phone Number", "Facsmile", "Email", "Address", "Other" };
    for (int i = 0; i < arrayOfString1.length; i++)
      localJPanel1.add(createLabel(arrayOfString1[i]));
    for (i = 0; i < arrayOfString1.length; i++)
      for (int j = 0; j < arrayOfString2.length; j++)
        localJPanel1.add(j == 0 ? createLabel(arrayOfString2[i] + ":") : createButton(arrayOfString2[i]));
    MigLayout localMigLayout2 = new MigLayout("wrap,flowy", "[]unrel[]rel[]", "[top]15[center]15[bottom]15[fill]15[fill,baseline]15[baseline]15[]");
    String[] arrayOfString3 = { "[top]", "[center]", "[bottom]", "[fill]", "[fill,baseline]", "[baseline]", "[] (Default)" };
    JPanel localJPanel2 = createTabPanel(localMigLayout2);
    String[] arrayOfString4 = { "One", benchRuns == 0 ? new String[] { "<html>One</html>", "<html>One<br>Two</html>" } : "One/Two" };
    for (int k = 0; k < arrayOfString3.length; k++)
      localJPanel2.add(createLabel(arrayOfString3[k]));
    for (k = 0; k < arrayOfString4.length; k++)
      for (int m = 0; m < arrayOfString3.length; m++)
        localJPanel2.add(createButton(arrayOfString4[k]));
    for (k = 0; k < arrayOfString3.length; k++)
      localJPanel2.add(createTextField("JTextFied"));
    for (k = 0; k < arrayOfString3.length; k++)
      localJPanel2.add(createTextArea("JTextArea", 1, 8));
    for (k = 0; k < arrayOfString3.length; k++)
      localJPanel2.add(createTextArea("JTextArea\nwith two lines", 1, 10));
    for (k = 0; k < arrayOfString3.length; k++)
      localJPanel2.add(createTextAreaScroll("Scrolling JTextArea\nwith two lines", 1, 15, true));
    localJTabbedPane.addTab("Horizontal", localJPanel1);
    localJTabbedPane.addTab("Vertical", localJPanel2);
    setSource("JTabbedPane tabbedPane = new JTabbedPane();\n\n// Horizontal tab\nMigLayout horLM = new MigLayout(\"wrap\",\n                                     \"[left]15[center]15[right]15[fill]15[]\",\n                                     \"rel[]rel\");\n\nString[] horLabels = new String[] {\"[left]\", \"[center]\", \"[right]\", \"[fill]\", \"[] (Default)\"};\nJPanel horTab = createTabPanel(horLM);\nString[] horNames = new String[] {\"First Name\", \"Phone Number\", \"Facsmile\", \"Email\", \"Address\"};\nfor (int c = 0; c < horLabels.length; c++)\n\thorTab.add(createLabel(horLabels[c]));\n\nfor (int r = 0; r < horLabels.length; r++) {\n\tfor (int c = 0; c < horNames.length; c++)\n\t\thorTab.add(createButton(horNames[r]));\n}\n\n// Vertical tab\nMigLayout verLM = new MigLayout(\"wrap,flowy\",\n                                \"[]unrel[]rel[]\",\n                                \"[top]15[center]15[bottom]15[fill]15[fill,baseline]15[baseline]15[]\");\n\nString[] verLabels = new String[] {\"[top]\", \"[center]\", \"[bottom]\", \"[fill]\", \"[fill,baseline]\", \"[baseline]\", \"[] (Default)\"};\nJPanel verTab = createTabPanel(verLM);\n\nString[] verNames = new String[] {\"<html>One</html>\", \"<html>One<br>Two</html>\"};\nfor (int c = 0; c < verLabels.length; c++)\n\tverTab.add(createLabel(verLabels[c]));\n\nfor (int r = 0; r < verNames.length; r++) {\n\tfor (int c = 0; c < verLabels.length; c++)\n\t\tverTab.add(createButton(verNames[r]));\n}\n\nfor (int c = 0; c < verLabels.length; c++)\n\tverTab.add(createTextField(\"JTextFied\"));\n\nfor (int c = 0; c < verLabels.length; c++)\n\tverTab.add(createTextArea(\"JTextArea\", 1, 8));\n\nfor (int c = 0; c < verLabels.length; c++)\n\tverTab.add(createTextArea(\"JTextArea\\nwith two lines\", 1, 10));\n\nfor (int c = 0; c < verLabels.length; c++)\n\tverTab.add(createTextAreaScroll(\"Scrolling JTextArea\\nwith two lines\", 1, 15, true));\n\ntabbedPane.addTab(\"Horizontal\", horTab);\ntabbedPane.addTab(\"Vertical\", verTab);");
    return localJTabbedPane;
  }

  public JComponent createQuick_Start()
  {
    JTabbedPane localJTabbedPane = new JTabbedPane();
    JPanel localJPanel = createTabPanel(new MigLayout("inset 20"));
    addSeparator(localJPanel, "General");
    localJPanel.add(createLabel("Company"), "gap para");
    localJPanel.add(createTextField(""), "span, growx");
    localJPanel.add(createLabel("Contact"), "gap para");
    localJPanel.add(createTextField(""), "span, growx, wrap para");
    addSeparator(localJPanel, "Propeller");
    localJPanel.add(createLabel("PTI/kW"), "gap para");
    localJPanel.add(createTextField(10));
    localJPanel.add(createLabel("Power/kW"), "gap para");
    localJPanel.add(createTextField(10), "wrap");
    localJPanel.add(createLabel("R/mm"), "gap para");
    localJPanel.add(createTextField(10));
    localJPanel.add(createLabel("D/mm"), "gap para");
    localJPanel.add(createTextField(10));
    localJTabbedPane.addTab("Quick Start", localJPanel);
    setSource("JTabbedPane tabbedPane = new JTabbedPane();\n\nJPanel p = createTabPanel(new MigLayout());\n\naddSeparator(p, \"General\");\n\np.add(createLabel(\"Company\"), \"gap para\");\np.add(createTextField(\"\"),    \"span, growx, wrap\");\np.add(createLabel(\"Contact\"), \"gap para\");\np.add(createTextField(\"\"),    \"span, growx, wrap para\");\n\naddSeparator(p, \"Propeller\");\n\np.add(createLabel(\"PTI/kW\"),  \"gap para\");\np.add(createTextField(10));\np.add(createLabel(\"Power/kW\"),\"gap para\");\np.add(createTextField(10),    \"wrap\");\np.add(createLabel(\"R/mm\"),    \"gap para\");\np.add(createTextField(10));\np.add(createLabel(\"D/mm\"),    \"gap para\");\np.add(createTextField(10));\n\ntabbedPane.addTab(\"Quick Start\", p);");
    return localJTabbedPane;
  }

  public JComponent createGrow_Shrink()
  {
    JTabbedPane localJTabbedPane = new JTabbedPane();
    MigLayout localMigLayout1 = new MigLayout("nogrid");
    JPanel localJPanel1 = createTabPanel(localMigLayout1);
    JScrollPane localJScrollPane1 = createTextAreaScroll("Use the slider to see how the components shrink depending on the constraints set on them.\n\n'shp' means Shrink Priority. Lower values will be shrunk before higer ones and the default value is 100.\n\n'shrink' means Shrink Weight. Lower values relative to other's means they will shrink less when space is scarse. Shrink Weight is only relative to components with the same Shrink Priority. Default Shrink Weight is 100.\n\nThe component's minimum size will always be honored.", 0, 0, true);
    localJScrollPane1.setOpaque(false);
    localJScrollPane1.setBorder(new EmptyBorder(10, 10, 10, 10));
    ((JTextArea)localJScrollPane1.getViewport().getView()).setOpaque(false);
    localJScrollPane1.getViewport().setOpaque(false);
    JSplitPane localJSplitPane1 = new JSplitPane(1, true, localJPanel1, localJScrollPane1);
    localJSplitPane1.setOpaque(false);
    localJSplitPane1.setBorder(null);
    localJPanel1.add(createTextField("shp 110", 12), "shp 110");
    localJPanel1.add(createTextField("Default (100)", 12), "");
    localJPanel1.add(createTextField("shp 90", 12), "shp 90");
    localJPanel1.add(createTextField("shrink 25", 20), "newline,shrink 25");
    localJPanel1.add(createTextField("shrink 75", 20), "shrink 75");
    localJPanel1.add(createTextField("Default", 20), "newline");
    localJPanel1.add(createTextField("Default", 20), "");
    localJPanel1.add(createTextField("shrink 0", 40), "newline,shrink 0");
    localJPanel1.add(createTextField("shp 110", 12), "newline,shp 110");
    localJPanel1.add(createTextField("shp 100,shrink 25", 12), "shp 100,shrink 25");
    localJPanel1.add(createTextField("shp 100,shrink 75", 12), "shp 100,shrink 75");
    localJTabbedPane.addTab("Shrink", localJSplitPane1);
    MigLayout localMigLayout2 = new MigLayout("nogrid", "[grow]", "");
    JPanel localJPanel2 = createTabPanel(localMigLayout2);
    JScrollPane localJScrollPane2 = createTextAreaScroll("'gp' means Grow Priority. Higher values will be grown before lower ones and the default value is 100.\n\n'grow' means Grow Weight. Higher values relative to other's means they will grow more when space is up for takes. Grow Weight is only relative to components with the same Grow Priority. Default Grow Weight is 0 which means components will normally not grow. \n\nNote that the buttons in the first and last row have max width set to 170 to emphasize Grow Priority.\n\nThe component's maximum size will always be honored.", 0, 0, true);
    localJScrollPane2.setOpaque(false);
    localJScrollPane2.setBorder(new EmptyBorder(10, 10, 10, 10));
    ((JTextArea)localJScrollPane2.getViewport().getView()).setOpaque(false);
    localJScrollPane2.getViewport().setOpaque(false);
    JSplitPane localJSplitPane2 = new JSplitPane(1, true, localJPanel2, localJScrollPane2);
    localJSplitPane2.setOpaque(false);
    localJSplitPane2.setBorder(null);
    localJPanel2.add(createButton("gp 110,grow"), "gp 110,grow,wmax 170");
    localJPanel2.add(createButton("Default (100),grow"), "grow,wmax 170");
    localJPanel2.add(createButton("gp 90,grow"), "gp 90,grow,wmax 170");
    localJPanel2.add(createButton("Default Button"), "newline");
    localJPanel2.add(createButton("growx"), "newline,growx,wrap");
    localJPanel2.add(createButton("gp 110,grow"), "gp 110,grow,wmax 170");
    localJPanel2.add(createButton("gp 100,grow 25"), "gp 100,grow 25,wmax 170");
    localJPanel2.add(createButton("gp 100,grow 75"), "gp 100,grow 75,wmax 170");
    localJTabbedPane.addTab("Grow", localJSplitPane2);
    setSource("JTabbedPane tabbedPane = new JTabbedPane();\n\n// shrink tab\nMigLayout slm = new MigLayout(\"nogrid\");\nJPanel sPanel = createTabPanel(slm);\n\nJScrollPane sDescrText = createTextAreaScroll(\"Use the slider to see how the components shrink depending on the constraints set on them.\\n\\n'shp' means Shrink Priority. \" +\n                                              \"Lower values will be shrunk before higer ones and the default value is 100.\\n\\n'shrink' means Shrink Weight. \" +\n                                              \"Lower values relative to other's means they will shrink less when space is scarse. \" +\n                                              \"Shrink Weight is only relative to components with the same Shrink Priority. Default Shrink Weight is 100.\\n\\n\" +\n                                              \"The component's minimum size will always be honored.\", 0, 0, true);\n\nsDescrText.setOpaque(OPAQUE);\nsDescrText.setBorder(new EmptyBorder(10, 10, 10, 10));\n((JTextArea) sDescrText.getViewport().getView()).setOpaque(OPAQUE);\nsDescrText.getViewport().setOpaque(OPAQUE);\n\nJSplitPane sSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, sPanel, sDescrText);\nsSplitPane.setOpaque(OPAQUE);\nsSplitPane.setBorder(null);\n\nsPanel.add(createTextField(\"shp 110\", 12), \"shp 110\");\nsPanel.add(createTextField(\"Default (100)\", 12), \"\");\nsPanel.add(createTextField(\"shp 90\", 12), \"shp 90\");\n\nsPanel.add(createTextField(\"shrink 25\", 20), \"newline,shrink 25\");\nsPanel.add(createTextField(\"shrink 75\", 20), \"shrink 75\");\n\nsPanel.add(createTextField(\"Default\", 20), \"newline\");\nsPanel.add(createTextField(\"Default\", 20), \"\");\n\nsPanel.add(createTextField(\"shrink 0\", 40), \"newline,shrink 0\");\n\nsPanel.add(createTextField(\"shp 110\", 12), \"newline,shp 110\");\nsPanel.add(createTextField(\"shp 100,shrink 25\", 12), \"shp 100,shrink 25\");\nsPanel.add(createTextField(\"shp 100,shrink 75\", 12), \"shp 100,shrink 75\");\ntabbedPane.addTab(\"Shrink\", sSplitPane);\n\n// Grow tab\nMigLayout glm = new MigLayout(\"nogrid\", \"[grow]\", \"\");\nJPanel gPanel = createTabPanel(glm);\n\nJScrollPane gDescrText = createTextAreaScroll(\"'gp' means Grow Priority. \" +\n                                              \"Higher values will be grown before lower ones and the default value is 100.\\n\\n'grow' means Grow Weight. \" +\n                                              \"Higher values relative to other's means they will grow more when space is up for takes. \" +\n                                              \"Grow Weight is only relative to components with the same Grow Priority. Default Grow Weight is 0 which means \" +\n                                              \"components will normally not grow. \\n\\nNote that the buttons in the first and last row have max width set to 170 to \" +\n                                              \"emphasize Grow Priority.\\n\\nThe component's maximum size will always be honored.\", 0, 0, true);\n\ngDescrText.setOpaque(OPAQUE);\ngDescrText.setBorder(new EmptyBorder(10, 10, 10, 10));\n((JTextArea) gDescrText.getViewport().getView()).setOpaque(OPAQUE);\ngDescrText.getViewport().setOpaque(OPAQUE);\n\nJSplitPane gSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, gPanel, gDescrText);\ngSplitPane.setOpaque(OPAQUE);\ngSplitPane.setBorder(null);\n\ngPanel.add(createButton(\"gp 110,grow\"), \"gp 110,grow,wmax 170\");\ngPanel.add(createButton(\"Default (100),grow\"), \"grow,wmax 170\");\ngPanel.add(createButton(\"gp 90,grow\"), \"gp 90,grow,wmax 170\");\n\ngPanel.add(createButton(\"Default Button\"), \"newline\");\n\ngPanel.add(createButton(\"growx\"), \"newline,growx,wrap\");\n\ngPanel.add(createButton(\"gp 110,grow\"), \"gp 110,grow,wmax 170\");\ngPanel.add(createButton(\"gp 100,grow 25\"), \"gp 100,grow 25,wmax 170\");\ngPanel.add(createButton(\"gp 100,grow 75\"), \"gp 100,grow 75,wmax 170\");\ntabbedPane.addTab(\"Grow\", gSplitPane);");
    return localJTabbedPane;
  }

  public JComponent createPlainApi()
  {
    JTabbedPane localJTabbedPane = new JTabbedPane();
    MigLayout localMigLayout = new MigLayout(new LC(), null, null);
    JPanel localJPanel = createTabPanel(localMigLayout);
    addSeparator(localJPanel, "Manufacturer");
    localJPanel.add(createLabel("Company"));
    localJPanel.add(createTextField(""), "span,growx");
    localJPanel.add(createLabel("Contact"));
    localJPanel.add(createTextField(""), "span,growx");
    localJPanel.add(createLabel("Order No"));
    localJPanel.add(createTextField(15), "wrap");
    addSeparator(localJPanel, "Inspector");
    localJPanel.add(createLabel("Name"));
    localJPanel.add(createTextField(""), "span,growx");
    localJPanel.add(createLabel("Reference No"));
    localJPanel.add(createTextField(""), "wrap");
    localJPanel.add(createLabel("Status"));
    localJPanel.add(createCombo(new String[] { "In Progress", "Finnished", "Released" }), "wrap");
    addSeparator(localJPanel, "Ship");
    localJPanel.add(createLabel("Shipyard"));
    localJPanel.add(createTextField(""), "span,growx");
    localJPanel.add(createLabel("Register No"));
    localJPanel.add(createTextField(""));
    localJPanel.add(createLabel("Hull No"), "right");
    localJPanel.add(createTextField(15), "wrap");
    localJPanel.add(createLabel("Project StructureType"));
    localJPanel.add(createCombo(new String[] { "New Building", "Convention", "Repair" }));
    localJTabbedPane.addTab("Plain", localJPanel);
    return localJTabbedPane;
  }

  private JLabel createLabel(String paramString)
  {
    return createLabel(paramString, 10);
  }

  private JLabel createLabel(String paramString, int paramInt)
  {
    JLabel localJLabel = new JLabel(paramString, paramInt);
    configureActiveComponet(localJLabel);
    return localJLabel;
  }

  public JComboBox createCombo(String[] paramArrayOfString)
  {
    JComboBox localJComboBox = new JComboBox(paramArrayOfString);
    if (PlatformDefaults.getCurrentPlatform() == 1)
      localJComboBox.setOpaque(false);
    return localJComboBox;
  }

  private JTextField createTextField(int paramInt)
  {
    return createTextField("", paramInt);
  }

  private JTextField createTextField(String paramString)
  {
    return createTextField(paramString, 0);
  }

  private JTextField createTextField(String paramString, int paramInt)
  {
    JTextField localJTextField = new JTextField(paramString, paramInt);
    configureActiveComponet(localJTextField);
    return localJTextField;
  }

  private JButton createButton()
  {
    return createButton("");
  }

  private JButton createButton(String paramString)
  {
    return createButton(paramString, false);
  }

  private JButton createButton(String paramString, boolean paramBoolean)
  {
    11 local11 = new JButton(paramString)
    {
      public void addNotify()
      {
        super.addNotify();
        if (SwingDemo.benchRuns == 0)
        {
          if (getText().length() == 0)
          {
            String str = (String)((MigLayout)getParent().getLayout()).getComponentConstraints(this);
            setText((str != null) && (str.length() > 0) ? str : "<Empty>");
          }
        }
        else
          setText("Benchmark Version");
      }
    };
    if (paramBoolean)
      local11.setFont(local11.getFont().deriveFont(1));
    configureActiveComponet(local11);
    local11.setOpaque(buttonOpaque);
    local11.setContentAreaFilled(contentAreaFilled);
    return local11;
  }

  private JToggleButton createToggleButton(String paramString)
  {
    JToggleButton localJToggleButton = new JToggleButton(paramString);
    localJToggleButton.setOpaque(buttonOpaque);
    return localJToggleButton;
  }

  private JCheckBox createCheck(String paramString)
  {
    JCheckBox localJCheckBox = new JCheckBox(paramString);
    configureActiveComponet(localJCheckBox);
    localJCheckBox.setOpaque(false);
    return localJCheckBox;
  }

  private JPanel createTabPanel(LayoutManager paramLayoutManager)
  {
    JPanel localJPanel = new JPanel(paramLayoutManager);
    configureActiveComponet(localJPanel);
    localJPanel.setOpaque(false);
    return localJPanel;
  }

  private JComponent createPanel()
  {
    return createPanel("");
  }

  private JComponent createPanel(String paramString)
  {
    12 local12 = new JLabel(paramString, 0)
    {
      public void addNotify()
      {
        super.addNotify();
        if ((SwingDemo.benchRuns == 0) && (getText().length() == 0))
        {
          String str = (String)((MigLayout)getParent().getLayout()).getComponentConstraints(this);
          setText((str != null) && (str.length() > 0) ? str : "<Empty>");
        }
      }
    };
    local12.setBorder(new EtchedBorder());
    local12.setOpaque(true);
    configureActiveComponet(local12);
    return local12;
  }

  private JTextArea createTextArea(String paramString, int paramInt1, int paramInt2)
  {
    JTextArea localJTextArea = new JTextArea(paramString, paramInt1, paramInt2);
    localJTextArea.setBorder(UIManager.getBorder("TextField.border"));
    localJTextArea.setFont(UIManager.getFont("TextField.font"));
    localJTextArea.setWrapStyleWord(true);
    localJTextArea.setLineWrap(true);
    configureActiveComponet(localJTextArea);
    return localJTextArea;
  }

  private JScrollPane createTextAreaScroll(String paramString, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    JTextArea localJTextArea = new JTextArea(paramString, paramInt1, paramInt2);
    localJTextArea.setFont(UIManager.getFont("TextField.font"));
    localJTextArea.setWrapStyleWord(true);
    localJTextArea.setLineWrap(true);
    JScrollPane localJScrollPane = new JScrollPane(localJTextArea, paramBoolean ? 20 : 21, 31);
    return localJScrollPane;
  }

  private JComponent configureActiveComponet(JComponent paramJComponent)
  {
    if (benchRuns == 0)
    {
      paramJComponent.addMouseMotionListener(this.toolTipListener);
      paramJComponent.addMouseListener(this.constraintListener);
    }
    return paramJComponent;
  }

  private void addSeparator(JPanel paramJPanel, String paramString)
  {
    JLabel localJLabel = createLabel(paramString);
    localJLabel.setForeground(LABEL_COLOR);
    paramJPanel.add(localJLabel, "gapbottom 1, span, split 2, aligny center");
    paramJPanel.add(configureActiveComponet(new JSeparator()), "gapleft rel, growx");
  }

  private static class ConstraintsDialog extends JDialog
    implements ActionListener, KeyEventDispatcher
  {
    private static final Color ERROR_COLOR = new Color(255, 180, 180);
    private final JPanel mainPanel = new JPanel(new MigLayout("fillx,flowy,ins dialog", "[fill]", "2[]2"));
    final JTextField layoutConstrTF = createConstraintField(paramString1);
    final JTextField rowsConstrTF = createConstraintField(paramString2);
    final JTextField colsConstrTF = createConstraintField(paramString3);
    final JTextField componentConstrTF = createConstraintField(paramString4);
    private final JButton okButt = new JButton("OK");
    private final JButton cancelButt = new JButton("Cancel");
    private boolean okPressed = false;

    public ConstraintsDialog(Frame paramFrame, String paramString1, String paramString2, String paramString3, String paramString4)
    {
      super(paramString4 != null ? "Edit Component Constraints" : "Edit Container Constraints", true);
      if (this.componentConstrTF != null)
      {
        this.mainPanel.add(new JLabel("Component Constraints"));
        this.mainPanel.add(this.componentConstrTF);
      }
      if (this.layoutConstrTF != null)
      {
        this.mainPanel.add(new JLabel("Layout Constraints"));
        this.mainPanel.add(this.layoutConstrTF);
      }
      if (this.colsConstrTF != null)
      {
        this.mainPanel.add(new JLabel("Column Constraints"), "gaptop unrel");
        this.mainPanel.add(this.colsConstrTF);
      }
      if (this.rowsConstrTF != null)
      {
        this.mainPanel.add(new JLabel("Row Constraints"), "gaptop unrel");
        this.mainPanel.add(this.rowsConstrTF);
      }
      this.mainPanel.add(this.okButt, "tag ok,split,flowx,gaptop 15");
      this.mainPanel.add(this.cancelButt, "tag cancel,gaptop 15");
      setContentPane(this.mainPanel);
      this.okButt.addActionListener(this);
      this.cancelButt.addActionListener(this);
    }

    public void addNotify()
    {
      super.addNotify();
      KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);
    }

    public void removeNotify()
    {
      KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(this);
      super.removeNotify();
    }

    public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
    {
      if (paramKeyEvent.getKeyCode() == 27)
        dispose();
      return false;
    }

    public void actionPerformed(ActionEvent paramActionEvent)
    {
      if (paramActionEvent.getSource() == this.okButt)
        this.okPressed = true;
      dispose();
    }

    private JTextField createConstraintField(String paramString)
    {
      if (paramString == null)
        return null;
      JTextField localJTextField = new JTextField(paramString, 50);
      localJTextField.setFont(new Font("monospaced", 0, 12));
      localJTextField.addKeyListener(new KeyAdapter(localJTextField)
      {
        public void keyPressed(KeyEvent paramKeyEvent)
        {
          if (paramKeyEvent.getKeyCode() == 10)
          {
            SwingDemo.ConstraintsDialog.this.okButt.doClick();
            return;
          }
          Timer localTimer = new Timer(50, new ActionListener()
          {
            public void actionPerformed(ActionEvent paramActionEvent)
            {
              String str = SwingDemo.ConstraintsDialog.1.this.val$tf.getText();
              try
              {
                if (SwingDemo.ConstraintsDialog.1.this.val$tf == SwingDemo.ConstraintsDialog.this.layoutConstrTF)
                  ConstraintParser.parseLayoutConstraint(str);
                else if (SwingDemo.ConstraintsDialog.1.this.val$tf == SwingDemo.ConstraintsDialog.this.rowsConstrTF)
                  ConstraintParser.parseRowConstraints(str);
                else if (SwingDemo.ConstraintsDialog.1.this.val$tf == SwingDemo.ConstraintsDialog.this.colsConstrTF)
                  ConstraintParser.parseColumnConstraints(str);
                else if (SwingDemo.ConstraintsDialog.1.this.val$tf == SwingDemo.ConstraintsDialog.this.componentConstrTF)
                  ConstraintParser.parseComponentConstraint(str);
                SwingDemo.ConstraintsDialog.1.this.val$tf.setBackground(Color.WHITE);
                SwingDemo.ConstraintsDialog.this.okButt.setEnabled(true);
              }
              catch (Exception localException)
              {
                SwingDemo.ConstraintsDialog.1.this.val$tf.setBackground(SwingDemo.ConstraintsDialog.ERROR_COLOR);
                SwingDemo.ConstraintsDialog.this.okButt.setEnabled(false);
              }
            }
          });
          localTimer.setRepeats(false);
          localTimer.start();
        }
      });
      return localJTextField;
    }

    private boolean showDialog()
    {
      setVisible(true);
      return this.okPressed;
    }
  }

  private static class ToolTipListener extends MouseMotionAdapter
  {
    public void mouseMoved(MouseEvent paramMouseEvent)
    {
      JComponent localJComponent = (JComponent)paramMouseEvent.getSource();
      LayoutManager localLayoutManager = localJComponent.getParent().getLayout();
      if ((localLayoutManager instanceof MigLayout))
      {
        Object localObject = ((MigLayout)localLayoutManager).getComponentConstraints(localJComponent);
        if ((localObject instanceof String))
          localJComponent.setToolTipText(localObject != null ? "\"" + localObject + "\"" : "null");
      }
    }
  }

  private class ConstraintListener extends MouseAdapter
  {
    private ConstraintListener()
    {
    }

    public void mousePressed(MouseEvent paramMouseEvent)
    {
      if (paramMouseEvent.isPopupTrigger())
        react(paramMouseEvent);
    }

    public void mouseReleased(MouseEvent paramMouseEvent)
    {
      if (paramMouseEvent.isPopupTrigger())
        react(paramMouseEvent);
    }

    public void react(MouseEvent paramMouseEvent)
    {
      JComponent localJComponent = (JComponent)paramMouseEvent.getSource();
      LayoutManager localLayoutManager = localJComponent.getParent().getLayout();
      if (!(localLayoutManager instanceof MigLayout))
        localLayoutManager = localJComponent.getLayout();
      if ((localLayoutManager instanceof MigLayout))
      {
        MigLayout localMigLayout = (MigLayout)localLayoutManager;
        boolean bool = localMigLayout.isManagingComponent(localJComponent);
        String str1 = bool ? localMigLayout.getComponentConstraints(localJComponent) : null;
        if ((bool) && (str1 == null))
          str1 = "";
        Object localObject1 = bool ? null : localMigLayout.getRowConstraints();
        Object localObject2 = bool ? null : localMigLayout.getColumnConstraints();
        Object localObject3 = bool ? null : localMigLayout.getLayoutConstraints();
        SwingDemo.ConstraintsDialog localConstraintsDialog = new SwingDemo.ConstraintsDialog(SwingDemo.this, (localObject3 instanceof LC) ? IDEUtil.getConstraintString((LC)localObject3, false) : (String)localObject3, (localObject1 instanceof AC) ? IDEUtil.getConstraintString((AC)localObject1, false, false) : (String)localObject1, (localObject2 instanceof AC) ? IDEUtil.getConstraintString((AC)localObject2, false, false) : (String)localObject2, (str1 instanceof CC) ? IDEUtil.getConstraintString((CC)str1, false) : (String)str1);
        localConstraintsDialog.pack();
        localConstraintsDialog.setLocationRelativeTo(localJComponent);
        if (localConstraintsDialog.showDialog())
        {
          try
          {
            if (bool)
            {
              String str2 = localConstraintsDialog.componentConstrTF.getText().trim();
              localMigLayout.setComponentConstraints(localJComponent, str2);
              if ((localJComponent instanceof JButton))
              {
                localJComponent.setFont(SwingDemo.BUTT_FONT);
                ((JButton)localJComponent).setText(str2.length() == 0 ? "<Empty>" : str2);
              }
            }
            else
            {
              localMigLayout.setLayoutConstraints(localConstraintsDialog.layoutConstrTF.getText());
              localMigLayout.setRowConstraints(localConstraintsDialog.rowsConstrTF.getText());
              localMigLayout.setColumnConstraints(localConstraintsDialog.colsConstrTF.getText());
            }
          }
          catch (Exception localException)
          {
            StringWriter localStringWriter = new StringWriter();
            localException.printStackTrace(new PrintWriter(localStringWriter));
            JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(localJComponent), localStringWriter.toString(), "Error parsing Constraint!", 0);
            return;
          }
          localJComponent.invalidate();
          localJComponent.getParent().validate();
        }
      }
    }
  }
}

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     net.miginfocom.demo.SwingDemo
 * JD-Core Version:    0.6.0
 */