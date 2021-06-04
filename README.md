# YBKChart
YBKChart is a library of 3D graphics charts for Android. :bar_chart:<br>
For more information, see the [Wiki](https://github.com/YunByungKwan/YBKChart/wiki).
<br>
<br>

## Chart List
- [Pie Chart](#pie-chart)
<br>

## Download
Use gradle.

```gradle
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.YunByungKwan:YBKChart:X.X.X'
}
```
<br>

## How to use
Ex) Pie Chart

activity_main.xml >
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <com.kwancorp.ybkchart.piechart.PieChart
        android:id="@+id/pieChart"
        android:layout_width="300dp"
        android:layout_height="300dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

MainActivity.kt >
```kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pieChart = findViewById<PieChart>(R.id.pieChart)
        val list = mutableListOf(
            Item("Java", 1F),
            Item("Kotlin", 1F),
            Item("Python", 1F),
            Item("C++", 1F),
            Item("C", 1F),
            Item("JavaScript", 1F),
            Item("CSS", 1F),
            Item("HTML", 2F)
        )
        pieChart.setItems(list)
        pieChart.setBorderWidth(2F)
        pieChart.setItemTextSize(25F)
        pieChart.setHeight(100F)
    }
}
```
<br>

## Pie Chart
Go to [the document](https://github.com/YunByungKwan/YBKChart/wiki/Pie-Chart).<br>
<img src="https://user-images.githubusercontent.com/51109517/120685984-75e8b900-c4db-11eb-90be-e490c33fdea5.jpg" width=300 height=300/>
