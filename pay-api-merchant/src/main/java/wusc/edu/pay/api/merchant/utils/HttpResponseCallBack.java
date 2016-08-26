package wusc.edu.pay.api.merchant.utils;

import java.io.IOException;
import java.io.InputStream;

public interface HttpResponseCallBack {

	public void processResponse(InputStream responseBody) throws IOException;
}
