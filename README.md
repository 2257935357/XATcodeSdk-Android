# 使用文档
## 1.SDK引入
```gradle
 repositories {
				...
        maven {
            url "http://101.37.191.20:9091/repository/maven-releases/"
        }
        maven {
            url "http://101.37.191.20:9091/repository/maven-snapshots/"
        }
			...
    }
```
> SDK需要在` JavaVersion.VERSION_1_8 `环境中使用所以需要设置
```gradle
//在 app/build 写入

android {
    ...
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}

dependencies {
        //sdk库
        implementation 'com.admobile:xatscode-sdk:1.0.4-SNAPSHOT'
        //----start 需要引入这些依赖
        implementation 'com.squareup.retrofit2:retrofit:2.5.0'
        implementation 'com.squareup.retrofit2:converter-gson:2.4.0'
        implementation 'com.squareup.okhttp3:logging-interceptor:3.10.0'
        implementation 'io.reactivex.rxjava2:rxandroid:2.1.0'
        implementation 'com.squareup.retrofit2:adapter-rxjava2:2.4.0'
        implementation 'io.reactivex.rxjava2:rxjava:2.2.8'
        //----end 需要引入这些依赖
}
```




## 2.1 初始化SDK
```java
/**
 * Application初始化
 */
XATSCodeSDK.init(this,"申请的AppId");
```
## 2.2 SDK权限

```xml
<!--网络权限必不可少-->
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```



## 3.监听器
```java
/**
 * 搜索书籍完成接口
 */
interface ISearchBookCompleteListaner {}
/**
 * 书籍目录列表接口
 */
interface IBooksCatalogueCallback {}
/**
 * 书籍章节目录转码接口
 */
interface IBooksTransContentCallback {}
/**
 * 尝试指定Url转小说内容以及获取小说目录列表接口
 */
interface IBooksTransUrlToNovelCallback {}
```


## 4.1 API详情
```java
/**
 * 获取书籍列表
 *
 * @param keyword      关键词
 * @param searchSource 搜索来源 百度，360，神马引擎
 * @param listaner     结果
 */
void obtainBooksRoster(String keyword, int searchSource, ISearchBookCompleteListaner listener)

/**
 * 获取某个书的所有章节目录
 *
 * @param bookUrl  书籍url
 * @param callback
 */
void obtainBooksCatalogue(String bookUrl, IBooksCatalogueCallback callback)

/**
 * 获取对应的章节转码内容
 *
 * @param catalogueUrl 章节url
 * @param callback
 */
void obtainBooksCurrentCatalogueContent(String catalogueUrl, IBooksTransContentCallback callback)

/**
 * 尝试获取指定url小说
 * 给定一个未知的url，尝试获取url内小说详情，成功则返回小说内容以及小说的章节目录列表
 * @param url
 */
void tryToObtainAppointUrlToNovel(String url, IBooksTransUrlToNovelCallback callback)
```

## 4.2 搜索来源
* `SEARCH_TYPE_SOURCE.SEARCH_SHENMA_SOURCE`&nbsp;&nbsp;&nbsp;&nbsp;神马搜索
* `SEARCH_TYPE_SOURCE.SEARCH_BAIDU_SOURCE`&nbsp;&nbsp;&nbsp;&nbsp;百度搜索
* `SEARCH_TYPE_SOURCE.SEARCH_360_SOURCE`&nbsp;&nbsp;&nbsp;&nbsp;360搜索


## 5.Model模型
> 只解释重要参数

| `XATransCodeBookEntity` |     |     |
| ------ | ------ | ------ |
| 参数 | 解释 |  |
| name | 书籍名称  |
| url  | 书籍链接，后续需要传入此字段获取书籍目录|
| | |

<br>

| `XACatalog` | | |
| ------| ------ | ------
| 参数 | 解释| |
|  url | 书籍第XX章URL（`obtainBooksCurrentCatalogueContent`方法传入该url获取章节内容）
|title | 章节名称 | |
| | |

<br>

| `XAContent` | | |
| ------ | ------ | ------
| 参数 | 解释| |
| chapterUrl | 章节url | |
| bookName | 书籍名称 | |
| content | 该章节内容 | |
| chapterName | 章节名称 | |
| XACatalog | 章节结构（见Model模型） | |
| XAContentNavi | 章节导航 | | |
| |  |

<br>

| `XAContentNavi` | | |
| ------ | ------ | ------
| 参数 | 解释| |
| preUrl | 上一章url| |
| nextUrl | 下一章url | |
| catalogUrl | 当前url | |
|| |
> 注：此preUrl,nextUrl参数不推荐使用，可能会出现错误url，最好自行计算需要的章节url可以参考XACatalog

### 使用合作

客户接入前，需要签订协议，按流程发送至商务邮箱：2257935357@qq.com，邮箱确认后方可接入


协议书见项目[文件](https://github.com/2257935357/XATcodeSdk-Android/blob/main/%E4%BE%9D%E6%B3%95%E7%BB%8F%E8%90%A5%E6%89%BF%E8%AF%BA%E4%B9%A6.docx)
