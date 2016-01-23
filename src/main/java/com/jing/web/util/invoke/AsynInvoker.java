package com.jing.web.util.invoke;

/**
 * 使用子线程去执行指定对象中的方法
 * @author bxy-jing
 *
 * @param <T>
 */
public class AsynInvoker<T> {
	private T ret;
	private Thread t;
	
	/**
	 * 构造函数
	 * @param taget
	 * @param method
	 * @param returnType
	 * @param args
	 */
	public AsynInvoker(final Object taget, final String method, final Class<T> returnType, final Object... args) {
		t = new Thread(){
			@Override
			public void run(){
				ret = Invoker.invoke(taget, method, returnType, args);
			}
		};
		t.start();
	}
	
	public AsynInvoker(final Object taget, final String method, final Object... args) {
		t = new Thread(){
			@Override
			public void run(){
				Invoker.invoke(taget, method, args);
			}
		};
		t.start();
	}
	
	public T getData(){
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return ret;
	}
}
