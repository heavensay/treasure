package com.helix.demo.rpc.grpc.json;

import com.helix.demo.rpc.grpc.gencode.HelloRequest;
import com.helix.demo.rpc.grpc.gencode.HelloResponse;
import com.helix.demo.rpc.grpc.gencode.HelloServiceGrpc;
import io.grpc.*;
import io.grpc.stub.AbstractStub;

import java.util.concurrent.TimeUnit;

import static io.grpc.stub.ClientCalls.blockingUnaryCall;

public class GrpcJsonClient {
    public static void main(String[] args) {
        ManagedChannel channel = null;
        try {
            channel = ManagedChannelBuilder.forAddress("192.168.0.108", 50051).usePlaintext().build();
            HelloJsonStub stub = new HelloJsonStub(channel);

//            HelloServiceGrpc.HelloServiceBlockingStub stub = HelloServiceGrpc.newBlockingStub(channel);

            HelloResponse helloResponse = stub
                    .hello(HelloRequest.newBuilder().setFirstName("Baeldung").setLastName("gRPC").build());

            System.out.println(helloResponse.getGreeting());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (channel != null) {
                try {
                    channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static final class HelloJsonStub extends AbstractStub<GrpcJsonClient.HelloJsonStub> {

        static final MethodDescriptor<HelloRequest, HelloResponse> METHOD_SAY_HELLO =
                HelloServiceGrpc.getHelloMethod().toBuilder(
                    JsonMarshaller.jsonMarshaller(HelloRequest.getDefaultInstance()),
                    JsonMarshaller.jsonMarshaller(HelloResponse.getDefaultInstance())).build();

        protected HelloJsonStub(Channel channel) {
            super(channel);
        }

        protected HelloJsonStub(Channel channel, CallOptions callOptions) {
            super(channel, callOptions);
        }

        @Override
        protected GrpcJsonClient.HelloJsonStub build(Channel channel, CallOptions callOptions) {
            return new GrpcJsonClient.HelloJsonStub(channel, callOptions);
        }

        public HelloResponse hello(HelloRequest request) {
            return blockingUnaryCall(
                    getChannel(), METHOD_SAY_HELLO, getCallOptions(), request);
        }
    }
}