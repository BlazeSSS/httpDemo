package com.example.http.demo;


import java.util.concurrent.Flow.*;
import java.util.concurrent.SubmissionPublisher;

public class FlowDemo {
    public static void main(String[] args) throws InterruptedException {
        // 1. 定义发布者，发布的数据类型是 Integer
        // 使用 SubmissionPublisher
        SubmissionPublisher<Integer> publiser = new SubmissionPublisher<>();

        // 2. 定义处理器，对数据进行过滤，并转换为 String 类型
        MyProcessor processor = new MyProcessor();

        // 3. 发布者 和 处理器 建立订阅关系
        System.out.println("-- 执行 publiser.subscribe -- start");
        publiser.subscribe(processor);
        System.out.println("-- 执行 publiser.subscribe -- end");

        // 4. 定义最终订阅者，消费 String 类型数据
        Subscriber<String> subscriber = new Subscriber<>() {

            private Subscription subscription;

            @Override
            public void onSubscribe(Subscription subscription) {
                System.out.println("订阅者 - onSubscribe - Start");

                // 保存订阅关系，需要用它来给发布者相应
                this.subscription = subscription;

                // 亲求一个数据
                this.subscription.request(1);

                System.out.println("订阅者 - onSubscribe - End");
            }

            @Override
            public void onNext(String item) {
                System.out.println("订阅者 - onNext - Start");
                // 接受到一个数据，处理
                System.out.println("订阅者 - 接受到数据： " + item);

                // 处理完调用request再请求一个数据
                this.subscription.request(1);

                System.out.println("订阅者 - onNext - End");
            }

            @Override
            public void onError(Throwable throwable) {
                // 出现了异常(例如处理数据的时候产生了异常)
                throwable.printStackTrace();

                // 我们可以告诉发布者, 后面不接受数据了
                this.subscription.cancel();
            }

            @Override
            public void onComplete() {
                // 全部数据处理完了(发布者关闭了)
                System.out.println("处理完了!");
            }
        };

        // 5. 处理器 和 最终订阅者 建立订阅关系
        System.out.println("-- 执行 processor.subscribe -- start");
        processor.subscribe(subscriber);
        System.out.println("-- 执行 processor.subscribe -- end");

        // 6. 生产数据，并发布
        System.out.println("-- 发布者 发布数据：211 -- start");
        publiser.submit(211);
        System.out.println("-- 发布者 发布数据：211 -- end");
        System.out.println("-- 发布者 发布数据：111 -- start");
        publiser.submit(111);
        System.out.println("-- 发布者 发布数据：111 -- end");

        // 7. 结束后，关闭发布者
        System.out.println("-- 发布者 关闭 -- start");
        publiser.close();
        System.out.println("-- 发布者 关闭 -- end");

        // 主线程延迟停止，否者数据没有消费就退出
        Thread.currentThread().join(1000);
    }
}

class MyProcessor extends SubmissionPublisher<String> implements Processor<Integer, String> {

    private Subscription subscription;

    @Override
    public void onSubscribe(Subscription subscription) {
        System.out.println("处理器 - onSubscribe - Start");

        // 保存订阅关系，需要用它来给发布者相应
        this.subscription = subscription;

        // 亲求一个数据
        this.subscription.request(1);

        System.out.println("处理器 - onSubscribe - End");
    }

    @Override
    public void onNext(Integer item) {
        System.out.println("处理器 - onNext - Start");
        // 接受到一个数据，处理
        System.out.println("处理器 - 接受到数据： " + item);

        // 过滤掉小于0的, 然后发布出去
        if (item > 0) {
            this.submit("转换后的数据:" + item);
        }

        // 处理完调用request再请求一个数据
        this.subscription.request(1);

        System.out.println("处理器 - onNext - End");
    }

    @Override
    public void onError(Throwable throwable) {
        // 出现了异常(例如处理数据的时候产生了异常)
        throwable.printStackTrace();

        // 我们可以告诉发布者, 后面不接受数据了
        this.subscription.cancel();
    }

    @Override
    public void onComplete() {
        // 全部数据处理完了(发布者关闭了)
        System.out.println("处理器处理完了!");
        // 关闭发布者
        this.close();
    }
}