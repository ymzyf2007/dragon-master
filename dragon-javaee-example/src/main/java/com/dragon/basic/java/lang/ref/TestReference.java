package com.dragon.basic.java.lang.ref;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.LinkedList;

public class TestReference {
	
	private static ReferenceQueue<VeryBig> rq = new ReferenceQueue<VeryBig>();
	
	public static void checkQueue() {
		Reference<? extends VeryBig> ref = null;
		while((ref = rq.poll()) != null) {
			System.out.println("In queue：" + ((VeryBigWeakReference) (ref)).id);
		}
	}
	
	public static void main(String[] args) {
		int size = 3;
		LinkedList<WeakReference<VeryBig>> weakList = new LinkedList<WeakReference<VeryBig>>();
		for(int i = 0; i < size; i++) {
			weakList.add(new VeryBigWeakReference(new VeryBig("Weak " + i), rq));
			System.out.println("Just created weak：" + weakList.getLast());
		}
		
		System.gc();
		// 下面休息几秒，让上面的垃圾回收线程运行完成
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		checkQueue();
	}

}

class VeryBig {
	public String id;
	// 占用空间，让线程进行回收
	byte[] b = new byte[2 * 1024];
	
	public VeryBig(String id) {
		this.id = id;
	}
	
	protected void finalize() {
		System.out.println("Finalizing VeryBig " + id);
	}
}

class VeryBigWeakReference extends WeakReference<VeryBig> {
	
	public String id;

	public VeryBigWeakReference(VeryBig big, ReferenceQueue<VeryBig> rq) {
		super(big, rq);
		this.id = big.id;
	}
	
	protected void finalize() {
		System.out.println("Finalizing VeryBigWeakReference " + id);
	}
	
}

/*
 * 输出结果
Just created weak：test.java.util.reference.VeryBigWeakReference@530cf2
Just created weak：test.java.util.reference.VeryBigWeakReference@76fba0
Just created weak：test.java.util.reference.VeryBigWeakReference@181ed9e
Finalizing VeryBig Weak 2
Finalizing VeryBig Weak 1
Finalizing VeryBig Weak 0
In queue：Weak 1
In queue：Weak 2
In queue：Weak 0
*/

