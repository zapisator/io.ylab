package io.ylab.frontend;

import io.ylab.backend.domain.model.Car;
import io.ylab.frontend.domain.model.Order;
import io.ylab.frontend.presentation.view.DefaultDetailView;
import io.ylab.frontend.presentation.view.DefaultDetailsView;
import io.ylab.frontend.presentation.view.DefaultFieldView;
import io.ylab.frontend.presentation.view.DefaultMenuView;

import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        DefaultMenuView rootView = DefaultMenuView.create("root");
        DefaultFieldView loginView = DefaultFieldView.create("login");
        DefaultFieldView registrationView = DefaultFieldView.create("registration");

        DefaultMenuView adminView = DefaultMenuView.create("admin");
        DefaultMenuView adminLogsView = DefaultMenuView.create("admin-logs");
        DefaultMenuView adminUserView = DefaultMenuView.create("admin-user");

        DefaultMenuView clientView = DefaultMenuView.create("client");
        DefaultDetailsView clientCarsView = DefaultDetailsView.create(Collections.<Car>emptyList(), "client-cars");
        DefaultDetailView clientCarDetailView = DefaultDetailView.create(
                Car.builder().build(), "client-car-detail"
        );
        DefaultMenuView clientCartView = DefaultMenuView.create("client-cart");
        DefaultDetailsView clientOrdersView = DefaultDetailsView.create(
                Collections.emptyList(), "client-orders");

        DefaultMenuView managerView = DefaultMenuView.create("manager");
        DefaultDetailsView managerCarsView = DefaultDetailsView.create(
                Collections.emptyList(), "manager-cars");
        DefaultDetailView managerCarDetailsView = DefaultDetailView.create(
                Order.builder().build(), "manager-order-details");
        DefaultDetailsView managerOrdersView = DefaultDetailsView.create(
                Collections.emptyList(), "manager-orders");


        System.out.println(clientCarsView);
    }

}