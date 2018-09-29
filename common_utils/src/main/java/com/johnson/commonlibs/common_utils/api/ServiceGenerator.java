package com.johnson.commonlibs.common_utils.api;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.johnson.commonlibs.common_utils.R;
import com.johnson.commonlibs.common_utils.annotations.Host;
import com.johnson.commonlibs.common_utils.contants.DateStyle;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.net.ssl.*;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.concurrent.TimeUnit;

/**
 * Network Utils <br/>
 * Created by zhangwt on 15/9/11.
 *
 * @version 1.0
 */
public final class ServiceGenerator {
    private boolean USE_HTTPS = true; //是否使用https方式请求  true 是https  false 是http 方式
    private volatile static ServiceGenerator INSTANCE;
    private Retrofit.Builder mRetrofit;
    private boolean KEEP_ALIVE = true; //是否使用保持连接方式 false 不保持连接 true 保持连接
    private OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
    private HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    private static Gson gson;

    private ServiceGenerator(Context context) {
        if (!KEEP_ALIVE) {
            okHttpClient.addInterceptor(new HeaderSelectionInterceptor());
        }
        gson = new GsonBuilder().setDateFormat(DateStyle.YYYY_MM_DD_HH_MM_SS).create();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        okHttpClient.addInterceptor(logging);
        okHttpClient.interceptors().add(logging);

        //加入抓包工具
        okHttpClient.addNetworkInterceptor(new StethoInterceptor());
        okHttpClient.connectTimeout(10000, TimeUnit.MILLISECONDS);
        okHttpClient.readTimeout(10000, TimeUnit.MILLISECONDS);
        mRetrofit = new Retrofit.Builder();

        mRetrofit.addConverterFactory(GsonConverterFactory.create(gson));
        //如果使用RxAndroid跟RxJava与Retrofit组合使用的话，必须使用下面一行代码
        mRetrofit.addCallAdapterFactory(RxJavaCallAdapterFactory.create());
        mRetrofit.client(USE_HTTPS ? generateHttpsClient(context) : getHttpClient());
//        mRetrofit.client(generateHttpsClient(context));
//        mRetrofit.client(getUnsafeOkHttpClient());

    }

    /**
     * 使用http方式请求网络
     * @return
     */
    @NonNull
    private OkHttpClient getHttpClient() {
        okHttpClient.connectTimeout(10 , TimeUnit.SECONDS);
        return okHttpClient.build();
    }
    /**
     * https 请求
     *
     * @return
     */
    private OkHttpClient generateHttpsClient(Context context) {
        try {
            SSLSocketFactory socketFactory = null;
//            socketFactory = new NoSSLv3SocketFactory(getSSLConfig(context, R.raw.shsmile).getSocketFactory());
            socketFactory = new NoSSLv3SocketFactory(getSSLConfig(context, R.raw.smartcampus).getSocketFactory());
            okHttpClient.sslSocketFactory(socketFactory);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return okHttpClient.build();
    }

    //对请求头做处理
    static final class HeaderSelectionInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            request = request.newBuilder().addHeader("Connection" , "close").build();
            return chain.proceed(request);
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static SSLContext getSSLConfig(Context context, int certificate) throws CertificateException, IOException,
            KeyStoreException, NoSuchAlgorithmException, KeyManagementException {

        // 从文件中加载CAs
        CertificateFactory cf = null;
        cf = CertificateFactory.getInstance("X.509");

        Certificate ca;

        InputStream cert = context.getResources().openRawResource(certificate);
        ca = cf.generateCertificate(cert);


        // 使用信任的CAs创建一个KeyStore
        String keyStoreType = KeyStore.getDefaultType();
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(null, null);
        keyStore.setCertificateEntry("ca", ca);

        // 根据我们的KeyStore创建TrustManager
        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(keyStore);

        // 使用我们的TrustManager创建SSLSocketFactory
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, tmf.getTrustManagers(), null);

        return sslContext;
    }

//    /**
//     * singleInstance
//     *
//     * @return
//     */
//    public static ServiceGenerator generate(Context context) {//此类做法效率最高并为线程安全的
//        mContext = context;
//        return RestClientFactory.Instance;
//    }

    /**
     * singleInstance
     *
     * @return
     */
    public static ServiceGenerator generate(Context c) {//此类做法效率最高并为线程安全的
        if (INSTANCE == null) {
            synchronized (ServiceGenerator.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ServiceGenerator(c);
                }
            }
        }
        return INSTANCE;
    }

    /**
     * getApiService
     *
     * @param service Service
     * @param <T>
     * @return service
     */
    public <T> T getApiService(Class<T> service) {
        return mRetrofit.build().create(service);
    }

    /**
     * setting host url
     *
     * @param url 标识有@Host的类
     */
    public ServiceGenerator setEndpoint(Class<?> url) {
        if (url.isAnnotationPresent(Host.class)) {
            Host host = url.getAnnotation(Host.class);
            String hostUrl = host.value();
            mRetrofit.baseUrl(hostUrl).build();
            return INSTANCE;
        } else {
            Log.e("Error", "unknow host address : " + url + " , try to add @Host(url)");
        }
        return null;
    }
}

