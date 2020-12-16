package com.helix.demo.rpc.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;

/**
 * grpc 简单demo；
 *
 * grpc server和client代码由maven plug:protobuf-maven-plugin自动生成；目录在target/generate-test-resources/protobuf下面
 *
 * @author lijianyu@yunloan.net
 * @date 2020-11-18 17:40
 */
public class GrpcServer {
    public static void main(String[] args) {
        try {

            int port = 50051;
            final Server server = ServerBuilder.forPort(port)
                    .addService(new HelloServiceImpl())
                    .build()
                    .start();
            System.out.println("Server started, listening on " + port);
            server.awaitTermination();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
