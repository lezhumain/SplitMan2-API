package com.dju.demo;

import com.dju.demo.helpers.FileHelper;
import com.dju.demo.helpers.IpHelper;
import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String args[]) throws InterruptedException {
//        final String target = "./target.json";
//        final String json = "{\"a\": \"1fn\"}";
//
//        try {
//            FileHelper.get_instance().writeFile(target, json);
//            final String res = FileHelper.get_instance().readFile(target);
//            System.out.println(res);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        //Create the observable
//        Single<String> testSingle = Single.just("Hello World");
//
//        //Create an observer
//        Disposable disposable = testSingle
//                .delay(2, TimeUnit.SECONDS, Schedulers.io())
//                .subscribeWith(
//                        new DisposableSingleObserver<String>() {
//
//                            @Override
//                            public void onError(Throwable e) {
//                                e.printStackTrace();
//                            }
//
//                            @Override
//                            public void onSuccess(String value) {
//                                System.out.println(value);
//                            }
//                        });
//        Thread.sleep(3000);
//        //start observing
//        disposable.dispose();
//        System.out.println("After ?");

        System.out.println("");
    }
}
