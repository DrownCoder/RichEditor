# RichEditor
富文本编辑器

>考虑到富文本编辑需要支持长文本的类型，目前大部分富文本使用自定义单个的Edittext无法实现View的复用，于是利用RecycleView为基础实现的富文本编辑器，目前处于开发过程中
### 前言
对于富文本编辑器的实现，首先我们肯定会想到实现的编辑器需要支持的几个必要特性：
>1.涉及**大量**文字，图片，文字样式的展示与编辑。  
>2.涉及**极其复杂**的用户交互。  

目前Github上我所了解的富文本编辑器基本上实现方式基于两种：
* 1.基于WebView拓展的富文本编辑器。
* 2.基于EditText重写的富文本编辑器。

对于这两种方案，这里提出一些我个人的看法。
###### 1.WebView实现
首先WebView的渲染性能一个弊端所在，其次当涉及极其复杂的人机交互，WebView的实现起来就会比较困难。还有一点就是WebView的兼容性也是一个需要考虑的一点。
###### 2.EditText重写
对于重写单个EditText，确实对于交互和文字渲染，样式支持，都有很强的拓展性。但是考虑到会存在大量的图片，这里就需要考虑到内存的情况，对于EditText来说，肯定不存在View的复用，基本上有多少图片，就要多少内存。另一方面原生的TextView对于大量文字的渲染一直被人诟病，对此也有很多对于TextView的性能优化的方案。
###### RecyclerView实现
所以我最终选择使用RecyclerView作为实现富文本编辑器的实现方案。虽然有坑，但是也是一种可行性方案。（豆瓣的编辑器就是使用RecyclerView实现）  
**优点**：首先RecyclerVie作为一款原生组件，对于大量UI组件的展示有非常良好的性能，其次RecyclerView的复用机制对于内存消耗的控制提供了的很好的支持。 
**缺点：**：当然这里也不是说RecyclerView就绝对是实现富文本编辑器的首选方案，我在实现的过程中也遇到了很多大坑，这里就随便列举几个：  
1.焦点的控制  
2.数据的拼接  
3.样式的存储  
4.光标的位置  
and much more...  
还好最后这些坑也找到了解决方案，所以这里分享一下这种实现方案，也为有需求的人提供一种可做参考的实现方案吧。  
### 已实现功能  
1.文本的粗体、斜体、下划线、中划线、删除线、超链接、引用样式、H1、H2、H3、H4。  
2.图片的插入和删除  
3.选中文本实时更改样式  
4.任意位置换行保持样式。  
5.两行删除为一行保持样式。  
6.随光标实时显示文字样式到控制面板。  
7.任意位置插入样式  
8.最终编辑文本转MarkDown(有Bug～。。。。)  
等。。。
### 实现效果
![效果图](https://upload-images.jianshu.io/upload_images/7866586-08b3647c4e8e0442.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)  
![样式拼接](https://upload-images.jianshu.io/upload_images/7866586-93e0b97fb8bd4aa2.gif?imageMogr2/auto-orient/strip)
>对于RecyclerView实现而言，回车对应的操作就是增加一个Model，所以回车换行和删除就需要做非常多的逻辑情况处理，并且还涉及到样式索引的拼接和分割，总之是一个大坑。

![样式选中修改](https://upload-images.jianshu.io/upload_images/7866586-839cb0eed0dd0436.gif?imageMogr2/auto-orient/strip)
>选中后，需要对光标，样式的索引，样式的清除和分割，还有样式的重新创建和赋值，大坑啊大坑。

![样式同步](https://upload-images.jianshu.io/upload_images/7866586-6d220d43e05ed961.gif?imageMogr2/auto-orient/strip)
>光标对应到对应样式的字符串时，下面的面板对应实时更改当前样式，需要利用区间的逻辑判断，对光标和样式区间进行逻辑判断，坑越来越多。。。

还有许多复杂的交互处理，这里没有展示，具体大家可以查看源码。
### 项目地址
[RichEditor](https://github.com/DrownCoder/RichEditor)
### 使用方式
这里并没有将工程发布到JitPack，因为作为一款富文本编辑器，每人都有自己独特的需求和交互方式，没办法做到一个富文本能够应付所有的需求。并且由于富文本编辑器的交互逻辑确实复杂，没办法保证兼容到所有到交互和情况，所以这里只是尽自己可能实现到交互情况。

1.引入editor的lib到工程
2.xml加入编辑器
```
<com.study.xuan.editor.widget.Editor
        android:id="@+id/editor"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
```
就可以正常使用了。
#### 进阶
简单到封装了一下使用利用RichHelper
```
//绑定xml中的Editor
public void attach(Editor editor);
//new 一个Editor
public Editor buildEditor(Context context)
//部分事件回调
public void setCallBack(onEditorEventListener callBack)
//操作面板右侧空白增加自定义布局
public void setMoreOperateLayout(View view)
//异步转义MarkDown
public void toMarkDown()
```
```
public interface onEditorCallback { 
    //行数量变化时
    void onLineNumChange(List<RichModel> data);
    //点击操作面板的图片添加图片，可以使用自己项目中的图片框架，选中后对应调用editor.addPhoto(List<String> data);方法即可
    void onPhotoEvent();
    //转义MarkDown的进度回调
    void onMarkDownTaskDoing(int progress, int max);
    //转义MarkDown成功
    void onMarkDownTaskFinished(String markdown);
}
```
### 架构图
![架构图](https://upload-images.jianshu.io/upload_images/7866586-99c0138eac156e24.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#### 1.RichBuilder  
全局单例，底层架构，帮助RichEditor整体的功能实现。  
###### 1.1 IPanel  
实现类PanelBuilder包含两个实现类，FontParamBuilder表示字符类型的样式，ParagraphBuilder表示段落类型的样式。Panle和Editor的通信方式是通过底层的RichBuilder单例中的IPanel。  
###### 1.2 IAbstractFactory  
抽象工程类，用于外层对span类型的创建。其中抽象工厂又分为ICharacterStyleFactory,IParagraphFactory,IUpdateAppearanceFactory三种span工厂，分别对应CharacterFactory(字符样式span工厂)，ParagraphFactory(段落样式工厂)，（自定义工厂未实现）。  
###### 1.3 ISearchStrategy  
搜索策略，用于对于某一段落中的span样式的遍历和处理，其中NormalSearch实现ISearchStrategy，利用常规遍历处理（可以自定义实现快排或其他效率高的排序方式进行处理）  
###### 1.4 IParamManager  
参数管理接口，实现类对应ParamManager，用于对当前样式和预输入样式的对比和处理。  
#### 2.Editor  
编辑器实现类，继承于RecyclerView，Adapter对应RichAdapter，Model对应RichModel。  
###### 2.1 ISpanFilter  
输入过滤器，用于对输入和删除时的样式处理。  
**SpanStep1Filter**  
第一级过滤器，用于处理样式的追加和混杂时，对于`SPAN_EXCLUSIVE_INCLUSIVE`和`SPAN_EXCLUSIVE_EXCLUSIVE`的处理。  
**SpanStep2Filter**  
第二级过滤器，用于处理样式的创建和保持，用于获取当前文本所有的样式集，并记录样式对应的index，保持到对应的RichModel中。  
###### 2.2 ParseAsyncTask  
异步处理，用于处理将数据转换成对应的转换类型。  
**Parse**  
转换接口  
**MarkDownParse**  
转为MarkDown语法的逻辑处理，利用正则表达式。  
###### 2.3 RichModelHelper  
数据处理类，用与合并样式，处理样式等相关的数据处理。  
#### 3.Panel  
Panel表示操作面板，Panel默认使用的是EditorPanelAlpha。Panel通过RichBuilder中的IPanel实现和编辑器Editor的联动。  

### 进度：
【2018.3.17】:一行文本内的富文本编辑。  
【2018.3.19】:完成多行文本的删除，增加一行，下一行删除到上一行。并且保持样式。  
【2018.3.20】:完成多行文本的删除和增加，包括首行回删，行内回车，保持富文本样式随段落移动。  
【2018.3.24】:完成点击后操作栏的同步，保持当前文本的样式和操作栏样式统一。  
【2018.3.29】:完成插入新样式的功能，初步完成编辑的基本功能。  
【2018.4.09】:完成段落样式功能。  
【2018.4.11】:初步完成MarkDown语法转义。
### License
```
Copyright 2017 [DrownCoder] 

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```

