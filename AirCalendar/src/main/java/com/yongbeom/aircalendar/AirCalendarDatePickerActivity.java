/***********************************************************************************
 * The MIT License (MIT)
 * <p/>
 * Copyright (c) 2017 LeeYongBeom
 * https://github.com/yongbeam
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 ***********************************************************************************/
package com.yongbeom.aircalendar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yongbeom.aircalendar.core.DatePickerController;
import com.yongbeom.aircalendar.core.DayPickerView;
import com.yongbeom.aircalendar.core.SimpleMonthAdapter;
import com.yongbeom.aircalendar.core.selectDateModel;
import com.yongbeom.aircalendar.core.util.AirCalendarUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class AirCalendarDatePickerActivity extends AppCompatActivity implements DatePickerController {

    public final static String EXTRA_FLAG = "FLAG";
    public final static String EXTRA_IS_BOOIKNG = "IS_BOOING";
    public final static String EXTRA_IS_SELECT = "IS_SELECT";
    public final static String EXTRA_BOOKING_DATES = "BOOKING_DATES";
    public final static String EXTRA_SELECT_DATE_SY = "SELECT_START_DATE_Y";
    public final static String EXTRA_SELECT_DATE_SM = "SELECT_START_DATE_M";
    public final static String EXTRA_SELECT_DATE_SD = "SELECT_START_DATE_D";
    public final static String EXTRA_SELECT_DATE_EY = "SELECT_END_DATE_Y";
    public final static String EXTRA_SELECT_DATE_EM = "SELECT_END_DATE_M";
    public final static String EXTRA_SELECT_DATE_ED = "SELECT_END_DATE_D";
    public final static String EXTRA_IS_MONTH_LABEL = "IS_MONTH_LABEL";

    public final static String RESULT_SELECT_START_DATE = "start_date";
    public final static String RESULT_SELECT_END_DATE = "end_date";
    public final static String RESULT_SELECT_START_VIEW_DATE = "start_date_view";
    public final static String RESULT_SELECT_END_VIEW_DATE = "end_date_view";
    public final static String RESULT_FLAG = "flag";
    public final static String RESULT_TYPE = "result_type";
    public final static String RESULT_STATE = "result_state";

    private DayPickerView pickerView;
    private TextView tv_start_date;
    private TextView tv_end_date;
    private TextView tv_popup_msg;
    private RelativeLayout rl_done_btn;
    private RelativeLayout rl_reset_btn;
    private RelativeLayout rl_popup_select_checkout_info_ok;
    private RelativeLayout rl_checkout_select_info_popup;
    private RelativeLayout rl_iv_back_btn_bg;

    private String SELECT_START_DATE = "";
    private String SELECT_END_DATE = "";
    private int YEAR = 2017;

    private String FLAG = "all";
    private boolean isSelect = false;
    private boolean isBooking = false;
    private boolean isMonthLabel = false;
    private boolean isOk = false;
    private boolean isReset = false;
    private ArrayList<String> dates;
    private selectDateModel selectDate;

    private int sYear = 0;
    private int sMonth = 0;
    private int sDay = 0;
    private int eYear = 0;
    private int eMonth = 0;
    private int eDay = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aicalendar_activity_date_picker);

        Intent getData = getIntent();
        FLAG = getData.getStringExtra(EXTRA_FLAG) != null ? getData.getStringExtra(EXTRA_FLAG):"all";
        isBooking = getData.getBooleanExtra(EXTRA_IS_BOOIKNG , false);
        isSelect = getData.getBooleanExtra(EXTRA_IS_SELECT , false);
        isMonthLabel = getData.getBooleanExtra(EXTRA_IS_MONTH_LABEL , false);
        dates = getData.getStringArrayListExtra(EXTRA_BOOKING_DATES);

        sYear = getData.getIntExtra(EXTRA_SELECT_DATE_SY , 0);
        sMonth = getData.getIntExtra(EXTRA_SELECT_DATE_SM , 0);
        sDay = getData.getIntExtra(EXTRA_SELECT_DATE_SD , 0);

        eYear = getData.getIntExtra(EXTRA_SELECT_DATE_EY , 0);
        eMonth = getData.getIntExtra(EXTRA_SELECT_DATE_EM , 0);
        eDay = getData.getIntExtra(EXTRA_SELECT_DATE_ED , 0);


        if(sYear == 0 || sMonth == 0 || sDay == 0
                || eYear == 0 || eMonth == 0 || eDay == 0){
            selectDate = new selectDateModel();
            isSelect = false;
        }


        init();

    }

    private void init(){

        rl_done_btn = (RelativeLayout) findViewById(R.id.rl_done_btn);
        tv_start_date = (TextView) findViewById(R.id.tv_start_date);
        tv_end_date = (TextView) findViewById(R.id.tv_end_date);
        tv_popup_msg = (TextView) findViewById(R.id.tv_popup_msg);
        rl_checkout_select_info_popup = (RelativeLayout) findViewById(R.id.rl_checkout_select_info_popup);
        rl_reset_btn = (RelativeLayout) findViewById(R.id.rl_reset_btn);
        rl_popup_select_checkout_info_ok = (RelativeLayout) findViewById(R.id.rl_popup_select_checkout_info_ok);
        rl_checkout_select_info_popup = (RelativeLayout) findViewById(R.id.rl_checkout_select_info_popup);
        rl_iv_back_btn_bg = (RelativeLayout) findViewById(R.id.rl_iv_back_btn_bg);

        pickerView = (DayPickerView) findViewById(R.id.pickerView);
        pickerView.setIsMonthDayLabel(isMonthLabel);

        SimpleDateFormat formatter = new SimpleDateFormat ( "yyyy", Locale.KOREA );
        Date currentTime = new Date ( );
        String dTime = formatter.format ( currentTime );
        YEAR = Integer.valueOf(dTime) + 1;

        if(dates != null && dates.size() != 0 && isBooking){
            pickerView.setShowBooking(true);
            pickerView.setBookingDateArray(dates);
        }

        if(isSelect){
            selectDate = new selectDateModel();
            pickerView.setSelected(true);
            selectDate.setFristYear(sYear);
            selectDate.setFristMonth(sMonth);
            selectDate.setFristDay(sDay);
            selectDate.setLastYear(eYear);
            selectDate.setLastMonth(eMonth);
            selectDate.setLastDay(eDay);

            pickerView.setSelected(selectDate);
        }

        pickerView.setController(this);


        rl_done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((SELECT_START_DATE == null || SELECT_START_DATE.equals("")) && (SELECT_END_DATE == null || SELECT_END_DATE.equals(""))){
                    SELECT_START_DATE = "";
                    SELECT_END_DATE = "";
                }else{
                    if(FLAG.equals("all")){
                        if(SELECT_START_DATE == null || SELECT_START_DATE.equals("")){
                            tv_popup_msg.setText("날짜를 모두 선택해주세요.");
                            rl_checkout_select_info_popup.setVisibility(View.VISIBLE);
                            return;
                        }else if(SELECT_END_DATE == null || SELECT_END_DATE.equals("")){
                            tv_popup_msg.setText("체크아웃 날짜를 선택해주세요.");
                            rl_checkout_select_info_popup.setVisibility(View.VISIBLE);
                            return;
                        }
                    }else{
                        if(SELECT_START_DATE == null || SELECT_START_DATE.equals("")){
                            tv_popup_msg.setText("날짜를 모두 선택해주세요.");
                            rl_checkout_select_info_popup.setVisibility(View.VISIBLE);
                            return;
                        }
                    }
                }


                Intent resultIntent = new Intent();
                resultIntent.putExtra(RESULT_SELECT_START_DATE , SELECT_START_DATE );
                resultIntent.putExtra(RESULT_SELECT_END_DATE , SELECT_END_DATE );
                resultIntent.putExtra(RESULT_SELECT_START_VIEW_DATE , tv_start_date.getText().toString() );
                resultIntent.putExtra(RESULT_SELECT_END_VIEW_DATE , tv_end_date.getText().toString() );
                resultIntent.putExtra(RESULT_FLAG , FLAG );
                resultIntent.putExtra(RESULT_TYPE , FLAG );
                resultIntent.putExtra(RESULT_STATE , "done" );
                setResult(RESULT_OK , resultIntent);
                finish();
            }
        });

        rl_reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isReset = true;
                SELECT_START_DATE = "";
                SELECT_END_DATE = "";
                setContentView(R.layout.aicalendar_activity_date_picker);
                init();
            }
        });

        rl_popup_select_checkout_info_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_checkout_select_info_popup.setVisibility(View.GONE);
            }
        });

        rl_iv_back_btn_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public int getMaxYear() {
        return YEAR;
    }

    @Override
    public void onDayOfMonthSelected(int year, int month, int day) {
        String dateDay = "";
        String setDate = "";

        try{
            String month_str = (month+1)+"";
            String yyyy_str = year+"";
            String day_str = day+"";

            if((month+1) < 10){
                month_str = "0"+month_str;
            }

            if(day < 10){
                day_str = "0"+day_str;
            }

            setDate = yyyy_str+month_str+day_str;
            dateDay = AirCalendarUtils.getDateDay(setDate , "yyyyMMdd");
        }catch (Exception e){

        }


        if(FLAG.equals("start")){
            tv_start_date.setTextColor(0xff4a4a4a);

            String month_str = (month+1)+"";
            if((month+1) < 10){
                month_str = "0"+month_str;
            }

            String day_str = day+"";
            if(day < 10){
                day_str = "0"+day_str;
            }
            tv_start_date.setText(year+"-"+month_str+"-"+day_str+" "+dateDay);
            SELECT_START_DATE = year+"-"+month_str+"-"+day_str+"";
        }else if(FLAG.equals("end")){
            tv_end_date.setTextColor(0xff4a4a4a);

            String month_str = (month+1)+"";
            if((month+1) < 10){
                month_str = "0"+month_str;
            }

            String day_str = day+"";
            if(day < 10){
                day_str = "0"+day_str;
            }
            tv_end_date.setText(year+"-"+month_str+"-"+day_str+" "+dateDay);
            SELECT_START_DATE = year+"-"+month_str+"-"+day_str+"";
        }else if(FLAG.equals("all")){

            if(SELECT_END_DATE != null || !SELECT_END_DATE.equals("")){
                // 체크아웃 날짜가 있으면 모두 리셋
                SELECT_START_DATE = "";
                SELECT_END_DATE = "";
                tv_start_date.setText("날짜선택");
                tv_start_date.setTextColor(0xff1abc9c);
                tv_end_date.setText("날짜선택");
                tv_end_date.setTextColor(0xff1abc9c);


                // 초기화 하고 체크인 날짜 셋
                tv_start_date.setTextColor(0xff4a4a4a);
                String month_str = (month+1)+"";
                if((month+1) < 10){
                    month_str = "0"+month_str;
                }

                String day_str = day+"";
                if(day < 10){
                    day_str = "0"+day_str;
                }
                SELECT_START_DATE = year+"-"+month_str+"-"+day_str;
                tv_start_date.setText(year+"-"+month_str+"-"+day_str+" "+dateDay);

            }else if(SELECT_START_DATE != null && !SELECT_START_DATE.equals("")){
                // 시작 날짜가 있으면 체크아웃 날짜만 리셋
                SELECT_END_DATE = "";
                tv_end_date.setText("날짜선택");
                tv_end_date.setTextColor(0xff1abc9c);


                // 선택됬으니 체크아웃 날짜 셋
                tv_end_date.setTextColor(0xff4a4a4a);
                String month_str = (month+1)+"";
                if((month+1) < 10){
                    month_str = "0"+month_str;
                }

                String day_str = day+"";
                if(day < 10){
                    day_str = "0"+day_str;
                }
                tv_end_date.setText(year+"-"+month_str+"-"+day_str+" "+dateDay);

            }
        }
    }

    @Override
    public void onDateRangeSelected(SimpleMonthAdapter.SelectedDays<SimpleMonthAdapter.CalendarDay> selectedDays) {


        try{
            Calendar cl = Calendar.getInstance();

            // 체크인
            cl.setTimeInMillis(selectedDays.getFirst().getDate().getTime());
            String start_month_str = (cl.get(Calendar.MONTH)+1)+"";
            if((cl.get(Calendar.MONTH)+1) < 10){
                start_month_str = "0"+start_month_str;
            }

            String start_day_str = cl.get(Calendar.DAY_OF_MONTH)+"";
            if(cl.get(Calendar.DAY_OF_MONTH) < 10){
                start_day_str = "0"+start_day_str;
            }
            String startSetDate = cl.get(Calendar.YEAR)+start_month_str+start_day_str;
            String startDateDay = AirCalendarUtils.getDateDay(startSetDate , "yyyyMMdd");
            String startDate = cl.get(Calendar.YEAR) + "-" + start_month_str + "-" + start_day_str;

            // 체크아웃
            cl.setTimeInMillis(selectedDays.getLast().getDate().getTime());
            String end_month_str = (cl.get(Calendar.MONTH)+1)+"";
            if((cl.get(Calendar.MONTH)+1) < 10){
                end_month_str = "0"+end_month_str;
            }

            String end_day_str = cl.get(Calendar.DAY_OF_MONTH)+"";
            if(cl.get(Calendar.DAY_OF_MONTH) < 10){
                end_day_str = "0"+end_day_str;
            }
            String endSetDate = cl.get(Calendar.YEAR)+end_month_str+end_day_str;
            String endDateDay = AirCalendarUtils.getDateDay(endSetDate , "yyyyMMdd");
            String endDate = cl.get(Calendar.YEAR) + "-" + end_month_str + "-" + end_day_str;

            SELECT_START_DATE = startDate;
            SELECT_END_DATE = endDate;

            if(SELECT_START_DATE.equals(SELECT_END_DATE)){
                // 같은날짜 선택시 리셋
                SELECT_START_DATE = "";
                SELECT_END_DATE = "";

                tv_start_date.setText("날짜선택");
                tv_start_date.setTextColor(0xff1abc9c);

                tv_end_date.setText("날짜선택");
                tv_end_date.setTextColor(0xff1abc9c);
            }else{
                isOk = true;
                if(isBooking){
                    for (int i = 0; i < dates.size(); i++) {
                        if (startDate != null && endDate != null) {
                            if (!AirCalendarUtils.checkStartDateToEndDate(startDate, endDate)) {
                                try {
                                    int date_cnt = AirCalendarUtils.getDiffDay(startDate, endDate);
                                    for (int k = 1; k < date_cnt; k++) {
                                        if(dates.get(i).equals(AirCalendarUtils.getAfterDate(startDate, k))){
                                            isOk = false;
                                            AlertDialog.Builder ab = new AlertDialog.Builder(AirCalendarDatePickerActivity.this);
                                            ab.setMessage("이미 예약이 완료된 날짜입니다.\n다시 선택해주세요.");
                                            ab.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            });
                                            ab.show();

                                            break;
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
                if(isOk){
                    tv_start_date.setText(startDate + " " + startDateDay);
                    tv_start_date.setTextColor(0xff4a4a4a);

                    tv_end_date.setText(endDate + " " + endDateDay);
                    tv_end_date.setTextColor(0xff4a4a4a);
                }else{
                    SELECT_START_DATE = "";
                    SELECT_END_DATE = "";

                    tv_start_date.setText("날짜선택");
                    tv_start_date.setTextColor(0xff1abc9c);

                    tv_end_date.setText("날짜선택");
                    tv_end_date.setTextColor(0xff1abc9c);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
