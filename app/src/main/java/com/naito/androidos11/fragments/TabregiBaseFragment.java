package com.naito.androidos11.fragments;

import android.os.AsyncTask;

import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
public class TabregiBaseFragment extends Fragment {
    OkHttpClient client = new OkHttpClient();
    protected  void hoge() {

//        Request.Builder;
//        OkHttpClient.Builder.
    }
    void httpGet(@NotNull String url) throws IOException {

        ExecutorService executorService  = Executors.newSingleThreadExecutor();
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                Request request = new Request.Builder().url(url).build();

                try {
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            // 圏外等、ネットワークが使えない状況下で入る。
                            System.out.println("onFailure!!!");
                            e.printStackTrace();
                            OnHttpError(-999);
//                            throw e;

                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            // HTTP通信した結果。HTTP_CODE等はここで。
                            System.out.println("onResponse!!!" + response.code());
                            byte[] ret = response.body().bytes();

                            OnHttpComplete(ret);


                        }
                    });
                } catch(Exception e) {
                    System.out.println("えら〜");
                    e.printStackTrace();
                    OnHttpError(-1);

                }
//                finally {
//                }
            }
        });



    }


    protected void OnHttpComplete(byte[] buf) {
        System.out.println("-------------OnComplete data size=" + buf.length + "bytes.");
    }
    protected void OnHttpError(int errCode) {
        System.out.println("-------------OnHttpError errCode=" + errCode);
    }
    protected void OnHttpProgress(long current, long contentSize) {
        System.out.println("-------------OnHttpProgress=" + current + "/" + contentSize);
    }

}
