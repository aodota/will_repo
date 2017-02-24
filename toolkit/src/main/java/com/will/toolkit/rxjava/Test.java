package com.will.toolkit.rxjava;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import org.apache.commons.lang3.StringUtils;

/**
 * Test
 *
 * @author wangys
 * @version 1.0.0.0 2017/2/24 16:24
 */
public class Test {
    public static void main(String[] args) {
//        System.out.println(StringUtils.chop("hello world"));
        Observable.create(new ObservableOnSubscribe<String>() {
            /**
             * Called for each Observer that subscribes.
             *
             * @param e the safe emitter instance, never null
             * @throws Exception on error
             */
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("hello");
                e.onNext("world");
                e.onComplete();
            }
        }).observeOn(Schedulers.io())
        .subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                System.out.println(s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
