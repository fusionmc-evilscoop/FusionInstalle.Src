/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import com.jidesoft.plaf.LookAndFeelFactory;
/*     */ import com.jidesoft.plaf.UIDefaultsLookup;
/*     */ import java.lang.reflect.Method;
/*     */ import javax.swing.BoundedRangeModel;
/*     */ import javax.swing.DefaultBoundedRangeModel;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JSlider;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ 
/*     */ public class RangeSlider extends JSlider
/*     */ {
/*     */   private static final String uiClassID = "SliderUI";
/*  26 */   private boolean _rangeDraggable = true;
/*     */   public static final String CLIENT_PROPERTY_MOUSE_POSITION = "RangeSlider.mousePosition";
/*     */ 
/*     */   public RangeSlider()
/*     */   {
/*     */   }
/*     */ 
/*     */   public RangeSlider(int orientation)
/*     */   {
/*  42 */     super(orientation);
/*     */   }
/*     */ 
/*     */   public RangeSlider(int min, int max)
/*     */   {
/*  53 */     super(min, max);
/*     */   }
/*     */ 
/*     */   public RangeSlider(int min, int max, int low, int high)
/*     */   {
/*  65 */     super(new DefaultBoundedRangeModel(low, high - low, min, max));
/*     */   }
/*     */ 
/*     */   public void updateUI()
/*     */   {
/*  76 */     if (UIDefaultsLookup.get("RangeSliderUI") == null)
/*  77 */       LookAndFeelFactory.installJideExtension();
/*     */     try
/*     */     {
/*  80 */       Class uiClass = Class.forName(UIManager.getString("RangeSliderUI"));
/*  81 */       Class acClass = JComponent.class;
/*  82 */       Method m = uiClass.getMethod("createUI", new Class[] { acClass });
/*  83 */       if (m != null) {
/*  84 */         Object uiObject = m.invoke(null, new Object[] { this });
/*  85 */         setUI((ComponentUI)uiObject);
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/*  89 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getUIClassID()
/*     */   {
/* 103 */     return "SliderUI";
/*     */   }
/*     */ 
/*     */   public int getLowValue()
/*     */   {
/* 112 */     return getModel().getValue();
/*     */   }
/*     */ 
/*     */   public int getHighValue()
/*     */   {
/* 121 */     return getModel().getValue() + getModel().getExtent();
/*     */   }
/*     */ 
/*     */   public boolean contains(int value)
/*     */   {
/* 131 */     return (value >= getLowValue()) && (value <= getHighValue());
/*     */   }
/*     */ 
/*     */   public void setValue(int value)
/*     */   {
/* 136 */     Object clientProperty = getClientProperty("RangeSlider.mousePosition");
/* 137 */     if (clientProperty != null) {
/* 138 */       if (Boolean.TRUE.equals(clientProperty)) {
/* 139 */         setLowValue(value);
/*     */       }
/*     */       else {
/* 142 */         setHighValue(value);
/*     */       }
/*     */     }
/*     */     else
/* 146 */       setLowValue(value);
/*     */   }
/*     */ 
/*     */   public void setLowValue(int lowValue)
/*     */   {
/*     */     int high;
/*     */     int high;
/* 157 */     if (lowValue + getModel().getExtent() > getMaximum()) {
/* 158 */       high = getMaximum();
/*     */     }
/*     */     else {
/* 161 */       high = getHighValue();
/*     */     }
/* 163 */     int extent = high - lowValue;
/*     */ 
/* 165 */     getModel().setRangeProperties(lowValue, extent, getMinimum(), getMaximum(), true);
/*     */   }
/*     */ 
/*     */   public void setHighValue(int highValue)
/*     */   {
/* 175 */     getModel().setExtent(highValue - getLowValue());
/*     */   }
/*     */ 
/*     */   public boolean isRangeDraggable()
/*     */   {
/* 184 */     return this._rangeDraggable;
/*     */   }
/*     */ 
/*     */   public void setRangeDraggable(boolean rangeDraggable)
/*     */   {
/* 193 */     this._rangeDraggable = rangeDraggable;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.RangeSlider
 * JD-Core Version:    0.6.0
 */