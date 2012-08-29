/*     */ package com.jidesoft.spinner;
/*     */ 
/*     */ import java.text.DateFormat;
/*     */ import java.text.Format;
/*     */ import java.util.Date;
/*     */ import java.util.TimeZone;
/*     */ import javax.swing.JFormattedTextField;
/*     */ import javax.swing.JFormattedTextField.AbstractFormatter;
/*     */ import javax.swing.JSpinner;
/*     */ import javax.swing.JSpinner.DateEditor;
/*     */ import javax.swing.SpinnerDateModel;
/*     */ import javax.swing.text.DefaultFormatter;
/*     */ import javax.swing.text.InternationalFormatter;
/*     */ 
/*     */ public class DateSpinner extends JSpinner
/*     */ {
/*     */   public DefaultFormatter _formatter;
/*     */   public JSpinner.DateEditor _timeEditor;
/*     */   public DateFormat _format;
/*     */ 
/*     */   public DateSpinner()
/*     */   {
/*  27 */     this("hh:mm:ss");
/*     */   }
/*     */ 
/*     */   public DateSpinner(String format)
/*     */   {
/*  36 */     this(format, new Date());
/*     */   }
/*     */ 
/*     */   public DateSpinner(String format, Date date)
/*     */   {
/*  46 */     super(new SpinnerDateModel(date, null, null, 5));
/*     */ 
/*  50 */     setFormat(format);
/*     */ 
/*  52 */     customizeSpinner();
/*     */   }
/*     */ 
/*     */   private void customizeDateEditor()
/*     */   {
/*  57 */     JFormattedTextField.AbstractFormatter formatter = this._timeEditor.getTextField().getFormatter();
/*  58 */     if ((formatter instanceof DefaultFormatter)) {
/*  59 */       this._formatter = ((DefaultFormatter)formatter);
/*     */     }
/*     */     else {
/*  62 */       throw new IllegalStateException("The formatter is not an instance of DefaultFormatter.");
/*     */     }
/*     */ 
/*  65 */     if ((formatter instanceof InternationalFormatter)) {
/*  66 */       Format f = ((InternationalFormatter)formatter).getFormat();
/*  67 */       if ((f instanceof DateFormat)) {
/*  68 */         this._format = ((DateFormat)f);
/*     */       }
/*     */     }
/*     */ 
/*  72 */     if (this._format == null)
/*  73 */       throw new IllegalStateException("The format is not an instance of SimpleDateFormat.");
/*     */   }
/*     */ 
/*     */   public void setFormat(String format)
/*     */   {
/*  84 */     this._timeEditor = createDateEditor(format);
/*  85 */     customizeDateEditor();
/*  86 */     setEditor(this._timeEditor);
/*     */   }
/*     */ 
/*     */   protected void customizeSpinner()
/*     */   {
/*  93 */     setLenient(false);
/*  94 */     setCommitsOnValidEdit(true);
/*  95 */     setAllowsInvalid(false);
/*  96 */     setOverwriteMode(true);
/*  97 */     SpinnerWheelSupport.installMouseWheelSupport(this);
/*     */   }
/*     */ 
/*     */   protected JSpinner.DateEditor createDateEditor(String format)
/*     */   {
/* 107 */     return new JSpinner.DateEditor(this, format);
/*     */   }
/*     */ 
/*     */   public void setCommitsOnValidEdit(boolean commit)
/*     */   {
/* 121 */     this._formatter.setCommitsOnValidEdit(commit);
/*     */   }
/*     */ 
/*     */   public boolean getCommitsOnValidEdit()
/*     */   {
/* 130 */     return this._formatter.getCommitsOnValidEdit();
/*     */   }
/*     */ 
/*     */   public void setOverwriteMode(boolean overwriteMode)
/*     */   {
/* 140 */     this._formatter.setOverwriteMode(overwriteMode);
/*     */   }
/*     */ 
/*     */   public boolean getOverwriteMode()
/*     */   {
/* 149 */     return this._formatter.getOverwriteMode();
/*     */   }
/*     */ 
/*     */   public void setAllowsInvalid(boolean allowsInvalid)
/*     */   {
/* 160 */     this._formatter.setAllowsInvalid(allowsInvalid);
/*     */   }
/*     */ 
/*     */   public boolean getAllowsInvalid()
/*     */   {
/* 169 */     return this._formatter.getAllowsInvalid();
/*     */   }
/*     */ 
/*     */   public void setTimeZone(TimeZone zone)
/*     */   {
/* 178 */     this._format.setTimeZone(zone);
/*     */   }
/*     */ 
/*     */   public TimeZone getTimeZone()
/*     */   {
/* 187 */     return this._format.getTimeZone();
/*     */   }
/*     */ 
/*     */   public void setLenient(boolean lenient)
/*     */   {
/* 199 */     this._format.setLenient(lenient);
/*     */   }
/*     */ 
/*     */   public boolean isLenient()
/*     */   {
/* 208 */     return this._format.isLenient();
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.spinner.DateSpinner
 * JD-Core Version:    0.6.0
 */