# ApiMock
基于Retrofit2+Rxjava2模拟api数据请求工具

### 为什么要用apimock

在Android日常开发中，我们使用retrofit+Rxjava获取接口数据。使用apimock有以下好处：

1. Android UI需求开发完成了，可以不用等待后台接口发布即可根据已定好的数据结构模拟数据进行联调。
2. 方便mock出你想要的各种场景数据，快速验证不同场景下展现不同的界面。真正实现与后端进行并行工作。

### 使用场景： Retrofit2 + Rxjava2。

### 如何使用ApiMock

1. 在需要模拟数据的module中添加依赖：
   ```
   dependencies {  
      implementation 'com.android.util:apimock:1.0.1'
   }
   ```
   注意： 项目需要引入以下库（版本号可不一致）：
   ```
   implementation 'com.squareup.retrofit2:retrofit:2.3.0'
   implementation 'io.reactivex.rxjava2:rxandroid:2.0.2'
   implementation "io.reactivex.rxjava2:rxjava:2.1.14"
   implementation 'com.squareup.retrofit2:converter-gson:2.3.0'
   implementation 'com.squareup.retrofit2:adapter-rxjava2:2.3.0'
   ```
2. 在Application的onCreate方法中初始化,方法如下：
   ```
   /**
    * 初始化mock数据源
    * @param context
    * @param fileName  assets文件夹下文件名称
    * @param mockDataUrl  远程mockdata文件地址(不填或者为空则默认从本地assets获取)
    */
    public static void init(Context context, String fileName, String mockDataUrl)
   ```
   `ApiMock.init(this, "mockdata.json"); `
   
   或者
   
   `ApiMock.init(this, "mockdata.json","http://10.181.52.38:8081/html/mockdata.json");`
  
   其中的`mockdata.json`文件是定义在assets文件夹下的mock数据, 第三个参数`mockDataUrl`是模拟数据存放的远程地址（比如存放在远程或者本地服务器中），不填或者为空则默认从本地assets获取。
   模拟数据格式如下(字段名称可根据需要修改)：(此处的common/getversion 是指对应Retrofit中注解的api的path部分)
    ```
     {
        "common/getversion": {
          "code": 200,
          "message": "测试一下",
          "data": {
            "version": "5.2.0",
            "url": "http://test.com/test.apk",
            "description": "1.  全新UI体验\r\n2.  优化性能，修复已知问题",
            "forceUpdate": false
          }
        }
      }
     ```
   
3. 在项目中使用apimock去mock需要的数据
   ```
   ApiMock.getApiService(Constants.BASE_URL, ApiService.class)
        .getversion()
        .subscribe(new Consumer<BaseResponse<VersionInfoEntity>>() {
             @Override
             public void accept(BaseResponse<VersionInfoEntity> versinfon) throws Exception {
                 Toast.makeText(MainActivity.this, versinfon.data.toString(),
                                    Toast.LENGTH_SHORT).show();
                }
             }, new Consumer<Throwable>() {
              @Override
              public void accept(Throwable throwable) throws Exception {
                  Toast.makeText(MainActivity.this, "onFailure:"+throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                 }
         });
   ```
   
