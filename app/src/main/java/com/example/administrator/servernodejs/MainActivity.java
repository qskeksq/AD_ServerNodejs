package com.example.administrator.servernodejs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.administrator.servernodejs.domain.Bbs;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recycler;
    List<Bbs> data;
    RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lambdaTest();
        initView();
        data = new ArrayList<>();
        adapter = new RecyclerAdapter(data, this);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));
    }

    private void lambdaTest() {
        new Thread(() -> Log.i("Lambda", "running =============== ok")).start();
    }

    private void initView() {
        recycler = (RecyclerView) findViewById(R.id.recycler);
    }

    private void loader(){
        // 1. 레트로핏 생성
        Retrofit client = new Retrofit.Builder()
                .baseUrl(IBbs.SERVER)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        // 2. 서비스 연결
        IBbs myBbs = client.create(IBbs.class);

        // 3. 서비스의 특정 함수 호출
        Observable<List<Bbs>> observable = myBbs.read();

        // 4. subscribe 등록
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        //  데이터를 꺼내고

                        // 어댑터를 세팅하고

                        // 어댑터 갱신
                );
    }

    interface IBbs {
        String SERVER = "http://192.168.10.85/";

        @GET("bbs")
        Observable<List<Bbs>> read();

        @POST
        void write(Bbs bbs);

        @PUT
        void update(Bbs bbs);

        @DELETE
        void delete(Bbs bbs);
    }

    interface IUser {

    }

}
