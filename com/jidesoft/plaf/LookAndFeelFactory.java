/*      */ package com.jidesoft.plaf;
/*      */ 
/*      */ import com.jidesoft.icons.IconsFactory;
/*      */ import com.jidesoft.plaf.basic.BasicPainter;
/*      */ import com.jidesoft.plaf.basic.Painter;
/*      */ import com.jidesoft.plaf.basic.ThemePainter;
/*      */ import com.jidesoft.plaf.eclipse.Eclipse3xMetalUtils;
/*      */ import com.jidesoft.plaf.eclipse.Eclipse3xWindowsUtils;
/*      */ import com.jidesoft.plaf.eclipse.EclipseMetalUtils;
/*      */ import com.jidesoft.plaf.eclipse.EclipseWindowsUtils;
/*      */ import com.jidesoft.plaf.office2003.Office2003Painter;
/*      */ import com.jidesoft.plaf.office2003.Office2003WindowsUtils;
/*      */ import com.jidesoft.plaf.office2007.Office2007WindowsUtils;
/*      */ import com.jidesoft.plaf.vsnet.VsnetMetalUtils;
/*      */ import com.jidesoft.plaf.vsnet.VsnetWindowsUtils;
/*      */ import com.jidesoft.plaf.xerto.XertoMetalUtils;
/*      */ import com.jidesoft.plaf.xerto.XertoWindowsUtils;
/*      */ import com.jidesoft.swing.JideSwingUtilities;
/*      */ import com.jidesoft.utils.ProductNames;
/*      */ import com.jidesoft.utils.SecurityUtils;
/*      */ import com.jidesoft.utils.SystemInfo;
/*      */ import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
/*      */ import java.awt.Color;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Rectangle;
/*      */ import java.io.PrintStream;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.lang.reflect.Method;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Vector;
/*      */ import javax.swing.BorderFactory;
/*      */ import javax.swing.Icon;
/*      */ import javax.swing.ImageIcon;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.JToolBar;
/*      */ import javax.swing.LookAndFeel;
/*      */ import javax.swing.UIDefaults;
/*      */ import javax.swing.UIDefaults.LazyInputMap;
/*      */ import javax.swing.UIManager;
/*      */ import javax.swing.UIManager.LookAndFeelInfo;
/*      */ import javax.swing.plaf.BorderUIResource;
/*      */ import javax.swing.plaf.ColorUIResource;
/*      */ import javax.swing.plaf.FontUIResource;
/*      */ import javax.swing.plaf.InsetsUIResource;
/*      */ import javax.swing.plaf.metal.MetalLookAndFeel;
/*      */ import sun.swing.SwingLazyValue;
/*      */ 
/*      */ public class LookAndFeelFactory
/*      */   implements ProductNames
/*      */ {
/*      */   public static final String WINDOWS_LNF = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
/*      */   public static final String METAL_LNF = "javax.swing.plaf.metal.MetalLookAndFeel";
/*      */   public static final String AQUA_LNF = "apple.laf.AquaLookAndFeel";
/*      */   public static final String AQUA_LNF_6 = "com.apple.laf.AquaLookAndFeel";
/*      */   public static final String QUAQUA_LNF = "ch.randelshofer.quaqua.QuaquaLookAndFeel";
/*      */   public static final String ALLOY_LNF = "com.incors.plaf.alloy.AlloyLookAndFeel";
/*      */   public static final String SYNTHETICA_LNF = "de.javasoft.plaf.synthetica.SyntheticaLookAndFeel";
/*      */   public static final String SYNTHETICA_LNF_PREFIX = "de.javasoft.plaf.synthetica.Synthetica";
/*      */   public static final String PLASTIC3D_LNF = "com.jgoodies.plaf.plastic.Plastic3DLookAndFeel";
/*      */   public static final String PLASTIC3D_LNF_1_3 = "com.jgoodies.looks.plastic.Plastic3DLookAndFeel";
/*      */   public static final String PLASTICXP_LNF = "com.jgoodies.looks.plastic.PlasticXPLookAndFeel";
/*      */   public static final String TONIC_LNF = "com.digitprop.tonic.TonicLookAndFeel";
/*      */   public static final String A03_LNF = "a03.swing.plaf.A03LookAndFeel";
/*      */   public static final String PGS_LNF = "com.pagosoft.plaf.PgsLookAndFeel";
/*      */   public static final String GTK_LNF = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
/*      */   public static final String NIMBUS_LNF_NAME = "NimbusLookAndFeel";
/*      */   public static final int VSNET_STYLE_WITHOUT_MENU = 0;
/*      */   public static final int VSNET_STYLE = 1;
/*      */   public static final int ECLIPSE_STYLE = 2;
/*      */   public static final int OFFICE2003_STYLE = 3;
/*      */   public static final int XERTO_STYLE = 4;
/*      */   public static final int XERTO_STYLE_WITHOUT_MENU = 6;
/*      */   public static final int ECLIPSE3X_STYLE = 5;
/*      */   public static final int OFFICE2007_STYLE = 7;
/*  298 */   private static int _style = -1;
/*  299 */   private static int _defaultStyle = -1;
/*      */   private static LookAndFeel _lookAndFeel;
/*      */   public static final String JIDE_EXTENSION_INSTALLLED = "jidesoft.extendsionInstalled";
/*      */   public static final String JIDE_STYLE_INSTALLED = "jidesoft.extendsionStyle";
/*  330 */   private static List<UIDefaultsCustomizer> _uiDefaultsCustomizers = new Vector();
/*  331 */   private static List<UIDefaultsInitializer> _uiDefaultsInitializers = new Vector();
/*  332 */   private static Map<String, String> _installedLookAndFeels = new HashMap();
/*      */   public static final String LAF_INSTALLED = "installed";
/*      */   public static final String LAF_NOT_INSTALLED = "not installed";
/* 1518 */   private static int _productsUsed = -1;
/*      */ 
/*      */   public static int getDefaultStyle()
/*      */   {
/*  347 */     if (_defaultStyle == -1) {
/*  348 */       String defaultStyle = SecurityUtils.getProperty("jide.defaultStyle", "-1");
/*      */       try {
/*  350 */         _defaultStyle = Integer.parseInt(defaultStyle);
/*      */       }
/*      */       catch (NumberFormatException e)
/*      */       {
/*      */       }
/*  355 */       if (_defaultStyle == -1)
/*      */       {
/*      */         int suggestedStyle;
/*      */         try
/*      */         {
/*      */           int suggestedStyle;
/*  358 */           if ((SystemInfo.isWindowsVistaAbove()) && ((UIManager.getLookAndFeel() instanceof WindowsLookAndFeel)) && (SystemInfo.isJdk6Above())) {
/*  359 */             suggestedStyle = 7;
/*      */           }
/*      */           else
/*      */           {
/*      */             int suggestedStyle;
/*  361 */             if ((XPUtils.isXPStyleOn()) && ((UIManager.getLookAndFeel() instanceof WindowsLookAndFeel))) {
/*  362 */               suggestedStyle = 3;
/*      */             }
/*      */             else
/*  365 */               suggestedStyle = (getProductsUsed() & 0x10) == 0 ? 0 : 1;
/*      */           }
/*      */         }
/*      */         catch (UnsupportedOperationException e) {
/*  369 */           suggestedStyle = (getProductsUsed() & 0x10) == 0 ? 0 : 1;
/*      */         }
/*  371 */         return suggestedStyle;
/*      */       }
/*      */     }
/*  374 */     return _defaultStyle;
/*      */   }
/*      */ 
/*      */   public static void setDefaultStyle(int defaultStyle)
/*      */   {
/*  383 */     _defaultStyle = defaultStyle;
/*      */   }
/*      */ 
/*      */   public static void installJideExtension()
/*      */   {
/*  418 */     installJideExtension(getDefaultStyle());
/*      */   }
/*      */ 
/*      */   public static void installJideExtension(int style)
/*      */   {
/*  452 */     installJideExtension(UIManager.getLookAndFeelDefaults(), UIManager.getLookAndFeel(), style);
/*      */   }
/*      */ 
/*      */   public static boolean isJideExtensionInstalled()
/*      */   {
/*  462 */     return UIDefaultsLookup.getBoolean("jidesoft.extendsionInstalled");
/*      */   }
/*      */ 
/*      */   public static void installJideExtension(UIDefaults uiDefaults, LookAndFeel lnf, int style)
/*      */   {
/*  473 */     if ((isJideExtensionInstalled()) && (_style == style) && (_lookAndFeel == lnf)) {
/*  474 */       return;
/*      */     }
/*      */ 
/*  477 */     _style = style;
/*  478 */     uiDefaults.put("jidesoft.extendsionStyle", Integer.valueOf(_style));
/*      */ 
/*  480 */     _lookAndFeel = lnf;
/*  481 */     UIDefaultsInitializer[] initializers = getUIDefaultsInitializers();
/*  482 */     for (UIDefaultsInitializer initializer : initializers) {
/*  483 */       if (initializer != null) {
/*  484 */         initializer.initialize(uiDefaults);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  506 */     if (((lnf.getClass().getName().equals("com.incors.plaf.alloy.AlloyLookAndFeel")) && (isAlloyLnfInstalled())) || ((lnf.getClass().getName().equals("com.jgoodies.plaf.plastic.Plastic3DLookAndFeel")) && (isPlastic3DLnfInstalled())) || ((lnf.getClass().getName().equals("com.jgoodies.looks.plastic.Plastic3DLookAndFeel")) && (isPlastic3D13LnfInstalled())) || ((lnf.getClass().getName().equals("com.jgoodies.looks.plastic.PlasticXPLookAndFeel")) && (isPlasticXPLnfInstalled())) || ((lnf.getClass().getName().equals("com.pagosoft.plaf.PgsLookAndFeel")) && (isPgsLnfInstalled())) || ((lnf.getClass().getName().equals("com.digitprop.tonic.TonicLookAndFeel")) && (isTonicLnfInstalled())))
/*      */     {
/*  512 */       switch (style) {
/*      */       case 7:
/*  514 */         VsnetWindowsUtils.initComponentDefaults(uiDefaults);
/*  515 */         Office2003WindowsUtils.initComponentDefaults(uiDefaults);
/*  516 */         Office2007WindowsUtils.initComponentDefaults(uiDefaults);
/*  517 */         Office2007WindowsUtils.initClassDefaults(uiDefaults, false);
/*  518 */         break;
/*      */       case 3:
/*  520 */         VsnetWindowsUtils.initComponentDefaults(uiDefaults);
/*  521 */         Office2003WindowsUtils.initComponentDefaults(uiDefaults);
/*  522 */         Office2003WindowsUtils.initClassDefaults(uiDefaults, false);
/*  523 */         break;
/*      */       case 0:
/*      */       case 1:
/*  526 */         VsnetMetalUtils.initComponentDefaults(uiDefaults);
/*  527 */         VsnetMetalUtils.initClassDefaults(uiDefaults);
/*      */ 
/*  529 */         Painter gripperPainter = new Painter() {
/*      */           public void paint(JComponent c, Graphics g, Rectangle rect, int orientation, int state) {
/*  531 */             Office2003Painter.getInstance().paintGripper(c, g, rect, orientation, state);
/*      */           }
/*      */         };
/*  536 */         uiDefaults.put("Gripper.painter", gripperPainter);
/*  537 */         uiDefaults.put("JideTabbedPane.gripperPainter", gripperPainter);
/*  538 */         uiDefaults.put("JideTabbedPane.defaultTabShape", Integer.valueOf(4));
/*  539 */         uiDefaults.put("JideTabbedPane.selectedTabTextForeground", UIDefaultsLookup.getColor("controlText"));
/*  540 */         uiDefaults.put("JideTabbedPane.unselectedTabTextForeground", UIDefaultsLookup.getColor("controlText"));
/*  541 */         uiDefaults.put("JideTabbedPane.foreground", UIDefaultsLookup.getColor("controlText"));
/*  542 */         uiDefaults.put("JideTabbedPane.light", UIDefaultsLookup.getColor("control"));
/*  543 */         uiDefaults.put("JideSplitPaneDivider.gripperPainter", gripperPainter);
/*      */ 
/*  545 */         int products = getProductsUsed();
/*  546 */         if ((products & 0x1) == 0) break;
/*  547 */         ImageIcon titleButtonImage = IconsFactory.getImageIcon(VsnetWindowsUtils.class, "icons/title_buttons_windows.gif");
/*  548 */         int titleButtonSize = 10;
/*      */ 
/*  550 */         uiDefaults.put("DockableFrameUI", "com.jidesoft.plaf.vsnet.VsnetDockableFrameUI");
/*  551 */         uiDefaults.put("DockableFrameTitlePane.hideIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 0, 10, 10));
/*  552 */         uiDefaults.put("DockableFrameTitlePane.unfloatIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 10, 10, 10));
/*  553 */         uiDefaults.put("DockableFrameTitlePane.floatIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 20, 10, 10));
/*  554 */         uiDefaults.put("DockableFrameTitlePane.autohideIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 30, 10, 10));
/*  555 */         uiDefaults.put("DockableFrameTitlePane.stopAutohideIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 40, 10, 10));
/*  556 */         uiDefaults.put("DockableFrameTitlePane.hideAutohideIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 50, 10, 10));
/*  557 */         uiDefaults.put("DockableFrameTitlePane.maximizeIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 60, 10, 10));
/*  558 */         uiDefaults.put("DockableFrameTitlePane.restoreIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 70, 10, 10));
/*  559 */         uiDefaults.put("DockableFrameTitlePane.buttonGap", Integer.valueOf(4));
/*  560 */         uiDefaults.put("DockableFrame.titleBorder", new BorderUIResource(BorderFactory.createEmptyBorder(1, 0, 2, 0)));
/*  561 */         uiDefaults.put("DockableFrame.border", new BorderUIResource(BorderFactory.createEmptyBorder(2, 0, 0, 0)));
/*  562 */         uiDefaults.put("DockableFrameTitlePane.gripperPainter", gripperPainter);
/*  563 */         break;
/*      */       case 2:
/*  566 */         EclipseMetalUtils.initComponentDefaults(uiDefaults);
/*  567 */         EclipseMetalUtils.initClassDefaults(uiDefaults);
/*  568 */         break;
/*      */       case 5:
/*  570 */         Eclipse3xMetalUtils.initComponentDefaults(uiDefaults);
/*  571 */         Eclipse3xMetalUtils.initClassDefaults(uiDefaults);
/*  572 */         break;
/*      */       case 4:
/*      */       case 6:
/*  575 */         XertoMetalUtils.initComponentDefaults(uiDefaults);
/*  576 */         XertoMetalUtils.initClassDefaults(uiDefaults);
/*      */       }
/*      */ 
/*  579 */       uiDefaults.put("Theme.painter", BasicPainter.getInstance());
/*      */     }
/*  581 */     else if (lnf.getClass().getName().equals(MetalLookAndFeel.class.getName())) {
/*  582 */       switch (style) {
/*      */       case 1:
/*      */       case 3:
/*      */       case 7:
/*  586 */         VsnetMetalUtils.initComponentDefaults(uiDefaults);
/*  587 */         VsnetMetalUtils.initClassDefaultsWithMenu(uiDefaults);
/*  588 */         break;
/*      */       case 2:
/*  590 */         EclipseMetalUtils.initComponentDefaults(uiDefaults);
/*  591 */         EclipseMetalUtils.initClassDefaults(uiDefaults);
/*  592 */         break;
/*      */       case 5:
/*  594 */         Eclipse3xMetalUtils.initComponentDefaults(uiDefaults);
/*  595 */         Eclipse3xMetalUtils.initClassDefaults(uiDefaults);
/*  596 */         break;
/*      */       case 0:
/*  598 */         VsnetMetalUtils.initComponentDefaults(uiDefaults);
/*  599 */         VsnetMetalUtils.initClassDefaults(uiDefaults);
/*  600 */         break;
/*      */       case 4:
/*      */       case 6:
/*  603 */         XertoMetalUtils.initComponentDefaults(uiDefaults);
/*  604 */         XertoMetalUtils.initClassDefaults(uiDefaults);
/*  605 */         break;
/*      */       default:
/*  605 */         break;
/*      */       }
/*      */ 
/*      */     }
/*  609 */     else if ((lnf instanceof MetalLookAndFeel)) {
/*  610 */       switch (style) {
/*      */       case 0:
/*      */       case 1:
/*      */       case 3:
/*      */       case 7:
/*  615 */         VsnetMetalUtils.initComponentDefaults(uiDefaults);
/*  616 */         VsnetMetalUtils.initClassDefaults(uiDefaults);
/*  617 */         break;
/*      */       case 2:
/*  619 */         EclipseMetalUtils.initClassDefaults(uiDefaults);
/*  620 */         EclipseMetalUtils.initComponentDefaults(uiDefaults);
/*  621 */         break;
/*      */       case 5:
/*  623 */         Eclipse3xMetalUtils.initClassDefaults(uiDefaults);
/*  624 */         Eclipse3xMetalUtils.initComponentDefaults(uiDefaults);
/*  625 */         break;
/*      */       case 4:
/*      */       case 6:
/*  628 */         XertoMetalUtils.initComponentDefaults(uiDefaults);
/*  629 */         XertoMetalUtils.initClassDefaults(uiDefaults);
/*      */       }
/*      */ 
/*      */     }
/*  633 */     else if ((lnf instanceof WindowsLookAndFeel)) {
/*  634 */       switch (style) {
/*      */       case 7:
/*  636 */         VsnetWindowsUtils.initComponentDefaultsWithMenu(uiDefaults);
/*  637 */         VsnetWindowsUtils.initClassDefaultsWithMenu(uiDefaults);
/*  638 */         Office2003WindowsUtils.initComponentDefaults(uiDefaults);
/*  639 */         Office2007WindowsUtils.initComponentDefaults(uiDefaults);
/*  640 */         Office2007WindowsUtils.initClassDefaults(uiDefaults);
/*  641 */         break;
/*      */       case 3:
/*  643 */         VsnetWindowsUtils.initComponentDefaultsWithMenu(uiDefaults);
/*  644 */         VsnetWindowsUtils.initClassDefaultsWithMenu(uiDefaults);
/*  645 */         Office2003WindowsUtils.initClassDefaults(uiDefaults);
/*  646 */         Office2003WindowsUtils.initComponentDefaults(uiDefaults);
/*  647 */         break;
/*      */       case 2:
/*  649 */         EclipseWindowsUtils.initClassDefaultsWithMenu(uiDefaults);
/*  650 */         EclipseWindowsUtils.initComponentDefaultsWithMenu(uiDefaults);
/*  651 */         break;
/*      */       case 5:
/*  653 */         Eclipse3xWindowsUtils.initClassDefaultsWithMenu(uiDefaults);
/*  654 */         Eclipse3xWindowsUtils.initComponentDefaultsWithMenu(uiDefaults);
/*  655 */         break;
/*      */       case 1:
/*  657 */         VsnetWindowsUtils.initComponentDefaultsWithMenu(uiDefaults);
/*  658 */         VsnetWindowsUtils.initClassDefaultsWithMenu(uiDefaults);
/*  659 */         break;
/*      */       case 0:
/*  661 */         VsnetWindowsUtils.initComponentDefaults(uiDefaults);
/*  662 */         VsnetWindowsUtils.initClassDefaults(uiDefaults);
/*  663 */         break;
/*      */       case 4:
/*  665 */         XertoWindowsUtils.initComponentDefaultsWithMenu(uiDefaults);
/*  666 */         XertoWindowsUtils.initClassDefaultsWithMenu(uiDefaults);
/*  667 */         break;
/*      */       case 6:
/*  669 */         XertoWindowsUtils.initComponentDefaults(uiDefaults);
/*  670 */         XertoWindowsUtils.initClassDefaults(uiDefaults);
/*      */       }
/*      */ 
/*      */     }
/*  675 */     else if (((isAquaLnfInstalled()) && ((isLnfInUse("com.apple.laf.AquaLookAndFeel")) || (isLnfInUse("apple.laf.AquaLookAndFeel")))) || ((isQuaquaLnfInstalled()) && (isLnfInUse("ch.randelshofer.quaqua.QuaquaLookAndFeel"))))
/*      */     {
/*      */       try
/*      */       {
/*  679 */         Class aquaJideUtils = getUIManagerClassLoader().loadClass("com.jidesoft.plaf.aqua.AquaJideUtils");
/*  680 */         aquaJideUtils.getMethod("initComponentDefaults", new Class[] { UIDefaults.class }).invoke(null, new Object[] { uiDefaults });
/*  681 */         aquaJideUtils.getMethod("initClassDefaults", new Class[] { UIDefaults.class }).invoke(null, new Object[] { uiDefaults });
/*      */       }
/*      */       catch (ClassNotFoundException e) {
/*  684 */         throw new RuntimeException(e);
/*      */       }
/*      */       catch (IllegalAccessException e) {
/*  687 */         throw new RuntimeException(e);
/*      */       }
/*      */       catch (IllegalArgumentException e) {
/*  690 */         throw new RuntimeException(e);
/*      */       }
/*      */       catch (InvocationTargetException e) {
/*  693 */         JideSwingUtilities.throwInvocationTargetException(e);
/*      */       }
/*      */       catch (NoSuchMethodException e) {
/*  696 */         throw new RuntimeException(e);
/*      */       }
/*      */       catch (SecurityException e) {
/*  699 */         throw new RuntimeException(e);
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*  704 */       if ((isGTKLnfInstalled()) && (isLnfInUse("com.sun.java.swing.plaf.gtk.GTKLookAndFeel"))) {
/*  705 */         new GTKInitializer().initialize(uiDefaults);
/*      */       }
/*  707 */       else if ((isSyntheticaLnfInstalled()) && ((lnf.getClass().getName().startsWith("de.javasoft.plaf.synthetica.Synthetica")) || (isLnfInUse("de.javasoft.plaf.synthetica.SyntheticaLookAndFeel"))))
/*      */       {
/*  709 */         new SyntheticaInitializer().initialize(uiDefaults);
/*      */       }
/*  711 */       else if ((isNimbusLnfInstalled()) && (lnf.getClass().getName().indexOf("NimbusLookAndFeel") != -1)) {
/*  712 */         new NimbusInitializer().initialize(uiDefaults);
/*      */       }
/*      */ 
/*  715 */       switch (style) {
/*      */       case 7:
/*  717 */         if (SystemInfo.isWindows()) {
/*  718 */           VsnetWindowsUtils.initComponentDefaultsWithMenu(uiDefaults);
/*  719 */           Office2003WindowsUtils.initComponentDefaults(uiDefaults);
/*  720 */           Office2007WindowsUtils.initComponentDefaults(uiDefaults);
/*  721 */           Office2007WindowsUtils.initClassDefaults(uiDefaults);
/*      */         }
/*      */         else {
/*  724 */           VsnetMetalUtils.initComponentDefaults(uiDefaults);
/*  725 */           VsnetMetalUtils.initClassDefaults(uiDefaults);
/*      */         }
/*  727 */         break;
/*      */       case 3:
/*  729 */         if (SystemInfo.isWindows()) {
/*  730 */           VsnetWindowsUtils.initComponentDefaultsWithMenu(uiDefaults);
/*  731 */           Office2003WindowsUtils.initComponentDefaults(uiDefaults);
/*  732 */           Office2003WindowsUtils.initClassDefaults(uiDefaults);
/*      */         }
/*      */         else {
/*  735 */           VsnetMetalUtils.initComponentDefaults(uiDefaults);
/*  736 */           VsnetMetalUtils.initClassDefaults(uiDefaults);
/*      */         }
/*  738 */         break;
/*      */       case 2:
/*  740 */         if (SystemInfo.isWindows()) {
/*  741 */           EclipseWindowsUtils.initClassDefaultsWithMenu(uiDefaults);
/*  742 */           EclipseWindowsUtils.initComponentDefaultsWithMenu(uiDefaults);
/*      */         }
/*      */         else {
/*  745 */           EclipseMetalUtils.initClassDefaults(uiDefaults);
/*  746 */           EclipseMetalUtils.initComponentDefaults(uiDefaults);
/*      */         }
/*  748 */         break;
/*      */       case 5:
/*  750 */         if (SystemInfo.isWindows()) {
/*  751 */           Eclipse3xWindowsUtils.initClassDefaultsWithMenu(uiDefaults);
/*  752 */           Eclipse3xWindowsUtils.initComponentDefaultsWithMenu(uiDefaults);
/*      */         }
/*      */         else {
/*  755 */           Eclipse3xMetalUtils.initClassDefaults(uiDefaults);
/*  756 */           Eclipse3xMetalUtils.initComponentDefaults(uiDefaults);
/*      */         }
/*  758 */         break;
/*      */       case 1:
/*  760 */         if (SystemInfo.isWindows()) {
/*  761 */           VsnetWindowsUtils.initClassDefaultsWithMenu(uiDefaults);
/*  762 */           VsnetWindowsUtils.initComponentDefaultsWithMenu(uiDefaults);
/*      */         }
/*      */         else {
/*  765 */           VsnetMetalUtils.initComponentDefaults(uiDefaults);
/*  766 */           VsnetMetalUtils.initClassDefaults(uiDefaults);
/*      */         }
/*  768 */         break;
/*      */       case 0:
/*  770 */         if (SystemInfo.isWindows()) {
/*  771 */           VsnetWindowsUtils.initClassDefaults(uiDefaults);
/*  772 */           VsnetWindowsUtils.initComponentDefaults(uiDefaults);
/*      */         }
/*      */         else {
/*  775 */           VsnetMetalUtils.initComponentDefaults(uiDefaults);
/*  776 */           VsnetMetalUtils.initClassDefaults(uiDefaults);
/*      */         }
/*  778 */         break;
/*      */       case 4:
/*  780 */         if (SystemInfo.isWindows()) {
/*  781 */           XertoWindowsUtils.initClassDefaultsWithMenu(uiDefaults);
/*  782 */           XertoWindowsUtils.initComponentDefaultsWithMenu(uiDefaults);
/*      */         }
/*      */         else {
/*  785 */           XertoMetalUtils.initComponentDefaults(uiDefaults);
/*  786 */           XertoMetalUtils.initClassDefaults(uiDefaults);
/*      */         }
/*  788 */         break;
/*      */       case 6:
/*  790 */         if (SystemInfo.isWindows()) {
/*  791 */           XertoWindowsUtils.initClassDefaults(uiDefaults);
/*  792 */           XertoWindowsUtils.initComponentDefaults(uiDefaults);
/*      */         }
/*      */         else {
/*  795 */           XertoMetalUtils.initComponentDefaults(uiDefaults);
/*  796 */           XertoMetalUtils.initClassDefaults(uiDefaults);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  801 */       if ((isGTKLnfInstalled()) && (isLnfInUse("com.sun.java.swing.plaf.gtk.GTKLookAndFeel"))) {
/*  802 */         new GTKCustomizer().customize(uiDefaults);
/*      */       }
/*  804 */       else if ((lnf.getClass().getName().startsWith("de.javasoft.plaf.synthetica.Synthetica")) || ((isLnfInstalled("de.javasoft.plaf.synthetica.SyntheticaLookAndFeel")) && (isLnfInUse("de.javasoft.plaf.synthetica.SyntheticaLookAndFeel")))) {
/*  805 */         new SyntheticaCustomizer().customize(uiDefaults);
/*      */       }
/*      */     }
/*      */ 
/*  809 */     uiDefaults.put("jidesoft.extendsionInstalled", Boolean.TRUE);
/*      */ 
/*  811 */     UIDefaultsCustomizer[] customizers = getUIDefaultsCustomizers();
/*  812 */     for (UIDefaultsCustomizer customizer : customizers)
/*  813 */       if (customizer != null)
/*  814 */         customizer.customize(uiDefaults);
/*      */   }
/*      */ 
/*      */   public static boolean isLnfInstalled(String lnfName)
/*      */   {
/*  826 */     String installed = (String)_installedLookAndFeels.get(lnfName);
/*  827 */     if (installed != null) {
/*  828 */       return "installed".equals(installed);
/*      */     }
/*  830 */     return loadLnfClass(lnfName) != null;
/*      */   }
/*      */ 
/*      */   public static ClassLoader getUIManagerClassLoader() {
/*  834 */     Object cl = UIManager.get("ClassLoader");
/*  835 */     if ((cl instanceof ClassLoader)) {
/*  836 */       return (ClassLoader)cl;
/*      */     }
/*  838 */     ClassLoader classLoader = LookAndFeelFactory.class.getClassLoader();
/*  839 */     if (classLoader == null) {
/*  840 */       classLoader = ClassLoader.getSystemClassLoader();
/*      */     }
/*  842 */     return classLoader;
/*      */   }
/*      */ 
/*      */   public static boolean isLnfInUse(String lnfName)
/*      */   {
/*  852 */     return ((!_installedLookAndFeels.containsKey(lnfName)) || ((_installedLookAndFeels.get(lnfName) != null) && (!((String)_installedLookAndFeels.get(lnfName)).equals("not installed")))) && (isAssignableFrom(lnfName, UIManager.getLookAndFeel().getClass()));
/*      */   }
/*      */ 
/*      */   public static void setLnfInstalled(String lnfName, boolean installed)
/*      */   {
/*  865 */     _installedLookAndFeels.put(lnfName, installed ? "installed" : "not installed");
/*      */   }
/*      */ 
/*      */   private static Class loadLnfClass(String lnfName) {
/*      */     try {
/*  870 */       Class clazz = getUIManagerClassLoader().loadClass(lnfName);
/*  871 */       Map map = new HashMap(_installedLookAndFeels);
/*  872 */       map.put(lnfName, "installed");
/*  873 */       _installedLookAndFeels = map;
/*  874 */       return clazz;
/*      */     }
/*      */     catch (ClassNotFoundException e) {
/*  877 */       Map map = new HashMap(_installedLookAndFeels);
/*  878 */       map.put(lnfName, "not installed");
/*  879 */       _installedLookAndFeels = map;
/*  880 */     }return null;
/*      */   }
/*      */ 
/*      */   private static boolean isAssignableFrom(String lnfName, Class cls)
/*      */   {
/*  885 */     if (lnfName.equals(cls.getName())) {
/*  886 */       return true;
/*      */     }
/*  888 */     Class cl = loadLnfClass(lnfName);
/*  889 */     return (cl != null) && (cl.isAssignableFrom(cls));
/*      */   }
/*      */ 
/*      */   public static boolean isAquaLnfInstalled()
/*      */   {
/*  898 */     return (isLnfInstalled("com.apple.laf.AquaLookAndFeel")) || (isLnfInstalled("apple.laf.AquaLookAndFeel"));
/*      */   }
/*      */ 
/*      */   public static boolean isQuaquaLnfInstalled()
/*      */   {
/*  908 */     return isLnfInstalled("ch.randelshofer.quaqua.QuaquaLookAndFeel");
/*      */   }
/*      */ 
/*      */   public static boolean isAlloyLnfInstalled()
/*      */   {
/*  917 */     return isLnfInstalled("com.incors.plaf.alloy.AlloyLookAndFeel");
/*      */   }
/*      */ 
/*      */   public static boolean isGTKLnfInstalled()
/*      */   {
/*  926 */     return isLnfInstalled("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
/*      */   }
/*      */ 
/*      */   public static boolean isPlastic3DLnfInstalled()
/*      */   {
/*  935 */     return isLnfInstalled("com.jgoodies.plaf.plastic.Plastic3DLookAndFeel");
/*      */   }
/*      */ 
/*      */   public static boolean isPlastic3D13LnfInstalled()
/*      */   {
/*  944 */     return isLnfInstalled("com.jgoodies.looks.plastic.Plastic3DLookAndFeel");
/*      */   }
/*      */ 
/*      */   public static boolean isPlasticXPLnfInstalled()
/*      */   {
/*  953 */     return isLnfInstalled("com.jgoodies.looks.plastic.PlasticXPLookAndFeel");
/*      */   }
/*      */ 
/*      */   public static boolean isTonicLnfInstalled()
/*      */   {
/*  962 */     return isLnfInstalled("com.digitprop.tonic.TonicLookAndFeel");
/*      */   }
/*      */ 
/*      */   public static boolean isA03LnfInstalled()
/*      */   {
/*  971 */     return isLnfInstalled("a03.swing.plaf.A03LookAndFeel");
/*      */   }
/*      */ 
/*      */   public static boolean isPgsLnfInstalled()
/*      */   {
/*  980 */     return isLnfInstalled("com.pagosoft.plaf.PgsLookAndFeel");
/*      */   }
/*      */ 
/*      */   public static boolean isSyntheticaLnfInstalled()
/*      */   {
/*  998 */     return isLnfInstalled("de.javasoft.plaf.synthetica.SyntheticaLookAndFeel");
/*      */   }
/*      */ 
/*      */   public static boolean isNimbusLnfInstalled()
/*      */   {
/* 1007 */     UIManager.LookAndFeelInfo[] infos = UIManager.getInstalledLookAndFeels();
/* 1008 */     for (UIManager.LookAndFeelInfo info : infos) {
/* 1009 */       if (info.getClassName().indexOf("NimbusLookAndFeel") != -1) {
/* 1010 */         return true;
/*      */       }
/*      */     }
/* 1013 */     return false;
/*      */   }
/*      */ 
/*      */   public static void installDefaultLookAndFeelAndExtension()
/*      */   {
/* 1021 */     installDefaultLookAndFeel();
/*      */ 
/* 1023 */     installJideExtension();
/*      */   }
/*      */ 
/*      */   public static void installDefaultLookAndFeel()
/*      */   {
/*      */     try
/*      */     {
/* 1032 */       String lnfName = SecurityUtils.getProperty("swing.defaultlaf", null);
/* 1033 */       if (lnfName == null) {
/* 1034 */         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
/*      */       }
/*      */       else
/* 1037 */         UIManager.setLookAndFeel(lnfName);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1041 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */ 
/*      */   public static LookAndFeel getLookAndFeel()
/*      */   {
/* 1051 */     return _lookAndFeel;
/*      */   }
/*      */ 
/*      */   public static int getStyle()
/*      */   {
/* 1060 */     return _style;
/*      */   }
/*      */ 
/*      */   public static UIDefaultsCustomizer[] getUIDefaultsCustomizers()
/*      */   {
/* 1069 */     return (UIDefaultsCustomizer[])_uiDefaultsCustomizers.toArray(new UIDefaultsCustomizer[_uiDefaultsCustomizers.size()]);
/*      */   }
/*      */ 
/*      */   public static void addUIDefaultsCustomizer(UIDefaultsCustomizer uiDefaultsCustomizer)
/*      */   {
/* 1088 */     if (!_uiDefaultsCustomizers.contains(uiDefaultsCustomizer))
/* 1089 */       _uiDefaultsCustomizers.add(uiDefaultsCustomizer);
/*      */   }
/*      */ 
/*      */   public static void removeUIDefaultsCustomizer(UIDefaultsCustomizer uiDefaultsCustomizer)
/*      */   {
/* 1099 */     _uiDefaultsCustomizers.remove(uiDefaultsCustomizer);
/*      */   }
/*      */ 
/*      */   public static UIDefaultsInitializer[] getUIDefaultsInitializers()
/*      */   {
/* 1108 */     return (UIDefaultsInitializer[])_uiDefaultsInitializers.toArray(new UIDefaultsInitializer[_uiDefaultsInitializers.size()]);
/*      */   }
/*      */ 
/*      */   public static void addUIDefaultsInitializer(UIDefaultsInitializer uiDefaultsInitializer)
/*      */   {
/* 1130 */     if (!_uiDefaultsInitializers.contains(uiDefaultsInitializer))
/* 1131 */       _uiDefaultsInitializers.add(uiDefaultsInitializer);
/*      */   }
/*      */ 
/*      */   public static void removeUIDefaultsInitializer(UIDefaultsInitializer uiDefaultsInitializer)
/*      */   {
/* 1141 */     _uiDefaultsInitializers.remove(uiDefaultsInitializer);
/*      */   }
/*      */ 
/*      */   private static Icon loadSyntheticaIcon(Class syntheticaClass, String key)
/*      */   {
/*      */     try
/*      */     {
/* 1408 */       Method method = syntheticaClass.getMethod("loadIcon", new Class[] { String.class });
/* 1409 */       return (Icon)method.invoke(null, new Object[] { key });
/*      */     } catch (Exception e) {
/*      */     }
/* 1412 */     return IconsFactory.getImageIcon(syntheticaClass, UIDefaultsLookup.getString(key));
/*      */   }
/*      */ 
/*      */   public static void verifyDefaults(UIDefaults table, Object[] keyValueList)
/*      */   {
/* 1462 */     int i = 0; for (int max = keyValueList.length; i < max; i += 2) {
/* 1463 */       Object value = keyValueList[(i + 1)];
/* 1464 */       if (value == null) {
/* 1465 */         System.out.println("The value for " + keyValueList[i] + " is null");
/*      */       }
/*      */       else {
/* 1468 */         Object oldValue = table.get(keyValueList[i]);
/* 1469 */         if (oldValue != null)
/* 1470 */           System.out.println("The value for " + keyValueList[i] + " exists which is " + oldValue);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void putDefaults(UIDefaults table, Object[] keyValueArray)
/*      */   {
/* 1485 */     int i = 0; for (int max = keyValueArray.length; i < max; i += 2) {
/* 1486 */       Object value = keyValueArray[(i + 1)];
/* 1487 */       if (value == null) {
/* 1488 */         table.remove(keyValueArray[i]);
/*      */       }
/* 1491 */       else if (table.get(keyValueArray[i]) == null)
/* 1492 */         table.put(keyValueArray[i], value);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void overwriteDefaults(UIDefaults table, Object[] keyValueArray)
/*      */   {
/* 1507 */     int i = 0; for (int max = keyValueArray.length; i < max; i += 2) {
/* 1508 */       Object value = keyValueArray[(i + 1)];
/* 1509 */       if (value == null) {
/* 1510 */         table.remove(keyValueArray[i]);
/*      */       }
/*      */       else
/* 1513 */         table.put(keyValueArray[i], value);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static int getProductsUsed()
/*      */   {
/* 1521 */     if (_productsUsed == -1) {
/* 1522 */       _productsUsed = 0;
/*      */       try {
/* 1524 */         Class.forName("com.jidesoft.docking.Product");
/* 1525 */         _productsUsed |= 1;
/*      */       }
/*      */       catch (Throwable e)
/*      */       {
/*      */       }
/*      */       try {
/* 1531 */         Class.forName("com.jidesoft.action.Product");
/* 1532 */         _productsUsed |= 16;
/*      */       }
/*      */       catch (Throwable e)
/*      */       {
/*      */       }
/*      */       try {
/* 1538 */         Class.forName("com.jidesoft.document.Product");
/* 1539 */         _productsUsed |= 2;
/*      */       }
/*      */       catch (Throwable e)
/*      */       {
/*      */       }
/*      */       try {
/* 1545 */         Class.forName("com.jidesoft.grid.Product");
/* 1546 */         _productsUsed |= 4;
/*      */       }
/*      */       catch (Throwable e)
/*      */       {
/*      */       }
/*      */       try {
/* 1552 */         Class.forName("com.jidesoft.wizard.Product");
/* 1553 */         _productsUsed |= 8;
/*      */       }
/*      */       catch (Throwable e)
/*      */       {
/*      */       }
/*      */       try {
/* 1559 */         Class.forName("com.jidesoft.pivot.Product");
/* 1560 */         _productsUsed |= 32;
/*      */       }
/*      */       catch (Throwable e)
/*      */       {
/*      */       }
/*      */       try {
/* 1566 */         Class.forName("com.jidesoft.shortcut.Product");
/* 1567 */         _productsUsed |= 64;
/*      */       }
/*      */       catch (Throwable e)
/*      */       {
/*      */       }
/*      */       try {
/* 1573 */         Class.forName("com.jidesoft.editor.Product");
/* 1574 */         _productsUsed |= 128;
/*      */       }
/*      */       catch (Throwable e)
/*      */       {
/*      */       }
/*      */       try {
/* 1580 */         Class.forName("com.jidesoft.rss.Product");
/* 1581 */         _productsUsed |= 256;
/*      */       }
/*      */       catch (Throwable e)
/*      */       {
/*      */       }
/*      */       try {
/* 1587 */         Class.forName("com.jidesoft.diff.Product");
/* 1588 */         _productsUsed |= 16384;
/*      */       }
/*      */       catch (Throwable e)
/*      */       {
/*      */       }
/*      */     }
/* 1594 */     return _productsUsed;
/*      */   }
/*      */ 
/*      */   public static void setProductsUsed(int productsUsed)
/*      */   {
/* 1606 */     _productsUsed = productsUsed;
/*      */   }
/*      */ 
/*      */   public static boolean isCurrentLnfDecorated()
/*      */   {
/* 1615 */     return (!isLnfInstalled("de.javasoft.plaf.synthetica.SyntheticaLookAndFeel")) || (!isLnfInUse("de.javasoft.plaf.synthetica.SyntheticaLookAndFeel"));
/*      */   }
/*      */ 
/*      */   public static void main(String[] args)
/*      */   {
/*      */   }
/*      */ 
/*      */   public static class NimbusInitializer
/*      */     implements LookAndFeelFactory.UIDefaultsInitializer
/*      */   {
/*      */     public void initialize(UIDefaults defaults)
/*      */     {
/* 1418 */       Object marginBorder = new SwingLazyValue("javax.swing.plaf.basic.BasicBorders$MarginBorder");
/*      */ 
/* 1421 */       Object[] uiDefaults = { "textHighlight", new ColorUIResource(197, 218, 233), "controlText", new ColorUIResource(Color.BLACK), "activeCaptionText", new ColorUIResource(Color.BLACK), "MenuItem.acceleratorFont", new FontUIResource("Arial", 0, 12), "ComboBox.background", new ColorUIResource(Color.WHITE), "ComboBox.disabledForeground", new ColorUIResource(Color.DARK_GRAY), "ComboBox.disabledBackground", new ColorUIResource(Color.GRAY), "activeCaption", new ColorUIResource(197, 218, 233), "inactiveCaption", new ColorUIResource(Color.DARK_GRAY), "control", new ColorUIResource(220, 223, 228), "controlLtHighlight", new ColorUIResource(Color.WHITE), "controlHighlight", new ColorUIResource(Color.LIGHT_GRAY), "controlShadow", new ColorUIResource(133, 137, 144), "controlDkShadow", new ColorUIResource(Color.BLACK), "MenuItem.background", new ColorUIResource(237, 239, 242), "SplitPane.background", new ColorUIResource(220, 223, 228), "Tree.hash", new ColorUIResource(Color.GRAY), "TextField.foreground", new ColorUIResource(Color.BLACK), "TextField.inactiveForeground", new ColorUIResource(Color.BLACK), "TextField.selectionForeground", new ColorUIResource(Color.WHITE), "TextField.selectionBackground", new ColorUIResource(197, 218, 233), "Table.gridColor", new ColorUIResource(Color.BLACK), "TextField.background", new ColorUIResource(Color.WHITE), "Table.selectionBackground", defaults.getColor("Tree.selectionBackground"), "Table.selectionForeground", defaults.getColor("Tree.selectionForeground"), "Menu.border", marginBorder, "MenuItem.border", marginBorder, "CheckBoxMenuItem.border", marginBorder, "RadioButtonMenuItem.border", marginBorder };
/*      */ 
/* 1456 */       LookAndFeelFactory.putDefaults(defaults, uiDefaults);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class SyntheticaCustomizer
/*      */     implements LookAndFeelFactory.UIDefaultsCustomizer
/*      */   {
/*      */     public void customize(UIDefaults defaults)
/*      */     {
/*      */       try
/*      */       {
/* 1191 */         Class syntheticaClass = Class.forName("de.javasoft.plaf.synthetica.SyntheticaLookAndFeel");
/* 1192 */         Class syntheticaFrameBorder = Class.forName("com.jidesoft.plaf.synthetica.SyntheticaFrameBorder");
/* 1193 */         Class syntheticaPopupBorder = Class.forName("com.jidesoft.plaf.synthetica.SyntheticaPopupBorder");
/* 1194 */         Color toolbarBackground = new JToolBar().getBackground();
/* 1195 */         int products = LookAndFeelFactory.getProductsUsed();
/*      */ 
/* 1197 */         Object[] uiDefaults = { "JideTabbedPaneUI", "com.jidesoft.plaf.synthetica.SyntheticaJideTabbedPaneUI", "RangeSliderUI", "javax.swing.plaf.synth.SynthRangeSliderUI", "JideSplitPane.dividerSize", Integer.valueOf(6), "JideTabbedPane.tabAreaBackground", UIManager.getColor("control"), "JideTabbedPane.background", UIManager.getColor("control"), "JideTabbedPane.defaultTabShape", Integer.valueOf(9), "JideTabbedPane.defaultTabShape", Integer.valueOf(9), "JideTabbedPane.contentBorderInsets", new InsetsUIResource(2, 2, 2, 2), "JideButton.foreground", UIDefaultsLookup.getColor("Button.foreground"), "JideSplitButton.foreground", UIDefaultsLookup.getColor("Button.foreground"), "Icon.floating", Boolean.FALSE, "ContentContainer.background", toolbarBackground, "PopupMenu.border", syntheticaPopupBorder.newInstance() };
/*      */ 
/* 1212 */         LookAndFeelFactory.overwriteDefaults(defaults, uiDefaults);
/*      */ 
/* 1215 */         if ((products & 0x2) != 0) {
/* 1216 */           Object[] uiDefaults = { "CollapsiblePane.background", UIDefaultsLookup.getColor("TaskPane.borderColor"), "CollapsiblePane.emphasizedBackground", UIDefaultsLookup.getColor("TaskPane.borderColor"), "CollapsiblePane.foreground", UIDefaultsLookup.getColor("TaskPane.titleForeground"), "CollapsiblePane.emphasizedForeground", UIDefaultsLookup.getColor("TaskPane.specialTitleForeground"), "CollapsiblePane.font", UIDefaultsLookup.getFont("TaskPane.font") != null ? UIDefaultsLookup.getFont("TaskPane.font") : UIDefaultsLookup.getFont("Label.font"), "StatusBarItem.border", new BorderUIResource(BorderFactory.createEmptyBorder(2, 2, 2, 2)), "StatusBar.childrenOpaque", Boolean.valueOf(false), "StatusBar.paintResizableIcon", Boolean.valueOf(false), "OutlookTabbedPane.buttonStyle", Integer.valueOf(0), "FloorTabbedPane.buttonStyle", Integer.valueOf(0) };
/*      */ 
/* 1229 */           LookAndFeelFactory.overwriteDefaults(defaults, uiDefaults);
/*      */         }
/*      */ 
/* 1232 */         if ((products & 0x4) != 0) {
/* 1233 */           Object[] uiDefaults = { "NestedTableHeaderUI", "com.jidesoft.plaf.synthetica.SyntheticaNestedTableHeaderUI", "EditableTableHeaderUI", "com.jidesoft.plaf.synthetica.SyntheticaEditableTableHeaderUI", "List.focusInputMap", new UIDefaults.LazyInputMap(new Object[] { "ctrl C", "copy", "ctrl V", "paste", "ctrl X", "cut", "COPY", "copy", "PASTE", "paste", "CUT", "cut", "control INSERT", "copy", "shift INSERT", "paste", "shift DELETE", "cut", "UP", "selectPreviousRow", "KP_UP", "selectPreviousRow", "shift UP", "selectPreviousRowExtendSelection", "shift KP_UP", "selectPreviousRowExtendSelection", "ctrl shift UP", "selectPreviousRowExtendSelection", "ctrl shift KP_UP", "selectPreviousRowExtendSelection", "ctrl UP", "selectPreviousRowChangeLead", "ctrl KP_UP", "selectPreviousRowChangeLead", "DOWN", "selectNextRow", "KP_DOWN", "selectNextRow", "shift DOWN", "selectNextRowExtendSelection", "shift KP_DOWN", "selectNextRowExtendSelection", "ctrl shift DOWN", "selectNextRowExtendSelection", "ctrl shift KP_DOWN", "selectNextRowExtendSelection", "ctrl DOWN", "selectNextRowChangeLead", "ctrl KP_DOWN", "selectNextRowChangeLead", "LEFT", "selectPreviousColumn", "KP_LEFT", "selectPreviousColumn", "shift LEFT", "selectPreviousColumnExtendSelection", "shift KP_LEFT", "selectPreviousColumnExtendSelection", "ctrl shift LEFT", "selectPreviousColumnExtendSelection", "ctrl shift KP_LEFT", "selectPreviousColumnExtendSelection", "ctrl LEFT", "selectPreviousColumnChangeLead", "ctrl KP_LEFT", "selectPreviousColumnChangeLead", "RIGHT", "selectNextColumn", "KP_RIGHT", "selectNextColumn", "shift RIGHT", "selectNextColumnExtendSelection", "shift KP_RIGHT", "selectNextColumnExtendSelection", "ctrl shift RIGHT", "selectNextColumnExtendSelection", "ctrl shift KP_RIGHT", "selectNextColumnExtendSelection", "ctrl RIGHT", "selectNextColumnChangeLead", "ctrl KP_RIGHT", "selectNextColumnChangeLead", "HOME", "selectFirstRow", "shift HOME", "selectFirstRowExtendSelection", "ctrl shift HOME", "selectFirstRowExtendSelection", "ctrl HOME", "selectFirstRowChangeLead", "END", "selectLastRow", "shift END", "selectLastRowExtendSelection", "ctrl shift END", "selectLastRowExtendSelection", "ctrl END", "selectLastRowChangeLead", "PAGE_UP", "scrollUp", "shift PAGE_UP", "scrollUpExtendSelection", "ctrl shift PAGE_UP", "scrollUpExtendSelection", "ctrl PAGE_UP", "scrollUpChangeLead", "PAGE_DOWN", "scrollDown", "shift PAGE_DOWN", "scrollDownExtendSelection", "ctrl shift PAGE_DOWN", "scrollDownExtendSelection", "ctrl PAGE_DOWN", "scrollDownChangeLead", "ctrl A", "selectAll", "ctrl SLASH", "selectAll", "ctrl BACK_SLASH", "clearSelection", "SPACE", "addToSelection", "ctrl SPACE", "toggleAndAnchor", "shift SPACE", "extendTo", "ctrl shift SPACE", "moveSelectionTo" }), "List.focusInputMap.RightToLeft", new UIDefaults.LazyInputMap(new Object[] { "LEFT", "selectNextColumn", "KP_LEFT", "selectNextColumn", "shift LEFT", "selectNextColumnExtendSelection", "shift KP_LEFT", "selectNextColumnExtendSelection", "ctrl shift LEFT", "selectNextColumnExtendSelection", "ctrl shift KP_LEFT", "selectNextColumnExtendSelection", "ctrl LEFT", "selectNextColumnChangeLead", "ctrl KP_LEFT", "selectNextColumnChangeLead", "RIGHT", "selectPreviousColumn", "KP_RIGHT", "selectPreviousColumn", "shift RIGHT", "selectPreviousColumnExtendSelection", "shift KP_RIGHT", "selectPreviousColumnExtendSelection", "ctrl shift RIGHT", "selectPreviousColumnExtendSelection", "ctrl shift KP_RIGHT", "selectPreviousColumnExtendSelection", "ctrl RIGHT", "selectPreviousColumnChangeLead", "ctrl KP_RIGHT", "selectPreviousColumnChangeLead" }) };
/*      */ 
/* 1324 */           LookAndFeelFactory.overwriteDefaults(defaults, uiDefaults);
/*      */         }
/*      */ 
/* 1327 */         if ((products & 0x10) != 0) {
/* 1328 */           Object[] uiDefaults = { "CommandBar.background", toolbarBackground, "CommandBar.border", new BorderUIResource(BorderFactory.createEmptyBorder()), "CommandBar.borderVert", new BorderUIResource(BorderFactory.createEmptyBorder()), "CommandBar.borderFloating", syntheticaFrameBorder.newInstance(), "CommandBar.titleBarBackground", UIDefaultsLookup.getColor("InternalFrame.activeTitleBackground"), "CommandBar.titleBarForeground", UIDefaultsLookup.getColor("InternalFrame.activeTitleForeground"), "CommandBarContainer.verticalGap", Integer.valueOf(0) };
/*      */ 
/* 1337 */           LookAndFeelFactory.overwriteDefaults(defaults, uiDefaults);
/*      */         }
/*      */ 
/* 1340 */         if ((products & 0x1) != 0) {
/* 1341 */           Object[] uiDefaults = { "Workspace.background", UIManager.getColor("control"), "DockableFrame.inactiveTitleForeground", UIDefaultsLookup.getColor("Synthetica.docking.titlebar.color"), "DockableFrame.activeTitleForeground", UIDefaultsLookup.getColor("Synthetica.docking.titlebar.color.selected"), "DockableFrame.titleBorder", UIDefaultsLookup.getColor("Synthetica.docking.border.color"), "FrameContainer.contentBorderInsets", new InsetsUIResource(2, 2, 2, 2), "DockableFrameTitlePane.hideIcon", LookAndFeelFactory.access$000(syntheticaClass, "Synthetica.docking.titlebar.close"), "DockableFrameTitlePane.hideRolloverIcon", LookAndFeelFactory.access$000(syntheticaClass, "Synthetica.docking.titlebar.close.hover"), "DockableFrameTitlePane.hideActiveIcon", LookAndFeelFactory.access$000(syntheticaClass, "Synthetica.docking.titlebar.active.close"), "DockableFrameTitlePane.hideRolloverActiveIcon", LookAndFeelFactory.access$000(syntheticaClass, "Synthetica.docking.titlebar.active.close.hover"), "DockableFrameTitlePane.floatIcon", LookAndFeelFactory.access$000(syntheticaClass, "Synthetica.docking.titlebar.undock"), "DockableFrameTitlePane.floatRolloverIcon", LookAndFeelFactory.access$000(syntheticaClass, "Synthetica.docking.titlebar.undock.hover"), "DockableFrameTitlePane.floatActiveIcon", LookAndFeelFactory.access$000(syntheticaClass, "Synthetica.docking.titlebar.active.undock"), "DockableFrameTitlePane.floatRolloverActiveIcon", LookAndFeelFactory.access$000(syntheticaClass, "Synthetica.docking.titlebar.active.undock.hover"), "DockableFrameTitlePane.unfloatIcon", LookAndFeelFactory.access$000(syntheticaClass, "Synthetica.docking.titlebar.dock"), "DockableFrameTitlePane.unfloatRolloverIcon", LookAndFeelFactory.access$000(syntheticaClass, "Synthetica.docking.titlebar.dock.hover"), "DockableFrameTitlePane.unfloatActiveIcon", LookAndFeelFactory.access$000(syntheticaClass, "Synthetica.docking.titlebar.active.dock"), "DockableFrameTitlePane.unfloatRolloverActiveIcon", LookAndFeelFactory.access$000(syntheticaClass, "Synthetica.docking.titlebar.active.dock.hover"), "DockableFrameTitlePane.autohideIcon", LookAndFeelFactory.access$000(syntheticaClass, "Synthetica.docking.titlebar.iconify"), "DockableFrameTitlePane.autohideRolloverIcon", LookAndFeelFactory.access$000(syntheticaClass, "Synthetica.docking.titlebar.iconify.hover"), "DockableFrameTitlePane.autohideActiveIcon", LookAndFeelFactory.access$000(syntheticaClass, "Synthetica.docking.titlebar.active.iconify"), "DockableFrameTitlePane.autohideRolloverActiveIcon", LookAndFeelFactory.access$000(syntheticaClass, "Synthetica.docking.titlebar.active.iconify.hover"), "DockableFrameTitlePane.stopAutohideIcon", LookAndFeelFactory.access$000(syntheticaClass, "Synthetica.docking.titlebar.restore"), "DockableFrameTitlePane.stopAutohideRolloverIcon", LookAndFeelFactory.access$000(syntheticaClass, "Synthetica.docking.titlebar.restore.hover"), "DockableFrameTitlePane.stopAutohideActiveIcon", LookAndFeelFactory.access$000(syntheticaClass, "Synthetica.docking.titlebar.active.restore"), "DockableFrameTitlePane.stopAutohideRolloverActiveIcon", LookAndFeelFactory.access$000(syntheticaClass, "Synthetica.docking.titlebar.active.restore.hover"), "DockableFrameTitlePane.hideAutohideIcon", LookAndFeelFactory.access$000(syntheticaClass, "Synthetica.docking.titlebar.iconify"), "DockableFrameTitlePane.hideAutohideRolloverIcon", LookAndFeelFactory.access$000(syntheticaClass, "Synthetica.docking.titlebar.iconify.hover"), "DockableFrameTitlePane.hideAutohideActiveIcon", LookAndFeelFactory.access$000(syntheticaClass, "Synthetica.docking.titlebar.active.iconify"), "DockableFrameTitlePane.hideAutohideRolloverActiveIcon", LookAndFeelFactory.access$000(syntheticaClass, "Synthetica.docking.titlebar.active.iconify.hover"), "DockableFrameTitlePane.maximizeIcon", LookAndFeelFactory.access$000(syntheticaClass, "Synthetica.docking.titlebar.maximize"), "DockableFrameTitlePane.maximizeRolloverIcon", LookAndFeelFactory.access$000(syntheticaClass, "Synthetica.docking.titlebar.maximize.hover"), "DockableFrameTitlePane.maximizeActiveIcon", LookAndFeelFactory.access$000(syntheticaClass, "Synthetica.docking.titlebar.active.maximize"), "DockableFrameTitlePane.maximizeRolloverActiveIcon", LookAndFeelFactory.access$000(syntheticaClass, "Synthetica.docking.titlebar.active.maximize.hover"), "DockableFrameTitlePane.restoreIcon", LookAndFeelFactory.access$000(syntheticaClass, "Synthetica.docking.titlebar.restore"), "DockableFrameTitlePane.restoreRolloverIcon", LookAndFeelFactory.access$000(syntheticaClass, "Synthetica.docking.titlebar.restore.hover"), "DockableFrameTitlePane.restoreActiveIcon", LookAndFeelFactory.access$000(syntheticaClass, "Synthetica.docking.titlebar.active.restore"), "DockableFrameTitlePane.restoreRolloverActiveIcon", LookAndFeelFactory.access$000(syntheticaClass, "Synthetica.docking.titlebar.active.restore.hover"), "DockableFrameTitlePane.use3dButtons", Boolean.FALSE, "DockableFrameTitlePane.contentFilledButtons", Boolean.FALSE, "DockableFrameTitlePane.buttonGap", Integer.valueOf(0) };
/*      */ 
/* 1393 */           LookAndFeelFactory.overwriteDefaults(defaults, uiDefaults);
/*      */         }
/* 1395 */         Class painterClass = Class.forName("com.jidesoft.plaf.synthetica.SyntheticaJidePainter");
/* 1396 */         Method getInstanceMethod = painterClass.getMethod("getInstance", new Class[0]);
/* 1397 */         Object painter = getInstanceMethod.invoke(null, new Object[0]);
/* 1398 */         UIDefaultsLookup.put(UIManager.getDefaults(), "Theme.painter", painter);
/*      */       }
/*      */       catch (Exception e) {
/* 1401 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class SyntheticaInitializer
/*      */     implements LookAndFeelFactory.UIDefaultsInitializer
/*      */   {
/*      */     public void initialize(UIDefaults defaults)
/*      */     {
/*      */       try
/*      */       {
/* 1167 */         Class syntheticaPopupBorder = Class.forName("com.jidesoft.plaf.synthetica.SyntheticaPopupBorder");
/* 1168 */         Object[] uiDefaults = { "Label.font", UIDefaultsLookup.getFont("Button.font"), "ToolBar.font", UIDefaultsLookup.getFont("Button.font"), "MenuItem.acceleratorFont", UIDefaultsLookup.getFont("Button.font"), "ComboBox.disabledForeground", defaults.get("Synthetica.comboBox.disabled.textColor"), "ComboBox.disabledBackground", defaults.get("Synthetica.comboBox.disabled.backgroundColor"), "Slider.focusInsets", new InsetsUIResource(0, 0, 0, 0), "PopupMenu.border", syntheticaPopupBorder.newInstance() };
/*      */ 
/* 1177 */         LookAndFeelFactory.putDefaults(defaults, uiDefaults);
/*      */       }
/*      */       catch (Exception e) {
/* 1180 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class GTKCustomizer
/*      */     implements LookAndFeelFactory.UIDefaultsCustomizer
/*      */   {
/*      */     public void customize(UIDefaults defaults)
/*      */     {
/* 1157 */       Object[] uiDefaults = { "RangeSliderUI", "javax.swing.plaf.synth.SynthRangeSliderUI" };
/*      */ 
/* 1160 */       LookAndFeelFactory.overwriteDefaults(defaults, uiDefaults);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class GTKInitializer
/*      */     implements LookAndFeelFactory.UIDefaultsInitializer
/*      */   {
/*      */     public void initialize(UIDefaults defaults)
/*      */     {
/* 1146 */       Object[] uiDefaults = { "activeCaption", defaults.getColor("textHighlight"), "activeCaptionText", defaults.getColor("textHighlightText"), "inactiveCaptionBorder", defaults.getColor("controlShadowtextHighlightText") };
/*      */ 
/* 1151 */       LookAndFeelFactory.putDefaults(defaults, uiDefaults);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static abstract interface UIDefaultsInitializer
/*      */   {
/*      */     public abstract void initialize(UIDefaults paramUIDefaults);
/*      */   }
/*      */ 
/*      */   public static abstract interface UIDefaultsCustomizer
/*      */   {
/*      */     public abstract void customize(UIDefaults paramUIDefaults);
/*      */   }
/*      */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.LookAndFeelFactory
 * JD-Core Version:    0.6.0
 */