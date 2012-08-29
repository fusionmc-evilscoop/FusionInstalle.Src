/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.KeyboardFocusManager;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.swing.InputMap;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.KeyStroke;
/*     */ import javax.swing.SwingUtilities;
/*     */ 
/*     */ public class ArrowKeyNavigationSupport
/*     */ {
/*  37 */   private int[] _keyCode = { 37, 39, 38, 40 };
/*     */   private Class[] _componentTypes;
/*     */   public static final String CLIENT_PROPERTY_ARROWKEY_NAVIGATION_SUPPORT = "ArrowKeyNavigationSupport.previousAction";
/*     */ 
/*     */   public ArrowKeyNavigationSupport()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ArrowKeyNavigationSupport(Class[] componentTypes)
/*     */   {
/*  45 */     this._componentTypes = componentTypes;
/*     */   }
/*     */ 
/*     */   public ArrowKeyNavigationSupport(int[] keyCodes) {
/*  49 */     this._keyCode = keyCodes;
/*     */   }
/*     */ 
/*     */   public ArrowKeyNavigationSupport(Class[] componentTypes, int[] keyCode) {
/*  53 */     this._keyCode = keyCode;
/*  54 */     this._componentTypes = componentTypes;
/*     */   }
/*     */ 
/*     */   public void install(JComponent container)
/*     */   {
/*  63 */     for (int keyCode : this._keyCode) {
/*  64 */       InputMap inputMap = container.getInputMap(1);
/*  65 */       KeyStroke keyStroke = KeyStroke.getKeyStroke(keyCode, 0);
/*  66 */       Object actionName = inputMap.get(keyStroke);
/*  67 */       if (actionName != null) {
/*  68 */         container.putClientProperty("ArrowKeyNavigationSupport.previousAction", actionName);
/*     */       }
/*  70 */       container.registerKeyboardAction(new NavigationAction(container, keyCode), "ArrowKeyNavigation " + keyCode, keyStroke, 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void uninstall(JComponent container)
/*     */   {
/*  80 */     for (int keyCode : this._keyCode) {
/*  81 */       Object actionName = container.getClientProperty("ArrowKeyNavigationSupport.previousAction");
/*  82 */       if (actionName != null) {
/*  83 */         InputMap inputMap = container.getInputMap(1);
/*  84 */         KeyStroke keyStroke = KeyStroke.getKeyStroke(keyCode, 0);
/*  85 */         inputMap.put(keyStroke, actionName);
/*     */       }
/*     */       else {
/*  88 */         container.unregisterKeyboardAction(KeyStroke.getKeyStroke(keyCode, 0));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private class NavigationAction implements ActionListener {
/*     */     private JComponent _parent;
/*     */     private int _keyCode;
/*     */ 
/*     */     public NavigationAction(JComponent c, int key) {
/*  98 */       this._parent = c;
/*  99 */       this._keyCode = key;
/*     */     }
/*     */ 
/*     */     public void actionPerformed(ActionEvent e) {
/* 103 */       List rects = new ArrayList();
/* 104 */       List components = new ArrayList();
/* 105 */       JideSwingUtilities.setRecursively(this._parent, new JideSwingUtilities.Handler(rects, components) {
/*     */         public void postAction(Component c) {
/*     */         }
/*     */ 
/*     */         public void action(Component c) {
/* 110 */           if (ArrowKeyNavigationSupport.this._componentTypes != null) {
/* 111 */             boolean allowed = false;
/* 112 */             for (Class allowedType : ArrowKeyNavigationSupport.this._componentTypes) {
/* 113 */               if (allowedType.isAssignableFrom(c.getClass())) {
/* 114 */                 allowed = true;
/* 115 */                 break;
/*     */               }
/*     */             }
/* 118 */             if (!allowed) return;
/*     */           }
/* 120 */           Rectangle bounds = c.getBounds();
/* 121 */           this.val$rects.add(SwingUtilities.convertRectangle(c, bounds, ArrowKeyNavigationSupport.NavigationAction.this._parent));
/* 122 */           this.val$components.add(c);
/*     */         }
/*     */ 
/*     */         public boolean condition(Component c) {
/* 126 */           return (c.isVisible()) && (c.isDisplayable()) && (c.isFocusable()) && (c.isEnabled());
/*     */         }
/*     */       });
/* 129 */       Component owner = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
/* 130 */       Component c = null;
/* 131 */       switch (this._keyCode) {
/*     */       case 39:
/* 133 */         c = findComponentToRight(owner, rects, components);
/* 134 */         break;
/*     */       case 37:
/* 136 */         c = findComponentToLeft(owner, rects, components);
/* 137 */         break;
/*     */       case 38:
/* 139 */         c = findComponentToAbove(owner, rects, components);
/* 140 */         break;
/*     */       case 40:
/* 142 */         c = findComponentToBelow(owner, rects, components);
/*     */       }
/*     */ 
/* 145 */       if (c != null) c.requestFocusInWindow(); 
/*     */     }
/*     */ 
/*     */     private Component findComponentToRight(Component c, List<Rectangle> rects, List<Component> components)
/*     */     {
/* 149 */       int max = 2147483647;
/* 150 */       Component found = null;
/* 151 */       Rectangle src = SwingUtilities.convertRectangle(c, c.getBounds(), this._parent);
/* 152 */       for (int i = 0; i < rects.size(); i++) {
/* 153 */         Rectangle dst = (Rectangle)rects.get(i);
/* 154 */         if (dst.x <= src.x + src.width) {
/*     */           continue;
/*     */         }
/* 157 */         if (dst.y + dst.height < src.y) {
/*     */           continue;
/*     */         }
/* 160 */         if (dst.y > src.y + src.height)
/*     */         {
/*     */           continue;
/*     */         }
/* 164 */         int dist = dst.x - src.x - src.width;
/* 165 */         if (dist < max) {
/* 166 */           max = dist;
/* 167 */           found = (Component)components.get(i);
/*     */         }
/*     */       }
/*     */ 
/* 171 */       return found;
/*     */     }
/*     */ 
/*     */     private Component findComponentToBelow(Component c, List<Rectangle> rects, List<Component> components) {
/* 175 */       int max = 2147483647;
/* 176 */       Component found = null;
/* 177 */       Rectangle src = SwingUtilities.convertRectangle(c, c.getBounds(), this._parent);
/* 178 */       for (int i = 0; i < rects.size(); i++) {
/* 179 */         Rectangle dst = (Rectangle)rects.get(i);
/* 180 */         if (dst.y <= src.y + src.height) {
/*     */           continue;
/*     */         }
/* 183 */         if (dst.x + dst.width < src.x) {
/*     */           continue;
/*     */         }
/* 186 */         if (dst.x > src.x + src.width)
/*     */         {
/*     */           continue;
/*     */         }
/* 190 */         int dist = dst.y - src.y - src.height;
/* 191 */         if (dist < max) {
/* 192 */           max = dist;
/* 193 */           found = (Component)components.get(i);
/*     */         }
/*     */       }
/*     */ 
/* 197 */       return found;
/*     */     }
/*     */ 
/*     */     private Component findComponentToLeft(Component c, List<Rectangle> rects, List<Component> components) {
/* 201 */       int max = 2147483647;
/* 202 */       Component found = null;
/* 203 */       Rectangle src = SwingUtilities.convertRectangle(c, c.getBounds(), this._parent);
/* 204 */       for (int i = 0; i < rects.size(); i++) {
/* 205 */         Rectangle dst = (Rectangle)rects.get(i);
/* 206 */         if (dst.x + dst.width >= src.x) {
/*     */           continue;
/*     */         }
/* 209 */         if (dst.y + dst.height < src.y) {
/*     */           continue;
/*     */         }
/* 212 */         if (dst.y > src.y + src.height)
/*     */         {
/*     */           continue;
/*     */         }
/* 216 */         int dist = src.x - dst.x - dst.width;
/* 217 */         if (dist < max) {
/* 218 */           max = dist;
/* 219 */           found = (Component)components.get(i);
/*     */         }
/*     */       }
/*     */ 
/* 223 */       return found;
/*     */     }
/*     */ 
/*     */     private Component findComponentToAbove(Component c, List<Rectangle> rects, List<Component> components) {
/* 227 */       int max = 2147483647;
/* 228 */       Component found = null;
/* 229 */       Rectangle src = SwingUtilities.convertRectangle(c, c.getBounds(), this._parent);
/* 230 */       for (int i = 0; i < rects.size(); i++) {
/* 231 */         Rectangle dst = (Rectangle)rects.get(i);
/* 232 */         if (dst.y + dst.height >= src.y) {
/*     */           continue;
/*     */         }
/* 235 */         if (dst.x + dst.width < src.x) {
/*     */           continue;
/*     */         }
/* 238 */         if (dst.x > src.x + src.width)
/*     */         {
/*     */           continue;
/*     */         }
/* 242 */         int dist = src.y - dst.y - dst.height;
/* 243 */         if (dist < max) {
/* 244 */           max = dist;
/* 245 */           found = (Component)components.get(i);
/*     */         }
/*     */       }
/*     */ 
/* 249 */       return found;
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.ArrowKeyNavigationSupport
 * JD-Core Version:    0.6.0
 */