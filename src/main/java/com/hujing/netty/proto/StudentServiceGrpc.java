package com.hujing.netty.proto;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.23.0)",
    comments = "Source: Student.proto")
public final class StudentServiceGrpc {

  private StudentServiceGrpc() {}

  public static final String SERVICE_NAME = "proto.StudentService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<MyRequest,
      MyResponse> getGetRealNameByUsernameMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetRealNameByUsername",
      requestType = MyRequest.class,
      responseType = MyResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<MyRequest,
      MyResponse> getGetRealNameByUsernameMethod() {
    io.grpc.MethodDescriptor<MyRequest, MyResponse> getGetRealNameByUsernameMethod;
    if ((getGetRealNameByUsernameMethod = StudentServiceGrpc.getGetRealNameByUsernameMethod) == null) {
      synchronized (StudentServiceGrpc.class) {
        if ((getGetRealNameByUsernameMethod = StudentServiceGrpc.getGetRealNameByUsernameMethod) == null) {
          StudentServiceGrpc.getGetRealNameByUsernameMethod = getGetRealNameByUsernameMethod =
              io.grpc.MethodDescriptor.<MyRequest, MyResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetRealNameByUsername"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  MyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  MyResponse.getDefaultInstance()))
              .setSchemaDescriptor(new StudentServiceMethodDescriptorSupplier("GetRealNameByUsername"))
              .build();
        }
      }
    }
    return getGetRealNameByUsernameMethod;
  }

  private static volatile io.grpc.MethodDescriptor<MyRequest,
      StreamResponse> getGetStreamResponseByUsernameMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetStreamResponseByUsername",
      requestType = MyRequest.class,
      responseType = StreamResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<MyRequest,
      StreamResponse> getGetStreamResponseByUsernameMethod() {
    io.grpc.MethodDescriptor<MyRequest, StreamResponse> getGetStreamResponseByUsernameMethod;
    if ((getGetStreamResponseByUsernameMethod = StudentServiceGrpc.getGetStreamResponseByUsernameMethod) == null) {
      synchronized (StudentServiceGrpc.class) {
        if ((getGetStreamResponseByUsernameMethod = StudentServiceGrpc.getGetStreamResponseByUsernameMethod) == null) {
          StudentServiceGrpc.getGetStreamResponseByUsernameMethod = getGetStreamResponseByUsernameMethod =
              io.grpc.MethodDescriptor.<MyRequest, StreamResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetStreamResponseByUsername"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  MyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  StreamResponse.getDefaultInstance()))
              .setSchemaDescriptor(new StudentServiceMethodDescriptorSupplier("GetStreamResponseByUsername"))
              .build();
        }
      }
    }
    return getGetStreamResponseByUsernameMethod;
  }

  private static volatile io.grpc.MethodDescriptor<StreamResponse,
      StreamResponseList> getSendStreamRequestMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SendStreamRequest",
      requestType = StreamResponse.class,
      responseType = StreamResponseList.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<StreamResponse,
      StreamResponseList> getSendStreamRequestMethod() {
    io.grpc.MethodDescriptor<StreamResponse, StreamResponseList> getSendStreamRequestMethod;
    if ((getSendStreamRequestMethod = StudentServiceGrpc.getSendStreamRequestMethod) == null) {
      synchronized (StudentServiceGrpc.class) {
        if ((getSendStreamRequestMethod = StudentServiceGrpc.getSendStreamRequestMethod) == null) {
          StudentServiceGrpc.getSendStreamRequestMethod = getSendStreamRequestMethod =
              io.grpc.MethodDescriptor.<StreamResponse, StreamResponseList>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SendStreamRequest"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  StreamResponse.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  StreamResponseList.getDefaultInstance()))
              .setSchemaDescriptor(new StudentServiceMethodDescriptorSupplier("SendStreamRequest"))
              .build();
        }
      }
    }
    return getSendStreamRequestMethod;
  }

  private static volatile io.grpc.MethodDescriptor<TestStreamRequest,
      com.hujing.netty.proto.TestStreamResponse> getSendStreamRequestAndGetResponseMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SendStreamRequestAndGetResponse",
      requestType = TestStreamRequest.class,
      responseType = com.hujing.netty.proto.TestStreamResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<TestStreamRequest,
      com.hujing.netty.proto.TestStreamResponse> getSendStreamRequestAndGetResponseMethod() {
    io.grpc.MethodDescriptor<TestStreamRequest, com.hujing.netty.proto.TestStreamResponse> getSendStreamRequestAndGetResponseMethod;
    if ((getSendStreamRequestAndGetResponseMethod = StudentServiceGrpc.getSendStreamRequestAndGetResponseMethod) == null) {
      synchronized (StudentServiceGrpc.class) {
        if ((getSendStreamRequestAndGetResponseMethod = StudentServiceGrpc.getSendStreamRequestAndGetResponseMethod) == null) {
          StudentServiceGrpc.getSendStreamRequestAndGetResponseMethod = getSendStreamRequestAndGetResponseMethod =
              io.grpc.MethodDescriptor.<TestStreamRequest, com.hujing.netty.proto.TestStreamResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SendStreamRequestAndGetResponse"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  TestStreamRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hujing.netty.proto.TestStreamResponse.getDefaultInstance()))
              .setSchemaDescriptor(new StudentServiceMethodDescriptorSupplier("SendStreamRequestAndGetResponse"))
              .build();
        }
      }
    }
    return getSendStreamRequestAndGetResponseMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static StudentServiceStub newStub(io.grpc.Channel channel) {
    return new StudentServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static StudentServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new StudentServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static StudentServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new StudentServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class StudentServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void getRealNameByUsername(MyRequest request,
                                      io.grpc.stub.StreamObserver<MyResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetRealNameByUsernameMethod(), responseObserver);
    }

    /**
     */
    public void getStreamResponseByUsername(MyRequest request,
                                            io.grpc.stub.StreamObserver<StreamResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetStreamResponseByUsernameMethod(), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<StreamResponse> sendStreamRequest(
        io.grpc.stub.StreamObserver<StreamResponseList> responseObserver) {
      return asyncUnimplementedStreamingCall(getSendStreamRequestMethod(), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<TestStreamRequest> sendStreamRequestAndGetResponse(
        io.grpc.stub.StreamObserver<com.hujing.netty.proto.TestStreamResponse> responseObserver) {
      return asyncUnimplementedStreamingCall(getSendStreamRequestAndGetResponseMethod(), responseObserver);
    }

    @Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetRealNameByUsernameMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                MyRequest,
                MyResponse>(
                  this, METHODID_GET_REAL_NAME_BY_USERNAME)))
          .addMethod(
            getGetStreamResponseByUsernameMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                MyRequest,
                StreamResponse>(
                  this, METHODID_GET_STREAM_RESPONSE_BY_USERNAME)))
          .addMethod(
            getSendStreamRequestMethod(),
            asyncClientStreamingCall(
              new MethodHandlers<
                StreamResponse,
                StreamResponseList>(
                  this, METHODID_SEND_STREAM_REQUEST)))
          .addMethod(
            getSendStreamRequestAndGetResponseMethod(),
            asyncBidiStreamingCall(
              new MethodHandlers<
                TestStreamRequest,
                com.hujing.netty.proto.TestStreamResponse>(
                  this, METHODID_SEND_STREAM_REQUEST_AND_GET_RESPONSE)))
          .build();
    }
  }

  /**
   */
  public static final class StudentServiceStub extends io.grpc.stub.AbstractStub<StudentServiceStub> {
    private StudentServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private StudentServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected StudentServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new StudentServiceStub(channel, callOptions);
    }

    /**
     */
    public void getRealNameByUsername(MyRequest request,
                                      io.grpc.stub.StreamObserver<MyResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetRealNameByUsernameMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getStreamResponseByUsername(MyRequest request,
                                            io.grpc.stub.StreamObserver<StreamResponse> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getGetStreamResponseByUsernameMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<StreamResponse> sendStreamRequest(
        io.grpc.stub.StreamObserver<StreamResponseList> responseObserver) {
      return asyncClientStreamingCall(
          getChannel().newCall(getSendStreamRequestMethod(), getCallOptions()), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<TestStreamRequest> sendStreamRequestAndGetResponse(
        io.grpc.stub.StreamObserver<com.hujing.netty.proto.TestStreamResponse> responseObserver) {
      return asyncBidiStreamingCall(
          getChannel().newCall(getSendStreamRequestAndGetResponseMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   */
  public static final class StudentServiceBlockingStub extends io.grpc.stub.AbstractStub<StudentServiceBlockingStub> {
    private StudentServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private StudentServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected StudentServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new StudentServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public MyResponse getRealNameByUsername(MyRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetRealNameByUsernameMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<StreamResponse> getStreamResponseByUsername(
        MyRequest request) {
      return blockingServerStreamingCall(
          getChannel(), getGetStreamResponseByUsernameMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class StudentServiceFutureStub extends io.grpc.stub.AbstractStub<StudentServiceFutureStub> {
    private StudentServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private StudentServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected StudentServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new StudentServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<MyResponse> getRealNameByUsername(
        MyRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetRealNameByUsernameMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_REAL_NAME_BY_USERNAME = 0;
  private static final int METHODID_GET_STREAM_RESPONSE_BY_USERNAME = 1;
  private static final int METHODID_SEND_STREAM_REQUEST = 2;
  private static final int METHODID_SEND_STREAM_REQUEST_AND_GET_RESPONSE = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final StudentServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(StudentServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_REAL_NAME_BY_USERNAME:
          serviceImpl.getRealNameByUsername((MyRequest) request,
              (io.grpc.stub.StreamObserver<MyResponse>) responseObserver);
          break;
        case METHODID_GET_STREAM_RESPONSE_BY_USERNAME:
          serviceImpl.getStreamResponseByUsername((MyRequest) request,
              (io.grpc.stub.StreamObserver<StreamResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @Override
    @SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SEND_STREAM_REQUEST:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.sendStreamRequest(
              (io.grpc.stub.StreamObserver<StreamResponseList>) responseObserver);
        case METHODID_SEND_STREAM_REQUEST_AND_GET_RESPONSE:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.sendStreamRequestAndGetResponse(
              (io.grpc.stub.StreamObserver<com.hujing.netty.proto.TestStreamResponse>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class StudentServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    StudentServiceBaseDescriptorSupplier() {}

    @Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return StudentProto.getDescriptor();
    }

    @Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("StudentService");
    }
  }

  private static final class StudentServiceFileDescriptorSupplier
      extends StudentServiceBaseDescriptorSupplier {
    StudentServiceFileDescriptorSupplier() {}
  }

  private static final class StudentServiceMethodDescriptorSupplier
      extends StudentServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    StudentServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (StudentServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new StudentServiceFileDescriptorSupplier())
              .addMethod(getGetRealNameByUsernameMethod())
              .addMethod(getGetStreamResponseByUsernameMethod())
              .addMethod(getSendStreamRequestMethod())
              .addMethod(getSendStreamRequestAndGetResponseMethod())
              .build();
        }
      }
    }
    return result;
  }
}
