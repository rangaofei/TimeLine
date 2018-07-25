# sakatimeline

一个简单的时间线控件，采用recyclerview实现。

0.2.3更新：

新增可以控制icon大小的属性：

```xml
app:timeLineIconHeight="30dp"
app:timeLineIconWidth="30dp"
```

新增样式
```xml
app:strokeType="midEndPoint"
```
如图：

  <div align="center">
 <img src="https://github.com/rangaofei/TimeLine/blob/master/Pics/Screenshot_1532504922.jpg" height="480" width="270" >
  </div>

未设置该大小时会自动设置成为timePadding的三分之二；当其中一个设置另一个未设置时，
未设置的值会自动设置成为已设置的值。

## 集成方式

```groovy
    implementation 'com.rangaofei:sakatimeline:0.2.2'
    annotationProcessor 'com.rangaofei:JavaTimeLine:0.2.2'
```
然后修改app级别的module的build.gradle 文件：

```groovy
android {

    defaultConfig {

        //省略代码
        javaCompileOptions{
            annotationProcessorOptions.includeCompileClasspath=true
        }
    }
}
```

假如没出现问题的话就集成成功了

## 使用StepView

StepView内部使用apt处理注解实现了adapter，adapter包含两种布局，key和value。目前推出的只需要使用value布局即可。

首先要定义一个model，这个model要编写一些注解

```java
@TimeLine(valueLayoutId = "R.layout.item_value")
public class StepViewModel {

    @TimeLineTextView(key = false, value = "R.id.value")
    public String text;

    public StepViewModel(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
```
注意，每个变量必须采用public修饰，否则获取不到变量的值

然后先build一次，在需要使用model的时候要写入

```java
private AbstractTimeLineAdapter adapter;

private List<StepViewModel> stepViewModels = new ArrayList<>();
```
系统会自动生成一个StepViewModelViewAdapter文件，这个adapter需要传入一个list作为数据源，
这个list中的item类型必须设置为被标注为TimeLine的类。

```java
stepViewModels.add(new StepViewModel("快递发出"));
stepViewModels.add(new StepViewModel("快递签收"));
stepViewModels.add(new StepViewModel("快递丢失"));
adapter = new StepViewModelAdapter(stepViewModels);
binding.tlv.setTimeLineConfig(adapter, TimeLineType.StepViewType.RIGHT_STEP_PROGRESS);
```
这样我们就完成了基本设置。
运行可以看到效果图：

 <div align="center">
 <img src="https://github.com/rangaofei/TimeLine/blob/master/Pics/simple_stepview_all.png" height="480" width="270" >

 <img src="https://github.com/rangaofei/TimeLine/blob/master/Pics/SimpleStepViewUnnormal.png" height="480" width="270" >

  </div>

   <div align="center">
 <img src="https://github.com/rangaofei/TimeLine/blob/master/Pics/StepViewAnim.gif" height="480" width="270" >
  </div>
## 为item设置不同的样式

注意：目前只支持textview的部分属性的不同样式设置

支持imageview的src显示。

同样是前边的类，稍微修改一下：

```java
@TimeLine(valueLayoutId = "R.layout.item_value")
public class StepViewModel {

    @TimeLineTextView(key = false, id = "R.id.value", style = "R.style.StepView1", styleAnchor = "R.style.StepView2")
    public String text;
    @TimeLineAnchor({"R.id.value"})
    public boolean right;

    public StepViewModel(String text, boolean right) {
        this.text = text;
        this.right = right;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
```

我们为TimeLineTextView加上了style和styleAnchor的值，这两个值是自己定义的style样式，这些样式只包含部分attr
```xml
    <attr name="android:textSize"/>
    <attr name="android:textColor"/>
    <attr name="android:textColorHint"/>
    <attr name="android:textColorLink"/>
    <attr name="android:textStyle"/>
    <attr name="android:typeface"/>
    <attr name="android:fontFamily"/>
    <attr name="android:shadowColor"/>
    <attr name="android:shadowDy"/>
    <attr name="android:shadowDx"/>
    <attr name="android:shadowRadius"/>
```

下面是示例中定义的两个style：

```xml
<style name="StepView1" parent="AppTheme">
    <item name="android:textSize">14sp</item>
    <item name="android:textColor">@color/white</item>
</style>

<style name="StepView2" parent="AppTheme">
    <item name="android:textSize">20sp</item>
    <item name="android:textColor">@color/black</item>
    <item name="backgroundProxy">@color/white</item>
</style>
```

修改主界面代码：

```java
stepViewModels.add(new StepViewModel("快递发出\n我没收到", false));
stepViewModels.add(new StepViewModel("快递签收我收到了", false));
stepViewModels.add(new StepViewModel("快递丢失", true));
```

可以看到第三个item设置为true了，那么StepViewModel中的@TextView注解中的styleAnchor将会生效，
其他未设置为true的item生效的将会是@TextView注解中的style。

背景可以是任意的drawable和color，设置背景使用了动态代理。

<div align="center">
 <img src="https://github.com/rangaofei/TimeLine/blob/master/Pics/StepViewDiff.png" height="480" width="270" >
  </div>

## 替换默认的圆为用户自定义的drawable

首先要定义一个`List<Drawable> list`，这个drawable中添加要替换index位置的drawable。

要注意添加的顺序，将会与step的顺序一致，假如传入null，则会绘制原有的圆圈。
假如添加的drawable数量少于index位置的数量，则会循环利用

```java
list = new ArrayList<>();
list.add(getResources().getDrawable(R.drawable.ic_order));
list.add(null);
((TimeLineView) findViewById(R.id.one)).
        setTimeLineConfig(adapter, TimeLineType.StepViewType.BOTTOM_STEP_PROGRESS, 1, list);
((TimeLineView) findViewById(R.id.two)).
        setTimeLineConfig(adapter, TimeLineType.StepViewType.TOP_STEP_PROGRESS, 1);
((TimeLineView) findViewById(R.id.three)).
        setTimeLineConfig(adapter, TimeLineType.StepViewType.LEFT_STEP_PROGRESS, 3, list);
((TimeLineView) findViewById(R.id.four)).
        setTimeLineConfig(adapter, TimeLineType.StepViewType.RIGHT_STEP_PROGRESS, 3);
```

为1和3添加了自定义drawable，看一下效果图

<div align="center">
    <img src="https://github.com/rangaofei/TimeLine/blob/master/Pics/StepViewCustomDrawable.png" height="480" width="270" >
</div>


## 注解说明

### @TimeLine

用于标记类，被标记的类将作为model使用

- name 不为空的时候生成的adapter名称为name，为空的时候生成的adapter名称为model的name加"Adapter"
- keyLayoutId 用来设置KeyViewHolder的布局文件，必须是string类型，例如："R.layout.item_key"，

- valueLayoutId 用来设置ValueViewHolder的布局文件，必须是string类型，例如："R.layout.item_value"

这里的id必须和下文中将要介绍的TimeLineTextView和TimeLineImageView中的key对应，key为true，则必须对应keyLayoutId，
key为false，则必须对应valueLayoutId。




## TimeLineView的接口

```java
//初始化TimeLine的配置
void setTimeLineConfig(AbstractTimeLineAdapter adapter, TimeLineType type)
```
TimeLineType的类型：
```java
enum StepViewType implements TimeLineType {
        LEFT_STEP_PROGRESS,//左侧显示当前的步骤，右侧显示内容
        RIGHT_STEP_PROGRESS,//右侧显示当前的步骤，左侧显示内容
        TOP_STEP_PROGRESS,//上侧显示当前的步骤，下侧显示内容
        BOTTOM_STEP_PROGRESS,//下侧显示当前的步骤，上侧显示内容
    }
```

```java
//升级当前分隔点，默认使用动画，可以通过设置showAnim来设置
public void updateDividerNum(int dividerNum)
public void updateDividerNum(int dividerNum, boolean showAnim)

```

## TimeLineView的xml配置

#### timePadding

空白位置的大小，也就是用来显示时间轴的宽度(纵向)或者高度(横向)
设置后，自定义时间轴指示器drawable的默认宽和高均为此值的2/3；
默认的时间轴指示器的半径为此值的1/2.

#### timeStrokeWidth

时间轴的粗细。默认值是10dp。

#### timeStrokeColor

时间轴的颜色。默认是灰色。

#### strokeType

时间轴的类型。有两种：normal和noEndPoint。如图所示

#### timeLineType

时间轴的位置，共有四种：left、right、top、bottom

#### timeIndexColor

显示序号的颜色

#### timeIndexSize

显示序号的字的大小

#### stepShowOrder

是否显示序号

#### stepPreColor

设置前景色

#### stepAfterColor

设置后景色

## style可以设置的属性

#### visibleProxy

设置可见性，visible，invisible，gone，和view的可见性一致

#### backgroundProxy

设置背景，和view的background一致

#### clickProxy

设置可点击，与view的clickable一致


