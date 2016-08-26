package wusc.edu.pay.common.web.context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 使用ThreadLocal来存储Session,以便实现Session any where.<br/>
 * ThreadLocal为解决多线程程序的并发问题提供了一种新的思路。使用这个工具类可以很简洁地编写出优美的多线程程序。<br/>
 * ThreadLocal很容易让人望文生义，想当然地认为是一个“本地线程”。其实，ThreadLocal并不是一个Thread，而是Thread的局部变量，<br/>
 * 也许把它命名为ThreadLocalVariable更容易让人理解一些。当使用ThreadLocal维护变量时，<br/>
 * ThreadLocal为每个使用该变量的线程提供独立的变量副本 ，所以每一个线程都可以独立地改变自己的副本，而不会影响其它线程所对应的副本。<br/>
 * 从线程的角度看，目标变量就象是线程的本地变量，这也是类名中“Local”所要表达的意思。<br/>
 * 所以，在Java中编写线程局部变量的代码相对来说要笨拙一些，因此造成线程局部变量没有在Java开发者中得到很好的普及。
 * ThreadLocal的接口方法ThreadLocal类接口很简单，只有4个方法：
 * void set(Object value) 
 * //设置当前线程的线程局部变量的值。
 * public Object get() 
 * //该方法返回当前线程所对应的线程局部变量。
 * public void remove() 
 * //将当前线程局部变量的值删除，目的是为了减少内存的占用，该方法是JDK 5.0新增的方法。
 * //需要指出的是，当线程结束后，对应该线程的局部变量将自动被垃圾回收，
 * 所以显式调用该方法清除线程的局部变量并不是必须的操作，但它可以加快内存回收的速度。
 * protected Object initialValue()
 * //返回该线程局部变量的初始值，该方法是一个protected的方法，显然是为了让子类覆盖而设计的。
 * 这个方法是一个延迟调用方法，在线程第1次调用get()或set(Object)时才执行，并且仅执行1次。ThreadLocal中的缺省//实现直接返回一个null。 
 * 
 * ThreadLocal是如何做到为每一个线程维护变量的副本的呢？
 * 其实实现的思路很简单：在ThreadLocal类中有一个Map，用于存储每一个线程的变量副本，
 * Map中元素的键为线程对象，而值对应线程的变量副本。
 * 
 * 
 * 参考：http://uule.iteye.com/blog/870464 <br/>
 * 
 * @author WuShuicheng(吴水成).
 * @email wu-sc@foxmail.com .
 * @version 1.0, 2013-5-10,下午4:08:40.
 */
public class ThreadLocalContext {

	// private final static Logger log = Logger.getLogger(ThreadLocalContext.class);

	private static ThreadLocal<HttpServletRequest> threadLocalHttpRequest = new ThreadLocal<HttpServletRequest>();
	private static ThreadLocal<HttpServletResponse> threadLocalHttpResponse = new ThreadLocal<HttpServletResponse>();

	public static void setHttpRequest(HttpServletRequest request) {
		threadLocalHttpRequest.set(request);
	}

	public static HttpServletRequest getHttpRequest() {
		// log.info("=== get threadLocalHttpRequest");
		return threadLocalHttpRequest.get();
	}

	public static void setHttpResponse(HttpServletResponse response) {
		threadLocalHttpResponse.set(response);
	}

	public static HttpServletResponse getHttpResponse() {
		return threadLocalHttpResponse.get();
	}

	public static void remove() {
		threadLocalHttpRequest.remove();
		threadLocalHttpResponse.remove();
	}
}