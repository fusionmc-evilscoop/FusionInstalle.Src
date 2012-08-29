/*      */ package org.apache.commons.io;
/*      */ 
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.CharArrayWriter;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.OutputStream;
/*      */ import java.io.OutputStreamWriter;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.Reader;
/*      */ import java.io.StringWriter;
/*      */ import java.io.Writer;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import org.apache.commons.io.output.ByteArrayOutputStream;
/*      */ 
/*      */ public class IOUtils
/*      */ {
/*      */   public static final char DIR_SEPARATOR_UNIX = '/';
/*      */   public static final char DIR_SEPARATOR_WINDOWS = '\\';
/*   97 */   public static final char DIR_SEPARATOR = File.separatorChar;
/*      */   public static final String LINE_SEPARATOR_UNIX = "\n";
/*      */   public static final String LINE_SEPARATOR_WINDOWS = "\r\n";
/*      */   public static final String LINE_SEPARATOR;
/*      */   private static final int DEFAULT_BUFFER_SIZE = 4096;
/*      */ 
/*      */   public static void closeQuietly(Reader input)
/*      */   {
/*      */     try
/*      */     {
/*  141 */       if (input != null)
/*  142 */         input.close();
/*      */     }
/*      */     catch (IOException ioe)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void closeQuietly(Writer output)
/*      */   {
/*      */     try
/*      */     {
/*  159 */       if (output != null)
/*  160 */         output.close();
/*      */     }
/*      */     catch (IOException ioe)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void closeQuietly(InputStream input)
/*      */   {
/*      */     try
/*      */     {
/*  177 */       if (input != null)
/*  178 */         input.close();
/*      */     }
/*      */     catch (IOException ioe)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void closeQuietly(OutputStream output)
/*      */   {
/*      */     try
/*      */     {
/*  195 */       if (output != null)
/*  196 */         output.close();
/*      */     }
/*      */     catch (IOException ioe)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public static byte[] toByteArray(InputStream input)
/*      */     throws IOException
/*      */   {
/*  217 */     ByteArrayOutputStream output = new ByteArrayOutputStream();
/*  218 */     copy(input, output);
/*  219 */     return output.toByteArray();
/*      */   }
/*      */ 
/*      */   public static byte[] toByteArray(Reader input)
/*      */     throws IOException
/*      */   {
/*  235 */     ByteArrayOutputStream output = new ByteArrayOutputStream();
/*  236 */     copy(input, output);
/*  237 */     return output.toByteArray();
/*      */   }
/*      */ 
/*      */   public static byte[] toByteArray(Reader input, String encoding)
/*      */     throws IOException
/*      */   {
/*  259 */     ByteArrayOutputStream output = new ByteArrayOutputStream();
/*  260 */     copy(input, output, encoding);
/*  261 */     return output.toByteArray();
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public static byte[] toByteArray(String input)
/*      */     throws IOException
/*      */   {
/*  277 */     return input.getBytes();
/*      */   }
/*      */ 
/*      */   public static char[] toCharArray(InputStream is)
/*      */     throws IOException
/*      */   {
/*  296 */     CharArrayWriter output = new CharArrayWriter();
/*  297 */     copy(is, output);
/*  298 */     return output.toCharArray();
/*      */   }
/*      */ 
/*      */   public static char[] toCharArray(InputStream is, String encoding)
/*      */     throws IOException
/*      */   {
/*  320 */     CharArrayWriter output = new CharArrayWriter();
/*  321 */     copy(is, output, encoding);
/*  322 */     return output.toCharArray();
/*      */   }
/*      */ 
/*      */   public static char[] toCharArray(Reader input)
/*      */     throws IOException
/*      */   {
/*  338 */     CharArrayWriter sw = new CharArrayWriter();
/*  339 */     copy(input, sw);
/*  340 */     return sw.toCharArray();
/*      */   }
/*      */ 
/*      */   public static String toString(InputStream input)
/*      */     throws IOException
/*      */   {
/*  358 */     StringWriter sw = new StringWriter();
/*  359 */     copy(input, sw);
/*  360 */     return sw.toString();
/*      */   }
/*      */ 
/*      */   public static String toString(InputStream input, String encoding)
/*      */     throws IOException
/*      */   {
/*  381 */     StringWriter sw = new StringWriter();
/*  382 */     copy(input, sw, encoding);
/*  383 */     return sw.toString();
/*      */   }
/*      */ 
/*      */   public static String toString(Reader input)
/*      */     throws IOException
/*      */   {
/*  398 */     StringWriter sw = new StringWriter();
/*  399 */     copy(input, sw);
/*  400 */     return sw.toString();
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public static String toString(byte[] input)
/*      */     throws IOException
/*      */   {
/*  414 */     return new String(input);
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public static String toString(byte[] input, String encoding)
/*      */     throws IOException
/*      */   {
/*  433 */     if (encoding == null) {
/*  434 */       return new String(input);
/*      */     }
/*  436 */     return new String(input, encoding);
/*      */   }
/*      */ 
/*      */   public static List readLines(InputStream input)
/*      */     throws IOException
/*      */   {
/*  456 */     InputStreamReader reader = new InputStreamReader(input);
/*  457 */     return readLines(reader);
/*      */   }
/*      */ 
/*      */   public static List readLines(InputStream input, String encoding)
/*      */     throws IOException
/*      */   {
/*  478 */     if (encoding == null) {
/*  479 */       return readLines(input);
/*      */     }
/*  481 */     InputStreamReader reader = new InputStreamReader(input, encoding);
/*  482 */     return readLines(reader);
/*      */   }
/*      */ 
/*      */   public static List readLines(Reader input)
/*      */     throws IOException
/*      */   {
/*  500 */     BufferedReader reader = new BufferedReader(input);
/*  501 */     List list = new ArrayList();
/*  502 */     String line = reader.readLine();
/*  503 */     while (line != null) {
/*  504 */       list.add(line);
/*  505 */       line = reader.readLine();
/*      */     }
/*  507 */     return list;
/*      */   }
/*      */ 
/*      */   public static LineIterator lineIterator(Reader reader)
/*      */   {
/*  540 */     return new LineIterator(reader);
/*      */   }
/*      */ 
/*      */   public static LineIterator lineIterator(InputStream input, String encoding)
/*      */     throws IOException
/*      */   {
/*  575 */     Reader reader = null;
/*  576 */     if (encoding == null)
/*  577 */       reader = new InputStreamReader(input);
/*      */     else {
/*  579 */       reader = new InputStreamReader(input, encoding);
/*      */     }
/*  581 */     return new LineIterator(reader);
/*      */   }
/*      */ 
/*      */   public static InputStream toInputStream(String input)
/*      */   {
/*  594 */     byte[] bytes = input.getBytes();
/*  595 */     return new ByteArrayInputStream(bytes);
/*      */   }
/*      */ 
/*      */   public static InputStream toInputStream(String input, String encoding)
/*      */     throws IOException
/*      */   {
/*  612 */     byte[] bytes = encoding != null ? input.getBytes(encoding) : input.getBytes();
/*  613 */     return new ByteArrayInputStream(bytes);
/*      */   }
/*      */ 
/*      */   public static void write(byte[] data, OutputStream output)
/*      */     throws IOException
/*      */   {
/*  630 */     if (data != null)
/*  631 */       output.write(data);
/*      */   }
/*      */ 
/*      */   public static void write(byte[] data, Writer output)
/*      */     throws IOException
/*      */   {
/*  649 */     if (data != null)
/*  650 */       output.write(new String(data));
/*      */   }
/*      */ 
/*      */   public static void write(byte[] data, Writer output, String encoding)
/*      */     throws IOException
/*      */   {
/*  673 */     if (data != null)
/*  674 */       if (encoding == null)
/*  675 */         write(data, output);
/*      */       else
/*  677 */         output.write(new String(data, encoding));
/*      */   }
/*      */ 
/*      */   public static void write(char[] data, Writer output)
/*      */     throws IOException
/*      */   {
/*  696 */     if (data != null)
/*  697 */       output.write(data);
/*      */   }
/*      */ 
/*      */   public static void write(char[] data, OutputStream output)
/*      */     throws IOException
/*      */   {
/*  717 */     if (data != null)
/*  718 */       output.write(new String(data).getBytes());
/*      */   }
/*      */ 
/*      */   public static void write(char[] data, OutputStream output, String encoding)
/*      */     throws IOException
/*      */   {
/*  742 */     if (data != null)
/*  743 */       if (encoding == null)
/*  744 */         write(data, output);
/*      */       else
/*  746 */         output.write(new String(data).getBytes(encoding));
/*      */   }
/*      */ 
/*      */   public static void write(String data, Writer output)
/*      */     throws IOException
/*      */   {
/*  763 */     if (data != null)
/*  764 */       output.write(data);
/*      */   }
/*      */ 
/*      */   public static void write(String data, OutputStream output)
/*      */     throws IOException
/*      */   {
/*  783 */     if (data != null)
/*  784 */       output.write(data.getBytes());
/*      */   }
/*      */ 
/*      */   public static void write(String data, OutputStream output, String encoding)
/*      */     throws IOException
/*      */   {
/*  806 */     if (data != null)
/*  807 */       if (encoding == null)
/*  808 */         write(data, output);
/*      */       else
/*  810 */         output.write(data.getBytes(encoding));
/*      */   }
/*      */ 
/*      */   public static void write(StringBuffer data, Writer output)
/*      */     throws IOException
/*      */   {
/*  828 */     if (data != null)
/*  829 */       output.write(data.toString());
/*      */   }
/*      */ 
/*      */   public static void write(StringBuffer data, OutputStream output)
/*      */     throws IOException
/*      */   {
/*  848 */     if (data != null)
/*  849 */       output.write(data.toString().getBytes());
/*      */   }
/*      */ 
/*      */   public static void write(StringBuffer data, OutputStream output, String encoding)
/*      */     throws IOException
/*      */   {
/*  871 */     if (data != null)
/*  872 */       if (encoding == null)
/*  873 */         write(data, output);
/*      */       else
/*  875 */         output.write(data.toString().getBytes(encoding));
/*      */   }
/*      */ 
/*      */   public static void writeLines(Collection lines, String lineEnding, OutputStream output)
/*      */     throws IOException
/*      */   {
/*  896 */     if (lines == null) {
/*  897 */       return;
/*      */     }
/*  899 */     if (lineEnding == null) {
/*  900 */       lineEnding = LINE_SEPARATOR;
/*      */     }
/*  902 */     for (Iterator it = lines.iterator(); it.hasNext(); ) {
/*  903 */       Object line = it.next();
/*  904 */       if (line != null) {
/*  905 */         output.write(line.toString().getBytes());
/*      */       }
/*  907 */       output.write(lineEnding.getBytes());
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void writeLines(Collection lines, String lineEnding, OutputStream output, String encoding)
/*      */     throws IOException
/*      */   {
/*      */     Iterator it;
/*  929 */     if (encoding == null) {
/*  930 */       writeLines(lines, lineEnding, output);
/*      */     } else {
/*  932 */       if (lines == null) {
/*  933 */         return;
/*      */       }
/*  935 */       if (lineEnding == null) {
/*  936 */         lineEnding = LINE_SEPARATOR;
/*      */       }
/*  938 */       for (it = lines.iterator(); it.hasNext(); ) {
/*  939 */         Object line = it.next();
/*  940 */         if (line != null) {
/*  941 */           output.write(line.toString().getBytes(encoding));
/*      */         }
/*  943 */         output.write(lineEnding.getBytes(encoding));
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void writeLines(Collection lines, String lineEnding, Writer writer)
/*      */     throws IOException
/*      */   {
/*  961 */     if (lines == null) {
/*  962 */       return;
/*      */     }
/*  964 */     if (lineEnding == null) {
/*  965 */       lineEnding = LINE_SEPARATOR;
/*      */     }
/*  967 */     for (Iterator it = lines.iterator(); it.hasNext(); ) {
/*  968 */       Object line = it.next();
/*  969 */       if (line != null) {
/*  970 */         writer.write(line.toString());
/*      */       }
/*  972 */       writer.write(lineEnding);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static int copy(InputStream input, OutputStream output)
/*      */     throws IOException
/*      */   {
/*  999 */     long count = copyLarge(input, output);
/* 1000 */     if (count > 2147483647L) {
/* 1001 */       return -1;
/*      */     }
/* 1003 */     return (int)count;
/*      */   }
/*      */ 
/*      */   public static long copyLarge(InputStream input, OutputStream output)
/*      */     throws IOException
/*      */   {
/* 1022 */     byte[] buffer = new byte[4096];
/* 1023 */     long count = 0L;
/* 1024 */     int n = 0;
/* 1025 */     while (-1 != (n = input.read(buffer))) {
/* 1026 */       output.write(buffer, 0, n);
/* 1027 */       count += n;
/*      */     }
/* 1029 */     return count;
/*      */   }
/*      */ 
/*      */   public static void copy(InputStream input, Writer output)
/*      */     throws IOException
/*      */   {
/* 1049 */     InputStreamReader in = new InputStreamReader(input);
/* 1050 */     copy(in, output);
/*      */   }
/*      */ 
/*      */   public static void copy(InputStream input, Writer output, String encoding)
/*      */     throws IOException
/*      */   {
/* 1074 */     if (encoding == null) {
/* 1075 */       copy(input, output);
/*      */     } else {
/* 1077 */       InputStreamReader in = new InputStreamReader(input, encoding);
/* 1078 */       copy(in, output);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static int copy(Reader input, Writer output)
/*      */     throws IOException
/*      */   {
/* 1104 */     long count = copyLarge(input, output);
/* 1105 */     if (count > 2147483647L) {
/* 1106 */       return -1;
/*      */     }
/* 1108 */     return (int)count;
/*      */   }
/*      */ 
/*      */   public static long copyLarge(Reader input, Writer output)
/*      */     throws IOException
/*      */   {
/* 1125 */     char[] buffer = new char[4096];
/* 1126 */     long count = 0L;
/* 1127 */     int n = 0;
/* 1128 */     while (-1 != (n = input.read(buffer))) {
/* 1129 */       output.write(buffer, 0, n);
/* 1130 */       count += n;
/*      */     }
/* 1132 */     return count;
/*      */   }
/*      */ 
/*      */   public static void copy(Reader input, OutputStream output)
/*      */     throws IOException
/*      */   {
/* 1156 */     OutputStreamWriter out = new OutputStreamWriter(output);
/* 1157 */     copy(input, out);
/*      */ 
/* 1160 */     out.flush();
/*      */   }
/*      */ 
/*      */   public static void copy(Reader input, OutputStream output, String encoding)
/*      */     throws IOException
/*      */   {
/* 1188 */     if (encoding == null) {
/* 1189 */       copy(input, output);
/*      */     } else {
/* 1191 */       OutputStreamWriter out = new OutputStreamWriter(output, encoding);
/* 1192 */       copy(input, out);
/*      */ 
/* 1195 */       out.flush();
/*      */     }
/*      */   }
/*      */ 
/*      */   public static boolean contentEquals(InputStream input1, InputStream input2)
/*      */     throws IOException
/*      */   {
/* 1217 */     if (!(input1 instanceof BufferedInputStream)) {
/* 1218 */       input1 = new BufferedInputStream(input1);
/*      */     }
/* 1220 */     if (!(input2 instanceof BufferedInputStream)) {
/* 1221 */       input2 = new BufferedInputStream(input2);
/*      */     }
/*      */ 
/* 1224 */     int ch = input1.read();
/* 1225 */     while (-1 != ch) {
/* 1226 */       int ch2 = input2.read();
/* 1227 */       if (ch != ch2) {
/* 1228 */         return false;
/*      */       }
/* 1230 */       ch = input1.read();
/*      */     }
/*      */ 
/* 1233 */     int ch2 = input2.read();
/* 1234 */     return ch2 == -1;
/*      */   }
/*      */ 
/*      */   public static boolean contentEquals(Reader input1, Reader input2)
/*      */     throws IOException
/*      */   {
/* 1254 */     if (!(input1 instanceof BufferedReader)) {
/* 1255 */       input1 = new BufferedReader(input1);
/*      */     }
/* 1257 */     if (!(input2 instanceof BufferedReader)) {
/* 1258 */       input2 = new BufferedReader(input2);
/*      */     }
/*      */ 
/* 1261 */     int ch = input1.read();
/* 1262 */     while (-1 != ch) {
/* 1263 */       int ch2 = input2.read();
/* 1264 */       if (ch != ch2) {
/* 1265 */         return false;
/*      */       }
/* 1267 */       ch = input1.read();
/*      */     }
/*      */ 
/* 1270 */     int ch2 = input2.read();
/* 1271 */     return ch2 == -1;
/*      */   }
/*      */ 
/*      */   static
/*      */   {
/*  112 */     StringWriter buf = new StringWriter(4);
/*  113 */     PrintWriter out = new PrintWriter(buf);
/*  114 */     out.println();
/*  115 */     LINE_SEPARATOR = buf.toString();
/*      */   }
/*      */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.commons.io.IOUtils
 * JD-Core Version:    0.6.0
 */