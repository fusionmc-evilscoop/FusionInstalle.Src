/*     */ package com.jidesoft.plaf.basic;
/*     */ 
/*     */ import com.jidesoft.plaf.UIDefaultsLookup;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import javax.swing.AbstractButton;
/*     */ import javax.swing.ButtonModel;
/*     */ import javax.swing.InputMap;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JPopupMenu;
/*     */ import javax.swing.KeyStroke;
/*     */ import javax.swing.MenuElement;
/*     */ import javax.swing.MenuSelectionManager;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.plaf.ComponentInputMapUIResource;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicButtonListener;
/*     */ 
/*     */ public class BasicJideButtonListener extends BasicButtonListener
/*     */ {
/*  16 */   private boolean _mouseOver = false;
/*     */ 
/*     */   public BasicJideButtonListener(AbstractButton b) {
/*  19 */     super(b);
/*     */   }
/*     */ 
/*     */   public void mouseEntered(MouseEvent e)
/*     */   {
/*  24 */     AbstractButton b = (AbstractButton)e.getSource();
/*  25 */     ButtonModel model = b.getModel();
/*  26 */     if (b.isRolloverEnabled()) {
/*  27 */       model.setRollover(true);
/*     */     }
/*     */ 
/*  30 */     this._mouseOver = true;
/*     */ 
/*  32 */     if (model.isPressed())
/*  33 */       model.setArmed(true);
/*     */   }
/*     */ 
/*     */   public void propertyChange(PropertyChangeEvent e)
/*     */   {
/*  38 */     super.propertyChange(e);
/*  39 */     String prop = e.getPropertyName();
/*  40 */     if (("buttonStyle".equals(prop)) || ("opaque".equals(prop)) || ("contentAreaFilled".equals(prop)))
/*     */     {
/*  44 */       AbstractButton b = (AbstractButton)e.getSource();
/*  45 */       b.repaint();
/*     */     }
/*  47 */     else if (("orientation".equals(prop)) || ("hideActionText".equals(prop)))
/*     */     {
/*  49 */       AbstractButton b = (AbstractButton)e.getSource();
/*  50 */       b.invalidate();
/*  51 */       b.repaint();
/*     */     }
/*  53 */     else if (("verticalTextPosition".equals(prop)) || ("horizontalTextPosition".equals(prop)))
/*     */     {
/*  55 */       AbstractButton b = (AbstractButton)e.getSource();
/*  56 */       b.updateUI();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void mouseClicked(MouseEvent e)
/*     */   {
/*  65 */     super.mouseClicked(e);
/*  66 */     cancelMenuIfNecessary(e);
/*     */   }
/*     */ 
/*     */   public void mouseReleased(MouseEvent e)
/*     */   {
/*  71 */     AbstractButton b = (AbstractButton)e.getSource();
/*  72 */     ButtonModel model = b.getModel();
/*  73 */     if ((b.contains(e.getPoint())) && 
/*  74 */       (b.isRolloverEnabled())) {
/*  75 */       model.setRollover(true);
/*     */     }
/*     */ 
/*  78 */     if (!this._mouseOver)
/*     */     {
/*  80 */       model.setArmed(false);
/*  81 */       model.setPressed(false);
/*     */     }
/*  83 */     super.mouseReleased(e);
/*  84 */     cancelMenuIfNecessary(e);
/*     */   }
/*     */ 
/*     */   private void cancelMenuIfNecessary(MouseEvent e)
/*     */   {
/*  93 */     AbstractButton b = (AbstractButton)e.getSource();
/*  94 */     MenuSelectionManager manager = MenuSelectionManager.defaultManager();
/*  95 */     MenuElement[] menuElements = manager.getSelectedPath();
/*  96 */     for (int i = menuElements.length - 1; i >= 0; i--) {
/*  97 */       MenuElement menuElement = menuElements[i];
/*  98 */       if (((menuElement instanceof JPopupMenu)) && (((JPopupMenu)menuElement).isAncestorOf(b))) {
/*  99 */         b.getModel().setPressed(false);
/* 100 */         b.getModel().setArmed(false);
/* 101 */         b.getModel().setRollover(false);
/* 102 */         if (Boolean.FALSE.equals(b.getClientProperty("JideButton.hidePopupMenu"))) break;
/* 103 */         manager.clearSelectedPath(); break;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void mouseExited(MouseEvent e)
/*     */   {
/* 112 */     AbstractButton b = (AbstractButton)e.getSource();
/* 113 */     ButtonModel model = b.getModel();
/* 114 */     if (b.isRolloverEnabled()) {
/* 115 */       model.setRollover(false);
/*     */     }
/* 117 */     model.setArmed(false);
/* 118 */     this._mouseOver = false;
/*     */   }
/*     */ 
/*     */   void updateMnemonicBinding(AbstractButton b)
/*     */   {
/* 127 */     int m = b.getMnemonic();
/* 128 */     if (m != 0) {
/* 129 */       InputMap map = SwingUtilities.getUIInputMap(b, 2);
/*     */ 
/* 132 */       if (map == null) {
/* 133 */         map = new ComponentInputMapUIResource(b);
/* 134 */         SwingUtilities.replaceUIInputMap(b, 2, map);
/*     */       }
/*     */ 
/* 137 */       map.clear();
/* 138 */       map.put(KeyStroke.getKeyStroke(m, 8, false), "pressed");
/*     */ 
/* 140 */       map.put(KeyStroke.getKeyStroke(m, 8, true), "released");
/*     */ 
/* 142 */       map.put(KeyStroke.getKeyStroke(m, 0, true), "released");
/*     */     }
/*     */     else {
/* 145 */       InputMap map = SwingUtilities.getUIInputMap(b, 2);
/*     */ 
/* 147 */       if (map != null)
/* 148 */         map.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */   static Object getUIOfType(ComponentUI ui, Class clazz)
/*     */   {
/* 161 */     if (clazz.isInstance(ui)) {
/* 162 */       return ui;
/*     */     }
/* 164 */     return null;
/*     */   }
/*     */ 
/*     */   public InputMap getInputMap(int condition, JComponent c)
/*     */   {
/* 176 */     if (condition == 0) {
/* 177 */       BasicJideButtonUI ui = (BasicJideButtonUI)getUIOfType(((AbstractButton)c).getUI(), BasicJideButtonUI.class);
/*     */ 
/* 179 */       if (ui != null) {
/* 180 */         return (InputMap)UIDefaultsLookup.get(ui.getPropertyPrefix() + "focusInputMap");
/*     */       }
/*     */     }
/* 183 */     return null;
/*     */   }
/*     */ 
/*     */   public static void loadActionMap(LazyActionMap map)
/*     */   {
/* 229 */     map.put(new Actions("pressed"));
/* 230 */     map.put(new Actions("released"));
/*     */   }
/*     */ 
/*     */   public void installKeyboardActions(JComponent c)
/*     */   {
/* 236 */     AbstractButton b = (AbstractButton)c;
/*     */ 
/* 238 */     updateMnemonicBinding(b);
/*     */ 
/* 240 */     LazyActionMap.installLazyActionMap(c, BasicJideButtonListener.class, "JideButton.actionMap");
/*     */ 
/* 243 */     InputMap km = getInputMap(0, c);
/*     */ 
/* 245 */     SwingUtilities.replaceUIInputMap(c, 0, km);
/*     */   }
/*     */ 
/*     */   private static class Actions extends UIAction
/*     */   {
/*     */     private static final String PRESS = "pressed";
/*     */     private static final String RELEASE = "released";
/*     */ 
/*     */     Actions(String name)
/*     */     {
/* 195 */       super();
/*     */     }
/*     */ 
/*     */     public void actionPerformed(ActionEvent e) {
/* 199 */       AbstractButton b = (AbstractButton)e.getSource();
/* 200 */       String key = getName();
/* 201 */       if ("pressed".equals(key)) {
/* 202 */         ButtonModel model = b.getModel();
/* 203 */         model.setArmed(true);
/* 204 */         model.setPressed(true);
/* 205 */         if ((!b.hasFocus()) && (b.isRequestFocusEnabled())) {
/* 206 */           b.requestFocus();
/*     */         }
/*     */       }
/* 209 */       else if ("released".equals(key)) {
/* 210 */         ButtonModel model = b.getModel();
/* 211 */         model.setPressed(false);
/* 212 */         model.setArmed(false);
/*     */       }
/*     */     }
/*     */ 
/*     */     public boolean isEnabled(Object sender)
/*     */     {
/* 218 */       return (sender == null) || (!(sender instanceof AbstractButton)) || (((AbstractButton)sender).getModel().isEnabled());
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.basic.BasicJideButtonListener
 * JD-Core Version:    0.6.0
 */