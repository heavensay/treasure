package com.helix.demo.rpc.grpc.json;

import com.helix.demo.rpc.grpc.HelloServiceImpl;
import com.helix.demo.rpc.grpc.gencode.HelloRequest;
import com.helix.demo.rpc.grpc.gencode.HelloResponse;
import com.helix.demo.rpc.grpc.gencode.HelloServiceGrpc;
import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerServiceDefinition;
import io.grpc.stub.ServerCalls;
import io.grpc.stub.StreamObserver;

import static io.grpc.stub.ServerCalls.asyncUnaryCall;

/**
 * grpc json序列化demo
 *
 *
 * @author lijianyu@yunloan.net
 * @date 2020-11-18 17:40
 */
public class GrpcJsonServer {
    public static void main(String[] args) {
        try {

            int port = 50051;
            final Server server = ServerBuilder.forPort(port)
                    .addService(new HelloImpl())
                    .build()
                    .start();
            System.out.println("Server started, listening on " + port);

            server.awaitTermination();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class HelloImpl implements BindableService {

        private void hello(HelloRequest req, StreamObserver<HelloResponse> responseObserver) {
            HelloResponse reply = HelloResponse.newBuilder().setGreeting("Hello " + req.getFirstName() + " " + req.getLastName()).build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }

        @Override
        public ServerServiceDefinition bindService() {
            return io.grpc.ServerServiceDefinition
                    .builder(HelloServiceGrpc.getServiceDescriptor().getName())
                    .addMethod(GrpcJsonClient.HelloJsonStub.METHOD_SAY_HELLO,
                            asyncUnaryCall(
                                    new ServerCalls.UnaryMethod<HelloRequest, HelloResponse>() {
                                        @Override
                                        public void invoke(
                                                HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
                                            hello(request, responseObserver);
                                        }
                                    }))
                    .build();
        }
    }
}
