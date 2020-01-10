# Android缺省页控件封装

# 引入
Add it in your root build.gradle at the end of repositories:
```groovy
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
Step 2. Add the dependency
```groovy
	dependencies {
	        implementation 'com.github.hisgod:DefaultPage:0.0.1'
	}
```

# 使用

## 支持属性

```xml
app:empty_layout="@layout/empty"    //自定义空页布局
app:error_layout="@layout/error"    //自定义错误布局
app:load_layout="@layout/load"      //自定义加载布局
```

**注意：错误页面显示时，方便开发者不需要每次在请求失败回调中，进行`findViewById`找到重试按钮的`ID`，`DefaultView`已经封装错误页面中某个控件包含`retry`ID的，即可使用下面错误页函数，并响应设置了`retry`id控件的点击事件**

```kotlin
dv.showError {
	getData()
}
```

> 错误页布局规范如下

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:gravity="center"
    android:orientation="vertical">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:text="错误测试" />
    <!--下面Button添加了retry，所以错误页显示时，可监听到此控件点击事件-->
    <Button
        android:id="@+id/retry"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="重试" />
</LinearLayout>
```

* **所以错误页，可以给任何控件设置`retry`作为`id`，但必须唯一**

## 使用步骤

> 添加DefaultView控件

```xml
<com.aib.view.DefaultView 
    android:id="@+id/dv"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:empty_layout="@layout/empty"
    app:error_layout="@layout/error"
    app:load_layout="@layout/load">

    ... 
    
</com.aib.view.DefaultView>
```

> 结合网络请求库使用（案例使用Retrofit）

```kotlin
        Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.newThread()))
                .baseUrl("https://tenapi.cn/")
                .build()
                .create(ApiService::class.java)
                .getData("广州")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<WeatherBean> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {
                        //显示加载UI
                        dv.showLoad()
                    }

                    override fun onNext(t: WeatherBean) {
                        //显示成功UI
                        dv.showSuccess()
                        tv.text = t.data.ganmao
                    }

                    override fun onError(e: Throwable) {
                        //显示错误UI，包含retry的ID控件点击事件相应
                        dv.showError {
                            getData()
                        }

                        //显示错误UI，不包含retry的ID控件点击事件相应
                        //dv.showError()
                    }
                })
```