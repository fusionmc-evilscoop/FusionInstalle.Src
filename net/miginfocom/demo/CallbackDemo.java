package net.miginfocom.demo;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.IdentityHashMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import net.miginfocom.layout.BoundSize;
import net.miginfocom.layout.ComponentWrapper;
import net.miginfocom.layout.LayoutCallback;
import net.miginfocom.layout.UnitValue;
import net.miginfocom.swing.MigLayout;

public class CallbackDemo extends JFrame
  implements ActionListener, MouseMotionListener, MouseListener
{
  private final Timer repaintTimer = new Timer(20, new ActionListener()
  {
    public void actionPerformed(ActionEvent paramActionEvent)
    {
      ((JPanel)CallbackDemo.this.getContentPane()).revalidate();
    }
  });
  private final IdentityHashMap<Object, Long> pressMap = new IdentityHashMap();
  private Point mousePos = null;
  private static Font[] FONTS = new Font[120];

  public CallbackDemo()
  {
    super("MiG Layout Callback Demo");
    MigLayout localMigLayout = new MigLayout("align center bottom, insets 30");
    2 local2 = new JPanel(localMigLayout)
    {
      protected void paintComponent(Graphics paramGraphics)
      {
        ((Graphics2D)paramGraphics).setPaint(new GradientPaint(0.0F, getHeight() / 2, Color.WHITE, 0.0F, getHeight(), new Color(240, 238, 235)));
        paramGraphics.fillRect(0, 0, getWidth(), getHeight());
      }
    };
    setContentPane(local2);
    localMigLayout.addLayoutCallback(new LayoutCallback(local2)
    {
      public BoundSize[] getSize(ComponentWrapper paramComponentWrapper)
      {
        if ((paramComponentWrapper.getComponent() instanceof JButton))
        {
          Component localComponent = (Component)paramComponentWrapper.getComponent();
          Point localPoint = CallbackDemo.this.mousePos != null ? SwingUtilities.convertPoint(this.val$panel, CallbackDemo.this.mousePos, localComponent) : new Point(-1000, -1000);
          float f = (float)Math.sqrt(Math.pow(Math.abs(localPoint.x - localComponent.getWidth() / 2.0F), 2.0D) + Math.pow(Math.abs(localPoint.y - localComponent.getHeight() / 2.0F), 2.0D));
          f = Math.max(2.0F - f / 200.0F, 1.0F);
          return new BoundSize[] { new BoundSize(new UnitValue(70.0F * f), ""), new BoundSize(new UnitValue(70.0F * f), "") };
        }
        return null;
      }

      public void correctBounds(ComponentWrapper paramComponentWrapper)
      {
        Long localLong = (Long)CallbackDemo.this.pressMap.get(paramComponentWrapper.getComponent());
        if (localLong != null)
        {
          long l = System.nanoTime() - localLong.longValue();
          double d = 100.0D - l / 100000000.0D;
          int i = (int)Math.round(Math.abs(Math.sin(l / 300000000.0D) * d));
          paramComponentWrapper.setBounds(paramComponentWrapper.getX(), paramComponentWrapper.getY() - i, paramComponentWrapper.getWidth(), paramComponentWrapper.getHeight());
          if (d < 0.5D)
          {
            CallbackDemo.this.pressMap.remove(paramComponentWrapper.getComponent());
            if (CallbackDemo.this.pressMap.size() == 0)
              CallbackDemo.this.repaintTimer.stop();
          }
        }
      }
    });
    for (int i = 0; i < 10; i++)
      local2.add(createButton(i), "aligny 0.8al");
    JLabel localJLabel = new JLabel("Can't you just feel the urge to press one of those Swing JButtons?");
    localJLabel.setFont(new Font("verdana", 0, 24));
    localJLabel.setForeground(new Color(150, 150, 150));
    local2.add(localJLabel, "pos 0.5al 0.2al");
    local2.addMouseMotionListener(this);
    local2.addMouseListener(this);
  }

  private JButton createButton(int paramInt)
  {
    4 local4 = new JButton(String.valueOf("MIG LAYOUT".charAt(paramInt)))
    {
      public Font getFont()
      {
        if (CallbackDemo.FONTS[0] == null)
          for (int i = 0; i < CallbackDemo.FONTS.length; i++)
            CallbackDemo.FONTS[i] = new Font("tahoma", 0, i);
        return CallbackDemo.FONTS[(getWidth() >> 1)];
      }
    };
    local4.setForeground(new Color(100, 100, 100));
    local4.setFocusPainted(false);
    local4.addMouseMotionListener(this);
    local4.addActionListener(this);
    local4.setMargin(new Insets(0, 0, 0, 0));
    return local4;
  }

  public void mouseDragged(MouseEvent paramMouseEvent)
  {
  }

  public void mouseMoved(MouseEvent paramMouseEvent)
  {
    if ((paramMouseEvent.getSource() instanceof JButton))
      this.mousePos = SwingUtilities.convertPoint((Component)paramMouseEvent.getSource(), paramMouseEvent.getPoint(), getContentPane());
    else
      this.mousePos = paramMouseEvent.getPoint();
    ((JPanel)getContentPane()).revalidate();
  }

  public void mousePressed(MouseEvent paramMouseEvent)
  {
  }

  public void mouseReleased(MouseEvent paramMouseEvent)
  {
  }

  public void mouseClicked(MouseEvent paramMouseEvent)
  {
  }

  public void mouseEntered(MouseEvent paramMouseEvent)
  {
  }

  public void mouseExited(MouseEvent paramMouseEvent)
  {
    this.mousePos = null;
    ((JPanel)getContentPane()).revalidate();
  }

  public void actionPerformed(ActionEvent paramActionEvent)
  {
    this.pressMap.put(paramActionEvent.getSource(), Long.valueOf(System.nanoTime()));
    this.repaintTimer.start();
  }

  public static void main(String[] paramArrayOfString)
  {
    try
    {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch (Exception localException)
    {
    }
    CallbackDemo localCallbackDemo = new CallbackDemo();
    localCallbackDemo.setDefaultCloseOperation(3);
    localCallbackDemo.setSize(970, 500);
    localCallbackDemo.setLocationRelativeTo(null);
    localCallbackDemo.setVisible(true);
  }
}

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     net.miginfocom.demo.CallbackDemo
 * JD-Core Version:    0.6.0
 */