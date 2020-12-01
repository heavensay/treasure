package com.helix.demo.rpc.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;

/**
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
