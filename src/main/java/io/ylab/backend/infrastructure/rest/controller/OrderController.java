package io.ylab.backend.infrastructure.rest.controller;

import io.ylab.common.annotation.Endpoint;
import io.ylab.common.annotation.RequestMapping;
import io.ylab.common.request.Request;
import io.ylab.common.response.DefaultResponse;
import io.ylab.common.response.Response;
import io.ylab.backend.domain.model.Order;

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
 * Контроллер,  отвечающий за обработку запросов,  связанных с заказами.
 */
@RequestMapping("/orders")
public class OrderController implements CrudController<Order> {
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
    @Endpoint(method = POST, roles = {CLIENT})
    public Response<Order> createOne(Request request) {
        return DefaultResponse.<Order>builder()
                .message("createOne")
                .data(Order.builder().build())
                .dataCanonicalClassName(Order.class.getCanonicalName())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Endpoint(method = GET, roles = {MANAGER, CLIENT})
    public Response<List<Order>> readMany(Request request) {
        return DefaultResponse.<List<Order>>builder()
                .message("seeAll")
                .data(Collections.singletonList(Order.builder().build()))
                .dataCanonicalClassName(List.class.getCanonicalName())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Endpoint(method = GET, path = "/{userId}", isTemplate = true, roles = {MANAGER, CLIENT})
    public Response<Order> readOne(Request request) {
        return DefaultResponse.<Order>builder()
                .message("seeOne")
                .data(Order.builder().build())
                .dataCanonicalClassName(Order.class.getCanonicalName())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Endpoint(method = PUT, path = "/{userId}", roles = {CLIENT})
    public Response<Order> updateOne(Request request) {
        return DefaultResponse.<Order>builder()
                .message("editOne")
                .data(Order.builder().build())
                .dataCanonicalClassName(Order.class.getCanonicalName())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Endpoint(method = DELETE, path = "/{userId}", isTemplate = true, roles = {CLIENT})
    public Response<Order> deleteOne(Request request) {
        return DefaultResponse.<Order>builder()
                .message("deleteOne")
                .data(Order.builder().build())
                .dataCanonicalClassName(Order.class.getCanonicalName())
                .build();
    }
}
