package net.miginfocom.demo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import javax.swing.AbstractListModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import net.miginfocom.layout.PlatformDefaults;
import net.miginfocom.swing.MigLayout;

class HiDPIDemoPanel extends JPanel
{
  public HiDPIDemoPanel()
  {
    super(new MigLayout());
    JLabel localJLabel1 = new JLabel("A Small Label:");
    JTextField localJTextField1 = new JTextField(10);
    JButton localJButton1 = new JButton("Cancel");
    JButton localJButton2 = new JButton("OK");
    JButton localJButton3 = new JButton("Help");
    JList localJList = new JList();
    JLabel localJLabel2 = new JLabel("Label:");
    JTextField localJTextField2 = new JTextField(10);
    JLabel localJLabel3 = new JLabel("This is another section");
    JSeparator localJSeparator = new JSeparator();
    JTextArea localJTextArea = new JTextArea("Some general text that takes place, doesn't offend anyone and fills some pixels.", 3, 30);
    JLabel localJLabel4 = new JLabel("Some Text Area");
    JLabel localJLabel5 = new JLabel("Some List:");
    JComboBox localJComboBox = new JComboBox();
    JCheckBox localJCheckBox = new JCheckBox("Orange");
    JScrollBar localJScrollBar1 = new JScrollBar(1);
    JScrollBar localJScrollBar2 = new JScrollBar(0, 30, 40, 0, 100);
    JRadioButton localJRadioButton = new JRadioButton("Apple");
    JProgressBar localJProgressBar = new JProgressBar();
    localJProgressBar.setValue(50);
    JSpinner localJSpinner = new JSpinner(new SpinnerNumberModel(50, 0, 100, 1));
    JTree localJTree = new JTree();
    localJTree.setOpaque(false);
    localJTree.setEnabled(false);
    localJList.setModel(new AbstractListModel()
    {
      String[] strings = { "Donald Duck", "Mickey Mouse", "Pluto", "Cartman" };

      public int getSize()
      {
        return this.strings.length;
      }

      public Object getElementAt(int paramInt)
      {
        return this.strings[paramInt];
      }
    });
    localJList.setVisibleRowCount(4);
    localJList.setBorder(new LineBorder(Color.GRAY));
    localJTextArea.setLineWrap(true);
    localJTextArea.setWrapStyleWord(true);
    localJTextArea.setBorder(new LineBorder(Color.GRAY));
    localJComboBox.setModel(new DefaultComboBoxModel(new String[] { "Text in ComboBox" }));
    localJCheckBox.setMargin(new Insets(0, 0, 0, 0));
    add(localJLabel1, "split, span");
    add(localJTextField1, "");
    add(localJLabel2, "gap unrelated");
    add(localJTextField2, "wrap");
    add(localJLabel3, "split, span");
    add(localJSeparator, "growx, span, gap 2, wrap unrelated");
    add(localJLabel4, "wrap 2");
    add(localJTextArea, "span, wmin 150, wrap unrelated");
    add(localJLabel5, "wrap 2");
    add(localJList, "split, span");
    add(localJScrollBar1, "growy");
    add(localJProgressBar, "width 80!");
    add(localJTree, "wrap unrelated");
    add(localJScrollBar2, "split, span, growx");
    add(localJSpinner, "wrap unrelated");
    add(localJComboBox, "span, split");
    add(localJRadioButton, "");
    add(localJCheckBox, "wrap unrelated");
    add(localJButton3, "split, span, tag help2");
    add(localJButton2, "tag ok");
    add(localJButton1, "tag cancel");
    HiDPISimulator.TEXT_AREA = localJTextArea;
  }

  public void paint(Graphics paramGraphics)
  {
    if (HiDPISimulator.GUI_BUF == null)
    {
      BufferedImage localBufferedImage = new BufferedImage(getWidth(), getHeight(), 2);
      Graphics2D localGraphics2D = localBufferedImage.createGraphics();
      super.paint(localGraphics2D);
      localGraphics2D.dispose();
      paramGraphics.drawImage(localBufferedImage, 0, 0, null);
      HiDPISimulator.GUI_BUF = localBufferedImage;
      if (HiDPISimulator.CUR_DPI == PlatformDefaults.getDefaultDPI())
        HiDPISimulator.ORIG_GUI_BUF = localBufferedImage;
      SwingUtilities.invokeLater(new Runnable()
      {
        public void run()
        {
          HiDPISimulator.MIRROR_PANEL.revalidate();
          HiDPISimulator.MIRROR_PANEL.repaint();
        }
      });
    }
    else
    {
      super.paint(paramGraphics);
    }
  }
}

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     net.miginfocom.demo.HiDPIDemoPanel
 * JD-Core Version:    0.6.0
 */