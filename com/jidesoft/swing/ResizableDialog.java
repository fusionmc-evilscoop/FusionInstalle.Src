/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import com.jidesoft.utils.PortingUtils;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dialog;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Frame;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.HeadlessException;
/*     */ import java.awt.KeyboardFocusManager;
/*     */ import java.awt.event.ComponentAdapter;
/*     */ import java.awt.event.ComponentEvent;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JRootPane;
/*     */ import javax.swing.KeyStroke;
/*     */ import javax.swing.border.Border;
/*     */ 
/*     */ public class ResizableDialog extends JDialog
/*     */   implements ResizableSupport
/*     */ {
/*     */   private ResizablePanel _resizablePanel;
/*     */   private boolean _routingKeyStrokes;
/*     */ 
/*     */   public ResizableDialog()
/*     */     throws HeadlessException
/*     */   {
/*  28 */     initComponents();
/*     */   }
/*     */ 
/*     */   public ResizableDialog(Frame owner) throws HeadlessException {
/*  32 */     super(owner);
/*  33 */     initComponents();
/*     */   }
/*     */ 
/*     */   public ResizableDialog(Frame owner, boolean modal) throws HeadlessException {
/*  37 */     super(owner, modal);
/*  38 */     initComponents();
/*     */   }
/*     */ 
/*     */   public ResizableDialog(Frame owner, String title) throws HeadlessException {
/*  42 */     super(owner, title);
/*  43 */     initComponents();
/*     */   }
/*     */ 
/*     */   public ResizableDialog(Frame owner, String title, boolean modal) throws HeadlessException {
/*  47 */     super(owner, title, modal);
/*  48 */     initComponents();
/*     */   }
/*     */ 
/*     */   public ResizableDialog(Frame owner, String title, boolean modal, GraphicsConfiguration gc) {
/*  52 */     super(owner, title, modal, gc);
/*  53 */     initComponents();
/*     */   }
/*     */ 
/*     */   public ResizableDialog(Dialog owner) throws HeadlessException {
/*  57 */     super(owner);
/*  58 */     initComponents();
/*     */   }
/*     */ 
/*     */   public ResizableDialog(Dialog owner, boolean modal) throws HeadlessException {
/*  62 */     super(owner, modal);
/*  63 */     initComponents();
/*     */   }
/*     */ 
/*     */   public ResizableDialog(Dialog owner, String title) throws HeadlessException {
/*  67 */     super(owner, title);
/*  68 */     initComponents();
/*     */   }
/*     */ 
/*     */   public ResizableDialog(Dialog owner, String title, boolean modal) throws HeadlessException {
/*  72 */     super(owner, title, modal);
/*  73 */     initComponents();
/*     */   }
/*     */ 
/*     */   public ResizableDialog(Dialog owner, String title, boolean modal, GraphicsConfiguration gc) throws HeadlessException {
/*  77 */     super(owner, title, modal, gc);
/*  78 */     initComponents();
/*     */   }
/*     */ 
/*     */   protected void initComponents()
/*     */   {
/*  85 */     setModal(false);
/*  86 */     setUndecorated(true);
/*     */ 
/*  88 */     this._resizablePanel = new ResizablePanel()
/*     */     {
/*     */       protected Resizable createResizable() {
/*  91 */         return new Resizable(this)
/*     */         {
/*     */           public void resizing(int resizeDir, int newX, int newY, int newW, int newH) {
/*  94 */             Container container = ResizableDialog.this.getContentPane();
/*  95 */             PortingUtils.setPreferredSize(container, new Dimension(newW, newH));
/*  96 */             if (ResizableDialog.this.isUndecorated()) {
/*  97 */               ResizableDialog.this.setBounds(newX, newY, newW, newH);
/*     */             }
/*  99 */             ResizableDialog.this.resizing();
/*     */           }
/*     */ 
/*     */           public void beginResizing(int resizeCorner)
/*     */           {
/* 105 */             super.beginResizing(resizeCorner);
/* 106 */             ResizableDialog.this.beginResizing();
/*     */           }
/*     */ 
/*     */           public void endResizing(int resizeCorner)
/*     */           {
/* 111 */             super.endResizing(resizeCorner);
/* 112 */             ResizableDialog.this.endResizing();
/*     */           }
/*     */ 
/*     */           public boolean isTopLevel()
/*     */           {
/* 117 */             return true;
/*     */           }
/*     */         };
/*     */       }
/*     */ 
/*     */       protected boolean processKeyBinding(KeyStroke ks, KeyEvent e, int condition, boolean pressed) {
/* 124 */         boolean processed = super.processKeyBinding(ks, e, condition, pressed);
/* 125 */         if ((processed) || (e.isConsumed()) || (!ResizableDialog.this.isRoutingKeyStrokes())) {
/* 126 */           return processed;
/*     */         }
/*     */ 
/* 129 */         if ((e.getSource() instanceof JComponent)) {
/* 130 */           JRootPane rootPane = ((JComponent)e.getSource()).getRootPane();
/* 131 */           Class componentClass = rootPane.getClass();
/* 132 */           while ((componentClass != JComponent.class) && (componentClass != null))
/* 133 */             componentClass = componentClass.getSuperclass();
/*     */           try
/*     */           {
/* 136 */             if (componentClass != null) {
/* 137 */               Method m = componentClass.getDeclaredMethod("processKeyBinding", new Class[] { KeyStroke.class, KeyEvent.class, Integer.TYPE, Boolean.TYPE });
/* 138 */               m.setAccessible(true);
/* 139 */               processed = ((Boolean)m.invoke(rootPane, new Object[] { ks, e, Integer.valueOf(2), Boolean.valueOf(pressed) })).booleanValue();
/*     */             }
/*     */           }
/*     */           catch (NoSuchMethodException e1) {
/* 143 */             e1.printStackTrace();
/*     */           }
/*     */           catch (InvocationTargetException e1) {
/* 146 */             e1.printStackTrace();
/*     */           }
/*     */           catch (IllegalAccessException e1) {
/* 149 */             e1.printStackTrace();
/*     */           }
/*     */         }
/* 152 */         if ((processed) || (e.isConsumed())) {
/* 153 */           return processed;
/*     */         }
/*     */ 
/* 156 */         Component routingParent = ResizableDialog.this.getRoutingComponent();
/* 157 */         if (routingParent == null) {
/* 158 */           return false;
/*     */         }
/* 160 */         KeyboardFocusManager.getCurrentKeyboardFocusManager().redispatchEvent(routingParent, e);
/*     */ 
/* 162 */         return e.isConsumed();
/*     */       }
/*     */     };
/* 165 */     setContentPane(this._resizablePanel);
/*     */ 
/* 168 */     addComponentListener(new ComponentAdapter()
/*     */     {
/*     */       public void componentResized(ComponentEvent e) {
/* 171 */         ResizableDialog.this._resizablePanel.setSize(ResizableDialog.this.getSize());
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   protected void beginResizing()
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void resizing()
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void endResizing()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void setBorder(Border border)
/*     */   {
/* 192 */     this._resizablePanel.setBorder(border);
/*     */   }
/*     */ 
/*     */   public Border getBorder()
/*     */   {
/* 202 */     return this._resizablePanel.getBorder();
/*     */   }
/*     */ 
/*     */   public Resizable getResizable()
/*     */   {
/* 211 */     return this._resizablePanel.getResizable();
/*     */   }
/*     */ 
/*     */   public Component getRoutingComponent() {
/* 215 */     return getOwner();
/*     */   }
/*     */ 
/*     */   public void setRoutingKeyStrokes(boolean routingKeyStrokes) {
/* 219 */     this._routingKeyStrokes = routingKeyStrokes;
/*     */   }
/*     */ 
/*     */   public boolean isRoutingKeyStrokes() {
/* 223 */     return this._routingKeyStrokes;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.ResizableDialog
 * JD-Core Version:    0.6.0
 */