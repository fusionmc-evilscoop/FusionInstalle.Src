/*     */ package com.jidesoft.dialog;
/*     */ 
/*     */ import com.jidesoft.swing.JideSwingUtilities;
/*     */ import com.jidesoft.swing.MultilineLabel;
/*     */ import com.jidesoft.utils.SecurityUtils;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Rectangle;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ 
/*     */ public class BannerPanel extends JPanel
/*     */ {
/*     */   protected String _title;
/*     */   protected String _subtitle;
/*     */   protected ImageIcon _titleIcon;
/*     */   public static final String TITLE_PROPERTY = "title";
/*     */   public static final String SUBTITLE_PROPERTY = "subTitle";
/*     */   public static final String ICON_PROPERTY = "icon";
/*     */   public static final String ICON_COMPONENT_PROPERTY = "iconComponent";
/*     */   public static final String PROPERTY_TITLE_FONT = "titleFont";
/*     */   public static final String PROPERTY_SUBTITLE_FONT = "subTitleFont";
/*     */   public static final String PROPERTY_TITLE_ICON_LOCATION = "titleIconLocation";
/*     */   private JComponent _iconComponent;
/*  57 */   protected int _subTitleIndent = 20;
/*     */   protected Font _titleFont;
/*     */   protected Color _titleColor;
/*     */   protected Font _subTitleFont;
/*     */   protected Color _subTitleColor;
/*     */   protected Paint _backgroundPaint;
/*     */   protected PropertyChangeListener _propertyListener;
/*     */   private JLabel _titleLabel;
/*     */   private MultilineLabel _subtitleLabel;
/*     */   protected Color _startColor;
/*     */   protected Color _endColor;
/*     */   protected boolean _isVertical;
/*  73 */   private int _titleIconLocation = 11;
/*     */   public JPanel _textPanel;
/*     */ 
/*     */   public BannerPanel()
/*     */   {
/*  80 */     lazyInitialize();
/*     */   }
/*     */ 
/*     */   public BannerPanel(String title)
/*     */   {
/*  89 */     setTitle(title);
/*  90 */     lazyInitialize();
/*     */   }
/*     */ 
/*     */   public BannerPanel(String title, String subtitle)
/*     */   {
/* 100 */     setTitle(title);
/* 101 */     setSubtitle(subtitle);
/* 102 */     lazyInitialize();
/*     */   }
/*     */ 
/*     */   public BannerPanel(String title, String subtitle, ImageIcon titleIcon)
/*     */   {
/* 113 */     setTitle(title);
/* 114 */     setSubtitle(subtitle);
/* 115 */     setTitleIcon(titleIcon);
/* 116 */     lazyInitialize();
/*     */   }
/*     */ 
/*     */   public BannerPanel(String title, String subtitle, JComponent iconComponent)
/*     */   {
/* 128 */     setTitle(title);
/* 129 */     setSubtitle(subtitle);
/* 130 */     this._iconComponent = iconComponent;
/* 131 */     lazyInitialize();
/*     */   }
/*     */ 
/*     */   public void lazyInitialize() {
/* 135 */     removeAll();
/* 136 */     this._textPanel = new JPanel(new BorderLayout(5, 5));
/* 137 */     this._textPanel.setOpaque(false);
/* 138 */     this._textPanel.setBorder(BorderFactory.createEmptyBorder(3, 10, 2, 10));
/*     */ 
/* 140 */     if (getSubTitleFont() == null) {
/* 141 */       setSubTitleFont(getFont());
/*     */     }
/*     */ 
/* 144 */     this._subtitleLabel = new MultilineLabel(getSubtitle())
/*     */     {
/*     */       public Dimension getMinimumSize() {
/* 147 */         return new Dimension(0, 0);
/*     */       }
/*     */     };
/* 150 */     this._subtitleLabel.setFont(getSubTitleFont());
/* 151 */     if (getSubTitleColor() == null) {
/* 152 */       setSubTitleColor(getForeground());
/*     */     }
/* 154 */     this._subtitleLabel.setForeground(getSubTitleColor());
/* 155 */     this._subtitleLabel.setBorder(BorderFactory.createEmptyBorder(0, getSubTitleIndent(), 0, 0));
/* 156 */     this._textPanel.add(this._subtitleLabel, "Center");
/*     */ 
/* 158 */     this._titleLabel = new JLabel(getTitle())
/*     */     {
/*     */       public Dimension getMinimumSize() {
/* 161 */         return new Dimension(0, super.getMinimumSize().height);
/*     */       }
/*     */     };
/* 164 */     if (getTitleFont() == null) {
/* 165 */       setTitleFont(SecurityUtils.createFont(getFont().getFontName(), 1, getFont().getSize() + 2));
/*     */     }
/* 167 */     this._titleLabel.setFont(getTitleFont());
/* 168 */     if (getTitleColor() == null) {
/* 169 */       setTitleColor(getForeground());
/*     */     }
/* 171 */     this._titleLabel.setForeground(getTitleColor());
/* 172 */     if ((getSubtitle() != null) && (getSubtitle().length() != 0)) {
/* 173 */       this._textPanel.add(this._titleLabel, "First");
/*     */     }
/*     */     else {
/* 176 */       this._textPanel.add(this._titleLabel, "Center");
/*     */     }
/*     */ 
/* 179 */     if ((getTitleIcon() == null) && (this._iconComponent == null)) {
/* 180 */       this._iconComponent = new JLabel("");
/*     */     }
/* 182 */     else if ((getTitleIcon() != null) || (this._iconComponent == null))
/*     */     {
/* 185 */       this._iconComponent = new JLabel(getTitleIcon());
/*     */     }
/* 187 */     this._iconComponent.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
/*     */ 
/* 189 */     setLayout(new BorderLayout(5, 0));
/* 190 */     add(this._textPanel, "Center");
/* 191 */     addIconComponent(this._iconComponent);
/*     */ 
/* 193 */     this._propertyListener = new PropertyChangeListener() {
/*     */       public void propertyChange(PropertyChangeEvent evt) {
/* 195 */         if ((BannerPanel.this._titleLabel != null) && ("title".equals(evt.getPropertyName()))) {
/* 196 */           BannerPanel.this._titleLabel.setText((String)evt.getNewValue());
/*     */         }
/* 198 */         else if ((BannerPanel.this._subtitleLabel != null) && ("subTitle".equals(evt.getPropertyName()))) {
/* 199 */           String text = (String)evt.getNewValue();
/* 200 */           BannerPanel.this._subtitleLabel.setText(text);
/* 201 */           if ((text != null) && (text.length() != 0)) {
/* 202 */             BannerPanel.this._textPanel.add(BannerPanel.this._titleLabel, "First");
/* 203 */             BannerPanel.this._textPanel.add(BannerPanel.this._subtitleLabel, "Center");
/*     */           }
/*     */           else {
/* 206 */             BannerPanel.this._textPanel.add(BannerPanel.this._titleLabel, "Center");
/*     */           }
/*     */         }
/* 209 */         else if ("icon".equals(evt.getPropertyName())) {
/* 210 */           if ((BannerPanel.this._iconComponent instanceof JLabel)) {
/* 211 */             ((JLabel)BannerPanel.this._iconComponent).setIcon(BannerPanel.this.getTitleIcon());
/*     */           }
/*     */         }
/* 214 */         else if ("iconComponent".equals(evt.getPropertyName())) {
/* 215 */           if ((evt.getOldValue() instanceof JComponent)) {
/* 216 */             BannerPanel.this._textPanel.remove((JComponent)evt.getOldValue());
/*     */           }
/* 218 */           if ((evt.getNewValue() instanceof JComponent)) {
/* 219 */             BannerPanel.this.addIconComponent((JComponent)evt.getNewValue());
/*     */           }
/*     */         }
/* 222 */         else if ("titleFont".equals(evt.getPropertyName())) {
/* 223 */           if (BannerPanel.this._titleLabel != null) {
/* 224 */             BannerPanel.this._titleLabel.setFont((Font)evt.getNewValue());
/*     */           }
/*     */         }
/* 227 */         else if ("subTitleFont".equals(evt.getPropertyName())) {
/* 228 */           if (BannerPanel.this._subtitleLabel != null) {
/* 229 */             BannerPanel.this._subtitleLabel.setFont((Font)evt.getNewValue());
/*     */           }
/*     */         }
/* 232 */         else if ("titleIconLocation".equals(evt.getPropertyName())) {
/* 233 */           BannerPanel.this.addIconComponent(BannerPanel.this._iconComponent);
/*     */         }
/*     */       }
/*     */     };
/* 237 */     addPropertyChangeListener(this._propertyListener);
/*     */   }
/*     */ 
/*     */   private void addIconComponent(JComponent component) {
/* 241 */     if (component != null)
/* 242 */       switch (getTitleIconLocation()) {
/*     */       case 3:
/* 244 */         add(component, "East");
/* 245 */         break;
/*     */       case 7:
/* 247 */         add(component, "West");
/* 248 */         break;
/*     */       case 10:
/* 250 */         add(component, "Before");
/* 251 */         break;
/*     */       case 11:
/* 253 */         add(component, "After");
/*     */       case 4:
/*     */       case 5:
/*     */       case 6:
/*     */       case 8:
/*     */       case 9:
/*     */       }
/*     */   }
/*     */ 
/*     */   protected ImageIcon prepareTitleIcon(ImageIcon icon)
/*     */   {
/* 267 */     return icon;
/*     */   }
/*     */ 
/*     */   public Paint getBackgroundPaint()
/*     */   {
/* 276 */     return this._backgroundPaint;
/*     */   }
/*     */ 
/*     */   public void setBackgroundPaint(Paint backgroundPaint)
/*     */   {
/* 286 */     this._backgroundPaint = backgroundPaint;
/*     */   }
/*     */ 
/*     */   public void setGradientPaint(Color startColor, Color endColor, boolean isVertical)
/*     */   {
/* 299 */     setStartColor(startColor);
/* 300 */     setEndColor(endColor);
/* 301 */     setVertical(isVertical);
/*     */   }
/*     */ 
/*     */   protected void paintComponent(Graphics g)
/*     */   {
/* 311 */     super.paintComponent(g);
/* 312 */     if ((getStartColor() != null) && (getEndColor() != null)) {
/* 313 */       JideSwingUtilities.fillGradient((Graphics2D)g, new Rectangle(0, 0, getWidth(), getHeight()), getStartColor(), getEndColor(), isVertical());
/*     */     }
/* 315 */     else if (getBackgroundPaint() != null) {
/* 316 */       Graphics2D g2d = (Graphics2D)g;
/* 317 */       g2d.setPaint(getBackgroundPaint());
/* 318 */       g2d.fillRect(0, 0, getWidth(), getHeight());
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getTitle()
/*     */   {
/* 328 */     return this._title;
/*     */   }
/*     */ 
/*     */   public void setTitle(String title)
/*     */   {
/* 337 */     String old = this._title;
/* 338 */     this._title = title;
/* 339 */     firePropertyChange("title", old, this._title);
/*     */   }
/*     */ 
/*     */   public String getSubtitle()
/*     */   {
/* 348 */     return this._subtitle;
/*     */   }
/*     */ 
/*     */   public void setSubtitle(String subtitle)
/*     */   {
/* 357 */     String old = this._subtitle;
/* 358 */     this._subtitle = subtitle;
/* 359 */     firePropertyChange("subTitle", old, this._subtitle);
/*     */   }
/*     */ 
/*     */   public ImageIcon getTitleIcon()
/*     */   {
/* 368 */     return this._titleIcon;
/*     */   }
/*     */ 
/*     */   public void setTitleIcon(ImageIcon titleIcon)
/*     */   {
/* 377 */     ImageIcon old = this._titleIcon;
/* 378 */     this._titleIcon = prepareTitleIcon(titleIcon);
/* 379 */     firePropertyChange("icon", old, this._titleIcon);
/*     */   }
/*     */ 
/*     */   public JComponent getIconComponent()
/*     */   {
/* 391 */     return this._iconComponent;
/*     */   }
/*     */ 
/*     */   public void setIconComponent(JComponent iconComponent)
/*     */   {
/* 400 */     JComponent old = this._iconComponent;
/* 401 */     this._iconComponent = iconComponent;
/* 402 */     firePropertyChange("iconComponent", old, this._iconComponent);
/*     */   }
/*     */ 
/*     */   public Font getSubTitleFont()
/*     */   {
/* 411 */     return this._subTitleFont;
/*     */   }
/*     */ 
/*     */   public void setSubTitleFont(Font subTitleFont)
/*     */   {
/* 420 */     Font old = this._subTitleFont;
/* 421 */     this._subTitleFont = subTitleFont;
/* 422 */     firePropertyChange("subTitleFont", old, this._subTitleFont);
/*     */   }
/*     */ 
/*     */   public Font getTitleFont()
/*     */   {
/* 431 */     return this._titleFont;
/*     */   }
/*     */ 
/*     */   public void setTitleFont(Font titleFont)
/*     */   {
/* 440 */     Font old = this._titleFont;
/* 441 */     this._titleFont = titleFont;
/* 442 */     firePropertyChange("titleFont", old, this._titleFont);
/*     */   }
/*     */ 
/*     */   public int getSubTitleIndent()
/*     */   {
/* 451 */     return this._subTitleIndent;
/*     */   }
/*     */ 
/*     */   public void setSubTitleIndent(int subTitleIndent)
/*     */   {
/* 461 */     this._subTitleIndent = subTitleIndent;
/*     */   }
/*     */ 
/*     */   public Color getTitleColor()
/*     */   {
/* 470 */     return this._titleColor;
/*     */   }
/*     */ 
/*     */   public void setTitleColor(Color titleColor)
/*     */   {
/* 479 */     this._titleColor = titleColor;
/* 480 */     if (this._titleLabel != null)
/* 481 */       this._titleLabel.setForeground(titleColor);
/*     */   }
/*     */ 
/*     */   public Color getSubTitleColor()
/*     */   {
/* 491 */     return this._subTitleColor;
/*     */   }
/*     */ 
/*     */   public void setSubTitleColor(Color subTitleColor)
/*     */   {
/* 500 */     this._subTitleColor = subTitleColor;
/* 501 */     if (this._subtitleLabel != null)
/* 502 */       this._subtitleLabel.setForeground(subTitleColor);
/*     */   }
/*     */ 
/*     */   public void setBackground(Color bg)
/*     */   {
/* 508 */     super.setBackground(bg);
/* 509 */     if (this._titleLabel != null) {
/* 510 */       this._titleLabel.setBackground(bg);
/*     */     }
/* 512 */     if (this._subtitleLabel != null) {
/* 513 */       this._subtitleLabel.setBackground(bg);
/*     */     }
/* 515 */     if (this._iconComponent != null)
/* 516 */       this._iconComponent.setBackground(bg);
/*     */   }
/*     */ 
/*     */   public void setForeground(Color fg)
/*     */   {
/* 522 */     super.setForeground(fg);
/* 523 */     setTitleColor(fg);
/* 524 */     setSubTitleColor(fg);
/* 525 */     if (this._iconComponent != null)
/* 526 */       this._iconComponent.setForeground(fg);
/*     */   }
/*     */ 
/*     */   public Color getStartColor()
/*     */   {
/* 531 */     return this._startColor;
/*     */   }
/*     */ 
/*     */   public void setStartColor(Color startColor) {
/* 535 */     this._startColor = startColor;
/*     */   }
/*     */ 
/*     */   public Color getEndColor() {
/* 539 */     return this._endColor;
/*     */   }
/*     */ 
/*     */   public void setEndColor(Color endColor) {
/* 543 */     this._endColor = endColor;
/*     */   }
/*     */ 
/*     */   public boolean isVertical() {
/* 547 */     return this._isVertical;
/*     */   }
/*     */ 
/*     */   public void setVertical(boolean vertical) {
/* 551 */     this._isVertical = vertical;
/*     */   }
/*     */ 
/*     */   public int getTitleIconLocation()
/*     */   {
/* 560 */     return this._titleIconLocation;
/*     */   }
/*     */ 
/*     */   public void setTitleIconLocation(int titleIconLocation)
/*     */   {
/* 572 */     int old = this._titleIconLocation;
/* 573 */     if (old != titleIconLocation) {
/* 574 */       this._titleIconLocation = titleIconLocation;
/* 575 */       firePropertyChange("titleIconLocation", old, this._titleIconLocation);
/*     */     }
/*     */   }
/*     */ 
/*     */   public JComponent getTitleLabel()
/*     */   {
/* 585 */     return this._titleLabel;
/*     */   }
/*     */ 
/*     */   public JComponent getSubtitleLabel()
/*     */   {
/* 594 */     return this._subtitleLabel;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.dialog.BannerPanel
 * JD-Core Version:    0.6.0
 */