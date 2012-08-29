package net.miginfocom.demo;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;
import net.miginfocom.layout.PlatformDefaults;
import net.miginfocom.swing.MigLayout;
import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.fonts.SubstanceFontUtilities;
import org.jvnet.substance.skin.SubstanceBusinessBlackSteelLookAndFeel;

public class HiDPISimulator
{
  static final String SYSTEM_LAF_NAME = "System";
  static final String SUBSTANCE_LAF_NAME = "Substance";
  static final String OCEAN_LAF_NAME = "Ocean";
  static final String NUMBUS_LAF_NAME = "Nimbus (Soon..)";
  static JFrame APP_GUI_FRAME;
  static HiDPIDemoPanel HiDPIDEMO_PANEL;
  static JPanel SIM_PANEL;
  static JPanel MIRROR_PANEL;
  static JScrollPane MAIN_SCROLL;
  static JTextArea TEXT_AREA;
  static boolean SCALE_LAF = false;
  static boolean SCALE_FONTS = true;
  static boolean SCALE_LAYOUT = true;
  static boolean PAINT_GHOSTED = false;
  static BufferedImage GUI_BUF = null;
  static BufferedImage ORIG_GUI_BUF = null;
  static int CUR_DPI = PlatformDefaults.getDefaultDPI();
  static HashMap<String, Font> ORIG_DEFAULTS;

  private static JPanel createScaleMirror()
  {
    return new JPanel(new MigLayout())
    {
      protected void paintComponent(Graphics paramGraphics)
      {
        super.paintComponent(paramGraphics);
        if (HiDPISimulator.GUI_BUF != null)
        {
          Graphics2D localGraphics2D = (Graphics2D)paramGraphics.create();
          double d = getToolkit().getScreenResolution();
          AffineTransform localAffineTransform = localGraphics2D.getTransform();
          localGraphics2D.scale(d / HiDPISimulator.CUR_DPI, d / HiDPISimulator.CUR_DPI);
          localGraphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
          localGraphics2D.drawImage(HiDPISimulator.GUI_BUF, 0, 0, null);
          localGraphics2D.setTransform(localAffineTransform);
          if ((HiDPISimulator.ORIG_GUI_BUF != null) && (HiDPISimulator.PAINT_GHOSTED))
          {
            localGraphics2D.setComposite(AlphaComposite.getInstance(3, 0.2F));
            localGraphics2D.drawImage(HiDPISimulator.ORIG_GUI_BUF, 0, 0, null);
          }
          localGraphics2D.dispose();
        }
      }

      public Dimension getPreferredSize()
      {
        return HiDPISimulator.ORIG_GUI_BUF != null ? new Dimension(HiDPISimulator.ORIG_GUI_BUF.getWidth(), HiDPISimulator.ORIG_GUI_BUF.getHeight()) : new Dimension(100, 100);
      }

      public Dimension getMinimumSize()
      {
        return getPreferredSize();
      }
    };
  }

  private static JPanel createSimulator()
  {
    JRadioButton localJRadioButton1 = new JRadioButton("UIManager Font Substitution", true);
    JRadioButton localJRadioButton2 = new JRadioButton("Native Look&Feel Scaling", false);
    JRadioButton localJRadioButton3 = new JRadioButton("No Scaling", false);
    JRadioButton localJRadioButton4 = new JRadioButton("Native MigLayout Gap Scaling", true);
    JRadioButton localJRadioButton5 = new JRadioButton("No Gap Scaling", false);
    JComboBox localJComboBox1 = new JComboBox(new String[] { "System", "Substance", "Ocean", "Nimbus (Soon..)" });
    ButtonGroup localButtonGroup1 = new ButtonGroup();
    ButtonGroup localButtonGroup2 = new ButtonGroup();
    JCheckBox localJCheckBox = new JCheckBox("Overlay \"Optimal\" HiDPI Result");
    localJRadioButton2.setEnabled(false);
    localButtonGroup1.add(localJRadioButton1);
    localButtonGroup1.add(localJRadioButton2);
    localButtonGroup1.add(localJRadioButton3);
    localButtonGroup2.add(localJRadioButton4);
    localButtonGroup2.add(localJRadioButton5);
    Vector localVector = new Vector();
    float f = 0.5F;
    while (f < 2.01F)
    {
      localVector.add(Math.round(PlatformDefaults.getDefaultDPI() * f) + " DPI (" + Math.round(f * 100.0F + 0.499F) + "%)");
      f += 0.1F;
    }
    JComboBox localJComboBox2 = new JComboBox(localVector);
    localJComboBox2.setSelectedIndex(5);
    JPanel localJPanel = new JPanel(new MigLayout("alignx center, insets 10px, flowy", "[]", "[]3px[]0px[]"));
    JLabel localJLabel1 = new JLabel("Look & Feel:");
    JLabel localJLabel2 = new JLabel("Simulated DPI:");
    JLabel localJLabel3 = new JLabel("Component/Text Scaling:");
    JLabel localJLabel4 = new JLabel("LayoutManager Scaling:");
    JLabel localJLabel5 = new JLabel("Visual Aids:");
    localJPanel.add(localJLabel1, "");
    localJPanel.add(localJComboBox1, "wrap");
    localJPanel.add(localJLabel2, "");
    localJPanel.add(localJComboBox2, "wrap");
    localJPanel.add(localJLabel3, "");
    localJPanel.add(localJRadioButton1, "");
    localJPanel.add(localJRadioButton2, "");
    localJPanel.add(localJRadioButton3, "wrap");
    localJPanel.add(localJLabel4, "");
    localJPanel.add(localJRadioButton4, "");
    localJPanel.add(localJRadioButton5, "wrap");
    localJPanel.add(localJLabel5, "");
    localJPanel.add(localJCheckBox, "");
    lockFont(new Component[] { localJComboBox2, localJRadioButton1, localJRadioButton2, localJRadioButton4, localJRadioButton3, localJComboBox1, localJCheckBox, localJPanel, localJLabel1, localJLabel2, localJRadioButton5, localJLabel3, localJLabel4, localJLabel5 });
    localJComboBox1.addActionListener(new ActionListener(localJComboBox1, localJComboBox2, localJRadioButton2, localJRadioButton1)
    {
      public void actionPerformed(ActionEvent paramActionEvent)
      {
        HiDPISimulator.GUI_BUF = null;
        try
        {
          Object localObject = this.val$lafCombo.getSelectedItem();
          this.val$dpiCombo.setSelectedIndex(5);
          if (localObject.equals("System"))
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
          else if (localObject.equals("Substance"))
            UIManager.setLookAndFeel(new SubstanceBusinessBlackSteelLookAndFeel());
          else if (localObject.equals("Ocean"))
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
          else
            JOptionPane.showMessageDialog(HiDPISimulator.APP_GUI_FRAME, "Nimbus will be included as soon as it is ready!");
          if (HiDPISimulator.ORIG_DEFAULTS != null)
          {
            Iterator localIterator = HiDPISimulator.ORIG_DEFAULTS.keySet().iterator();
            while (localIterator.hasNext())
            {
              String str = (String)localIterator.next();
              UIManager.put(str, null);
            }
          }
          HiDPISimulator.ORIG_DEFAULTS = null;
          if (UIManager.getLookAndFeel().getName().toLowerCase().contains("windows"))
            UIManager.put("TextArea.font", UIManager.getFont("TextField.font"));
          else
            UIManager.put("TextArea.font", null);
          SwingUtilities.updateComponentTreeUI(HiDPISimulator.APP_GUI_FRAME);
          HiDPISimulator.MAIN_SCROLL.setBorder(null);
          if (localObject.equals("System"))
          {
            if (this.val$scaleCompsLaf.isSelected())
              this.val$scaleCompsFonts.setSelected(true);
            this.val$scaleCompsLaf.setEnabled(false);
          }
          else if (localObject.equals("Substance"))
          {
            this.val$scaleCompsLaf.setEnabled(true);
          }
          else if (localObject.equals("Ocean"))
          {
            if (this.val$scaleCompsLaf.isSelected())
              this.val$scaleCompsFonts.setSelected(true);
            this.val$scaleCompsLaf.setEnabled(false);
          }
          HiDPISimulator.access$000(HiDPISimulator.CUR_DPI);
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
      }
    });
    localJCheckBox.addActionListener(new ActionListener(localJCheckBox)
    {
      public void actionPerformed(ActionEvent paramActionEvent)
      {
        HiDPISimulator.GUI_BUF = null;
        HiDPISimulator.PAINT_GHOSTED = this.val$ghostCheck.isSelected();
        HiDPISimulator.APP_GUI_FRAME.repaint();
      }
    });
    localJRadioButton4.addItemListener(new ItemListener(localJRadioButton4)
    {
      public void itemStateChanged(ItemEvent paramItemEvent)
      {
        HiDPISimulator.GUI_BUF = null;
        HiDPISimulator.SCALE_LAYOUT = this.val$scaleLayoutMig.isSelected();
        HiDPISimulator.access$000(HiDPISimulator.CUR_DPI);
      }
    });
    5 local5 = new ItemListener(localJRadioButton2, localJRadioButton1)
    {
      public void itemStateChanged(ItemEvent paramItemEvent)
      {
        if (paramItemEvent.getStateChange() == 1)
        {
          HiDPISimulator.GUI_BUF = null;
          HiDPISimulator.SCALE_LAF = this.val$scaleCompsLaf.isSelected();
          HiDPISimulator.SCALE_FONTS = this.val$scaleCompsFonts.isSelected();
          HiDPISimulator.access$000(HiDPISimulator.CUR_DPI);
        }
      }
    };
    localJRadioButton2.addItemListener(local5);
    localJRadioButton1.addItemListener(local5);
    localJRadioButton3.addItemListener(local5);
    localJComboBox2.addItemListener(new ItemListener(localJComboBox2)
    {
      public void itemStateChanged(ItemEvent paramItemEvent)
      {
        if (paramItemEvent.getStateChange() == 1)
        {
          HiDPISimulator.GUI_BUF = null;
          HiDPISimulator.CUR_DPI = Integer.parseInt(this.val$dpiCombo.getSelectedItem().toString().substring(0, 3).trim());
          HiDPISimulator.access$000(HiDPISimulator.CUR_DPI);
        }
      }
    });
    return localJPanel;
  }

  private static void lockFont(Component[] paramArrayOfComponent)
  {
    for (Component localComponent : paramArrayOfComponent)
    {
      Font localFont = localComponent.getFont();
      localComponent.setFont(localFont.deriveFont(localFont.getSize()));
    }
  }

  private static void revalidateGUI()
  {
    APP_GUI_FRAME.getContentPane().invalidate();
    APP_GUI_FRAME.repaint();
  }

  private static synchronized void setDPI(int paramInt)
  {
    float f1 = paramInt / Toolkit.getDefaultToolkit().getScreenResolution();
    TEXT_AREA.setSize(0, 0);
    PlatformDefaults.setHorizontalScaleFactor(Float.valueOf(0.1F));
    PlatformDefaults.setHorizontalScaleFactor(SCALE_LAYOUT ? Float.valueOf(f1) : null);
    PlatformDefaults.setVerticalScaleFactor(SCALE_LAYOUT ? Float.valueOf(f1) : null);
    float f2 = SCALE_FONTS ? paramInt / Toolkit.getDefaultToolkit().getScreenResolution() : 1.0F;
    Object localObject2;
    Object localObject3;
    if (ORIG_DEFAULTS == null)
    {
      ORIG_DEFAULTS = new HashMap();
      localObject1 = new HashSet(UIManager.getLookAndFeelDefaults().keySet());
      localIterator = ((Set)localObject1).iterator();
      while (localIterator.hasNext())
      {
        localObject2 = localIterator.next().toString();
        localObject3 = UIManager.get(localObject2);
        if ((localObject3 instanceof Font))
          ORIG_DEFAULTS.put(localObject2, (Font)localObject3);
      }
    }
    Object localObject1 = ORIG_DEFAULTS.entrySet();
    Iterator localIterator = ((Set)localObject1).iterator();
    while (localIterator.hasNext())
    {
      localObject2 = (Map.Entry)localIterator.next();
      localObject3 = (Font)((Map.Entry)localObject2).getValue();
      if (!SCALE_LAF)
        UIManager.put(((Map.Entry)localObject2).getKey(), new FontUIResource(((Font)localObject3).deriveFont(((Font)localObject3).getSize() * f2)));
      else
        UIManager.put(((Map.Entry)localObject2).getKey(), null);
    }
    if (SCALE_LAF)
      scaleSubstanceLAF(f1);
    else if (UIManager.getLookAndFeel().getName().toLowerCase().contains("substance"))
      scaleSubstanceLAF(1.0F);
    SwingUtilities.updateComponentTreeUI(HiDPIDEMO_PANEL);
    revalidateGUI();
  }

  private static void scaleSubstanceLAF(float paramFloat)
  {
    SubstanceLookAndFeel.setFontPolicy(SubstanceFontUtilities.getScaledFontPolicy(paramFloat));
    try
    {
      UIManager.setLookAndFeel(new SubstanceBusinessBlackSteelLookAndFeel());
    }
    catch (Exception localException)
    {
    }
    SwingUtilities.updateComponentTreeUI(APP_GUI_FRAME);
    MAIN_SCROLL.setBorder(null);
  }

  public static void main(String[] paramArrayOfString)
  {
    try
    {
      System.setProperty("apple.laf.useScreenMenuBar", "true");
      System.setProperty("com.apple.mrj.application.apple.menu.about.name", "HiDPI Simulator");
    }
    catch (Exception localException)
    {
    }
    PlatformDefaults.setDefaultHorizontalUnit(1);
    PlatformDefaults.setDefaultVerticalUnit(2);
    SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        try
        {
          UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
        if (UIManager.getLookAndFeel().getName().toLowerCase().contains("windows"))
          UIManager.put("TextArea.font", UIManager.getFont("TextField.font"));
        HiDPISimulator.APP_GUI_FRAME = new JFrame("Resolution Independence Simulator");
        JPanel localJPanel = new JPanel(new MigLayout("fill, insets 0px, nocache"));
        1 local1 = new JPanel(new MigLayout("fill, insets 0px, nocache"))
        {
          public void paintComponent(Graphics paramGraphics)
          {
            Graphics2D localGraphics2D = (Graphics2D)paramGraphics.create();
            localGraphics2D.setPaint(new GradientPaint(0.0F, 0.0F, new Color(20, 20, 30), 0.0F, getHeight(), new Color(90, 90, 110), false));
            localGraphics2D.fillRect(0, 0, getWidth(), getHeight());
            localGraphics2D.setFont(localGraphics2D.getFont().deriveFont(1, 13.0F));
            localGraphics2D.setPaint(Color.WHITE);
            localGraphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            localGraphics2D.drawString("Left panel shows the scaled version. Right side shows how this would look on a HiDPI screen. It should look the same as the original panel!", 10, 19);
            localGraphics2D.dispose();
          }
        };
        HiDPISimulator.HiDPIDEMO_PANEL = new HiDPIDemoPanel();
        HiDPISimulator.SIM_PANEL = HiDPISimulator.access$100();
        HiDPISimulator.MIRROR_PANEL = HiDPISimulator.access$200();
        HiDPISimulator.MAIN_SCROLL = new JScrollPane(local1);
        HiDPISimulator.MAIN_SCROLL.setBorder(null);
        local1.add(HiDPISimulator.HiDPIDEMO_PANEL, "align center center, split, span, width pref!");
        local1.add(HiDPISimulator.MIRROR_PANEL, "id mirror, gap 20px!, width pref!");
        localJPanel.add(HiDPISimulator.SIM_PANEL, "dock south");
        localJPanel.add(HiDPISimulator.MAIN_SCROLL, "dock center");
        Dimension localDimension = Toolkit.getDefaultToolkit().getScreenSize();
        HiDPISimulator.APP_GUI_FRAME.setContentPane(localJPanel);
        HiDPISimulator.APP_GUI_FRAME.setSize(Math.min(1240, localDimension.width), Math.min(950, localDimension.height - 30));
        HiDPISimulator.APP_GUI_FRAME.setDefaultCloseOperation(3);
        HiDPISimulator.APP_GUI_FRAME.setLocationRelativeTo(null);
        HiDPISimulator.APP_GUI_FRAME.setVisible(true);
      }
    });
  }
}

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     net.miginfocom.demo.HiDPISimulator
 * JD-Core Version:    0.6.0
 */