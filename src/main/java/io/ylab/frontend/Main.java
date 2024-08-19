package io.ylab.frontend;

import io.ylab.frontend.application.controller.ActionRouter;

public class Main {
    public static void main(String[] args) {
        final ActionRouter actionRouter = ActionRouter.create();
        actionRouter.run();
//        DefaultMenuView rootView = DefaultMenuView.create("root");
//        DefaultFieldView loginView = DefaultFieldView.create("login");
//        DefaultFieldView registrationView = DefaultFieldView.create("registration");
//
//        DefaultMenuView adminView = DefaultMenuView.create("admin");
//        DefaultMenuView adminLogsView = DefaultMenuView.create("admin-logs");
//        DefaultMenuView adminUserView = DefaultMenuView.create("admin-user");
//
//        DefaultMenuView clientView = DefaultMenuView.create("client");
//        DefaultDetailsView clientCarsView = DefaultDetailsView.create(Collections.<Car>emptyList(), "client-cars");
//        DefaultDetailView clientCarDetailView = DefaultDetailView.create(
//                Car.builder().build(), "client-car-detail"
//        );
//        DefaultMenuView clientCartView = DefaultMenuView.create("client-cart");
//        DefaultDetailsView clientOrdersView = DefaultDetailsView.create(
//                Collections.emptyList(), "client-orders");
//
//        DefaultMenuView managerView = DefaultMenuView.create("manager");
//        DefaultDetailsView managerCarsView = DefaultDetailsView.create(
//                Collections.emptyList(), "manager-cars");
//        DefaultDetailView managerCarDetailsView = DefaultDetailView.create(
//                Order.builder().build(), "manager-order-details");
//        DefaultDetailsView managerOrdersView = DefaultDetailsView.create(
//                Collections.emptyList(), "manager-orders");
//
//
//        System.out.println(clientCarsView);
    }

}