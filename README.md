# sakatimeline

一个简单的时间线控件，采用recyclerview实现。

## 使用方式

```groovy
implementation 'com.rangaofei:sakatimeline:0.0.6''
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
binding.tlv.setTimeLineConfig(adapter, TimeLineType.LEFT_STEP_PROGRESS);
```
这样我们就完成了基本设置。
运行可以看到效果图：

![simple_stepview_left]()