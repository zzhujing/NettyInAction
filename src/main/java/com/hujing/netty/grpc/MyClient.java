package com.hujing.netty.grpc;

import com.hujing.netty.proto.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * @author hujing
 * @date 19-9-22
 */
public class MyClient {
    private static final Logger logger = Logger.getLogger(MyClient.class.getName());

    private ManagedChannel channel;
    private StudentServiceGrpc.StudentServiceBlockingStub stub;
    private StudentServiceGrpc.StudentServiceStub asyncStub;

    public MyClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port)
                // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
                // needing certificates.
                .usePlaintext()
                .build());
    }

    public MyClient(ManagedChannel channel) {
        this.channel = channel;
        this.stub = StudentServiceGrpc.newBlockingStub(channel);
        this.asyncStub = StudentServiceGrpc.newStub(channel);
    }


    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public void sayHello(String username) {
        MyRequest request = MyRequest.newBuilder().setUsername("hujing").build();
        MyResponse response = stub.getRealNameByUsername(request);
        System.out.println(response);
    }

    public void testStreamResponse(String username) {
        MyRequest request = MyRequest.newBuilder().setUsername("hujing").build();
        Iterator<StreamResponse> iter = stub.getStreamResponseByUsername(request);
        while (iter.hasNext()) {
            StreamResponse resp = iter.next();
            System.out.println(resp.getCity() + "==========" + resp.getCode());
        }
    }

    public void testSreamRequest() throws InterruptedException {


        //服务端回调
        StreamObserver<StreamResponseList> streamObserver = new StreamObserver<StreamResponseList>() {
            @Override
            public void onNext(StreamResponseList streamResponseList) {
                streamResponseList.getStreamResponseList().forEach(System.out::println);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println(throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("send message completed");
            }
        };

        //发送流式请求
        StreamObserver<StreamResponse> streamResponseStreamObserver = asyncStub.sendStreamRequest(streamObserver);
        streamResponseStreamObserver.onNext(StreamResponse.newBuilder().setCity("hz").setCode(123).build());
        streamResponseStreamObserver.onNext(StreamResponse.newBuilder().setCity("sh").setCode(123).build());
        streamResponseStreamObserver.onCompleted();
    }

    public void testTwoWayFlow() throws InterruptedException {
        StreamObserver<TestStreamResponse> testStreamResponseStreamObserver = new StreamObserver<TestStreamResponse>() {
            @Override
            public void onNext(TestStreamResponse testStreamResponse) {
                System.out.println(testStreamResponse.getResp());
            }
            @Override
            public void onError(Throwable throwable) {

            }
            @Override
            public void onCompleted() {
                System.out.println("completed");
            }
        };
        StreamObserver<TestStreamRequest> testStreamRequestStreamObserver = asyncStub.sendStreamRequestAndGetResponse(testStreamResponseStreamObserver);

        testStreamRequestStreamObserver.onNext(TestStreamRequest.newBuilder().setReq("req1").build());
        testStreamRequestStreamObserver.onNext(TestStreamRequest.newBuilder().setReq("req2").build());
        testStreamRequestStreamObserver.onNext(TestStreamRequest.newBuilder().setReq("req3").build());
        testStreamRequestStreamObserver.onCompleted();
        TimeUnit.SECONDS.sleep(5);
    }

    public static void main(String[] args) throws InterruptedException {
        MyClient client = new MyClient("localhost", 50051);
        client.sayHello("hujing");

        System.out.println("========================");

        client.testStreamResponse("hujing");
        System.out.println("===========");

        client.testSreamRequest();

        System.out.println("====================");
        client.testTwoWayFlow();
    }
}
