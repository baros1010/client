package test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class FixedSizeThreadPool {
	// 多个线程
	private List<Thread> workers;
	// 仓库
	private BlockingQueue<Runnable> queue;

	// 具体的线程
	private static class Worker extends Thread {
		private FixedSizeThreadPool pool;

		public Worker(FixedSizeThreadPool pool) {
			this.pool = pool;
		}

		public void run() {
			Runnable task = null;
			while (this.pool.flag||this.pool.queue.size()>0) {
				try {
					// 如果没有任务等待（阻塞状态获取仓库中的任务）
					if(this.pool.flag){
						task = pool.queue.take();
					}else{
						task = pool.queue.poll();
					}
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (task != null) {
					//任务运行
					task.run();
					System.out.println(Thread.currentThread().getName()+"执行任务");
				}

			}
		}
	}

	public FixedSizeThreadPool(int poolsize, int tasksize) {
		// 最大任务数
		this.queue = new LinkedBlockingQueue<>(tasksize);
		this.workers = Collections.synchronizedList(new ArrayList<>());
		for(int i=0 ;i<poolsize;i++){
			Worker worker =new Worker(this);
			worker.start();
			workers.add(worker);
		}
	}
	//向仓库放入任务
	public boolean submit(Runnable runnable){
		return this.queue.offer(runnable);
		
	}
	private volatile boolean flag=true;
	public void shutdown(){
		flag=false;
	}
}
