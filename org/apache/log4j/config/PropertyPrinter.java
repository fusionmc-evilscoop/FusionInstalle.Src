/*     */ package org.apache.log4j.config;
/*     */ 
/*     */ import java.io.PrintWriter;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import org.apache.log4j.Appender;
/*     */ import org.apache.log4j.Category;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.LogManager;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.Priority;
/*     */ 
/*     */ public class PropertyPrinter
/*     */   implements PropertyGetter.PropertyCallback
/*     */ {
/*  31 */   protected int numAppenders = 0;
/*  32 */   protected Hashtable appenderNames = new Hashtable();
/*  33 */   protected Hashtable layoutNames = new Hashtable();
/*     */   protected PrintWriter out;
/*     */   protected boolean doCapitalize;
/*     */ 
/*     */   public PropertyPrinter(PrintWriter out)
/*     */   {
/*  39 */     this(out, false);
/*     */   }
/*     */ 
/*     */   public PropertyPrinter(PrintWriter out, boolean doCapitalize)
/*     */   {
/*  44 */     this.out = out;
/*  45 */     this.doCapitalize = doCapitalize;
/*     */ 
/*  47 */     print(out);
/*  48 */     out.flush();
/*     */   }
/*     */ 
/*     */   protected String genAppName()
/*     */   {
/*  53 */     return "A" + this.numAppenders++;
/*     */   }
/*     */ 
/*     */   protected boolean isGenAppName(String name)
/*     */   {
/*  62 */     if ((name.length() < 2) || (name.charAt(0) != 'A')) return false;
/*     */ 
/*  64 */     for (int i = 0; i < name.length(); i++) {
/*  65 */       if ((name.charAt(i) < '0') || (name.charAt(i) > '9')) return false;
/*     */     }
/*  67 */     return true;
/*     */   }
/*     */ 
/*     */   public void print(PrintWriter out)
/*     */   {
/*  78 */     printOptions(out, Logger.getRootLogger());
/*     */ 
/*  80 */     Enumeration cats = LogManager.getCurrentLoggers();
/*  81 */     while (cats.hasMoreElements())
/*  82 */       printOptions(out, (Logger)cats.nextElement());
/*     */   }
/*     */ 
/*     */   protected void printOptions(PrintWriter out, Logger cat)
/*     */   {
/*  88 */     Enumeration appenders = cat.getAllAppenders();
/*  89 */     Level prio = cat.getLevel();
/*  90 */     String appenderString = prio == null ? "" : prio.toString();
/*     */ 
/*  92 */     while (appenders.hasMoreElements()) {
/*  93 */       Appender app = (Appender)appenders.nextElement();
/*     */       String name;
/*  96 */       if ((name = (String)this.appenderNames.get(app)) == null)
/*     */       {
/*  99 */         if (((name = app.getName()) == null) || (isGenAppName(name))) {
/* 100 */           name = genAppName();
/*     */         }
/* 102 */         this.appenderNames.put(app, name);
/*     */ 
/* 104 */         printOptions(out, app, "log4j.appender." + name);
/* 105 */         if (app.getLayout() != null) {
/* 106 */           printOptions(out, app.getLayout(), "log4j.appender." + name + ".layout");
/*     */         }
/*     */       }
/* 109 */       appenderString = appenderString + ", " + name;
/*     */     }
/* 111 */     String catKey = "log4j.logger." + cat.getName();
/*     */ 
/* 114 */     if (appenderString != "") {
/* 115 */       out.println(catKey + "=" + appenderString);
/*     */     }
/* 117 */     if ((!cat.getAdditivity()) && (cat != Logger.getRootLogger()))
/* 118 */       out.println("log4j.additivity." + cat.getName() + "=false");
/*     */   }
/*     */ 
/*     */   protected void printOptions(PrintWriter out, Object obj, String fullname)
/*     */   {
/* 124 */     out.println(fullname + "=" + obj.getClass().getName());
/* 125 */     PropertyGetter.getProperties(obj, this, fullname + ".");
/*     */   }
/*     */ 
/*     */   public void foundProperty(Object obj, String prefix, String name, Object value)
/*     */   {
/* 130 */     if (((obj instanceof Appender)) && ("name".equals(name))) {
/* 131 */       return;
/*     */     }
/* 133 */     if (this.doCapitalize) {
/* 134 */       name = capitalize(name);
/*     */     }
/* 136 */     this.out.println(prefix + name + "=" + value.toString());
/*     */   }
/*     */ 
/*     */   public static String capitalize(String name) {
/* 140 */     if ((Character.isLowerCase(name.charAt(0))) && (
/* 141 */       (name.length() == 1) || (Character.isLowerCase(name.charAt(1))))) {
/* 142 */       StringBuffer newname = new StringBuffer(name);
/* 143 */       newname.setCharAt(0, Character.toUpperCase(name.charAt(0)));
/* 144 */       return newname.toString();
/*     */     }
/*     */ 
/* 147 */     return name;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 152 */     new PropertyPrinter(new PrintWriter(System.out));
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.config.PropertyPrinter
 * JD-Core Version:    0.6.0
 */