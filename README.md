# sakatimeline

一个简单的时间线控件，采用recyclerview实现。

## 使用方式

```groovy
implementation 'com.rangaofei:sakatimeline:0.0.9''
```
然后修改app级别的module的build.gralde 文件：

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

假如没问题的话就集成成功了

### 使用setpview

首先要定义一个model，这个model可以直接要编写一些注解

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

![simple_stepview_left](https://github.com/rangaofei/TimeLine/blob/master/Pics/simple_stepview_all.png)

![simple_stepview_left](https://github.com/rangaofei/TimeLine/blob/master/Pics/SimpleStepViewUnnormal.png)

![simple_stepview_left](https://github.com/rangaofei/TimeLine/blob/master/Pics/StepViewAnim.gif)
## 注解使用

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
