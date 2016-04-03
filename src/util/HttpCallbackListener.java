package util;
//通过这个接口来回调服务返回的结果
public interface HttpCallbackListener {
	void onFinish(String response);
	void onError(Exception e);
}
