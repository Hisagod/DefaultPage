# Android缺省页控件封装

![](a.gif)

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
	        implementation 'com.github.hisgod:DefaultPage:0.0.2'
	}
```

# 使用

## 支持属性

> DefaultView格式

```xml
<?xml version="1.0" encoding="utf-8"?>
<com.aib.view.DefaultView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/dv"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:empty_layout="@layout/empty"
    app:error_layout="@layout/error"
    app:load_layout="@layout/load">
	...
</com.aib.view.DefaultView>
```

> 添加自定义空页面XML布局

```xml
app:empty_layout="@layout/empty"
```

> 添加自定义错误页面XML布局

```xml
app:error_layout="@layout/error"
```

错误页XML布局规范如下

* 错误页XML布局中给任何一个控件添加**唯一ID**，开发者不需要每次在网络请求的错误回调设置**重试控件**点击事件

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:gravity="center"
    android:orientation="vertical">

    <TextView
        android:id="@+id/tv_error_tip"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:text="错误测试" />

    <Button
        android:id="@+id/retry"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="重试" />
</LinearLayout>
```

网络请求错误回调，直接调用`showError`可响应设置了`id`为`retry`点击事件

```kotlin
//方式一：显示错误信息提示
tv_error_tip.text = it.msg
//设置retry的ID控件点击事件
dv.showError {
	getData()
}
```

> 添加自定义加载XML布局

```xml
app:load_layout="@layout/load"
```

## 使用步骤

1. 添加DefaultView控件，并添加自定义加载，自定义错误，自定义空布局

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

2. 网络请求回调处，根据回调的标识，进行显示相对应的加载页，错误页，空数据页，成功页的显示

```kotlin
    private fun getData(count: Int) {
        vm.getOne(count).observe(this, androidx.lifecycle.Observer {
            when (it.status) {
                NetStatus.LOAD -> {
                    //显示加载UI
                    dv.showLoad()
                }
                NetStatus.EMPTY -> {
                    //首次获取数据为null
                    dv.showEmpty()
                }
                NetStatus.SUCCESS -> {
                    //第一步：显示成功UI
                    dv.showSuccess()
                    //第二步：设置UI数据
                    tv.text = it.data.toString()
                }
                NetStatus.ERROR -> {
                    //方式一：显示错误信息提示
                    tv_error_tip.text = it.msg
                    //设置retry的ID控件点击事件
                    dv.showError {
                        getData(count)
                    }

                    //方式二：显示错误UI，不包含retry的ID控件点击事件相应
                    //tv_error_tip.text = it.msg
                    //dv.showError()
                }
            }
        })
    }
```

**详细使用，请clone仓库代码，run起来熟悉**