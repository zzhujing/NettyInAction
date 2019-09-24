package com.hujing.netty.grpc;

import com.hujing.netty.proto.*;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * @author hujing
 * @date 19-9-22
 */
public class MyServer {

    private static final Logger logger = Logger.getLogger(MyServer.class.getName());

    private Server server;

    private void start() throws IOException {
        /* The port on which the server should run */
        int port = 50051;
        server = ServerBuilder.forPort(port)
                .addService(new StudentServiceImpl())
                .build()
                .start();
        logger.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // Use stderr here since the logger may have been reset by its JVM shutdown hook.
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            this.stop();
            System.err.println("*** server shut down");
        }));
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final MyServer server = new MyServer();
        server.start();
        server.blockUntilShutdown();
    }

    static class StudentServiceImpl extends StudentServiceGrpc.StudentServiceImplBase {
        @Override
        public void getRealNameByUsername(MyRequest request, StreamObserver<MyResponse> responseObserver) {
            MyResponse response = MyResponse.newBuilder().setRealName("Hello " + request.getUsername()).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }

        @Override
        public void getStreamResponseByUsername(MyRequest request, StreamObserver<StreamResponse> responseObserver) {
            System.out.println("收到客户端数据: " + request.getUsername());
            responseObserver.onNext(StreamResponse.newBuilder().setCity("hz").setCode(123).build());
            responseObserver.onNext(StreamResponse.newBuilder().setCity("sh").setCode(456).build());
            responseObserver.onNext(StreamResponse.newBuilder().setCity("bj").setCode(789).build());
            responseObserver.onCompleted();
        }

        @Override
        public StreamObserver<StreamResponse> sendStreamRequest(StreamObserver<StreamResponseList> responseObserver) {
            return new StreamObserver<StreamResponse>() {
                private StreamResponseList streamResponseList = StreamResponseList.getDefaultInstance();

                @Override
                public void onNext(StreamResponse streamResponse) {
                    System.out.println(streamResponse.toString());
                }

                @Override
                public void onError(Throwable throwable) {
                    System.out.println(throwable.getMessage());
                }

                @Override
                public void onCompleted() {
                    StreamResponseList list = StreamResponseList.newBuilder().addStreamResponse(StreamResponse.newBuilder().setCity("xx").setCode(1).build()).build();
                    responseObserver.onNext(list);
                    responseObserver.onCompleted();
                }
            };
        }


        @Override
        public StreamObserver<TestStreamRequest> sendStreamRequestAndGetResponse(StreamObserver<TestStreamResponse> responseObserver) {
            //客户端的回调
            return new StreamObserver<TestStreamRequest>() {
                @Override
                public void onNext(TestStreamRequest testStreamRequest) {
                    System.out.println(testStreamRequest.getReq());
                }

                @Override
                public void onError(Throwable throwable) {
                    System.out.println(throwable.getMessage());
                }

                @Override
                public void onCompleted() {
                    responseObserver.onNext(TestStreamResponse.newBuilder().setResp("resp1").build());
                    responseObserver.onNext(TestStreamResponse.newBuilder().setResp("resp2").build());
                    responseObserver.onNext(TestStreamResponse.newBuilder().setResp("resp3").build());
                    responseObserver.onCompleted();
                }
            };
        }
    }
}
