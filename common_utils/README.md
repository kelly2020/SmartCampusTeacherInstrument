# common_utils
common_utils file,network,eg

Commonlibs 做为一会依赖工程可以以最快的速度完成网络请求

使用需知：<br/>
  1，项目初始使用gradle为编译工具，IDEA跟android studio用户可以直接clone项目依赖

  2, 没有使用gradle的用户需要下载这三个jar文件<br/>
     <li>retrofit:2.0.0-beta2.jar</li> <br/>
     <li>converter-gson:2.0.0-beta2.jar</li><br/>
     <li>logging-interceptor:2.6.0.jar</li><br/>
  
  3，最后要注意的是此版本为2.0的版本，使用的api跟1.9的有点不同，如果有什么问题可以联系我
  


<h3>Usage<h3>
	(1). 在项目中建立一个专门管理URL的类，当然名字可随便取，方便起见，我用Urls.class 并继承AppURL类
		
		@Host("http://192.168.1.10/")
		public class Urls extends AppURL{
			@Override 
			public String getUrl() {
				return null;
			}
		}
		
		@Host 配置项目的基础url
		
		
		
	(2).  建立Apiservice类，类名可以随便取 ，但一定注意这个一定要是一个接口
		public interface Apiservice {
			@POST("/user/login")
			Call<String> login(@Query("username") String username);
		}
		
	
	(3).  最后一步在基类中声明框架
		public class BaseActivity extends Activity {
			
			public Apiservice apiservice;
			
			public void onCreate(Bundle saveInstance) {
				super.onCreate(saveInstance);
				
				apiservice = ServiceGenerator.generate().setEndpoint(Urls.class).getApiService(Apiservice.class);
			}
		
		}
		
	(4). 在子类中可以直接使用
		Call<String> call = apiservice.login("username");
		call.enqueue(new Callback<String> {
			
			@Override 
			public void onResponse(Response<String> response,Retrofit retrofit) {
				if (response.isSuccess()) { //200
					String body = response.body();
				}else {
					APIError apierror = ErrorUtils.parseError(response,retrofit);
					Log.e("Error:",apierror.message());
				}
			}
			
			@Override
			public void onFailure(Throwable t) { //400-599 http status code
				//TODO 可以自己处理
			}
			
		});
		
		以上两个方法都在MainThread中，可以更新UI
		
		
		
		
	注意 ： 项目中使用了注解功能 ，可省去findviewbyid()的代码
	
	但是需要activity继承自BaseActivity这个类
	public class A extends BaseActivity {
		
		@InjectView(id = R.id.text)
		private TextView textview;
		
		oncreate() {
			setInjectContentView(R.layout.xxxx);
		}
		
		//.......后续代码自己完成
	}
	
	基础框架可正常运行
		
		
		
		
		
		
		
		
		
