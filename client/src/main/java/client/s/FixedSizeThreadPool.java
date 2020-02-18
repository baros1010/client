package test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class FixedSizeThreadPool {
	// ����߳�
	private List<Thread> workers;
	// �ֿ�
	private BlockingQueue<Runnable> queue;

	// ������߳�
	private static class Worker extends Thread {
		private FixedSizeThreadPool pool;

		public Worker(FixedSizeThreadPool pool) {
			this.pool = pool;
		}

		public void run() {
			Runnable task = null;
			while (this.pool.flag||this.pool.queue.size()>0) {
				try {
					// ���û������ȴ�������״̬��ȡ�ֿ��е�����
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
					//��������
					task.run();
					System.out.println(Thread.currentThread().getName()+"ִ������");
				}

			}
		}
	}

	public FixedSizeThreadPool(int poolsize, int tasksize) {
		// ���������
		this.queue = new LinkedBlockingQueue<>(tasksize);
		this.workers = Collections.synchronizedList(new ArrayList<>());
		for(int i=0 ;i<poolsize;i++){
			Worker worker =new Worker(this);
			worker.start();
			workers.add(worker);
		}
	}
	//��ֿ��������
	public boolean submit(Runnable runnable){
		return this.queue.offer(runnable);
		
	}
	private volatile boolean flag=true;
	public void shutdown(){
		flag=false;
	}
}
