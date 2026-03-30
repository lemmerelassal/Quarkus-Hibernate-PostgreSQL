package com.example.grpc;

import java.util.List;

import io.grpc.stub.StreamObserver;
import io.quarkus.grpc.GrpcService;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@GrpcService
public class HelloGrpcServiceImpl extends HelloGrpcServiceGrpc.HelloGrpcServiceImplBase {

    @Inject
    EntityManager entityManager;

    @Override
    @Transactional
    public void getAtomNames(AtomNameRequest request, StreamObserver<AtomNameResponse> responseObserver) {
        List<String> atomNames = entityManager
                .createQuery("SELECT f.pdbId FROM FastaSequence f ORDER BY f.pdbId ASC", String.class)
                .setMaxResults(100)
                .getResultList();

        AtomNameResponse response = AtomNameResponse.newBuilder()
                .addAllAtomNames(atomNames)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
