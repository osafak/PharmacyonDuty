package com.osafak.edirnenobetcieczane;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.async.util.Charsets;
import com.koushikdutta.ion.Ion;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "asd";
    private List<PharmaModel> mPharmacy;
    private RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pharmacy_list);

        mRecyclerView=(RecyclerView)findViewById(R.id.pharmacy_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c.getTime());

        Log.d("dene",formattedDate);

        Ion.with(this)
                .load("http://www.edirneeo.org.tr/?p=nobetci&s=3")
                .setBodyParameter("p", "nobetci")
                .setBodyParameter("s", "3")
                .setBodyParameter("tarih1", formattedDate)
                .setBodyParameter("tarih2", formattedDate)
                .setBodyParameter("oku", "1")
                .setBodyParameter("ilce_aktif","aktif")
                .setBodyParameter("Submit", "++listele++")
                .asString(Charsets.ISO_8859_1)
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        int startIndex=result.indexOf("<table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"2\" cellspacing=\"2\" class=\"kenar_yazi\" >");
                        String str=result.substring(startIndex,result.indexOf("</table>",startIndex));
                        str = str.trim().replaceAll("[\\t\\n\\r]", " ");
                        str=str.replace("Ý","İ");
                        str=str.replace("Þ","Ş");
                        str=str.replace("Ð","Ğ");
                        Log.d(TAG, str);
                        final String regex = "<tr .*?>.*?<td.*?class=\"kenar_yazi\" .*?>.*?<\\/td>.*?<td .*? class=\"kenar_yazi\">(.*?)<\\/td>.*?<td.*?class=\"kenar_yazi\">(.*?)<\\/td>.*?<td.*?class=\"kenar_yazi\">(.*?)<\\/td>.*?<td.*?class=\"kenar_yazi\">(.*?)<\\/td>.*?<\\/tr>";

                        mPharmacy=new ArrayList<PharmaModel>();
                        final Pattern pattern = Pattern.compile(regex);
                        final Matcher matcher = pattern.matcher(str);
                        while (matcher.find()) {
                            System.out.println("Group " + 1 + ": " + matcher.group(1));
                            PharmaModel mPharma=new PharmaModel();
                            mPharma.setName(matcher.group(1));
                            mPharma.setPhone(matcher.group(2));
                            mPharma.setDate(matcher.group(3));
                            mPharma.setAddress(matcher.group(4));
                            mPharmacy.add(mPharma);
                        }
                        mRecyclerView.setAdapter(new ListeAdapter(mPharmacy, getApplicationContext()));
                    }
                });

    }
}

