/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Cursor;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.geom.Area;
/*     */ import java.awt.image.BufferedImage;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JPopupMenu;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JViewport;
/*     */ import javax.swing.event.MouseInputAdapter;
/*     */ import javax.swing.event.MouseInputListener;
/*     */ 
/*     */ public class ScrollPaneOverview extends JComponent
/*     */ {
/*     */   private static final int MAX_SIZE = 400;
/*     */   private static final int MAX_SCALE = 20;
/*     */   private Component _owner;
/*     */   private JScrollPane _scrollPane;
/*     */   private Component _viewComponent;
/*     */   protected JPopupMenu _popupMenu;
/*     */   private BufferedImage _image;
/*     */   private Rectangle _startRectangle;
/*     */   private Rectangle _rectangle;
/*     */   private Point _startPoint;
/*     */   private double _scale;
/*     */   private int xOffset;
/*     */   private int yOffset;
/*  35 */   private Color _selectionBorder = Color.BLACK;
/*     */ 
/*     */   public ScrollPaneOverview(JScrollPane scrollPane, Component owner) {
/*  38 */     this._scrollPane = scrollPane;
/*  39 */     this._owner = owner;
/*  40 */     this._image = null;
/*  41 */     this._startRectangle = null;
/*  42 */     this._rectangle = null;
/*  43 */     this._startPoint = null;
/*  44 */     this._scale = 0.0D;
/*     */ 
/*  46 */     setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
/*  47 */     setCursor(Cursor.getPredefinedCursor(13));
/*  48 */     MouseInputListener mil = new MouseInputAdapter()
/*     */     {
/*     */       public void mousePressed(MouseEvent e)
/*     */       {
/*  53 */         if (ScrollPaneOverview.this._startPoint != null) {
/*  54 */           Point newPoint = e.getPoint();
/*  55 */           int deltaX = (int)((newPoint.x - ScrollPaneOverview.this._startPoint.x) / ScrollPaneOverview.this._scale);
/*  56 */           int deltaY = (int)((newPoint.y - ScrollPaneOverview.this._startPoint.y) / ScrollPaneOverview.this._scale);
/*  57 */           ScrollPaneOverview.this.scroll(deltaX, deltaY);
/*     */         }
/*  59 */         ScrollPaneOverview.access$002(ScrollPaneOverview.this, null);
/*  60 */         ScrollPaneOverview.access$302(ScrollPaneOverview.this, ScrollPaneOverview.this._rectangle);
/*     */       }
/*     */ 
/*     */       public void mouseMoved(MouseEvent e)
/*     */       {
/*  65 */         if (ScrollPaneOverview.this._startPoint == null) {
/*  66 */           ScrollPaneOverview.access$002(ScrollPaneOverview.this, new Point(ScrollPaneOverview.this._rectangle.x + ScrollPaneOverview.this._rectangle.width / 2, ScrollPaneOverview.this._rectangle.y + ScrollPaneOverview.this._rectangle.height / 2));
/*     */         }
/*  68 */         Point newPoint = e.getPoint();
/*  69 */         ScrollPaneOverview.this.moveRectangle(newPoint.x - ScrollPaneOverview.this._startPoint.x, newPoint.y - ScrollPaneOverview.this._startPoint.y);
/*     */       }
/*     */     };
/*  72 */     addMouseListener(mil);
/*  73 */     addMouseMotionListener(mil);
/*  74 */     this._popupMenu = new JPopupMenu();
/*  75 */     this._popupMenu.setLayout(new BorderLayout());
/*  76 */     this._popupMenu.add(this, "Center");
/*     */   }
/*     */ 
/*     */   public void setSelectionBorderColor(Color selectionBorder) {
/*  80 */     this._selectionBorder = selectionBorder;
/*     */   }
/*     */ 
/*     */   public Color getSelectionBorder() {
/*  84 */     return this._selectionBorder;
/*     */   }
/*     */ 
/*     */   protected void paintComponent(Graphics g)
/*     */   {
/*  89 */     if ((this._image == null) || (this._rectangle == null))
/*  90 */       return;
/*  91 */     Graphics2D g2d = (Graphics2D)g;
/*  92 */     Insets insets = getInsets();
/*  93 */     int xOffset = insets.left;
/*  94 */     int yOffset = insets.top;
/*     */ 
/*  96 */     g.setColor(this._scrollPane.getViewport().getView().getBackground());
/*  97 */     g.fillRect(0, 0, getWidth(), getHeight());
/*  98 */     g.drawImage(this._image, xOffset, yOffset, null);
/*     */ 
/* 100 */     int availableWidth = getWidth() - insets.left - insets.right;
/* 101 */     int availableHeight = getHeight() - insets.top - insets.bottom;
/* 102 */     Area area = new Area(new Rectangle(xOffset, yOffset, availableWidth, availableHeight));
/* 103 */     area.subtract(new Area(this._rectangle));
/* 104 */     g.setColor(new Color(255, 255, 255, 128));
/* 105 */     g2d.fill(area);
/*     */ 
/* 107 */     Color oldcolor = g.getColor();
/* 108 */     g.setColor(this._selectionBorder);
/* 109 */     g.drawRect(this._rectangle.x, this._rectangle.y, this._rectangle.width, this._rectangle.height);
/*     */ 
/* 111 */     g.setColor(oldcolor);
/*     */   }
/*     */ 
/*     */   public Dimension getPreferredSize()
/*     */   {
/* 116 */     if ((this._image == null) || (this._rectangle == null))
/* 117 */       return new Dimension();
/* 118 */     Insets insets = getInsets();
/* 119 */     return new Dimension(this._image.getWidth(null) + insets.left + insets.right, this._image.getHeight(null) + insets.top + insets.bottom);
/*     */   }
/*     */ 
/*     */   public void display() {
/* 123 */     this._viewComponent = this._scrollPane.getViewport().getView();
/* 124 */     if (this._viewComponent == null) {
/* 125 */       return;
/*     */     }
/*     */ 
/* 128 */     int maxSize = Math.max(400, Math.max(this._scrollPane.getWidth(), this._scrollPane.getHeight()) / 2);
/*     */ 
/* 130 */     int width = Math.min(this._viewComponent.getWidth(), this._scrollPane.getViewport().getWidth() * 20);
/* 131 */     if (width == 0) {
/* 132 */       return;
/*     */     }
/* 134 */     int height = Math.min(this._viewComponent.getHeight(), this._scrollPane.getViewport().getHeight() * 20);
/* 135 */     if (height == 0) {
/* 136 */       return;
/*     */     }
/* 138 */     double scaleX = maxSize / width;
/* 139 */     double scaleY = maxSize / height;
/*     */ 
/* 141 */     this._scale = Math.max(0.05D, Math.min(scaleX, scaleY));
/*     */ 
/* 143 */     this._image = new BufferedImage((int)(width * this._scale), (int)(height * this._scale), 1);
/* 144 */     Graphics2D g = this._image.createGraphics();
/*     */ 
/* 159 */     g.scale(this._scale, this._scale);
/* 160 */     g.setClip(this.xOffset, this.yOffset, width, height);
/*     */ 
/* 162 */     boolean wasDoubleBuffered = this._viewComponent.isDoubleBuffered();
/*     */     try {
/* 164 */       if ((this._viewComponent instanceof JComponent)) {
/* 165 */         ((JComponent)this._viewComponent).setDoubleBuffered(false);
/*     */       }
/* 167 */       this._viewComponent.paint(g);
/*     */     }
/*     */     finally {
/* 170 */       if ((this._viewComponent instanceof JComponent)) {
/* 171 */         ((JComponent)this._viewComponent).setDoubleBuffered(wasDoubleBuffered);
/*     */       }
/* 173 */       g.dispose();
/*     */     }
/*     */ 
/* 176 */     this._startRectangle = this._scrollPane.getViewport().getViewRect();
/* 177 */     Insets insets = getInsets();
/* 178 */     this._startRectangle.x = (int)(this._scale * this._startRectangle.x + insets.left);
/* 179 */     this._startRectangle.y = (int)(this._scale * this._startRectangle.y + insets.right);
/*     */     Rectangle tmp383_380 = this._startRectangle; tmp383_380.width = (int)(tmp383_380.width * this._scale);
/*     */     Rectangle tmp401_398 = this._startRectangle; tmp401_398.height = (int)(tmp401_398.height * this._scale);
/* 182 */     this._rectangle = this._startRectangle;
/* 183 */     Point centerPoint = new Point(this._rectangle.x + this._rectangle.width / 2, this._rectangle.y + this._rectangle.height / 2);
/* 184 */     showPopup(-centerPoint.x, -centerPoint.y, this._owner);
/*     */   }
/*     */ 
/*     */   protected void showPopup(int x, int y, Component owner)
/*     */   {
/* 197 */     this._popupMenu.show(owner, x, y);
/*     */   }
/*     */ 
/*     */   private void moveRectangle(int aDeltaX, int aDeltaY) {
/* 201 */     if (this._startRectangle == null)
/* 202 */       return;
/* 203 */     Insets insets = getInsets();
/* 204 */     Rectangle newRect = new Rectangle(this._startRectangle);
/* 205 */     newRect.x += aDeltaX;
/* 206 */     newRect.y += aDeltaY;
/* 207 */     newRect.x = Math.min(Math.max(newRect.x, insets.left), getWidth() - insets.right - newRect.width);
/* 208 */     newRect.y = Math.min(Math.max(newRect.y, insets.right), getHeight() - insets.bottom - newRect.height);
/* 209 */     Rectangle clip = new Rectangle();
/* 210 */     Rectangle.union(this._rectangle, newRect, clip);
/* 211 */     clip.grow(2, 2);
/* 212 */     this._rectangle = newRect;
/* 213 */     paintImmediately(clip);
/*     */   }
/*     */ 
/*     */   private void scroll(int aDeltaX, int aDeltaY) {
/* 217 */     JComponent component = (JComponent)this._scrollPane.getViewport().getView();
/* 218 */     Rectangle rect = component.getVisibleRect();
/* 219 */     rect.x += this.xOffset + aDeltaX;
/* 220 */     rect.y += this.yOffset + aDeltaY;
/* 221 */     component.scrollRectToVisible(rect);
/* 222 */     this._popupMenu.setVisible(false);
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.ScrollPaneOverview
 * JD-Core Version:    0.6.0
 */