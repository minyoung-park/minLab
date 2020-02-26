package miny.common.apis;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import miny.common.CValue;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

@Service
public class CallApi {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	private String baseUrl = "https://munbs.com";

	private CallApiInterface callString;
	private CallApiInterface call;
	private CallApiInterface callXml;

	private OkHttpClient client;

	@PostConstruct
	public void init() {
		client = closeableOKHTTP();
	}

	@Bean(name = "closeableOKHTTP")
	public OkHttpClient closeableOKHTTP() {
		ConnectionPool pool = new ConnectionPool(5, 1, TimeUnit.SECONDS);
		OkHttpClient client = new OkHttpClient.Builder() //
				.connectTimeout(CValue.CONNECT_TIMEOUT, TimeUnit.SECONDS) // 연결 타임아웃 시간 설정
				.writeTimeout(CValue.WRITE_TIMEOUT, TimeUnit.SECONDS) // 쓰기 타임아웃 시간 설정
				.readTimeout(CValue.READ_TIMEOUT, TimeUnit.SECONDS) // 읽기 타임아웃 시간 설정
				.followRedirects(true) //
				.retryOnConnectionFailure(false) //
				.connectionPool(pool) //
				.build();
		return client;
	}

	@PreDestroy
	public void destroy() {
		if (client != null) {
			ConnectionPool connectionPool = client.connectionPool();
			connectionPool.evictAll();
			logger.info("OKHTTP connections iddle: {}, all: {}", connectionPool.idleConnectionCount(),
					connectionPool.connectionCount());
			ExecutorService executorService = client.dispatcher().executorService();
			executorService.shutdown();
			try {
				executorService.awaitTermination(3, TimeUnit.MINUTES);
				logger.info("OKHTTP ExecutorService closed.");
			} catch (InterruptedException e) {
				logger.warn("InterruptedException on destroy()", e);
			}
		}
	}

	public CallApiInterface getCallString() {
		if (this.callString == null) {
			Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).client(client)
					.addConverterFactory(ScalarsConverterFactory.create())
					// .addConverterFactory(GsonConverterFactory.create())
					.build();
			this.callString = retrofit.create(CallApiInterface.class);
		}
		return this.callString;
	}

	public CallApiInterface getCall() {
		if (this.call == null) {
			Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).client(client)
					.addConverterFactory(GsonConverterFactory.create()).build();
			this.call = retrofit.create(CallApiInterface.class);
		}
		return this.call;
	}

	public CallApiInterface getCallXml() {
		if (this.callXml == null) {
			Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).client(client)
					.addConverterFactory(SimpleXmlConverterFactory.create())
					.addConverterFactory(GsonConverterFactory.create()).build();
			this.callXml = retrofit.create(CallApiInterface.class);
		}
		return this.callXml;
	}
}
