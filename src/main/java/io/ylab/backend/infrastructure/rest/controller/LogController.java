package io.ylab.backend.infrastructure.rest.controller;

import io.ylab.common.request.Request;
import io.ylab.common.response.DefaultResponse;
import io.ylab.common.annotation.Endpoint;
import io.ylab.common.annotation.RequestMapping;
import io.ylab.common.response.Response;
import io.ylab.backend.domain.model.Log;

import java.util.Collections;
import java.util.List;

import static io.ylab.common.authorization.UserRole.ADMIN;
import static io.ylab.common.authorization.UserRole.CLIENT;
import static io.ylab.common.authorization.UserRole.MANAGER;
import static io.ylab.common.method.MethodType.DELETE;
import static io.ylab.common.method.MethodType.GET;
import static io.ylab.common.method.MethodType.OPTIONS;
import static io.ylab.common.method.MethodType.POST;
import static io.ylab.common.method.MethodType.PUT;

/**
 * Контроллер,  отвечающий за обработку запросов,  связанных с логами.
 */
@RequestMapping("/logs")
public class LogController implements CrudController<Log> {
    /**
     * {@inheritDoc}
     */
    @Override
    @Endpoint(method = OPTIONS, roles = {ADMIN, MANAGER, CLIENT})
    public Response<List<String>> menu() {
        return DefaultResponse.<List<String>>builder()
                .message("menu")
                .data(Collections.singletonList("menu"))
                .dataCanonicalClassName(List.class.getCanonicalName())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Endpoint(method = POST, roles = {ADMIN})
    public Response<Log> createOne(Request request) {
        return DefaultResponse.<Log>builder()
                .message("createOne")
                .data(Log.builder().build())
                .dataCanonicalClassName(Log.class.getCanonicalName())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Endpoint(method = GET, roles = {ADMIN})
    public Response<List<Log>> readMany(Request request) {
        return DefaultResponse.<List<Log>>builder()
                .message("seeAll")
                .data(Collections.singletonList(Log.builder().build()))
                .dataCanonicalClassName(List.class.getCanonicalName())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Endpoint(method = GET, path = "/{logId}", isTemplate = true, roles = {ADMIN})
    public Response<Log> readOne(Request request) {
        return DefaultResponse.<Log>builder()
                .message("seeOne")
                .data(Log.builder().build())
                .dataCanonicalClassName(Log.class.getCanonicalName())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Endpoint(method = PUT, path = "/{logId}", roles = {ADMIN})
    public Response<Log> updateOne(Request request) {
        return DefaultResponse.<Log>builder()
                .message("editOne")
                .data(Log.builder().build())
                .dataCanonicalClassName(Log.class.getCanonicalName())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Endpoint(method = DELETE, path = "/{logId}", isTemplate = true, roles = {CLIENT})
    public Response<Log> deleteOne(Request request) {
        return DefaultResponse.<Log>builder()
                .message("deleteOne")
                .data(Log.builder().build())
                .dataCanonicalClassName(Log.class.getCanonicalName())
                .build();
    }
}