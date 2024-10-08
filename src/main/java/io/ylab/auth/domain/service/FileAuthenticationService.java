package io.ylab.auth.domain.service;

import io.ylab.auth.domain.model.User;
import io.ylab.common.authorization.UserRole;
import io.ylab.frontend.infrastructure.resources.DefaultResourceProvider;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;

import static java.util.Collections.singletonList;
import static java.util.Objects.requireNonNull;

/**
 * Реализация сервиса аутентификации,
 * которая  загружает  данные  пользователей  из  файла  конфигурации.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileAuthenticationService implements AuthenticationService {

    /**
     * Текущий аутентифицированный пользователь.
     */
    private User currentUser;

    /**
     * Список пользователей,  загруженных  из  файла  конфигурации.
     */
    private final ArrayList<User> users = new ArrayList<>();

    /**
     * Создает новый  экземпляр  сервиса  и  загружает  пользователей  из  файла  конфигурации.
     *
     * @return Новый  экземпляр  сервиса.
     */
    public static FileAuthenticationService create() {
        final FileAuthenticationService authenticationService = new FileAuthenticationService();

        try {
//            final URI classPath = requireNonNull(Thread.currentThread()
//                    .getContextClassLoader()
//                    .getResource(""))
//                    .toURI();
//            final String configPath = Paths.get(classPath)
//                    .getParent()
//                    .getParent()
//                    .getParent()
//                    .getParent()
//                    .resolve("config/config.properties")
//                    .toString();
            authenticationService.loadUsersFromConfig();

            return authenticationService;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Добавляет пользователя в список пользователей.
     *
     * @param user Пользователь для добавления.
     */
    public void addUser(User user) {
        users.add(user);
    }

    /**
     * Загружает пользователей из файла конфигурации.
     *
//     * @param configFilePath Путь к файлу конфигурации.
     * @throws IOException В случае ошибки при чтении файла.
     */
    private void loadUsersFromConfig() throws IOException {
//        try (FileInputStream input = new FileInputStream(configFilePath)) {
        final DefaultResourceProvider resourceProvider = new DefaultResourceProvider("config");
//            final Properties prop = new Properties();
//
//            prop.load(input);

            final String adminUsername = resourceProvider.getString("admin.username");
            final String adminPasswordHash = resourceProvider.getString("admin.passwordHash");
            final String managerUsername = resourceProvider.getString("manager.username");
            final String managerPasswordHash = resourceProvider.getString("manager.passwordHash");
            final String clientUsername = resourceProvider.getString("client.username");
            final String clientPasswordHash = resourceProvider.getString("client.passwordHash");

            users.add(new User(adminUsername, adminPasswordHash, singletonList(UserRole.ADMIN)));
            users.add(new User(managerUsername, managerPasswordHash, singletonList(UserRole.MANAGER)));
            users.add(new User(clientUsername, clientPasswordHash, singletonList(UserRole.CLIENT)));
//        }
    }

    /**
     * Проверяет,  существует  ли  пользователь  с  указанным  именем  и  паролем.
     *
     * @param username Имя пользователя.
     * @param password Пароль.
     * @return true,  если  пользователь  найден,  иначе  false.
     */
    @Override
    public boolean authenticate(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPasswordHash().equals(password)) {
                currentUser = user;
                return true;
            }
        }
        return false;
    }

    /**
     * Возвращает  текущего  аутентифицированного  пользователя.
     *
     * @return Текущий  пользователь,  или  null,  если  пользователь  не  аутентифицирован.
     */
    @Override
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Устанавливает  текущего  аутентифицированного  пользователя.
     *
     * @param user Пользователь  для  установки.
     */
    @Override
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
}