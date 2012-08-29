/*     */ package com.jidesoft.utils;
/*     */ 
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.beans.PropertyChangeSupport;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.BlockingQueue;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.ExecutionException;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.FutureTask;
/*     */ import java.util.concurrent.LinkedBlockingQueue;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.ThreadPoolExecutor;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.TimeoutException;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import java.util.concurrent.locks.Condition;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.Timer;
/*     */ 
/*     */ public abstract class SwingWorker<T, V>
/*     */   implements Future<T>, Runnable
/*     */ {
/*     */   private static final int MAX_WORKER_THREADS = 10;
/*     */   private volatile int progress;
/*     */   private volatile StateValue state;
/*     */   private final FutureTask<T> future;
/*     */   private final PropertyChangeSupport propertyChangeSupport;
/*     */   private AccumulativeRunnable<V> doProcess;
/*     */   private AccumulativeRunnable<Integer> doNotifyProgressChange;
/* 202 */   private static final AccumulativeRunnable<Runnable> doSubmit = new DoSubmitAccumulativeRunnable(null);
/*     */ 
/* 205 */   private static ExecutorService executorService = null;
/*     */ 
/*     */   public SwingWorker()
/*     */   {
/* 232 */     Callable callable = new Callable()
/*     */     {
/*     */       public T call() throws Exception {
/* 235 */         SwingWorker.this.setState(SwingWorker.StateValue.STARTED);
/* 236 */         return SwingWorker.this.doInBackground();
/*     */       }
/*     */     };
/* 240 */     this.future = new FutureTask(callable)
/*     */     {
/*     */       protected void done() {
/* 243 */         SwingWorker.this.doneEDT();
/* 244 */         SwingWorker.this.setState(SwingWorker.StateValue.DONE);
/*     */       }
/*     */     };
/* 248 */     this.state = StateValue.PENDING;
/* 249 */     this.propertyChangeSupport = new SwingWorkerPropertyChangeSupport(this);
/* 250 */     this.doProcess = null;
/* 251 */     this.doNotifyProgressChange = null;
/*     */   }
/*     */ 
/*     */   protected abstract T doInBackground()
/*     */     throws Exception;
/*     */ 
/*     */   public final void run()
/*     */   {
/* 273 */     this.future.run();
/*     */   }
/*     */ 
/*     */   protected final void publish(V[] chunks)
/*     */   {
/* 338 */     synchronized (this) {
/* 339 */       if (this.doProcess == null)
/* 340 */         this.doProcess = new AccumulativeRunnable()
/*     */         {
/*     */           public void run(List<V> args) {
/* 343 */             SwingWorker.this.process(args);
/*     */           }
/*     */ 
/*     */           protected void submit()
/*     */           {
/* 348 */             SwingWorker.doSubmit.add(new Runnable[] { this });
/*     */           }
/*     */         };
/*     */     }
/* 353 */     this.doProcess.add(chunks);
/*     */   }
/*     */ 
/*     */   protected void process(List<V> chunks)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void done()
/*     */   {
/*     */   }
/*     */ 
/*     */   protected final void setProgress(int progress)
/*     */   {
/* 405 */     if ((progress < 0) || (progress > 100)) {
/* 406 */       throw new IllegalArgumentException("the value should be from 0 to 100");
/*     */     }
/* 408 */     if (this.progress == progress) {
/* 409 */       return;
/*     */     }
/* 411 */     int oldProgress = this.progress;
/* 412 */     this.progress = progress;
/* 413 */     if (!getPropertyChangeSupport().hasListeners("progress")) {
/* 414 */       return;
/*     */     }
/* 416 */     synchronized (this) {
/* 417 */       if (this.doNotifyProgressChange == null)
/* 418 */         this.doNotifyProgressChange = new AccumulativeRunnable()
/*     */         {
/*     */           public void run(List<Integer> args)
/*     */           {
/* 422 */             SwingWorker.this.firePropertyChange("progress", args.get(0), args.get(args.size() - 1));
/*     */           }
/*     */ 
/*     */           protected void submit()
/*     */           {
/* 429 */             SwingWorker.doSubmit.add(new Runnable[] { this });
/*     */           }
/*     */         };
/*     */     }
/* 434 */     this.doNotifyProgressChange.add(new Integer[] { Integer.valueOf(oldProgress), Integer.valueOf(progress) });
/*     */   }
/*     */ 
/*     */   public final int getProgress()
/*     */   {
/* 443 */     return this.progress;
/*     */   }
/*     */ 
/*     */   public final void execute()
/*     */   {
/* 456 */     getWorkersExecutorService().execute(this);
/*     */   }
/*     */ 
/*     */   public final boolean cancel(boolean mayInterruptIfRunning)
/*     */   {
/* 464 */     return this.future.cancel(mayInterruptIfRunning);
/*     */   }
/*     */ 
/*     */   public final boolean isCancelled()
/*     */   {
/* 471 */     return this.future.isCancelled();
/*     */   }
/*     */ 
/*     */   public final boolean isDone()
/*     */   {
/* 478 */     return this.future.isDone();
/*     */   }
/*     */ 
/*     */   public final T get()
/*     */     throws InterruptedException, ExecutionException
/*     */   {
/* 519 */     return this.future.get();
/*     */   }
/*     */ 
/*     */   public final T get(long timeout, TimeUnit unit)
/*     */     throws InterruptedException, ExecutionException, TimeoutException
/*     */   {
/* 529 */     return this.future.get(timeout, unit);
/*     */   }
/*     */ 
/*     */   public final void addPropertyChangeListener(PropertyChangeListener listener)
/*     */   {
/* 548 */     getPropertyChangeSupport().addPropertyChangeListener(listener);
/*     */   }
/*     */ 
/*     */   public final void removePropertyChangeListener(PropertyChangeListener listener)
/*     */   {
/* 564 */     getPropertyChangeSupport().removePropertyChangeListener(listener);
/*     */   }
/*     */ 
/*     */   public final void firePropertyChange(String propertyName, Object oldValue, Object newValue)
/*     */   {
/* 587 */     getPropertyChangeSupport().firePropertyChange(propertyName, oldValue, newValue);
/*     */   }
/*     */ 
/*     */   public final PropertyChangeSupport getPropertyChangeSupport()
/*     */   {
/* 605 */     return this.propertyChangeSupport;
/*     */   }
/*     */ 
/*     */   public final StateValue getState()
/*     */   {
/* 620 */     if (isDone()) {
/* 621 */       return StateValue.DONE;
/*     */     }
/*     */ 
/* 624 */     return this.state;
/*     */   }
/*     */ 
/*     */   private void setState(StateValue state)
/*     */   {
/* 634 */     StateValue old = this.state;
/* 635 */     this.state = state;
/* 636 */     firePropertyChange("state", old, state);
/*     */   }
/*     */ 
/*     */   private void doneEDT()
/*     */   {
/* 643 */     Runnable doDone = new Runnable()
/*     */     {
/*     */       public void run() {
/* 646 */         SwingWorker.this.done();
/*     */       }
/*     */     };
/* 649 */     if (SwingUtilities.isEventDispatchThread()) {
/* 650 */       doDone.run();
/*     */     }
/*     */     else
/* 653 */       doSubmit.add(new Runnable[] { doDone });
/*     */   }
/*     */ 
/*     */   private static synchronized ExecutorService getWorkersExecutorService()
/*     */   {
/* 667 */     if (executorService == null)
/*     */     {
/* 669 */       ThreadFactory threadFactory = new ThreadFactory()
/*     */       {
/* 671 */         final AtomicInteger threadNumber = new AtomicInteger(1);
/*     */ 
/*     */         public Thread newThread(Runnable r) {
/* 674 */           StringBuilder name = new StringBuilder("SwingWorker-pool-");
/*     */ 
/* 676 */           name.append(System.identityHashCode(this));
/* 677 */           name.append("-thread-");
/* 678 */           name.append(this.threadNumber.getAndIncrement());
/*     */ 
/* 680 */           Thread t = new Thread(r, name.toString());
/*     */ 
/* 682 */           if (t.isDaemon())
/* 683 */             t.setDaemon(false);
/* 684 */           if (t.getPriority() != 5)
/* 685 */             t.setPriority(5);
/* 686 */           return t;
/*     */         }
/*     */       };
/* 697 */       executorService = new ThreadPoolExecutor(0, 10, 5L, TimeUnit.SECONDS, new LinkedBlockingQueue(), threadFactory)
/*     */       {
/* 702 */         private final ReentrantLock pauseLock = new ReentrantLock();
/* 703 */         private final Condition unpaused = this.pauseLock.newCondition();
/* 704 */         private boolean isPaused = false;
/* 705 */         private final ReentrantLock executeLock = new ReentrantLock();
/*     */ 
/*     */         public void execute(Runnable command)
/*     */         {
/* 734 */           this.executeLock.lock();
/*     */           try
/*     */           {
/* 737 */             this.pauseLock.lock();
/*     */             try {
/* 739 */               this.isPaused = true;
/*     */             }
/*     */             finally {
/* 742 */               this.pauseLock.unlock();
/*     */             }
/*     */ 
/* 745 */             setCorePoolSize(10);
/* 746 */             super.execute(command);
/* 747 */             setCorePoolSize(0);
/*     */ 
/* 749 */             this.pauseLock.lock();
/*     */             try {
/* 751 */               this.isPaused = false;
/* 752 */               this.unpaused.signalAll();
/*     */             }
/*     */             finally {
/* 755 */               this.pauseLock.unlock();
/*     */             }
/*     */           }
/*     */           finally {
/* 759 */             this.executeLock.unlock();
/*     */           }
/*     */         }
/*     */ 
/*     */         protected void afterExecute(Runnable r, Throwable t)
/*     */         {
/* 765 */           super.afterExecute(r, t);
/* 766 */           this.pauseLock.lock();
/*     */           try {
/* 768 */             while (this.isPaused)
/* 769 */               this.unpaused.await();
/*     */           }
/*     */           catch (InterruptedException ignore)
/*     */           {
/*     */           }
/*     */           finally
/*     */           {
/* 776 */             this.pauseLock.unlock();
/*     */           }
/*     */         } } ;
/*     */     }
/* 781 */     return executorService;
/*     */   }
/*     */ 
/*     */   private class SwingWorkerPropertyChangeSupport extends PropertyChangeSupport
/*     */   {
/*     */     SwingWorkerPropertyChangeSupport(Object source)
/*     */     {
/* 810 */       super();
/*     */     }
/*     */ 
/*     */     public void firePropertyChange(PropertyChangeEvent evt)
/*     */     {
/* 815 */       if (SwingUtilities.isEventDispatchThread()) {
/* 816 */         super.firePropertyChange(evt);
/*     */       }
/*     */       else
/* 819 */         SwingWorker.doSubmit.add(new Runnable[] { new Runnable(evt)
/*     */         {
/*     */           public void run() {
/* 822 */             SwingWorker.SwingWorkerPropertyChangeSupport.this.firePropertyChange(this.val$evt);
/*     */           }
/*     */         }
/*     */          });
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class DoSubmitAccumulativeRunnable extends AccumulativeRunnable<Runnable>
/*     */     implements ActionListener
/*     */   {
/*     */     private static final int DELAY = 33;
/*     */ 
/*     */     protected void run(List<Runnable> args)
/*     */     {
/* 790 */       for (Runnable runnable : args)
/* 791 */         runnable.run();
/*     */     }
/*     */ 
/*     */     protected void submit()
/*     */     {
/* 797 */       Timer timer = new Timer(33, this);
/* 798 */       timer.setRepeats(false);
/* 799 */       timer.start();
/*     */     }
/*     */ 
/*     */     public void actionPerformed(ActionEvent event) {
/* 803 */       run();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static enum StateValue
/*     */   {
/* 214 */     PENDING, 
/*     */ 
/* 218 */     STARTED, 
/*     */ 
/* 223 */     DONE;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.utils.SwingWorker
 * JD-Core Version:    0.6.0
 */