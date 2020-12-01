package com.helix.demo.rpc.grpc;

import com.helix.demo.rpc.grpc.gencode.HelloRequest;
import com.helix.demo.rpc.grpc.gencode.HelloResponse;
import com.helix.demo.rpc.grpc.gencode.HelloServiceGrpc;
import io.grpc.stub.StreamObserver;

/**
 * @author lijianyu@yunloan.net
 * @date 2020-11-18 17:35
 */
public class HelloServiceImpl extends HelloServiceGrpc.HelloServiceImplBase {
    @Override
    public void hello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        String greeting = new StringBuilder()
                .append("Hello, ")
                .append(request.getFirstName())
                .append(" ")
                .append(request.getLastName())
                .toString();

        HelloResponse response = HelloResponse.newBuilder()
                .setGreeting(greeting)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }
}
