# AirCalendar
Airbnb-style calendar

[ ![Download](https://api.bintray.com/packages/yongbeam/maven/AirCalendar/images/download.svg) ](https://bintray.com/yongbeam/maven/AirCalendar/_latestVersion)

# Example
<p align="center">
  <img src="sc/sc1.png" width="200"/>
  <img src="sc/sc2.png" width="200"/>
  <img src="sc/sc3.png" width="200"/>
</p>



# Usage

1.Include the library as local library project.
```gradle
  dependencies {
      compile 'com.yongbeom.aircalendar:aircalendar:0.0.1@aar'
  }
```

2.Add AirCalendarDatePickerActivity into your AndroidManifest.xml
```xml
    <activity android:name="com.yongbeom.aircalendar.AirCalendarDatePickerActivity"
            android:theme="@style/Theme.AppCompat.NoActionBar" />
```


3.The AirCalendar configuration is created using the builder pattern.
```java
        AirCalendarIntent intent = new AirCalendarIntent(this);
        intent.isBooking(false);
        intent.isSelect(false);
        intent.setBookingDateArray('Array Dates( format: yyyy-MM-dd');
        intent.setStartDate(yyyy , MM , dd); // int
        intent.setEndDate(yyyy , MM , dd); // int
        intent.isMonthLabels(false);
        startActivityForResult(intent, REQUEST_CODE);
```

4.Override onActivityResult method and handle AirCalendar result.
```java
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if(data != null){
                Toast.makeText(this, "Select Date range : " + data.getStringExtra(AirCalendarDatePickerActivity.RESULT_SELECT_START_DATE) + " ~ " + data.getStringExtra(AirCalendarDatePickerActivity.RESULT_SELECT_END_DATE), Toast.LENGTH_SHORT).show();
            }
        }
    }
```



#### Option
```java
      intent.isBooking(false);      // DEFAULT false
      intent.isSelect(false);       // DEFAULT false
      intent.setBookingDateArray(); // DEFAULT NULL
      intent.setStartDate();        // DEFAULT NULL
      intent.setEndDate();          // DEFAULT NULL
      intent.isMonthLabels(false);  // DEFAULT false
```



#### Result Option
```java
    AirCalendarDatePickerActivity.RESULT_SELECT_START_DATE
    AirCalendarDatePickerActivity.RESULT_SELECT_END_DATE
    AirCalendarDatePickerActivity.RESULT_SELECT_START_VIEW_DATE // Day of the week
    AirCalendarDatePickerActivity.RESULT_SELECT_END_VIEW_DATE   // Day of the week
    AirCalendarDatePickerActivity.RESULT_FLAG
    AirCalendarDatePickerActivity.RESULT_TYPE
    AirCalendarDatePickerActivity.RESULT_STATE
```



## Thanks
>* [CalendarListview](https://github.com/traex/CalendarListview)


# License

Copyright © 2017 copyright YongBeomLee

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
