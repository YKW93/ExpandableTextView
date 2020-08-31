# ExpandableTextView
Read More TextView for Android

![](https://user-images.githubusercontent.com/41371709/91629351-4adcb080-ea03-11ea-8210-fbda11379b21.gif)

## Usage
#### Gradle
```gradle
dependencies {
    implementation 'com.wayne.expandabletextview:expandable-textview:1.1.0'
}
```

#### How to use
* xml
```xml
<com.wayne.expandabletextview.ExpandableTextView
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:expandable_text_font="@font/roboto_bold"                                             
    app:expandable_gradient_view_height="16dp"
    app:expandable_text="text"
    app:expandable_text_collapsedLines="3"
    app:expandable_text_color="@android:color/black"
    app:expandable_text_line_spacing_extra="4sp"
    app:expandable_text_size="14sp" />
```

* kotlin
```kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        et_test.stateChangeListener = object : OnStateChangeListener {
            override fun onStateChanged(state: ExpandableTextView.State) {
                tv_current_state.text = state.name
            }
        }
        // change font
        et_test.textTypeface = ResourcesCompat.getFont(this, R.font.roboto_bold)
    }
}
```


#### Attributes
- `expandable_gradient_view_height` (16dp)
- `expandable_text` ("")
- `expandable_text_collapsedLines` (4) 
- `expandable_text_color` (Color.BLACK) 
- `expandable_text_line_spacing_extra` (8dp) 
- `expandable_text_size` (14dp)
- `expandable_text_font`

#### Important note while applying font
- You must create a directory to res/value/ and place the font file within that path.

#### Fetures
- expand/colleapse animation

## License  
```  
Copyright 2020 @Wayne

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
