/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import com.jidesoft.utils.PortingUtils;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.HeadlessException;
/*     */ import java.awt.KeyboardFocusManager;
/*     */ import java.awt.event.ComponentAdapter;
/*     */ import java.awt.event.ComponentEvent;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JRootPane;
/*     */ import javax.swing.KeyStroke;
/*     */ import javax.swing.border.Border;
/*     */ 
/*     */ public class ResizableFrame extends JFrame
/*     */   implements ResizableSupport
/*     */ {
/*     */   protected ResizablePanel _resizablePanel;
/*     */   private boolean _routingKeyStrokes;
/*     */ 
/*     */   public ResizableFrame()
/*     */     throws HeadlessException
/*     */   {
/*  28 */     initComponents();
/*     */   }
/*     */ 
/*     */   public ResizableFrame(GraphicsConfiguration gc) {
/*  32 */     super(gc);
/*  33 */     initComponents();
/*     */   }
/*     */ 
/*     */   public ResizableFrame(String title) throws HeadlessException {
/*  37 */     super(title);
/*  38 */     initComponents();
/*     */   }
/*     */ 
/*     */   public ResizableFrame(String title, GraphicsConfiguration gc) {
/*  42 */     super(title, gc);
/*  43 */     initComponents();
/*     */   }
/*     */ 
/*     */   protected void initComponents()
/*     */   {
/*  50 */     setUndecorated(true);
/*     */ 
/*  52 */     this._resizablePanel = new ResizablePanel()
/*     */     {
/*     */       protected Resizable createResizable() {
/*  55 */         return new Resizable(this)
/*     */         {
/*     */           public void resizing(int resizeDir, int newX, int newY, int newW, int newH) {
/*  58 */             Container container = ResizableFrame.this.getContentPane();
/*  59 */             PortingUtils.setPreferredSize(container, new Dimension(newW, newH));
/*  60 */             if (ResizableFrame.this.isUndecorated()) {
/*  61 */               ResizableFrame.this.setBounds(newX, newY, newW, newH);
/*     */             }
/*  63 */             ResizableFrame.this.resizing();
/*     */           }
/*     */ 
/*     */           public void beginResizing(int resizeCorner)
/*     */           {
/*  69 */             super.beginResizing(resizeCorner);
/*  70 */             ResizableFrame.this.beginResizing();
/*     */           }
/*     */ 
/*     */           public void endResizing(int resizeCorner)
/*     */           {
/*  75 */             super.endResizing(resizeCorner);
/*  76 */             ResizableFrame.this.endResizing();
/*     */           }
/*     */ 
/*     */           public boolean isTopLevel()
/*     */           {
/*  81 */             return true;
/*     */           }
/*     */         };
/*     */       }
/*     */ 
/*     */       protected boolean processKeyBinding(KeyStroke ks, KeyEvent e, int condition, boolean pressed) {
/*  88 */         boolean processed = super.processKeyBinding(ks, e, condition, pressed);
/*  89 */         if ((processed) || (e.isConsumed()) || (!ResizableFrame.this.isRoutingKeyStrokes())) {
/*  90 */           return processed;
/*     */         }
/*     */ 
/*  93 */         if ((e.getSource() instanceof JComponent)) {
/*  94 */           JRootPane rootPane = ((JComponent)e.getSource()).getRootPane();
/*  95 */           Class componentClass = rootPane.getClass();
/*  96 */           while ((componentClass != JComponent.class) && (componentClass != null))
/*  97 */             componentClass = componentClass.getSuperclass();
/*     */           try
/*     */           {
/* 100 */             if (componentClass != null) {
/* 101 */               Method m = componentClass.getDeclaredMethod("processKeyBinding", new Class[] { KeyStroke.class, KeyEvent.class, Integer.TYPE, Boolean.TYPE });
/* 102 */               m.setAccessible(true);
/* 103 */               processed = ((Boolean)m.invoke(rootPane, new Object[] { ks, e, Integer.valueOf(2), Boolean.valueOf(pressed) })).booleanValue();
/*     */             }
/*     */           }
/*     */           catch (NoSuchMethodException e1) {
/* 107 */             e1.printStackTrace();
/*     */           }
/*     */           catch (InvocationTargetException e1) {
/* 110 */             e1.printStackTrace();
/*     */           }
/*     */           catch (IllegalAccessException e1) {
/* 113 */             e1.printStackTrace();
/*     */           }
/*     */         }
/* 116 */         if ((processed) || (e.isConsumed())) {
/* 117 */           return processed;
/*     */         }
/*     */ 
/* 120 */         Component routingParent = ResizableFrame.this.getRoutingComponent();
/* 121 */         if (routingParent == null) {
/* 122 */           return false;
/*     */         }
/* 124 */         KeyboardFocusManager.getCurrentKeyboardFocusManager().redispatchEvent(routingParent, e);
/*     */ 
/* 126 */         return e.isConsumed();
/*     */       }
/*     */     };
/* 129 */     setContentPane(this._resizablePanel);
/*     */ 
/* 132 */     addComponentListener(new ComponentAdapter()
/*     */     {
/*     */       public void componentResized(ComponentEvent e) {
/* 135 */         ResizableFrame.this._resizablePanel.setSize(ResizableFrame.this.getSize());
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
/* 156 */     this._resizablePanel.setBorder(border);
/*     */   }
/*     */ 
/*     */   public Border getBorder()
/*     */   {
/* 166 */     return this._resizablePanel.getBorder();
/*     */   }
/*     */ 
/*     */   public Resizable getResizable()
/*     */   {
/* 175 */     return this._resizablePanel.getResizable();
/*     */   }
/*     */ 
/*     */   public Component getRoutingComponent() {
/* 179 */     return getOwner();
/*     */   }
/*     */ 
/*     */   public void setRoutingKeyStrokes(boolean routingKeyStrokes) {
/* 183 */     this._routingKeyStrokes = routingKeyStrokes;
/*     */   }
/*     */ 
/*     */   public boolean isRoutingKeyStrokes() {
/* 187 */     return this._routingKeyStrokes;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.ResizableFrame
 * JD-Core Version:    0.6.0
 */