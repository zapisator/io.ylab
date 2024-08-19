package io.ylab.backend.infrastructure.rest.controller;

import io.ylab.common.annotation.Endpoint;
import io.ylab.common.annotation.RequestMapping;
import io.ylab.common.request.DefaultForm;
import io.ylab.common.request.Form;
import io.ylab.common.request.Request;
import io.ylab.common.response.DefaultResponse;
import io.ylab.common.response.Response;

import java.util.Collections;
import java.util.List;

import static io.ylab.common.authorization.UserRole.NONE;
import static io.ylab.common.method.MethodType.DELETE;
import static io.ylab.common.method.MethodType.GET;
import static io.ylab.common.method.MethodType.OPTIONS;
import static io.ylab.common.method.MethodType.POST;
import static io.ylab.common.method.MethodType.PUT;

/**
 * Контроллер,  отвечающий за обработку запросов к корневому пути ("/").
 */
@RequestMapping("/")
public class RootController implements CrudController<Form>{

    /**
     * {@inheritDoc}
     */
    @Override
    @Endpoint(method = OPTIONS)
    public Response<List<String>> menu() {
        return DefaultResponse.<List<String>>builder()
                .message("Main Menu Options")
                .data(Collections.singletonList("Main Menu Options"))
                .dataCanonicalClassName(List.class.getCanonicalName())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Endpoint(method = POST, roles = {NONE})
    public Response<Form> createOne(Request request) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Endpoint(method = GET, roles = {NONE})
    public Response<List<Form>> readMany(Request request) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Endpoint(method = GET)
    public Response<Form> readOne(Request request) {
        return DefaultResponse.<Form>builder()
                .message("Main menu:")
                .data(DefaultForm.builder()
                        .fieldsCount(1)
                        .fieldName("menu_choice")
                        .fieldDescription("Enter your choice:\n1. Login\n2. Registration\n0. Exit")
                        .build())
                .dataCanonicalClassName(Form.class.getCanonicalName())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Endpoint(method = PUT, path = "/{carId}", roles = {NONE})
    public Response<Form> updateOne(Request request) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Endpoint(method = DELETE, path = "/{carId}", isTemplate = true, roles = {NONE})
    public Response<Form> deleteOne(Request request) {
        return null;
    }

}
