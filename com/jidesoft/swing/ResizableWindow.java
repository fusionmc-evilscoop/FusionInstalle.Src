/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import com.jidesoft.utils.PortingUtils;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Frame;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.KeyboardFocusManager;
/*     */ import java.awt.Window;
/*     */ import java.awt.event.ComponentAdapter;
/*     */ import java.awt.event.ComponentEvent;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JRootPane;
/*     */ import javax.swing.JWindow;
/*     */ import javax.swing.KeyStroke;
/*     */ import javax.swing.border.Border;
/*     */ 
/*     */ public class ResizableWindow extends JWindow
/*     */   implements ResizableSupport
/*     */ {
/*     */   private ResizablePanel _resizablePanel;
/*     */   private boolean _routingKeyStrokes;
/*     */ 
/*     */   public ResizableWindow()
/*     */   {
/*  28 */     initComponents();
/*     */   }
/*     */ 
/*     */   public ResizableWindow(Frame owner) {
/*  32 */     super(owner);
/*  33 */     initComponents();
/*     */   }
/*     */ 
/*     */   public ResizableWindow(GraphicsConfiguration gc) {
/*  37 */     super(gc);
/*  38 */     initComponents();
/*     */   }
/*     */ 
/*     */   public ResizableWindow(Window owner) {
/*  42 */     super(owner);
/*  43 */     initComponents();
/*     */   }
/*     */ 
/*     */   public ResizableWindow(Window owner, GraphicsConfiguration gc) {
/*  47 */     super(owner, gc);
/*  48 */     initComponents();
/*     */   }
/*     */ 
/*     */   protected void initComponents()
/*     */   {
/*  55 */     this._resizablePanel = new ResizablePanel()
/*     */     {
/*     */       protected Resizable createResizable() {
/*  58 */         return new Resizable(this)
/*     */         {
/*     */           public void resizing(int resizeDir, int newX, int newY, int newW, int newH) {
/*  61 */             Container container = ResizableWindow.this.getContentPane();
/*  62 */             PortingUtils.setPreferredSize(container, new Dimension(newW, newH));
/*  63 */             ResizableWindow.this.setBounds(newX, newY, newW, newH);
/*  64 */             ResizableWindow.this.resizing();
/*     */           }
/*     */ 
/*     */           public void beginResizing(int resizeCorner)
/*     */           {
/*  70 */             super.beginResizing(resizeCorner);
/*  71 */             ResizableWindow.this.beginResizing();
/*     */           }
/*     */ 
/*     */           public void endResizing(int resizeCorner)
/*     */           {
/*  76 */             super.endResizing(resizeCorner);
/*  77 */             ResizableWindow.this.endResizing();
/*     */           }
/*     */ 
/*     */           public boolean isTopLevel()
/*     */           {
/*  83 */             return true;
/*     */           }
/*     */         };
/*     */       }
/*     */ 
/*     */       protected boolean processKeyBinding(KeyStroke ks, KeyEvent e, int condition, boolean pressed)
/*     */       {
/*  91 */         boolean processed = super.processKeyBinding(ks, e, condition, pressed);
/*  92 */         if ((processed) || (e.isConsumed()) || (!ResizableWindow.this.isRoutingKeyStrokes())) {
/*  93 */           return processed;
/*     */         }
/*     */ 
/*  96 */         if ((e.getSource() instanceof JComponent)) {
/*  97 */           JRootPane rootPane = ((JComponent)e.getSource()).getRootPane();
/*  98 */           Class componentClass = rootPane.getClass();
/*  99 */           while ((componentClass != JComponent.class) && (componentClass != null))
/* 100 */             componentClass = componentClass.getSuperclass();
/*     */           try
/*     */           {
/* 103 */             if (componentClass != null) {
/* 104 */               Method m = componentClass.getDeclaredMethod("processKeyBinding", new Class[] { KeyStroke.class, KeyEvent.class, Integer.TYPE, Boolean.TYPE });
/* 105 */               m.setAccessible(true);
/* 106 */               processed = ((Boolean)m.invoke(rootPane, new Object[] { ks, e, Integer.valueOf(2), Boolean.valueOf(pressed) })).booleanValue();
/*     */             }
/*     */           }
/*     */           catch (NoSuchMethodException e1) {
/* 110 */             e1.printStackTrace();
/*     */           }
/*     */           catch (InvocationTargetException e1) {
/* 113 */             e1.printStackTrace();
/*     */           }
/*     */           catch (IllegalAccessException e1) {
/* 116 */             e1.printStackTrace();
/*     */           }
/*     */         }
/* 119 */         if ((processed) || (e.isConsumed())) {
/* 120 */           return processed;
/*     */         }
/*     */ 
/* 123 */         Component routingParent = ResizableWindow.this.getRoutingComponent();
/* 124 */         if (routingParent == null) {
/* 125 */           return false;
/*     */         }
/* 127 */         KeyboardFocusManager.getCurrentKeyboardFocusManager().redispatchEvent(routingParent, e);
/*     */ 
/* 129 */         return e.isConsumed();
/*     */       }
/*     */     };
/* 132 */     setContentPane(this._resizablePanel);
/*     */ 
/* 135 */     addComponentListener(new ComponentAdapter()
/*     */     {
/*     */       public void componentResized(ComponentEvent e) {
/* 138 */         ResizableWindow.this._resizablePanel.setSize(ResizableWindow.this.getSize());
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
/* 159 */     this._resizablePanel.setBorder(border);
/*     */   }
/*     */ 
/*     */   public Border getBorder()
/*     */   {
/* 169 */     return this._resizablePanel.getBorder();
/*     */   }
/*     */ 
/*     */   public Resizable getResizable()
/*     */   {
/* 178 */     return this._resizablePanel.getResizable();
/*     */   }
/*     */ 
/*     */   public Component getRoutingComponent() {
/* 182 */     return getOwner();
/*     */   }
/*     */ 
/*     */   public void setRoutingKeyStrokes(boolean routingKeyStrokes) {
/* 186 */     this._routingKeyStrokes = routingKeyStrokes;
/*     */   }
/*     */ 
/*     */   public boolean isRoutingKeyStrokes() {
/* 190 */     return this._routingKeyStrokes;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.ResizableWindow
 * JD-Core Version:    0.6.0
 */