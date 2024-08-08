package io.ylab.backend.infrastructure.rest.controller;

import io.ylab.common.request.Request;
import io.ylab.common.response.Response;

import java.util.List;

/**
 * Интерфейс для контроллеров,  реализующих CRUD (Create, Read, Update, Delete) операции
 * для определенного типа сущностей.
 *
 * @param <T> Тип сущности,  для которой реализуются CRUD операции.
 */
public interface CrudController<T> {
    /**
     * Возвращает список опций меню,  доступных для данного контроллера.
     *
     * @return Ответ,  содержащий список опций меню.
     */
    Response<List<String>> menu();
    /**
     * Создает новую сущность на основе данных,  переданных в запросе.
     *
     * @param request Запрос,  содержащий данные для создания сущности.
     * @return Ответ,  содержащий информацию о созданной сущности.
     */
    Response<T> createOne(Request request);
    /**
     * Возвращает список всех сущностей.
     *
     * @param request Запрос.
     * @return Ответ,  содержащий список всех сущностей.
     */
    Response<List<T>> readMany(Request request);
    /**
     * Возвращает сущность по ее идентификатору.
     *
     * @param request Запрос,  содержащий идентификатор сущности.
     * @return Ответ,  содержащий информацию о запрошенной сущности.
     */
    Response<T> readOne(Request request);
    /**
     * Обновляет сущность на основе данных,  переданных в запросе.
     *
     * @param request Запрос,  содержащий идентификатор сущности и  обновленные данные.
     * @return Ответ,  содержащий информацию об обновленной сущности.
     */
    Response<T> updateOne(Request request);
    /**
     * Удаляет сущность по ее идентификатору.
     *
     * @param request Запрос,  содержащий идентификатор сущности.
     * @return Ответ,  содержащий информацию об удаленной сущности.
     */
    Response<T> deleteOne(Request request);
}
